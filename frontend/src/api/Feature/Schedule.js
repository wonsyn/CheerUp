import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function getSchedule(params, success, fail) {
  await api
    .get(`/schedule/list`, { params: params, headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function addSchedule(body, success, fail) {
  await api
    .post(`/schedule/create`, JSON.stringify(body), { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function updateSchedule(body, success, fail) {
  await api
    .put(`/schedule/update/`, JSON.stringify(body), { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

async function deleteSchedule(scheduleId, success, fail) {
  await api
    .delete(`/schedule/delete/${scheduleId}`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

export { getSchedule, addSchedule, updateSchedule, deleteSchedule };
