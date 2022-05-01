-- Create table
create table [dbo].[OA_DC_RECVDOC]
(
  FID                NVARCHAR(32) not null,
  VERSION            int,
  FDOCNUMBER         NVARCHAR(100),
  FTITLE             NVARCHAR(150),
  FDOCSOURCEDEPT     NVARCHAR(100),
  FDOCSOURCEDATE     DATETIME,
  FDOCCOPIES         int,
  FPAGECOUNT         NVARCHAR(10),
  FDOCTYPE           NVARCHAR(100),
  FURGENCY           NVARCHAR(10),
  FCONFIDENTIALITY   NVARCHAR(10),
  FDOCRECVNO         NVARCHAR(100),
  FDOCRECVDATE       DATETIME,
  FREMARK            NVARCHAR(2000),
  FATTACHMENT        NVARCHAR(2000),
  FCREATEOGNID       NVARCHAR(32),
  FCREATEOGNNAME     NVARCHAR(64),
  FCREATEDEPTID      NVARCHAR(32),
  FCREATEDEPTNAME    NVARCHAR(64),
  FCREATEPOSID       NVARCHAR(32),
  FCREATEPOSNAME     NVARCHAR(64),
  FCREATEPERSONID    NVARCHAR(32),
  FCREATEPERSONNAME  NVARCHAR(64),
  FCREATEPERSONFID   NVARCHAR(512),
  FCREATEPERSONFNAME NVARCHAR(1024),
  FCREATETIME        DATETIME
)ON [PRIMARY]

alter table [dbo].[OA_DC_RECVDOC] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 
  
-- Create table
create table [dbo].[OA_DC_SENDDOC]
(
  FID                NVARCHAR(32) not null,
  VERSION            int,
  FTITLE             NVARCHAR(150),
  FTYPE              NVARCHAR(100),
  FDOCNUMBER         NVARCHAR(100),
  FURGENCY           NVARCHAR(10),
  FCONFIDENTIALITY   NVARCHAR(10),
  FPAGECOUNT         NVARCHAR(10),
  FPRINTCOUNT        NVARCHAR(10),
  FMAINDEPT          NVARCHAR(2000),
  FCOPYDEPT          NVARCHAR(2000),
  FSENDDATE          DATETIME,
  FCREATEOGNID       NVARCHAR(32),
  FCREATEOGNNAME     NVARCHAR(64),
  FCREATEDEPTID      NVARCHAR(32),
  FCREATEDEPTNAME    NVARCHAR(64),
  FCREATEPOSID       NVARCHAR(32),
  FCREATEPOSNAME     NVARCHAR(64),
  FCREATEPERSONID    NVARCHAR(32),
  FCREATEPERSONNAME  NVARCHAR(64),
  FCREATEPERSONFID   NVARCHAR(512),
  FCREATEPERSONFNAME NVARCHAR(1024),
  FCREATETIME        DATETIME,
  FATTACHMENT        NVARCHAR(4000),
  FDOCFILE           NVARCHAR(1000)
)ON [PRIMARY]

alter table [dbo].[OA_DC_SENDDOC] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

-- Create table
create table [dbo].[OA_EM_RECEIVEEMAIL]
(
  FID            NVARCHAR(100) not null,
  VERSION        int,
  FEMAILNAME     NVARCHAR(100),
  FTEXT          TEXT,
  FFJID          NVARCHAR(4000),
  FSENDPERNAME   NVARCHAR(100),
  FSENDPERID     NVARCHAR(100),
  FREPLYSTATE    NVARCHAR(100),
  FQUREY         NVARCHAR(100),
  FCONSIGNEE     NVARCHAR(100),
  FCONSIGNEEID   NVARCHAR(100),
  FCONSIGNEECODE NVARCHAR(100),
  FSENDTIME      DATETIME,
  FSENDPERCODE   NVARCHAR(100),
  FSENDOGN       NVARCHAR(100),
  FSENDDEPT      NVARCHAR(100),
  FCOLLECT       NVARCHAR(10) DEFAULT '0'
)ON [PRIMARY]

alter table [dbo].[OA_EM_RECEIVEEMAIL] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

-- Create table
create table [dbo].[OA_EM_SENDEMAIL]
(
  FID            NVARCHAR(100) not null,
  VERSION        int,
  FEMAILNAME     NVARCHAR(200),
  FCONSIGNEE     TEXT,
  FTEXT          TEXT,
  FSTATE         NVARCHAR(100),
  FCONSIGNEEID   TEXT,
  FCONSIGNEECODE TEXT,
  FFJID          NVARCHAR(4000),
  FSENDPERNAME   NVARCHAR(100),
  FSENDPERID     NVARCHAR(100),
  FCREATTIME     DATETIME,
  FSENDTIME      DATETIME,
  FSENDPERCODE   NVARCHAR(100),
  FSENDOGN       NVARCHAR(100),
  FSENDDEPT      NVARCHAR(100),
  FCOLLECT       NVARCHAR(10) DEFAULT '0'
)ON [PRIMARY]

alter table [dbo].[OA_EM_SENDEMAIL] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

-- Create table
create table [dbo].[OA_FLOWCONCLUSION]
(
  FID             NVARCHAR(32) not null,
  FCONCLUSIONNAME NVARCHAR(4000),
  FORDER          INTEGER,
  FCREATORID      NVARCHAR(100),
  FCREATOR        NVARCHAR(100),
  VERSION         INTEGER
)ON [PRIMARY]

