import { getMyWordList, updateMyWord, deleteMyWord, addFavWord, removeFavWord } from "@/api/Feature/Word";

const state = {
  myWordList: [],
};

const getters = {
  getMyWordList() {
    return state.myWordList;
  },
};

const mutations = {
  SET_MY_WORDLIST(data) {
    state.myWordList = data;
  },
};

const actions = {
  async getMyWordList(userId) {
    await getMyWordList(
      userId,
      ({ data }) => {
        mutations.SET_MY_WORDLIST(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async updateMyword(wordId, wordExp) {
    const word = {
      personalWordExp: wordExp,
      personalWordId: wordId,
    };
    await updateMyWord(
      word,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async deleteMyWord(wordId) {
    await deleteMyWord(
      wordId,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async addFavWord(favWordId) {
    await addFavWord(
      favWordId,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async removeFavWord(favWordId) {
    await removeFavWord(
      favWordId,
      ({ data }) => {
        console.log(data);
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
