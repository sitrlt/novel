<template>
  <div class="app-container">
    <!-- 左侧导航栏 -->
    <div class="sidebar">
      <div class="user-info">
        <div class="avatar-container" v-if="reader">
          <el-upload
              ref="coverUpload"
              :show-file-list="false"
              class="avatar-uploader"
              :auto-upload="false"
              :on-change="handleCoverChange"
              accept="image/png, image/jpeg, image/webp"
          >
            <template v-if="reader.coverImage">
              <div class="cover-preview">
                <img
                    :src="reader.coverImage"
                    class="avatar"
                    alt="封面预览"
                    width="100"
                />
              </div>
            </template>
          </el-upload>
          <span class="user-name">{{ reader.username }}</span>
        </div>
        <div
            class="nav-item"
            :class="{ active: currentView === 'bookshelf' }"
            @click="showBookshelf"
        >
          <i class="el-icon-s-bookshelf"></i>
          <Reading/>
          <span>我的书架</span>
          <span class="book-count">{{ bookCount }}</span>
        </div>
        <div
            class="nav-item"
            :class="{ active: currentView === 'messages' }"
            @click="showMessages"
        >
          <i class="el-icon-s-email"></i>
          <Message/>
          <span>消息中心</span>
        </div>
        <div
            class="nav-item"
            :class="{ active: currentView === 'change-password' }"
            @click="showChangePassword"
        >
          <i class="el-icon-s-help"></i>
          <EditPen/>
          <span>修改密码</span>
        </div>
        <div
            class="nav-item"
            :class="{ active: currentView === 'profile' }"
            @click="showProfile"
        >
          <i class="el-icon-s-help"></i>
          <Location/>
          <span>个人中心</span>
        </div>
        <div
            class="nav-item"
            :class="{ active: currentView === 'problem' }"
            @click="showProblem"
        >
          <i class="el-icon-s-help"></i>
          <Setting/>
          <span>问题反馈</span>
        </div>
      </div>
    </div>
    <!-- 右侧内容区域 -->
    <div class="content">
      <!--书架详情-->
      <div v-if="currentView === 'bookshelf'" class="card">
        <div class="shelf-toggle-header">
            <div class="bookshelf-header" @click="toggleShelf('bookshelf')">
              <h2>我的书架 (<span>{{ bookCount }}</span>)</h2>
            </div>
            <div class="bookshelf-header" @click="toggleShelf('reservationShelf')">
              <h2>我的预约书架 (<span>{{ reservationBookCount }}</span>)</h2>
            </div>
          </div>
        <div class="bookshelf" :style="{ display: currentShelf === 'bookshelf'? 'block' : 'none' }">
          <div class="book-item" v-for="(book, index) in books" :key="index">
          <input type="checkbox" class="checkbox">
          <div class="book-cover">
            <img :src="book.book.coverImage" alt="Book Cover" class="cover-img">
          </div>
          <div class="book-details">
            <div class="book-title">{{ book.book.title }}</div>
            <div class="book-author">{{ book.book.author }}</div>
            <div class="book-read-status">{{ getStatus(book.borrowRecord) }}</div>
          </div>
          <button class="return-button" :disabled="book.borrowRecord.status === '已归还'" @click="returnBook(index)">归还
          </button>
          <div >
            <el-dialog
                width="50%"
                title="逾期提醒"
                v-model="isOverdueDialogVisible"
                center
            >
              <p>您有书籍已逾期，请尽快归还。</p>
            </el-dialog>
          </div>
        </div>
          </div>
        <div class="reservation-shelf" :style="{ display: currentShelf ==='reservationShelf'? 'block' : 'none' }">
          <div class="book-item" v-for="(reservationBook, index) in reservationBooks" :key="index.id">
            <input type="checkbox" class="checkbox">
            <div class="book-cover">
              <img :src="reservationBook.book?.coverImage" alt="Book Cover" class="cover-img">
            </div>
            <div class="book-details">
              <div class="book-title">{{ reservationBook.book?.title }}</div>
              <div class="book-author">{{ reservationBook.book?.author }}</div>
              <div class="book-read-status">{{ reservationBook.status }}</div>
            </div>
            <button class="return-button" @click="cancelReservation(index)">取消预约</button>
          </div>
        </div>

      </div>
      <!--信息中心-->
      <div v-if="currentView === 'messages'" class="card">
        <div class="message-header">
          <span :class="{ 'active-tab': currentTab === '系统消息' }" @click="changeTab('系统消息')">系统消息</span>
          <span :class="{ 'active-tab': currentTab === '活动通知' }" @click="changeTab('活动通知')">活动通知</span>
          <button class="clear-button" @click="clearMessages">清空</button>
        </div>
        <div class="message-list" v-if="currentTab === '系统消息'">
          <div v-if="messages.length > 0" v-for="(message, index) in messages" :key="index" >
            <div class="message-item" v-if="message.status === '已处理'">
            <div class="message-icon">
              <span><Star/></span>
            </div>
            <div class="message-content">
              <span class="message-title">{{ message.targetEntity === 'admin'? '管理员反馈通知' : '出版社反馈通知' }}</span>
              <span class="message-time">{{ message.responseDate }}</span>
            </div>
              <div class="message-text-wrapper">
                <p class="message-text">{{ message.adminResponse }}</p>

                <el-button @click="showMessageDetails(message)" class="return-button" style="margin-right: 48px">查看详情</el-button>
              </div>
            </div>
          </div>
          <div v-else class="empty-message">
            <img src="https://cdn-icons-png.flaticon.com/512/261/261563.png" alt="empty-icon"
                 style="width: 80px; height: 80px; margin-bottom: 10px;">
            <p>还没有任何消息哦</p>
          </div>
          <el-dialog
              v-model="dialogVisible"
              title="反馈详情"
              width="40%"
          >
            <div v-if="currentMessage">
              <p><strong>反馈对象：</strong>{{ currentMessage.targetEntity === 'admin'? '管理员' : '出版社' }}</p>
              <p><strong>回复时间：</strong>{{ currentMessage.responseDate }}</p>
              <p><strong>反馈类型：</strong>{{ currentMessage.feedbackType }}</p>
              <p><strong>问题反馈：</strong>{{ currentMessage.content }}</p>
              <p><strong>回复内容：</strong>{{ currentMessage.adminResponse }}</p>
            </div>
            <template #footer>
              <el-button @click="dialogVisible = false">关闭</el-button>
            </template>
          </el-dialog>

        </div>
      </div>
      <div v-if="currentView === 'change-password'">
        <el-container class="card">
          <el-main>
            <h2>修改密码</h2>
            <el-form-item label="邮箱号">
              <el-input v-model="tableform.email" placeholder="请输入邮箱号" @blur="checkEmail" required/>
              <span class="s" v-if="show1">{{ errormessage }}</span>
            </el-form-item>
            <el-form-item label="验证码">
              <el-row>
                <el-col :span="16">
                  <el-input v-model="tableform.code" placeholder="验证码"/>
                </el-col>
                <el-col :span="8" class="code-col">
                  <el-button class="code-button" @click="sendVerificationCode">
                    {{ isWaiting ? `重新获取 ${remainingTime} 秒` : '获取验证码' }}
                  </el-button>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="tableform.password" placeholder="设置新密码" show-password :show-password="false "
                        @blur="checkPwd"/>
              <span class="s" v-if="show">{{ message }}</span>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :style="{ backgroundColor: '#f8bbd0', border: 'none' }" @click="onSubmit">确定
              </el-button>
            </el-form-item>
          </el-main>
        </el-container>

      </div>
      <div v-if="currentView === 'profile'" class="personal-center">
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
              <span v-if="hoveredItem === 'username' && !isEditing.username" class="edit-span"
                    @click="editField('username')"><Edit/>编辑</span>
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
              <span v-if="hoveredItem === 'email' && !isEditing.email" class="edit-span" @click="editField('email')"><Edit/>编辑</span>
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
              <span v-if="hoveredItem === 'phone' && !isEditing.phone" class="edit-span" @click="editField('phone')"><Edit/>编辑</span>
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
              <span class="edit-span" v-if="hoveredItem === 'address' && !isEditing.address"
                    @click="editField('address')"><Edit/>编辑</span>
            </div>
            <!-- 爱好 -->
            <div class="data-item" @mouseenter="hoveredItem = 'interests'" @mouseleave="hoveredItem = null">
              <span>爱好: </span>
              <el-tag v-for="tag in reader.interests" :key="tag.label">{{tag.label}}</el-tag>
              <span @click="isEditDialogVisible = true"  class="edit-span" v-if="hoveredItem === 'interests' && !isEditing.username"><Edit/>编辑</span>
              <el-dialog v-model="isEditDialogVisible" title="编辑爱好" >
                <div>
                  <h3>已有爱好</h3>
                  <el-tag v-for="(interest, index) in reader.interests" :key="interest.id" closable @close="deleteInterest(interest.id)">
                    {{ interest.label }}
                  </el-tag>
                </div>
                <div>
                  <h3>所有标签类型</h3>
                  <el-tag v-for="(label, index) in allLabels" :key="index" @click="addInterest(label.id)">
                    {{ label.label}}
                  </el-tag>
                </div>
                <template #footer>
                  <el-button @click="isEditDialogVisible = false">关闭</el-button>
                </template>
              </el-dialog>
            </div>

            <div class="data-item">
                <span>注册日期: </span>
                <span>{{ reader.registrationDate }}</span>
              </div>
            </div>
          </div>
        </div>
      <div v-if="currentView === 'problem'">
        <div class="card">
          <h2>提交问题反馈</h2>
          <form @submit.prevent="submitFeedback">
            <div class="form-group">
              <label for="targetEntity">反馈对象</label>
              <select id="targetEntity" v-model="feedback.targetEntity">
                <option value="admin">管理员</option>
                <option value="publisher">出版社</option>
              </select>
            </div>
            <div class="form-group" v-if="feedback.targetEntity === 'publisher'">
              <label for="bookTitle">书籍名称</label>
              <select id="bookTitle" v-model="selectedBookTitle">
                <option v-for="book in books" :value="book.book.title">{{ book.book.title }}</option>
              </select>
            </div>
            <div class="form-group">
              <label for="feedbackType">反馈类型</label>
              <input type="text" id="feedbackType" v-model="feedback.feedbackType" :placeholder="getFeedbackTypePlaceholder()" />
            </div>
            <div class="form-group">
              <label for="description">具体描述</label>
              <textarea id="description" v-model="feedback.content" required></textarea>
            </div>
            <button class="return-button" type="submit">提交反馈</button>
          </form>
        </div>
      </div>
    </div>
    </div>
