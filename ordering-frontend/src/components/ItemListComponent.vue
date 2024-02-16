<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px"><h1>{{pageTitle}}</h1></div>
        <div class="d-flex justify-content-between" style="margin-top: 20px;">
            <form style="margin-bottom: 10px;" @submit.prevent="searchItems">
                <select v-model="searchType" class="form-control" style="display: inline-block; width:auto; margin-right:5px;">
                    <option value="optional">선택</option>
                    <option value="name">상품명</option>
                    <option value="category">카테고리</option>
                </select>
                <input v-model="searchValue" type="text" class="form-control" style="display: inline-block; width:auto; margin-right:5px;"/>
                <button type="submit" class="form-control" style="display: inline-block; width:auto; margin-right:5px;">검색</button>
            </form>
            <div v-if="!isAdmin">
                <button @click="addCart" class="btn btn-secondary">장바구니</button>
                <button @click="placeOrder" class="btn btn-success">주문하기</button>
            </div>
            <div v-if="isAdmin">
                <a href="/item/create"><button class="btn btn-success">상품등록</button></a>
            </div>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th v-if="!isAdmin"></th>
                    <th>제품사진</th>
                    <th>제품명</th>
                    <th>가격</th>
                    <th>재고수량</th>
                    <th v-if="!isAdmin">주문수량</th>
                    <th v-if="isAdmin">Action</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in itemList" :key="item.id"> 
                    <!-- 체크박스를 선택하면 value가 true가 담기게 됨 -->
                    <td v-if="!isAdmin"><input type="checkbox" v-model="selectedItems[item.id]"/></td>
                    <td><img :src="getItem(item.id)" style="height: 100px; width: auto;"/></td>
                    <td>{{item.name}}</td>
                    <td>{{item.price}}</td>
                    <td>{{item.stockQuantity}}</td>
                    <td v-if="!isAdmin"><input type="number" v-model="item.quantity" min="1" style="width: 60px" /></td>
                    <td v-if='isAdmin'><button @click="deleteItem(item.id)" class="btn btn-secondary">삭제</button></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>



<!-- 추후 해결 필요
1. v-model(양방향 바인딩) 사용시 검색어 입력 후 검색버튼 누르지 않아도 데이터 제한이 됨 -> 단방향으로 해결?
2. 처음 주는 데이터 많이 주기 화면크기 줄일 시 스크롤이 사라지면서 데이터가 다 안나옴
-->  



