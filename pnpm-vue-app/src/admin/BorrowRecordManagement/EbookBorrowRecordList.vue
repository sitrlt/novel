<template>
  <div class="topTool" style="width: 98%">
    <el-input v-model="sname" placeholder="请输入文章名字搜索"
              @input="handleSearchName" :prefix-icon="Search">
    </el-input>
    <el-button type="primary" :icon="Plus" @click="handleAdd"
               style="margin-left: 20px;" class="custom-pink-button">添加数据
    </el-button>
    <el-button type="danger" :icon="Delete" @click="handleDelList"
               v-if="multipleSelection.length>0">删除选中数据
    </el-button><!--当数据表格中有选中数据时才显示-->
  </div>
  <!-- 显示员工信息的表格 -->
  <el-table :data="displayedItems"
            :header-cell-style="{ background: '#f8bbd0',color:'white'}"
            empty-text="暂无数据"
            @selection-change="handleSelectionChange"
            @sort-change="handleSortChange"
            border
            style="width: 98%"
  >
    <el-table-column type="selection"/>
    <el-table-column prop="id" label="序号" width="100" ></el-table-column>
    <el-table-column prop="reader.username" label="昵称" width="100" ></el-table-column>
    <el-table-column prop="book.title" label="文章" width="250" fixed></el-table-column>
    <el-table-column prop="borrowDate" label="借阅日期" width="120"></el-table-column>
    <el-table-column prop="dueDate" label="应还日期" width="150"> </el-table-column>
    <el-table-column prop="borrowingFee" label="借阅费用" width="100">
      <template #default="scope">
        {{ scope.row.borrowingFee}}<span v-if="scope.row.borrowingFee>0">元</span>
      </template>
    </el-table-column>
    <el-table-column prop="returnDate" label="归还日期" width="120"> </el-table-column>
    <el-table-column prop="status" label="状态" width="120"> </el-table-column>
    <el-table-column label="操作" min-width="350">
      <template #default="{ row }">
        <el-button type="danger" size="small" icon="Delete"
                   @click="onDelete(row)">删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
      :current-page="currentPage"
      :page-size="pageSize"
      :page-sizes="[4,10,15,20]"
      :total="total"
      layout="sizes,prev, pager, next"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
  />
</template>

<script setup>
import axios from "axios";
import {computed, onMounted, reactive, watch} from 'vue'
import {ref} from 'vue'
import {Plus, Delete, Edit,Search,ZoomIn} from '@element-plus/icons-vue'
import {ElMessage, ElMessageBox} from 'element-plus'
//员工数组
// 创建响应式数据
let members = reactive([])
let currentPage = ref(1);// 定义当前页码初始值为1
let pageSize = ref(4);// 定义每页显示的数据条数为5
let total = ref(0);// 初始化数据总条数
let displayedItems = ref(members.slice(0, pageSize));//初始化当前页显示数据
const dialogFormVisible = ref(false)//初始化弹窗不显示
const dialogVisible = ref(false)
let tableform = ref({
})//初始化弹窗表单数据
let dialogType = ref('add')//初始化弹窗类型为增加弹窗
const dialogTitle = computed(() => {//设置弹窗的标题
  return dialogType.value === 'add' ? '新增数据' : '编辑数据'
})
const publisherNames = ref([]);



/*// 发送请求获取数据
axios.get("http://localhost:8080/EbookBorrowRecord/findAll").then((response) => {
  members = response.data;
  total.value =members.length;
  displayedItems.value = members; // 更新ref变量的值\
}).catch((error) => {
  console.error("请求出错:", error);
  // 处理错误，例如显示错误信息或采取其他措施
})*/
// 监听数据变化
onMounted(() => {
  getData();
})
// 监听sex属性的变化，并更新表单
watch(tableform.value.sex, (newVal) => {
  tableform.value.sex = newVal;
});

//获取当前页数据
const getData = () => {
  axios.get(`http://localhost:8080/EbookBorrowRecord/findByPage`, {
    params: {
      pageNum: currentPage.value,
      pageSize: pageSize.value
    }
  })
      .then(response => {
        displayedItems.value = response.data.records;
        console.log(displayedItems.value)
        // 筛选出 status 为已归还的元素

        total.value = response.data.total;
      })
      .catch(error => {
        console.error(error);
      });
};
//处理页码变化点击事件
const handleCurrentChange = (pageNum) => {
  currentPage.value = pageNum;
  getData();
};
//处理每页显示多少条事件
const handleSizeChange=(pagesize)=>{
  pageSize.value=pagesize;
  getData();
}
//存储当前页数据副本，方便清空搜索框还原当前页数据
let sname = ref('')//初始化搜索框的值
const handleSearchName = (val) => {
  if (val.length > 0) {
    axios.get(`http://localhost:8080/borrowRecord/search`,{params:{keyword:val}}).then(response => {
      displayedItems.value = response.data;
      console.log(displayedItems.value)
      ElMessage({type: 'success', message: '查询成功！',})
    }).catch(error => {
      console.error(error);
    });
  } else {
    getData();
  }
};
// 定义 handleSortChange 方法，接收 sortData 参数
const handleSortChange = (sortData) => {
  const {prop, order} = sortData;
  displayedItems.value = displayedItems.value.sort((a, b) => {
    if (order === 'ascending') {//升序
      return a[prop] > b[prop] ? 1 : -1;
    } else {//降序
      return a[prop] < b[prop] ? 1 : -1;

    }
  });
}

