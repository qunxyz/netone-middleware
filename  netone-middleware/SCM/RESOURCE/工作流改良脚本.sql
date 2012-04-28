DROP TABLE IF EXISTS `netone`.`t_wf_participant`;
CREATE TABLE `netone`.`t_wf_participant` (
  `lsh` VARCHAR(32) NOT NULL DEFAULT '',
  `workcode` VARCHAR(32) NOT NULL DEFAULT '',
  `username` VARCHAR(45) NOT NULL DEFAULT '',
  `usercode` VARCHAR(45) NOT NULL DEFAULT '',
  `types` CHAR(2) NOT NULL DEFAULT '',
  `extendattribute` VARCHAR(100) DEFAULT '',
  `statusnow`  CHAR(2) NOT NULL DEFAULT '',
  `sync`  CHAR(1) NOT NULL DEFAULT '',
  `commitername` VARCHAR(45) NOT NULL DEFAULT '',
  `commitercode` VARCHAR(45) NOT NULL DEFAULT '',
  `auditnode` VARCHAR(100) NOT NULL DEFAULT '',
   `donetime` VARCHAR(100) NOT NULL DEFAULT '',
   `createtime` VARCHAR(100) NOT NULL DEFAULT '',
   `limitime` int  DEFAULT NULL,
  PRIMARY KEY(`lsh`)
)
ENGINE = InnoDB DEFAULT CHARSET=gbk;

alter table t_wf_participant add column opemode char(2);
alter table t_wf_participant add column actname VARCHAR(100);
alter table t_wf_participant add column actid VARCHAR(45);
alter table t_wf_participant add column msg char(1) default '0';

CREATE TABLE `netone`.`t_wf_relevantvar_tmp` (
  `runtimeid` VARCHAR(32) NOT NULL DEFAULT '',
  `d0` VARCHAR(100),
  `d1` VARCHAR(100),
  `d2` VARCHAR(100),
  `d3` VARCHAR(100),
  `d4` VARCHAR(100),
  `d5` VARCHAR(100),
  `d6` VARCHAR(100),
  `d7` VARCHAR(100),
  `d8` VARCHAR(100),
  PRIMARY KEY(`runtimeid`)
)
ENGINE = InnoDB DEFAULT CHARSET=gbk;

alter table t_wf_relevantvar_tmp add column appname VARCHAR(100);
alter table t_wf_relevantvar_tmp add column lsh VARCHAR(45);

use netone;
alter table t_wf_participant modify auditnode varchar(1000);
use iss;
alter table t_yijianset modify yijian varchar(1000);


ALTER TABLE `iss`.`t_user` ADD INDEX `IDX_USER_USERCODE`(`userCode`);

ALTER TABLE `netone`.`t_wf_participant` ADD INDEX `IDX_WF_PARTICIPANT_USERCODE`(`usercode`),
 ADD INDEX `IDX_WF_PARTICIPANT_TYPES`(`types`);

