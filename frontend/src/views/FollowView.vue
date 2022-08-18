<template>
  <div id="follow" class="mx-auto" style="min-width: 660px">
    <div class="d-flex justify-content-around">
      <div class="mx-5 d-flex btn-group" role="group" aria-label="Basic radio toggle button group">
        <input @click="followListTab = 'followings'" type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked />
        <label class="btn btn-outline-primary" for="btnradio1">
          <strong v-if="followListTab === 'followings'">팔로잉</strong>
          <div v-else>팔로잉</div></label
        >

        <input @click="followListTab = 'followers'" type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off" />
        <label class="btn btn-outline-primary" for="btnradio2">
          <strong v-if="followListTab === 'followers'">팔로워</strong>
          <div v-else>팔로워</div></label
        >
      </div>
      <div id="searchuser" class="nav-item me-3">
        <form @submit.prevent="autoFillInput()" class="pe-3">
          <input id="searchinput" type="text" v-model="userInput" @input="submitAutoComplete" autocomplete="off" placeholder="유저 이름" />
        </form>
        <div v-if="!!userInput == true" id="user-search-box" class="autocomplete disabled bg-light">
          <div class="d-flex justify-content-start" @click="searchUserAdd" style="cursor: pointer" v-for="(user, i) in result" :key="i">
            <img class="profile-icon" v-if="user.userImgUrl != null" :src="require(user.userImgUrl)" v-bind:alt="user.userImgName" style="height: 20px" />
            <img class="profile-icon" v-else src="@/assets/blank_profile.png" v-bind:alt="user.userImgName" style="height: 20px" />
            <span>{{ user.nickname }}({{ user.id }})</span>
          </div>
        </div>
      </div>
    </div>
    <div style="background-color: #ffffee; width: 75%; border-radius: 30px" class="my-3 mx-auto py-5 px-3">
      <div v-for="user in followList" :key="user.userId">
        <follow-list-item :user="user"></follow-list-item>
      </div>
    </div>
  </div>
</template>

<script>
import FollowListItem from "@/components/FollowListItem.vue";
import router from "@/router";
import useStore from "@/store";

const store = useStore();
const userStore = store.modules.userStore;

export default {
  name: "FollowView",
  components: {
    FollowListItem,
  },
  data() {
    return {
      userId: sessionStorage.getItem("current_user_id"),
      profile: {},
      username: "",
      followList: [],
      followListTab: "",
      userInput: "",
      result: [],
    };
  },
  methods: {
    async fetchData() {
      this.username = this.$route.username;
      await userStore.actions.getProfile(this.username);
      this.profile = userStore.getters.profile();
      if (this.followListTab === "followers") {
        await userStore.actions.getFollowerList(this.profile.id);
        this.followList = userStore.getters.followerList();
      } else {
        await userStore.actions.getFollowingList(this.profile.id);
        this.followList = userStore.getters.followingList();
      }
    },
    submitAutoComplete() {
      const autocomplete = document.querySelector(".autocomplete");
      if (this.userInput) {
        autocomplete.classList.remove("disabled");
        this.result = this.followList
          .filter((user) => {
            const matchId = user?.id.match(new RegExp("^" + this.userInput, "i"));
            const matchNickname = user?.nickname.match(new RegExp("^" + this.userInput, "i"));
            if (matchId) {
              return matchId;
            }
            return matchNickname;
          })
          .sort(function (a, b) {
            var nameA = a.id.toUpperCase(); // ignore upper and lowercase
            var nameB = b.id.toUpperCase(); // ignore upper and lowercase
            if (nameA < nameB) {
              return -1;
            }
            if (nameA > nameB) {
              return 1;
            }

            // 이름이 같을 경우
            return 0;
          })
          .slice(0, 10);
      } else {
        this.result = [];
        autocomplete.classList.add("disabled");
      }
    },
    searchUserAdd(event) {
      const searchInput = document.getElementById("searchinput");
      this.userInput = event.target.innerText;
      searchInput.focus();
    },
    autoFillInput() {
      this.userInput = this.result[0].id;
      router.push({ name: "profile", params: { username: this.userInput } });
      this.userInput = "";
    },
  },
  async created() {
    this.username = this.$route.username;
    await userStore.actions.getProfile(this.username);
    this.profile = userStore.getters.profile();
    this.followListTab = this.$route.params.param;
    if (this.followListTab === "followers") {
      await userStore.actions.getFollowerList(this.profile.id);
      this.followList = userStore.getters.followerList();
    } else {
      await userStore.actions.getFollowingList(this.profile.id);
      this.followList = userStore.getters.followingList();
    }
  },
  watch: {
    followListTab: "fetchData",
  },
};
</script>

<style scoped>
form {
  outline: none;
  background-color: #00dd99;
  border-radius: 15px;
}
input {
  outline: none;
  border-width: 0;
  border-radius: 15px;
  background: transparent;
  padding-left: 1rem;
}
input::placeholder {
  color: white;
}
input[type="text"] {
  color: white;
}
#user-search-box {
  position: absolute;
  margin-left: auto;
  margin-right: auto;
}
.profile-icon {
  width: 20px;
  height: 20px;
  border-radius: 70%;
  overflow: hidden;
}
</style>
