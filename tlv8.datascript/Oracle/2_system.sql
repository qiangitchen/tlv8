CREATE TABLE SA_ONLINEINFO (
  SID varchar2(32) NOT NULL,
  SUSERID varchar2(36),
  SUSERNAME varchar2(100),
  SUSERFID varchar2(1024),
  SUSERFNAME varchar2(1024),
  SLOGINIP varchar2(100),
  SLOGINDATE timestamp,
  SSESSIONID varchar2(100),
  SSERVICEIP varchar2(100),
  SMACHINECODE varchar2(100),
  VERSION int
);
alter table SA_ONLINEINFO add primary key (SID);
alter table SA_ONLINEINFO add unique (SSESSIONID);
alter table SA_ONLINEINFO add unique (SSERVICEIP);

create table SA_PortalProfiles
(
  sID       VARCHAR2(100) not null,
  version   NUMBER,
  sName     VARCHAR2(100),
  sPersonID VARCHAR2(100),
  sValue    VARCHAR2(4000)
)
;
alter table SA_PortalProfiles add primary key (sID);

create table SA_Code
(
  sID     VARCHAR2(32) not null,
  sType   VARCHAR2(64),
  sCode   VARCHAR2(64) not null,
  sName   VARCHAR2(64),
  version INTEGER not null
);

alter table SA_Code add constraint PK_SA_CODE primary key (sID);

create table SA_KVSequence
(
  K VARCHAR2(255) not null,
  V INTEGER not null
);
alter table SA_KVSequence add constraint PK_KVSEQUENCE primary key (K);

create table SA_OPAgent
(
  sID         VARCHAR2(32) not null,
  sOrgFID     VARCHAR2(2048) not null,
  sOrgFName   VARCHAR2(2048) not null,
  sAgentID    VARCHAR2(32) not null,
  sValidState INTEGER,
  sStartTime  DATE,
  sFinishTime DATE,
  sProcess	  CLOB,
  sCreatorID  VARCHAR2(32),
  sCreatorName VARCHAR2(64),
  sCreateTime DATE,
  sCanTranAgent INTEGER,
  version     INTEGER not null
);

alter table SA_OPAgent add constraint PK_OPAGENT primary key (sID);

CREATE TABLE sa_opauthorize (
  SID varchar2(100) NOT NULL,
  SORGID varchar2(65),
  SORGNAME varchar2(255),
  SORGFID varchar2(2048),
  SORGFNAME varchar2(2048),
  SAUTHORIZEROLEID varchar2(32),
  SDESCRIPTION varchar2(1024),
  SCREATORFID varchar2(2048),
  SCREATORFNAME varchar2(2048),
  SCREATETIME date,
  VERSION int,
  SROLELEVEL varchar2(100)
);
alter table sa_opauthorize add primary key (sID);
create index IDX_OPAUTH_ROLEID on sa_opauthorize(SAUTHORIZEROLEID);

create table SA_OPManagement
(
  sID             VARCHAR2(32) not null,
  sOrgID          VARCHAR2(65) not null,
  sOrgName        VARCHAR2(255),
  sOrgFID         VARCHAR2(2048) not null,
  sOrgFName       VARCHAR2(2048),
  sManageOrgID    VARCHAR2(65) not null,
  sManageOrgName  VARCHAR2(255),
  sManageOrgFID   VARCHAR2(2048) not null,
  sManageOrgFName VARCHAR2(2048),
  sManageTypeID   VARCHAR2(32) not null,
  sCreatorFID     VARCHAR2(2048),
  sCreatorFName   VARCHAR2(2048),
  sCreateTime     DATE,
  version         INTEGER not null
);

alter table SA_OPManagement
  add constraint PK_OPMANAGEMENT primary key (sID);
  
create table SA_OPManageType
(
  sID                VARCHAR2(32) not null,
  sName              VARCHAR2(255),
  sCode              VARCHAR2(32),
  sManageOrgKind     VARCHAR2(64),
  sManageOrgKindName VARCHAR2(255),
  sLeaderNumber      INTEGER,
  sIsSystem          INTEGER,
  version            INTEGER not null
);
alter table SA_OPManageType
  add constraint PK_OPMANAGETYPE primary key (sID);
  
CREATE TABLE sa_oporg (
  SID varchar2(100) NOT NULL,
  SNAME varchar2(128) NOT NULL,
  SCODE varchar2(64) NOT NULL,
  SLONGNAME varchar2(255),
  SFNAME varchar2(1024),
  SFCODE varchar2(2048),
  SFID varchar2(1024),
  SORGKINDID varchar2(5),
  SVALIDSTATE int,
  SPARENT varchar2(100),
  SLEVEL int,
  SPHONE varchar2(64),
  SFAX varchar2(64),
  SADDRESS varchar2(255),
  SZIP varchar2(16),
  SDESCRIPTION varchar2(1024),
  SPERSONID varchar2(32),
  SNODEKIND varchar2(32),
  VERSION int,
  SSHOWNAME varchar2(100),
  SSEQUENCE int DEFAULT 1
);
alter table sa_oporg add primary key (sID);
create index SA_OPORG_SFID on sa_oporg(SFID);
create index IDX_OPORG_CODE on sa_oporg(SCODE);
create index IDX_SA_OPORG_SORGKINDID on sa_oporg(SORGKINDID);
create index IDX_SA_OPORG_SPARENT on sa_oporg(SPARENT);
create index IDX_SA_OPORG_SPERSONID on sa_oporg(SPERSONID);

create table SA_OPParentRole
(
  sID         VARCHAR2(32) not null,
  sRoleID     VARCHAR2(32) not null,
  sParentRoleID VARCHAR2(32) not null,
  version     INTEGER not null
);
alter table SA_OPParentRole
  add constraint PK_OPROLEROLE primary key (sID);
  
create table SA_OPPermission
(
  sID               VARCHAR2(32) not null,
  sPermissionRoleID VARCHAR2(32) not null,
  sProcess          VARCHAR2(1024),
  sActivityFName    VARCHAR2(1024),
  sActivity         VARCHAR2(1024),
  SACTIONSNAMES     CLOB,
  sActions         	CLOB,
  sSemanticDP       VARCHAR2(2048),
  sPermissionKind   INTEGER,
  sDescription      VARCHAR2(1024),
  sSequence         INTEGER,
  sValidState       INTEGER,
  version           INTEGER not null
);
alter table SA_OPPermission
  add constraint PK_OPPERMISSION primary key (sID);
create index IDX_OPPERM_ACTIVITY on SA_OPPermission (sActivity);
create index IDX_OPPERM_PROCESS on SA_OPPermission (sProcess);
create index IDX_OPPERM_ROLEID on SA_OPPermission (sPermissionRoleID);

create table SA_OPRole
(
  sID          VARCHAR2(32) not null,
  sName        VARCHAR2(255),
  sCode        VARCHAR2(32),
  sCatalog     VARCHAR2(128),
  sRoleKind    VARCHAR2(32),
  sParentRolesNames VARCHAR2(512),
  sDescription VARCHAR2(1024),
  sSequence    INTEGER,
  sValidState  INTEGER,
  version      INTEGER not null
);
alter table SA_OPRole
  add constraint PK_OPROLE primary key (sID);
create index IDX_OPROLE_CODE on SA_OPRole (sCode);

create table SA_OPRoleManagement
(
  sID           VARCHAR2(32) not null,
  sOrgID        VARCHAR2(65) not null,
  sOrgName      VARCHAR2(255),
  sOrgFID       VARCHAR2(2048) not null,
  sOrgFName     VARCHAR2(2048),
  sRoleID       VARCHAR2(32) not null,
  sCreatorFID    VARCHAR2(2048),
  sCreatorFName VARCHAR2(2048),
  sCreateTime   DATE,
  version       INTEGER not null
);
alter table SA_OPRoleManagement
  add constraint PK_OPROLEMANAGE primary key (sID);
  
create table SA_TaskType
(
  sID              VARCHAR2(50) not null,
  sName            VARCHAR2(500),
  sConcept         VARCHAR2(500),
  sNewActivity     VARCHAR2(1024),
  sExecuteActivity VARCHAR2(1024),
  sKind            VARCHAR2(500),
  version          INTEGER,
  sProcess         VARCHAR2(1024),
  sActivity        VARCHAR2(1024)
);
alter table SA_TaskType
  add constraint PK_TASKTYPE primary key (sID);
  
