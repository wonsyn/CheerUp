<template>
  <div class="container">
    <div class="row">
      <div class="col-11">기사 제목</div>
      <div class="col-1">
        <button class="btn">Scrap</button>
      </div>
    </div>
    <div>컨텐츠 내용</div>
    <hr />
    <voca-list-item></voca-list-item>
    <div id="voca_add_window" class="px-3 pb-3" style="display: none">
      <input id="voca_name_input" class="px-3 py-1 mb-2" type="text" placeholder="단어명" style="font-size: 14px; width: 100%; border-radius: 7px" /> <br />
      <textarea name="voca_add_textarea" id="voca_add_textarea" class="px-2" placeholder="단어 내용.." style="font-size: 13px; width: 100%; height: 130px; border-radius: 7px"></textarea>
      <div class="d-flex justify-content-end">
        <button id="voca_add_btn" class="me-2 btn btn-primary" @click="addVoca" style="font-size: 15px; height: 30px">추가</button>
        <button id="voca_cancel_btn" class="btn btn-danger" @click="closeVocaAddWindow" style="font-size: 15px; height: 30px">취소</button>
      </div>
    </div>
    <div class="d-flex">
      <button id="vocalist_add_btn" class="btn ms-3 mt-2" @click="openVocaAddWindow" style="font-size: 13px; font-weight: bold; background-color: #00dd99; color: white">내 단어장에 추가</button>
    </div>
    <hr />
    <div class="text-start ps-3" style="font-size: 13px; font-weight: bold">댓글</div>
    <comment-list-item></comment-list-item>

    <div class="d-flex justify-content-start px-3 pt-3">
      <img class="me-2" :src="loginUserInfo.userImgUrl" alt="profile" style="width: 20px; height: 20px" />
      <span class="me-2" style="font-weight: bold">{{ loginUserId }}</span>
      <input type="text" id="input_comment_create" placeholder="댓글을 입력하세요." style="font-size: 14px; width: 100%; border-radius: 7px" />
    </div>
    <hr />
    <div class="text-start ps-3 mb-3" style="font-size: 13px; font-weight: bold">~~~님이 좋아할 만한 기사들</div>
    <!-- <feed-list></feed-list> -->
  </div>
</template>

<script>
import CommentListItem from "@/components/CommentListItem.vue";
import VocaListItem from "@/components/VocaListItem.vue";
import useStore from "@/store";
// import FeedList from "@/components/FeedList.vue";

const userStore = useStore().modules.userStore;

export default {
  components: {
    CommentListItem,
    VocaListItem,
    // FeedList,
  },
  data() {
    return {
      loginUserId: sessionStorage.getItem("user_id"),
      loginUserInfo: Object,
    };
  },
  async created() {
    await userStore.actions.getProfile(this.loginUserId);
    this.loginUserInfo = userStore.getters.profile();
  },
  methods: {
    openVocaAddWindow() {
      const vocaWindow = document.getElementById("voca_add_window");
      const vocaListAddButton = document.getElementById("vocalist_add_btn");

      vocaWindow.classList.add("d-block");
      vocaListAddButton.classList.add("d-none");
      vocaWindow.classList.remove("d-none");
      vocaListAddButton.classList.remove("d-block");
    },
    closeVocaAddWindow() {
      const vocaWindow = document.getElementById("voca_add_window");
      const vocaListAddButton = document.getElementById("vocalist_add_btn");

      vocaWindow.classList.add("d-none");
      vocaListAddButton.classList.add("d-block");
      vocaWindow.classList.remove("d-block");
      vocaListAddButton.classList.remove("d-none");
    },
    addVoca() {
      const vocaName = document.getElementById("voca_name_input").value;
      const vocaDesc = document.getElementById("voca_add_textarea").value;

      console.log("Name: " + vocaName);
      console.log("Desc: " + vocaDesc);
    },
  },
};
</script>

<style scoped>
.spaced {
  display: none;
}
</style>
