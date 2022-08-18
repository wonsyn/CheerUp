<template>
  <div class="home">
    <div class="container">
      <div class="mb-4" style="border: 1px solid #41c89f; border-radius: 7px">
        <div class="row p-3">
          <select id="select_feed_type" style="border-width: 0" class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
            <option selected value="0">전체</option>
            <option value="1">뉴스</option>
            <option value="2">정보글</option>
          </select>
          <select style="border-width: 0" id="select_feed_category" class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
            <option selected value="0">산업군(전체)</option>
            <option value="1">금융</option>
            <option value="2">게임</option>
            <option value="3">보안</option>
            <option value="4">IT 서비스</option>
            <option value="5">모바일</option>
          </select>
          <div class="col text-end me-3">
            <button @click="searchFeed" type="button" class="btn btn-primary">검색</button>
          </div>
          <!-- <div class="text-start pt-3 align-middle">
            <div class="form-check form-switch">
              <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault" />
              <label class="form-check-label" for="flexSwitchCheckDefault">팔로우 한 사람 게시글 보기</label>
            </div>
          </div> -->
        </div>
      </div>
      <feed-list v-if="token != null" :feedList="feedList" :key="listKey" class="p-3" style="background-color: #eeeeee88; border-radius: 7px"></feed-list>
    </div>
  </div>
</template>

<script>
import FeedList from "@/components/FeedList.vue";
import useStore from "@/store/index.js";
import router from "@/router";

const feedStore = useStore().modules.feedStore;

export default {
  name: "HomeView",
  components: {
    FeedList,
  },
  data() {
    return {
      feedList: [],
      listKey: 0,
      token: String,
      socket: useStore().modules.userStore.getters.socket(),
    };
  },
  async created() {
    this.token = sessionStorage.getItem("access-token");
    if (sessionStorage.getItem("access-token") == null) {
      router.push({ name: "login" });
    } else {
      await feedStore.actions.getFeed(0, 0);
      this.feedList = feedStore.getters.getFeedList();
      // 알람 리스트 확인
      // 오늘 날짜를 백으로 던지고
      // 백에서 앞으로 일주일 알람 있는지 확인
      // 있다면 소켓 전송
    }
  },
  methods: {
    async searchFeed() {
      const selectType = document.getElementById("select_feed_type");
      const selectCategory = document.getElementById("select_feed_category");

      let type = selectType.options[selectType.selectedIndex].value;
      let category = selectCategory.options[selectCategory.selectedIndex].value;

      await feedStore.actions.getFeed(type, category);
      this.feedList = feedStore.getters.getFeedList();
    },
  },
};
</script>
<style scoped></style>
