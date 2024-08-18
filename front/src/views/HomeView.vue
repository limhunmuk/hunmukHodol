<script setup lang="ts">
import { ref } from 'vue'
import axios from "axios";
import {useRouter} from "vue-router";

const posts = ref([]);
const router = useRouter();

axios.get('/api/posts?page=1&size=5')
.then((response) => {
  response.data.forEach((post:any) => {
    posts.value.push(post);
  });
})
.then((error) => {
    console.log(error);
})
/**
const moveToRead = () => {
  router.push('/read');
}*/
</script>

<template>
  <div>홈</div>

  <ul>
    <li v-for="post in posts" :key="post.id">
      <div>
        <router-link :to="{name : 'read' , params : {postId: post.id}}">{{ post.title }}</router-link>
      </div>
      <div>{{ post.contents }}</div>
    </li>
  </ul>
</template>
