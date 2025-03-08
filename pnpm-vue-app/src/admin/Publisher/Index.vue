<template>
  <el-container style="height: 100vh">
    <el-header ><Header/></el-header>
    <el-container>
      <el-aside width="200px"><Menu/></el-aside>
      <el-main style="margin-top: 20px ">
        <router-view></router-view>
      </el-main>
    </el-container>

  </el-container>
</template>


<script setup>
import Header from './PublisherHeader.vue'
import Menu from './PublisherMenu.vue'
</script>

<style scoped>
.el-header{
  background-color: #f2f6f4;
}
.el-main {
  --el-main-padding: 0px;
  box-sizing: border-box;
  display: block;
  flex: 1;
  flex-basis: auto;
  overflow: auto;
  padding: var(--el-main-padding);
}
</style>