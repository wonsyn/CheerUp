<template>
  <div class="container">
    <div class="d-inline">
      <h3 class="text-center">보드 리스트</h3>
      <form v-if="currentUser == profile.id" class="position-relative" @submit.prevent="createBoard">
        <input type="text" v-model="newBoardName" />
        {{ newBoard }}
        <button type="submit" class="btn btn-outline-primary">보드 생성</button>
      </form>
    </div>

    <div class="row d-flex justify-content-center">
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
      console.log("getboardList method");
      await boardStore.actions.getBoardList(this.profile.userId);
      this.boardList = boardStore.getters.boardList();
    },
  },
  computed: {},
  async created() {
    console.log("created");
    await boardStore.actions.getBoardList(this.profile.userId);
    this.subBoardList = boardStore.getters.boardList();
    this.boardList = this.subBoardList;
  },
  watch: {
    $route: "getBoardList",
  },
};
</script>

<style scoped></style>
