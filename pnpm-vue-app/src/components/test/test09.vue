<template>
  <div class="add-employee-container">
  <div class="header">
    <span class="back-button">← 返回</span>
    <span class="title">添加员工</span>
  </div>
  <form class="form-container">
    <div class="form-item">
      <label class="label-required">* 账号:</label>
      <input type="text" placeholder="请输入账号" v-model="account" />
    </div>
    <div class="form-item">
      <label class="label-required">* 员工姓名:</label>
      <input type="text" placeholder="请输入员工姓名" v-model="name" />
    </div>
    <div class="form-item">
      <label class="label-required">* 手机号:</label>
      <input type="text" placeholder="请输入手机号" v-model="phone" />
    </div>
    <div class="form-item gender-item">
      <label class="label-normal">性别:</label>
      <div class="gender-options">
        <input type="radio" id="male" value="男" v-model="gender" />
        <label for="male" :class="{ 'active-radio': gender === '男' }">男</label>
        <input type="radio" id="female" value="女" v-model="gender" />
        <label for="female" :class="{ 'active-radio': gender === '女' }">女</label>
      </div>
    </div>
    <div class="form-item">
      <label class="label-required">* 身份证号:</label>
      <input type="text" placeholder="请输入身份证号" v-model="idNumber" />
    </div>
    <div class="button-group">
      <button class="cancel-button" @click="resetForm">取消</button>
      <button class="save-button" type="submit" @click="submitForm">保存</button>
      <button class="save-and-continue-button" type="submit" @click="submitAndRestForm">保存并继续添加</button>
    </div>
    <div v-if="message" :class="messageType">{{ message }}</div>
  </form>
  </div>
</template>


<script setup>
import { ref } from 'vue';
import axios from 'axios';
import {ElMessage, ElMessageBox} from "element-plus";
// 表单数据
const account = ref('');
const name = ref('');
const phone = ref('');
const gender = ref('男');
const idNumber = ref('');

// 消息提示
const message = ref('');
const messageType = ref('');

const submitForm =()=> {
    // 构建要发送的数据对象
    const employeeData = {
      account: account.value,
      name: name.value,
      phone: phone.value,
      gender: gender.value,
      idNumber: idNumber.value
    };
   console.log(employeeData)
    axios
        .post("http://localhost:8080/employees", employeeData)
        .then((response) => {
          // 更新成功后，重新从后端获取最新的数据
          ElMessage({type: 'success', message: '添加成功!',})
        })
        .catch((error) => {
          console.error("请求出错:", error);
        });
};


const resetForm = () => {
  account.value = '';
  name.value = '';
  phone.value = '';
  gender.value = '男';
  idNumber.value = '';
};

const submitAndRestForm = () =>{
  submitForm();
  resetForm();
};

</script>

<style scoped>
.add-employee-container {
                    padding: 20px;
                    background-color: #f9f9f9;
                  }

.header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.back-button {
          margin-right: 10px;
          cursor: pointer;
        }

.title {
  font-size: 18px;
  font-weight: bold;
}

.form-container {
          background-color: white;
          padding: 20px;
          border-radius: 5px;
          box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
        }

.form-item {
          margin-bottom: 15px;
        }

.label-required {
           color: #e53935;
           margin-right: 5px;
         }

.label-normal {
           margin-right: 5px;
         }

input[type="text"] {
  width: 300px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

.gender-item {
            display: flex;
            align-items: center;
          }

.gender-options {
            display: flex;
            align-items: center;
          }

input[type="radio"] {
  margin-right: 5px;
}

.active-radio {
            color: #ff9800;
          }

.button-group {
            display: flex;
            justify-content: flex-end;
            margin-top: 20px;
          }

.cancel-button {
            padding: 8px 20px;
            border: 1px solid #ccc;
            border-radius: 3px;
            background-color: white;
            cursor: pointer;
            margin-right: 10px;
          }

.save-button {
          padding: 8px 20px;
          border: none;
          border-radius: 3px;
          background-color: #ff9800;
          color: white;
          cursor: pointer;
          margin-right: 10px;
        }

.save-and-continue-button {
                           padding: 8px 20px;
                           border: none;
                           border-radius: 3px;
                           background-color: #333;
                           color: white;
                           cursor: pointer;
                         }
</style>