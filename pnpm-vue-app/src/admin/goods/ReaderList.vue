<template>
  <div class="topTool" style="width: 98%">
    <el-input v-model="sname" placeholder="请输入姓名搜索"
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
            style="width: 98%"

  >
    <el-table-column type="selection"/>
    <el-table-column prop="id" label="序号" width="100"></el-table-column>
    <el-table-column  prop="username" label="昵称" width="100"></el-table-column>
    <el-table-column fixed label="头像" width="100">
      <template #default="scope">
        <el-image :src="scope.row.coverImage" fit="contain">
          <template #error>
            <div class="image-slot">
              <el-icon><Picture /></el-icon>
            </div>
          </template>
        </el-image>
      </template>
    </el-table-column>
    <el-table-column prop="password" label="密码" width="100"></el-table-column>
    <el-table-column prop="email" label="邮箱" width="200"></el-table-column>
    <el-table-column prop="address" label="地址" width="150"></el-table-column>
    <el-table-column prop="phone" label="手机号" width="120"></el-table-column>
    <el-table-column prop="accountBalance" label="账户余额" width="120">
      <template #default="scope">
        {{ scope.row.accountBalance}}<span v-if="scope.row.accountBalance>0">元</span>
      </template>
    </el-table-column>
    <el-table-column prop="registrationDateStr" label="注册日期" width="200"></el-table-column>
    <el-table-column label="操作" min-width="180">
      <template #default="{ row }">
        <el-button type="primary" size="small" icon="Edit" class="custom-pink-button"
                   @click="leEdit(row)">编辑
        </el-button>
        <el-button type="danger" size="small" icon="Delete"
                   @click="onDelete(row)">删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[4,10,15,20]"
      :total="total"
      layout="sizes,prev, pager, next"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
  />
  <el-dialog v-model="dialogFormVisible" :title="dialogTitle">
    <el-form :model="tableform">
      <el-form-item label="ID" :label-width="100">
        <el-input v-model="tableform.id" autocomplete="off"  disabled/>
      </el-form-item>
      <el-form-item label="昵称" :label-width="100">
        <el-input v-model="tableform.username" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="头像" :label-width="100">
        <el-upload
            ref="coverUpload"
            :show-file-list="false"
            class="avatar-uploader"
            :auto-upload="false"
            :on-change="handleCoverChange"
            accept="image/png, image/jpeg, image/webp"
        >
          <template v-if="tableform.coverImage">
            <div class="cover-preview">
              <img
                  :src="tableform.coverImage"
                  class="avatar"
                  alt="封面预览"
                  width="100"
              />
              <div class="hover-mask">
                <el-icon @click.stop="removeCover"><Delete /></el-icon>
              </div>
            </div>
          </template>
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="密码" :label-width="100">
        <el-input v-model="tableform.password" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="邮箱" :label-width="100">
        <el-input v-model="tableform.email" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="手机号" :label-width="100">
        <el-input v-model="tableform.phone" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="地址" :label-width="100">
        <el-input v-model="tableform.address" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="账户余额" :label-width="100">
        <el-input v-model="tableform.accountBalance" autocomplete="off"/>
      </el-form-item>
    </el-form>
    <template #footer>
    <span class="dialog-footer">
      <el-button type="primary" @click="dialogOk">
        确定
      </el-button>
    </span>
    </template>
  </el-dialog>
</template>

<script setup>
import axios from "axios";
import {computed, onMounted, reactive, watch} from 'vue'
import {ref} from 'vue'
import {Plus, Delete, Edit} from '@element-plus/icons-vue'
import {ElMessage, ElMessageBox} from 'element-plus'
//员工数组
// 创建响应式数据
let members = reactive([])
let currentPage = ref(1);// 定义当前页码初始值为1
let pageSize = ref(4);// 定义每页显示的数据条数为5
let total = ref(0);// 初始化数据总条数
let displayedItems = ref(members.slice(0, pageSize));//初始化当前页显示数据
const dialogFormVisible = ref(false)//初始化弹窗不显示
let tableform = ref({
})//初始化弹窗表单数据
let dialogType = ref('add')//初始化弹窗类型为增加弹窗
const dialogTitle = computed(() => {//设置弹窗的标题
  return dialogType.value === 'add' ? '新增数据' : '编辑数据'
})
// 发送请求获取数据
axios.get("http://localhost:8080/reader/findAll").then((response) => {
  members = response.data;
  total.value =members.length;
  displayedItems.value = members; // 更新ref变量的值\
}).catch((error) => {
  console.error("请求出错:", error);
  // 处理错误，例如显示错误信息或采取其他措施
})

