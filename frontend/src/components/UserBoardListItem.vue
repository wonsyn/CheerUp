<template>
  <div id="board-list-item" class="board-list-item card my-3" style="width: 18rem">
    <img @click="goBoardDetail" style="cursor: pointer" src="@/assets/logo.png" class="card-img-top" alt="img" />
    <div class="card-body">
      <h4 @click="goBoardDetail" style="cursor: pointer" v-if="!isEdit" class="card-text text-start">{{ board.boardName }}</h4>
      <form v-else @submit.prevent="updateBoard">
        <input class="w-75 my-1" type="text" v-model="inputForUpdate" />
        <button class="btn btn-sm btn-outline-primary mx-1">수정</button>
      </form>
      <div class="card-footer d-flex justify-content-end">
        <button v-if="!isEdit" @click="isEdit = true" class="btn btn-sm btn-outline-primary mx-1">수정</button>
        <button v-else @click="cancel" class="btn btn-sm btn-outline-primary mx-1">취소</button>
        <button v-if="!isEdit" @click="deleteBoard" class="btn btn-sm btn-outline-secondary">삭제</button>
      </div>
    </div>
  </div>
</template>

<script>
import useStore from "@/store";
// import router from "@/router";

const store = useStore();
const boardStore = store.modules.boardStore;
const scrapStore = store.modules.scrapStore;
// const userStore = store.modules.userStore;

export default {
  name: "userBoardListItem",
  data() {
    return {
      currentUser: sessionStorage.getItem("current_user"),
      scrapsInBoard: {},
      scrapForThumbnail: {},
      isEdit: false,
      inputForUpdate: "",
    };
  },
  props: {
    board: Object,
  },
  methods: {
    goBoardDetail() {
      boardStore.actions.fetchBoard(this.board);
      console.log(boardStore.state.board);
      this.$emit("viewBoard");
    },
    cancel() {
      this.isEdit = false;
    },
    async updateBoard() {
      console.log("update");
      const params = {
        userId: this.board.userId,
        boardId: this.board.boardId,
        boardName: this.inputForUpdate,
      };
      await boardStore.actions.updateBoard(params);
      const isSuccess = boardStore.getters.isSuccess();
      this.scrapsInBoard = scrapStore.getters.scrapsInBoard();
      if (isSuccess) {
        console.log(isSuccess);
        this.$parent.getBoardList();
        this.isEdit = false;
      } else {
        console.log("변경 실패!");
      }
    },
    async deleteBoard() {
      console.log("delete");
      await boardStore.actions.deleteBoard(this.board.boardId);
      this.$parent.getBoardList();
    },
  },
  async created() {
    const params = {
      userId: this.board.userId,
      boardId: this.board.boardId,
    };
    console.log(params);
    await scrapStore.actions.getFeedInBoard(params);
    this.scrapsInBoard = scrapStore.getters.scrapsInBoard();
    if (this.scrapsInBoard) {
      this.scrapForThumbnail = this.scrapsInBoard.at(-1);
    }
    console.log(this.board);
    boardStore.actions.fetchBoard(this.board);
    this.inputForUpdate = this.board.boardName;
  },
};
</script>

<style scoped></style>
