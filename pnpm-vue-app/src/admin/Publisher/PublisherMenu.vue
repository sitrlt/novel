<template>
  <el-menu
      :default-active="$route.path"
      class="el-menu-vertical-demo"
      :router="true"
      background-color="#f8bbd0"
      text-color="#fff"
      active-text-color="#ffd04b"
  >
    <template v-for="item in menuData">
      <el-sub-menu v-if="item.children" :index="item.text">
        <template #title>
          <el-icon>
            <component :is="item.icon"></component>
          </el-icon>
          <span>{{ item.text }}</span>
        </template>
        <template v-for="child in item.children">
          <el-menu-item :index="child.path">
            <el-icon>
              <component :is="child.icon"></component>
            </el-icon>
            <span>{{ child.text }}</span>
          </el-menu-item>
        </template>
      </el-sub-menu>
      <el-menu-item v-else :key="item.text" :index="item.path">
        <el-icon>
          <component :is="item.icon"></component>
        </el-icon>
        <span>{{ item.text }}</span>
      </el-menu-item>
    </template>
  </el-menu>
</template>

<script setup>
const menuData=[
  {
    text: '首页',
    icon: 'HomeFilled',
    path: '/publisher/l1'
  },
  {
    text: '图书管理',
    icon: 'Menu',
    children: [
      {text: '新书发布',icon: 'Grid',path: '/publisher/l2'},
      {text: '书籍管理',icon: 'Grid',path: '/publisher/l3'},
    ]
  },
  {
    text: '库存管理',
    icon: 'Memo',
    path:'/publisher/l4'
  },
  {
    text: '反馈管理',
    icon: 'Medal',
    children: [
      {text: '反馈管理',icon: 'Watch',path: '/publisher/l5'}
    ]
  },
  {
    text: '修改密码',
    icon: 'EditPen',
    path: '/publisher/l6'
  },

]
</script>

<style scoped>
.el-menu{
  margin-left: 20px;
  margin-top:35px;
  height: 90%;
  border-radius: 5px;

}
.el-menu-item .icon {
  width: 16px;
  margin-right: 10px; /* 可选，根据需要调整图标和文本之间的间距 */
}
</style>