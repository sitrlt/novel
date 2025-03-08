<template>
  <div class="topTool">
    <el-button type="primary" :icon="Plus" @click="handleAdd" style="margin-left: 20px;">添加数据</el-button>
    <el-button type="danger" :icon="Delete" @click="handleDelList" v-if="multipleSelection.length > 0">删除选中数据</el-button>
  </div>
  <div>
    <el-table :data="membersData" :header-cell-style="{ background: '#f6f9fa' }" empty-text="暂无数据" @selection-change="handleSelectionChange" @sort-change="handleSortChange" style="width: 100%;">
      <el-table-column type="selection" />
      <el-table-column prop="id" label="主键" sortable></el-table-column>
      <el-table-column prop="receiver" label="收货人" sortable></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="x" label="是否默认地址"></el-table-column>
      <el-table-column label="操作" min-width="180">
        <template #default="{ row }">
          <el-button type="primary" size="small" icon="Edit" @click="leEdit(row)">编辑</el-button>
          <el-button type="danger" size="small" icon="Delete" @click="onDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination layout="prev, pager, next, sizes" :total="totalMembers" :page-size="pageSize" :page-sizes="[5, 10, 20]" @current-change="handleCurrentChange" @size-change="handleSizeChange"></el-pagination>
    <el-dialog v-model="dialogFormVisible" :title="dialogTitle">
      <el-form :model="tableform">
        <el-form-item label="ID" :label-width="100">
          <el-input v-model="tableform.id" autocomplete="off" :disabled="true" />
        </el-form-item>
        <el-form-item label="收货人" :label-width="100">
          <el-input v-model="tableform.receiver" autocomplete="off" />
        </el-form-item>
        <el-form-item label="手机号" :label-width="100">
          <el-input v-model="tableform.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="地址" :label-width="100">
          <el-input v-model="tableform.address" autocomplete="off" />
        </el-form-item>
        <el-form-item label="是否默认地址" :label-width="100">
          <el-input v-model="tableform.x" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="dialogOk">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import axios from "axios";
import { reactive, ref, onMounted, watch, computed } from 'vue';
import { ElMessage, ElMessageBox } from "element-plus";
import { Search as SearchIcon, Plus as PlusIcon, Delete as DeleteIcon, Edit as EditIcon } from '@element-plus/icons-vue';

// 创建响应式数据
let members = reactive([]);

// 创建ref变量
let pageSize = ref(5);
let currentPage = ref(1);
let totalMembers = ref(0);
let membersData = ref(members.slice(0, pageSize.value));
const dialogFormVisible = ref(false);
let dialogType = ref('add');
const dialogTitle = computed(() => dialogType.value === 'add'? '新增数据' : '编辑数据');
let tableform = ref({});

// 发送请求获取数据
axios.get("http://localhost:8080/address/findAll").then((response) => {
  members = response.data;
  totalMembers.value = members.length;
  membersData.value = members;
}).catch((error) => {
  console.error("请求出错:", error);
});

// 监听数据变化
onMounted(() => {
  getData();
});

//获取当前页数据
const getData = () => {
  axios.get('http://localhost:8080/address/findByPage', {
    params: {
      pageNum: currentPage.value,
      pageSize: pageSize.value
    }
  })
      .then(response => {
        membersData.value = response.data.records;
        totalMembers.value = response.data.total;
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
const handleSizeChange = (pagesize) => {
  pageSize.value = pagesize;
  getData();
};

//存储当前页数据副本，方便清空搜索框还原当前页数据
const sname = ref('');
const handleSearchName = (val) => {
  if (val.length > 0) {
    axios.get(`http://localhost:8080/user/name/${val}`).then(response => {
      membersData.value = response.data;
    }).catch(error => {
      console.error(error);
    });
  } else {
    getData();
  }
};

//创建添加数据按钮事件handleAdd
const handleAdd = () => {
  dialogFormVisible.value = true;
  dialogType.value = 'add';
  tableform.value = {};
};

const inputId = (isDuplicate) => {
  if (isDuplicate) {
    ElMessageBox.alert('ID已存在', '警告', {
      confirmButtonText: '确定',
      type: 'warning'
    });
  } else {
    console.log("ID does not exist");
  }
};

const dialogOk = () => {
  dialogFormVisible.value = false;
  if (dialogType.value === 'add') {
    const newUser = { ...tableform.value };
    console.log(newUser);
    axios
        .post("http://localhost:8080/address/create", tableform.value)
        .then((response) => {
          ElMessage({ type: 'success', message: '添加成功!' });
          getData();
        })
        .catch((error) => {
          console.error("请求出错:", error);
        });
  } else {
    axios.put(`http://localhost:8080/address/uid/${tableform.value.id}`, tableform.value)
        .then(() => {
          ElMessage({ type: 'success', message: '修改成功!' });
          getData();
        })
        .catch((error) => {
          console.error("请求出错:", error);
        });
  }
};

function leEdit(row) {
  dialogFormVisible.value = true;
  dialogType.value = 'edit';
  tableform.value = { ...row };
}

const delrow = (row) => {
  axios.delete(`http://localhost:8080/address/id/${row.id}`)
      .then(() => {
        getData();
      })
      .catch((error) => {
        console.error("请求出错:", error);
      });
};

//创建handleSelectionChange方法
// 创建响应式变量multipleSelection，用于存储当前选中的数据行
let multipleSelection = ref([]);
// 处理表格行选中状态变化的方法，val是当前选中的数据行数组
const handleSelectionChange = (val) => {
  multipleSelection.value = [];
  val.forEach(item => {
    multipleSelection.value.push(item);
  });
};

// 定义 handleSortChange 方法，接收 sortData 参数
const handleSortChange = (sortData) => {
  const { prop, order } = sortData;
  membersData.value = membersData.value.sort((a, b) => {
    if (order === 'ascending') {
      return a[prop] > b[prop]? 1 : -1;
    } else {
      return a[prop] < b[prop]? 1 : -1;
    }
  });
};

const onDelete = (row) => {
  ElMessageBox.confirm(
      '您确定要删除姓名为【' + row.receiver + '】的数据吗?', '危险操作',
      { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' }
  )
      .then(() => {
        delrow(row);
        ElMessage({ type: 'success', message: '完成删除！' });
      })
      .catch(() => {
        ElMessage({ type: 'info', message: '取消删除!' });
      });
};

const handleDelList = () => {
  ElMessageBox.confirm(
      '您确定要删除选择的数据吗?',
      '危险操作',
      { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' }
  ).then(() => {
    multipleSelection.value.forEach(item => {
      delrow(item);
    });
    ElMessage({ type: 'success', message: '完成批量删除！' });
  }).catch(() => {
    ElMessage({ type: 'info', message: '取消删除!' });
  });
};
</script>

<style scoped>
.form {
  width: 400px;
  margin-bottom: 20px;
}

/*在ElementPlus中，可使用组件名称的类选择器选择对应组件，从而修改默认组件样式*/
.el-pagination { /*选择分页组件，默认采取Flex布局*/
  justify-content: center; /*水平方向居中对齐*/
  margin-top: 8px;
}

.topTool {
  display: flex;
  flex-direction: row-reverse;
  justify-content: space-between;
  margin-bottom: 8px;
}
</style>