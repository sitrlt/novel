<template>
  <div class="auth-container">
    <div class="auth-card">
      <div class="auth-card-left">
        <h3>欢迎登录网络书籍借阅系统</h3>
      </div>
      <div class="auth-card-right">
        <h3 style="text-align: center;">Login</h3>
        <form @submit.prevent="handleSignUp">
          <div class="input-group">
            <label for="employeeId">账号 *</label>
            <input type="text" id="employeeId" placeholder="请输入账号" v-model="form.employeeId" required />
          </div>
          <div class="input-group">
            <label for="password">密码 *</label>
            <input type="password" id="password" placeholder="请输入密码" v-model="form.password" required />
          </div>
          <div class="input-group">
            <label for="role">身份 *</label>
            <select id="role" v-model="form.role" required>
              <option disabled value="">请选择身份</option>
              <option value="admin">管理员</option>
              <option value="reader">读者</option>
              <option value="publisher">出版社</option>
            </select>
          </div>
          <!-- 忘记密码链接 -->
          <p class="auth-link" style="text-align: right; margin-top: -10px;">
            <router-link to="/forgetPassword">忘记密码？</router-link>
          </p>
          <button type="submit" class="auth-btn">登录</button>
        </form>
        <!-- 注册链接 -->
        <p class="auth-link" style="text-align: center; margin-top: 20px;">
          没有账号？<router-link to="/register">立即注册</router-link>
        </p>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import axios from "axios";
import {ElMessage} from "element-plus";
import { useRouter } from 'vue-router';

const router = useRouter();

function setUserIdInSessionStorage(role, userId) {
  const storageKey = `sessionUserId_${role}`;
  sessionStorage.setItem(storageKey, userId);
}

const form = ref({
  employeeId: '',
  password: '',
});
const handleSignUp = () => {
  console.log('Creating account with:', form.value);
  console.log(form.value.role)
  if(form.value.role === 'admin') {
    axios.post('http://localhost:8080/Librarian/login', form.value).then((response) => {
      ElMessage({type: 'success', message: '登录成功!',})
      console.log(response.data)
      localStorage.setItem('librarianId', response.data.id);
      router.push('/a1');
    })
  }else if(form.value.role==='reader'){
    const loginData = {
      phone: form.value.employeeId,
      password: form.value.password
    };
    console.log(loginData)
    axios.post('http://localhost:8080/reader/login', loginData).then((response) => {
      const role ='reader';
      setUserIdInSessionStorage(role, response.data.id);

      console.log(response.data.id);
      ElMessage({type: 'success', message: '登录成功!',})
      console.log(response.data)
      //router.push({ name: 'novel', params: { id: response.data.id } });
       router.push('/novel/shouye');
    })
  }else{
    const loginData = {
      phone: form.value.employeeId,
      password: form.value.password
    };
    console.log(loginData)
    axios.post('http://localhost:8080/publishers/login', loginData).then((response) => {
      console.log(response.data.id)
      ElMessage({type: 'success', message: '登录成功!',})
      console.log(response.data)
      localStorage.setItem('publisherId', response.data.id);
      router.push('/publisher');
    })
  }
};
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url('./src/assets/pinkBack.jpg');
  background-size: cover;
  height: 730px;
}

.auth-card {
  display: flex;
  background-color: white;
  border-radius: 6px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  width: 55%;
  height: 60%;
}

.auth-card-left {
  background-color: #fba9d8;
  color: white;
  padding: 15px;
  flex: 1;

}
.auth-link {
  font-size: 0.9rem;
  color: #fba9d8;
  text-decoration: none;
}

.auth-link a {
  color: #fba9d8;
  text-decoration: underline;
}

.auth-link a:hover {
  text-decoration: none;
}
.auth-card-left h3 {
  font-family: 'Courier New', Courier, monospace;
  font-size: 35px;
  margin-top: 100px;
  animation: glow 1.5s ease-in-out infinite alternate;
}
.auth-card-right {
  background-color: white;
  padding: 2rem;
  flex: 1;
  display: flex;
  flex-direction: column;
  text-align: center;
}

.input-group {
  display: flex;
  align-items: center;
  margin-bottom: 2rem;
}

.input-group label {
  margin-right: 1rem; /* 标签和输入框之间的间距 */
  white-space: nowrap; /* 防止标签换行 */
}

.input-group input,
.input-group select {
  flex: 1;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
}

.auth-btn {
  width: 100%;
  padding: 0.75rem;
  background-color: #fba9d8;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.auth-btn:hover {
  background-color: #f585c3;
}
</style>

<!--.auth-card-right {
  padding: 2rem;
  flex: 1;
  height: auto;
}

.input-group {
  margin-bottom: 1rem;
}

.input-group label {
  display: block;
  margin-bottom: 0.5rem;
}

.input-group input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.auth-btn {
  width: 100%;
  padding: 0.75rem;
  margin-top: 20px;
  margin-left: 10px;
  background-color: #fba9d8;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.auth-btn:hover {
  background-color: #f585c3;
}-->