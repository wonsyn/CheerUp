<template>
  <nav v-if="token != null" id="navbar-frame" class="navbar navbar-expand-lg px-3 mb-5" style="box-shadow: 0px 2px 7px 1px lightgray">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/" @click="goMain"><img src="@/assets/logo.png" alt="home" style="height: 50px" /></router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link id="nav-btn-main" class="nav-link" aria-current="page" to="/">메인</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/calendar" @click="outMain">달력</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/schedule" @click="outMain">일정</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/voca" @click="outMain">단어장</router-link>
          </li>
        </ul>
        <div id="searchuser" class="nav-item me-3">
          <form @submit.prevent="autoFillInput()" class="pe-3">
            <input id="searchinput" type="text" v-model="userInput" @input="submitAutoComplete" autocomplete="off" placeholder="유저 이름" />
          </form>
          <div v-if="!!userInput == true" id="user-search-box" class="autocomplete disabled bg-light">
            <div class="d-flex justify-content-start" @click="searchUserAdd" style="cursor: pointer" v-for="(user, i) in result" :key="i">
              <img v-if="user.userImgUrl != null" :src="require(user.userImgUrl)" v-bind:alt="user.userImgName" style="height: 20px" />
              <img v-else src="@/assets/logo.png" v-bind:alt="user.userImgName" style="height: 20px" />
              <span>{{ user.id }}</span>
            </div>
          </div>
        </div>
        <div class="nav-item me-3">
          <notice-tab></notice-tab>
        </div>
        <div class="nav-item">
          <router-link class="nav-link" :to="{ name: 'profile', params: { username: currentUser } }"
            ><strong>{{ currentUser }}</strong></router-link
          >
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
// import { mapGetters } from "vuex";
import NoticeTab from "@/components/NoticeTab.vue";
import useStore from "@/store";
import router from "@/router";

const store = useStore();
const userStore = store.modules.userStore;

export default {
  name: "NavBar",
  components: {
    NoticeTab,
  },
  data() {
    return {
      // isLogin: false,
      users: [],
      userInput: null,
      result: [],
      currentUser: String,
      dayBefore: ["오늘", "어제", /* "2일 전", "3일 전", "4일 전", "5일 전", "6일 전", */ "1주 전" /* "2주 전", "3주 전"*/],
      token: String,
    };
  },
  watch: {
    $route: "fetchCurrentUser",
  },
  methods: {
    fetchCurrentUser() {
      // this.isLogin = userStore.getters.isLogin();
      this.token = sessionStorage.getItem("access-token");
      console.log("acccccesssss", this.token);
      this.currentUser = sessionStorage.getItem("current_user");
    },
    async submitAutoComplete() {
      const autocomplete = document.querySelector(".autocomplete");
      console.log(autocomplete);
      await this.searchUserById();
      if (this.userInput) {
        console.log("search");
        autocomplete.classList.remove("disabled");
        // this.result = this.users.filter((user) => {
        //   return user.match(new RegExp("^" + this.userInput, "i"));
        // });
        // this.result = this.users.map((user) => {
        //   return user.id;
        // });
        this.result = this.users.slice(0, 10);
      } else {
        this.result = [];
        autocomplete.classList.add("disabled");
      }
    },
    async searchUserById() {
      await userStore.actions.searchById(this.userInput);
      this.users = userStore.getters.userList();
      console.log(this.users);
    },
    async searchUserAdd(event) {
      const searchInput = document.getElementById("searchinput");
      this.userInput = event.target.innerText;
      await this.searchUserById();
      this.result = this.users;
      searchInput.focus();
    },
    autoFillInput() {
      this.userInput = this.result[0].id;
      router.push({ name: "profile", params: { username: this.userInput } });
      console.log(router);
      this.userInput = "";
    },
    goMain() {
      const mainBtn = document.getElementById("nav-btn-main");
      mainBtn.classList.add("active");
    },
    outMain() {
      const mainBtn = document.getElementById("nav-btn-main");
      mainBtn.classList.remove("active");
    },
    replaceImg(e) {
      e.target.src = require(`@/assets/logo.png`);
    },
  },
};
</script>

<style scoped>
.navbar-main {
  display: flex;
  justify-content: space-between;
}
.navbar-element {
  display: flex;
  justify-content: space-around;
  text-align: center;
}
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
@media (max-width: 500px) {
  .dropdown-menu {
    height: 200px;
    overflow-y: auto;
  }
}
</style>
