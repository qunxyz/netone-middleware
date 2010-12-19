<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:directive.page import="oe.frame.orm.OrmerEntry" />
<%  

String []alldb={"CREATE TABLE IIS_FILEUPLOAD_DIRS(   DIRID   NUMERIC(22,0)   NOT NULL,
   DIRNAME                        VARCHAR(100)                   NOT NULL,
   DIRPARENTID                    NUMERIC(22,0)                  NOT NULL,
   DIRDESC                        VARCHAR(1000),
   PARTICIPANT                    VARCHAR(100),
   TYPES                          VARCHAR(2),
   PRIMARY KEY (DIRID));","CREATE TABLE IF NOT EXISTS IIS_FILEUPLOAD_FILES
(
   FILEID                         VARCHAR(50)                    NOT NULL,
   FILENAME                       VARCHAR(100)                   NOT NULL,
   DIRID                          NUMERIC(22,0)                  NOT NULL,
   FILEDESC                       VARCHAR(1000),
   USERID                         NUMERIC(22,0),
   FILESIZE                       NUMERIC(22,0),
   EXTNAME                        VARCHAR(100),
   CREATETIME                     DATETIME,
   DOWNLOADCNT                    NUMERIC(22,0),
   PARTICIPANT                    VARCHAR(100),
   PRIMARY KEY (FILEID)
);","CREATE TABLE IF NOT EXISTS T_CMS_GROUP
(
   GROUPID                        VARCHAR(100)                   NOT NULL,
   GROUPNAME                      VARCHAR(100),
   EXTENDATTRIBUTE                VARCHAR(1000),
   DESCRIPTION                    VARCHAR(4000),
   TYPES                          VARCHAR(20),
   PARTICIPANT                    VARCHAR(100),
   PRIMARY KEY (GROUPID)
);","CREATE TABLE IF NOT EXISTS T_CMS_INFOCELL
(
   CELLID                         VARCHAR(50)                    NOT NULL,
   CELLNAME                       VARCHAR(100),
   EXTENDATTRIBUTE                VARCHAR(1000),
   BELONGTO                       VARCHAR(100),
   INTIME                         VARCHAR(10),
   PARTICIPANT                    VARCHAR(100),
   BODY                           LONGTEXT,
   TYPES                          VARCHAR(2),
   PRIMARY KEY (CELLID)
);","CREATE TABLE IF NOT EXISTS T_CMS_INFOMODEL
(
   MODELID                        VARCHAR(30)                    NOT NULL,
   MODELNAME                      VARCHAR(100),
   DESCRIPTION                    VARCHAR(1000),
   EXTENDATTRIBUTE                VARCHAR(1000),
   USERID                         VARCHAR(100),
   ACCESSMODE                     CHAR(2)                        NOT NULL,
   INFOXML                        VARCHAR(1000),
   PARTICIPANT                    VARCHAR(100),
   HIT                            NUMERIC(20),
   LEVELS                         CHAR(1),
   WINTIME                        VARCHAR(30),
   PRIMARY KEY (MODELID)
);","CREATE TABLE IF NOT EXISTS T_CMS_JPPMIDWARE
(
   JPPMID                         VARCHAR(100)                   NOT NULL,
   JPPMIDNAME                     VARCHAR(100),
   ACCESSTYPE                     CHAR(1),
   JPPMIDSCRIPT                   TEXT,
   JPPMIDSCRIPTAPI                TEXT,
   PARTICIPANT                    VARCHAR(100),
   EXTENDATTRIBUTE                VARCHAR(100),
   PRIMARY KEY (JPPMID)
);","CREATE TABLE IF NOT EXISTS T_CMS_RECORDTIP
(
   TIPID                          VARCHAR(32)                    NOT NULL,
   TIPINFO                        LONGTEXT,
   SQLID                          VARCHAR(30)                    NOT NULL,
   WRITER                         VARCHAR(30)                    NOT NULL,
   COLUMNID                       VARCHAR(30)                    NOT NULL,
   RECORDID                       VARCHAR(1000)                  NOT NULL,
   PRIMARY KEY (TIPID)
);","CREATE TABLE IF NOT EXISTS T_CS_DEPT
(
   DEPTCODE                       VARCHAR(32)                    NOT NULL,
   DEPTNAME                       VARCHAR(100),
   FATHERCODE                     VARCHAR(32),
   DESCRIPTION                    VARCHAR(1000),
   EXTENDATTRIBUTE                VARCHAR(1000),
   SYSTEMID                       VARCHAR(32)                    NOT NULL,
   PARTICIPANT                    VARCHAR(100),
   PRIMARY KEY (DEPTCODE)
);","CREATE TABLE IF NOT EXISTS T_CS_USER
(
   USERCODE                       VARCHAR(100)                   NOT NULL,
   SYSTEMID                       VARCHAR(100),
   NAME                           VARCHAR(100),
   STATUSINFO                     CHAR(2),
   EXTENDINFO                     VARCHAR(1000),
   PASSWORDX                      VARCHAR(100),
   TYPES                          CHAR(2),
   CREATED                        VARCHAR(30),
   DESCRIPTION                    VARCHAR(1000),
   EMAIL                          VARCHAR(100),
   ADDRESSINFO                    VARCHAR(100),
   FOX                            VARCHAR(100),
   PHONE                          VARCHAR(100),
   BUSSINESS                      VARCHAR(100),
   PARTICIPANT                    VARCHAR(100),
   PRIMARY KEY (USERCODE)
);","CREATE TABLE IF NOT EXISTS T_CS_USER2DEPT
(
   DEPTUSERCODE                   VARCHAR(32)                    NOT NULL,
   DEPTCODE                       VARCHAR(32),
   USERCODE                       VARCHAR(100),
   PARTICIPANT                    VARCHAR(100),
   PRIMARY KEY (DEPTUSERCODE)
);"}

for(int i=0;i<alldb.length;i++){
OrmerEntry.fetchOrmer("coredb").fetchSqlBean().executesql(alldb[i]);

}



%>
