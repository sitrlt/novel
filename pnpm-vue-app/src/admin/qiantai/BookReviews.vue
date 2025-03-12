<template>
  <div class="main-container">
    <!-- 面包屑导航 -->
    <el-breadcrumb :separator-icon="ArrowRight" class="breadcrumb">
      <el-breadcrumb-item :to="{ path: '/novel/shouye' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item v-if="currentBook">{{ currentBook.title }}吧</el-breadcrumb-item>
      <el-breadcrumb-item v-else>未找到书籍</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 书籍卡片 -->
    <div class="book-card" v-if="currentBook">
      <img :src="currentBook.coverImage" alt="书籍封面" class="book-cover">
      <div class="book-info">
        <h2 class="book-title">{{ currentBook.title }}</h2>
        <el-button type="primary" class="publish-comment-btn" @click="showCommentDialog = true">发布评论</el-button>
      </div>
    </div>
    <!-- 评论区 -->
    <div class="comment-section">
      <div v-for="(comment, index) in comments" :key="index" class="comment-item">
        <div class="comment-header">
          <img :src="comment.reader.coverImage" alt="头像" class="avatar">
          <span class="username">{{ comment.reader.username }}</span>
          <span class="time">{{ comment.reviewDate }}</span>
          <div class="rating-container">
            <span v-for="i in 5" :key="i" :class="{ 'active-star': i <= comment.rating }">★</span>
          </div>
        </div>
        <p class="comment-content">{{ comment.reviewText }}</p>
      </div>
    </div>
    <!-- 评论弹窗 -->
    <el-dialog v-model="showCommentDialog" title="发布评论">
      <el-form :model="newComment" ref="commentFormRef">
        <el-form-item label="评分">
          <el-rate v-model="newComment.rating"></el-rate>
        </el-form-item>
        <el-form-item label="评论内容">
          <el-input v-model="newComment.content" type="textarea" rows="4">
          </el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCommentDialog = false">取消</el-button>
        <el-button type="primary" @click="submitComment">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import {useRoute} from 'vue-router';
import {ArrowRight} from '@element-plus/icons-vue';
import {ElMessage} from "element-plus";

const route = useRoute();
const currentBook = ref(null);
const bookId = parseInt(route.params.id);
const id = sessionStorage.getItem('sessionUserId_reader');

if (!id) {
  console.log('未获取到用户 ID，可能用户未登录或会话已过期');
}

const comments = ref([]);
const showCommentDialog = ref(false);
const showEmojiPicker = ref(false);
const newComment = ref({
  rating: 0,
  content: ''
});
const commentFormRef = ref(null);

const getDate = () => {
  axios.get(`http://localhost:8080/bookReviews/findById/${bookId}`)
      .then((response) => {
        comments.value = response.data;
      })
      .catch((error) => {
        console.error("请求出错:", error);
      });
};

onMounted(() => {
  getDate();
  axios.get(`http://localhost:8080/book/findById/${bookId}`)
      .then((response) => {
        currentBook.value = response.data;
      })
      .catch((error) => {
        console.error("请求出错:", error);
      });
});

const submitComment = () => {
  if (newComment.value.content.trim() === '') {
    console.log('评论内容不能为空');
    return;
  }
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  const seconds = String(now.getSeconds()).padStart(2, '0');
  const currentDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

  const newCommentData = {
    readerId: id,
    bookId: bookId,
    reviewDate: currentDate,
    reviewText: newComment.value.content,
    rating: newComment.value.rating
  };
  console.log(newCommentData);
  axios.post("http://localhost:8080/bookReviews/create", newCommentData)
      .then((response) => {
        ElMessage({type: 'success', message: '添加成功!'});
        getDate();
      })
      .catch((error) => {
        ElMessage.error('添加失败');
        console.error("请求出错:", error);
      });
  showCommentDialog.value = false;
};

</script>

<style scoped>
.main-container {
  padding: 20px;
}

.breadcrumb {
  margin-bottom: 20px;
}

.book-card {
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 15px;
  display: flex;
  align-items: start;
  margin-bottom: 20px;
}

.book-cover {
  width: 80px;
  height: 120px;
  object-fit: cover;
  margin-right: 15px;
}

.book-title {
  font-size: 18px;
  margin-bottom: 10px;
}

.publish-comment-btn {
  margin-top: 5px;
}

.comment-section {
  margin-top: 20px;
}

.comment-item {
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 15px;
  margin-bottom: 15px;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.username {
  font-weight: 500;
}

.time {
  color: #666;
  font-size: 12px;
  margin-left: 10px;
}

.rating-container {
  display: inline-block;
  margin-left: 10px;
}

.rating-container span {
  font-size: 14px;
  color: #ccc;
}

.active-star {
  color: gold !important;
}

.comment-content {
  line-height: 1.5;
}

.picker {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
}
</style>