CREATE TABLE sa_opperson (
  SID varchar2(36) NOT NULL,
  SNAME varchar2(64) NOT NULL,
  SCODE varchar2(64) NOT NULL,
  SIDCARD varchar2(36),
  SNUMB int NOT NULL,
  SLOGINNAME varchar2(64),
  SPASSWORD varchar2(64),
  SPASSWORDTIMELIMIT int,
  SPASSWORDMODIFYTIME date,
  SMAINORGID varchar2(36) NOT NULL,
  SSAFELEVELID varchar2(36),
  SSEQUENCE int,
  SVALIDSTATE int,
  SDESCRIPTION varchar2(2048),
  SSEX varchar2(8),
  SBIRTHDAY date,
  SJOINDATE date,
  SHOMEPLACE varchar2(64),
  SDEGREE varchar2(16),
  SGRADUATESCHOOL varchar2(128),
  SSPECIALITY varchar2(128),
  SSCHOOLLENGTH varchar2(16),
  STITLE varchar2(64),
  SMARRIAGE varchar2(16),
  SCARDNO varchar2(36),
  SCARDKIND varchar2(64),
  SFAMILYADDRESS varchar2(255),
  SZIP varchar2(16),
  SMSN varchar2(64),
  SQQ varchar2(36),
  SMAIL varchar2(64),
  SMOBILEPHONE varchar2(36),
  SFAMILYPHONE varchar2(36),
  SOFFICEPHONE varchar2(36),
  VERSION int,
  SPHOTO clob,
  SCOUNTRY varchar2(64),
  SPROVINCE varchar2(64),
  SCITY varchar2(64),
  SPOSITIONS varchar2(64),
  SSCHOOL varchar2(64),
  SSTUDY varchar2(64),
  SENGLISHNAME varchar2(128),
  FCASN varchar2(100),
  FSIGNM varchar2(100)
);
alter table sa_opperson add primary key (sID);
create index IDX_OPPERSON_SCODE on sa_opperson(SCODE);
create index IDX_OPPERSON_SMAINORGID on sa_opperson(SMAINORGID);
create index XMSGINDEXMESSAGESVALIDSTATE on sa_opperson(SVALIDSTATE);

CREATE TABLE sa_task (
  SID varchar2(36) NOT NULL,
  SPARENTID varchar2(36) ,
  SNAME varchar2(255) ,
  SCONTENT clob,
  SREMARK clob,
  SFLOWID varchar2(36) ,
  STYPEID varchar2(36) ,
  STYPENAME varchar2(64) ,
  SIMPORTANCEID varchar2(36) ,
  SIMPORTANCENAME varchar2(64) ,
  SEMERGENCYID varchar2(36) ,
  SEMERGENCYNAME varchar2(64) ,
  SPROCESS varchar2(255) ,
  SACTIVITY varchar2(255) ,
  SCREATETIME date ,
  SDISTRIBUTETIME date ,
  SLASTMODIFYTIME date ,
  SWARNINGTIME date ,
  SLIMITTIME date ,
  SESTARTTIME date ,
  SEFINISHTIME date ,
  SASTARTTIME date ,
  SAFINISHTIME date ,
  SEXECUTETIME date ,
  SCPERSONID varchar2(36) ,
  SCPERSONNAME varchar2(255) ,
  SCDEPTID varchar2(36) ,
  SCDEPTNAME varchar2(255) ,
  SCOGNID varchar2(36) ,
  SCOGNNAME varchar2(255) ,
  SEPERSONID varchar2(36) ,
  SEPERSONNAME varchar2(255) ,
  SEDEPTID varchar2(36) ,
  SEDEPTNAME varchar2(255) ,
  SEOGNID varchar2(36) ,
  SEOGNNAME varchar2(255) ,
  SCUSTOMERID varchar2(64) ,
  SCUSTOMERNAME varchar2(255) ,
  SPROJECTID varchar2(64) ,
  SPROJECTNAME varchar2(255) ,
  SPLANID varchar2(64) ,
  SPLANNAME varchar2(255) ,
  SVARIABLE clob,
  SFAKE varchar2(8) ,
  SACTIVE varchar2(8) ,
  SLOCK varchar2(36) ,
  SSTATUSID varchar2(36) ,
  SSTATUSNAME varchar2(64) ,
  VERSION int ,
  SAIID varchar2(36) ,
  SCATALOGID varchar2(36) ,
  SKINDID varchar2(36) ,
  SAIACTIVE varchar2(8) ,
  SAISTATUSID varchar2(255) ,
  SAISTATUSNAME varchar2(255) ,
  SSOURCEID varchar2(36) ,
  SCURL varchar2(255) ,
  SEURL varchar2(255) ,
  SEXECUTEMODE varchar2(32) ,
  SEXECUTEMODE2 varchar2(255) ,
  SPREEMPTMODE varchar2(32) ,
  SSEQUENCE int ,
  SCPERSONCODE varchar2(64) ,
  SCPOSID varchar2(36) ,
  SCPOSCODE varchar2(64) ,
  SCPOSNAME varchar2(255) ,
  SCDEPTCODE varchar2(64) ,
  SCOGNCODE varchar2(64) ,
  SCFID varchar2(1024) ,
  SEPERSONCODE varchar2(64) ,
  SEPOSID varchar2(36) ,
  SEPOSCODE varchar2(64) ,
  SEPOSNAME varchar2(255) ,
  SEDEPTCODE varchar2(64) ,
  SEOGNCODE varchar2(64) ,
  SEFID varchar2(1024) ,
  SEXECUTORNAMES varchar2(255) ,
  SRESPONSIBLE varchar2(8) ,
  SCUSTOMERCODE varchar2(64) ,
  SPROJECTCODE varchar2(64) ,
  SPLANCODE varchar2(64) ,
  SDATA1 varchar2(128) ,
  SDATA2 varchar2(128) ,
  SDATA3 varchar2(128) ,
  SDATA4 varchar2(128) ,
  SWORKTIME int ,
  SCFNAME varchar2(255) ,
  SEFNAME varchar2(255) ,
  SHINTS varchar2(1024) ,
  SSHORTCUT varchar2(1024) ,
  SFRONTID varchar2(36) ,
  SFMAKERNAME varchar2(100)
);
alter table sa_task add constraint PK_sa_task primary key (sID);
create index sa_task_SFLOWID on sa_task (SFLOWID);
create index SA_STATUS on sa_task (SSTATUSID);
create index SA_TASK_SPROCESS on sa_task (SPROCESS);
create index SA_TASK_SDATA_1 on sa_task (SDATA1);
create index SA_TASK_SPARENTID on sa_task (SPARENTID);
create index IDX_TASK_SCPERSONID on SA_Task (SCPERSONID);
create index IDX_TASK_SEPERSONID on SA_Task (SEPERSONID);

CREATE TABLE sa_log (
  SID varchar2(32) NOT NULL,
  STYPENAME varchar2(100) ,
  SDESCRIPTION clob,
  SPROCESS varchar2(2048) ,
  SPROCESSNAME varchar2(2048) ,
  SACTIVITY varchar2(100) ,
  SACTIVITYNAME varchar2(2048) ,
  SACTION varchar2(100) ,
  SACTIONNAME varchar2(2048) ,
  SSTATUSNAME varchar2(64) ,
  SCREATETIME date NULL ,
  SCREATORFID varchar2(2048) ,
  SCREATORFNAME varchar2(2048) ,
  SCREATORPERSONID varchar2(36) ,
  SCREATORPERSONNAME varchar2(255) ,
  SCREATORPOSID varchar2(36) ,
  SCREATORPOSNAME varchar2(255) ,
  SCREATORDEPTID varchar2(36) ,
  SCREATORDEPTNAME varchar2(255) ,
  SCREATOROGNID varchar2(36) ,
  SCREATOROGNNAME varchar2(255) ,
  SIP varchar2(32) ,
  VERSION decimal(10,0) ,
  SDEVICETYPE varchar2(100) ,
  SOPERATINGSYSTEM varchar2(100) 
);
alter table sa_log add primary key (sID);

CREATE TABLE sa_loginlog (
  SID varchar2(32) NOT NULL,
  SUSERID varchar2(36) ,
  SUSERNAME varchar2(100) ,
  SLOGINIP varchar2(100) ,
  SLOGINTIME date ,
  PASSWORD varchar2(100) ,
  VERSION int ,
  SSERVICEIP varchar2(100) ,
  SDAY varchar2(100) ,
  SDAYNUM int
); 
alter table sa_loginlog add primary key (sID);

CREATE TABLE sa_msnalert(
  SID varchar2(32) NOT NULL,
  SNAME varchar2(100),
  SURL varchar2(1024),
  SMESSAGE varchar2(2048),
  SSTATE int DEFAULT 0 NOT NULL,
  SBILLID varchar2(36),
  SBILTABLE varchar2(100),
  SRPERSONID varchar2(100),
  SSPERSONID varchar2(100),
  SSDATE date,
  SRDATE date,
  VERSION int DEFAULT 0,
  SMURL varchar2(1024),
  SMSTATE int DEFAULT 0,
  SOPIONID varchar2(36) DEFAULT ''
);
alter table sa_msnalert add primary key (sID);
create index SA_MSNALERT_SRPERSONID on sa_msnalert (SRPERSONID);
create index SA_MSNALERT_SOPIONID on sa_msnalert (SOPIONID);
create index SA_MSNALERT_SSDATE on sa_msnalert (SSDATE);
create index SA_MSNALERT_SBILLID on sa_msnalert (SBILLID);
create index SA_MSNALERT_SSTATE on sa_msnalert (SSTATE);

