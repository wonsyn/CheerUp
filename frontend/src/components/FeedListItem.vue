<template>
  <div class="my-3">
    <div class="card mx-auto" style="width: 18rem">
      <img :src="feedImgUrl" class="card-img-top" alt="..." />
      <div class="card-body">
        <p class="card-text">{{ feedTitle }}</p>
      </div>
      <div class="card-footer d-flex justify-content-between">
        <div>{{ feedSource }}</div>
        <div><i class="fas fa-bookmark"></i><i class="fal fa-bookmark"></i></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "FeedListItem",
  props: {
    feedAuthor: String,
    feedCategory: String,
    feedContent: String,
    feedDate: Date,
    feedId: Number,
    feedImgName: String,
    feedImgUrl: String,
    feedSource: String,
    feedTitle: String,
    feedType: Number,
    feedUrl: String,
  },
  components: {},
  setup() {},
};
</script>

<style scoped></style>
