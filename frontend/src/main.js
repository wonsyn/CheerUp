import { createApp } from "vue";
import App from "./App.vue";
import router from "@/router";
import store from "./store";
import Vuex from "vuex";
import FeedList from "@/components/FeedList.vue";

import "bootstrap/dist/css/bootstrap.css";
import "./assets/css/main.css";
import "bootstrap";

createApp(App).component("FeedList", FeedList).use(store).use(router).use(Vuex).mount("#app");
