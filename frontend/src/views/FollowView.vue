<template>
  <div id="follow" class="mx-auto">
    <div class="d-flex justify-content-around">
      <div class="mx-5" style="box-shadow: 0px 5px 5px 1px lightgray">
        <span style="cursor: pointer" class="px-5" @click="followListTab = 'followings'"> <strong v-if="followListTab === 'followings'">팔로잉</strong><span v-else>팔로잉</span></span>
        |
        <span style="cursor: pointer" class="px-5" @click="followListTab = 'followers'"><strong v-if="followListTab === 'followers'">팔로워</strong><span v-else>팔로워</span></span>
      </div>
      <div id="searchuser" class="nav-item me-3">
        <form @submit.prevent="autoFillInput()" class="pe-3">
          <input id="searchinput" type="text" v-model="userInput" @input="submitAutoComplete" autocomplete="off" placeholder="유저 이름" />
        </form>
        <div v-if="!!userInput == true" id="user-search-box" class="autocomplete disabled bg-light">
          <div class="d-flex justify-content-start" @click="searchUserAdd" style="cursor: pointer" v-for="(user, i) in result" :key="i">
            <img v-if="user.userImgUrl != null" :src="require(user.userImgUrl)" v-bind:alt="user.userImgName" style="height: 20px" />
            <img v-else src="@/assets/logo.png" v-bind:alt="user.userImgName" style="height: 20px" />
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
        console.log("search");
        autocomplete.classList.remove("disabled");
        this.result = this.followList
          .filter((user) => {
            const matchId = user.id.match(new RegExp("^" + this.userInput, "i"));
            const matchNickname = user.nickname.match(new RegExp("^" + this.userInput, "i"));
            if (matchId) {
              return matchId;
            }
            return matchNickname;
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
    console.log("created");
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
</style>
