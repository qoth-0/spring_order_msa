import {createRouter, createWebHistory} from 'vue-router';
import LoginComponent from '@/views/LoginComponent'
import ItemList from '@/views/ItemList';
import BasicComponent from '@/components/BasicComponent';

// export default인 경우에는 {} 필요없고, 여러 요소가 있을 경우에는 {} 필요
import { memberRoutes } from './memberRouter';
import { orderRoutes } from './orderRouter';
import { itemRoutes } from './itemRouter';


const routes = [    
    {
        path: '/', // url 경로 지정
        name: 'HOME', // 라우터 이름
        component: ItemList // 보여줄 컴포넌트 - 사용하기 위해 상단에 import 필요
    },
    {
        path: '/login', 
        name: 'Login', 
        component: LoginComponent
    },
    {
        path: '/basic', 
        name: 'BasicComponent', 
        component: BasicComponent
    },

    // 멤버 관련 경로 추가
    ...memberRoutes, // ...(스프레드 연산자) : 배열 요소를 다른 배열요소에 합칠 때 사용
    ...orderRoutes,
    ...itemRoutes
];
const router = createRouter({
    // vue-router는 내부적으로 두가지 방식의 히스토리(뒤로가기 가능) 관리를 제공
    // createWebHistory(주로 사용), createHashHistory
    history: createWebHistory(),
    routes
});

export default router;