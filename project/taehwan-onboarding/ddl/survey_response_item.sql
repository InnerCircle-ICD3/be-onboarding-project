create table PUBLIC.SURVEY_RESPONSE_ITEM
(
    ID                BIGINT auto_increment,
    SURVEY_REPONSE_ID BIGINT not null,
    ITEM_ID           BIGINT not null,
    RESPONSE_VALUE    CHARACTER VARYING,
    constraint SURVEY_RESPONSE_ITEM_PK
        primary key (ID),
    constraint SURVEY_RESPONSE_ITEM_ITEM_ID_FK
        foreign key (ITEM_ID) references PUBLIC.ITEM,
    constraint SURVEY_RESPONSE_ITEM_SURVEY_RESPONSE_ID_FK
        foreign key (SURVEY_REPONSE_ID) references PUBLIC.SURVEY_RESPONSE
);

comment on table PUBLIC.SURVEY_RESPONSE_ITEM is '개별 응답 항목';

comment on column PUBLIC.SURVEY_RESPONSE_ITEM.SURVEY_REPONSE_ID is '응답 참조키';

comment on column PUBLIC.SURVEY_RESPONSE_ITEM.ITEM_ID is '응답 항목 참조키';

comment on column PUBLIC.SURVEY_RESPONSE_ITEM.RESPONSE_VALUE is '응답값';