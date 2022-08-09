import userStore from "@/store/modules/User.js";
import feedStore from "@/store/modules/Feed.js";
import commentStore from "@/store/modules/Comment.js";
import boardStore from "@/store/modules/Board";
import scrapStore from "@/store/modules/Scrap";

const store = {
  modules: {
    userStore,
    feedStore,
    commentStore,
    boardStore,
    scrapStore,
  },
};

const useStore = function () {
  return store;
};

export default useStore;
