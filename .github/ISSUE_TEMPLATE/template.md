---
name: 백엔드 온보딩 프로젝트
about: 백엔드 온보딩 프로젝트 이슈 생성을 위한 템플릿
title: "백엔드 온보딩 프로젝트 - {이름; 예: 진태양}"
labels: ''
assignees: ''
---

## 작업 브랜치 URL

여기에 작업하고 있는 브랜치 URL 링크를 추가합니다. (예: https://github.com/InnerCircle-ICD3/be-onboarding-project/tree/taeyang-onboarding)

## 작업 상세 내용

- [x] Branch 생성
- [ ] 프로젝트 디렉토리 구조 설정
- [ ] 프로젝트 설계
  - [ ] 도메인 모델 설계 (Survey, Question, Response 등)
  - [ ] 데이터베이스 스키마 설계
  - [ ] 서비스 아키텍처 설계
  - [ ] 예외 처리 전략 수립
- [ ] 프로젝트 셋업
  - [ ] Spring Boot 프로젝트 생성
  - [ ] 의존성 추가 (Spring Data JPA, H2 등)
  - [ ] 기본 설정 파일 작성
- [ ] 핵심 기능 구현
  - [ ] 도메인 모델 구현
    - [ ] Survey 엔티티 구현
    - [ ] Question 엔티티 구현
    - [ ] Response 엔티티 구현
  - [ ] 설문조사 생성 API 구현
    - [ ] Repository 구현
    - [ ] Service 구현
    - [ ] Controller 구현
    - [ ] 유효성 검증 로직 구현
  - [ ] 설문조사 수정 API 구현
    - [ ] Service 구현
    - [ ] Controller 구현
    - [ ] 기존 응답 유지 로직 구현
  - [ ] 설문조사 응답 제출 API 구현
    - [ ] Repository 구현
    - [ ] Service 구현
    - [ ] Controller 구현
    - [ ] 유효성 검증 로직 구현
  - [ ] 설문조사 응답 조회 API 구현
    - [ ] Service 구현
    - [ ] Controller 구현
    - [ ] (Advanced) 검색 기능 구현
