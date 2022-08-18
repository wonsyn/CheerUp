<template>
  <div class="container my-3">
    <div class="d-inline">
      <form v-if="currentUser == profile.id" class="position-relative" @submit.prevent="createBoard">
        <input class="ps-3" type="text" v-model="newBoardName" style="border: none; border-bottom: 1px solid black; padding-left: 2px" placeholder="보드 이름" />
        {{ newBoard }}
        <button type="submit" class="btn btn-outline-primary mx-3">보드 생성</button>
      </form>
    </div>

    <div class="row d-flex justify-content-center">
      <h5 v-if="boardList === undefined || boardList === null || boardList.length === 0">보드가 없습니다.</h5>
      <div class="col-auto" v-for="board in boardList" :key="board.boardId">
        <user-board-list-item :board="board" :profile="profile" @viewBoard="viewBoard"></user-board-list-item>
      </div>
    </div>
  </div>
</template>

<script>
import UserBoardListItem from "@/components/UserBoardListItem.vue";
import useStore from "@/store";
// import router from "@/router";

const store = useStore();
const boardStore = store.modules.boardStore;

export default {
  components: {
    UserBoardListItem,
  },
  props: {
    profile: Object,
  },
  data() {
    return {
      newBoardName: "",
      userId: "",
      boardList: [],
      subBoardList: [],
      currentUser: sessionStorage.getItem("current_user"),
    };
  },
  methods: {
    viewBoard() {
      this.$emit("viewBoard");
    },
    async createBoard() {
      if (this.newBoardName && this.currentUser == this.profile.id) {
        const params = {
          userId: this.profile.userId,
          boardName: this.newBoardName,
        };
        await boardStore.actions.createBoard(params);
        this.getBoardList();
      }
      this.boardList = this.subBoardList;
      this.newBoardName = "";
    },
    async getBoardList() {
      await boardStore.actions.getBoardList(this.profile.userId);
      this.boardList = boardStore.getters.boardList();
    },
  },
  computed: {},
  async created() {
    await boardStore.actions.getBoardList(this.profile.userId);
    this.subBoardList = boardStore.getters.boardList();
    this.boardList = this.subBoardList;
  },
  watch: {
    $route: "getBoardList",
  },
};
</script>

<style scoped>
input:focus {
  outline: none;
}
</style>
