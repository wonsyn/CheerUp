import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/LoginView.vue";
import SignUpView from "@/views/SignUpView.vue";
// import CalendarView from "@/views/CalendarView.vue"
// import FollowView from "@/views/FollowView.vue"
// import NotFoundView from "@/views/NotFoundView.vue"
// import ProfileView from "@/views/ProfileView.vue"
// import ScheduleView from "@/views/ScheduleView.vue"
// import UserEditView from "@/views/UserEditView.vue"
// import VocaView from "@/views/VocaView.vue"

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
  {
    path: "/signup",
    name: "signup",
    component: SignUpView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
