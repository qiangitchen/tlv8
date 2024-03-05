create table OA_PUB_BASECODE
(
  	FID                         VARCHAR(64) NOT NULL,
  	FCODE                       VARCHAR(36) NOT NULL,
    FSCODE                      VARCHAR(36),
    FSCOPE                      VARCHAR(36) NOT NULL,
    FSCOPENAME                  VARCHAR(36),
    FNAME                       VARCHAR(64),
    FSNAME                      VARCHAR(64),
    FDESCRIPTION                VARCHAR(255),
    FPARENTID                   VARCHAR(36),
    FLEVEL                      INT,
    FURL                        VARCHAR(255),
    FSEQUENCE                   INT,
    FCREATEDEPTID               VARCHAR(36),
    FCREATEDEPTNAME             VARCHAR(128),
    FCREATEPERID                VARCHAR(36),
    FCREATEPERNAME              VARCHAR(64),
    FCREATETIME                 DATETIME,
    FDISUSETIME                 DATETIME,
    FUSESTATUS                  INT,
    FUSESTATUSNAME              VARCHAR(64),
    FCANEDIT                    INT,
    FCANDELETE                  INT,
    FUPDATEDEPTID               VARCHAR(36),
    FUPDATEDEPTNAME             VARCHAR(128),
    FUPDATEPERID                VARCHAR(36),
    FUPDATEPERNAME              VARCHAR(64),
    FUPDATETIME                 DATETIME,
    fCreateOgnID                VARCHAR(64),
    fCreateOgnName              VARCHAR(128),
    fCreatePsnID                VARCHAR(64),
    fCreatePsnName              VARCHAR(100),
    fCreatePsnFID               VARCHAR(1024),
    fUpdatePsnID                VARCHAR(64),
    fUpdatePsnName              VARCHAR(100),
    VERSION                     INT
);
alter table OA_PUB_BASECODE ADD PRIMARY KEY (FCODE, FSCOPE);
	 
create table OA_DC_RECVDOC
(
  FID                VARCHAR(32) not null comment '主键',
  VERSION            INT comment '版本号',
  FDOCNUMBER         VARCHAR(100) comment '文号',
  FTITLE             VARCHAR(150) comment '标题',
  FDOCSOURCEDEPT     VARCHAR(100) comment '发文单位',
  FDOCSOURCEDATE     DATETIME comment '发文日期',
  FDOCCOPIES         INT,
  FPAGECOUNT         VARCHAR(10) comment '页数',
  FDOCTYPE           VARCHAR(100) comment '类型',
  FURGENCY           VARCHAR(10) comment '缓急',
  FCONFIDENTIALITY   VARCHAR(10),
  FDOCRECVNO         VARCHAR(100) comment '文号',
  FDOCRECVDATE       DATETIME comment '收文日期',
  FREMARK            VARCHAR(2000) comment '备注',
  FATTACHMENT        VARCHAR(2000) comment '附件',
  FCREATEOGNID       VARCHAR(32) comment '创建机构ID',
  FCREATEOGNNAME     VARCHAR(64) comment '创建机构名称',
  FCREATEDEPTID      VARCHAR(32) comment '创建部门ID',
  FCREATEDEPTNAME    VARCHAR(64) comment '创建部门名称',
  FCREATEPOSID       VARCHAR(32) comment '创建人岗位ID',
  FCREATEPOSNAME     VARCHAR(64) comment '创建人岗位',
  FCREATEPERSONID    VARCHAR(32) comment '创建人ID',
  FCREATEPERSONNAME  VARCHAR(64) comment '创建人名称',
  FCREATEPERSONFID   VARCHAR(512) comment '创建人FID',
  FCREATEPERSONFNAME VARCHAR(1024) comment '创建人FNAME',
  FCREATETIME        DATETIME comment '创建时间'
);

alter table OA_DC_RECVDOC
  add primary key (FID);
  

