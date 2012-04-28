ALTER TABLE `iss`.`t_department` MODIFY COLUMN `orders` INT(11) UNSIGNED DEFAULT 0;

ALTER TABLE `iss`.`t_user` ADD COLUMN `orders` INT(11) AFTER `notice`;


UPDATE netone.ums_protectedobject SET ACTIONURL=REPLACE(ACTIONURL,'127.0.0.1','192.168.120.33') WHERE NATURALNAME LIKE 'FRAMEPG.FRAMEPG.JEWELRY%';
UPDATE netone.ums_protectedobject SET ACTIONURL=REPLACE(ACTIONURL,'192.168.120.33','127.0.0.1') WHERE NATURALNAME LIKE 'FRAMEPG.FRAMEPG.JEWELRY%';


DELETE FROM iss.t_user;
DELETE FROM iss.t_departmentlevelrow;
DELETE FROM iss.t_departmentlevel;
DELETE FROM iss.t_department_temp;
SET FOREIGN_KEY_CHECKS = 0;
DELETE FROM iss.t_department;
DELETE FROM iss.t_area;
SET FOREIGN_KEY_CHECKS = 1; 
DELETE FROM iss.t_areadepartment;
DELETE FROM netone.ums_protectedobject WHERE NATURALNAME LIKE 'DEPT.DEPT.297236c57ea1410d841db89adbfd3f08%';