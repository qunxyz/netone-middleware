CREATE TABLE IF NOT EXISTS myaccount
(
   lsh                    varchar(32)                  NOT NULL,
   name                  VARCHAR(100),
   types                    VARCHAR(100),
   oriprice                double,
   curprice                double,
   estprice                double,
   description              VARCHAR(1000),
   participant              varchar(100),
   created       datetime,
   auditing char(1),
   PRIMARY KEY (lsh)
);