create table OA_DC_SENDDOC
(
  FID                VARCHAR(32) not null,
  VERSION            INT,
  FTITLE             VARCHAR(150) comment '标题',
  FTYPE              VARCHAR(100) comment '发文类型',
  FDOCNUMBER         VARCHAR(100) comment '文号',
  FURGENCY           VARCHAR(10) comment '缓急',
  FCONFIDENTIALITY   VARCHAR(10) ,
  FPAGECOUNT         VARCHAR(10) ,
  FPRINTCOUNT        VARCHAR(10) comment '打印份数',
  FMAINDEPT          VARCHAR(2000) comment '主送',
  FCOPYDEPT          VARCHAR(2000) comment '抄送',
  FSENDDATE          DATETIME comment '发送时间',
  FCREATEOGNID       VARCHAR(32) comment '创建机构ID',
  FCREATEOGNNAME     VARCHAR(64) comment '创建机构名称',
  FCREATEDEPTID      VARCHAR(32) comment '创建部门ID',
  FCREATEDEPTNAME    VARCHAR(64) comment '创建部门名称',
  FCREATEPOSID       VARCHAR(32) comment '创建岗位ID',
  FCREATEPOSNAME     VARCHAR(64) comment '创建岗位名称',
  FCREATEPERSONID    VARCHAR(32) comment '创建人ID',
  FCREATEPERSONNAME  VARCHAR(64) comment '创建人名称',
  FCREATEPERSONFID   VARCHAR(512) comment '创建人全ID',
  FCREATEPERSONFNAME VARCHAR(1024) comment '创建人全名称',
  FCREATETIME        DATETIME comment '创建名称',
  FATTACHMENT        VARCHAR(4000) comment '附件',
  FDOCFILE           VARCHAR(1000) comment '正文'
);

alter table OA_DC_SENDDOC add primary key (FID);

create table OA_EM_RECEIVEEMAIL
(
  FID            VARCHAR(100) not null comment '主键',
  VERSION        INT comment '版本号',
  FEMAILNAME     VARCHAR(200) comment '邮件标题',
  FTEXT          TEXT comment '邮件内容',
  FFJID          VARCHAR(4000) comment '附件',
  FSENDPERNAME   VARCHAR(100) comment '发送名称',
  FSENDPERID     VARCHAR(100) comment '发送人ID',
  FREPLYSTATE    VARCHAR(100) comment '接收状态',
  FQUREY         VARCHAR(100) comment '查看状态',
  FCONSIGNEE     VARCHAR(100) comment '收件人',
  FCONSIGNEEID   VARCHAR(100) comment '收件人ID',
  FCONSIGNEECODE VARCHAR(100) comment '收件人编码',
  FSENDTIME      DATETIME comment '发送时间',
  FSENDPERCODE   VARCHAR(100) comment '发送人编码',
  FSENDOGN       VARCHAR(100) comment '发送机构',
  FSENDDEPT      VARCHAR(100) comment '发送部门',
  FCOLLECT       VARCHAR(10) DEFAULT '0'
);

alter table OA_EM_RECEIVEEMAIL add primary key (FID);

create table OA_EM_SENDEMAIL
(
  FID            VARCHAR(100) not null,
  VERSION        INT,
  FEMAILNAME     VARCHAR(200) comment '邮件标题',
  FCONSIGNEE     TEXT comment '收件人',
  FTEXT          TEXT comment '邮件内容',
  FSTATE         VARCHAR(100) comment '发送状态',
  FCONSIGNEEID   TEXT comment '收件人ID',
  FCONSIGNEECODE TEXT comment '收件人编码',
  FFJID          VARCHAR(4000) comment '附件',
  FSENDPERNAME   VARCHAR(100) comment '发件人',
  FSENDPERID     VARCHAR(100) comment '发件人ID',
  FCREATTIME     DATETIME comment '创建时间',
  FSENDTIME      DATETIME comment '发送时间',
  FSENDPERCODE   VARCHAR(100) comment '发件人编码',
  FSENDOGN       VARCHAR(100) comment '发送机构',
  FSENDDEPT      VARCHAR(100) comment '发送部门',
  FCOLLECT       VARCHAR(10) DEFAULT '0'
);

alter table OA_EM_SENDEMAIL add primary key (FID);

create table OA_FLOWCONCLUSION
(
  FID             VARCHAR(32) NOT NULL comment '主键',
  FCONCLUSIONNAME VARCHAR(4000) comment '审批意见',
  FORDER          INT comment '排序',
  FCREATORID      VARCHAR(100) comment '创建人ID',
  FCREATOR        VARCHAR(100) comment '创建人',
  VERSION         INT comment '版本号'
);
  
alter table OA_FLOWCONCLUSION add primary key (FID);

