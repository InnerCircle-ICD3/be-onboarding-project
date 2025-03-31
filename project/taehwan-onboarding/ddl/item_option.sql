create table PUBLIC.ITEM_OPTION
(
    ID           BIGINT auto_increment,
    ITEM_ID      BIGINT,
    TITLE        CHARACTER VARYING,
    OPTION_ORDER INTEGER,
    constraint ITEM_OPTION_PK
        primary key (ID),
    constraint ITEM_OPTION_ITEM_ID_FK
        foreign key (ITEM_ID) references PUBLIC.ITEM
);

comment on table PUBLIC.ITEM_OPTION is '입력 항목 옵션';

comment on column PUBLIC.ITEM_OPTION.ID is '입력 항목 식별자';

comment on column PUBLIC.ITEM_OPTION.ITEM_ID is '입력 항목 참조키';

comment on column PUBLIC.ITEM_OPTION.OPTION_ORDER is '선택항목 정렬순서';