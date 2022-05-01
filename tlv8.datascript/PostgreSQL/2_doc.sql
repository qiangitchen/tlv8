CREATE TABLE Doc_Admin
(
    fID                         VARCHAR(32) NOT NULL,
    fLeve                       INT,
    fName                       VARCHAR(500),
    version                     INT
);

alter table Doc_Admin
  add primary key (FID);

CREATE TABLE Doc_Author
(
    fID                         VARCHAR(32) NOT NULL,
    fUserID                     VARCHAR(32),
    fAmLeve                     INT,
    version                     INT
);

alter table Doc_Author
  add primary key (FID);

CREATE TABLE Doc_DocPath
(
    fID                         VARCHAR(32) NOT NULL,
    fFileID                     VARCHAR(32),
    fFilePath                   VARCHAR(200),
    fFileSize                   FLOAT,
    fVersion                    INT,
    fAddTime                    timestamp,
    version                     INT
);

alter table Doc_DocPath add primary key (FID);
alter table Doc_DocPath add unique (fFileID);

CREATE TABLE Doc_Document
(
    fID                         VARCHAR(32) NOT NULL,
    fDocID                      VARCHAR(32),
    fDocName                    VARCHAR(1000),
    fExtName                    VARCHAR(100),
    fDocSize                    FLOAT,
    fDocType                    VARCHAR(100),
    fAddTime                    timestamp,
    fUpdateTime                 timestamp,
    version                     INT
);
alter table Doc_Document add primary key (FID);
alter table Doc_Document add unique (fDocID);

CREATE TABLE Doc_Index
(
    fMax                        bigint
);

CREATE TABLE Doc_Log
(
    fID                         VARCHAR(32) NOT NULL,
    fUserID                     VARCHAR(32),
    fAddTime                    timestamp,
    fAction                     VARCHAR(100),
    fMessage                    TEXT,
    version                     INT
);

alter table Doc_Log
  add primary key (FID);

CREATE TABLE Doc_PathIndex
(
    fDocPath                    VARCHAR(200),
    fMax                        INT
);


CREATE TABLE Doc_Result
(
    resultID                    VARCHAR(500),
    resultContent               TEXT
);

CREATE TABLE Doc_User
(
    fID                         VARCHAR(32) NOT NULL,
    fLoginID                    VARCHAR(100),
    fUserName                   VARCHAR(100),
    fEnable                     INT,
    version                     INT
);

alter table Doc_User
  add primary key (FID);

