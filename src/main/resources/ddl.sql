#创建数据库
CREATE DATABASE `jackal-db`;
#创建表
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(32) NOT NULL COMMENT '玩家名',
  `user_age` tinyint(11) NOT NULL DEFAULT '1' COMMENT '玩家年龄',
  `user_status` tinyint(11) NOT NULL DEFAULT '1' COMMENT '玩家状态',
  `user_score` bigint(20) NOT NULL DEFAULT '0' COMMENT '玩家积分',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  KEY `idx_time_score` (`user_score`,`utime`,`user_name`,`user_age`)
) ENGINE=InnoDB AUTO_INCREMENT=484652 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';
;