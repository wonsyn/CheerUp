import { getUser, login, signup, follow, unfollow, getFollowerList, getFollowingList } from "@/api/Feature/User.js";

const state = {
  isLogin: false,
  currentUser: {},
  token: sessionStorage.getItem("token") || "",
  profile: {},
  followerList: {},
  followingList: {},
  isFollowing: false,
};

const getters = {
  profile() {
    return state.profile;
  },
  followerList() {
    return state.followerList;
  },
  followingList() {
    return state.followingList;
  },
  isFollowing() {
    return state.isFollowing;
  },
};

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
  SET_PROFILE: (user) => {
    state.profile = user;
  },
  SET_FOLLOWER_LIST: (followerList) => {
    state.followerList = followerList;
  },
  SET_FOLLOWING_LIST: (followingList) => {
    state.followingList = followingList;
  },
  SET_IS_FOLLOWING: (isFollowing) => {
    state.isFollowing = isFollowing;
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
  async getProfile(id) {
    await getUser(
      id,
      ({ data }) => {
        console.log(data);
        if (data["message"] === "success") {
          console.log(data.userDetail);
          mutations.SET_PROFILE(data.userDetail);
        } else {
          console.log("else");
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async follow(id) {
    await follow(
      id,
      ({ data }) => {
        console.log(data);
        if (data["message"] === "success") {
          mutations.SET_IS_FOLLOWING(true);
        } else {
          console.log("failed");
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async unfollow(id) {
    await unfollow(
      id,
      ({ data }) => {
        console.log(data);
        if (data["message"] === "success") {
          mutations.SET_IS_FOLLOWING(false);
        } else {
          console.log("failed");
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async getFollowerList(id) {
    await getFollowerList(
      id,
      ({ data }) => {
        if (data["message"] === "success") {
          console.log(data);
          mutations.SET_FOLLOWER_LIST(data.followerList);
        } else {
          console.log("failed");
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async getFollowingList(id) {
    await getFollowingList(
      id,
      ({ data }) => {
        if (data["message"] === "success") {
          console.log(data);
          mutations.SET_FOLLOWING_LIST(data.followingList);
        } else {
          console.log("failed");
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
