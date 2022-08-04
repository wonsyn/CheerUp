import { apiInstance } from "@/api/index.js";

const api = apiInstance();
const access_token = sessionStorage.getItem("access-token");

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
  await api
    .get(`/follow/status/${id}`, { headers: { "access-token": access_token } })
    .then(success)
    .catch(fail);
}
async function follow(id, success, fail) {
  await api
    .post(`/follow`, { id: id }, { headers: { "access-token": access_token } })
    .then(success)
    .catch(fail);
}
async function unfollow(id, success, fail) {
  await api
    .delete(`/follow/delete/${id}`, { headers: { "access-token": access_token } })
    .then(success)
    .catch(fail);
}
async function getFollowerList(id, success, fail) {
  await api
    .get(
      `/follow/followerlist`,
      {
        id: id,
      },
      { headers: { "access-token": access_token } },
    )
    .then(success)
    .catch(fail);
}
async function getFollowingList(params, success, fail) {
  await api
    .get(`/follow/followinglist`, { params: { id: params.id } }, { headers: { "access-token": access_token } })
    .then(success)
    .catch(fail);
}

export { login, signup, getUser, isFollowing, follow, unfollow, getFollowerList, getFollowingList };
