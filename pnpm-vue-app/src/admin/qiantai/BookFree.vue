<template>
  <div class="filter-container">
    <!-- 左侧筛选栏 -->
    <div class="left-sidebar">
      <!-- 性别筛选 -->
      <div class="filter-group">
        <div class="filter-options">
          <span :class="{ active: selectedCategory === '全部' }" @click="toggleCategory('全部')">全部</span>
          <span
              v-for="label in labels"
              :key="label.id"
              :class="{ active: selectedCategory === label.label }"
              @click="toggleCategory(label.label)"
          >
            {{ label.label }}
          </span>
        </div>
      </div>
      <!-- 属性筛选 -->
      <div class="filter-group">
        <div class="filter-options">
          <span :class="{ active: selectedProperty === '全部' }" @click="toggleProperty('全部')">全部</span>
          <span :class="{ active: selectedProperty === '免费' }" @click="toggleProperty('免费')">免费</span>
          <span :class="{ active: selectedProperty === 'VIP' }" @click="toggleProperty('VIP')">VIP</span>
        </div>
      </div>
      <div class="filter-group">
        <div class="filter-options">
          <span :class="{ active: selectedEbook === '全部' }" @click="toggleEbook('全部')">全部</span>
          <span :class="{ active: selectedEbook === '电子书' }" @click="toggleEbook('电子书')">电子书</span>
          <span :class="{ active: selectedEbook === '实体书' }" @click="toggleEbook('实体书')">实体书</span>
        </div>
      </div>
    </div>
    <!-- 右侧小说列表 -->
    <div class="right-content">
      <div class="book-list-container">
        <!-- 书籍展示区域 -->
        <div class="book-items">
          <div v-for="(group, groupIndex) in displayedItems" :key="groupIndex" class="book-row">
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
      <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[4, 10, 15, 20]"
          :total="total"
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import router from "../../router.js";

// 左侧筛选条件的响应式数据
const selectedCategory = ref('全部');
const selectedProperty = ref('全部');
const selectedEbook = ref('全部');
const currentPage = ref(1);
const pageSize = ref(9);
const total = ref(0);
const displayedItems = ref([]);
const booksPerRow = 3;
// 发送请求获取数据
const member = ref([]);
// 存储 label 表的数据
const labels = ref([]);

const goToBookDetail = (bookId) => {
  router.push({ name: 'BookDesc', params: { id: bookId } });
};

// 获取 label 表的数据
const getLabels = async () => {
  try {
    const response = await axios.get('http://localhost:8080/label/findAll');
    labels.value = response.data;
    console.log(labels.value)
  } catch (error) {
    console.error('获取标签数据失败:', error);
  }
};

// 获取数据的方法
// 获取数据的方法
// 获取数据的方法
const getData = () => {
  let payable;
  let ebook;
  if (selectedProperty.value === '免费') {
    payable = false;
  } else if (selectedProperty.value === 'VIP') {
    payable = true;
  }
  if(selectedEbook.value === '实体书'){
    ebook = false
  }else if(selectedEbook.value === '电子书'){
    ebook = true
  }

  const params = {
    pageNum: currentPage.value,
    pageSize: pageSize.value,
  };

  if (selectedCategory.value!== '全部') {
    params.label = selectedCategory.value;
  }

  if (selectedProperty.value!== '全部') {
    params.isPayable = payable;
  }
  if (selectedEbook.value!== '全部') {
    params.isEbook = ebook;
  }

  console.log('请求参数:', params);

  axios.get(`http://localhost:8080/book/label/findByPage`, {
    params
  })
      .then(response => {
        console.log('响应数据:', response.data);
        member.value = response.data.records;
        total.value = response.data.total;
        // 清空之前的分组数据
        displayedItems.value = [];
        // 按每行数量分组
        for (let i = 0; i < member.value.length; i += booksPerRow) {
          displayedItems.value.push(member.value.slice(i, i + booksPerRow));
        }
      })
      .catch(error => {
        console.error(error);
      });
};

// 切换分类的方法
const toggleCategory = (category) => {
  selectedCategory.value = category;
  getData();
};
// 切换属性的方法
const toggleProperty = (property) => {
  selectedProperty.value = property;
  getData();
};
const toggleEbook = (ebook) => {
  selectedEbook.value = ebook;
  getData();
}
// 处理页码变化点击事件
const handleCurrentChange = (pageNum) => {
  currentPage.value = pageNum;
  getData();
};
// 处理每页显示多少条事件
const handleSizeChange = (pagesize) => {
  pageSize.value = pagesize;
  getData();
};

// 组件挂载时获取数据
onMounted(async () => {
  await getLabels();
  getData();
});
</script>

<style scoped>
/* 样式部分保持不变 */
.filter-container {
  display: flex;
  width: 100%;
  background-color: #f9f4f8;
  height: 100%;
}
.left-sidebar {
  background-color: white;
  width: 20%;
  padding: 10px;
  height: 502px;
  margin: 12px;
}
.right-content {
  width: 80%;
  padding: 10px;
}
.filter-group {
  margin-bottom: 10px;
}
.filter-title {
  font-size: 16px;
  margin-bottom: 5px;
}
.filter-options {
  display: flex;
  flex-wrap: wrap;
}
.filter-options span {
  border-radius: 5px;
  padding: 5px 10px;
  margin-right: 5px;
  margin-bottom: 5px;
  cursor: pointer;
}
.filter-options span.active {
  background-color: #f8bbd0;
  color: white;
  border-radius: 2px;
}
.pagination {
  margin-top: 20px;
}
.pagination button {
  margin: 0 5px;
}

.book-list-container {
  display: flex;
  padding: 20px;
  background-color: white;
}

/*在ElementPlus中，可使用组件名称的类选择器选择对应组件，从而修改默认组件样式*/
.el-pagination { /*选择分页组件，默认采取Flex布局*/
  justify-content: center; /*水平方向居中对齐*/
  margin-top: 8px;
}
.book-items {
  flex: 3;
}

.book-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  gap: 10px; /* 在同一行的书籍容器之间添加间距 */
}

.book-item {
  width: calc(40% - 10px); /* 一行显示四本，每本宽度为 25% 减去间距 */
  display: flex;
  background-color: white;
  padding: 10px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  font-size: 11px;
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
</style>