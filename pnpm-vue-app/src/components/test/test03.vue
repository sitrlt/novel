<template>
        <!-- 添加会员信息的表单 -->
        <el-form :model="form" ref="form" label-width="100px" class="form">
            <el-form-item label="会员ID">
                <el-input v-model="form.id"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone">
                <el-input v-model="form.phone"></el-input>
            </el-form-item>
            <el-form-item label="会员等级" prop="level">
                <el-input v-model="form.level"></el-input>
            </el-form-item>
            <el-form-item label="积分" prop="points">
                <el-input v-model="form.points"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onAdd">增加</el-button>
                <el-button type="default" @click="onReset">重置</el-button>
            </el-form-item>
        </el-form>

        <!-- 显示会员信息的表格 -->
        <el-table :data="members" style="width: 100%">
            <el-table-column prop="id" label="会员ID"></el-table-column>
            <el-table-column prop="name" label="姓名"></el-table-column>
            <el-table-column prop="phone" label="联系方式"></el-table-column>
            <el-table-column prop="level" label="会员等级"></el-table-column>
            <el-table-column prop="points" label="积分"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="danger"  @click="onDelete(scope.$index)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
</template>

<script setup>
    import { reactive } from 'vue'
    import { ref } from 'vue'

    const form = reactive({
                id: '',
                name: '',
                phone: '',
                level: '',
                points: ''
            })

            const members = reactive([
                {id: '001', name: '张三', phone: '13812345678', level: 'VIP', points: 1000},
                {id: '002', name: '李四', phone: '13912345678', level: '普通会员', points: 800},
                {id: '003', name: '王五', phone: '18812345678', level: 'VIP', points: 1200}
            ])

            // 增加会员信息
            const onAdd = () => {
                members.push({
                    id: form.id,
                    name: form.name,
                    phone: form.phone,
                    level: form.level,
                    points: form.points
                })
                onReset();
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
            const onDelete = (index) => {
// 从会员信息数组中删除对应索引的会员信息
                members.splice(index, 1)
            }


</script>
<style scoped>
    .form {
        width: 400px;
        margin-bottom: 20px;
    }
</style>