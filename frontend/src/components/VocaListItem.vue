<template>
  <div class="py-3 px-5 my-3" style="background-color: rgb(245, 245, 245); border-radius: 10px">
    <div class="d-flex">
      <span class="ps-2" style="font-weight: bold; font-size: 20px">{{ personalWord }}</span>
    </div>
    <hr />
    <div style="text-align: left; font-size: 14px">{{ personalWordExp }}</div>
    <br />
    <div class="d-flex">
      <div class="me-auto">{{ personalWordDate }}</div>
      <img class="me-2" src="@/assets/edit.png" alt="edit" style="width: 4%; height: 4%" />
      <img v-if="isFav" class="me-2" src="@/assets/star_filled.png" alt="fav" style="width: 4%; height: 4%" />
      <img v-else class="me-2" src="@/assets/star_outline.png" alt="fav" style="width: 4%; height: 4%" />
      <img src="@/assets/delete.png" alt="delete" style="width: 4%; height: 4%" />
    </div>
  </div>
</template>

<script>
export default {
  props: {
    personalWordId: Number,
    userId: Number,
    personalWord: String,
    personalWordExp: String,
    personalFavWord: Number,
    personalWordDate: String,
  },
  date() {
    return {
      isFav: false,
    };
  },
  methods: {},
};
</script>

<style scoped>
hr {
  background-color: #00dd99;
  height: 3px;
  border: 0px;
}
</style>