//创建添加数据按钮事件handleAdd
const handleAdd = () => { //箭头函数
  dialogFormVisible.value = true;
  dialogType.value = 'add';
  tableform.value = {}
}

const checkInventoryAndBorrow = async (row) => {
  try {
    // 先检查库存
    const inventoryResponse = await axios.get(`http://localhost:8080/bookInventory/availableCount/${row.book.isbn}`);
    const availableCount = inventoryResponse.data;
    if (availableCount > 0) {
      await leEdit(row);
    } else {
      ElMessage({ type: 'error', message: '库存不足，无法借阅!' });
    }
  } catch (error) {
    ElMessage.error('检查库存失败');
    console.error("检查库存请求出错:", error);
  }
};

const leEdit = async (row) => {
  try {
    // 表单数据设置为传入行数据，并更新状态为借阅中
    tableform.value = { ...row, status: '借阅中' };
    // 发送修改请求
    const response = await axios.put(
        `http://localhost:8080/borrowRecord/count/${tableform.value.id}`,
        tableform.value
    );
    // 根据后端返回结果进行提示
    if (response.data === '借阅成功') {
      ElMessage({ type: 'success', message: '借阅成功!' });
    }
  } catch (error) {
    ElMessage.error('修改失败');
    console.error("请求出错:", error);
  }
};

const rejectBorrowRequest = (request) => {
  ElMessage.success('已成功拒绝借阅请求');
  const rejectionMessage = '读者有逾期未归还书籍，不得借阅';
  // 使用 localStorage 存储拒绝信息，以读者姓名为键
  localStorage.setItem(request.reader.username, rejectionMessage);
};
//创建删除行数据的方法delrow，代码如下：
const delrow = (row) => { // 定义一个函数，用于删除某行数据
  axios.delete(`http://localhost:8080/borrowRecord/id/${row.id}`)
      .then(() => {
        getData();
      })
      .catch((error) => {
        console.error("请求出错:", error);
        // 处理错误，例如显示错误信息或采取其他措施
      });
}
//修改删除按钮点击事件方法handleDelete，代码如下：
const onDelete = (row) => {
  ElMessageBox.confirm(
      '您确定要删除姓名为【' + row.reader.username + '】的数据吗?', '危险操作',
      {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning',}
  )
      .then(() => {
        delrow(row)
        ElMessage({type: 'success', message: '完成删除！',})
      })
      .catch(() => {
        ElMessage({type: 'info', message: '取消删除!',})
      })
}
//创建handleSelectionChange方法
// 创建响应式变量multipleSelection，用于存储当前选中的数据行
let multipleSelection = ref([])
// 处理表格行选中状态变化的方法，val是当前选中的数据行数组
const handleSelectionChange = (val) => {
  // 清空数组，确保每次更新都是最新选中状态
  multipleSelection.value = []
  val.forEach(item => { // 遍历选中的数据行数组
    multipleSelection.value.push(item) // 将选中的每一行数据存入multipleSelection数组中
  })
}
const handleDelList = () => {
  ElMessageBox.confirm(
      '您确定要删除选择的数据吗?',
      '危险操作',
      {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning',}
  ).then(() => {
    multipleSelection.value.forEach(item => {
      delrow(item)
    })
    ElMessage({type: 'success', message: '完成批量删除！',})
  }).catch(() => {
    ElMessage({type: 'info', message: '取消删除!',})
  })
}
</script>
<style scoped>
.topTool {
  display: flex;
  justify-content: space-between;
  margin-bottom: 12px;
  margin-top: 16px;
}
.cover-image {
  max-width: 100%;
  width: 400px;
  height: 450px;
}

.book-info {
  display: flex;
  flex-direction: column;
}

.book-title {
  font-size: 1.2em;
  margin-bottom: 10px;
}

.book-details {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-gap: 10px;
  margin-bottom: 10px;
}

.book-details p {
  margin: 0;
}

.book-description {
  margin-top: 10px;
}
.form {
  width: 400px;
  margin-bottom: 20px;
}

/*在ElementPlus中，可使用组件名称的类选择器选择对应组件，从而修改默认组件样式*/
.el-pagination { /*选择分页组件，默认采取Flex布局*/
  justify-content: center; /*水平方向居中对齐*/
  margin-top: 8px;

}
.custom-pink-button {
  background-color: #f8bbd0;
  color: white;
  border: none;
  border-radius: 4px;
}

.custom-pink-button:hover,
.custom-pink-button:focus {
  background-color: #faa3c1;
  color: white;
  border: none;
}

.custom-pink-button:active {
  background-color: #faa3c1;
  color: white;
  border: none;
}


</style>
