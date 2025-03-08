<template>
  <el-container style="height: 100vh">
    <el-header><BookHeader/></el-header>
    <el-main style="z-index: -1;height: 500px"> </el-main>

  </el-container>
  <el-footer  style="text-align: center;">
    <div class="divider"></div>
    Copyright © 1999-2025 www.hongxiu.com All Rights Reserved版权所有
  </el-footer>
</template>

<script setup>
import BookHeader from './BookHeader.vue'
</script>

<style scoped>
.divider {
  border-top: 1px solid #000;
  margin-top: 500px;
}
</style>