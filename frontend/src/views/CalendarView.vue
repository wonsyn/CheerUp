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
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import router from "@/router";
import Popover from "bootstrap/js/src/popover";
import useStore from "@/store";

const userStore = useStore().modules.userStore;
const scrapStore = useStore().modules.scrapStore;
const boardStore = useStore().modules.boardStore;
const feedStore = useStore().modules.feedStore;

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
      console.log(arg.event);
      if (confirm("피드 상세 페이지로 이동")) {
        router.push({ name: "detail", params: { feedId: arg.event.extendedProps.feedId } });
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
    searchFeed() {
      const selectType = document.getElementById("select_feed_type");
      const selectCategory = document.getElementById("select_feed_category");
      const selectBoard = document.getElementById("select_board");

      let type = selectType.options[selectType.selectedIndex].value;
      let category = selectCategory.options[selectCategory.selectedIndex].value;
      let boardId = selectBoard.options[selectCategory.selectedIndex].value;

      console.log(" " + type + category + boardId);
      this.eventList = [...this.scrapList];
      if (type > 0) {
        this.eventList.filter((scrap) => {
          scrap.scrapfeeTtype == type;
        });
      }
      if (category > 0) {
        this.eventList.filter((scrap) => {
          scrap.category == category;
        });
      }
      if (boardId > 0) {
        this.eventList.filter((scrap) => {
          scrap.boardId == boardId;
        });
      }
      this.calendarOptions.events = this.eventList;
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
    await userStore.actions.getProfile(this.username);
    this.profile = userStore.getters.profile();
    await scrapStore.actions.getScrapList(this.profile.id);
    this.scrapList = scrapStore.getters.scrapList();
    this.scrapList.map(async function (el) {
      const eventListElement = el;
      await feedStore.actions.getFeedDetail(el.feedId);
      let feed = feedStore.getters.getFeedDetail();
      const categories = ["전체", "금융", "게임", "보안", "IT 서비스", "모바일"];
      const types = ["기타", "뉴스", "정보"];
      const category = categories[feed.feedCategory || 0];
      const type = feed.feedType;
      const typeName = types[type || 0];
      eventListElement["title"] = feed.feedTitle;
      eventListElement["date"] = el.feedDate;
      if (type == 1) {
        eventListElement["backgroundColor"] = "#00dd99";
      } else if (type == 2) {
        eventListElement["backgroundColor"] = "#5bc0de";
      } else {
        eventListElement["backgroundColor"] = "#000000";
      }
      eventListElement["content"] = typeName + "," + (feed.feedSource || "") + ", " + category;
      eventListElement["imgUrl"] = feed.feedImgUrl;
      eventListElement["category"] = feed.feedCategory;
      return eventListElement;
    });
    this.calendarOptions.events = this.scrapList;
    // const testEvent = { title: "test1", date: "2022-08-14", content: "1, , 게임", imgUrl: "https://www.naver.com", feedId: 1160 };
    // this.calendarOptions.events.push(testEvent);
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
