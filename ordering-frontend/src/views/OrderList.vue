<template>
    <OrderListComponent 
    :isAdmin="true" 
    apiUrl="/orders"
     />
</template>

<script>
import OrderListComponent from '@/components/OrderListComponent.vue';
export default {
    components:{ // OrderListComponent의 컴포넌트 등록
        OrderListComponent
    }
    
}
</script>
