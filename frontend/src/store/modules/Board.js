import { createBoard, getBoardList } from "@/api/Feature/Feed";

const state = {
  board: {},
  boardList: {},
};

const getters = {
  boardList() {
    return state.boardList;
  },
};

const mutations = {
  SET_BOARD_LIST(boardList) {
    state.boardList = boardList;
  },
};

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
  async getBoardList(userId) {
    await getBoardList(
      userId,
      ({ data }) => {
        console.log(data);
        mutations.SET_BOARD_LIST(data);
        if (data["message"] === "success") {
          console.log(data);
          mutations.SET_BOARD_LIST(data);
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
