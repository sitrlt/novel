<template>
    <p>传入子组件数据：<input type="text" v-model="fmsg"></p>
    <Son :prop1="fmsg"/><!-- 调用Child组件 -->
</template>

<script setup>
    import Son from './Son.vue';
   const fmsg = ref('abc');
</script>

<style scoped>

</style>