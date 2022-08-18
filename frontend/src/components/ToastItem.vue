<template>
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

export default {
  name: "ToastItem",
  methods: {
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

<style scoped></style>
