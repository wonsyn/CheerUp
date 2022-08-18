<template>
  <div class="container mt-5">
    <div style="color: #00dd99; font-size: 30px; font-weight: bold">내 일정</div>
    <div class="d-flex mb-3">
      <div class="me-auto"></div>
      <button class="btn btn-success" @click="createSchedule" data-bs-toggle="modal" data-bs-target="#scheduleCreateModal" data-bs-title="일정 추가">일정 추가하기</button>
    </div>
    <div class="p-3 shadow" style="border-radius: 15px">
      <table class="rtable table table-bordered table-striped table-hover">
        <thead>
          <tr>
            <th scope="col">번호</th>
            <th scope="col">일정명</th>
            <th scope="col">기업</th>
            <th scope="col">날짜</th>
            <th scope="col">메모</th>
          </tr>
        </thead>
        <tbody>
          <schedule-table-item
            v-for="(schedule, index) in scheduleList"
            :key="index"
            :index="index + 1"
            v-bind="schedule"
            @click="updateSchedule(index)"
            data-bs-toggle="modal"
            data-bs-target="#scheduleEditModal"
            :data-bs-title="index + 1"
          ></schedule-table-item>
        </tbody>
      </table>
    </div>

    <div class="modal fade" id="scheduleEditModal" tabindex="-1" aria-labelledby="scheduleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="scheduleModalLabel">일정</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form>
              <div class="mb-3">
                <label for="schedule-name" class="col-form-label">일정명:</label>
                <input type="text" v-model="curSchedule.scheduleTitle" class="form-control" id="schedule-edit-name" />
              </div>
              <div class="mb-3">
                <label for="company-name" class="col-form-label">기업명:</label>
                <input type="text" v-model="curSchedule.scheduleCompany" class="form-control" id="company-edit-name" />
              </div>
              <div class="mb-3">
                <label for="schedule-date" class="col-form-label">날짜:</label>
                <input type="date" v-model="curSchedule.scheduleDate" class="form-control" id="schedule-edit-date" />
              </div>
              <div class="mb-3">
                <label for="schedule-memo" class="col-form-label">메모:</label>
                <textarea class="form-control" v-model="curSchedule.scheduleMemo" id="schedule-edit-memo" style="min-height: 200px"></textarea>
              </div>
            </form>
          </div>
          <div class="modal-footer d-flex">
            <button type="button" @click="executeDelete" id="btn_modal_edit_delete" class="btn btn-danger" data-bs-dismiss="modal">삭제</button>
            <div class="me-auto"></div>
            <button type="button" @click="executeUpdate" data-bs-dismiss="modal" id="btn_modal_edit_submit" class="btn btn-info">수정</button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
          </div>
        </div>
      </div>
    </div>

    <div class="modal fade" id="scheduleCreateModal" tabindex="-1" aria-labelledby="scheduleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="scheduleModalLabel">일정 추가</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form>
              <div class="mb-3">
                <label for="schedule-name" class="col-form-label">일정명:</label>
                <input type="text" class="form-control" id="schedule-create-name" />
              </div>
              <div class="mb-3">
                <label for="company-name" class="col-form-label">기업명:</label>
                <input type="text" class="form-control" id="company-create-name" />
              </div>
              <div class="mb-3">
                <label for="schedule-date" class="col-form-label">날짜:</label>
                <input type="date" class="form-control" id="schedule-create-date" />
              </div>
              <div class="mb-3">
                <label for="schedule-memo" class="col-form-label">메모:</label>
                <textarea class="form-control" id="schedule-create-memo" style="min-height: 200px"></textarea>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" @click="executeCreate" id="btn_modal_create_submit" data-bs-dismiss="modal" class="btn btn-primary">추가</button>
            <button type="button" class="btn btn-secondary" id="btn_modal_create_close" data-bs-dismiss="modal">취소</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ScheduleTableItem from "@/components/SchduleTableItem.vue";
import useStore from "@/store/index.js";

const scheduleStore = useStore().modules.scheduleStore;
const userStore = useStore().modules.userStore;

