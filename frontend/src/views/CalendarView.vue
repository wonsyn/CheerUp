<template>
  <!-- <button @click="toggleWeekends">toggle weekends</button> -->

  <div class="my-5 w-75 mx-auto"><FullCalendar :options="calendarOptions" /></div>
</template>

<script>
import "@fullcalendar/core/vdom"; // solves problem with Vite
// import * as Popper from "@popperjs/core";
import FullCalendar from "@fullcalendar/vue3";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
// import bootstrap from "bootstrap";
import Popover from "bootstrap/js/src/popover";

export default {
  name: "CalendarView",
  components: {
    FullCalendar, // make the <FullCalendar> tag available
  },
  data() {
    return {
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
            dayMaxEvents: 6,
          },
        },
      },
    };
  },
  computed: {
    customHeader() {
      const headerElements = document.getElementsByClassName("fc-toolbar-chunk");
      console.log(headerElements);
      return headerElements;
    },
  },
  created() {
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
      const myPopoverTrigger = arg.el;
      const myPopover = new Popover(myPopoverTrigger, {
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
      const myPopoverTrigger = arg.el;
      const myPopover = new Popover(myPopoverTrigger);
      console.log(myPopover);
      // myPopover.toggle();
      console.log("mouse leave", arg.event.extendedProps.type);
    },
  },
};
</script>
<style>
:root {
  --fc-button-bg-color: #fafafa;
  --fc-button-text-color: #2c3e50;
  --fc-button-active-bg-color: #1a252f;
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
.fc-event-title {
  cursor: pointer;
  text-decoration: none;
  color: black;
}
.fc-col-header-cell {
  background-color: #bbbbbb;
}
</style>
