<script setup lang="ts">
import { ref } from 'vue'

const count = ref(0)
//const count = 0;
</script>

<template>
  <br>
  <div>
    <input type="text" placeholder="제목을 입력해주세요"/>
  </div>
  <div>
    <textarea rows="15" placeholder="내용을 입력해주세요"></textarea>
  </div>
  <button>작성</button>
</template>

<style scoped>

</style>