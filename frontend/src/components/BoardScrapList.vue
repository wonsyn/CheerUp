<template>
  <div class="container">
    <div>
      <div class="d-flex"><button @click="goBack" class="btn btn-success mx-3 px-5 my-0">이전으로</button></div>

      <h3 class="my-0 align-text-bottom">{{ board.boardName }}</h3>
      <div></div>
    </div>

    <div class="row d-flex justify-content-center">
      <h5 v-if="scrapsInBoard.length == 0">스크랩이 없습니다.</h5>
      <feed-list-item class="col-auto" v-for="feed of scrapsInBoard" :key="feed.feedId" v-bind="feed" :scrapList="scrapsInBoard"></feed-list-item>
    </div>
  </div>
</template>

<script>
import FeedListItem from "@/components/FeedListItem";
import useStore from "@/store";
// import router from "@/router";

const store = useStore();
const scrapStore = store.modules.scrapStore;

export default {
  name: "BoardScrapList",
  data() {
    return {
      scrapsInBoard: null,
    };
  },
  components: {
    FeedListItem,
  },
  props: {
    board: Object,
    profile: Object,
  },
  methods: {
    goBack() {
      this.$emit("goBoardList");
    },
    async getScrapList() {
      const params = {
        userId: this.profile.userId,
        boardId: this.board.boardId,
      };
      await scrapStore.actions.getFeedInBoard(params);
      this.scrapsInBoard = scrapStore.getters.scrapsInBoard();
    },
  },
  async created() {
    const params = {
      userId: this.profile.userId,
      boardId: this.board.boardId,
    };
    await scrapStore.actions.getFeedInBoard(params);
    this.scrapsInBoard = scrapStore.getters.scrapsInBoard();
  },
  watch: {},
};
</script>

<style scoped></style>
