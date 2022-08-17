import userStore from "@/store/modules/User.js";
import feedStore from "@/store/modules/Feed.js";
import commentStore from "@/store/modules/Comment.js";
import wordStore from "@/store/modules/Word.js";
import boardStore from "@/store/modules/Board.js";
import scrapStore from "@/store/modules/Scrap.js";
import scheduleStore from "@/store/modules/Schedule.js";
import notificationStore from "@/store/modules/Notification.js";

const store = {
  modules: {
    userStore,
    feedStore,
    commentStore,
    wordStore,
    boardStore,
    scrapStore,
    scheduleStore,
    notificationStore,
  },
};

const useStore = function () {
  return store;
};

export default useStore;
