import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/LoginView.vue";
import SignUpView from "@/views/SignUpView.vue";
import CalendarView from "@/views/CalendarView.vue";
import FollowView from "@/views/FollowView.vue";
import NotFoundView from "@/views/NotFoundView.vue";
import ProfileView from "@/views/ProfileView.vue";
import ScheduleView from "@/views/ScheduleView.vue";
import UserEditView from "@/views/UserEditView.vue";
import VocaView from "@/views/VocaView.vue";
import FeedDetailView from "@/views/FeedDetailView.vue";
import AuthorizationView from "@/views/AuthorizationView";

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
  {
    path: "/calendar",
    name: "calendar",
    component: CalendarView,
  },
  {
    path: "/follow/:username",
    name: "follow",
    component: FollowView,
  },
  {
    path: "/profile/:username",
    name: "profile",
    component: ProfileView,
  },
  {
    path: "/schedule",
    name: "schedule",
    component: ScheduleView,
  },
  {
    path: "/edit",
    name: "useredit",
    component: UserEditView,
  },
  {
    path: "/voca",
    name: "voca",
    component: VocaView,
  },
  {
    path: "/404",
    name: "notfound",
    component: NotFoundView,
  },
  {
    path: "/detail/:feedId",
    name: "detail",
    component: FeedDetailView,
    props: true,
  },
  {
    path: "/auth",
    name: "userauth",
    component: AuthorizationView,
  },
  // {
  //   path: "*",
  //   redirect: "/404",
  // },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
