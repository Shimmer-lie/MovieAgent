<template>
  <div class="app-layout">
    <div class="sidebar">
      <div class="sidebar-content">
        <div class="logo-section">
          <div class="logo-wrapper">
            <img src="@/assets/logo.png" alt="八千代影院" />
          </div>
          <span class="logo-text">🎬 八千代影院AI</span>
        </div>

        <el-button class="new-chat-button" @click="newChat" round>
          <i class="fa-solid fa-plus"></i>
          <span>开启新会话</span>
        </el-button>
        
        <div class="sidebar-tagline">
          <span> 智能推荐</span>
          <span> 深度解析</span>
        </div>
      </div>

      <div class="sidebar-footer">
        <div class="version-info">
          <i class="fa-solid fa-compact-disc"></i>
          <span>V 1.2.0 Cine-AI</span>
        </div>
      </div>
    </div>

    <div class="main-content">
      <div class="chat-container">
        <div class="message-list" ref="messaggListRef">
          <div
            v-for="(message, index) in messages"
            :key="index"
            :class="['message-wrapper', message.isUser ? 'user-align' : 'bot-align']"
          >
            <div class="avatar">
              <i :class="message.isUser ? 'fa-solid fa-user-astronaut' : 'fa-solid fa-film'"></i>
            </div>
            
            <div :class="['message-bubble', message.isUser ? 'user-style' : 'bot-style']">
              <div class="content-text" v-html="message.content"></div>
              
              <div class="loading-dots" v-if="message.isTyping">
                <span class="dot"></span>
                <span class="dot"></span>
                <span class="dot"></span>
              </div>
            </div>
          </div>
        </div>

        <div class="input-area">
          <div class="input-wrapper">
            <el-input
              v-model="inputMessage"
              placeholder="聊聊想看的电影，或让AI推荐几部..."
              @keyup.enter="sendMessage"
              :disabled="isSending"
            >
              <template #prefix>
                <i class="fa-solid fa-magnifying-glass"></i>
              </template>
            </el-input>
            <el-button 
              @click="sendMessage" 
              :disabled="isSending || !inputMessage.trim()" 
              type="primary" 
              circle
            >
              <i class="fa-solid fa-paper-plane"></i>
            </el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch, nextTick } from 'vue'
import axios from 'axios'
import { v4 as uuidv4 } from 'uuid'

const messaggListRef = ref()
const isSending = ref(false)
const uuid = ref()
const inputMessage = ref('')
const messages = ref([])

onMounted(() => {
  initUUID()
  hello()
})

// 自动滚动到底部
watch(messages, () => {
  nextTick(() => {
    if (messaggListRef.value) {
      messaggListRef.value.scrollTo({
        top: messaggListRef.value.scrollHeight,
        behavior: 'smooth'
      })
    }
  })
}, { deep: true })

const hello = () => {
  sendRequest('你好')
}

const sendMessage = () => {
  if (inputMessage.value.trim() && !isSending.value) {
    const msg = inputMessage.value.trim()
    inputMessage.value = ''
    sendRequest(msg)
  }
}

const sendRequest = async (message) => {
  isSending.value = true
  
  if(messages.value.length > 0) {
    messages.value.push({ isUser: true, content: message, isTyping: false })
  }

  const botMsg = { isUser: false, content: '', isTyping: true }
  messages.value.push(botMsg)
  const lastMsgIndex = messages.value.length - 1

  try {
    await axios.post(
      '/api/movieai/chat', 
      { memoryId: uuid.value, message },
      {
        responseType: 'text',
        onDownloadProgress: (e) => {
          const fullText = e.event.target.responseText
          messages.value[lastMsgIndex].content = convertStreamOutput(fullText)
        },
      }
    )
  } catch (error) {
    console.error('Streaming Error:', error)
    messages.value[lastMsgIndex].content = '抱歉，放映机故障，请重试。'
  } finally {
    messages.value[lastMsgIndex].isTyping = false
    isSending.value = false
  }
}

const initUUID = () => {
  let storedUUID = localStorage.getItem('user_uuid')
  if (!storedUUID) {
    const rawUuid = uuidv4()
    let number = 0
    for (let i = 0; i < rawUuid.length && i < 6; i++) {
      number = number * 16 + (parseInt(rawUuid[i], 16) || 0)
    }
    storedUUID = number % 1000000
    localStorage.setItem('user_uuid', storedUUID)
  }
  uuid.value = storedUUID
}