create table OA_FLOWRECORD
(
  FID             VARCHAR(32) not null comment '主键',
  FNODENAME       VARCHAR(100) comment '环节名称',
  FAGREETEXT      VARCHAR(4000) comment '审批意见',
  FCREATEPERNAME  VARCHAR(100) comment '审批人',
  FCREATEDEPTNAME VARCHAR(100) comment '审批部门',
  FCREATETIME     DATETIME comment '审批时间',
  FBILLID         VARCHAR(32) comment '业务ID',
  FCREATEDEPTID   VARCHAR(100) comment '审批部门ID',
  FCREATEPERID    VARCHAR(100) comment '审批人ID',
  FNODEID         VARCHAR(100) comment '环节标识',
  VERSION         INT comment '版本号',
  FOPVIEWID       VARCHAR(100) comment '显示位置div id',
  FTASKID         VARCHAR(100) comment '任务id',
  FFLOWID         VARCHAR(100) comment '流程id',
  FSIGN           text comment '签名',
  fremark         VARCHAR(255) comment '备注'
);

alter table OA_FLOWRECORD add primary key (FID);
  
create table OA_KM_KNOWLEDGE
(
  FID             VARCHAR(36) not null comment '主键',
  VERSION         INT comment '版本号',
  FCREATEOGNID    VARCHAR(36) comment '创建机构ID',
  FCREATEOGNNAME  VARCHAR(64) comment '创建机构名称',
  FCREATEDEPTID   VARCHAR(36) comment '创建部门ID',
  FCREATEDEPTNAME VARCHAR(64) comment '创建部门名称',
  FCREATEPSNID    VARCHAR(36) comment '创建人ID',
  FCREATEPSNNAME  VARCHAR(64) comment '创建人名称',
  FCREATETIME     DATETIME comment '创建时间',
  FNOTICETYPE     VARCHAR(30) comment '通知公告类型',
  FSENDTYPE       INT comment '发布方式',
  FPOSTEDRANGE    TEXT comment '发布范围',
  FTITLE          VARCHAR(200) comment '标题',
  FSENDDATE       DATETIME comment '发布时间',
  FCONTENT        TEXT comment '内容',
  FATTACHMENT     TEXT comment '附件',
  FSENDSTATUS     INT comment '发布状态:0未发布；1发布',
  FDEPTCHECK      TEXT comment '科室审核意见',
  FOFFICECHECK    TEXT comment '部门审核意见',
  FLEADERCHECK    TEXT comment '分管领导审核意见',
  FPOSTEDRANGEID  TEXT comment '发布范围ID'
);
  
alter table OA_KM_KNOWLEDGE add primary key (FID);

create table OA_KM_KNOWLEDGERANGE
(
  FID           VARCHAR(32) NOT NULL comment '主键',
  FBILLID       VARCHAR(32) comment '业务主键',
  FFULLID       VARCHAR(1024) comment '发送人FID',
  FFULLNAME     VARCHAR(1024) comment '发送人全名',
  FCREATORID    VARCHAR(100) comment '创建人ID',
  FCREATOR      VARCHAR(100) comment '创建人名称',
  FCREATEDEPTID VARCHAR(100) comment '创建人部门ID',
  FCREATEDEPT   VARCHAR(100) comment '创建人部门',
  FCREATEDATE   DATETIME comment '创建时间',
  VERSION       INT comment '版本号',
  FNAME         VARCHAR(100) comment '名称'
);
  
alter table OA_KM_KNOWLEDGERANGE add primary key (FID);

create table OA_NOTICE_PERSON
(
  FID             VARCHAR(64) not null comment '主键',
  VERSION         INT comment '版本号',
  FOGNID          VARCHAR(64) comment '机构ID',
  FOGNNAME        VARCHAR(200) comment '机构名称',
  FORGID          VARCHAR(64) comment '组织ID',
  FORGNAME        VARCHAR(200) comment '组织名称',
  FCREATEID       VARCHAR(64) comment '创建人ID',
  FCREATENAME     VARCHAR(200) comment '创建人名称',
  FCREATEDATETIME DATETIME comment '创建时间',
  FPERSONID       VARCHAR(64) comment '人员ID',
  FPERSONNAME     VARCHAR(200) comment '人员名称',
  FREADDATE       DATETIME comment '查看时间',
  FPERSONFNAME    VARCHAR(500) comment '查看人全名称',
  FMAINID         VARCHAR(64) comment '业务主键',
  FBROWSE         VARCHAR(5) comment '查看',
  FDEPTNAME       VARCHAR(100) comment '部门名称',
  FDEPTID         VARCHAR(100) comment '部门ID'
);

