CREATE TABLE [dbo].[Doc_Admin]
(
    fID                         NVARCHAR(32) NOT NULL,
    fLeve                       INT,
    fName                       NVARCHAR(500),
    version                     INT
)ON [PRIMARY]

alter table [dbo].[Doc_Admin] ADD 
	PRIMARY KEY CLUSTERED (
		 [fID]
	) ON [PRIMARY] 

CREATE TABLE [dbo].[Doc_Author]
(
    fID                         NVARCHAR(32) NOT NULL,
    fUserID                     NVARCHAR(32),
    fAmLeve                     INT,
    version                     INT
)ON [PRIMARY]

alter table [dbo].[Doc_Author] ADD 
	PRIMARY KEY CLUSTERED (
		 [fID]
	) ON [PRIMARY] 

CREATE TABLE [dbo].[Doc_DocPath]
(
    fID                         NVARCHAR(32) NOT NULL,
    fFileID                     NVARCHAR(32),
    fFilePath                   NVARCHAR(200),
    fFileSize                   FLOAT,
    fVersion                    INT,
    fAddTime                    DATETIME,
    version                     INT
)ON [PRIMARY]

alter table [dbo].[Doc_DocPath] ADD 
	PRIMARY KEY CLUSTERED (
		 [fID]
	) ON [PRIMARY] 

CREATE TABLE [dbo].[Doc_Document]
(
    fID                         NVARCHAR(32) NOT NULL,
    fDocID                      NVARCHAR(32),
    fDocName                    NVARCHAR(1000),
    fExtName                    NVARCHAR(100),
    fDocSize                    FLOAT,
    fDocType                    NVARCHAR(100),
    fAddTime                    DATETIME,
    fUpdateTime                 DATETIME,
    version                     INT
)ON [PRIMARY]

alter table [dbo].[Doc_Document] ADD 
	PRIMARY KEY CLUSTERED (
		 [fID]
	) ON [PRIMARY] 

CREATE TABLE [dbo].[Doc_Index]
(
    fMax                        bigint
)ON [PRIMARY]

CREATE TABLE [dbo].[Doc_Log]
(
    fID                         NVARCHAR(32) NOT NULL,
    fUserID                     NVARCHAR(32),
    fAddTime                    DATETIME,
    fAction                     NVARCHAR(100),
    fMessage                    TEXT,
    version                     INT
)ON [PRIMARY]

alter table [dbo].[Doc_Log] ADD 
	PRIMARY KEY CLUSTERED (
		 [fID]
	) ON [PRIMARY] 

CREATE TABLE [dbo].[Doc_PathIndex]
(
    fDocPath                    NVARCHAR(200),
    fMax                        INT
)ON [PRIMARY]


CREATE TABLE [dbo].[Doc_Result]
(
    resultID                    NVARCHAR(500),
    resultContent               TEXT
)ON [PRIMARY]

CREATE TABLE [dbo].[Doc_User]
(
    fID                         NVARCHAR(32) NOT NULL,
    fLoginID                    NVARCHAR(100),
    fUserName                   NVARCHAR(100),
    fEnable                     INT,
    version                     INT
)ON [PRIMARY]

alter table [dbo].[Doc_User] ADD 
	PRIMARY KEY CLUSTERED (
		 [fID]
	) ON [PRIMARY] 

