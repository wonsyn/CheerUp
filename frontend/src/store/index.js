import userStore from "@/store/modules/User.js";
import feedStore from "@/store/modules/Feed.js";

const store = {
  modules: {
    userStore,
    feedStore,
  },
};

const useStore = function () {
  return store;
};

export default useStore;
