<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px">
            <h1>상품 등록</h1>
        </div>
        <form  @submit.prevent="itemCreate">
            <div class="form-group">
                <label for="name">상품명</label>
                <input class="form-control" type="text" v-model="name"><br>
            </div>
            <div class="form-group">
                <label for="name">카테고리</label>
                <input class="form-control" type="text" v-model="category"><br>
            </div>
            <div class="form-group">
                <label for="email">가격</label>
                <input class="form-control" type="number" v-model="price"><br>
            </div>
            <div class="form-group">
                <label for="password">재고수량</label>
                <input class="form-control" type="number" v-model="stockQuantity"><br>
            </div>
            <div class="form-group">
                <label for="password">상품 이미지 : </label>
                <!-- @change와 @click의 비교 : 
                    click은 요소가 클릭될 때마다 함수 실행
                    change는 해당 태그의 값이 변경될 때 함수 실행 -->
                <input class="form-control" type="file" accept="image/*" @change="fileUpload"><br> <!--파일이 업로드되면 fileUpload 실행-->
            </div>
            <button type="submit" class="btn btn-primary edit-btn">상품등록</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data () {
        return {
            name: "",
            category: "",
            price: null,
            stockQuantity: null,
            itemImage: null,
        }
    },
    methods: {
        fileUpload(event) {
            // event.target: 이벤트가 발생한 DOM요소를 가리키는 객체
            this.itemImage = event.target.files[0]
        },
        async itemCreate() {
            // multi-part formdata형식의 경우 new FormData를 통해 객체 생성 (json형태인 memberCreate랑 비교해서 보기)
            const registerData = new FormData();
            registerData.append("name", this.name);
            registerData.append("category", this.category);
            registerData.append("price", this.price);
            registerData.append("stockQuantity", this.stockQuantity);
            registerData.append("itemImage", this.itemImage);
            const token = localStorage.getItem('token');
            const headers = {Authorization: `Bearer ${token}`} 
            await axios.post(`${process.env.VUE_APP_API_BASE_URL}/item/create`, registerData, {headers});
            this.$router.push("/items/manage");
        }
    }
}
</script>

<style lang="scss" scoped>

</style>