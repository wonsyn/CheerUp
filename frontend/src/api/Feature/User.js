import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}
async function signup(user, success, fail) {
  await api.post(`/user/signup`, JSON.stringify(user)).then(success).catch(fail);
}
async function getUser(id, success, fail) {
  await api.get(`/user/detail/${id}`, id).then(success).catch(fail);
}
async function isFollowing(id, success, fail) {
  const access_token = sessionStorage.getItem("access-token");
  await api
    .get(`/follow/status/${id}`, { id: id, headers: { Authorization: access_token } })
    .then(success)
    .catch(fail);
}
async function follow(id, success, fail) {
  const access_token = sessionStorage.getItem("access-token");
  await api
    .post(`/follow/status/${id}`, { id: id, headers: { Authorization: access_token } })
    .then(success)
    .catch(fail);
}
async function unfollow(id, success, fail) {
  const access_token = sessionStorage.getItem("access-token");
  await api
    .delete(`/follow/delete/${id}`, { id: id, headers: { Authorization: access_token } })
    .then(success)
    .catch(fail);
}
async function getFollowerList(params, success, fail) {
  const access_token = sessionStorage.getItem("access-token");
  await api
    .get(`/follow/followerlist`, {
      params: {
        id: params.id,
      },
      headers: { Authorization: access_token },
    })
    .then(success)
    .catch(fail);
}
async function getFollowingList(params, success, fail) {
  const access_token = sessionStorage.getItem("access-token");
  await api
    .get(`/follow/followinglist`, {
      params: {
        id: params.id,
      },
      headers: { Authorization: access_token },
    })
    .then(success)
    .catch(fail);
}

export { login, signup, getUser, isFollowing, follow, unfollow, getFollowerList, getFollowingList };