alter table OA_NOTICE_PERSON add primary key (FID);
  
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
  FCREATEDATE   DATETIME,
  VERSION       INT default 0,
  FFID          VARCHAR(1024),
  FFNAME        VARCHAR(1024)
);

alter table OA_PUB_DOCPIGEONHOLE add primary key (FID);

create table OA_PUB_DOCPIGPERM
(
  FID           VARCHAR(32) not null comment '主键',
  FFOLDERID     VARCHAR(100) comment '目录ID',
  FPERGORGFID   VARCHAR(1024) comment '指定orgfid',
  FPERGORG      VARCHAR(1024) comment '指定org',
  FJURISDICTION INT comment '权限',
  FCREATORID    VARCHAR(100) comment '创建人ID',
  FCREATOR      VARCHAR(100) comment '创建人',
  FCREATEDEPTID VARCHAR(100) comment '创建部门ID',
  FCREATEDEPT   VARCHAR(100) comment '创建部门',
  FCREATEDATE   DATETIME comment '创建时间',
  VERSION       INT
);

alter table OA_PUB_DOCPIGPERM add primary key (FID);
  

create table OA_PULICNOTICE
(
  FID             VARCHAR(64) not null comment '主键',
  VERSION         INT comment '版本号',
  FOGNID          VARCHAR(64) comment '机构ID',
  FOGNNAME        VARCHAR(200) comment '机构名称',
  FORGID          VARCHAR(64) comment '部门ID',
  FORGNAME        VARCHAR(200) comment '部门名称',
  FCREATEID       VARCHAR(64) comment '创建人ID',
  FCREATENAME     VARCHAR(200) comment '创建人名称',
  FCREATEDATETIME DATETIME comment '创建时间',
  FPUSHDATETIME   DATETIME comment '发布时间',
  FTITLE          VARCHAR(200) comment '标题',
  FCONTENT        TEXT comment '内容',
  FCREATEDATE     DATE comment '创建日期',
  FPUSHID         VARCHAR(64) comment '发布人ID',
  FPUSHNAME       VARCHAR(200) comment '发布人名称',
  FTYPE           VARCHAR(100) comment '类型',
  FATTFILE        VARCHAR(400) comment '附件'
);
alter table OA_PULICNOTICE add primary key (FID);

create table OA_PUB_EXECUTE
(
  FID             varchar(32) not null,
  FMASTERID       varchar(100),
  FTASKID         varchar(100) comment '任务ID',
  FACTIVITYNAME   varchar(500) comment '环节名称',
  FACTIVITYLABEL  varchar(1000) comment '环节标题',
  FOPINION        varchar(1024) comment '审核意见',
  FSTATE          varchar(100) comment '状态',
  FSTATENAME      varchar(100) comment '状态名称',
  FCREATEPSNID    varchar(64) comment '人员ID',
  FCREATEPSNNAME  varchar(100) comment '人员名称',
  FCREATEPSNFID   varchar(1024) comment '人员全ID',
  FCREATEPSNFNAME varchar(2048) comment '人员全名称',
  FCREATETIME     DATETIME comment '时间',
  VERSION         INT
);
alter table OA_PUB_EXECUTE add primary key (FID);

create table OA_DOC_REDHEADPERM
(
  FID           varchar(32) not null,
  FTYPE         varchar(100) comment '类型',
  FNAME         varchar(100) comment '名称',
  FRHFILE       varchar(1024) comment '文件',
  FOGNID        varchar(100) comment '机构id',
  FOGNNAME      varchar(100) comment '机构名称',
  FCREATORID    varchar(100) comment '创建人ID',
  FCREATOR      varchar(100) comment '创建人',
  FCREATEDEPTID varchar(100) comment '创建部门ID',
  FCREATEDEPT   varchar(100) comment '创建部门',
  FCREATEDATE   DATETIME comment '创建时间',
  VERSION       INT
);
alter table OA_DOC_REDHEADPERM add primary key (FID);
alter table OA_DOC_REDHEADPERM add index oa_redhead_ognid (FOGNID);

