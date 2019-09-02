DROP TABLE usr_user;
create table usr_user(
 id varchar(32) not null comment '主键',
 name varchar(16) default null comment '姓名',
 user_name varchar(32) default null comment '用户名',
 password varchar(64) default null comment '密码',
 mobile varchar(12) default null comment '手机号',
 status varchar(8) not null comment '状态,USE:可用,UNUSE: 不可用,LOCKED: 锁定 ',
 created_at timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
 updated_at timestamp null  default current_timestamp on update CURRENT_TIMESTAMP comment '更新时间',
 primary key(id)
 )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


 insert into usr_user (id,name,user_name,password,mobile,status)values('1','系统管理员','admin','admin','18600544643','USE');
 