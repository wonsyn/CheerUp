<template>
  <div>
    <div id="userauth" class="container">
      <div>
        <img alt="logo" src="@/assets/logo.png" style="height: 200px" />
      </div>
      <div>
        <h3>비밀번호 확인</h3>
        <!-- <account-error-list v-if="authError"></account-error-list> -->
        <form @submit.prevent="passwordCheck" class="d-flex flex-column">
          <div class="input-group mb-3 mx-auto" style="width: 30%">
            <input type="password" class="form-control" v-model="credentials.password" placeholder="Password" />
          </div>
          <button type="submit" class="btn mx-auto mb-2" style="width: 12%; font-size: 15px; font-weight: bold; color: white; background-color: #00dd99">확인</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import useStore from "@/store";
import router from "@/router";

const store = useStore();
const userStore = store.modules.userStore;

export default {
  name: "AuthorizationView",
  data() {
    return {
      credentials: {
        password: "",
      },
      currentUser: "",
      myProfile: {},
    };
  },
  methods: {
    passwordCheck() {
      if (this.credentials.password === this.myProfile.password) {
        router.push({ name: "useredit" });
      }
    },
  },
  async created() {
    await userStore.actions.getProfile(sessionStorage.getItem("current_user"));
    this.myProfile = userStore.getters.profile();
  },
};
</script>

<style scoped></style>
