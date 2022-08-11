import { getSchedule } from "@/api/Feature/Schedule.js";

const state = {
  scheduleList: [],
};

const getters = {
  getScheduleList() {
    return state.scheduleList;
  },
};

const mutations = {
  SET_SCHEDULE_LIST(data) {
    console.log("mutations", data);
    state.scheduleList = data;
  },
};

const actions = {
  async getSchedule() {
    let today = new Date();
    let year = today.getFullYear();
    let month = today.getMonth() + 1;
    let day = today.getDate();

    let date = {
      scheduleDate: year + "-" + month + "-" + day,
    };
    await getSchedule(
      date,
      ({ data }) => {
        mutations.SET_SCHEDULE_LIST(data.scheduleList);
        console.log(data.scheduleList);
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
