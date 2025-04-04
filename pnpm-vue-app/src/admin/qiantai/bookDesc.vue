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
          <div class="book-header" :key="route.params.id">
            <h2 class="book-title">
              {{ currentBook.title }}
              <span class="book-meta">
                作者：<span class="author-name">{{ currentBook.author }}</span>
                | 出版日期：<span class="update-time">{{ currentBook.publishDate }}</span>
                <span v-if="!currentBook.isEbook"> | 可借：<span class="update-time">{{ currentBook.bookInventory?.availableCopies }}</span></span>
              </span>
            </h2>
            <el-tag v-for="tag in currentBook.labels" :key="tag.label">{{tag.label}}</el-tag>
            <el-tag v-if="currentBook.isPayable">付费:{{currentBook.borrowingFee}}元</el-tag>
            <el-tag v-else>免费</el-tag>
            <el-tag>评分：{{totalRanking}}分</el-tag>
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
      <!-- 猜你喜欢区域 -->
      <div class="recommended-books-section">
        <h2 class="section-title">猜你喜欢</h2>
        <div class="book-list1">
          <div class="book-item1" v-for="(book, index) in recommendedBooks" :key="index" @click="goToBookDetail(book.id)">
            <img :src="book.coverImage" alt="Recommended Book Cover" class="book-cover1" />
            <p class="book-title1">{{ book.title }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import {ElBreadcrumb, ElBreadcrumbItem, ElMessage, ElMessageBox} from 'element-plus';
import { ArrowRight } from '@element-plus/icons-vue';
import { useRoute } from 'vue-router';
import axios from "axios";
import router from "../../router.js";

function getUserIdFromSessionStorage() {
  const role ='reader';
  const storageKey = `sessionUserId_${role}`;
  return sessionStorage.getItem(storageKey);
}

const id = getUserIdFromSessionStorage();
if (id) {
  console.log('当前登录读者的用户 ID:', id);
} else {
  console.log('未获取到用户 ID，可能用户未登录或会话已过期');
}

const route = useRoute();
const status = ref('待处理');
const currentBook = ref();
const bookId = parseInt(route.params.id);
const totalRanking = ref();
const goToLabel = () => {
  if (currentBook.value.labels[0].label === '古言') {
    router.push('/novel/ancient');
  } else {
    router.push('/novel/modern');
  }
};

const now = new Date();
const year = now.getFullYear();
const month = String(now.getMonth() + 1).padStart(2, '0');
const day = String(now.getDate()).padStart(2, '0');
const currentDateString = `${year}-${month}-${day}`;
const currentDate = new Date(year, month - 1, day);
const dueDate = new Date(currentDate.getTime());
dueDate.setDate(dueDate.getDate() + 60);

const dueYear = dueDate.getFullYear();
const dueMonth = String(dueDate.getMonth() + 1).padStart(2, '0');
const dueDay = String(dueDate.getDate()).padStart(2, '0');

const dueDateString = `${dueYear}-${dueMonth}-${dueDay}`;

console.log(`借阅日期: ${currentDateString}`);
console.log(`归还日期: ${dueDateString}`);

const getData = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/book/findById/${bookId}`);
    currentBook.value = response.data;
    console.log("当前书籍",currentBook.value)
    updateStatus();
    return response.data;
  } catch (error) {
    console.error("请求出错:", error);
    return null;
  }
};

const updateStatus = () => {
  if (currentBook.value && currentBook.value.isEbook === true) {
    status.value = '借阅中';
  } else {
    status.value = '待处理';
  }
};

onMounted(async () => {
  await getData();
  await getBookList();
  getCount();
  getRanking()
});

const addToBorrowList = async () => {
  try {
    const borrowRecord = {
      readerId: id,
      bookIsbn: currentBook.value.isbn,
      dueDate: dueDateString,
      borrowDate: currentDateString,
      status: status.value,
      borrowingFee:currentBook.value.borrowingFee
    };
    const response = await axios.post("http://localhost:8080/borrowRecord/create", borrowRecord);
    if(response.data === '插入成功') {
      ElMessage({type: 'success', message: '添加成功!'});
    }else if(response.data === '该读者与书籍的组合已存在，无需重复插入'){
      ElMessage.error('不可重复加入借书架');
    }
    updateStatus();
  } catch (error) {
    ElMessage.error('添加失败');
    console.error("请求出错:", error);
  }
  console.log('加入借书架:', currentBook.value);
};

const goToCommentSection = () => {
  router.push({ name: 'BookReviews', params: { id: bookId } });
};

const addToReservationList = async () => {
  try {
    const reservation = {
      readerId: id,
      bookIsbn: currentBook.value.isbn,
    };
    const response = await axios.post("http://localhost:8080/reservation/create", reservation);
    ElMessage({ type: 'success', message: '添加成功!' });
    updateStatus();
  } catch (error) {
    ElMessage.error('添加失败');
    console.error("请求出错:", error);
  }
  console.log('加入预约书架:', currentBook.value);
};

const handleButtonClick = async () => {
  const readerRequest = await axios.get(`http://localhost:8080/reader/findById/${id}`);
  // 获取响应数据
  const reader = readerRequest.data;
  console.log(readerRequest.data);
  // 检查余额是否足够
  if (reader.accountBalance < currentBook.value.borrowingFee) {
    ElMessage.error('账户余额不足，请先充值。');
    return;
  }
  if (currentBook.value.bookInventory?.availableCopies === 0) {
    await addToReservationList();
  } else {
    if (currentBook.value.isPayable) {
      if (currentBook.value.isEbook) {
        try {
          // 弹出确认对话框
          await ElMessageBox.confirm(
              '该书籍为付费借阅，确认是否借阅？',
              '确认借阅',
              {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning'
              }
          );

          // 扣除余额
          reader.accountBalance -= currentBook.value.borrowingFee;
          // 模拟更新读者账户余额到后端
          await axios.put(`http://localhost:8080/reader/uid/${id}`, reader);
          // 加入借书架
          await addToBorrowList();
          ElMessage.success('借阅成功，已加入借书架。');
        } catch (error) {
          // 用户取消借阅
          if (error === 'cancel') {
            ElMessage.info('已取消借阅。');
          }
        }
      } else {
        // 实体书待处理
        await ElMessageBox.confirm(
            '该书籍为付费借阅，管理员处理完自动扣费',
            '确认借阅',
            {
              confirmButtonText: '确认',
              cancelButtonText: '取消',
              type: 'warning'
            }
        );
        // 检查余额是否足够
        if (reader.accountBalance < currentBook.value.borrowingFee) {
          ElMessage.error('账户余额不足，请先充值。');
          return;
        }
        await addToBorrowList();
      }
    } else {
      // 免费书籍直接加入借书架
      await addToBorrowList();
      ElMessage.success('借阅成功，已加入借书架。');
    }

  }
};
const count = ref(0);
const getCount = () => {
  axios.get(`http://localhost:8080/bookReviews/countByBookId/${bookId}`)
      .then((response) => {
        count.value = response.data;
        console.log(response.data);
      })
      .catch((error) => {
        console.error("请求出错:", error);
      });
};
const bookReviews = ref([]); // 初始化为空数组

