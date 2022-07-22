<template>
  <div id="login">로그인</div>
  <form>    
    <div class="input-group mb-3">
      <span class="input-group-text" id="basic-addon1">아이디</span>
      <input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1" />
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text" id="basic-addon2">비밀번호</span>
      <input type="text" class="form-control" placeholder="Password" aria-label="Password" aria-describedby="basic-addon2" />
    </div>
    <button type="submit" class="btn btn-primary">로그인</button>
  </form>
  <div>
    <p>social login</p>
    <div><button>Sign in with Naver</button></div>
    <div><button>Sign in with Kakao</button></div>
    <div><button>Sign in with Facebook</button></div>
    <div><button>Sign in with Twitter</button></div>
    <div><button>Sign in with Google</button></div>
  </div>
</template>

<script>
export default {
  name: "LoginView",
  components: {},
};
</script>

<style scoped></style>
