DROP TABLE IF EXISTS `t_censorship`;
CREATE TABLE `t_censorship` (
  `unid` varchar(32) NOT NULL,/** ����ID */
  `subject` varchar(45) NOT NULL,/** �������� */
  `handler` varchar(45) NOT NULL,/** ���췢���� */
  `chargedept` varchar(45) NOT NULL,/** ���췢���� */
  `duetime` datetime DEFAULT NULL,/** Ҫ�����ʱ�� */
  `donetime` datetime DEFAULT NULL,/** ʵ�����ʱ�� */
  `transdept` varchar(45) DEFAULT NULL,/** ���벿�� */
  `memo` varchar(45) DEFAULT NULL,/** ����˵�� */
  `newtime` datetime DEFAULT NULL,/** ���췢��ʱ�� */
  `frome` varchar(45) DEFAULT NULL,/** ������Դ */
  PRIMARY KEY (`unid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `t_censorshiplog`;
CREATE TABLE `t_censorshiplog` (
  `punid` varchar(32) NOT NULL,/** ��־ID */
  `unid` varchar(45) NOT NULL,/** ����ID */
  `sname` varchar(45) NOT NULL,/** ������ */
  `actionname` varchar(45) NOT NULL,/** ���� */
  `tname` varchar(45) NOT NULL,/** Ŀ���� */
  `addtime` datetime DEFAULT NULL,/** ����ʱ�� */
  PRIMARY KEY (`punid`),
   CONSTRAINT `FK_t_censorshiplog_unid` FOREIGN KEY (`unid`) REFERENCES `t_censorship` (`unid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `t_censorshipstatus`;
CREATE TABLE `t_censorshipstatus` (
  `unid` varchar(32) NOT NULL,/** ����ID */
  `perunid` varchar(45) NOT NULL,/** ��ԱID */
  `parentunid` varchar(45) DEFAULT NULL,/** ����ԱID */
  `yijian` varchar(32) DEFAULT NULL,/** ��� */
  `addtime` datetime DEFAULT NULL,/** ��дʱ�� */
  `state` int(11) DEFAULT 0,/** ״̬*/
  `isdelete` int(11) DEFAULT NULL,/** �Ƿ�ɾ�� */
  `loglinkunid` varchar(45) DEFAULT NULL,/** ��־ID */
  KEY `IDX_t_censorshipstatus_perunid` (`perunid`),
  KEY `IDX_t_censorshipstatus_state` (`state`),
  CONSTRAINT `FK_t_censorshipstatus_unid` FOREIGN KEY (`unid`) REFERENCES `t_censorship` (`unid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `t_shopping`;
CREATE TABLE `t_shopping` (
  `shoppingId` VARCHAR(100) NOT NULL DEFAULT '',
  `purchaseIllustrate` VARCHAR(100) NOT NULL DEFAULT '',
  `clientId` VARCHAR(45) NOT NULL DEFAULT '',
  `operateTime` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
  `operate` VARCHAR(45) DEFAULT '',
  `price` VARCHAR(45) DEFAULT '',
  `status` INT(11) DEFAULT NULL,
  PRIMARY KEY (`shoppingId`)
) ENGINE=INNODB DEFAULT CHARSET=gbk;

/*Table structure for table `t_shoppingdetail` */

DROP TABLE IF EXISTS `t_shoppingdetail`;

CREATE TABLE `t_shoppingdetail` (
  `shoppingDetailId` VARCHAR(100) NOT NULL DEFAULT '',
  `shoppingId` VARCHAR(100) NOT NULL DEFAULT '',
  `deviceName` VARCHAR(100) NOT NULL DEFAULT '',
  `units` VARCHAR(45) DEFAULT '',
  `consignmentCount` VARCHAR(45) DEFAULT '',
  `unitPrice` VARCHAR(45) DEFAULT '',
  `hopeDeliveryTime` DATETIME DEFAULT '0000-00-00 00:00:00',
  `suggestBrand` VARCHAR(100) DEFAULT '',
  `note` VARCHAR(100) DEFAULT '',
  PRIMARY KEY (`shoppingDetailId`)
) ENGINE=INNODB DEFAULT CHARSET=gbk;

/** �����ն� ���� */

CREATE TABLE `t_groupterminal` (
  `groupTerminalId` VARCHAR(100) NOT NULL DEFAULT '',
  `subjectId` VARCHAR(100) DEFAULT NULL,
  `customerName` VARCHAR(100) DEFAULT NULL,
  `customerPhone` VARCHAR(100) DEFAULT NULL,
  `clientId` VARCHAR(100) DEFAULT NULL,
  `phone` VARCHAR(45) DEFAULT NULL,
  `groupName` VARCHAR(100) DEFAULT NULL,
  `groupId` VARCHAR(45) DEFAULT NULL,
  `groupValueLevel` VARCHAR(45) DEFAULT NULL,
  `post` VARCHAR(45) DEFAULT NULL,
  `postLevel` VARCHAR(45) DEFAULT NULL,
  `phoneManufacturers` VARCHAR(45) DEFAULT NULL,
  `phoneModels` VARCHAR(45) DEFAULT NULL,
  `networkTime` DATETIME DEFAULT NULL,
  `phonePrice` VARCHAR(45) DEFAULT NULL,
  `phoneAmount` INT(10) UNSIGNED DEFAULT NULL,
  `applicationProject` VARCHAR(100) DEFAULT NULL,
  `storedOrGive` VARCHAR(45) DEFAULT NULL,
  `monthConsumption` VARCHAR(45) DEFAULT NULL,
  `monthAmount` VARCHAR(45) DEFAULT NULL,
  `preferentialSchemes` INT(10) UNSIGNED DEFAULT NULL,
  `activityTitleAndDueTime` VARCHAR(100) DEFAULT NULL,
  `applyNote` VARCHAR(200) DEFAULT NULL,
  `status` INT(10) UNSIGNED DEFAULT '0',
  `operateTiem` DATETIME DEFAULT NULL,
  PRIMARY KEY (`groupTerminalId`)
) ENGINE=INNODB DEFAULT CHARSET=gbk;

/** �����ն� ��ϸ */
CREATE TABLE `t_groupterminaldetail` (
  `groupTerminalDetailId` VARCHAR(100) NOT NULL DEFAULT '',
  `groupTerminalId` VARCHAR(100) DEFAULT NULL,
  `lastlastlastMonth` VARCHAR(45) DEFAULT NULL,
  `monthBeforeLast` VARCHAR(45) DEFAULT NULL,
  `lastMonth` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`groupTerminalDetailId`)
) ENGINE=INNODB DEFAULT CHARSET=gbk;

/** ����洢 */
CREATE TABLE `t_yijianset` (
  `unid` varchar(32) NOT NULL,
  `userid` varchar(32) NOT NULL,
  `yijian` varchar(45) NOT NULL,
  PRIMARY KEY (`unid`),
  KEY `IDX_t_yijianset` (`yijian`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/** ���� */
CREATE TABLE `t_file` (
  `unid` varchar(32) NOT NULL,
  `d_unid` varchar(45) DEFAULT NULL,
  `u_unid` varchar(45) DEFAULT NULL,
  `f_size` varchar(45) DEFAULT NULL,
  `f_type` varchar(45) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `filename` varchar(45) DEFAULT NULL,
  `updatetime` varchar(45) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`unid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
