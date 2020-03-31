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
  ip varchar(16) default null comment '服务IP',
  tenant_type varchar(8) default null comment '服务分组',
  balance int(8) default 0 comment '账户余额',
  overdraft int(8) default 0 comment '透支金额',
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
  effect_at varchar(18) default null comment '生效日期',
  expire_at varchar(18) default null comment '过期时间',
  vendor varchar(2) default null comment '接口厂商，1：百度 2：科大讯飞 3：阿里云',
  status varchar(8) not null comment '状态，1：可用 0：不可用',
  app_id varchar(64) default null comment 'APPID',
  app_key varchar(128) default null comment '密钥',
  dissipation int(20) default null comment '最低消费',
  costDate varchar(20) default null comment '低消的下个扣费时间',
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
  sip_ip varchar(32) default null comment 'sip服务器IP',
  sip_port varchar(8) default null comment 'sip服务器端口号',
  to_ip varchar(32) default null comment '对接服务器IP',
  to_port varchar(32) default null comment '对接服务器端口',
  username varchar(16) default null comment '管理员登录名',
  password varchar(64) default null comment '管理员登录密码',
  secret_key varchar(64) default null comment '密钥',
  created_at timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
  updated_at timestamp not null default current_timestamp on update CURRENT_TIMESTAMP comment '更新时间',
  primary key(id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


drop table acc_tenant_trade;
create table acc_tenant_trade(
  id varchar(32) not null comment '主键',
  tenant_id varchar(32) not null comment '租户ID',
  trade_type varchar(4) not null comment '交易类型：1：支付 2：消费 3：冲账',
  amount int(10)  not null comment '交易金额',
  pay_type varchar(8) default null comment '支付方式',
  note varchar(255) default null comment '备注',
  creator_id varchar(32) default null comment '创建人',
  created_at timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
  primary key(id),
  key idx_teant_id_tenant_trade (tenant_id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


drop table acc_tenant_bill;
create table acc_tenant_bill(
  id varchar(32) not null comment '主键',
  tenant_id varchar(32) not null comment '租户ID',
  product_code varchar(8) not null comment '产品编号',
  bill_type varchar(4) not null comment '账单类型,1: 包月费用 2:调用次数' ,
  fee int(8) default 0 comment '单价',
  duration int(8) default 0 comment '订购时长',
  amount int(8) default 0 comment '总金额',
  trade_id varchar(32) default null comment '交易ID',
  note varchar(128) default null comment '交易备注',
  creator_id varchar(32) default null comment '创建人',
  created_at timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
  primary key(id),
  key idx_teant_id_tenant_bill (tenant_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


drop table usr_resource;
create table usr_resource(
  id varchar(32) not null comment '主键',
  name varchar(32) not null comment '资源名称',
  code varchar(32) not null comment '资源编码',
  type varchar(8) not null comment '资源类型，MENU:菜单 ACTION:按钮',
  url varchar(128) default null comment '资源地址' ,
  icon varchar(32) default null comment '资源图标',
  visible int(1) default 1 comment '是否可见',
  parent_id varchar(32) default null comment '父亲节点',
  seq int(2) default 0 comment '排序',
  level int(2) default null comment '级别',
  created_at timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
  updated_at timestamp not null default current_timestamp on update CURRENT_TIMESTAMP comment '更新时间',
  primary key(id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


drop table usr_role_resource;
create table usr_role_resource(
  role_id varchar(32) not null comment '角色ID',
  resource_id varchar(32) not null comment '资源ID',
  primary key(role_id,resource_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


drop table usr_user_role;
create table usr_user_role(
  user_id varchar(32) not null comment '用户ID',
  role_id varchar(32) not null comment '角色ID',
  primary key(user_id,role_id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;


drop table usr_role;
create table usr_role(
  id varchar(32) not null comment '主键',
  name varchar(32) not null comment '角色名称',
  created_at timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
  updated_at timestamp not null default current_timestamp on update CURRENT_TIMESTAMP comment '更新时间',
  primary key(id)
)ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;








