<template>
  <div class="container">
    <div class="row">
      <div class="col">
        col
        <feed-list-item></feed-list-item>
      </div>
      <div class="col">
        col
        <feed-list-item></feed-list-item>
      </div>
      <div class="col">
        col
        <feed-list-item></feed-list-item>
      </div>
    </div>
  </div>
</template>

<script>
import FeedListItem from "@/components/CommentListItem";

export default {
  name: UserScrapList,
  components: {
    FeedListItem,
  },
};
</script>

<style scoped></style>
