#创建数据库
CREATE DATABASE `jackal-db`;
#创建表
CREATE TABLE `jackal-db`.`tb_account` (
	`account_id` int(11) NOT NULL auto_increment comment '账户ID',
	`account` varchar(32) DEFAULT NULL comment '账户',
	`ctime` datetime DEFAULT NULL comment '创建时间',
	`utime` datetime DEFAULT NULL comment '更新时间',
	PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 comment '账户信息表';