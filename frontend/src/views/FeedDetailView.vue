<template>
  <div class="container px-4 py-3" style="border-radius: 7px; box-shadow: 0 0 10px 7px lightgray">
    <div class="row">
      <div style="font-size: 30px; font-weight: bold">{{ feedDetail.feedTitle }}</div>
    </div>
    <div class="d-flex">
      <div class="me-auto"></div>
      <button class="btn">Scrap</button>
    </div>
    <br />
    <div v-html="feedDetail.feedContent"></div>
    <hr />
    <detail-voca-list-item></detail-voca-list-item>
    <detail-voca-list-item></detail-voca-list-item>
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
      <img class="me-2 mt-1 align-middle" :src="loginUserInfo.userImgUrl" alt="profile" style="width: 20px; height: 20px" />
      <span class="me-3 mt-1 align-middle" style="font-weight: bold">{{ loginUserId }}</span>
      <input type="text" class="px-2 me-3" id="input_comment_create" placeholder="댓글을 입력하세요." style="font-size: 14px; width: 100%; border-radius: 7px" />
      <button @click="addComment" class="btn" style="background-color: #00dd99; color: white; font-weight: bold; width: 7%">작성</button>
    </div>
    <hr />
    <div class="text-start ps-3 mb-3" style="font-size: 13px; font-weight: bold">~~~님이 좋아할 만한 기사들</div>
    <!-- <feed-list></feed-list> -->
  </div>
</template>

<script>
import CommentListItem from "@/components/CommentListItem.vue";
import DetailVocaListItem from "@/components/DetailVocaListItem.vue";
import useStore from "@/store";
// import FeedList from "@/components/FeedList.vue";

const userStore = useStore().modules.userStore;
const commentStore = useStore().modules.commentStore;
const feedStore = useStore().modules.feedStore;

export default {
  components: {
    CommentListItem,
    DetailVocaListItem,
    // FeedList,
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
    };
  },
  async created() {
    await userStore.actions.getProfile(this.loginUserId);
    this.loginUserInfo = userStore.getters.profile();
    await commentStore.actions.listComment(this.feedId);
    this.commentList = commentStore.getters.getCommentList();
    await feedStore.actions.getFeedDetail(this.feedId);
    this.feedDetail = feedStore.getters.getFeedDetail();
    this.feedDetail.feedContent = this.feedDetail.feedContent.substr(1, this.feedDetail.feedContent.length - 2);
    const contentDiv = document.getElementById("div_content");
    contentDiv.innerHTML = this.feedDetail.feedContent;
  },
  methods: {
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
  },
};
</script>

<style scoped>
.spaced {
  display: none;
}
</style>
