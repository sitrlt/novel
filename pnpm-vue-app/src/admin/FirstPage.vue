<template>
  <div class="container">
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
    <div id="bookBorrowChart" style="width: 600px; height: 400px;"></div>
    <div id="dayBorrowChart" style="width: 600px; height: 400px;"></div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
// 引入ElementPlus图标
import {Bell, Folder, FolderChecked, House, Notebook, Sunny} from '@element-plus/icons-vue';
import axios from "axios";
import * as echarts from "echarts"; // 示例图标，可根据需求替换

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
const getBook = () => {
  axios.get('http://localhost:8080/borrow/statistics/book')
      .then(response => {
        const data = response.data;
        const bookNames = Object.keys(data);
        const borrowCounts = Object.values(data);

        // 将书籍名称和借阅次数组合成数组，并按照借阅次数降序排序
        const sortedData = bookNames.map((name, index) => ({ name, borrowCount: borrowCounts[index] }))
            .sort((a, b) => b.borrowCount - a.borrowCount);

        // 选择前八条数据
        const topEightBooks = sortedData.slice(0, 8);

        // 分别获取书名和借阅次数
        const topBookNames = topEightBooks.map(book => book.name);
        const topBorrowCounts = topEightBooks.map(book => book.borrowCount);

        console.log('Top 8 book names:', topBookNames);
        console.log('Top 8 borrow counts:', topBorrowCounts);

        const chart = echarts.init(document.getElementById('bookBorrowChart'));
        const option = {
          title: {
            text: '借阅排行前八',
            left: 'center'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          xAxis: {
            data: topBookNames,
            axisLabel: {
              fontSize: 8,
              rotate: 45,
              fontFamily: 'Arial, sans-serif'
            }
          },
          yAxis: {
            axisTick: {
              show: false // 隐藏 y 轴刻度线
            },
            axisLine: {
              show: false // 隐藏 y 轴轴线
            },
            splitLine: {
              show: false // 隐藏 y 轴网格线
            }
          },
          series: [{
            type: 'bar',
            data: topBorrowCounts,
            itemStyle: {
              color: '#f8bbd0' // 设置柱身为粉色
            }
          }],
        };
        chart.setOption(option);
        chart.resize(); // 手动调用 resize 确保图表尺寸正确
      });
}
const getDay = () => {
  // 获取每月的借阅数量统计并绘制图表
  axios.get('http://localhost:8080/borrow/statistics/day')
      .then(response => {
        const data = response.data;
        console.log(data)
        // 初始化 ECharts 实例，注意这里将容器 ID 修改为对应每日统计图表的 ID
        const chart = echarts.init(document.getElementById('dayBorrowChart'));
        const option = {
          title: {
            // 修改标题为每日的借阅数量统计
            text: '每日的借阅数量统计'
          },
          xAxis: {
            // 设置 x 轴为类目轴
            type: 'category',
            // 直接使用日期字符串作为 x 轴的数据
            data: Object.keys(data)
          },
          yAxis: {
            axisTick: {
              show: false // 隐藏 y 轴刻度线
            },
            axisLine: {
              show: false // 隐藏 y 轴轴线
            },
            splitLine: {
              show: false // 隐藏 y 轴网格线
            }
          },
          series: [{
            type: 'line',
            data: Object.values(data),
            itemStyle: {
              color: '#f8bbd0' // 设置柱身为粉色
            }
          }]
        };
        chart.setOption(option);
      })
      .catch(error => {
        console.error('获取每日借阅统计数据时出错:', error);
      });
}
onMounted(async () => {
  try {
    const response = await fetch('http://localhost:8080/bookAll/total');
    const data = await response.json();
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
  getDay()
  getBook()
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

.container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin-top: 30px;
  margin-left: 30px;
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
.borrow{
  display: flex;
}
</style>