<template>
  <div class="my-3" @click="moveDetail">
    <div class="card mx-auto" style="width: 24rem">
      <img v-if="feedImgUrl != null" :src="feedImgUrl" class="card-img-top" alt="thumbnail" style="width: 100%; max-height: 12rem; object-fit: cover" />
      <img v-else src="@/assets/logo.png" class="card-img-top" alt="thumbnail" style="width: 100%; max-height: 12rem; object-fit: cover" />
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
import router from "@/router";

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
  methods: {
    moveDetail() {
      console.log("detail", this.feedId);
      router.push({ name: "detail", params: { feedId: this.feedId } });
    },
  },
};
</script>

<style scoped></style>
