<template>
  <div class="shopping-cart">
    <h2>我的购物车</h2>
    <p class="tip">温馨提示：产品是否购买成功，以最终下单为准哦，请尽快结算</p>
    <table>
      <thead>
      <tr>
        <th><input type="checkbox" /></th>
        <th>商品名称</th>
        <th>单价</th>
        <th>数量</th>
        <th>小计</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="item in cartItems" :key="item.id">
        <td><input type="checkbox" v-model="item.selected" /></td>
        <td>{{ item.name }}</td>
        <td>{{ item.price }}元</td>
        <td>
          <button @click="decreaseQuantity(item)">-</button>
          {{ item.quantity }}
          <button @click="increaseQuantity(item)">+</button>
        </td>
        <td>{{ item.quantity * item.price }}元</td>
        <td><button @click="removeItem(item)">删除</button></td>
      </tr>
      </tbody>
    </table>
    <div class="summary">
      <span>继续购物 共 {{ totalItems }} 件商品，已选择 {{ selectedItemsCount }} 件</span>
      <span>合计：{{ totalPrice }}元</span>
      <button @click="checkout" class="checkout-button">去结算</button>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue';

export default {
  setup() {
    const cartItems = ref([
      { id: 1, name: '小米CC9 Pro', price: 2599, quantity: 1, selected: false },
      // 可以在这里添加更多商品
    ]);

    const totalItems = computed(() => cartItems.value.length);
    const selectedItemsCount = computed(() => cartItems.value.filter(item => item.selected).length);
    const totalPrice = computed(() => {
      return cartItems.value.reduce((total, item) => {
        return item.selected ? total + item.price * item.quantity : total;
      }, 0);
    });

    const increaseQuantity = (item) => {
      item.quantity++;
    };

    const decreaseQuantity = (item) => {
      if (item.quantity > 1) {
        item.quantity--;
      }
    };

    const removeItem = (item) => {
      cartItems.value = cartItems.value.filter(i => i.id !== item.id);
    };

    const checkout = () => {
      alert('结算功能尚未实现');
    };

    return {
      cartItems,
      totalItems,
      selectedItemsCount,
      totalPrice,
      increaseQuantity,
      decreaseQuantity,
      removeItem,
      checkout,
    };
  },
};
</script>

<style scoped>
.shopping-cart {
  width: 80%;
  margin: 20px auto;
  background-color: #f9f9f9;
  border: 1px solid #e8e8e8;
  border-radius: 8px;
  padding: 20px;
}

h2 {
  color: #ff6700;
  margin-bottom: 10px;
}

.tip {
  color: #666;
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th, td {
  border: 1px solid #ddd;
  padding: 10px;
  text-align: center;
}

th {
  background-color: #f2f2f2;
}

.summary {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-top: 1px solid #e8e8e8;
}

.summary span {
  color: #333;
}

.checkout-button {
  background-color: #ff6700;
  color: #fff;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 4px;
}

.checkout-button:hover {
  background-color: #e65c00;
}
</style>