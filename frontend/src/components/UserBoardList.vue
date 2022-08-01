<template>
  <div class="container">
    <h3>보드 리스트</h3>
    <div class="row">
      <div class="col">
        <user-board-list-item></user-board-list-item>
      </div>
      <div class="col">
        <user-board-list-item></user-board-list-item>
      </div>
      <div class="col">
        <user-board-list-item></user-board-list-item>
      </div>
    </div>
  </div>
</template>

<script>
import UserBoardListItem from "@/components/UserBoardListItem.vue";

export default {
  components: {
    UserBoardListItem,
  },
};
</script>

<style scoped></style>