alter table [dbo].[OA_FLOWCONCLUSION] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 
	
  
-- Create table
create table [dbo].[OA_FLOWRECORD]
(
  FID             NVARCHAR(32) not null,
  FNODENAME       NVARCHAR(100),
  FAGREETEXT      NVARCHAR(4000),
  FCREATEPERNAME  NVARCHAR(100),
  FCREATEDEPTNAME NVARCHAR(100),
  FCREATETIME     DATETIME,
  FBILLID         NVARCHAR(32),
  FCREATEDEPTID   NVARCHAR(100),
  FCREATEPERID    NVARCHAR(100),
  FNODEID         NVARCHAR(100),
  VERSION         INTEGER,
  FOPVIEWID       NVARCHAR(100),
  FTASKID         NVARCHAR(100),
  FFLOWID         NVARCHAR(100),
  FSIGN           text
)ON [PRIMARY]

alter table [dbo].[OA_FLOWRECORD] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 
	  
-- Create table
create table [dbo].[OA_KM_KNOWLEDGE]
(
  FID             NVARCHAR(36) not null,
  VERSION         int,
  FCREATEOGNID    NVARCHAR(36),
  FCREATEOGNNAME  NVARCHAR(64),
  FCREATEDEPTID   NVARCHAR(36),
  FCREATEDEPTNAME NVARCHAR(64),
  FCREATEPSNID    NVARCHAR(36),
  FCREATEPSNNAME  NVARCHAR(64),
  FCREATETIME     DATETIME,
  FNOTICETYPE     NVARCHAR(30),
  FSENDTYPE       int,
  FPOSTEDRANGE    NVARCHAR(4000),
  FTITLE          NVARCHAR(200),
  FSENDDATE       DATETIME,
  FCONTENT        NVARCHAR(4000),
  FATTACHMENT     TEXT,
  FSENDSTATUS     int,
  FDEPTCHECK      NVARCHAR(4000),
  FOFFICECHECK    NVARCHAR(4000),
  FLEADERCHECK    NVARCHAR(4000),
  FPOSTEDRANGEID  NVARCHAR(4000)
)ON [PRIMARY]

alter table [dbo].[OA_KM_KNOWLEDGE] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 
	  
-- Create table
create table [dbo].[OA_KM_KNOWLEDGERANGE]
(
  FID           NVARCHAR(32) NOT NULL,
  FBILLID       NVARCHAR(32),
  FFULLID       NVARCHAR(1024),
  FFULLNAME     NVARCHAR(1024),
  FCREATORID    NVARCHAR(100),
  FCREATOR      NVARCHAR(100),
  FCREATEDEPTID NVARCHAR(100),
  FCREATEDEPT   NVARCHAR(100),
  FCREATEDATE   DATETIME,
  VERSION       INTEGER,
  FNAME         NVARCHAR(100)
)ON [PRIMARY]

alter table [dbo].[OA_KM_KNOWLEDGERANGE] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 
	  
-- Create table
create table [dbo].[OA_NOTICE_PERSON]
(
  FID             NVARCHAR(64) not null,
  VERSION         INTEGER,
  FOGNID          NVARCHAR(64),
  FOGNNAME        NVARCHAR(200),
  FORGID          NVARCHAR(64),
  FORGNAME        NVARCHAR(200),
  FCREATEID       NVARCHAR(64),
  FCREATENAME     NVARCHAR(200),
  FCREATEDATETIME DATETIME,
  FPERSONID       NVARCHAR(64),
  FPERSONNAME     NVARCHAR(200),
  FREADDATE       DATETIME,
  FPERSONFNAME    NVARCHAR(500),
  FMAINID         NVARCHAR(64),
  FBROWSE         NVARCHAR(5),
  FDEPTNAME       NVARCHAR(100),
  FDEPTID         NVARCHAR(100)
)ON [PRIMARY]

alter table [dbo].[OA_NOTICE_PERSON] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 
  
-- Create table
create table [dbo].[OA_PUB_DOCPIGEONHOLE]
(
  FID           NVARCHAR(32) not null,
  FNAME         NVARCHAR(100),
  FPARENTID     NVARCHAR(32),
  FTABLENAME    NVARCHAR(100),
  FBILLID       NVARCHAR(32),
  FURL          NVARCHAR(1024),
  FCREATEDEPT   NVARCHAR(100),
  FCREATEDEPTID NVARCHAR(100),
  FCREATOR      NVARCHAR(100),
  FCREATORID    NVARCHAR(100),
  FCREATEDATE   DATETIME default GETDATE(),
  VERSION       INTEGER default 0,
  FFID          NVARCHAR(1024),
  FFNAME        NVARCHAR(1024)
)ON [PRIMARY]

alter table [dbo].[OA_PUB_DOCPIGEONHOLE] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

-- Create table
create table [dbo].[OA_PUB_DOCPIGPERM]
(
  FID           NVARCHAR(32) not null,
  FFOLDERID     NVARCHAR(100),
  FPERGORGFID   NVARCHAR(1024),
  FPERGORG      NVARCHAR(1024),
  FJURISDICTION int,
  FCREATORID    NVARCHAR(100),
  FCREATOR      NVARCHAR(100),
  FCREATEDEPTID NVARCHAR(100),
  FCREATEDEPT   NVARCHAR(100),
  FCREATEDATE   DATETIME,
  VERSION       INTEGER
)ON [PRIMARY]

