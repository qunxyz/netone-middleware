CREATE TABLE IF NOT EXISTS myaccount
(
   lsh                    varchar(32)                  NOT NULL,
   name                  VARCHAR(100),
   types                    VARCHAR(100),
   oriprice                double,
   curprice                double,
   description              VARCHAR(1000),
   PRIMARY KEY (lsh)
);
