<template>
    <div>
        <h1>会员信息</h1>
        <!-- :model绑定表单数据，label-width设置表单项标题的宽度，class为CSS样式类 -->
        <el-form :model="form" label-width="100px" class="form">
            <el-form-item label="会员ID"><!--表单项，label为表单项标题-->
                <el-input v-model="form.id"></el-input><!--表单项输入框，v-model绑定数据-->
            </el-form-item>
            <el-form-item label="姓名" prop="name"><!-- 姓名表单项，设置 prop 属性为 "name"，用于表单验证 -->
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone"><!-- 联系方式表单项，设置 prop 属性为 "phone"，用于表单验证 -->
                <el-input v-model="form.phone"></el-input>
            </el-form-item>
            <el-form-item label="会员等级" prop="level"><!-- 会员等级表单项，设置 prop 属性为 "level"，用于表单验证 -->
                <div class="my-2 flex items-center text-sm">
                    <el-radio-group v-model="radio" class="ml-4"><!-- 会员等级单选按钮组，绑定到 radio 变量 -->
                        <el-radio label="VIP会员">VIP会员</el-radio><!--单选框选项，label为绑定的值-->
                        <el-radio label="普通会员">普通会员</el-radio>
                        <el-radio label="黄金会员">黄金会员</el-radio>
                    </el-radio-group>
                </div>
            </el-form-item>
            <el-form-item label="积分" prop="points">
                <el-input v-model="form.points"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="add">增加</el-button><!--按钮组件，@click为点击事件绑定方法-->
            </el-form-item>
        </el-form>
        <table>
            <tr>
                <th class="title">会员ID</th>
                <th class="title">姓名</th>
                <th class="title">手机号</th>
                <th class="title">会员</th>
                <th class="title">积分</th>
                <th class="title">操作</th>
            </tr>
            <tr v-for="(item,index) in members"><!--v-for循环渲染列表-->
                <td>{{item.id}}</td><!--列表项数据-->
                <td>{{item.name}}</td>
                <td>{{item.phone}}</td>
                <td>{{item.level}}</td>
                <td>{{item.points}}</td>
                <td>
                    <el-button type="danger" @click="del(index)">删除</el-button> <!--按钮组件，@click为点击事件绑定方法-->
                </td>
            </tr>
        </table>
    </div>
</template>

<script setup>
import { reactive } from 'vue'
import { ref } from 'vue'
    // 引入element-plus组件
    import {ElMessage, ElMessageBox} from 'element-plus'
    //使用ref函数创建一个响应式变量radio，初始值为字符串'1'。
    const radio = ref('1')
    // 定义响应式数据
    const form = reactive({
        id: '',
        name: '',
        phone: '',
        level: radio,// form对象的level属性与radio变量建立绑定，level会自动响应radio的变化
        points: ''
    })
    // 定义响应式数组
    const members = reactive([
        {id: '20209013', name: '张三', phone: '13812345678', level: 'VIP会员', points: 1000},
        {id: '20201290', name: '李四', phone: '13912345678', level: '普通会员', points: 800},
        {id: '20219012', name: '王五', phone: '18812345678', level: 'VIP会员', points: 1200}
    ])

    function add() {
        // 判断表单数据是否填写完整
        if (form.id && form.name && form.phone && form.level && form.points) {
            // 将表单数据添加到会员信息数组中
            members.push({
                id: form.id,
                name: form.name,
                phone: form.phone,
                level: form.level,
                points: form.points
            })
            // 清空表单数据
            onReset()
        } else {
            // 表单数据不完整，弹出提示
            alert('请填写完整的会员信息')
        }
    }

    // 重置表单数据
    const onReset = () => {
        form.id = ''
        form.name = ''
        form.phone = ''
        form.level = ''
        form.points = ''
    }
    // 删除会员信息
    const del = (index) => {
        ElMessageBox.confirm(
            // 弹出删除确认框
            '请问要删除该会员信息吗?',
            '提示',
            {
                confirmButtonText: 'OK',
                cancelButtonText: 'Cancel',
                type: 'warning',
            }
        )
            .then(() => {
                // 点击确认删除会员信息
                members.splice(index, 1)
                // 弹出删除成功提示框
                ElMessage({
                    type: 'success',
                    message: '删除成功',
                })
            })
            .catch(() => {
                // 点击取消删除，弹出删除失败提示框
                ElMessage({
                    type: 'info',
                    message: '删除失败',
                })
            })
    }
</script>

<style scoped>/*定义局部样式*/
table { /*设置表格样式*/
    width: 1000px; /*表格宽度为1000px*/
    border-collapse: collapse; /*合并单元格边框*/
}

th, /*设置表格表头样式*/
td { /*设置表格数据样式*/
    border: 1px solid #ccc; /*设置边框样式*/
    padding: 8px; /*设置内边距*/
    text-align: center; /*设置文本居中对齐*/
}

.title {
    background-color: #e4e7e9;
    border: none;
}

</style>