<script setup lang="ts">

import {RouterView} from "vue-router";
</script>

<template>
<el-main class="content">
  <el-row>
    <el-col>
      <RouterView />
    </el-col>
  </el-row>
</el-main>

</template>

<style scoped>
.content {
  padding: 0;
  height: calc(100vh - 60px);
}
</style>