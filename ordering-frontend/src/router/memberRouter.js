// 컴포넌트 import
import MemberCreate from '@/views/MemberCreate'
import MemberList from '@/views/MemberList';
import MemberOrders from '@/views/MemberOrders';
import MyPage from '@/views/MyPage';

// 회원 관련 경로 관리 -> url을 검색 시 보여줄 화면(component) 맵핑
export const memberRoutes = [ 
    
    // 회원가입
    // http://localhost:8081/member/create
    {
        path: '/member/create',  
        name: 'MemberCreate', 
        component: MemberCreate 
    },
    // 회원목록
    {
        path: '/members', 
        name: 'MemberList', 
        component: MemberList 
    },
    // 회원별 주문조회
    {
        path: '/member/:id/orders', 
        name: 'MemberOrders', 
        component: MemberOrders,
        props: true
    },
    // 마이페이지 
    {
        path: '/mypage', 
        name: 'MyPage', 
        component: MyPage
    },
];