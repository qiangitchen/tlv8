CREATE TABLE `bo_blog` (
  `SID` varchar(100) NOT NULL,
  `VERSION` int,
  `NAME` varchar(100),
  `DESCRIPTION` varchar(100),
  `ENTRY_NUMBER` int,
  `RECENT_NUMBER` int,
  `RECENT_ENTRY_NUMBER` int,
  `STATUS` int,
  `COMMENT_AUDIT` varchar(100),
  `OPENSCOPE` varchar(100),
  `SETTING` varchar(100),
  `BLOGCATEGORY` varchar(100),
  `CHAIRMAN` varchar(100),
  `CHAIRMAN_ID` varchar(100),
  PRIMARY KEY (`SID`)
);


CREATE TABLE `bo_category` (
  `SID` varchar(100) NOT NULL,
  `VERSION` int,
  `NAME` varchar(100),
  `DISPLAY_ORDER` int,
  `DESCRIPTION` varchar(100),
  `FMASTERID` varchar(100),
  PRIMARY KEY (`SID`)
);


CREATE TABLE `bo_entry` (
  `SID` varchar(100) NOT NULL,
  `VERSION` int,
  `TITLE` varchar(100),
  `CONTENT` longtext,
  `CATEGORY_ID` varchar(100),
  `CATEGORY_NAME` varchar(100),
  `COMMENT_HIT` int,
  `ALLOW_COMMENT` int,
  `STATUS` int DEFAULT '0',
  `CREATED_TIME` datetime,
  `UPDATED_TIME` datetime,
  `HITS` int,
  `BO_BLOGID` varchar(100),
  `ACCESSORIES` varchar(1024),
  `FEXTEND01` varchar(1024),
  `ONESELF` varchar(100),
  `FID` varchar(100),
  PRIMARY KEY (`SID`)
);

create table BO_COMMENT
(
  SID          VARCHAR(100) not null,
  AUTHOR       VARCHAR(100) comment '回复人',
  EMAIL        VARCHAR(100) comment '邮箱',
  CONTENT      TEXT comment '回复内容',
  CREATED_TIME DATETIME comment '回复时间',
  ENTRY_ID     VARCHAR(100) comment '帖子ID',
  ENTRY_TITLE  VARCHAR(100) comment '帖子标题',
  IP           VARCHAR(100),
  STATUS       INT,
  VERSION      INT
);

alter table BO_COMMENT
  add primary key (SID);

create table PERSONAL_FILE
(
  SID                VARCHAR(32) not null,
  SFILENAME          VARCHAR(1024),
  SFILESIZE          VARCHAR(100),
  SDOCPATH           TEXT,
  SFILEID			 VARCHAR(100),
  DOCID              VARCHAR(100),
  SCREATORID         VARCHAR(100),
  SCREATORNAME       VARCHAR(100),
  SMASTERID          VARCHAR(32),
  SACCESSORY         TEXT,
  SACCESSCURRENTID   VARCHAR(1024),
  SACCESSCURRENTNAME VARCHAR(1024),
  VERSION            INT
);
alter table PERSONAL_FILE add primary key (SID);

create table PERSONALDOCNODE
(
  SID                VARCHAR(32) not null,
  SPARENTID          VARCHAR(100),
  SPARENTNAME        VARCHAR(100),
  SCREATORID         VARCHAR(100),
  SCREATORNAME       VARCHAR(100),
  SPATH              VARCHAR(100),
  DESCRIPTION        VARCHAR(1024),
  SACCESSCURRENTID   VARCHAR(1024),
  SACCESSCURRENTNAME VARCHAR(1024),
  VERSION            INT
);
alter table PERSONALDOCNODE add primary key (SID);

