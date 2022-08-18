import { createBoard, getBoardList, updateBoard, deleteBoard } from "@/api/Feature/Feed";
import Swal from "sweetalert2";

const state = {
  board: {},
  boardList: {},
  isSuccess: false,
  boardCreateFailed: false,
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
  boardCreateFailed() {
    return state.boardCreateFailed;
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
  SET_CREATE_BOARD_FAILED(boardCreateFailed) {
    state.boardCreateFailed = boardCreateFailed;
  },
};

const actions = {
  async createBoard(params) {
    await createBoard(
      params,
      ({ data }) => {
        if (data["message"] === "success") {
          mutations.SET_BOARD_LIST(data);
          mutations.SET_CREATE_BOARD_FAILED(false);
          Swal.fire({
            position: "center",
            icon: "success",
            title: "보드 추가 성공!",
            showConfirmButton: false,
            timer: 1000,
          });
        } else {
          mutations.SET_CREATE_BOARD_FAILED(true);
        }
      },
      (error) => {
        console.log(error);
        mutations.SET_CREATE_BOARD_FAILED(true);
      },
    );
  },
  async getBoardList(userId) {
    await getBoardList(
      userId,
      ({ data }) => {
        mutations.SET_BOARD_LIST(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async updateBoard(params) {
    await updateBoard(
      params,
      ({ data }) => {
        if (data["message"] === "success") {
          mutations.SET_IS_SUCCESS(true);
          mutations.SET_BOARD_LIST(data);
        } else {
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
            mutations.SET_BOARD_LIST(data);
          }
        },
        (error) => {
          console.log(error);
        },
      );
    }
  },
  fetchBoard(board) {
    mutations.SET_BOARD(board);
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
