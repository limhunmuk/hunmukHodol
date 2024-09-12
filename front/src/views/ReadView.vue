<script setup lang="ts">

import { ref } from 'vue'
import {defineProps, onMounted} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const router = useRouter();
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

const moveToEdit = () => {
  console.log('moveToEdit');
  router.push({name : 'edit' , params : {postId: post.value.id}});
}
</script>

<template>
  <div style="padding-left: 10%;">


    <el-row>
        <el-col>
          <h2 class="title">{{ post.title }}</h2>
        </el-col>
    </el-row>

    <el-row>
        <el-col>
          <div class="sub d-flex">
            <div class="category">dev</div><br>
            <div class="reg-date">24.08.24</div>
          </div>
        </el-col>
    </el-row>
    <el-row>
        <el-col>
          <div class="content">{{ post.contents }}</div>
        </el-col>
    </el-row>
    <div class="d-flex justify-content-end">
      <el-row>
        <el-col>
          <el-button type="warning" @click="moveToEdit()">수정</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped>
    .title {
        font-size: 1.6rem;
        font-weight: 600;
        color: #2c3e50;
    }
    .content {
        margin-top: 20px;
        margin-bottom: 20px;
        font-size: 0.9rem;
        color: #282828;
        white-space: break-spaces;
      line-height: 1.8;
    }

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

      .sub {
        font-size: 0.8rem;
        color: #696969;
        margin-left: 0.5rem;

        .category {
          margin-right: 0.5rem;
        }

        .reg-date {
          margin-right: 0.5rem;
        }

      }
    }
</style>