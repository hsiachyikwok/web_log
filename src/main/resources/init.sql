-- we don't know how to generate schema web_log (class Schema) :(
create table log
(
  id int unsigned auto_increment
    primary key,
  dir_name varchar(255) null,
  file_name varchar(255) null,
  create_time datetime null,
  update_time timestamp null on update CURRENT_TIMESTAMP,
  is_del int null
)
;

create table user
(
  id int unsigned auto_increment
    primary key,
  user_name varchar(255) null comment '用户名',
  password varchar(255) null comment '密码',
  is_admin int null comment '是否管理员 1是 0不是',
  current_opt_log varchar(255) null comment '当前正要打开的日志文件',
  create_time datetime null,
  update_time timestamp null on update CURRENT_TIMESTAMP,
  is_del int null
)
;

create table user_log_ref
(
  id int not null
    primary key,
  log_id int null,
  user_id int null,
  create_time datetime null,
  update_time timestamp null on update CURRENT_TIMESTAMP,
  is_del int null
)
;

