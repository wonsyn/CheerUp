import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function writeComment(comment, success, fail) {
  await api
    .post(`/comment/create`, JSON.stringify(comment), { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function listComment(feedId, success, fail) {
  await api
    .get(`/comment/read/${feedId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function editComment(body, success, fail) {
  await api
    .put(`/comment/update`, JSON.stringify(body), { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function deleteComment(commentId, success, fail) {
  await api
    .delete(`/comment/delete/${commentId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function addLike(body, success, fail) {
  await api
    .post(`/comment/like`, JSON.stringify(body), { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function deleteLike(body, success, fail) {
  await api
    .delete(`/comment/like`, { data: body, headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function checkLike(params, success, fail) {
  await api
    .get(`/comment/like/chk/${params.commentId}/${params.userId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

export { writeComment, listComment, editComment, deleteComment, addLike, deleteLike, checkLike };
