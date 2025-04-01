<template>
  <div class="container">
  <div class="back" >

  </div>
    <div class="back-span">
      <el-icon><ArrowLeft /></el-icon>
      <span>{{data1.materialName|| '未填写'}}</span>
    </div>
  <div class="rfid-standard-container">
    <h1 class="title">RFID标签安装标准</h1>

    <div class="standard-table">
      <!-- 第一行：装备名称和类别 -->
      <div class="table-row">
        <div class="table-cell">
          <span class="label">装备名称：</span>
          <span class="value">{{ data1.materialName || '未填写' }}</span>
        </div>
        <div class="table-cell">
          <span class="label">装备类别：</span>
          <span class="value">{{ data1.parentMenu || '未填写' }}</span>
        </div>
      </div>
    </div>
    <div>
      <div class="divider"></div>
      <div style="padding: 0 15px;">
      <span class="label">安装方案：</span>
    <div class="image-container">
      <img v-if="data1.imageUrl" :src="data1.chipcodeImg" alt="装备安装图片" class="installation-image">
      <div v-else class="no-image">暂无图片</div>
    </div>
      </div>
    </div>
    <div class="divider"></div>

    <div class="specs-section">
      <div class="spec-row">
        <span class="label">标签类型：</span>
        <span class="value">ABS</span>
      </div>
      <div class="spec-row">
        <span class="label">尺寸：</span>
        <span class="value">85*22mm/80*20mm/45*45mm</span>
      </div>
    </div>
    <div class="divider"></div>

    <div class="tag-requirements">
      <div class="spec-row">
        <span class="label">标签打印要求：</span>
        <span class="value">标签右侧、左侧和下方分别显示从http://xfzb.119.gov.cn/#/login网站申请下来的产品名称、二维码和唯一编码。</span>
        <div class="example">
          <span>示例：</span>
          <div class="example-image-container">
            <img v-if="data1.tagExample" :src="data1.tagExample" alt="标签示例" class="example-image">
            <div v-else class="no-example">无示例</div>
          </div>
        </div>
      </div>

    </div>
    <div class="divider"></div>

    <div class="installation-standard">
      <div class="spec-row">
        <span class="label">安装标准：</span>
        <span class="value pre-formatted">{{ data1.chipcodeRemark || '未填写' }}</span>
      </div>
    </div>

    <div class="notes-section">
      <div class="spec-row">
        <span class="label">注：</span>
        <span class="value pre-formatted">由于品牌与型号差异性请根据实际情况选择安装位置。</span>
      </div>
    </div>
  </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue'
import axios from "axios";
const data1 = ref({

})
const commodityId = ref(9297)
const getData = () => {
/*  axios.get("http://t3ndkm.natappfree.cc/equip/commodityById",{
    params: {
      commodityId:commodityId.value
    }
  }).then((response) => {
    data.value = response.data.data;
    console.log("当前数据：",response.data.data)
  }).catch((error) => {
    console.error("请求出错:", error);
    // 处理错误，例如显示错误信息或采取其他措施
  })*/
  fetch(' http://h9s5kd.natappfree.cc/equip/commodityById')
      .then(response => response.json())
      .then(data => {
        data1.value = data.data
        console.log(data.data)
      });
}
onMounted(() => {
  getData();

});
</script>

<style scoped>
.container{
  max-width: 800px;
  margin: 0 auto;

}
.rfid-standard-container {
  padding: 10px;
  font-family: Arial, sans-serif;
  color: #333;
  background-color: #fff;
  border: 1px solid #e0e0e0;

}

.title {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.sub-title {
  font-size: 18px;
  font-weight: bold;
  margin: 15px 0 10px 0;
}

.standard-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 15px;
}

.table-row {
  padding: 0 15px;
}

.table-cell {
  margin-bottom: 10px;
}

.full-width {
  flex-basis: 100%;
}

.label {
  font-weight: bold;
  min-width: 80px;
  margin-right: 10px;
}

.value {
  flex-grow: 1;
}

.pre-formatted {
  white-space: pre-wrap;
  word-break: break-word;
}


.image-container {
  text-align: center;
  margin: 15px 0;
  padding: 10px;
  background-color: #f9f9f9;
  border: 1px dashed #ccc;
  border-radius: 4px;

}

.installation-image {
  max-width: 100%;
  max-height: 300px;
  border: 1px solid #ddd;
}

.no-image {
  color: #999;
  padding: 30px 0;
}

.specs-section, .tag-requirements, .installation-standard, .notes-section {
  margin: 15px 0;
  padding: 0 15px;
}
.divider{
  border: 1px solid #ddd;

}
.spec-row {
  margin-bottom: 10px;
}
.example{
  display: flex;
}
.example-image-container {
  text-align: center;
  margin: 10px 0;
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.example-image {
  max-width: 100px;
  max-height: 100px;
  border: 1px solid #ddd;
}

.no-example {
  color: #999;
}
.back{
  background-color: #ededed;
  height: 70px;
}
.back-span{
  background-color: #ededed;
  padding:10px;
}
</style>