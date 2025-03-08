<template>
  <div class="main-content">
    <div class="content">
      <el-breadcrumb :separator-icon="ArrowRight" class="breadcrumb">
        <el-breadcrumb-item :to="{ path: '/novel/shouye' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="currentBook" @click="goToLabel">{{ currentBook.labels[0].label }}</el-breadcrumb-item>
        <el-breadcrumb-item v-if="currentBook">{{ currentBook.title }}</el-breadcrumb-item>
        <el-breadcrumb-item v-else>未找到书籍</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="book-container" v-if="currentBook">
        <div class="book-cover">
          <img :src="currentBook.coverImage" alt="Book Cover" />
        </div>
        <div class="book-info">
          <div class="book-header">
            <h2 class="book-title">
              {{ currentBook.title }}
              <span class="book-meta">
          作者：<span class="author-name">{{ currentBook.author }}</span>
          | 出版日期：<span class="update-time">{{ currentBook.publishDate }}</span>
          <span v-if="!currentBook.ebook"> | 可借：<span class="update-time">{{ currentBook.bookInventory?.availableCopies }}</span></span>
        </span>
            </h2>
            <el-tag v-for="tag in currentBook.labels" :key="tag.label">{{tag.label}}</el-tag>
          </div>
          <div class="book-synopsis">
            <p>{{ currentBook.description }}</p>
          </div>
          <el-button @click="handleButtonClick" class="add-to-borrow-btn">
            {{ currentBook.bookInventory?.availableCopies === 0? '加入预约书架' : '加入借书架' }}
          </el-button>
        </div>

      </div>
      <div v-else>
        <p>未找到该书籍信息</p>
      </div>
      <!-- 新增的书友吧区域 -->
      <div class="book-friends-section">
        <div class="book-friends-title">
          <span class="book-friends-text">书友吧</span>
          <span class="comment-count" @click="goToCommentSection">评论{{count}}></span>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import {ref, onMounted} from 'vue';
import {ElBreadcrumb, ElBreadcrumbItem, ElMessage} from 'element-plus';
import {ArrowRight} from '@element-plus/icons-vue';
import {useRoute} from 'vue-router';
import axios from "axios";
import router from "../../router.js";
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

const route = useRoute();
const status = ref('待处理');
const currentBook = ref();
const bookId = parseInt(route.params.id);
const goToLabel = () => {
  if (currentBook.value.labels[0].label === '古言') {
    router.push('/novel/ancient');
  } else {
    router.push('/novel/modern');
  }
};
// 获取当前时间
const now = new Date();
const year = now.getFullYear();
const month = String(now.getMonth() + 1).padStart(2, '0');
const day = String(now.getDate()).padStart(2, '0');
const currentDateString = `${year}-${month}-${day}`;
const currentDate = new Date(year, month - 1, day);
const dueDate = new Date(currentDate.getTime());
dueDate.setDate(dueDate.getDate() + 60);

// 获取计算后的日期的年份、月份和日期
const dueYear = dueDate.getFullYear();
const dueMonth = String(dueDate.getMonth() + 1).padStart(2, '0');
const dueDay = String(dueDate.getDate()).padStart(2, '0');

// 格式化归还日期字符串，只保留年月日
const dueDateString = `${dueYear}-${dueMonth}-${dueDay}`;

console.log(`借阅日期: ${currentDateString}`);
console.log(`归还日期: ${dueDateString}`);
const getData = () => {
  axios.get(`http://localhost:8080/book/findById/${bookId}`)
      .then((response) => {
        currentBook.value = response.data;
        console.log(currentBook.value.isbn)
        if(currentBook.value.ebook === true){
          status.value = '借阅中'
        }
      })
      .catch((error) => {
        console.error("请求出错:", error);
        // 处理错误，例如显示错误信息或采取其他措施
      });
}
const addToBorrowList = async () => {
  try {
    // 等待 getData 函数中的请求完成
    await getData();
    // 创建 borrowRecord 对象
    const borrowRecord = {
      readerId: id,
      bookIsbn: currentBook.value.isbn,
      dueDate: dueDateString,
      borrowDate: currentDateString,
      status: status.value
    };
    console.log(borrowRecord);
    // 发送添加借阅记录的请求
    const response = await axios.post("http://localhost:8080/borrowRecord/create", borrowRecord);
    ElMessage({ type: 'success', message: '添加成功!' });
    console.log(response.data);
  } catch (error) {
    ElMessage.error('添加失败');
    console.error("请求出错:", error);
  }
  console.log('加入借书架:', currentBook.value);
};

