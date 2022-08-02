<template>
  <div class="container my-5">
    {{ state }}
    <div style="font-weight: bold; font-size: 40px" @click="checkData">회원가입</div>
    <div class="row">
      <div class="col py-3" style="display: inline-block; vertical-align: middle">
        <img src="@/assets/logo.png" alt="logo" style="width: 65%; height: 50%" />
        <br />
        <div>취Up에 가입하고 다양한 소식을 받아보세요!</div>
      </div>
      <div class="col py-3">
        <div class="py-3 px-3">
          <div style="border: 1px black solid; padding: 5%; border-radius: 10px; box-shadow: 5px 5px 5px">
            <form @submit.prevent="signUp()" class="needs-validation">
              <label for="id" class="form-label">ID</label>
              <div class="input-group mb-3" required>
                <input v-model="state.id" id="id" type="text" class="form-control" placeholder="ID" aria-label="User ID" aria-describedby="btn_check_dup" required />
                <button class="btn btn-outline-primary" type="button" id="btn_check_dup" @click="checkDup">중복확인</button>
              </div>
              <div id="id-check-valid" class="invalid-feedback">ID는 4자 이상 입력하세요.</div>
              <div id="id-check-invalid" class="valid-feedback">사용 가능한 ID입니다.</div>

              <label for="nickname" class="form-label">Nickname</label>
              <div class="input-group mb-3">
                <input v-model="state.nickname" id="nickname" type="text" class="form-control" placeholder="Nickname" aria-label="User Nickname" aria-describedby="btn_check_dup" />
                <button class="btn btn-outline-primary" type="button" id="btn_check_dup">중복확인</button>
              </div>

              <label for="password" class="form-label">Password</label>
              <div class="input-group mb-3">
                <input v-model="state.password" id="password" type="password" class="form-control" placeholder="Password" aria-label="Recipient's username" />
              </div>

              <label for="password_check" class="form-label">Password Check</label>
              <div class="input-group mb-3">
                <input v-model="state.password_check" id="password_check" type="password" class="form-control" placeholder="Password" aria-label="Recipient's username" />
              </div>

              <label for="email" class="form-label">Email</label>
              <div class="input-group mb-3">
                <input v-model="state.email_front" id="email" type="text" class="form-control" placeholder="Email" aria-label="Username" />
                <span class="input-group-text">@</span>
                <input v-model="state.email_back" type="text" class="form-control" placeholder="도메인 주소" aria-label="도메인" id="email-back" />
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

              <label for="#">Keywords</label>
              <hr />

              <input type="submit" class="btn btn-primary mt-3" value="회원가입" />
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";

export default {
  name: "SignUp",
  setup() {
    const state = reactive({
      id: "",
      nickname: "",
      password: "",
      password_check: "",
      email_front: "",
      email_back: "",
      // validState: computed(() => {
      //   let idCheckValid = document.getElementById("idCheckValid");
      //   let idCheckInvalid = document.getElementById("idCheckInvalid");
      //   console.log("computed");
      //   console.log(idCheckValid);
      //   if (state.id.length >= 4) {
      //     idCheckValid.style.visibility = "visible";
      //     idCheckInvalid.style.visibility = "none";
      //   } else if (state.id.length == 0) {
      //     idCheckValid.style.visibility = "none";
      //     idCheckInvalid.style.visibility = "none";
      //   } else {
      //     idCheckValid.style.visibility = "none";
      //     idCheckInvalid.style.visibility = "visible";
      //   }
      //   return state.id.length;
      // }),
      // invalidFeedback: computed(() => {
      //   if (state.id.length > 0) {
      //     return "아이디는 4자 이상 입력하세요.";
      //   } else {
      //     return "아이디를 입력하세요.";
      //   }
      // }),
    });
    const checkDup = function () {
      let idCheckValid = document.getElementById("idCheckValid");
      let idCheckInvalid = document.getElementById("#id-check-invalid");
      console.log(idCheckInvalid);
      if (state.id.length >= 4) {
        idCheckValid.style.visibility = "visible";
        idCheckInvalid.style.visibility = "none";
      } else if (state.id.length == 0) {
        idCheckValid.style.visibility = "none";
        idCheckInvalid.style.visibility = "none";
      } else {
        idCheckValid.style.visibility = "none";
        idCheckInvalid.style.visibility = "visible";
      }
    };
    const autoFillEmail = function (txt) {
      let emailBack = document.getElementById("email-back");
      console.log(emailBack);
      if (txt != null) {
        state.email_back = txt;
      } else {
        state.email_back = "";
      }
      emailBack.focus();
    };
    const signUp = () => {
      alert(state);
    };
    return {
      state,
      checkDup,
      autoFillEmail,
      signUp,
    };
  },
};
</script>

<style scoped></style>
