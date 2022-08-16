<template>
  <div class="container">
    <div class="row d-flex justify-content-center">
      <feed-list-item class="col-auto" v-for="feed in list" :key="feed.feedId" v-bind="feed" :scrapList="scrapList"></feed-list-item>
      <div class="px-3">
        <button class="btn btn-primary" style="width: 100%" @click="moreFeed">더보기</button>
      </div>
    </div>
  </div>
</template>

<script>
import FeedListItem from "@/components/FeedListItem.vue";
import scrapStore from "@/store/modules/Scrap";

export default {
  name: "FeedList",
  components: {
    FeedListItem,
  },
  props: {
    feedList: [],
  },
  data() {
    return {
      list: [],
      size: 20,
    };
  },
  methods: {
    async getScrapList() {
      await scrapStore.actions.getScrapList(sessionStorage.getItem("current_user_num"));
      this.scrapList = scrapStore.getters.scrapList();
    },
    moreFeed() {
      let oldsize = this.size;
      this.size = this.size + 20;
      for (let i = oldsize; i < this.size; i++) {
        this.list.push(this.feedList[i]);
        if (i == this.feedList.length - 1) {
          break;
        }
      }
    },
  },
  mounted() {},
  computed: {
    scrapList() {
      console.log("feedlist computed", scrapStore.getters.scrapList());
      return scrapStore.getters.scrapList();
    },
  },
  created() {
    for (let i = 0; i < this.size; i++) {
      this.list.push(this.feedList[i]);
      if (i == this.feedList.length - 1) {
        break;
      }
    }
    scrapStore.actions.getScrapList(sessionStorage.getItem("current_user_num"));

    console.log("FeedList.vue created start");
    console.log(this.feedList);

    console.log("FeedList.vue created end");
  },
  watch: {
    feedList() {
      console.log("FeedList.vue watch start");

      this.list.length = 0;
      for (let i = 0; i < this.size; i++) {
        this.list.push(this.feedList[i]);
      }
      console.log("FeedList.vue watch end");
    },
    scrapList: "getScrapList",
  },
};
</script>

<style scoped></style>
