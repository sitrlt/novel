<template>
  <div class="reading-ranking">
    <div class="category-tabs">
      <el-button
          v-for="(category, index) in categories"
          :key="index"
          :type="activeCategory === category.id? 'primary' : ''"
          @click="changeCategory(category.id)"
      >
        {{ category.label }}
      </el-button>
    </div>
    <div class="book-list">
      <div v-for="(record, index) in filteredRecords" :key="index" class="book-item">
        <div class="book-cover">
          <img :src="record.book.coverImage" :alt="record.book.title" />
          <span class="rank">{{ index + 1 }}</span>
        </div>
        <div class="book-info">
          <h2 class="book-title">{{ record.book.title }}</h2>
          <div class="book-meta">
            <span class="author">{{ record.book.author }}</span>  |
            <span class="borrow-count">借阅量: {{ record.borrow_count }}</span>
          </div>
          <p class="book-desc">{{ record.book.description }}</p>
          <!-- 显示书籍标签 -->
          <div class="book-labels">
            <span v-for="(label, labelIndex) in record.book.labels" :key="labelIndex" class="book-label">
              {{ label.label }}
            </span>
          </div>
        </div>
        <div class="book-actions">
          <el-button type="primary" size="small">书籍详情</el-button>
          <el-button size="small">加入书架</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { ElButton } from 'element-plus';
import axios from 'axios';

// 模拟分类数据，调整为和 Label 类一致的数据结构
const categories = ref([]);

const getLabel = () => {
  // 发送请求获取数据
  axios.get("http://localhost:8080/label/findAll").then((response) => {
    const allCategory = { id: 0, label: '全部' };
    // 将“全部”选项放在数组首位
    categories.value = [allCategory, ...response.data];
    console.log(categories.value);
  }).catch((error) => {
    console.error("请求出错:", error);
    // 处理错误，例如显示错误信息或采取其他措施
  });
};

// 初始模拟数据，这里的结构要和后端返回数据结构对应
const records = ref([]);

// 当前选中的分类
const activeCategory = ref(1); // 默认选中“全部”，其 id 设为 1
// 过滤后的记录列表
const filteredRecords = ref([]);

const getCategoryLabel = (id) => {
  const category = categories.value.find(cat => cat.id === id);
  return category? category.label : '';
};

const changeCategory = (categoryId) => {
  activeCategory.value = categoryId;
  const categoryLabel = getCategoryLabel(categoryId);
  console.log('当前选择的分类ID:', categoryId);
  console.log('当前选择的分类标签:', categoryLabel);
  if (categoryId === 0) {
    // 如果选择“全部”，则显示所有记录
    filteredRecords.value = records.value;
    console.log('选择“全部”，显示所有', records.value.length, '条记录');
  } else {
    // 否则根据选择的分类过滤记录
    const filtered = records.value.filter((record) => {
      const match = record.book.labels.some((label) => label.label === categoryLabel);
      console.log('书籍:', record.book.title, '是否匹配当前分类:', match);
      return match;
    });
    filteredRecords.value = filtered;
    console.log('选择非“全部”分类，过滤后剩余', filtered.length, '条记录');
  }
};

onMounted(() => {
  getLabel();
  // 在组件挂载后发送请求获取数据
  axios.get('http://localhost:8080/books/ranking')
      .then(response => {
        const data = response.data;
        // 假设后端返回的数据和 records 结构一致，直接赋值
        console.log(data);
        records.value = data;
        // 初始默认显示所有书籍
        filteredRecords.value = records.value;
      })
      .catch(error => {
        console.error('获取数据失败', error);
      });
});
</script>

<style scoped>
.reading-ranking {
  padding: 20px;
  border-radius: 5px;
}

.title {
  margin-bottom: 20px;
}

.category-tabs {
  margin-bottom: 20px;
}

.book-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.book-item {
  display: flex;
  align-items: start;
  background-color: white;
  border-radius: 5px;
  padding: 15px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
}

.book-cover {
  width: 100px;
  height: 140px;
  position: relative;
  margin-right: 15px;
}

.book-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 5px;
}

.rank {
  position: absolute;
  top: 5px;
  left: 5px;
  background-color: orange;
  color: white;
  padding: 3px 8px;
  border-radius: 3px;
  font-size: 14px;
}

.book-info {
  flex: 1;
}

.book-title {
  margin-bottom: 10px;
}

.book-meta {
  color: #666;
  font-size: 14px;
  margin-bottom: 10px;
}

.book-desc {
  color: #333;
  font-size: 14px;
  line-height: 1.5;
  margin-bottom: 10px;
  -webkit-line-clamp: 2; /* 限制显示两行 */
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.update-info {
  color: #007bff;
  font-size: 14px;
}

.book-actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.borrow-count {
  color: #007bff;
  margin-left: 10px;
}

.book-labels {
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
  margin-top: 5px;
}

.book-label {
  background-color: #e0e0e0;
  padding: 2px 5px;
  border-radius: 3px;
  font-size: 12px;
}
</style>


