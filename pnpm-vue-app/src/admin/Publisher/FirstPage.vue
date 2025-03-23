<template>
  <div class="container1">
    <div class="card" v-for="(item, index) in bookStats" :key="index">
      <div class="card-header">
        <span>{{ item.title }}</span>
        <span :class="['tag', item.tagClass]">{{ item.tag }}</span>
      </div>
      <div class="card-body">
        <h1>{{ item.value }}本</h1>
        <!-- 使用ElementPlus图标 -->
        <el-icon :size="18" :class="['icon', item.tagClass]" style="margin-top: 10px;margin-left: 8px">
          <component :is="item.iconComponent"></component>
        </el-icon>
      </div>
      <p class="subtext">{{ item.subtext }}</p>
    </div>

  <div class="borrow">
    <div style="width: 500px; height: 500px" ref="pieChartRef"></div>
    <div style="width: 500px; height: 500px;margin-left: 10px" ref="barChartRef"></div>
  </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
import axios from 'axios';
import {Bell, FolderChecked, Notebook, Sunny} from "@element-plus/icons-vue";
function getUserIdFromSessionStorage() {
  const role ='publisher'; // 假设读者角色标识为'reader'
  const storageKey = `sessionUserId_${role}`;
  return sessionStorage.getItem(storageKey);
}

// 在需要获取用户 ID 的地方调用该函数
const id = getUserIdFromSessionStorage();

if (id) {
  console.log('当前登录出版社的用户 ID:', id);
  // 在这里可以进行后续操作，比如根据用户 ID 进行数据请求等
} else {
  console.log('未获取到用户 ID，可能用户未登录或会话已过期');
}

const publisherId = ref(id); // Retrieve the publisherId from localStorage
const pieChartRef = ref(null);
const barChartRef = ref(null);
const pieChartData = ref([]);
const barChartData = ref([]);

// 定义数据响应式变量
const bookStats = ref([
  {
    title: '图书总数',
    tag: '总',
    value: 0,
    subtext: '本周新增0本',
    tagClass: 'tag-pink',
    iconComponent: Notebook // 使用ElementPlus图标组件
  },
  {
    title: '在借书籍',
    tag: '用',
    value: 0,
    subtext: '共0人',
    tagClass: 'tag-green',
    iconComponent: FolderChecked
  },
  {
    title: '已还书籍',
    tag: '还',
    value: 0,
    subtext: '共0人',
    tagClass: 'tag-pink',
    iconComponent: Sunny
  },
  {
    title: '逾期未还',
    tag: '逾',
    value: 0,
    subtext: '共0人',
    tagClass: 'tag-green',
    iconComponent: Bell
  }
]);

const fetchData = async () => {
  if (publisherId.value) {
    try {
      const response = await axios.get(`http://localhost:8080/borrow/publisher-label-counts/${publisherId.value}`);
      const data = response.data;
      const labelNames = data.map(item => item['label']);
      const borrowCounts = data.map(item => item['borrow_count']);
      pieChartData.value = labelNames.map((name, index) => ({ name, value: borrowCounts[index] }));
      initPieChart();
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }
};

const fetchSalesData = async () => {
  if (publisherId.value) {
    try {
      const response = await axios.get(`http://localhost:8080/borrow/statisticsByDay/${publisherId.value}`);
      const data = response.data;
      console.log(data)
      barChartData.value = Object.entries(data).map(([date, sales]) => ({ name: date, value: sales }));
      initBarChart();
    } catch (error) {
      console.error('Error fetching sales data:', error);
    }
  }
};

const initPieChart = () => {
  const myChart = echarts.init(pieChartRef.value);
  const option = {
    title: {
      text: `借阅书籍的标签分类`,
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: '借阅次数',
        type: 'pie',
        radius: '50%',
        data: pieChartData.value,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  };
  myChart.setOption(option);
};

const initBarChart = () => {
  const myChart = echarts.init(barChartRef.value);
  const option = {
    title: {
      text: `每日借阅销售余额`,
      left: 'center'
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: barChartData.value.map(item => item.name),

    },
    yAxis: {
      type: 'value',
      axisLabel: {
        formatter: '{value} 元'
      },
      splitLine: {
        show: false // 隐藏 y 轴网格线
      }
    },
    series: [
      {
        data: barChartData.value.map(item => item.value),
        type: 'bar',
        barWidth: '20%', // 调整条形图的宽度
        itemStyle: {
          color: '#f8bbd0' // 设置柱身为粉色
        }
      }
    ]
  };
  myChart.setOption(option);
  myChart.resize(); // 手动调用 resize 确保图表尺寸正确

};

onMounted(async () => {
  try {
    const response = await axios(`http://localhost:8080/bookAllByPublisher/total/${publisherId.value}`);
    const data = await response.data;
    bookStats.value[0].value = data.total;
    bookStats.value[0].subtext = `本周新增${data.total}本`;
    bookStats.value[1].value = data.borrowing;
    bookStats.value[1].subtext = `共${data.borrowingCount}人`;
    bookStats.value[2].value = data.returned;
    bookStats.value[2].subtext = `共${data.returnedCount}人`;
    bookStats.value[3].value = data.overdue;
    bookStats.value[3].subtext = `共${data.overdueCount}人`;
  } catch (error) {
    console.error('Error fetching data:', error);
  }
  await fetchData();
  await fetchSalesData();
});
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body{
  font-family: 'Arial, sans-serif',fantasy;
}
.card {
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 250px;
}
.card-body{
  display: flex;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.tag {
  padding: 3px 8px;
  border-radius: 3px;
  font-size: 12px;
}

.tag-green {
  background-color: #c8e6c9;
  color: #388e3c;
}

.card-body h1 {
  font-size: 24px;
  margin-bottom: 5px;
}
.tag-pink {
  background-color: #f8bbd0;
  color: #f33680;
}

.subtext {
  margin-top: 5px;
  font-size: 14px;
  color: #666;
}
.container1{
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin-top: 30px;
  margin-left: 30px;
}
.borrow{
  margin-top: 10px;
  display: flex;
}
</style>
