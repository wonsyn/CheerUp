<template>
  <div id="login" class="container">
    <div>
      <img alt="logo" src="@/assets/logo.png" style="height: 200px" />
    </div>
    <div>
      <!-- <account-error-list v-if="authError"></account-error-list> -->
      <form @submit.prevent="login(credentials)" class="d-flex flex-column">
        <div class="input-group mb-3 mx-auto" style="width: 30%">
          <input type="text" class="form-control" v-model="credentials.username" placeholder="ID" required />
        </div>
        <div class="input-group mb-3 mx-auto" style="width: 30%">
          <input type="password" class="form-control" v-model="credentials.password" placeholder="Password" required />
        </div>
        <button type="submit" class="btn btn-primary mx-auto mb-2" style="width: 5%; font-size: 10px">로그인</button>
      </form>
      <div class="d-flex flex-column">
        <!-- <router-link class="mx-auto mb-1" to="#" style="text-decoration: none; font-size: 10px">아이디/비밀번호 찾기</router-link> -->
        <router-link class="mx-auto" to="/signup" style="text-decoration: none; font-size: 10px">회원가입</router-link>
      </div>
      <div>
        <hr />
        <div class="mx-auto" style="cursor: pointer; width: 25%" @click="test()">Google 로그인</div>
        <div class="mx-auto" style="cursor: pointer; width: 25%" @click="test()">Naver 로그인</div>
        <div class="mx-auto" style="cursor: pointer; width: 25%" @click="test()">Kakao 로그인</div>
        <div class="mx-auto" style="cursor: pointer; width: 25%" @click="test()">Twitter 로그인</div>
        <div class="mx-auto" style="cursor: pointer; width: 25%" @click="test()">Facebook 로그인</div>
      </div>
    </div>
  </div>
</template>

<script>
// import AccountErrorList from @/components/AccountErrorList
// import {mapActions, mapGetters } from 'vuex'

export default {
  name: "LoginView",
  components: {
    // AccountErrorList,
  },
  data() {
    return {
      credentials: {
        username: "",
        password: "",
      },
    };
  },
  computed: {
    // ...mapGetters(['에러']),
  },
  methods: {
    test() {
      confirm("wow");
    },
    login() {
      const msg = `ID: ${this.credentials.username} / Password: ${this.credentials.password}`;
      confirm(msg);
    },
    // ...mapActions(['로그인']),
  },
};
</script>

<style scoped></style>
