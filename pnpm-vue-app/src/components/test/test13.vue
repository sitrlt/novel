<template>
  <div class="container">
    <div class="breadcrumb">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item>修改密码</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
      <el-form-item label="原密码" prop="oldPassword" :rules="[{ required: true, message: '请输入原密码', trigger: 'blur' }]">
        <el-input type="password" v-model="form.oldPassword" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword" :rules="[{ required: true, message: '请输入新密码', trigger: 'blur' }]">
        <el-input type="password" v-model="form.newPassword" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword" :rules="[{ required: true, message: '请输入确认密码', trigger: 'blur' }, { validator: confirmPasswordValidator, trigger: 'blur' }]">
        <el-input type="password" v-model="form.confirmPassword" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="submitForm">确定</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import axios from 'axios';

// 表单数据
const form = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
});

// 表单验证规则
const rules = reactive({
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请输入确认密码', trigger: 'blur' },
  ]
});

// 确认密码验证方法
const confirmPasswordValidator = (rule, value, callback) => {
  if (value!== form.newPassword) {
    callback(new Error('两次输入的密码不一致'));
  } else {
    callback();
  }
};

// 提交表单方法
const formRef = ref(null);
const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      axios.post('http://localhost:8080/user/changePassword', {
        oldPassword: form.oldPassword,
        newPassword: form.newPassword
      })
          .then((response) => {
            if (response.data.code === 200) {
              ElMessage.success('密码修改成功');
              // 清空表单数据
              form.oldPassword = '';
              form.newPassword = '';
              form.confirmPassword = '';
            } else {
              ElMessage.error(response.data.message);
            }
          })
          .catch((error) => {
            ElMessage.error('密码修改失败，请稍后重试');
            console.error(error);
          });
    }
  });
};
</script>

<style scoped>
.container {
  padding: 20px;
}

.breadcrumb {
  margin-bottom: 20px;
}
</style>