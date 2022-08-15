<template>
  <div class="container">
    <h3>스크랩 리스트</h3>
    <div class="row d-flex justify-content-center">
      {{ scrapList }}
      <h5 v-if="scrapList === undefined || scrapList === null || scrapList.length === 0">스크랩이 없습니다.</h5>
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
    console.log(this.scrapList);
  },
  watch: {},
};
</script>

<style scoped></style>