CREATE TABLE sa_online_log (
  SID varchar2(32) NOT NULL,
  SUSERID varchar2(36),
  SUSERNAME varchar2(100),
  SUSERFID varchar2(1024),
  SUSERFNAME varchar2(1024),
  SLOGINIP varchar2(100),
  SLOGINDATE date,
  SLOGOUTDATE date,
  SSESSIONID varchar2(100),
  SSERVICEIP varchar2(100),
  VERSION int,
  SDAY varchar2(100),
  SDAYNUM int
);
alter table sa_online_log add constraint PK_sa_online_log primary key (sID);
create index SA_PNLINE_LOG_SLOGINDATE on sa_online_log(SLOGINDATE);
create index SA_ONLINE_LOG_SSERVICEIP on sa_online_log(SSERVICEIP);
create index SA_ONLINE_LOG_SSESSIONID on sa_online_log(SSESSIONID);

CREATE TABLE sa_opperson_deatail (
  SID varchar2(36) NOT NULL,
  SPERSONID varchar2(36),
  STYPE varchar2(500),
  SSQUNS varchar2(500),
  SCODE varchar2(500),
  SNAME varchar2(500),
  SREMARK varchar2(500),
  SCLASS varchar2(500),
  SUPDATEDATE date,
  SUPDATORID varchar2(36),
  SUPDATORNAME varchar2(255),
  VERSION int
);
alter table sa_opperson_deatail add constraint PK_sa_opperson_deatail primary key (sID);
create index sa_opperson_deatail_psmid on sa_opperson_deatail(SPERSONID);

create table SA_PORTALLETS
(
  SPERSONID    VARCHAR2(100),
  SLAYOUTSET   VARCHAR2(1024),
  SPANLES      clob,
  SCREATORID   VARCHAR2(100),
  SCREATORNAME VARCHAR2(100),
  SCREATEDATE  DATE
);
alter table SA_PORTALLETS add primary key (SPERSONID);

CREATE TABLE sa_task_timelimit(
  sID varchar2(32) NOT NULL,
  SPROCESSID varchar2(255),
  SPROCESSNAME varchar2(512),
  SACTIVITY varchar2(255),
  SACTIVITYNAME varchar2(512),
  SDLIMIT int,
  VERSION int
);
alter table sa_task_timelimit add primary key (sID);

CREATE TABLE sa_worklog (
  SID varchar2(32) NOT NULL,
  SLOCK varchar2(100),
  SNAME varchar2(100),
  SCUSTOMERNAME varchar2(100),
  SIMPORTANCENAME varchar2(100),
  SPLANNAME varchar2(100),
  SPROJECTNAME varchar2(100),
  SEMERGENCYNAME varchar2(100),
  SLIMITTIME date,
  SCONTENT clob,
  SCREATORFID varchar2(1024),
  SCREATOFNAME varchar2(1024),
  SCREATETIME date,
  FEXTEND01 varchar2(100),
  VERSION int
);
alter table sa_worklog add primary key (sID);

CREATE TABLE sa_flowfolder (
  SID varchar2(32) NOT NULL,
  SPROCESSID varchar2(100),
  SPROCESSNAME varchar2(100),
  SCODE varchar2(100),
  SNAME varchar2(100),
  SPARENT varchar2(32),
  SIDPATH varchar2(4000),
  SNAMEPATH varchar2(4000),
  SCODEPATH varchar2(4000),
  VERSION int DEFAULT 0 NOT NULL
);
alter table sa_flowfolder add primary key (sID);
create index SOU_SA_FLWFOLDER_SPARENT on sa_flowfolder(SPARENT);

CREATE TABLE sa_flowdrawlg (
  SID varchar2(32) NOT NULL,
  SPROCESSID varchar2(100) NOT NULL,
  SPROCESSNAME varchar2(100),
  SDRAWLG clob,
  VERSION int DEFAULT 0,
  SPROCESSACTY clob,
  SCREATORID varchar2(100),
  SCREATORNAME varchar2(100),
  SUPDATORID varchar2(100),
  SUPDATORNAME varchar2(100),
  SCREATETIME date,
  SUPDATETIME date,
  FENABLED int DEFAULT 1,
  SFOLDERID varchar2(32)
);
alter table sa_flowdrawlg add primary key (sID);
create unique index SA_FLOWDRAWLG_SPROCESSID on sa_flowdrawlg(SPROCESSID);

comment on column sa_flowdrawlg.SID is '主键';
comment on column sa_flowdrawlg.SPROCESSID is '流程标识';
comment on column sa_flowdrawlg.SPROCESSNAME is '流程名称';
comment on column sa_flowdrawlg.SDRAWLG is '流程图';
comment on column sa_flowdrawlg.VERSION is '版本号';
comment on column sa_flowdrawlg.SPROCESSACTY is '流程环节';
comment on column sa_flowdrawlg.SCREATORID is '创建人ID';
comment on column sa_flowdrawlg.SCREATORNAME is '创建人名称';
comment on column sa_flowdrawlg.SUPDATORID is '修改人ID';
comment on column sa_flowdrawlg.SUPDATORNAME is '修改人名称';
comment on column sa_flowdrawlg.SCREATETIME is '创建时间';
comment on column sa_flowdrawlg.SUPDATETIME is '修改时间';
comment on column sa_flowdrawlg.FENABLED is '启用状态';
comment on column sa_flowdrawlg.SFOLDERID is '目录ID';

CREATE TABLE sa_flowdrawlg_ipo (
  SID varchar2(32) NOT NULL,
  SPROCESSID varchar2(100) NOT NULL,
  SPROCESSNAME varchar2(100),
  SDRAWLG clob,
  VERSION int DEFAULT 0,
  SPROCESSACTY clob,
  SCREATORID varchar2(100),
  SCREATORNAME varchar2(100),
  SUPDATORID varchar2(100),
  SUPDATORNAME varchar2(100),
  SCREATETIME date,
  SUPDATETIME date,
  FENABLED int DEFAULT 1
);
alter table sa_flowdrawlg_ipo add primary key (sID);

CREATE TABLE sa_flowinfo (
  ID varchar2(32) NOT NULL,
  STOACTIVITY varchar2(500),
  STOOPERATORID varchar2(4000),
  VERSION int DEFAULT 0,
  SFROMACTIVITYINSTANCEID varchar2(500),
  STASKNAME varchar2(500),
  STASKCONTENT varchar2(500),
  STASKCREATETIME date,
  STASKEMERGENCYID number,
  STASKWARNINGTIME date,
  STASKLIMITTIME date
);
alter table sa_flowinfo add primary key (ID);

CREATE TABLE sa_flowtrace (
  SID varchar2(36) NOT NULL,
  SOPERATORID varchar2(36),
  SOPERATORCODE varchar2(100),
  SOPERATORNAME varchar2(100),
  SCURL varchar2(1024),
  SEURL varchar2(1024),
  SCHECKPSN varchar2(4000),
  VERSION int DEFAULT 0 NOT NULL
);
alter table sa_flowtrace add primary key (sID);
create index SA_FLOWTRACE_SOPERATORID on sa_flowtrace(SOPERATORID);
create index SA_FLOWTRACE_SEURL on sa_flowtrace(SEURL);

CREATE TABLE sa_remindinfo (
  SID varchar2(32) NOT NULL,
  STITLE varchar2(100),
  SCONTEXT varchar2(1000),
  SDATETIME date,
  SSTATE varchar2(100),
  SACTION varchar2(100),
  SPERSONID varchar2(32),
  SPERSONNAME varchar2(100),
  SORGID varchar2(32),
  SORGNAME varchar2(100),
  VERSION int DEFAULT 0
);
ALTER TABLE sa_remindinfo ADD PRIMARY KEY (SID);
comment on column sa_remindinfo.STITLE is '标题';
comment on column sa_remindinfo.SCONTEXT is '描述';
comment on column sa_remindinfo.SDATETIME is '时间';
comment on column sa_remindinfo.SSTATE is '状态';
comment on column sa_remindinfo.SACTION is '操作';
comment on column sa_remindinfo.SPERSONID is '人员ID';
comment on column sa_remindinfo.SPERSONNAME is '人员名称';
comment on column sa_remindinfo.SORGID is '组织ID';
comment on column sa_remindinfo.SORGNAME is '组织名称';

