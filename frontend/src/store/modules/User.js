import { getUser, login, signup, follow, unfollow, getFollowerList, getFollowingList, isFollowing, searchById, checkId, updateUserInfo } from "@/api/Feature/User.js";
import main from "../../App.vue";
// import notification from "@/Notification.js";

const state = {
  profile: {},
  followerList: {},
  followingList: {},
  isFollowing: false,
  userList: [],
  socket: null,
  socketMessage: "",
  isValidId: false,
  socketUrl: "",
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
  socket() {
    return state.socket;
  },
  socketMessage() {
    return state.socketMessage;
  },
  isValidId() {
    return state.isValidId;
  },
  socketUrl() {
    return state.socketUrl;
  },
};

const mutations = {
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
  SET_SOCKET: (socket) => {
    state.socket = socket;
  },
  SET_SOCKETMESSAGE: (socketMessage) => {
    state.socketMessage = socketMessage;
  },
  SET_CHECK_ID: (isValidId) => {
    state.isValidId = isValidId;
  },
  SET_SOCKET_URL: (socketUrl) => {
    state.socketUrl = socketUrl;
  },
};

const actions = {
  async connect() {
    // var ws = new WebSocket("ws://localhost:3000/api/cheerup/ws?id=" + sessionStorage.getItem("current_user"));
    var ws = new WebSocket("wss://cheerup.kro.kr:3000/api/cheerup/ws?id=" + sessionStorage.getItem("current_user"));
    mutations.SET_SOCKET(ws);
    ws.onopen = async (msg) => {
      console.log("onopen connected", ws);
      ws.send(msg);
    };
    ws.onmessage = async (e) => {
      console.log("onmessage: ", e.data);
      mutations.SET_SOCKETMESSAGE(e.data);
      console.log(main.methods.getSocketMessage());
    };
    ws.onclose = async (e) => {
      console.log("disconnect", e);
      mutations.SET_SOCKET(null);
      this.connect();

      // setTimeout(() => {
      // }, 1000);
    };
    /*
    mutations.SET_SOCKET(ws);
    console.log("connect(): " + ws);
    state.socket.onmessage = function (e) {
      mutations.SET_SOCKETMESSAGE(e.data);
      console.log("리턴 데이터: ", e.data);
      console.log(e.data.alarmReceiverId);
      console.log("user.js: " + getters.socketMessage());
      console.log(main.methods.getSocketMessage());
    };
    state.socket.onopen = function (msg) {
      console.log("socket open", msg);
      // actions.sendMessage(msg);
      state.socket.send(msg);
    };
    state.socket.onclose = async function () {
      console.log("언제 꺼지냐");
      mutations.SET_SOCKET(null);
    };
    */
  },
  async login(user) {
    await login(
      user,
      ({ data }) => {
        if (data["message"] === "success") {
          let access_token = data["access-token"];
          let refresh_token = data["refresh-token"];
          sessionStorage.setItem("access-token", access_token);
          sessionStorage.setItem("refresh_token", refresh_token);
          sessionStorage.setItem("current_user", user.id);

          // 연결
          // this.connect();
          // 알람데이터 중 기한이 1주일 남은 것들 중 가장 빠른 것 1개만 가져오기
          // notification.getLatestAlarm();

          // console.log("로그인: ", user);
          // 있다면 toast

          // 없다면 걍 고
        } else {
          console.log("Login Fail");
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
        sessionStorage.setItem("current_user_nickname", data.userDetail.nickname);
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
        if (data["message"] != "success") {
          console.log(data);
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
        if (data["message"] === "success") {
          mutations.SET_USER_LIST(data.userList);
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
        if (data["message"] === "success") {
          mutations.SET_PROFILE(data.userDetail);
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
        if (data["message"] === "success") {
          mutations.SET_IS_FOLLOWING(true);
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
        if (data["message"] === "success") {
          mutations.SET_IS_FOLLOWING(false);
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
          mutations.SET_FOLLOWER_LIST(data.followList);
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
          mutations.SET_FOLLOWING_LIST(data.followList);
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
          mutations.SET_IS_FOLLOWING(data.follow);
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  logout() {
    sessionStorage.removeItem("access-token");
    sessionStorage.removeItem("refresh_token");
    sessionStorage.removeItem("current_user");
    sessionStorage.removeItem("current_user_num");
  },
  async checkId(param) {
    await checkId(
      param,
      ({ data }) => {
        if (data["message"] === "success") {
          mutations.SET_CHECK_ID(true);
        } else {
          console.log(data);
          mutations.SET_CHECK_ID(false);
        }
      },
      (error) => {
        console.log(error);
      },
    );
  },
  async updateuserInfo(profile) {
    await updateUserInfo(
      profile,
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
