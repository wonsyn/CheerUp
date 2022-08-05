import { apiInstance } from "../index.js";

const api = apiInstance();
const access_token = sessionStorage.getItem("access-token");

async function getFeed(params, success, fail) {
  await api
    .get(`/feed/main`, {
      params: {
        industry: params.industry,
        type: params.type,
      },
      headers: { "access-token": access_token },
    })
    .then(success)
    .catch(fail);
}

export { getFeed };
