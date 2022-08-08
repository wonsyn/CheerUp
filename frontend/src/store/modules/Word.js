import { getMyWordList, updateMyWord } from "@/api/Feature/Word";

const state = {
  myWordList: [],
};

const getters = {};

const mutations = {};

const actions = {
  async getMyWordList(userId) {
    getMyWordList(
      userId,
      ({ data }) => {
        mutations.SET_MY_WORD_LIST(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async updateMyword() {
    // const body = {
    // }
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
