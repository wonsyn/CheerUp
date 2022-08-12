<template>
  <div class="container">
    <div style="border: 1px black solid; border-radius: 7px">
      <div class="row p-3">
        <select id="select_feed_type" class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
          <option selected value="0">전체</option>
          <option value="1">뉴스</option>
          <option value="2">정보글</option>
        </select>
        <select id="select_feed_category" class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
          <option selected value="0">산업군(전체)</option>
          <option value="1">금융</option>
          <option value="2">게임</option>
          <option value="3">보안</option>
          <option value="4">IT 서비스</option>
          <option value="5">모바일</option>
        </select>
        <select id="select_board" class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
          <option selected value="0">전체</option>
          <option v-for="board in boardList" :key="board.boardId" :value="board.boardId">{{ board.boardName }}</option>
        </select>
        <div class="col text-end me-3">
          <button @click="searchFeed" type="button" class="btn" style="background-color: #00dd99">검색</button>
        </div>
      </div>
    </div>
    <div class="m-5 mx-auto"><FullCalendar :options="calendarOptions" /></div>
  </div>
</template>

<script>
import "@fullcalendar/core/vdom"; // solves problem with Vite
// import * as Popper from "@popperjs/core";
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
// import bootstrap from "bootstrap";
import Popover from "bootstrap/js/src/popover";
import useStore from "@/store";

const userStore = useStore().modules.userStore;
const scrapStore = useStore().modules.scrapStore;
const boardStore = useStore().modules.boardStore;

export default {
  name: "CalendarView",
  components: {
    FullCalendar, // make the <FullCalendar> tag available
  },
  data() {
    return {
      profile: {},
      scrapList: [],
      boardList: [],
      eventList: [],
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin],
        initialView: "dayGridMonth",
        timeZone: "Asia/Seoul",
        weekends: true, // initial value
        locale: "ko",
        headerToolbar: {
          start: "",
          center: "prev title next",
          end: "today",
        },
        titleFormat: {
          year: "numeric",
          month: "numeric",
        },
        dayHeaderFormat: {
          weekday: "short",
        },
        eventClick: this.handleEventClick,
        eventMouseEnter: this.handleMouseEnter,
        eventMouseLeave: this.handleMouseLeave,
        eventBackgroundColor: "#00dd99",
        eventDisplay: "list-item",
        eventOrder: "type",
        eventOrderStrict: false,
        events: [],
        dayMaxEventRow: true,
        views: {
          dayGrid: {
            dayMaxEvents: 5,
          },
        },
      },
    };
  },
  methods: {
    handleDateClick: function (arg) {
      alert("date click! " + arg.dateStr);
    },
    handleEventClick: function (arg) {
      console.log(arg.event);
      alert("event click! " + arg);
    },
    handleMouseEnter: function (arg) {
      const myPopover = new Popover(arg.el, {
        trigger: "hover",
        title: arg.event.title,
        content: arg.event.extendedProps.type,
        delay: { show: 200, hide: 200 },
        animation: true,
      });
      myPopover.toggle();
      console.log("mouse enter", arg.event.title);
    },
    handleMouseLeave: function (arg) {
      const myPopover = new Popover(arg.el);
      console.log(myPopover);
    },
    async fetchData() {
      await userStore.actions.getProfile(this.username);
      this.profile = userStore.getters.profile();
      console.log(this.profile);
      await scrapStore.actions.getScrapList(this.profile.id);
      this.scrapList = scrapStore.getters.scrapList();
      await boardStore.actions.getBoardList(this.profile.userId);
      this.boardList = boardStore.getters.boardList();
    },
    async searchFeed() {
      const selectType = document.getElementById("select_feed_type");
      const selectCategory = document.getElementById("select_feed_category");

      let type = selectType.options[selectType.selectedIndex].value;
      let category = selectCategory.options[selectCategory.selectedIndex].value;

      console.log(" " + type + category);

      await scrapStore.actions.getScrapList(this.profile.id);
      this.feedList = scrapStore.getters.getScrapList();
    },
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
    currentUser() {
      return sessionStorage.getItem("current_user");
    },
  },
  async created() {
    console.log("created");
    this.eventList = [
      { title: "dvent13333333333333333333333333333333", date: "2022-08-13", type: "1", backgroundColor: "#000000" },
      { title: "cvent2", date: "2022-08-13", type: "2", backgroundColor: "#ff0000" },
      { title: "bvent2", date: "2022-08-13", type: "2" },
      { title: "avent2", date: "2022-08-13", type: "1" },
      { title: "event2", date: "2022-08-13", type: "2" },
      { title: "event2", date: "2022-08-13", type: "2" },
      { title: "event2", date: "2022-08-13", type: "2" },
      { title: "event2", date: "2022-08-13", type: "2" },
      { title: "event2", date: "2022-08-13", type: "2" },
      { title: "event2", date: "2022-08-13", type: "2" },
      { title: "event2", date: "2022-08-13", type: "2" },
      { title: "event2", date: "2022-08-13", type: "2" },

      { title: "event3", date: "2022-08-14", type: "3" },
    ];
    this.calendarOptions.events.push(...this.eventList);
    await userStore.actions.getProfile(this.username);
    this.profile = userStore.getters.profile();
    await scrapStore.actions.getScrapList(this.profile.id);
    this.scrapList = scrapStore.getters.scrapList();
    await boardStore.actions.getBoardList(this.profile.userId);
    this.boardList = boardStore.getters.boardList();
  },
};
</script>
<style>
:root {
  --fc-button-bg-color: #fafafa;
  --fc-button-text-color: #2c3e50;
  --fc-button-focus-bg-color: #c0c0c0;
  --fc-button-hover-bg-color: #c0c0c0;
  --fc-button-active-bg-color: #bbbbbb;
}
.fc-toolbar-chunk {
  display: flex;
}
.fc-toolbar-title {
  color: #00dd99;
}
.fc-col-header-cell-cushion {
  text-decoration: none;
  color: white;
}
.fc-daygrid-day-frame {
  overflow: hidden;
  height: auto;
  max-height: 35vh;
}
.fc-daygrid-day-top {
  background-color: #fafafa;
}
.fc-daygrid-day-number {
  text-decoration: none;
  color: black;
  margin-left: auto;
  margin-right: auto;
  font-size: 1vw;
}
.fc-daygrid-more-link {
  text-decoration: none;
  color: #727272;
}
.fc-event-title {
  cursor: pointer;
  text-decoration: none;
  color: black;
}
.fc-col-header-cell {
  background-color: #bbbbbb;
}
</style>
