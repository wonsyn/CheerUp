import { getUser, login, signup, follow, unfollow, getFollowerList, getFollowingList, isFollowing, searchById } from "@/api/Feature/User.js";

const state = {
  isLogin: false,
  profile: {},
  followerList: {},
  followingList: {},
  isFollowing: false,
  userList: [],
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
  userList() {
    return state.userList;
  },
};

const mutations = {
  SET_IS_LOGIN: (isLogin) => {
    state.isLogin = isLogin;
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
  SET_USER_LIST: (userList) => {
    state.userList = userList;
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
          sessionStorage.setItem("current_user", user.id);
        } else {
          mutations.SET_IS_LOGIN(false);
        }
      },
      (error) => {
        console.log(error);
      },
    );
    await getUser(
      user.id,
      ({ data }) => {
        sessionStorage.setItem("current_user_num", data.userDetail.userId);
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
          console.log("signup failed");
          mutations.SET_IS_LOGIN(false);
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async searchById(id) {
    await searchById(
      id,
      ({ data }) => {
        console.log(data);
        if (data["message"] === "success") {
          console.log(data);
          mutations.SET_USER_LIST(data.userList);
        } else {
          console.log("search failed");
        }
      },
      (error) => {
        console.log(error);
      },
    );
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
          mutations.SET_FOLLOWER_LIST(data.followList);
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
          mutations.SET_FOLLOWING_LIST(data.followList);
        } else {
          console.log("failed");
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async isFollowing(id) {
    await isFollowing(
      id,
      ({ data }) => {
        if (data["message"] === "success") {
          console.log("is following");
          console.log(data);
          mutations.SET_IS_FOLLOWING(data.follow);
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
