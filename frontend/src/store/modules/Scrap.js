import { getFeedInBoard, getScrapList } from "@/api/Feature/Feed";

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
        if (data["message"] === "success") {
          mutations.SET_SCRAP_LIST(data);
          console.log("success");
          console.log(data);
        } else {
          console.log("failed");
          console.log(data);
        }
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
        if (data["message"] === "success") {
          mutations.SET_SCRAP_IN_BOARD(data);
          console.log("success");
          console.log(data);
        } else {
          console.log("failed");
          console.log(data);
        }
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