CREATE TABLE `cyea_news_count` (
  `SID` varchar(100) NOT NULL,
  `NEWS_NUMBER` int DEFAULT '0',
  `NEWS_PERSON` varchar(100),
  `NEWS_PERSONID` varchar(100),
  `NEWS_RELEASEID` varchar(100),
  `YETPERSON` varchar(100),
  `YETPERSONID` varchar(100),
  `VERSION` int,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `cyea_news_release` (
  `SID` varchar(32) NOT NULL,
  `VERSION` int,
  `FNEWSTITLE` varchar(200),
  `FRELEASEDEPARTMENT` varchar(100),
  `FPEOPLE` varchar(50),
  `FTIME` datetime,
  `FNEWSNUMBER` varchar(100),
  `FRELEASECONNEXT` longtext,
  `FSTATE` varchar(20),
  `FSETTOPWHETHER` varchar(10),
  `FSETTOPTIME` datetime,
  `FSETENDTIME` datetime,
  `FACCESSORIES` varchar(1024),
  `FCOLUMNNAME` varchar(100),
  `FOPENSCOPE` varchar(4000),
  `FOPENSCOPEID` varchar(4000),
  `SMINIPIC` longblob,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `cyea_newscolumn` (
  `SID` varchar(32) NOT NULL,
  `VERSION` int,
  `FCOLUMNCODE` varchar(100),
  `FCOLUMNNAME` varchar(100),
  `FCOLUMNDESCRIBES` varchar(100),
  `FCOLUMNSTATE` varchar(50),
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_docauth` (
  `SID` varchar(36) NOT NULL,
  `SDOCPATH` TEXT,
  `SAUTHORIZERFID` varchar(512),
  `SAUTHORIZERNAME` varchar(64),
  `SAUTHORIZERDEPTNAME` varchar(64),
  `SAUTHORIZEEFID` varchar(512),
  `SAUTHORIZEENAME` varchar(64),
  `SGRANTTIME` datetime,
  `SACCESS` int,
  `SSCOPE` varchar(16),
  `SAUTHORIZEEDEPTNAME` varchar(64),
  `VERSION` decimal(10,0),
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_docnamespace` (
  `SID` varchar(128) NOT NULL,
  `SDISPLAYNAME` varchar(256),
  `SHOST` varchar(128),
  `SPORT` int,
  `SURL` varchar(128),
  `VERSION` int,
  `SFLAG` int,
  `SACCESSMODE` varchar(64),
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_docnode` (
  `SID` varchar(36) NOT NULL,
  `SPARENTID` varchar(36),
  `SDOCNAME` varchar(2500),
  `SSEQUENCE` varchar(128),
  `SSIZE` float,
  `SKIND` varchar(128),
  `SDOCPATH` text,
  `SDOCDISPLAYPATH` longtext,
  `SCREATORFID` varchar(2048),
  `SCREATORNAME` varchar(64),
  `SCREATORDEPTNAME` varchar(64),
  `SCREATETIME` datetime,
  `SEDITORFID` varchar(2048),
  `SEDITORNAME` varchar(64),
  `SEDITORDEPTNAME` varchar(64),
  `SLASTWRITERFID` varchar(2048),
  `SLASTWRITERNAME` varchar(64),
  `SLASTWRITERDEPTNAME` varchar(64),
  `SLASTWRITETIME` datetime,
  `SFILEID` varchar(128),
  `SDESCRIPTION` longtext,
  `SDOCLIVEVERSIONID` int,
  `VERSION` decimal(10,0),
  `SCLASSIFICATION` varchar(128),
  `SKEYWORDS` varchar(256),
  `SDOCSERIALNUMBER` varchar(128),
  `SFINISHTIME` datetime,
  `SNAMESPACE` varchar(256),
  `SCACHENAME` varchar(100),
  `SREVISIONCACHENAME` varchar(100),
  `SFLAG` int,
  PRIMARY KEY (`SID`)
);
alter table sa_docnode add index IDX_SA_DOCNODE (SPARENTID);
alter table sa_docnode add index IND_SA_DONNODE_SKIND (SKIND);
alter table sa_docnode add index IDX_SA_DOCNODE_SFILEID (SFILEID);

create table SA_DOCLOG
(
  SID           VARCHAR(36) not null,
  SPERSONNAME   VARCHAR(64),
  SACCESSTYPE   VARCHAR(16),
  SDOCID        VARCHAR(64),
  STIME         DATETIME,
  SDEPTNAME     VARCHAR(64),
  SPERSONFID    VARCHAR(512),
  SDOCVERSIONID INT,
  SDOCNAME      VARCHAR(128),
  SSIZE         FLOAT,
  VERSION       INT
);
alter table SA_DOCLOG ADD PRIMARY KEY (SID);


CREATE TABLE `sa_flowcontrol` (
  `SID` varchar(36) NOT NULL,
  `SOPERATORID` varchar(50),
  `SPROCESS` varchar(255),
  `SACTIVITY` varchar(255),
  `SACTION` varchar(36),
  `SKIND` varchar(16),
  `SCONTENT` longtext,
  `VERSION` int,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_flowdata` (
  `SID` varchar(36) NOT NULL,
  `SFLOWID` varchar(36),
  `SPROCESS` varchar(36),
  `SACTIVITY` varchar(255),
  `SACTION` varchar(255),
  `SKIND` varchar(36),
  `SCONTENT` longtext,
  `VERSION` int,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_flowdrawlg` (
  `SID` varchar(32) NOT NULL COMMENT '主键',
  `SPROCESSID` varchar(100) NOT NULL COMMENT '流程标识',
  `SPROCESSNAME` varchar(100) COMMENT '流程名称',
  `SDRAWLG` longtext COMMENT '流程图',
  `VERSION` int DEFAULT '0' COMMENT '版本号',
  `SPROCESSACTY` longtext COMMENT '流程环节',
  `SCREATORID` varchar(100) COMMENT '创建人ID',
  `SCREATORNAME` varchar(100) COMMENT '创建人名称',
  `SUPDATORID` varchar(100) COMMENT '修改人ID',
  `SUPDATORNAME` varchar(100) COMMENT '修改人名称',
  `SCREATETIME` datetime COMMENT '创建时间',
  `SUPDATETIME` datetime COMMENT '修改时间',
  `FENABLED` int COMMENT '启用状态',
  `SFOLDERID` varchar(32) COMMENT '目录ID',
  PRIMARY KEY (`SID`)
);
alter table sa_flowdrawlg add unique SA_FLOWDRAWLG_SPROCESSID(SPROCESSID);


CREATE TABLE `sa_flowdrawlg_ipo` (
  `SID` varchar(32) NOT NULL,
  `SPROCESSID` varchar(100) NOT NULL,
  `SPROCESSNAME` varchar(100),
  `SDRAWLG` text,
  `VERSION` int,
  `SPROCESSACTY` text,
  `SCREATORID` varchar(100),
  `SCREATORNAME` varchar(100),
  `SUPDATORID` varchar(100),
  `SUPDATORNAME` varchar(100),
  `SCREATETIME` datetime,
  `SUPDATETIME` datetime,
  `FENABLED` int,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_flowfolder` (
  `SID` varchar(32) NOT NULL,
  `SPROCESSID` varchar(100),
  `SPROCESSNAME` varchar(100),
  `SCODE` varchar(100),
  `SNAME` varchar(100),
  `SPARENT` varchar(32),
  `SIDPATH` varchar(4000),
  `SNAMEPATH` varchar(4000),
  `SCODEPATH` varchar(4000),
  `VERSION` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`SID`)
);
alter table sa_flowfolder add index SOU_SA_FLWFOLDER_SPARENT(SPARENT);


CREATE TABLE `sa_flowinfo` (
  `ID` varchar(32) NOT NULL,
  `STOACTIVITY` varchar(500),
  `STOOPERATORID` varchar(4000),
  `VERSION` decimal,
  `SFROMACTIVITYINSTANCEID` varchar(500),
  `STASKNAME` varchar(500),
  `STASKCONTENT` varchar(500),
  `STASKCREATETIME` datetime,
  `STASKEMERGENCYID` decimal,
  `STASKWARNINGTIME` datetime,
  `STASKLIMITTIME` datetime,
  PRIMARY KEY (`ID`)
);


CREATE TABLE `sa_flowinfoext` (
  `ID` varchar(100) NOT NULL,
  `SPROCESS` varchar(200) NOT NULL,
  `SFROMACTIVITY` varchar(200) NOT NULL,
  `SCREATORID` varchar(50),
  `SEXECUTORS` longtext,
  `STOACTIVITY` varchar(500),
  `VERSION` decimal(10,0),
  PRIMARY KEY (`ID`)
);


CREATE TABLE `sa_flowtrace` (
  `SID` varchar(36) NOT NULL,
  `SOPERATORID` varchar(36),
  `SOPERATORCODE` varchar(100),
  `SOPERATORNAME` varchar(100),
  `SCURL` varchar(1024),
  `SEURL` varchar(1024),
  `SCHECKPSN` varchar(4000),
  `VERSION` int NOT NULL,
  PRIMARY KEY (`SID`)
);
alter table sa_flowtrace add index SA_FLOWTRACE_SOPERATORID(SOPERATORID);
alter table sa_flowtrace add index SA_FLOWTRACE_SEURL(SEURL);


CREATE TABLE `sa_kvsequence` (
  `K` varchar(200) NOT NULL,
  `V` int NOT NULL,
  PRIMARY KEY (`K`)
);


CREATE TABLE `sa_links` (
  `SID` varchar(32),
  `STITLE` varchar(100),
  `SURL` varchar(1024),
  `SUSERNAME` varchar(100),
  `SPASSWORD` varchar(100),
  `SEXPARAMS` varchar(1024),
  `SOPENTYPE` varchar(20),
  `SCREATID` varchar(100),
  `SCREATER` varchar(100),
  `SCREATEDATE` datetime,
  `VERSION` int
);


CREATE TABLE `sa_log` (
  `SID` varchar(32) NOT NULL,
  `STYPENAME` varchar(100),
  `SDESCRIPTION` longtext,
  `SPROCESS` varchar(2048),
  `SPROCESSNAME` varchar(2048),
  `SACTIVITY` varchar(100),
  `SACTIVITYNAME` varchar(2048),
  `SACTION` varchar(100),
  `SACTIONNAME` varchar(2048),
  `SSTATUSNAME` varchar(64),
  `SCREATETIME` datetime,
  `SCREATORFID` varchar(2048),
  `SCREATORFNAME` varchar(2048),
  `SCREATORPERSONID` varchar(36),
  `SCREATORPERSONNAME` varchar(255),
  `SCREATORPOSID` varchar(36),
  `SCREATORPOSNAME` varchar(255),
  `SCREATORDEPTID` varchar(36),
  `SCREATORDEPTNAME` varchar(255),
  `SCREATOROGNID` varchar(36),
  `SCREATOROGNNAME` varchar(255),
  `SIP` varchar(32),
  `VERSION` decimal(10,0),
  `SDEVICETYPE` varchar(100),
  `SOPERATINGSYSTEM` varchar(100),
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_loginlog` (
  `SID` varchar(32) NOT NULL,
  `SUSERID` varchar(36),
  `SUSERNAME` varchar(100),
  `SLOGINIP` varchar(100),
  `SLOGINTIME` datetime,
  `PASSWORD` varchar(100),
  `VERSION` int,
  `SSERVICEIP` varchar(100),
  `SDAY` varchar(100),
  `SDAYNUM` int,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_msnalert` (
  `SID` varchar(32) NOT NULL,
  `SNAME` varchar(100),
  `SURL` varchar(1024),
  `SMESSAGE` varchar(2048),
  `SSTATE` int NOT NULL DEFAULT '0',
  `SBILLID` varchar(36),
  `SBILTABLE` varchar(100),
  `SRPERSONID` varchar(100),
  `SSPERSONID` varchar(100),
  `SSDATE` datetime,
  `SRDATE` datetime,
  `VERSION` int DEFAULT '0',
  `SMURL` varchar(1024),
  `SMSTATE` int DEFAULT '0',
  `SOPIONID` varchar(36) DEFAULT '',
  PRIMARY KEY (`SID`)
);
alter table sa_msnalert add index SA_MSNALERT_SRPERSONID(SRPERSONID);
alter table sa_msnalert add index SA_MSNALERT_SOPIONID(SOPIONID);
alter table sa_msnalert add index SA_MSNALERT_SSDATE(SSDATE);
alter table sa_msnalert add index SA_MSNALERT_SBILLID(SBILLID);
alter table sa_msnalert add index SA_MSNALERT_SSTATE(SSTATE);


CREATE TABLE `sa_online_log` (
  `SID` varchar(32) NOT NULL,
  `SUSERID` varchar(36),
  `SUSERNAME` varchar(100),
  `SUSERFID` varchar(1024),
  `SUSERFNAME` varchar(1024),
  `SLOGINIP` varchar(100),
  `SLOGINDATE` datetime,
  `SLOGOUTDATE` datetime,
  `SSESSIONID` varchar(100),
  `SSERVICEIP` varchar(100),
  `VERSION` int,
  `SDAY` varchar(100),
  `SDAYNUM` int,
  PRIMARY KEY (`SID`)
);
alter table sa_online_log add index SA_PNLINE_LOG_SLOGINDATE(SLOGINDATE);
alter table sa_online_log add index SA_ONLINE_LOG_SSERVICEIP(SSERVICEIP);
alter table sa_online_log add index SA_ONLINE_LOG_SSESSIONID(SSESSIONID);


CREATE TABLE `sa_onlineinfo` (
  `SID` varchar(32) NOT NULL,
  `SUSERID` varchar(36),
  `SUSERNAME` varchar(100),
  `SUSERFID` varchar(1024),
  `SUSERFNAME` varchar(1024),
  `SLOGINIP` varchar(100),
  `SLOGINDATE` datetime,
  `SSESSIONID` varchar(100),
  `SSERVICEIP` varchar(100),
  `VERSION` int,
  `SMACHINECODE` varchar(100),
  PRIMARY KEY (`SID`)
);
alter table sa_onlineinfo add index SA_ONLINEINFO_SSESSIONID(SSESSIONID);
alter table sa_onlineinfo add index SA_ONLINEINFO_SSERVICEIP(SSERVICEIP);


CREATE TABLE `sa_opagent` (
  `SID` varchar(32) NOT NULL,
  `SORGFID` varchar(2048) NOT NULL,
  `SORGFNAME` varchar(2048) NOT NULL,
  `SAGENTID` varchar(32) NOT NULL,
  `SVALIDSTATE` int,
  `SSTARTTIME` datetime,
  `SFINISHTIME` datetime,
  `SPROCESS` longtext,
  `SCREATORID` varchar(32),
  `SCREATORNAME` varchar(64),
  `SCREATETIME` datetime,
  `SCANTRANAGENT` int,
  `VERSION` int NOT NULL,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_opauthorize` (
  `SID` varchar(100) NOT NULL,
  `SORGID` varchar(65),
  `SORGNAME` varchar(255),
  `SORGFID` varchar(2048),
  `SORGFNAME` varchar(2048),
  `SAUTHORIZEROLEID` varchar(32),
  `SDESCRIPTION` varchar(1024),
  `SCREATORFID` varchar(2048),
  `SCREATORFNAME` varchar(2048),
  `SCREATETIME` datetime,
  `VERSION` int,
  `SROLELEVEL` varchar(100),
  PRIMARY KEY (`SID`)
);
alter table sa_opauthorize add index IDX_OPAUTH_ROLEID(SAUTHORIZEROLEID);


CREATE TABLE `sa_opmanagement` (
  `SID` varchar(32) NOT NULL,
  `SORGID` varchar(65) NOT NULL,
  `SORGNAME` varchar(255),
  `SORGFID` varchar(2048) NOT NULL,
  `SORGFNAME` varchar(2048),
  `SMANAGEORGID` varchar(65) NOT NULL,
  `SMANAGEORGNAME` varchar(255),
  `SMANAGEORGFID` varchar(2048) NOT NULL,
  `SMANAGEORGFNAME` varchar(2048),
  `SMANAGETYPEID` varchar(32) NOT NULL,
  `SCREATORFID` varchar(2048),
  `SCREATORFNAME` varchar(2048),
  `SCREATETIME` datetime,
  `VERSION` int NOT NULL,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_opmanagetype` (
  `SID` varchar(32) NOT NULL,
  `SNAME` varchar(255),
  `SCODE` varchar(32),
  `SMANAGEORGKIND` varchar(64),
  `SMANAGEORGKINDNAME` varchar(255),
  `SLEADERNUMBER` int,
  `SISSYSTEM` int,
  `VERSION` int NOT NULL,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_opmobilelog` (
  `SID` varchar(32) NOT NULL,
  `SUSERID` varchar(100),
  `SUSERNAME` varchar(100),
  `SIP` varchar(100),
  `SDATE` datetime,
  `SMODE` varchar(100),
  `VERSION` int,
  `SSESSIONID` varchar(100),
  `SLOGOUTDATE` datetime,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_oporg` (
  `SID` varchar(100) NOT NULL,
  `SNAME` varchar(128) NOT NULL,
  `SCODE` varchar(64) NOT NULL,
  `SLONGNAME` varchar(255),
  `SFNAME` varchar(1024),
  `SFCODE` varchar(2048),
  `SFID` varchar(1024) NOT NULL,
  `SORGKINDID` varchar(5) NOT NULL,
  `SVALIDSTATE` int,
  `SPARENT` varchar(100),
  `SLEVEL` int,
  `SPHONE` varchar(64),
  `SFAX` varchar(64),
  `SADDRESS` varchar(255),
  `SZIP` varchar(16),
  `SDESCRIPTION` varchar(1024),
  `SPERSONID` varchar(32),
  `SNODEKIND` varchar(32),
  `VERSION` int NOT NULL,
  `SSHOWNAME` varchar(100),
  `SSEQUENCE` int DEFAULT '1',
  PRIMARY KEY (`SID`)
);
alter table sa_oporg add index SA_OPORG_SFID(SFID);
alter table sa_oporg add index IDX_OPORG_CODE(SCODE);
alter table sa_oporg add index IDX_SA_OPORG_SORGKINDID(SORGKINDID);
alter table sa_oporg add index IDX_SA_OPORG_SPARENT(SPARENT);
alter table sa_oporg add index IDX_SA_OPORG_SPERSONID(SPERSONID);


CREATE TABLE `sa_oporg_interface` (
  `SOGNID` varchar(36) NOT NULL,
  `SOGNNAME` varchar(200),
  PRIMARY KEY (`SOGNID`)
);


CREATE TABLE `sa_oppermission` (
  `SID` varchar(32) NOT NULL,
  `SPERMISSIONROLEID` varchar(32) NOT NULL,
  `SPROCESS` varchar(1024),
  `SACTIVITYFNAME` varchar(1024),
  `SACTIVITY` varchar(1024),
  `SACTIONSNAMES` longtext,
  `SACTIONS` longtext,
  `SSEMANTICDP` varchar(2048),
  `SPERMISSIONKIND` int,
  `SDESCRIPTION` varchar(1024),
  `SSEQUENCE` int,
  `SVALIDSTATE` int,
  `VERSION` int NOT NULL,
  PRIMARY KEY (`SID`)
);
alter table sa_oppermission add index IDX_OPPERM_ROLEID(SPERMISSIONROLEID);
alter table sa_oppermission add index IDX_OPPERM_ACTIVITY(SACTIVITY);
alter table sa_oppermission add index IDX_OPPERM_PROCESS(SPROCESS);


CREATE TABLE `sa_opperson` (
  `SID` varchar(36) NOT NULL,
  `SNAME` varchar(64) NOT NULL,
  `SCODE` varchar(64) NOT NULL,
  `SIDCARD` varchar(36),
  `SNUMB` int NOT NULL,
  `SLOGINNAME` varchar(64),
  `SPASSWORD` varchar(64),
  `SPASSWORDTIMELIMIT` int,
  `SPASSWORDMODIFYTIME` datetime,
  `SMAINORGID` varchar(36) NOT NULL,
  `SSAFELEVELID` varchar(36),
  `SSEQUENCE` int,
  `SVALIDSTATE` int,
  `SDESCRIPTION` varchar(2048),
  `SSEX` varchar(8),
  `SBIRTHDAY` datetime,
  `SJOINDATE` datetime,
  `SHOMEPLACE` varchar(64),
  `SDEGREE` varchar(16),
  `SGRADUATESCHOOL` varchar(128),
  `SSPECIALITY` varchar(128),
  `SSCHOOLLENGTH` varchar(16),
  `STITLE` varchar(64),
  `SMARRIAGE` varchar(16),
  `SCARDNO` varchar(36),
  `SCARDKIND` varchar(64),
  `SFAMILYADDRESS` varchar(255),
  `SZIP` varchar(16),
  `SMSN` varchar(64),
  `SQQ` varchar(36),
  `SMAIL` varchar(64),
  `SMOBILEPHONE` varchar(36),
  `SFAMILYPHONE` varchar(36),
  `SOFFICEPHONE` varchar(36),
  `VERSION` int,
  `SPHOTO` longblob,
  `SCOUNTRY` varchar(64),
  `SPROVINCE` varchar(64),
  `SCITY` varchar(64),
  `SPOSITIONS` varchar(64),
  `SSCHOOL` varchar(64),
  `SSTUDY` varchar(64),
  `SENGLISHNAME` varchar(128),
   FCASN varchar(100), 
   FSIGNM VARCHAR(100),
  PRIMARY KEY (`SID`)
);
alter table sa_opperson add unique IDX_OPPERSON_SCODE(SCODE);
alter table sa_opperson add index IDX_OPPERSON_SMAINORGID(SMAINORGID);
alter table sa_opperson add index XMSGINDEXMESSAGESVALIDSTATE(SVALIDSTATE);


CREATE TABLE `sa_opperson_deatail` (
  `SID` varchar(36) NOT NULL,
  `SPERSONID` varchar(36),
  `STYPE` varchar(500),
  `SSQUNS` varchar(500),
  `SCODE` varchar(500),
  `SNAME` varchar(500),
  `SREMARK` varchar(500),
  `SCLASS` varchar(500),
  `SUPDATEDATE` datetime,
  `SUPDATORID` varchar(36),
  `SUPDATORNAME` varchar(255),
  `VERSION` int,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_oprole` (
  `SID` varchar(32) NOT NULL comment '主键',
  `SNAME` varchar(255) comment '角色名称',
  `SCODE` varchar(32) comment '角色编号',
  `SCATALOG` varchar(128) comment '',
  `SROLEKIND` varchar(32) comment '角色分类',
  `SPARENTROLESNAMES` varchar(512) comment '父角色名称',
  `SDESCRIPTION` varchar(1024) comment '描述',
  `SSEQUENCE` int comment '排序',
  `SVALIDSTATE` int comment '状态',
  `VERSION` int NOT NULL comment '版本号',
  PRIMARY KEY (`SID`)
);
alter table sa_oprole add unique IDX_OPROLE_CODE(SCODE);


CREATE TABLE `sa_oprolemanage` (
  `SID` varchar(36) NOT NULL,
  `SORGID` varchar(36) comment '机构ID',
  `SORGFID` varchar(360) comment '机构FID',
  `SPERSONID` varchar(36) comment '人员ID',
  `SROLEID` varchar(36) comment '角色ID',
  `SCREATORID` varchar(36) comment '创建人ID',
  `SCREATETIME` datetime comment '创建时间',
  `VERSION` int
);


CREATE TABLE `sa_oprolemanagement` (
  `SID` varchar(35) NOT NULL,
  `SORGID` varchar(80) NOT NULL,
  `SORGNAME` varchar(255),
  `SORGFID` varchar(2048) NOT NULL,
  `SORGFNAME` varchar(2048),
  `SROLEID` varchar(35) NOT NULL,
  `SCREATORFID` varchar(2048),
  `SCREATORFNAME` varchar(2048),
  `SCREATETIME` datetime,
  `VERSION` int NOT NULL,
  PRIMARY KEY (`SID`)
);

create table SA_PORTALLETS
(
  SPERSONID    VARCHAR(100) not null comment '人员ID',
  SLAYOUTSET   VARCHAR(1024) comment '布局',
  SPANLES      TEXT comment '信息框',
  SCREATORID   VARCHAR(100) comment '创建人ID',
  SCREATORNAME VARCHAR(100) comment '创建人名称',
  SCREATEDATE  DATETIME comment '创建时间'
);

alter table SA_PORTALLETS
  add primary key (SPERSONID);


CREATE TABLE `sa_oprolerole` (
  `SID` varchar(36) NOT NULL,
  `SROLEID` varchar(36) NOT NULL,
  `SPARENTROLE` varchar(36) NOT NULL,
  `VERSION` int,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_opsenddocdist` (
  `SID` varchar(36) NOT NULL,
  `SCUROGNID` varchar(64),
  `SDISOGNID` varchar(64),
  `SDISOGNNAME` varchar(128),
  `SDISOGNFID` varchar(1024),
  `SDISOGNFNAME` varchar(2048),
  `VERSION` decimal(10,0),
  PRIMARY KEY (`SID`)
);
alter table sa_opsenddocdist add unique SA_OPSENDDOCDIST_OGNID(SCUROGNID);


CREATE TABLE `sa_opsenddocogn` (
  `SID` varchar(36) NOT NULL,
  `SCUROGNID` varchar(64),
  `SLIMIT` decimal(10,0) DEFAULT '0',
  `VERSION` decimal(10,0),
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_portalprofiles` (
  `SID` varchar(100) NOT NULL,
  `VERSION` decimal(10,0),
  `SNAME` varchar(100),
  `SPERSONID` varchar(100),
  `SVALUE` longtext,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_portelinfo` (
  `SID` varchar(100) NOT NULL,
  `STHEMETYPE` varchar(10),
  `SPSNDESKINFO` longtext,
  `SPERSONID` varchar(100),
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_project` (
  `SID` decimal(10,0) NOT NULL,
  `SNAME` varchar(50) NOT NULL,
  `CREATE_DATE` datetime,
  `CREATOR` decimal(10,0),
  `CREATE_DEPARTMENT` decimal(10,0) NOT NULL,
  `STARTDATE` varchar(50),
  `ENDDATE` varchar(19),
  `STATUS` varchar(10),
  `DESCRIPTION` varchar(255),
  `PRIORITY` decimal(10,0),
  `VERSION` decimal(10,0),
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_psnmytask` (
  `SID` varchar(64) NOT NULL,
  `SCAPTION` varchar(100),
  `SSTARTDATE` datetime,
  `SENDDATE` datetime,
  `SPRIORITY` int,
  `SCONTENT` varchar(1000),
  `SSTATUS` varchar(30),
  `SAFFAIRSTYPE` int,
  `SCOMPLETERATE` int,
  `SSTARTDATE_AXIS` int,
  `SSENDDATE` int,
  `SWHOUSER` varchar(100),
  `VERSION` int,
  `SSENDDATE_AXIS` int,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_psnschedule` (
  `SID` varchar(100) NOT NULL,
  `SCAPTION` varchar(100),
  `SSTARTDATE` datetime,
  `SENDDATE` datetime,
  `SCONTENT` varchar(4000),
  `SPRIORITY` int,
  `SSTATUS` varchar(50),
  `SSTARTDATE_AXIS` int,
  `SSENDDATE_AXIS` int,
  `SAFFAIRSTYPE` int,
  `SWHOUSER` varchar(100),
  `VERSION` int,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_remindinfo` (
  `SID` varchar(32) NOT NULL,
  `STITLE` varchar(100) COMMENT '标题',
  `SCONTEXT` varchar(1000) COMMENT '描述',
  `SDATETIME` datetime COMMENT '时间',
  `SSTATE` varchar(100) COMMENT '状态',
  `SACTION` varchar(100) COMMENT '操作',
  `SPERSONID` varchar(32) COMMENT '人员ID',
  `SPERSONNAME` varchar(100) COMMENT '人员名称',
  `SORGID` varchar(32) COMMENT '组织ID',
  `SORGNAME` varchar(100) COMMENT '组织名称',
  `VERSION` int,
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_task` (
  `SID` varchar(36) NOT NULL,
  `SPARENTID` varchar(36),
  `SNAME` varchar(255),
  `SCONTENT` text,
  `SREMARK` text,
  `SFLOWID` varchar(36),
  `STYPEID` varchar(36),
  `STYPENAME` varchar(64), 
  `SIMPORTANCEID` varchar(36),
  `SIMPORTANCENAME` varchar(64),
  `SEMERGENCYID` varchar(36),
  `SEMERGENCYNAME` varchar(64),
  `SPROCESS` varchar(255),
  `SACTIVITY` varchar(255),
  `SCREATETIME` datetime,
  `SDISTRIBUTETIME` datetime,
  `SLASTMODIFYTIME` datetime,
  `SWARNINGTIME` datetime,
  `SLIMITTIME` datetime,
  `SESTARTTIME` datetime,
  `SEFINISHTIME` datetime,
  `SASTARTTIME` datetime,
  `SAFINISHTIME` datetime,
  `SEXECUTETIME` datetime,
  `SCPERSONID` varchar(36),
  `SCPERSONNAME` varchar(255),
  `SCDEPTID` varchar(36),
  `SCDEPTNAME` varchar(255),
  `SCOGNID` varchar(36),
  `SCOGNNAME` varchar(255),
  `SEPERSONID` varchar(36),
  `SEPERSONNAME` varchar(255),
  `SEDEPTID` varchar(36),
  `SEDEPTNAME` varchar(255),
  `SEOGNID` varchar(36),
  `SEOGNNAME` varchar(255),
  `SCUSTOMERID` varchar(64),
  `SCUSTOMERNAME` varchar(255),
  `SPROJECTID` varchar(64),
  `SPROJECTNAME` varchar(255),
  `SPLANID` varchar(64),
  `SPLANNAME` varchar(255),
  `SVARIABLE` text,
  `SFAKE` varchar(8),
  `SACTIVE` varchar(8),
  `SLOCK` varchar(36),
  `SSTATUSID` varchar(36),
  `SSTATUSNAME` varchar(64),
  `VERSION` int,
  `SAIID` varchar(36),
  `SCATALOGID` varchar(36),
  `SKINDID` varchar(36),
  `SAIACTIVE` varchar(8),
  `SAISTATUSID` varchar(255),
  `SAISTATUSNAME` varchar(255),
  `SSOURCEID` varchar(36),
  `SCURL` varchar(255),
  `SEURL` varchar(255),
  `SEXECUTEMODE` varchar(32),
  `SEXECUTEMODE2` varchar(255),
  `SPREEMPTMODE` varchar(32),
  `SSEQUENCE` int,
  `SCPERSONCODE` varchar(64),
  `SCPOSID` varchar(36),
  `SCPOSCODE` varchar(64),
  `SCPOSNAME` varchar(255),
  `SCDEPTCODE` varchar(64),
  `SCOGNCODE` varchar(64),
  `SCFID` varchar(1024),
  `SEPERSONCODE` varchar(64),
  `SEPOSID` varchar(36),
  `SEPOSCODE` varchar(64),
  `SEPOSNAME` varchar(255),
  `SEDEPTCODE` varchar(64),
  `SEOGNCODE` varchar(64),
  `SEFID` varchar(1024),
  `SEXECUTORNAMES` varchar(255),
  `SRESPONSIBLE` varchar(8),
  `SCUSTOMERCODE` varchar(64),
  `SPROJECTCODE` varchar(64),
  `SPLANCODE` varchar(64),
  `SDATA1` varchar(128),
  `SDATA2` varchar(128),
  `SDATA3` varchar(128),
  `SDATA4` varchar(128),
  `SWORKTIME` int,
  `SCFNAME` varchar(255),
  `SEFNAME` varchar(255),
  `SHINTS` varchar(1024),
  `SSHORTCUT` varchar(1024),
  `SFRONTID` varchar(36),
  `SFMAKERNAME` varchar(100),
  PRIMARY KEY (`SID`)
);
alter table sa_task add index SA_FLOWID(SFLOWID);
alter table sa_task add index SA_STATUS(SSTATUSID);
alter table sa_task add index SA_TASK_SPROCESS(SPROCESS);
alter table sa_task add index SA_TASK_SDATA_1(SDATA1);
alter table sa_task add index SA_TASK_SPARENTID(SPARENTID);


CREATE TABLE `sa_task_history` (
  `SID` varchar(36) NOT NULL,
  `SPARENTID` varchar(36),
  `SNAME` varchar(255),
  `SCONTENT` longtext,
  `SREMARK` longtext,
  `SFLOWID` varchar(36),
  `STYPEID` varchar(36),
  `STYPENAME` varchar(64),
  `SIMPORTANCEID` varchar(36),
  `SIMPORTANCENAME` varchar(64),
  `SEMERGENCYID` varchar(36),
  `SEMERGENCYNAME` varchar(64),
  `SPROCESS` varchar(255),
  `SACTIVITY` varchar(255),
  `SCREATETIME` datetime,
  `SDISTRIBUTETIME` datetime,
  `SLASTMODIFYTIME` datetime,
  `SWARNINGTIME` datetime,
  `SLIMITTIME` datetime,
  `SESTARTTIME` datetime,
  `SEFINISHTIME` datetime,
  `SASTARTTIME` datetime,
  `SAFINISHTIME` datetime,
  `SEXECUTETIME` datetime,
  `SCPERSONID` varchar(36),
  `SCPERSONNAME` varchar(255),
  `SCDEPTID` varchar(36),
  `SCDEPTNAME` varchar(255),
  `SCOGNID` varchar(36),
  `SCOGNNAME` varchar(255),
  `SEPERSONID` varchar(36),
  `SEPERSONNAME` varchar(255),
  `SEDEPTID` varchar(36),
  `SEDEPTNAME` varchar(255),
  `SEOGNID` varchar(36),
  `SEOGNNAME` varchar(255),
  `SCUSTOMERID` varchar(64),
  `SCUSTOMERNAME` varchar(255),
  `SPROJECTID` varchar(64),
  `SPROJECTNAME` varchar(255),
  `SPLANID` varchar(64),
  `SPLANNAME` varchar(255),
  `SVARIABLE` longtext,
  `SFAKE` varchar(8),
  `SACTIVE` varchar(8),
  `SLOCK` varchar(36),
  `SSTATUSID` varchar(36),
  `SSTATUSNAME` varchar(64),
  `VERSION` int,
  `SAIID` varchar(36),
  `SCATALOGID` varchar(36),
  `SKINDID` varchar(36),
  `SAIACTIVE` varchar(8),
  `SAISTATUSID` varchar(255),
  `SAISTATUSNAME` varchar(255),
  `SSOURCEID` varchar(36),
  `SCURL` varchar(255),
  `SEURL` varchar(255),
  `SEXECUTEMODE` varchar(32),
  `SEXECUTEMODE2` varchar(255),
  `SPREEMPTMODE` varchar(32),
  `SSEQUENCE` int,
  `SCPERSONCODE` varchar(64),
  `SCPOSID` varchar(36),
  `SCPOSCODE` varchar(64),
  `SCPOSNAME` varchar(255),
  `SCDEPTCODE` varchar(64),
  `SCOGNCODE` varchar(64),
  `SCFID` varchar(1024),
  `SEPERSONCODE` varchar(64),
  `SEPOSID` varchar(36),
  `SEPOSCODE` varchar(64),
  `SEPOSNAME` varchar(255),
  `SEDEPTCODE` varchar(64),
  `SEOGNCODE` varchar(64),
  `SEFID` varchar(1024),
  `SEXECUTORNAMES` varchar(255),
  `SRESPONSIBLE` varchar(8),
  `SCUSTOMERCODE` varchar(64),
  `SPROJECTCODE` varchar(64),
  `SPLANCODE` varchar(64),
  `SDATA1` varchar(128),
  `SDATA2` varchar(128),
  `SDATA3` varchar(128),
  `SDATA4` varchar(128),
  `SWORKTIME` int,
  `SCFNAME` varchar(255),
  `SEFNAME` varchar(255),
  `SHINTS` varchar(1024),
  `SSHORTCUT` varchar(1024),
  `SFRONTID` varchar(36),
  `SFMAKERNAME` varchar(100),
  PRIMARY KEY (`SID`)
);


CREATE TABLE `sa_tasktype` (
  `SID` varchar(50) NOT NULL,
  `SNAME` varchar(500),
  `SCONCEPT` varchar(500),
  `SNEWACTIVITY` varchar(500),
  `SEXECUTEACTIVITY` varchar(500),
  `SKIND` varchar(500),
  `VERSION` decimal(10,0),
  PRIMARY KEY (`SID`)
);

CREATE TABLE `sa_task_timelimit`  (
  `sID` varchar(32) NOT NULL,
  `SPROCESSID` varchar(255) COMMENT '流程标识',
  `SPROCESSNAME` varchar(512) COMMENT '流程名称',
  `SACTIVITY` varchar(255)  COMMENT '流程环节',
  `SACTIVITYNAME` varchar(512) COMMENT '环节名称',
  `SDLIMIT` int COMMENT '时限（天）',
  `VERSION` int,
  PRIMARY KEY (`sID`) 
);


CREATE TABLE `sa_worklog` (
  `SID` varchar(32) NOT NULL,
  `SLOCK` varchar(100),
  `SNAME` varchar(100),
  `SCUSTOMERNAME` varchar(100),
  `SIMPORTANCENAME` varchar(100),
  `SPLANNAME` varchar(100),
  `SPROJECTNAME` varchar(100),
  `SEMERGENCYNAME` varchar(100),
  `SLIMITTIME` datetime,
  `SCONTENT` longtext,
  `SCREATORFID` varchar(1024),
  `SCREATOFNAME` varchar(1024),
  `SCREATETIME` datetime,
  `FEXTEND01` varchar(100),
  `VERSION` int,
  PRIMARY KEY (`SID`)
);

CREATE TABLE `sa_mailset`  (
  `SID` varchar(32) NOT NULL,
  `SMAIL` varchar(100) COMMENT '邮箱地址',
  `SACCOUNT` varchar(100) COMMENT '密码',
  `SNAME` varchar(200) COMMENT '发信名称',
  `STYPE` varchar(20) COMMENT '邮箱类型',
  `SSENDHOST` varchar(100) COMMENT '发件服务器',
  `SSENDPOST` varchar(10) COMMENT '发件端口',
  `SISSSL` varchar(10) COMMENT '发件是否启用SSL',
  `SRECHOST` varchar(100) COMMENT '收件服务器',
  `SRECPORT` varchar(20) COMMENT '收件端口',
  `SRECSSL` varchar(10) COMMENT '收件是否启用SSL',
  `SISPUB` varchar(10) COMMENT '是否公用',
  `FCREATEPSNFID` varchar(2048) COMMENT '创建人FID',
  `FCREATEPSNID` varchar(36) COMMENT '创建人ID',
  `FCREATEPSNNAME` varchar(100) COMMENT '创建人名称',
  `FCREATEDEPTID` varchar(36) COMMENT '部门ID',
  `FCREATEDEPTNAME` varchar(200) COMMENT '部门名称',
  `FCREATEOGNID` varchar(36) COMMENT '机构ID',
  `FCREATEOGNNAME` varchar(200) COMMENT '机构名称',
  `FCREATEORGID` varchar(36) COMMENT '组织ID',
  `FCREATEORGNAME` varchar(200) COMMENT '组织名称',
  `FCREATETIME` datetime(0) COMMENT '创建时间',
  `VERSION` int,
  PRIMARY KEY (`SID`)
);

CREATE TABLE `im_message` (
  `sID` varchar(32) NOT NULL,
  `favatar` varchar(1000) DEFAULT NULL COMMENT '发送人头像',
  `fusername` varchar(100) DEFAULT NULL COMMENT '发送人昵称',
  `fid` varchar(100) DEFAULT NULL COMMENT '发送人ID',
  `content` text COMMENT '消息内容',
  `tavatar` varchar(1000) DEFAULT NULL COMMENT '目标头像',
  `tname` varchar(100) DEFAULT NULL COMMENT '目标名称',
  `tsign` varchar(500) DEFAULT NULL COMMENT '对方签名',
  `stype` varchar(100) DEFAULT NULL COMMENT '消息类型',
  `tusername` varchar(100) DEFAULT NULL COMMENT '目标昵称',
  `state` int DEFAULT NULL COMMENT '状态',
  `stime` datetime DEFAULT NULL COMMENT '时间',
  `VERSION` int DEFAULT NULL,
  `groupname` varchar(100) DEFAULT NULL COMMENT '群组名称',
  `tid` varchar(100) DEFAULT NULL COMMENT '目标ID',
  PRIMARY KEY (`sID`),
  KEY `im_message_fid` (`fid`),
  KEY `im_message_tid` (`tid`),
  KEY `im_message_state` (`state`),
  KEY `im_message_stype` (`stype`)
);

create or replace view sa_oporg_mphone_view as
select sID,
       sParent,
       sCode,
       sName,
       ifnull(sOrgKindID, 'org') sOrgKindID,
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
       concat(p.sName,'(',ifnull(p.smobilephone,''),')') as sname,
       'psm' sOrgKindID,
       concat(t.sFID,'/',p.sid) as sfid,
       t.sValidState,
       concat(t.sfcode,'/',p.scode) as sfcode,
       concat(t.sFName,'/',p.sname) as sfname,
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

CREATE OR REPLACE VIEW `news_tables` AS select `a`.`SID` AS `SID`,`a`.`VERSION` AS `VERSION`,`a`.`FNEWSTITLE` AS `FNEWSTITLE`,`a`.`FSTATE` AS `FSTATE`,`a`.`FPEOPLE` AS `FPEOPLE`,`a`.`FTIME` AS `FTIME`,`a`.`FOPENSCOPE` AS `FOPENSCOPE`,`b`.`NEWS_NUMBER` AS `NEWS_NUMBER`,`b`.`NEWS_PERSON` AS `NEWS_PERSON`,`b`.`YETPERSON` AS `YETPERSON`,`a`.`FOPENSCOPEID` AS `FOPENSCOPEID` from (`cyea_news_release` `a` left join `cyea_news_count` `b` on((`a`.`SID` = `b`.`NEWS_RELEASEID`)));


CREATE OR REPLACE VIEW `sa_loginlog_view` AS select `t`.`SID` AS `sid`,`a`.`SNAME` AS `sname`,`a`.`SCODE` AS `scode`,`a`.`SFNAME` AS `sfname`,`t`.`SLOGINIP` AS `sloginip`,`t`.`SLOGINTIME` AS `slogintime`,`b`.`SMOBILEPHONE` AS `smobilephone`,`t`.`VERSION` AS `version` from ((`sa_loginlog` `t` join `sa_oporg` `a`) join `sa_opperson` `b`) where (`t`.`SUSERID` = `a`.`SPERSONID` and `b`.`SID` = `a`.`SPERSONID`);


CREATE OR REPLACE VIEW `sa_onlineinfo_view` AS select `t`.`SID` AS `sid`,`a`.`SNAME` AS `sname`,`a`.`SCODE` AS `scode`,`a`.`SFNAME` AS `sfname`,`t`.`SLOGINIP` AS `sloginip`,`t`.`SLOGINDATE` AS `slogindate`,`b`.`STITLE` AS `STITLE`,`b`.`SMOBILEPHONE` AS `smobilephone`,`t`.`SSESSIONID` AS `ssessionid`,`t`.`SSERVICEIP` AS `sserviceip`,`t`.`VERSION` AS `version` from ((`sa_onlineinfo` `t` join `sa_oporg` `a`) join `sa_opperson` `b`) where ((`t`.`SUSERID` = `a`.`SPERSONID`) and (`b`.`SID` = `a`.`SPERSONID`));


CREATE OR REPLACE VIEW `sa_opmobilelog_view` AS select distinct `p`.`SID` AS `sid`,`p`.`SCODE` AS `scode`,`p`.`SNAME` AS `sname`,`o`.`SFNAME` AS `sfname`,`p`.`SMOBILEPHONE` AS `smobilephone`,`p`.`VERSION` AS `version` from ((`v8sys`.`sa_opmobilelog` `l` join `v8sys`.`sa_oporg` `o` on((`o`.`SPERSONID` = `l`.`SUSERID`))) join `v8sys`.`sa_opperson` `p` on((`p`.`SID` = `l`.`SUSERID`)));


CREATE OR REPLACE VIEW `sa_oporg_recycled` AS select `o`.`SID` AS `sID`,`o`.`SPARENT` AS `sParent`,`o`.`SCODE` AS `sCode`,`o`.`SNAME` AS `sName`,ifnull(`o`.`SORGKINDID`,'org') AS `sOrgKindID`,`o`.`SFID` AS `sFID`,`o`.`SVALIDSTATE` AS `sValidState`,`o`.`SFCODE` AS `SFCODE`,`o`.`SFNAME` AS `sFName`,`o`.`SSEQUENCE` AS `sSequence`,`o`.`SDESCRIPTION` AS `SDESCRIPTION`,`o`.`SADDRESS` AS `SADDRESS`,`o`.`VERSION` AS `version` from `sa_oporg` `o` where (`o`.`SVALIDSTATE` = -(1)) union all select `p`.`SID` AS `sID`,`t`.`SID` AS `sParent`,`p`.`SCODE` AS `sCode`,`p`.`SNAME` AS `sName`,'psm' AS `sOrgKindID`,concat(`t`.`SFID`,'/',`p`.`SID`) AS `concat(t.sFID,'/',p.sid)`,`t`.`SVALIDSTATE` AS `sValidState`,concat(`t`.`SFCODE`,'/',`p`.`SCODE`) AS `concat(t.sfcode,'/',p.scode)`,concat(`t`.`SFNAME`,'/',`p`.`SNAME`) AS `concat(t.sFName,'/',p.sname)`,`t`.`SSEQUENCE` AS `sSequence`,`t`.`SDESCRIPTION` AS `SDESCRIPTION`,`t`.`SADDRESS` AS `SADDRESS`,`p`.`VERSION` AS `version` from (`sa_opperson` `p` left join `sa_oporg` `t` on((`p`.`SMAINORGID` = `t`.`SID`))) where ((`p`.`SMAINORGID` is not null) and (`p`.`SVALIDSTATE` = -(1)));


CREATE OR REPLACE VIEW `sa_oporg_view` AS select `o`.`SID` AS `sID`,`o`.`SPARENT` AS `sParent`,`o`.`SCODE` AS `sCode`,`o`.`SNAME` AS `sName`,ifnull(`o`.`SORGKINDID`,'org') AS `sOrgKindID`,`o`.`SFID` AS `sFID`,`o`.`SVALIDSTATE` AS `sValidState`,`o`.`SFCODE` AS `SFCODE`,`o`.`SFNAME` AS `sFName`,`o`.`SSEQUENCE` AS `sSequence`,`o`.`SDESCRIPTION` AS `SDESCRIPTION`,`o`.`SADDRESS` AS `SADDRESS`,'' AS `smobilephone`,`o`.`VERSION` AS `version` from `sa_oporg` `o` where ((`o`.`SVALIDSTATE` = 1) and (`o`.`SORGKINDID` <> 'psm')) union all select `p`.`SID` AS `sID`,`t`.`SID` AS `sParent`,`p`.`SCODE` AS `sCode`,`p`.`SNAME` AS `sName`,'psm' AS `sOrgKindID`,concat(`t`.`SFID`,'/',`p`.`SID`) AS `CONCAT(t.sFID,'/',p.sid)`,`t`.`SVALIDSTATE` AS `sValidState`,concat(`t`.`SFCODE`,'/',`p`.`SCODE`) AS `CONCAT(t.sfcode,'/',p.scode)`,concat(`t`.`SFNAME`,'/',`p`.`SNAME`) AS `CONCAT(t.sFName,'/',p.sname)`,(`t`.`SLEVEL` + `p`.`SSEQUENCE`) AS `sSequence`,`t`.`SDESCRIPTION` AS `SDESCRIPTION`,`t`.`SADDRESS` AS `SADDRESS`,ifnull(`p`.`SMOBILEPHONE`,'') AS `smobilephone`,`p`.`VERSION` AS `version` from (`sa_opperson` `p` left join `sa_oporg` `t` on((`p`.`SMAINORGID` = `t`.`SID`))) where ((`p`.`SMAINORGID` is not null) and (`t`.`SVALIDSTATE` = 1) and (`p`.`SVALIDSTATE` = 1));


CREATE OR REPLACE VIEW `sa_task_monitor` AS select `t`.`SID` AS `SID`,`t`.`SPARENTID` AS `SPARENTID`,`t`.`SNAME` AS `SNAME`,`t`.`SCONTENT` AS `SCONTENT`,`t`.`SREMARK` AS `SREMARK`,`t`.`SFLOWID` AS `SFLOWID`,`t`.`STYPEID` AS `STYPEID`,`t`.`STYPENAME` AS `STYPENAME`,`t`.`SIMPORTANCEID` AS `SIMPORTANCEID`,`t`.`SIMPORTANCENAME` AS `SIMPORTANCENAME`,`t`.`SEMERGENCYID` AS `SEMERGENCYID`,`t`.`SEMERGENCYNAME` AS `SEMERGENCYNAME`,`t`.`SPROCESS` AS `SPROCESS`,`t`.`SACTIVITY` AS `SACTIVITY`,`t`.`SCREATETIME` AS `SCREATETIME`,`t`.`SDISTRIBUTETIME` AS `SDISTRIBUTETIME`,`t`.`SLASTMODIFYTIME` AS `SLASTMODIFYTIME`,`t`.`SWARNINGTIME` AS `SWARNINGTIME`,`t`.`SLIMITTIME` AS `SLIMITTIME`,`t`.`SESTARTTIME` AS `SESTARTTIME`,`t`.`SEFINISHTIME` AS `SEFINISHTIME`,`t`.`SASTARTTIME` AS `SASTARTTIME`,`t`.`SAFINISHTIME` AS `SAFINISHTIME`,`t`.`SEXECUTETIME` AS `SEXECUTETIME`,`t`.`SCPERSONID` AS `SCPERSONID`,`t`.`SCPERSONNAME` AS `SCPERSONNAME`,`t`.`SCDEPTID` AS `SCDEPTID`,`t`.`SCDEPTNAME` AS `SCDEPTNAME`,`t`.`SCOGNID` AS `SCOGNID`,`t`.`SCOGNNAME` AS `SCOGNNAME`,`t`.`SEPERSONID` AS `SEPERSONID`,`t`.`SEPERSONNAME` AS `SEPERSONNAME`,`t`.`SEDEPTID` AS `SEDEPTID`,`t`.`SEDEPTNAME` AS `SEDEPTNAME`,`t`.`SEOGNID` AS `SEOGNID`,`t`.`SEOGNNAME` AS `SEOGNNAME`,`t`.`SCUSTOMERID` AS `SCUSTOMERID`,`t`.`SCUSTOMERNAME` AS `SCUSTOMERNAME`,`t`.`SPROJECTID` AS `SPROJECTID`,`t`.`SPROJECTNAME` AS `SPROJECTNAME`,`t`.`SPLANID` AS `SPLANID`,`t`.`SPLANNAME` AS `SPLANNAME`,`t`.`SVARIABLE` AS `SVARIABLE`,`t`.`SFAKE` AS `SFAKE`,`t`.`SACTIVE` AS `SACTIVE`,`t`.`SLOCK` AS `SLOCK`,`t`.`SSTATUSID` AS `SSTATUSID`,`t`.`SSTATUSNAME` AS `SSTATUSNAME`,`t`.`VERSION` AS `VERSION`,`t`.`SAIID` AS `SAIID`,`t`.`SCATALOGID` AS `SCATALOGID`,`t`.`SKINDID` AS `SKINDID`,`t`.`SAIACTIVE` AS `SAIACTIVE`,`t`.`SAISTATUSID` AS `SAISTATUSID`,`t`.`SAISTATUSNAME` AS `SAISTATUSNAME`,`t`.`SSOURCEID` AS `SSOURCEID`,`t`.`SCURL` AS `SCURL`,`t`.`SEURL` AS `SEURL`,`t`.`SEXECUTEMODE` AS `SEXECUTEMODE`,`t`.`SEXECUTEMODE2` AS `SEXECUTEMODE2`,`t`.`SPREEMPTMODE` AS `SPREEMPTMODE`,`t`.`SSEQUENCE` AS `SSEQUENCE`,`t`.`SCPERSONCODE` AS `SCPERSONCODE`,`t`.`SCPOSID` AS `SCPOSID`,`t`.`SCPOSCODE` AS `SCPOSCODE`,`t`.`SCPOSNAME` AS `SCPOSNAME`,`t`.`SCDEPTCODE` AS `SCDEPTCODE`,`t`.`SCOGNCODE` AS `SCOGNCODE`,`t`.`SCFID` AS `SCFID`,`t`.`SEPERSONCODE` AS `SEPERSONCODE`,`t`.`SEPOSID` AS `SEPOSID`,`t`.`SEPOSCODE` AS `SEPOSCODE`,`t`.`SEPOSNAME` AS `SEPOSNAME`,`t`.`SEDEPTCODE` AS `SEDEPTCODE`,`t`.`SEOGNCODE` AS `SEOGNCODE`,`t`.`SEFID` AS `SEFID`,`t`.`SEXECUTORNAMES` AS `SEXECUTORNAMES`,`t`.`SRESPONSIBLE` AS `SRESPONSIBLE`,`t`.`SCUSTOMERCODE` AS `SCUSTOMERCODE`,`t`.`SPROJECTCODE` AS `SPROJECTCODE`,`t`.`SPLANCODE` AS `SPLANCODE`,`t`.`SDATA1` AS `SDATA1`,`t`.`SDATA2` AS `SDATA2`,`t`.`SDATA3` AS `SDATA3`,`t`.`SDATA4` AS `SDATA4`,`t`.`SWORKTIME` AS `SWORKTIME`,`t`.`SCFNAME` AS `SCFNAME`,`t`.`SEFNAME` AS `SEFNAME`,`t`.`SHINTS` AS `SHINTS`,`t`.`SSHORTCUT` AS `SSHORTCUT`,`t`.`SFRONTID` AS `SFRONTID`,`t`.`SFMAKERNAME` AS `SFMAKERNAME`,`p`.`SNAME` AS `sflowName` from (`sa_task` `t` join `sa_task` `p`) where ((`t`.`SID` = `p`.`SPARENTID`) and (`t`.`SFLOWID` = `t`.`SID`));


CREATE OR REPLACE VIEW `sa_vpnlog_view` AS select distinct `o`.`SID` AS `sid`,`o`.`SCODE` AS `scode`,`o`.`SNAME` AS `sname`,`o`.`SFNAME` AS `sfname`,`o`.`VERSION` AS `version`,`l`.`SLOGINTIME` AS `slogintime` from (`sa_loginlog` `l` join `sa_oporg` `o` on `o`.`SPERSONID` = `l`.`SUSERID`) where ((`l`.`SLOGINIP` = '59.216.32.182') and (`o`.`SID` is not null) and (not(exists(select `p`.`SUSERID` from `sa_opmobilelog` `p` where (`l`.`SUSERID` = `p`.`SUSERID`)))) and (`l`.`SLOGINTIME` = (select max(`lg`.`SLOGINTIME`) from `sa_loginlog` `lg` where (`lg`.`SUSERID` = `l`.`SUSERID`)))) order by `l`.`SLOGINTIME` desc;


INSERT INTO `sa_docnamespace` VALUES ('defaultDocNameSpace', '文档中心', '127.0.0.1', '8080', 'http://127.0.0.1:8080/DocServer', '37', '1', '0');
INSERT INTO `sa_docnode` VALUES ('root', null, '文档中心', '', null, 'dir', '/root', '/文档中心', '', '', '', null, '', '', '', '', '', '', null, '', '', null, '4', '', '', '', null, 'defaultDocNameSpace', '', '', '1');
INSERT INTO `sa_opmanagetype` VALUES ('systemManagement', '系统管理', 'systemManagement', 'ogn', ' 机构 部门 岗位 工作组 人员', null, null, '2');
INSERT INTO `sa_oporg` VALUES ('ORG01', '管理员', 'TULIN', '', '/管理员', '/TULIN', '/ORG01.ogn', 'ogn', '1', null, '1', '', '', '', '', '', '', '', '16', '', '96');
INSERT INTO `sa_oporg` VALUES ('PSN01@ORG01', 'system', 'SYSTEM', '', '/管理员/system', '/TULIN/SYSTEM', '/ORG01.ogn/PSN01@ORG01.psm', 'psm', '1', 'ORG01', '2', '', '', '', '', '', 'PSN01', 'nkLeaf', '15', '', '1');
INSERT INTO sa_opauthorize(`SID`, `SORGID`, `SORGNAME`, `SORGFID`, `SORGFNAME`, `SAUTHORIZEROLEID`, `SDESCRIPTION`, `SCREATORFID`, `SCREATORFNAME`, `SCREATETIME`, `VERSION`, `SROLELEVEL`)VALUES('AHR01', 'PSN01@ORG01', '管理员', '/ORG01.ogn/PSN01@ORG01.psm', '/管理员/system', 'RL01', '超级管理员', '/ORG01.ogn/PSN01@ORG01.psm', '/tlv8/system', '2020/11/3', 0, '0');
INSERT INTO `sa_oppermission` VALUES ('01B955F5829F44219FB2806797B45A3B', 'RL01', '/OA/doc/process/baseCode/docSecretLevel/docSecretLevelProcess', '/其它/基础设置/公文密级', 'docSecretLevelActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO `sa_oppermission` VALUES ('098A9455CDDD44299140A281D3DE875F', 'RL01', '/OA/doc/process/baseCode/docExigenceLevel/docExigenceLevelProcess', '/其它/基础设置/公文紧急程度', 'docExigenceLevelActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO `sa_oppermission` VALUES ('182217CC50E84AB2A6EA3085A18BA621', 'RL01', '/SA/doc/docSetting/docSettingProcess', '/系统管理/文档/文档配置', 'mainActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO `sa_oppermission` VALUES ('1B1AF2E6140A445796ABE0442CBB54DE', 'RL01', '/SA/doc/docPermission/docPermissionProcess', '/系统管理/文档/文档关联', 'mainActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO `sa_oppermission` VALUES ('1F4272327EEF446B8005AEB1C1A42E0B', 'RL01', '/SA/OPM/authorization/authorizationProcess', '/系统管理/组织权限/授权管理-分级', 'gradeActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('265569FC504443E1B134592E3B5FCC5C', 'RL01', '/SA/OPM/log/logProcess', '/系统管理/组织权限/组织机构日志', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('3566316F52F84896ACE1EF9BF42018BE', 'RL01', '/SA/OPM/organization/organizationProcess', '/系统管理/组织权限/组织管理', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('38A312285E834995AFCAC32E2D50265A', 'RL01', '/SA/doc/docCenter/docCenterProcess', '/系统管理/文档/文档中心', 'docCenter', '', '', '', '0', '', null, '1', '0');
INSERT INTO `sa_oppermission` VALUES ('3B051D390A7B4426B2663AE27F3A45A8', 'RL01', '/OA/docrs/process/baseCode/docRedHead/docRedHeadProcess', '/其它/红头维护', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('5F62DE289C8648689D20D9370ACAE21C', 'RL01', '/SA/OPM/role/roleProcess', '/系统管理/组织权限/角色管理', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('660E9AF723874169A20E80C50B6D6C04', 'RL01', '/OA/docrs/process/docArchive/docArchiveProcess', '/其它/公文归档查询', 'mainActivity', '', '', '', '0', '', null, '1', '2');
INSERT INTO `sa_oppermission` VALUES ('6A02EEF862114421A0EC8BD3A4BD4222', 'RL01', '/SA/OPM/authorization/authorizationProcess', '/系统管理/组织权限/授权管理', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('6F21B10EAECC46BE95A7A9349D0B041F', 'RL01', '/SA/OPM/grade/gradeProcess', '/系统管理/组织权限/分级管理', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('7A17C36CD1EF4DC1A8C42B805A716556', 'RL01', '/OA/softwareDownLoad/process/software/softwareProcess', '/文件共享/目录维护', 'staticActivity1', '', '', '', '0', '', null, '1', '0');
INSERT INTO `sa_oppermission` VALUES ('82E555A346274CE092AE25B4DD0BFBE8', 'RL01', '/SA/log/logProcess', '/系统管理/系统工具/操作日志', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('84EBC08BB9D64F4092267873339FBEE2', 'RL01', '/SA/OPM/repairTools/repairToolsProcess', '/系统管理/组织权限/修复工具', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('851787396B3E4D3092B05A8B113131DD', 'RL01', '/OA/softwareDownLoad/process/software/softwareProcess', '/文件共享/目录维护', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('9D8D492C5D49460E9DDA5F6CED4B1545', 'RL01', '/SA/update/updateProcess', '/系统管理/系统工具/软件更新', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('9DC6B147AC1F478E9DFE5199F4EC8BCF', 'RL01', '/SA/online/onlineProcess', '/系统管理/系统工具/在线用户分级', 'gradeOnlineUserActivity', '', '', '', '0', '', null, '1', '6');
INSERT INTO `sa_oppermission` VALUES ('A84780D2CEF64C5B9DE9947ECD13ED28', 'RL01', '/SA/doc/docSearch/docSearchProcess', '/系统管理/文档/文档检索', 'mainActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO `sa_oppermission` VALUES ('AC305CCADA8D4947849F0E4F539077B0', 'RL01', '/OA/docrs/process/setReceiveDocTemplate/setReceiveDocTemplateProcess', '/其它/收发文基础设置/设置收发文模版', 'mainActivity', '', '', '', '0', '', null, '1', '2');
INSERT INTO `sa_oppermission` VALUES ('AD0706B27A22492BA4E979D5DF5CD2AA', 'RL01', '/OA/docrs/process/baseCode/docType/docTypePrecess', '/其它/收发文基础设置/设置公文类别', 'mainActivity', '', '', '', '0', '', null, '1', '2');
INSERT INTO `sa_oppermission` VALUES ('BCBEB3FEA6DB4283AD2494254FDBCDAE', 'RL01', '/SA/online/onlineProcess', '/系统管理/系统工具/在线用户', 'mainActivity', '', '', '', '0', '', null, '1', '3');
INSERT INTO `sa_oppermission` VALUES ('C6914BAA2E84424C901DBF8FD95144D7', 'RL01', '/SA/OPM/management/managementProcess', '/系统管理/组织权限/业务管理权限', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('C6B3C40F53200001F12719F019995DA0', 'RL01', '/SA/OPM/recycled/recycledProcess', '/系统管理/组织机构/回收站', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C72FB49C224000015E2927B019E010D9', 'RL01', '/OA/Report/reportImgShowProcess', '/统计报表/时间维度统计图表展示', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C72FF32DC44000015C101BD21A0010B4', 'RL01', '/OA/Report/UserCountByCounty/usercountbycountyProcess', '/统计报表/各县系统用户统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C73002B742B000017654CC60934013C6', 'RL01', '/OA/Report/personusedayReport/mainActivityProcess', '/统计报表/用户使用系统时间统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7304519F47000017F591F2B1FD6EB50', 'RL01', '/OA/Report/OnlineInfoCountOnWeek/mainActivityProcess', '/统计报表/按在线用户量统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7304EB047300001DED41F75D51012B9', 'RL01', '/OA/Report/ConcurrentOnDay/mainActivityProcess', '/统计报表/天内并发量统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7305130B87000018EA718841EB06700', 'RL01', '/OA/Report/UserIncrement/userincrementProcess', '/统计报表/用户增长量统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7305169AE000001C7741ACC284019D3', 'RL01', '/OA/log/LoginLogProcess', '/系统管理/系统工具/用户登录统计', 'LoginLog', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7305169AED0000167D26C507440DE00', 'RL01', '/SA/log/mobileAppLogProcess', '/系统管理/系统工具/手机用户统计', 'mobileAppLog', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7305169AF800001EA2316506D70FAF0', 'RL01', '/SA/log/VPNLogProcess', '/系统管理/系统工具/VPN用户统计', 'VPNLog', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7305169B1900001B9731A801F5D9020', 'RL01', '/SA/MobileNumber/MobileNumbersProcess', '/系统管理/系统工具/移动号码段维护', 'MobileNumbers', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C73052FED5200001504DBB4012607480', 'RL01', '/OA/Report/RecvFileCountOnWeek/mainActivityProcess', '/统计报表/周来文登记数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C73053D2C5B000017ACC110014DC1EDD', 'RL01', '/OA/Report/SendFileCountOnWeek/mainActivityProcess', '/统计报表/周发文数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C73054DDF8700001A473530D17AF1669', 'RL01', '/OA/Report/RecvFileCountByMonth/mainActivityProcess', '/统计报表/按月收文登记数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C730560A70B00001914298E4E92056A0', 'RL01', '/OA/Report/SendFileCountByMonth/mainActivityProcess', '/统计报表/按月发文数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C73058D190100001504B2A1B59C01545', 'RL01', '/OA/Report/RecvFileCountByMonth/reportAllActivityProcess', '/统计报表/收文登记数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C73059667980000122D8187815D01037', 'RL01', '/OA/Report/SendFileCountByMonth/reportAllActivityProcess', '/统计报表/发文数统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C730989BF62000012D6E164C164082F0', 'RL01', '/OA/Report/RecvFileCountByMonth/reportProcess', '/统计报表/收文数据对比', 'reportLineActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C730989BF7400001AC5C1EF079D02590', 'RL01', '/OA/Report/SendFileCountByMonth/reportProcess', '/统计报表/发文数据对比', 'reportLineActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C731F08F25A00001934520F0BE70F700', 'RL01', '/OA/ShortMessage/process/SendMsgFromSystem/sendMsgFromSystemProcess', '/发送短信/从系统通讯录发短信', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C731F08F2A80000183FC1B434C5818AA', 'RL01', '/OA/ShortMessage/process/SendMsgQuery/sendMsgQueryProcess', '/发送短信/已发短信查询', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7362262EE800001931710941E606820', 'RL01', '/OA/personal/process/personalInfo/personalProcess', '/其它/个人痕迹查询', 'markActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C79092E0D2C00001FFD6E8A61810C900', 'RL01', '/OA/hr/wage/ListActivityPersonProcess', '/其它/个人薪资查询', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7A4DAEE8A500001ADED569E1537A090', 'RL01', '/OA/docrs/process/sendDoc/AllSendedProcess', '/其它/已发文件', 'AllSendedActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7A5BEC63EE0000123FA2804F3571E8B', 'RL01', '/OA/CheckWorkAtten/baseProcess', '/工作管理/考勤管理/上班时间设置', 'workDateActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7B2671830000001A63111A019609FA0', 'RL01', '/OA/Project/Manage/mainProcess', '/州级项目审批情况/项目审批情况统计', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7B2F1D86D00000168DBE4C02B1013E2', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况登记(州内)', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7B2F53FD6A0000169FDDF90149019F0', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况查看(州内)', 'lookActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7B2F53FD8C00001BC5C1F901078EE00', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况登记(州外)', 'mainActivity1', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7B2F53FDB3000019EF0141B73404B10', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况查看(州外)', 'lookActivity1', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C7D8FEE6EFE00001B6751A1E12303070', 'RL01', '/OA/fawenDanwei/mainProcess', '/系统管理/系统工具/允许发文单位', 'mainActivity', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C82E3F2608B00001E83E18231E2A18BA', 'RL01', '/SA/theme/bgProcess', '/系统管理/系统工具/登录页背景配置', 'mainActivity', '', '', '', null, '', null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C8336D112D0000013CE01830EDF01F56', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/填写工作日志', 'myWorkLog', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C8336D112E7000016B60624914781E9A', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/我的工作日志', 'MyWorklogList', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C8336D112FE00001DDAFA0612340187E', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/工作日志审批', 'waitWorkLog', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C8336D1131300001CB7E1FF017531313', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/工作日志汇总', 'allWorklogList', '', '', '', null, '', null, null, '0');
INSERT INTO `sa_oppermission` VALUES ('C8373BC0E94000011694194017001AE0', 'RL01', '/OA/CheckWorkAtten/personnelProcess', '/考勤管理/个人考勤', 'personnelActivity', '', '', '', null, '', null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C8373BC0EB8000013B42146CBC7010F0', 'RL01', '/OA/CheckWorkAtten/personnelProcess', '/考勤管理/考勤记录', 'DayChekinList', '', '', '', null, '', null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C8373BC0EE1000011C8215C05B15EFB0', 'RL01', '/OA/CheckWorkAtten/personnelProcess', '/考勤管理/考勤统计', 'reportActivity', '', '', '', null, '', null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C8A5A0B5BED00001ADC31CC0968311E8', 'RL01', 'SA/services/pcProcess', '/系统管理/系统工具/服务器监控', 'mainActivity', '', '', '', null, '', null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C8D3540ADE800001392115941C10100B', 'RL01', '/OA/docrs/process/sendDoc/sendDocProcess', '/发文管理/删除发文', 'SendDel', '', '', '', null, '', null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C8DC2295A8E00001B8DE85203AD016AE', 'RL01', '/SA/task/taskCenter/process', '/任务中心/任务列表', 'mainActivity', '', '', '', null, '', null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C8DC2295A9600001BBE919691C301C37', 'RL01', '/SA/process/monitor/process', '/任务中心/流程监控', 'mainActivity', '', '', '', null, '', null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C8DC2295A9E00001D89590F5198012BC', 'RL01', '/SA/task/unFlowmana/process', '/任务中心/任务处理', 'mainActivity', '', '', '', null, '', null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C8DC2295AA6000017BF83A6965004D50', 'RL01', '/flw/dwr/process', '/任务中心/流程设计', 'vml-dwr-editer', '', '', '', null, '', null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C94E5AE783D000017BE612A0165419DF', 'RL01', '029AB9CC9C5D4378B25C9BCB6C32B33D', '/采购管理/采购登记', 'bizActivity2', null, null, null, null, null, null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C94E5AE784A0000163C271C810651321', 'RL01', '029AB9CC9C5D4378B25C9BCB6C32B33D', '/采购管理/采购审批', 'bizActivity4', null, null, null, null, null, null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C94EE9000440000135A1A7F050121CA8', 'RL01', '/SA/task/taskCenter/process', '/任务中心/流程监控', 'monitorActivity', null, null, null, null, null, null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C94EE9000540000138D11EF078DC6810', 'RL01', '/SA/task/taskCenter/process', '/任务中心/任务处理', 'unFlowmanaActivity', null, null, null, null, null, null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C94EE90006D000013BDE12401F401700', 'RL01', '/SA/task/taskCenter/process', '/任务中心/系统提醒', 'reminActivity', null, null, null, null, null, null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('C94F4E969D600001886F19D05E501F55', 'RL01', '/SA/OPM/recycled/recycledProcess', '/系统管理/组织机构/回收站-分级', 'gradeRecycledActivity', null, null, null, null, null, null, null, '1');
INSERT INTO `sa_oppermission` VALUES ('D0EC487F72FF4D768C92520A8B90F458', 'RL01', '/OA/docrs/process/baseCode/docArchiveTree/docArchiveTreePrecess', '/其它/收发文基础设置/设置公文归档', 'mainActivity', '', '', '', '0', '', null, '1', '2');
INSERT INTO `sa_oppermission` VALUES ('E6D1673C76174CCEA9919C0C16453C98', 'RL01', '/SA/OPM/management/managementProcess', '/系统管理/组织权限/业务管理权限-分级', 'gradeActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('EE9E19F50356498C96BB193356F8AF38', 'RL01', '/SA/OPM/organization/organizationProcess', '/系统管理/组织权限/组织管理-分级', 'gradeActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_oppermission` VALUES ('EF8F1CC8AB6F4728A7D083B6DD1790C1', 'RL01', '/OA/docrs/process/setReceiveDocTemplate/setReceiveDocTemplateProcess', '/其它/收发文基础设置/设置映射关系', 'SetORMstaticActivity', '', '', '', '0', '', null, '1', '0');
INSERT INTO `sa_oppermission` VALUES ('F2AF3284E2D6405E9990376C19C57D45', 'RL01', '/SA/OPM/agent/agentProcess', '/系统管理/组织权限/代理设置', 'mainActivity', '', '', '', '0', '', null, '1', '1');
INSERT INTO `sa_opperson` VALUES ('PSN01', 'system', 'SYSTEM', '', '1', '管理员', 'C4CA4238A0B923820DCC509A6F75849B', '120', '2020-10-20', 'ORG01', '', '1', '1', '', '男', '1976-12-03', '2020-10-06', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '14769660886', '', '', '4', null, '', '', '', '', '', '', '','',null);
INSERT INTO `sa_oprole` VALUES ('RL01', '超级管理员', 'opm', '勿删-系统应用', 'fun', '', '', '0', '1', '4');
INSERT INTO `sa_oprole` VALUES ('RL02', '任务', 'task', '系统管理', 'fun', '', '', '0', '1', '2');
INSERT INTO `sa_oprole` VALUES ('RL02-doc', '文档', 'doc', '系统管理', 'fun', '', '', '0', '1', '2');
INSERT INTO sa_flowfolder VALUES ('root', null, '根目录', 'root', '根目录', null, '/D08B63947EE1444D8C97DC0C5EAD8AD6', '/根目录', '/root', 0);
