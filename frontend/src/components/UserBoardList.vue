<template>
  <div class="container">
    <h3>보드 리스트</h3>
    <form @submit.prevent="createBoard">
      <input type="text" v-model="newBoard.boardName" />
      <button class="btn btn-outline-primary">보드 생성</button>
    </form>

    <div class="row">
      <div class="col-4" v-for="board in boardList" :key="board.boardId">
        <user-board-list-item :board="board"></user-board-list-item>
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
const userStore = store.modules.userStore;

export default {
  components: {
    UserBoardListItem,
  },
  data() {
    return {
      profile: {},
      newBoard: {
        boardName: "",
        userId: "",
      },
      boardList: {},
    };
  },
  methods: {
    createBoard() {
      console.log(this.newBoard);
      boardStore.actions.createBoard(this.newBoard);
    },
  },
  async created() {
    const userId = userStore.getters.profile().userId;
    this.newBoard.userId = userId;
    await boardStore.actions.getBoardList(userId);
    this.boardList = boardStore.getters.boardList();
  },
};
</script>

<style scoped></style>
