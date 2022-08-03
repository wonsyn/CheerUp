import userStore from "@/store/modules/User.js";

const store = {
  modules: {
    userStore,
  },
};

const useStore = function () {
  return store;
};

export default useStore;
