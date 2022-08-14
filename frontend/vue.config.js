const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      "/v1": {
        target: "https://openapi.naver.com/",
        changeOrigin: true,
      },
      "/api":{
        target: "cheerup.kro.kr:3000/",
        changeOrigin: true,
      }
    },
  },
});
