<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px">
            <h1>로그인</h1>
        </div>
        <!-- submit은 기본적으로 폼 제출 시 브라우저가 페이지를 새로고침하므로 해당 동작(새로고침)을 막기 위해 prevent사용 -->
        <form @submit.prevent="doLogin">
            <div class="form-group">
                <label for="email">이메일</label>
                <input class="form-control" type="email" v-model="email"><br>
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input class="form-control" type="password" v-model="password"><br>
            </div>
            <button type="submit" class="btn btn-primary edit-btn">로그인</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios';
import {jwtDecode} from 'jwt-decode';
export default {
    data () {
        return {
            email: "",
            password: "",
        }
    },
    methods: {
        // 2가지 예외 가능성 : 
        // 1) 200번대 상태값이면서 token이 비어있는 경우
        // 2) 200번태 상태값이 아닌 경우
        async doLogin() {
            try {
                const loginData = {email: this.email, password: this.password};
                const response = await axios.post(`${process.env.VUE_APP_API_BASE_URL}/doLogin`, loginData);
                const token = response.data.result.token;
                if(token) { // 로그인 성공 시 토큰과 role 정보를 로컬스토리지에 저장
                    const decoded = jwtDecode(token); 
                    console.log(decoded);
                    localStorage.setItem("token", token)
                    localStorage.setItem("role", decoded.role)
                    // this.$router.push("/"); // router는 전역에서 사용 가능 - 리로드 X
                    window.location.href = "/"; // 홈으로 새로고침 - 리로드 해줌(header상태 변경해줌)
                }else {
                    console.log("200 ok but not token"); // 디버깅 용
                    alert("Login Failed"); // 사용자에게 출력
                }
                console.log(token);
            }catch(error) {
                // error.response.data가 기본 에러메시지 구조
                // error_message는 ErrorResponseDto에서 설정한 변수명
                const error_message = error.response.data.error_message;
                if(error_message) {
                    console.log(error_message);
                    alert(error_message);
                }else {
                    console.log(error);
                    alert("Login Failed");

                }    
            }
            
        }
    },
}
</script>

<style lang="scss" scoped>

</style>