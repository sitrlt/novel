<template>
  <div class="contianer">
    <div class="info1">
      <h1>网络书籍借阅平台管理系统</h1>
    </div>
    <div class="info2" v-if="currentLibrarian">
      <el-dropdown>
    <span style="color: white">
      {{ currentLibrarian.name }}
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
import router from "../router.js";
import {onMounted, ref} from "vue";
const currentLibrarian = ref(null);
const id = localStorage.getItem('librarianId');  // Retrieve the username from route parametersRetrieve the username from route parameters

onMounted(() => {
  axios.get(`http://localhost:8080/Librarian/findById/${id}`)
      .then((response) => {
        currentLibrarian.value = response.data;
        console.log(response)
      })
      .catch((error) => {
        console.error("请求出错:", error);
        // 处理错误，例如显示错误信息或采取其他措施
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

}

.info1 {
  flex: 1;
}

.info2{
  display: flex;
  align-items: center;
}
</style>
