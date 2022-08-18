<template>
  <div class="w-75 mx-auto">
    <div class="d-flex justify-content-between align-items-center mt-3">
      <router-link :to="{ name: 'profile', params: { username: user.id } }" class="d-flex text-decoration-none text-dark">
        <img class="profile-icon" v-if="user.userImgUrl != null" :src="require(user.userImgUrl)" v-bind:alt="user.userImgName" style="height: 20px" />
        <img class="profile-icon" v-else src="@/assets/blank_profile.png" v-bind:alt="user.userImgName" style="height: 20px" />
        <strong class="align-bottom">{{ user.nickname }}</strong>
        <span>( {{ user.id }})</span>
      </router-link>
      <div v-if="currentUser != user.id">
        <button v-if="!isFollowing" @click="toggleFollow" class="btn btn-sm btn-outline-danger"><img src="@/assets/heart_outline.png" style="height: 20px" /></button>
        <button v-else @click="toggleFollow" class="btn btn-sm btn-outline-danger"><img src="@/assets/heart_fill.png" style="height: 20px" /></button>
      </div>
    </div>
    <hr class="py-0 my-0" />
  </div>
</template>

<script>
import useStore from "@/store";
// import router from "@/router";

const store = useStore();
// const boardStore = store.modules.boardStore;
// const scrapStore = store.modules.scrapStore;
const userStore = store.modules.userStore;

export default {
  name: "FollowListItem",
  data() {
    return {
      isFollowing: Boolean,
      currentUser: sessionStorage.getItem("current_user"),
    };
  },
  props: {
    user: Object,
  },
  methods: {
    async follow() {
      await userStore.actions.follow(this.user.id);
      this.isFollowing = userStore.getters.isFollowing();
      console.log("follow" + store.modules.userStore.state.socket);
    },
    async unfollow() {
      await userStore.actions.unfollow(this.user.id);
      this.isFollowing = userStore.getters.isFollowing();
    },
    async toggleFollow() {
      console.log("follow/unfollow");
      if (this.isFollowing === true) {
        console.log("is following", this.user);
        await this.unfollow();
      } else {
        console.log("is not following", this.user);
        await this.follow();
      }
    },
  },
  async created() {
    console.log("user", this.user);
    await userStore.actions.isFollowing(this.user.id);
    this.isFollowing = userStore.getters.isFollowing();
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
