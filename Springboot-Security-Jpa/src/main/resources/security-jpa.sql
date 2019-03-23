/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : springsecurityhibernate

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-08-29 08:22:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `series` varchar(255) NOT NULL,
  `last_used` datetime DEFAULT NULL,
  `token` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`series`),
  UNIQUE KEY `UK_bqa9l0go97v49wwyx4c0u3kpd` (`token`),
  UNIQUE KEY `UK_f8t9fsfwc17s6qcbx0ath6l3h` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for t_resources
-- ----------------------------
DROP TABLE IF EXISTS `t_resources`;
CREATE TABLE `t_resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_time` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `parentid` varchar(255) NOT NULL,
  `res_key` varchar(255) DEFAULT NULL,
  `resmethod` varchar(255) NOT NULL,
  `resurl` varchar(255) NOT NULL,
  `sort` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `up_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_resources
-- ----------------------------
INSERT INTO `t_resources` VALUES ('1', '2018-08-05 17:32:05', '用户管理', '0', 'select', 'GET', '/user/**', '0', '0', '2018-08-07 17:32:45');
INSERT INTO `t_resources` VALUES ('2', '2018-08-05 17:33:19', '用户添加', '1', 'add', 'POST', '/user/**', '0', '1', '2018-08-07 17:33:43');
INSERT INTO `t_resources` VALUES ('3', '2018-08-04 17:33:19', '用户修改', '1', 'update', 'PUT', '/user/**', '0', '1', '2018-08-08 17:31:19');
INSERT INTO `t_resources` VALUES ('4', '2018-08-01 17:33:13', '用户删除', '1', 'delete', 'DELETE', '/user/**', '0', '1', '2018-08-09 17:33:29');
INSERT INTO `t_resources` VALUES ('5', '2018-08-15 10:52:56', '角色管理', '0', 'roleSelect', 'GET', '/role/**', '0', '0', '2018-08-17 10:53:35');
INSERT INTO `t_resources` VALUES ('6', '2018-08-12 10:54:24', '角色添加', '5', 'roleAdd', 'POST', '/role/**', '0', '1', '2018-08-14 10:53:42');
INSERT INTO `t_resources` VALUES ('7', '2018-08-06 10:54:45', '角色修改', '5', 'roleUpdate', 'PUT', '/role/**', '0', '1', '2018-08-15 10:55:18');
INSERT INTO `t_resources` VALUES ('8', '2018-08-01 10:55:27', '角色删除', '5', 'roleDelete', 'DELETE', '/role/**', '0', '1', '2018-08-15 10:55:54');
INSERT INTO `t_resources` VALUES ('9', '2018-08-16 21:43:13', '权限管理', '0', 'resourcesSelect', 'GET', '/resources/**', '0', '0', '2018-08-17 21:43:46');
INSERT INTO `t_resources` VALUES ('10', '2018-08-16 21:43:56', '权限添加', '9', 'resourcesAdd', 'POST', '/resources/**', '0', '1', '2018-08-17 21:45:04');
INSERT INTO `t_resources` VALUES ('11', '2018-08-16 21:45:09', '权限修改', '9', 'resourcesUpdate', 'PUT', '/resources/**', '0', '1', '2018-08-17 21:45:41');
INSERT INTO `t_resources` VALUES ('12', '2018-08-16 21:45:47', '权限删除', '9', 'resourcesDelete', 'DELETE', '/resources/**', '0', '1', '2018-08-17 21:46:12');
INSERT INTO `t_resources` VALUES ('13', '2018-08-20 10:56:28', '所有用户', '1', 'user', 'ALL', '/user/**', '0', '1', '2018-08-22 10:57:26');
INSERT INTO `t_resources` VALUES ('14', '2018-08-20 10:57:39', '所有角色', '5', 'role', 'ALL', '/role/**', '0', '1', '2018-08-22 10:58:09');
INSERT INTO `t_resources` VALUES ('15', '2018-08-20 10:58:32', '所有权限', '9', 'resources', 'ALL', '/resources/**', '0', '1', '2018-08-21 10:59:04');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_time` datetime DEFAULT NULL,
  `roledesc` varchar(255) NOT NULL,
  `rolekey` varchar(255) NOT NULL,
  `up_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '2018-08-06 17:33:53', '管理员', 'ADMIN', '2018-08-28 09:03:54');
INSERT INTO `t_role` VALUES ('2', '2018-08-09 17:00:37', '普通用户', 'USER', '2018-08-28 10:36:45');
INSERT INTO `t_role` VALUES ('5', '2018-08-20 22:12:03', '测试角色', 'TT', '2018-08-27 17:45:14');

-- ----------------------------
-- Table structure for t_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resources`;
CREATE TABLE `t_role_resources` (
  `role_id` int(11) NOT NULL,
  `resources_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`resources_id`),
  KEY `FK2th2ascr0p6x5p7bcngsqthv5` (`resources_id`),
  CONSTRAINT `FK2th2ascr0p6x5p7bcngsqthv5` FOREIGN KEY (`resources_id`) REFERENCES `t_resources` (`id`),
  CONSTRAINT `FKqduu7eif4j0x214kmdhobgvm2` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_resources
-- ----------------------------
INSERT INTO `t_role_resources` VALUES ('1', '1');
INSERT INTO `t_role_resources` VALUES ('2', '1');
INSERT INTO `t_role_resources` VALUES ('5', '1');
INSERT INTO `t_role_resources` VALUES ('1', '2');
INSERT INTO `t_role_resources` VALUES ('2', '2');
INSERT INTO `t_role_resources` VALUES ('1', '3');
INSERT INTO `t_role_resources` VALUES ('1', '4');
INSERT INTO `t_role_resources` VALUES ('1', '5');
INSERT INTO `t_role_resources` VALUES ('2', '5');
INSERT INTO `t_role_resources` VALUES ('5', '5');
INSERT INTO `t_role_resources` VALUES ('1', '6');
INSERT INTO `t_role_resources` VALUES ('1', '7');
INSERT INTO `t_role_resources` VALUES ('1', '8');
INSERT INTO `t_role_resources` VALUES ('1', '9');
INSERT INTO `t_role_resources` VALUES ('2', '9');
INSERT INTO `t_role_resources` VALUES ('5', '9');
INSERT INTO `t_role_resources` VALUES ('1', '10');
INSERT INTO `t_role_resources` VALUES ('1', '11');
INSERT INTO `t_role_resources` VALUES ('1', '12');
INSERT INTO `t_role_resources` VALUES ('1', '13');
INSERT INTO `t_role_resources` VALUES ('1', '14');
INSERT INTO `t_role_resources` VALUES ('1', '15');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_time` datetime DEFAULT NULL,
  `enable` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `up_time` datetime DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jhib4legehrm4yscx9t3lirqi` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '2018-08-05 17:34:19', '1', '$2a$10$VTzXQxFkYrkLUDdbXW5sAOWUOHs/v1LVPdRL6c80AFg/76qn313sO', '2018-08-07 17:34:38', 'lin');
INSERT INTO `t_user` VALUES ('2', '2018-08-14 16:38:34', '1', '$2a$10$eZz93HLCtCN3myspIiKZ/eV0qRjlZ/GrnBK1BsYRwMwLc6DmsM6NK', '2018-08-23 19:53:04', 'admin');
INSERT INTO `t_user` VALUES ('3', '2018-08-20 21:49:56', '0', '$2a$10$9yithUFJ1CDuusYTcWBn6undeJnnSkVFPFMScSuKxmOCMH9tEdek.', '2018-08-21 10:01:30', 'tt');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa9c8iiy6ut0gnx491fqx4pxam` (`role_id`),
  CONSTRAINT `FKa9c8iiy6ut0gnx491fqx4pxam` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FKq5un6x7ecoef5w1n39cop66kl` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1');
INSERT INTO `t_user_role` VALUES ('2', '5');