const goToCommentSection = () => {
  router.push({ name: 'BookReviews', params: { id: bookId } });
}

const addToReservationList = async () => {
  try {
    // 等待 getData 函数中的请求完成
    await getData();
    // 创建 borrowRecord 对象
    const reservation = {
      readerId: id,
      bookIsbn: currentBook.value.isbn,
    };
    console.log(reservation);
    // 发送添加借阅记录的请求
    const response = await axios.post("http://localhost:8080/reservation/create", reservation);
    ElMessage({type: 'success', message: '添加成功!'});
    console.log(response.data);
  } catch (error) {
    ElMessage.error('添加失败');
    console.error("请求出错:", error);
  }
  console.log('加入预约书架:', currentBook.value);
};

const handleButtonClick = () => {
  if (currentBook.value.bookInventory?.availableCopies === 0) {
    // 加入预约书架的逻辑
    addToReservationList();
  } else {
    // 加入借书架的逻辑
    addToBorrowList();
  }
};

const count = ref(0);
const getCount = () => {
  axios.get(`http://localhost:8080/bookReviews/countByBookId/${bookId}`)
      .then((response) => {
        count.value = response.data;
        console.log(response.data)
      })
      .catch((error) => {
        console.error("请求出错:", error);
        // 处理错误，例如显示错误信息或采取其他措施
      });
}

onMounted(() => {
 getData()
  getCount()
});
</script>
<style scoped>
.main-content {
  display: flex;
  padding: 20px;
  justify-content: center; /* 水平居中 */
}
.content {
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* 水平左对齐 */
  width: 100%; /* 使容器宽度为100% */
  max-width: 800px; /* 设置最大宽度 */
  margin-right: 160px;
}


.breadcrumb {
  /* 确保面包屑有合适的层级，不会被其他元素覆盖 */
  z-index: 1;
  margin-bottom: 20px; /* 给面包屑和书籍容器之间添加一些间距 */
}

.book-container {
  display: flex;
  align-items: center;
  background-color: #fff;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  width: 1000px;
  margin: auto;
}

.book-cover {
  width: 180px;
  height: 240px;
  margin: 20px 20px 0;
  border: none;
  box-shadow: 0 1px 6px rgba(0, 0, 0, .3), 0 0 5px #f9f2e9 inset;
}

.book-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 4px;
}

.book-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.book-header {
  margin-bottom: 10px;
}

.book-title {
  font-size: 25px;
}

.book-meta {
  font-size: 14px;
  color: #666;
  margin-left: 10px;
}

.author-name {
  font-weight: bold;
  color: #fba9d8;
}

.book-update {
  font-size: 14px;
  color: #999;
  margin-bottom: 0; /* 移除默认的底部边距 */
}

.book-synopsis {
  font-size: 16px;
  color: #333;
  line-height: 1.5;
  margin-top: 10px;
}
.add-to-borrow-btn {
  background-color: #f8bbd0;
  color: white;
  border: none;
  border-radius: 5px;
  transition: background-color 0.3s ease;
  z-index: 1;
  margin-top: 10px;
  padding: 10px 0;
}
.add-to-borrow-btn:hover {
  background-color: #f585c3;
}

.book-friends-section {
  z-index: 1;
  display: flex;
  align-items: center;
  background-color: #fff;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  margin-top: 10px;
  width: 1000px;
}

.book-friends-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.comment-count {
  margin-left: 880px;
  cursor: pointer;
  color: #666;
}

.el-tag {
  margin-left: 10px;
  color: #f8bbd0;
  background-color: white;
  border: 1px solid #f8bbd0; /* 修改边框颜色为橙色 */
}
</style>