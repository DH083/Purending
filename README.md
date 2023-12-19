# PURENDING
자기 관리 어플리케이션

# 🍮 프로젝트 소개


# 📅 개발 기간
2023.0. ~ 2023.0.

# 🧑‍🤝‍🧑 팀원
✅ 팀원1: 이도희(<b>본인</b>) - 팀장, 로그인 및 회원가입 페이지, 메인페이지, 설정, 관리기능(만보기)<br>
☑ 팀원2: 노명진 - UI 디자인 및 제작, 날씨 페이지, 관리기능(스트레칭), ppt 제작<br>
☑ 팀원3: 유은경 - 일기 페이지, 관리기능(타이머)<br>

# ⚙️ 개발 환경
![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white) ![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)

# 📋 기능 소개
<h3>✅ 나의 제작 페이지</h3>
☑ 팀원의 제작 페이지
<hr>
<b>✅ 안내 페이지</b>

- 어플 아이콘
- 로딩화면
- 로그인 버튼 클릭 시 로그인 페이지 이동
- 회원가입 버튼 클릭 시 회원가입 페이지 이동
- 구글 로그인 버튼 클릭 시 구글 계정 연동 로그인

<img src="https://github.com/DH083/Purending/assets/147012079/c6f96b14-2191-4d59-a946-7b7557c6b416.png"  width="200" height="100%"/> <img src="https://github.com/DH083/Purending/assets/147012079/fd747cca-2cf6-4eae-9519-b3ae6e0c73c3.png"  width="300" height="100%"/> <img src="https://github.com/DH083/Purending/assets/147012079/131df46f-3f08-4b22-92b5-810dbff6efb9.png"  width="300" height="100%"/>
<hr>

<b>✅ 회원가입 페이지</b>

- 비밀번호, 이메일 형식 확인
- 회원가입 버튼을 클릭했을 때 공란이 있거나 유효성검사가 완료되지 않은 칸이 있다면 회원가입 실패
- 공란이 없거나, 모든 유효성검사가 완료되었다면 DB에 회원 추가, 메인 페이지 이동

<img src="https://github.com/DH083/Purending/assets/147012079/eb1a28b6-d549-4701-af6c-c6ed3eec6ce0.png"  width="300" height="100%"/>
<hr>

<b>✅ 로그인 페이지</b>

- 로그인 버튼 클릭 시 유저 로그인, 메인화면 이동
- 로그인 버튼을 클릭했을 때 공란이 있으면 로그인 실패
- 입력한 아이디와 비밀번호가 DB와 비교했을 때 일치하지 않을 시 로그인 실패

<img src="https://github.com/DH083/Purending/assets/147012079/f333f48d-8526-4ddf-ad99-e25f0b85d8a4.png"  width="300" height="100%"/>
<hr>

<b>✅ 메인 페이지</b>

- 화면 터치시 말풍선의 내용이 바뀜
- 좌측 상단에 현재 기온과 날씨
- 좌측 상단에 날씨 클릭 시 날씨 페이지로 이동
- 우측 상단에 톱니바퀴 아이콘 클릭 시 설정 메뉴 나타남
- 일기쓰기 버튼 클릭 시 다이어리 작성 페이지로 이동
- 좌측 하단 종이 아이콘 클릭 시 관리 페이지로 이동

<img src="https://github.com/DH083/Purending/assets/147012079/aa298e39-c7a8-4979-a7bf-d470e69eb56c.png"  width="300" height="100%"/> <img src="https://github.com/DH083/Purending/assets/147012079/d792286e-5a8f-4975-83d5-b940715a297e.png"  width="300" height="100%"/>
<hr>

<b>☑ 날씨 페이지</b>

- 상단에 현재 위치와 날짜, 시간 나타냄
- 중앙에 현재 날씨와 기온 나타냄
- 하단 좌측에 현재 풍랑
- 하단 중앙에 현재 습도
- 하단 우측에 새로고침 버튼 클릭 시 날씨 정보를 새로 불러옴 (새로고침을 누르지 않아도 일정 시간마다 새로 불러온다)

