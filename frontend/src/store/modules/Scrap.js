import { getFeedInBoard, getScrapList, createScrap, deleteScrap, editScrap } from "@/api/Feature/Feed";
import useStore from "@/store/index.js";
import Swal from "sweetalert2";

const state = {
  scrapsInBoard: [],
  scrapList: [],
};

const getters = {
  scrapsInBoard() {
    return state.scrapsInBoard;
  },
  scrapList() {
    return state.scrapList;
  },
};

const mutations = {
  SET_SCRAP_IN_BOARD(scraps) {
    state.scrapsInBoard = scraps;
  },
  SET_SCRAP_LIST(scrapList) {
    state.scrapList = scrapList;
  },
};

const actions = {
  async getScrapList(param) {
    await getScrapList(
      param,
      ({ data }) => {
        mutations.SET_SCRAP_LIST(data);
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async getFeedInBoard(params) {
    await getFeedInBoard(
      params,
      ({ data }) => {
        mutations.SET_SCRAP_IN_BOARD(data);
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async createScrap(params) {
    if (useStore().modules.userStore.getters.socket() === null || useStore().modules.userStore.getters.socket().readyState === 3) {
      console.log("scrap socket disconnected");
      await useStore().modules.userStore.actions.connect();
    }

    await useStore().modules.userStore.actions.getFollowerList(sessionStorage.getItem("current_user"));
    let followerList = useStore().modules.userStore.getters.followerList();

    await createScrap(
      params,
      ({ data }) => {
        console.log(data);
        console.log("sessionid: ", sessionStorage.getItem("current_user"));
        console.log("scrapData: ", params);

        console.log("팔로워 리스트: ", followerList);
        let socketMsg = "";
        followerList.forEach(function (item) {
          socketMsg = "scrap," + sessionStorage.getItem("current_user") + "," + item.id + "," + params.feedId + ",0";
          useStore().modules.userStore.getters.socket().onopen(socketMsg);
        });

        // for (var follower in followerList) {
        //   console.log(follower.id);
        //   // socketMsg = "scrap," + sessionStorage.getItem("current_user") + "," + follower.id + "," + params.feedId + ",0";
        //   // useStore().modules.userStore.getters.socket().onopen(socketMsg);
        // }

        // this.socket.onopen(socketMsg);
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async deleteScrap(param) {
    await deleteScrap(
      param,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async editScrap(params) {
    await editScrap(
      params,
      ({ data }) => {
        console.log(data);
        Swal.fire({
          position: "center",
          icon: "success",
          title: "스크랩 저장 성공!",
          showConfirmButton: false,
          timer: 1000,
        });
      },
      (error) => {
        console.log(error);
      },
    );
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
