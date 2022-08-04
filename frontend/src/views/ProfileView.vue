<template>
  <div id="profile-view">
    프로필 {{ profile }}
    <div id="user-profile" class="d-flex justify-content-around">
      <div></div>
      <div id="profile-img" class="box">
        <img class="profile" :src="profile.userImgUrl" :alt="profile.userImgName" />
      </div>
      <div id="user-info" class="my-3">
        <div id="username" class="my-3">
          <span class="m-3">{{ profile.nickname }}</span>
          <span>
            <button class="btn btn-sm btn-outline-dark" v-if="currentUser.username == username">정보수정</button>
            <button class="btn btn-sm btn-outline-dark" v-else-if="isFollowing === true">팔로우 취소</button>
            <button class="btn btn-sm btn-outline-dark" v-else>팔로우</button>
          </span>
        </div>
        <div id="user-follow"><span>팔로우: 999</span> / <span>팔로워: 999</span></div>
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
        <user-board-list></user-board-list>
      </div>
      <div v-else>
        <user-scrap-list></user-scrap-list>
      </div>
    </div>
  </div>
</template>

<script>
import UserBoardList from "@/components/UserBoardList.vue";
import UserScrapList from "@/components/UserScrapList.vue";
import useStore from "@/store";

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
      currentUser: {
        username: "user44",
        id: 1,
      },
      username: this.$route.params.username,
      onBoardTab: false,
      profile: {},
      isFollowing: false,
      followers: null,
      followings: null,
    };
  },
  methods: {
    clickBoardTab() {
      this.onBoardTab = true;
    },
    clickScrapTab() {
      this.onBoardTab = false;
    },
  },
  async created() {
    console.log("created");
    await userStore.actions.getProfile(this.username);
    this.profile = userStore.getters.profile();
    console.log(this.profile);
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
