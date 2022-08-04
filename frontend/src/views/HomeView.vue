<template>
  <div class="home">
    <div class="container">
      <div style="border: 1px black solid; border-radius: 7px">
        <div class="row p-3">
          <select id="select_feed_type" class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
            <option selected value="0">전체</option>
            <option value="1">뉴스</option>
            <option value="2">정보글</option>
          </select>
          <select id="select_feed_category" class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
            <option selected value="0">산업군</option>
            <option value="1">금융</option>
            <option value="2">게임</option>
            <option value="3">보안</option>
            <option value="4">IT 서비스</option>
            <option value="5">모바일</option>
          </select>
          <div class="col text-end me-3">
            <button type="button" class="btn" style="background-color: #00dd99">검색</button>
          </div>
          <div class="text-start pt-3 align-middle">
            <div class="form-check form-switch">
              <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault" />
              <label class="form-check-label" for="flexSwitchCheckDefault">팔로우 한 사람 게시글 보기</label>
            </div>
          </div>
        </div>
      </div>
      <feed-list :feedList="feedList" :key="listKey" class="mt-3 p-3" style="border: 1px black solid; border-radius: 7px"></feed-list>
    </div>
  </div>
</template>

<script>
import FeedList from "@/components/FeedList.vue";
import useStore from "@/store/index.js";
import router from "@/router";

const feedStore = useStore().modules.feedStore;
const userStore = useStore().modules.userStore;

export default {
  name: "HomeView",
  components: {
    FeedList,
  },
  data() {
    return {
      feedList: [],
      listKey: 0,
    };
  },
  async created() {
    if (!userStore.state.isLogin) {
      router.push({ name: "login" });
    } else {
      await feedStore.actions.getFeed(0);
      this.feedList = feedStore.getters.getFeedList();
    }
  },
  methods: {
    // forceRender() {
    //   this.listKey += 1;
    // },
  },
  computed: {
    compFeedList() {
      console.log("HomeView.vue computed start");

      return feedStore.getters.getFeedList();
    },
  },
  watch: {
    compFeedList(val) {
      console.log("HomeView.vue watch start");
      console.log(feedStore.getters.getFeedList());
      this.feedList = feedStore.getters.getFeedList();

      console.log(val);
      console.log("HomeView.vue watch end");
    },
  },
};
</script>
