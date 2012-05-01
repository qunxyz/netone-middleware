0、要求用户吧未完成的订单全部走完

1、从仓库 导出  t_stackcoderelevance 并入中心数据库

2、旧数据整改
alter table t_otherpayment modify  accountantMonth int;
alter table t_indent modify onestatus int;
alter table t_indent modify twoStatus int;
alter table t_plan modify onestatus int;
alter table t_plan modify twoStatus int;

3、清理新系统的数据数据
delete from t_allocatecargodetail;
delete from t_stackcoderelevance;
delete from t_payment;
delete from t_allocatecargo;
delete from t_indentdetail;
delete from t_indent;
delete from t_instoragedetail;
delete from t_instorage;
delete from t_outstoragedetail;
delete from t_outstorage;
delete from t_plandetail;
delete from t_plan;
delete from t_producelinedetail;
delete from t_produceline;

delete from t_collectionpayment;
delete from t_incentivepaymentcash;
delete from t_managepayment;
delete from t_otherpayment;

delete from t_reimbursepayment;

4、启动数据迁移程序

