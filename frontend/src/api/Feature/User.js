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
  await api.get(`/follow/status/${id}`, id).then(success).catch(fail);
}
async function follow(id, success, fail) {
  await api.post(`/follow/status/${id}`, id).then(success).catch(fail);
}
async function unfollow(id, success, fail) {
  await api.delete(`/follow/delete/${id}`, id).then(success).catch(fail);
}

export { login, signup, getUser, isFollowing, follow, unfollow };