</template>

<script setup>
import {nextTick, onMounted, ref, watch} from 'vue';
import axios from "axios";
import {Edit, EditPen, Location, LocationFilled, Message, Plus, Reading, Setting, Star} from '@element-plus/icons-vue';
import {ElMessage, ElMessageBox} from "element-plus";

const currentView = ref('bookshelf');
const bookCount = ref();
const books = ref([]);
const reservationBooks = ref([]);
const reservationBookCount = ref(0);
const reader = ref({});
const currentShelf = ref('bookshelf');
function getUserIdFromSessionStorage() {
  const role ='reader'; // 假设读者角色标识为'reader'
  const storageKey = `sessionUserId_${role}`;
  return sessionStorage.getItem(storageKey);
}

// 在需要获取用户 ID 的地方调用该函数
const id = getUserIdFromSessionStorage();

if (id) {
  console.log('当前登录读者的用户 ID:', id);
  // 在这里可以进行后续操作，比如根据用户 ID 进行数据请求等
} else {
  console.log('未获取到用户 ID，可能用户未登录或会话已过期');
}
const isOverdueDialogVisible = ref(false);
const getData = () => {
  axios.get(`http://localhost:8080/borrowRecord/findById/${id}`)
      .then((response) => {
        books.value = response.data;

        console.log(books.value)
        bookCount.value = books.value.length
        // 遍历 books 数组，检查 borrowRecord 的状态
        for (const book of books.value) {
          if (book.borrowRecord && book.borrowRecord.status === '已逾期') {
            isOverdueDialogVisible.value = true;
            break; // 找到一个逾期记录就跳出循环
          }
        }
      })
      .catch((error) => {
        console.error("请求出错:", error);
        // 处理错误，例如显示错误信息或采取其他措施
      });

}
const showBookshelf = () => {
  currentView.value = 'bookshelf';
};
const showMessages = () => {
  currentView.value = 'messages';
};
const showChangePassword = () => {
  currentView.value = 'change-password';
};
const showProfile = () => {
  currentView.value = 'profile';
};
const showProblem = () => {
  currentView.value = 'problem';

}
onMounted(() => {
  console.log(id)
  getReader();
  getData()
  getMessages()
  getLabel()
  fetchReservationBooks()
});

