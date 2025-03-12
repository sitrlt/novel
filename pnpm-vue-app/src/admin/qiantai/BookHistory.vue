<template>
  <div class="book-list-container">
    <!-- 书籍展示区域 -->
    <div class="book-items">
      <div v-for="(group, groupIndex) in bookGroups" :key="groupIndex" class="book-row">
        <div
            v-for="(book, index) in group"
            :key="index"
            class="book-item"
            @click="goToBookDetail(book.id)"
        >
          <div class="book-thumbnail">
            <img :src="book.coverImage" alt="book cover" class="book-cover">
          </div>
          <div class="book-details">
            <h3 class="book-title">{{ book.title }}</h3>
            <p class="book-desc">{{ book.description }}</p>
            <div class="book-meta">
              <span class="book-author">{{ book.author }}</span>
              <span class="book-genre">{{ book.genre }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
import {ref, onMounted, onUnmounted, reactive} from 'vue';
import axios from "axios";
import router from "../../router.js";


let members = ref([])
// 发送请求获取数据

// 将所有书籍数据分成三行，每行三列
const bookGroups = ref([]);
const booksPerRow = 4;
// 发送请求获取数据
const member = ref([])
axios.get("http://localhost:8080/book/findAll")
    .then((response) => {
      member.value = response.data;
      console.log(member.value);

      // 筛选出 publisherId 为 '古言' 的书籍
      members.value = member.value.filter(item => item.labels[0]?.label === '历史文化');

      // 按每行数量分组
      for (let i = 0; i < members.value.length; i += booksPerRow) {
        bookGroups.value.push(members.value.slice(i, i + booksPerRow));
      }
    })
    .catch((error) => {
      console.error("请求出错:", error);
      // 处理错误，例如显示错误信息或采取其他措施
    });
const goToBookDetail = (bookId) => {
  router.push({name: 'BookDesc', params: {id: bookId}});
};

</script>

<style scoped>


.book-list-container {
  display: flex;
  padding: 20px;
  background-color: #f9f4f8;
}

.book-items {
  z-index: 1;

  flex: 3;
}

.book-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  gap: 10px; /* 在同一行的书籍容器之间添加间距 */
}

.book-item {
  width: calc(33.33% - 10px);
  display: flex;
  background-color: white;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-size: 12px;
  margin-bottom: -14px; /* 在不同行的书籍容器之间添加垂直间距 */
}

.book-thumbnail {
  width: 80px;
  height: 120px;
  margin-right: 10px;
}

.book-cover {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.book-details {
  flex: 1;
}

.book-title {
  font-size: 14px;
  margin-bottom: 5px;
}

.book-desc {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2; /* 限制显示两行 */
  overflow: hidden;
  text-overflow: ellipsis;
}

.book-meta {
  font-size: 10px;
  color: #999;
}

.book-author {
  margin-right: 5px;
}

.recommend-list {
  flex: 1;
  margin-left: 20px;
}

.recommend-item {
  display: block;
  margin-bottom: 10px;
  font-size: 14px;
}

.recommend-genre {
  margin-right: 5px;
  color: #c81623;
}
</style>