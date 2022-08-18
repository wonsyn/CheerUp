import { getNotice, checkNotice, getLatestAlarm } from "@/api/Feature/Notification.js";

const state = {
  notices: [],
  alarmDetail: {},
};

const getters = {
  getNotices() {
    return state.notices;
  },
  getAlarm() {
    return state.alarmDetail;
  },
};

const mutations = {
  SET_NOTICES(data) {
    state.notices = data;
  },
  SET_ALARMDETAIL(alarmDetail) {
    state.alarmDetail = alarmDetail;
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
  async getLatestAlarm() {
    await getLatestAlarm(
      ({ data }) => {
        if (data.message == "success") {
          mutations.SET_ALARMDETAIL(data.alarmDetail);
        } else {
          console.log(data);
        }
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
