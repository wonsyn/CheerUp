<template>
  <div class="home">
    <div class="container">
      <div style="border: 1px black solid; border-radius: 7px">
        <div class="row p-3">
          <select class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
            <option selected>전체</option>
            <option value="1">뉴스</option>
            <option value="2">정보글</option>
          </select>
          <select class="form-select form-select-sm col d-flex mx-3" aria-label=".form-select-sm example">
            <option selected>산업군</option>
            <option value="1">금융권</option>
            <option value="2">게임</option>
            <option value="3">IT 서비스</option>
            <option value="4">보안</option>
            <option value="5">솔루션 컨설팅</option>
            <option value="6">모바일</option>
          </select>
          <div class="col text-end me-3">
            <button type="button" class="btn" style="background-color: #00dd99">검색</button>
          </div>
          <div class="text-start pt-3 align-middle">
            <div class="form-check form-switch">
              <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault" />
              <label class="form-check-label" for="flexSwitchCheckDefault">팔로우 한 사람 게시글 보기</label>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import
export default {
  name: "HomeView",
  components: {},
};
</script>
