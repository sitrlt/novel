<template>
  <div class="auth-container">
    <div class="auth-card">
      <div class="auth-card-left">
        <h3>欢迎注册网络书籍借阅系统</h3>
      </div>
      <div class="auth-card-right">
        <h3 style="text-align: center;">Register</h3>
        <form @submit.prevent="handleSignUp">
          <div class="input-group">
            <label for="employeeId">账号 *</label>
            <input
                type="text"
                id="employeeId"
                placeholder="请输入账号"
                v-model="phone"
                required
            />
          </div>
          <div class="input-group">
            <label for="password">密码 *</label>
            <input
                type="password"
                id="password"
                placeholder="请输入密码"
                v-model="password"
                required
            />
          </div>
          <div class="input-group">
            <label for="confirmPassword">确认密码 *</label>
            <input
                type="password"
                id="confirmPassword"
                placeholder="请再次输入密码"
                v-model="confirmPassword"
                required
            />
          </div>

          <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
          <button type="submit" class="auth-btn">确定</button>
        </form>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue';
import axios from 'axios';

const username = ref(generateRandomChineseUsername());
console.log(username.value)
// 从项目中的图片数组随机选择一张作为头像
function getRandomAvatar(avatars) {
  const index = Math.floor(Math.random() * avatars.length);
  return 'http://localhost:8080/uploads/avatars/' + avatars[index]; // 假设图片放在public/assets/avatars目录下
}

const avatarFiles = ['img.png', 'img1.png', 'img2.png','img3.jpg'];
const coverImage = ref(getRandomAvatar(avatarFiles));
const password = ref('');
const phone = ref('');
const confirmPassword = ref('');

const errorMessage = ref('');

const handleSignUp = async () => {
  if (password.value !== confirmPassword.value) {
    errorMessage.value = '两次输入的密码不一致！';
    return;
  }

  try {
    const response = await axios.post('http://localhost:8080/reader/register', null, {
      params: {
        username: username.value,
        password: password.value,
        phone: phone.value,
        coverImage: coverImage.value
      }
    });

    if (response.status === 200) {
      alert('注册成功！请登录。');
      window.location.href = '/login';
    } else {
      errorMessage.value = '注册失败，请稍后再试。';
    }
  } catch (error) {
    errorMessage.value = '注册失败，请稍后再试。';
  }
};

// 生成随机中文用户名
function generateRandomChineseUsername(length = 2) {
  const commonChineseWords = "森月凌零淼芸椿繁伏夙苏沁瑰玫拾玖七褛封凤枫蔻幽柚子珑褚蕙碎遂绥缘洲蒲熠星周齐思钧林";
  let result = '';
  for (let i = 0; i < length; i++) {
    const randomIndex = Math.floor(Math.random() * commonChineseWords.length);
    result += commonChineseWords.charAt(randomIndex);
  }
  return result;
}


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

.auth-card-left h3 {
  font-family: 'Courier New', Courier, monospace;
  font-size: 35px;
  margin-top: 100px;
  animation: glow 1.5s ease-in-out infinite alternate;
}
.auth-card-right {
  background-color: white;
  margin-top: 15px;
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

