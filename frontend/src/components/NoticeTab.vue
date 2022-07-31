<template>
  <router-link class="nav-link dropdown-toggle dropdown-center" to="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">알림</router-link>
  <ul class="dropdown-menu dropdown-menu-end">
    <div v-for="day in dayBefore" :key="day">
      <li>{{ day }}</li>
      <li v-for="a in arr" :key="a" class="d-flex">
        <img src="@/assets/logo.png" alt="profile-img" style="height: 20px" />
        <router-link class="dropdown-item" to="#">알림 - {{ a }}</router-link>
        <span style="font-size: 10px; color: #848484">1시간 전</span>
      </li>
      <li><hr class="dropdown-divider" /></li>
    </div>
  </ul>
</template>

<script>
// import { mapGetters } from "vuex";

export default {
  name: "NoticeTab",
  components: {},
  data() {
    return {
      arr: ["김아무개 님이 새 피드 'XXX XXXX XXXXX...'를 스크랩했습니다", "정아무개님이 당신을 팔로우 하였습니다.", "2번", "3번"],
      dayBefore: ["오늘", "어제", /* "2일 전", "3일 전", "4일 전", "5일 전", "6일 전", */ "1주 전" /* "2주 전", "3주 전"*/],
    };
  },
  computed: {},
  methods: {
    // ...mapGetters(['isLoggedIn', 'currentUser']),
    // username() {
    //   return this.currentUser.username ? this.currentUser.username : 'guest'
    // },
  },
};
</script>

<style scoped>
ul {
  border-color: #00dd99;
  border-width: 3px;
}
</style>
