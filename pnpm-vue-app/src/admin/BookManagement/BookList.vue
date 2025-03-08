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
            style="width: 98%"
  >
    <el-table-column type="selection"/>
    <el-table-column prop="id" label="工号" width="100"></el-table-column>
    <el-table-column prop="isbn" label="ISBN" width="200"></el-table-column>
    <el-table-column prop="title" label="文章" width="230"></el-table-column>
    <el-table-column prop="author" label="作者" width="100"></el-table-column>
    <el-table-column fixed label="封面" width="100">
      <template #default="scope">
        <el-image :src="scope.row.coverImage" fit="contain">
          <template #error>
            <div class="image-slot">
              <el-icon>
                <Picture/>
              </el-icon>
            </div>
          </template>
        </el-image>
      </template>
    </el-table-column>
    <el-table-column prop="publishDate" label="出版日期" width="120"></el-table-column>
    <el-table-column prop="publisher.name" label="出版社名称" width="120"></el-table-column>
    <el-table-column label="操作" min-width="300">
      <template #default="{ row }">
        <el-button type="primary" size="small" icon="Edit" class="custom-pink-button"
                   @click="leEdit(row)">编辑
        </el-button>
        <el-button type="danger" size="small" icon="Delete"
                   @click="onDelete(row)">删除
        </el-button>
        <el-button type="primary" size="small" icon="StarFilled"
                   class="custom-pink-button"
                   @click="onLook(row)">详情
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
        <el-input v-model="tableform.id" autocomplete="off" @blur="inputId" disabled/>
      </el-form-item>
      <el-form-item label="ISBN" :label-width="100">
        <el-input v-model="tableform.isbn" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="书名" :label-width="100">
        <el-input v-model="tableform.title" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="作者" :label-width="100">
        <el-input v-model="tableform.author" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="封面" :label-width="100">
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
                <el-icon @click.stop="removeCover">
                  <Delete/>
                </el-icon>
              </div>
            </div>
          </template>
          <el-icon v-else class="avatar-uploader-icon">
            <Plus/>
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="简述" :label-width="100">
        <el-input v-model="tableform.description" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="出版时间" :label-width="100">
        <el-date-picker
            v-model="tableform.publishDate"
            type="date"
            placeholder="选择日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="出版社id" :label-width="100">
        <el-input v-model="tableform.publisherId" autocomplete="off"/>
      </el-form-item>
      <el-form-item label="类型id" :label-width="100">
        <el-input v-model="tableform.typeId" autocomplete="off"/>
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
  <el-dialog
      v-model="dialogVisible"
      title="书籍详情"
      width="50%"
  >
    <el-row :gutter="20">
      <el-col :span="12">
        <img :src="tableform.coverImage" alt="书籍封面" class="cover-image"/>
      </el-col>
      <el-col :span="12">
        <div class="book-info">
          <p class="book-title"><strong>书名：</strong>{{ tableform.title }}</p>
          <div class="book-details">
            <p><strong>作者：</strong>{{ tableform.author }}</p>
            <p><strong>出版社：</strong>{{ tableform.publisher.name }}</p>
            <p><strong>ISBN：</strong>{{ tableform.isbn }}</p>
            <p>
              <strong>标签：</strong>
              <span v-for="(labelItem, index) in tableform.labels" :key="index">
                 {{ labelItem.label }}
                <span v-if="index < tableform.labels.length - 1">,</span>
              </span>
            </p>
            <!--<p><strong>可借：</strong><span :style="{ color: tableform.stockQuantity === '0' ? 'red' : 'green' }">{{ tableform.stockQuantity }}</span></p>-->
          </div>
          <p class="book-description"><strong>简介：</strong>{{ tableform.description }}</p>
        </div>
      </el-col>
    </el-row>
    <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
  </el-dialog>
</template>

<script setup>
import axios from "axios";
import {computed, onMounted, reactive, watch} from 'vue'
import {ref} from 'vue'
import {Plus, Delete, Search} from '@element-plus/icons-vue'
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
let tableform = ref({})//初始化弹窗表单数据
let dialogType = ref('add')//初始化弹窗类型为增加弹窗
const dialogTitle = computed(() => {//设置弹窗的标题
  return dialogType.value === 'add' ? '新增数据' : '编辑数据'
})
const publisherNames = ref([]);


/*

// 发送请求获取数据
axios.get("http://localhost:8080/book/findAll").then((response) => {
  members = response.data;
  total.value =members.length;
  displayedItems.value = members; // 更新ref变量的值\
}).catch((error) => {
  console.error("请求出错:", error);
  // 处理错误，例如显示错误信息或采取其他措施
})
*/


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
    axios.post('http://localhost:8080/book/upload-cover', formData)
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
  axios.get(`http://localhost:8080/book/findByPage`, {
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
const handleSizeChange = (pagesize) => {
  pageSize.value = pagesize;
  getData();
}
//存储当前页数据副本，方便清空搜索框还原当前页数据
let sname = ref('')//初始化搜索框的值
const handleSearchName = (val) => {
  if (val.length > 0) {
    axios.get(`http://localhost:8080/book/search`, {params: {keyword: val}}).then(response => {
      displayedItems.value = response.data;
      console.log(displayedItems.value)
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
//判断ID是否存在
const inputId = () => {
  const isDuplicate = members.some(member => member.id === tableform.value.id)
  if (isDuplicate) {
    ElMessageBox({
      title: 'Duplicate ID',
      message: '你输入的ID已存在，请重新输入！！！',
      type: 'warning',
      showCancelButton: false,
      confirmButtonText: 'OK',
      customClass: 'my-message-box' // 自定义样式类名
    });
    return false
  }
  return true;
}
//创建弹窗确定按钮事件dialogOk
const dialogOk = () => {
  dialogFormVisible.value = false; // 关闭对话框

  if (dialogType.value === 'add') {
    // 添加数据的逻辑
    uploadFile().then(() => {
      // 确保图片上传成功后再发送添加请求
      axios.post("http://localhost:8080/book/create", tableform.value)
          .then(() => {
            ElMessage({type: 'success', message: '添加成功!'});
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
        axios.put(`http://localhost:8080/book/uid/${tableform.value.id}`, tableform.value)
            .then(() => {
              ElMessage({type: 'success', message: '修改成功!'});
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
      axios.put(`http://localhost:8080/book/uid/${tableform.value.id}`, tableform.value)
          .then(() => {
            ElMessage({type: 'success', message: '修改成功!'});
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

function onLook(row) { // row 为当前行的数据
  dialogVisible.value = true // 设置对话框显示状态为 true
  tableform.value = {...row} //表单数据设置为传入行数据

}

//创建删除行数据的方法delrow，代码如下：
const delrow = (row) => { // 定义一个函数，用于删除某行数据
  axios.delete(`http://localhost:8080/book/id/${row.id}`)
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
      '您确定要删除姓名为【' + row.title + '】的数据吗?', '危险操作',
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
