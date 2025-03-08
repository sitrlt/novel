<template>
  <div class="login">
    <el-form :model="tableform" label-width="120px" class="demo-ruleForm aaa">
      <div class="return-btn" @click="goBack">
        <el-link @click="comeBack"><el-icon><Back /></el-icon></el-link>
      </div>
      <h2>忘记密码</h2>
      <el-form-item label="邮箱号">
        <el-input v-model="tableform.email" placeholder="请输入邮箱号" @blur="checkEmail" required/>
        <span class="s" v-if="show1">{{ errormessage }}</span>
      </el-form-item>
      <el-form-item label="验证码">
        <el-row>
          <el-col :span="16">
            <el-input v-model="tableform.code" placeholder="验证码" />
          </el-col>
            <el-col :span="8" class="code-col">
                <button class="code-button" @click.prevent="sendVerificationCode">
                    {{ buttonText }}
                </button>

            </el-col>
        </el-row>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="tableform.password" placeholder="设置新密码" show-password :show-password="false " @blur="checkPwd"/>
        <span class="s" v-if="show">{{ message }}</span>

      </el-form-item>
      <el-form-item>
        <button type="submit" class="auth-btn" @click="onSubmit">确定</button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import axios from "axios";
import {ref, reactive, watch, computed} from 'vue'
import {useRouter} from 'vue-router';
import {ElMessage} from "element-plus";
import {Back} from '@element-plus/icons-vue';

const tableform = ref({})
const show = ref(false)
const show1 = ref(false)
const message = ref('')
const errormessage = ref('');
const isWaiting = ref(false);
const remainingTime = ref(0);
let timerId = null;
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
const router = useRouter();
const comeBack = () => {
  router.push('/');
}
function sendVerificationCode()
{
    const loginData = {
        email: tableform.value.email,
    };
    console.log(loginData);
    axios.post('http://localhost:8080/reader/forget', loginData).
    then(response => {
        console.log(response.data);
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
    }).catch(error => {
        console.log('错误');
        console.error(error);
    });
}

// 修改为计算属性
const buttonText = computed(() => {
    if (isWaiting.value) {
        return `重新获取 ${remainingTime.value} 秒`;
    } else {
        return '获取验证码';
    }
});

const onSubmit = () => {
  const loginData = {
    email: tableform.value.email,
    password:tableform.value.password,
    code:tableform.value.code
  };
  console.log(loginData);
  axios.post('http://localhost:8080/reader/update',loginData).then((response)=>{
    if(response.data===true) {
      ElMessage({type: 'success', message: '修改成功!',})
    }else{
      ElMessage({type: 'error', message: '验证码错误，请重新输入!',})
    }
  })
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
  color: black;
  font-size: 18px;

}
.auth-btn {
  width: 40%;
  padding: 10px;
  background-color: #fba9d8;
  color: white;
  border: none;
  border-radius: 2px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-left: 40px;
}

h2 {
  color: black;
  text-align: center;
  padding: 20px;
  margin-left: 100px;
}
.el-form-item /deep/ .el-form-item__content{
  line-height: inherit;
}
.login {
  /*设置背景图片和背景图片的大小*/
  width: 100%;
  position: fixed;
  margin: -8px -8px auto;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('./src/assets/pinkBack.jpg');
  background-size: cover;
  height: 730px;
}

.demo-ruleForm {
  border: 1px solid #DCDFE6;
  width: 350px;
  height: 400px;
  margin: 100px auto;
  padding: 35px 120px 15px 25px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  background-color: white;
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
  color: black;
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
  /*margin-right: 10px; !* 添加右侧间距 *!*/
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

<!--const chineseSurnames = "森月凌零淼芸椿繁伏夙沁瑰玫拾玖七褛封凤枫蔻幽柚珑褚蕙碎遂绥缘洲蒲熠星周齐思钧林苏林叶萧沈安乔楚伊阮凌慕简颜纪";
  const chineseNames = "森月凌零淼芸椿繁伏夙苏沁瑰玫拾玖七褛封凤枫蔻幽柚子珑褚蕙碎遂绥缘洲蒲熠星周齐思钧林知许清欢景行星野羽墨洛书婉兮逸尘锦程念安云舒书瑶子衿怀瑾梦璃沐晨若溪景澜泽卿静姝";
-->