<img src="https://github.com/DH083/Purending/assets/147012079/a39f7af2-084f-4f2f-82ba-77c82c94ebd6.png"  width="300" height="100%"/>
<hr>

<b>✅ 관리 페이지</b>

- 다이어리 클릭 시 다이어리 페이지로 이동
- 호흡하기 클릭 시 타이머 페이지로 이동
- 산책하기 클릭 시 만보기 페이지로 이동
- 스트레칭 클릭 시 스트레칭 페이지로 이동

<img src="https://github.com/DH083/Purending/assets/147012079/60f41ab4-5b12-4f3d-9fac-b3a88bd53be0.png"  width="300" height="100%"/>
<hr>

<b>☑ 다이어리 페이지</b>

- 작성한 일기 목록
- 우측 하단에 연필 아이콘 클릭 시 일기 작성 페이지로 이동
- 날짜 부분 클릭시 달력 나타내기, 날짜 선택 후 확인 버튼 클릭 시 날짜 삽입
- 저장 버튼 클릭 시 일기 저장
- 일기 목록에서 일기 선택시 해당 일기 전체내용 보여주기
- 우측 상단에 햄버거 버튼 클릭시 일기 수정 및 삭제 가능

<img src="https://github.com/DH083/Purending/assets/147012079/d3b52e63-519a-4f7a-8815-b10e152a2a1f.png"  width="300" height="100%"/> <img src="https://github.com/DH083/Purending/assets/147012079/f518758e-bd92-47d2-8017-5b19447155c8.png"  width="300" height="100%"/> <img src="https://github.com/DH083/Purending/assets/147012079/d0d39adc-5066-4864-86d0-4b5f11768c46.png"  width="300" height="100%"/> <img src="https://github.com/DH083/Purending/assets/147012079/457a374a-b17f-4399-beae-ef96858b63e3.png"  width="300" height="100%"/> <img src="https://github.com/DH083/Purending/assets/147012079/6bc2ac11-04d2-4f27-accc-2a1e4acff351.png"  width="300" height="100%"/>
<hr>

<b>☑ 타이머 페이지</b>

- 호흡 타이머
- '+ 버튼 클릭 시 타이머 시간 설정 가능
- 시작 버튼 클릭 시 타이머 시작, 정지 버튼 클릭 시 타이머 정지
- 화살표 아이콘 클릭 시 타이머 리셋

<img src="https://github.com/DH083/Purending/assets/147012079/9eccc11c-21b0-4eda-a049-4e1b10671e32.png"  width="300" height="100%"/> <img src="https://github.com/DH083/Purending/assets/147012079/83584693-1936-4d34-9a7a-e64427d2bba1.png"  width="300" height="100%"/> <img src="https://github.com/DH083/Purending/assets/147012079/cb297209-0507-4392-b007-6df73936622f.png"  width="300" height="100%"/>
<hr>

<b>✅ 만보기 페이지</b>

- 

<img src="https://github.com/DH083/Purending/assets/147012079/9d0e1012-fac2-4d76-b235-159a743382de.png"  width="300" height="100%"/> <img src="https://github.com/DH083/Purending/assets/147012079/84a88f86-2cc3-48fe-adf4-f4ff6b13e3b4.png"  width="300" height="100%"/> <img src="https://github.com/DH083/Purending/assets/147012079/d570fd59-e7d7-4c98-889c-9332dd0e8c73.png"  width="300" height="100%"/>
<hr>

<b>✅  페이지</b>

- 

<img src=".png"  width="300" height="100%"/>
<hr>

<b>✅  페이지</b>

- 

<img src=".png"  width="300" height="100%"/>
<hr>

<b>✅  페이지</b>

- 

<img src=".png"  width="300" height="100%"/>
<hr>

<b>✅  페이지</b>

- 

<img src=".png"  width="300" height="100%"/>
<hr>
