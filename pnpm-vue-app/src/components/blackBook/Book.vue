<template>
    <div class="topapp">
        <div class="header">
            <h1>小黑笔记本</h1>
            <input v-model="r"  autofocus="autofocus" autocomplete="off" placeholder="请输入任务" @keyup.enter="addlist"/>
        </div>
       <div class="main">
             <ul>
                 <li v-for="(item,index) in list">
                     <div class="view">
                         <span>{{index+1}}.</span>
                         <label>{{item}}</label>
                         <button @click="dele(index)">X</button>
                     </div>
                 </li>
             </ul>
       </div>
        <div class=" footer" >
            <span class="todo-count">
                <strong>{{list.length}}</strong>
                "items left"
            </span>
            <button class="clear-completed" @click="clear" > Clear</button>
        </div>
    </div>
</template>

<script setup>
    let r = "";
    let list =reactive( ["吃饭饭","睡觉觉","写代码"]);
     function addlist() {
          list.push(r);
          onReset();
     }
     function onReset() {
          r =" ";
     }
     function dele(index) {
          list.splice(index,1)
     }
     function clear() {
          list.splice(0);
     }
</script>

<style scoped>
   .footer{
       display: block;
       color: #777;
       padding: 10px 15px;
       height: 20px;
       border-top: 1px solid #e6e6e6;
   }
   .todo-count{

   }

</style>