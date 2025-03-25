create table surveys
(
    id          bigint primary key,
    title       varchar(100) not null comment '설문조사 이름',
    description text         not null comment '설문조사 설명',
    created_at  timestamp(2) default current_timestamp(2),
    updated_at  timestamp(2) default current_timestamp(2),
    deleted_at  timestamp(2)
);

create table survey_items
(
    id            bigint primary key,
    survey_id     bigint,
    title         varchar(100) not null comment '항목 이름',
    description   text         not null comment '항목 설명',
    item_type     varchar(50)  not null comment '항목 입력형태(SHORT_TEXT:단답형, LONG_TEXT:장문형, SINGLE_SELECT:단일선택 리스트, MULTI_SELECT:다중선택 리스트)',
    is_required   boolean      not null default false comment '항목 필수여부',
    display_order int          not null comment '항목 표출순서',
    created_at    timestamp(2)          default current_timestamp(2),
    updated_at    timestamp(2)          default current_timestamp(2),
    deleted_at    timestamp(2)
);

create table survey_item_options
(
    id            bigint primary key,
    item_id       bigint       not null,
    title         varchar(100) not null comment '옵션 이름',
    display_order int          not null comment '항목 표출순서',
    created_at    timestamp(2) default current_timestamp(2),
    updated_at    timestamp(2) default current_timestamp(2),
    deleted_at    timestamp(2)
);

create table responses
(
    id               bigint primary key,
    survey_id        bigint       not null,
    respondent_email varchar(254) not null comment '응답자 이메일',
    title            varchar(100) not null comment '제출 당시의 설문조사 이름',
    description      text         not null comment '제출 당시의 설문조사 설명',
    created_at       timestamp(2) default current_timestamp(2),
    updated_at       timestamp(2) default current_timestamp(2),
    deleted_at       timestamp(2)
);

create table response_items
(
    id             bigint primary key,
    response_id    bigint       not null,
    survey_item_id bigint       not null,
    title          varchar(100) not null comment '제출 당시의 항목 이름',
    description    text         not null comment '제출 당시의 항목 설명',
    item_type      varchar(50)  not null comment '제출 당시의 항목 입력형태',
    answer         text         not null comment '답변',
    created_at     timestamp(2) default current_timestamp(2),
    updated_at     timestamp(2) default current_timestamp(2),
    deleted_at     timestamp(2)
);