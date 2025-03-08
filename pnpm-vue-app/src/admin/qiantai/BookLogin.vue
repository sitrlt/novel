<template>
  <div class="app-container">
    <!-- 头部导航 -->
    <header class="header">
      <div class="logo-container">
        <img src="https://via.placeholder.com/100x50" alt="logo" class="logo">
        <span class="logo-text">悦读云借</span>
      </div>
      <div class="search-container">
        <!-- 使用 @keyup.enter 监听 Enter 键按下事件 -->
        <el-input
            placeholder="傲气如我"
            class="search-input"
            :prefix-icon="Search"
            v-model="searchQuery"
            @keyup.enter="handleSearch"
        ></el-input>
      </div>
      <div class="function-buttons" v-if="currentReader">
        <el-icon :size="20"><bookmark /></el-icon>
        <span class="function-text" @click="goToShelf">我的书架</span>
        <el-icon :size="20"><message-square /></el-icon>
        <el-icon :size="20"><view /></el-icon>
      </div>
    </header>
    <!-- 分类导航 -->
    <el-menu
        default-active="1"
        class="category-nav fixed-nav"
        mode="horizontal"
    >
      <template v-for="item in menuData">
        <el-sub-menu v-if="item.children" :index="item.text">
          <template #title>
            <router-link :to="item.path">{{ item.text }}</router-link>
          </template>
          <template v-for="child in item.children">
            <el-menu-item :index="child.path">
              <router-link :to="child.path">{{ child.text }}</router-link>
            </el-menu-item>
          </template>
        </el-sub-menu>
        <el-menu-item v-else :key="item.text" :index="item.path">
          <router-link :to="item.path">{{ item.text }}</router-link>
        </el-menu-item>
      </template>
    </el-menu>
    <el-main>
      <div>
        <router-view />
      </div>
    </el-main>
  </div>
  <el-footer  style="text-align: center;">
    <div class="divider"></div>
    Copyright © 1999-2025 www.hongxiu.com All Rights Reserved版权所有
  </el-footer>
</template>

<script setup>
// 用于标识是否是首页
import {onMounted, ref} from "vue";
import { Search, View } from '@element-plus/icons-vue';
import { ElMessage } from "element-plus";
import {useRoute,useRouter} from "vue-router";
import axios from "axios";



const menuData = ref([
  {
    text: '首页',
    path: '/novel/shouye',
  },
  {
    text: '排行榜',
    path: '/novel/desc',
  },
  {
    text: '古言',
    path: '/ancient',
  },
  {
    text: '现言',
    path: '/modern',
  },
  {
    text: '玄幻仙侠',
    path: '/fantasy',
  }
]);
// 定义搜索关键词响应式数据
const searchQuery = ref('');
const route = useRoute(); // 使用 vue-router 的 useRouter 钩子
const router = useRouter(); // 使用 vue-router 的 useRouter 钩子
const currentReader = ref();
onMounted(() => {
  // const id = localStorage.getItem('userId');  // Retrieve the username from route parametersRetrieve the username from route parameters
  const id = parseInt(route.params.id);

  axios.get(`http://localhost:8080/reader/findById/${id}`)
      .then((response) => {
        currentReader.value = response.data;
        console.log(currentReader.value)
      })
      .catch((error) => {
        console.error("请求出错:", error);
        // 处理错误，例如显示错误信息或采取其他措施
      });
});
const goToShelf = () => {
  router.push({ name: 'BookShelf', params: { id: currentReader.value.id } });
};
// 定义搜索处理函数
const handleSearch = () => {
  if (searchQuery.value) {
    router.push({ path: '/novel/search-results', query: { keyword: searchQuery.value } });
  }
};
</script>

<style scoped>
.app-container {
  font-family: Arial, sans-serif;
  background-color: #f9f4f8;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

a {
  text-decoration: none;
}
.logo-container {
  display: flex;
  align-items: center;
}
.book-cover {
  width: 40px; /* 根据需要调整图片宽度 */
  height: 40px; /* 根据需要调整图片高度，使其成为正方形 */
  object-fit: cover; /* 确保图片内容被正确覆盖 */
  border-radius: 50%; /* 将图片裁剪成圆形 */
  transition: transform 0.3s ease; /* 平滑动画效果 */
}

.logo {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}

.logo-text {
  font-size: 24px;
  color: #c81623;
  font-weight: bold;
}
.divider {
  border-top: 1px solid #000;
}

.logo-url {
  font-size: 12px;
  color: #666;
}

.search-container {
  flex: 1;
  margin: 0 20px;
}

.search-input {
  width: 100%;
}

.function-buttons {
  display: flex;
  align-items: center;
}

.function-text {
  margin: 0 10px;
}

.category-nav {
  display: flex;
  justify-content: space-around;
  align-items: center;
  background-color: #f8bbd0;
  padding: 10px 0;
}

.category-item {
  cursor: pointer;
  padding: 5px 15px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.category-item:hover {
  background-color: #f48fb1;
}

.category-item.active {
  color: white;
  border-bottom: 1px solid gray;
}
</style>