<template>
  <div id="login" class="d-flex justify-content-center">
    <div>
      <h1>로그인</h1>
      <img alt="logo" src="../assets/logo.png" />
    </div>
    <div>
      <!-- <account-error-list v-if="authError"></account-error-list> -->
      <form @submit.prevent="login(credentials)">
        <div class="input-group mb-3">
          <input type="text" class="form-control" v-model="credentials.username" placeholder="Username" required />
        </div>
        <div class="input-group mb-3">
          <input type="password" class="form-control" v-model="credentials.password" placeholder="Password" required />
        </div>
        <button type="submit" class="btn btn-primary">로그인</button>
      </form>
      <div>
        <p>소셜 계정으로 로그인:</p>
        <div>
          <div><img style="cursor: pointer; width: 25%" @click="test()" src="" alt="google-auth" /></div>
          <div><img style="cursor: pointer; width: 25%" @click="test()" src="" alt="naver-auth" /></div>
          <div><img style="cursor: pointer; width: 25%" @click="test()" src="" alt="kakao-auth" /></div>
          <div><img style="cursor: pointer; width: 25%" @click="test()" src="" alt="twitter-auth" /></div>
          <div><img style="cursor: pointer; width: 25%" @click="test()" src="" alt="facebook-auth" /></div>
        </div>
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

<style scoped>
div {
  border: 2px solid black;
}
</style>
