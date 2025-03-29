DROP TABLE IF EXISTS answer_option;
DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS survey_response;
DROP TABLE IF EXISTS item_option;
DROP TABLE IF EXISTS survey_item;
DROP TABLE IF EXISTS survey;

CREATE TABLE survey (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        description VARCHAR(255),
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE survey_item (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             survey_id BIGINT NOT NULL,
                             title VARCHAR(255) NOT NULL,
                             description VARCHAR(255),
                             input_type VARCHAR(20) NOT NULL,
                             is_required BOOLEAN NOT NULL DEFAULT FALSE,
                             is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                             FOREIGN KEY (survey_id) REFERENCES survey(id)
);

CREATE TABLE item_option (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             survey_item_id BIGINT NOT NULL,
                             value VARCHAR(255) NOT NULL,
                             FOREIGN KEY (survey_item_id) REFERENCES survey_item(id)
);

CREATE TABLE survey_response (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 survey_id BIGINT NOT NULL,
                                 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (survey_id) REFERENCES survey(id)
);

CREATE TABLE answer (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        survey_response_id BIGINT NOT NULL,
                        survey_item_id BIGINT NOT NULL,
                        item_option_id BIGINT,
                        value VARCHAR(255),
                        FOREIGN KEY (survey_response_id) REFERENCES survey_response(id),
                        FOREIGN KEY (survey_item_id) REFERENCES survey_item(id),
                        FOREIGN KEY (item_option_id) REFERENCES item_option(id)
);

CREATE TABLE answer_option (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               answer_id BIGINT NOT NULL,
                               item_option_id BIGINT NOT NULL,
                               FOREIGN KEY (answer_id) REFERENCES answer(id),
                               FOREIGN KEY (item_option_id) REFERENCES item_option(id)
);