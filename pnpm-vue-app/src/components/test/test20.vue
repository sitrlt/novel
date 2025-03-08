<template>
  <div>
    <el-table :data="bookList" style="width: 100%">
      <el-table-column prop="title" label="书籍名称"></el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button class="custom-pink-button" size="small" @click="handleReservation(row)" icon="Star">
            {{ row.stock > 0? '可借阅' : '无库存' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="提示" center>
      <p>已自动将该书分配给预约读者，并加入自动借书架，已通知相关读者可借阅。</p>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref } from 'vue';

// 模拟书籍列表数据
const bookList = ref([
  {
    id: 1,
    title: '书籍1',
    stock: 2 // 库存数量
  },
  {
    id: 2,
    title: '书籍2',
    stock: 0
  }
]);

const dialogVisible = ref(false);

// 处理预约和弹窗逻辑
const handleReservation = (row) => {
  if (row.stock > 0) {
    // 这里可以添加实际通知预约读者和加入自动借书架的逻辑，先模拟
    console.log(`已处理书籍 ${row.title} 的借阅操作`);
    dialogVisible.value = true;
  }
};
</script>

<style scoped>
.custom-pink-button {
  background-color: pink;
  color: white;
  border: none;
}
</style>