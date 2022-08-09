<template>
  <div class="container">
    <div class="d-inline">
      <h3 class="text-center">보드 리스트</h3>
      <form class="position-relative" @submit.prevent="createBoard">
        <input type="text" v-model="newBoardName" />
        {{ newBoard }}
        <button type="submit" class="btn btn-outline-primary">보드 생성</button>
      </form>
    </div>

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
    };
  },
  methods: {
    async createBoard() {
      if (this.newBoardName) {
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
};
</script>

<style scoped></style>