//上传头像
const handleCoverChange = (file) => {
  const validTypes = ['image/jpeg', 'image/png', 'image/webp'];
  const fileType = file.raw.type;
  const fileExtension = file.name.split('.').pop().toLowerCase();

  if (!validTypes.includes(fileType) && !['jpg', 'jpeg', 'png', 'webp'].includes(fileExtension)) {
    ElMessage.error('仅支持 JPG/PNG/WEBP 格式');
    return false;
  }

  if (file.size / 1024 / 1024 > 2) {
    ElMessage.error('图片大小不能超过2MB');
    return false;
  }

  // 显示图片预览
  const reader = new FileReader();
  reader.onload = (e) => {
    tableform.value.coverImage = e.target.result; // Base64 数据
  };
  reader.readAsDataURL(file.raw);

  // 保存文件对象，用于后续上传
  tableform.value.coverFile = file.raw;
};

const removeCover = () => {
  tableform.value.coverImage = null;
  tableform.value.coverFile = null;
};

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
  axios.get(`http://localhost:8080/reader/findByPage`, {
    params: {
      pageNum: currentPage.value,
      pageSize: pageSize.value
    }
  })
      .then(response => {
        displayedItems.value = response.data.records;
        console.log(response.data)
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
    axios.get(`http://localhost:8080/reader/search`,{params:{keyword:val}}).then(response => {
      displayedItems.value = response.data;
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
// 图片上传逻辑
const uploadFile = () => {
  return new Promise((resolve, reject) => {
    if (!tableform.value.coverFile) {
      ElMessage.error('请先选择图片');
      reject(new Error('未选择图片'));
      return;
    }

    // 创建 FormData 并上传文件
    const formData = new FormData();
    formData.append('file', tableform.value.coverFile);

    axios.post('http://localhost:8080/reader/upload-cover', formData)
        .then((response) => {
          ElMessage.success('图片上传成功');
          tableform.value.coverImage = response.data; // 更新为后端返回的图片路径
          resolve();
        })
        .catch((error) => {
          ElMessage.error('图片上传失败');
          reject(error);
        });
  });
};

//创建弹窗确定按钮事件dialogOk
const dialogOk = () => {
  dialogFormVisible.value = false; // 关闭对话框

  if (dialogType.value === 'add') {
    // 添加数据的逻辑
    uploadFile().then(() => {
      // 确保图片上传成功后再发送添加请求
      axios.post("http://localhost:8080/reader/create", tableform.value)
          .then((response) => {
            ElMessage({ type: 'success', message: '添加成功!' });
            getData(); // 重新获取数据
          })
          .catch((error) => {
            ElMessage.error('添加失败');
            console.error("请求出错:", error);
          });
    }).catch((error) => {
      ElMessage.error('图片上传失败，无法添加数据');
      console.error(error);
    });
  } else {
    // 修改数据的逻辑
    if (tableform.value.coverFile) {
      // 如果有新的图片文件，则先上传图片
      uploadFile().then(() => {
        // 确保图片上传成功后再发送修改请求
        axios.put(`http://localhost:8080/reader/uid/${tableform.value.id}`, tableform.value)
            .then((response) => {
              ElMessage({ type: 'success', message: '修改成功!' });
              getData(); // 重新获取数据
            })
            .catch((error) => {
              ElMessage.error('修改失败');
              console.error("请求出错:", error);
            });
      }).catch((error) => {
        ElMessage.error('图片上传失败，无法修改数据');
        console.error(error);
      });
    } else {
      // 如果没有新的图片文件，直接发送修改请求
      axios.put(`http://localhost:8080/reader/uid/${tableform.value.id}`, tableform.value)
          .then((response) => {
            ElMessage({ type: 'success', message: '修改成功!' });
            getData(); // 重新获取数据
          })
          .catch((error) => {
            ElMessage.error('修改失败');
            console.error("请求出错:", error);
          });
    }
  }
};



//修改编辑按钮点击事件handleEdit，代码如下：
function leEdit(row) { // row 为当前行的数据
  dialogFormVisible.value = true // 设置对话框显示状态为 true
  tableform.value = {...row} //表单数据设置为传入行数据
  dialogType.value = 'edit' // 设置对话框类型为 "edit"，方便修改弹窗标题
}

//创建删除行数据的方法delrow，代码如下：
const delrow = (row) => { // 定义一个函数，用于删除某行数据
  axios.delete(`http://localhost:8080/reader/id/${row.id}`)
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
      '您确定要删除姓名为【' + row.username + '】的数据吗?', '危险操作',
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
/* src/styles/global.css */
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
.form {
  width: 400px;
  margin-bottom: 20px;
}

/*在ElementPlus中，可使用组件名称的类选择器选择对应组件，从而修改默认组件样式*/
.el-pagination { /*选择分页组件，默认采取Flex布局*/
  justify-content: center; /*水平方向居中对齐*/
  margin-top: 8px;
}

</style>
