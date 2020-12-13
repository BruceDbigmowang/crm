/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : budget

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2020-09-01 13:07:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for advanceexpense
-- ----------------------------
DROP TABLE IF EXISTS `advanceexpense`;
CREATE TABLE `advanceexpense` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `REQNUM` varchar(30) DEFAULT NULL,
  `DEPTCODE` varchar(10) DEFAULT NULL,
  `DEPTNAME` varchar(50) DEFAULT NULL,
  `MAKERACCOUNT` varchar(10) DEFAULT NULL,
  `MAKERNAME` varchar(50) DEFAULT NULL,
  `EXPENSETYPE` varchar(10) DEFAULT NULL,
  `EXPENSENAME` varchar(100) DEFAULT NULL,
  `EXPENSEAMOUNT` decimal(10,0) DEFAULT NULL,
  `EXPENSEDESC` varchar(255) DEFAULT NULL,
  `USEDATE` date DEFAULT NULL,
  `MAKEDATE` date DEFAULT NULL,
  `USEMONTH` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of advanceexpense
-- ----------------------------
INSERT INTO `advanceexpense` VALUES ('7', 'KPL1C202008270006', 'D0001', '董办室', 'SK0083', '张明', 'BG0003', '人事方面', '5000', '无', '2020-09-03', '2020-08-27', '2020-09');
INSERT INTO `advanceexpense` VALUES ('8', 'KPL1C202008270007', 'D0001', '董办室', 'SK0083', '张明', 'BG0003', '人事方面', '6000', '无', '2020-09-15', '2020-08-27', '2020-09');

