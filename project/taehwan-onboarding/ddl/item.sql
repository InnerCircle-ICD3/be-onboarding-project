create table PUBLIC.ITEM
(
    ID          INTEGER auto_increment,
    SURVEY_ID   BIGINT            not null,
    TITLE       CHARACTER VARYING not null,
    IS_REQUIRED BOOLEAN,
    ITEM_ORDER  INTEGER,
    TYPE        CHARACTER VARYING,
    constraint ITEM_PK
        primary key (ID),
    constraint ITEM_SURVEY_ID_FK
        foreign key (SURVEY_ID) references PUBLIC.SURVEY
);

comment on table PUBLIC.ITEM is '설문받을 항목';

comment on column PUBLIC.ITEM.SURVEY_ID is '설문조사 참조키';

comment on column PUBLIC.ITEM.TITLE is '항목 이름';

comment on column PUBLIC.ITEM.IS_REQUIRED is '필수항목 여부';

comment on column PUBLIC.ITEM."order" is '항목 생성 순서';

comment on column PUBLIC.ITEM.TYPE is '입력 타입';