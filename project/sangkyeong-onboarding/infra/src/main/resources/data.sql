
-- 테스트 데이터 삽입

-- 설문조사 1: 고객 만족도 조사
INSERT INTO survey (id, title, description, created_at, updated_at)
VALUES (1, '고객 만족도 조사', '저희 서비스에 대한 만족도를 평가해주세요.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 설문조사 1의 문항들
-- 1. 단답형 질문
INSERT INTO survey_item (id, survey_id, title, description, input_type, is_required)
VALUES (1, 1, '이름을 입력해주세요.', NULL, 'SHORT_TEXT', true);

-- 2. 장문형 질문
INSERT INTO survey_item (id, survey_id, title, description, input_type, is_required)
VALUES (2, 1, '저희 서비스에 대한 의견을 자유롭게 작성해주세요.', '개선사항이나 불편한 점을 알려주세요.', 'LONG_TEXT', false);

-- 3. 단일 선택형 질문
INSERT INTO survey_item (id, survey_id, title, description, input_type, is_required)
VALUES (3, 1, '전반적인 서비스 만족도는 어떠신가요?', NULL, 'SINGLE_CHOICE', true);

-- 단일 선택형 질문의 선택지
INSERT INTO item_option (id, survey_item_id, value) VALUES (1, 3, '매우 만족');
INSERT INTO item_option (id, survey_item_id, value) VALUES (2, 3, '만족');
INSERT INTO item_option (id, survey_item_id, value) VALUES (3, 3, '보통');
INSERT INTO item_option (id, survey_item_id, value) VALUES (4, 3, '불만족');
INSERT INTO item_option (id, survey_item_id, value) VALUES (5, 3, '매우 불만족');

-- 4. 다중 선택형 질문
INSERT INTO survey_item (id, survey_id, title, description, input_type, is_required)
VALUES (4, 1, '가장 자주 이용하시는 기능은 무엇인가요? (복수 선택 가능)', NULL, 'MULTIPLE_CHOICE', false);

-- 다중 선택형 질문의 선택지
INSERT INTO item_option (id, survey_item_id, value) VALUES (6, 4, '주문 기능');
INSERT INTO item_option (id, survey_item_id, value) VALUES (7, 4, '배송 조회');
INSERT INTO item_option (id, survey_item_id, value) VALUES (8, 4, '고객 서비스');
INSERT INTO item_option (id, survey_item_id, value) VALUES (9, 4, '리뷰 작성');
INSERT INTO item_option (id, survey_item_id, value) VALUES (10, 4, '포인트 적립');

-- 설문조사 2: 이벤트 참여 신청서
INSERT INTO survey (id, title, description, created_at, updated_at)
VALUES (2, '2025년 봄 신제품 출시 이벤트 신청', '신제품 체험단 모집을 위한 신청서입니다.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 설문조사 2의 문항들
-- 1. 단답형 질문
INSERT INTO survey_item (id, survey_id, title, description, input_type, is_required)
VALUES (5, 2, '이름', NULL, 'SHORT_TEXT', true);

-- 2. 단답형 질문
INSERT INTO survey_item (id, survey_id, title, description, input_type, is_required)
VALUES (6, 2, '연락처', '연락 가능한 전화번호를 입력해주세요.', 'SHORT_TEXT', true);

-- 3. 단답형 질문
INSERT INTO survey_item (id, survey_id, title, description, input_type, is_required)
VALUES (7, 2, '이메일', NULL, 'SHORT_TEXT', true);

-- 4. 장문형 질문
INSERT INTO survey_item (id, survey_id, title, description, input_type, is_required)
VALUES (8, 2, '지원 동기', '신제품 체험단에 지원하시는 이유를 작성해주세요.', 'LONG_TEXT', true);

-- 5. 단일 선택형 질문
INSERT INTO survey_item (id, survey_id, title, description, input_type, is_required)
VALUES (9, 2, '체험을 원하는 제품', NULL, 'SINGLE_CHOICE', true);

-- 단일 선택형 질문의 선택지
INSERT INTO item_option (id, survey_item_id, value) VALUES (11, 9, '스마트 워치');
INSERT INTO item_option (id, survey_item_id, value) VALUES (12, 9, '무선 이어폰');
INSERT INTO item_option (id, survey_item_id, value) VALUES (13, 9, '블루투스 스피커');

-- 설문조사 1에 대한 응답 예시
-- 응답 1
INSERT INTO survey_response (id, survey_id, created_at)
VALUES (1, 1, CURRENT_TIMESTAMP);

-- 응답 1의 답변들
INSERT INTO answer (id, survey_response_id, survey_item_id, value)
VALUES (1, 1, 1, '김철수');

INSERT INTO answer (id, survey_response_id, survey_item_id, value)
VALUES (2, 1, 2, '전반적으로 만족하지만 배송이 조금 느린 것 같습니다. 배송 속도가 개선되면 좋겠습니다.');

INSERT INTO answer (id, survey_response_id, survey_item_id, item_option_id)
VALUES (3, 1, 3, 2);

-- 다중 선택형 질문에 대한 답변
INSERT INTO answer (id, survey_response_id, survey_item_id)
VALUES (4, 1, 4);

INSERT INTO answer_option (id, answer_id, item_option_id)
VALUES (1, 4, 6);

INSERT INTO answer_option (id, answer_id, item_option_id)
VALUES (2, 4, 7);

-- 응답 2
INSERT INTO survey_response (id, survey_id, created_at)
VALUES (2, 1, CURRENT_TIMESTAMP);

-- 응답 2의 답변들
INSERT INTO answer (id, survey_response_id, survey_item_id, value)
VALUES (5, 2, 1, '이영희');

INSERT INTO answer (id, survey_response_id, survey_item_id, value)
VALUES (6, 2, 2, '상품 품질이 좋고 고객 서비스도 친절해요. 계속 이용할 예정입니다.');

INSERT INTO answer (id, survey_response_id, survey_item_id, item_option_id)
VALUES (7, 2, 3, 1);

-- 다중 선택형 질문에 대한 답변
INSERT INTO answer (id, survey_response_id, survey_item_id)
VALUES (8, 2, 4);

INSERT INTO answer_option (id, answer_id, item_option_id)
VALUES (3, 8, 8);

INSERT INTO answer_option (id, answer_id, item_option_id)
VALUES (4, 8, 9);

INSERT INTO answer_option (id, answer_id, item_option_id)
VALUES (5, 8, 10);