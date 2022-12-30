CREATE TABLE Doc_Admin
(
    fID                         VARCHAR2(32) NOT NULL,
    fLeve                       INTEGER,
    fName                       VARCHAR2(500),
    version                     INTEGER
);
alter table Doc_Admin add constraint PK_Doc_Admin primary key (fID);

CREATE TABLE Doc_Author
(
    fID                         VARCHAR2(32) NOT NULL,
    fUserID                     VARCHAR2(32),
    fAmLeve                     INTEGER,
    version                     INTEGER
);
alter table Doc_Author add constraint PK_Doc_Author primary key (fID);

CREATE TABLE Doc_DocPath
(
    fID                         VARCHAR2(32) NOT NULL,
    fFileID                     VARCHAR2(32),
    fFilePath                   VARCHAR2(200),
    fFileSize                   NUMBER,
    fVersion                    INTEGER,
    fAddTime                    datetime,
    version                     INTEGER
);
alter table Doc_DocPath add constraint PK_Doc_DocPath primary key (fID);

CREATE TABLE Doc_Document
(
    fID                         VARCHAR2(32) NOT NULL,
    fDocID                      VARCHAR2(32),
    fDocName                    VARCHAR2(1000),
    fExtName                    VARCHAR2(100),
    fDocSize                    NUMBER,
    fDocType                    VARCHAR2(100),
    fAddTime                    datetime,
    fUpdateTime                 datetime,
    version                     INTEGER
);
alter table Doc_Document add constraint PK_Doc_Document primary key (fID);

CREATE TABLE Doc_Index
(
    fMax                        number
);

CREATE TABLE Doc_Log
(
    fID                         VARCHAR2(32) NOT NULL,
    fUserID                     VARCHAR2(32),
    fAddTime                    datetime,
    fAction                     VARCHAR2(100),
    fMessage                    CLOB,
    version                     INTEGER
);
alter table Doc_Log add constraint PK_Doc_Log primary key (fID);

CREATE TABLE Doc_PathIndex
(
    fDocPath                    VARCHAR2(200),
    fMax                        INTEGER
);


CREATE TABLE Doc_Result
(
    resultID                    VARCHAR2(500),
    resultContent               CLOB
);

CREATE TABLE Doc_User
(
    fID                         VARCHAR2(32) NOT NULL,
    fLoginID                    VARCHAR2(100),
    fUserName                   VARCHAR2(100),
    fEnable                     INTEGER,
    version                     INTEGER
);
alter table Doc_User add constraint PK_Doc_User primary key (fID);

