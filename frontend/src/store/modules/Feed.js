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
    console.log(state.feedList);
  },
};

const actions = {
  async getFeed(type) {
    await getFeed(
      type,
      ({ data }) => {
        console.log(data);
        mutations.SET_FEEDLIST(data);
        // if (data["message"] === "success") {
        //   mutations.SET_FEEDLIST(data["feedList"]);
        //   console.log(data["feedList"]);
        // }
        // return data;
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
