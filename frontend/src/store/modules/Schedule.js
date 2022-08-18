import { getSchedule, updateSchedule, deleteSchedule, addSchedule } from "@/api/Feature/Schedule.js";
import Swal from "sweetalert2";
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
  async addSchedule(scheduleTitle, scheduleCompany, scheduleDate, scheduleMemo) {
    const body = {
      userId: sessionStorage.getItem("current_user_num"),
      scheduleTitle: scheduleTitle,
      scheduleCompany: scheduleCompany,
      scheduleDate: scheduleDate,
      scheduleMemo: scheduleMemo,
    };
    await addSchedule(
      body,
      ({ data }) => {
        Swal.fire({
          position: "center",
          icon: "success",
          title: "일정 추가 성공!",
          showConfirmButton: false,
          timer: 1000,
        });
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async updateSchedule(scheduleId, scheduleTitle, scheduleCompany, scheduleDate, scheduleMemo) {
    const body = {
      scheduleId: scheduleId,
      scheduleTitle: scheduleTitle,
      scheduleCompany: scheduleCompany,
      scheduleDate: scheduleDate,
      scheduleMemo: scheduleMemo,
    };
    await updateSchedule(
      body,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async deleteSchedule(scheduleId) {
    await deleteSchedule(
      scheduleId,
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
