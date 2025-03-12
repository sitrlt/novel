<template>
  <div>
    <textarea v-model="comment.content"></textarea>
    <button @click="togglePicker">😀</button>
    <emoji-picker v-if="showPicker" v-model="comment.content" @select="addEmoji"></emoji-picker>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { Picker } from 'emoji-mart-vue';
import 'emoji-mart-vue-fast/css/emoji-mart.css';

const showPicker = ref(false);
const comment = ref({ content: '' });

function togglePicker() {
  showPicker.value = !showPicker.value;
}

function addEmoji(emoji) {
  comment.value.content += emoji.native;
  showPicker.value = false;
}
</script>