-- ----------------------------
-- Table structure for approveperson
-- ----------------------------
DROP TABLE IF EXISTS `approveperson`;
CREATE TABLE `approveperson` (
  `ACCOUNT` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DEPT` varchar(30) DEFAULT NULL,
  `ROLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ACCOUNT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of approveperson
-- ----------------------------
INSERT INTO `approveperson` VALUES ('sk0044', '陈文钱', 'D0002', 'manager3');
INSERT INTO `approveperson` VALUES ('SK0080', '万建斌', 'D0001', 'chairman');
INSERT INTO `approveperson` VALUES ('SK0081', '孙飞', 'D0001', 'manager1');
INSERT INTO `approveperson` VALUES ('SK0082', '韩高鹏', 'D0001', 'manager2');

-- ----------------------------
-- Table structure for budgetleaf
-- ----------------------------
DROP TABLE IF EXISTS `budgetleaf`;
CREATE TABLE `budgetleaf` (
  `ID` varchar(255) NOT NULL,
  `DEPTNAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `DEPTCODE` varchar(10) DEFAULT NULL,
  `MAKERACCOUNT` varchar(10) DEFAULT NULL,
  `MAKERNAME` varchar(30) DEFAULT NULL,
  `MAKEDATE` date DEFAULT NULL,
  `PHONECOST` decimal(10,0) DEFAULT '0',
  `PHONEDESC` varchar(1000) DEFAULT NULL,
  `REPAIRCOST` decimal(10,0) DEFAULT '0',
  `REPAIRDESC` varchar(1000) DEFAULT NULL,
  `HRCOST` decimal(10,0) DEFAULT '0',
  `HRDESC` varchar(1000) DEFAULT NULL,
  `CANTEENCOST` decimal(10,0) DEFAULT '0',
  `CANTEENDESC` varchar(1000) DEFAULT NULL,
  `FINANCECOST` decimal(10,0) DEFAULT '0',
  `FINANCEDESC` varchar(1000) DEFAULT NULL,
  `INTERESTCOST` decimal(10,0) DEFAULT '0',
  `INTERESTDESC` varchar(1000) DEFAULT NULL,
  `COMPENSATECOST` decimal(10,0) DEFAULT '0',
  `COMPENSATEDESC` varchar(1000) DEFAULT NULL,
  `WELFARECOST` decimal(10,0) DEFAULT '0',
  `WELFAREDESC` varchar(1000) DEFAULT NULL,
  `TRAINCOST` decimal(10,0) DEFAULT '0',
  `TRAINDESC` varchar(1000) DEFAULT NULL,
  `TRAVELCOST` decimal(10,0) DEFAULT '0',
  `TRAVELDESC` varchar(1000) DEFAULT NULL,
  `SERVECOST` decimal(10,0) DEFAULT '0',
  `SERVEDESC` varchar(1000) DEFAULT NULL,
  `VEHICLECOST` decimal(10,0) DEFAULT '0',
  `VEHICLEDESC` varchar(1000) DEFAULT NULL,
  `OFFICECOST` decimal(10,0) DEFAULT '0',
  `OFFICEDESC` varchar(1000) DEFAULT NULL,
  `LOGISTICSCOST` decimal(10,0) DEFAULT '0',
  `LOGISTICSDESC` varchar(1000) DEFAULT NULL,
  `POSTCOST` decimal(10,0) DEFAULT '0',
  `POSTDESC` varchar(1000) DEFAULT NULL,
  `OTHERCOST` decimal(10,0) DEFAULT '0',
  `OTHERDESC` varchar(1000) DEFAULT NULL,
  `APPROVESTATUS` char(1) DEFAULT NULL,
  `RESULT` varchar(100) DEFAULT NULL,
  `USEMONTH` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of budgetleaf
-- ----------------------------

-- ----------------------------
-- Table structure for budgetlist
-- ----------------------------
DROP TABLE IF EXISTS `budgetlist`;
CREATE TABLE `budgetlist` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `REQNUM` varchar(100) NOT NULL,
  `DEPTNAME` varchar(100) DEFAULT NULL,
  `DEPTCODE` varchar(10) DEFAULT NULL,
  `MAKERACCOUNT` varchar(10) DEFAULT NULL,
  `MAKERNAME` varchar(30) DEFAULT NULL,
  `EXPENSETYPE` varchar(10) DEFAULT NULL,
  `EXPENSENAME` varchar(50) DEFAULT NULL,
  `EXPENSEAMOUNT` decimal(10,0) DEFAULT NULL,
  `EXPENSEDESC` varchar(500) DEFAULT NULL,
  `USEMONTH` varchar(255) DEFAULT NULL,
  `MAKEDATE` date DEFAULT NULL,
  `STATUS` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of budgetlist
-- ----------------------------
INSERT INTO `budgetlist` VALUES ('22', 'KPL1A202008270001', '董办室', 'D0001', 'SK0083', '张明', 'BG0003', '人事方面', '30000', '无', '2020-09', '2020-08-27', 'N');
INSERT INTO `budgetlist` VALUES ('23', 'KPL1A202008270001', '董办室', 'D0001', 'SK0083', '张明', 'BG0005', '财务方面', '30000', '无', '2020-09', '2020-08-27', 'N');
INSERT INTO `budgetlist` VALUES ('24', 'KPL1A202008270002', '董办室', 'D0001', 'SK0083', '张明', 'BG0003', '人事方面', '10000', '无', '2020-09', '2020-08-27', 'Y');
INSERT INTO `budgetlist` VALUES ('25', 'KPL1A202008270002', '董办室', 'D0001', 'SK0083', '张明', 'BG0005', '财务方面', '10000', '无', '2020-09', '2020-08-27', 'Y');
INSERT INTO `budgetlist` VALUES ('26', 'KPL1A202008270003', '董办室', 'D0001', 'SK0083', '张明', 'BG0003', '人事方面', '3000', '无', '2020-09', '2020-08-27', 'Y');
INSERT INTO `budgetlist` VALUES ('27', 'KPL1A202008270003', '董办室', 'D0001', 'SK0083', '张明', 'BG0008', '福利费用', '10000', '无', '2020-09', '2020-08-27', 'Y');
INSERT INTO `budgetlist` VALUES ('28', 'KPL1A202008270004', '董办室', 'D0001', 'SK0083', '张明', 'BG0005', '财务方面', '50000', '无', '2020-09', '2020-08-27', 'N');
INSERT INTO `budgetlist` VALUES ('29', 'KPL1A202008270005', '董办室', 'D0001', 'SK0083', '张明', 'BG0003', '人事方面', '5000', '无', '2020-09', '2020-08-27', 'Y');

-- ----------------------------
-- Table structure for budgetrecord
-- ----------------------------
DROP TABLE IF EXISTS `budgetrecord`;
CREATE TABLE `budgetrecord` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DEPTCODE` varchar(30) DEFAULT NULL,
  `USEMONTH` varchar(100) DEFAULT NULL,
  `COSTTYPE` varchar(10) DEFAULT NULL,
  `AMOUNT` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of budgetrecord
-- ----------------------------
INSERT INTO `budgetrecord` VALUES ('34', 'D0001', '2020-09', 'BG0003', '13000');
INSERT INTO `budgetrecord` VALUES ('35', 'D0001', '2020-09', 'BG0005', '10000');
INSERT INTO `budgetrecord` VALUES ('37', 'D0001', '2020-09', 'BG0008', '10000');

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `DEPTCODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DEPTNAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DEPTCODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('D0001', '董办室');
INSERT INTO `dept` VALUES ('D0002', '品质部');
INSERT INTO `dept` VALUES ('D0003', '工程技术');
INSERT INTO `dept` VALUES ('D0004', '厂务部');
INSERT INTO `dept` VALUES ('D0005', '人力资源部');
INSERT INTO `dept` VALUES ('D0006', '仓储部');
INSERT INTO `dept` VALUES ('D0007', '采购');
INSERT INTO `dept` VALUES ('D0008', '经营层办公室');
INSERT INTO `dept` VALUES ('D0009', '事业一部市场运营');
INSERT INTO `dept` VALUES ('D0010', '事业一部喷涂线');
INSERT INTO `dept` VALUES ('D0011', '事业一部铝件自动线');
INSERT INTO `dept` VALUES ('D0012', '事业二部市场运营');
INSERT INTO `dept` VALUES ('D0013', '事业二部滚挂镀线');
INSERT INTO `dept` VALUES ('D0014', '事业二部连续线');
INSERT INTO `dept` VALUES ('D0015', '计划部');

-- ----------------------------
-- Table structure for deptemployee
-- ----------------------------
DROP TABLE IF EXISTS `deptemployee`;
CREATE TABLE `deptemployee` (
  `DEPTCODE` varchar(10) NOT NULL,
  `ACCOUNT` varchar(10) NOT NULL,
  PRIMARY KEY (`DEPTCODE`,`ACCOUNT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of deptemployee
-- ----------------------------
INSERT INTO `deptemployee` VALUES ('D0001', 'SK0083');
INSERT INTO `deptemployee` VALUES ('D0002', 'SK0044');
INSERT INTO `deptemployee` VALUES ('D0003', 'NK0004');
INSERT INTO `deptemployee` VALUES ('D0003', 'SK0002');
INSERT INTO `deptemployee` VALUES ('D0004', 'SK0001');
INSERT INTO `deptemployee` VALUES ('D0005', 'SK0093');
INSERT INTO `deptemployee` VALUES ('D0006', 'SK0092');
INSERT INTO `deptemployee` VALUES ('D0007', 'SK0094');
INSERT INTO `deptemployee` VALUES ('D0008', 'SK0080');
INSERT INTO `deptemployee` VALUES ('D0008', 'SK0081');
INSERT INTO `deptemployee` VALUES ('D0008', 'SK0082');
INSERT INTO `deptemployee` VALUES ('D0009', 'NK0012');
INSERT INTO `deptemployee` VALUES ('D0009', 'SK0084');
INSERT INTO `deptemployee` VALUES ('D0009', 'SK0085');
INSERT INTO `deptemployee` VALUES ('D0009', 'SK0097');
INSERT INTO `deptemployee` VALUES ('D0010', 'SK0088');
INSERT INTO `deptemployee` VALUES ('D0011', 'SK0089');
INSERT INTO `deptemployee` VALUES ('D0012', 'SK0086');
INSERT INTO `deptemployee` VALUES ('D0012', 'SK0087');
INSERT INTO `deptemployee` VALUES ('D0012', 'SK0099');
INSERT INTO `deptemployee` VALUES ('D0013', 'SK0090');
INSERT INTO `deptemployee` VALUES ('D0014', 'SK0091');
INSERT INTO `deptemployee` VALUES ('D0015', 'SK0098');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `account` varchar(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('NK0004', '胡俭梁', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('NK0012', '王翔', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0001', '李青山', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0002', '刘飞', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0044', '陈文钱', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0080', '万建斌', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0081', '孙飞', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0082', '韩高鹏', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0083', '张明', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0084', '杨锋', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0085', '王凯', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0086', '唐勇', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0087', '李高峰', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0088', '唐才林', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0089', '谢文军', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0090', '鄢俊杰', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0091', '谭伟伟', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0092', '孟凡松', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0093', '路静', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0094', '李敏', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0097', '万婷', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0098', '刘盼', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `employee` VALUES ('SK0099', '杨波', 'E10ADC3949BA59ABBE56E057F20F883E');

-- ----------------------------
-- Table structure for expensetype
-- ----------------------------
DROP TABLE IF EXISTS `expensetype`;
CREATE TABLE `expensetype` (
  `EXPENSE_CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `EXPENSE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `EXPENSE_STATUS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`EXPENSE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of expensetype
-- ----------------------------
INSERT INTO `expensetype` VALUES ('BG0001', '电话网络费', 'Y');
INSERT INTO `expensetype` VALUES ('BG0002', '修理费', 'Y');
INSERT INTO `expensetype` VALUES ('BG0003', '人事方面', 'Y');
INSERT INTO `expensetype` VALUES ('BG0004', '食堂费用', 'Y');
INSERT INTO `expensetype` VALUES ('BG0005', '财务方面', 'Y');
INSERT INTO `expensetype` VALUES ('BG0006', '借款利息', 'Y');
INSERT INTO `expensetype` VALUES ('BG0007', '安置及补偿费用', 'Y');
INSERT INTO `expensetype` VALUES ('BG0008', '福利费用', 'Y');
INSERT INTO `expensetype` VALUES ('BG0009', '培训费用', 'Y');
INSERT INTO `expensetype` VALUES ('BG0010', '差旅费用', 'Y');
INSERT INTO `expensetype` VALUES ('BG0011', '招待费用', 'Y');
INSERT INTO `expensetype` VALUES ('BG0012', '车辆费用', 'Y');
INSERT INTO `expensetype` VALUES ('BG0013', '办公费用', 'Y');
INSERT INTO `expensetype` VALUES ('BG0014', '物流费用', 'Y');
INSERT INTO `expensetype` VALUES ('BG0015', '邮寄费用', 'Y');
INSERT INTO `expensetype` VALUES ('BG0016', '其它', 'Y');

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
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

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
INSERT INTO `flow` VALUES ('51', 'budget', '1', 'manager1', null, '2', null, null, null);
INSERT INTO `flow` VALUES ('52', 'budget', '2', 'chairman', null, '-1', null, null, null);
INSERT INTO `flow` VALUES ('53', 'reimburse', '1', 'manager1', null, '2', null, null, null);
INSERT INTO `flow` VALUES ('54', 'reimburse', '2', 'chairman', null, '-1', null, null, null);
INSERT INTO `flow` VALUES ('55', 'apply', '1', 'manager1', null, '2', null, null, null);
INSERT INTO `flow` VALUES ('56', 'apply', '2', 'chairman', null, '-1', null, null, null);

-- ----------------------------
-- Table structure for flowrecord
-- ----------------------------
DROP TABLE IF EXISTS `flowrecord`;
CREATE TABLE `flowrecord` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `REQUESTNUM` varchar(255) DEFAULT NULL,
  `APPROVENAME` varchar(50) DEFAULT NULL,
  `APPROVEACCOUNT` varchar(30) DEFAULT NULL,
  `TYPE` varchar(100) DEFAULT NULL,
  `NODE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of flowrecord
-- ----------------------------
INSERT INTO `flowrecord` VALUES ('58', 'KPL1A202008270001', '万建斌', 'SK0080', 'budget', '1');
INSERT INTO `flowrecord` VALUES ('59', 'KPL1A202008270002', '万建斌', 'SK0080', 'budget', '1');
INSERT INTO `flowrecord` VALUES ('60', 'KPL1A202008270003', '万建斌', 'SK0080', 'budget', '1');
INSERT INTO `flowrecord` VALUES ('61', 'KPL1A202008270004', '万建斌', 'SK0080', 'budget', '1');
INSERT INTO `flowrecord` VALUES ('62', 'KPL1A202008270005', '万建斌', 'SK0080', 'budget', '1');
INSERT INTO `flowrecord` VALUES ('63', 'KPL1C202008270006', '万建斌', 'SK0080', 'apply', '1');
INSERT INTO `flowrecord` VALUES ('67', 'KPL1C202008270007', '万建斌', 'SK0080', 'apply', '1');
INSERT INTO `flowrecord` VALUES ('68', 'KPL1B202008280001', '万建斌', 'SK0080', 'reimburse', '1');
INSERT INTO `flowrecord` VALUES ('69', 'KPL1B202008280002', '万建斌', 'SK0080', 'reimburse', '1');

-- ----------------------------
-- Table structure for flowrequest
-- ----------------------------
DROP TABLE IF EXISTS `flowrequest`;
CREATE TABLE `flowrequest` (
  `ID` varchar(255) DEFAULT NULL,
  `REQUESTTYPE` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `LEAFNUM` varchar(255) DEFAULT NULL,
  `DES` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `MAKER` varchar(30) DEFAULT NULL,
  `MAKERNAME` varchar(255) DEFAULT NULL,
  `MAKEDATE` date DEFAULT NULL,
  `REQUESTSTATUS` char(1) DEFAULT NULL,
  `DEPT` varchar(10) DEFAULT NULL,
  `DEPTNAME` varchar(255) DEFAULT NULL,
  `USEDCODE` varchar(10) DEFAULT NULL,
  `USEDNAME` varchar(255) DEFAULT NULL,
  `NOTE` varchar(500) DEFAULT NULL,
  `FILENAME` varchar(500) DEFAULT NULL,
  `USEMONTH` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of flowrequest
-- ----------------------------
INSERT INTO `flowrequest` VALUES ('KPL1A202008270001', 'budget', null, '董办室-张明提交2020-09部门预算申请', 'SK0083', '张明', '2020-08-27', 'N', 'D0001', '董办室', null, null, '预算过高-万建斌(SK0080)', null, '2020-09');
INSERT INTO `flowrequest` VALUES ('KPL1A202008270002', 'budget', null, '董办室-张明提交2020-09部门预算申请', 'SK0083', '张明', '2020-08-27', 'C', 'D0001', '董办室', null, null, null, null, '2020-09');
INSERT INTO `flowrequest` VALUES ('KPL1A202008270003', 'budget', null, '董办室-张明提交2020-09部门预算申请', 'SK0083', '张明', '2020-08-27', 'C', 'D0001', '董办室', null, null, null, null, '2020-09');
INSERT INTO `flowrequest` VALUES ('KPL1A202008270004', 'budget', null, '董办室-张明提交2020-09部门预算申请', 'SK0083', '张明', '2020-08-27', 'N', 'D0001', '董办室', null, null, '预算过高-万建斌(SK0080)', null, '2020-09');
INSERT INTO `flowrequest` VALUES ('KPL1A202008270005', 'budget', null, '董办室-张明提交2020-09部门预算申请', 'SK0083', '张明', '2020-08-27', 'C', 'D0001', '董办室', null, null, null, null, '2020-09');
INSERT INTO `flowrequest` VALUES ('KPL1C202008270006', 'apply', null, 'xxx-张明2020-08-27', 'SK0083', '张明', '2020-08-27', 'C', 'D0001', '董办室', 'D0001', '董办室', null, null, '2020-09');
INSERT INTO `flowrequest` VALUES ('KPL1C202008270007', 'apply', null, '发福利-张明2020-08-27', 'SK0083', '张明', '2020-08-27', 'N', 'D0001', '董办室', 'D0001', '董办室', '开销过大-万建斌(SK0080)', null, '2020-09');
INSERT INTO `flowrequest` VALUES ('KPL1B202008280001', 'reimburse', 'KPL1C202008270006', '董办室-张明2020-08-28提交报销流程', 'SK0083', '张明', '2020-08-28', 'N', 'D0001', '董办室', 'D0001', '董办室', '与发票不符-万建斌(SK0080)', null, '2020-08');
INSERT INTO `flowrequest` VALUES ('KPL1B202008280002', 'reimburse', 'KPL1C202008270006', '董办室-张明2020-08-28提交报销流程', 'SK0083', '张明', '2020-08-28', 'C', 'D0001', '董办室', 'D0001', '董办室', null, null, '2020-08');

-- ----------------------------
-- Table structure for reimburse
-- ----------------------------
DROP TABLE IF EXISTS `reimburse`;
CREATE TABLE `reimburse` (
  `ID` varchar(20) NOT NULL,
  `DEPTNAME` varchar(100) DEFAULT NULL,
  `DEPTCODE` varchar(10) DEFAULT NULL,
  `MAKERACCOUNT` varchar(10) DEFAULT NULL,
  `MAKERNAME` varchar(30) DEFAULT NULL,
  `EXPENSETYPE` varchar(10) DEFAULT NULL,
  `EXPENSENAME` varchar(50) DEFAULT NULL,
  `EXPENSEAMOUNT` decimal(10,0) DEFAULT NULL,
  `EXPENSEDESC` varchar(500) DEFAULT NULL,
  `USEMONTH` varchar(255) DEFAULT NULL,
  `MAKEDATE` date DEFAULT NULL,
  `EXPENSESTATUS` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reimburse
-- ----------------------------
INSERT INTO `reimburse` VALUES ('R20200828000001', '董办室', 'D0001', 'SK0083', '张明', 'BG0003', '人事方面', '5000', '无', '2020-08', '2020-08-28', 'O');
INSERT INTO `reimburse` VALUES ('R20200828000002', '董办室', 'D0001', 'SK0083', '张明', 'BG0003', '人事方面', '4000', '无', '2020-08', '2020-08-28', 'O');

-- ----------------------------
-- Table structure for requestreimburse
-- ----------------------------
DROP TABLE IF EXISTS `requestreimburse`;
CREATE TABLE `requestreimburse` (
  `REQID` varchar(255) NOT NULL,
  `RID` varchar(255) NOT NULL,
  PRIMARY KEY (`REQID`,`RID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of requestreimburse
-- ----------------------------
INSERT INTO `requestreimburse` VALUES ('KPL1B202008280001', 'R20200828000001');
INSERT INTO `requestreimburse` VALUES ('KPL1B202008280002', 'R20200828000002');
