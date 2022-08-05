import userStore from "@/store/modules/User.js";
import feedStore from "@/store/modules/Feed.js";
import commentStore from "@/store/modules/Comment.js";

const store = {
  modules: {
    userStore,
    feedStore,
    commentStore,
  },
};

const useStore = function () {
  return store;
};

export default useStore;
