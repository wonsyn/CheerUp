import { apiInstance } from "@/api/index.js";

const api = apiInstance();
const headers = {
  "access-token": sessionStorage.getItem("access-token"),
};

async function getMyWordList(userId, success, fail) {
  api.get(`/word/myword/${userId}`, { headers: headers }).then(success).catch(fail);
}

async function getDBWordList(success, fail) {
  api.get(`/word/listtest`, { headers: headers }.then(success).catch(fail));
}

async function updateMyWord(body, success, fail) {
  api.put(`word/update`, body, { headers: headers }).then(success).catch(fail);
}

export { getMyWordList, getDBWordList, updateMyWord };
