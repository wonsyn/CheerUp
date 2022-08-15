<template>
  <nav-bar></nav-bar>
  <router-view />
  <main-footer></main-footer>
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
</template>
<script>
import NavBar from "@/components/layout/HeaderNavBar.vue";
import MainFooter from "@/components/layout/MainFooter.vue";
import { Toast } from "bootstrap";
import useStore from "@/store/index.js";

const userStore = useStore().modules.userStore;

export default {
  data() {
    return {
      socket: userStore.getters.socket(),
      socketMessage: "",
    };
  },
  components: {
    NavBar,
    MainFooter,
  },
  methods: {
    getSocketMessage() {
      this.socketMessage = userStore.getters.socketMessage();
      console.log("###methods####: ", this.socketMessage);
      if (this.socketMessage.charAt(0) === "a") this.toast(this.socketMessage.substring(1), "팔로우 알림!!", "");
      else if (this.socketMessage.charAt(0) === "b") this.toast(this.socketMessage.substring(1), "스크랩 알림!!", "");
      else if (this.socketMessage.charAt(0) === "c") this.toast(this.socketMessage.substring(1), "댓글 좋아요 알림!!", "");
    },
    toast(msg, head, small) {
      const toastWindow = document.getElementById("toast-template");
      const toastBody = document.getElementById("toast-body");
      const toastHead = document.getElementById("toast-head");
      const toastSmall = document.getElementById("toast-small");

      // toastBody.innerText = msg;
      toastBody.innerHTML = msg;
      toastHead.innerText = head;
      toastSmall.innerText = small;
      const toast = new Toast(toastWindow);
      toast.show();
    },
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

.toast {
  background-color: #ff6e6e;
  color: white;
  font-weight: bold;
  max-width: 100%;
}
</style>
