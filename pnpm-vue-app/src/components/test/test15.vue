<template>
  <div class="order-confirmation">
    <h2>确认订单</h2>
    <div class="order-info">
      <div class="address">
        <h3>收货地址</h3>
        <div class="address-details">
          <p>吴同学</p>
          <p>13333634203</p>
          <p>湖北省 黄冈市 红安县</p>
        </div>
        <button class="add-address">添加新地址</button>
      </div>
      <div class="product">
        <h3>商品及优惠券</h3>
        <div class="product-item">
          <img  alt="Product Image" class="product-image" />
          <div class="product-details">
            <p>米家互联网空调C1（一级能效）</p>
            <p>2599元 x 1</p>
            <p class="price">2599元</p>
          </div>
        </div>
      </div>
      <div class="shipping">
        <span>配送方式：包邮</span>
      </div>
      <div class="invoice">
        <span>发票：电子发票 个人 商品明细</span>
      </div>
    </div>
    <div class="order-summary">
      <div class="summary-details">
        <p>商品件数：1件</p>
        <p>商品总价：2599元</p>
        <p>活动优惠：0元</p>
        <p>优惠券抵扣：0元</p>
        <p>运费：0元</p>
        <p class="total">应付总额：2599元</p>
      </div>
    </div>
    <div class="order-actions">
      <button class="back-button">返回购物车</button>
      <button class="checkout-button">结算</button>
    </div>
  </div>
</template>

<script>
export default {
  setup() {
    // 这里可以添加组件的逻辑
  },
};
</script>

<style scoped>
.order-confirmation {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  background-color: #fff;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 4px;
  font-family: Arial, sans-serif;
  display: flex;
  flex-direction: column;
}

h2 {
  color: #ff6700;
  font-size: 24px;
  margin-bottom: 20px;
}

.order-info {
  margin-bottom: 20px;
}

.address, .product, .shipping, .invoice {
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

h3 {
  color: #333;
  font-size: 16px;
  margin-bottom: 5px;
}

p {
  color: #666;
  font-size: 14px;
}

.add-address {
  background-color: #f5f5f5;
  color: #333;
  border: 1px solid #ddd;
  padding: 5px 10px;
  border-radius: 2px;
  cursor: pointer;
  margin-top: 5px;
}

.product-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.product-image {
  width: 80px;
  height: 80px;
  margin-right: 10px;
}

.product-details {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.price {
  color: #ff6700;
  font-size: 18px;
  font-weight: bold;
}

.order-summary {
  margin-bottom: 20px;
  display: flex;
  justify-content: flex-end;
}

.summary-details p {
  margin: 5px 0;
}

.total {
  color: #ff6700;
  font-size: 18px;
  font-weight: bold;
}

.order-actions {
  display: flex;
  justify-content: space-between;
}

.back-button, .checkout-button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.back-button {
  background-color: #f5f5f5;
  color: #333;
}

.checkout-button {
  background-color: #ff6700;
  color: #fff;
}

.checkout-button:hover {
  background-color: #e65c00;
}
</style>