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
      <div class="function-buttons">
        <span class="function-text" @click="goToShelf">我的借书架</span>
        <img :src="currentReader ? currentReader.coverImage : defaultAvatar" alt="book cover" class="book-cover" @click="goToLogin">
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
    <el-main style="z-index: -1;">
      <div>
        <router-view />
      </div>
    </el-main>
  </div>

</template>

<script setup>
// 用于标识是否是首页
import {onMounted, ref} from "vue";
import { Search, View } from '@element-plus/icons-vue';
import { ElMessage } from "element-plus";
import {useRoute,useRouter} from "vue-router";
import axios from "axios";

const defaultAvatar = 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage109.360doc.com%2FDownloadImg%2F2024%2F10%2F0623%2F289300255_1_20241006115546856.png&refer=http%3A%2F%2Fimage109.360doc.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1743416499&t=2f61ed8dc9a46a7b4f6d3cc3a7e3448d'; // 初始头像路径

const menuData = ref([
  {
    text: '首页',
    path: '/novel/shouye',
  },
  {
    text: '排行榜',
    path: '/novel/ranking',
  },
  {
    text: '古言',
    path: '/novel/ancient',
  },
  {
    text: '现言',
    path: '/novel/modern',
  },
  {
    text: '历史文化',
    path: '/novel/history',
  }
]);
// 假设已经引入相关依赖
// 定义获取用户 ID 的函数
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

// 定义搜索关键词响应式数据
const searchQuery = ref('');
const route = useRoute(); // 使用 vue-router 的 useRouter 钩子
const router = useRouter(); // 使用 vue-router 的 useRouter 钩子
const currentReader = ref();
onMounted(() => {

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
    router.push({name: 'BookShelf', params: {id: currentReader.value.id}});
};
const goToLogin = () => {
  // 如果当前头像是初始头像，则跳转到登录界面
  if (!currentReader.value || currentReader.value.coverImage === defaultAvatar) {
    router.push('/link23'); // 假设登录路由路径是 '/login'
  }
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
  height: 1500px;
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