<template>
  <!-- HTML태그에 변수를 넣어 사용할 경우 {{  }}로 사용 -->
  <h1>hello world {{myLang}}</h1>
  <input type="text" value="myvalue"/>
  <!-- :value는 v-bind:value= 의 축약어 -->
  <!-- v-bind는 input박스에서 많이 사용하는 단방향 데이터 바인딩 방식 -->
  <input type="text" :value="inputvalue1"/>
  <!-- v-model은 양방향 데이터 바인딩 모델이라는 차이점 -->
  <input type="text" v-model="inputvalue2"/>
  <!-- @click : onclick과 같음 -->
  <button @click="showValue">변수 변경사항 확인</button>
  <!-- computed 예제 -->
  <div>
    <h2>{{count}}</h2>
    <button @click="increment">increment</button>
  </div>
  <div>
    <h2>{{doubleCount}}</h2>
  </div>
</template>

<script>
export default {
  data() {
    return{
      myLang: "python",
      inputvalue1: "python2",
      inputvalue2: "python3",
      count: 0,
    }
  },
  // vue생명주기에서 인스턴스가 생성되는 시점을 created라고 함
  // -> 화면이 열리기 전에 실행되는 함수
  created() {
    this.myLang = "java"
  },
  // computed는 종속된 반응형 데이터(count)가 변경될 때만 함수를 다시 실행하여 값을 계산하는 계산 함수
  computed: {
    doubleCount() {
      return this.count * 2;
    }
  },
  methods: {
    increment() {
      this.count++;
    },
    showValue() {
      alert(this.inputvalue2);
    }
  }
  // axios를 써서 서버에 data요청 -> result => mylang = result.lang;
}
</script>