alter table [dbo].[OA_PUB_DOCPIGPERM] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 
	  
-- Create table
create table [dbo].[OA_PULICNOTICE]
(
  FID             NVARCHAR(64) not null,
  VERSION         INTEGER,
  FOGNID          NVARCHAR(64),
  FOGNNAME        NVARCHAR(200),
  FORGID          NVARCHAR(64),
  FORGNAME        NVARCHAR(200),
  FCREATEID       NVARCHAR(64),
  FCREATENAME     NVARCHAR(200),
  FCREATEDATETIME DATETIME,
  FPUSHDATETIME   DATETIME,
  FTITLE          NVARCHAR(200),
  FCONTENT        TEXT,
  FCREATEDATE     DATETIME,
  FPUSHID         NVARCHAR(64),
  FPUSHNAME       NVARCHAR(200),
  FTYPE           NVARCHAR(100),
  FATTFILE        NVARCHAR(400)
)ON [PRIMARY]

alter table [dbo].[OA_PULICNOTICE] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY]

-- Create table
create table [dbo].[OA_PUB_EXECUTE]
(
  FID             NVARCHAR(32) not null,
  FMASTERID       NVARCHAR(100),
  FTASKID         NVARCHAR(100),
  FACTIVITYNAME   NVARCHAR(500),
  FACTIVITYLABEL  NVARCHAR(1000),
  FOPINION        NVARCHAR(1024),
  FSTATE          NVARCHAR(100),
  FSTATENAME      NVARCHAR(100),
  FCREATEPSNID    NVARCHAR(64),
  FCREATEPSNNAME  NVARCHAR(100),
  FCREATEPSNFID   NVARCHAR(1024),
  FCREATEPSNFNAME NVARCHAR(2048),
  FCREATETIME     DATETIME,
  VERSION         INTEGER
)ON [PRIMARY]
 
alter table [dbo].[OA_PUB_EXECUTE] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

-- Create table
create table [dbo].[OA_DOC_REDHEADPERM]
(
  FID           NVARCHAR(32) not null,
  FTYPE         NVARCHAR(100),
  FNAME         NVARCHAR(100),
  FRHFILE       NVARCHAR(1024),
  FOGNID        NVARCHAR(100),
  FOGNNAME      NVARCHAR(100),
  FCREATORID    NVARCHAR(100),
  FCREATOR      NVARCHAR(100),
  FCREATEDEPTID NVARCHAR(100),
  FCREATEDEPT   NVARCHAR(100),
  FCREATEDATE   DATETIME,
  VERSION       INTEGER
)ON [PRIMARY]
 
alter table [dbo].[OA_DOC_REDHEADPERM] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

-- Create table
create table [dbo].[OA_ADM_MYGROUPFROM]
(
  FID           NVARCHAR(32) not null,
  FCREATORID    NVARCHAR(100),
  FCREATOR      NVARCHAR(100),
  FCREATEDEPTID NVARCHAR(100),
  FCREATEDEPT   NVARCHAR(100),
  FCREATEDATE   DATETIME,
  FOUTKEY       NVARCHAR(32),
  FPERSONID     NVARCHAR(32),
  FPERSONNAME   NVARCHAR(200),
  VERSION       INTEGER
)ON [PRIMARY]
alter table [dbo].[OA_ADM_MYGROUPFROM] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 
  
-- Create table
create table [dbo].[OA_ADM_MYGROUPMAIN]
(
  FID           NVARCHAR(32) not null,
  FCREATORID    NVARCHAR(100),
  FCREATOR      NVARCHAR(100),
  FCREATEDEPTID NVARCHAR(100),
  FCREATEDEPT   NVARCHAR(100),
  FCREATEDATE   DATETIME,
  FGROUPNAME    NVARCHAR(200),
  VERSION       INTEGER
)ON [PRIMARY]
alter table [dbo].[OA_ADM_MYGROUPMAIN] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 
GO

-- Create table
create table [dbo].[OA_HR_WARG]
(
  FID           NVARCHAR(32) not null,
  VERSION       INTEGER,
  FCREATORID    NVARCHAR(100),
  FCREATOR      NVARCHAR(100),
  FCREATEDEPTID NVARCHAR(100),
  FCREATEDEPT   NVARCHAR(100),
  FCREATEDATE   DATETIME,
  FSCODE        NVARCHAR(64),
  FPERSONNEME   NVARCHAR(64),
  FPOSTWAGE     FLOAT,
  FLEVEWAGE     FLOAT,
  FBASEJX       FLOAT,
  FREFORM       FLOAT,
  FAWARDJX      FLOAT,
  FAGEWAGE      FLOAT,
  FRETURN       FLOAT,
  FONLYSON      FLOAT,
  FLEAVE        FLOAT,
  FOTHERSUM     FLOAT,
  FSHOULDPAY    FLOAT,
  FDUE          FLOAT,
  FHOUSE        FLOAT,
  FMEDICARE     FLOAT,
  FLOSSCARE     FLOAT,
  FOTHERDIV     FLOAT,
  FLOANDEBIT    FLOAT,
  FTAX          FLOAT,
  FDIVSUM       FLOAT,
  FPAYWAGE      FLOAT,
  FPAYDATE      NVARCHAR(30),
  FWAGEYEAR     FLOAT,
  FWAGEMONTH    FLOAT,
  FJIJIANBUTIE  FLOAT,
  FYEBANFEI     FLOAT,
  FBEIZHU       NVARCHAR(4000)
)ON [PRIMARY]
alter table [dbo].[OA_HR_WARG] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

