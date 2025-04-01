<template>
  <div v-for="(item,index) in data" :key=index>
    <div style="display:flex;">
      <img :src="item.book.coverImage" alt="封面预览" class="img"/>
    <span>{{item.book.title}}</span>
    </div>

  </div>
</template>

<script>
import axios from "axios";
export default {
  data(){
    return{
       data:null
    }
  },
  methods:{
    getData(){
      axios.get("http://localhost:8080/reservation/findAll").then((response)=>{
        this.data = response.data.map(item=>{
         return{
           ...item,
         }

        });
        console.log("当前数据：",this.data)
      }).catch((error)=>{
        console.error("错误：",error)
      })
    }
  },
  mounted() {
    this.getData()
  }
}
</script>

<style scoped>
.img{
  width: 80px;
  height: 90px;
}
</style>