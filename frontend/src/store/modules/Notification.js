import { getNotice, checkNotice } from "@/api/Feature/Notification.js";

const state = {
  notices: [],
};

const getters = {
  getNotices() {
    return state.notices;
  },
};

const mutations = {
  SET_NOTICES(data) {
    state.notices = data;
  },
};

const actions = {
  async getNotice() {
    await getNotice(
      ({ data }) => {
        if (data.message == "success") {
          mutations.SET_NOTICES(data.alarmList);
        } else {
          console.log(data);
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async checkNotice(noticeId) {
    await checkNotice(
      noticeId,
      ({ data }) => {
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
