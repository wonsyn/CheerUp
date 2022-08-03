import { login } from "@/api/Feature/User.js";

const state = {
  isLogin: false,
};

const getters = {};

const mutations = {
  SET_IS_LOGIN: (isLogin) => {
    state.isLogin = isLogin;
  },
};

const actions = {
  async login(user) {
    await login(
      user,
      ({ data }) => {
        console.log(data);
        if (data["message"] === "success") {
          let access_token = data["access-token"];
          let refresh_token = data["refresh-token"];
          mutations.SET_IS_LOGIN(true);
          sessionStorage.setItem("access-token", access_token);
          sessionStorage.setItem("refresh_token", refresh_token);
        } else {
          mutations.SET_IS_LOGIN(false);
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
