import axios from "axios";

function apiInstance() {
  const instance = axios.create({
    baseURL: "/v1/search/news.json",
    headers: {
      "Content-type": "application/json",
      "X-Naver-Client-Id": "izgPIRMbMDgYMMU5M425",
      "X-Naver-Client-Secret": "gzWRWd8LoH",
    },
  });
  return instance;
}

const api = apiInstance();

function getNews(text, start, success, fail) {
  // text: 검색어, start: 검색 결과 시작지점
  api
    .get(``, { params: { query: text, display: 100, start: start, sim: "date" } })
    .then(success)
    .catch(fail);
}

// function test() {
//   getNews(
//     {
//       query: "IT 디지털 웹 앱",
//       display: 100,
//       start: 1,
//       sim: "date",
//     },
//     ({ data }) => {
//       console.log(data);
//     },
//     (error) => {
//       console.log(error);
//     },
//   );
// }

export { getNews };