const getReader = () => {
  axios.get(`http://localhost:8080/reader/findById/${id}`)
      .then((response) => {
        reader.value = response.data;
        console.log(reader.value)
      })
      .catch((error) => {
        console.error("请求出错:", error);
        // 处理错误，例如显示错误信息或采取其他措施
      });
}
//修改密码界面
const tableform = ref({})
const show = ref(false)
const show1 = ref(false)
const message = ref(``)
const errormessage = ref('');
const isWaiting = ref(false);
const remainingTime = ref(0);
let timerId = null;
const checkEmail = () => {
  const emailRegex = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
  if (!emailRegex.test(tableform.value.email)) {
    show1.value = true
    errormessage.value = '邮箱号错误！！'
  } else {
    errormessage.value = '';
  }
}
const checkPwd = () => {
  // 使用正则表达式验证数字长度为10
  const numberRegex = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*[\W_]).{8,16}$/

  if (!numberRegex.test(tableform.value.password)) {
    show.value = true
    message.value = '要求8-16位，至少包含数字，字母，字符两种元素'
  } else {
    message.value = '';
  }
}
const sendVerificationCode = async () => {
  const loginData = {
    email: tableform.value.email,
  };
  console.log(loginData.email)
  try {
    // 发送请求到后端，请求后端发送验证码到指定邮箱
    const response = await axios.post('http://localhost:8080/reader/forget', loginData);
    // 请求成功，收到验证码
    const verificationCode = response.data.verificationCode;
    tableform.verificationCode = verificationCode;
    console.log('验证码已发送至邮箱');
    isWaiting.value = true;
    remainingTime.value = 60;

    timerId = setInterval(() => {
      remainingTime.value--;
      if (remainingTime.value === 0) {
        clearInterval(timerId);
        isWaiting.value = false;
      }
    }, 1000);
  } catch (error) {
    console.error('发送验证码失败', error);
  }
}
const onSubmit = () => {
  const loginData = {
    email: tableform.value.email,
    password: tableform.value.password,
    code: tableform.value.code
  };
  console.log(loginData);
  axios.post('http://localhost:8080/reader/update', loginData).then((response) => {
    if (response.data === true) {
      ElMessage({type: 'success', message: '修改成功!',})
    } else {
      ElMessage({type: 'error', message: '验证码错误，请重新输入!',})
    }
  })
}

