<template>
  <div class="app-container">
    <!-- 主体内容 -->
    <main class="main-content">
      <!-- 轮播图 -->
      <div class="banner">
        <div class="carousel-track" >
          <div
              class="carousel-slide"
              v-for="(item, index) in carouselItems"
              :key="index"
              :style="{ opacity: currentIndex === index ? 1 : 0, transition: 'opacity 0.5s ease' }"
          >
            <img :src="item.image" alt="banner" class="banner-img" @click="selectSlide(index)"/>
          </div>
        </div>
        <div class="banner-text">
          <span
              v-for="(item, index) in carouselItems"
              :key="index"
              :class="{ active: currentIndex === index }"
              @click="selectSlide(index)"
          >
            {{ item.title }}
          </span>
        </div>
      </div>
      <!-- 公告栏 -->
      <div class="announcement">
        <div class="announcement-title">
          <el-icon :size="20">
            <View/>
          </el-icon>
          <span>公告</span>
        </div>
        <ul class="announcement-list">
          <li
              v-for="(activity, index) in activities"
              :key="activity.id"
              @click="getClickHandler(activity.id)"
          >
           [活动] {{ activity.activityName }}
          </li>
        </ul>
      </div>
    </main>
  </div>
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
              <span class="book-tag">{{ book.labels.label }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 右侧推荐列表区域 -->
    <div class="recommend-list">
      <div v-for="(recommend, index) in recommendBooks" :key="index" @click="goToBookDetail(recommend.id)" class="recommend-item">
        <span class="recommend-genre">[{{ recommend.labels[0].label }}]</span>
        <span class="recommend-title">{{ recommend.title }}</span>
      </div>
    </div>
  </div>
  <!-- 弹窗：编辑爱好 -->
  <el-dialog v-model="isEditDialogVisible" title="编辑爱好">
    <div>
      <h3>已有爱好</h3>
      <el-tag v-for="(interest, index) in currentReader.interests" :key="interest.id" closable @close="deleteInterest(interest.id)">
        {{ interest.label }}
      </el-tag>
    </div>
    <div>
      <h3>所有标签类型</h3>
      <el-tag v-for="(label, index) in allLabels" :key="index" @click="addInterest(label.id)">
        {{ label.label }}
      </el-tag>
    </div>
    <template #footer>
      <el-button @click="closeEditDialog">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref, onMounted, onUnmounted, reactive} from 'vue';
import {Search, View} from '@element-plus/icons-vue'
import {ElIcon, ElInput, ElMessage} from 'element-plus';
import axios from "axios";
const carouselItems = ref([
  {

    image: 'https://bossaudioandcomic-1252317822.image.myqcloud.com/activity/document/4ca01de4a024a119fe4c35e344cc128d.jpg',
    title: '师妹的修炼方法它'
  },
  {
    image: 'https://bossaudioandcomic-1252317822.image.myqcloud.com/activity/document/7c434406aeff36eb350e3b706237f160.jpg',
    title: '将军，夫人又去给'
  },
  {
    image: 'https://bossaudioandcomic-1252317822.image.myqcloud.com/activity/document/7cf1696363f5099007c00dc261caa40e.jpg',
    title: '清冷美人总腿软，'
  },
  {
    image: 'https://bossaudioandcomic-1252317822.image.myqcloud.com/activity/document/bbf9902537c3486211fbaf1d66d53676.jpg',
    title: '钓系公主不追了，'
  },
  {
    image: 'https://bossaudioandcomic-1252317822.image.myqcloud.com/activity/document/4cec405b9b825c5f8e644a049109088a.jpg',
    title: '与前男友在婚礼上'
  }
]);
import { useRouter } from 'vue-router';

const router = useRouter();
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

let members = ref([])
// 发送请求获取数据

// 将所有书籍数据分成三行，每行三列
const bookGroups = ref([]);
const booksPerRow = 3;
const recommendBooks = ref([]);


const getBook = () => {
  // 发送请求获取数据
  axios.get(`http://localhost:8080/book/interests/${id}`)
      .then((response) => {
        members.value = response.data.slice(0, 9);
        console.log(members.value)
        // 数据获取成功后进行分组操作
        recommendBooks.value= response.data.slice(10,24)
        for (let i = 0; i < members.value.length; i += booksPerRow) {
          bookGroups.value.push(members.value.slice(i, i + booksPerRow));
        }
      })
      .catch((error) => {
        console.error("请求出错:", error);
        // 处理错误，例如显示错误信息或采取其他措施
      });
}
const goToBookDetail = (bookId) => {
  router.push({name: 'BookDesc', params: {id: bookId}});
};

//公告
const activities = ref()

const getActivity = () => {
  // 发送请求获取数据
  axios.get("http://localhost:8080/activity/findAll").then((response) => {
    activities.value = response.data;
    console.log(response.data)
  }).catch((error) => {
    console.error("请求出错:", error);
    // 处理错误，例如显示错误信息或采取其他措施
  })

}

const getClickHandler = (activityId) => {
    router.push({ name: 'new', params: { id: activityId } })
};



//轮播图
const currentIndex = ref(0);
let interval;

const selectSlide = (index) => {
  currentIndex.value = index;
  clearInterval(interval); // 点击时清除当前定时器
  startCarousel(); // 重新开始自动轮播
};

