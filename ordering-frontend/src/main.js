import { createApp } from 'vue'
import App from './App.vue' 
import router from '@/router/index'
import '@/assets/css/bootstrap.min.css'
import axios from 'axios'; 
import store from './store/cart.js'

// index.html의 id가 app인 태그에 마운트가 되도록하는 코드
// createApp(App).mount('#app') 
const app = createApp(App);

// 401 응답의 경우 interceptor를 통해 공통적으로 토큰 제거 후 로그아웃 처리 - 프로그램 전체에서 토큰만료 시 적용
axios.interceptors.response.use(response => response, error => {
    if (error.response && error.response.status === 401){
        localStorage.clear();
        window.location.href = "/login";
    }
    return Promise.reject(error); // 401 외 나머지는 무시하겠다.
})

// vue애플리케이션에서 전역적으로 기능을 사용할 경우에 아래와 같이 use 사용
app.use(router);
app.use(store);
app.mount('#app');