//个人中心界面
const hoveredItem = ref(null);
const isEditing = ref({
  username: false,
  email: false,
  phone: false,
  address: false,
});
const editData = reader;
// 使用 ref 来获取 DOM 元素
const usernameInput = ref(null);
const emailInput = ref(null);
const phoneInput = ref(null);
const addressInput = ref(null);
const editField = (field) => {
  isEditing.value[field] = true;
  nextTick(() => {
    const inputRef = {
      username: usernameInput,
      email: emailInput,
      phone: phoneInput,
      address: addressInput,
    }[field].value;
    if (inputRef) {
      inputRef.focus();
    }
  });
};
const saveField = async (field) => {
  console.log(reader.value)
  // 发送请求保存数据
  try {
    const response = await axios.post('http://localhost:8080/reader/save', {
      id: reader.value.id, // 假设用户名是唯一标识
      [field]: editData.value[field]
    });
    console.log('Data saved successfully:', response.data);
    reader.value[field] = editData.value[field];
    isEditing.value[field] = false;
  } catch (error) {
    console.error('Error saving data:', error);
  }
};

// 个人爱好
// 所有标签类型
const allLabels = ref([]);
const getLabel = () => {
  // 发送请求获取数据
  axios.get("http://localhost:8080/label/findAll").then((response) => {
    allLabels.value = response.data;
    console.log(allLabels.value)
  }).catch((error) => {
    console.error("请求出错:", error);
    // 处理错误，例如显示错误信息或采取其他措施
  })
}
// 控制编辑弹窗显示状态
const isEditDialogVisible = ref(false);

// 删除爱好
const deleteInterest = (id) => {
  axios.delete(`http://localhost:8080/interests/labelId/${id}`)
      .then(() => {
        reader.value.interests = reader.value.interests.filter(interest => interest.id!== id);
        ElMessage({type: 'success', message: '完成删除！',})
      })
      .catch((error) => {
         ElMessage.error('删除爱好失败，请稍后重试');
      });
};

