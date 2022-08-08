<template>
  <div class="container">
    <div class="d-flex">
      <div class="me-auto mt-3"></div>
      <form class="me-1" @submit.prevent="searchVoca" style="width: 30%">
        <input type="text" v-model="searchWord" placeholder="검색할 단어를 입력하세요." style="width: 100%; border: 0px solid black; border-bottom: 1px solid black" />
      </form>
      <img src="@/assets/magnifying-glass.png" alt="search" style="width: 2%; height: 2%" />
    </div>
    <div class="row py-3">
      <div class="col">
        <voca-list-item v-for="voca in list1" :key="voca.personalWordId" v-bind="voca" @refresh-list="refreshList"></voca-list-item>
      </div>
      <div class="col">
        <voca-list-item v-for="voca in list2" :key="voca.personalWordId" v-bind="voca" @refresh-list="refreshList"></voca-list-item>
      </div>
    </div>
  </div>
</template>

<script>
import VocaListItem from "@/components/VocaListItem.vue";
import useStore from "@/store/index.js";

const wordStore = useStore().modules.wordStore;

export default {
  components: {
    VocaListItem,
  },
  data() {
    return {
      searchWord: null,
      myVocaList: [],
      list1: [],
      list2: [],
    };
  },
  async created() {
    await wordStore.actions.getMyWordList(sessionStorage.getItem("current_user_num"));
    this.myVocaList = wordStore.getters.getMyWordList();

    for (let i = 0; i < this.myVocaList.length; i++) {
      if (i % 2 == 0) {
        this.list1.push(this.myVocaList[i]);
      } else {
        this.list2.push(this.myVocaList[i]);
      }
    }
  },
  methods: {
    searchVoca() {},

    async refreshList() {
      await wordStore.actions.getMyWordList(sessionStorage.getItem("current_user_num"));
      this.myVocaList = wordStore.getters.getMyWordList();

      for (let i = 0; i < this.myVocaList.length; i++) {
        if (i % 2 == 0) {
          this.list1.push(this.myVocaList[i]);
        } else {
          this.list2.push(this.myVocaList[i]);
        }
      }
    },
  },
};
</script>

<style scoped>
input:focus {
  outline: none;
}
</style>
