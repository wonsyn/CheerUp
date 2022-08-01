<template>
  <div id="comment_item" class="my-2 py-1 d-flex px-3" @mouseover="displayEdit" @mouseout="hideEdit" style="border-radius: 5px; height: 35px">
    <img class="align-self-center" src="@/assets/logo.png" alt="profile" style="width: 20px; height=20px" />
    <div class="ms-2 text-start align-self-center" style="width: 100px; font-size: 15px; font-weight: bold">Jehee</div>
    <div class="ms-2 align-self-center" style="font-size: 13px">진짜 좋은 정보에요 !</div>
    <img class="ms-3 align-self-center" @click="addLike" src="@/assets/logo.png" alt="like" style="width: 17px; height=17px" />
    <div class="ms-1 me-auto align-self-center" style="font-size: 10px">15</div>
    <div id="comment_edit" class="d-none">
      <button id="btn_comment_edit" class="btn me-1 align-middle" @click="openCommentEditWindow" style="background-color: #eae784; height: 25px; font-size: 10px">수정</button>
      <button id="btn_comment_cancel" class="btn btn-danger align-self-center align-middle" @click="deleteComment" style="color: black; height: 25px; font-size: 10px">삭제</button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    addLike() {
      console.log("like");
    },
    displayEdit() {
      const editWindow = document.getElementById("comment_edit");
      const comment = document.getElementById("comment_item");

      editWindow.classList.add("d-block");
      editWindow.classList.remove("d-none");
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
    EditComment() {},
    deleteComment() {},
  },
};
</script>

<style scoped></style>