const convertStreamOutput = (output) => {
  return output.replace(/\n/g, '<br>').replace(/ /g, '&nbsp;')
}

const newChat = () => {
  localStorage.removeItem('user_uuid')
  window.location.reload()
}
</script>

<style scoped>
/* 全局布局 */
.app-layout {
  display: flex;
  height: 100vh;
  background-color: #0b0f1a; /* 更深邃的背景 */
  color: #f8fafc;
  font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
  overflow: hidden;
}

/* 侧边栏：下移及美化 */
.sidebar {
  width: 280px;
  background: linear-gradient(180deg, #1e293b 0%, #0b0f1a 100%);
  display: flex;
  flex-direction: column;
  padding: 24px;
  border-right: 1px solid rgba(255, 255, 255, 0.05);
}

.sidebar-content {
  padding-top: 60px; /* 内容整体下移 */
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.logo-section {
  margin-bottom: 40px;
  text-align: center;
}

.logo-wrapper img {
  width: 130px; /* 图片放大 */
  height: 130px;
  border-radius: 28px;
  box-shadow: 0 12px 40px rgba(59, 130, 246, 0.4);
  border: 2px solid rgba(255, 255, 255, 0.1);
}

.logo-text {
  display: block;
  margin-top: 20px;
  font-size: 24px;
  font-weight: 800;
  color: #60a5fa;
  text-align: center;
  letter-spacing: 2px;
}

.new-chat-button {
  width: 100%;
  height: 48px;
  margin-top: 30px;
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
  border: none;
  color: white;
  font-weight: bold;
}

.sidebar-footer {
  margin-top: auto;
  padding-bottom: 20px;
  color: rgba(255, 255, 255, 0.2);
  font-size: 12px;
}

/* 主内容区域布局 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-image: radial-gradient(circle at top right, #1e293b, #0b0f1a);
}

.chat-container {
  max-width: 900px;
  width: 100%;
  margin: 0 auto;
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 20px;
  min-height: 0; /* 关键：防止被内容撑开 */
}

.message-list {
  flex: 1;
  overflow-y: auto;
  padding-right: 10px;
}

/* 消息气泡 */
.message-wrapper {
  display: flex;
  margin-bottom: 24px;
  animation: fadeIn 0.4s ease-out;
}

.avatar {
  width: 44px;
  height: 44px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  margin: 0 12px;
}

.user-align { flex-direction: row-reverse; }
.user-align .avatar { background: #2563eb; }
.bot-align .avatar { background: #334155; color: #fbbf24; }

.message-bubble {
  max-width: 75%;
  padding: 12px 18px;
  border-radius: 20px;
  line-height: 1.6;
}

.user-style { background: #2563eb; color: white; border-bottom-right-radius: 4px; }
.bot-style { background: rgba(30, 41, 59, 0.7); color: #e2e8f0; border-bottom-left-radius: 4px; }

/* 输入框区域优化：解决违和感 */
.input-area {
  padding: 20px 0;
  flex-shrink: 0;
}

.input-wrapper {
  background: rgba(15, 23, 42, 0.6); /* 深色透明背景 */
  backdrop-filter: blur(20px);      /* 毛玻璃效果 */
  padding: 8px 12px;
  border-radius: 30px;
  display: flex;
  align-items: center;
  border: 1px solid rgba(255, 255, 255, 0.08);
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.4);
}

/* 覆盖 Element Plus 默认样式 */
:deep(.el-input__wrapper) {
  background-color: transparent !important;
  box-shadow: none !important;
}

:deep(.el-input__inner) {
  color: #f8fafc !important;
  height: 45px;
}

:deep(.el-input__prefix-inner) {
  color: rgba(255, 255, 255, 0.4);
  font-size: 18px;
}

/* 滚动条美化 */
.message-list::-webkit-scrollbar { width: 4px; }
.message-list::-webkit-scrollbar-thumb { background: rgba(255,255,255,0.1); border-radius: 10px; }

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

@media (max-width: 768px) {
  .sidebar { display: none; }
}
</style>