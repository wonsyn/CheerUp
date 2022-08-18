import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function getFeed(params, success, fail) {
  await api
    .get(`/feed/main`, {
      params: {
        industry: params.industry,
        type: params.type,
      },
      headers: { "access-token": sessionStorage.getItem("access-token") },
    })
    .then(success)
    .catch(fail);
}
async function createBoard(params, success, fail) {
  await api
    .post(`/feed/board/create`, JSON.stringify(params), { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function getBoardList(userId, success, fail) {
  await api
    .get(`/feed/board/list/${userId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function updateBoard(params, success, fail) {
  await api
    .put(`/feed/board/update`, JSON.stringify(params), { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function deleteBoard(boardId, success, fail) {
  await api
    .delete(`/feed/board/delete/${boardId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function getScrapList(userId, success, fail) {
  await api
    .get(`/feed/scrap/${userId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function getFeedInBoard(params, success, fail) {
  await api
    .get(`/feed/scrap/${params.userId}/${params.boardId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function getFeedDetail(feedId, success, fail) {
  await api
    .get(`/feed/detail/${feedId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function recommFeed(feedId, success, fail) {
  await api
    .get(`/feed/recomm/${feedId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function createScrap(params, success, fail) {
  await api
    .post(`/feed/scrap/create`, JSON.stringify(params), { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function deleteScrap(myfeedId, success, fail) {
  await api
    .delete(`/feed/scrap/delete/${myfeedId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function editScrap(params, success, fail) {
  await api
    .put(`/feed/scrap/update/${params.myfeedId}/${params.boardId}`, {}, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
export { getFeed, createBoard, getBoardList, updateBoard, deleteBoard, getFeedInBoard, getFeedDetail, recommFeed, getScrapList, createScrap, deleteScrap, editScrap };
