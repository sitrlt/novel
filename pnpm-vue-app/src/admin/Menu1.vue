<template>
  <el-menu
      :default-active="$route.path"
      class="el-menu-vertical-demo"
      :router="true"
      background-color="#33414f"
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
      path: '/'
    },
    {
      text: '用户管理',
      icon: 'Menu',
      children: [
        {text: '用户列表',icon: 'Grid',path: '/l1'},
        {text: '添加用户',icon: 'HelpFilled',path: '/l2'},
        {text: '用户权限管理',icon: 'Promotion',path: '/l3'}
      ]
    },
    {
      text: '商品管理',
      icon: 'EditPen',
      children: [
        {text: '商品列表',icon: 'Menu',path: '/l4'},
        {text: '修改商品',icon: 'StarFilled',path: ''},
        {text: '商品分类管理',icon: 'Promotion',path: ''}
      ]
    },
    {
      text: '客户管理',
      icon: 'Memo',
      children: [
        {text: '客户列表',icon: 'Notebook',path: ''},
        {text: '会员列表',icon: 'Promotion',path: ''},
        {text: '验光信息列表',icon: 'Document',path: ''}
      ]
    },
    {
      text: '活动管理',
      icon: 'Medal',
      children: [
        {text: '活动列表',icon: 'Watch',path: ''},
        {text: '活动报名',icon: 'Promotion',path: ''},
        {text: '活动评价',icon: 'Help',path: ''}
      ]
    },
  ]
  //使用Json文件导入菜单数据
  // import datas from './meun.json'
  // const menuData=JSON.parse(JSON.stringify(datas))

</script>
<style scoped>
.el-menu{
  height: 100%;
}
.el-menu-item .icon {
  width: 16px;
  margin-right: 10px; /* 可选，根据需要调整图标和文本之间的间距 */
}
</style>