const startCarousel = () => {
  interval = setInterval(() => {
    currentIndex.value = (currentIndex.value + 1) % carouselItems.value.length;
  }, 5000);
};

const stopCarousel = () => {
  clearInterval(interval);
};

onMounted(() => {
  startCarousel();
  window.addEventListener('visibilitychange', () => {
    if (document.visibilityState === 'hidden') {
      stopCarousel();
    } else {
      startCarousel();
    }
  });
  getBook()
  getActivity()
  getLabel()
  getReader()
});

onUnmounted(() => {
  stopCarousel();
  window.removeEventListener('visibilitychange', () => {
    if (document.visibilityState === 'hidden') {
      stopCarousel();
    } else {
      startCarousel();
    }
  });
});

// 个人爱好
// 所有标签类型
const currentReader = ref();
// 获取用户数据
const getReader = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/reader/findById/${id}`);
    currentReader.value = response.data;
    await checkReaderInterests()
    console.log("获取到的用户数据:", currentReader.value);
  } catch (error) {
    console.error("获取用户数据失败:", error);
  }
};
const allLabels = ref([]);
const getLabel = () => {
  // 发送请求获取数据
  axios.get("http://localhost:8080/label/findAll").then((response) => {
    allLabels.value = response.data;
    console.log(allLabels.value)
  }).catch((error) => {
    console.error("请求出错:", error);
    // 处理错误，例如显示错误信息或采取其他措施
  })
}
// 控制编辑弹窗显示状态
const isEditDialogVisible = ref(false);
// 关闭弹窗并重新加载用户数据
const closeEditDialog = () => {
  isEditDialogVisible.value = false;
  getReader(); // 重新加载用户数据
  getBook()
};
// 删除爱好
const deleteInterest = (id) => {
  axios.delete(`http://localhost:8080/interests/labelId/${id}`)
      .then(() => {
        currentReader.value.interests = currentReader.value.interests.filter(interest => interest.id!== id);
        ElMessage({type: 'success', message: '完成删除！',})
      })
      .catch((error) => {
        ElMessage.error('删除爱好失败，请稍后重试');
      });
};

// 添加爱好
const addInterest = (labelId) => {
  const labelObj = allLabels.value.find((label) => label.id === labelId);
  const label = labelObj ? labelObj.label : '';
  const isExist = currentReader.value.interests.some((interest) => interest.label === label);

  const interest = {
    labelId: labelId,
    readerId: id
  };
  if (!isExist) {
    axios
        .post('http://localhost:8080/interests/create', interest)
        .then((response) => {
          getReader()
          console.log(response.data)
          ElMessage({ type: 'success', message: '添加成功!' });
        })
        .catch((error) => {
          ElMessage.error('添加失败');
          console.error('请求出错:', error);
        });
  }else{
    ElMessage.error('已添加，请添加其他');
  }
};

// 检查读者兴趣标签是否为空
const checkReaderInterests = async () => {
  console.log(currentReader.value)
  if (currentReader.value.interests.length === 0) {
    isEditDialogVisible.value = true;
  }
};

</script>

<style scoped>
.app-container {
  font-family: Arial, sans-serif;
  background-color: #f9f4f8;
}

.main-content {
  display: flex;
  padding: 20px;

}

.banner {
  position: relative;
  width: 70%;
  overflow: hidden; /* 隐藏超出部分 */
}

.carousel-track {
  display: flex;
}

.carousel-slide {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
}

.banner-img {
  height: auto;
  cursor: pointer;
  width: 100%;
}

.banner-text {
  position: absolute;
  bottom: -1px;
  left: 0;
  display: flex;
  justify-content: space-around;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
}

.banner-text span {
  cursor: pointer;
  padding: 5px 12px;
  transition: background-color 0.3s ease;
}

.banner-text span.active {
  background-color: #f48fb1;
}

.announcement {
  z-index: 1;

  width: 30%;
  margin-left: 20px;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 10px;
}

.recommend-list {
  flex: 1;
  margin-left: 20px;
}

.recommend-item {
  display: flex;
  margin-bottom: 10px;
  align-items: center;
  font-size: 14px;
}


.announcement-list li {
  margin-bottom: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  padding: 5px;
}

.announcement-list li:hover {
  background-color: #f0f0f0;
}

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
  overflow: hidden; /* 确保放大时不会超出容器 */
}

.book-cover {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94); /* 缓慢放大 */
}

.book-cover:hover {
  transform: scale(1.1); /* 鼠标悬停时放大1.1倍，可根据需要调整比例 */
  opacity: 0.9; /* 轻微透明（可选） */
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

.book-tag {
  color: rgba(234, 52, 52, 0.38); /* 深灰色文字 */

}

.book-meta {
  font-size: 12px;
  color: #999;

}

.book-author {
  margin-right: 5px;
}

.announcement-list {
  list-style: none;
  padding: 0;
}

.announcement-item {
  cursor: pointer;
  padding: 10px;
}
.recommend-genre {
  margin-right: 5px;
  color: #f8bbd0;
}
.el-tag {
  margin-left: 10px;
  color: #f8bbd0;
  background-color: #f9f4f8;
  border: 1px solid #f8bbd0;
}
</style>