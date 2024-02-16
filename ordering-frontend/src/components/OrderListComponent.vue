<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>주문 목록</h1></div>
        <table class="table">
            <thead>
                <th>#</th>
                <th>회원이메일</th>
                <th>주문상태</th>
                <th v-if="isAdmin === true">ACTION</th>
            </thead>
            <tbody>
                <template v-for="order in orderList" :key="order.id">
                    <tr @click="toggleOrder(order.id)" style="cursor: pointer"> 
                        <td>{{order.id}}</td>
                        <td>{{order.memberEmail}}</td>
                        <td>{{order.orderStatus}}</td>
                        <!-- click.stop : 클릭 후 다른 움직임 x -->
                        <td v-if="isAdmin === true"><button @click.stop="orderCancel(order.id)" v-if="order.orderStatus === 'ORDERED'">CANCEL</button></td>
                    </tr>
                    <tr v-if="visibleOrder.has(order.id)">
                        <td class="text-center" colspan="4">
                            <span v-for="orderItem in order.orderItems" :key="orderItem.id">
                                아이템명 : {{ orderItem.itemName }}
                                주문수량 : {{ orderItem.count }}개 
                                <br>
                            </span>
                        </td>
                    </tr>
                </template>
            </tbody>



        </table>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    props: ['isAdmin', 'apiUrl'],
    data() {
        return{
            orderList: [],
            visibleOrder: new Set(),
        }
        
    },
    async created() {
        try {
            const token = localStorage.getItem('token');
            const headers = {Authorization: `Bearer ${token}`} 
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}${this.apiUrl}`, {headers});
            this.orderList = response.data;
        }catch(error) {
            console.log(error);
        }
    },
    methods: {
        toggleOrder(orderId) { // 주문 상세 내역 토글
            if(this.visibleOrder.has(orderId)) {
                this.visibleOrder.delete(orderId);
            }else {
                this.visibleOrder.add(orderId);
            }
        },
        async orderCancel(orderId) { // 주문 취소
            if(confirm("정말 취소 하시겠습니까?")) { // confirm : 확인-true/취소-false
                try {
                    const token = localStorage.getItem('token');
                    const headers = {Authorization: `Bearer ${token}`} 
                    await axios.delete(`${process.env.VUE_APP_API_BASE_URL}/order/${orderId}/cancel`, {headers});
                    // 리로드 대신 삭제 요청한 order의 상태값 변경
                    const order = this.orderList.find(order => order.id === orderId);
                    order.orderStatus = "CANCELED";
                }catch(error) {
                console.log(error);
                alert("주문취소에 실패했습니다.");
                }
            }
            
                
            
        }
    },
}
</script>
