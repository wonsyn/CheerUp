<template>
  <div class="container">
    <div class="row">
      <div class="col">
        <feed-list-item v-for="feed in list1" :key="feed.feedId" v-bind="feed"></feed-list-item>
      </div>
      <div class="col">
        <feed-list-item v-for="feed in list2" :key="feed.feedId" v-bind="feed"></feed-list-item>
      </div>
      <div class="col">
        <feed-list-item v-for="feed in list3" :key="feed.feedId" v-bind="feed"></feed-list-item>
      </div>
    </div>
  </div>
</template>

<script>
import FeedListItem from "@/components/FeedListItem.vue";
// import useStore from "@/store/index.js";
// import { throwStatement } from "@babel/types";

// const store = useStore();

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
      list1: [],
      list2: [],
      list3: [],
    };
  },
  created() {
    console.log("FeedList.vue created start");
    console.log(this.feedList);
    for (let i = 0; i < this.feedList.length; i++) {
      if (i % 3 == 0) {
        this.list1.push(this.feedList[i]);
      } else if (i % 3 == 1) {
        this.list2.push(this.feedList[i]);
      } else if (i % 3 == 2) {
        this.list3.push(this.feedList[i]);
      }
      console.log(this.feedList[i]);
    }
    console.log("FeedList.vue created end");
  },
  watch: {
    feedList() {
      console.log("FeedList.vue watch start");
      for (let i = 0; i < this.feedList.length; i++) {
        if (i % 3 == 0) {
          this.list1.push(this.feedList[i]);
        } else if (i % 3 == 1) {
          this.list2.push(this.feedList[i]);
        } else if (i % 3 == 2) {
          this.list3.push(this.feedList[i]);
        }
        console.log(this.feedList[i]);
      }
      this.$forceUpdate();
      console.log("FeedList.vue watch end");
    },
  },
};
</script>

<style scoped></style>