CREATE TABLE sa_links (
  SID varchar2(32) NOT NULL,
  STITLE varchar2(100),
  SURL varchar2(1024),
  SUSERNAME varchar2(100),
  SPASSWORD varchar2(100),
  SEXPARAMS varchar2(1024),
  SOPENTYPE varchar2(20),
  SCREATID varchar2(100),
  SCREATER varchar2(100),
  SCREATEDATE date,
  VERSION int DEFAULT 0
);
alter table sa_links add primary key (SID);

CREATE TABLE SA_DocNameSpace
(
  sID          VARCHAR2(128) NOT NULL,
  sDisplayName VARCHAR2(256),
  sHost        VARCHAR2(128),
  sPort        INTEGER,
  sUrl         VARCHAR2(128),
  version      INTEGER,
  sFlag        INTEGER,
  sAccessMode  VARCHAR2(64) 	
);
alter table SA_DocNameSpace add primary key (sID);

CREATE TABLE sa_docnode (
  SID varchar2(36) NOT NULL,
  SPARENTID varchar2(36),
  SDOCNAME varchar2(2500),
  SSEQUENCE varchar2(128),
  SSIZE number,
  SKIND varchar2(128),
  SDOCPATH varchar2(2048),
  SDOCDISPLAYPATH clob,
  SCREATORFID varchar2(2048),
  SCREATORNAME varchar2(64),
  SCREATORDEPTNAME varchar2(64),
  SCREATETIME date,
  SEDITORFID varchar2(2048),
  SEDITORNAME varchar2(64),
  SEDITORDEPTNAME varchar2(64),
  SLASTWRITERFID varchar2(2048),
  SLASTWRITERNAME varchar2(64),
  SLASTWRITERDEPTNAME varchar2(64),
  SLASTWRITETIME date,
  SFILEID varchar2(128),
  SDESCRIPTION clob,
  SDOCLIVEVERSIONID int,
  VERSION int,
  SCLASSIFICATION varchar2(128),
  SKEYWORDS varchar2(256),
  SDOCSERIALNUMBER varchar2(128),
  SFINISHTIME date,
  SNAMESPACE varchar2(256),
  SCACHENAME varchar2(100),
  SREVISIONCACHENAME varchar2(100),
  SFLAG int
);
alter table sa_docnode add primary key (sID);
create index IDX_SA_DOCNODE on sa_docnode(SPARENTID);
create index IND_SA_DONNODE_SKIND on sa_docnode(SKIND);
create index IDX_SA_DOCNODE_SFILEID on sa_docnode(SFILEID);

CREATE TABLE cyea_news_count (
  SID varchar2(100) NOT NULL,
  NEWS_NUMBER int DEFAULT 0,
  NEWS_PERSON varchar2(100),
  NEWS_PERSONID varchar2(100),
  NEWS_RELEASEID varchar2(100),
  YETPERSON varchar2(100),
  YETPERSONID varchar2(100),
  VERSION int DEFAULT 0
);
alter table cyea_news_count add primary key (sID);

CREATE TABLE cyea_news_release (
  SID varchar2(32) NOT NULL,
  VERSION int DEFAULT 0,
  FNEWSTITLE varchar2(200) DEFAULT NULL,
  FRELEASEDEPARTMENT varchar2(100) DEFAULT NULL,
  FPEOPLE varchar2(50) DEFAULT NULL,
  FTIME date,
  FNEWSNUMBER varchar2(100),
  FRELEASECONNEXT clob,
  FSTATE varchar2(20),
  FSETTOPWHETHER varchar2(10),
  FSETTOPTIME date,
  FSETENDTIME date,
  FACCESSORIES varchar2(1024),
  FCOLUMNNAME varchar2(100),
  FOPENSCOPE varchar2(4000),
  FOPENSCOPEID varchar2(4000),
  SMINIPIC clob
);
alter table cyea_news_release add primary key (sID);

CREATE TABLE sa_opmobilelog (
  SID varchar2(32) NOT NULL,
  SUSERID varchar2(100),
  SUSERNAME varchar2(100),
  SIP varchar2(100),
  SDATE date,
  SMODE varchar2(100) DEFAULT NULL,
  VERSION int DEFAULT 0,
  SSESSIONID varchar2(100),
  SLOGOUTDATE date
);
alter table sa_opmobilelog add primary key (sID);

create table SA_MAILSET
(
  SID             VARCHAR2(32) not null,
  SMAIL           VARCHAR2(100),
  SACCOUNT        VARCHAR2(100),
  SNAME           VARCHAR2(200),
  STYPE           VARCHAR2(20),
  SSENDHOST       VARCHAR2(100),
  SSENDPOST       VARCHAR2(10),
  SISSSL          VARCHAR2(10),
  SRECHOST        VARCHAR2(100),
  SRECPORT        VARCHAR2(20),
  SRECSSL         VARCHAR2(10),
  SISPUB          VARCHAR2(10),
  FCREATEPSNFID   VARCHAR2(2048),
  FCREATEPSNID    VARCHAR2(36),
  FCREATEPSNNAME  VARCHAR2(100),
  FCREATEDEPTID   VARCHAR2(36),
  FCREATEDEPTNAME VARCHAR2(200),
  FCREATEOGNID    VARCHAR2(36),
  FCREATEOGNNAME  VARCHAR2(200),
  FCREATEORGID    VARCHAR2(36),
  FCREATEORGNAME  VARCHAR2(200),
  FCREATETIME     DATE,
  VERSION         INTEGER
);
alter table SA_MAILSET add primary key (SID);
comment on table SA_MAILSET  is '邮箱设置';
comment on column SA_MAILSET.SMAIL  is '邮箱地址';
comment on column SA_MAILSET.SACCOUNT  is '密码';
comment on column SA_MAILSET.SNAME  is '发信名称';
comment on column SA_MAILSET.STYPE  is '邮箱类型';
comment on column SA_MAILSET.SSENDHOST  is '发件服务器';
comment on column SA_MAILSET.SSENDPOST  is '发件端口';
comment on column SA_MAILSET.SISSSL  is '发件是否启用SSL';
comment on column SA_MAILSET.SRECHOST  is '收件服务器';
comment on column SA_MAILSET.SRECPORT  is '收件端口';
comment on column SA_MAILSET.SRECSSL  is '收件是否启用SSL';
comment on column SA_MAILSET.SISPUB  is '是否公用';
comment on column SA_MAILSET.FCREATEPSNFID  is '创建人FID';
comment on column SA_MAILSET.FCREATEPSNID  is '创建人ID';
comment on column SA_MAILSET.FCREATEPSNNAME  is '创建人名称';
comment on column SA_MAILSET.FCREATEDEPTID  is '部门ID';
comment on column SA_MAILSET.FCREATEDEPTNAME  is '部门名称';
comment on column SA_MAILSET.FCREATEOGNID  is '机构ID';
comment on column SA_MAILSET.FCREATEOGNNAME  is '机构名称';
comment on column SA_MAILSET.FCREATEORGID  is '组织ID';
comment on column SA_MAILSET.FCREATEORGNAME  is '组织名称';
comment on column SA_MAILSET.FCREATETIME  is '创建时间';

CREATE TABLE im_message (
  sID varchar2(32) NOT NULL,
  favatar varchar2(1000),
  fusername varchar2(100),
  fid varchar2(100),
  content CLOB,
  tid varchar2(100),
  tavatar varchar2(1000),
  tname varchar2(100),
  tsign varchar2(500),
  stype varchar2(100),
  tusername varchar2(100),
  groupname varchar2(100),
  state integer,
  stime date,
  VERSION integer
);
alter table im_message
  add constraint im_message_key primary key (sID);
create index im_message_fid on im_message (fid);
create index im_message_tid on im_message (tid);
create index im_message_state on im_message (state);
create index im_message_stype on im_message (stype);
comment on column im_message.fid
  is '发送人ID';
comment on column im_message.fusername
  is '发送人昵称';
comment on column im_message.favatar
  is '发送人头像';
comment on column im_message.content
  is '消息内容';
comment on column im_message.tid
  is '目标ID';
comment on column im_message.tavatar
  is '目标头像';
comment on column im_message.tname
  is '目标名称';
comment on column im_message.tsign
  is '对方签名';
comment on column im_message.stype
  is '消息类型';
comment on column im_message.tusername
  is '目标昵称';
comment on column im_message.groupname
  is '群组名称';
comment on column im_message.state
  is '状态';
comment on column im_message.stime
  is '发送时间';

create or replace view sa_oporg_mphone_view as
select sID,
       sParent,
       sCode,
       sName,
       nvl(sOrgKindID, 'org') sOrgKindID,
       sFID,
       sValidState,
       SFCODE,
       sFName,
       sSequence,
       SDESCRIPTION,
       SADDRESS,
       '' smobilephone,
       version
  from Sa_Oporg o
 where o.svalidstate = 1 and o.sorgkindid <> 'psm'
 union all