-- Create table
create table [dbo].[OA_WORK_PLAN_PERSON]
(
  FID             NVARCHAR(36) not null,
  VERSION         INTEGER,
  FOGNID          NVARCHAR(100),
  FOGNNAME        NVARCHAR(200),
  FORGID          NVARCHAR(100),
  FORGNAME        NVARCHAR(200),
  FCREATEID       NVARCHAR(100),
  FCREATENAME     NVARCHAR(200),
  FCREATEDATETIME DATETIME,
  FPERSONID       NVARCHAR(100),
  FPERSONNAME     NVARCHAR(200),
  FREADDATE       DATETIME,
  FPERSONFNAME    NVARCHAR(1024),
  FMAINID         NVARCHAR(100),
  FBROWSE         NVARCHAR(100),
  FOPINION        NVARCHAR(4000)
)ON [PRIMARY]
alter table [dbo].[OA_WORK_PLAN_PERSON] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

-- Create table
create table [dbo].[OA_WP_MONTHPLAN]
(
  FID                    NVARCHAR(100) not null,
  VERSION                INTEGER,
  FOGNID                 NVARCHAR(64),
  FOGNNAME               NVARCHAR(200),
  FORGID                 NVARCHAR(64),
  FORGNAME               NVARCHAR(200),
  FCREATEID              NVARCHAR(64),
  FCREATENAME            NVARCHAR(200),
  FCREATEDATETIME        DATETIME,
  FPUSHDATETIME          DATETIME,
  FPUSHID                NVARCHAR(64),
  FPUSHNAME              NVARCHAR(200),
  FTARGETFINISHCONDITION NVARCHAR(100),
  FUNFINISHEDCAUSE       NVARCHAR(100),
  FWINTHROUGHMETHOD      NVARCHAR(100),
  FINNOVATEHARVEST       NVARCHAR(100),
  FTITLE                 NVARCHAR(200),
  PLANPERSON             NVARCHAR(50)
)ON [PRIMARY]
alter table [dbo].[OA_WP_MONTHPLAN] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

create table [dbo].[OA_WP_MONTHPLAN_DETAIL]
(
  FID                NVARCHAR(100) not null,
  VERSION            INTEGER,
  FOGNID             NVARCHAR(64),
  FOGNNAME           NVARCHAR(200),
  FORGID             NVARCHAR(64),
  FORGNAME           NVARCHAR(200),
  FCREATEID          NVARCHAR(64),
  FCREATENAME        NVARCHAR(200),
  FCREATEDATETIME    DATETIME,
  FSIGNIFICANCEGRADE NVARCHAR(100),
  FTARGETCONTENT     NVARCHAR(200),
  FMETHODMEASURE     NVARCHAR(200),
  FACCOMPLISH        NVARCHAR(100),
  FMAINID            NVARCHAR(100),
  FOPINION           NVARCHAR(100),
  FORDERTIME         DATETIME,
  FCOMPLETE          NVARCHAR(100)
)ON [PRIMARY]
alter table [dbo].[OA_WP_MONTHPLAN_DETAIL] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

create table [dbo].[OA_WP_WEEKPLAN]
(
  FID             NVARCHAR(100) not null,
  VERSION         INTEGER,
  FOGNID          NVARCHAR(64),
  FOGNNAME        NVARCHAR(200),
  FORGID          NVARCHAR(64),
  FORGNAME        NVARCHAR(200),
  FCREATEID       NVARCHAR(64),
  FCREATENAME     NVARCHAR(200),
  FCREATEDATETIME DATETIME,
  FPUSHDATETIME   DATETIME,
  FPUSHID         NVARCHAR(100),
  FPUSHNAME       NVARCHAR(200),
  FTITLE          NVARCHAR(1000),
  STARTTIME       DATETIME,
  FINISHTIME      DATETIME,
  PLANPERSON      NVARCHAR(100),
  FAPPROVALPSNID  NVARCHAR(100),
  FCONTENT        TEXT
)ON [PRIMARY]
alter table [dbo].[OA_WP_WEEKPLAN] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

create table [dbo].[OA_WP_WEEKPLAN_DETAIL]
(
  FID                   NVARCHAR(100) not null,
  VERSION               INTEGER,
  FOGNID                NVARCHAR(64),
  FOGNNAME              NVARCHAR(200),
  FORGID                NVARCHAR(64),
  FORGNAME              NVARCHAR(200),
  FCREATEID             NVARCHAR(64),
  FCREATENAME           NVARCHAR(200),
  FCREATEDATETIME       DATETIME,
  MONTHLY               NVARCHAR(50),
  PROJECTNAME           NVARCHAR(100),
  SERIALNUMBER          NVARCHAR(20),
  JOBCONTENT            NVARCHAR(200),
  DEPARTMENTCOOPERATION NVARCHAR(200),
  COMPLETIONEVALUATION  NVARCHAR(200),
  COMPLETECONDITION     NVARCHAR(50),
  UNFINISHEDCAUSE       NVARCHAR(200),
  AUDITOR               NVARCHAR(20),
  MAINID                NVARCHAR(100),
  STARTTIME             DATETIME,
  FINISHTIME            DATETIME,
  SORT                  INTEGER,
  PLANTYPE              NVARCHAR(20),
  FCONTENT              NVARCHAR(50),
  ACCOMPLISH            NVARCHAR(50),
  FCOMPLETTIME          DATETIME,
  FIMPORTANT            NVARCHAR(100)
)ON [PRIMARY]
alter table [dbo].[OA_WP_WEEKPLAN_DETAIL] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