// 添加爱好
const addInterest = (labelId) => {
  const labelObj = allLabels.value.find((label) => label.id === labelId);
  const label = labelObj ? labelObj.label : '';
  const isExist = reader.value.interests.some((interest) => interest.label === label);

  const interest = {
    labelId: labelId,
    readerId: id
  };
  if (!isExist) {
    axios
        .post('http://localhost:8080/interests/create', interest)
        .then((response) => {
           getReader()
          console.log(response.data)
          ElMessage({ type: 'success', message: '添加成功!' });
        })
        .catch((error) => {
          ElMessage.error('添加失败');
          console.error('请求出错:', error);
        });
  }else{
    ElMessage.error('已添加，请添加其他');
  }
};

//修改图片
const uploadFile = () => {
  return new Promise((resolve, reject) => {
    if (!reader.value.coverFile) {
      ElMessage.error('请先选择图片');
      reject(new Error('未选择图片'));
      return;
    }
    // 创建 FormData 并上传文件
    const formData = new FormData();
    formData.append('file', reader.value.coverFile);
    axios.post('http://localhost:8080/book/upload-cover', formData)
        .then((response) => {
          ElMessage.success('图片上传成功');
          reader.value.coverImage = response.data; // 更新为后端返回的图片路径
          resolve();
        })
        .catch((error) => {
          ElMessage.error('图片上传失败');
          reject(error);
        });
  });
};
const handleCoverChange = (file) => {
  const validTypes = ['image/jpeg', 'image/png', 'image/webp'];
  const fileType = file.raw.type;
  const fileExtension = file.name.split('.').pop().toLowerCase();

  if (!validTypes.includes(fileType) && !['jpg', 'jpeg', 'png', 'webp'].includes(fileExtension)) {
    ElMessage.error('仅支持 JPG/PNG/WEBP 格式');
    return false;
  }

  if (file.size / 1024 / 1024 > 2) {
    ElMessage.error('图片大小不能超过2MB');
    return false;
  }

  // 显示图片预览
  const reader1 = new FileReader();
  reader1.onload = (e) => {
    reader.value.coverImage = e.target.result; // Base64 数据
  };
  reader1.readAsDataURL(file.raw);

  // 保存文件对象，用于后续上传
  reader.value.coverFile = file.raw;
  if (reader.value.coverFile) {
    // 如果有新的图片文件，则先上传图片
    uploadFile().then(() => {
      // 确保图片上传成功后再发送修改请求
      axios.put(`http://localhost:8080/reader/uid/${reader.value.id}`, reader.value)
          .then((response) => {
            ElMessage({type: 'success', message: '修改成功!'});
            getData(); // 重新获取数据
          })
          .catch((error) => {
            ElMessage.error('修改失败');
            console.error("请求出错:", error);
          });
    }).catch((error) => {
      ElMessage.error('图片上传失败，无法修改数据');
      console.error(error);
    });
  }

};


// 获取当前日期的函数
const getCurrentDate = () => {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};
const returnBook = (index) => {
  const book = books.value[index];
  if (book.borrowRecord.status === '借阅中' || book.borrowRecord.status === '已逾期' ) {
    const updatedBorrowRecord = {...book.borrowRecord, status: '已归还', returnDate: getCurrentDate()};
    axios.put(`http://localhost:8080/borrowRecord/uid/${book.borrowRecord.id}`, updatedBorrowRecord)
        .then((response) => {
          ElMessage({
            type: 'success',
            message: '归还成功!'
          });
          book.borrowRecord.status = '已归还'; // 更新前端状态
        })
        .catch((error) => {
          ElMessage.error('归还失败');
          console.error("请求出错:", error);
        });
  }else if(book.borrowRecord.status === '待处理'){
    ElMessage.error('该书籍未借阅成功');
  } else {
    ElMessage.error('该书籍已归还');
  }
};
// 根据借阅记录计算状态的函数
const getStatus = (borrowRecord) => {
  if (borrowRecord.status === '已归还') {
    return '已归还';
  }else if(borrowRecord.status === '待处理'){
    return '待处理';
  }else if(borrowRecord.status === '已逾期'){
    return '已逾期';
  }
  const currentDate = getCurrentDate();
  const dueDate = borrowRecord.dueDate;
  if (currentDate > dueDate && borrowRecord.status === '借阅中') {
    const updatedBorrowRecord = {...borrowRecord, status: '已逾期'};
    const response = axios.put(`http://localhost:8080/borrowRecord/uid/${borrowRecord.id}`, updatedBorrowRecord);
    return '已逾期';
  }

  return '借阅中';
};