select p.sID,
       t.sid sParent,
       p.sCode,
       p.sName||'('||nvl(p.smobilephone,'')||')' as sname,
       'psm' sOrgKindID,
       t.sFID||'/'||p.sid as sfid,
       t.sValidState,
       t.sfcode||'/'||p.scode as sfcode,
       t.sFName||'/'||p.sname as sfname,
       t.slevel + p.sSequence sSequence,
       t.SDESCRIPTION,
       t.SADDRESS,
       p.smobilephone,
       p.version
  from sa_opperson p
  left join sa_oporg t on p.smainorgid = t.sid
 where p.smainorgid is not null
   and t.svalidstate = 1
   and p.svalidstate = 1;
   
CREATE OR REPLACE VIEW news_tables AS select a.SID AS SID,a.VERSION AS VERSION,a.FNEWSTITLE AS FNEWSTITLE,a.FSTATE AS FSTATE,a.FPEOPLE AS FPEOPLE,a.FTIME AS FTIME,a.FOPENSCOPE AS FOPENSCOPE,b.NEWS_NUMBER AS NEWS_NUMBER,b.NEWS_PERSON AS NEWS_PERSON,b.YETPERSON AS YETPERSON,a.FOPENSCOPEID AS FOPENSCOPEID from cyea_news_release a left join cyea_news_count b on a.SID = b.NEWS_RELEASEID;
CREATE OR REPLACE VIEW sa_loginlog_view AS select t.SID AS sid,a.SNAME AS sname,a.SCODE AS scode,a.SFNAME AS sfname,t.SLOGINIP AS sloginip,t.SLOGINTIME AS slogintime,b.SMOBILEPHONE AS smobilephone,t.VERSION AS version from sa_loginlog t , sa_oporg a , sa_opperson b where t.SUSERID = a.SPERSONID and b.SID = a.SPERSONID;
CREATE OR REPLACE VIEW sa_onlineinfo_view AS select t.SID AS sid,a.SNAME AS sname,a.SCODE AS scode,a.SFNAME AS sfname,t.SLOGINIP AS sloginip,t.SLOGINDATE AS slogindate,b.STITLE AS STITLE,b.SMOBILEPHONE AS smobilephone,t.SSESSIONID AS ssessionid,t.SSERVICEIP AS sserviceip,t.VERSION AS version from sa_onlineinfo t , sa_oporg a , sa_opperson b where t.SUSERID = a.SPERSONID and b.SID = a.SPERSONID;
CREATE OR REPLACE VIEW sa_opmobilelog_view AS select distinct p.SID AS sid,p.SCODE AS scode,p.SNAME AS sname,o.SFNAME AS sfname,p.SMOBILEPHONE AS smobilephone,p.VERSION AS version from sa_opmobilelog l join sa_oporg o on o.SPERSONID = l.SUSERID join sa_opperson p on p.SID = l.SUSERID;
CREATE OR REPLACE VIEW sa_oporg_recycled AS select o.SID AS sID,o.SPARENT AS sParent,o.SCODE AS sCode,o.SNAME AS sName,nvl(o.SORGKINDID,'org') AS sOrgKindID,o.SFID AS sFID,o.SVALIDSTATE AS sValidState,o.SFCODE AS SFCODE,o.SFNAME AS sFName,o.SSEQUENCE AS sSequence,o.SDESCRIPTION AS SDESCRIPTION,o.SADDRESS AS SADDRESS,o.VERSION AS version from sa_oporg o where o.SVALIDSTATE = -1 union all select p.SID AS sID,t.SID AS sParent,p.SCODE AS sCode,p.SNAME AS sName,'psm' AS sOrgKindID,t.SFID||'/'||p.SID AS SFID,t.SVALIDSTATE AS sValidState,t.SFCODE||'/'||p.SCODE AS SFCODE,t.SFNAME||'/'||p.SNAME AS SFNAME,t.SSEQUENCE AS sSequence,t.SDESCRIPTION AS SDESCRIPTION,t.SADDRESS AS SADDRESS,p.VERSION AS version from sa_opperson p left join sa_oporg t on p.SMAINORGID = t.SID where p.SMAINORGID is not null and p.SVALIDSTATE = -1;
CREATE OR REPLACE VIEW sa_oporg_view AS select o.SID AS sID,o.SPARENT AS sParent,o.SCODE AS sCode,o.SNAME AS sName,nvl(o.SORGKINDID,'org') AS sOrgKindID,o.SFID AS sFID,o.SVALIDSTATE AS sValidState,o.SFCODE AS SFCODE,o.SFNAME AS sFName,o.SSEQUENCE AS sSequence,o.SDESCRIPTION AS SDESCRIPTION,o.SADDRESS AS SADDRESS,'' AS smobilephone,o.VERSION AS version from sa_oporg o where ((o.SVALIDSTATE = 1) and (o.SORGKINDID <> 'psm')) union all select p.SID AS sID,t.SID AS sParent,p.SCODE AS sCode,p.SNAME AS sName,'psm' AS sOrgKindID,t.SFID||'/'||p.SID AS SFID,t.SVALIDSTATE AS sValidState,t.SFCODE||'/'||p.SCODE AS SFCODE,t.SFNAME||'/'||p.SNAME AS SFNAME,(t.SLEVEL + p.SSEQUENCE) AS sSequence,t.SDESCRIPTION AS SDESCRIPTION,t.SADDRESS AS SADDRESS,nvl(p.SMOBILEPHONE,'') AS smobilephone,p.VERSION AS version from sa_opperson p left join sa_oporg t on p.SMAINORGID = t.SID where p.SMAINORGID is not null and t.SVALIDSTATE = 1 and p.SVALIDSTATE = 1;
CREATE OR REPLACE VIEW sa_task_monitor AS select t.SID AS SID,t.SPARENTID AS SPARENTID,t.SNAME AS SNAME,t.SCONTENT AS SCONTENT,t.SREMARK AS SREMARK,t.SFLOWID AS SFLOWID,t.STYPEID AS STYPEID,t.STYPENAME AS STYPENAME,t.SIMPORTANCEID AS SIMPORTANCEID,t.SIMPORTANCENAME AS SIMPORTANCENAME,t.SEMERGENCYID AS SEMERGENCYID,t.SEMERGENCYNAME AS SEMERGENCYNAME,t.SPROCESS AS SPROCESS,t.SACTIVITY AS SACTIVITY,t.SCREATETIME AS SCREATETIME,t.SDISTRIBUTETIME AS SDISTRIBUTETIME,t.SLASTMODIFYTIME AS SLASTMODIFYTIME,t.SWARNINGTIME AS SWARNINGTIME,t.SLIMITTIME AS SLIMITTIME,t.SESTARTTIME AS SESTARTTIME,t.SEFINISHTIME AS SEFINISHTIME,t.SASTARTTIME AS SASTARTTIME,t.SAFINISHTIME AS SAFINISHTIME,t.SEXECUTETIME AS SEXECUTETIME,t.SCPERSONID AS SCPERSONID,t.SCPERSONNAME AS SCPERSONNAME,t.SCDEPTID AS SCDEPTID,t.SCDEPTNAME AS SCDEPTNAME,t.SCOGNID AS SCOGNID,t.SCOGNNAME AS SCOGNNAME,t.SEPERSONID AS SEPERSONID,t.SEPERSONNAME AS SEPERSONNAME,t.SEDEPTID AS SEDEPTID,t.SEDEPTNAME AS SEDEPTNAME,t.SEOGNID AS SEOGNID,t.SEOGNNAME AS SEOGNNAME,t.SCUSTOMERID AS SCUSTOMERID,t.SCUSTOMERNAME AS SCUSTOMERNAME,t.SPROJECTID AS SPROJECTID,t.SPROJECTNAME AS SPROJECTNAME,t.SPLANID AS SPLANID,t.SPLANNAME AS SPLANNAME,t.SVARIABLE AS SVARIABLE,t.SFAKE AS SFAKE,t.SACTIVE AS SACTIVE,t.SLOCK AS SLOCK,t.SSTATUSID AS SSTATUSID,t.SSTATUSNAME AS SSTATUSNAME,t.VERSION AS VERSION,t.SAIID AS SAIID,t.SCATALOGID AS SCATALOGID,t.SKINDID AS SKINDID,t.SAIACTIVE AS SAIACTIVE,t.SAISTATUSID AS SAISTATUSID,t.SAISTATUSNAME AS SAISTATUSNAME,t.SSOURCEID AS SSOURCEID,t.SCURL AS SCURL,t.SEURL AS SEURL,t.SEXECUTEMODE AS SEXECUTEMODE,t.SEXECUTEMODE2 AS SEXECUTEMODE2,t.SPREEMPTMODE AS SPREEMPTMODE,t.SSEQUENCE AS SSEQUENCE,t.SCPERSONCODE AS SCPERSONCODE,t.SCPOSID AS SCPOSID,t.SCPOSCODE AS SCPOSCODE,t.SCPOSNAME AS SCPOSNAME,t.SCDEPTCODE AS SCDEPTCODE,t.SCOGNCODE AS SCOGNCODE,t.SCFID AS SCFID,t.SEPERSONCODE AS SEPERSONCODE,t.SEPOSID AS SEPOSID,t.SEPOSCODE AS SEPOSCODE,t.SEPOSNAME AS SEPOSNAME,t.SEDEPTCODE AS SEDEPTCODE,t.SEOGNCODE AS SEOGNCODE,t.SEFID AS SEFID,t.SEXECUTORNAMES AS SEXECUTORNAMES,t.SRESPONSIBLE AS SRESPONSIBLE,t.SCUSTOMERCODE AS SCUSTOMERCODE,t.SPROJECTCODE AS SPROJECTCODE,t.SPLANCODE AS SPLANCODE,t.SDATA1 AS SDATA1,t.SDATA2 AS SDATA2,t.SDATA3 AS SDATA3,t.SDATA4 AS SDATA4,t.SWORKTIME AS SWORKTIME,t.SCFNAME AS SCFNAME,t.SEFNAME AS SEFNAME,t.SHINTS AS SHINTS,t.SSHORTCUT AS SSHORTCUT,t.SFRONTID AS SFRONTID,t.SFMAKERNAME AS SFMAKERNAME,p.SNAME AS sflowName from sa_task t , sa_task p where t.SID = p.SPARENTID and t.SFLOWID = t.SID;


