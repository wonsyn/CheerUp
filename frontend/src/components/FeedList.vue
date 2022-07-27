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
import FeedListItem from "@/components/FeedListItem.vue";

export default {
  name: "FeedList",
  components: {
    FeedListItem,
  },
  data() {
    return {};
  },
};
</script>

<style scoped></style>
