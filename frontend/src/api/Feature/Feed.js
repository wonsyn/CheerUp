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
async function updateBoard(params, success, fail) {
  await api
    .put(`/feed/board/update`, JSON.stringify(params), { headers: { "access-token": access_token } })
    .then(success)
    .catch(fail);
}
async function deleteBoard(boardId, success, fail) {
  await api
    .delete(`/feed/board/delete/${boardId}`, { headers: { "access-token": access_token } })
    .then(success)
    .catch(fail);
}
async function getFeedInBoard(params, success, fail) {
  await api
    .get(`/feed/scrap/${params.userId}/${params.boardId}`, { headers: { "access-token": access_token } })
    .then(success)
    .catch(fail);
}

export { getFeed, createBoard, getBoardList, updateBoard, deleteBoard, getFeedInBoard };
