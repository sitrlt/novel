<template>
  <div class="card">
    <div class="info">
      <h2>基本信息</h2>
      <div class="data-item" @mouseenter="hoveredItem = 'username'" @mouseleave="hoveredItem = null">
        <span>用户昵称: </span>
        <span v-if="!isEditing.username">{{ reader.username }}</span>
        <el-input
            v-else
            type="text"
            v-model="editData.username"
            ref="usernameInput"
            @blur="saveField('username')"
            @keyup.enter="saveField('username')"
            class="edit-input"
        />
        <span v-if="hoveredItem === 'username' && !isEditing.username" class="edit-span" @click="editField('username')">编辑</span>
      </div>

      <div class="data-item" @mouseenter="hoveredItem = 'email'" @mouseleave="hoveredItem = null">
        <span>邮    箱: </span>
        <span v-if="!isEditing.email">{{ reader.email }}</span>
        <el-input
            v-else
            type="email"
            v-model="editData.email"
            ref="emailInput"
            @blur="saveField('email')"
            @keyup.enter="saveField('email')"
            class="edit-input"
        />
        <span v-if="hoveredItem === 'email' && !isEditing.email" class="edit-span" @click="editField('email')">编辑</span>
      </div>

      <div class="data-item" @mouseenter="hoveredItem = 'phone'" @mouseleave="hoveredItem = null">
        <span>联系方式: </span>
        <span v-if="!isEditing.phone">{{ reader.phone }}</span>
        <el-input
            v-else
            type="text"
            v-model="editData.phone"
            ref="phoneInput"
            @blur="saveField('phone')"
            @keyup.enter="saveField('phone')"
            class="edit-input"
        />
        <span v-if="hoveredItem === 'phone' && !isEditing.phone" class="edit-span" @click="editField('phone')">编辑</span>
      </div>

      <div class="data-item" @mouseenter="hoveredItem = 'address'" @mouseleave="hoveredItem = null">
        <span>所在地区: </span>
        <span v-if="!isEditing.address">{{ reader.address }}</span>
        <el-input
            v-else
            type="text"
            v-model="editData.address"
            ref="addressInput"
            @blur="saveField('address')"
            @keyup.enter="saveField('address')"
            class="edit-input"
        />
        <span class="edit-span" v-if="hoveredItem === 'address' && !isEditing.address"  @click="editField('address')"><Edit/>编辑</span>
      </div>
      <div class="data-item">
        <span>注册日期: </span>
        <span >{{ reader.registrationDate }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick } from 'vue';

    const reader = ref({
      username: '张三',
      email: 'zhangsan@example.com',
      phone: '1234567890',
      address: '北京市',
      registrationDate: '2023-01-01',
    });

    const hoveredItem = ref(null);
    const isEditing = ref({
      username: false,
      email: false,
      phone: false,
      address: false,
      registrationDate: false,
    });
    const editData = ref({ ...reader.value });

    // 使用 ref 来获取 DOM 元素
    const usernameInput = ref(null);
    const emailInput = ref(null);
    const phoneInput = ref(null);
    const addressInput = ref(null);
    const registrationDateInput = ref(null);

    const editField = (field) => {
      isEditing.value[field] = true;
      nextTick(() => {
        const inputRef = {
          username: usernameInput,
          email: emailInput,
          phone: phoneInput,
          address: addressInput,
          registrationDate: registrationDateInput,
        }[field].value;

        if (inputRef) {
          inputRef.focus();
        }
      });
    };

    const saveField = (field) => {
      reader.value[field] = editData.value[field];
      isEditing.value[field] = false;
    };

</script>

<style>
.card {
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 100%;
}

.data-item {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
}

.data-item span:first-child {
  margin-right: 10px;
  white-space: nowrap;
}

.edit-span{
  display: inline-flex; /* 确保按钮内容在同一行 */
  align-items: center; /* 垂直居中对齐 */
  color: #f8bbd0;
}
.edit-span svg {
  margin-right: 5px; /* 添加一些间距 */
  width: 16px; /* 调整图标大小 */
  height: 16px;
}
.edit-input {
  width: 120px;
}
.info h2 {
  margin: 0 0 20px;
  color: #333;
}

.info p {
  margin: 45px 0;
  color: #666;
}

.info a {
  color: #007bff;
  text-decoration: none;
}


</style>