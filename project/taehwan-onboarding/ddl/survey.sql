create table PUBLIC.SURVEY
(
    ID          BIGINT auto_increment,
    TITLE       CHARACTER VARYING,
    DESCRIPTION CHARACTER VARYING,
    VERSION     BIGINT,
    CREATED_AT  TIMESTAMP default CURRENT_TIMESTAMP not null,
    UPDATED_AT  TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    constraint SURVEY_PK
        primary key (ID)
);

comment on table PUBLIC.SURVEY is '설문조사';

comment on column PUBLIC.SURVEY.ID is '설문조사 식별자';

comment on column PUBLIC.SURVEY.TITLE is '설문조사 이름';

comment on column PUBLIC.SURVEY.DESCRIPTION is '설문조사 설명';

comment on column PUBLIC.SURVEY.VERSION is '동시성 검증용 버전';

comment on column PUBLIC.SURVEY.CREATED_AT is '생성일시';

comment on column PUBLIC.SURVEY.UPDATED_AT is '수정일시';