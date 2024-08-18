<script setup lang="ts">

import { ref } from 'vue'
import axios from 'axios'
import {useRouter} from "vue-router";

const title = ref('');
const contents = ref('');

const useRoute = useRouter();

const edit = () => {
    console.log(title.value);
    console.log(contents.value);

    //alert(title.value + ' ' + content.value);
    axios.post('/api/posts', {
        title: title.value,
        contents: contents.value
    }).then((response) => {
        console.log(response);
        useRoute.replace('/');
    }).catch((error) => {
        console.log(error);
    });
}
</script>

<template>
    <div>수정</div>
    <br>
    <div class="">
        <el-input type="text" v-model="title" placeholder="제목을 입력해주세요"/>
    </div>
    <br>
    <div class="d-flex">
        <el-input type="textarea" v-model="contents"  rows="15" placeholder="내용을 입력해주세요"></el-input>
    </div>
        <br>
    <div>
        <el-button type="primary" @click="edit()">작성</el-button>
    </div>
</template>

<style scoped>

</style>