<template>
  <div class="my-3">
    <div :id="'card-' + feedId" class="card mx-auto ct-item" style="width: 24rem">
      <img @click="moveDetail" v-if="feedImgUrl != null" :src="feedImgUrl" class="card-img-top pointer" alt="thumbnail" style="width: 100%; max-height: 12rem; object-fit: cover" />
      <img @click="moveDetail" v-else src="@/assets/logo.png" class="card-img-top pointer" alt="thumbnail" style="width: 100%; max-height: 12rem; object-fit: cover" />
      <div @click="moveDetail" class="card-body mt-2 pointer" style="margin-top: auto; margin-bottom: auto">
        <p class="card-text">{{ feedTitle }}</p>
      </div>
      <div class="card-footer d-flex justify-content-between">
        <div>{{ feedSource }}</div>
        <button @click="modalMethod" v-if="isBookmarked == true" :id="'btn-scrap-' + feedId" class="btn btn-sm">
          <img class="bookmark-icon" src="@/assets/bookmark_filled.png" style="height: 25px" />
        </button>
        <button @click="modalMethod" v-else :id="'btn-scrap-' + feedId" class="btn btn-sm" style="border: 0">
          <img class="bookmark-icon" src="@/assets/bookmark_blank.png" style="height: 25px" />
        </button>
      </div>
    </div>
    <div class="modal fade" style="z-index: 10000" :id="'boardSelectModal-' + feedId" tabindex="-1" :aria-labelledby="'boardSelectModalLabel-' + feedId" aria-hidden="true">
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
</template>

<script>
import router from "@/router";
import scrapStore from "@/store/modules/Scrap";
import boardStore from "@/store/modules/Board";
import Modal from "bootstrap/js/dist/modal.js";
import Swal from "sweetalert2";

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
      newScrapList: [],
      filteredScrap: {},
      boardList: [],
      newBoardName: "",
    };
  },
  methods: {
    modalMethod() {
      const newModal = new Modal(document.getElementById("boardSelectModal-" + this.feedId));
      newModal.show();
    },
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
        Swal.fire({
          title: "스크랩을 취소하시겠습니까?",
          // text: "스크랩을 취소합니다!",
          showCancelButton: true,
          confirmButtonColor: "#ee7785",
          cancelButtonColor: "#00dd99",
          confirmButtonText: "스크랩 취소",
          cancelButtonText: "이전으로",
        }).then((result) => {
          if (result.isConfirmed) {
            scrapStore.actions.deleteScrap(this.filteredScrap.myfeedId);
            this.$parent.getScrapList();
            this.isBookmarked = false;
          }
        });
      } else if (this.isBookmarked == false) {
        const params = {
          boardId: this.boardId,
          feedId: this.feedId,
          scrapfeedType: this.feedType,
          userId: Number(this.currentUserId),
        };
        await scrapStore.actions.createScrap(params);
        Swal.fire({
          position: "center",
          icon: "success",
          title: "성공",
          showConfirmButton: false,
          timer: 900,
        });
      } else {
        const params = {
          boardId: this.boardId,
          myfeedId: this.filteredScrap.myfeedId,
        };
        await scrapStore.actions.editScrap(params);
        Swal.fire({
          position: "center",
          icon: "success",
          title: "성공",
          showConfirmButton: false,
          timer: 900,
        });
      }
      scrapBtn.blur();
      this.fetchScrapList();
      this.$parent.getScrapList();
      this.boardId = 0;
    },
    moveDetail() {
      if (this.feedType == 1) {
        router.push({ name: "detail", params: { feedId: this.feedId } });
      } else {
        window.open(this.feedUrl, "blog");
      }
    },
    async fetchScrapList() {
      await scrapStore.actions.getScrapList(sessionStorage.getItem("current_user_num"));
      this.newScrapList = scrapStore.getters.scrapList();
      const filtered = this.newScrapList.filter((el) => el.feedId === this.feedId);

      if (filtered?.length > 0) {
        this.isBookmarked = true;
        this.filteredScrap = filtered.at(0);
      } else {
        this.isBookmarked = false;
      }
      await boardStore.actions.getBoardList(this.currentUserId);
      this.boardList = boardStore.getters.boardList();
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
    } else {
      this.isBookmarked = false;
    }

    await boardStore.actions.getBoardList(this.currentUserId);
    this.boardList = boardStore.getters.boardList();
    const cardEl = document.getElementById("card-" + this.feedId);
    if (this.$route.name == "profile") {
      cardEl.classList.add("slide-in");
    }
  },
  watch: {},
};
</script>

<style scoped>
.pointer {
  cursor: pointer;
}
.ct-item {
  -webkit-text-size-adjust: 100%;
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
  --vh: 7.22px;
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
  text-align: center;
  box-sizing: inherit;
  border: 0;
  font: inherit;
  margin: 0;
  outline: none;
  padding: 0;
  vertical-align: baseline;
  backface-visibility: hidden;
  background: #fff;
  border-radius: 12px;
  box-shadow: 10px 20px 20px 0 rgba(92, 95, 112, 0.08);
  overflow: hidden;
  transform: translateZ(0);
  transition: all 0.15s;
  height: 350px;
  width: 300px;
}
.ct-item:hover {
  transform: scale(1.025);
}
/* .movie-list {
  display: flex;
  margin-left: 2rem;
  margin-right: 2rem;
} */
/* .movie-item {
  position: relative;
  display: block;
  flex: 1 1 0px;
  transform: scale(0.8);
  transition: all 0.5s ease-in-out;
  text-align: center;
}
#wrap {
  margin: 0 auto;
  text-align: center;
  width: 300px;
}
#wrap:hover .more {
  opacity: 1;
}
.movie-item:hover {
  transform: scale(1.1);
}
.movie-item:hover img {
  opacity: 0.7;
}
.movie-item:hover .more {
  opacity: 1;
} */
.slide-in {
  animation: change 1s ease forwards;
}

@keyframes change {
  from {
    transform: translateX(100%);
    visibility: visible;
  }
  to {
    transform: translateX(0%);
  }
}
</style>
