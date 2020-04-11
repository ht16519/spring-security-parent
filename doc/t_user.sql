/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : db_security

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2020-04-09 16:01:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `email` varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱',
  `last_login` datetime DEFAULT NULL COMMENT '用户最后一次登录时间',
  `mobile` varchar(16) DEFAULT '' COMMENT '手机号码',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `status` int(2) NOT NULL DEFAULT '10' COMMENT '状态（-1：冻结，10：正常）',
  `username` varchar(16) NOT NULL,
  `nickname` varchar(16) NOT NULL DEFAULT '',
  `avatar` varchar(255) NOT NULL COMMENT '用户头像',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `sex` int(1) NOT NULL DEFAULT '1' COMMENT '性别（1，0：男：女）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `que_nick_name` (`nickname`),
  UNIQUE KEY `que_email` (`email`),
  UNIQUE KEY `que_user_name` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('12', '2323810996@qq.com', null, '', '$2a$10$SxJvCfpnRob.PUfAVJmLUObsVGdfxMGaYh1dvMggsOOyLcSYxpgae', '10', 'admin', '小甜心', '/static/assets/images/ava/default.png', '2019-05-29 14:38:01', null, '1');
INSERT INTO `t_user` VALUES ('13', '2323810991@qq.com', null, '', '$2a$10$WX9UpTZYIHr.6u1cZhJ9iOy5N5yNioqe0SwPsnxYPfGXfgYBZZDjm', '10', 'ht19523', '百特', '/static/assets/images/ava/default.png', '2019-06-03 12:24:44', null, '1');
INSERT INTO `t_user` VALUES ('14', 'demoData', '2020-03-31 08:00:00', 'demoData', '$2a$10$3fMoeZGx.LB9L4YqHyll..x5Gtp5imUs/Wic30iEm7MeCNWKBWbaW', '1', 'demoData', 'demoData', 'demoData', '2020-03-31 08:00:00', '2020-03-31 08:00:00', '1');
