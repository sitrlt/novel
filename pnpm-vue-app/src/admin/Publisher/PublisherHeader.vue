<template>
  <div class="contianer">
    <div class="info1">
      <h1>网络书籍借阅平台管理系统</h1>
    </div>
    <div class="info2" v-if="currentPublisher">
      <el-dropdown>
    <span style="color: white">
      {{ currentPublisher.name }}
      <el-icon class="el-icon--right">
        <arrow-down/>
      </el-icon>
    </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="gout">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import {ArrowDown} from '@element-plus/icons-vue'
import axios from "axios";
import {ElMessage} from "element-plus";
import {onMounted, ref} from "vue";
import router from "../../router.js";
const currentPublisher = ref(null);
function getUserIdFromSessionStorage() {
  const role ='publisher'; // 假设读者角色标识为'reader'
  const storageKey = `sessionUserId_${role}`;
  return sessionStorage.getItem(storageKey);
}

// 在需要获取用户 ID 的地方调用该函数
const id = getUserIdFromSessionStorage();

if (id) {
  console.log('当前登录出版社的用户 ID:', id);
  // 在这里可以进行后续操作，比如根据用户 ID 进行数据请求等
} else {
  console.log('未获取到用户 ID，可能用户未登录或会话已过期');
}
onMounted(() => {
  console.log(id)
  axios.get(`http://localhost:8080/publishers/findById/${id}`)
      .then((response) => {
        currentPublisher.value = response.data;
        console.log(response)
      })
      .catch((error) => {
        console.error("请求出错:", error);
      });
});
const gout = () => {
  router.push({name: 'login'});
}
</script>
<style scoped>
.contianer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #f8bbd0;
  color: white;
  border-radius: 5px;

}

.info1 {
  flex: 1;
}

.info2{
  display: flex;
  align-items: center;
}
</style>
