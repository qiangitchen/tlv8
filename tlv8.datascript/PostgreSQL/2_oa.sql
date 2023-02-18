-- Create table
create table OA_DC_RECVDOC
(
  FID                VARCHAR(32) not null,
  VERSION            int,
  FDOCNUMBER         VARCHAR(100),
  FTITLE             VARCHAR(150),
  FDOCSOURCEDEPT     VARCHAR(100),
  FDOCSOURCEDATE     timestamp,
  FDOCCOPIES         int,
  FPAGECOUNT         VARCHAR(10),
  FDOCTYPE           VARCHAR(100),
  FURGENCY           VARCHAR(10),
  FCONFIDENTIALITY   VARCHAR(10),
  FDOCRECVNO         VARCHAR(100),
  FDOCRECVDATE       timestamp,
  FREMARK            VARCHAR(2000),
  FATTACHMENT        VARCHAR(2000),
  FCREATEOGNID       VARCHAR(32),
  FCREATEOGNNAME     VARCHAR(64),
  FCREATEDEPTID      VARCHAR(32),
  FCREATEDEPTNAME    VARCHAR(64),
  FCREATEPOSID       VARCHAR(32),
  FCREATEPOSNAME     VARCHAR(64),
  FCREATEPERSONID    VARCHAR(32),
  FCREATEPERSONNAME  VARCHAR(64),
  FCREATEPERSONFID   VARCHAR(512),
  FCREATEPERSONFNAME VARCHAR(1024),
  FCREATETIME        timestamp
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_DC_RECVDOC
  add constraint OA_DC_RECVDOC_KEY primary key (FID);
  
-- Create table
create table OA_DC_SENDDOC
(
  FID                VARCHAR(32) not null,
  VERSION            int,
  FTITLE             VARCHAR(150),
  FTYPE              VARCHAR(100),
  FDOCNUMBER         VARCHAR(100),
  FURGENCY           VARCHAR(10),
  FCONFIDENTIALITY   VARCHAR(10),
  FPAGECOUNT         VARCHAR(10),
  FPRINTCOUNT        VARCHAR(10),
  FMAINDEPT          VARCHAR(2000),
  FCOPYDEPT          VARCHAR(2000),
  FSENDDATE          timestamp,
  FCREATEOGNID       VARCHAR(32),
  FCREATEOGNNAME     VARCHAR(64),
  FCREATEDEPTID      VARCHAR(32),
  FCREATEDEPTNAME    VARCHAR(64),
  FCREATEPOSID       VARCHAR(32),
  FCREATEPOSNAME     VARCHAR(64),
  FCREATEPERSONID    VARCHAR(32),
  FCREATEPERSONNAME  VARCHAR(64),
  FCREATEPERSONFID   VARCHAR(512),
  FCREATEPERSONFNAME VARCHAR(1024),
  FCREATETIME        timestamp,
  FATTACHMENT        VARCHAR(4000),
  FDOCFILE           VARCHAR(1000)
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
  FID            VARCHAR(100) not null,
  VERSION        int,
  FEMAILNAME     VARCHAR(100),
  FTEXT          text,
  FFJID          VARCHAR(4000),
  FSENDPERNAME   VARCHAR(100),
  FSENDPERID     VARCHAR(100),
  FREPLYSTATE    VARCHAR(100),
  FQUREY         VARCHAR(100),
  FCONSIGNEE     VARCHAR(100),
  FCONSIGNEEID   VARCHAR(100),
  FCONSIGNEECODE VARCHAR(100),
  FSENDTIME      timestamp,
  FSENDPERCODE   VARCHAR(100),
  FSENDOGN       VARCHAR(100),
  FSENDDEPT      VARCHAR(100),
  FCOLLECT       VARCHAR(10) DEFAULT '0'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_EM_RECEIVEEMAIL
  add constraint OA_EM_RECEIVEEMAIL_KEY primary key (FID);

-- Create table
create table OA_EM_SENDEMAIL
(
  FID            VARCHAR(100) not null,
  VERSION        int,
  FEMAILNAME     VARCHAR(200),
  FCONSIGNEE     text,
  FTEXT          text,
  FSTATE         VARCHAR(100),
  FCONSIGNEEID   text,
  FCONSIGNEECODE text,
  FFJID          VARCHAR(4000),
  FSENDPERNAME   VARCHAR(100),
  FSENDPERID     VARCHAR(100),
  FCREATTIME     timestamp,
  FSENDTIME      timestamp,
  FSENDPERCODE   VARCHAR(100),
  FSENDOGN       VARCHAR(100),
  FSENDDEPT      VARCHAR(100),
  FCOLLECT       VARCHAR(10) DEFAULT '0'
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_EM_SENDEMAIL
  add constraint OA_EM_SENDEMAIL_KEY primary key (FID);

-- Create table
create table OA_FLOWCONCLUSION
(
  FID             VARCHAR(32),
  FCONCLUSIONNAME VARCHAR(4000),
  FORDER          INTEGER,
  FCREATORID      VARCHAR(100),
  FCREATOR        VARCHAR(100),
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
  FID             VARCHAR(32) not null,
  FNODENAME       VARCHAR(100),
  FAGREETEXT      VARCHAR(4000),
  FCREATEPERNAME  VARCHAR(100),
  FCREATEDEPTNAME VARCHAR(100),
  FCREATETIME     timestamp,
  FBILLID         VARCHAR(32),
  FCREATEDEPTID   VARCHAR(100),
  FCREATEPERID    VARCHAR(100),
  FNODEID         VARCHAR(100),
  VERSION         INTEGER,
  FOPVIEWID       VARCHAR(100),
  FTASKID         VARCHAR(100),
  FFLOWID         VARCHAR(100),
  FSIGN           text,
  fremark         VARCHAR(255)
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
  FID             VARCHAR(36) not null,
  VERSION         int,
  FCREATEOGNID    VARCHAR(36),
  FCREATEOGNNAME  VARCHAR(64),
  FCREATEDEPTID   VARCHAR(36),
  FCREATEDEPTNAME VARCHAR(64),
  FCREATEPSNID    VARCHAR(36),
  FCREATEPSNNAME  VARCHAR(64),
  FCREATETIME     timestamp,
  FNOTICETYPE     VARCHAR(30),
  FSENDTYPE       int,
  FPOSTEDRANGE    VARCHAR(4000),
  FTITLE          VARCHAR(200),
  FSENDDATE       timestamp,
  FCONTENT        VARCHAR(4000),
  FATTACHMENT     text,
  FSENDSTATUS     int,
  FDEPTCHECK      VARCHAR(4000),
  FOFFICECHECK    VARCHAR(4000),
  FLEADERCHECK    VARCHAR(4000),
  FPOSTEDRANGEID  VARCHAR(4000)
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
  FID           VARCHAR(32),
  FBILLID       VARCHAR(32),
  FFULLID       VARCHAR(1024),
  FFULLNAME     VARCHAR(1024),
  FCREATORID    VARCHAR(100),
  FCREATOR      VARCHAR(100),
  FCREATEDEPTID VARCHAR(100),
  FCREATEDEPT   VARCHAR(100),
  FCREATEDATE   timestamp,
  VERSION       INTEGER,
  FNAME         VARCHAR(100)
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
  FID             VARCHAR(64) not null,
  VERSION         INTEGER,
  FOGNID          VARCHAR(64),
  FOGNNAME        VARCHAR(200),
  FORGID          VARCHAR(64),
  FORGNAME        VARCHAR(200),
  FCREATEID       VARCHAR(64),
  FCREATENAME     VARCHAR(200),
  FCREATEDATETIME timestamp,
  FPERSONID       VARCHAR(64),
  FPERSONNAME     VARCHAR(200),
  FREADDATE       timestamp,
  FPERSONFNAME    VARCHAR(500),
  FMAINID         VARCHAR(64),
  FBROWSE         VARCHAR(5),
  FDEPTNAME       VARCHAR(100),
  FDEPTID         VARCHAR(100)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_NOTICE_PERSON
  add constraint OA_NOTICE_PERSON_KEY primary key (FID);
  
-- Create table
create table OA_PUB_DOCPIGEONHOLE
(
  FID           VARCHAR(32) not null,
  FNAME         VARCHAR(100),
  FPARENTID     VARCHAR(32),
  FTABLENAME    VARCHAR(100),
  FBILLID       VARCHAR(32),
  FURL          VARCHAR(1024),
  FCREATEDEPT   VARCHAR(100),
  FCREATEDEPTID VARCHAR(100),
  FCREATOR      VARCHAR(100),
  FCREATORID    VARCHAR(100),
  FCREATEDATE   timestamp default now(),
  VERSION       INTEGER default 0,
  FFID          VARCHAR(1024),
  FFNAME        VARCHAR(1024)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_PUB_DOCPIGEONHOLE
  add constraint OA_PUB_DOCPIGEONHOLE_KEY primary key (FID);

-- Create table
create table OA_PUB_DOCPIGPERM
(
  FID           VARCHAR(32) not null,
  FFOLDERID     VARCHAR(100),
  FPERGORGFID   VARCHAR(1024),
  FPERGORG      VARCHAR(1024),
  FJURISDICTION int,
  FCREATORID    VARCHAR(100),
  FCREATOR      VARCHAR(100),
  FCREATEDEPTID VARCHAR(100),
  FCREATEDEPT   VARCHAR(100),
  FCREATEDATE   timestamp,
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
  add constraint YJOA_OA_PUB_DOCPIGPERM_KEY primary key (FID);
  
-- Create table
create table OA_PULICNOTICE
(
  FID             VARCHAR(64) not null,
  VERSION         INTEGER,
  FOGNID          VARCHAR(64),
  FOGNNAME        VARCHAR(200),
  FORGID          VARCHAR(64),
  FORGNAME        VARCHAR(200),
  FCREATEID       VARCHAR(64),
  FCREATENAME     VARCHAR(200),
  FCREATEDATETIME timestamp,
  FPUSHDATETIME   timestamp,
  FTITLE          VARCHAR(200),
  FCONTENT        text,
  FCREATEDATE     timestamp,
  FPUSHID         VARCHAR(64),
  FPUSHNAME       VARCHAR(200),
  FTYPE           VARCHAR(100),
  FATTFILE        VARCHAR(400)
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_PULICNOTICE
  add constraint OA_PULICNOTICE_KEY primary key (FID);
  
create table OA_RE_DAYREPORT
(
  FID                VARCHAR(64),
  VERSION            int,
  FTITLE             VARCHAR(1024),
  FCREATEOGNID       VARCHAR(64),
  FCREATEOGNNAME     VARCHAR(200),
  FCREATEDEPTID      VARCHAR(64),
  FCREATEDEPTNAME    VARCHAR(200),
  FCREATEPOSID       VARCHAR(64),
  FCREATEPOSNAME     VARCHAR(200),
  FCREATEPERSONID    VARCHAR(64),
  FCREATEPERSONNAME  VARCHAR(200),
  FCREATEPERSONFID   VARCHAR(1024),
  FCREATEPERSONFNAME VARCHAR(2048),
  FCREATETIME        timestamp,
  FPUSHDATETIME      timestamp,
  FFILE              VARCHAR(1000),
  FCONTEXT           text
);
alter table OA_RE_DAYREPORT
  add constraint OA_RE_DAYREPORT_KEY primary key (FID);
  
create table OA_RE_DAYREPORTDETAIL
(
  FID             VARCHAR(100),
  VERSION         int,
  FMASTERID       VARCHAR(100),
  FCONTENT        text,
  FPLANDATE       timestamp,
  FOTHERPERSON    VARCHAR(2000),
  FCOMPLETE       VARCHAR(1024),
  FREMARK         VARCHAR(1024),
  FCOMPLETEREMARK VARCHAR(1024),
  FCREATETIME     timestamp
);
alter table OA_RE_DAYREPORTDETAIL
  add constraint OA_RE_DAYREPORTDETAIL_KEY primary key (FID);
  
create table OA_RE_MONTHREPORT
(
  FID                VARCHAR(100),
  VERSION            INTEGER,
  FTITLE             VARCHAR(1000),
  FCREATEOGNID       VARCHAR(64),
  FCREATEOGNNAME     VARCHAR(200),
  FCREATEDEPTID      VARCHAR(64),
  FCREATEDEPTNAME    VARCHAR(200),
  FCREATEPOSID       VARCHAR(64),
  FCREATEPOSNAME     VARCHAR(200),
  FCREATEPERSONID    VARCHAR(64),
  FCREATEPERSONNAME  VARCHAR(200),
  FCREATEPERSONFID   VARCHAR(1024),
  FCREATEPERSONFNAME VARCHAR(1024),
  FCREATETIME        timestamp,
  FPUSHDATETIME      timestamp,
  FFILE              VARCHAR(1000),
  FCONTEXT           text
);
alter table OA_RE_MONTHREPORT
  add constraint OA_RE_MONTHREPORT_KEY primary key (FID);
  
create table OA_RE_WEEKREPORT
(
  FID                VARCHAR(100),
  VERSION            int,
  FTITLE             VARCHAR(1000),
  FCREATEOGNID       VARCHAR(64),
  FCREATEOGNNAME     VARCHAR(200),
  FCREATEDEPTID      VARCHAR(64),
  FCREATEDEPTNAME    VARCHAR(200),
  FCREATEPOSID       VARCHAR(64),
  FCREATEPOSNAME     VARCHAR(200),
  FCREATEPERSONID    VARCHAR(64),
  FCREATEPERSONNAME  VARCHAR(200),
  FCREATEPERSONFID   VARCHAR(1024),
  FCREATEPERSONFNAME VARCHAR(1024),
  FCREATETIME        timestamp,
  FPUSHDATETIME      timestamp,
  FFILE              VARCHAR(1000),
  FCONTEXT           text
);
alter table OA_RE_WEEKREPORT
  add constraint OA_RE_WEEKREPORT_KEY primary key (FID);
  
create table OA_RE_WEEKREPORTDETAIL
(
  FID             VARCHAR(4000),
  VERSION         int,
  FMASTERID       VARCHAR(4000),
  FCONTENT        VARCHAR(4000),
  FPLANDATE       timestamp,
  FOTHERPERSON    VARCHAR(4000),
  FCOMPLETE       VARCHAR(4000),
  FREMARK         VARCHAR(4000),
  FCOMPLETEREMARK VARCHAR(4000),
  FCREATETIME     timestamp
);
alter table OA_RE_WEEKREPORTDETAIL
  add constraint OA_RE_WEEKREPORTDETAIL_KEY primary key (FID);
  
create table OA_WORK_PLAN_PERSON
(
  FID             VARCHAR(4000),
  VERSION         int,
  FOGNID          VARCHAR(4000),
  FOGNNAME        VARCHAR(4000),
  FORGID          VARCHAR(4000),
  FORGNAME        VARCHAR(4000),
  FCREATEID       VARCHAR(4000),
  FCREATENAME     VARCHAR(4000),
  FCREATEDATETIME timestamp,
  FPERSONID       VARCHAR(4000),
  FPERSONNAME     VARCHAR(4000),
  FREADDATE       timestamp,
  FPERSONFNAME    VARCHAR(4000),
  FMAINID         VARCHAR(4000),
  FBROWSE         VARCHAR(2000),
  FOPINION        VARCHAR(4000)
);
alter table OA_WORK_PLAN_PERSON
  add constraint OA_WORK_PLAN_PERSON_KEY primary key (FID);
  
create table OA_WP_MONTHPLAN
(
  FID                    VARCHAR(100) not null,
  VERSION                INTEGER,
  FOGNID                 VARCHAR(64),
  FOGNNAME               VARCHAR(200),
  FORGID                 VARCHAR(64),
  FORGNAME               VARCHAR(200),
  FCREATEID              VARCHAR(64),
  FCREATENAME            VARCHAR(200),
  FCREATEDATETIME        timestamp,
  FPUSHDATETIME          timestamp,
  FPUSHID                VARCHAR(64),
  FPUSHNAME              VARCHAR(200),
  FTARGETFINISHCONDITION VARCHAR(100),
  FUNFINISHEDCAUSE       VARCHAR(100),
  FWINTHROUGHMETHOD      VARCHAR(100),
  FINNOVATEHARVEST       VARCHAR(100),
  FTITLE                 VARCHAR(200),
  PLANPERSON             VARCHAR(50)
);
alter table OA_WP_MONTHPLAN
  add constraint OA_WP_MONTHPLAN_KEY primary key (FID);
  
create table OA_WP_MONTHPLAN_DETAIL
(
  FID                VARCHAR(100),
  VERSION            int,
  FOGNID             VARCHAR(64),
  FOGNNAME           VARCHAR(200),
  FORGID             VARCHAR(64),
  FORGNAME           VARCHAR(200),
  FCREATEID          VARCHAR(64),
  FCREATENAME        VARCHAR(200),
  FCREATEDATETIME    timestamp,
  FSIGNIFICANCEGRADE VARCHAR(100),
  FTARGETCONTENT     VARCHAR(200),
  FMETHODMEASURE     VARCHAR(200),
  FACCOMPLISH        VARCHAR(100),
  FMAINID            VARCHAR(100),
  FOPINION           VARCHAR(100),
  FORDERTIME         timestamp,
  FCOMPLETE          VARCHAR(100)
);
alter table OA_WP_MONTHPLAN_DETAIL
  add constraint OA_WP_MONTHPLAN_DETAIL_KEY primary key (FID);
  
create table OA_WP_WEEKPLAN
(
  FID             VARCHAR(100),
  VERSION         int,
  FOGNID          VARCHAR(64),
  FOGNNAME        VARCHAR(200),
  FORGID          VARCHAR(64),
  FORGNAME        VARCHAR(200),
  FCREATEID       VARCHAR(64),
  FCREATENAME     VARCHAR(200),
  FCREATEDATETIME timestamp,
  FPUSHDATETIME   timestamp,
  FPUSHID         VARCHAR(100),
  FPUSHNAME       VARCHAR(200),
  FTITLE          VARCHAR(1000),
  STARTTIME       timestamp,
  FINISHTIME      timestamp,
  PLANPERSON      VARCHAR(100),
  FAPPROVALPSNID  VARCHAR(100),
  FCONTENT        text
);
alter table OA_WP_WEEKPLAN
  add constraint OA_WP_WEEKPLAN_KEY primary key (FID);
  
create table OA_WP_WEEKPLAN_DETAIL
(
  FID                   VARCHAR(100) not null,
  VERSION               INTEGER,
  FOGNID                VARCHAR(64),
  FOGNNAME              VARCHAR(200),
  FORGID                VARCHAR(64),
  FORGNAME              VARCHAR(200),
  FCREATEID             VARCHAR(64),
  FCREATENAME           VARCHAR(200),
  FCREATEDATETIME       timestamp,
  MONTHLY               VARCHAR(50),
  PROJECTNAME           VARCHAR(100),
  SERIALNUMBER          VARCHAR(20),
  JOBCONTENT            VARCHAR(200),
  DEPARTMENTCOOPERATION VARCHAR(200),
  COMPLETIONEVALUATION  VARCHAR(200),
  COMPLETECONDITION     VARCHAR(50),
  UNFINISHEDCAUSE       VARCHAR(200),
  AUDITOR               VARCHAR(20),
  MAINID                VARCHAR(100),
  STARTTIME             timestamp,
  FINISHTIME            timestamp,
  SORT                  int,
  PLANTYPE              VARCHAR(20),
  FCONTENT              VARCHAR(50),
  ACCOMPLISH            VARCHAR(50),
  FCOMPLETTIME          timestamp,
  FIMPORTANT            VARCHAR(100)
);
alter table OA_WP_WEEKPLAN_DETAIL
  add constraint OA_WP_WEEKPLAN_DETAIL_KEY primary key (FID);
  
-- Create table
create table OA_PUB_EXECUTE
(
  FID             VARCHAR(32) not null,
  FMASTERID       VARCHAR(100),
  FTASKID         VARCHAR(100),
  FACTIVITYNAME   VARCHAR(500),
  FACTIVITYLABEL  VARCHAR(1000),
  FOPINION        VARCHAR(1024),
  FSTATE          VARCHAR(100),
  FSTATENAME      VARCHAR(100),
  FCREATEPSNID    VARCHAR(64),
  FCREATEPSNNAME  VARCHAR(100),
  FCREATEPSNFID   VARCHAR(1024),
  FCREATEPSNFNAME VARCHAR(2048),
  FCREATETIME     date,
  VERSION         int
);
-- Create/Recreate primary, unique and foreign key constraints 
alter table OA_PUB_EXECUTE
  add constraint OA_PUB_EXECUTE_key primary key (FID);
  
-- Create table
create table OA_DOC_REDHEADPERM
(
  FID           VARCHAR(32) not null,
  FTYPE         VARCHAR(100),
  FNAME         VARCHAR(100),
  FRHFILE       VARCHAR(1024),
  FOGNID        VARCHAR(100),
  FOGNNAME      VARCHAR(100),
  FCREATORID    VARCHAR(100),
  FCREATOR      VARCHAR(100),
  FCREATEDEPTID VARCHAR(100),
  FCREATEDEPT   VARCHAR(100),
  FCREATEDATE   timestamp,
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
  FID           VARCHAR(32) not null,
  FCREATORID    VARCHAR(100),
  FCREATOR      VARCHAR(100),
  FCREATEDEPTID VARCHAR(100),
  FCREATEDEPT   VARCHAR(100),
  FCREATEDATE   timestamp,
  FOUTKEY       VARCHAR(32),
  FPERSONID     VARCHAR(32),
  FPERSONNAME   VARCHAR(200),
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
  FID           VARCHAR(32) not null,
  FCREATORID    VARCHAR(100),
  FCREATOR      VARCHAR(100),
  FCREATEDEPTID VARCHAR(100),
  FCREATEDEPT   VARCHAR(100),
  FCREATEDATE   timestamp,
  FGROUPNAME    VARCHAR(200),
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
  FID           VARCHAR(32) not null,
  VERSION       INTEGER,
  FCREATORID    VARCHAR(100),
  FCREATOR      VARCHAR(100),
  FCREATEDEPTID VARCHAR(100),
  FCREATEDEPT   VARCHAR(100),
  FCREATEDATE   timestamp,
  FSCODE        VARCHAR(64),
  FPERSONNEME   VARCHAR(64),
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
  FPAYDATE      VARCHAR(30),
  FWAGEYEAR     FLOAT,
  FWAGEMONTH    FLOAT,
  FJIJIANBUTIE  FLOAT,
  FYEBANFEI     FLOAT,
  FBEIZHU       VARCHAR(4000)
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
  FID varchar(32) NOT NULL,
  FMINGCHENG varchar(100) DEFAULT NULL,
  VERSION int DEFAULT NULL,
  CODE varchar(200) DEFAULT NULL,
  SCODE varchar(20) DEFAULT NULL,
  fdate timestamp DEFAULT NULL,
  fbillid varchar(32) DEFAULT NULL,
  PRIMARY KEY (FID)
);

CREATE TABLE oa_leave (
  FID varchar(36) NOT NULL,
  FCREATORNAME varchar(255) DEFAULT NULL,
  FCREATORID varchar(36) DEFAULT NULL,
  FCREATORFID varchar(500) DEFAULT NULl,
  FCREATORFNAME varchar(1000) DEFAULT NULL,
  FCREATEDATE timestamp DEFAULT NULL,
  FSTARTDATE date DEFAULT NULL,
  FENDDATE date DEFAULT NULL,
  FDAY integer DEFAULT NULL,
  FLEAVETYPE varchar(255) DEFAULT NULL,
  FSTATE varchar(50) DEFAULT NULL,
  FREASON varchar(500) DEFAULT NULL,
  VERSION varchar(255) DEFAULT NULL,
  PRIMARY KEY (FID)
);
  
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