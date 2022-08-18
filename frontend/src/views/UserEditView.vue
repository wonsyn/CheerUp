<template>
  <div class="container my-5">
    <div style="font-weight: bold; font-size: 40px" @click="checkData">회원 정보 수정</div>
    <div class="row">
      <div class="col-6 pt-5" style="display: inline-block; vertical-align: middle">
        <span class="mb-2" style="font-size: 30px"><strong>프로필 아이콘</strong></span>
        <br />
        <img
          v-if="profile.userImgUrl != null"
          class="my-3 profile-icon"
          :src="require('@/assets/profile_icon/profile-' + this.profile.userImgUrl + '.png')"
          alt="profile"
          style="width: 50%; height: 50%"
        />
        <img v-else class="my-3 profile-icon" src="@/assets/blank_profile.png" alt="profile" style="width: 50%; height: 50%" />
        <div class="row">
          <div class="col-3 px-2" v-for="(path, i) in iconList" :key="i" @click="changeImg(path, i)">
            <img :src="path" alt="icons" style="width: 30%" />
          </div>
        </div>
      </div>
      <div class="col-6 mt-5 py-5">
        <div class="py-3 px-3">
          <div style="border: 1px black solid; padding: 5%; border-radius: 10px; box-shadow: 5px 5px 5px">
            <form @submit.prevent="edit" class="needs-validation">
              <div class="d-flex jusity-content-center">
                <span><strong>ID: </strong> {{ profile.id }}</span>
              </div>

              <label for="nickname" class="form-label"><strong>Nickname</strong></label>
              <div class="input-group mb-3">
                <input v-model="profile.nickname" id="nickname" type="text" class="form-control" placeholder="Nickname" aria-label="User Nickname" />
              </div>

              <label for="password" class="form-label"><strong>Password</strong></label>
              <div class="input-group mb-3">
                <input v-model="profile.password" id="password" type="password" class="form-control" placeholder="Password" aria-label="Recipient's username" />
              </div>

              <label for="password_check" class="form-label"><strong>Password Check</strong></label>
              <div class="input-group mb-3">
                <input v-model="password_check" id="password_check" type="password" class="form-control" placeholder="Password" aria-label="Recipient's username" />
              </div>
              <div class="d-flex jusity-content-center">
                <span><strong>Email: </strong> {{ profile.email }}</span>
              </div>

              <hr />
              <button type="submit" class="btn btn-primary mt-3">정보 수정</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import useStore from "@/store/index.js";
import router from "@/router";
import Swal from "sweetalert2";

const userStore = useStore().modules.userStore;

export default {
  data() {
    return {
      password_check: "",
      email_front: "",
      email_back: "",
      profile: {},
      userImgUrl: String,
      iconList: [],
    };
  },
  async created() {
    await userStore.actions.getProfile(sessionStorage.getItem("current_user"));
    this.profile = userStore.getters.profile();

    this.iconList.length = 0;
    for (let i = 0; i < 20; i++) {
      let path = require("@/assets/profile_icon/profile-" + i + ".png");
      this.iconList.push(path);
    }
  },
  methods: {
    async edit() {
      if (this.profile.password != this.password_check) {
        Swal.fire({
          icon: "error",
          text: "비밀번호를 확인해주세요!",
          confirmButtonColor: "#ee7785",
        });
      } else {
        await userStore.actions.updateuserInfo(this.profile);

        router.push({ name: "profile", params: { username: sessionStorage.getItem("current_user") } });
        Swal.fire({
          position: "center",
          icon: "success",
          title: "프로필 변경 완료!",
          showConfirmButton: false,
          timer: 900,
        });
      }
    },
    changeImg(path, i) {
      this.profile.userImgUrl = i;
    },
  },
};
</script>

<style scoped>
.profile-icon {
  width: 20px;
  height: 20px;
  border-radius: 70%;
  overflow: hidden;
}
</style>
