create table user
(
    id           bigint auto_increment comment '用户id'
        primary key,
    code         varchar(11)                                                                         not null comment '学号',
    password     text                                                                                not null comment '密码',
    avator       varchar(255) default '2024-02-08-17-36-46-e789ae35-def4-4461-a4ff-c57ccde6d858.jpg' null comment '头像',
    name         varchar(5)                                                                          not null comment '姓名',
    phone        varchar(11)                                                                         null comment '手机号',
    province     varchar(10)                                                                         null comment '所在省',
    city         varchar(10)                                                                         null comment '所在市',
    clazz        int                                                                                 not null comment '班级',
    major        varchar(10)                                                                         not null comment '专业',
    academy      varchar(15)                                                                         not null comment '学院',
    duty         tinyint      default 0                                                              not null comment '职位（0成员 1副部长 2 部长 3副社长 4社长）',
    department   tinyint                                                                             null comment '部门（0-运营部，1-技术部，2-宣传部）',
    introduction text                                                                                null comment '个人介绍',
    ID_card      varchar(18)                                                                         null comment '身份证号码',
    status       tinyint(1)   default 0                                                              not null comment '状态（0正常 1封号）',
    is_deleted   tinyint(1)   default 0                                                              not null comment '是否删除（0正常 1删除）',
    create_time  datetime                                                                            not null comment '创建时间',
    update_time  datetime                                                                            not null comment '更新时间',
    create_user  int                                                                                 not null comment '创建者',
    update_user  int                                                                                 not null comment '更新者',
    role         tinyint                                                                             not null comment '角色（0普通成员，1管理员，2超管）',
    gender       tinyint      default 1                                                              not null comment '性别(1-男 0-女)'
);