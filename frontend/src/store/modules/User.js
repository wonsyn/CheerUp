import { login, signup } from "@/api/Feature/User.js";

const state = {
  isLogin: false,
  currentUser: {},
  token: localStorage.getItem("token") || "",
};

const getters = {};

const mutations = {
  SET_IS_LOGIN: (isLogin) => {
    state.isLogin = isLogin;
  },
  SET_TOKEN: (token) => {
    state.token = token;
  },
  SET_CURRENT_USER: (user) => {
    state.currentUser = user;
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
  async signup(user) {
    await signup(
      user,
      ({ data }) => {
        console.log(data);
        if (data["message"] === "success") {
          console.log(data);
          console.log(user);
        } else {
          console.log(data);
          console.log(user);
          mutations.SET_IS_LOGIN(false);
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  saveToken(token) {
    localStorage.setItem("token", token);
    mutations.SET_TOKEN(token);
  },
  removeToken() {
    localStorage.setItem("token", "");
    mutations.SET_TOKEN("");
  },
  fetchCurrentUser(user) {
    mutations.SET_CURRENT_USER(user);
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};
