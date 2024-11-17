select * from pgxc_group;

create table if not exists public.announcement
(
    id          bigint       not null
        primary key,
    title       varchar(255) not null,
    content     text,
    status      tinyint,
    create_user bigint       not null,
    update_user bigint       not null,
    create_time timestamp    not null,
    update_time timestamp    not null,
    is_deleted  tinyint     not null
)distribute by shard(id) to group default_group;

comment on column public.announcement.id is '公告id';

comment on column public.announcement.title is '公告标题';

comment on column public.announcement.content is '公告内容';

comment on column public.announcement.status is '公告状态（0普通公告 1紧急公告）';

comment on column public.announcement.create_user is '创建人';

comment on column public.announcement.update_user is '修改人';

comment on column public.announcement.create_time is '创建时间';

comment on column public.announcement.update_time is '修改时间';

comment on column public.announcement.is_deleted is '是否删除(0未删除 1已删除)';

alter table public.announcement
    owner to opentenbase;

create table if not exists public."user"
(
    id           bigserial
        primary key,
    code         varchar(11)            not null,
    password     text                   not null,
    avator       varchar(255) default ''::character varying,
    name         varchar(15)            not null,
    phone        varchar(11)  default NULL::character varying,
    province     varchar(50)  default NULL::character varying,
    city         varchar(50)  default NULL::character varying,
    clazz        integer                not null,
    major        varchar(50)            not null,
    academy      varchar(150)           not null,
    duty         tinyint     default 0 not null,
    department   tinyint     default 3 not null,
    introduction text,
    id_card      varchar(18)  default NULL::character varying,
    status       tinyint     default 0 not null,
    is_deleted   integer      default 0 not null,
    create_time  timestamp              not null,
    update_time  timestamp              not null,
    create_user  integer                not null,
    update_user  integer                not null,
    role         tinyint     default 0 not null,
    gender       tinyint     default 1 not null,
    constraint user_code_unique
        unique (id, code)
)distribute by shard(id) to group default_group;

alter table public."user"
    owner to opentenbase;
