<template>
    <OrderListComponent 
    :isAdmin="true" 
    :apiUrl="`/member/${id}/orders`"
     />

</template>


<script>
import OrderListComponent from '@/components/OrderListComponent.vue';
export default {
    props:['id'], // 매개변수로 데이터 넘겨받을 때 props사용
    components:{ // OrderListComponent의 컴포넌트 등록
        OrderListComponent
    }
}
</script>
