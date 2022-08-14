<template v-if="getNewSocketMessage === true">
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
import { Toast } from "bootstrap";
import useStore from "@/store/index.js";

const userStore = useStore().modules.userStore;
// var getNewSocketMessage = false;
export default {
  data() {
    return {
      socket: userStore.getters.socket(),
      socketMessage: (this.socket.onmessage = function (e) {
        return e.data;
      }),
    };
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
.toast {
  background-color: #ff6e6e;
  color: white;
  font-weight: bold;
  max-width: 100%;
}
</style>
