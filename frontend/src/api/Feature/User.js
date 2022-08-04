import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}
async function signup(user, success, fail) {
  await api.post(`/user/signup`, JSON.stringify(user)).then(success).catch(fail);
}

export { login, signup };
