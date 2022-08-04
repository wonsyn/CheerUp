<template>
  <div class="my-3">
    <div class="card mx-auto" style="width: 24rem">
      <img :src="feedImgUrl" class="card-img-top" alt="thumbnail" style="width: 100%; max-height: 12rem; object-fit: cover" />
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
  methods: {
    calcImgSize() {
      // var maxWidth = 382;
      // var maxHeight = 192;

      const img = new Image();
      img.src = this.feedImgUrl;
      console.log(img.width);
      console.log(img.height);

      img.width = 200;

      return img;
    },
  },
};
</script>

<style scoped></style>
