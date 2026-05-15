-- V1__baseline.sql
create table tbl_admin
(
    id           binary(16)                      not null
        primary key,
    admin_id     varchar(30) collate utf8mb4_bin not null,
    class_num    int                             null,
    grade        int                             null,
    name         varchar(10)                     not null,
    password     char(60)                        not null,
    role         varchar(255)                    not null,
    device_token varchar(225)                    null
);

create table tbl_admin_device_token
(
    id           binary(16)   not null
        primary key,
    admin_id     binary(16)   not null,
    device_token varchar(255) not null,
    os           varchar(10)  not null,
    constraint uk_admin_device_token
        unique (device_token),
    constraint fk_admin_device_token_admin
        foreign key (admin_id) references tbl_admin (id)
            on update cascade on delete cascade
);

create index idx_admin_device_token_admin_id
    on tbl_admin_device_token (admin_id);

create table tbl_meal
(
    id        binary(16)   not null
        primary key,
    cal       varchar(255) null,
    meal_date date         not null,
    meal_type varchar(20)  not null,
    menu      varchar(255) null,
    constraint uk_meal_date_type
        unique (meal_date, meal_type)
)
    charset = utf8mb3;

create table tbl_notice
(
    id           binary(16)  not null
        primary key,
    admin_id     binary(16)  not null,
    content      text        not null,
    create_at    datetime    null,
    teacher_name varchar(10) not null,
    title        text        not null,
    constraint fk_notice_admin
        foreign key (admin_id) references tbl_admin (id)
            on update cascade on delete cascade
);

create index idx_notice_admin_id
    on tbl_notice (admin_id);

create table tbl_outbox
(
    id         binary(16)                               not null
        primary key,
    payload    json                                     not null,
    event_type varchar(100)                             not null,
    created_at datetime(6) default CURRENT_TIMESTAMP(6) not null
);

create table tbl_schedule
(
    id         binary(16)   not null
        primary key,
    event_date date         null,
    event_name varchar(255) null
);

create table tbl_self_study
(
    id           binary(16)  not null
        primary key,
    date         date        not null,
    floor        int         not null,
    teacher_name varchar(10) not null
);

create table tbl_timetable
(
    id           binary(16)   not null
        primary key,
    class_num    int          not null,
    day_week     int          not null,
    grade        int          not null,
    period int not null,
    subject_name varchar(255) not null
);

create table tbl_topic_subscription
(
    id            binary(16)   not null
        primary key,
    device_token  text         null,
    is_subscribed bit          not null,
    topic         varchar(255) null,
    user_id       varchar(255) null
)
    charset = utf8mb3;

create table tbl_user
(
    id                binary(16)                      not null
        primary key,
    account_id        varchar(40) collate utf8mb4_bin not null,
    class_num         tinyint                         not null,
    grade             tinyint                         not null,
    name              varchar(10)                     not null,
    num               tinyint                         not null,
    password          char(60)                        not null,
    profile_file_name varchar(255)                    null,
    role              varchar(255)                    not null,
    device_token      varchar(255)                    null
);

create table tbl_after_school
(
    id        binary(16)   not null
        primary key,
    class_num tinyint      not null,
    grade     tinyint      not null,
    num       tinyint      not null,
    status1   varchar(255) not null,
    status2   varchar(255) not null,
    status3   varchar(255) not null,
    user_id   binary(16)   not null,
    user_name varchar(10)  not null,
    constraint fk_after_school_user
        foreign key (user_id) references tbl_user (id)
            on update cascade on delete cascade
);

create index idx_after_school_user_id
    on tbl_after_school (user_id);

create table tbl_application
(
    id               binary(16)                              not null
        primary key,
    application_kind varchar(255)                            not null,
    application_type varchar(255)                            not null,
    class_num        tinyint                                 not null,
    date             date                                    not null,
    end              varchar(255)                            null,
    grade            tinyint                                 not null,
    num              tinyint                                 not null,
    reason           varchar(255) collate utf8mb4_unicode_ci null,
    start            varchar(255)                            not null,
    status           varchar(255)                            not null,
    teacher_name     varchar(10)                             null,
    user_id          binary(16)                              null,
    user_name        varchar(10)                             not null,
    constraint fk_application_user
        foreign key (user_id) references tbl_user (id)
            on update cascade on delete set null
)
    charset = utf8mb3;

