<template>
  <div class="personal-center">
    <div class="sidebar">
      <ul>
        <li :class="{ active: currentTab === 'personal' }" @click="currentTab = 'personal'">个人中心</li>
        <li :class="{ active: currentTab === 'address' }" @click="currentTab = 'address'">收货地址</li>
        <li :class="{ active: currentTab === 'favorite' }" @click="currentTab = 'favorite'">商品收藏</li>
        <li :class="{ active: currentTab === 'order' }" @click="currentTab = 'order'">商品订单</li>
      </ul>
    </div>
    <div class="main-content" v-if="currentTab === 'address'">
      <div class="topTool">
        <button class="add-button" @click="handleAdd">+ 添加新地址</button>
      </div>
      <div>
        <table class="address-table">
          <thead>
          <tr>
            <th>主键</th>
            <th>收货人</th>
            <th>电话</th>
            <th>地址</th>
            <th>是否默认地址</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="member in membersData" :key="member.id">
            <td>{{ member.id }}</td>
            <td>{{ member.receiver }}</td>
            <td>{{ member.phone }}</td>
            <td>{{ member.address }}</td>
            <td>{{ member.x === true ? '是' : '否' }}</td>
            <td>
              <button class="edit-button" @click="leEdit(member)">修改</button>
              <button class="delete-button" @click="onDelete(member)">删除</button>
            </td>
          </tr>
          </tbody>
        </table>
        <el-pagination class="pagination" layout="prev, pager, next, sizes" :total="totalMembers" :page-size="pageSize" :page-sizes="[5, 10, 20]" @current-change="handleCurrentChange" @size-change="handleSizeChange"></el-pagination>
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

    </div>
  </div>
</template>


<script setup>
import {ref, onMounted, reactive, computed} from 'vue';
import axios from 'axios';
import {ElMessage, ElMessageBox} from "element-plus";

// 当前选中的侧边栏标签
const currentTab = ref('address');
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


//创建添加数据按钮事件handleAdd
const handleAdd = () => {
  dialogFormVisible.value = true;
  dialogType.value = 'add';
  tableform.value = {};
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
.personal-center {
  display: flex;
}

.sidebar {
  width: 200px;
  background-color: #f4f4f4;
  padding: 20px;
}

.sidebar ul {
  list-style: none;
  padding: 0;
}

.sidebar li {
  padding: 10px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.sidebar li.active {
  background-color: #ff6600;
  color: white;
}

.main-content {
  flex: 1;
  padding: 20px;
}

.add-button {
  background-color: #ff6600;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  float: right;
  cursor: pointer;
}

.address-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.address-table th,
.address-table td {
  border: 1px solid #ccc;
  padding: 10px;
  text-align: left;
}

.edit-button {
  background-color: #ff9800;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 3px;
  cursor: pointer;
}

.delete-button {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 3px;
  cursor: pointer;
  margin-left: 5px;
}

.pagination {
  margin-top: 20px;
}

.pagination button {
  padding: 5px 10px;
  border: 1px solid #ccc;
  background-color: white;
  cursor: pointer;
}

.pagination span {
  padding: 5px 10px;
  margin: 0 5px;
  cursor: pointer;
  border: 1px solid #ccc;
}

.pagination span.active {
  background-color: #ff6600;
  color: white;
}



</style>