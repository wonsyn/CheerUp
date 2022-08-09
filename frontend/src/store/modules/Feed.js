import { getFeed, getFeedDetail } from "@/api/Feature/Feed.js";

const state = {
  feedList: [],
  feedDetail: null,
};

const getters = {
  getFeedList() {
    return state.feedList;
  },
  getFeedDetail() {
    return state.feedDetail;
  },
};

const mutations = {
  SET_FEEDLIST(feedList) {
    state.feedList = feedList;
  },
  SET_FEED_DETAIL(feedDetail) {
    state.feedDetail = feedDetail;
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

  async getFeedDetail(feedId) {
    await getFeedDetail(
      feedId,
      ({ data }) => {
        mutations.SET_FEED_DETAIL(data);
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
