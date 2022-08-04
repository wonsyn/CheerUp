import { apiInstance } from "../index.js";

const api = apiInstance();

async function getFeed(type, success, fail) {
  const access_token = sessionStorage.getItem("access-token");
  await api
    .get(`/feed/${type}`, { headers: { Authorization: access_token } })
    .then(success)
    .catch(fail);
}

export { getFeed };
