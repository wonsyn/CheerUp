<template>
  <div id="profile-view">
    프로필
    <div id="user-profile" class="d-flex justify-content-around">
      <div></div>
      <div id="profile-img" class="box">
        <img class="profile" :src="profile.userImgUrl" :alt="profile.userImgName" />
      </div>
      <div id="user-info" class="my-3">
        <div id="username" class="my-3">
          <span class="m-3">{{ profile.nickname }}</span>
          <span>
            <button class="btn btn-sm btn-outline-dark" v-if="currentUser === username"><router-link to="/auth">정보 수정</router-link></button>
            <button @click="unfollow" class="btn btn-sm btn-outline-dark" v-else-if="isFollowing === true">팔로우 취소</button>
            <button @click="follow" class="btn btn-sm btn-outline-dark" v-else>팔로우</button>
          </span>
        </div>
        <div id="user-follow">
          <span @click="goFollowList('followings')">팔로우: {{ followings }}</span> / <span @click="goFollowList('followers')">팔로워: {{ followers }}</span>
        </div>
      </div>
      <div></div>
    </div>
    <div id="user-scrap-and-board">
      <div class="d-flex justify-content-around my-5 py-3 fw-bold" style="box-shadow: 0px 5px 5px 1px lightgray">
        <div></div>
        <div @click="clickScrapTab">스크랩</div>
        |
        <div @click="clickBoardTab">보드</div>
        <div></div>
      </div>
      <div v-if="onBoardTab == true">
        <user-board-list :profile="profile" :boardList="boardList"></user-board-list>
      </div>
      <div v-else>
        <user-scrap-list :profile="profile"></user-scrap-list>
      </div>
    </div>
  </div>
</template>

<script>
import UserBoardList from "@/components/UserBoardList.vue";
import UserScrapList from "@/components/UserScrapList.vue";
import useStore from "@/store";
import router from "@/router";

const store = useStore();
const userStore = store.modules.userStore;

export default {
  name: "ProfileView",
  components: {
    UserBoardList,
    UserScrapList,
  },
  data() {
    return {
      password: "",
      onBoardTab: false,
      profile: {},
      isFollowing: false,
      followers: 0,
      followings: 0,
    };
  },
  methods: {
    goFollowList(param) {
      router.push({ name: "follow", usename: this.profile.id, params: { param: param } });
    },
    clickBoardTab() {
      this.onBoardTab = true;
    },
    clickScrapTab() {
      this.onBoardTab = false;
    },
    async follow() {
      await userStore.actions.follow(this.profile.id);
      this.isFollowing = userStore.getters.isFollowing();
      this.followers++;
      console.log("follow" + store.modules.userStore.state.socket);
    },
    async unfollow() {
      await userStore.actions.unfollow(this.profile.id);
      this.isFollowing = userStore.getters.isFollowing();
      this.followers--;
    },
    passwordCheck() {
      if (this.profile.id === this.currentUser && this.profile.password === this.password) {
        console.log("success");
        router.push("useredit");
        this.password = "";
      } else {
        this.password = "";
        console.log("failed");
      }
    },
    async fetchData() {
      await userStore.actions.getProfile(this.username);
      this.profile = userStore.getters.profile();
      console.log(this.profile);
      await userStore.actions.isFollowing(this.username);
      this.isFollowing = userStore.getters.isFollowing();
      await userStore.actions.getFollowerList(this.profile.id);
      this.followers = userStore.getters.followerList()?.length;
      await userStore.actions.getFollowingList(this.profile.id);
      this.followings = userStore.getters.followingList()?.length;
      this.onBoardTab = false;
    },
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
    currentUser() {
      return sessionStorage.getItem("current_user");
    },
  },
  async created() {
    console.log("created");
    await userStore.actions.getProfile(this.username);
    this.profile = userStore.getters.profile();
    console.log(this.profile);
    await userStore.actions.isFollowing(this.username);
    this.isFollowing = userStore.getters.isFollowing();
    await userStore.actions.getFollowerList(this.profile.id);
    this.followers = userStore.getters.followerList()?.length;
    await userStore.actions.getFollowingList(this.profile.id);
    this.followings = userStore.getters.followingList()?.length;
  },
  watch: {
    $route: "fetchData",
  },
};
</script>

<style scoped>
.box {
  width: 150px;
  height: 150px;
  border-radius: 70%;
  overflow: hidden;
}
.profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
