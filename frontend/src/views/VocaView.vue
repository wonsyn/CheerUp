<template>
  <div class="container">
    <div class="d-flex">
      <div class="me-auto mt-3"></div>
      <img v-if="onFav" class="me-2" @click="changeFav" src="@/assets/star_filled.png" alt="star_filled" style="width: 2%; height: 2%" />
      <img v-else class="me-2" @click="changeFav" src="@/assets/star_outline.png" alt="star_outline" style="width: 2%; height: 2%" />
      <form class="me-1" @submit.prevent="searchVoca" style="width: 30%">
        <input type="text" v-model="searchWord" placeholder="검색할 단어를 입력하세요." style="width: 100%; border: 0px solid black; border-bottom: 1px solid black" />
      </form>
      <img src="@/assets/magnifying-glass.png" @click="searchVoca" alt="search" style="width: 2%; height: 2%; cursor: pointer" />
    </div>
    <div class="row d-flex justify-content-left py-3">
      <voca-list-item class="col-6" v-for="voca in myVocaList" :key="voca.personalWordId" v-bind="voca" @refresh-list="refreshList"></voca-list-item>
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
      onFav: false,
      searchWord: null,
      myVocaList: [],
    };
  },
  async created() {
    await wordStore.actions.getMyWordList(sessionStorage.getItem("current_user_num"));
    this.myVocaList = wordStore.getters.getMyWordList();
  },
  methods: {
    async searchVoca() {
      this.onFav = false;
      await wordStore.actions.getMyWordList(sessionStorage.getItem("current_user_num"), this.searchWord);
      this.myVocaList = wordStore.getters.getMyWordList();
    },

    async refreshList() {
      await wordStore.actions.getMyWordList(sessionStorage.getItem("current_user_num"));
      this.myVocaList = wordStore.getters.getMyWordList();
    },

    async changeFav() {
      if (this.onFav) {
        await wordStore.actions.getMyWordList(sessionStorage.getItem("current_user_num"));
        this.myVocaList = wordStore.getters.getMyWordList();
        this.onFav = false;
      } else {
        await wordStore.actions.getMyFavWordList(sessionStorage.getItem("current_user_num"));
        this.myVocaList = wordStore.getters.getMyFavWordList();
        this.onFav = true;
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
