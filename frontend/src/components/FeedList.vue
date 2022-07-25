<template>
  <div class="container">
    <div class="row">
      <div>FeedList</div>
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
import FeedListItem from "@/components/FeedListItem.vue";

export default {
  name: "FeedList",
  components: {
    FeedListItem,
  },
};
</script>

<style scoped></style>
