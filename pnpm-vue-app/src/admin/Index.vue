<template>
    <el-container style="height: 100vh">
        <el-header ><Header/></el-header>
        <el-container>
            <el-aside width="200px"><Menu/></el-aside>
            <el-main style="margin-top: 20px">
                <router-view></router-view>
            </el-main>
        </el-container>

    </el-container>
</template>


<script setup>
   import Menu from './Menu.vue'
    import Header from './Header.vue'
</script>

<style scoped>

</style>