const getRanking = () => {
  axios.get(`http://localhost:8080/bookReviews/findById/${bookId}`)
      .then((response) => {
        bookReviews.value = response.data;
        console.log(bookReviews.value);

        // 在数据获取成功后进行计算
        const starCounts = countStarRatings(bookReviews.value);
        console.log("当前", starCounts);
        const finalRating = calculateDoubanRating(starCounts);
        totalRanking.value = finalRating
        console.log("最终评分", finalRating);
      })
      .catch((error) => {
        console.error("请求出错:", error);
      });
};

const countStarRatings = (reviews) => {
  const starCounts = {
    a: 0,  // 5星数量
    b: 0,  // 4星数量
    c: 0,  // 3星数量
    d: 0,  // 2星数量
    e: 0   // 1星数量
  };
  console.log(reviews.length);
  for (let i = 0; i < reviews.length; i++) {
    const rating = reviews[i].rating;
    switch (rating) {
      case 5:
        starCounts.a++;
        break;
      case 4:
        starCounts.b++;
        break;
      case 3:
        starCounts.c++;
        break;
      case 2:
        starCounts.d++;
        break;
      case 1:
        starCounts.e++;
        break;
      default:
        break;
    }
  }
  return starCounts;
};

const calculateDoubanRating = (starCounts) => {
  const { a, b, c, d, e } = starCounts;
  const numerator = 10 * a + 8 * b + 6 * c + 4 * d + 2 * e;
  const denominator = a + b + c + d + e;
  const rating = denominator === 0 ? 0 : numerator / denominator;
  return parseFloat(rating.toFixed(1));
};
const bookList = ref([]);
const getBookList = async () => {
  try {
    const response = await axios.get("http://localhost:8080/book/findAll");
    bookList.value = response.data;
    console.log("书籍列表数据：", bookList.value);
    return response.data;
  } catch (error) {
    console.error("请求出错:", error);
    return null;
  }
};

const recommendedBooks = computed(() => {
  if (!currentBook.value ||!currentBook.value.labels || currentBook.value.labels.length === 0) {
    return [];
  }
  const currentBookLabels = currentBook.value.labels.map(label => label.label);
  return bookList.value.filter(book => {
    // 使用 id 进行比较
    return book.id!== currentBook.value.id && book.labels.some(label => currentBookLabels.includes(label.label));
  }).slice(0, 6);
});

const goToBookDetail = async (bookId) => {
  const currentRoute = router.currentRoute.value;
  try {
    const response = await axios.get(`http://localhost:8080/book/findById/${bookId}`);
    currentBook.value = response.data;
    updateStatus();
    if (currentRoute.name === 'BookDesc') {
      router.replace({ name: 'BookDesc', params: { id: bookId } });
    } else {
      router.push({ name: 'BookDesc', params: { id: bookId } });
    }
  } catch (error) {
    console.error('获取书籍详情出错:', error);
  }
};

watch(currentBook, () => {
  updateStatus();
}, { deep: true });
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
  transition: transform 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94); /* 缓慢放大 */

}

.book-cover:hover {
  transform: scale(1.1); /* 鼠标悬停时放大1.1倍，可根据需要调整比例 */
  opacity: 0.9; /* 轻微透明（可选） */
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
.recommended-books-section {
  margin-top: 20px;
  align-items: center;
  background-color: #fff;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  width: 1000px;
}
.section-title {
  font-size: 24px;
  margin-bottom: 20px;
}

.book-list1 {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.book-item1 {
  width: calc(33.333% - 20px);
  box-sizing: border-box;
  padding: 10px;
  text-align: center;
}

.book-cover1 {
  width: 44%;
  height: 173px;
  object-fit: cover;
  transition: transform 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94); /* 缓慢放大 */
}

.book-cover1:hover {
  transform: scale(1.1); /* 鼠标悬停时放大1.1倍，可根据需要调整比例 */
}


.book-title1 {
  font-size: 16px;
  color: #333;
}

</style>