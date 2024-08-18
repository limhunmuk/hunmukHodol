<script setup lang="ts">

import { ref } from 'vue'
import {defineProps, onMounted} from "vue";
import axios from "axios";
const props = defineProps({
  postId: {
    type: [Number, String],
    required: true
  }
})

const post = ref({});
onMounted(() => {
  console.log(props.postId);

  axios.get('/api/posts/' + props.postId)
    .then((response) => {
        console.log(response);
        post.value = response.data;
    })
});
</script>

<template>
  <h1>{{ post.title }}</h1>
  <div>{{ post.contents }}</div>
  <div>
    <router-link :to="{name : 'edit' , params : {postId: post.id}}">수정</router-link>
  </div>


</template>

<style scoped>

</style>