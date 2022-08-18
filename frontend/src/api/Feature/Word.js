import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function getMyWordList(userId, success, fail) {
  await api
    .get(`/word/myword/${userId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function getDBWordList(success, fail) {
  await api
    .get(`/word/listtest`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function updateMyWord(body, success, fail) {
  await api
    .put(`word/update`, JSON.stringify(body), { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function deleteMyWord(wordId, success, fail) {
  await api
    .delete(`/word/delete/${wordId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function addFavWord(wordId, success, fail) {
  await api
    .put(`/word/fav/add/${wordId}`, {}, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function removeFavWord(wordId, success, fail) {
  await api
    .put(`/word/fav/cancel/${wordId}`, {}, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function getMyFavWordList(userId, success, fail) {
  await api
    .get(`/word/myword/fav/${userId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function addMyWord(body, success, fail) {
  await api
    .post(`/word/create`, JSON.stringify(body), { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function searchMyWordList(params, success, fail) {
  await api
    .get(`/word/myword/${params.userId}`, { params: { keyword: params.keyword }, headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

export { getMyWordList, getDBWordList, updateMyWord, deleteMyWord, addFavWord, removeFavWord, getMyFavWordList, addMyWord, searchMyWordList };