create table [dbo].[OA_RE_DAYREPORT]
(
  FID                NVARCHAR(64) not null,
  VERSION            INTEGER,
  FTITLE             NVARCHAR(1024),
  FCREATEOGNID       NVARCHAR(64),
  FCREATEOGNNAME     NVARCHAR(200),
  FCREATEDEPTID      NVARCHAR(64),
  FCREATEDEPTNAME    NVARCHAR(200),
  FCREATEPOSID       NVARCHAR(64),
  FCREATEPOSNAME     NVARCHAR(200),
  FCREATEPERSONID    NVARCHAR(64),
  FCREATEPERSONNAME  NVARCHAR(200),
  FCREATEPERSONFID   NVARCHAR(1024),
  FCREATEPERSONFNAME NVARCHAR(2048),
  FCREATETIME        DATETIME,
  FPUSHDATETIME      DATETIME,
  FFILE              NVARCHAR(1000),
  FCONTEXT           text
)ON [PRIMARY]
alter table [dbo].[OA_RE_DAYREPORT] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

create table [dbo].[OA_RE_WEEKREPORT]
(
  FID                NVARCHAR(100) not null,
  VERSION            INTEGER,
  FTITLE             NVARCHAR(1000),
  FCREATEOGNID       NVARCHAR(64),
  FCREATEOGNNAME     NVARCHAR(200),
  FCREATEDEPTID      NVARCHAR(64),
  FCREATEDEPTNAME    NVARCHAR(200),
  FCREATEPOSID       NVARCHAR(64),
  FCREATEPOSNAME     NVARCHAR(200),
  FCREATEPERSONID    NVARCHAR(64),
  FCREATEPERSONNAME  NVARCHAR(200),
  FCREATEPERSONFID   NVARCHAR(1024),
  FCREATEPERSONFNAME NVARCHAR(1024),
  FCREATETIME        DATETIME,
  FPUSHDATETIME      DATETIME,
  FFILE              NVARCHAR(1000),
  FCONTEXT           text
)ON [PRIMARY]
alter table [dbo].[OA_RE_WEEKREPORT] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 

create table [dbo].[OA_RE_MONTHREPORT]
(
  FID                NVARCHAR(100) not null,
  VERSION            INTEGER,
  FTITLE             NVARCHAR(1000),
  FCREATEOGNID       NVARCHAR(64),
  FCREATEOGNNAME     NVARCHAR(200),
  FCREATEDEPTID      NVARCHAR(64),
  FCREATEDEPTNAME    NVARCHAR(200),
  FCREATEPOSID       NVARCHAR(64),
  FCREATEPOSNAME     NVARCHAR(200),
  FCREATEPERSONID    NVARCHAR(64),
  FCREATEPERSONNAME  NVARCHAR(200),
  FCREATEPERSONFID   NVARCHAR(1024),
  FCREATEPERSONFNAME NVARCHAR(1024),
  FCREATETIME        DATETIME,
  FPUSHDATETIME      DATETIME,
  FFILE              NVARCHAR(1000),
  FCONTEXT           text
)ON [PRIMARY]
alter table [dbo].[OA_RE_MONTHREPORT] ADD 
	PRIMARY KEY CLUSTERED (
		 [FID]
	) ON [PRIMARY] 
	
