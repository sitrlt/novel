<template>
  <div class="novel-list-container">
    <div class="content">
      <div class="breadcrumb-container">
        <el-breadcrumb :separator-icon="ArrowRight" class="breadcrumb">
          <el-breadcrumb-item :to="{ path: '/novel/shouye' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>小说搜索</el-breadcrumb-item>
          <el-breadcrumb-item>{{ searchKeyword }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="novel-item" v-for="novel in novels" :key="novel.id" @click="goToBookDetail(novel.id)">
        <img :src="novel.coverImage" :alt="novel.title" class="novel-cover"/>
        <div class="novel-info">
          <div class="novel-header">
            <h2 class="novel-title">{{ novel.title }}</h2>
            <p class="novel-author">作者：<span class="author-name">{{ novel.author }}</span></p>
            <p class="novel-update">出版日期：<span class="update-time">{{ novel.publishDate }}</span></p>
          </div>
          <div class="novel-synopsis">
            <p>{{ novel.description }}</p>
          </div>
          <el-button type="primary" @click="goToBookDetail(novel.id)">书籍详情</el-button>
          <el-button>加入借书架</el-button>
        </div>
        <hr class="novel-divider"> <!-- 添加分隔线 -->
      </div>
    </div>
  </div>
</template>

<script setup>
import {ArrowRight} from '@element-plus/icons-vue';
import {ref, onMounted, watch, computed} from 'vue';
import {useRoute} from 'vue-router';
import axios from 'axios';
import router from "../../router.js";

const route = useRoute();
const searchKeyword = computed(() => route.query.keyword || '');
const novels = ref([]);

// 使用 axios 获取小说列表
const fetchNovels = async () => {
  if (!searchKeyword.value) return; // 如果搜索关键词为空，则不执行请求

  try {
    axios.get(`http://localhost:8080/book/search`, {params: {keyword: searchKeyword.value}}).then(response => {
      novels.value = response.data;
      console.log(novels)
    }).catch(error => {
      console.error(error);
    });
  } catch (error) {
    console.error('获取小说列表失败:', error);
    // 处理错误情况，例如显示错误消息
  }
};

const goToBookDetail = (bookId) => {
  router.push({ name: 'BookDesc', params: { id: bookId } });
};

onMounted(() => {
  fetchNovels();
});

watch(searchKeyword, (newKeyword) => {
  if (newKeyword) {
    fetchNovels();
  }
});

</script>

<style scoped>
.novel-list-container {
  display: flex;
  justify-content: center;
  padding: 20px;
}

.content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  max-width: 800px;
}

.breadcrumb-container {
  z-index: 1;
  margin-bottom: 20px;
}

.breadcrumb {
  display: flex;
  align-items: center;
}

.novel-item {
  z-index: 1;
  display: flex;
  align-items: flex-start;
  margin-bottom: 20px;
  width: 100%;
  border-bottom: 1px solid #ccc; /* 添加底部边框 */
  padding-bottom: 20px; /* 添加内边距 */
}

.novel-cover {
  width: 100px;
  height: 150px;
  margin-right: 20px;
  object-fit: cover;
}

.novel-info {
  flex-grow: 1;
}

.novel-header {
  margin-bottom: 10px;
}

.novel-title {
  font-size: 18px;
  font-weight: bold;
}

.novel-author,
.novel-update {
  color: #666;
}

.novel-synopsis {
  margin-bottom: 20px;
}

.el-button {
  margin-right: 10px;
}

.novel-divider {
  border: none;
  border-top: 1px solid #ccc; /* 定义分隔线的样式 */
  margin: 20px 0; /* 调整分隔线与内容的间距 */
}
</style>
<!--
import {ArrowRight} from '@element-plus/icons-vue';

import { ElMessage } from 'element-plus';

const novels = ref([
  {
    id: 1,
    title: '惊世神凰',
    author: '邹墨',
    publishDate: '2020-12-25 05:32:20',
    description: '她本是现代隐世家主，一朝穿越，成了异界第一废材？退婚？废材？特喵的...',
    cover: 'path/to/cover1.jpg'
  },
  {
    id: 2,
    title: '医妃惊世',
    author: '顾染锦',
    publishDate: '2021-08-26 09:15:11',
    description: '她本是实力强悍，医术超群的世家家主。一朝穿越成将军府的废柴嫡小姐...',
    cover: 'path/to/cover2.jpg'
  },
  // 更多小说数据...
]);
-->