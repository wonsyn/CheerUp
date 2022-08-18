<template>
  <div v-if="!isDeleted">
    <div v-if="!isEdit" class="py-3 px-5 my-3" style="background-color: #eeeeee88; border-radius: 10px">
      <div class="d-flex">
        <span class="ps-2" style="font-weight: bold; font-size: 20px">{{ personalWord }}</span>
      </div>
      <hr />
      <div style="text-align: left; font-size: 14px">{{ wordExp }}</div>
      <br />
      <div class="d-flex">
        <div class="me-auto">{{ personalWordDate }}</div>
        <img class="me-2" @click="openWordEditWindow" src="@/assets/edit.png" alt="edit" style="width: 4%; height: 4%" />
        <img v-if="isFav" class="me-2" @click="updateFav" src="@/assets/star_filled.png" alt="fav" style="width: 4%; height: 4%" />
        <img v-else class="me-2" @click="updateFav" src="@/assets/star_outline.png" alt="fav" style="width: 4%; height: 4%" />
        <img src="@/assets/delete.png" @click="deleteWord" alt="delete" style="width: 4%; height: 4%" />
      </div>
    </div>
    <div v-else class="py-3 px-5 my-3" style="background-color: #eeeeee88; border-radius: 10px">
      <div class="d-flex">
        <span class="ps-2" style="font-weight: bold; font-size: 20px">{{ personalWord }}</span>
      </div>
      <hr />
      <textarea
        name="voca_add_textarea"
        id="voca_add_textarea"
        class="px-2"
        v-model="wordExp"
        placeholder="단어 내용.."
        style="font-size: 13px; width: 100%; height: 130px; border-radius: 7px"
      ></textarea>
      <div class="d-flex justify-content-end">
        <button id="voca_add_btn" class="me-2 btn btn-info" @click="editWord" style="font-size: 15px; height: 30px">수정</button>
        <button id="voca_cancel_btn" class="btn btn-danger" @click="cancelWordEdit" style="font-size: 15px; height: 30px">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
import useStore from "@/store/index.js";

const wordStore = useStore().modules.wordStore;

export default {
  props: {
    personalWordId: Number,
    userId: Number,
    personalWord: String,
    personalWordExp: String,
    personalFavWord: Number,
    personalWordDate: String,
  },
  data() {
    return {
      isFav: false,
      isEdit: false,
      isDeleted: false,
      wordExp: this.personalWordExp,
      originalExp: String,
    };
  },
  created() {
    if (this.personalFavWord == 0) {
      this.isFav = false;
    } else if (this.personalFavWord == 1) {
      this.isFav = true;
    }
  },
  methods: {
    openWordEditWindow() {
      this.originalExp = this.wordExp;
      this.isEdit = true;
    },
    closeWordEditWindow() {
      this.isEdit = false;
    },
    async editWord() {
      await wordStore.actions.updateMyword(this.personalWordId, this.wordExp);
      this.closeWordEditWindow();
    },
    cancelWordEdit() {
      this.wordExp = this.originalExp;
      this.closeWordEditWindow();
    },
    async deleteWord() {
      if (confirm("단어를 삭제하시겠습니까?")) {
        await wordStore.actions.deleteMyWord(this.personalWordId);
        this.isDeleted = true;
        this.$emit("refresh");
      }
    },
    async updateFav() {
      if (this.isFav) {
        await wordStore.actions.removeFavWord(this.personalWordId);
        this.isFav = false;
      } else {
        await wordStore.actions.addFavWord(this.personalWordId);
        this.isFav = true;
      }
    },
  },
};
</script>

<style scoped>
hr {
  background-color: #00dd99;
  height: 3px;
  border: 0px;
}
</style>
