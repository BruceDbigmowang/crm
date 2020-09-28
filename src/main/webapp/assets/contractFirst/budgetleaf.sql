/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : budget

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2020-08-08 15:31:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for budgetleaf
-- ----------------------------
DROP TABLE IF EXISTS `budgetleaf`;
CREATE TABLE `budgetleaf` (
  `ID` varchar(255) NOT NULL,
  `BUSSINESSNAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `BUSSINESSCODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
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
INSERT INTO `budgetleaf` VALUES ('KPL0A202008040001', '集团事业部', 'B0001', '信息部', 'D0001', '105452', 'Bruce', '2020-08-04', '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, '1000', null, 'W', null, '2020-09');
INSERT INTO `budgetleaf` VALUES ('KPL0A202008050001', '集团事业部', 'B0001', '信息部', 'D0001', '105452', 'Bruce', '2020-08-05', '3000', null, '3000', null, '3000', null, '3000', null, '3000', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '3000', null, 'W', null, '2020-08');
