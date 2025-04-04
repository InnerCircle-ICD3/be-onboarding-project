-- 설문조사 테이블: 설문의 기본 정보를 저장
CREATE TABLE survey (
    survey_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '설문 고유 식별자',
    survey_name VARCHAR(255) NOT NULL COMMENT '설문 이름',
    description TEXT COMMENT '설문 설명(선택)',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시간',
    INDEX idx_survey_name (survey_name) -- 설문 이름으로 검색을 위한 인덱스
) COMMENT '설문조사 테이블: 설문의 기본 정보를 저장';

-- 질문 테이블: 설문에 포함된 질문 항목을 저장
CREATE TABLE question (
    question_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '질문 고유 식별자',
    survey_id BIGINT NOT NULL COMMENT '연결된 설문 ID',
    sequence SMALLINT NOT NULL COMMENT '질문 배치 순서',
    question_name VARCHAR(255) NOT NULL COMMENT '질문 내용',
    description TEXT COMMENT '질문 설명(선택)',
    question_type ENUM(
        'SHORT_ANSWER', -- 단답형
        'LONG_ANSWER', -- 장문형
        'SINGLE_CHOICE', -- 단일 선택 리스트
        'MULTIPLE_CHOICE' -- 다중 선택 리스트
    ) NOT NULL COMMENT '질문 유형',
    required BOOLEAN DEFAULT FALSE COMMENT '필수 응답 여부',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    FOREIGN KEY (survey_id) REFERENCES survey(survey_id) ON DELETE CASCADE, -- 설문 삭제 시 질문도 함께 삭제
    INDEX idx_survey_id (survey_id) -- 설문 ID로 질문 조회를 위한 인덱스
) COMMENT '질문 테이블: 설문에 포함된 질문 항목을 저장';

-- 질문 옵션 테이블: 선택형 질문(단일/다중 선택)의 선택지를 저장
CREATE TABLE question_option (
    option_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '옵션 고유 식별자',
    question_id BIGINT NOT NULL COMMENT '연결된 질문 ID',
    option_text VARCHAR(255) NOT NULL COMMENT '옵션 내용',
    sequence INT NOT NULL COMMENT '옵션 배치 순서',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    FOREIGN KEY (question_id) REFERENCES question(question_id) ON DELETE CASCADE, -- 질문 삭제 시 옵션도 함께 삭제
    INDEX idx_question_id (question_id) -- 질문 ID로 옵션 조회를 위한 인덱스
) COMMENT '질문 옵션 테이블: 선택형 질문(단일/다중 선택)의 선택지를 저장';

-- 설문 응답 테이블: 사용자의 설문 응답 정보를 저장
CREATE TABLE survey_response (
    response_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '응답 고유 식별자',
    survey_id BIGINT NOT NULL COMMENT '연결된 설문 ID',
    respondent_id VARCHAR(255) COMMENT '응답자 ID(필요 시)',
    status VARCHAR(50) COMMENT '응답 상태 (예: 완료, 미완료)',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '응답 제출 시간',
    FOREIGN KEY (survey_id) REFERENCES survey(survey_id) ON DELETE CASCADE, -- 설문 삭제 시 응답도 함께 삭제
    INDEX idx_survey_id (survey_id) -- 설문 ID로 응답 조회를 위한 인덱스
) COMMENT '설문 응답 테이블: 사용자의 설문 응답 정보를 저장';

-- 응답 항목 테이블: 각 질문에 대한 응답 내용을 저장
CREATE TABLE response_item (
    item_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '응답 항목 고유 식별자',
    response_id BIGINT NOT NULL COMMENT '연결된 응답 ID',
    question_id BIGINT NOT NULL COMMENT '연결된 질문 ID',
    text_value TEXT COMMENT '텍스트 응답 값 (단답형, 장문형)',
    option_id BIGINT COMMENT '선택한 옵션 ID (단일/다중 선택형)',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '응답 항목 생성 시간',
    FOREIGN KEY (response_id) REFERENCES survey_response(response_id) ON DELETE CASCADE, -- 응답 삭제 시 응답 항목도 함께 삭제
    FOREIGN KEY (question_id) REFERENCES question(question_id), -- 질문 참조
    FOREIGN KEY (option_id) REFERENCES question_option(option_id), -- 옵션 참조
    CONSTRAINT uc_response_option UNIQUE (response_id, question_id, option_id), -- 중복 응답 방지
    INDEX idx_response_question (response_id, question_id), -- 응답 ID와 질문 ID로 조회를 위한 인덱스
    INDEX idx_create_time (create_time) -- 생성 시간 기준 조회를 위한 인덱스
) COMMENT '응답 항목 테이블: 각 질문에 대한 응답 내용을 저장'; 