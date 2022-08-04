import { getFeed } from "@/api/Feature/Feed.js";

const state = {
  feedList: [],
};

const getters = {
  getFeedList() {
    return state.feedList;
  },
};

const mutations = {
  SET_FEEDLIST(feedList) {
    state.feedList = feedList;
  },
};

const actions = {
  async getFeed(type, category) {
    const params = {
      type: type,
      industry: category,
    };
    await getFeed(
      params,
      ({ data }) => {
        console.log(data);
        mutations.SET_FEEDLIST(data);
      },
      (error) => {
        console.log(error);
      },
    );
    console.log("api end");
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
