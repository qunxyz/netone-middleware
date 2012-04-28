ALTER TABLE `iss`.`t_department` MODIFY COLUMN `orders` INT(11) UNSIGNED DEFAULT 0;

ALTER TABLE `iss`.`t_user` ADD COLUMN `orders` INT(11) AFTER `notice`;
