<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import Header from "@/components/Header.vue";
import Contents from "@/components/Contents.vue";
</script>

<template>
<!--  <el-header>-->
<!--      <RouterLink to="/">Home</RouterLink>-->
<!--      <RouterLink to="/write">글작성</RouterLink>-->
<!--  </el-header>-->

  <Header />
  <Contents />

</template>
<style>

body {
  font-family: "Pretendard Variable", Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", sans-serif;
}




</style>