create table OA_ADM_MYGROUPFROM
(
  FID           varchar(32) not null,
  FCREATORID    varchar(100),
  FCREATOR      varchar(100),
  FCREATEDEPTID varchar(100),
  FCREATEDEPT   varchar(100),
  FCREATEDATE   datetime,
  FOUTKEY       varchar(32),
  FPERSONID     varchar(32),
  FPERSONNAME   varchar(200),
  VERSION       int
);
alter table OA_ADM_MYGROUPFROM add primary key (FID);

create table OA_ADM_MYGROUPMAIN
(
  FID           varchar(32) not null,
  FCREATORID    varchar(100),
  FCREATOR      varchar(100),
  FCREATEDEPTID varchar(100),
  FCREATEDEPT   varchar(100),
  FCREATEDATE   datetime,
  FGROUPNAME    varchar(200),
  VERSION       int
);
alter table OA_ADM_MYGROUPMAIN add primary key (FID);

create table OA_HR_WARG
(
  FID           varchar(32) not null,
  VERSION       int,
  FCREATORID    varchar(100),
  FCREATOR      varchar(100),
  FCREATEDEPTID varchar(100),
  FCREATEDEPT   varchar(100),
  FCREATEDATE   DATETIME,
  FSCODE        varchar(64) comment '员工编号',
  FPERSONNEME   varchar(64) comment '姓名',
  FPOSTWAGE     float comment '岗位工资',
  FLEVEWAGE     float comment '薪级工资',
  FBASEJX       float comment '基础性绩效工资标准',
  FREFORM       float comment '改革性补贴',
  FAWARDJX      float comment '奖励性绩效补贴',
  FAGEWAGE      float comment '护龄津贴',
  FRETURN       float comment '回贴',
  FONLYSON      float comment '独子费',
  FLEAVE        float comment '病事假',
  FOTHERSUM     float comment '其他',
  FSHOULDPAY    float comment '应发合计',
  FDUE          float comment '会费',
  FHOUSE        float comment '住房',
  FMEDICARE     float comment '医保',
  FLOSSCARE     float comment '失保',
  FOTHERDIV     float comment '其他费',
  FLOANDEBIT    float comment '贷扣款',
  FTAX          float comment '代扣税',
  FDIVSUM       float comment '扣款合计',
  FPAYWAGE      float comment '实发合计',
  FPAYDATE      varchar(30) comment '日期',
  FWAGEYEAR     float comment '薪资年',
  FWAGEMONTH    float comment '薪资月',
  FJIJIANBUTIE  float comment '纪检补贴',
  FYEBANFEI     float comment '夜班费',
  FBEIZHU       varchar(4000) comment '备注'
);
alter table OA_HR_WARG add primary key (FID);

create table OA_WORK_PLAN_PERSON
(
  FID             varchar(100),
  VERSION         int,
  FOGNID          varchar(64),
  FOGNNAME        varchar(200),
  FORGID          varchar(64),
  FORGNAME        varchar(200),
  FCREATEID       varchar(64),
  FCREATENAME     varchar(200),
  FCREATEDATETIME DATETIME,
  FPERSONID       varchar(64),
  FPERSONNAME     varchar(200),
  FREADDATE       DATETIME,
  FPERSONFNAME    varchar(200),
  FMAINID         varchar(100),
  FBROWSE         varchar(100),
  FOPINION        varchar(1000)
);
alter table OA_WORK_PLAN_PERSON add primary key (FID);

create table OA_WP_MONTHPLAN
(
  FID                    varchar(100) not null,
  VERSION                int,
  FOGNID                 varchar(64),
  FOGNNAME               varchar(200),
  FORGID                 varchar(64),
  FORGNAME               varchar(200),
  FCREATEID              varchar(64),
  FCREATENAME            varchar(200),
  FCREATEDATETIME        DATETIME,
  FPUSHDATETIME          DATETIME,
  FPUSHID                varchar(64),
  FPUSHNAME              varchar(200),
  FTARGETFINISHCONDITION varchar(100),
  FUNFINISHEDCAUSE       varchar(100),
  FWINTHROUGHMETHOD      varchar(100),
  FINNOVATEHARVEST       varchar(100),
  FTITLE                 varchar(200),
  PLANPERSON             varchar(50)
);
alter table OA_WP_MONTHPLAN add primary key (FID);

