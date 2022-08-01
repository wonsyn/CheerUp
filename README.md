# Cheer up, 취Up

B104

연창모, 윤원상, 장원석, 장진세, 정제희

![팀원소개](C:\SSAFY\common\readme.assets\팀원소개.png)



## :bell: Git Convention

#### 1) flow

> 피처 이름 수정 > 패치 > 풀 > 깃플로우 [기능닫기] > 푸시 > 깃플로우 [새기능 시작]



#### 2) commit

`#지라이슈번호 [Add] 네이버 소셜로그인 버튼 추가`

`#지라이슈번호 [Fix] 추천기능 버그 수정`



- Add  : 기능 추가
- Style : 세미콜론 누락 등 코드변경이 없는 경우
- Fix : 버그 수정
- Update : Fix와 달리 원래 정상적으로 동작했지만 보완의 개념
- Remove(Clean이나 Eliminate) : ‘unnecessary’, ‘useless’, ‘unneeded’, ‘unused’, ‘duplicated’가 붙는 경우
- Refactor : 코드 리팩토링
- Test : 테스트 코드
- Docs: 문서 변경
- Rename : 파일 이름 변경 



## :bell: Jira Convention

`이름 [기능] 할일`

### 예시

연아 [Main] UI 틀잡기

메시 [Feed] 게시글(피드)스크랩하기

흥민 [news - Summary] 뉴스 API 스케줄러 구현



### ! 필수

- epic : `FE`, `BE`, `ML`, `문서작업` 중 선택
- Assignee 지정
- 스토리 포인트 지정
  - 1point 당 1시간
  - 4시간을 넘기지 않는 것을 권장

### 선택

- desccription에 상세 내용 작성
- 이슈 완료할때 desccription에 코드 작성
- 실제 작업과 시간 측정해 sub-task 생성