<script>
import axios from 'axios';
import { mapActions } from 'vuex';
export default {
    props: ['isAdmin', 'pageTitle'],
    data() {
        return{
            itemList: [],
            // 페이징 처리
            pageSize: 20, // 20개씩 보여줌
            currentPage: 0,
            searchType: 'optional',
            searchValue: '',
            isLastPage: false,
            isLoading: false,
            // checkbox 선택된 것 담는 객체
            selectedItems: {}
        }
    },
    created() { // 화면이 완성되기 전 호출
        this.loadItems();
    },
    // mounted : window dom객체(화면)가 생성된 이후에 실행되는 hook함수
    mounted() {
        // 무한 스크롤 : scroll 동작이 발생할 때마다 scrollPagination함수가 호출됨
        window.addEventListener('scroll', this.scrollPagination);
    },  
    methods: {
        ...mapActions(['addToCart']),
        addCart() {
            const orderItems = Object.keys(this.selectedItems)
                            .filter(key=> this.selectedItems[key] === true)
                            .map(key => {
                                const item = this.itemList.find(item => item.id == key);
                                return {itemId: item.id, name: item.name, count: item.quantity}
                            });
            
            //mutation 직접호출 방식
            // vuex에서 키밋이라는 용어는 상태변경을 위해 mutation을 호출하는 과정을 의미
            // orderItems.forEach(item => this.$store.commit('addToCart', item))
            // actions 호출 방식
            orderItems.forEach(item => this.$store.dispatch('addToCart', item))
            this.selectedItems = [];
           
        },  
        async deleteItem(itemId) {
            if(confirm("정말 삭제 하시겠습니까?")) { // confirm : 확인-true/취소-false
                try {
                    const token = localStorage.getItem('token');
                    const headers = {Authorization: `Bearer ${token}`} 
                    await axios.delete(`${process.env.VUE_APP_API_BASE_URL}/item/${itemId}/delete`, {headers});
                    window.location.reload();
                }catch(error) {
                console.log(error);
                alert("상품 삭제에 실패했습니다.");
                }
            }    
        },
        async placeOrder() {
            // 데이터 예시
            // {
            //     "1":true,
            //     "2":false,
            // }
            // Object.keys : 위의 데이터구조에서 1, 2와 같은 key값을 추출하는 메서드
            // 체크박스에서 데이터 가져오기
            const orderItems = Object.keys(this.selectedItems)
                            .filter(key=> this.selectedItems[key] === true)
                            .map(key => {
                                const item = this.itemList.find(item => item.id == key);
                                return {itemId: item.id, count:item.quantity}
                            });
            if(orderItems.length < 1) {
                alert("선택한 물건이 없습니다.")
                return;
            }

            if(!confirm(`${orderItems.length}개의 상품을 장바구니에 넣으시겠습니까?`)) {
                console.log("주문이 취소되었습니다.");
                return;
            }
            const token = localStorage.getItem('token');
            const headers = {Authorization: `Bearer ${token}`}
            try {
                if(this.getTotalQuantity < 1) {
                alert("장바구니에 물건이 없습니다.")
                return;
                }
                if(!confirm(`${this.getTotalQuantity}개의 상품을 주문하시겠습니까?`)) {
                console.log("주문이 취소되었습니다.");
                return;
                }
                await axios.post(`${process.env.VUE_APP_API_BASE_URL}/order/create`, orderItems, {headers});           
                console.log(orderItems);
                alert("주문완료 되었습니다.");
                window.location.reload();
            }catch(error) {
                
                console.log(error);
                alert("주문이 실패되었습니다.");
            }
        },
        searchItems() {
            this.itemList = [];
            this.selectedItems = [];
            this.currentPage = 0;
            this.isLastPage = false;
            this.loadItems();
        },
        scrollPagination() {
            // innerHeight : 브라우저 창의 내부 높이(뷰포트)를 픽셀단위로 변환
            // scrollY : 스크롤을 통해 Y축을 이동한 거리
            // offsetHeight : 전체 브라우저 창의 높이
            const nearBottom = window.innerHeight + window.scrollY >= document.body.offsetHeight - 500;
            if(nearBottom && !this.isLastPage && !this.isLoading) { // 마지막페이지 전까지 스크롤하단인 경우에
                this.currentPage++;
                this.loadItems();
            }
        },
        getItem(id) {
            return `${process.env.VUE_APP_API_BASE_URL}/item/${id}/image`;
        },
        async loadItems() {
            this.isLoading = true;
            try {
                // params 객체 생성 : url 추가 시 자동으로 파라미터 형식으로 보내짐
                const params = {
                    page: this.currentPage,
                    size: this.pageSize,
                    // [] : [] 안의 값을 꺼냄
                    // [this.searchType]: this.serchValue
                }
                if(this.searchType === "name") {
                    params.name = this.searchValue;
                }else if(this.searchType === "category") {
                    params.category = this.searchValue;
                }
                console.log("data호출");
                const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/items`, {params});
                const addItemList = response.data.map(item=>({...item, quantity:1}));
                // 마지막 페이지인지 확인 - 더 이상 api호출 안하도록
                if(addItemList.length < this.pageSize) {
                    this.isLastPage = true;
                }
                this.itemList = [...this.itemList, ...addItemList];
            }catch(error) {
                console.log(error);
            }
            this.isLoading = false;
        }
    },
}
</script>
