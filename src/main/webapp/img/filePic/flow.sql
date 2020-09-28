/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : budget

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2020-09-04 11:21:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for flow
-- ----------------------------
DROP TABLE IF EXISTS `flow`;
CREATE TABLE `flow` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(50) DEFAULT NULL,
  `NODE` int(11) DEFAULT NULL,
  `ROLE` varchar(100) DEFAULT NULL,
  `ROLEACCOUNT` varchar(100) DEFAULT NULL,
  `NEXTNODE` int(11) DEFAULT NULL,
  `DEPTCODE` varchar(10) DEFAULT NULL,
  `DEPTNAME` varchar(100) DEFAULT NULL,
  `DESCRIBE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of flow
-- ----------------------------
INSERT INTO `flow` VALUES ('15', 'budget', '1', 'chairman', null, '-1', 'D0001', '董办室', '董办室预算审批流程节点1');
INSERT INTO `flow` VALUES ('16', 'reimburse', '1', 'chairman', null, '-1', 'D0001', '董办室', '董办室报销审批流程节点1');
INSERT INTO `flow` VALUES ('17', 'apply', '1', 'chairman', null, '-1', 'D0001', '董办室', '董办室事前费用支出申请审批流程节点1');
INSERT INTO `flow` VALUES ('18', 'budget', '1', 'manager3', null, '2', 'D0002', '品质部', '品质部预算审批流程节点1');
INSERT INTO `flow` VALUES ('19', 'budget', '2', 'chairman', null, '-1', 'D0002', '品质部', '品质部预算审批流程节点2');
INSERT INTO `flow` VALUES ('20', 'reimburse', '1', 'manager3', null, '2', 'D0002', '品质部', '品质部报销审批流程节点1');
INSERT INTO `flow` VALUES ('21', 'reimburse', '2', 'chairman', null, '-1', 'D0002', '品质部', '品质部报销审批流程节点2');
INSERT INTO `flow` VALUES ('22', 'apply', '1', 'manager3', null, '2', 'D0002', '品质部', '品质部事前费用支出申请审批流程节点1');
INSERT INTO `flow` VALUES ('23', 'apply', '2', 'chairman', null, '-1', 'D0002', '品质部', '品质部事前费用支出申请审批流程节点2');
INSERT INTO `flow` VALUES ('24', 'budget', '1', 'manager2', null, '2', 'D0003', '工程技术', '工程技术预算审批流程节点1');
INSERT INTO `flow` VALUES ('25', 'budget', '2', 'manager1', null, '3', 'D0003', '工程技术', '工程技术预算审批流程节点2');
INSERT INTO `flow` VALUES ('26', 'budget', '3', 'chairman', null, '-1', 'D0003', '工程技术', '工程技术预算审批流程节点3');
INSERT INTO `flow` VALUES ('27', 'reimburse', '1', 'manager2', null, '2', 'D0003', '工程技术', '工程技术报销审批流程节点1');
INSERT INTO `flow` VALUES ('28', 'reimburse', '2', 'manager1', null, '3', 'D0003', '工程技术', '工程技术报销审批流程节点2');
INSERT INTO `flow` VALUES ('29', 'reimburse', '3', 'chairman', null, '-1', 'D0003', '工程技术', '工程技术报销审批流程节点3');
INSERT INTO `flow` VALUES ('30', 'apply', '1', 'manager2', null, '2', 'D0003', '工程技术', '工程技术事前费用支出申请审批流程节点1');
INSERT INTO `flow` VALUES ('31', 'apply', '2', 'manager1', null, '3', 'D0003', '工程技术', '工程技术事前费用支出申请审批流程节点2');
INSERT INTO `flow` VALUES ('32', 'apply', '3', 'chairman', null, '-1', 'D0003', '工程技术', '工程技术事前费用支出申请审批流程节点3');
INSERT INTO `flow` VALUES ('33', 'budget', '1', 'manager1', null, '2', 'D0004', '厂务部', '厂务部预算审批流程节点1');
INSERT INTO `flow` VALUES ('34', 'budget', '2', 'chairman', null, '-1', 'D0004', '厂务部', '厂务部预算审批流程节点2');
INSERT INTO `flow` VALUES ('35', 'reimburse', '1', 'manager1', null, '2', 'D0004', '厂务部', '厂务部报销审批流程节点1');
INSERT INTO `flow` VALUES ('36', 'reimburse', '2', 'chairman', null, '-1', 'D0004', '厂务部', '厂务部报销审批流程节点2');
INSERT INTO `flow` VALUES ('37', 'apply', '1', 'manager1', null, '2', 'D0004', '厂务部', '厂务部事前费用支出申请审批流程节点1');
INSERT INTO `flow` VALUES ('38', 'apply', '2', 'chairman', null, '-1', 'D0004', '厂务部', '厂务部事前费用支出申请审批流程节点2');
INSERT INTO `flow` VALUES ('39', 'budget', '1', 'manager1', null, '2', 'D0005', '人力资源部', '人力资源部预算审批流程节点1');
INSERT INTO `flow` VALUES ('40', 'budget', '2', 'chairman', null, '-1', 'D0005', '人力资源部', '人力资源部预算审批流程节点2');
INSERT INTO `flow` VALUES ('41', 'reimburse', '1', 'manager1', null, '2', 'D0005', '人力资源部', '人力资源部报销审批流程节点1');
INSERT INTO `flow` VALUES ('42', 'reimburse', '2', 'chairman', null, '-1', 'D0005', '人力资源部', '人力资源部报销审批流程节点2');
INSERT INTO `flow` VALUES ('43', 'apply', '1', 'manager1', null, '2', 'D0005', '人力资源部', '人力资源部事前费用支出申请审批流程节点1');
INSERT INTO `flow` VALUES ('44', 'apply', '2', 'chairman', null, '-1', 'D0005', '人力资源部', '人力资源部事前费用支出申请审批流程节点2');
INSERT INTO `flow` VALUES ('45', 'budget', '1', 'manager1', null, '2', 'D0006', '仓储部', '仓储部预算审批流程节点1');
INSERT INTO `flow` VALUES ('46', 'budget', '2', 'chairman', null, '-1', 'D0006', '仓储部', '仓储部预算审批流程节点2');
INSERT INTO `flow` VALUES ('47', 'reimburse', '1', 'manager1', null, '2', 'D0006', '仓储部', '仓储部报销审批流程节点1');
INSERT INTO `flow` VALUES ('48', 'reimburse', '2', 'chairman', null, '-1', 'D0006', '仓储部', '仓储部报销审批流程节点2');
INSERT INTO `flow` VALUES ('49', 'apply', '1', 'manager1', null, '2', 'D0006', '仓储部', '仓储部事前费用支出申请审批流程节点1');
INSERT INTO `flow` VALUES ('50', 'apply', '2', 'chairman', null, '-1', 'D0006', '仓储部', '仓储部事前费用支出申请审批流程节点2');
INSERT INTO `flow` VALUES ('51', 'budget', '1', 'manager1', null, '2', 'D0007', '采购', '采购预算审批流程节点1');
INSERT INTO `flow` VALUES ('52', 'budget', '2', 'chairman', null, '-1', 'D0007', '采购', '采购预算审批流程节点2');
INSERT INTO `flow` VALUES ('53', 'reimburse', '1', 'manager1', null, '2', 'D0007', '采购', '采购报销审批流程节点1');
INSERT INTO `flow` VALUES ('54', 'reimburse', '2', 'chairman', null, '-1', 'D0007', '采购', '采购报销流程审批节点2');
INSERT INTO `flow` VALUES ('55', 'apply', '1', 'manager1', null, '2', 'D0007', '采购', '采购事前费用支出申请审批流程节点1');
INSERT INTO `flow` VALUES ('56', 'apply', '2', 'chairman', null, '-1', 'D0007', '采购', '采购事前费用支出申请审批流程节点2');
INSERT INTO `flow` VALUES ('57', 'budget', '1', 'manager2', null, '2', 'D0008', '经营层办公室', '经营层办公室预算审批流程节点1');
INSERT INTO `flow` VALUES ('58', 'budget', '2', 'manager1', null, '3', 'D0008', '经营层办公室', '经营层办公室预算审批流程节点2');
INSERT INTO `flow` VALUES ('59', 'budget', '3', 'chairman', null, '-1', 'D0008', '经营层办公室', '经营层办公室预算审批流程节点3');
INSERT INTO `flow` VALUES ('60', 'reimburse', '1', 'manager2', null, '2', 'D0008', '经营层办公室', '经营层办公室报销审批流程节点1');
INSERT INTO `flow` VALUES ('61', 'reimburse', '2', 'manager1', null, '3', 'D0008', '经营层办公室', '经营层办公室报销审批流程节点2');
INSERT INTO `flow` VALUES ('62', 'reimburse', '3', 'chairman', null, '-1', 'D0008', '经营层办公室', '经营层办公室报销审批流程节点3');
INSERT INTO `flow` VALUES ('63', 'apply', '1', 'manager2', null, '2', 'D0008', '经营层办公室', '经营层办公室事前费用支出申请审批流程节点1');
INSERT INTO `flow` VALUES ('64', 'apply', '2', 'manager1', null, '3', 'D0008', '经营层办公室', '经营层办公室事前费用支出申请审批流程节点2');
INSERT INTO `flow` VALUES ('65', 'apply', '3', 'chairman', null, '-1', 'D0008', '经营层办公室', '经营层办公室事前费用支出申请审批流程节点3');
INSERT INTO `flow` VALUES ('66', 'budget', '1', 'chairman', null, '-1', 'D0009', '事业一部市场运营', '事业一部市场运营预算审批流程节点1');
INSERT INTO `flow` VALUES ('67', 'reimburse', '1', 'chairman', null, '-1', 'D0009', '事业一部市场运营', '事业一部市场运营报销审批流程节点1');
INSERT INTO `flow` VALUES ('68', 'apply', '1', 'chairman', null, '-1', 'D0009', '事业一部市场运营', '事业一部市场运营事前费用支出申请审批流程节点1');
INSERT INTO `flow` VALUES ('69', 'budget', '1', 'manager1', null, '2', 'D0010', '事业一部喷涂线', '事业一部喷涂线预算审批流程节点1');
INSERT INTO `flow` VALUES ('70', 'budget', '2', 'chairman', null, '-1', 'D0010', '事业一部喷涂线', '事业一部喷涂线预算审批流程节点2');
INSERT INTO `flow` VALUES ('71', 'reimburse', '1', 'manager1', null, '2', 'D0010', '事业一部喷涂线', '事业一部喷涂线报销审批流程节点1');
INSERT INTO `flow` VALUES ('72', 'reimburse', '2', 'chairman', null, '-1', 'D0010', '事业一部喷涂线', '事业一部喷涂线报销审批流程节点2');
INSERT INTO `flow` VALUES ('73', 'apply', '1', 'manager1', null, '2', 'D0010', '事业一部喷涂线', '事业一部喷涂线事前费用支出申请审批流程节点1');
INSERT INTO `flow` VALUES ('74', 'apply', '2', 'chairman', null, '-1', 'D0010', '事业一部喷涂线', '事业一部喷涂线事前费用支出申请审批流程节点2');
INSERT INTO `flow` VALUES ('75', 'budget', '1', 'manager2', null, '2', 'D0011', '事业一部铝件自动线', '事业一部铝件自动线预算审批流程节点1');
INSERT INTO `flow` VALUES ('76', 'budget', '2', 'manager1', null, '3', 'D0011', '事业一部铝件自动线', '事业一部铝件自动线预算审批流程节点2');
INSERT INTO `flow` VALUES ('77', 'budget', '3', 'chairman', null, '-1', 'D0011', '事业一部铝件自动线', '事业一部铝件自动线预算审批流程节点3');
INSERT INTO `flow` VALUES ('78', 'reimburse', '1', 'manager2', null, '2', 'D0011', '事业一部铝件自动线', '事业一部铝件自动线报销审批流程节点1');
INSERT INTO `flow` VALUES ('79', 'reimburse', '2', 'manager1', null, '3', 'D0011', '事业一部铝件自动线', '事业一部铝件自动线报销审批流程节点2');
INSERT INTO `flow` VALUES ('80', 'reimburse', '3', 'chairman', null, '-1', 'D0011', '事业一部铝件自动线', '事业一部铝件自动线报销审批流程节点3');
INSERT INTO `flow` VALUES ('81', 'apply', '1', 'manager2', null, '2', 'D0011', '事业一部铝件自动线', '事业一部铝件自动线事前费用支出申请审批节点1');
INSERT INTO `flow` VALUES ('82', 'apply', '2', 'manager1', null, '3', 'D0011', '事业一部铝件自动线', '事业一部铝件自动线事前费用支出申请审批节点2');
INSERT INTO `flow` VALUES ('83', 'apply', '3', 'chairman', null, '-1', 'D0011', '事业一部铝件自动线', '事业一部铝件自动线事前费用支出申请审批节点3');
INSERT INTO `flow` VALUES ('84', 'budget', '1', 'chairman', null, '-1', 'D0012', '事业二部市场运营', '事业二部市场运营预算审批节点1');
INSERT INTO `flow` VALUES ('85', 'reimburse', '1', 'chairman', null, '-1', 'D0012', '事业二部市场运营', '事业二部市场运营报销审批节点1');
INSERT INTO `flow` VALUES ('86', 'apply', '1', 'chairman', null, '-1', 'D0012', '事业二部市场运营', '事业二部市场运营事前费用支出申请审批节点1');
INSERT INTO `flow` VALUES ('87', 'budget', '1', 'manager2', null, '2', 'D0013', '事业二部滚挂镀线', '事业二部滚挂镀线预算审批节点1');
INSERT INTO `flow` VALUES ('88', 'budget', '2', 'chairman', null, '-1', 'D0013', '事业二部滚挂镀线', '事业二部滚挂镀线预算审批节点2');
INSERT INTO `flow` VALUES ('89', 'reimburse', '1', 'manager2', null, '2', 'D0013', '事业二部滚挂镀线', '事业二部滚挂镀线报销审批节点1');
INSERT INTO `flow` VALUES ('90', 'reimburse', '2', 'chairman', null, '-1', 'D0013', '事业二部滚挂镀线', '事业二部滚挂镀线报销审批节点2');
INSERT INTO `flow` VALUES ('91', 'apply', '1', 'manager2', null, '2', 'D0013', '事业二部滚挂镀线', '事业二部滚挂镀线事前费用支出申请审批节点1');
INSERT INTO `flow` VALUES ('92', 'apply', '2', 'chairman', null, '-1', 'D0013', '事业二部滚挂镀线', '事业二部滚挂镀线事前费用支出申请审批节点2');
INSERT INTO `flow` VALUES ('93', 'budget', '1', 'manager2', null, '2', 'D0014', '事业二部连续线', '事业二部连续线预算审批节点1');
INSERT INTO `flow` VALUES ('94', 'budget', '2', 'chairman', null, '-1', 'D0014', '事业二部连续线', '事业二部连续线预算审批节点2');
INSERT INTO `flow` VALUES ('95', 'reimburse', '1', 'manager2', null, '2', 'D0014', '事业二部连续线', '事业二部连续线报销审批节点1');
INSERT INTO `flow` VALUES ('96', 'reimburse', '2', 'chairman', null, '-1', 'D0014', '事业二部连续线', '事业二部连续线报销审批节点2');
INSERT INTO `flow` VALUES ('97', 'apply', '1', 'manager2', null, '2', 'D0014', '事业二部连续线', '事业二部连续线事前费用支出申请审批节点1');
INSERT INTO `flow` VALUES ('98', 'apply', '2', 'chairman', null, '-1', 'D0014', '事业二部连续线', '事业二部连续线事前费用支出申请审批节点2');
INSERT INTO `flow` VALUES ('99', 'budget', '1', 'manager2', null, '2', 'D0015', '计划部', '计划部预算审批节点1');
INSERT INTO `flow` VALUES ('100', 'budget', '2', 'chairman', null, '-1', 'D0015', '计划部', '计划部预算审批节点2');
INSERT INTO `flow` VALUES ('101', 'reimburse', '1', 'manager2', null, '2', 'D0015', '计划部', '计划部报销审批节点1');
INSERT INTO `flow` VALUES ('102', 'reimburse', '2', 'chairman', null, '-1', 'D0015', '计划部', '计划部报销审批节点2');
INSERT INTO `flow` VALUES ('103', 'apply', '1', 'manager2', null, '2', 'D0015', '计划部', '计划部事前费用支出申请审批节点1');
INSERT INTO `flow` VALUES ('104', 'apply', '2', 'chairman', null, '-1', 'D0015', '计划部', '计划部事前费用支出申请审批节点2');
