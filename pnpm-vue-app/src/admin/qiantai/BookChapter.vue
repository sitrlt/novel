<template>
  <div class="top">
    <div class="book-container">
      <el-breadcrumb :separator-icon="ArrowRight" class="breadcrumb">
        <el-breadcrumb-item :to="{ path: '/novel/shouye' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="currentBook" @click="goToLabel">{{ currentBook.labels[0].label }}</el-breadcrumb-item>
        <el-breadcrumb-item v-if="currentBook" >{{ currentBook.title }}</el-breadcrumb-item>
        <el-breadcrumb-item v-else>未找到书籍</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="book-reader">
        <div v-if="currentChapter" class="chapter">
          <div class="book-top">
            <h3>{{ currentChapter.title }}</h3>
            <div class="book-header">
              <span>书名：{{ currentBook?.title }}</span>
              <span style="margin-left: 10px">作者：{{ currentBook.author }}</span>
            </div>
          </div>
          <div v-for="(paragraph, index) in currentChapter.contents" :key="index" class="paragraph">
            <p v-html="paragraph"></p>
          </div>
        </div>
        <div v-else class="empty-message">
          <img src="https://cdn-icons-png.flaticon.com/512/261/261563.png" alt="empty-icon"
               style="width: 80px; height: 80px; margin-bottom: 10px;">
          <p>还没有任何消息哦</p>
        </div>
        <div class="book-button">
          <button v-if="currentIndex > 0" @click="prevChapter">上一章</button>
          <button v-if="currentIndex < chapters.length - 1" @click="nextChapter">下一章</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from 'vue';
import {useRoute} from 'vue-router';
import {ArrowRight} from '@element-plus/icons-vue';

import axios from "axios";
import router from "../../router.js";

const route = useRoute();

const bookId = parseInt(route.params.id);
const chapters = ref([]);
const currentIndex = ref(0);
const currentBook = ref();
const currentChapter = computed(() => {
  return chapters.value[currentIndex.value];
});

const getBook = async () => {
  const response = await axios.get(`http://localhost:8080/book/findById/${bookId}`);
  currentBook.value = response.data;
}

const fetchChapters = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/bookChapter/${bookId}`);
    chapters.value = response.data.map(chapter => ({
      title: chapter.title,
      contents: chapter.contents
          .replace(/<br\s*\/?>/gi, '\n') // 将<br>替换为换行符
          .split('\n') // 按行分割
    }));
  } catch (error) {
    console.error("请求出错:", error);
  }
};
const goToLabel = () => {
  if (currentBook.value.labels[0].label === '古言') {
    router.push('/novel/ancient');
  } else {
    router.push('/novel/modern');
  }
};
const nextChapter = () => {
  if (currentIndex.value < chapters.value.length - 1) {
    currentIndex.value++;
  }
};

const prevChapter = () => {
  if (currentIndex.value > 0) {
    currentIndex.value--;
  }
};
onMounted(() => {
  fetchChapters();
  getBook()
})
</script>

<style scoped>
.top{

}
.book-container {
  margin: auto;
  width: 70%;

}

.book-reader {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-family: 'simsun', sans-serif;
  padding: 20px;
  background-color: #f9f4f8;

}

.chapter {
  margin-bottom: 20px;
  text-align: left; /* 确保内容左对齐 */
}

.book-header {
  margin-bottom: 10px;
}

.breadcrumb {
  /* 确保面包屑有合适的层级，不会被其他元素覆盖 */
  z-index: 1;
  margin-bottom: 20px; /* 给面包屑和书籍容器之间添加一些间距 */
}


h2 {
  font-size: 20px;
  color: #000000;
  margin-bottom: 10px;
}

.paragraph {
  margin-bottom: 20px;
  white-space: pre-wrap; /* 保留空格和换行符 */
}

p {
  font-size: 16px;
  line-height: 1.6;
  margin: 0;
}

.chapter span {
  font-size: 13px;
  color: #575654;
}

.back-button {
  display: flex;
}

.book-top {
  margin-left: 60px;
}

button {
  margin: 10px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  background-color: #f9f4f8;

}

.empty-message {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  color: #999;
}
</style>