create table PUBLIC.SURVEY_RESPONSE
(
    ID            BIGINT not null,
    SURVEY_ID     BIGINT not null,
    RESPONDENT_ID CHARACTER VARYING,
    RESPONSE_AT   TIMESTAMP,
    STATUS        CHARACTER VARYING,
    constraint SURVEY_RESPONSE_PK
        primary key (ID),
    constraint SURVEY_RESPONSE_SURVEY_ID_FK
        foreign key (SURVEY_ID) references PUBLIC.SURVEY
);

comment on table PUBLIC.SURVEY_RESPONSE is '응답 항목';

comment on column PUBLIC.SURVEY_RESPONSE.SURVEY_ID is '설문조사 참조키';

comment on column PUBLIC.SURVEY_RESPONSE.RESPONDENT_ID is '제출자 아이디(익명 시 null)';

comment on column PUBLIC.SURVEY_RESPONSE.RESPONSE_AT is '제출시각';

comment on column PUBLIC.SURVEY_RESPONSE.STATUS is '최종응답 상태(구현 시 제거될 수 있음)';