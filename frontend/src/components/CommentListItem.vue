<template>
  <div :id="'comment_template' + commentId">
    <div :id="'item' + commentId" class="my-2 py-1 d-flex px-3" @mouseover="displayEdit" @mouseout="hideEdit" style="border-radius: 5px; height: 35px">
      <img v-if="userImgUrl === null" class="profile-icon align-self-center" src="@/assets/blank_profile.png" alt="profile" style="width: 20px; height: 20px" />
      <img v-else class="profile-icon align-self-center" :src="userImgUrlreq" alt="profile" style="width: 20px; height: 20px" />
      <!-- {{ userImgUrl }} -->
      <div class="ms-2 text-start align-self-center" style="width: 100px; font-size: 15px; font-weight: bold">{{ id }}</div>
      <div class="ms-2 align-self-center" style="font-size: 13px">{{ content }}</div>
      <div class="pe-3 me-auto d-flex" @click="updateLike">
        <img v-if="like" class="ms-3 align-self-center" src="@/assets/heart_fill.png" alt="like" style="width: 17px; height=17px" />
        <img v-else class="ms-3 align-self-center" src="@/assets/heart_outline.png" alt="like" style="width: 17px; height=17px" />
        <div class="ms-1 align-self-center" style="font-size: 10px">{{ likeNum }}</div>
        <div class="ms-2 align-self-center" style="font-size: 5px; color: darkgray">{{ commentDate }}</div>
      </div>
      <div :id="'btn' + commentId" class="d-none">
        <button :id="'btn_comment_edit' + commentId" class="btn btn-info me-1 align-middle" @click="openCommentEditWindow" style="height: 25px; font-size: 10px">수정</button>
        <button :id="'btn_comment_delete' + commentId" class="btn btn-danger align-self-center align-middle" @click="deleteComment" style="height: 25px; font-size: 10px">삭제</button>
      </div>
    </div>
    <div :id="'comment_edit_item' + commentId" class="d-none">
      <div class="my-2 py-1 d-flex px-3">
        <img v-if="userImgUrl === null" class="profile-icon align-self-center" src="@/assets/blank_profile.png" alt="profile" style="width: 20px; height: 20px" />
        <img v-else class="profile-icon align-self-center" :src="userImgUrlreq" alt="profile" style="width: 20px; height: 20px" />
        <div class="ms-2 text-start align-self-center" style="width: 100px; font-size: 15px; font-weight: bold">{{ id }}</div>
        <input type="text" class="px-2" :id="'input_comment_edit' + commentId" :value="content" placeholder="댓글을 입력하세요." style="font-size: 14px; width: 100%; border-radius: 7px" />
      </div>
      <div class="pe-3 d-flex">
        <div class="me-auto" />
        <button id="btn_comment_edit_done" class="btn btn-success me-1 align-middle" @click="editComment" style="height: 25px; font-size: 10px">수정</button>
        <button id="btn_comment_cancel" class="btn btn-danger align-self-center align-middle" @click="closeCommentEditWindow" style="height: 25px; font-size: 10px">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
import useStore from "@/store/index.js";

