import { createBoard, getBoardList, updateBoard, deleteBoard } from "@/api/Feature/Feed";

const state = {
  board: {},
  boardList: {},
  isSuccess: false,
};

const getters = {
  boardList() {
    return state.boardList;
  },
  isSuccess() {
    return state.isSuccess;
  },
  board() {
    return state.board;
  },
};

const mutations = {
  SET_BOARD_LIST(boardList) {
    state.boardList = boardList;
  },
  SET_IS_SUCCESS(isSuccess) {
    state.isSuccess = isSuccess;
  },
  SET_BOARD(board) {
    state.board = board;
  },
};

const actions = {
  async createBoard(params) {
    await createBoard(
      params,
      ({ data }) => {
        if (data["message"] === "success") {
          console.log(data);
          mutations.SET_BOARD_LIST(data);
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
      },
      (error) => {
        console.log(error);
      },
    );
  },
  getBoard(boardId) {
    for (let board in state.boardList) {
      if (board.boardId === boardId) {
        mutations.SET_BOARD(board);
        break;
      } else {
        console.log(boardId, "not found");
      }
    }
  },
  async updateBoard(params) {
    await updateBoard(
      params,
      ({ data }) => {
        if (data["message"] === "success") {
          console.log(data);
          mutations.SET_IS_SUCCESS(true);
          mutations.SET_BOARD_LIST(data);
        } else {
          console.log("update board failed");
          mutations.SET_IS_SUCCESS(false);
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async deleteBoard(boardId) {
    if (confirm("정말 삭제하시겠습니까?")) {
      await deleteBoard(
        boardId,
        ({ data }) => {
          if (data["message"] === "success") {
            console.log(data);
            mutations.SET_BOARD_LIST(data);
          } else {
            console.log("delete board failed");
          }
        },
        (error) => {
          console.log(error);
        },
      );
    }
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
