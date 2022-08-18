<template>
  <div id="profile-view">
    <h3>프로필</h3>
    <div id="user-profile" class="d-flex justify-content-around">
      <div></div>
      <div id="profile-img" class="box">
        <img class="profile" :src="imgUrl" :alt="profile.userImgName" />
      </div>
      <div id="user-info" class="my-3">
        <div id="username" class="my-3">
          <h4 class="m-3">{{ profile.nickname }} ( {{ profile.id }} )</h4>
          <span>
            <router-link v-if="currentUser === username" to="/auth"><button class="btn btn-sm btn-outline-success">정보 수정</button></router-link>
            <button @click="unfollow" class="btn btn-sm btn-outline-danger" v-else-if="isFollowing === true">팔로우 취소</button>
            <button @click="follow" class="btn btn-sm btn-outline-danger" v-else>팔로우</button>
          </span>
        </div>
        <div id="user-follow">
          <button class="btn btn-outline-primary mx-1" style="cursor: pointer" @click="goFollowList('followings')">팔로잉 {{ followings }}</button>
          <button class="btn btn-outline-primary mx-1" style="cursor: pointer" @click="goFollowList('followers')">팔로워 {{ followers }}</button>
        </div>
      </div>
      <div></div>
    </div>
    <div id="user-scrap-and-board">
      <div class="d-flex justify-content-around my-5 py-3 fw-bold" style="box-shadow: 0px 5px 5px 1px lightgray">
        <div></div>
        <div id="scraptab" class="text-center px-5 py-1 fs-5" @click="clickScrapTab">스크랩</div>
        |
        <div id="boardtab" class="text-center px-5 py-1 fs-5 tabcursor" @click="clickBoardTab">보드</div>
        <div></div>
      </div>
      <div class="slide-in-right" v-if="onBoardTab == true">
        <user-board-list :profile="profile" :boardList="boardList" v-if="viewScrapsInBoard === false" @viewBoard="viewBoard"></user-board-list>
        <board-scrap-list :board="board" :profile="profile" @goBoardList="goBoardTab" v-else></board-scrap-list>
      </div>
      <div class="slide-in" v-else>
        <user-scrap-list :profile="profile"></user-scrap-list>
      </div>
    </div>
  </div>
</template>

<script>
import UserBoardList from "@/components/UserBoardList.vue";
import UserScrapList from "@/components/UserScrapList.vue";
import BoardScrapList from "@/components/BoardScrapList.vue";
import useStore from "@/store";
import router from "@/router";

const store = useStore();
const userStore = store.modules.userStore;
const boardStore = store.modules.boardStore;

export default {
  name: "ProfileView",
  components: {
    UserBoardList,
    UserScrapList,
    BoardScrapList,
  },
  data() {
    return {
      password: "",
      onBoardTab: false,
      profile: {},
      isFollowing: false,
      followers: 0,
      followings: 0,
      viewScrapsInBoard: false,
      board: {},
      imgUrl: null,
    };
  },
  methods: {
    viewBoard() {
      this.board = boardStore.getters.board();
      this.viewScrapsInBoard = true;
    },
    goFollowList(param) {
      router.push({ name: "follow", usename: this.profile.id, params: { param: param } });
    },
    clickBoardTab() {
      if (this.onBoardTab === false) {
        const boardTab = document.getElementById("boardtab");
        const scrapTab = document.getElementById("scraptab");
        this.onBoardTab = true;
        this.viewScrapsInBoard = false;
        boardTab.classList.remove("tabcursor");
        scrapTab.classList.add("tabcursor");
      }
    },
    goBoardTab() {
      this.onBoardTab = true;
      this.viewScrapsInBoard = false;
    },
    clickScrapTab() {
      if (this.onBoardTab === true) {
        const boardTab = document.getElementById("boardtab");
        const scrapTab = document.getElementById("scraptab");
        this.onBoardTab = false;
        this.viewScrapsInBoard = false;
        boardTab.classList.add("tabcursor");
        scrapTab.classList.remove("tabcursor");
      }
    },
    async follow() {
      if (userStore.getters.socket() === null || userStore.getters.socket().readyState === 3) {
        console.log("follow socket disconnected");
        await userStore.actions.connect();
      }
      await userStore.actions.follow(this.profile.id);
      this.isFollowing = userStore.getters.isFollowing();
      this.followers++;
      // 소켓 전송
      console.log("profile_id: " + this.profile.id);
      console.log("세션 아이디: " + sessionStorage.getItem("current_user"));
      // 소켓 발생 종류, 보내는 사람, 받는 사람, 피드 번호, 피드 제목
      let socketMsg = "follow," + sessionStorage.getItem("current_user") + "," + this.profile.id + ",0,0";
      // store.modules.userStore.actions.connect();
      // userStore.actions.sendMessage(socketMsg);
      // if (userStore.getters.socket().target.readyState != 1) userStore.actions.connect();
      userStore.getters.socket().onopen(socketMsg);
    },
    async unfollow() {
      await userStore.actions.unfollow(this.profile.id);
      this.isFollowing = userStore.getters.isFollowing();
      this.followers--;
    },
    passwordCheck() {
      if (this.profile.id === this.currentUser && this.profile.password === this.password) {
        router.push("useredit");
        this.password = "";
      } else {
        this.password = "";
      }
    },
    async fetchData() {
      await userStore.actions.getProfile(this.username);
      this.profile = userStore.getters.profile();
      await userStore.actions.isFollowing(this.username);
      this.isFollowing = userStore.getters.isFollowing();
      await userStore.actions.getFollowerList(this.profile.id);
      this.followers = userStore.getters.followerList()?.length;
      await userStore.actions.getFollowingList(this.profile.id);
      this.followings = userStore.getters.followingList()?.length;
      this.onBoardTab = false;
      this.viewScrapsInBoard = false;
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
    await userStore.actions.getProfile(this.username);
    this.profile = userStore.getters.profile();
    await userStore.actions.isFollowing(this.username);
    this.isFollowing = userStore.getters.isFollowing();
    await userStore.actions.getFollowerList(this.profile.id);
    this.followers = userStore.getters.followerList()?.length;
    await userStore.actions.getFollowingList(this.profile.id);
    this.followings = userStore.getters.followingList()?.length;
    this.imgUrl = this.profile.userImgUrl == null ? require("@/assets/blank_profile.png") : require("@/assets/profile_icon/profile-" + this.profile.userImgUrl + ".png");
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
.tabcursor {
  cursor: pointer;
  color: #a9a9a9;
}
</style>
