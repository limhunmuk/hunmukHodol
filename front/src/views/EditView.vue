<script setup lang="ts">

import {defineProps, ref} from 'vue'
import axios from 'axios'
import {useRouter} from "vue-router";

const post = ref({});
const props = defineProps({
  postId: {
    type: [Number, String],
    required: true
  }
})

axios.get('/api/posts/' + props.postId)
.then((response) => {
    console.log(response);
    post.value = response.data;
});

const useRoute = useRouter();

const edit = () => {

    axios.patch('/api/posts/' + props.postId, post.value)
      .then((response) => {
          console.log(response);
          useRoute.replace('/');
      }).catch((error) => {
          console.log(error);
      });
}
</script>

<template>
  <div style="padding-left: 10%">
    <el-row >
        <el-col>
        <el-input type="text" v-model="post.title" placeholder="제목을 입력해주세요"/>
        </el-col>
    </el-row>
    <el-row class="mt-3">
        <el-col>
        <el-input type="textarea" v-model="post.contents"  rows="15" placeholder="내용을 입력해주세요"></el-input>
        </el-col>
    </el-row>
    <el-row class="mt-3">
        <el-col class="d-flex justify-content-end">
          <el-button type="warning" @click="edit()">수정</el-button>
        </el-col>
    </el-row>
  </div>
</template>

<style scoped>

</style>