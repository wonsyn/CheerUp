<template>
  <div class="container my-3">
    <div class="row d-flex justify-content-center">
      <h5 v-if="scrapList === undefined || scrapList === null || scrapList.length === 0">스크랩이 없습니다.</h5>

      <feed-list-item class="col-auto" v-else v-for="feed in scrapList" :key="feed.feedId" v-bind="feed" :scrapList="scrapList"></feed-list-item>
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
      await scrapStore.actions.getScrapList(this.profile.userId);
      this.scrapList = scrapStore.getters.scrapList();
    },
    async getScrapList() {
      await scrapStore.actions.getScrapList(this.profile.userId);
      this.scrapList = scrapStore.getters.scrapList();
    },
  },
  computed: {
    currentUserId() {
      return sessionStorage.getItem("current_user_num");
    },
  },
  async created() {
    await scrapStore.actions.getScrapList(this.profile.userId);
    this.scrapList = scrapStore.getters.scrapList();
  },
  watch: {
    profile: "fetchData",
  },
};
</script>

<style scoped></style>
