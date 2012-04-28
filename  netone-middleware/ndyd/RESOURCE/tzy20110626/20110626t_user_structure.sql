/*
SQLyog Ultimate v8.8 
MySQL - 5.0.22-community : Database - iss
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`iss` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `iss`;

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `userId` varchar(32) NOT NULL,
  `userCode` varchar(45) default NULL,
  `userName` varchar(100) default NULL,
  `departmentId` varchar(32) default NULL,
  `status` varchar(6) default NULL,
  `extendinfo` varchar(1000) default NULL,
  `password` varchar(300) default NULL,
  `types` varchar(20) default NULL,
  `description` varchar(3000) default NULL,
  `email` varchar(300) default NULL,
  `addressinfo` varchar(300) default NULL,
  `fox` varchar(300) default NULL,
  `phone` varchar(300) default NULL,
  `bussiness` varchar(300) default NULL,
  `participant` varchar(300) default NULL,
  `sex` varchar(3) default NULL,
  `marriage` varchar(3) default NULL,
  `ids` varchar(60) default NULL,
  `major` varchar(300) default NULL,
  `createdDate` datetime default NULL,
  `modifyDate` datetime default NULL,
  `cancelDate` datetime default NULL,
  `zw` varchar(45) default NULL,
  `accounttypes` int(2) default NULL,
  `leavetime` datetime default NULL,
  `backtime` datetime default NULL,
  `dlr` varchar(10) default NULL,
  `notice` int(10) default NULL,
  PRIMARY KEY  (`userId`),
  KEY `FK_t_user_departmentId` (`departmentId`),
  CONSTRAINT `FK_t_user_departmentId` FOREIGN KEY (`departmentId`) REFERENCES `t_department` (`departmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
