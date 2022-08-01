<template>
  <nav id="navbar-frame" class="navbar navbar-expand-lg px-3 mb-5" style="box-shadow: 0px 2px 7px 1px lightgray">
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
            <router-link class="nav-link" to="#" @click="outMain">달력</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="#" @click="outMain">일정</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="#" @click="outMain">단어장</router-link>
          </li>
        </ul>
        <div id="searchuser" class="nav-item dropdown me-3">
          <form @submit.prevent="autoFillInput(result[0])" class="dropdown-toggle pe-3" data-bs-toggle="dropdown" data-bs-auto-close="outside" aria-expanded="false">
            <input type="text" v-model="userInput" @input="submitAutoComplete" placeholder="유저 이름" />
          </form>
          <ul v-if="!!userInput == true" id="user-search-box" class="dropdown-menu autocomplete disabled">
            <li class="dropdown-item">결과</li>
            <li class="dropdown-item" @click="searchUserAdd" style="cursor: pointer" v-for="(res, i) in result" :key="i">
              <img src="@/assets/logo.png" alt="profile-img" style="height: 20px" />
              <span>{{ res }}</span>
            </li>
          </ul>
        </div>
        <div class="nav-item me-3">
          <notice-tab></notice-tab>
        </div>
        <div class="nav-item"><router-link class="nav-link" :to="{ name: 'profile', params: { username: 'user1' } }">유저 프로필</router-link></div>
      </div>
    </div>
  </nav>
</template>

<script>
// import { mapGetters } from "vuex";
import NoticeTab from "@/components/NoticeTab.vue";

export default {
  name: "NavBar",
  components: {
    NoticeTab,
  },
  data() {
    return {
      users: ["장진세", "장원석", "정제희", "윤원상", "연창모", "BongMyeong-dong", "DeokMyeong-Dong", "CheerUpChUp", "JangDae-Dong", "Ajax", "Algorithm", "Vuejs Vuex", "Java Spring"],
      userInput: null,
      result: [],
      dayBefore: ["오늘", "어제", /* "2일 전", "3일 전", "4일 전", "5일 전", "6일 전", */ "1주 전" /* "2주 전", "3주 전"*/],
    };
  },
  computed: {},
  methods: {
    // ...mapGetters(['isLoggedIn', 'currentUser']),
    // username() {
    //   return this.currentUser.username ? this.currentUser.username : 'guest'
    // },
    submitAutoComplete() {
      const autocomplete = document.querySelector(".autocomplete");
      if (this.userInput) {
        autocomplete.classList.remove("disabled");
        this.result = this.users.filter((user) => {
          return user.match(new RegExp("^" + this.userInput, "i"));
        });
      } else {
        this.result = [];
        autocomplete.classList.add("disabled");
      }
    },
    searchUserAdd(event) {
      this.userInput = event.target.innerText;
    },
    autoFillInput() {
      this.userInput = this.result[0];
    },
    goMain() {
      const mainBtn = document.getElementById("nav-btn-main");
      mainBtn.classList.add("active");
    },
    outMain() {
      const mainBtn = document.getElementById("nav-btn-main");
      mainBtn.classList.remove("active");
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
