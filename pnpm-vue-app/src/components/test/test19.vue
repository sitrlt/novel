<template>
  <div class="stats-container">
    <div class="stat-item">
      <h2>总用户量</h2>
      <p>{{ totalUsers }}</p>
      <p>较昨日 <span :class="{'increase': dailyUsersIncrease > 0, 'decrease': dailyUsersIncrease < 0}">{{ dailyUsersIncrease }}</span></p>
    </div>
    <div class="stat-item">
      <h2>总书籍量</h2>
      <p>{{ totalBooks }}</p>
      <p>今日新增 <span :class="{'increase': dailyBooksAdded > 0, 'decrease': dailyBooksAdded < 0}">{{ dailyBooksAdded }}</span> 较昨日 <span :class="{'increase': dailyBooksChange > 0, 'decrease': dailyBooksChange < 0}">{{ dailyBooksChange }}</span></p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const totalUsers = ref(143103);
const dailyUsersIncrease = ref(156);

const totalBooks = ref(3012);
const dailyBooksAdded = ref(56);
const dailyBooksChange = ref(-15);
</script>

<style scoped>
.stats-container {
  display: flex;
  gap: 20px;
}

.stat-item {
  background-color: #f8bbd0;
  padding: 20px;
  color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
  margin-top: 50px;
  margin-left: 50px;
  width: 200px;
}

.stat-item h2 {
  margin: 0 0 10px;
  font-size: 18px;
}

.stat-item p {
  margin: 5px 0;
}

.increase {
  color: green;
}

.decrease {
  color: red;
}
</style>