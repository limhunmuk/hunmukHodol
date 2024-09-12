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

  <div>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div class="title">
        <router-link :to="{name : 'read' , params : {postId: post.id}}">{{ post.title }}</router-link>
      </div>
      <div class="content">
        {{ post.contents }}
      </div>

      <div class="sub d-flex">

        <div class="category">dev</div><br>
        <div class="reg-date">24.08.24</div>
      </div>


    </li>
  </ul>
  </div>

</template>
<style scoped>

ul {
    list-style: none;
    padding: 0;
    margin-top: 20px;


  li {
    margin-bottom: 1.2rem;

    .title a {

      color: #2f4f4f;
      font-size: 1.0rem;
      font-weight: bold;
    }

    & .title a:hover {
      text-decoration: underline;
    }

    .content {
      font-size: 0.98rem;
      color: #696969;
    }

  }

}

</style>
