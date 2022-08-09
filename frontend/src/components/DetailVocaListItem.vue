<template>
  <div>
    <div id="voca_item" class="py-2 d-flex" @mouseover="displayBtn" @mouseout="hideBtn" style="border-radius: 5px">
      <div class="me-auto">
        <div id="word_name" class="text-start ps-5 pb-3" style="font-size: 13px; font-weight: bold">단어명</div>
        <div id="word_desc" class="text-break text-start px-3" style="font-size: 12px">단어내용단어내용단어내용단어내용단어내용단어내용단어내용단어내용단어내용</div>
      </div>
      <div id="voca_edit" class="d-none align-middle my-auto me-3">
        <button id="btn_voca_edit" class="btn me-1" @click="openVocaEditWindow" style="background-color: #eae784; height: 25px; font-size: 11px">수정 및 추가</button>
      </div>
    </div>
    <div id="voca_edit_window" class="d-none">
      <input id="voca_edit_input" class="px-3 py-1 mb-2" type="text" placeholder="단어명" style="font-size: 14px; width: 100%; border-radius: 7px" /> <br />
      <textarea name="voca_edit_textarea" id="voca_edit_textarea" class="px-2" placeholder="단어 내용.." style="font-size: 13px; width: 100%; height: 130px; border-radius: 7px"></textarea>
      <div class="d-flex justify-content-end">
        <button id="voca_edit_btn" class="me-2 btn btn-primary" @click="editVoca" style="font-size: 15px; height: 30px">수정 및 추가</button>
        <button id="voca_cancel_btn" class="btn btn-danger" @click="closeVocaEditWindow" style="font-size: 15px; height: 30px">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    displayBtn() {
      const btnWindow = document.getElementById("voca_edit");
      const vocaItem = document.getElementById("voca_item");

      btnWindow.classList.add("d-block");
      btnWindow.classList.remove("d-none");
      vocaItem.style.backgroundColor = "lightgrey";
    },
    hideBtn() {
      const btnWindow = document.getElementById("voca_edit");
      const vocaItem = document.getElementById("voca_item");
      btnWindow.classList.add("d-none");
      btnWindow.classList.remove("d-block");
      vocaItem.style.backgroundColor = "white";
    },
    openVocaEditWindow() {
      const vocaItem = document.getElementById("voca_item");
      const vocaEditWindow = document.getElementById("voca_edit_window");

      vocaItem.classList.remove("d-block");
      vocaItem.classList.add("d-none");
      vocaEditWindow.classList.remove("d-none");
      vocaEditWindow.classList.add("d-block");

      const wordName = document.getElementById("word_name").innerText;
      const wordDesc = document.getElementById("word_desc").innerText;
      const vocaEditInput = document.getElementById("voca_edit_input");
      const vocaEditTextarea = document.getElementById("voca_edit_textarea");

      vocaEditInput.value = wordName;
      vocaEditTextarea.value = wordDesc;
    },
    closeVocaEditWindow() {
      const vocaItem = document.getElementById("voca_item");
      const vocaEditWindow = document.getElementById("voca_edit_window");

      vocaItem.classList.remove("d-none");
      vocaItem.classList.add("d-block");
      vocaEditWindow.classList.remove("d-block");
      vocaEditWindow.classList.add("d-none");

      const vocaEditInput = document.getElementById("voca_edit_input");
      const vocaEditTextarea = document.getElementById("voca_edit_textarea");

      vocaEditInput.value = "";
      vocaEditTextarea.value = "";
    },
    editVoca() {},
  },
};
</script>

<style scoped></style>
