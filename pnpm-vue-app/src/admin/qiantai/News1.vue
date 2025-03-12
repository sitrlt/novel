<template>
  <div class="main-container">
    <header class="header">
      <h1>{{ mockActivity.activityName }}</h1>
    </header>
    <div class="content-container">
      <div class="event-info">
        <p><strong>活动名称：</strong>{{ mockActivity.activityName }}</p>
        <p><strong>活动描述：</strong>{{ mockActivity.activityDescription }}</p>
        <div class="event-time">
          <p><strong>开始时间：</strong>{{ mockActivity.startTime }}</p>
          <p><strong>结束时间：</strong>{{ mockActivity.endTime }}</p>
        </div>
        <p><strong>活动状态：</strong><span :class="statusClass">{{ mockActivity.activityStatus }}</span></p>
      </div>
      <a :href="registrationLink" class="register-button" :disabled="mockActivity.participationStatus=== '已报名'" @click.prevent="handleRegistration">立即报名</a>
    </div>
    <div class="comment-section">
      <h2>参与者反馈</h2>
      <form @submit.prevent="addComment">
        <input v-model="newComment" type="text" placeholder="写下你的评论">
        <button type="submit">提交评论</button>
      </form>
      <div v-for="(comment, index) in comments" :key="index" class="comment">
        {{ comment }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from "axios";
import {ElMessage} from "element-plus";

const route = useRoute();
const registrationLink = ref('#');
const newComment = ref('');
const comments = ref([]);
const statusClass = ref('event-status');
const activityId = parseInt(route.params.id);
// 这里可以从 API 获取活动信息，示例中使用模拟数据
const mockActivity = ref({});
function getUserIdFromSessionStorage() {
  const role ='reader'; // 假设读者角色标识为'reader'
  const storageKey = `sessionUserId_${role}`;
  return sessionStorage.getItem(storageKey);
}
// 在需要获取用户 ID 的地方调用该函数
const id = getUserIdFromSessionStorage();
if (id) {
  console.log('当前登录读者的用户 ID:', id);
  // 在这里可以进行后续操作，比如根据用户 ID 进行数据请求等
} else {
  console.log('未获取到用户 ID，可能用户未登录或会话已过期');
}
onMounted(() => {
 getData()
});



const getData = () => {
  axios.get(`http://localhost:8080/activity/findById/${activityId}`)
      .then((response) => {
        mockActivity.value = response.data;
        console.log(mockActivity.value)
      })
      .catch((error) => {
        console.error("请求出错:", error);
        // 处理错误，例如显示错误信息或采取其他措施
      });
}
const participation ={
    readerId:id,
    activityId:activityId,
    participationStatus:'已报名'
}

const handleRegistration = () => {
  axios.post("http://localhost:8080/participation/create", participation)
      .then((response) => {
        if(response.data === '插入成功') {
          ElMessage({type: 'success', message: '报名成功!'});
        }else if(response.data === '该读者与活动的组合已存在，无需重复插入'){
          ElMessage.error('不可重复报名');

        }
      })
      .catch((error) => {
        console.error("请求出错:", error);
      });
};

const addComment = () => {
  if (newComment.value.trim()!== '') {
    comments.value.push(newComment.value);
    newComment.value = '';
  }
};
</script>

<style scoped>

body{
  height: 100%;
  background-color: #f9f4f8;
}

.main-container {
  font-family: Arial, sans-serif;
  line-height: 1.6;
  height: 100vh;
  margin: 0;
  padding: 0;
  background-color: #f9f4f8;
  display: flex;
  flex-direction: column;
}

.header {
  background-color: #f8bbd0;
  color: white;
  text-align: center;
  padding: 2em 0;
}

.content-container {
  max-width: 800px;
  margin: 5em auto;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 2em;
  border-radius: 5px;
  flex: 1;
}

.event-info {
  margin-bottom: 2em;
}

.event-info p {
  margin: 0.5em 0;
}

.event-time {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1em;
}

.event-status {
  color: red;
  font-weight: bold;
}

.register-button {
  display: inline-block;
  background-color: #f8bbd0;
  color: white;
  padding: 0.8em 1.5em;
  text-decoration: none;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.register-button:hover {
  background-color: #fca1c0;
}
.return-button:disabled {
  background-color: #cccccccc;
  cursor: not-allowed;
}


.comment-section {
  padding: 2em;
  text-align: center;
}

input {
  padding: 0.6em;
  margin-right: 0.5em;
  border: 1px solid #ccc;
  border-radius: 3px;
}

button {
  padding: 0.6em 1em;
  background-color: #f8bbd0;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #fca1c0;
}

.comment {
  margin-top: 1em;
  background-color: #e9e9e9;
  padding: 0.5em 1em;
  border-radius: 3px;
  display: inline-block;
}
</style>