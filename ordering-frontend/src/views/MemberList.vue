<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>회원 목록</h1></div>
        <table class="table" >
            <thead>
                <tr>
                    <th>id</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>주문수량</th>
                </tr>
            </thead>
            <tbody>
                <!-- for문 안에서 key로 유일한 식별자를 알려줘야 함-->
                <tr v-for="member in memberList" :key="member.id">
                    <td>{{member.id}}</td>
                    <td>{{member.name}}</td>
                    <td>{{member.email}}</td>
                    <!-- member 별 주문내역 조회 -->
                    <td><a :href="`/member/${member.id}/orders`">{{member.orderCount}}</a></td>
                </tr>
            </tbody>
    
        </table>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return{
            memberList: [],
        }
        
    },
    async created() {
        try {
            const token = localStorage.getItem('token');
            const headers = {Authorization: `Bearer ${token}`} 
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/members`, {headers});
            this.memberList = response.data;
        }catch(error) {
            console.log(error);
        }
        
    }
}
</script>