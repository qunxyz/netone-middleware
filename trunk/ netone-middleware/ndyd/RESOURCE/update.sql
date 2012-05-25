ALTER TABLE `iss`.`t_department` MODIFY COLUMN `orders` INT(11) UNSIGNED DEFAULT 0;

ALTER TABLE `iss`.`t_user` ADD COLUMN `orders` INT(11) AFTER `notice`;

/** 20120504地图*/
DROP TABLE IF EXISTS `t_area`;
CREATE TABLE `t_area` (
  `areaId` varchar(32) NOT NULL,
  `areaCode` varchar(45) NOT NULL,
  `areaName` varchar(100) DEFAULT NULL,
  `extension` varchar(200) DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  `parentAreaId` varchar(32) DEFAULT NULL,
  `nLevel` int(11) DEFAULT '0',
  `nLevelCode` varchar(8000) DEFAULT '[0]',
  `nLevelName` varchar(8000) DEFAULT '[0]',
  `level` varchar(45) DEFAULT '0',
  `map` varchar(100) DEFAULT '',
  PRIMARY KEY (`areaId`),
  KEY `index_nlevel` (`nLevel`),
  KEY `index_level` (`level`),
  KEY `index_areaCode` (`areaCode`),  
  KEY `index_nLevelCode` (`nLevelCode`),
  KEY `Index_pareaId` (`parentAreaId`),
  CONSTRAINT `FK_t_area_areaId` FOREIGN KEY (`parentAreaId`) REFERENCES `t_area` (`areaId`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

DROP TABLE IF EXISTS `t_areadepartment`;
CREATE TABLE `t_areadepartment` (
  `rid` varchar(32) NOT NULL,
  `areaId` varchar(32) NOT NULL,
  `departmentId` varchar(32) NOT NULL,
  PRIMARY KEY (`rid`),
  KEY `index_areaId` (`areaId`),
  KEY `index_departmentId` (`departmentId`),
  CONSTRAINT `FK_t_areadepartment_areaId` FOREIGN KEY (`areaId`) REFERENCES `t_area` (`areaId`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/** 新增附件关联工作流ID */
ALTER TABLE `iss`.`t_file` 
 ADD COLUMN `wf_code` VARCHAR(45) AFTER `updatetime`;

/** 附件文件名及文件大小字段长度改大*/
ALTER TABLE `iss`.`t_file` 
 MODIFY COLUMN `f_size` VARCHAR(100) CHARACTER SET gbk COLLATE gbk_chinese_ci,
 MODIFY COLUMN `filename` VARCHAR(500) CHARACTER SET gbk COLLATE gbk_chinese_ci;
 