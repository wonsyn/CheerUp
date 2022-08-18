<template>
  <div id="board-list-item" class="ct-item board-list-item card my-3 slide-in-right" style="width: 18rem">
    <img v-if="scrapForThumbnail != null" @click="goBoardDetail" style="width: 100%; max-height: 12rem; object-fit: cover; cursor: pointer" :src="scrapForThumbnail" class="card-img-top" alt="img" />
    <img v-else @click="goBoardDetail" style="width: 100%; max-height: 12rem; object-fit: cover; cursor: pointer" src="@/assets/logo.png" class="card-img-top" alt="img" />
    <div class="card-body">
      <h4 @click="goBoardDetail" style="cursor: pointer" v-if="!isEdit" class="card-text text-start">{{ board.boardName }}</h4>
      <form v-else @submit.prevent="updateBoard">
        <input class="w-75 my-1" type="text" v-model="inputForUpdate" />
        <button class="btn btn-sm btn-info mx-1">수정</button>
      </form>
      <div v-if="currentUser == profile.id" class="card-footer d-flex justify-content-end">
        <button v-if="!isEdit" @click="isEdit = true" class="btn btn-sm btn-info mx-1">수정</button>
        <button v-else @click="cancel" class="btn btn-sm btn-outline-danger mx-1">취소</button>
        <button v-if="!isEdit" @click="deleteBoard" class="btn btn-sm btn-danger">삭제</button>
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
      scrapForThumbnail: null,
      isEdit: false,
      inputForUpdate: "",
    };
  },
  props: {
    board: Object,
    profile: Object,
  },
  methods: {
    goBoardDetail() {
      boardStore.actions.fetchBoard(this.board);
      this.$emit("viewBoard");
    },
    cancel() {
      this.isEdit = false;
    },
    async updateBoard() {
      if (this.currentUser == this.profile.id) {
        const params = {
          userId: this.board.userId,
          boardId: this.board.boardId,
          boardName: this.inputForUpdate,
        };
        await boardStore.actions.updateBoard(params);
        const isSuccess = boardStore.getters.isSuccess();
        this.scrapsInBoard = scrapStore.getters.scrapsInBoard();
        if (isSuccess) {
          this.$parent.getBoardList();
          this.isEdit = false;
        }
      }
    },
    async deleteBoard() {
      if (this.currentUser == this.profile.id) {
        await boardStore.actions.deleteBoard(this.board.boardId);
        this.$parent.getBoardList();
      }
    },
  },
  async created() {
    const params = {
      userId: this.board.userId,
      boardId: this.board.boardId,
    };
    await scrapStore.actions.getFeedInBoard(params);
    this.scrapsInBoard = scrapStore.getters.scrapsInBoard();
    if (this.scrapsInBoard.length > 0) {
      const thumbsInd = this.scrapsInBoard.length - 1;
      this.scrapForThumbnail = this.scrapsInBoard[thumbsInd].feedImgUrl;
    }

    boardStore.actions.fetchBoard(this.board);
    this.inputForUpdate = this.board.boardName;
  },
  mounted() {},
};
</script>

<style scoped>
.ct-item:hover {
  transform: scale(1.025);
  transition: all 0.15s;
}
.slide-in-right {
  animation: change2 1s ease forwards;
}
@keyframes change2 {
  from {
    transform: translateX(-100%);
    visibility: visible;
  }
  to {
    transform: translateX(0%);
  }
}
</style>
