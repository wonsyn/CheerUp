<template>
  <div class="container">
    <h3>스크랩 리스트</h3>
    <div class="row">
      <div class="col">
        <feed-list-item></feed-list-item>
      </div>
      <div class="col">
        <feed-list-item></feed-list-item>
      </div>
      <div class="col">
        <feed-list-item></feed-list-item>
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
};
</script>

<style scoped></style>
