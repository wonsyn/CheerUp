import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function getNotice(success, fail) {
  await api
    .get(`/alarm/listByDate`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function checkNotice(alarmId, success, fail) {
  await api
    .put(`/alarm/check`, { params: { alarmId: alarmId }, headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}
async function getLatestAlarm(success, fail) {
  await api
    .get(`/alarm/getlatestalarm`, { headers: { "access-token": sessionStorage.getItem("access-token") } })
    .then(success)
    .catch(fail);
}

export { getNotice, checkNotice, getLatestAlarm };
