<template>
  <div class="login">
    <el-form :model="tableform" label-width="120px" class="demo-ruleForm aaa">
      <h2>Login</h2>
      <el-form-item label="用户名" >
        <el-input v-model="tableform.id" placeholder="请输入账号" @blur="checkID" required/>
        <span v-if="show">{{ message }}</span>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="tableform.password" placeholder="请输入密码" show-password :show-password="false" />
        <el-link class="forget-password" @click="forgetPassword">忘记密码？</el-link>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
let members = reactive([
])
const tableform = ref({
}) // 初始化弹窗表单数据
const show = ref(false)
const message = ref('')

const checkID = () => {
  // 使用正则表达式验证数字长度为10
  const numberRegex = /^\d{10}$/

  if (!numberRegex.test(tableform.value.id)) {
    show.value = true
    message.value = '用户名必须为10位数字'
  }else{
    message.value='';
  }
}


const onSubmit = () => {
  const loginData = {
    id: tableform.value.id,
    password: tableform.value.password
  };

  axios.post('http://localhost:8080/userStu/login',loginData).then(()=>{
    ElMessage({type: 'success',message: '登录成功!',})

  })
}
import { useRouter } from 'vue-router';
import axios from "axios";
import {ElMessage} from "element-plus";
const router = useRouter();

const forgetPassword = () => {
  router.push('/t1')
}
</script>

<style scoped>
span{
  padding: 0;
  margin: 0;
  color: red;
}
h2 {
  color: #fff;
  text-align: center;
  padding:20px ;
  margin-left: 100px;
}
.login{
  /*设置背景图片和背景图片的大小*/
  background: url('https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn.sinaimg.cn%2Fsinakd20118%2F213%2Fw2048h1365%2F20210814%2F7b9f-2a92e31017fa57ebe2ba91b6732cd677.jpg&refer=http%3A%2F%2Fn.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667994889&t=605b0d4d9da596720e43758351740bf1');
  width: 100%;
  height: 100%;
  position:fixed;
  background-size: 100%,130%;
  margin: -8px -8px auto;
}
.demo-ruleForm{
  border: 1px solid #DCDFE6;
  width: 300px;
  height: 370px;
  margin: 100px auto;
  padding: 35px 120px 15px 25px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
  background-color: #00000060;
}
.el-input /deep/ .el-input__wrapper {
  background-color: rgba(255, 255, 255, 0.247);
}
.el-input /deep/ .el-input__inner {
  color: black;
}
.el-input{
  text-color:black;
}
.el-form-item /deep/ .el-form-item__label{
  color:white;
}
.el-button {
  margin-top: 5px;
  width: 60%;
  height: 30px;
  border-radius: 10px;
  border: 0;
  color: #fff;
  text-align: center;
  line-height: 30px;
  font-size: 15px;
  background-image: linear-gradient(to right, #30cfd0, #330867);
}
.el-form-item{
  margin-bottom: 35px;
}
</style>