//删除预约
const deleteReservation = (row) => {
  axios.delete(`http://localhost:8080/reservation/delete/${row}`)
      .then(()=>{
        ElMessage({type: 'success', message: '取消预约成功!'});
      }).catch((error)=>{
    console.error("请求出错:", error);
  })
}
const fetchReservationBooks = () => {
  axios.get(`http://localhost:8080/reservation/findById/${id}`)
      .then((response) => {
        reservationBooks.value = response.data;
        console.log(reservationBooks.value)
        reservationBookCount.value = reservationBooks.value.length
        console.log( reservationBooks.value)
      })
      .catch((error) => {
        console.error("请求出错:", error);
      });
};


const cancelReservation = (index) => {
  const reservationBook = reservationBooks.value[index].book;
  const id = reservationBooks.value[index].id
  ElMessageBox.confirm(
      '您确定要取消预约名为【' + reservationBook.title + '】的书籍吗?', '危险操作',
      {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning',}
  )
      .then(() => {
        deleteReservation(id)
        const newItems = [...reservationBooks.value];
        newItems.splice(index, 1);
        reservationBooks.value = newItems;
        reservationBookCount.value = newItems.length
      })
      .catch(() => {
        ElMessage({type: 'info', message: '取消删除!',})
      })
};
const toggleShelf = (shelfType) => {
  currentShelf.value = shelfType;
};
//问题反馈
const feedback = ref({
  readerId: id,
  content: '',
  feedbackType:'',
  targetEntity:''
});
console.log(feedback.value)
// 用于存储当前选中的书籍书名
const selectedBookTitle = ref('');

// 监听 selectedBookTitle 的变化，更新反馈类型
watch(selectedBookTitle, (newValue) => {
  if (newValue && feedback.value.targetEntity === 'publisher') {
    feedback.value.feedbackType = `关于书籍《${newValue}》的反馈`;
  }
});

// 根据反馈对象返回不同的占位符
const getFeedbackTypePlaceholder = () => {
  if (feedback.value.targetEntity === 'publisher') {
    return `请输入关于书籍《${selectedBookTitle.value}》的反馈类型`;
  }
  return '请输入反馈类型';
};
const submitFeedback = async () => {
  try {
    const response = await axios.post('http://localhost:8080/feedback/create', feedback.value);
    feedback.value = '';
    ElMessage({
      type: 'success',
      message: '反馈提交成功!'
    });
    // 清空表单
  } catch (error) {
    ElMessage.error('反馈提交失败');
    console.error("请求出错:", error);
  }
};


//消息中心
const currentTab = ref('系统消息');
// 消息列表
const messages = ref([]);
// 当前页码
const page = ref(1);

// 切换选项卡的方法
const changeTab = (tab) => {
  currentTab.value = tab;
};

const getMessages = () => {
  axios.get(`http://localhost:8080/feedback/findById/${id}`)
      .then((response) => {
        messages.value = response.data;
        console.log(response)
      })
      .catch((error) => {
        console.error("请求出错:", error);
        // 处理错误，例如显示错误信息或采取其他措施
      });
}
const dialogVisible = ref(false);
const currentMessage = ref(null);

const showMessageDetails = (message) => {
  currentMessage.value = message;
  dialogVisible.value = true;
};
// 清空消息的方法
const clearMessages = () => {
  ElMessageBox.confirm(
      '确定要将 系统消息 清空吗？', '危险操作',
      {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning',}
  )
      .then(() => {
        deleteAll()
        ElMessage({type: 'success', message: '完成删除！',})
      })
      .catch(() => {
        ElMessage({type: 'info', message: '取消删除!',})
      })
};

const deleteAll = () => {
  axios.delete(`http://localhost:8080/feedback/delete/${id}`)
      .then(() => {
        messages.value = [];
      })
      .catch((error) => {
        console.error("请求出错:", error);
        // 处理错误，例如显示错误信息或采取其他措施
      });
}

</script>

<style scoped>
.app-container {
  display: flex;
  min-height: 100vh;
}

.sidebar {
  width: 200px;
  background-color: #f5f5f5;
  padding: 20px;
}

.user-info {
  text-align: center;
}

.avatar-container {
  background-color: #f8bbd0;
  border-radius: 10px;
  padding: 20px;
  margin-bottom: 20px;
}

.avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  margin-bottom: 10px;
}

.user-name {
  color: white;
  font-size: 16px;
  display: block;
}

.user-id {
  color: white;
  font-size: 14px;
  display: block;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 10px 0;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.nav-item.active {
  color: #f8bbd0;
  font-weight: bold;
}

.
.nav-item i {
  margin-right: 10px;
}

.book-count {
  background-color: #f8bbd0;
  color: white;
  border-radius: 50%;
  padding: 3px 7px;
  margin-left: 10px;
}


.shelf-toggle-header {
  border-radius: 5px;
  padding: 10px;
  margin: 10px;
  cursor: pointer;
  display: flex;
}

.bookshelf-header,
.reservation-shelf-header {
  margin-right: 100px;
}

.bookshelf-header h2 {
  font-size: 20px;
  margin: 0;
}

.book-item {
  display: flex;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #e0e0e0;
}

.checkbox {
  margin-right: 10px;
}

.book-cover {
  width: 60px;
  height: 80px;
  position: relative;
  margin-right: 10px;
}

.cover-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.book-details {
  flex: 1;
}

.book-title {
  font-size: 16px;
  margin-bottom: 5px;
}

.book-author {
  font-size: 14px;
  color: #666;
  margin-bottom: 5px;
}

.book-read-status {
  font-size: 14px;
  color: red;
}

.content {
  flex-grow: 1;
  padding: 20px;
}

.personal-center {
  display: flex;
  align-items: center;
}

.card {
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 98%;
}

.data-item {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
}

.el-tag {
  margin-left: 10px;
  color: #f8bbd0;
  background-color: #f9f4f8;
  border: 1px solid #f8bbd0; /* 修改边框颜色为橙色 */
}
.data-item span:first-child {
  margin-right: 10px;
  white-space: nowrap;
}
.data-item span {
  font-weight: bold;
}

.edit-span {
  margin-left: 10px;
  display: inline-flex; /* 确保按钮内容在同一行 */
  align-items: center; /* 垂直居中对齐 */
  color: #f8bbd0;
}

.edit-span svg {
  margin-right: 2px; /* 添加一些间距 */
  width: 18px; /* 调整图标大小 */
  height: 18px;
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

.return-button {
  margin-left: 10px;
  padding: 5px 10px;
  background-color: #f8bbd0;
  color: white;
  border: none;
  border-radius: 2px;
  cursor: pointer;
}

.return-button:hover {
  background-color: #f585c3;

}

.return-button:disabled {
  background-color: #cccccccc;
  cursor: not-allowed;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group select,
.form-group textarea {
  width: 85%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #f9f4f8;

}
.form-group input{
  width: 84%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #f9f4f8;

}

.form-group textarea {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  background-color: #f9f4f8;
}


.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.active-tab {
  border-bottom: 2px solid #f8bbd0;
  color: #f8bbd0;
}

.clear-button {
  background-color: transparent;
  border: 1px solid #ccc;
  border-radius: 17px;
  padding: 5px 15px;
  cursor: pointer;
  color: #f8bbd0;
}

.message-list {
  margin-bottom: 20px;
}

.message-item {
  border-bottom: 1px solid #eaeaea;
  padding: 15px 0;
}

.message-icon {
  display: inline-block;
  width: 20px;
  height: 20px;
  margin-right: 10px;
}

.message-content {
  display: inline-block;
  vertical-align: top;
  width: calc(100% - 30px);
}

.message-title {
  font-weight: bold;
}

.message-time {
  margin-left: 740px;
  color: #999;
}

.message-text {
  margin-left: 30px;

}

.view-details {
  color: green;
  float: right;
}

.empty-message {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  color: #999;
}

.nav-item svg {
  margin-right: 3px; /* 添加一些间距 */
  width: 18px; /* 调整图标大小 */
  height: 18px;
}

.message-text-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.book-container {
  display: flex;
  flex-direction: column;
}
.bookshelf,
.reservation-shelf {
  flex: 1;
  border-radius: 5px;
  padding: 10px;
  margin: 10px;
}

</style>