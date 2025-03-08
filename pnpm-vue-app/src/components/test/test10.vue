<template>
  <div class="personal-center">
    <div class="sidebar">
      <ul>
        <li :class="{ active: currentTab === 'address' }" @click="currentTab = 'address'">收货地址</li>
        <li :class="{ active: currentTab === 'favorite' }" @click="currentTab = 'favorite'">商品收藏</li>
        <li :class="{ active: currentTab === 'order' }" @click="currentTab = 'order'">商品订单</li>
      </ul>
    </div>
    <div class="main-content" v-if="currentTab === 'address'">
      <button class="add-button" @click="showAddAddressModal = true">+ 添加新地址</button>
      <table class="address-table">
        <thead>
        <tr>
          <th>主键</th>
          <th>收货人</th>
          <th>电话</th>
          <th>地址</th>
          <th>是否默认地址</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="address in addressList" :key="address.id">
          <td>{{ address.id }}</td>
          <td>{{ address.receiver }}</td>
          <td>{{ address.phone }}</td>
          <td>{{ address.address }}</td>
          <td>{{ address.isDefault? '是' : '否' }}</td>
          <td>
            <button class="edit-button" @click="editAddress(address)">修改</button>
            <button class="delete-button" @click="deleteAddress(address.id)">删除</button>
          </td>
        </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button :disabled="page === 1" @click="page--">上一页</button>
        <span :class="{ active: index === page - 1 }" v-for="(index, i) in totalPages" :key="i" @click="page = index + 1">{{ index + 1 }}</span>
        <button :disabled="page === totalPages" @click="page++">下一页</button>
      </div>

      <!-- 添加地址模态框 -->
      <div v-if="showAddAddressModal" class="modal">
        <div class="modal-content">
          <span class="close" @click="showAddAddressModal = false">&times;</span>
          <h2>添加新地址</h2>
          <form @submit.prevent="addAddress">
            <label for="receiver">收货人:</label>
            <input type="text" id="receiver" v-model="newAddress.receiver" required>
            <label for="phone">电话:</label>
            <input type="text" id="phone" v-model="newAddress.phone" required>
            <label for="address">地址:</label>
            <input type="text" id="address" v-model="newAddress.address" required>
            <label for="isDefault">是否默认地址:</label>
            <input type="checkbox" id="isDefault" v-model="newAddress.isDefault">
            <button type="submit">保存</button>
          </form>
        </div>
      </div>

      <!-- 修改地址模态框 -->
      <div v-if="showEditAddressModal" class="modal">
        <div class="modal-content">
          <span class="close" @click="showEditAddressModal = false">&times;</span>
          <h2>修改地址</h2>
          <form @submit.prevent="updateAddress">
            <label for="editReceiver">收货人:</label>
            <input type="text" id="editReceiver" v-model="editedAddress.receiver" required>
            <label for="editPhone">电话:</label>
            <input type="text" id="editPhone" v-model="editedAddress.phone" required>
            <label for="editAddress">地址:</label>
            <input type="text" id="editAddress" v-model="editedAddress.address" required>
            <label for="editIsDefault">是否默认地址:</label>
            <input type="checkbox" id="editIsDefault" v-model="editedAddress.isDefault">
            <button type="submit">保存</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

// 当前选中的侧边栏标签
const currentTab = ref('address');
// 地址列表
const addressList = ref([]);
// 总页数
const totalPages = ref(1);
// 当前页码
const page = ref(1);
// 添加地址模态框显示状态
const showAddAddressModal = ref(false);
// 修改地址模态框显示状态
const showEditAddressModal = ref(false);
// 新地址对象
const newAddress = ref({
  receiver: '',
  phone: '',
  address: '',
  isDefault: false
});
// 编辑中的地址对象
const editedAddress = ref({});

// 获取地址列表
const fetchAddressList = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/addresses?page=${page.value - 1}&size=10`);
    addressList.value = response.data.content;
    totalPages.value = response.data.totalPages;
  } catch (error) {
    console.error('获取地址列表失败', error);
  }
};

// 添加地址
const addAddress = async () => {
  try {
    await axios.post('http://localhost:8080/addresses', newAddress.value);
    showAddAddressModal.value = false;
    newAddress.value = {
      receiver: '',
      phone: '',
      address: '',
      isDefault: false
    };
    fetchAddressList();
  } catch (error) {
    console.error('添加地址失败', error);
  }
};

// 删除地址
const deleteAddress = async (id) => {
  if (window.confirm('确定要删除该地址吗？')) {
    try {
      await axios.delete(`http://localhost:8080/addresses/${id}`);
      fetchAddressList();
    } catch (error) {
      console.error('删除地址失败', error);
    }
  }
};

// 编辑地址
const editAddress = (address) => {
  showEditAddressModal.value = true;
  editedAddress.value = { ...address };
};

// 更新地址
const updateAddress = async () => {
  try {
    await axios.put(`http://localhost:8080/addresses/${editedAddress.value.id}`, editedAddress.value);
    showEditAddressModal.value = false;
    fetchAddressList();
  } catch (error) {
    console.error('更新地址失败', error);
  }
};

onMounted(() => {
  fetchAddressList();
});
</script>

<style scoped>
.personal-center {
              display: flex;
            }

.sidebar {
  width: 200px;
  background-color: #f4f4f4;
  padding: 20px;
}

.sidebar ul {
  list-style: none;
  padding: 0;
}

.sidebar li {
  padding: 10px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.sidebar li.active {
  background-color: #ff6600;
  color: white;
}

.main-content {
          flex: 1;
          padding: 20px;
        }

.add-button {
         background-color: #ff6600;
         color: white;
         border: none;
         padding: 10px 20px;
         border-radius: 5px;
         float: right;
         cursor: pointer;
       }

.address-table {
             width: 100%;
             border-collapse: collapse;
             margin-top: 20px;
           }

.address-table th,
           .address-table td {
                        border: 1px solid #ccc;
                        padding: 10px;
                        text-align: left;
                      }

.edit-button {
          background-color: #ff9800;
          color: white;
          border: none;
          padding: 5px 10px;
          border-radius: 3px;
          cursor: pointer;
        }

.delete-button {
            background-color: #4caf50;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 3px;
            cursor: pointer;
            margin-left: 5px;
          }

.pagination {
  margin-top: 20px;
}

.pagination button {
  padding: 5px 10px;
  border: 1px solid #ccc;
  background-color: white;
  cursor: pointer;
}

.pagination span {
  padding: 5px 10px;
  margin: 0 5px;
  cursor: pointer;
  border: 1px solid #ccc;
}

.pagination span.active {
  background-color: #ff6600;
  color: white;
}

.modal {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
           background-color: white;
           margin: 15% auto;
           padding: 20px;
           border: 1px solid #888;
           width: 50%;
           border-radius: 5px;
         }

.close {
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>