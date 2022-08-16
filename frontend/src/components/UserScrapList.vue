<template>
  <div class="container">
    <h3>스크랩 리스트</h3>
    <div class="row d-flex justify-content-center">
      <h5 v-if="scrapList === undefined || scrapList === null || scrapList.length === 0">스크랩이 없습니다.</h5>
      <div v-else>
        <feed-list-item class="col-auto" v-for="feed in scrapList" :key="feed.feedId" v-bind="feed"></feed-list-item>
      </div>
    </div>
  </div>
</template>

<script>
import FeedListItem from "@/components/FeedListItem";
import useStore from "@/store";
// import router from "@/router";

const store = useStore();
// const userStore = store.modules.userStore;
const scrapStore = store.modules.scrapStore;

export default {
  name: "UserScrapList",
  data() {
    return {
      scrapList: [],
    };
  },
  components: {
    FeedListItem,
  },
  props: {
    profile: Object,
  },
  methods: {
    async fetchData() {
      console.log("UserScrapList.vue created start");
      await scrapStore.actions.getScrapList(this.profile.userId);
      this.scrapList = scrapStore.getters.scrapList();
      console.log("scraplist", this.scrapList);
    },
  },
  computed: {
    currentUserId() {
      return sessionStorage.getItem("current_user_num");
    },
  },
  async created() {
    console.log("UserScrapList.vue created start");
    await scrapStore.actions.getScrapList(this.profile.userId);
    this.scrapList = scrapStore.getters.scrapList();
    console.log("scraplist", this.scrapList);
  },
  watch: {
    profile: "fetchData",
  },
};
</script>

<style scoped></style>
