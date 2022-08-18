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
      <div id="toast-body" @click="move" class="toast-body"></div>
    </div>
  </div>
</template>
<script>
import NavBar from "@/components/layout/HeaderNavBar.vue";
import MainFooter from "@/components/layout/MainFooter.vue";
import { Toast } from "bootstrap";
import useStore from "@/store/index.js";
import router from "@/router";

const userStore = useStore().modules.userStore;

export default {
  data() {
    return {
      socket: userStore.getters.socket(),
      socketMessage: "",
      url: "",
      arr: [],
    };
  },
  components: {
    NavBar,
    MainFooter,
  },
  // created() {
  //   if (sessionStorage.getItem["access-token"] != null) {
  //     userStore.actions.connect();
  //     console.log("APP: ", userStore.getters.socket());
  //   }
  // },
  methods: {
    getSocketMessage() {
      this.socketMessage = userStore.getters.socketMessage();
      console.log("###methods####: ", this.socketMessage);
      this.arr = this.socketMessage.split("%%%");
      userStore.mutations.SET_SOCKET_URL(this.arr[5]);
      if (this.arr[0] === "follow") this.toast(this.arr, "팔로우 알림!!", "");
      else if (this.arr[0] === "scrap") this.toast(this.arr, "스크랩 알림!!", "");
      else if (this.arr[0] === "comment_like") this.toast(this.arr, "댓글 좋아요 알림!!", "");
      else if (this.arr[0] === "schedule") {
        // 날짜 계산 필요
        console.log("here: ", this.arr[6]);
        let today = new Date();
        today.setHours(today.getHours() + 9);
        const date1 = new Date(this.arr[6]);
        const date2 = new Date(today.toISOString().substring(0, 10));
        const diffDate = date1.getTime() - date2.getTime();
        const day = diffDate / (1000 * 60 * 60 * 24);

        const tmp = this.arr[4].split("@");
        if (day == 0) this.arr[4] = tmp[0] + " 오늘입니다.";
        //  신한은행 신한은행 면접일정이 일정이 오늘입니다.
        //  신한은행 신한은행 면접일정이 일정이 오늘입니다.
        else this.arr[4] = tmp[0] + day + tmp[1];
        this.toast(this.arr, "등록 일정 알림!!", "");
      }
    },
    toast(msg, head, small) {
      const toastWindow = document.getElementById("toast-template");
      const toastBody = document.getElementById("toast-body");
      const toastHead = document.getElementById("toast-head");
      const toastSmall = document.getElementById("toast-small");

      toastBody.innerText = msg[4];
      // toastBody.innerHTML = "<router-link to='" + msg[5] + "'>" + msg[4] + "</router-link>";
      toastHead.innerText = head;
      toastSmall.innerText = small;
      const toast = new Toast(toastWindow);
      toast.show();
    },
    move() {
      console.log("들어오나");
      router.push(userStore.getters.socketUrl());
    },
  },
};
</script>

<style>
@font-face {
  font-family: "RIDIBatang";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.0/RIDIBatang.woff") format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: "InfinitySans-RegularA1";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-RegularA1.woff") format("woff");
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: "NanumSquareRound";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff") format("woff");
  font-weight: normal;
  font-style: normal;
}
#app {
  font-family: NanumSquareRound, Helvetica, Arial, sans-serif;
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
