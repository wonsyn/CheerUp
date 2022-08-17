<template>
  <router-link class="nav-link dropdown-toggle dropdown-center" to="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">알림</router-link>
  <ul class="dropdown-menu dropdown-menu-end mt-3 me-3 px-3 scroll-box">
    <!-- <div v-for="day in dayBefore" :key="day">
      <li>{{ day }}</li> -->
    <li v-if="noticeLen == 0">알림이 없습니다.</li>
    <li v-else v-for="notice in notices" :key="notice.alarmId" class="d-flex">
      <router-link class="dropdown-item" :to="notice.alarmUrl" style="border-radius: 3px; color: black">
        <img src="@/assets/logo.png" alt="notice-img" style="height: 20px" />
        <div class="ms-2 mb-0" style="word-wrap: break-word; word-break: break-all; white-space: normal">
          {{ notice.alarmContent }}
        </div>
        <span class="ms-2" style="font-size: 10px; color: #848484">{{ dateDiff(notice) }}</span>
      </router-link>
    </li>
    <!-- </div> -->
  </ul>
</template>

<script>
import useStore from "@/store/index.js";

const notificationStore = useStore().modules.notificationStore;

export default {
  name: "NoticeTab",
  components: {},
  data() {
    return {
      arr: ["김아무개 님이 새 피드 'XXX XXXX XXXXX...'를 스크랩했습니다", "정아무개님이 당신을 팔로우 하였습니다.", "2번", "3번"],
      // dayBefore: ["오늘", "어제", /* "2일 전", "3일 전", "4일 전", "5일 전", "6일 전", */ "1주 전" /* "2주 전", "3주 전"*/],
      notices: [],
    };
  },
  computed: {
    noticeLen() {
      return this.notices.length;
    },
  },
  methods: {
    // ...mapGetters(['isLoggedIn', 'currentUser']),
    // username() {
    //   return this.currentUser.username ? this.currentUser.username : 'guest'
    // },
    dateDiff(notice) {
      const nowDate = new Date();
      const today = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate(), nowDate.getHours(), nowDate.getMinutes(), nowDate.getSeconds());
      const alarmDate = new Date(notice.alarmDateTime);
      let diff = (today.getTime() - alarmDate.getTime()) / (1000 * 60 * 60);

      if (diff > 24) {
        return parseInt(diff / 24) + "일 전";
      } else {
        let time = parseInt(diff);
        if (time == 0) return "1시간 이내";
        return time + "시간 전";
      }
    },
  },
  async created() {
    await notificationStore.actions.getNotice();
    this.notices = notificationStore.getters.getNotices();

    for (let i = 0; i < this.notices.length; i++) {
      if (this.notices[i].alarmType === "schedule") {
        const strArr = this.notices[i].alarmContent.split("@");
        console.log("strArr", strArr);

        const nowDate = new Date();
        const today = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate(), nowDate.getHours(), nowDate.getMinutes(), nowDate.getSeconds());
        const alarmDate = new Date(this.notices[i].scheduleDateTime);
        let diff = (alarmDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

        if (diff <= 7) {
          if (parseInt(diff) == 0) {
            this.notices[i].alarmContent = strArr[0] + "오늘입니다.";
            continue;
          }
          this.notices[i].alarmContent = strArr[0] + parseInt(diff) + strArr[1];
        }
      }
    }
  },
};
</script>

<style scoped>
ul {
  border-color: #00dd99;
  border-width: 2px;
}
ul.scroll-box {
  width: 700px;
  /* height: auto; */
  max-height: 500px;
  overflow-y: auto;
}
</style>