CREATE OR REPLACE VIEW sa_vpnlog_view AS 
select distinct o.SID AS sid,o.SCODE AS scode,o.SNAME AS sname,o.SFNAME AS sfname,o.VERSION AS version,
l.SLOGINTIME AS slogintime from (sa_loginlog l join sa_oporg o on o.SPERSONID = l.SUSERID) 
where ((l.SLOGINIP = 'x.x.x.x') and (o.SID is not null) 
	   and (not(exists(select p.SUSERID from sa_opmobilelog p where (l.SUSERID = p.SUSERID)))) 
and (l.SLOGINTIME = (select max(lg.SLOGINTIME) from sa_loginlog lg where (lg.SUSERID = l.SUSERID)))) 
order by l.SLOGINTIME desc;

  insert into SA_Code (sID, sType, sCode, sName, version) values('org.psm','org','psm','人员成员',0);
  insert into SA_Code (sID, sType, sCode, sName, version) values('org.psn','org','psn','人员',0);
  insert into SA_Code (sID, sType, sCode, sName, version) values('org.ogn','org','ogn','机构',0);
  insert into SA_Code (sID, sType, sCode, sName, version) values('org.dpt','org','dpt','部门',0);
  insert into SA_Code (sID, sType, sCode, sName, version) values('org.grp','org','grp','工作组',0);
  insert into SA_Code (sID, sType, sCode, sName, version) values('org.pos','org','pos','岗位',0);
  insert into SA_Code (sID, sType, sCode, sName, version) values('importance.tiHighest','importance','tiHighest','最高',0);
  insert into SA_Code (sID, sType, sCode, sName, version) values('importance.tiHigh','importance','tiHigh','高',1);
  insert into SA_Code (sID, sType, sCode, sName, version) values('importance.tiMiddle','importance','tiMiddle','中',2);
  insert into SA_Code (sID, sType, sCode, sName, version) values('importance.tiLower','importance','tiLower','低',3);
  insert into SA_Code (sID, sType, sCode, sName, version) values('emergency.teHighest','emergency','teHighest','紧急',4);
  insert into SA_Code (sID, sType, sCode, sName, version) values('emergency.teHigh','emergency','teHigh','急迫',5);
  insert into SA_Code (sID, sType, sCode, sName, version) values('emergency.teMiddle','emergency','teMiddle','一般',6);
  insert into SA_Code (sID, sType, sCode, sName, version) values('emergency.teLower','emergency','teLower','较低',7);
  insert into SA_Code (sID, sType, sCode, sName, version) values('executeStatus.tesFinished','executeStatus','tesFinished','已完成',9);
  insert into SA_Code (sID, sType, sCode, sName, version) values('executeStatus.tesReady','executeStatus','tesReady','尚未处理',10);
  insert into SA_Code (sID, sType, sCode, sName, version) values('executeStatus.tesExecuting','executeStatus','tesExecuting','正在处理',11);
  insert into SA_Code (sID, sType, sCode, sName, version) values('executeStatus.tesSleeping','executeStatus','tesSleeping','暂缓处理',16);
  insert into SA_Code (sID, sType, sCode, sName, version) values('executeStatus.tesCanceled','executeStatus','tesCanceled','已取消',19);
  insert into SA_Code (sID, sType, sCode, sName, version) values('executeStatus.tesAborted','executeStatus','tesAborted','已终止',20);
  insert into SA_Code (sID, sType, sCode, sName, version) values('executeStatus.tesReturned','executeStatus','tesReturned','已经回退',16);
  insert into SA_Code (sID, sType, sCode, sName, version) values('taskScope.tsProcess','taskScope','tsProcess','流程',17);
  insert into SA_Code (sID, sType, sCode, sName, version) values('taskScope.tsTask','taskScope','tsTask','任务',18);
  insert into SA_Code (sID, sType, sCode, sName, version) values('taskKind.tkProcessInstance','taskKind','tkProcessInstance','流程',19);
  insert into SA_Code (sID, sType, sCode, sName, version) values('taskKind.tkTask','taskKind','tkTask','活动',20);
  insert into SA_Code (sID, sType, sCode, sName, version) values('taskKind.tkExecutor','taskKind','tkExecutor','任务',21);
  insert into SA_Code (sID, sType, sCode, sName, version) values('taskKind.tkNotice','taskKind','tkNotice','通知',27);
  insert into SA_Code (sID, sType, sCode, sName, version) values('taskExecuteMode.temPreempt','taskExecuteMode','temPreempt','抢占',22);
  insert into SA_Code (sID, sType, sCode, sName, version) values('taskExecuteMode.temSequential','taskExecuteMode','temSequential','顺序',23);
  insert into SA_Code (sID, sType, sCode, sName, version) values('taskExecuteMode.temSimultaneous','taskExecuteMode','temSimultaneous','同时',24);
  insert into SA_Code (sID, sType, sCode, sName, version) values('taskPreemptMode.tpmOpen','taskPreemptMode','tpmOpen','打开',25);
  insert into SA_Code (sID, sType, sCode, sName, version) values('taskPreemptMode.tpmExecute','taskPreemptMode','tpmExecute','执行',26);
  insert into SA_Code (sID, sType, sCode, sName, version) values('safeLevel.0','safeLevel','0','普通',0);
  insert into SA_Code (sID, sType, sCode, sName, version) values('safeLevel.1','safeLevel','1','秘密',0);
  insert into SA_Code (sID, sType, sCode, sName, version) values('safeLevel.2','safeLevel','2','绝密',0);
commit;

