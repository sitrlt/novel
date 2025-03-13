<template>
  <div class="form-container">
    <el-form :model="tableform"  label-width="60px">
      <el-form-item label="ID" v-if="show">
        <el-input v-model="tableform.id" autocomplete="off" @blur="inputId" disabled />
      </el-form-item>
      <el-form-item label="ISBN">
        <el-input v-model="tableform.isbn" autocomplete="off" />
      </el-form-item>
      <el-form-item label="书名">
        <el-input v-model="tableform.title" autocomplete="off" />
      </el-form-item>
      <el-form-item label="作者">
        <el-input v-model="tableform.author" autocomplete="off" />
      </el-form-item>
      <el-form-item label="封面">
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
                  <Delete />
                </el-icon>
              </div>
            </div>
          </template>
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="简述">
        <el-input v-model="tableform.description" autocomplete="off" />
      </el-form-item>
      <el-form-item label="出版时间">
        <el-date-picker
            v-model="tableform.publishDate"
            type="date"
            placeholder="选择日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="借阅费用" :label-width="100">
        <el-input v-model="tableform.borrowingFee" autocomplete="off"/>
      </el-form-item>

      <el-form-item class="form-button-item">
        <el-button type="primary" @click="dialogOk">
          确定
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { Delete, Plus } from '@element-plus/icons-vue';
import {ElMessage} from "element-plus";
import axios from "axios";
const id = localStorage.getItem('publisherId');  // Retrieve the username from route parametersRetrieve the username from route parameters
console.log(id);
const show = ref(false)
// 初始化表单数据
const tableform = ref({
  id: '',
  isbn: '',
  title: '',
  author: '',
  coverImage: '',
  description: '',
  publishDate: '',
});

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


// 处理确定按钮点击事件
const dialogOk = () => {
    // 添加数据的逻辑

    uploadFile().then(() => {
      // 确保图片上传成功后再发送添加请求
      const reformable = {...tableform.value,publisherId:id,status:'待审核'}
      console.log(reformable)
      axios.post("http://localhost:8080/book/create", reformable)
          .then((response) => {
            ElMessage({type: 'success', message: '添加成功!'});
          })
          .catch((error) => {
            ElMessage.error('添加失败');
            console.error("请求出错:", error);
          });
    }).catch((error) => {
      ElMessage.error('图片上传失败，无法添加数据');
      console.error(error);
    });
};
</script>

<style scoped>
.form-container {
  background-color: white;
  padding: 20px;
  border-radius: 2px;
  margin-right: 23px;
  margin-top: 20px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  height: 678px;
}

.el-form-item {
  margin-bottom: 40px;
}

.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 100px;
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}

.cover-preview {
  position: relative;
  width: 100px;
  height: 100px;
}

.hover-mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transition: opacity 0.3s;
}

.cover-preview:hover .hover-mask {
  opacity: 1;
}

.form-button-item {
  display: flex;
  justify-content: flex-start;
}
</style>