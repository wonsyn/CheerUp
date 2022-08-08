import { createBoard, getBoard } from "@/api/Feature/Feed";

const state = {};

const getters = {};

const mutations = {};

const actions = {
  async createBoard(params) {
    await createBoard(
      params,
      ({ data }) => {
        if (data["message"] === "success") {
          console.log(data);
        } else {
          console.log("create board failed");
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async getBoard(userId) {
    await getBoard(
      userId,
      ({ data }) => {
        if (data["message"] === "success") {
          console.log(data);
        } else {
          console.log("board list failed");
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
