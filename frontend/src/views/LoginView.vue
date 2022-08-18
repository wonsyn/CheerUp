<template>
  <div style="min-height: 60vh">
    <div style="height: 10vh"></div>
    <div id="login" class="container my-5">
      <div class="my-5">
        <h4>A부터 Z까지 책임지는 당신의 취업 전문비서</h4>
        <img alt="logo" src="@/assets/logo.png" style="height: 200px" />
      </div>

      <div>
        <form @submit.prevent="loginCheck" class="d-flex flex-column">
          <div class="input-group mb-3 mx-auto" style="width: 30%">
            <input type="text" class="form-control" v-model="credentials.id" placeholder="ID" />
          </div>
          <div class="input-group mb-3 mx-auto" style="width: 30%">
            <input type="password" class="form-control" v-model="credentials.password" placeholder="Password" />
          </div>
          <button type="submit" class="btn mx-auto mb-2" style="width: 12%; font-size: 16px; font-weight: bold; color: white; background-color: #00dd99">로그인</button>
        </form>
        <div class="d-flex flex-column">
          <router-link class="mx-auto" to="/signup" style="text-decoration: none; font-size: 14px">회원가입</router-link>
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
import { Toast } from "bootstrap";
import useStore from "@/store/index.js";
import router from "@/router";

const store = useStore();

export default {
  name: "LoginView",
  data() {
    return {
      credentials: {
        id: "",
        password: "",
      },
    };
  },
  computed: {
    checkInfo() {
      if (this.credentials.id.length == 0 || this.credentials.password.length == 0) {
        return false;
      }
      return true;
    },
  },
  created() {
    if (sessionStorage.getItem("access-token") != null) {
      router.push({ name: "home" });
    }
  },
  methods: {
    async login() {
      await store.modules.userStore.actions.login(this.credentials);
    },
    async loginCheck() {
      if (this.checkInfo) {
        await this.login(this.credentials);
        if (sessionStorage.getItem("access-token") != null) {
          store.modules.userStore.actions.connect();
          console.log("LoginView(78): " + store.modules.userStore.getters.socket());
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
