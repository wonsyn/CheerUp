<template>
  <div class="container">
    <div class="row d-flex justify-content-center">
      <feed-list-item class="col-auto" v-for="feed in list" :key="feed.feedId" v-bind="feed"></feed-list-item>
      <div class="px-3">
        <button v-if="hasMore" class="btn btn-primary" style="width: 100%" @click="moreFeed">더보기</button>
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
  props: {
    feedList: [],
  },
  data() {
    return {
      list: [],
      size: 20,
      hasMore: true,
    };
  },
  mounted() {},
  created() {
    for (let i = 0; i < this.size; i++) {
      this.list.push(this.feedList[i]);
      if (i == this.feedList.length - 1) {
        this.hasMore = false;
        break;
      }
    }
    console.log(this.list);
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
  },
  methods: {
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
};
</script>

<style scoped></style>
