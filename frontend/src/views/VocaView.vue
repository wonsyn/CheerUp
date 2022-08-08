<template>
  <div>
    <div class="container">
      <div class="d-flex">
        <div class="me-auto mt-3"></div>
        <form class="me-1" @submit.prevent="searchVoca" style="width: 30%">
          <input type="text" v-model="searchWord" placeholder="검색할 단어를 입력하세요." style="width: 100%; border: 0px solid black; border-bottom: 1px solid black" />
        </form>
        <img src="@/assets/magnifying-glass.png" alt="search" style="width: 2%; height: 2%" />
      </div>
      <div class="row">
        <div class="col">
          col
          <voca-list-item></voca-list-item>
        </div>
        <div class="col">
          col
          <voca-list-item></voca-list-item>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VocaListItem from "@/components/VocaListItem.vue";

export default {
  component: {
    VocaListItem,
  },
  data() {
    return {
      searchWord: null,
    };
  },
  methods: {
    searchVoca() {},
  },
};
</script>

<style scoped>
input:focus {
  outline: none;
}
</style>
