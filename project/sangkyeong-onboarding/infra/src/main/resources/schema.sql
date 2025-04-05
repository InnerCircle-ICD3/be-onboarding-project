DROP TABLE IF EXISTS survey;
DROP TABLE IF EXISTS survey_item;
DROP TABLE IF EXISTS item_option;
DROP TABLE IF EXISTS survey_response;
DROP TABLE IF EXISTS text_answer;
DROP TABLE IF EXISTS choice_answer_option;
DROP TABLE IF EXISTS choice_answer;

-- 설문조사 테이블 생성
CREATE TABLE survey (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        description TEXT NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 설문조사 항목 테이블 생성
CREATE TABLE survey_item (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             survey_id BIGINT NOT NULL,
                             title VARCHAR(255) NOT NULL,
                             description TEXT,
                             input_type VARCHAR(20) NOT NULL,
                             is_required BOOLEAN NOT NULL DEFAULT FALSE,
                             is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- 항목 선택지 테이블 생성
CREATE TABLE item_option (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             survey_item_id BIGINT NOT NULL,
                             value VARCHAR(255) NOT NULL,
                             is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- 설문조사 응답 테이블 생성
CREATE TABLE survey_response (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 survey_id BIGINT NOT NULL,
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 텍스트 답변 테이블 생성
CREATE TABLE text_answer (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             survey_response_id BIGINT NOT NULL,
                             survey_item_id BIGINT NOT NULL,
                             value TEXT NOT NULL
);

-- 선택형 답변 테이블 생성
CREATE TABLE choice_answer (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               survey_response_id BIGINT NOT NULL,
                               survey_item_id BIGINT NOT NULL
);

-- 선택형 답변 옵션 테이블 생성
CREATE TABLE choice_answer_option (
                                       choice_answer_id BIGINT NOT NULL,
                                       item_option_ids BIGINT NOT NULL
);

-- -- 인덱스 생성
-- CREATE INDEX idx_survey_item_survey_id ON survey_item (survey_id);
-- CREATE INDEX idx_item_option_survey_item_id ON item_option (survey_item_id);
-- CREATE INDEX idx_survey_response_survey_id ON survey_response (survey_id);
-- CREATE INDEX idx_text_answer_survey_response_id ON text_answer (survey_response_id);
-- CREATE INDEX idx_text_answer_survey_item_id ON text_answer (survey_item_id);
-- CREATE INDEX idx_choice_answer_survey_response_id ON choice_answer (survey_response_id);
-- CREATE INDEX idx_choice_answer_survey_item_id ON choice_answer (survey_item_id);
-- CREATE INDEX idx_choice_answer_options_choice_answer_id ON choice_answer_option (choice_answer_id);