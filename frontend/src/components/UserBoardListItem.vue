<template>
  <div class="card my-3" style="width: 18rem">
    <img src="@/assets/logo.png" class="card-img-top" alt="img" />
    <div class="card-body">
      <h4 class="card-text text-start">{{ board.boardName }}</h4>
      <div class="card-footer d-flex justify-content-end">
        <button @click="boardUpdate" class="btn btn-sm btn-outline-primary mx-1">수정</button>
        <button @click="boardDelete" class="btn btn-sm btn-outline-secondary">삭제</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "userBoardListItem",
  props: {
    board: Object,
  },
  created() {},
};
</script>

<style scoped></style>
