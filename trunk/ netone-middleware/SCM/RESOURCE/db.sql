DROP TABLE IF EXISTS `t_censorship`;
CREATE TABLE `t_censorship` (
  `unid` varchar(32) NOT NULL,/** 督办ID */
  `subject` varchar(45) NOT NULL,/** 督办名称 */
  `handler` varchar(45) NOT NULL,/** 督办发起人 */
  `chargedept` varchar(45) NOT NULL,/** 督办发起部门 */
  `duetime` datetime DEFAULT NULL,/** 要求完成时间 */
  `donetime` datetime DEFAULT NULL,/** 实际完成时间 */
  `transdept` varchar(45) DEFAULT NULL,/** 参与部门 */
  `memo` varchar(45) DEFAULT NULL,/** 督办说明 */
  `newtime` datetime DEFAULT NULL,/** 督办发起时间 */
  `frome` varchar(45) DEFAULT NULL,/** 督办来源 */
  PRIMARY KEY (`unid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `t_censorshiplog`;
CREATE TABLE `t_censorshiplog` (
  `punid` varchar(32) NOT NULL,/** 日志ID */
  `unid` varchar(45) NOT NULL,/** 督办ID */
  `sname` varchar(45) NOT NULL,/** 操作人 */
  `actionname` varchar(45) NOT NULL,/** 动作 */
  `tname` varchar(45) NOT NULL,/** 目标人 */
  `addtime` datetime DEFAULT NULL,/** 操作时间 */
  PRIMARY KEY (`punid`),
   CONSTRAINT `FK_t_censorshiplog_unid` FOREIGN KEY (`unid`) REFERENCES `t_censorship` (`unid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `t_censorshipstatus`;
CREATE TABLE `t_censorshipstatus` (
  `unid` varchar(32) NOT NULL,/** 督办ID */
  `perunid` varchar(45) NOT NULL,/** 人员ID */
  `parentunid` varchar(45) DEFAULT NULL,/** 父人员ID */
  `yijian` varchar(32) DEFAULT NULL,/** 意见 */
  `addtime` datetime DEFAULT NULL,/** 填写时间 */
  `state` int(11) DEFAULT 0,/** 状态*/
  `isdelete` int(11) DEFAULT NULL,/** 是否删除 */
  `loglinkunid` varchar(45) DEFAULT NULL,/** 日志ID */
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

/** 集团终端 主表 */

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

/** 集团终端 明细 */
CREATE TABLE `t_groupterminaldetail` (
  `groupTerminalDetailId` VARCHAR(100) NOT NULL DEFAULT '',
  `groupTerminalId` VARCHAR(100) DEFAULT NULL,
  `lastlastlastMonth` VARCHAR(45) DEFAULT NULL,
  `monthBeforeLast` VARCHAR(45) DEFAULT NULL,
  `lastMonth` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`groupTerminalDetailId`)
) ENGINE=INNODB DEFAULT CHARSET=gbk;

/** 意见存储 */
CREATE TABLE `t_yijianset` (
  `unid` varchar(32) NOT NULL,
  `userid` varchar(32) NOT NULL,
  `yijian` varchar(45) NOT NULL,
  PRIMARY KEY (`unid`),
  KEY `IDX_t_yijianset` (`yijian`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/** 附件 */
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
