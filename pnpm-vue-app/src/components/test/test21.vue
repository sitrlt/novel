<template>
<div><p>{{message}}</p></div>
<ul>
  <li v-for="(stu,index) in stus" :key="index">{{stu}}</li>
</ul>

  <div v-if="message" >课件</div>
  <div v-else>不可见</div>

</template>

<script setup>
import {ref,onMounted} from 'vue'
const message = ref()
const stus = ["香蕉","苹果","栗子"]
onMounted(()=>{

})
</script>

<style scoped>

</style>