CREATE TABLE [dbo].[Edu_PaymentInfo](
	[fID] [varchar](32) NOT NULL,
	[fEledeId] [varchar](100) NULL,
	[fOrderCode] [varchar](100) NULL,
	[fOrderName] [varchar](100) NULL,
	[fRemark] [varchar](1000) NULL,
	[fState] [int] NULL,
	[fReturnText] [varchar](4000) NULL,
	[VERSION] [int] NULL,
	[Money] [float] NULL,
	[UserId] [varchar](100) NULL,
	[modelId] [int] NULL,
	[UserName] [varchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[fID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
  
-- Add comments to the columns 
EXECUTE sp_addextendedproperty N'MS_Description', N'标题', 'USER', N'dbo', 'TABLE', N'OA_DC_SENDDOC', 'COLUMN', N'FTITLE'
EXECUTE sp_addextendedproperty N'MS_Description', N'发文类型', 'USER', N'dbo', 'TABLE', N'OA_DC_SENDDOC', 'COLUMN', N'FTYPE'
EXECUTE sp_addextendedproperty N'MS_Description', N'文号', 'USER', N'dbo', 'TABLE', N'OA_DC_SENDDOC', 'COLUMN', N'FDOCNUMBER'
EXECUTE sp_addextendedproperty N'MS_Description', N'缓急', 'USER', N'dbo', 'TABLE', N'OA_DC_SENDDOC', 'COLUMN', N'FURGENCY'
EXECUTE sp_addextendedproperty N'MS_Description', N'打印份数', 'USER', N'dbo', 'TABLE', N'OA_DC_SENDDOC', 'COLUMN', N'FPRINTCOUNT'
EXECUTE sp_addextendedproperty N'MS_Description', N'主送', 'USER', N'dbo', 'TABLE', N'OA_DC_SENDDOC', 'COLUMN', N'FMAINDEPT'
EXECUTE sp_addextendedproperty N'MS_Description', N'抄送', 'USER', N'dbo', 'TABLE', N'OA_DC_SENDDOC', 'COLUMN', N'FCOPYDEPT'
EXECUTE sp_addextendedproperty N'MS_Description', N'发送时间', 'USER', N'dbo', 'TABLE', N'OA_DC_SENDDOC', 'COLUMN', N'FSENDDATE'
EXECUTE sp_addextendedproperty N'MS_Description', N'附件', 'USER', N'dbo', 'TABLE', N'OA_DC_SENDDOC', 'COLUMN', N'FATTACHMENT'
EXECUTE sp_addextendedproperty N'MS_Description', N'正文', 'USER', N'dbo', 'TABLE', N'OA_DC_SENDDOC', 'COLUMN', N'FDOCFILE'

EXECUTE sp_addextendedproperty N'MS_Description', N'标题', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FTITLE'
EXECUTE sp_addextendedproperty N'MS_Description', N'类型', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FDOCTYPE'
EXECUTE sp_addextendedproperty N'MS_Description', N'文号', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FDOCNUMBER'
EXECUTE sp_addextendedproperty N'MS_Description', N'缓急', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FURGENCY'
EXECUTE sp_addextendedproperty N'MS_Description', N'份数', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FPAGECOUNT'
EXECUTE sp_addextendedproperty N'MS_Description', N'发文单位', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FDOCSOURCEDEPT'
EXECUTE sp_addextendedproperty N'MS_Description', N'发文日期', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FDOCSOURCEDATE'
EXECUTE sp_addextendedproperty N'MS_Description', N'收文号', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FDOCRECVNO'
EXECUTE sp_addextendedproperty N'MS_Description', N'附件', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FATTACHMENT'
EXECUTE sp_addextendedproperty N'MS_Description', N'收文日期', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FDOCRECVDATE'
EXECUTE sp_addextendedproperty N'MS_Description', N'备注', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FREMARK'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建机构ID', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FCREATEOGNID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建机构名称', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FCREATEOGNNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建部门ID', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FCREATEDEPTID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建部门名称', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FCREATEDEPTNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人岗位ID', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FCREATEPOSID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人岗位', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FCREATEPOSNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人ID', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FCREATEPERSONID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人名称', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FCREATEPERSONNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人FID', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FCREATEPERSONFID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人FNAME', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FCREATEPERSONFNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建时间', 'USER', N'dbo', 'TABLE', N'OA_DC_RECVDOC', 'COLUMN', N'FCREATETIME'

EXECUTE sp_addextendedproperty N'MS_Description', N'审批意见', 'USER', N'dbo', 'TABLE', N'OA_FLOWCONCLUSION', 'COLUMN', N'FCONCLUSIONNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'排序', 'USER', N'dbo', 'TABLE', N'OA_FLOWCONCLUSION', 'COLUMN', N'FORDER'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人ID', 'USER', N'dbo', 'TABLE', N'OA_FLOWCONCLUSION', 'COLUMN', N'FCREATORID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人', 'USER', N'dbo', 'TABLE', N'OA_FLOWCONCLUSION', 'COLUMN', N'FCREATOR'

EXECUTE sp_addextendedproperty N'MS_Description', N'环节名称', 'USER', N'dbo', 'TABLE', N'OA_FLOWRECORD', 'COLUMN', N'FNODENAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'审批意见', 'USER', N'dbo', 'TABLE', N'OA_FLOWRECORD', 'COLUMN', N'FAGREETEXT'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人', 'USER', N'dbo', 'TABLE', N'OA_FLOWRECORD', 'COLUMN', N'FCREATEPERNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建部门', 'USER', N'dbo', 'TABLE', N'OA_FLOWRECORD', 'COLUMN', N'FCREATEDEPTNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建时间', 'USER', N'dbo', 'TABLE', N'OA_FLOWRECORD', 'COLUMN', N'FCREATETIME'
EXECUTE sp_addextendedproperty N'MS_Description', N'业务ID', 'USER', N'dbo', 'TABLE', N'OA_FLOWRECORD', 'COLUMN', N'FBILLID'
EXECUTE sp_addextendedproperty N'MS_Description', N'部门ID', 'USER', N'dbo', 'TABLE', N'OA_FLOWRECORD', 'COLUMN', N'FCREATEDEPTID'
EXECUTE sp_addextendedproperty N'MS_Description', N'人员ID', 'USER', N'dbo', 'TABLE', N'OA_FLOWRECORD', 'COLUMN', N'FCREATEPERID'
EXECUTE sp_addextendedproperty N'MS_Description', N'环节标识', 'USER', N'dbo', 'TABLE', N'OA_FLOWRECORD', 'COLUMN', N'FNODEID'
EXECUTE sp_addextendedproperty N'MS_Description', N'显示位置div id', 'USER', N'dbo', 'TABLE', N'OA_FLOWRECORD', 'COLUMN', N'FOPVIEWID'

EXECUTE sp_addextendedproperty N'MS_Description', N'通知公告', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', null, null
EXECUTE sp_addextendedproperty N'MS_Description', N'通知公告类型', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FNOTICETYPE'
EXECUTE sp_addextendedproperty N'MS_Description', N'发布方式', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FSENDTYPE'
EXECUTE sp_addextendedproperty N'MS_Description', N'发布范围', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FPOSTEDRANGE'
EXECUTE sp_addextendedproperty N'MS_Description', N'标题', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FTITLE'
EXECUTE sp_addextendedproperty N'MS_Description', N'发布时间', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FSENDDATE'
EXECUTE sp_addextendedproperty N'MS_Description', N'内容', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FCONTENT'
EXECUTE sp_addextendedproperty N'MS_Description', N'附件', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FATTACHMENT'
EXECUTE sp_addextendedproperty N'MS_Description', N'发布状态:0未发布；1发布', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FSENDSTATUS'
EXECUTE sp_addextendedproperty N'MS_Description', N'科室审核意见', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FDEPTCHECK'
EXECUTE sp_addextendedproperty N'MS_Description', N'院务部审核意见', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FOFFICECHECK'
EXECUTE sp_addextendedproperty N'MS_Description', N'分管领导审核意见', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FLEADERCHECK'
EXECUTE sp_addextendedproperty N'MS_Description', N'发布范围ID', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGE', 'COLUMN', N'FPOSTEDRANGEID'

EXECUTE sp_addextendedproperty N'MS_Description', N'主键', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGERANGE', 'COLUMN', N'FID'
EXECUTE sp_addextendedproperty N'MS_Description', N'业务主键', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGERANGE', 'COLUMN', N'FBILLID'
EXECUTE sp_addextendedproperty N'MS_Description', N'发送FID', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGERANGE', 'COLUMN', N'FFULLID'
EXECUTE sp_addextendedproperty N'MS_Description', N'发送全名', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGERANGE', 'COLUMN', N'FFULLNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人ID', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGERANGE', 'COLUMN', N'FCREATORID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGERANGE', 'COLUMN', N'FCREATOR'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建部门ID', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGERANGE', 'COLUMN', N'FCREATEDEPTID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建部门', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGERANGE', 'COLUMN', N'FCREATEDEPT'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建时间', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGERANGE', 'COLUMN', N'FCREATEDATE'
EXECUTE sp_addextendedproperty N'MS_Description', N'名称', 'USER', N'dbo', 'TABLE', N'OA_KM_KNOWLEDGERANGE', 'COLUMN', N'FNAME'

EXECUTE sp_addextendedproperty N'MS_Description', N'归档目录权限', 'USER', N'dbo', 'TABLE', N'OA_PUB_DOCPIGPERM', null, null
EXECUTE sp_addextendedproperty N'MS_Description', N'目录ID', 'USER', N'dbo', 'TABLE', N'OA_PUB_DOCPIGPERM', 'COLUMN', N'FFOLDERID'
EXECUTE sp_addextendedproperty N'MS_Description', N'指定orgfid', 'USER', N'dbo', 'TABLE', N'OA_PUB_DOCPIGPERM', 'COLUMN', N'FPERGORGFID'
EXECUTE sp_addextendedproperty N'MS_Description', N'指定org', 'USER', N'dbo', 'TABLE', N'OA_PUB_DOCPIGPERM', 'COLUMN', N'FPERGORG'
EXECUTE sp_addextendedproperty N'MS_Description', N'权限', 'USER', N'dbo', 'TABLE', N'OA_PUB_DOCPIGPERM', 'COLUMN', N'FJURISDICTION'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人ID', 'USER', N'dbo', 'TABLE', N'OA_PUB_DOCPIGPERM', 'COLUMN', N'FCREATORID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人', 'USER', N'dbo', 'TABLE', N'OA_PUB_DOCPIGPERM', 'COLUMN', N'FCREATOR'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建部门ID', 'USER', N'dbo', 'TABLE', N'OA_PUB_DOCPIGPERM', 'COLUMN', N'FCREATEDEPTID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建部门', 'USER', N'dbo', 'TABLE', N'OA_PUB_DOCPIGPERM', 'COLUMN', N'FCREATEDEPT'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建时间', 'USER', N'dbo', 'TABLE', N'OA_PUB_DOCPIGPERM', 'COLUMN', N'FCREATEDATE'

EXECUTE sp_addextendedproperty N'MS_Description', N'业务id' , 'USER',N'dbo', 'TABLE',N'Edu_PaymentInfo', 'COLUMN',N'fEledeId'
EXECUTE sp_addextendedproperty N'MS_Description', N'单号' , 'USER',N'dbo', 'TABLE',N'Edu_PaymentInfo', 'COLUMN',N'fOrderCode'
EXECUTE sp_addextendedproperty N'MS_Description', N'订单名称' , 'USER',N'dbo', 'TABLE',N'Edu_PaymentInfo', 'COLUMN',N'fOrderName'
EXECUTE sp_addextendedproperty N'MS_Description', N'订单描述' , 'USER',N'dbo', 'TABLE',N'Edu_PaymentInfo', 'COLUMN',N'fRemark'
EXECUTE sp_addextendedproperty N'MS_Description', N'状态0未支付1已支付' , 'USER',N'dbo', 'TABLE',N'Edu_PaymentInfo', 'COLUMN',N'fState'
EXECUTE sp_addextendedproperty N'MS_Description', N'返回信息' , 'USER',N'dbo', 'TABLE',N'Edu_PaymentInfo', 'COLUMN',N'fReturnText'
EXECUTE sp_addextendedproperty N'MS_Description', N'用户ID' , 'USER',N'dbo', 'TABLE',N'Edu_PaymentInfo', 'COLUMN',N'UserId'
EXECUTE sp_addextendedproperty N'MS_Description', N'类型' , 'USER',N'dbo', 'TABLE',N'Edu_PaymentInfo', 'COLUMN',N'modelId'
EXECUTE sp_addextendedproperty N'MS_Description', N'用户名称' , 'USER',N'dbo', 'TABLE',N'Edu_PaymentInfo', 'COLUMN',N'UserName'
EXECUTE sp_addextendedproperty N'MS_Description', N'支付信息' , 'USER',N'dbo', 'TABLE',N'Edu_PaymentInfo'
  
GO

create VIEW oa_notice_person_view as
select t."FID",t."VERSION",t."FOGNID",t."FOGNNAME",t."FORGID",t."FORGNAME",t."FCREATEID",t."FCREATENAME",t."FCREATEDATETIME",t."FPUSHDATETIME",t."FTITLE",t."FCONTENT",t."FCREATEDATE",t."FPUSHID",t."FPUSHNAME",t."FTYPE",t1.fpersonid fpersonid,t1.fbrowse
from OA_PULICNOTICE t
left join OA_NOTICE_PERSON  t1 on t.fid=t1.fmainid
GO

create view look_week_work_plan as
select t.fid,t.FPUSHDATETIME,t.FPUSHID,t.FPUSHNAME,t.FCREATEID,t.FTITLE,t.FCREATENAME,t.FCONTENT,t.STARTTIME,
t.finishtime,t.forgname,t1.fpersonname,t1.fpersonid,t1.fbrowse,t.version 
from OA_WP_WEEKPLAN t
left join Oa_Work_Plan_Person t1 on t.fid=t1.fmainid
GO

create view look_month_work_plan as
select t.fid,
       t.FPUSHDATETIME,
       t.FPUSHID,
       t.FPUSHNAME,
       t.FCREATEID,
       t.FTITLE,
       t.FCREATENAME,
       t.forgid,
       t.forgname,
       t1.fpersonname,
       t.FCREATEDATETIME,
       t1.fpersonid,
       t1.fbrowse,
       t.version
  from OA_WP_MONTHPLAN t
  left join Oa_Work_Plan_Person t1
    on t.fid = t1.fmainid
GO
    
 create view show_oa_re_dayreport as
select t.FID,
       t.VERSION,
       t.FTITLE,
       t.FCREATEOGNID,
       t.FCREATEOGNNAME,
       t.FCREATEDEPTID,
       t.FCREATEDEPTNAME,
       t.FCREATEPOSID,
       t.FCREATEPOSNAME,
       t.FCREATEPERSONID,
       t.FCREATEPERSONNAME,
       t.FCREATEPERSONFID,
       t.FCREATEPERSONFNAME,
       t.FCREATETIME,
       t.FPUSHDATETIME,
       t.FFILE,
       t1.fpersonid fpersonid,
       t1.fbrowse
  from OA_RE_DAYREPORT t
  left join OA_NOTICE_PERSON t1
    on t.fid = t1.fmainid
GO
    
create view show_oa_re_weekreport as
select t.FID,
       t.VERSION,
       t.FTITLE,
       t.FCREATEOGNID,
       t.FCREATEOGNNAME,
       t.FCREATEDEPTID,
       t.FCREATEDEPTNAME,
       t.FCREATEPOSID,
       t.FCREATEPOSNAME,
       t.FCREATEPERSONID,
       t.FCREATEPERSONNAME,
       t.FCREATEPERSONFID,
       t.FCREATEPERSONFNAME,
       t.FCREATETIME,
       t.FPUSHDATETIME,
       t.FFILE,
       t1.fpersonid fpersonid,
       t1.fbrowse
  from OA_RE_WEEKREPORT t
  left join OA_NOTICE_PERSON t1
    on t.fid = t1.fmainid
GO
    
create view show_oa_re_monthreport as
select t.FID,
       t.VERSION,
       t.FTITLE,
       t.FCREATEOGNID,
       t.FCREATEOGNNAME,
       t.FCREATEDEPTID,
       t.FCREATEDEPTNAME,
       t.FCREATEPOSID,
       t.FCREATEPOSNAME,
       t.FCREATEPERSONID,
       t.FCREATEPERSONNAME,
       t.FCREATEPERSONFID,
       t.FCREATEPERSONFNAME,
       t.FCREATETIME,
       t.FPUSHDATETIME,
       t.FFILE,
       t1.fpersonid fpersonid,
       t1.fbrowse
  from OA_RE_MONTHREPORT t
  left join OA_NOTICE_PERSON t1
    on t.fid = t1.fmainid
GO