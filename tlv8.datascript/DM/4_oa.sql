-- Create table
create table OA_DC_RECVDOC
(
  FID                VARCHAR2(32) not null,
  VERSION            NUMBER,
  FDOCNUMBER         VARCHAR2(100),
  FTITLE             VARCHAR2(150),
  FDOCSOURCEDEPT     VARCHAR2(100),
  FDOCSOURCEDATE     datetime,
  FDOCCOPIES         NUMBER,
  FPAGECOUNT         VARCHAR2(10),
  FDOCTYPE           VARCHAR2(100),
  FURGENCY           VARCHAR2(10),
  FCONFIDENTIALITY   VARCHAR2(10),
  FDOCRECVNO         VARCHAR2(100),
  FDOCRECVDATE       datetime,
  FREMARK            VARCHAR2(2000),
  FATTACHMENT        VARCHAR2(2000),
  FCREATEOGNID       VARCHAR2(32),
  FCREATEOGNNAME     VARCHAR2(64),
  FCREATEDEPTID      VARCHAR2(32),
  FCREATEDEPTNAME    VARCHAR2(64),
  FCREATEPOSID       VARCHAR2(32),
  FCREATEPOSNAME     VARCHAR2(64),
  FCREATEPERSONID    VARCHAR2(32),
  FCREATEPERSONNAME  VARCHAR2(64),
  FCREATEPERSONFID   VARCHAR2(512),
  FCREATEPERSONFNAME VARCHAR2(1024),
  FCREATETIME        datetime
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_DC_RECVDOC
  add constraint OA_DC_RECVDOC_KEY primary key (FID);
  
-- Create table
create table OA_DC_SENDDOC
(
  FID                VARCHAR2(32) not null,
  VERSION            NUMBER,
  FTITLE             VARCHAR2(150),
  FTYPE              VARCHAR2(100),
  FDOCNUMBER         VARCHAR2(100),
  FURGENCY           VARCHAR2(10),
  FCONFIDENTIALITY   VARCHAR2(10),
  FPAGECOUNT         VARCHAR2(10),
  FPRINTCOUNT        VARCHAR2(10),
  FMAINDEPT          VARCHAR2(2000),
  FCOPYDEPT          VARCHAR2(2000),
  FSENDDATE          datetime,
  FCREATEOGNID       VARCHAR2(32),
  FCREATEOGNNAME     VARCHAR2(64),
  FCREATEDEPTID      VARCHAR2(32),
  FCREATEDEPTNAME    VARCHAR2(64),
  FCREATEPOSID       VARCHAR2(32),
  FCREATEPOSNAME     VARCHAR2(64),
  FCREATEPERSONID    VARCHAR2(32),
  FCREATEPERSONNAME  VARCHAR2(64),
  FCREATEPERSONFID   VARCHAR2(512),
  FCREATEPERSONFNAME VARCHAR2(1024),
  FCREATETIME        datetime,
  FATTACHMENT        VARCHAR2(4000),
  FDOCFILE           VARCHAR2(1000)
);
-- Add comments to the columns 
comment on column OA_DC_SENDDOC.FTITLE
  is '标题';
comment on column OA_DC_SENDDOC.FTYPE
  is '发文类型';
comment on column OA_DC_SENDDOC.FDOCNUMBER
  is '文号';
comment on column OA_DC_SENDDOC.FURGENCY
  is '缓急';
comment on column OA_DC_SENDDOC.FPRINTCOUNT
  is '打印份数';
comment on column OA_DC_SENDDOC.FMAINDEPT
  is '主送';
comment on column OA_DC_SENDDOC.FCOPYDEPT
  is '抄送';
comment on column OA_DC_SENDDOC.FSENDDATE
  is '发送时间';
comment on column OA_DC_SENDDOC.FATTACHMENT
  is '附件';
comment on column OA_DC_SENDDOC.FDOCFILE
  is '正文';
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_DC_SENDDOC
  add constraint OA_DC_SENDDOC_KEY primary key (FID);

-- Create table
create table OA_EM_RECEIVEEMAIL
(
  FID            VARCHAR2(100) not null,
  VERSION        NUMBER,
  FEMAILNAME     VARCHAR2(100),
  FTEXT          CLOB,
  FFJID          VARCHAR2(4000),
  FSENDPERNAME   VARCHAR2(100),
  FSENDPERID     VARCHAR2(100),
  FREPLYSTATE    VARCHAR2(100),
  FQUREY         VARCHAR2(100),
  FCONSIGNEE     VARCHAR2(100),
  FCONSIGNEEID   VARCHAR2(100),
  FCONSIGNEECODE VARCHAR2(100),
  FSENDTIME      datetime,
  FSENDPERCODE   VARCHAR2(100),
  FSENDOGN       VARCHAR2(100),
  FSENDDEPT      VARCHAR2(100),
  FCOLLECT       VARCHAR2(10) DEFAULT '0'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_EM_RECEIVEEMAIL
  add constraint OA_EM_RECEIVEEMAIL_KEY primary key (FID);

-- Create table
create table OA_EM_SENDEMAIL
(
  FID            VARCHAR2(100) not null,
  VERSION        NUMBER,
  FEMAILNAME     VARCHAR2(200),
  FCONSIGNEE     CLOB,
  FTEXT          CLOB,
  FSTATE         VARCHAR2(100),
  FCONSIGNEEID   CLOB,
  FCONSIGNEECODE CLOB,
  FFJID          VARCHAR2(4000),
  FSENDPERNAME   VARCHAR2(100),
  FSENDPERID     VARCHAR2(100),
  FCREATTIME     datetime,
  FSENDTIME      datetime,
  FSENDPERCODE   VARCHAR2(100),
  FSENDOGN       VARCHAR2(100),
  FSENDDEPT      VARCHAR2(100),
  FCOLLECT       VARCHAR2(10) DEFAULT '0'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_EM_SENDEMAIL
  add constraint OA_EM_SENDEMAIL_KEY primary key (FID);

-- Create table
create table OA_FLOWCONCLUSION
(
  FID             VARCHAR2(32),
  FCONCLUSIONNAME VARCHAR2(4000),
  FORDER          INTEGER,
  FCREATORID      VARCHAR2(100),
  FCREATOR        VARCHAR2(100),
  VERSION         INTEGER
);
-- Add comments to the columns 
comment on column OA_FLOWCONCLUSION.FCONCLUSIONNAME
  is '审批意见';
comment on column OA_FLOWCONCLUSION.FORDER
  is '排序';
comment on column OA_FLOWCONCLUSION.FCREATORID
  is '创建人ID';
comment on column OA_FLOWCONCLUSION.FCREATOR
  is '创建人';
  
-- Create table
create table OA_FLOWRECORD
(
  FID             VARCHAR2(32) not null,
  FNODENAME       VARCHAR2(100),
  FAGREETEXT      VARCHAR2(4000),
  FCREATEPERNAME  VARCHAR2(100),
  FCREATEDEPTNAME VARCHAR2(100),
  FCREATETIME     datetime,
  FBILLID         VARCHAR2(32),
  FCREATEDEPTID   VARCHAR2(100),
  FCREATEPERID    VARCHAR2(100),
  FNODEID         VARCHAR2(100),
  VERSION         INTEGER,
  FOPVIEWID       VARCHAR2(100),
  FTASKID         VARCHAR2(100),
  FFLOWID         VARCHAR2(100),
  FSIGN           clob,
  fremark         VARCHAR2(255)
);
-- Add comments to the columns 
comment on column OA_FLOWRECORD.FNODENAME
  is '环节名称';
comment on column OA_FLOWRECORD.FAGREETEXT
  is '审批意见';
comment on column OA_FLOWRECORD.FCREATEPERNAME
  is '创建人';
comment on column OA_FLOWRECORD.FCREATEDEPTNAME
  is '创建部门';
comment on column OA_FLOWRECORD.FCREATETIME
  is '创建时间';
comment on column OA_FLOWRECORD.FBILLID
  is '业务ID';
comment on column OA_FLOWRECORD.FCREATEDEPTID
  is '部门ID';
comment on column OA_FLOWRECORD.FCREATEPERID
  is '人员ID';
comment on column OA_FLOWRECORD.FNODEID
  is '环节标识';
comment on column OA_FLOWRECORD.FOPVIEWID
  is '显示位置div id';
comment on column OA_FLOWRECORD.FTASKID
  is '任务id';
comment on column OA_FLOWRECORD.FFLOWID
  is '流程id';
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_FLOWRECORD
  add constraint OA_FLOWRECORD_KEY primary key (FID);
  
-- Create table
create table OA_KM_KNOWLEDGE
(
  FID             VARCHAR2(36) not null,
  VERSION         NUMBER,
  FCREATEOGNID    VARCHAR2(36),
  FCREATEOGNNAME  VARCHAR2(64),
  FCREATEDEPTID   VARCHAR2(36),
  FCREATEDEPTNAME VARCHAR2(64),
  FCREATEPSNID    VARCHAR2(36),
  FCREATEPSNNAME  VARCHAR2(64),
  FCREATETIME     datetime,
  FNOTICETYPE     VARCHAR2(30),
  FSENDTYPE       NUMBER,
  FPOSTEDRANGE    VARCHAR2(4000),
  FTITLE          VARCHAR2(200),
  FSENDDATE       datetime,
  FCONTENT        VARCHAR2(4000),
  FATTACHMENT     CLOB,
  FSENDSTATUS     NUMBER,
  FDEPTCHECK      VARCHAR2(4000),
  FOFFICECHECK    VARCHAR2(4000),
  FLEADERCHECK    VARCHAR2(4000),
  FPOSTEDRANGEID  VARCHAR2(4000)
);
-- Add comments to the table 
comment on table OA_KM_KNOWLEDGE
  is '通知公告';
-- Add comments to the columns 
comment on column OA_KM_KNOWLEDGE.FNOTICETYPE
  is '通知公告类型';
comment on column OA_KM_KNOWLEDGE.FSENDTYPE
  is '发布方式';
comment on column OA_KM_KNOWLEDGE.FPOSTEDRANGE
  is '发布范围';
comment on column OA_KM_KNOWLEDGE.FTITLE
  is '标题';
comment on column OA_KM_KNOWLEDGE.FSENDDATE
  is '发布时间';
comment on column OA_KM_KNOWLEDGE.FCONTENT
  is '内容';
comment on column OA_KM_KNOWLEDGE.FATTACHMENT
  is '附件';
comment on column OA_KM_KNOWLEDGE.FSENDSTATUS
  is '发布状态:0未发布；1发布';
comment on column OA_KM_KNOWLEDGE.FDEPTCHECK
  is '科室审核意见';
comment on column OA_KM_KNOWLEDGE.FOFFICECHECK
  is '院务部审核意见';
comment on column OA_KM_KNOWLEDGE.FLEADERCHECK
  is '分管领导审核意见';
comment on column OA_KM_KNOWLEDGE.FPOSTEDRANGEID
  is '发布范围ID';
  
-- Create table
create table OA_KM_KNOWLEDGERANGE
(
  FID           VARCHAR2(32),
  FBILLID       VARCHAR2(32),
  FFULLID       VARCHAR2(1024),
  FFULLNAME     VARCHAR2(1024),
  FCREATORID    VARCHAR2(100),
  FCREATOR      VARCHAR2(100),
  FCREATEDEPTID VARCHAR2(100),
  FCREATEDEPT   VARCHAR2(100),
  FCREATEDATE   datetime,
  VERSION       INTEGER,
  FNAME         VARCHAR2(100)
);
-- Add comments to the columns 
comment on column OA_KM_KNOWLEDGERANGE.FID
  is '主键';
comment on column OA_KM_KNOWLEDGERANGE.FBILLID
  is '业务主键';
comment on column OA_KM_KNOWLEDGERANGE.FFULLID
  is '发送FID';
comment on column OA_KM_KNOWLEDGERANGE.FFULLNAME
  is '发送全名';
comment on column OA_KM_KNOWLEDGERANGE.FCREATORID
  is '创建人ID';
comment on column OA_KM_KNOWLEDGERANGE.FCREATOR
  is '创建人';
comment on column OA_KM_KNOWLEDGERANGE.FCREATEDEPTID
  is '创建部门ID';
comment on column OA_KM_KNOWLEDGERANGE.FCREATEDEPT
  is '创建部门';
comment on column OA_KM_KNOWLEDGERANGE.FCREATEDATE
  is '创建时间';
comment on column OA_KM_KNOWLEDGERANGE.FNAME
  is '名称';
  
-- Create table
create table OA_NOTICE_PERSON
(
  FID             VARCHAR2(64) not null,
  VERSION         INTEGER,
  FOGNID          VARCHAR2(64),
  FOGNNAME        VARCHAR2(200),
  FORGID          VARCHAR2(64),
  FORGNAME        VARCHAR2(200),
  FCREATEID       VARCHAR2(64),
  FCREATENAME     VARCHAR2(200),
  FCREATEDATETIME datetime,
  FPERSONID       VARCHAR2(64),
  FPERSONNAME     VARCHAR2(200),
  FREADDATE       datetime,
  FPERSONFNAME    VARCHAR2(500),
  FMAINID         VARCHAR2(64),
  FBROWSE         NVARCHAR2(5),
  FDEPTNAME       VARCHAR2(100),
  FDEPTID         VARCHAR2(100)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_NOTICE_PERSON
  add constraint OA_NOTICE_PERSON_KEY primary key (FID);
  
-- Create table
create table OA_PUB_DOCPIGEONHOLE
(
  FID           VARCHAR2(32) not null,
  FNAME         VARCHAR2(100),
  FPARENTID     VARCHAR2(32),
  FTABLENAME    VARCHAR2(100),
  FBILLID       VARCHAR2(32),
  FURL          VARCHAR2(1024),
  FCREATEDEPT   VARCHAR2(100),
  FCREATEDEPTID VARCHAR2(100),
  FCREATOR      VARCHAR2(100),
  FCREATORID    VARCHAR2(100),
  FCREATEDATE   datetime default sysdate,
  VERSION       INTEGER default 0,
  FFID          VARCHAR2(1024),
  FFNAME        VARCHAR2(1024)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_PUB_DOCPIGEONHOLE
  add constraint OA_PUB_DOCPIGEONHOLE_KEY primary key (FID);

-- Create table
create table OA_PUB_DOCPIGPERM
(
  FID           VARCHAR2(32) not null,
  FFOLDERID     VARCHAR2(100),
  FPERGORGFID   VARCHAR2(1024),
  FPERGORG      VARCHAR2(1024),
  FJURISDICTION NUMBER,
  FCREATORID    VARCHAR2(100),
  FCREATOR      VARCHAR2(100),
  FCREATEDEPTID VARCHAR2(100),
  FCREATEDEPT   VARCHAR2(100),
  FCREATEDATE   datetime,
  VERSION       INTEGER
);
-- Add comments to the table 
comment on table OA_PUB_DOCPIGPERM
  is '归档目录权限';
-- Add comments to the columns 
comment on column OA_PUB_DOCPIGPERM.FFOLDERID
  is '目录ID';
comment on column OA_PUB_DOCPIGPERM.FPERGORGFID
  is '指定orgfid';
comment on column OA_PUB_DOCPIGPERM.FPERGORG
  is '指定org';
comment on column OA_PUB_DOCPIGPERM.FJURISDICTION
  is '权限';
comment on column OA_PUB_DOCPIGPERM.FCREATORID
  is '创建人ID';
comment on column OA_PUB_DOCPIGPERM.FCREATOR
  is '创建人';
comment on column OA_PUB_DOCPIGPERM.FCREATEDEPTID
  is '创建部门ID';
comment on column OA_PUB_DOCPIGPERM.FCREATEDEPT
  is '创建部门';
comment on column OA_PUB_DOCPIGPERM.FCREATEDATE
  is '创建时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_PUB_DOCPIGPERM
  add constraint OA_PUB_DOCPIGPERM_KEY primary key (FID);
  
-- Create table
create table OA_PULICNOTICE
(
  FID             VARCHAR2(64) not null,
  VERSION         INTEGER,
  FOGNID          VARCHAR2(64),
  FOGNNAME        VARCHAR2(200),
  FORGID          VARCHAR2(64),
  FORGNAME        VARCHAR2(200),
  FCREATEID       VARCHAR2(64),
  FCREATENAME     VARCHAR2(200),
  FCREATEDATETIME datetime,
  FPUSHDATETIME   datetime,
  FTITLE          VARCHAR2(200),
  FCONTENT        CLOB,
  FCREATEDATE     datetime,
  FPUSHID         VARCHAR2(64),
  FPUSHNAME       VARCHAR2(200),
  FTYPE           VARCHAR2(100),
  FATTFILE        VARCHAR2(400)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_PULICNOTICE
  add constraint OA_PULICNOTICE_KEY primary key (FID);
  
create table OA_RE_DAYREPORT
(
  FID                VARCHAR2(64),
  VERSION            NUMBER,
  FTITLE             VARCHAR2(1024),
  FCREATEOGNID       VARCHAR2(64),
  FCREATEOGNNAME     VARCHAR2(200),
  FCREATEDEPTID      VARCHAR2(64),
  FCREATEDEPTNAME    VARCHAR2(200),
  FCREATEPOSID       VARCHAR2(64),
  FCREATEPOSNAME     VARCHAR2(200),
  FCREATEPERSONID    VARCHAR2(64),
  FCREATEPERSONNAME  VARCHAR2(200),
  FCREATEPERSONFID   VARCHAR2(1024),
  FCREATEPERSONFNAME VARCHAR2(2048),
  FCREATETIME        datetime,
  FPUSHDATETIME      datetime,
  FFILE              VARCHAR2(1000),
  FCONTEXT           CLOB
);
alter table OA_RE_DAYREPORT
  add constraint OA_RE_DAYREPORT_KEY primary key (FID);
  
create table OA_RE_DAYREPORTDETAIL
(
  FID             VARCHAR2(100),
  VERSION         NUMBER,
  FMASTERID       VARCHAR2(100),
  FCONTENT        CLOB,
  FPLANDATE       datetime,
  FOTHERPERSON    VARCHAR2(2000),
  FCOMPLETE       VARCHAR2(1024),
  FREMARK         VARCHAR2(1024),
  FCOMPLETEREMARK VARCHAR2(1024),
  FCREATETIME     datetime
);
alter table OA_RE_DAYREPORTDETAIL
  add constraint OA_RE_DAYREPORTDETAIL_KEY primary key (FID);
  
create table OA_RE_MONTHREPORT
(
  FID                VARCHAR2(100),
  VERSION            INTEGER,
  FTITLE             VARCHAR2(1000),
  FCREATEOGNID       VARCHAR2(64),
  FCREATEOGNNAME     VARCHAR2(200),
  FCREATEDEPTID      VARCHAR2(64),
  FCREATEDEPTNAME    VARCHAR2(200),
  FCREATEPOSID       VARCHAR2(64),
  FCREATEPOSNAME     VARCHAR2(200),
  FCREATEPERSONID    VARCHAR2(64),
  FCREATEPERSONNAME  VARCHAR2(200),
  FCREATEPERSONFID   VARCHAR2(1024),
  FCREATEPERSONFNAME VARCHAR2(1024),
  FCREATETIME        datetime,
  FPUSHDATETIME      datetime,
  FFILE              VARCHAR2(1000),
  FCONTEXT           CLOB
);
alter table OA_RE_MONTHREPORT
  add constraint OA_RE_MONTHREPORT_KEY primary key (FID);
  
create table OA_RE_WEEKREPORT
(
  FID                VARCHAR2(100),
  VERSION            NUMBER,
  FTITLE             VARCHAR2(1000),
  FCREATEOGNID       VARCHAR2(64),
  FCREATEOGNNAME     VARCHAR2(200),
  FCREATEDEPTID      VARCHAR2(64),
  FCREATEDEPTNAME    VARCHAR2(200),
  FCREATEPOSID       VARCHAR2(64),
  FCREATEPOSNAME     VARCHAR2(200),
  FCREATEPERSONID    VARCHAR2(64),
  FCREATEPERSONNAME  VARCHAR2(200),
  FCREATEPERSONFID   VARCHAR2(1024),
  FCREATEPERSONFNAME VARCHAR2(1024),
  FCREATETIME        datetime,
  FPUSHDATETIME      datetime,
  FFILE              VARCHAR2(1000),
  FCONTEXT           CLOB
);
alter table OA_RE_WEEKREPORT
  add constraint OA_RE_WEEKREPORT_KEY primary key (FID);
  
create table OA_RE_WEEKREPORTDETAIL
(
  FID             VARCHAR2(4000),
  VERSION         NUMBER,
  FMASTERID       VARCHAR2(4000),
  FCONTENT        VARCHAR2(4000),
  FPLANDATE       datetime,
  FOTHERPERSON    VARCHAR2(4000),
  FCOMPLETE       VARCHAR2(4000),
  FREMARK         VARCHAR2(4000),
  FCOMPLETEREMARK VARCHAR2(4000),
  FCREATETIME     datetime
);
alter table OA_RE_WEEKREPORTDETAIL
  add constraint OA_RE_WEEKREPORTDETAIL_KEY primary key (FID);
  
create table OA_WORK_PLAN_PERSON
(
  FID             VARCHAR2(4000),
  VERSION         NUMBER,
  FOGNID          VARCHAR2(4000),
  FOGNNAME        VARCHAR2(4000),
  FORGID          VARCHAR2(4000),
  FORGNAME        VARCHAR2(4000),
  FCREATEID       VARCHAR2(4000),
  FCREATENAME     VARCHAR2(4000),
  FCREATEDATETIME datetime,
  FPERSONID       VARCHAR2(4000),
  FPERSONNAME     VARCHAR2(4000),
  FREADDATE       datetime,
  FPERSONFNAME    VARCHAR2(4000),
  FMAINID         VARCHAR2(4000),
  FBROWSE         VARCHAR2(2000),
  FOPINION        VARCHAR2(4000)
);
alter table OA_WORK_PLAN_PERSON
  add constraint OA_WORK_PLAN_PERSON_KEY primary key (FID);
  
create table OA_WP_MONTHPLAN
(
  FID                    VARCHAR2(100) not null,
  VERSION                INTEGER,
  FOGNID                 VARCHAR2(64),
  FOGNNAME               VARCHAR2(200),
  FORGID                 VARCHAR2(64),
  FORGNAME               VARCHAR2(200),
  FCREATEID              VARCHAR2(64),
  FCREATENAME            VARCHAR2(200),
  FCREATEDATETIME        datetime,
  FPUSHDATETIME          datetime,
  FPUSHID                VARCHAR2(64),
  FPUSHNAME              VARCHAR2(200),
  FTARGETFINISHCONDITION VARCHAR2(100),
  FUNFINISHEDCAUSE       VARCHAR2(100),
  FWINTHROUGHMETHOD      VARCHAR2(100),
  FINNOVATEHARVEST       VARCHAR2(100),
  FTITLE                 VARCHAR2(200),
  PLANPERSON             VARCHAR2(50)
);
alter table OA_WP_MONTHPLAN
  add constraint OA_WP_MONTHPLAN_KEY primary key (FID);
  
create table OA_WP_MONTHPLAN_DETAIL
(
  FID                VARCHAR2(100),
  VERSION            NUMBER,
  FOGNID             VARCHAR2(64),
  FOGNNAME           VARCHAR2(200),
  FORGID             VARCHAR2(64),
  FORGNAME           VARCHAR2(200),
  FCREATEID          VARCHAR2(64),
  FCREATENAME        VARCHAR2(200),
  FCREATEDATETIME    datetime,
  FSIGNIFICANCEGRADE VARCHAR2(100),
  FTARGETCONTENT     VARCHAR2(200),
  FMETHODMEASURE     VARCHAR2(200),
  FACCOMPLISH        VARCHAR2(100),
  FMAINID            VARCHAR2(100),
  FOPINION           VARCHAR2(100),
  FORDERTIME         datetime,
  FCOMPLETE          VARCHAR2(100)
);
alter table OA_WP_MONTHPLAN_DETAIL
  add constraint OA_WP_MONTHPLAN_DETAIL_KEY primary key (FID);
  
create table OA_WP_WEEKPLAN
(
  FID             VARCHAR2(100),
  VERSION         NUMBER,
  FOGNID          VARCHAR2(64),
  FOGNNAME        VARCHAR2(200),
  FORGID          VARCHAR2(64),
  FORGNAME        VARCHAR2(200),
  FCREATEID       VARCHAR2(64),
  FCREATENAME     VARCHAR2(200),
  FCREATEDATETIME datetime,
  FPUSHDATETIME   datetime,
  FPUSHID         VARCHAR2(100),
  FPUSHNAME       VARCHAR2(200),
  FTITLE          VARCHAR2(1000),
  STARTTIME       datetime,
  FINISHTIME      datetime,
  PLANPERSON      VARCHAR2(100),
  FAPPROVALPSNID  VARCHAR2(100),
  FCONTENT        CLOB
);
alter table OA_WP_WEEKPLAN
  add constraint OA_WP_WEEKPLAN_KEY primary key (FID);
  
create table OA_WP_WEEKPLAN_DETAIL
(
  FID                   VARCHAR2(100) not null,
  VERSION               INTEGER,
  FOGNID                VARCHAR2(64),
  FOGNNAME              VARCHAR2(200),
  FORGID                VARCHAR2(64),
  FORGNAME              VARCHAR2(200),
  FCREATEID             VARCHAR2(64),
  FCREATENAME           VARCHAR2(200),
  FCREATEDATETIME       datetime,
  MONTHLY               VARCHAR2(50),
  PROJECTNAME           VARCHAR2(100),
  SERIALNUMBER          VARCHAR2(20),
  JOBCONTENT            VARCHAR2(200),
  DEPARTMENTCOOPERATION VARCHAR2(200),
  COMPLETIONEVALUATION  VARCHAR2(200),
  COMPLETECONDITION     VARCHAR2(50),
  UNFINISHEDCAUSE       VARCHAR2(200),
  AUDITOR               VARCHAR2(20),
  MAINID                VARCHAR2(100),
  STARTTIME             datetime,
  FINISHTIME            datetime,
  SORT                  NUMBER,
  PLANTYPE              VARCHAR2(20),
  FCONTENT              VARCHAR2(50),
  ACCOMPLISH            VARCHAR2(50),
  FCOMPLETTIME          datetime,
  FIMPORTANT            VARCHAR2(100)
);
alter table OA_WP_WEEKPLAN_DETAIL
  add constraint OA_WP_WEEKPLAN_DETAIL_KEY primary key (FID);
  
-- Create table
create table OA_PUB_EXECUTE
(
  FID             varchar2(32) not null,
  FMASTERID       varchar2(100),
  FTASKID         varchar2(100),
  FACTIVITYNAME   varchar2(500),
  FACTIVITYLABEL  varchar2(1000),
  FOPINION        varchar2(1024),
  FSTATE          varchar2(100),
  FSTATENAME      varchar2(100),
  FCREATEPSNID    varchar2(64),
  FCREATEPSNNAME  varchar2(100),
  FCREATEPSNFID   varchar2(1024),
  FCREATEPSNFNAME varchar2(2048),
  FCREATETIME     datetime,
  VERSION         number
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_PUB_EXECUTE
  add constraint OA_PUB_EXECUTE_key primary key (FID);
  
-- Create table
create table OA_DOC_REDHEADPERM
(
  FID           VARCHAR2(32) not null,
  FTYPE         VARCHAR2(100),
  FNAME         VARCHAR2(100),
  FRHFILE       VARCHAR2(1024),
  FOGNID        VARCHAR2(100),
  FOGNNAME      VARCHAR2(100),
  FCREATORID    VARCHAR2(100),
  FCREATOR      VARCHAR2(100),
  FCREATEDEPTID VARCHAR2(100),
  FCREATEDEPT   VARCHAR2(100),
  FCREATEDATE   datetime,
  VERSION       INTEGER
);

-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_DOC_REDHEADPERM
  add constraint OA_DOC_REDHEADPERM_key primary key (FID);
-- Create/Recreate indexes 
create index oa_redhead_ognid on OA_DOC_REDHEADPERM (FOGNID);

-- Add comments to the table 
comment on table OA_DOC_REDHEADPERM
  is '红头文件配置';
-- Add comments to the columns 
comment on column OA_DOC_REDHEADPERM.FTYPE
  is '类型';
comment on column OA_DOC_REDHEADPERM.FNAME
  is '名称';
comment on column OA_DOC_REDHEADPERM.FRHFILE
  is '文件';
comment on column OA_DOC_REDHEADPERM.FOGNID
  is '机构id';
comment on column OA_DOC_REDHEADPERM.FOGNNAME
  is '机构名称';
comment on column OA_DOC_REDHEADPERM.FCREATORID
  is '创建人ID';
comment on column OA_DOC_REDHEADPERM.FCREATOR
  is '创建人';
comment on column OA_DOC_REDHEADPERM.FCREATEDEPTID
  is '创建部门ID';
comment on column OA_DOC_REDHEADPERM.FCREATEDEPT
  is '创建部门';
comment on column OA_DOC_REDHEADPERM.FCREATEDATE
  is '创建时间';
  
-- Create table
create table OA_ADM_MYGROUPFROM
(
  FID           VARCHAR2(32) not null,
  FCREATORID    VARCHAR2(100),
  FCREATOR      VARCHAR2(100),
  FCREATEDEPTID VARCHAR2(100),
  FCREATEDEPT   VARCHAR2(100),
  FCREATEDATE   datetime,
  FOUTKEY       VARCHAR2(32),
  FPERSONID     VARCHAR2(32),
  FPERSONNAME   VARCHAR2(200),
  VERSION       INTEGER
);
alter table OA_ADM_MYGROUPFROM
  add constraint OA_ADM_MYGROUPFROM_key primary key (FID);
-- Add comments to the columns 
comment on column OA_ADM_MYGROUPFROM.FCREATORID
  is '创建人ID';
comment on column OA_ADM_MYGROUPFROM.FCREATOR
  is '创建人';
comment on column OA_ADM_MYGROUPFROM.FCREATEDEPTID
  is '创建人部门ID';
comment on column OA_ADM_MYGROUPFROM.FCREATEDEPT
  is '创建人部门';
comment on column OA_ADM_MYGROUPFROM.FCREATEDATE
  is '创建日期时间';
comment on column OA_ADM_MYGROUPFROM.FOUTKEY
  is '外键';
comment on column OA_ADM_MYGROUPFROM.FPERSONID
  is '组人员ID';
comment on column OA_ADM_MYGROUPFROM.FPERSONNAME
  is '组人员名称';
  
-- Create table
create table OA_ADM_MYGROUPMAIN
(
  FID           VARCHAR2(32) not null,
  FCREATORID    VARCHAR2(100),
  FCREATOR      VARCHAR2(100),
  FCREATEDEPTID VARCHAR2(100),
  FCREATEDEPT   VARCHAR2(100),
  FCREATEDATE   datetime,
  FGROUPNAME    VARCHAR2(200),
  VERSION       INTEGER
);
alter table OA_ADM_MYGROUPMAIN
  add constraint OA_ADM_MYGROUPMAIN_key primary key (FID);
-- Add comments to the columns 
comment on column OA_ADM_MYGROUPMAIN.FCREATORID
  is '创建人ID';
comment on column OA_ADM_MYGROUPMAIN.FCREATOR
  is '创建人';
comment on column OA_ADM_MYGROUPMAIN.FCREATEDEPTID
  is '创建人部门ID';
comment on column OA_ADM_MYGROUPMAIN.FCREATEDEPT
  is '创建人部门';
comment on column OA_ADM_MYGROUPMAIN.FCREATEDATE
  is '创建日期时间';
comment on column OA_ADM_MYGROUPMAIN.FGROUPNAME
  is '组群名称';
  
-- Create table
create table OA_HR_WARG
(
  FID           VARCHAR2(32) not null,
  VERSION       INTEGER,
  FCREATORID    VARCHAR2(100),
  FCREATOR      VARCHAR2(100),
  FCREATEDEPTID VARCHAR2(100),
  FCREATEDEPT   VARCHAR2(100),
  FCREATEDATE   datetime,
  FSCODE        VARCHAR2(64),
  FPERSONNEME   VARCHAR2(64),
  FPOSTWAGE     NUMBER,
  FLEVEWAGE     NUMBER,
  FBASEJX       NUMBER,
  FREFORM       NUMBER,
  FAWARDJX      NUMBER,
  FAGEWAGE      NUMBER,
  FRETURN       NUMBER,
  FONLYSON      NUMBER,
  FLEAVE        NUMBER,
  FOTHERSUM     NUMBER,
  FSHOULDPAY    NUMBER,
  FDUE          NUMBER,
  FHOUSE        NUMBER,
  FMEDICARE     NUMBER,
  FLOSSCARE     NUMBER,
  FOTHERDIV     NUMBER,
  FLOANDEBIT    NUMBER,
  FTAX          NUMBER,
  FDIVSUM       NUMBER,
  FPAYWAGE      NUMBER,
  FPAYDATE      VARCHAR2(30),
  FWAGEYEAR     NUMBER,
  FWAGEMONTH    NUMBER,
  FJIJIANBUTIE  NUMBER,
  FYEBANFEI     NUMBER,
  FBEIZHU       VARCHAR2(4000)
);
-- Add comments to the table 
comment on table OA_HR_WARG
  is '员工薪资';
-- Add comments to the columns 
comment on column OA_HR_WARG.FCREATORID
  is '创建人ID';
comment on column OA_HR_WARG.FCREATOR
  is '创建人';
comment on column OA_HR_WARG.FCREATEDEPTID
  is '创建部门ID';
comment on column OA_HR_WARG.FCREATEDEPT
  is '创建部门';
comment on column OA_HR_WARG.FCREATEDATE
  is '创建时间';
comment on column OA_HR_WARG.FSCODE
  is '员工编号';
comment on column OA_HR_WARG.FPERSONNEME
  is '姓名';
comment on column OA_HR_WARG.FPOSTWAGE
  is '岗位工资';
comment on column OA_HR_WARG.FLEVEWAGE
  is '薪级工资';
comment on column OA_HR_WARG.FBASEJX
  is '基础性绩效工资标准';
comment on column OA_HR_WARG.FREFORM
  is '改革性补贴';
comment on column OA_HR_WARG.FAWARDJX
  is '奖励性绩效补贴';
comment on column OA_HR_WARG.FAGEWAGE
  is '护龄津贴';
comment on column OA_HR_WARG.FRETURN
  is '回贴';
comment on column OA_HR_WARG.FONLYSON
  is '独子费';
comment on column OA_HR_WARG.FLEAVE
  is '病事假';
comment on column OA_HR_WARG.FOTHERSUM
  is '其他';
comment on column OA_HR_WARG.FSHOULDPAY
  is '应发合计';
comment on column OA_HR_WARG.FDUE
  is '会费';
comment on column OA_HR_WARG.FHOUSE
  is '住房';
comment on column OA_HR_WARG.FMEDICARE
  is '医保';
comment on column OA_HR_WARG.FLOSSCARE
  is '失保';
comment on column OA_HR_WARG.FOTHERDIV
  is '其他费';
comment on column OA_HR_WARG.FLOANDEBIT
  is '贷扣款';
comment on column OA_HR_WARG.FTAX
  is '代扣税';
comment on column OA_HR_WARG.FDIVSUM
  is '扣款合计';
comment on column OA_HR_WARG.FPAYWAGE
  is '实发合计';
comment on column OA_HR_WARG.FPAYDATE
  is '日期';
comment on column OA_HR_WARG.FWAGEYEAR
  is '薪资年';
comment on column OA_HR_WARG.FWAGEMONTH
  is '薪资月';
comment on column OA_HR_WARG.FJIJIANBUTIE
  is '纪检补贴';
comment on column OA_HR_WARG.FYEBANFEI
  is '夜班费';
comment on column OA_HR_WARG.FBEIZHU
  is '备注';
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_HR_WARG
  add constraint OA_HR_WAGE_KEY primary key (FID);
  
CREATE TABLE oa_test1  (
  FID varchar2(32) NOT NULL,
  FMINGCHENG varchar2(100),
  VERSION int,
  CODE varchar2(200),
  SCODE varchar2(20),
  fdate datetime,
  fbillid varchar2(32)
);
alter table oa_test1 add primary key (FID);

CREATE TABLE oa_leave (
  FID varchar2(36) NOT NULL,
  FCREATORNAME varchar2(255) DEFAULT NULL,
  FCREATORID varchar2(36) DEFAULT NULL,
  FCREATORFID varchar2(500) DEFAULT NULl,
  FCREATORFNAME varchar2(1000) DEFAULT NULL,
  FCREATEDATE datetime DEFAULT NULL,
  FSTARTDATE datetime DEFAULT NULL,
  FENDDATE datetime DEFAULT NULL,
  FDAY integer DEFAULT NULL,
  FLEAVETYPE varchar2(255) DEFAULT NULL,
  FSTATE varchar2(50) DEFAULT NULL,
  FREASON varchar2(500) DEFAULT NULL,
  VERSION varchar2(255) DEFAULT NULL
);
alter table oa_leave add primary key (FID);
  
create or replace view oa_notice_person_view as
select t.FID,
       t.VERSION,
       t.FOGNID,
       t.FOGNNAME,
       t.FORGID,
       t.FORGNAME,
       t.FCREATEID,
       t.FCREATENAME,
       t.FCREATEDATETIME,
       t.FPUSHDATETIME,
       t.FTITLE,
       t.FCONTENT,
       t.FCREATEDATE,
       t.FPUSHID,
       t.FPUSHNAME,
       t.FTYPE,
       t1.fpersonid fpersonid,
       t1.fbrowse
  from OA_PULICNOTICE t
  left join OA_NOTICE_PERSON t1 on t.fid = t1.fmainid;
  
create or replace view look_month_work_plan as
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
    on t.fid = t1.fmainid;
	
create or replace view look_week_work_plan as
select t.fid,
       t.FPUSHDATETIME,
       t.FPUSHID,
       t.FPUSHNAME,
       t.FCREATEID,
       t.FTITLE,
       t.FCREATENAME,
       t.FCONTENT,
       t.STARTTIME,
       t.finishtime,
       t.forgname,
       t1.fpersonname,
       t1.fpersonid,
       t1.fbrowse,
       t.version
  from OA_WP_WEEKPLAN t
  left join Oa_Work_Plan_Person t1
    on t.fid = t1.fmainid;

create or replace view oa_re_weekreport_count as
select p.sid fid,
       p.scode fcode,
       p.sname fname,
       p.ssequence,
       count(w.fid) repnum,
       0 version
  from v8sys.sa_opperson p
  left join oa_re_weekreport w
    on p.sid = w.fcreatepersonid
 where p.svalidstate = 1
   and p.sid != 'PSN01'
 group by p.sid, p.scode, p.sname, p.ssequence
 order by p.ssequence;

create or replace view show_oa_re_dayreport as
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
    on t.fid = t1.fmainid;

create or replace view show_oa_re_monthreport as
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
    on t.fid = t1.fmainid;

create or replace view show_oa_re_weekreport as
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
    on t.fid = t1.fmainid; 