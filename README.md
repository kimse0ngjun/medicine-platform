# 💊 Medicine Platform

![React](https://img.shields.io/badge/React-61DAFB?logo=react&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?logo=springboot&logoColor=white)
![Java](https://img.shields.io/badge/Java_21-007396?logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)

Medicine Platform은 의약품 리콜 정보를 쉽고 빠르게 조회할 수 있도록 개발한 개인 프로젝트입니다.

사용자는 제품명, LOT 번호, 의약품 이미지를 이용하여 리콜 여부를 확인할 수 있으며, Google Vision OCR을 통해 LOT 번호를 자동 추출하고,
OpenAI API를 활용하여 의약품 정보를 요약해 제공합니다.

---

# 📖 프로젝트 소개

- 제품명, LOT 번호, 이미지(OCR)를 이용한 의약품 리콜 조회
- JWT 기반 로그인 및 인증
- OpenAI API를 활용한 의약품 정보 요약
- Google Vision OCR을 이용한 LOT 번호 자동 인식
- 조회 이력 저장 및 관리

---

# 👥 개발자

| 이름   | 담당               |
| ------ | ------------------ |
| 김성준 | Backend / Frontend |

---

# 🛠 기술 스택

## Frontend

- React
- Vite
- React Router
- CSS

## Backend

- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA

## Database

- MySQL

## AI

- OpenAI API

## OCR

- Google Vision API

## Version Control

- Git
- GitHub

---

# 🏗 시스템 아키텍처

```text
                 사용자
                    │
                    ▼
          React (Vite Frontend)
                    │
        HTTP / REST API (JWT)
                    │
                    ▼
      Spring Boot (Backend Server)
        │           │            │
        │           │            │
        ▼           ▼            ▼
     MySQL      OpenAI API   Google Vision API
```

---

# 📂 프로젝트 구조

```text
frontend
├── src
│   ├── api
│   ├── assets
│   ├── components
│   ├── context
│   ├── pages
│   ├── style
│   ├── App.jsx
│   └── main.jsx
├── public
├── package.json
└── vite.config.js

backend
├── src
│   └── main
│       ├── java
│       │   ├── config
│       │   ├── controller
│       │   ├── dto
│       │   ├── entity
│       │   ├── repository
│       │   ├── security
│       │   ├── service
│       │   ├── util
│       │   └── MedicinePlatformApplication.java
│       └── resources
├── build.gradle
└── settings.gradle
```

---

# ✨ 주요 기능

## 🔐 회원 기능

- 회원가입
- 로그인
- JWT 인증
- 아이디 찾기
- 비밀번호 찾기

---

## 💊 의약품 조회

### 제품명 조회

- 제품명 검색
- 리콜 여부 확인

### LOT 번호 조회

- LOT 번호 조회
- 회수 여부 확인

### 이미지 조회

- Google Vision OCR
- LOT 번호 자동 추출
- 리콜 조회

---

## 🤖 AI 기능

- OpenAI API 활용
- 의약품 정보 요약
- 사용자 친화적인 설명 제공

---

## 👤 마이페이지

- 회원 정보 조회
- 조회 이력 확인
- 조회 이력 삭제
- 프론트 페이지네이션 적용

---

# ⚙ 실행 방법

## Frontend

```bash
cd frontend

npm install

npm run dev
```

## Backend

```bash
cd backend

./gradlew bootRun
```

---

# 📌 API 명세

| 기능        | Method | URL                    |
| ----------- | ------ | ---------------------- |
| 로그인      | POST   | /api/v1/auth/login     |
| 회원가입    | POST   | /api/v1/auth/signup    |
| 제품 조회   | GET    | /api/v1/recalls/search |
| LOT 조회    | GET    | /api/v1/recalls/lot    |
| 이미지 조회 | POST   | /api/v1/recalls/image  |
| AI 요약     | POST   | /api/v1/ai/summary     |

---

# 📌 주요 구현 사항

- JWT 기반 인증 및 인가
- Spring Security 적용
- Google Vision OCR 연동
- OpenAI API 연동
- 조회 이력 저장 및 삭제 기능
- 프론트엔드 페이지네이션 구현

---

# 📄 License

This project is for educational purposes.
