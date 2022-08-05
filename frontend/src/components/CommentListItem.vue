<template>
  <div>
    <div id="comment_item" class="my-2 py-1 d-flex px-3" @mouseover="displayEdit" @mouseout="hideEdit" style="border-radius: 5px; height: 35px">
      <img class="align-self-center" src="@/assets/logo.png" alt="profile" style="width: 20px; height=20px" />
      <div class="ms-2 text-start align-self-center" style="width: 100px; font-size: 15px; font-weight: bold">{{ id }}</div>
      <div class="ms-2 align-self-center" style="font-size: 13px">{{ commentContent }}</div>
      <img class="ms-3 align-self-center" @click="addLike" src="@/assets/logo.png" alt="like" style="width: 17px; height=17px" />
      <div class="ms-1 me-auto align-self-center" style="font-size: 10px">{{ countLike }}</div>
      <div id="comment_edit" class="d-none">
        <button id="btn_comment_edit" class="btn me-1 align-middle" @click="openCommentEditWindow" style="background-color: #eae784; height: 25px; font-size: 10px">수정</button>
        <button id="btn_comment_delete" class="btn btn-danger align-self-center align-middle" @click="deleteComment" style="color: black; height: 25px; font-size: 10px">삭제</button>
      </div>
    </div>
    <div id="comment_edit_item">
      <div class="my-2 py-1 d-flex px-3">
        <img class="align-self-center" src="@/assets/logo.png" alt="profile" style="width: 20px; height=20px" />
        <div class="ms-2 text-start align-self-center" style="width: 100px; font-size: 15px; font-weight: bold">{{ id }}</div>
        <input type="text" class="px-2" id="input_comment_edit" :value="commentContent" placeholder="댓글을 입력하세요." style="font-size: 14px; width: 100%; border-radius: 7px" />
      </div>
      <div class="pe-3 d-flex">
        <div class="me-auto" />
        <button
          id="btn_comment_edit_done"
          class="btn btn-primary me-1 align-middle"
          @click="editComment"
          style="background-color: #eae784; border-color: #eae784; color: black; height: 25px; font-size: 10px"
        >
          수정
        </button>
        <button id="btn_comment_cancel" class="btn btn-danger align-self-center align-middle" @click="cancelEditComment" style="color: black; height: 25px; font-size: 10px">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
import useStore from "@/store/index.js";

const commentStore = useStore().modules.commentStore;

export default {
  props: {
    commentContent: String,
    commentDate: String,
    commentId: Number,
    countLike: Number,
    feedId: Number,
    id: String,
  },

  created() {
    // const editBtn = document.getElementById("btn_comment_edit");
    // const deleteBtn = document.getElementById("btn_comment_delete");
    // if (sessionStorage.getItem("user_id") != this.id) {
    //   editBtn.classList.add("d-none");
    //   deleteBtn.classList.add("d-none");
    //   editBtn.classList.remove("d-inline");
    //   deleteBtn.classList.remove("d-inline");
    // } else {
    //   editBtn.classList.remove("d-none");
    //   deleteBtn.classList.remove("d-none");
    //   editBtn.classList.add("d-inline");
    //   deleteBtn.classList.add("d-inline");
    // }
  },
  methods: {
    addLike() {
      console.log("like");
    },
    displayEdit() {
      const editWindow = document.getElementById("comment_edit");
      const comment = document.getElementById("comment_item");

      editWindow.classList.add("d-block");
      editWindow.classList.remove("d-none");
      const editBtn = document.getElementById("btn_comment_edit");
      const deleteBtn = document.getElementById("btn_comment_delete");
      if (sessionStorage.getItem("user_id") != this.id) {
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
      const editWindow = document.getElementById("comment_edit");
      const comment = document.getElementById("comment_item");
      editWindow.classList.add("d-none");
      editWindow.classList.remove("d-block");
      comment.style.backgroundColor = "white";
    },
    openCommentEditWindow() {},
    closeCommentEditWindow() {},
    async editComment() {
      const inputCommentEdit = document.getElementById("input_comment_edit").value;
      console.log(inputCommentEdit);
      await commentStore.action.editComment(this.commentId, inputCommentEdit);
    },
    deleteComment() {},
  },
};
</script>

<style scoped></style>
