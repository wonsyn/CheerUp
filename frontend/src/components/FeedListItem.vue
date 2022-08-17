<template>
  <div class="my-3">
    <div class="card mx-auto" style="width: 24rem">
      <img @click="moveDetail" v-if="feedImgUrl != null" :src="feedImgUrl" class="card-img-top pointer" alt="thumbnail" style="width: 100%; max-height: 12rem; object-fit: cover" />
      <img @click="moveDetail" v-else src="@/assets/logo.png" class="card-img-top pointer" alt="thumbnail" style="width: 100%; max-height: 12rem; object-fit: cover" />
      <div class="card-body">
        <p @click="moveDetail" class="card-text pointer">{{ feedTitle }}</p>
      </div>
      <div class="card-footer d-flex justify-content-between">
        <div>{{ feedSource }}</div>
        <button
          v-if="isBookmarked == true"
          :id="'btn-scrap-' + feedId"
          class="btn btn-sm btn-outline-success"
          data-bs-toggle="modal"
          :data-bs-target="'#boardSelectModal-' + feedId"
          data-bs-whatever="0"
        >
          <img class="bookmark-icon" src="@/assets/bookmark_filled.png" style="height: 20px" />
        </button>
        <button v-else :id="'btn-scrap-' + feedId" class="btn btn-sm btn-outline-success" data-bs-toggle="modal" :data-bs-target="'#boardSelectModal-' + feedId" data-bs-whatever="0">
          <img class="bookmark-icon" src="@/assets/bookmark_blank.png" style="height: 20px" />
        </button>
        <div class="modal fade" :id="'boardSelectModal-' + feedId" tabindex="-1" :aria-labelledby="'boardSelectModalLabel-' + feedId" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" :id="'boardSelectModalLabel-' + feedId">스크랩</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <div v-if="isBookmarked == true" class="d-flex justify-content-start mx-2">
                  <button @click="scrapFeedAction('delete')" type="button" class="btn btn-outline-danger my-2" data-bs-dismiss="modal">스크랩 취소</button>
                </div>
                <form @submit.prevent="createBoard()">
                  <h6>보드 추가</h6>
                  <div class="mb-3 d-flex justify-content-center">
                    <input v-model="newBoardName" type="text" class="form-control w-75 mx-2" :id="'new-board-name-' + feedId" placeholder="새 보드" />

                    <button class="btn btn-primary w-25">+</button>
                  </div>
                </form>
                <form>
                  <div class="mb-3">
                    <label for="message-text" class="col-form-label" :id="'select-board-label-' + feedId">저장 경로</label>
                    <select v-model="boardId" class="form-select form-control" :id="'board-menu-' + feedId">
                      <option value="0" selected>기본 프로필에 저장</option>
                      <option v-for="board in boardList" :key="board.boardId" :value="board.boardId">{{ board.boardName }}</option>
                    </select>
                  </div>
                </form>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button @click="scrapFeedAction()" type="button" class="btn btn-primary" data-bs-dismiss="modal">스크랩</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import scrapStore from "@/store/modules/Scrap";
import boardStore from "@/store/modules/Board";

export default {
  name: "FeedListItem",
  props: {
    feedAuthor: String,
    feedCategory: String,
    feedContent: String,
    feedDate: Date,
    feedId: Number,
    feedImgName: String,
    feedImgUrl: String,
    feedSource: String,
    feedTitle: String,
    feedType: Number,
    feedUrl: String,
    scrapList: Array,
  },
  data() {
    return {
      isBookmarked: false,
      boardId: 0,
      filteredScrap: {},
      boardList: [],
      newBoardName: "",
    };
  },
  methods: {
    async createBoard() {
      if (this.newBoardName?.length > 0) {
        const params = {
          userId: this.currentUserId,
          boardName: this.newBoardName,
        };
        await boardStore.actions.createBoard(params);
        const isFailed = boardStore.getters.boardCreateFailed();
        const newBoardInput = document.getElementById("new-board-name-" + this.feedId);
        if (isFailed == true) {
          newBoardInput.classList.add("is-invalid");
        } else {
          newBoardInput.classList.remove("is-invalid");
          this.getBoardList();
        }
      }
      this.newBoardName = "";
    },
    async getBoardList() {
      await boardStore.actions.getBoardList(this.currentUserId);
      this.boardList = boardStore.getters.boardList();
    },
    async scrapFeedAction(res) {
      const scrapBtn = document.getElementById("btn-scrap-" + this.feedId);
      if (this.isBookmarked == true && res == "delete") {
        if (confirm("스크랩을 취소하시겠습니까?")) {
          scrapStore.actions.deleteScrap(this.filteredScrap.myfeedId);
          this.isBookmarked = false;
          this.$parent.getScrapList();
        }
      } else if (this.isBookmarked == false) {
        const params = {
          boardId: this.boardId,
          feedId: this.feedId,
          scrapfeedType: this.feedType,
          userId: Number(this.currentUserId),
        };
        await scrapStore.actions.createScrap(params);
        this.isBookmarked = true;
        this.boardId = 0;
      } else {
        const params = {
          boardId: this.boardId,
          myfeedId: this.filteredScrap.myfeedId,
        };
        await scrapStore.actions.editScrap(params);
        this.boardId = 0;
      }
      scrapBtn.blur();
      this.fetchScrapList();
      this.$parent.getScrapList();
    },
    moveDetail() {
      router.push({ name: "detail", params: { feedId: this.feedId } });
    },
    fetchScrapList() {
      scrapStore.actions.getScrapList(this.currentUserId);
    },
  },
  computed: {
    currentUserId() {
      return sessionStorage.getItem("current_user_num");
    },
  },
  async created() {
    const filtered = this.scrapList.filter((el) => el.feedId === this.feedId);

    if (filtered?.length > 0) {
      this.isBookmarked = true;
      this.filteredScrap = filtered.at(0);
      console.log("created scrap", this.filteredScrap);
    } else {
      this.isBookmarked = false;
    }
    await boardStore.actions.getBoardList(this.currentUserId);
    this.boardList = boardStore.getters.boardList();
  },
  watch: {},
};
</script>

<style scoped>
.pointer {
  cursor: pointer;
}
</style>
