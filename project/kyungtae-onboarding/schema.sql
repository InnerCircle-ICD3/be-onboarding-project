CREATE TABLE `Survey`
(
    `id`          BIGINT PRIMARY KEY NOT NULL COMMENT '설문조사 식별자',
    `external_id` CHAR(36)           NOT NULL COMMENT '외부 사용 키',
    `name`        VARCHAR(100)       NOT NULL COMMENT '설문조사 이름',
    `description` VARCHAR(500)       NOT NULL COMMENT '설문조사 설명',
    `status`      ENUM('READY', 'IN_PROGRESS', 'END') NOT NULL COMMENT '상태',
    `start_at`    TIMESTAMP          NOT NULL COMMENT '시작예정일시',
    `end_at`      TIMESTAMP NULL COMMENT '종료예정일시',
    `started_at`  TIMESTAMP NULL COMMENT '시작일시',
    `ended_at`    TIMESTAMP NULL COMMENT '종료일시',
    `created_at`  TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    `updated_at`  TIMESTAMP NULL COMMENT '수정일시',
    UNIQUE KEY `UK_Survey_external_id` (`external_id`),
    INDEX         `IDX_Survey_name` (`name`),
    INDEX         `IDX_Survey_description` (`description`)
) COMMENT '설문조사'
engine=InnoDB
default charset=utf8mb4
collate=utf8mb4_unicode_ci;

CREATE TABLE `SurveyQuestion`
(
    `id`          BIGINT PRIMARY KEY NOT NULL COMMENT '설문 받을 항목 식별자',
    `survey_id`   BIGINT             NOT NULL COMMENT '설문조사 식별자',
    `name`        VARCHAR(100)       NOT NULL COMMENT '항목 이름',
    `description` VARCHAR(500)       NOT NULL COMMENT '항목 설명',
    `input_type`  ENUM('SHORT_ANSWER', 'LONG_ANSWER', 'SINGLE_CHOICE', 'MULTI_CHOICE') NOT NULL COMMENT '입력 유형',
    `is_required` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '필수여부',
    `created_at`  TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    `updated_at`  TIMESTAMP NULL COMMENT '수정일시',
    `is_deleted`  TINYINT(1) NOT NULL DEFAULT 0 COMMENT '삭제여부',
    `deleted_at`  TIMESTAMP NULL COMMENT '삭제일시',
    FOREIGN KEY (`survey_id`) REFERENCES `Survey` (`id`),
    INDEX         `IDX_SurveyQuestion_name` (`name`),
    INDEX         `IDX_SurveyQuestion_description` (`description`)
) COMMENT '설문 받을 항목';
engine
=InnoDB
default charset=utf8mb4
collate=utf8mb4_unicode_ci;

CREATE TABLE `SurveyQuestionOption`
(
    `id`                 BIGINT PRIMARY KEY NOT NULL COMMENT '설문 받을 항목 옵션 식별자',
    `survey_question_id` BIGINT             NOT NULL COMMENT '설문 받을 항목 식별자',
    `content`            VARCHAR(100)       NOT NULL COMMENT '내용',
    `created_at`         TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    `updated_at`         TIMESTAMP NULL COMMENT '수정일시',
    `is_deleted`         TINYINT(1) NOT NULL DEFAULT 0 COMMENT '삭제여부',
    `deleted_at`         TIMESTAMP NULL COMMENT '삭제일시',
    FOREIGN KEY (`survey_question_id`) REFERENCES `SurveyQuestion` (`id`)
) COMMENT '설문 받을 항목 옵션';
engine
=InnoDB
default charset=utf8mb4
collate=utf8mb4_unicode_ci;

CREATE TABLE `SurveyQuestionResponse`
(
    `id`                 BIGINT PRIMARY KEY NOT NULL COMMENT '설문 응답 식별자',
    `survey_question_id` BIGINT             NOT NULL COMMENT '설문 받을 항목 식별자',
    `content`            VARCHAR(500)       NOT NULL COMMENT '내용',
    `input_type`         ENUM('SHORT_ANSWER', 'LONG_ANSWER', 'SINGLE_CHOICE', 'MULTI_CHOICE') NOT NULL COMMENT '입력 유형',
    `created_at`         TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    `updated_at`         TIMESTAMP NULL COMMENT '수정일시',
    `is_deleted`         TINYINT(1) NOT NULL DEFAULT 0 COMMENT '삭제여부',
    `deleted_at`         TIMESTAMP NULL COMMENT '삭제일시',
    FOREIGN KEY (`survey_question_id`) REFERENCES `SurveyQuestion` (`id`),
    INDEX                `IDX_SurveyQuestionResponse_content` (`content`)
) COMMENT '설문 응답';
engine
=InnoDB
default charset=utf8mb4
collate=utf8mb4_unicode_ci;

CREATE TABLE `SurveyQuestionOptionResponse`
(
    `survey_question_response_id` BIGINT NOT NULL COMMENT '설문 응답 식별자',
    `survey_question_option_id`   BIGINT NOT NULL COMMENT '설문 받을 항목 옵션 식별자',
    PRIMARY KEY (`survey_question_response_id`, `survey_question_option_id`),
    FOREIGN KEY (`survey_question_response_id`) REFERENCES `SurveyQuestionResponse` (`id`),
    FOREIGN KEY (`survey_question_option_id`) REFERENCES `SurveyQuestionOption` (`id`)
) COMMENT '설문 받을 항목 옵션 응답'
engine=InnoDB
default charset=utf8mb4
collate=utf8mb4_unicode_ci;