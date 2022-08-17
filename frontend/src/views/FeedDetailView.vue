<template>
  <div class="container px-4 py-3" style="border-radius: 7px; box-shadow: 0 0 10px 7px lightgray">
    <div class="row">
      <div style="font-size: 30px; font-weight: bold">{{ feedDetail.feedTitle }}</div>
    </div>
    <div class="d-flex">
      <div class="me-auto"></div>
      <button v-if="isBookmarked == true" id="btn-scrap" class="btn btn-sm btn-outline-success" data-bs-toggle="modal" data-bs-target="#boardSelectModal" data-bs-whatever="0">
        <img class="bookmark-icon" src="@/assets/bookmark_filled.png" style="height: 20px" />스크랩
      </button>
      <button v-else id="btn-scrap" class="btn btn-sm btn-outline-success" data-bs-toggle="modal" data-bs-target="#boardSelectModal" data-bs-whatever="0">
        <img class="bookmark-icon" src="@/assets/bookmark_blank.png" style="height: 20px" />스크랩
      </button>
      <div class="modal fade" id="boardSelectModal" tabindex="-1" aria-labelledby="boardSelectModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="boardSelectModalLabel">스크랩</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <div v-if="isBookmarked == true" class="d-flex justify-content-start mx-2">
                <button @click="scrapFeedAction('delete')" type="button" class="btn btn-outline-danger my-2" data-bs-dismiss="modal">스크랩 취소</button>
              </div>
              <form @submit.prevent="createBoard()">
                <h6>보드 추가</h6>
                <div class="mb-3 d-flex justify-content-center">
                  <input v-model="newBoardName" type="text" class="form-control w-75 mx-2" id="new-board-name" placeholder="새 보드" />

                  <button class="btn btn-primary w-25">+</button>
                </div>
              </form>
              <form>
                <div class="mb-3">
                  <label for="message-text" class="col-form-label" id="select-board-label">저장 경로</label>
                  <select v-model="boardId" class="form-select form-control" id="board-menu">
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
    <br />
    <div v-html="feedDetail.feedContent"></div>
    <hr />
    <detail-voca-list-item v-for="voca in vocaList" :key="voca.wordId" v-bind="voca"></detail-voca-list-item>
    <div id="voca_add_window" class="px-3 pb-3" style="display: none">
      <input id="voca_name_input" class="px-3 py-1 mb-2" type="text" placeholder="단어명" style="font-size: 14px; width: 100%; border-radius: 7px" /> <br />
      <textarea name="voca_add_textarea" id="voca_add_textarea" class="px-2" placeholder="단어 내용.." style="font-size: 13px; width: 100%; height: 130px; border-radius: 7px"></textarea>
      <div class="d-flex justify-content-end">
        <button id="voca_add_btn" class="me-2 btn btn-primary" @click="addVoca" style="font-size: 15px; height: 30px">추가</button>
        <button id="voca_cancel_btn" class="btn btn-danger" @click="closeVocaAddWindow" style="font-size: 15px; height: 30px">취소</button>
      </div>
    </div>
    <hr />
    <div class="text-start ps-3" style="font-size: 13px; font-weight: bold">댓글</div>
    <comment-list-item v-for="comment in commentList" :key="comment.commentId" v-bind="comment"></comment-list-item>

    <div class="d-flex justify-content-start px-3 pt-3">
      <img
        v-if="loginUserInfo.userImgUrl != null"
        class="profile-icon me-2 mt-1 align-middle"
        :src="require('@/assets/profile_icon/profile-' + loginUserInfo.userImgUrl + '.png')"
        alt="profile"
        style="width: 20px; height: 20px"
      />
      <img v-else class="profile-icon me-2 mt-1 align-middle" src="@/assets/blank_profile.png" alt="profile" style="width: 20px; height: 20px" />
      <span class="me-3 mt-1 align-middle" style="font-weight: bold">{{ loginUserId }}</span>
      <input type="text" class="px-2 me-3" id="input_comment_create" placeholder="댓글을 입력하세요." style="font-size: 14px; width: 100%; border-radius: 7px" />
      <button @click="addComment" class="btn" style="background-color: #00dd99; color: white; font-weight: bold; width: 7%">작성</button>
    </div>
    <hr />
    <div class="text-start ps-3 mb-3" style="font-size: 13px; font-weight: bold">{{ loginUserId }}님이 좋아할 만한 기사들</div>
    <div class="row d-flex justify-content-center">
      <feed-list-item class="col-auto" v-for="feed in recommList" :key="feed.feedId" v-bind="feed" :scrapList="scrapList"></feed-list-item>
    </div>
  </div>