const commentStore = useStore().modules.commentStore;
const userStore = useStore().modules.userStore;
export default {
  props: {
    commentContent: String,
    commentDate: String,
    commentId: Number,
    countLike: Number,
    feedId: Number,
    id: String,
  },
  data() {
    return {
      content: this.commentContent,
      likeNum: this.countLike,
      like: false,
      socket: userStore.getters.socket(),
      userImgUrl: String,
      userImgUrlreq: String,
    };
  },
  async created() {
    await commentStore.actions.checkLike(this.commentId, sessionStorage.getItem("current_user_num"));
    this.like = commentStore.getters.getLikeState();
    console.log(this.like);
    await userStore.actions.searchById(this.id);
    this.userImgUrl = userStore.getters.userList()[0].userImgUrl;
    if (this.userImgUrl != null) {
      this.userImgUrlreq = require("@/assets/profile_icon/profile-" + this.userImgUrl + ".png");
    }
    console.log("imgurl: ", this.userImgUrl);
    // console.log("imgurl: ", this.userImgUrlreq);
  },
  methods: {
    async updateLike() {
      if (userStore.getters.socket() === null || userStore.getters.socket().readyState === 3) {
        console.log("like socket disconnected");
        await userStore.actions.connect();
      }
      if (this.like) {
        commentStore.actions.deleteLike(this.commentId, sessionStorage.getItem("current_user_num")); // like == true 인 경우
        this.like = false;
        this.likeNum = this.likeNum - 1;
      } else {
        commentStore.actions.addLike(this.commentId, sessionStorage.getItem("current_user_num")); // like == false 인 경우
        this.like = true;
        this.likeNum = this.likeNum + 1;
        let socketMsg = "comment_like," + sessionStorage.getItem("current_user") + "," + this.id + "," + this.feedId + "," + this.commentContent;
        console.log("commentListItem(83): " + socketMsg);
        await userStore.getters.socket().onopen(socketMsg);
      }
    },
    displayEdit() {
      const editWindow = document.getElementById("btn" + this.commentId);
      const comment = document.getElementById("item" + this.commentId);

      editWindow.classList.add("d-block");
      editWindow.classList.remove("d-none");
      const editBtn = document.getElementById("btn_comment_edit" + this.commentId);
      const deleteBtn = document.getElementById("btn_comment_delete" + this.commentId);
      if (sessionStorage.getItem("current_user") != this.id) {
        editBtn.classList.add("d-none");
        deleteBtn.classList.add("d-none");
        editBtn.classList.remove("d-inline");
        deleteBtn.classList.remove("d-inline");
      } else {
        editBtn.classList.remove("d-none");
        deleteBtn.classList.remove("d-none");
        editBtn.classList.add("d-inline");
        deleteBtn.classList.add("d-inline");
      }
      comment.style.backgroundColor = "lightgrey";
    },

    hideEdit() {
      const editWindow = document.getElementById("btn" + this.commentId);
      const comment = document.getElementById("item" + this.commentId);
      editWindow.classList.add("d-none");
      editWindow.classList.remove("d-block");
      comment.style.backgroundColor = "white";
    },

    openCommentEditWindow() {
      const commentItem = document.getElementById("item" + this.commentId);
      const commentEditItem = document.getElementById("comment_edit_item" + this.commentId);

      commentItem.classList.add("d-none");
      commentEditItem.classList.add("d-block");
      commentItem.classList.remove("d-block");
      commentEditItem.classList.remove("d-none");
    },

    closeCommentEditWindow() {
      const commentItem = document.getElementById("item" + this.commentId);
      const commentEditItem = document.getElementById("comment_edit_item" + this.commentId);

      commentItem.classList.add("d-block");
      commentEditItem.classList.add("d-none");
      commentItem.classList.remove("d-none");
      commentEditItem.classList.remove("d-block");
    },

    async editComment() {
      const inputCommentEdit = document.getElementById("input_comment_edit" + this.commentId).value;
      console.log(this.commentId);
      console.log(inputCommentEdit);
      await commentStore.actions.editComment(this.commentId, inputCommentEdit);

      const commentItem = document.getElementById("item" + this.commentId);
      const commentEditItem = document.getElementById("comment_edit_item" + this.commentId);
      commentItem.classList.add("d-block");
      commentEditItem.classList.add("d-none");
      commentItem.classList.remove("d-none");
      commentEditItem.classList.remove("d-block");

      this.content = inputCommentEdit;
    },

    deleteComment() {
      if (confirm("댓글을 삭제하시겠습니까?")) {
        commentStore.actions.deleteComment(this.commentId);
        const commentTemplate = document.getElementById("comment_template" + this.commentId);
        commentTemplate.classList.add("d-none");
        commentTemplate.classList.remove("d-block");
      }
    },
  },
};
</script>

<style scoped>
.profile-icon {
  width: 20px;
  height: 20px;
  border-radius: 70%;
  overflow: hidden;
}
</style>
