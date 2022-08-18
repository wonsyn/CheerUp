import { getFeed, getFeedDetail, recommFeed } from "@/api/Feature/Feed.js";

const state = {
  feedList: [],
  feedDetail: null,
  recommList: [],
  commonFeedList: [],
};

const getters = {
  getFeedList() {
    return state.feedList;
  },
  getFeedDetail() {
    return state.feedDetail;
  },
  getRecommList() {
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
    await recommFeed(
      feedId,
      ({ data }) => {
        mutations.SET_RECOMM_LIST(data);
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
