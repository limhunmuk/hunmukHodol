<script setup lang="ts">

</script>

<template>
  <el-menu mode="vertical" class="header">
    <el-menu-item index="1">
        <router-link :to="'/'">
            <span>홈</span>
        </router-link>
    </el-menu-item>

    <el-menu-item index="2">
            <router-link :to="'/write'">
                <span>글작성</span>
            </router-link>
    </el-menu-item>
  </el-menu>

</template>

<style scoped>
body {
  margin: 0;
  padding: 0;
}
.header {
  padding: 0;
  width: 300px;
}

</style>