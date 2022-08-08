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
async function createBoard(params, success, fail) {
  await api
    .post(`/feed/board/create`, JSON.stringify(params), { headers: { "access-token": access_token } })
    .then(success)
    .catch(fail);
}
async function getBoardList(userId, success, fail) {
  await api
    .get(`/feed/board/list/${userId}`, { headers: { "access-token": access_token } })
    .then(success)
    .catch(fail);
}

export { getFeed, createBoard, getBoardList };
