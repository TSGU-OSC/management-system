CREATE TABLE `announcement`
(
    `id`          bigint                                                NOT NULL AUTO_INCREMENT COMMENT '公告id',
    `title`       varchar(255)                                          NOT NULL COMMENT '公告标题',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '公告内容',
    `status`      tinyint DEFAULT 0 COMMENT '公告状态（0普通公告 1紧急公告）',
    `create_user` bigint                                                NOT NULL COMMENT '创建人',
    `update_user` bigint                                                NOT NULL COMMENT '修改人',
    `create_time` datetime                                              NOT NULL COMMENT '创建时间',
    `update_time` datetime                                              NOT NULL COMMENT '修改时间',
    `is_deleted`  tinyint DEFAULT 0                                     NOT NULL COMMENT '是否删除(0未删除 1已删除)',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;