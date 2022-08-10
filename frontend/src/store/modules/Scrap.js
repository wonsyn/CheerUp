import { getFeedInBoard } from "@/api/Feature/Feed";

const state = {
  scrapsInBoard: [],
};

const getters = {
  scrapsInBoard() {
    return state.scrapsInBoard;
  },
};

const mutations = {
  SET_SCRAP_IN_BOARD(scraps) {
    state.scrapsInBoard = scraps;
  },
};

const actions = {
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
