<template>
  <div>
    <div id="login" class="container">
      <div>
        <img alt="logo" src="@/assets/logo.png" style="height: 200px" />
      </div>
      <div>
        <!-- <account-error-list v-if="authError"></account-error-list> -->
        <form @submit.prevent="loginCheck" class="d-flex flex-column">
          <div class="input-group mb-3 mx-auto" style="width: 30%">
            <input type="text" class="form-control" v-model="credentials.id" placeholder="ID" />
          </div>
          <div class="input-group mb-3 mx-auto" style="width: 30%">
            <input type="password" class="form-control" v-model="credentials.password" placeholder="Password" />
          </div>
          <button type="submit" class="btn mx-auto mb-2" style="width: 12%; font-size: 15px; font-weight: bold; color: white; background-color: #00dd99">로그인</button>
        </form>
        <div class="d-flex flex-column">
          <!-- <router-link class="mx-auto mb-1" to="#" style="text-decoration: none; font-size: 10px">아이디/비밀번호 찾기</router-link> -->
          <router-link class="mx-auto" to="/signup" style="text-decoration: none; font-size: 12px">회원가입</router-link>
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
    <div class="toast-container p-5 bottom-0 end-0">
      <div id="toast-template" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header">
          <img src="@/assets/logo.png" class="rounded me-2" alt="logo.png" style="width: 10%" />
          <strong id="toast-head" class="me-auto"></strong>
          <small id="toast-small"></small>
          <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div id="toast-body" class="toast-body"></div>
      </div>
    </div>
  </div>
</template>

<script>
// import AccountErrorList from @/components/AccountErrorList
// import {mapActions, mapGetters } from 'vuex'
// import bootstrap from "bootstrap";
import { Toast } from "bootstrap";
// import { mapActions, mapGetters } from "vuex";
import useStore from "@/store/index.js";
import router from "@/router";

const store = useStore();

export default {
  name: "LoginView",
  components: {
    // AccountErrorList,
  },
  data() {
    return {
      credentials: {
        id: "",
        password: "",
      },
    };
  },
  computed: {
    // ...mapGetters(['에러']),
    checkInfo() {
      if (this.credentials.id.length == 0 || this.credentials.password.length == 0) {
        return false;
      }
      return true;
    },
  },
  methods: {
    async login() {
      await store.modules.userStore.actions.login(this.credentials);
    },
    async loginCheck() {
      if (this.checkInfo) {
        await this.login(this.credentials);
        if (store.modules.userStore.state.isLogin) {
          router.push({ name: "home" });
        } else {
          this.toast("아이디 비밀번호가 올바르지 않습니다.", "로그인 실패", "");
        }
      } else {
        this.toast("아이디 비밀번호를 올바르게 입력해 주세요.", "로그인 실패", "");
      }
    },
    toast(msg, head, small) {
      const toastWindow = document.getElementById("toast-template");
      const toastBody = document.getElementById("toast-body");
      const toastHead = document.getElementById("toast-head");
      const toastSmall = document.getElementById("toast-small");

      toastBody.innerText = msg;
      toastHead.innerText = head;
      toastSmall.innerText = small;
      const toast = new Toast(toastWindow);
      toast.show();
    },
    // ...mapActions(['로그인']),
  },
};
</script>

<style scoped>
.toast {
  background-color: #ff6e6e;
  color: white;
  font-weight: bold;
  max-width: 100%;
}
</style>
