import { getFeed, getFeedDetail, recommFeed } from "@/api/Feature/Feed.js";

const state = {
  feedList: [],
  feedDetail: null,
  recommList: [],
};

const getters = {
  getFeedList() {
    return state.feedList;
  },
  getFeedDetail() {
    return state.feedDetail;
  },
  getRecommList() {
    console.log("getRecommList");
    return state.recommList;
  },
};

const mutations = {
  SET_FEEDLIST(feedList) {
    state.feedList = feedList;
  },
  SET_FEED_DETAIL(feedDetail) {
    state.feedDetail = feedDetail;
  },
  SET_RECOMM_LIST(recommList) {
    console.log("SET_RECOMM_LIST");
    state.recommList = recommList;
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

  async recommFeed(feedId) {
    console.log("call recommFeed");
    await recommFeed(
      feedId,
      ({ data }) => {
        console.log("call recommFeed2");
        mutations.SET_RECOMM_LIST(data);
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
