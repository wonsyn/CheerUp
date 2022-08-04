import { apiInstance } from "../index.js";

const api = apiInstance();

async function getFeed(params, success, fail) {
  const access_token = sessionStorage.getItem("access-token");
  await api
    .get(`/feed/main`, {
      params: {
        industry: params.industry,
        type: params.type,
      },
      headers: { Authorization: access_token },
    })
    .then(success)
    .catch(fail);
}

export { getFeed };
