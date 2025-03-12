<template>
  <div class="reading-ranking">
    <div class="category-tabs">
      <el-button
          v-for="(category, index) in categories"
          :key="index"
          :type="activeCategory === category.id? 'primary' : ''"
          @click="changeCategory(category.id)"
          :style="{ backgroundColor: activeCategory === category.id? '#f8bbd0' : 'white',
                    color: activeCategory === category.id? 'white' : '#f8bbd0',
                    border: activeCategory === category.id? 'none' : '1px solid #f8bbd0'
  }"
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
          <el-button @click="goToBookDetail(record.book.id)" class="custom-pink-button">书籍详情</el-button>
          <el-button class="custom-pink-button1" @click="handleButtonClick(record)">{{ record.bookInventory?.availableCopies === 0? '加入预约书架' : '加入借书架' }}
          </el-button>
        </div>
      </div>
    </div>
    <!-- 新增分页组件 -->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalRecords"
    >
    </el-pagination>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import {ElButton, ElMessage} from 'element-plus';
import axios from 'axios';
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

// 分页相关变量
const currentPage = ref(1); // 当前页码
const pageSize = ref(5); // 每页数量
const totalRecords = ref(0); // 总记录数

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
    filteredRecords.value = records.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
    console.log('选择“全部”，显示当前页', filteredRecords.value.length, '条记录');
  } else {
    // 否则根据选择的分类过滤记录
    const filtered = records.value.filter((record) => {
      const match = record.book.labels.some((label) => label.label === categoryLabel);
      console.log('书籍:', record.book.title, '是否匹配当前分类:', match);
      return match;
    });
    filteredRecords.value = filtered.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
    console.log('选择非“全部”分类，过滤后当前页剩余', filteredRecords.value.length, '条记录');
  }
};

// 每页数量变化时的处理函数
const handleSizeChange = (newSize) => {
  pageSize.value = newSize;
  currentPage.value = 1;
  if (activeCategory.value === 0) {
    filteredRecords.value = records.value.slice(0, pageSize.value);
  } else {
    const filtered = records.value.filter((record) => {
      const match = record.book.labels.some((label) => label.label === getCategoryLabel(activeCategory.value));
      return match;
    });
    filteredRecords.value = filtered.slice(0, pageSize.value);
  }
};

//
const goToBookDetail = (bookId) => {
  router.push({ name: 'BookDesc', params: { id: bookId } });
};

// 当前页码变化时的处理函数
const handleCurrentChange = (newPage) => {
  currentPage.value = newPage;
  if (activeCategory.value === 0) {
    filteredRecords.value = records.value.slice((newPage - 1) * pageSize.value, newPage * pageSize.value);
  } else {
    const filtered = records.value.filter((record) => {
      const match = record.book.labels.some((label) => label.label === getCategoryLabel(activeCategory.value));
      return match;
    });
    filteredRecords.value = filtered.slice((newPage - 1) * pageSize.value, newPage * pageSize.value);
  }
};

const addToReservationList = async (index) => {
  try {
    const reservation = {
      readerId: id,
      bookIsbn: index.bookIsbn,
    };
    const response = await axios.post("http://localhost:8080/reservation/create", reservation);
    ElMessage({ type: 'success', message: '添加成功!' });
  } catch (error) {
    ElMessage.error('添加失败');
    console.error("请求出错:", error);
  }
  console.log('加入预约书架:', index.value);
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
const status = ref('待处理');

const addToBorrowList = async (index) => {
  console.log(index)
  try {
    const borrowRecord = {
      readerId: id,
      bookIsbn: index.bookIsbn,
      dueDate: dueDateString,
      borrowDate: currentDateString,
      status: status.value
    };
    const response = await axios.post("http://localhost:8080/borrowRecord/create", borrowRecord);
    if(response.data === '插入成功') {
      ElMessage({type: 'success', message: '添加成功!'});
    }else if(response.data === '该读者与书籍的组合已存在，无需重复插入'){
      ElMessage.error('不可重复加入借书架');
    }
  } catch (error) {
    ElMessage.error('添加失败');
    console.error("请求出错:", error);
  }
  console.log('加入借书架:', index);
};


const handleButtonClick = (index) => {
  console.log(index)
   updateStatus(index)
  console.log(status.value)
  if (index.bookInventory?.availableCopies === 0) {
    addToReservationList(index);
  } else {
    addToBorrowList(index);
  }
};
const updateStatus = (index) => {
  if (index.book && index.book.ebook === true) {
    status.value = '借阅中';
  } else {
    status.value = '待处理';
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
        totalRecords.value = data.length;
        // 初始默认显示所有书籍的第一页
        filteredRecords.value = records.value.slice(0, pageSize.value);
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
/*在ElementPlus中，可使用组件名称的类选择器选择对应组件，从而修改默认组件样式*/
.el-pagination { /*选择分页组件，默认采取Flex布局*/
  justify-content: center; /*水平方向居中对齐*/
  margin-top: 8px;
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
  margin-right: 30px;

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
  margin-right: 30px;
}

.book-actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 60px;
  align-items: center;
}

.borrow-count {
  color: #f8bbd0;
  margin-left: 10px;
}

.book-labels {
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
  margin-top: 5px;
}

.book-label {
  background-color: #f8bbd0;
  padding: 2px 5px;
  border-radius: 3px;
  font-size: 12px;
  color: white;
}

.custom-pink-button {
  background-color: #f8bbd0;
  color: white;
  border: none;
  width: 90px; /* 添加固定宽度，可根据实际需求调整数值 */
  border-radius: 4px;
  padding: 8px 16px; /* 设置统一的内边距 */
  margin: 0; /* 设置统一的外边距，可根据实际需求调整 */
}

.custom-pink-button:hover,
.custom-pink-button:focus {
  background-color: #faa3c1;
  color: white;
  border: none;
}

.custom-pink-button:active {
  background-color: #faa3c1;
  color: white;
  border: none;
}
.custom-pink-button1 {
  background-color: white;
  color: #f8bbd0;
  border: 1px solid #f8bbd0;
  width: 90px; /* 添加固定宽度，可根据实际需求调整数值 */
  border-radius: 4px;
  padding: 8px 16px; /* 设置统一的内边距 */
  margin: 0; /* 设置统一的外边距，可根据实际需求调整 */
}

.custom-pink-button1:hover,
.custom-pink-button1:focus {
  background-color: white;
  color: #faa3c1;
  border: 1px solid #f8bbd0;
}

.custom-pink-button1:active {
  background-color: white;
  color: #faa3c1;
  border: none;
}
</style>


