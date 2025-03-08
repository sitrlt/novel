<template>
  <div class="bookshelf-container">
    <div class="user-info">
      <img :src="userAvatar" alt="Avatar" class="avatar">
      <div class="user-details">
        <span class="username">{{ username }}</span>
        <span class="user-id">ID:{{ userId }}</span>
      </div>
    </div>
    <div class="book-list">
      <div class="book-item" v-for="book in books" :key="book.id">
        <input type="checkbox" v-model="book.read">
        <img :src="book.cover" alt="Book Cover" class="book-cover">
        <div class="book-info">
          <p class="book-title">{{ book.title }}</p>
          <p class="book-author">{{ book.author }}</p>
          <p class="book-status">{{ book.status }}</p>
          <p class="book-update">{{ book.update }}</p>
          <button class="read-button">立即阅读</button>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.bookshelf-container {
  display: flex;
}

.user-info {
  background-color: #ff2c55;
  color: white;
  padding: 20px;
  display: flex;
  align-items: center;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
}

.user-details {
  display: flex;
  flex-direction: column;
}

.username {
  font-size: 18px;
  font-weight: bold;
}

.user-id {
  font-size: 14px;
}

.book-list {
  flex-grow: 1;
  padding: 20px;
}

.book-item {
  display: flex;
  align-items: center;
  background-color: white;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 5px;
}

.book-cover {
  width: 60px;
  height: 80px;
  margin-right: 10px;
}

.book-info {
  flex-grow: 1;
}

.book-title {
  font-size: 16px;
  font-weight: bold;
}

.book-author {
  color: #666;
}

.book-status {
  color: #999;
}

.book-update {
  color: #ff2c55;
}

.read-button {
  background-color: #ff2c55;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}
</style>
<script>
export default {
  data() {
    return {
      userAvatar: './path/to/avatar.jpg', // 用户头像路径
      username: '夙夜大宝贝晚安',
      userId: '1722184809',
      books: [
        {
          id: 1,
          cover: './path/to/book1.jpg',
          title: '神医废材妃',
          author: '连块',
          status: '已完结',
          update: '1年前更新至 番外终 此伏彼起 VIP',
          read: false
        },
        {
          id: 2,
          cover: './path/to/book2.jpg',
          title: '狂医废材妃',
          author: '连块',
          status: '已完结',
          update: '1年前更新至 新书《天才神医妃》上架求订求月票啦～ VIP',
          read: false
        },
        // 更多书籍数据...
      ]
    };
  }
};
</script>