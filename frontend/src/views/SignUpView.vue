<template>
  <div class="container my-5">
    <br />
    <br />

    <div style="font-weight: bold; font-size: 40px" @click="checkData">회원가입</div>
    <div class="row my-3">
      <div class="d-flex col align-items-center" style="display: inline-block">
        <div>
          <img src="@/assets/logo.png" alt="logo" style="width: 65%; height: 50%" />
          <br />
          <h5>취업이 고민이신가요?</h5>
          <h5 class="my-4">저희 <strong class="text-primary">Cheer Up</strong>과 함께 쉬운 취업 준비하세요!</h5>
        </div>
      </div>
      <div class="col py-3">
        <div class="py-3 px-3">
          <div style="border: 1px black solid; padding: 5%; border-radius: 10px; box-shadow: 5px 5px 5px">
            <form @submit.prevent="signup(credentials)" class="needs-validation">
              <label for="id" class="form-label">ID</label>
              <div class="input-group mb-3" style="border-radius: 10px" required>
                <input @input="dupIdBtn" v-model="credentials.id" id="id" type="text" class="form-control" placeholder="ID" aria-label="User ID" aria-describedby="btn_check_id" required />
                <button class="btn btn-outline-primary" style="border-radius: 0 0.375rem 0.375rem 0" id="btn_check_id" @click="checkDup" disabled>중복확인</button>
                <div v-if="credentials.id.length < 4" id="id-check-invalid" class="invalid-feedback">ID는 4자 이상 입력하세요.</div>
                <div v-else id="id-check-invalid" class="invalid-feedback">사용 불가능한 ID입니다.</div>
                <div id="id-check-valid" class="valid-feedback">사용 가능한 ID입니다.</div>
              </div>

              <label for="nickname" class="form-label">Nickname</label>
              <div class="input-group mb-3">
                <input v-model="credentials.nickname" id="nickname" type="text" class="form-control" placeholder="Nickname" aria-label="User Nickname" aria-describedby="btn_check_dup" required />
              </div>

              <label for="password" class="form-label">Password</label>
              <div class="input-group mb-3">
                <input v-model="credentials.password" id="password" type="password" class="form-control" placeholder="Password" required />
              </div>

              <label for="password_check" class="form-label">Password Check</label>
              <div class="input-group mb-3">
                <input v-model="password_check" @input="validCheck" id="password_check" type="password" class="form-control" placeholder="Password" required />
                <div id="pw-check-invalid" class="invalid-feedback">비밀번호가 틀렸습니다.</div>
              </div>

              <label for="email" class="form-label">Email</label>
              <div class="input-group mb-3">
                <input v-model="email_front" @input="completeEmail" id="email" type="text" class="form-control" placeholder="Email" aria-label="Username" required />
                <span class="input-group-text">@</span>
                <input v-model="email_back" @input="completeEmail" type="text" class="form-control" placeholder="domain.com" aria-label="도메인" id="email-back" required />
                <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">선택하세요..</button>
                <ul class="dropdown-menu dropdown-menu-end">
                  <li><a href="#" @click="autoFillEmail('naver.com')" class="dropdown-item">naver.com</a></li>
                  <li><a href="#" @click="autoFillEmail('gmail.com')" class="dropdown-item">gmail.com</a></li>
                  <li><a href="#" @click="autoFillEmail('nate.com')" class="dropdown-item">nate.com</a></li>
                  <li><a href="#" @click="autoFillEmail('hanmail.net')" class="dropdown-item">hanmail.net</a></li>
                  <li><hr class="dropdown-divider" /></li>
                  <li><a href="#" @click="autoFillEmail(null)" class="dropdown-item">직접입력</a></li>
                </ul>
              </div>
              <hr />
              <div class="text-end">
                <button type="submit" class="btn btn-primary mx-2">회원가입</button>
                <button type="button" @click="escape" class="btn btn-danger">뒤로</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <toast-item></toast-item>
  </div>
</template>

<script>
import useStore from "@/store";
import ToastItem from "@/components/ToastItem";
import router from "@/router";

const store = useStore();
const userStore = store.modules.userStore;

export default {
  name: "SignUp",
  components: {
    ToastItem,
  },
  data() {
    return {
      password_check: "",
      email_front: "",
      email_back: "",
      credentials: {
        id: "",
        nickname: "",
        password: "",
        email: this.email_front + "@" + this.email_back,
      },
      validId: "",
      isValid: false,
    };
  },
  methods: {
    escape() {
      router.push({ name: "login" });
    },
    completeEmail: function () {
      this.credentials.email = this.email_front + "@" + this.email_back;
    },
    validCheck() {
      const passwordCheck = document.getElementById("password_check");

      if (this.credentials.password === this.password_check) {
        passwordCheck.classList.remove("is-invalid");
        passwordCheck.classList.add("is-valid");
        return true;
      } else {
        passwordCheck.classList.add("is-invalid");
        passwordCheck.classList.remove("is-valid");
        return false;
      }
    },

    async signup() {
      if (this.validCheck() === true) {
        await userStore.actions.signup(this.credentials);
        await userStore.actions.login({ id: this.credentials.id, password: this.credentials.password });
        router.push({ name: "home" });
      } else {
        const passwordCheck = document.getElementById("password_check");
        passwordCheck.classList.add("is-invalid");
        passwordCheck.focus();
      }
    },
    async checkDup() {
      await userStore.actions.checkId(this.credentials.id);
      this.isValid = userStore.getters.isValidId();
      let inputId = document.getElementById("id");
      if (this.isValid == true) {
        this.validId = this.credentials.id;
        inputId.classList.remove("is-invalid");
        inputId.classList.add("is-valid");
      } else {
        inputId.classList.add("is-invalid");
        inputId.classList.remove("is-valid");
        inputId.focus();
      }
    },

    dupIdBtn() {
      let btnCheckDup = document.getElementById("btn_check_id");
      let inputId = document.getElementById("id");
      if (this.credentials.id.length > 3) {
        btnCheckDup.removeAttribute("disabled");
        inputId.classList.remove("is-invalid");
      } else {
        btnCheckDup.setAttribute("disabled", true);
        inputId.classList.remove("is-valid");
        inputId.classList.add("is-invalid");
      }
    },
    autoFillEmail: function (txt) {
      let emailBack = document.getElementById("email-back");
      if (txt != null) {
        this.email_back = txt;
      } else {
        this.email_back = "";
      }
      this.completeEmail();
      emailBack.focus();
    },
  },
  mounted() {
    let inputId = document.getElementById("id");
    inputId.classList.add("is-invalid");
    inputId.focus();
  },
  computed: {},
  watch: {},
};
</script>

<style scoped></style>
