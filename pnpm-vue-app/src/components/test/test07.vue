<template>
  <div class="login">
    <el-form :model="tableform" label-width="120px" class="demo-ruleForm aaa">
      <div class="return-btn" @click="goBack">
        <el-icon><Back /></el-icon>
        <el-link @click="comeBack">返回</el-link>
      </div>
      <h2>忘记密码</h2>
      <el-form-item label="邮箱号">
        <el-input v-model="tableform.email" placeholder="请输入邮箱号" required/>
      </el-form-item>
      <el-form-item label="验证码">
        <el-row>
          <el-col :span="16">
            <el-input v-model="tableform.code" placeholder="验证码" @click="checkID"/>
          </el-col>
          <el-col :span="8" class="code-col">
            <button class="code-button" @click="sendVerificationCode">
              {{ isWaiting ? `请等待 ${remainingTime} 秒` : '获取验证码' }}
            </button>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="tableform.password" placeholder="设置新密码" show-password :show-password="false"/>
        <span class="s" v-if="show">{{ message }}</span>

      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">确定</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import axios from "axios";
import {ref, reactive, watch} from 'vue'
import {useRouter} from 'vue-router';
const tableform = reactive({
  email: "",
  code: "",
  password:""
}) // 初始化弹窗表单数据
const show = ref(false)
const message = ref('')

const isWaiting = ref(false);
const remainingTime = ref(0);
let timerId = null;
const checkID = () => {
  // 使用正则表达式验证数字长度为10
  const numberRegex = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[\W_]).{8,16}$/

  if (!numberRegex.test(tableform.password)) {
    show.value = true
    message.value = '要求8-16位，至少包含数字，字母，字符两种元素'
  }else{
    message.value='';
  }
}
const router = useRouter();
const comeBack = () => {
  router.push('/link9');
}
const sendVerificationCode = async () => {
  try {
    // 发送请求到后端，请求后端发送验证码到指定邮箱
    const response = await axios.post('/api/send-verification-code', {
      email: tableform.email
    });
    // 请求成功，收到验证码
    const verificationCode = response.data.verificationCode;
    tableform.verificationCode = verificationCode;
    console.log('验证码已发送至邮箱');
    isWaiting.value = true;
    remainingTime.value = 60;

    timerId = setInterval(() => {
      remainingTime.value--;
      if (remainingTime.value === 0) {
        clearInterval(timerId);
        isWaiting.value = false;
      }
    }, 1000);
  } catch (error) {
    console.error('发送验证码失败', error);
  }
}
</script>

<style scoped>
.s{
  font-size: 0.5px;
}
span {
  padding: 0;
  margin: 0;
  color: red;
}
.return-btn {
  top: 20px;
  left: 20px;
  cursor: pointer;
}
 .el-link{
  margin: 0;
  color: white;
  font-size: 18px;

}

h2 {
  color: #fff;
  text-align: center;
  padding: 20px;
  margin-left: 100px;
}
.el-form-item /deep/ .el-form-item__content{
  line-height: inherit;
}
.login {
  /*设置背景图片和背景图片的大小*/
  background: url('https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn.sinaimg.cn%2Fsinakd20118%2F213%2Fw2048h1365%2F20210814%2F7b9f-2a92e31017fa57ebe2ba91b6732cd677.jpg&refer=http%3A%2F%2Fn.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667994889&t=605b0d4d9da596720e43758351740bf1');
  width: 100%;
  height: 100%;
  position: fixed;
  background-size: 100%, 130%;
  margin: -8px -8px auto;
}

.demo-ruleForm {
  border: 1px solid #DCDFE6;
  width: 300px;
  height: 400px;
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

.el-input {
  text-color: black;
}

.el-form-item /deep/ .el-form-item__label {
  color: white;
}

.el-button {
  width: 60%;
  height: 30px;
  border-radius: 10px;
  border: 0;
  color: #fff;
  text-align: center;
  line-height: 30px;
  font-size: 15px;
  background-image: linear-gradient(to right, #30cfd0, #330867);
  position: absolute;
}

.el-form-item {
  margin-bottom: 35px;
}

.code-col {
  display: flex;
  align-items: center;
}

.el-row {
  position: fixed;
}

.code-button {
  margin-left: 10px;

  color: red;
  background-color: inherit;
}
</style>
