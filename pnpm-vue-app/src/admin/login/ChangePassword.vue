<template>
  <div>
    <el-container class="card">
      <el-main>
        <h2>修改密码</h2>
        <el-form-item label="邮箱号">
          <el-input v-model="tableform.email" placeholder="请输入邮箱号" @blur="checkEmail" required/>
          <span class="s" v-if="show1">{{ errormessage }}</span>
        </el-form-item>
        <el-form-item label="验证码">
          <el-row>
            <el-col :span="16">
              <el-input v-model="tableform.code" placeholder="验证码"/>
            </el-col>
            <el-col :span="8" class="code-col">
              <el-button class="code-button" @click="sendVerificationCode">
                {{ isWaiting ? `重新获取 ${remainingTime} 秒` : '获取验证码' }}
              </el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="tableform.password" placeholder="设置新密码" show-password :show-password="false "
                    @blur="checkPwd"/>
          <span class="s" v-if="show">{{ message }}</span>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :style="{ backgroundColor: '#f8bbd0', border: 'none' }" @click="onSubmit">确定
          </el-button>
        </el-form-item>
      </el-main>
    </el-container>

  </div>
</template>

<script setup>
import {House, Location} from "@element-plus/icons-vue";
import {ref} from 'vue'
import axios from "axios";
import {ElMessage} from "element-plus";
import  {useRouter} from "vue-router";
const tableform = ref({})
const show = ref(false)
const show1 = ref(false)
const message = ref(``)
const errormessage = ref('');
const isWaiting = ref(false);
const remainingTime = ref(0);
let timerId = null;
const router  = useRouter();
const checkEmail = () => {
  const emailRegex = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
  if (!emailRegex.test(tableform.value.email)) {
    show1.value = true
    errormessage.value = '邮箱号错误！！'
  }else{
    errormessage.value='';
  }
}


const checkPwd = () => {
  // 使用正则表达式验证数字长度为10
  const numberRegex = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[\W_]).{8,16}$/

  if (!numberRegex.test(tableform.value.password)) {
    show.value = true
    message.value = '要求8-16位，至少包含数字，字母，字符两种元素'
  }else{
    message.value='';
  }
}

const sendVerificationCode = async () => {
  const loginData = {
    email: tableform.value.email,
  };
  try {
    // 发送请求到后端，请求后端发送验证码到指定邮箱
    const response = await axios.post('http://localhost:8080/librarian/forget', loginData);
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

const onSubmit = () => {
  const loginData = {
    email: tableform.value.email,
    password:tableform.value.password,
    code:tableform.value.code
  };
  console.log(loginData);
  axios.post('http://localhost:8080/librarian/update',loginData).then((response)=>{
    if(response.data===true) {
      ElMessage({type: 'success', message: '修改成功!',})
      loginData.value = '';
    }else{
      ElMessage({type: 'error', message: '验证码错误，请重新输入!',})
    }
  })
}



</script>

<style scoped>
.card {
  border-radius: 10px;
  padding: 20px;
  width: 98%;
}

.code-button {
  margin-left: 10px;
  color: #ffffff;
  background-color: #f8bbd0;
}

.s{
  padding: 0;
  margin: 0;
  color: red;
}

h2 {
  color: #000000;
  text-align: center;
  padding:20px ;
  margin-left: 100px;
}
</style>