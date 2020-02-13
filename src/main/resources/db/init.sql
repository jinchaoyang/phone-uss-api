DROP TABLE usr_user;
create table usr_user(
 id varchar(32) not null comment '主键',
 name varchar(16) default null comment '姓名',
 user_name varchar(32) default null comment '用户名',
 password varchar(64) default null comment '密码',
 mobile varchar(12) default null comment '手机号',
 status varchar(8) not null comment '状态,USE:可用,UNUSE: 不可用,LOCKED: 锁定 ',
 role varchar(8) not null comment '角色，1：系统管理员',
 created_at timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
 updated_at timestamp not null default current_timestamp on update CURRENT_TIMESTAMP comment '更新时间',
 primary key(id)
 )ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


 insert into usr_user (id,name,user_name,password,mobile,status,role)values('1','系统管理员','admin','admin','18600544643','USE',1);


drop table acc_tenant;
create table acc_tenant(
  id varchar(32) not null comment '主键',
  tenant_code varchar(32) not null comment '租户编码',
  name varchar(64) not null comment '租户名称',
  contact_name varchar(32) default null comment '联系人姓名',
  contact_phone varchar(16) default null comment '联系人手机',
  email varchar(64) default null comment '联系人邮箱',
  address varchar(255) default null comment '联系人地址',
  remark varchar(128) default null comment '备注',
  status varchar(8) not null comment '状态, 0: 已删除 1：可用 2：禁用 3：欠费 ',
  creator_id varchar(32) default null comment '创建人',
  operator_id varchar(32) default null comment '最近操作人',
  created_at timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
  updated_at timestamp not null default current_timestamp on update CURRENT_TIMESTAMP comment '更新时间',
  primary key(id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


drop table acc_tenant_product;
create table acc_tenant_product(
  id varchar(32) not null comment '主键',
  tenant_id varchar(32) not null comment '租户ID',
  product_type varchar(16) not null comment '产品类型',
  fee_type varchar(16) not null comment '计费类型',
  fee int(8) not null comment '费率，单位分',
  expire_at varchar(18) default null comment '过期时间',
  vendor varchar(2) default null comment '接口厂商，1：百度 2：科大讯飞 3：阿里云',
  status varchar(8) not null comment '状态，1：可用 0：不可用',
  app_id varchar(64) default null comment 'APPID',
  app_key varchar(128) default null comment '密钥',
  creator_id varchar(32) default null comment '创建人',
  operator_id varchar(32) default null comment '最近操作人',
  created_at timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
  updated_at timestamp not null default current_timestamp on update CURRENT_TIMESTAMP comment '更新时间',
  primary key(id),
  key idx_teant_id_tenant_product (tenant_id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


drop table acc_tenant_setting;
create table acc_tenant_setting(
  id varchar(32) not null comment '主键,同租户ID',
  ip varchar(64) not null comment 'IP地址',
  primary key(id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;








