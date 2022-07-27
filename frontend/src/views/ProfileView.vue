<template>
  <div id="profile-view">
    프로필
    <div id="user-profile">
      <div id="profile-img"></div>
      <div id="user-info">
        <div id="username"></div>
        <div id="user-follow"></div>
      </div>
    </div>
    <div id="user-scrap-and-board">
      <div id="user-board-tab">
        <user-board-list></user-board-list>
      </div>
      <div id="user-scrap-tab">
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
};
</script>

<style scoped></style>
