-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.22-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema abc
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ abc;
USE abc;

--
-- Table structure for table `abc`.`myaccount`
--

DROP TABLE IF EXISTS `myaccount`;
CREATE TABLE `myaccount` (
  `lsh` varchar(32) NOT NULL,
  `name` varchar(100) default NULL,
  `types` varchar(100) default NULL,
  `oriprice` double default NULL,
  `curprice` double default NULL,
  `description` varchar(1000) default NULL,
  `participant` varchar(100) default NULL,
  `created` datetime default NULL,
  PRIMARY KEY  (`lsh`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

--
-- Dumping data for table `abc`.`myaccount`
--

/*!40000 ALTER TABLE `myaccount` DISABLE KEYS */;
INSERT INTO `myaccount` (`lsh`,`name`,`types`,`oriprice`,`curprice`,`description`,`participant`,`created`) VALUES 
 ('27e1a649557047c09f1f7f3663f3f8fd','康熙字画','古董',200000,350000,'','','2010-09-10 18:45:23'),
 ('3cd5a424b9274f21b6dc1cfc67ef73d3','东方电子','股票',200000,210000,'','','2010-09-11 18:46:17'),
 ('6b235c5d8ffc414fa53b3e23915ca32e','西滨家园','房产',300000,500000,'','','2010-09-12 18:45:02'),
 ('77848439d2034635ae3238ec2f5b4c82','Fit','汽车',130000,120000,'','','2010-09-13 18:44:43'),
 ('a57ae151e55a4b6eb095e314949d5558','中国石油','股票',100000,80000,'','','2010-09-14 18:45:43'),
 ('ab4172c4eac847f6b11d84ae26933e40','中国石化','股票',200000,180000,'','','2010-09-15 18:46:00'),
 ('e607f1d9e43d41129b2598ccac292664','KIA','汽车',120000,100000,'','','2010-09-16 18:44:26');
/*!40000 ALTER TABLE `myaccount` ENABLE KEYS */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
