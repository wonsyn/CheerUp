import { apiInstance } from "@/api/index.js";

const api = apiInstance();
const headers = {
  "access-token": sessionStorage.getItem("access-token"),
};

async function getSchedule(params, success, fail) {
  await api.get(`/schedule/list`, { params: params, headers: headers }).then(success).catch(fail);
}

export { getSchedule };
