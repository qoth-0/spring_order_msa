import OrderList from '@/views/OrderList';
import OrderCart from '@/views/OrderCart';

// 주문 관련 경로 관리
export const orderRoutes = [ 
    
    // 주문목록
    // http:localhost:8081/orders
    {
        path: '/orders', 
        name: 'OrderList', 
        component: OrderList 
    },
    {
        path: '/cart', 
        name: 'OrderCart', 
        component: OrderCart 
    },
 
];