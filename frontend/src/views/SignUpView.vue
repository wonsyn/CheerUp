<template>
  <div class="container my-5">
    <div style="font-weight: bold; font-size: 40px">회원가입</div>
    <div class="row">
      <div class="col py-3" style="display: inline-block; vertical-align: middle">
        <img src="@/assets/logo.png" alt="logo" style="width: 65%; height: 50%" />
        <br />
        <div>취Up에 가입하고 다양한 소식을 받아보세요!</div>
      </div>
      <div class="col py-3">
        <div class="py-3 px-3">
          <div style="border: 1px black solid; padding: 5%; border-radius: 10px; box-shadow: 5px 5px 5px">
            <form action="#" class="needs-validation">
              <label for="id" class="form-label">ID</label>
              <div class="input-group mb-3" required>
                <input v-model="id" id="id" type="text" class="form-control" placeholder="ID" aria-label="User ID" aria-describedby="btn_check_dup" required />
                <button class="btn btn-outline-primary" type="button" id="btn_check_dup" @click="checkDup">중복확인</button>
              </div>
              <div id="id-check-valid" class="invalid-feedback">ID는 4자 이상 입력하세요.</div>
              <div id="id-check-invalid" class="valid-feedback">사용 가능한 ID입니다.</div>
            </form>

            <label for="nickname" class="form-label">Nickname</label>
            <div class="input-group mb-3">
              <input v-model="nickname" id="nickname" type="text" class="form-control" placeholder="Nickname" aria-label="User Nickname" aria-describedby="btn_check_dup" />
              <button class="btn btn-outline-primary" type="button" id="btn_check_dup">중복확인</button>
            </div>

            <label for="password" class="form-label">Password</label>
            <div class="input-group mb-3">
              <input v-model="password" id="password" type="password" class="form-control" placeholder="Password" aria-label="Recipient's username" />
            </div>

            <label for="password_check" class="form-label">Password Check</label>
            <div class="input-group mb-3">
              <input v-model="password_check" id="password_check" type="password" class="form-control" placeholder="Password" aria-label="Recipient's username" />
            </div>

            <label for="email" class="form-label">Email</label>
            <div class="input-group mb-3">
              <input v-model="email_front" id="email" type="text" class="form-control" placeholder="Email" aria-label="Username" />
              <span class="input-group-text">@</span>
              <input v-model="email_back" type="text" class="form-control" placeholder="Server" aria-label="Server" readonly />
              <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">선택하세요..</button>
              <ul class="dropdown-menu dropdown-menu-end">
                <li><a class="dropdown-item">naver.com</a></li>
                <li><a class="dropdown-item">gmail.com</a></li>
                <li><a class="dropdown-item">nate.com</a></li>
                <li><a class="dropdown-item">hanmail.net</a></li>
                <li><hr class="dropdown-divider" /></li>
                <li><a class="dropdown-item">직접입력 </a></li>
              </ul>
            </div>

            <label for="#">Keywords</label>
            <hr />

            <button type="button" class="btn btn-primary mt-3" disabled>회원가입</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      id: "",
      nickname: "",
      password: "",
      password_check: "",
      email_front: "",
      email_back: "",
    };
  },
  computed: {
    state() {
      let idCheckValid = document.getElementById("id-check-valid");
      let idCheckInvalid = document.getElementById("id-check-invalid");

      if (this.name.length >= 4) {
        idCheckValid.style.visibility = "visible";
        idCheckInvalid.style.visibility = "none";
      } else if (this.name.length == 0) {
        idCheckValid.style.visibility = "none";
        idCheckInvalid.style.visibility = "none";
      } else {
        idCheckValid.style.visibility = "none";
        idCheckInvalid.style.visibility = "visible";
      }
      return this.name.length;
    },
    invalidFeedback() {
      if (this.id.length > 0) {
        return "아이디는 4자 이상 입력하세요.";
      }
      return "아이디를 입력하세요.";
    },
  },
  methods: {
    checkDup() {
      let idCheckValid = document.getElementById("id-check-valid");
      let idCheckInvalid = document.getElementById("id-check-invalid");

      if (this.name.length >= 4) {
        idCheckValid.style.visibility = "visible";
        idCheckInvalid.style.visibility = "none";
      } else if (this.name.length == 0) {
        idCheckValid.style.visibility = "none";
        idCheckInvalid.style.visibility = "none";
      } else {
        idCheckValid.style.visibility = "none";
        idCheckInvalid.style.visibility = "visible";
      }
    },
  },
};
</script>

<style scoped></style>
