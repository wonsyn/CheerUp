<template>
  <div id="profile-view">
    프로필
    <div id="user-profile" class="d-flex justify-content-around">
      <div id="profile-img" class="box">
        <img class="profile" src="@/assets/logo.png" alt="profile-img" />
      </div>
      <div id="user-info">
        <div id="username">
          <span>{{ username }}</span>
          <span>
            <button v-if="currentUser.username == username">정보수정</button>
            <button v-else>팔로우</button>
          </span>
        </div>
        <div id="user-follow"><span>팔로우: 999</span> / <span>팔로워: 999</span></div>
      </div>
    </div>
    <div id="user-scrap-and-board" class="d-flex justify-content-around">
      <div id="user-board-tab">
        보드
        <user-board-list></user-board-list>
      </div>
      <div id="user-scrap-tab">
        스크랩
        <user-scrap-list></user-scrap-list>
      </div>
    </div>
  </div>
</template>

<script>
import UserBoardList from "@/components/UserBoardList.vue";
import UserScrapList from "@/components/UserScrapList.vue";

export default {
  name: "ProfileView",
  components: {
    UserBoardList,
    UserScrapList,
  },
  data() {
    return {
      currentUser: {
        username: "user1",
        id: 1,
      },
      username: this.$route.params.username,
    };
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
