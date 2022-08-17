<template>
  <div>
    <div :id="'voca_item' + wordId" class="py-2 d-flex" @mouseover="displayBtn" @mouseout="hideBtn" style="border-radius: 5px">
      <div class="me-auto">
        <div :id="'word_name' + wordId" class="text-start ps-3 pb-1" style="font-size: 13px; font-weight: bold">{{ word }}</div>
        <div :id="'word_desc' + wordId" class="text-break text-start px-3" style="font-size: 12px">{{ curExp }}</div>
      </div>
      <div :id="'voca_edit' + wordId" class="d-none align-middle my-auto me-3">
        <button :id="'btn_voca_edit' + wordId" class="btn me-1 btn-info" @click="openVocaEditWindow" style="height: 25px; font-size: 11px">수정 및 추가</button>
      </div>
    </div>
    <div :id="'voca_edit_window' + wordId" class="d-none">
      <div :id="'voca_edit_title' + wordId" class="px-3 py-1 mb-2 d-flex" type="text" placeholder="단어명" style="font-size: 14px; font-weight: bold; width: 100%; border-radius: 7px">{{ word }}</div>
      <textarea
        v-model="curExp"
        name="voca_edit_textarea"
        :id="'voca_edit_textarea' + wordId"
        class="px-2"
        placeholder="단어 내용.."
        style="font-size: 13px; width: 100%; height: 130px; border-radius: 7px"
      ></textarea>
      <div class="d-flex justify-content-end">
        <button :id="'voca_edit_btn' + wordId" class="me-2 btn btn-primary" @click="editVoca" style="font-size: 15px; height: 30px">수정 및 추가</button>
        <button :id="'voca_cancel_btn' + wordId" class="btn btn-danger" @click="closeVocaEditWindow" style="font-size: 15px; height: 30px">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
import useStore from "@/store";

const wordStore = useStore().modules.wordStore;

export default {
  props: {
    word: String,
    wordExp: String,
    wordId: Number,
  },
  data() {
    return {
      curExp: this.wordExp,
    };
  },
  created() {
    console.log("voca created");
  },
  methods: {
    displayBtn() {
      const btnWindow = document.getElementById("voca_edit" + this.wordId);
      const vocaItem = document.getElementById("voca_item" + this.wordId);

      btnWindow.classList.add("d-block");
      btnWindow.classList.remove("d-none");
      vocaItem.style.backgroundColor = "#eeeeee";
    },
    hideBtn() {
      const btnWindow = document.getElementById("voca_edit" + this.wordId);
      const vocaItem = document.getElementById("voca_item" + this.wordId);
      btnWindow.classList.add("d-none");
      btnWindow.classList.remove("d-block");
      vocaItem.style.backgroundColor = "white";
    },
    openVocaEditWindow() {
      const vocaItem = document.getElementById("voca_item" + this.wordId);
      const vocaEditWindow = document.getElementById("voca_edit_window" + this.wordId);

      vocaItem.classList.remove("d-block");
      vocaItem.classList.add("d-none");
      vocaEditWindow.classList.remove("d-none");
      vocaEditWindow.classList.add("d-block");

      const wordName = document.getElementById("word_name" + this.wordId).innerText;
      const wordDesc = document.getElementById("word_desc" + this.wordId).innerText;
      const vocaEditInput = document.getElementById("voca_edit_title" + this.wordId);
      const vocaEditTextarea = document.getElementById("voca_edit_textarea" + this.wordId);

      vocaEditInput.value = wordName;
      vocaEditTextarea.value = wordDesc;
    },
    closeVocaEditWindow() {
      const vocaItem = document.getElementById("voca_item" + this.wordId);
      const vocaEditWindow = document.getElementById("voca_edit_window" + this.wordId);

      vocaItem.classList.remove("d-none");
      vocaItem.classList.add("d-block");
      vocaEditWindow.classList.remove("d-block");
      vocaEditWindow.classList.add("d-none");

      const vocaEditInput = document.getElementById("voca_edit_title" + this.wordId);
      const vocaEditTextarea = document.getElementById("voca_edit_textarea" + this.wordId);

      vocaEditInput.value = "";
      vocaEditTextarea.value = "";
    },
    async editVoca() {
      await wordStore.actions.addMyWord(this.word, this.curExp);

      this.closeVocaEditWindow();
    },
  },
};
</script>

<style scoped></style>
