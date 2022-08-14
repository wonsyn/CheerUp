<template>
  <div class="container">
    <h3>스크랩 리스트</h3>
    <div class="row d-flex justify-content-center">
      <div class="col-auto" v-for="feed in scrapList" :key="feed.feedId" v-bind="feed">
        <feed-list-item :feed="feed"></feed-list-item>
      </div>
    </div>
  </div>
</template>

<script>
import FeedListItem from "@/components/FeedListItem";

export default {
  name: "UserScrapList",
  components: {
    FeedListItem,
  },
  props: {
    scrapList: Array,
  },
  created() {
    console.log("UserScrapList.vue created start");
  },
  watch: {},
};
</script>

<style scoped></style>
