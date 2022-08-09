import { apiInstance } from "@/api/index.js";

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

async function getFeedDetail(feedId, success, fail) {
  await api
    .get(`/feed/detail/${feedId}`, { headers: { "access-token": access_token } })
    .then(success)
    .catch(fail);
}

export { getFeed, getFeedDetail };
