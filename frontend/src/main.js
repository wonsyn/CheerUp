import { createApp } from "vue";
import App from "./App.vue";
import router from "@/router";
import store from "./store";
import Vuex from "vuex";

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap";

createApp(App).use(store).use(router).use(Vuex).mount("#app");
