<template>
  <div class="container">
    <div class="row">
      <div class="col">
        col
        <user-board-list-item></user-board-list-item>
      </div>
      <div class="col">
        col
        <user-board-list-item></user-board-list-item>
      </div>
      <div class="col">
        col
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
