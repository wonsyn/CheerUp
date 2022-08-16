import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}
async function signup(user, success, fail) {
  await api.post(`/user/signup`, JSON.stringify(user)).then(success).catch(fail);
}
async function getUser(id, success, fail) {
  await api
    .get(`/user/detail/${id}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function isFollowing(id, success, fail) {
  await api
    .get(`/follow/status/${id}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function follow(id, success, fail) {
  await api
    .post(`/follow`, { id: id }, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function unfollow(id, success, fail) {
  await api
    .delete(`/follow/delete/${id}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function getFollowerList(params, success, fail) {
  await api
    .get(`/follow/followerlist`, { params: { id: params }, headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function getFollowingList(params, success, fail) {
  await api
    .get(`/follow/followinglist`, { params: { id: params }, headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function searchById(params, success, fail) {
  await api
    .get(`/user/searchById`, { params: { id: params }, headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function checkId(param, success, fail) {
  await api
    .get(`/user/checkid`, { params: { id: param } })
    .then(success)
    .catch(fail);
}
async function updateUserInfo(body, success, fail) {
  await api
    .put(`/user/update`, JSON.stringify(body), { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

export { login, signup, getUser, isFollowing, follow, unfollow, getFollowerList, getFollowingList, searchById, checkId, updateUserInfo };