export default {
  components: {
    ScheduleTableItem,
  },
  data() {
    return {
      scheduleList: [],
      curSchedule: Object,
      socket: useStore().modules.userStore.getters.socket(),
    };
  },
  async created() {
    await scheduleStore.actions.getSchedule();
    this.scheduleList = scheduleStore.getters.getScheduleList();
    console.log("created", this.scheduleList);
  },
  methods: {
    updateSchedule(index) {
      const schedule = this.scheduleList[index];
      this.curSchedule = Object.assign({}, this.scheduleList[index]);
      const modal = document.getElementById("scheduleEditModal");
      modal.addEventListener("show.bs.modal", (event) => {
        // Button that triggered the modal
        const button = event.relatedTarget;
        // Extract info from data-bs-* attributes
        const number = button.getAttribute("data-bs-title");
        // If necessary, you could initiate an AJAX request here
        // and then do the updating in a callback.
        //
        // Update the modal's content.
        const modalTitle = modal.querySelector(".modal-title");
        const modalName = modal.querySelector("#schedule-edit-name");
        const modalCompany = modal.querySelector("#company-edit-name");
        const modalDate = modal.querySelector("#schedule-edit-date");
        const modalMemo = modal.querySelector("#schedule-edit-memo");

        modalTitle.textContent = `${number}번 일정 수정`;
        modalName.value = schedule.scheduleTitle;
        modalCompany.value = schedule.scheduleCompany;
        modalDate.value = schedule.scheduleDate;
        modalMemo.value = schedule.scheduleMemo;

        // const deleteBtn = modal.querySelector("#btn_modal_edit_delete");
        // deleteBtn.onclick = this.executeDelete(schedule.scheduleId);
        // deleteBtn.removeEventListener("click")
        // deleteBtn.addEventListener("click", () => {
        //   this.executeDelete(schedule.scheduleId);
        // });
        // const updateBtn = modal.querySelector("#btn_modal_edit_submit");
        // updateBtn.setAttribute("onClick", "this.executeUpdate");
        // updateBtn.addEventListener("click", () => {
        //   this.executeUpdate(schedule.scheduleId);
        // });
      });
    },
    createSchedule() {
      const modal = document.getElementById("scheduleCreateModal");
      modal.addEventListener("show.bs.modal", (event) => {
        // Button that triggered the modal
        const button = event.relatedTarget;
        // Extract info from data-bs-* attributes
        const title = button.getAttribute("data-bs-title");
        // If necessary, you could initiate an AJAX request here
        // and then do the updating in a callback.
        //
        // Update the modal's content.
        const modalTitle = modal.querySelector(".modal-title");
        const modalName = modal.querySelector("#schedule-create-name");
        const modalCompany = modal.querySelector("#company-create-name");
        const modalDate = modal.querySelector("#schedule-create-date");
        const modalMemo = modal.querySelector("#schedule-create-memo");

        modalTitle.textContent = `${title}`;
        modalName.value = "";
        modalCompany.value = "";
        modalDate.value = "";
        modalMemo.value = "";
      });
    },
    async executeCreate() {
      if (userStore.getters.socket() === null || userStore.getters.socket().readyState === 3) {
        // if (this.socket === null) {
        console.log("schedule socket disconnected", userStore.getters.socket());
        this.socket = await userStore.getters.socket();
        console.log("schedule socket disconnected ", this.socket);
        await userStore.actions.connect();
      }

      const modal = document.getElementById("scheduleCreateModal");

      const modalName = modal.querySelector("#schedule-create-name");
      const modalCompany = modal.querySelector("#company-create-name");
      const modalDate = modal.querySelector("#schedule-create-date");
      const modalMemo = modal.querySelector("#schedule-create-memo");

      await scheduleStore.actions.addSchedule(modalName.value, modalCompany.value, modalDate.value, modalMemo.value);

      // if (userStore.getters.socket() === null || userStore.getters.socket().readyState === 3) {
      //   console.log("schedule socket disconnected");
      //   await userStore.actions.connect();
      // }

      let today = new Date();
      today.setHours(today.getHours() + 9);
      const date1 = new Date(modalDate.value);
      const date2 = new Date(today.toISOString().substring(0, 10));
      const diffDate = date1.getTime() - date2.getTime();
      const day = diffDate / (1000 * 60 * 60 * 24);

      console.log("ScheduleView(210):", this.socket);

      if (day >= 0 && day <= 7) {
        let socketMsg = "schedule," + sessionStorage.getItem("current_user") + "," + sessionStorage.getItem("current_user") + "," + day + "," + modalName.value;
        userStore.getters.socket().onopen(socketMsg);
      }
      await scheduleStore.actions.getSchedule();
      this.scheduleList = scheduleStore.getters.getScheduleList();
    },
    async executeUpdate() {
      if (userStore.getters.socket() === null || userStore.getters.socket().readyState === 3) {
        // if (this.socket === null) {
        console.log("schedule socket disconnected", userStore.getters.socket());
        this.socket = await userStore.getters.socket();
        console.log("schedule socket disconnected ", this.socket);
        await userStore.actions.connect();
      }

      const modal = document.getElementById("scheduleEditModal");
      console.log("update", this.curSchedule.scheduleId);
      const modalName = modal.querySelector("#schedule-edit-name");
      const modalCompany = modal.querySelector("#company-edit-name");
      const modalDate = modal.querySelector("#schedule-edit-date");
      const modalMemo = modal.querySelector("#schedule-edit-memo");

      await scheduleStore.actions.updateSchedule(this.curSchedule.scheduleId, modalName.value, modalCompany.value, modalDate.value, modalMemo.value);

      let today = new Date();
      today.setHours(today.getHours() + 9);
      const date1 = new Date(modalDate.value);
      const date2 = new Date(today.toISOString().substring(0, 10));
      const diffDate = date1.getTime() - date2.getTime();
      const day = diffDate / (1000 * 60 * 60 * 24);

      if (day >= 0 && day <= 7) {
        // let socketMsg = "schedule," + sessionStorage.getItem("current_user") + "," + sessionStorage.getItem("current_user") + "," + day + "," + modalName.value;
        let socketMsg = "schedule," + sessionStorage.getItem("current_user") + "," + sessionStorage.getItem("current_user") + "," + modalDate.value + "," + modalName.value + "," + modalCompany.value;
        userStore.getters.socket().onopen(socketMsg);
      }

      await scheduleStore.actions.getSchedule();
      this.scheduleList = scheduleStore.getters.getScheduleList();
    },
    async executeDelete() {
      console.log(this.curSchedule.scheduleId);
      await scheduleStore.actions.deleteSchedule(this.curSchedule.scheduleId);

      await scheduleStore.actions.getSchedule();
      this.scheduleList = scheduleStore.getters.getScheduleList();
    },
  },
};
</script>

<style scoped>
.rtable {
  -webkit-text-size-adjust: none;
  background-color: #fff;
  color: #000;
  width: 100%;
  border-top-width: 0px;
  border-right-width: 0px;
  border-bottom-width: 0px;
  border-left-width: 0px;
  -webkit-border-horizontal-spacing: 0px;
  -webkit-border-vertical-spacing: 0px;
  outline: none;
  margin: 0;
  font-size: 14px;
  font-weight: 400;
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, "Apple SD Gothic Neo", "Noto Sans KR", "Malgun Gothic", sans-serif;
  border-collapse: collapse;
  border-radius: 10px;
}
</style>