create table OA_WP_MONTHPLAN_DETAIL
(
  FID                varchar(100),
  VERSION            int,
  FOGNID             varchar(64),
  FOGNNAME           varchar(200),
  FORGID             varchar(64),
  FORGNAME           varchar(200),
  FCREATEID          varchar(64),
  FCREATENAME        varchar(200),
  FCREATEDATETIME    DATETIME,
  FSIGNIFICANCEGRADE varchar(100),
  FTARGETCONTENT     varchar(200),
  FMETHODMEASURE     varchar(200),
  FACCOMPLISH        varchar(100),
  FMAINID            varchar(100),
  FOPINION           varchar(100),
  FORDERTIME         DATETIME,
  FCOMPLETE          varchar(100)
);
alter table OA_WP_MONTHPLAN_DETAIL add primary key (FID);

create table OA_WP_WEEKPLAN
(
  FID             varchar(100),
  VERSION         int,
  FOGNID          varchar(64),
  FOGNNAME        varchar(200),
  FORGID          varchar(64),
  FORGNAME        varchar(200),
  FCREATEID       varchar(64),
  FCREATENAME     varchar(200),
  FCREATEDATETIME DATETIME,
  FPUSHDATETIME   DATETIME,
  FPUSHID         varchar(100),
  FPUSHNAME       varchar(200),
  FTITLE          varchar(1000),
  STARTTIME       DATETIME,
  FINISHTIME      DATETIME,
  PLANPERSON      varchar(100),
  FAPPROVALPSNID  varchar(100),
  FCONTENT        text
);
alter table OA_WP_WEEKPLAN add primary key (FID);

create table OA_WP_WEEKPLAN_DETAIL
(
  FID                   varchar(100) not null,
  VERSION               int,
  FOGNID                varchar(64),
  FOGNNAME              varchar(200),
  FORGID                varchar(64),
  FORGNAME              varchar(200),
  FCREATEID             varchar(64),
  FCREATENAME           varchar(200),
  FCREATEDATETIME       DATETIME,
  MONTHLY               varchar(50),
  PROJECTNAME           varchar(100),
  SERIALNUMBER          varchar(20),
  JOBCONTENT            varchar(200),
  DEPARTMENTCOOPERATION varchar(200),
  COMPLETIONEVALUATION  varchar(200),
  COMPLETECONDITION     varchar(50),
  UNFINISHEDCAUSE       varchar(200),
  AUDITOR               varchar(20),
  MAINID                varchar(100),
  STARTTIME             DATETIME,
  FINISHTIME            DATETIME,
  SORT                  int,
  PLANTYPE              varchar(20),
  FCONTENT              varchar(50),
  ACCOMPLISH            varchar(50),
  FCOMPLETTIME          DATETIME,
  FIMPORTANT            varchar(100)
);
alter table OA_WP_WEEKPLAN_DETAIL add primary key (FID);

create table OA_RE_DAYREPORT
(
  FID                varchar(64),
  VERSION            int,
  FTITLE             varchar(1024),
  FCREATEOGNID       varchar(64),
  FCREATEOGNNAME     varchar(200),
  FCREATEDEPTID      varchar(64),
  FCREATEDEPTNAME    varchar(200),
  FCREATEPOSID       varchar(64),
  FCREATEPOSNAME     varchar(200),
  FCREATEPERSONID    varchar(64),
  FCREATEPERSONNAME  varchar(200),
  FCREATEPERSONFID   varchar(1024),
  FCREATEPERSONFNAME varchar(2048),
  FCREATETIME        DATETIME,
  FPUSHDATETIME      DATETIME,
  FFILE              varchar(1000),
  FCONTEXT           text
);
alter table OA_RE_DAYREPORT add primary key (FID);

CREATE TABLE oa_re_dayreportdetail  (
  FID varchar(100) NOT NULL,
  VERSION decimal(10, 0) DEFAULT NULL,
  FMASTERID varchar(100) DEFAULT NULL,
  FCONTENT longtext,
  FPLANDATE date DEFAULT NULL,
  FOTHERPERSON varchar(2000) DEFAULT NULL,
  FCOMPLETE varchar(1024) DEFAULT NULL,
  FREMARK varchar(1024) DEFAULT NULL,
  FCOMPLETEREMARK varchar(1024) DEFAULT NULL,
  FCREATETIME timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (FID)
);

