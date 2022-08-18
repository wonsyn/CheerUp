<template>
  <div class="container">
    <div style="border: 1px #00dd99 solid; border-radius: 7px">
      <div class="row p-3">
        <select style="border-width: 0" id="select_feed_type" class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
          <option selected value="0">전체</option>
          <option value="1">뉴스</option>
          <option value="2">정보글</option>
          <option value="3">일정</option>
        </select>
        <select style="border-width: 0" id="select_feed_category" class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
          <option selected value="0">산업군(전체)</option>
          <option value="1">금융</option>
          <option value="2">게임</option>
          <option value="3">보안</option>
          <option value="4">IT 서비스</option>
          <option value="5">모바일</option>
        </select>
        <select style="border-width: 0" id="select_board" class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
          <option selected value="0">전체</option>
          <option v-for="board in boardList" :key="board.boardId" :value="board.boardId">{{ board.boardName }}</option>
        </select>
        <div class="col text-end me-3">
          <button @click="searchFeed" type="button" class="btn btn-primary">검색</button>
        </div>
      </div>
    </div>
    <div class="m-5 mx-auto"><FullCalendar :options="calendarOptions" /></div>
  </div>
</template>

<script>
import "@fullcalendar/core/vdom"; // solves problem with Vite
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import router from "@/router";
import Popover from "bootstrap/js/src/popover";
import useStore from "@/store";
import Swal from "sweetalert2";

const userStore = useStore().modules.userStore;
const scrapStore = useStore().modules.scrapStore;
const boardStore = useStore().modules.boardStore;
const scheduleStore = useStore().modules.scheduleStore;

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
      scheduleList: [],
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
        displayEventTime: false,
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
    handleEventClick: function (arg) {
      const myPopover = Popover.getOrCreateInstance(arg.el);
      myPopover.hide();
      if (arg.event.extendedProps.type < 3) {
        let confirmMsg = "해당 피드의 상세 페이지로 이동합니다.";
        Swal.fire({
          title: "페이지를 이동하시겠습니까?",
          text: confirmMsg,
          showCancelButton: true,
          confirmButtonColor: "#00dd99",
          cancelButtonColor: "#ee7785",
          confirmButtonText: "이동",
          cancelButtonText: "취소",
        }).then((result) => {
          if (result.isConfirmed) {
            router.push({ name: "detail", params: { feedId: arg.event.extendedProps.feedId } });
          }
        });
      } else if (arg.event.extendedProps.type == 3) {
        let confirmMsg = "일정 관리 페이지로 이동합니다.";
        Swal.fire({
          title: "페이지를 이동하시겠습니까?",
          text: confirmMsg,
          showCancelButton: true,
          confirmButtonColor: "#00dd99",
          cancelButtonColor: "#ee7785",
          confirmButtonText: "이동",
          cancelButtonText: "취소",
        }).then((result) => {
          if (result.isConfirmed) {
            router.push({ name: "schedule" });
          }
        });
      }
    },
    handleMouseEnter: function (arg) {
      const myPopover = new Popover(arg.el, {
        trigger: "hover",
        title: arg.event.title,
        content: arg.event.extendedProps.content,
        delay: { show: 200, hide: 200 },
        animation: true,
      });
      myPopover.toggle();
    },
    handleMouseLeave: function (arg) {
      const myPopover = new Popover(arg.el);
      console.log(myPopover);
    },
    async fetchData() {
      await userStore.actions.getProfile(this.currentUser);
      this.profile = userStore.getters.profile();
      await scrapStore.actions.getScrapList(this.profile.userId);
      this.scrapList = scrapStore.getters.scrapList();
      await boardStore.actions.getBoardList(this.profile.userId);
      this.boardList = boardStore.getters.boardList();
      await scheduleStore.actions.getSchedule();
      this.boardList = scheduleStore.getters.getScheduleList();
    },
    searchFeed() {
      const selectType = document.getElementById("select_feed_type");
      const selectCategory = document.getElementById("select_feed_category");
      const selectBoard = document.getElementById("select_board");

      let type = selectType.options[selectType.selectedIndex].value;
      let category = selectCategory.options[selectCategory.selectedIndex].value;
      let boardId = selectBoard.options[selectBoard.selectedIndex].value;
      this.eventList = [...this.scrapList, ...this.scheduleList];
      if (type > 0) {
        this.eventList = this.eventList.filter((scrap) => scrap.type == type);
      }
      if (category > 0) {
        this.eventList = this.eventList.filter((scrap) => scrap.category == category);
      }
      if (boardId > 0) {
        this.eventList = this.eventList.filter((scrap) => scrap.boardId == boardId);
      }
      this.calendarOptions.events = this.eventList;
    },
  },
  computed: {
    currentUser() {
      return sessionStorage.getItem("current_user");
    },
  },
  async created() {
    await userStore.actions.getProfile(this.currentUser);
    this.profile = userStore.getters.profile();
    await scrapStore.actions.getScrapList(this.profile.userId);
    this.scrapList = scrapStore.getters.scrapList();
    await scheduleStore.actions.getSchedule();
    this.scheduleList = scheduleStore.getters.getScheduleList();
    this.scrapList = this.scrapList.map(function (el) {
      const eventListElement = el;
      const categories = ["전체", "금융", "게임", "보안", "IT 서비스", "모바일"];
      const types = ["기타", "뉴스", "정보"];
      const category = categories[el.feedCategory || 0];
      const type = el.scrapfeedType;
      const typeName = types[type || 0];
      const date = new Date(el.feedDate);
      eventListElement["title"] = el.feedTitle;
      eventListElement["date"] = el.myfeedDate;
      if (type == 1) {
        eventListElement["backgroundColor"] = "#00dd99";
      } else if (type == 2) {
        eventListElement["backgroundColor"] = "#c89ec4";
      } else {
        eventListElement["backgroundColor"] = "#000000";
      }
      eventListElement["content"] = typeName + ", " + category + ", " + Intl.DateTimeFormat().format(date) + "," + (el.feedSource || "");
      eventListElement["imgUrl"] = el.feedImgUrl;
      eventListElement["category"] = el.feedCategory;
      eventListElement["type"] = type;
      return eventListElement;
    });
    this.scheduleList = this.scheduleList.map(function (el) {
      const eventListElement = el;
      const category = el.scheduleCompany;
      const typeName = "일정";
      eventListElement["title"] = el.scheduleTitle;
      eventListElement["date"] = el.scheduleDate;
      eventListElement["type"] = 3;
      eventListElement["boardId"] = 0;
      eventListElement["backgroundColor"] = "#ee7785";
      eventListElement["content"] = typeName + ", " + category + ", " + el.scheduleDate + "/" + (el.scheduleMemo || "");
      eventListElement["category"] = 0;
      return eventListElement;
    });
    this.calendarOptions.events.push(...this.scrapList);
    this.calendarOptions.events.push(...this.scheduleList);
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
.fc-button {
  border-radius: 15px !important;
  border-color: #bbbbbb !important;
}
</style>
