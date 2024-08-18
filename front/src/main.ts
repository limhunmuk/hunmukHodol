import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

const app = createApp(App)

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import "bootstrap/dist/css/bootstrap-utilities.css"

app.use(createPinia())
app.use(router)
app.use(ElementPlus)

app.mount('#app')