create table OA_RE_WEEKREPORT
(
  FID                varchar(100),
  VERSION            int,
  FTITLE             varchar(1000),
  FCREATEOGNID       varchar(64),
  FCREATEOGNNAME     varchar(200),
  FCREATEDEPTID      varchar(64),
  FCREATEDEPTNAME    varchar(200),
  FCREATEPOSID       varchar(64),
  FCREATEPOSNAME     varchar(200),
  FCREATEPERSONID    varchar(64),
  FCREATEPERSONNAME  varchar(200),
  FCREATEPERSONFID   varchar(1024),
  FCREATEPERSONFNAME varchar(1024),
  FCREATETIME        DATETIME,
  FPUSHDATETIME      DATETIME,
  FFILE              varchar(1000),
  FCONTEXT           text
);
alter table OA_RE_WEEKREPORT add primary key (FID);

create table OA_RE_MONTHREPORT
(
  FID                varchar(100),
  VERSION            int,
  FTITLE             varchar(1000),
  FCREATEOGNID       varchar(64),
  FCREATEOGNNAME     varchar(200),
  FCREATEDEPTID      varchar(64),
  FCREATEDEPTNAME    varchar(200),
  FCREATEPOSID       varchar(64),
  FCREATEPOSNAME     varchar(200),
  FCREATEPERSONID    varchar(64),
  FCREATEPERSONNAME  varchar(200),
  FCREATEPERSONFID   varchar(1024),
  FCREATEPERSONFNAME varchar(1024),
  FCREATETIME        DATETIME,
  FPUSHDATETIME      DATETIME,
  FFILE              varchar(1000),
  FCONTEXT           text
);
alter table OA_RE_MONTHREPORT add primary key (FID);

CREATE TABLE oa_leave (
  FID varchar(36) NOT NULL,
  FCREATORNAME varchar(255) DEFAULT NULL COMMENT '申请人名称',
  FCREATORID varchar(36) DEFAULT NULL COMMENT '申请人',
  FCREATORFID varchar(500) DEFAULT NULL COMMENT '申请人FID',
  FCREATORFNAME varchar(1000) DEFAULT NULL COMMENT '申请人FNAME',
  FCREATEDATE datetime DEFAULT NULL COMMENT '申请时间',
  FSTARTDATE date DEFAULT NULL COMMENT '开始时间',
  FENDDATE date DEFAULT NULL COMMENT '结束时间',
  FDAY int(5) DEFAULT NULL COMMENT '请假天数',
  FLEAVETYPE varchar(255) DEFAULT NULL COMMENT '请假类型',
  FSTATE varchar(50) DEFAULT NULL COMMENT '申请状态',
  FREASON varchar(500) DEFAULT NULL COMMENT '请假原因',
  VERSION varchar(255) DEFAULT NULL,
  PRIMARY KEY (FID)
);

CREATE TABLE oa_test1  (
  FID varchar(32) NOT NULL,
  FMINGCHENG varchar(100) DEFAULT NULL,
  VERSION int(11) DEFAULT NULL,
  CODE varchar(200) DEFAULT NULL,
  SCODE varchar(20) DEFAULT NULL,
  fdate datetime(0) DEFAULT NULL,
  fbillid varchar(32) DEFAULT NULL,
  PRIMARY KEY (FID)
);
  
create or replace view oa_notice_person_view as
select t.FID,t.VERSION,t.FOGNID,t.FOGNNAME,t.FORGID,t.FORGNAME,t.FCREATEID,t.FCREATENAME,t.FCREATEDATETIME,t.FPUSHDATETIME,t.FTITLE,t.FCONTENT,t.FCREATEDATE,t.FPUSHID,t.FPUSHNAME,t.FTYPE,t1.fpersonid fpersonid,t1.fbrowse
from OA_PULICNOTICE t
left join OA_NOTICE_PERSON  t1 on t.fid=t1.fmainid;

create or replace view look_week_work_plan as
select t.fid,t.FPUSHDATETIME,t.FPUSHID,t.FPUSHNAME,t.FCREATEID,t.FTITLE,t.FCREATENAME,t.FCONTENT,t.STARTTIME,
t.finishtime,t.forgname,t1.fpersonname,t1.fpersonid,t1.fbrowse,t.version 
from OA_WP_WEEKPLAN t
left join Oa_Work_Plan_Person t1 on t.fid=t1.fmainid;

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