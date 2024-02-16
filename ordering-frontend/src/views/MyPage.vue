<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>회원 정보</h1></div>
        <table class="table" >
            <tr>
                <td>이름</td><td>{{member.name}}</td>
            </tr>
            <tr>
                <td>email</td><td>{{member.email}}</td>
            </tr>
            <tr>
                <td>도시</td><td>{{member.city}}</td>
            </tr>
            <tr>
                <td>상세주소</td><td>{{member.street}}</td>
            </tr>
            <tr>
                <td>우편번호</td><td>{{member.zipcode}}</td>
            </tr>
    
        </table>
    </div>
    <OrderListComponent
    :isAdmin="false"
    apiUrl="/member/myorders"
     />
</template>

<script>
import OrderListComponent from '@/components/OrderListComponent.vue';
import axios from 'axios';
export default {
    components:{ // OrderListComponent의 컴포넌트 등록
        OrderListComponent
    },
    data() {
        return{
            member: {},
        }
        
    },
    created() {
        this.fetchMember();
    },
    methods: {
        async fetchMember() {
            try {
            const token = localStorage.getItem('token');
            const headers = {Authorization: `Bearer ${token}`} 
            const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/member/myInfo`, {headers});
            this.member = response.data;
            }catch(error) {
                console.log(error);
            }       
        }
    },
}

</script>