</template>

<script>
import CommentListItem from "@/components/CommentListItem.vue";
import DetailVocaListItem from "@/components/DetailVocaListItem.vue";
import FeedListItem from "@/components/FeedListItem.vue";
import useStore from "@/store";

const userStore = useStore().modules.userStore;
const commentStore = useStore().modules.commentStore;
const feedStore = useStore().modules.feedStore;
const wordStore = useStore().modules.wordStore;
const scrapStore = useStore().modules.scrapStore;
const boardStore = useStore().modules.boardStore;

export default {
  components: {
    CommentListItem,
    DetailVocaListItem,
    FeedListItem,
  },
  props: {
    feedId: Number,
  },
  data() {
    return {
      loginUserId: sessionStorage.getItem("current_user"),
      loginUserInfo: Object,
      commentList: Object,
      feedDetail: null,
      vocaList: [],
      recommList: [],
      isBookmarked: false,
      boardId: 0,
      filteredScrap: {},
      myfeedId: null,
      boardList: [],
      newBoardName: "",
      currentUserId: sessionStorage.getItem("current_user_num"),
      scrapList: [],
    };
  },
  async created() {
    console.log("detail created", this.feedId);
    await feedStore.actions.recommFeed(this.feedId);
    this.recommList = feedStore.getters.getRecommList();
    console.log("this", ...this.recommList);
    await userStore.actions.getProfile(this.loginUserId);
    this.loginUserInfo = userStore.getters.profile();
    await commentStore.actions.listComment(this.feedId);
    this.commentList = commentStore.getters.getCommentList();
    await feedStore.actions.getFeedDetail(this.feedId);
    this.feedDetail = feedStore.getters.getFeedDetail();
    this.feedDetail.feedContent = this.feedDetail.feedContent.substr(1, this.feedDetail.feedContent.length - 2);
    this.feedDetail.feedContent = this.feedDetail.feedContent.replace(/data-src/g, "src");
    await wordStore.actions.getMyWordList(sessionStorage.getItem("current_user_num"));
    await scrapStore.actions.getScrapList(this.loginUserInfo.userId);
    this.scrapList = scrapStore.getters.scrapList();
    console.log("detail scraplist", this.scrapList);
    console.log("detail", this.recommList);
    const filtered = this.scrapList.filter((el) => el.feedId == this.feedId);

    if (filtered?.length > 0) {
      this.isBookmarked = true;
      this.filteredScrap = filtered.at(0);
      this.myfeedId = this.filteredScrap.myfeedId;
    } else {
      this.isBookmarked = false;
    }
    await boardStore.actions.getBoardList(this.currentUserId);
    this.boardList = boardStore.getters.boardList();
    const dbWordList = wordStore.getters.getDBWordList();
    this.vocaList = dbWordList.filter((x) => {
      return this.feedDetail.feedContent.indexOf(x.word) != -1;
    });
  },
  methods: {
    detectBottom() {
      var scrollTop = window.scrollTop();
      var innerHeight = window.innerHeight();
      var scrollHeight = document.body.prop("scrollHeight");
      if (scrollTop + innerHeight >= scrollHeight) {
        console.log("scroll true");
      } else {
        console.log("scroll false");
      }
    },
    async createBoard() {
      if (this.newBoardName?.length > 0) {
        const params = {
          userId: this.currentUserId,
          boardName: this.newBoardName,
        };
        await boardStore.actions.createBoard(params);
        const isFailed = boardStore.getters.boardCreateFailed();
        const newBoardInput = document.getElementById("new-board-name");
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
      const scrapBtn = document.getElementById("btn-scrap");
      if (this.isBookmarked == true && res == "delete") {
        if (confirm("스크랩을 취소하시겠습니까?")) {
          scrapStore.actions.deleteScrap(this.filteredScrap.myfeedId);
          this.isBookmarked = false;
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
    },
    async fetchScrapList() {
      await scrapStore.actions.getScrapList(this.currentUserId);
      this.scrapList = scrapStore.getters.scrapList();
      const filtered = this.scrapList.filter((el) => el.feedId == this.feedId);

      if (filtered?.length > 0) {
        this.filteredScrap = filtered.at(0);
        this.myfeedId = this.filteredScrap.myfeedId;
      }
    },
    openVocaAddWindow() {
      const vocaWindow = document.getElementById("voca_add_window");
      const vocaListAddButton = document.getElementById("vocalist_add_btn");

      vocaWindow.classList.add("d-block");
      vocaListAddButton.classList.add("d-none");
      vocaWindow.classList.remove("d-none");
      vocaListAddButton.classList.remove("d-block");
    },
    closeVocaAddWindow() {
      const vocaWindow = document.getElementById("voca_add_window");
      const vocaListAddButton = document.getElementById("vocalist_add_btn");

      vocaWindow.classList.add("d-none");
      vocaListAddButton.classList.add("d-block");
      vocaWindow.classList.remove("d-block");
      vocaListAddButton.classList.remove("d-none");
    },
    addVoca() {
      const vocaName = document.getElementById("voca_name_input").value;
      const vocaDesc = document.getElementById("voca_add_textarea").value;

      console.log("Name: " + vocaName);
      console.log("Desc: " + vocaDesc);
    },
    async addComment() {
      const comment_input = document.getElementById("input_comment_create");
      const userId = sessionStorage.getItem("current_user");
      const feedId = this.feedId;

      await commentStore.actions.writeComment(feedId, comment_input.value, userId);

      await commentStore.actions.listComment(this.feedId);
      this.commentList = commentStore.getters.getCommentList();
      comment_input.value = "";
    },
    async reload() {
      this.recommList = [];
      console.log("detail created", this.feedId, this.$route.params);
      await feedStore.actions.recommFeed(this.$route.params.feedId);
      this.recommList = feedStore.getters.getRecommList();
      console.log("recommend", ...this.recommList);
      await userStore.actions.getProfile(this.loginUserId);
      this.loginUserInfo = userStore.getters.profile();
      await commentStore.actions.listComment(this.feedId);
      this.commentList = commentStore.getters.getCommentList();
      await feedStore.actions.getFeedDetail(this.feedId);
      this.feedDetail = feedStore.getters.getFeedDetail();
      this.feedDetail.feedContent = this.feedDetail.feedContent.substr(1, this.feedDetail.feedContent.length - 2);
      this.feedDetail.feedContent = this.feedDetail.feedContent.replace(/data-src/g, "src");
      await wordStore.actions.getMyWordList(sessionStorage.getItem("current_user_num"));
      this.scrapList = scrapStore.getters.scrapList();
      const filtered = this.scrapList.filter((el) => el.feedId == this.feedId);

      if (filtered?.length > 0) {
        this.isBookmarked = true;
        this.filteredScrap = filtered.at(0);
        this.myfeedId = this.filteredScrap.myfeedId;
      } else {
        this.isBookmarked = false;
      }
      await boardStore.actions.getBoardList(this.currentUserId);
      this.boardList = boardStore.getters.boardList();
      const dbWordList = wordStore.getters.getDBWordList();
      // const myWordList = wordStore.getters.getMyWordList();
      this.vocaList = dbWordList.filter((x) => {
        return this.feedDetail.feedContent.indexOf(x.word) != -1;
      });
      window.scrollTo(0, 0);
    },
  },
  computed: {
    newFeedId() {
      return this.$route.params.feedId;
    },
  },
  mounted() {},
  watch: {
    $route: "reload",
  },
};
</script>

<style scoped>
.spaced {
  display: none;
}
.profile-icon {
  width: 20px;
  height: 20px;
  border-radius: 70%;
  overflow: hidden;
}
</style>
