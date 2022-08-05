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
            <button class="btn btn-sm btn-outline-dark" v-if="currentUser === username" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@mdo">정보수정</button>
            <button @click="unfollow" class="btn btn-sm btn-outline-dark" v-else-if="isFollowing === true">팔로우 취소</button>
            <button @click="follow" class="btn btn-sm btn-outline-dark" v-else>팔로우</button>
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">정보 수정</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <form @submit.prevent="passwordCheck">
                      <div class="mb-3">
                        <label for="recipient-name" class="col-form-label">비밀번호 입력:</label>
                        <input v-model="password" type="password" class="form-control" id="recipient-name" />
                      </div>
                    </form>
                  </div>
                  <div class="modal-footer d-flex justify-content-center">
                    <button type="button" class="btn btn-primary">확인</button>
                  </div>
                </div>
              </div>
            </div>
          </span>
        </div>
        <div id="user-follow">
          <span>팔로우: {{ followings }}</span> / <span>팔로워: {{ followers }}</span>
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
      currentUser: "",
      username: this.$route.params.username,
      onBoardTab: false,
      profile: {},
      isFollowing: false,
      followers: 0,
      followings: 0,
    };
  },
  methods: {
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
    },
    async unfollow() {
      await userStore.actions.unfollow(this.profile.id);
      this.isFollowing = userStore.getters.isFollowing();
      this.followers--;
    },
    passwordCheck() {
      if (this.profile.username === this.currentUser && this.profile.password === this.password) {
        router.push("useredit");
      } else {
        alert("비밀번호 오류");
        console.log("ser");
      }
    },
  },
  async created() {
    console.log("created");
    await userStore.actions.fetchCurrentUser();
    this.currentUser = userStore.getters.currentUser();
    console.log(this.currentUser);
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