INSERT INTO sa_docnamespace VALUES ('defaultDocNameSpace', '文档中心', '127.0.0.1', '8080', 'http://127.0.0.1:8080/DocServer', '37', '1', '0');
INSERT INTO sa_docnode VALUES ('root', null, '文档中心', '', null, 'dir', '/root', '/文档中心', '', '', '', null, '', '', '', '', '', '', null, '', '', null, '4', '', '', '', null, 'defaultDocNameSpace', '', '', '1');
INSERT INTO sa_opmanagetype VALUES ('systemManagement', '系统管理', 'systemManagement', 'ogn', ' 机构 部门 岗位 工作组 人员', null, null, '2');
INSERT INTO sa_oporg VALUES ('ORG01', '管理员', 'TULIN', '', '/管理员', '/TULIN', '/ORG01.ogn', 'ogn', '1', null, '1', '', '', '', '', '', '', '', '16', '', '96');
INSERT INTO sa_oporg VALUES ('PSN01@ORG01', 'system', 'SYSTEM', '', '/管理员/system', '/TULIN/SYSTEM', '/ORG01.ogn/PSN01@ORG01.psm', 'psm', '1', 'ORG01', '2', '', '', '', '', '', 'PSN01', 'nkLeaf', '15', '', '1');
INSERT INTO sa_opauthorize(SID, SORGID, SORGNAME, SORGFID, SORGFNAME, SAUTHORIZEROLEID, SDESCRIPTION, SCREATORFID, SCREATORFNAME, SCREATETIME, VERSION, SROLELEVEL)VALUES('AHR01', 'PSN01@ORG01', '管理员', '/ORG01.ogn/PSN01@ORG01.psm', '/管理员/system', 'RL01', '超级管理员', '/ORG01.ogn/PSN01@ORG01.psm', '/tlv8/system', sysdate, 0, 0);
INSERT INTO sa_oppermission VALUES ('01B955F5829F44219FB2806797B45A3B', 'RL01', '/OA/doc/process/baseCode/docSecretLevel/docSecretLevelProcess', '/其它/基础设置/公文密级', 'docSecretLevelActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO sa_oppermission VALUES ('098A9455CDDD44299140A281D3DE875F', 'RL01', '/OA/doc/process/baseCode/docExigenceLevel/docExigenceLevelProcess', '/其它/基础设置/公文紧急程度', 'docExigenceLevelActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO sa_oppermission VALUES ('182217CC50E84AB2A6EA3085A18BA621', 'RL01', '/SA/doc/docSetting/docSettingProcess', '/系统管理/文档/文档配置', 'mainActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO sa_oppermission VALUES ('1B1AF2E6140A445796ABE0442CBB54DE', 'RL01', '/SA/doc/docPermission/docPermissionProcess', '/系统管理/文档/文档关联', 'mainActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO sa_oppermission VALUES ('1F4272327EEF446B8005AEB1C1A42E0B', 'RL01', '/SA/OPM/authorization/authorizationProcess', '/系统管理/组织权限/授权管理-分级', 'gradeActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('265569FC504443E1B134592E3B5FCC5C', 'RL01', '/SA/OPM/log/logProcess', '/系统管理/组织权限/组织机构日志', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('3566316F52F84896ACE1EF9BF42018BE', 'RL01', '/SA/OPM/organization/organizationProcess', '/系统管理/组织权限/组织管理', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('38A312285E834995AFCAC32E2D50265A', 'RL01', '/SA/doc/docCenter/docCenterProcess', '/系统管理/文档/文档中心', 'docCenter', '', '', '', '0', '', null, '1', '0');
INSERT INTO sa_oppermission VALUES ('3B051D390A7B4426B2663AE27F3A45A8', 'RL01', '/OA/docrs/process/baseCode/docRedHead/docRedHeadProcess', '/其它/红头维护', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('5F62DE289C8648689D20D9370ACAE21C', 'RL01', '/SA/OPM/role/roleProcess', '/系统管理/组织权限/角色管理', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('660E9AF723874169A20E80C50B6D6C04', 'RL01', '/OA/docrs/process/docArchive/docArchiveProcess', '/其它/公文归档查询', 'mainActivity', '', '', '', '0', '', null, '1', '2');
INSERT INTO sa_oppermission VALUES ('6A02EEF862114421A0EC8BD3A4BD4222', 'RL01', '/SA/OPM/authorization/authorizationProcess', '/系统管理/组织权限/授权管理', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('6F21B10EAECC46BE95A7A9349D0B041F', 'RL01', '/SA/OPM/grade/gradeProcess', '/系统管理/组织权限/分级管理', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('7A17C36CD1EF4DC1A8C42B805A716556', 'RL01', '/OA/softwareDownLoad/process/software/softwareProcess', '/文件共享/目录维护', 'staticActivity1', '', '', '', '0', '', null, '1', '0');
INSERT INTO sa_oppermission VALUES ('82E555A346274CE092AE25B4DD0BFBE8', 'RL01', '/SA/log/logProcess', '/系统管理/系统工具/操作日志', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('84EBC08BB9D64F4092267873339FBEE2', 'RL01', '/SA/OPM/repairTools/repairToolsProcess', '/系统管理/组织权限/修复工具', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('851787396B3E4D3092B05A8B113131DD', 'RL01', '/OA/softwareDownLoad/process/software/softwareProcess', '/文件共享/目录维护', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('9D8D492C5D49460E9DDA5F6CED4B1545', 'RL01', '/SA/update/updateProcess', '/系统管理/系统工具/软件更新', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('9DC6B147AC1F478E9DFE5199F4EC8BCF', 'RL01', '/SA/online/onlineProcess', '/系统管理/系统工具/在线用户分级', 'gradeOnlineUserActivity', '', '', '', '0', '', null, '1', '6');
INSERT INTO sa_oppermission VALUES ('A84780D2CEF64C5B9DE9947ECD13ED28', 'RL01', '/SA/doc/docSearch/docSearchProcess', '/系统管理/文档/文档检索', 'mainActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO sa_oppermission VALUES ('AC305CCADA8D4947849F0E4F539077B0', 'RL01', '/OA/docrs/process/setReceiveDocTemplate/setReceiveDocTemplateProcess', '/其它/收发文基础设置/设置收发文模版', 'mainActivity', '', '', '', '0', '', null, '1', '2');
INSERT INTO sa_oppermission VALUES ('AD0706B27A22492BA4E979D5DF5CD2AA', 'RL01', '/OA/docrs/process/baseCode/docType/docTypePrecess', '/其它/收发文基础设置/设置公文类别', 'mainActivity', '', '', '', '0', '', null, '1', '2');
INSERT INTO sa_oppermission VALUES ('BCBEB3FEA6DB4283AD2494254FDBCDAE', 'RL01', '/SA/online/onlineProcess', '/系统管理/系统工具/在线用户', 'mainActivity', '', '', '', '0', '', null, '1', '3');
INSERT INTO sa_oppermission VALUES ('C6914BAA2E84424C901DBF8FD95144D7', 'RL01', '/SA/OPM/management/managementProcess', '/系统管理/组织权限/业务管理权限', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('C6B3C40F53200001F12719F019995DA0', 'RL01', '/SA/OPM/recycled/recycledProcess', '/系统管理/组织机构/回收站', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C72FB49C224000015E2927B019E010D9', 'RL01', '/OA/Report/reportImgShowProcess', '/统计报表/时间维度统计图表展示', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C72FF32DC44000015C101BD21A0010B4', 'RL01', '/OA/Report/UserCountByCounty/usercountbycountyProcess', '/统计报表/各县系统用户统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C73002B742B000017654CC60934013C6', 'RL01', '/OA/Report/personusedayReport/mainActivityProcess', '/统计报表/用户使用系统时间统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7304519F47000017F591F2B1FD6EB50', 'RL01', '/OA/Report/OnlineInfoCountOnWeek/mainActivityProcess', '/统计报表/按在线用户量统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7304EB047300001DED41F75D51012B9', 'RL01', '/OA/Report/ConcurrentOnDay/mainActivityProcess', '/统计报表/天内并发量统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7305130B87000018EA718841EB06700', 'RL01', '/OA/Report/UserIncrement/userincrementProcess', '/统计报表/用户增长量统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7305169AE000001C7741ACC284019D3', 'RL01', '/OA/log/LoginLogProcess', '/系统管理/系统工具/用户登录统计', 'LoginLog', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7305169AED0000167D26C507440DE00', 'RL01', '/SA/log/mobileAppLogProcess', '/系统管理/系统工具/手机用户统计', 'mobileAppLog', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7305169AF800001EA2316506D70FAF0', 'RL01', '/SA/log/VPNLogProcess', '/系统管理/系统工具/VPN用户统计', 'VPNLog', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7305169B1900001B9731A801F5D9020', 'RL01', '/SA/MobileNumber/MobileNumbersProcess', '/系统管理/系统工具/移动号码段维护', 'MobileNumbers', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C73052FED5200001504DBB4012607480', 'RL01', '/OA/Report/RecvFileCountOnWeek/mainActivityProcess', '/统计报表/周来文登记数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C73053D2C5B000017ACC110014DC1EDD', 'RL01', '/OA/Report/SendFileCountOnWeek/mainActivityProcess', '/统计报表/周发文数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C73054DDF8700001A473530D17AF1669', 'RL01', '/OA/Report/RecvFileCountByMonth/mainActivityProcess', '/统计报表/按月收文登记数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C730560A70B00001914298E4E92056A0', 'RL01', '/OA/Report/SendFileCountByMonth/mainActivityProcess', '/统计报表/按月发文数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C73058D190100001504B2A1B59C01545', 'RL01', '/OA/Report/RecvFileCountByMonth/reportAllActivityProcess', '/统计报表/收文登记数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C73059667980000122D8187815D01037', 'RL01', '/OA/Report/SendFileCountByMonth/reportAllActivityProcess', '/统计报表/发文数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C730989BF62000012D6E164C164082F0', 'RL01', '/OA/Report/RecvFileCountByMonth/reportProcess', '/统计报表/收文数据对比', 'reportLineActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C730989BF7400001AC5C1EF079D02590', 'RL01', '/OA/Report/SendFileCountByMonth/reportProcess', '/统计报表/发文数据对比', 'reportLineActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C731F08F25A00001934520F0BE70F700', 'RL01', '/OA/ShortMessage/process/SendMsgFromSystem/sendMsgFromSystemProcess', '/发送短信/从系统通讯录发短信', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C731F08F2A80000183FC1B434C5818AA', 'RL01', '/OA/ShortMessage/process/SendMsgQuery/sendMsgQueryProcess', '/发送短信/已发短信查询', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7362262EE800001931710941E606820', 'RL01', '/OA/personal/process/personalInfo/personalProcess', '/其它/个人痕迹查询', 'markActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C79092E0D2C00001FFD6E8A61810C900', 'RL01', '/OA/hr/wage/ListActivityPersonProcess', '/其它/个人薪资查询', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7A4DAEE8A500001ADED569E1537A090', 'RL01', '/OA/docrs/process/sendDoc/AllSendedProcess', '/其它/已发文件', 'AllSendedActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7A5BEC63EE0000123FA2804F3571E8B', 'RL01', '/OA/CheckWorkAtten/baseProcess', '/工作管理/考勤管理/上班时间设置', 'workDateActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7B2671830000001A63111A019609FA0', 'RL01', '/OA/Project/Manage/mainProcess', '/州级项目审批情况/项目审批情况统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7B2F1D86D00000168DBE4C02B1013E2', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况登记(州内)', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7B2F53FD6A0000169FDDF90149019F0', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况查看(州内)', 'lookActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7B2F53FD8C00001BC5C1F901078EE00', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况登记(州外)', 'mainActivity1', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7B2F53FDB3000019EF0141B73404B10', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况查看(州外)', 'lookActivity1', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C7D8FEE6EFE00001B6751A1E12303070', 'RL01', '/OA/fawenDanwei/mainProcess', '/系统管理/系统工具/允许发文单位', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C82E3F2608B00001E83E18231E2A18BA', 'RL01', '/SA/theme/bgProcess', '/系统管理/系统工具/登录页背景配置', 'mainActivity', '', '', '', null, '', null, null, '1');
INSERT INTO sa_oppermission VALUES ('C8336D112D0000013CE01830EDF01F56', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/填写工作日志', 'myWorkLog', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C8336D112E7000016B60624914781E9A', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/我的工作日志', 'MyWorklogList', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C8336D112FE00001DDAFA0612340187E', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/工作日志审批', 'waitWorkLog', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C8336D1131300001CB7E1FF017531313', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/工作日志汇总', 'allWorklogList', '', '', '', null, '', null, null, '0');
INSERT INTO sa_oppermission VALUES ('C8373BC0E94000011694194017001AE0', 'RL01', '/OA/CheckWorkAtten/personnelProcess', '/考勤管理/个人考勤', 'personnelActivity', '', '', '', null, '', null, null, '1');
INSERT INTO sa_oppermission VALUES ('C8373BC0EB8000013B42146CBC7010F0', 'RL01', '/OA/CheckWorkAtten/personnelProcess', '/考勤管理/考勤记录', 'DayChekinList', '', '', '', null, '', null, null, '1');
INSERT INTO sa_oppermission VALUES ('C8373BC0EE1000011C8215C05B15EFB0', 'RL01', '/OA/CheckWorkAtten/personnelProcess', '/考勤管理/考勤统计', 'reportActivity', '', '', '', null, '', null, null, '1');
INSERT INTO sa_oppermission VALUES ('C8A5A0B5BED00001ADC31CC0968311E8', 'RL01', 'SA/services/pcProcess', '/系统管理/系统工具/服务器监控', 'mainActivity', '', '', '', null, '', null, null, '1');
INSERT INTO sa_oppermission VALUES ('C8D3540ADE800001392115941C10100B', 'RL01', '/OA/docrs/process/sendDoc/sendDocProcess', '/发文管理/删除发文', 'SendDel', '', '', '', null, '', null, null, '1');
INSERT INTO sa_oppermission VALUES ('C8DC2295A8E00001B8DE85203AD016AE', 'RL01', '/SA/task/taskCenter/process', '/任务中心/任务列表', 'mainActivity', '', '', '', null, '', null, null, '1');
INSERT INTO sa_oppermission VALUES ('C8DC2295A9600001BBE919691C301C37', 'RL01', '/SA/process/monitor/process', '/任务中心/流程监控', 'mainActivity', '', '', '', null, '', null, null, '1');
INSERT INTO sa_oppermission VALUES ('C8DC2295A9E00001D89590F5198012BC', 'RL01', '/SA/task/unFlowmana/process', '/任务中心/任务处理', 'mainActivity', '', '', '', null, '', null, null, '1');
INSERT INTO sa_oppermission VALUES ('C8DC2295AA6000017BF83A6965004D50', 'RL01', '/flw/dwr/process', '/任务中心/流程设计', 'vml-dwr-editer', '', '', '', null, '', null, null, '1');
INSERT INTO sa_oppermission VALUES ('C94E5AE783D000017BE612A0165419DF', 'RL01', '029AB9CC9C5D4378B25C9BCB6C32B33D', '/采购管理/采购登记', 'bizActivity2', null, null, null, null, null, null, null, '1');
INSERT INTO sa_oppermission VALUES ('C94E5AE784A0000163C271C810651321', 'RL01', '029AB9CC9C5D4378B25C9BCB6C32B33D', '/采购管理/采购审批', 'bizActivity4', null, null, null, null, null, null, null, '1');
INSERT INTO sa_oppermission VALUES ('C94EE9000440000135A1A7F050121CA8', 'RL01', '/SA/task/taskCenter/process', '/任务中心/流程监控', 'monitorActivity', null, null, null, null, null, null, null, '1');
INSERT INTO sa_oppermission VALUES ('C94EE9000540000138D11EF078DC6810', 'RL01', '/SA/task/taskCenter/process', '/任务中心/任务处理', 'unFlowmanaActivity', null, null, null, null, null, null, null, '1');
INSERT INTO sa_oppermission VALUES ('C94EE90006D000013BDE12401F401700', 'RL01', '/SA/task/taskCenter/process', '/任务中心/系统提醒', 'reminActivity', null, null, null, null, null, null, null, '1');
INSERT INTO sa_oppermission VALUES ('C94F4E969D600001886F19D05E501F55', 'RL01', '/SA/OPM/recycled/recycledProcess', '/系统管理/组织机构/回收站-分级', 'gradeRecycledActivity', null, null, null, null, null, null, null, '1');
INSERT INTO sa_oppermission VALUES ('D0EC487F72FF4D768C92520A8B90F458', 'RL01', '/OA/docrs/process/baseCode/docArchiveTree/docArchiveTreePrecess', '/其它/收发文基础设置/设置公文归档', 'mainActivity', '', '', '', '0', '', null, '1', '2');
INSERT INTO sa_oppermission VALUES ('E6D1673C76174CCEA9919C0C16453C98', 'RL01', '/SA/OPM/management/managementProcess', '/系统管理/组织权限/业务管理权限-分级', 'gradeActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('EE9E19F50356498C96BB193356F8AF38', 'RL01', '/SA/OPM/organization/organizationProcess', '/系统管理/组织权限/组织管理-分级', 'gradeActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_oppermission VALUES ('EF8F1CC8AB6F4728A7D083B6DD1790C1', 'RL01', '/OA/docrs/process/setReceiveDocTemplate/setReceiveDocTemplateProcess', '/其它/收发文基础设置/设置映射关系', 'SetORMstaticActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO sa_oppermission VALUES ('F2AF3284E2D6405E9990376C19C57D45', 'RL01', '/SA/OPM/agent/agentProcess', '/系统管理/组织权限/代理设置', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO sa_opperson VALUES ('PSN01', 'system', 'SYSTEM', '', '1', '管理员', 'C4CA4238A0B923820DCC509A6F75849B', '120', sysdate, 'ORG01', '', '1', '1', '', '男', sysdate, sysdate, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '14769660886', '', '', '4', null, '', '', '', '', '', '', '','',null);
INSERT INTO sa_oprole VALUES ('RL01', '超级管理员', 'opm', '勿删-系统应用', 'fun', '', '', '0', '1', '4');
INSERT INTO sa_oprole VALUES ('RL02', '任务', 'task', '系统管理', 'fun', '', '', '0', '1', '2');
INSERT INTO sa_oprole VALUES ('RL02-doc', '文档', 'doc', '系统管理', 'fun', '', '', '0', '1', '2');
INSERT INTO sa_flowfolder VALUES ('root', null, '根目录', 'root', '根目录', null, '/D08B63947EE1444D8C97DC0C5EAD8AD6', '/根目录', '/root', 0);
commit;