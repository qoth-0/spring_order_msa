<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px">
            <h1>회원가입</h1>
        </div>
        <form @submit.prevent="memberCreate">
            <div class="form-group">
                <label for="name">이름</label>
                <input class="form-control" type="text" v-model="name"><br>
            </div>
            <div class="form-group">
                <label for="email">이메일</label>
                <input class="form-control" type="email" v-model="email"><br>
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input class="form-control" type="password" v-model="password"><br>
            </div>
            <div class="form-group">
                <label for="password">도시</label>
                <input class="form-control" type="text" v-model="city"><br>
            </div>
            <div class="form-group">
                <label for="password">상세주소</label>
                <input class="form-control" type="text" v-model="street"><br>
            </div>
            <div class="form-group">
                <label for="password">우편번호</label>
                <input class="form-control" type="text" v-model="zipcode"><br>
            </div>
            <button type="submit" class="btn btn-primary edit-btn">가입완료</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data () {
        return {
            name: "",
            email: "",
            password: "",
            city: "",
            street: "",
            zipcode: "",
        }
    },
    methods: {
        async memberCreate() {
            try {
                const registerData = {
                    name: this.name,
                    email: this.email, 
                    password: this.password,
                    city: this.city,
                    street: this.street,
                    zipcode: this.zipcode
                };
                await axios.post(`${process.env.VUE_APP_API_BASE_URL}/member/create`, registerData);
                // window.location.href="/login";
                this.$router.push({name: 'Login'});
            }catch(error) {
                const error_message = error.response.data.error_message;
                if(error_message) {
                    console.log(error_message);
                    alert(error_message);
                }else {
                    console.log(error);
                    alert("입력값 확인 필요");

                }    
            }
            
        }
    },
}
</script>

