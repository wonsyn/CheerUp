<template>
  <nav-bar></nav-bar>
  <router-view />
  <main-footer></main-footer>
  <toast-view></toast-view>
</template>
<script>
import NavBar from "@/components/layout/HeaderNavBar.vue";
import MainFooter from "@/components/layout/MainFooter.vue";
import ToastView from "@/components/layout/ToastView.vue";
import { Toast } from "bootstrap";
import useStore from "@/store/index.js";

const userStore = useStore().modules.userStore;

export default {
  data() {
    return {
      socket: userStore.getters.socket(),
      // socketMessage: (this.socket.onmessage = function (e) {
      //   return e.data;
      // }),
    };
  },
  components: {
    NavBar,
    MainFooter,
    ToastView,
  },
  created() {
    // setInterval(() => {
    //   // this.getSocketMessage();
    //   console.log("timeout", this.socketMessage);
    // }, 3000);
    // this.getSocketMessage();
  },
  computed: {
    // socketMessage() {
    //   console.log("#####computed####", userStore.getters.socketMessage());
    //   return userStore.getters.socketMessage();
    // },
    getSocketMessage() {
      console.log("###computed####", this.socketMessage);
      if (userStore.getters.socketMessage() === null) return null;
      return this.getSocketMessage2();
      // return this.socketMessage;
      // return this.toast("1", "2", "3");
      // return userStore.getters.socketMessage();
      // return userStore.getters.socketMessage;
      // return this.socket === null ? null : this.socket.onmessage();
    },
  },
  watch: {
    // socketMessage: function () {
    //   console.log("###watch####", this.socketMessage);
    //   this.toast("1", "2", "3");
    // },
    getSocketMessage(val) {
      console.log("##watch##", val);
      console.log("###watch####", this.socketMessage);
      this.toast("1", "2", "3");
    },
  },
  methods: {
    getSocketMessage2() {
      this.socketMessage = userStore.getters.socketMessage();
      console.log("###methods####: ", this.socketMessage);
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
