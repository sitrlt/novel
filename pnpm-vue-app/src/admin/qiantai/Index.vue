<template>
  <el-container style="height: 100%">
    <el-header><BookHeader/></el-header>
    <el-main style="z-index: -1;height: 500px;"> </el-main>

  </el-container>
</template>

<script setup>
import BookHeader from './BookHeader.vue'
</script>

<style scoped>
.divider {
  border-top: 1px solid #000;
  margin-top: 800px;
}

</style>