import { getMyWordList, updateMyWord, deleteMyWord, addFavWord, removeFavWord, getMyFavWordList, getDBWordList, addMyWord, searchMyWordList } from "@/api/Feature/Word";

const state = {
  myWordList: [],
  myFavWordList: [],
  DBWordList: [],
};

const getters = {
  getMyWordList() {
    return state.myWordList;
  },
  getMyFavWordList() {
    return state.myFavWordList;
  },
  getDBWordList() {
    return state.DBWordList;
  },
};

const mutations = {
  SET_MY_WORDLIST(data) {
    state.myWordList = data;
  },
  SET_MY_FAVWORDLIST(data) {
    state.myFavWordList = data;
  },
  SET_DBWORDLIST(data) {
    state.DBWordList = data;
  },
};

const actions = {
  async getMyWordList(userId, keyword) {
    if (keyword == null) {
      await getMyWordList(
        userId,
        ({ data }) => {
          mutations.SET_MY_WORDLIST(data);
        },
        (error) => {
          console.log(error);
        },
      );
    } else {
      const params = {
        userId: userId,
        keyword: keyword,
      };
      await searchMyWordList(
        params,
        ({ data }) => {
          mutations.SET_MY_WORDLIST(data);
        },
        (error) => {
          console.log(error);
        },
      );
    }
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

  async getMyFavWordList(userId) {
    await getMyFavWordList(
      userId,
      ({ data }) => {
        mutations.SET_MY_FAVWORDLIST(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async getDBWordList() {
    await getDBWordList(
      ({ data }) => {
        mutations.SET_DBWORDLIST(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },

  async addMyWord(personalWord, personalWordExp) {
    const body = {
      userId: sessionStorage.getItem("current_user_num"),
      personalWord: personalWord,
      personalWordExp: personalWordExp,
      personalFavWord: 0,
    };
    console.log(body);
    await addMyWord(
      body,
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