create index idx_application_user_id
    on tbl_application (user_id);

create table tbl_application_story
(
    id                  binary(16)                              not null
        primary key,
    date                date                                    not null,
    end                 varchar(255)                            null,
    reason              varchar(255) collate utf8mb4_unicode_ci null,
    start               varchar(255)                            not null,
    type                varchar(20)                             not null,
    user_id             binary(16)                              not null,
    user_name           varchar(10)                             not null,
    return_teacher_name varchar(10)                             null,
    constraint fk_application_story_user
        foreign key (user_id) references tbl_user (id)
            on update cascade on delete cascade
)
    charset = utf8mb3;

create index idx_application_story_user_id
    on tbl_application_story (user_id);

create table tbl_attendance
(
    id        binary(16)   not null
        primary key,
    class_num tinyint      not null,
    club      varchar(255) null,
    floor     int          null,
    grade     tinyint      not null,
    num       tinyint      not null,
    period10  varchar(255) not null,
    period6   varchar(255) not null,
    period7   varchar(255) not null,
    period8   varchar(255) not null,
    period9   varchar(255) not null,
    user_id   binary(16)   not null,
    user_name varchar(10)  not null,
    place     varchar(255) null,
    constraint fk_attendance_user
        foreign key (user_id) references tbl_user (id)
            on update cascade on delete cascade
);

create index idx_attendance_user_id
    on tbl_attendance (user_id);

create table tbl_classroom
(
    id           binary(16)             not null
        primary key,
    class_num    tinyint                not null,
    classroom    varchar(20)            not null,
    end_period   tinyint                not null,
    floor        tinyint                not null,
    grade        tinyint                not null,
    num          tinyint                not null,
    start_period tinyint                not null,
    status       varchar(255)           not null,
    user_id      binary(16)             not null,
    user_name    varchar(10)            not null,
    move         varchar(20) default '' not null,
    constraint fk_classroom_user
        foreign key (user_id) references tbl_user (id)
            on update cascade on delete cascade
);

create index idx_classroom_user_id
    on tbl_classroom (user_id);

create table tbl_status
(
    id        binary(16)   not null
        primary key,
    class_num tinyint      not null,
    grade     tinyint      not null,
    num       tinyint      not null,
    status    varchar(255) not null,
    user_id   binary(16)   not null,
    user_name varchar(10)  not null,
    constraint fk_status_user
        foreign key (user_id) references tbl_user (id)
            on update cascade on delete cascade
);

create index idx_status_user_id
    on tbl_status (user_id);

create table tbl_user_device_token
(
    id           binary(16)   not null
        primary key,
    user_id      binary(16)   not null,
    device_token varchar(255) not null,
    os           varchar(10)  not null,
    constraint uk_user_device_token
        unique (device_token),
    constraint fk_user_device_token_user
        foreign key (user_id) references tbl_user (id)
            on update cascade on delete cascade
);

create index idx_user_device_token_user_id
    on tbl_user_device_token (user_id);

create table tbl_weekend_meal
(
    id        binary(16)   not null
        primary key,
    class_num tinyint      not null,
    grade     tinyint      not null,
    num       tinyint      not null,
    status    varchar(255) not null,
    user_id   binary(16)   not null,
    user_name varchar(10)  not null,
    constraint fk_weekend_meal_user
        foreign key (user_id) references tbl_user (id)
            on update cascade on delete cascade
);

create index id_weekend_meal_id
    on tbl_weekend_meal (user_id);

create table tbl_weekend_meal_period
(
    id       binary(16) not null
        primary key,
    end      date       null,
    month    int        null,
    start    date       null,
    admin_id binary(16) not null,
    constraint fk_weekend_meal_period_admin
        foreign key (admin_id) references tbl_admin (id)
            on update cascade on delete cascade
)
    charset = utf8mb3;

create index idx_weekend_meal_period_admin_id
    on tbl_weekend_meal_period (admin_id);

create table user
(
    grade     int null,
    class_num int null,
    num       int null,
    name      int null
);

