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

export { login, signup, getUser };
