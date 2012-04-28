ALTER TABLE `iss`.`t_user` ADD COLUMN `zw` VARCHAR(45) AFTER `cancelDate`,
 ADD COLUMN `accounttypes` int(2) AFTER `zw`,
 ADD COLUMN `leavetime` DATETIME AFTER `accounttypes`,
 ADD COLUMN `backtime` DATETIME AFTER `leavetime`,
 ADD COLUMN `dlr` VARCHAR(10) AFTER `backtime`,
 ADD COLUMN `notice` int(4) AFTER `dlr`;