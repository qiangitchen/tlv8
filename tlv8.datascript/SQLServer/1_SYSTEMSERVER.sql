--create TABLE
create table [dbo].[SA_WPPLANPROCESSOR]
(
  [SID] [nvarchar] (64) not null,
  [SPLANID] [nvarchar] (64),
  [STYPE] [nvarchar] (12),
  [SDEPT] [nvarchar] (64),
  [SDEPTNAME] [nvarchar] (255),
  [SPERSON] [nvarchar] (64),
  [SPERSONNAME] [nvarchar] (255),
  [SMEMO]       integer,
  [version] [int]
)ON [PRIMARY]

alter table [dbo].[SA_WPPLANPROCESSOR] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY] 

create table [dbo].[SA_WPPLANENUMTYPES]
(
  [SID] [int] not null,
  [SENUMTYPE] [nvarchar] (50),
  [SCODE] [nvarchar] (50),
  [SENUMNAME] [nvarchar] (50),
  [VERSION] [int]
)ON [PRIMARY] 

alter table [dbo].[SA_WPPLANENUMTYPES] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_WPPLAN]
(
  [SID] [nvarchar] (32) not null,
  [SCLIENTACCOUNT] [nvarchar] (64) not null,
  [SPARENTPLANID]  [nvarchar](32),
  [SPLANCODE]      [nvarchar](32),
  [SPLANTITLE]     [nvarchar](255),
  [SPLANTYPEID]    [nvarchar](32),
  [SBIZTYPEID]     [nvarchar](32),
  [SBIZTYPENAME]   [nvarchar](255),
  [STIMESPANID]    [nvarchar](32),
  [SPLANSTATEID]   [nvarchar](32),
  [SPLANDESCRIPT]  [nvarchar](1024),
  [SCREATETIME]     [datetime] not null,
  [SCHANGETIME]     [datetime] not null,
  [SPLANBEGINTIME]  [datetime],
  [SPLANFINISHTIME] [datetime],
  [SFACTBEGINTIME]  [datetime],
  [SFACTFINISHTIME] [datetime],
  [SPLANPERCENT]    [int],
  [SFACTPERCENT]    [int],
  [SPERCENTMODEID]  [nvarchar](64),
  [SPLANCYCLE]      FLOAT,
  [SFACTCYCLE]      FLOAT,
  [SPLANMANHOUR]    FLOAT,
  [SFACTMANHOUR]    FLOAT,
  [SFINISHMODE]     [nvarchar](255),
  [SPLANYEAR]       [int],
  [SPLANMONTH]      [int],
  [SPLANWEEK]       [int],
  [SCREATORID]      [nvarchar](64),
  [SCREATORNAME]    [nvarchar](64),
  [SCREATORDEPTID]  [nvarchar](64),
  [SCREATDEPTNAME]  [nvarchar](64),
  [SCREATPOSID]     [nvarchar](64),
  [SCREATPOSNAME]   [nvarchar](32),
  [SPRINCPID]       [nvarchar](64),
  [SPRINCPNAME]     [nvarchar](64),
  [SPRINCPDEPTID]   [nvarchar](64),
  [SPRINCPDEPTNAME] [nvarchar](64),
  [SPRINCPPOSID]    [nvarchar](64),
  [SPRINCPPOSNAME]  [nvarchar](32),
  [SPRIORID]        [nvarchar](64),
  [SPRIORNAME]      [nvarchar](64),
  [SPRIORDEPTID]    [nvarchar](64),
  [SPRIORDEPTNAME]  [nvarchar](64),
  [SSAFELEVELID]    [int],
  [SAFFIRMLEVELID]  [int],
  [SISASSESS]       [nvarchar](10),
  [SISMONOPLAN]     [nvarchar](10),
  [SPIECEID]        [nvarchar](10),
  [SAMOUNT]         [int],
  [SAFFIRMOPRID]    [nvarchar](32),
  [SAFFIRMOPRSTEID] [nvarchar](32),
  [SPLANSORTCODE]   [nvarchar](64),
  [SADJUSTPLANID]   [nvarchar](32),
  [SADJROOTPLANID]  [nvarchar](32),
  [SCOADJUTANT]     [nvarchar](255),
  [STREECODE]       [nvarchar](255),
  [SPRIORPOSID]     [nvarchar](64),
  [SPRIORPOSNAME]   [nvarchar](32),
  [SESFIELD01]      [nvarchar](255),
  [SESFIELD02]      [nvarchar](255),
  [SESFIELD03]      [nvarchar](255),
  [SESFIELD04]      [nvarchar](255),
  [SESFIELD05]      [nvarchar](255),
  [SESFIELD06]      [nvarchar](255),
  [SECFIELD07]      [nvarchar](2048),
  [SECFIELD08]      [nvarchar](2048),
  [SENFIELD17]      [float],
  [SENFIELD18]      [float],
  [SENFIELD19]      [float],
  [SENFIELD20]      [float],
  [SEDFIELD33]      [datetime],
  [SEDFIELD34]      [datetime],
  [SEDFIELD35]      [datetime],
  [SEDFIELD36]      [datetime],
  [SEBFIELD50]      [nvarchar](1024),
  [SEBFIELD51]      [nvarchar](1024),
  [SETFIELD52]      [nvarchar](1024),
  [SETFIELD53]      [nvarchar](1024),
  [SPROJECT]        [nvarchar](32),
  [SPROJECTNAME]    [nvarchar](255),
  [SPREVS]          [nvarchar](255),
  [SPREVTITLES]     [nvarchar](256),
  [SRESOURCEIDS]    [nvarchar](255),
  [SRESOURCENAMES]  [nvarchar](255),
  [STERM]           [datetime],
  [SLIMITTYPEID]    [nvarchar](32),
  [SPLANLIMITDATE]  [datetime],
  [SWBSCODE]        [nvarchar](32),
  [SKEYPOINT]       [int],
  [SCALENDARID]     [nvarchar](32),
  [SIRC]            [int],
  [SSAMID]          [nvarchar](32),
  [SPRIORITY]       [nvarchar](32),
  [SWBSTYPEID]      [nvarchar](32),
  [SDEVOTED]        [int],
  [SPLANCOST]       [FLOAT],
  [SFACTCOST]       [FLOAT],
  [VERSION] [int]
)ON [PRIMARY]

alter table [dbo].[SA_WPPLAN] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_WPCOMMENUMTYPES]
(
  [SID]       [nvarchar](32) not null,
  [SENUMTYPE] [nvarchar](50),
  [SCODE]     [nvarchar](50),
  [SENUMNAME] [nvarchar](50),
  [VERSION]   [int]
)ON [PRIMARY] 

alter table [dbo].[SA_WPCOMMENUMTYPES] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
  
create table [dbo].[SA_WORKPLAN]
(
  SID     [nvarchar](10) not null,
  SNAME   [nvarchar](50),
  VERSION [int]
)ON [PRIMARY] 

alter table [dbo].[SA_WORKPLAN] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_TIMERTASK]
(
  [SID]            [nvarchar](50) not null,
  [SCAPTION]       [nvarchar](200) not null,
  [STIMERTASKTYPE] [nvarchar](50) not null,
  [STYPENAME]      [nvarchar](50),
  [STIMES]         [int],
  [SSTARTTIME]     [datetime] not null,
  [SENDTIME]       [datetime],
  [SLASTTIME]      [datetime],
  [SINTERVALTIME]  [datetime],
  [SPROCESS]       [nvarchar](100),
  [SACTIVITY]      [nvarchar](100),
  [SACTIONNAME]    [nvarchar](100),
  [SSTATE]         [nvarchar](30),
  [SEXECUTORID]    [nvarchar](50) not null,
  [SHANDLENAME]    [nvarchar](100) not null,
  [STASKREMARKS]   [nvarchar](500),
  [SISRETURN]      [nvarchar](50),
  [SRETURNPATTERN] [nvarchar](500),
  [SHASPARA]       [int],
  [VERSION]        [int],
  [SCONDITION]     [nvarchar](500),
  [SINTERVALDAY]   [int],
  [SINTERVALHOUR]  [int],
  [SINTERVALMIN]   [int],
  [STYPE]          [int],
  [SPASSWORD]      [nvarchar](100),
  [SSQLMODEL]      [nvarchar](50),
  [SCONTENT]       [text]
)ON [PRIMARY] 

alter table [dbo].[SA_TIMERTASK] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_TIMERACTIONPARA]
(
  SID        [nvarchar](50) not null,
  SOFTASK    [nvarchar](50) not null,
  SPARANAME  [nvarchar](50) not null,
  SDATATYPE  [nvarchar](50),
  SPARAVALUE [nvarchar](100) not null,
  VERSION    [int]
)ON [PRIMARY] 

alter table [dbo].[SA_TIMERACTIONPARA] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_TASK_HISTORY]
(
  SID             [nvarchar](36) not null,
  SPARENTID       [nvarchar](36),
  SNAME           [nvarchar](255),
  SCONTENT        [text],
  SREMARK         [text],
  SFLOWID         [nvarchar](36),
  STYPEID         [nvarchar](36),
  STYPENAME       [nvarchar](64),
  SIMPORTANCEID   [nvarchar](36),
  SIMPORTANCENAME [nvarchar](64),
  SEMERGENCYID    [nvarchar](36),
  SEMERGENCYNAME  [nvarchar](64),
  SPROCESS        [nvarchar](255),
  SACTIVITY       [nvarchar](255),
  SCREATETIME     [datetime],
  SDISTRIBUTETIME [datetime],
  SLASTMODIFYTIME [datetime],
  SWARNINGTIME    [datetime],
  SLIMITTIME      [datetime],
  SESTARTTIME     [datetime],
  SEFINISHTIME    [datetime],
  SASTARTTIME     [datetime],
  SAFINISHTIME    [datetime],
  SEXECUTETIME    [datetime],
  SCPERSONID      [nvarchar](36),
  SCPERSONNAME    [nvarchar](255),
  SCDEPTID        [nvarchar](36),
  SCDEPTNAME      [nvarchar](255),
  SCOGNID         [nvarchar](36),
  SCOGNNAME       [nvarchar](255),
  SEPERSONID      [nvarchar](36),
  SEPERSONNAME    [nvarchar](255),
  SEDEPTID        [nvarchar](36),
  SEDEPTNAME      [nvarchar](255),
  SEOGNID         [nvarchar](36),
  SEOGNNAME       [nvarchar](255),
  SCUSTOMERID     [nvarchar](64),
  SCUSTOMERNAME   [nvarchar](255),
  SPROJECTID      [nvarchar](64),
  SPROJECTNAME    [nvarchar](255),
  SPLANID         [nvarchar](64),
  SPLANNAME       [nvarchar](255),
  SVARIABLE       [text],
  SFAKE           [nvarchar](8),
  SACTIVE         [nvarchar](8),
  SLOCK           [nvarchar](36),
  SSTATUSID       [nvarchar](36),
  SSTATUSNAME     [nvarchar](64),
  VERSION         [int],
  SAIID           [nvarchar](36),
  SCATALOGID      [nvarchar](36),
  SKINDID         [nvarchar](36),
  SAIACTIVE       [nvarchar](8),
  SAISTATUSID     [nvarchar](255),
  SAISTATUSNAME   [nvarchar](255),
  SSOURCEID       [nvarchar](36),
  SCURL           [nvarchar](255),
  SEURL           [nvarchar](255),
  SEXECUTEMODE    [nvarchar](32),
  SEXECUTEMODE2   [nvarchar](255),
  SPREEMPTMODE    [nvarchar](32),
  SSEQUENCE       [int],
  SCPERSONCODE    [nvarchar](64),
  SCPOSID         [nvarchar](36),
  SCPOSCODE       [nvarchar](64),
  SCPOSNAME       [nvarchar](255),
  SCDEPTCODE      [nvarchar](64),
  SCOGNCODE       [nvarchar](64),
  SCFID           [nvarchar](1024),
  SEPERSONCODE    [nvarchar](64),
  SEPOSID         [nvarchar](36),
  SEPOSCODE       [nvarchar](64),
  SEPOSNAME       [nvarchar](255),
  SEDEPTCODE      [nvarchar](64),
  SEOGNCODE       [nvarchar](64),
  SEFID           [nvarchar](1024),
  SEXECUTORNAMES  [nvarchar](255),
  SRESPONSIBLE    [nvarchar](8),
  SCUSTOMERCODE   [nvarchar](64),
  SPROJECTCODE    [nvarchar](64),
  SPLANCODE       [nvarchar](64),
  SDATA1          [nvarchar](128),
  SDATA2          [nvarchar](128),
  SDATA3          [nvarchar](128),
  SDATA4          [nvarchar](128),
  SWORKTIME       [int],
  SCFNAME         [nvarchar](255),
  SEFNAME         [nvarchar](255),
  SHINTS          [nvarchar](1024),
  SSHORTCUT       [nvarchar](1024),
  SFRONTID        [nvarchar](36),
  SFMAKERNAME     [nvarchar](100)
)ON [PRIMARY] 

alter table [dbo].[SA_TASK_HISTORY] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_TASKTYPE]
(
  SID              [nvarchar](50) not null,
  SNAME            [nvarchar](500),
  SCONCEPT         [nvarchar](500),
  SNEWACTIVITY     [nvarchar](500),
  SEXECUTEACTIVITY [nvarchar](500),
  SKIND            [nvarchar](500),
  VERSION          [int]
)ON [PRIMARY] 

alter table [dbo].[SA_TASKTYPE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
create table [dbo].[SA_TASK_TIMELIMIT]
(
  SID              [nvarchar](32) not null,
  SPROCESSID       [nvarchar](255),
  SPROCESSNAME     [nvarchar](512),
  SACTIVITY        [nvarchar](255),
  SACTIVITYNAME    [nvarchar](512),
  SDLIMIT          [int],
  VERSION          [int]
)ON [PRIMARY]  

alter table [dbo].[SA_TASK_TIMELIMIT] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_TASK]
(
  SID             [nvarchar](36) not null,
  SPARENTID       [nvarchar](36),
  SNAME           [nvarchar](255),
  SCONTENT        [text],
  SREMARK         [text],
  SFLOWID         [nvarchar](36),
  STYPEID         [nvarchar](36),
  STYPENAME       [nvarchar](64),
  SIMPORTANCEID   [nvarchar](36),
  SIMPORTANCENAME [nvarchar](64),
  SEMERGENCYID    [nvarchar](36),
  SEMERGENCYNAME  [nvarchar](64),
  SPROCESS        [nvarchar](255),
  SACTIVITY       [nvarchar](255),
  SCREATETIME     [datetime],
  SDISTRIBUTETIME [datetime],
  SLASTMODIFYTIME [datetime],
  SWARNINGTIME    [datetime],
  SLIMITTIME      [datetime],
  SESTARTTIME     [datetime],
  SEFINISHTIME    [datetime],
  SASTARTTIME     [datetime],
  SAFINISHTIME    [datetime],
  SEXECUTETIME    [datetime],
  SCPERSONID      [nvarchar](36),
  SCPERSONNAME    [nvarchar](255),
  SCDEPTID        [nvarchar](36),
  SCDEPTNAME      [nvarchar](255),
  SCOGNID         [nvarchar](36),
  SCOGNNAME       [nvarchar](255),
  SEPERSONID      [nvarchar](36),
  SEPERSONNAME    [nvarchar](255),
  SEDEPTID        [nvarchar](36),
  SEDEPTNAME      [nvarchar](255),
  SEOGNID         [nvarchar](36),
  SEOGNNAME       [nvarchar](255),
  SCUSTOMERID     [nvarchar](64),
  SCUSTOMERNAME   [nvarchar](255),
  SPROJECTID      [nvarchar](64),
  SPROJECTNAME    [nvarchar](255),
  SPLANID         [nvarchar](64),
  SPLANNAME       [nvarchar](255),
  SVARIABLE       [text],
  SFAKE           [nvarchar](8),
  SACTIVE         [nvarchar](8),
  SLOCK           [nvarchar](36),
  SSTATUSID       [nvarchar](36),
  SSTATUSNAME     [nvarchar](64),
  VERSION         [int],
  SAIID           [nvarchar](36),
  SCATALOGID      [nvarchar](36),
  SKINDID         [nvarchar](36),
  SAIACTIVE       [nvarchar](8),
  SAISTATUSID     [nvarchar](255),
  SAISTATUSNAME   [nvarchar](255),
  SSOURCEID       [nvarchar](36),
  SCURL           [nvarchar](255),
  SEURL           [nvarchar](255),
  SEXECUTEMODE    [nvarchar](32),
  SEXECUTEMODE2   [nvarchar](255),
  SPREEMPTMODE    [nvarchar](32),
  SSEQUENCE       [int],
  SCPERSONCODE    [nvarchar](64),
  SCPOSID         [nvarchar](36),
  SCPOSCODE       [nvarchar](64),
  SCPOSNAME       [nvarchar](255),
  SCDEPTCODE      [nvarchar](64),
  SCOGNCODE       [nvarchar](64),
  SCFID           [nvarchar](1024),
  SEPERSONCODE    [nvarchar](64),
  SEPOSID         [nvarchar](36),
  SEPOSCODE       [nvarchar](64),
  SEPOSNAME       [nvarchar](255),
  SEDEPTCODE      [nvarchar](64),
  SEOGNCODE       [nvarchar](64),
  SEFID           [nvarchar](1024),
  SEXECUTORNAMES  [nvarchar](255),
  SRESPONSIBLE    [nvarchar](8),
  SCUSTOMERCODE   [nvarchar](64),
  SPROJECTCODE    [nvarchar](64),
  SPLANCODE       [nvarchar](64),
  SDATA1          [nvarchar](128),
  SDATA2          [nvarchar](128),
  SDATA3          [nvarchar](128),
  SDATA4          [nvarchar](128),
  SWORKTIME       [int],
  SCFNAME         [nvarchar](255),
  SEFNAME         [nvarchar](255),
  SHINTS          [nvarchar](1024),
  SSHORTCUT       [nvarchar](1024),
  SFRONTID        [nvarchar](36),
  SFMAKERNAME     [nvarchar](100)
)ON [PRIMARY] 

alter table [dbo].[SA_TASK] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
create index SA_FLOWID on SA_TASK (SFLOWID)
create index SA_STATUS on SA_TASK (SSTATUSID)
create index SA_TASK_SPROCESS on SA_TASK (SPROCESS)
create index SDATA_123654 on SA_TASK (SDATA1)
create index Taks_SPARENTID on SA_TASK (SPARENTID)

create table [dbo].[SA_SYSTEMMAINTAIN]
(
  SID          [nvarchar](36) not null,
  SCODE        [nvarchar](100),
  SNAME        [nvarchar](100),
  STEXT        [nvarchar](4000),
  SCREATORID   [nvarchar](36),
  SCREATORNAME [nvarchar](100),
  SCREATETIME  [datetime],
  VERSION      [int]
)ON [PRIMARY] 

alter table [dbo].[SA_SYSTEMMAINTAIN] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_REMINDPERSON]
(
  SREMIND [nvarchar](50) not null,
  SPERSON [nvarchar](50) not null,
  SID     [nvarchar](50) default NEWID(),
  VERSION [int]
)ON [PRIMARY] 

create unique index PK_REMINDPERSON on SA_REMINDPERSON (SREMIND, SPERSON)

create table [dbo].[SA_REMINDCONFIG]
(
  SID             [nvarchar](50) not null,
  SREMINDOVERTIME [nvarchar](10),
  SOFPERSONID     [nvarchar](50) not null,
  SREMINDINTERVAL [int],
  SREMINDP        [nvarchar](10),
  VERSION         [int] not null
)ON [PRIMARY] 

alter table [dbo].[SA_REMINDCONFIG] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
create table [dbo].[SA_REMINDINFO]
(
  SID         [nvarchar](50) not null,
  STITLE      [nvarchar](100),
  SCONTEXT    [nvarchar](1000),
  SDATETIME   [datetime],
  SSTATE      [nvarchar](100),
  SACTION     [nvarchar](100),
  SPERSONID   [nvarchar](32),
  SPERSONNAME [nvarchar](100),
  SORGID      [nvarchar](32),
  SORGNAME    [nvarchar](100),
  VERSION     [int]
)ON [PRIMARY] 

alter table [dbo].[SA_REMINDINFO] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_PSNSCHEDULE]
(
  SID             [nvarchar](100) not null,
  SCAPTION        [nvarchar](100),
  SSTARTDATE      [datetime],
  SENDDATE        [datetime],
  SCONTENT        [nvarchar](4000),
  SPRIORITY       [int],
  SSTATUS         [nvarchar](50),
  SSTARTDATE_AXIS [int],
  SSENDDATE_AXIS  [int],
  SAFFAIRSTYPE    [int],
  SWHOUSER        [nvarchar](100),
  VERSION         [int]
)ON [PRIMARY] 

alter table [dbo].[SA_PSNSCHEDULE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_PSNMYTASK]
(
  SID             [nvarchar](64) not null,
  SCAPTION        [nvarchar](100),
  SSTARTDATE      [datetime],
  SENDDATE        [datetime],
  SPRIORITY       [int],
  SCONTENT        [nvarchar](1000),
  SSTATUS         [nvarchar](30),
  SAFFAIRSTYPE    [int],
  SCOMPLETERATE   [int],
  SSTARTDATE_AXIS [int],
  SSENDDATE       [int],
  SWHOUSER        [nvarchar](100),
  VERSION         [int],
  SSENDDATE_AXIS  [int]
)ON [PRIMARY] 

alter table [dbo].[SA_PSNMYTASK] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_PROJECT]
(
  SID               [int] not null,
  SNAME             [nvarchar](50) not null,
  CREATE_DATE       [datetime],
  CREATOR           [int],
  CREATE_DEPARTMENT [int] not null,
  STARTDATE         [nvarchar](50),
  ENDDATE           [nvarchar](19),
  STATUS            [nvarchar](10),
  DESCRIPTION       [nvarchar](255),
  PRIORITY          [int],
  VERSION           [int]
)ON [PRIMARY] 

alter table [dbo].[SA_PROJECT] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_PORTELINFO]
(
  SID          [nvarchar](100) not null,
  STHEMETYPE   [nvarchar](10),
  SPSNDESKINFO [text],
  SPERSONID    [nvarchar](100)
)ON [PRIMARY] 

alter table [dbo].[SA_PORTELINFO] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_OPROLEROLE]
(
  SID         [nvarchar](36) not null,
  SROLEID     [nvarchar](36) not null,
  SPARENTROLE [nvarchar](36) not null,
  VERSION     [int]
)ON [PRIMARY] 

alter table [dbo].[SA_OPROLEROLE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_OPROLEMANAGE]
(
  SID         [nvarchar](36) not null,
  SORGID      [nvarchar](36),
  SORGFID     [nvarchar](360),
  SPERSONID   [nvarchar](36),
  SROLEID     [nvarchar](36),
  SCREATORID  [nvarchar](36),
  SCREATETIME [datetime],
  VERSION     [int]
)ON [PRIMARY] 

alter table [dbo].[SA_OPROLEMANAGE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_OPROLE]
(
  SID          [nvarchar](36) not null,
  SNAME        [nvarchar](64),
  SCODE        [nvarchar](36),
  SCATALOG     [nvarchar](36),
  SROLEKIND    [nvarchar](36),
  SPARENTNAMES [nvarchar](512),
  SDESCRIPTION [nvarchar](255),
  SSEQUENCE    [int],
  SVALIDSTATE  [int],
  VERSION      [int]
)ON [PRIMARY] 

alter table [dbo].[SA_OPROLE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
create index DYSYS_OPROLE on SA_OPROLE (SROLEKIND)
create index IDX_OPROLE_CODE on SA_OPROLE (SCODE)

create table [dbo].[SA_OPPERSON_IMPTEST]
(
  SID             [nvarchar](36) not null,
  SNAME           [nvarchar](64) not null,
  SCODE           [nvarchar](64) not null,
  SNUMB           [int],
  SLOGINNAME      [nvarchar](64),
  SPASSWORD       [nvarchar](64),
  SMAINORGID      [nvarchar](36),
  SSAFELEVELID    [nvarchar](36),
  SSEQUENCE       [int],
  SVALIDSTATE     [int],
  SDESCRIPTION    [nvarchar](255),
  SSEX            [nvarchar](8),
  SBIRTHDAY       [datetime],
  SJOINDATE       [datetime],
  SHOMEPLACE      [nvarchar](64),
  SDEGREE         [nvarchar](16),
  SGRADUATESCHOOL [nvarchar](128),
  SSPECIALITY     [nvarchar](128),
  SSCHOOLLENGTH   [nvarchar](16),
  STITLE          [nvarchar](64),
  SMARRIAGE       [nvarchar](4),
  SCARDNO         [nvarchar](36),
  SCARDKIND       [nvarchar](64),
  SFAMILYADDRESS  [nvarchar](255),
  SZIP            [nvarchar](6),
  SMSN            [nvarchar](64),
  SQQ             [nvarchar](36),
  SMAIL           [nvarchar](64),
  SMOBILEPHONE    [nvarchar](36),
  SFAMILYPHONE    [nvarchar](36),
  SOFFICEPHONE    [nvarchar](36),
  VERSION         [int],
  SPHOTO          image,
  SPROVINCE       [nvarchar](64),
  SCOUNTRY        [nvarchar](64),
  SCITY           [nvarchar](64),
  SPOSITIONS      [nvarchar](64),
  SSCHOOL         [nvarchar](64),
  SSTUDY          [nvarchar](64),
  SIDCARD         [nvarchar](100),
  SENGLISHNAME    [nvarchar](100),
  SFAX            [nvarchar](100)
)ON [PRIMARY] 

alter table [dbo].[SA_OPPERSON_IMPTEST] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_OPPERSONMEMBER]
(
  SID       [nvarchar](36) not null,
  SORGID    [nvarchar](36) not null,
  SPERSONID [nvarchar](36) not null,
  VERSION   [int]
)ON [PRIMARY] 

alter table [dbo].[SA_OPPERSONMEMBER] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create index IDX_OPPM_ORGID on SA_OPPERSONMEMBER (SORGID)
create index IDX_OPPM_PERSONID on SA_OPPERSONMEMBER (SPERSONID)

CREATE TABLE [dbo].[SA_OPPerson] (
	[sID] [nvarchar] (36)  NOT NULL ,
	[sName] [nvarchar] (64)  NOT NULL ,
	[sCode] [nvarchar] (64)  NOT NULL ,
	[sIDCard] [nvarchar] (36) NULL,
	[sNumb] [int] NOT NULL ,
	[sLoginName] [nvarchar] (64)  NULL ,
	[sPassword] [nvarchar] (64)  NULL ,
	[sMainOrgID] [nvarchar] (36)  NOT NULL ,
	[sSafeLevelID] [nvarchar] (36)  NULL ,
	[sSequence] [int] NULL ,
	[sValidState] [int] NULL ,
	[sDescription] [nvarchar] (255)  NULL ,
	[sSex] [nvarchar] (8)  NULL ,
	[sBirthday] [datetime] NULL ,
	[sJoinDate] [datetime] NULL ,
	[sHomePlace] [nvarchar] (64)  NULL ,
	[sDegree] [nvarchar] (16)  NULL ,
	[sGraduateSchool] [nvarchar] (128)  NULL ,
	[sSpeciality] [nvarchar] (128)  NULL ,
	[sSchoolLength] [nvarchar] (16)  NULL ,
	[sTitle] [nvarchar] (64)  NULL ,
	[sMarriage] [nvarchar] (4)  NULL ,
	[sCardNO] [nvarchar] (36)  NULL ,
	[sCardKind] [nvarchar] (64)  NULL ,
	[sFamilyAddress] [nvarchar] (255)  NULL ,
	[sZip] [nvarchar] (6)  NULL ,
	[sMsn] [nvarchar] (64)  NULL ,
	[sQQ] [nvarchar] (36)  NULL ,
	[sMail] [nvarchar] (64)  NULL ,
	[sMobilePhone] [nvarchar] (36)  NULL ,
	[sFamilyPhone] [nvarchar] (36)  NULL ,
	[sOfficePhone] [nvarchar] (36)  NULL ,
	[version] [int] NULL ,
	[sPhoto] [image] NULL ,
	[sCountry] [nvarchar] (64)  NULL ,
	[sProvince] [nvarchar] (64)  NULL ,
	[sCity] [nvarchar] (64)  NULL ,
	[SPOSITIONS] [nvarchar] (64)  NULL ,
	[SSCHOOL] [nvarchar] (64)  NULL ,
	[SSTUDY] [nvarchar] (64)  NULL ,
	[sEnglishName] [nvarchar] (128)  NULL,
	[FCASN] [nvarchar] (100)  NULL,
	[FSIGNM] [nvarchar] (100)  NULL   
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
 
ALTER TABLE [dbo].[SA_OPPerson] ADD 
	PRIMARY KEY  CLUSTERED 
	(
		[sID]
	)  ON [PRIMARY]   
	
create unique index IDX_OPPERSON_SCODE on SA_OPPERSON (SCODE)
create unique index ONLYONE_MUST on SA_OPPERSON (SLOGINNAME)
create index SA_OPPERSON_MAINORGID on SA_OPPERSON (SMAINORGID)
create index SA_OPPERSON_SVALIDSTATE on SA_OPPERSON (SVALIDSTATE)

CREATE TABLE [dbo].[SA_OPPermission] (
	[sID] [nvarchar] (32)  NOT NULL ,
	[sPermissionRoleID] [nvarchar] (32)  NOT NULL ,
	[sProcess] [nvarchar] (1024)  NULL ,
	[sActivityFName] [nvarchar] (1024)  NULL ,
	[sActivity] [nvarchar] (1024)  NULL ,
	[sActionsNames] [nvarchar] (1024)  NULL ,
	[sActions] [ntext] NULL ,
	[sSemanticDP] [nvarchar] (2048)  NULL ,
	[sPermissionKind] [int] NULL ,
	[sDescription] [nvarchar] (1024)  NULL ,
	[sSequence] [int] NULL ,
	[sValidState] [int] NULL ,
	[version] [int] NOT NULL 
) ON [PRIMARY]
 
ALTER TABLE [dbo].[SA_OPPermission] ADD 
	PRIMARY KEY  CLUSTERED 
	(
		[sID]
	)  ON [PRIMARY]  
	
create index IDX_OPPERM_ACTIVITY on SA_OPPERMISSION (SACTIVITY)
create index IDX_OPPERM_PROCESS on SA_OPPERMISSION (SPROCESS)
create index IDX_OPPERM_ROLEID on SA_OPPERMISSION (SPERMISSIONROLEID)

create table [dbo].[SA_OPORGENUM]
(
  SID     [nvarchar](36) not null,
  STYPE   [nvarchar](64),
  SCODE   [nvarchar](64),
  SNAME   [nvarchar](64),
  VERSION [int]
)ON [PRIMARY] 

alter table [dbo].[SA_OPORGENUM] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

CREATE TABLE [dbo].[SA_OPOrg] (
	[sID] [nvarchar] (65)  NOT NULL ,
	[sName] [nvarchar] (128)  NOT NULL ,
	[sCode] [nvarchar] (64)  NOT NULL ,
	[sLongName] [nvarchar] (255)  NULL ,
	[sFName] [nvarchar] (2048)  NULL ,
	[sFCode] [nvarchar] (2048)  NULL ,
	[sFID] [nvarchar] (2048)  NOT NULL ,
	[sOrgKindID] [nvarchar] (5)  NOT NULL ,
	[sSequence] [nvarchar] (128)  NULL ,
	[sValidState] [int] NULL ,
	[sParent] [nvarchar] (32)  NULL ,
	[sLevel] [int] NULL ,
	[sPhone] [nvarchar] (64)  NULL ,
	[sFax] [nvarchar] (64)  NULL ,
	[sAddress] [nvarchar] (255)  NULL ,
	[sZip] [nvarchar] (16)  NULL ,
	[sDescription] [nvarchar] (1024)  NULL ,
	[sPersonID] [nvarchar] (32)  NULL ,
	[sNodeKind] [nvarchar] (32)  NULL ,
	[version] [int] NOT NULL 
) ON [PRIMARY]
 
ALTER TABLE [dbo].[SA_OPOrg] ADD 
	PRIMARY KEY  CLUSTERED 
	(
		[sID]
	)  ON [PRIMARY] 
	
create index DYSYS_SAOPORG_SPARENT on SA_OPORG (SPARENT)
create index DYSYS_SAOPORG_SCODE on SA_OPORG (SCODE)
create index DYSYS_SAOPORG_SFID on SA_OPORG (SFID)
create index DYSYS_SAOPORG_SVALIDSTATE on SA_OPORG (SVALIDSTATE)

create table [dbo].[SA_OPMESSAGEPARA]
(
  SID         [nvarchar](50) not null,
  SCHATNUMBER [int],
  SSENDNUMBER [int],
  SFILESIZE   [int],
  SCANUSE     [int],
  SOPMID      [nvarchar](50) not null,
  SOPMKIND    [nvarchar](50) not null,
  [version] [int],
  SNAME       [nvarchar](50) not null
)ON [PRIMARY] 

alter table [dbo].[SA_OPMESSAGEPARA] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_OPMANAGETYPE]
(
  SID                [nvarchar](36) not null,
  SNAME              [nvarchar](36),
  SCODE              [nvarchar](36),
  SMANAGEORGKIND     [nvarchar](36),
  SMANAGEORGKINDNAME [nvarchar](60),
  SLEADERNUMBER      [int],
  SISSYSTEM          [int],
  VERSION            [int]
)ON [PRIMARY] 

alter table [dbo].[SA_OPMANAGETYPE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_OPMANAGEMENT]
(
  SID             [nvarchar](36) not null,
  SORGID          [nvarchar](36),
  SORGFID         [nvarchar](512),
  SPERSONID       [nvarchar](36),
  SMANAGEORGID    [nvarchar](36),
  SMANAGEORGFID   [nvarchar](512),
  SMANAGEPERSONID [nvarchar](36),
  SMANAGETYPEID   [nvarchar](36),
  SCREATORID      [nvarchar](36),
  SCREATETIME     [datetime],
  VERSION         [int],
  SORGNAME        [nvarchar](100),
  SORGFNAME       [nvarchar](2048),
  SCREATORFNAME   [nvarchar](2048),
  SMANAGEORGNAME  [nvarchar](100),
  SMANAGEORGFNAME [nvarchar](2048),
  SCREATORFID     [nvarchar](1024)
)ON [PRIMARY] 

alter table [dbo].[SA_OPMANAGEMENT] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
create index IDX_OPMANAGEMENT_SPERSONID on SA_OPMANAGEMENT (SPERSONID, SMANAGETYPEID)

create table [dbo].[SA_OPCHATPROMIS]
(
  SID             [nvarchar](50) not null,
  SOPMKIND        [nvarchar](50) not null,
  SOPMID          [nvarchar](50) not null,
  SNAME           [nvarchar](50) not null,
  SOTHERSIDEID    [nvarchar](50) not null,
  SOTHERSIDEKIND  [nvarchar](50) not null,
  SOTHERSIDENAME  [nvarchar](50) not null,
  SPROMISSIONTYPE [int] not null,
  VERSION         [int] not null,
  SOTHERSIDEURL   [nvarchar](512),
  SOPMURL         [nvarchar](512)
)ON [PRIMARY] 

alter table [dbo].[SA_OPCHATPROMIS] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

CREATE TABLE [dbo].[SA_OPAuthorize] (
	[sID] [nvarchar] (32)  NOT NULL ,
	[sOrgID] [nvarchar] (65)  NOT NULL ,
	[sOrgName] [nvarchar] (255)  NULL ,
	[sOrgFID] [nvarchar] (2048)  NOT NULL ,
	[sOrgFName] [nvarchar] (2048)  NULL ,
	[sAuthorizeRoleID] [nvarchar] (32)  NOT NULL ,
	[sDescription] [nvarchar] (1024)  NULL ,
	[sCreatorFID] [nvarchar] (2048)  NULL ,
	[sCreatorFName] [nvarchar] (2048)  NULL ,
	[sCreateTime] [datetime] NULL ,
	[version] [int] NOT NULL 
) ON [PRIMARY]
 
ALTER TABLE [dbo].[SA_OPAuthorize] ADD 
	PRIMARY KEY  CLUSTERED 
	(
		[sID]
	)  ON [PRIMARY]  
	
create index IDX_OPAUTH_ORGID on SA_OPAUTHORIZE (SORGID)
create index IDX_OPAUTH_ROLEID on SA_OPAUTHORIZE (SAUTHORIZEROLEID)

create table [dbo].[SA_OPAGENT]
(
  SID         [nvarchar](36) not null,
  SORGID      [nvarchar](36),
  SORGFID     [nvarchar](2048),
  SORGFNAME   [nvarchar](2048),
  SPERSONID   [nvarchar](36),
  SAGENTID    [nvarchar](36) not null,
  SACTIVE     [int],
  SVALIDSTATE [int],
  SSTARTTIME  [datetime],
  SFINISHTIME [datetime],
  SPROCESS    [text],
  SCREATORID  [nvarchar](36),
  SCREATORNAME [nvarchar](64),
  SCREATETIME [datetime],
  SCANTRANAGENT [int],
  VERSION     [int] not null
)ON [PRIMARY] 

alter table [dbo].[SA_OPAGENT] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_LOGINLOG]
(
  SID        [nvarchar](32) not null,
  SUSERID    [nvarchar](36),
  SUSERNAME  [nvarchar](100),
  SLOGINIP   [nvarchar](100),
  SLOGINTIME [datetime],
  PASSWORD   [nvarchar](100),
  VERSION    [int],
  SSERVICEIP [nvarchar](100),
  SDAY [nvarchar](10),
  SDAYNUM [int]
)ON [PRIMARY] 

alter table [dbo].[SA_LOGINLOG] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_LOG]
(
  SID                [nvarchar](100) not null,
  VERSION            [int],
  STYPENAME          [nvarchar](100),
  SDESCRIPTION       [nvarchar](1000),
  SCREATORFID        [nvarchar](1024),
  SCREATORFNAME      [nvarchar](1024),
  SCREATETIME        [datetime],
  SCREATORPERSONID   [nvarchar](100),
  SCREATORPERSONNAME [nvarchar](100),
  SCREATORPOSID      [nvarchar](100),
  SCREATORPOSNAME    [nvarchar](100),
  SCREATORDEPTID     [nvarchar](100),
  SCREATORDEPTNAME   [nvarchar](100),
  SCREATOROGNID      [nvarchar](100),
  SCREATOROGNNAME    [nvarchar](100),
  SPROCESS           [nvarchar](1024),
  SPROCESSNAME       [nvarchar](1024),
  SACTIVITY          [nvarchar](100),
  SACTIVITYNAME      [nvarchar](100),
  SACTIONNAME        [nvarchar](100),
  SACTION            [nvarchar](100),
  SSTATUSNAME        [nvarchar](100),
  SPARAMETERS        [nvarchar](100),
  SRESULT            [nvarchar](100),
  SESFIELD01         [nvarchar](100),
  SESFIELD02         [nvarchar](100),
  SESFIELD03         [nvarchar](100),
  SESFIELD04         [nvarchar](100),
  SEDFIELD21         [datetime],
  SEDFIELD22         [datetime],
  SETFIELD31         [text],
  SETFIELD32         [text],
  SEIFIELD41         [int],
  SEIFIELD42         [int],
  SEBFIELD51         VARBINARY(MAX),
  SEBFIELD52         VARBINARY(MAX),
  SENFIELD11         int,
  SENFIELD12         int,
  SIP                [nvarchar](100)
)ON [PRIMARY] 

alter table [dbo].[SA_LOG] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
create table [dbo].[SA_KVSEQUENCE]
(
  K [nvarchar](200) not null,
  V [int] not null
)ON [PRIMARY] 

alter table [dbo].[SA_KVSEQUENCE] ADD 
	PRIMARY KEY CLUSTERED(
		[K]
	) ON [PRIMARY] 

create table [dbo].[SA_HALTCAUTION]
(
  SID          [nvarchar](32) not null,
  STITLE       [nvarchar](1024),
  SSTARTTIME   [datetime],
  SENDTIME     [datetime],
  SATTENTION   integer,
  SCREATERID   [nvarchar](36),
  SCREATERNAME [nvarchar](100),
  SCREATETIME  [datetime],
  SISPUSHED    [int],
  VERSION      [int]
)ON [PRIMARY] 

alter table [dbo].[SA_HALTCAUTION] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
create table [dbo].[SA_FLOWTRACE]
(
  SID           [nvarchar](36) not null,
  SOPERATORID   [nvarchar](36),
  SOPERATORCODE [nvarchar](100),
  SOPERATORNAME [nvarchar](100),
  SCURL         [nvarchar](1024),
  SEURL         [nvarchar](1024),
  SCHECKPSN     [nvarchar](4000),
  VERSION       [int] not null
)ON [PRIMARY] 

alter table [dbo].[SA_FLOWTRACE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
create index SA_FLOWTRACE_SEURL on SA_FLOWTRACE (SEURL)
create index SA_FLOWTRACE_SOPERATORID on SA_FLOWTRACE (SOPERATORID)

create table [dbo].[SA_FLOWINFOEXT]
(
  ID            [nvarchar](100) not null,
  SPROCESS      [nvarchar](200) not null,
  SFROMACTIVITY [nvarchar](200) not null,
  SCREATORID    [nvarchar](50),
  SEXECUTORS    [text],
  STOACTIVITY   [nvarchar](500),
  VERSION       [int]
)ON [PRIMARY] 

alter table [dbo].[SA_FLOWINFOEXT] ADD 
	PRIMARY KEY CLUSTERED(
		[ID]
	) ON [PRIMARY] 

create table [dbo].[SA_FLOWINFO]
(
  ID                      [nvarchar](500) not null,
  STOACTIVITY             [nvarchar](500),
  STOOPERATORID           [nvarchar](4000),
  VERSION                 [int],
  SFROMACTIVITYINSTANCEID [nvarchar](500),
  STASKNAME               [nvarchar](500),
  STASKCONTENT            [nvarchar](500),
  STASKCREATETIME         [datetime],
  STASKEMERGENCYID        [int],
  STASKWARNINGTIME        [datetime],
  STASKLIMITTIME          [datetime]
)ON [PRIMARY] 

alter table [dbo].[SA_FLOWINFO] ADD 
	PRIMARY KEY CLUSTERED(
		[ID]
	) ON [PRIMARY] 

create table [dbo].[SA_FLOWFOLDER]
(
  SID          [nvarchar](32) not null,
  SPROCESSID   [nvarchar](100),
  SPROCESSNAME [nvarchar](100),
  SCODE        [nvarchar](100),
  SNAME        [nvarchar](100),
  SPARENT      [nvarchar](32),
  SIDPATH      [nvarchar](4000),
  SNAMEPATH    [nvarchar](4000),
  SCODEPATH    [nvarchar](4000),
  VERSION      [int] default 0 not null
)ON [PRIMARY] 

alter table [dbo].[SA_FLOWFOLDER] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create index SOU_SA_FLWFOLDER_SPARENT on SA_FLOWFOLDER (SPARENT)
	

create table [dbo].[SA_FLOWDRAWLG_IPO]
(
  SID          [nvarchar](32) not null,
  SPROCESSID   [nvarchar](100) not null,
  SPROCESSNAME [nvarchar](100),
  SDRAWLG      [text],
  VERSION      [int],
  SPROCESSACTY [text],
  SCREATORID   [nvarchar](100),
  SCREATORNAME [nvarchar](100),
  SUPDATORID   [nvarchar](100),
  SUPDATORNAME [nvarchar](100),
  SCREATETIME  [datetime],
  SUPDATETIME  [datetime],
  FENABLED     [int]
)ON [PRIMARY] 

alter table [dbo].[SA_FLOWDRAWLG_IPO] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_FLOWDRAWLG]
(
  SID          [nvarchar](32) not null,
  SPROCESSID   [nvarchar](100) not null,
  SPROCESSNAME [nvarchar](100),
  SDRAWLG      [text],
  VERSION      [int] default 0,
  SPROCESSACTY [text],
  SCREATORID   [nvarchar](100),
  SCREATORNAME [nvarchar](100),
  SUPDATORID   [nvarchar](100),
  SUPDATORNAME [nvarchar](100),
  SCREATETIME  [datetime],
  SUPDATETIME  [datetime],
  FENABLED     [int],
  SFOLDERID    [nvarchar](32)
)ON [PRIMARY] 

alter table [dbo].[SA_FLOWDRAWLG] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
create table [dbo].[SA_FLOWDATA]
(
  SID       [nvarchar](36) not null,
  SFLOWID   [nvarchar](36),
  SPROCESS  [nvarchar](36),
  SACTIVITY [nvarchar](255),
  SACTION   [nvarchar](255),
  SKIND     [nvarchar](36),
  SCONTENT  [text],
  VERSION   [int]
)ON [PRIMARY] 

alter table [dbo].[SA_FLOWDATA] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_FLOWCONTROL]
(
  SID         [nvarchar](36) not null,
  SOPERATORID [nvarchar](50),
  SPROCESS    [nvarchar](255),
  SACTIVITY   [nvarchar](255),
  SACTION     [nvarchar](36),
  SKIND       [nvarchar](16),
  SCONTENT    [text],
  VERSION     [int]
)ON [PRIMARY] 

alter table [dbo].[SA_FLOWCONTROL] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_FILTERPATTERN]
(
  SID       [nvarchar](36) not null,
  SPERSON   [nvarchar](36) not null,
  SPROCESS  [nvarchar](255) not null,
  SACTIVITY [nvarchar](128) not null,
  SINSTANCE [nvarchar](128) not null,
  SNAME     [nvarchar](128) not null,
  SCONTENT  [text],
  VERSION   [int],
  SORDERNUM [int] not null
)ON [PRIMARY] 

alter table [dbo].[SA_FILTERPATTERN] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_ENUMTYPE]
(
  SID     [nvarchar](36) not null,
  STYPE   [nvarchar](50),
  SCODE   [nvarchar](50) not null,
  SNAME   [nvarchar](50),
  VERSION [int]
)ON [PRIMARY] 

alter table [dbo].[SA_ENUMTYPE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_EMAILRECEIVE]
(
  SID        [nvarchar](100) not null,
  SFROMID    [nvarchar](100),
  SFROM      [nvarchar](100),
  STONAME    [nvarchar](100),
  STOID      [nvarchar](100),
  SISHASREAD [nvarchar](100),
  EMAILID    [nvarchar](100),
  SSENDTYPE  [nvarchar](50),
  SSTATUS    [nvarchar](50)
)ON [PRIMARY] 

alter table [dbo].[SA_EMAILRECEIVE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_DOCNODE]
(
  SID                 [nvarchar](36) not null,
  SPARENTID           [nvarchar](36),
  SDOCNAME            [nvarchar](128),
  SSEQUENCE           [nvarchar](128),
  SSIZE               float,
  SKIND               [nvarchar](128),
  SDOCPATH            [text],
  SDOCDISPLAYPATH     [text],
  SCREATORFID         [nvarchar](512),
  SCREATORNAME        [nvarchar](64),
  SCREATORDEPTNAME    [nvarchar](64),
  SCREATETIME         [datetime],
  SEDITORFID          [nvarchar](512),
  SEDITORNAME         [nvarchar](64),
  SEDITORDEPTNAME     [nvarchar](64),
  SLASTWRITERFID      [nvarchar](512),
  SLASTWRITERNAME     [nvarchar](64),
  SLASTWRITERDEPTNAME [nvarchar](64),
  SLASTWRITETIME      [datetime],
  SFILEID             [nvarchar](128),
  SDESCRIPTION        [text],
  SDOCLIVEVERSIONID   [int],
  VERSION             [int],
  SCLASSIFICATION     [nvarchar](128),
  SKEYWORDS           [nvarchar](256),
  SDOCSERIALNUMBER    [nvarchar](128),
  SFINISHTIME         [datetime],
  SNAMESPACE          [nvarchar](256),
  SCACHENAME          [nvarchar](100),
  SREVISIONCACHENAME  [nvarchar](100),
  SFLAG               [int] default 1,
  SCREATORID          [nvarchar](36)
)ON [PRIMARY] 

alter table [dbo].[SA_DOCNODE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_DOCNAMESPACE]
(
  SID          [nvarchar](128) not null,
  SDISPLAYNAME [nvarchar](256),
  SHOST        [nvarchar](128),
  SPORT        [int],
  SURL         [nvarchar](128),
  VERSION      [int],
  SFLAG        [int] default 1,
  SACCESSMODE  [nvarchar](64)
)ON [PRIMARY] 

alter table [dbo].[SA_DOCNAMESPACE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_DOCLOG]
(
  VERSION       [int],
  SPERSONNAME   [nvarchar](64),
  SACCESSTYPE   [nvarchar](16),
  SDOCID        [nvarchar](64),
  SID           [nvarchar](36) not null,
  STIME         [datetime],
  SDEPTNAME     [nvarchar](64),
  SPERSONFID    [nvarchar](512),
  SDOCVERSIONID [int],
  SDOCNAME      [nvarchar](128),
  SSIZE         [float]
)ON [PRIMARY] 

alter table [dbo].[SA_DOCLOG] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_DOCLINKDEFINE]
(
  SID           [nvarchar](36) not null,
  SROOTPATH     [nvarchar](2048),
  SROOTNAME     [nvarchar](512),
  SPROCESS      [nvarchar](512),
  SACTIVITY     [nvarchar](128),
  SPERSONFID    [nvarchar](512),
  SSUBPATH      [nvarchar](512),
  SACCESS       [int],
  VERSION       [int],
  SDEPTNAME     [nvarchar](64),
  SPERSONNAME   [nvarchar](64),
  SLIMITSIZE    [float],
  SSHOWCHILDREN [nvarchar](36),
  SDEFINEITEMS  [text],
  SDISPLAYNAME  [nvarchar](512)
)ON [PRIMARY] 

alter table [dbo].[SA_DOCLINKDEFINE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_DOCLINK]
(
  SID       [nvarchar](36) not null,
  SOWNERID  [nvarchar](256),
  SDOCID    [nvarchar](36),
  VERSION   [int],
  SACTIVITY [nvarchar](128),
  SPROCESS  [nvarchar](512)
)ON [PRIMARY] 

alter table [dbo].[SA_DOCLINK] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_DOCAUTH]
(
  SID                 [nvarchar](36) not null,
  SDOCPATH            [text],
  SAUTHORIZERFID      [nvarchar](512),
  SAUTHORIZERNAME     [nvarchar](64),
  SAUTHORIZERDEPTNAME [nvarchar](64),
  SAUTHORIZEEFID      [nvarchar](512),
  SAUTHORIZEENAME     [nvarchar](64),
  SGRANTTIME          [datetime],
  SACCESS             [int],
  SSCOPE              [nvarchar](16),
  SAUTHORIZEEDEPTNAME [nvarchar](64),
  VERSION             [int]
)ON [PRIMARY] 

alter table [dbo].[SA_DOCAUTH] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_CUSTOMER]
(
  SID          [nvarchar](36) not null,
  VERSION      [int],
  SNAME        [nvarchar](50),
  DESCRIPTION  [nvarchar](50),
  SHORT_NAME   [nvarchar](50),
  MANAGER      [nvarchar](50),
  PHONE_NUMBER [nvarchar](50)
)ON [PRIMARY] 

alter table [dbo].[SA_CUSTOMER] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_CHANGEAPPLY]
(
  FID               [nvarchar](100) not null,
  FTITLE            [nvarchar](100),
  FAPPLYDATE        [datetime],
  FAPPLYPERSON      [nvarchar](100),
  FAPPLYDEPARTMENT  [nvarchar](100),
  FTELPHONE         [nvarchar](100),
  FSYSTEMNAME       [nvarchar](100),
  FCHANGEFUNNAME    [nvarchar](100),
  FCCAUSEREMARK     [nvarchar](100),
  FCHANGEPLANREMARK [nvarchar](100),
  FBUSDEPOPTION     [nvarchar](100),
  FINFODEPOPTION    [nvarchar](100),
  FCOMOPTION        [nvarchar](100),
  FINFODEPFOROPTION [nvarchar](100),
  FSOFTCOMOPTION    [nvarchar](100),
  VERSION           int
)ON [PRIMARY] 

alter table [dbo].[SA_CHANGEAPPLY] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[SA_ADDRESSBOOKGROUP]
(
  SID        [nvarchar](100) not null,
  VERSION    [int],
  SPSNID     [nvarchar](100),
  SGROUPNAME [nvarchar](100),
  SGROUPTYPE [nvarchar](100),
  SORDERNUM  [int],
  SGROUPCODE [nvarchar](100)
)ON [PRIMARY] 

alter table [dbo].[SA_ADDRESSBOOKGROUP] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
CREATE TABLE [dbo].[SA_ADDRESSBOOK] (
  SID nvarchar(32) NOT NULL,
  SGROUP nvarchar(100),
  SORDER int,
  SNAME nvarchar(100),
  SEX nvarchar(10),
  SNICKNAME nvarchar(100),
  SMOBILEPHONE nvarchar(20),
  SEMAIL nvarchar(100),
  SQQ nvarchar(100),
  SMSN nvarchar(100),
  SJOB nvarchar(100),
  SBIRTHDAY date,
  SSPOUSE nvarchar(100),
  SCHILDREN nvarchar(100),
  SCOMNAME nvarchar(100),
  SCOMADDRESS nvarchar(100),
  SCOMPOSTCODE nvarchar(100),
  SWORKTEL nvarchar(100),
  SWORKFAX nvarchar(100),
  SHOMEADDRESS nvarchar(100),
  SHOMEPOSTCODE nvarchar(100),
  SHOMETEL nvarchar(100),
  SFREMARK nvarchar(100),
  version int
)ON [PRIMARY] 
alter table [dbo].[SA_ADDRESSBOOK] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[PUB_SYSCODE]
(
  FID           [nvarchar](36) not null,
  FCODE         [nvarchar](36),
  FSCODE        [nvarchar](36),
  FSCOPE        [nvarchar](36),
  FSCOPENAME    [nvarchar](36),
  FNAME         [nvarchar](64),
  FSNAME        [nvarchar](64),
  FDESCRIPTION  [nvarchar](255),
  FPARENTID     [nvarchar](36),
  FLEVEL        [int],
  FURL          [nvarchar](255),
  FSEQUENCE     [int],
  FCREATEDEPTID [nvarchar](36),
  FCREATEPERID  [nvarchar](36),
  FCREATETIME   [datetime],
  FDISUSETIME   [datetime],
  FUSESTATUS    [int],
  FUPDATEDEPTID [nvarchar](36),
  FUPDATEPERID  [nvarchar](36),
  FUPDATETIME   [datetime],
  VERSION       [int]
)ON [PRIMARY] 

alter table [dbo].[PUB_SYSCODE] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[PUB_PROCESS]
(
  FID               [nvarchar](36) not null,
  FMASTERID         [nvarchar](36),
  FBIZKIND          [nvarchar](36),
  FNODEID           [nvarchar](128),
  FNODENAME         [nvarchar](36),
  FNODEBIZKIND      [nvarchar](36),
  FTASKID           [nvarchar](36),
  FTASKMSGID        [nvarchar](36),
  FDEPTID           [nvarchar](64),
  FDEPTNAME         [nvarchar](64),
  FPOSITIONID       [nvarchar](64),
  FPERSONID         [nvarchar](64),
  FPERSONNAME       [nvarchar](64),
  FDATE             [datetime],
  FVERDICT          [nvarchar](36),
  FOPINION          [nvarchar](1024),
  FREMARK           [nvarchar](1024),
  FPROCESSSTATE     [nvarchar](36),
  FPROCESSSTATENAME [nvarchar](64),
  FCREATEDEPTID     [nvarchar](64),
  FCREATEDEPTNAME   [nvarchar](128),
  FCREATEPOSID      [nvarchar](64),
  FCREATEPERID      [nvarchar](64),
  FCREATEPERNAME    [nvarchar](64),
  FCREATEURL        [nvarchar](255),
  FCREATETIME       [datetime],
  FUPDATEDEPTID     [nvarchar](64),
  FUPDATEDEPTNAME   [nvarchar](128),
  FUPDATEPOSID      [nvarchar](64),
  FUPDATEPERID      [nvarchar](64),
  FUPDATEPERNAME    [nvarchar](64),
  FUPDATETIME       [datetime],
  FSIGN             int,
  VERSION           [int]
)ON [PRIMARY] 

alter table [dbo].[PUB_PROCESS] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[PUB_BIZKIND]
(
  FCODE        [nvarchar](36) not null,
  FSCOPE       [nvarchar](36) not null,
  FCONTENT     [nvarchar](64) not null,
  FDESCRIPTION [nvarchar](255),
  FDOCURL      [nvarchar](255),
  FSEQUENCE    [int],
  VERSION      [int]
)ON [PRIMARY] 

create table [dbo].[PUB_BASECODE]
(
  FID             [nvarchar](36) not null,
  FCODE           [nvarchar](36),
  FSCODE          [nvarchar](36),
  FSCOPE          [nvarchar](36),
  FSCOPENAME      [nvarchar](36),
  FNAME           [nvarchar](64),
  FSNAME          [nvarchar](64),
  FDESCRIPTION    [nvarchar](255),
  FPARENTID       [nvarchar](36),
  FLEVEL          [int],
  FURL            [nvarchar](255),
  FSEQUENCE       [int],
  FCREATEDEPTID   [nvarchar](36),
  FCREATEDEPTNAME [nvarchar](128),
  FCREATEPERID    [nvarchar](36),
  FCREATEPERNAME  [nvarchar](64),
  FCREATETIME     [datetime],
  FDISUSETIME     [datetime],
  FUSESTATUS      [int],
  FUSESTATUSNAME  [nvarchar](64),
  FCANEDIT        [int],
  FCANDELETE      [int],
  FUPDATEDEPTID   [nvarchar](36),
  FUPDATEDEPTNAME [nvarchar](128),
  FUPDATEPERID    [nvarchar](36),
  FUPDATEPERNAME  [nvarchar](64),
  FUPDATETIME     [datetime],
  VERSION         [int]
)ON [PRIMARY] 

create table [dbo].[PLANS_ENUMERATION_TYPES]
(
  ID        [int],
  ENUM_TYPE [nvarchar](50),
  CODE      [nvarchar](50),
  ENUM_NAME [nvarchar](50),
  VERSION   [int]
)ON [PRIMARY] 

create table [dbo].[PLACEINSTANCE]
(
  ID                  [int] not null,
  PROCESS_INSTANCE_ID [int],
  NAME                [nvarchar](500),
  OFPLACE             [nvarchar](500),
  VERSION             [int]
)ON [PRIMARY] 

alter table [dbo].[PLACEINSTANCE] ADD 
	PRIMARY KEY CLUSTERED(
		[ID]
	) ON [PRIMARY] 

create index IX_PLACEINSTANCE_001 on PLACEINSTANCE (PROCESS_INSTANCE_ID)

create table [dbo].[PERSONAL_FILE]
(
  SID                [nvarchar](100) not null,
  SFILENAME          [nvarchar](100),
  SFILESIZE          [nvarchar](100),
  SDOCPATH           [text],
  SFILEID			 [nvarchar](100),
  VERSION            [int],
  DOCID              [nvarchar](100),
  SCREATORID         [nvarchar](100),
  SCREATORNAME       [nvarchar](100),
  SMASTERID          [nvarchar](100),
  SACCESSORY         [text],
  SACCESSCURRENTID   [nvarchar](1024),
  SACCESSCURRENTNAME [nvarchar](1024)
)ON [PRIMARY] 

alter table [dbo].[PERSONAL_FILE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[PERSONALDOCNODE]
(
  SID                [nvarchar](100) not null,
  VERSION            [int],
  SPARENTID          [nvarchar](100),
  SPARENTNAME        [nvarchar](100),
  SCREATORID         [nvarchar](100),
  SCREATORNAME       [nvarchar](100),
  SPATH              [nvarchar](100),
  DESCRIPTION        [nvarchar](1024),
  SACCESSCURRENTID   [nvarchar](1024),
  SACCESSCURRENTNAME [nvarchar](1024)
)ON [PRIMARY] 

alter table [dbo].[PERSONALDOCNODE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[OA_PUB_SYSCODE]
(
  FCODE         [nvarchar](36) not null,
  FSCODE        [nvarchar](36),
  FSCOPE        [nvarchar](36) not null,
  FSCOPENAME    [nvarchar](36),
  FNAME         [nvarchar](64),
  FSNAME        [nvarchar](64),
  FDESCRIPTION  [nvarchar](255),
  FPARENTID     [nvarchar](36),
  FLEVEL        [int],
  FURL          [nvarchar](255),
  FSEQUENCE     [int],
  FCREATEDEPTID [nvarchar](36),
  FCREATEPERID  [nvarchar](36),
  FCREATETIME   [datetime],
  FDISUSETIME   [datetime],
  FUSESTATUS    [int],
  FUPDATEDEPTID [nvarchar](36),
  FUPDATEPERID  [nvarchar](36),
  FUPDATETIME   [datetime],
  VERSION       [int]
)ON [PRIMARY] 

create table [dbo].[OA_PUB_PROCESS]
(
  FID               [nvarchar](36) not null,
  FMASTERID         [nvarchar](36),
  FBIZKIND          [nvarchar](36),
  FNODEID           [nvarchar](255),
  FNODENAME         [nvarchar](255),
  FNODEBIZKIND      [nvarchar](255),
  FTASKID           [nvarchar](36),
  FTASKMSGID        [nvarchar](36),
  FDEPTID           [nvarchar](64),
  FDEPTNAME         [nvarchar](64),
  FPOSITIONID       [nvarchar](36),
  FPERSONID         [nvarchar](36),
  FPERSONNAME       [nvarchar](64),
  FDATE             [datetime],
  FVERDICT          [nvarchar](36),
  FOPINION          [nvarchar](1024),
  FREMARK           [nvarchar](1024),
  FPROCESSSTATE     [nvarchar](36),
  FPROCESSSTATENAME [nvarchar](64),
  FCREATEDEPTID     [nvarchar](36),
  FCREATEDEPTNAME   [nvarchar](64),
  FCREATEPOSID      [nvarchar](36),
  FCREATEPERID      [nvarchar](36),
  FCREATEPERNAME    [nvarchar](64),
  FCREATEURL        [nvarchar](1024),
  FCREATETIME       [datetime],
  FUPDATEDEPTID     [nvarchar](36),
  FUPDATEDEPTNAME   [nvarchar](64),
  FUPDATEPOSID      [nvarchar](36),
  FUPDATEPERID      [nvarchar](36),
  FUPDATEPERNAME    [nvarchar](64),
  FUPDATETIME       [datetime],
  FSIGN             VARBINARY(MAX),
  VERSION           [int],
  FBIZKINDNAME      [nvarchar](64),
  FCREATEURLNAME    [nvarchar](1024)
)ON [PRIMARY] 

alter table [dbo].[OA_PUB_PROCESS] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 
	
create index OA_PUB_PROCESS_INDEX_MASTERID on OA_PUB_PROCESS (FMASTERID)

create table [dbo].[OA_PUB_BASECODE]
(
  FCODE           [nvarchar](36) not null,
  FSCODE          [nvarchar](36),
  FSCOPE          [nvarchar](36) not null,
  FSCOPENAME      [nvarchar](36),
  FNAME           [nvarchar](64),
  FSNAME          [nvarchar](64),
  FDESCRIPTION    [nvarchar](255),
  FPARENTID       [nvarchar](36),
  FLEVEL          [int],
  FURL            [nvarchar](255),
  FSEQUENCE       [int],
  FCREATEDEPTID   [nvarchar](36),
  FCREATEDEPTNAME [nvarchar](128),
  FCREATEPERID    [nvarchar](36),
  FCREATEPERNAME  [nvarchar](64),
  FCREATETIME     [datetime],
  FDISUSETIME     [datetime],
  FUSESTATUS      [int],
  FUSESTATUSNAME  [nvarchar](64),
  FCANEDIT        [int],
  FCANDELETE      [int],
  FUPDATEDEPTID   [nvarchar](36),
  FUPDATEDEPTNAME [nvarchar](128),
  FUPDATEPERID    [nvarchar](36),
  FUPDATEPERNAME  [nvarchar](64),
  FUPDATETIME     [datetime],
  VERSION         [int],
  FID             [nvarchar](64) not null
)ON [PRIMARY] 

alter table [dbo].[OA_PUB_BASECODE] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[OA_IS_READERRANGE]
(
  FID            [nvarchar](36) not null,
  FMASTERID      [nvarchar](36) not null,
  FCREATEPERID   [nvarchar](64),
  FCREATEPERNAME [nvarchar](64),
  FCREATETIME    [datetime],
  FORGKIND       [nvarchar](64),
  FORGID         [nvarchar](128),
  FORGNAME       [nvarchar](64),
  FPERSONID      [nvarchar](64),
  FPERSONNAME    [nvarchar](64),
  FRANGEURL      [nvarchar](512),
  FRANGEURLNAME  [nvarchar](1024),
  VERSION        [int]
)ON [PRIMARY] 

alter table [dbo].[OA_IS_READERRANGE] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[OA_IS_READER]
(
  FID         [nvarchar](36) not null,
  FMASTERID   [nvarchar](36) not null,
  FPERSONID   [nvarchar](36) not null,
  FPERSONNAME [nvarchar](64),
  FCREATETIME [datetime],
  VERSION     [int],
  FURL        [nvarchar](512),
  FURLNAME    [nvarchar](1024)
)ON [PRIMARY] 

alter table [dbo].[OA_IS_READER] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 
	
create unique index OA_PUB_READER_INDEX1 on OA_IS_READER (FMASTERID, FPERSONID)

create table [dbo].[OA_IS_INFOISSUE]
(
  FID              [nvarchar](36) not null,
  FTITLE           [nvarchar](128),
  FKEYWORD         [nvarchar](64),
  FTYPE            [nvarchar](36),
  FCLASSIFY        [nvarchar](36),
  FCHANNEL         [nvarchar](64),
  FAPPLYPS         [nvarchar](64),
  FAPPLYDEPT       [nvarchar](64),
  FAPPPOSITION     [nvarchar](64),
  FAPPLYDATE       [datetime],
  FAREA            [text],
  FIMPORTANCE      [nvarchar](36),
  FSECRETLEVEL     [nvarchar](36),
  FSTARTTIME       [datetime],
  FENDTIME         [datetime],
  FTOP             [nvarchar](4),
  FTOPTIME         [datetime],
  FEXIGENCELEVEL   [nvarchar](36),
  FREMARK          [nvarchar](1024),
  FDEPTURL         [nvarchar](255),
  FPOSITION        [nvarchar](64),
  FCREATEDEPTID    [nvarchar](36),
  FCREATEPERID     [nvarchar](36),
  FCREATEURL       [nvarchar](255),
  FCREATETIME      [datetime],
  FUPDATEDEPTID    [nvarchar](36),
  FUPDATEPERID     [nvarchar](36),
  FUPDATETIME      [datetime],
  FNO              [nvarchar](36),
  FBIZSTATE        [nvarchar](36),
  FBIZSTATENAME    [nvarchar](36),
  VERSION          [int],
  FCONTENT         [text],
  FCREATEORGANNAME [nvarchar](36),
  FCREATEDEPTNAME  [nvarchar](36),
  FCREATEPERNAME   [nvarchar](36),
  FUPDATEDEPTNAME  [nvarchar](36),
  FUPDATEPERNAME   [nvarchar](36),
  FIMPORTANCENAME  [nvarchar](36),
  FTYPENAME        [nvarchar](36),
  FISSUEID         [nvarchar](36),
  FISSUENAME       [nvarchar](36)
)ON [PRIMARY] 

alter table [dbo].[OA_IS_INFOISSUE] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[OA_IS_INFO]
(
  FID                   [nvarchar](36) not null,
  FTYPE                 [nvarchar](36),
  FTYPENAME             [nvarchar](64),
  FCLASSIFY             [nvarchar](36),
  FCLASSIFYNAME         [nvarchar](64),
  FBIZSTATE             [nvarchar](36),
  FBIZSTATENAME         [nvarchar](64),
  FISSUESTATE           [nvarchar](36),
  FISSUESTATENAME       [nvarchar](64),
  FNO                   [nvarchar](36),
  FTITLE                [nvarchar](128),
  FKEYWORD              [nvarchar](64),
  FORIGIN               [nvarchar](128),
  FIMPORTANCE           [nvarchar](36),
  FIMPORTANCENAME       [nvarchar](64),
  FCONTENT              [text],
  FREMARK               [nvarchar](1024),
  FISTOP                int,
  FTOPSTARTDATE         [datetime],
  FTOPENDDATE           [datetime],
  FISSUERANGE           [nvarchar](4000),
  FTEMPLATE             [nvarchar](36),
  FTEMPLATENAME         [nvarchar](64),
  FREDTITLE             [nvarchar](36),
  FSTAMP                [nvarchar](36),
  FSTAMPNAME            [nvarchar](64),
  FVERIFYDEPTID         [nvarchar](36),
  FVERIFYDEPTNAME       [nvarchar](64),
  FVERIFYPERID          [nvarchar](36),
  FVERIFYPERNAME        [nvarchar](64),
  FVERIFYURL            [nvarchar](512),
  FVERIFYURLNAME        [nvarchar](1024),
  FVERIFYTIME           [datetime],
  FCREATEDEPTID         [nvarchar](64),
  FCREATEDEPTNAME       [nvarchar](64),
  FCREATEPERID          [nvarchar](64),
  FCREATEPERNAME        [nvarchar](64),
  FCREATEURL            [nvarchar](512),
  FCREATEURLNAME        [nvarchar](1024),
  FCREATETIME           [datetime],
  FUPDATEPERID          [nvarchar](64),
  FUPDATEPERNAME        [nvarchar](64),
  FUPDATETIME           [datetime],
  VERSION               int,
  FISSUEDATE            [datetime],
  FREDTITLENAME         [nvarchar](64),
  FREADCOUNT            [int],
  FCOMMENTCOUNT         [int],
  FAUTHOR               [nvarchar](64),
  FEDITOR               [nvarchar](64),
  FDOCKIND              [nvarchar](36),
  FDOCKINDNAME          [nvarchar](64),
  FSENDDIRECT           [nvarchar](36),
  FSENDDIRECTNAME       [nvarchar](64),
  FDOCEXIGENCELEVEL     [nvarchar](36),
  FDOCEXIGENCELEVELNAME [nvarchar](64),
  FDOCSECRETLEVEL       [nvarchar](36),
  FDOCSECRETLEVELNAME   [nvarchar](64),
  FSECRETYEAR           [int],
  FSECRETMONTH          [int],
  FDOCISSUEDATE         [datetime],
  FDOCTYPENAME          [nvarchar](64),
  FDRAFTPERNAME         [nvarchar](64),
  FDRAFTTIME            [datetime],
  FDRAFTDEPTNAME        [nvarchar](64),
  FFAILUREDAY           [int],
  FISSUERANGEID         [nvarchar](4000),
  FACCESSORIES          [nvarchar](4000)
)ON [PRIMARY] 

alter table [dbo].[OA_IS_INFO] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 
	
create index OA_IS_INFO_INDEX1 on OA_IS_INFO (FTYPE)
create index OA_IS_INFO_INDEX2 on OA_IS_INFO (FCLASSIFY)
create index OA_IS_INFO_INDEX3 on OA_IS_INFO (FTITLE)
create index OA_IS_INFO_INDEX4 on OA_IS_INFO (FKEYWORD)
create index OA_IS_INFO_INDEX5 on OA_IS_INFO (FCREATEPERID)
create index OA_IS_INFO_INDEX6 on OA_IS_INFO (FCREATEURL)

create table [dbo].[OA_ISSUANCE]
(
  FID               [nvarchar](32) not null,
  VERSION           [int] default 0,
  FTITLE            [nvarchar](128),
  FKEYWORD          [nvarchar](128),
  FSYMBOL           [nvarchar](32),
  FIMPORTANCEDEGREE [nvarchar](32),
  FCONTENT          [text],
  FTHUMBNAIL        VARBINARY(MAX),
  FPUBLISHEDLOGO    [nvarchar](32),
  FCREATORID        [nvarchar](32),
  FCREATOR          [nvarchar](100),
  FCREATEDATE       [datetime],
  FOPENSCOPEDEPT    [nvarchar](32),
  FOPENSCOPEDEPTID  [nvarchar](100),
  FISFINISHNAME     [nvarchar](100),
  FISFINISH         [int],
  FUPDATORID        [nvarchar](32),
  FUPDATOR          [nvarchar](100),
  FUPDATETIME       [datetime],
  FACCESSORIES      [nvarchar](1024),
  FREMARK           [nvarchar](1024),
  FOPENSCOPE        [nvarchar](4000),
  FOPENSCOPEID      [nvarchar](4000),
  FBILLCODE         [nvarchar](32) not null,
  FPEOPLE           [nvarchar](100),
  FPEOPLEID         [nvarchar](32),
  FPEOPLEDATE       [datetime]
)ON [PRIMARY] 

alter table [dbo].[OA_ISSUANCE] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 
  
create table [dbo].[ERR_MSG]
(
  FID         [nvarchar](36) not null,
  REMARK      [nvarchar](4000),
  FCREATEDATE [datetime],
  FNOTE       [nvarchar](4000),
  VERSION     [int]
)ON [PRIMARY] 

alter table [dbo].[ERR_MSG] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 
	
create table [dbo].[sa_opmobilelog]
(
  SID         [nvarchar](36) not null,
  SUSERID      [nvarchar](100),
  SUSERNAME      [nvarchar](100),
  SIP      [nvarchar](100),
  SDATE [datetime],
  SMODE      [nvarchar](100),
  SSESSIONID       [nvarchar](100),
  SLOGOUTDATE [datetime],
  VERSION     [int]
)ON [PRIMARY] 

alter table [dbo].[sa_opmobilelog] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[DEMO_PROJECTS]
(
  FID               [nvarchar](36) not null,
  NAME              [nvarchar](50) not null,
  CREATE_DATE       [datetime],
  CREATOR           [int],
  CREATE_DEPARTMENT [int] not null,
  STARTDATE         [nvarchar](50),
  ENDDATE           [nvarchar](19),
  STATUS            [nvarchar](10),
  DESCRIPTION       [nvarchar](255),
  PRIORITY          [int],
  VERSION           [int]
)ON [PRIMARY] 

alter table [dbo].[DEMO_PROJECTS] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[DEMO_PLANS]
(
  FID     [nvarchar](36) not null,
  NAME    [nvarchar](50),
  VERSION [int]
)ON [PRIMARY] 

alter table [dbo].[DEMO_PLANS] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[DEMO_FC_LOANAPPLY1]
(
  FID           [nvarchar](36) not null,
  FMASTERID     [nvarchar](36),
  FCOSTTYPEID   [nvarchar](36),
  FCOSTTYPENAME [nvarchar](64),
  FCOSTAMT      float,
  FSTARTDATE    [datetime],
  FENDDATE      [datetime],
  FREMARK       [nvarchar](255),
  FISHAIRCUT    [int],
  VERSION       [int]
)ON [PRIMARY] 

alter table [dbo].[DEMO_FC_LOANAPPLY1] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[DEMO_FC_LOANAPPLY]
(
  FID             [nvarchar](36) not null,
  FNO             [nvarchar](36),
  FLOANERID       [nvarchar](36),
  FLOANERNAME     [nvarchar](16),
  FLOANDATE       [datetime],
  FLOANPTMID      [nvarchar](36),
  FLOANDEPTID     [nvarchar](36),
  FLOANDEPTNAME   [nvarchar](64),
  FDEPTURL        [nvarchar](255),
  FPLANID         [nvarchar](36),
  FPLANNAME       [nvarchar](128),
  FPROJECTID      [nvarchar](36),
  FPROJECTNAME    [nvarchar](128),
  FCLIENTERID     [nvarchar](36),
  FCLIENTERNAME   [nvarchar](128),
  FLOANPURPOSE    [nvarchar](255),
  FLOANAMT        float,
  FRETURNDATE     [datetime],
  FBIZSTATE       [nvarchar](36),
  FBIZSTATENAME   [nvarchar](16),
  FREMARK         [nvarchar](255),
  FCREATEDEPTID   [nvarchar](36),
  FCREATEDEPTNAME [nvarchar](64),
  FCREATEPERID    [nvarchar](64),
  FCREATEPERNAME  [nvarchar](36),
  FCREATETIME     [datetime],
  FBIZTYPEID      [nvarchar](36),
  FCOSTTYPEID     [nvarchar](36),
  FISHAVECUT      [int],
  FLOANTYPEID     [nvarchar](36),
  FLOANTYPENAME   [nvarchar](64),
  FOLDLOANBILLID  [nvarchar](36),
  FISCUTED        [int],
  VERSION         [int]
)ON [PRIMARY] 

alter table [dbo].[DEMO_FC_LOANAPPLY] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[DEMO_DIRECT2]
(
  SID     [nvarchar](32) not null,
  VERSION [int] default 0,
  SCODE   [nvarchar](100),
  SMAT    [nvarchar](100),
  SREMARK [nvarchar](400),
  SBILLID [nvarchar](32)
)ON [PRIMARY] 

alter table [dbo].[DEMO_DIRECT2] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[DEMO_DIRECT1]
(
  SID     [nvarchar](32) not null,
  VERSION [int] default 0,
  SCODE   [nvarchar](100) not null,
  SMAT    [nvarchar](100),
  SREMARK [nvarchar](400),
  SBILLID [nvarchar](32)
)ON [PRIMARY] 

alter table [dbo].[DEMO_DIRECT1] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[DEMO_DIRECT0]
(
  SID         [nvarchar](32) not null,
  VERSION     [int] default 0,
  SCODE       [nvarchar](100),
  SNAME       [nvarchar](100),
  SCREATEDATE [datetime]
)ON [PRIMARY] 

alter table [dbo].[DEMO_DIRECT0] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
create table [dbo].[DEMO_CUSTOMS]
(
  FID          [nvarchar](36) not null,
  VERSION      [int],
  NAME         [nvarchar](50),
  DESCRIPTION  [nvarchar](50),
  SHORT_NAME   [nvarchar](50),
  MANAGER      [nvarchar](50),
  PHONE_NUMBER [nvarchar](50)
)ON [PRIMARY] 

alter table [dbo].[DEMO_CUSTOMS] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[DEMO_COST_CODE]
(
  FID          [nvarchar](50) not null,
  FCODE        [nvarchar](50),
  FNAME        [nvarchar](50),
  FDESCRIPTION [nvarchar](50),
  FSEQUENCE    [int],
  FCREATETIME  [datetime],
  VERSION      [nvarchar](50)
)ON [PRIMARY] 

alter table [dbo].[DEMO_COST_CODE] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 

create table [dbo].[CYEA_NOTICE]
(
  SID          [nvarchar](32) not null,
  VERSION      [int],
  FTITLE       [nvarchar](100),
  FDATE        [datetime],
  FCLASS       [nvarchar](50),
  FNAME        [nvarchar](50),
  FCONTENT     [text],
  FMAJOR_LEVEL [nvarchar](10),
  FSTATE       [nvarchar](50),
  FACCESSORIES [nvarchar](1024),
  FOPENSCOPE   [nvarchar](4000),
  FOPENSCOPEID [nvarchar](4000),
  FREADCOUNT   int
)ON [PRIMARY] 

alter table [dbo].[CYEA_NOTICE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	
create index CYEA_NOTICE_FOPENSCOPEID on CYEA_NOTICE (FOPENSCOPEID)

create table [dbo].[CYEA_NEWS_RELEASE]
(
  SID                [nvarchar](32) not null,
  VERSION            [int],
  FNEWSTITLE         [nvarchar](200),
  FRELEASEDEPARTMENT [nvarchar](100),
  FPEOPLE            [nvarchar](50),
  FTIME              [datetime],
  FNEWSNUMBER        [nvarchar](100),
  FRELEASECONNEXT    [text],
  FSTATE             [nvarchar](20),
  FSETTOPWHETHER     [nvarchar](10),
  FSETTOPTIME        [datetime],
  FSETENDTIME        [datetime],
  FACCESSORIES       [nvarchar](1024),
  FCOLUMNNAME        [nvarchar](100),
  FOPENSCOPE         [nvarchar](4000),
  FOPENSCOPEID       [nvarchar](4000),
  SMINIPIC           VARBINARY(MAX)
)ON [PRIMARY] 

alter table [dbo].[CYEA_NEWS_RELEASE] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[CYEA_NEWS_COUNT]
(
  SID            [nvarchar](100) not null,
  NEWS_NUMBER    [int] default '0',
  NEWS_PERSON    [nvarchar](100),
  NEWS_PERSONID  [nvarchar](100),
  NEWS_RELEASEID [nvarchar](100),
  YETPERSON      [nvarchar](100),
  YETPERSONID    [nvarchar](100),
  VERSION        [int]
)ON [PRIMARY] 

alter table [dbo].[CYEA_NEWS_COUNT] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[CYEA_NEWSCOLUMN]
(
  SID              [nvarchar](32) not null,
  VERSION          [int],
  FCOLUMNCODE      [nvarchar](100),
  FCOLUMNNAME      [nvarchar](100),
  FCOLUMNDESCRIBES [nvarchar](100),
  FCOLUMNSTATE     [nvarchar](50)
)ON [PRIMARY] 

alter table [dbo].[CYEA_NEWSCOLUMN] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table CATEGORIES
(
  CATEGORYID   [int] not null,
  CATEGORYNAME [nvarchar](15) not null,
  DESCRIPTION  [nvarchar](1024),
  PICTURE      [nvarchar](1024),
  VERSION      [int]
)ON [PRIMARY] 

create table [dbo].[BO_ENTRY]
(
  SID           [nvarchar](100) not null,
  VERSION       [int],
  TITLE         [nvarchar](100),
  CONTENT       [text],
  CATEGORY_ID   [nvarchar](100),
  CATEGORY_NAME [nvarchar](100),
  COMMENT_HIT   [int],
  ALLOW_COMMENT [int],
  STATUS        [int] default 0,
  CREATED_TIME  [datetime],
  UPDATED_TIME  [datetime],
  HITS          [int],
  BO_BLOGID     [nvarchar](100),
  ACCESSORIES   [nvarchar](1024),
  FEXTEND01     [nvarchar](1024),
  ONESELF       [nvarchar](100),
  FID         [nvarchar](100)
)ON [PRIMARY] 

alter table [dbo].[BO_ENTRY] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[BO_CATEGORY]
(
  SID           [nvarchar](100) not null,
  VERSION       [int],
  NAME          [nvarchar](100),
  DISPLAY_ORDER [int],
  DESCRIPTION   [nvarchar](100),
  FMASTERID     [nvarchar](100)
)ON [PRIMARY] 

alter table [dbo].[BO_CATEGORY] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[BO_BLOG]
(
  SID                 [nvarchar](100) not null,
  VERSION             [int],
  NAME                [nvarchar](100),
  DESCRIPTION         [nvarchar](100),
  ENTRY_NUMBER        [int],
  RECENT_NUMBER       [int],
  RECENT_ENTRY_NUMBER [int],
  STATUS              [int],
  COMMENT_AUDIT       [nvarchar](100),
  OPENSCOPE           [nvarchar](100),
  SETTING             [nvarchar](100),
  BLOGCATEGORY        [nvarchar](100),
  CHAIRMAN            [nvarchar](100),
  CHAIRMAN_ID         [nvarchar](100)
)ON [PRIMARY] 

alter table [dbo].[BO_BLOG] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[AGENT_MSG_ERR]
(
  FID         [nvarchar](36) not null,
  FCREATEDATE [nvarchar](36),
  FNOTE       [nvarchar](1024),
  VERSION     [int]
)ON [PRIMARY] 

alter table [dbo].[AGENT_MSG_ERR] ADD 
	PRIMARY KEY CLUSTERED(
		[FID]
	) ON [PRIMARY] 
	
create table [dbo].[JPOLITE_PROFILES]
(
  JPOLITE_PROFILES_VALUE [text],
  JPOLITE_PROFILES_KEY   [nvarchar](100),
  JPOLITE_PROFILES_ID    [nvarchar](100)
)ON [PRIMARY]    

create table [dbo].[SA_PORTALPROFILES]
(
  SID       [nvarchar](100) not null,
  VERSION   [int],
  SNAME     [nvarchar](100),
  SPERSONID [nvarchar](100),
  SVALUE    [nvarchar](4000)
)ON [PRIMARY] 

alter table [dbo].[SA_PORTALPROFILES] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

  
create table [dbo].[SA_PORTELSETTING]
(
  SID                [nvarchar](100) not null,
  SPSNDESKINFO       [text],
  SPERSONID          [nvarchar](100),
  SPORTALTYPE        [nvarchar](50),
  SORDER             [int],
  SISOPENWHENLONGING [int]
)ON [PRIMARY] 

alter table [dbo].[SA_PORTELSETTING] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

  
create table [dbo].[DEMO_FLOW_TEST]
(
  SID         [nvarchar](36) not null,
  FSTARTTIME  [datetime],
  FENDTIME    [datetime],
  FCREATOR    [nvarchar](100),
  FCREATETIME [datetime],
  FRESOURCE   [nvarchar](1024),
  VERSION     [int]
)ON [PRIMARY] 

alter table [dbo].[DEMO_FLOW_TEST] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 
	  
  
create table [dbo].[BO_COMMENT]
(
  SID          [nvarchar](100) not null,
  AUTHOR       [nvarchar](100),
  EMAIL        [nvarchar](100),
  CONTENT      TEXT,
  CREATED_TIME [datetime],
  ENTRY_ID     [nvarchar](100),
  ENTRY_TITLE  [nvarchar](100),
  IP           [nvarchar](100),
  STATUS       [int],
  VERSION      [int]
)ON [PRIMARY] 

alter table [dbo].[BO_COMMENT] ADD 
	PRIMARY KEY CLUSTERED(
		[SID]
	) ON [PRIMARY] 

create table [dbo].[SA_OPROLEMANAGEMENT]
(
  SID           [nvarchar](32) not null,
  SROLEID       [nvarchar](32),
  SORGID        [nvarchar](100),
  SORGFNAME     [nvarchar](1024),
  SORGFID       [nvarchar](1024),
  SORGNAME      [nvarchar](100),
  SCREATORFID   [nvarchar](1024),
  SCREATORFNAME [nvarchar](1024),
  SCREATETIME   [datetime],
  VERSION       [int]
)ON [PRIMARY]

alter table [dbo].[SA_OPROLEMANAGEMENT] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY] 
  
create table [dbo].[SA_PORTALLETS]
(
  SPERSONID    [nvarchar](100) not null,
  SLAYOUTSET   [nvarchar](1024),
  SPANLES      text,
  SCREATORID   [nvarchar](100),
  SCREATORNAME [nvarchar](100),
  SCREATEDATE  [datetime]
)ON [PRIMARY]

alter table [dbo].[SA_PORTALLETS] ADD 
	PRIMARY KEY CLUSTERED (
		 [SPERSONID]
	) ON [PRIMARY] 

create table [dbo].[APP_PUSHMESSAGE]
(
  SID        [nvarchar](32) not null,
  SPERSONID  [nvarchar](100),
  SMESSAGE   [text],
  SSENDSTATE [int] default 0,
  SRESTATE   [int] default 0,
  STITLE     [nvarchar](100),
  VERSION    [int] default 0,
  SSDATE     [datetime],
  SVDATE     [datetime]
)ON [PRIMARY]

alter table [dbo].[APP_PUSHMESSAGE] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY] 
	  
create table [dbo].[SYS_DEFINEMENU]
(
  SID             [nvarchar](64) not null,
  FTITLE          [nvarchar](100),
  FURL            [nvarchar](200),
  FPERSONID       [nvarchar](64),
  FPERSONNAME     [nvarchar](200),
  FOGNID          [nvarchar](64),
  FOGNNAME        [nvarchar](200),
  FCREATEDATETIME DATETIME default getdate(),
  VERSION         int default 0,
  FORDER          int default 0
)ON [PRIMARY]

alter table [dbo].[SYS_DEFINEMENU] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY] 
	
create table [dbo].[DEMO_CUSTOMER_INFO]
(
  SID            [nvarchar](32),
  FCUSTORMERNAME [nvarchar](100),
  FSEX           [nvarchar](10),
  FUNITNAME      [nvarchar](100),
  FCREATERNAME   [nvarchar](100),
  FCREATETIME    datetime,
  FREMARK        [nvarchar](1024),
  VERSION        int
)ON [PRIMARY]

create table [dbo].[T_USERS]
(
  SID      [nvarchar](32) not null,
  FILECONT [nvarchar](400),
  USERID   [nvarchar](100),
  VERSION  int
)ON [PRIMARY]

alter table [dbo].[T_USERS] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY] 
   
create table [dbo].[SA_OPMACHINECODE]
(
  SID          [nvarchar](32) not null,
  VERSION      int,
  SUSERCODE    [nvarchar](100),
  SMACHINECODE [nvarchar](100),
  SCREATEDATE  datetime,
  SSATE        [nvarchar](100) default '0',
  SREMARK      [nvarchar](4000)
)ON [PRIMARY]

alter table [dbo].[SA_OPMACHINECODE] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY] 

CREATE TABLE [dbo].[sa_handwr_signature]
(
    SID                         [nvarchar](32) NOT NULL,
    SPERSONID                   [nvarchar](32),
    SPERSONNAME                 [nvarchar](100),
    SCREATERID                  [nvarchar](32),
    SCREATERNAME                [nvarchar](100),
    SCREATETIME                 datetime,
    SHSPIC                      image,
    VERSION                     int
)

alter table [dbo].[sa_handwr_signature] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY] 
	
create table [dbo].[SA_ONLINEINFO]
(
  SID          [nvarchar](32) not null,
  SUSERID      [nvarchar](36),
  SUSERNAME    [nvarchar](100),
  SUSERFID     [nvarchar](1024),
  SUSERFNAME   [nvarchar](1024),
  SLOGINIP     [nvarchar](100),
  SLOGINDATE   datetime,
  SSESSIONID   [nvarchar](100),
  SSERVICEIP   [nvarchar](100),
  VERSION      int,
  SMACHINECODE [nvarchar](100)
)

alter table [dbo].[SA_ONLINEINFO] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY] 
	
create table [dbo].[sa_worklog]
(
  SID          [nvarchar](32) not null,
  SLOCK      [nvarchar](100),
  SNAME    [nvarchar](100),
  SCUSTOMERNAME     [nvarchar](100),
  SIMPORTANCENAME   [nvarchar](100),
  SPLANNAME     [nvarchar](100),
  SPROJECTNAME   [nvarchar](100),
  SEMERGENCYNAME   [nvarchar](100),
  SLIMITTIME   datetime,
  SCONTENT   text,
  SCREATORFID   [nvarchar](1024),
  SCREATOFNAME   [nvarchar](1024),
  SCREATETIME   datetime,
  VERSION      int,
  FEXTEND01 [nvarchar](100)
)

alter table [dbo].[sa_worklog] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY]
	
create table [dbo].[MAS_MSGSENDPERM]
(
  SID          [nvarchar](32) not null,
  SPERSONID    [nvarchar](100),
  SSENDTYPE    [nvarchar](100),
  SRECIVESTATE int default 1,
  SPERSONNAME  [nvarchar](100),
  SUPDATEDATE  datetime,
  SUPDATORID   [nvarchar](100),
  SUPDATORNAME [nvarchar](100),
  VERSION      int,
  SPHONENUMBER [nvarchar](100)
)

alter table [dbo].[MAS_MSGSENDPERM] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY]

create table [dbo].[MAS_SENDGROUP]
(
  SID          [nvarchar](32) not null,
  SBILLID      [nvarchar](32),
  SPERSONID    [nvarchar](100),
  SPERONNAME   [nvarchar](100),
  VERSION      int,
  SPHONENUMBER [nvarchar](100),
  SDEPTID      [nvarchar](100),
  SDEPTNAME    [nvarchar](100)
)

alter table [dbo].[MAS_SENDGROUP] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY]

create table [dbo].[MAS_SENDGROUPLIST]
(
  SID          [nvarchar](32) not null,
  SNAME        [nvarchar](100),
  SCREATORID   [nvarchar](100),
  SCREATORNAME [nvarchar](100),
  SCREATEDATE  datetime,
  VERSION      int
)

alter table [dbo].[MAS_SENDGROUPLIST] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY]

create table [dbo].[MAS_SENDMESSAGE]
(
  SID          [nvarchar](32) not null,
  SPHONENUMBER [nvarchar](100),
  SMESSAGETEXT [nvarchar](4000),
  SSTATE       int default 0,
  SSTATEBANE   [nvarchar](50) default '未发送',
  SSENDDATE    datetime,
  SCREATORID   [nvarchar](100),
  SCREATORNAME [nvarchar](100),
  SCREATEDATE  datetime,
  STYPE        [nvarchar](20),
  SSENDTYPE    [nvarchar](100),
  VERSION      int,
  SERROINFO    [nvarchar](4000),
  SCREATEOGNID [nvarchar](100) 
)

alter table [dbo].[MAS_SENDMESSAGE] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY]

create table [dbo].[MAS_WRITEMESSAGE]
(
  SID          [nvarchar](32) not null,
  SRANGEID     [nvarchar](100),
  SRANGENAME   [nvarchar](100),
  SPHONES      [nvarchar](4000),
  SPERSONIDS   [nvarchar](4000),
  SPERSONNAMES [nvarchar](4000),
  SMESSAGES    [nvarchar](4000),
  STYPE        [nvarchar](10),
  SSENDTYPE    [nvarchar](20),
  SCREATORID   [nvarchar](100),
  SCREATORNAME [nvarchar](100),
  SCREATEDATE  datetime,
  SSTATE       int,
  SCREATEOGNID [nvarchar](100), 
  VERSION      int
)

alter table [dbo].[MAS_WRITEMESSAGE] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY]
create index MAS_WRITEMESSAGE_SSTATE on MAS_WRITEMESSAGE (SSTATE)

create table [dbo].[mas_ognmsglimit]
(
  	sID [nvarchar](32) NOT NULL
    ,FOGNID [nvarchar](100)
    ,FOGNNAME [nvarchar](100)
    ,fSUNUM bigint
    ,fUSEDNUM bigint
    ,fIUPDATE datetime
    ,FCREATEOGNID [nvarchar](64)
    ,FCREATEOGNNAME [nvarchar](200)
    ,FCREATEPERSONID [nvarchar](64)
    ,FCREATEPERSONNAME [nvarchar](100)
    ,FCREATETIME datetime
    ,FSTATE int
    ,VERSION int
)

alter table [dbo].[mas_ognmsglimit] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY]
	
CREATE TABLE sa_opperson_deatail (
  SID varchar(36) NOT NULL,
  SPERSONID varchar(36) DEFAULT NULL,
  STYPE varchar(500) DEFAULT NULL,
  SSQUNS varchar(500) DEFAULT NULL,
  SCODE varchar(500) DEFAULT NULL,
  SNAME varchar(500) DEFAULT NULL,
  SREMARK varchar(500) DEFAULT NULL,
  SCLASS varchar(500) DEFAULT NULL,
  SUPDATEDATE datetime NULL DEFAULT NULL,
  SUPDATORID varchar(36) DEFAULT NULL,
  SUPDATORNAME varchar(255) DEFAULT NULL,
  VERSION int DEFAULT NULL,
  PRIMARY KEY (SID)
)

create table [dbo].[im_message]
(
  sID [nvarchar](32) NOT NULL,
  favatar [nvarchar](1000),
  fusername [nvarchar](100),
  fid [nvarchar](100),
  content text,
  tid [nvarchar](100),
  tavatar [nvarchar](1000),
  tname [nvarchar](100),
  tsign [nvarchar](500),
  stype [nvarchar](100),
  tusername [nvarchar](100),
  groupname [nvarchar](100),
  state int,
  stime datetime,
  VERSION int
)

alter table [dbo].[im_message] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY]
create index im_message_fid on im_message (fid)
create index im_message_tid on im_message (tid)
create index im_message_state on im_message (state)
create index im_message_stype on im_message (stype)

CREATE TABLE [dbo].[SA_MAILSET](
	[SID] [varchar](32) NOT NULL,
	[SMAIL] [varchar](100) NULL,
	[SACCOUNT] [varchar](100) NULL,
	[SNAME] [varchar](200) NULL,
	[STYPE] [varchar](20) NULL,
	[SSENDHOST] [varchar](100) NULL,
	[SSENDPOST] [varchar](10) NULL,
	[SISSSL] [varchar](10) NULL,
	[SRECHOST] [varchar](100) NULL,
	[SRECPORT] [varchar](20) NULL,
	[SRECSSL] [varchar](10) NULL,
	[SISPUB] [varchar](10) NULL,
	[FCREATEPSNFID] [varchar](2048) NULL,
	[FCREATEPSNID] [varchar](36) NULL,
	[FCREATEPSNNAME] [varchar](100) NULL,
	[FCREATEDEPTID] [varchar](36) NULL,
	[FCREATEDEPTNAME] [varchar](200) NULL,
	[FCREATEOGNID] [varchar](36) NULL,
	[FCREATEOGNNAME] [varchar](200) NULL,
	[FCREATEORGID] [varchar](36) NULL,
	[FCREATEORGNAME] [varchar](200) NULL,
	[FCREATETIME] [datetime] NULL,
	[VERSION] [int] NULL
)
alter table [dbo].[SA_MAILSET] ADD 
	PRIMARY KEY CLUSTERED (
		 [SID]
	) ON [PRIMARY]
	
GO

insert into SA_OPOrg values('ORG01','梌林先','TULIN',NULL,'/梌林先','/TULIN','/ORG01.ogn','ogn','/001',1,NULL,1,'','','','','',NULL,NULL,0)
insert into SA_OPOrg values('PSN01@ORG01','system','SYSTEM',NULL,'/梌林先/system','/TULIN/SYSTEM','/ORG01.ogn/PSN01@ORG01.psm','psm','/001/001',1,'ORG01',1,NULL,NULL,NULL,NULL,NULL,'PSN01',NULL,0)
     
insert into SA_OPPerson values('PSN01','system','SYSTEM',NULL,1,'SYSTEM','E10ADC3949BA59ABBE56E057F20F883E','ORG01',NULL,1,1,NULL,'男','1976-12-03 00:00:00','2006-06-06 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL)

insert into sa_oprole (SID, SNAME, SCODE, SCATALOG, SROLEKIND, SPARENTNAMES, SDESCRIPTION, SSEQUENCE, SVALIDSTATE, VERSION)
values ('325B39BAE3CB40AFB4FB6594F49AC75B', '系统管理员', null, '职员类型', 'fun', null, null, 0, 1, 0)

insert into sa_oprole (SID, SNAME, SCODE, SCATALOG, SROLEKIND, SPARENTNAMES, SDESCRIPTION, SSEQUENCE, SVALIDSTATE, VERSION)
values ('RL01', 'Administrator', 'Administrator', '系统管理员', 'fun', null, null, 0, 1, 0)

insert into SA_OPAuthorize values('AHR01','PSN01@ORG01','system','/ORG01.ogn/PSN01@ORG01.psm','/梌林先/system','RL01',NULL,'/ORG01.ogn/PSN01@ORG01.psm','/梌林先/system','2021-07-03 00:00:00',0)

insert into sa_opmanagetype (SID, SNAME, SCODE, SMANAGEORGKIND, SMANAGEORGKINDNAME, SLEADERNUMBER, SISSYSTEM, VERSION)
values ('C55B6142B0C00001373010807E411D15', '非流程任务处理', 'feiliuchengrenwuchuli', 'org dept pos group psn', '机构 部门 岗位 工作组 人员', null, null, 0)

insert into sa_opmanagetype (SID, SNAME, SCODE, SMANAGEORGKIND, SMANAGEORGKINDNAME, SLEADERNUMBER, SISSYSTEM, VERSION)
values ('systemManagement', '系统管理', 'systemManagement', 'org dept group pos', '机构 部门 工作组 岗位', 0, 1, 0)

insert into sa_oporgenum (SID, STYPE, SCODE, SNAME, VERSION)
values ('ONM01', 'org_kind', 'org', '机构', 0)

insert into sa_oporgenum (SID, STYPE, SCODE, SNAME, VERSION)
values ('ONM02', 'org_kind', 'dept', '部门', 0)

insert into sa_oporgenum (SID, STYPE, SCODE, SNAME, VERSION)
values ('ONM03', 'org_kind', 'group', '工作组', 0)

insert into sa_oporgenum (SID, STYPE, SCODE, SNAME, VERSION)
values ('ONM04', 'org_kind', 'pos', '岗位', 0)

insert into sa_oporgenum (SID, STYPE, SCODE, SNAME, VERSION)
values ('ONM05', 'safe_level', '0', '普通', 0)

insert into sa_oporgenum (SID, STYPE, SCODE, SNAME, VERSION)
values ('ONM06', 'safe_level', '1', '秘密', 0)

insert into sa_oporgenum (SID, STYPE, SCODE, SNAME, VERSION)
values ('ONM07', 'safe_level', '2', '绝密', 0)

insert into sa_docnamespace (SID, SDISPLAYNAME, SHOST, SPORT, SURL, VERSION, SFLAG, SACCESSMODE)
values ('defaultDocNameSpace', '知识中心', '127.0.0.1', 8080, 'http://127.0.0.1:8080/DocServer', 1, 1, '1')

insert into sa_docnode (SID, SPARENTID, SDOCNAME, SSEQUENCE, SSIZE, SKIND, SDOCPATH, SDOCDISPLAYPATH, SCREATORFID, SCREATORNAME, SCREATORDEPTNAME, SCREATETIME, SEDITORFID, SEDITORNAME, SEDITORDEPTNAME, SLASTWRITERFID, SLASTWRITERNAME, SLASTWRITERDEPTNAME, SLASTWRITETIME, SFILEID, SDESCRIPTION, SDOCLIVEVERSIONID, VERSION, SCLASSIFICATION, SKEYWORDS, SDOCSERIALNUMBER, SFINISHTIME, SNAMESPACE, SCACHENAME, SREVISIONCACHENAME, SFLAG, SCREATORID)
values ('root', null, '知识中心', null, null, 'dir', '/root', '/知识中心', '/ORG01.ogn/PSN01@ORG01.psm', 'system', null, getdate(), null, null, null, 'system', null, null, getdate(), null, null, 0, 1, null, null, null, null, 'defaultDocNameSpace', null, null, 1, 'PSN01')

insert into sa_flowfolder (SID, SPROCESSID, SPROCESSNAME, SCODE, SNAME, SPARENT, SIDPATH, SNAMEPATH, SCODEPATH, VERSION)
values ('root', null, null, 'root', '根目录', null, '/root', '/根目录', '/root', 0)

INSERT INTO sa_oppermission VALUES ('01B955F5829F44219FB2806797B45A3B', 'RL01', '/OA/doc/process/baseCode/docSecretLevel/docSecretLevelProcess', '/其它/基础设置/公文密级', 'docSecretLevelActivity', '', '', '', '0', '', null, '1', '0')
INSERT INTO sa_oppermission VALUES ('098A9455CDDD44299140A281D3DE875F', 'RL01', '/OA/doc/process/baseCode/docExigenceLevel/docExigenceLevelProcess', '/其它/基础设置/公文紧急程度', 'docExigenceLevelActivity', '', '', '', '0', '', null, '1', '0')
INSERT INTO sa_oppermission VALUES ('182217CC50E84AB2A6EA3085A18BA621', 'RL01', '/SA/doc/docSetting/docSettingProcess', '/系统管理/文档/文档配置', 'mainActivity', '', '', '', '0', '', null, '1', '0')
INSERT INTO sa_oppermission VALUES ('1B1AF2E6140A445796ABE0442CBB54DE', 'RL01', '/SA/doc/docPermission/docPermissionProcess', '/系统管理/文档/文档关联', 'mainActivity', '', '', '', '0', '', null, '1', '0')
INSERT INTO sa_oppermission VALUES ('1F4272327EEF446B8005AEB1C1A42E0B', 'RL01', '/SA/OPM/authorization/authorizationProcess', '/系统管理/组织权限/授权管理-分级', 'gradeActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('265569FC504443E1B134592E3B5FCC5C', 'RL01', '/SA/OPM/log/logProcess', '/系统管理/组织权限/组织机构日志', 'mainActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('3566316F52F84896ACE1EF9BF42018BE', 'RL01', '/SA/OPM/organization/organizationProcess', '/系统管理/组织权限/组织管理', 'mainActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('38A312285E834995AFCAC32E2D50265A', 'RL01', '/SA/doc/docCenter/docCenterProcess', '/系统管理/文档/文档中心', 'docCenter', '', '', '', '0', '', null, '1', '0')
INSERT INTO sa_oppermission VALUES ('3B051D390A7B4426B2663AE27F3A45A8', 'RL01', '/OA/docrs/process/baseCode/docRedHead/docRedHeadProcess', '/其它/红头维护', 'mainActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('5F62DE289C8648689D20D9370ACAE21C', 'RL01', '/SA/OPM/role/roleProcess', '/系统管理/组织权限/角色管理', 'mainActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('660E9AF723874169A20E80C50B6D6C04', 'RL01', '/OA/docrs/process/docArchive/docArchiveProcess', '/其它/公文归档查询', 'mainActivity', '', '', '', '0', '', null, '1', '2')
INSERT INTO sa_oppermission VALUES ('6A02EEF862114421A0EC8BD3A4BD4222', 'RL01', '/SA/OPM/authorization/authorizationProcess', '/系统管理/组织权限/授权管理', 'mainActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('6F21B10EAECC46BE95A7A9349D0B041F', 'RL01', '/SA/OPM/grade/gradeProcess', '/系统管理/组织权限/分级管理', 'mainActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('7A17C36CD1EF4DC1A8C42B805A716556', 'RL01', '/OA/softwareDownLoad/process/software/softwareProcess', '/文件共享/目录维护', 'staticActivity1', '', '', '', '0', '', null, '1', '0')
INSERT INTO sa_oppermission VALUES ('82E555A346274CE092AE25B4DD0BFBE8', 'RL01', '/SA/log/logProcess', '/系统管理/系统工具/操作日志', 'mainActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('84EBC08BB9D64F4092267873339FBEE2', 'RL01', '/SA/OPM/repairTools/repairToolsProcess', '/系统管理/组织权限/修复工具', 'mainActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('851787396B3E4D3092B05A8B113131DD', 'RL01', '/OA/softwareDownLoad/process/software/softwareProcess', '/文件共享/目录维护', 'mainActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('9D8D492C5D49460E9DDA5F6CED4B1545', 'RL01', '/SA/update/updateProcess', '/系统管理/系统工具/软件更新', 'mainActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('9DC6B147AC1F478E9DFE5199F4EC8BCF', 'RL01', '/SA/online/onlineProcess', '/系统管理/系统工具/在线用户分级', 'gradeOnlineUserActivity', '', '', '', '0', '', null, '1', '6')
INSERT INTO sa_oppermission VALUES ('A84780D2CEF64C5B9DE9947ECD13ED28', 'RL01', '/SA/doc/docSearch/docSearchProcess', '/系统管理/文档/文档检索', 'mainActivity', '', '', '', '0', '', null, '1', '0')
INSERT INTO sa_oppermission VALUES ('AC305CCADA8D4947849F0E4F539077B0', 'RL01', '/OA/docrs/process/setReceiveDocTemplate/setReceiveDocTemplateProcess', '/其它/收发文基础设置/设置收发文模版', 'mainActivity', '', '', '', '0', '', null, '1', '2')
INSERT INTO sa_oppermission VALUES ('AD0706B27A22492BA4E979D5DF5CD2AA', 'RL01', '/OA/docrs/process/baseCode/docType/docTypePrecess', '/其它/收发文基础设置/设置公文类别', 'mainActivity', '', '', '', '0', '', null, '1', '2')
INSERT INTO sa_oppermission VALUES ('BCBEB3FEA6DB4283AD2494254FDBCDAE', 'RL01', '/SA/online/onlineProcess', '/系统管理/系统工具/在线用户', 'mainActivity', '', '', '', '0', '', null, '1', '3')
INSERT INTO sa_oppermission VALUES ('C6914BAA2E84424C901DBF8FD95144D7', 'RL01', '/SA/OPM/management/managementProcess', '/系统管理/组织权限/业务管理权限', 'mainActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('C6B3C40F53200001F12719F019995DA0', 'RL01', '/SA/OPM/recycled/recycledProcess', '/系统管理/组织机构/回收站', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C72FB49C224000015E2927B019E010D9', 'RL01', '/OA/Report/reportImgShowProcess', '/统计报表/时间维度统计图表展示', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C72FF32DC44000015C101BD21A0010B4', 'RL01', '/OA/Report/UserCountByCounty/usercountbycountyProcess', '/统计报表/各县系统用户统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C73002B742B000017654CC60934013C6', 'RL01', '/OA/Report/personusedayReport/mainActivityProcess', '/统计报表/用户使用系统时间统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7304519F47000017F591F2B1FD6EB50', 'RL01', '/OA/Report/OnlineInfoCountOnWeek/mainActivityProcess', '/统计报表/按在线用户量统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7304EB047300001DED41F75D51012B9', 'RL01', '/OA/Report/ConcurrentOnDay/mainActivityProcess', '/统计报表/天内并发量统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7305130B87000018EA718841EB06700', 'RL01', '/OA/Report/UserIncrement/userincrementProcess', '/统计报表/用户增长量统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7305169AE000001C7741ACC284019D3', 'RL01', '/OA/log/LoginLogProcess', '/系统管理/系统工具/用户登录统计', 'LoginLog', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7305169AED0000167D26C507440DE00', 'RL01', '/SA/log/mobileAppLogProcess', '/系统管理/系统工具/手机用户统计', 'mobileAppLog', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7305169AF800001EA2316506D70FAF0', 'RL01', '/SA/log/VPNLogProcess', '/系统管理/系统工具/VPN用户统计', 'VPNLog', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7305169B1900001B9731A801F5D9020', 'RL01', '/SA/MobileNumber/MobileNumbersProcess', '/系统管理/系统工具/移动号码段维护', 'MobileNumbers', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C73052FED5200001504DBB4012607480', 'RL01', '/OA/Report/RecvFileCountOnWeek/mainActivityProcess', '/统计报表/周来文登记数统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C73053D2C5B000017ACC110014DC1EDD', 'RL01', '/OA/Report/SendFileCountOnWeek/mainActivityProcess', '/统计报表/周发文数统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C73054DDF8700001A473530D17AF1669', 'RL01', '/OA/Report/RecvFileCountByMonth/mainActivityProcess', '/统计报表/按月收文登记数统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C730560A70B00001914298E4E92056A0', 'RL01', '/OA/Report/SendFileCountByMonth/mainActivityProcess', '/统计报表/按月发文数统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C73058D190100001504B2A1B59C01545', 'RL01', '/OA/Report/RecvFileCountByMonth/reportAllActivityProcess', '/统计报表/收文登记数统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C73059667980000122D8187815D01037', 'RL01', '/OA/Report/SendFileCountByMonth/reportAllActivityProcess', '/统计报表/发文数统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C730989BF62000012D6E164C164082F0', 'RL01', '/OA/Report/RecvFileCountByMonth/reportProcess', '/统计报表/收文数据对比', 'reportLineActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C730989BF7400001AC5C1EF079D02590', 'RL01', '/OA/Report/SendFileCountByMonth/reportProcess', '/统计报表/发文数据对比', 'reportLineActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C731F08F25A00001934520F0BE70F700', 'RL01', '/OA/ShortMessage/process/SendMsgFromSystem/sendMsgFromSystemProcess', '/发送短信/从系统通讯录发短信', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C731F08F2A80000183FC1B434C5818AA', 'RL01', '/OA/ShortMessage/process/SendMsgQuery/sendMsgQueryProcess', '/发送短信/已发短信查询', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7362262EE800001931710941E606820', 'RL01', '/OA/personal/process/personalInfo/personalProcess', '/其它/个人痕迹查询', 'markActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C79092E0D2C00001FFD6E8A61810C900', 'RL01', '/OA/hr/wage/ListActivityPersonProcess', '/其它/个人薪资查询', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7A4DAEE8A500001ADED569E1537A090', 'RL01', '/OA/docrs/process/sendDoc/AllSendedProcess', '/其它/已发文件', 'AllSendedActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7A5BEC63EE0000123FA2804F3571E8B', 'RL01', '/OA/CheckWorkAtten/baseProcess', '/工作管理/考勤管理/上班时间设置', 'workDateActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7B2671830000001A63111A019609FA0', 'RL01', '/OA/Project/Manage/mainProcess', '/州级项目审批情况/项目审批情况统计', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7B2F1D86D00000168DBE4C02B1013E2', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况登记(州内)', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7B2F53FD6A0000169FDDF90149019F0', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况查看(州内)', 'lookActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7B2F53FD8C00001BC5C1F901078EE00', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况登记(州外)', 'mainActivity1', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7B2F53FDB3000019EF0141B73404B10', 'RL01', '/OA/OfficeOperation/registerProcess', '/(办件)运行情况/办件运行情况查看(州外)', 'lookActivity1', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C7D8FEE6EFE00001B6751A1E12303070', 'RL01', '/OA/fawenDanwei/mainProcess', '/系统管理/系统工具/允许发文单位', 'mainActivity', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C82E3F2608B00001E83E18231E2A18BA', 'RL01', '/SA/theme/bgProcess', '/系统管理/系统工具/登录页背景配置', 'mainActivity', '', '', '', null, '', null, null, '1')
INSERT INTO sa_oppermission VALUES ('C8336D112D0000013CE01830EDF01F56', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/填写工作日志', 'myWorkLog', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C8336D112E7000016B60624914781E9A', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/我的工作日志', 'MyWorklogList', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C8336D112FE00001DDAFA0612340187E', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/工作日志审批', 'waitWorkLog', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C8336D1131300001CB7E1FF017531313', 'RL01', '/OA/workLog/WorkLogProcess', '/工作台账管理/工作日志/工作日志汇总', 'allWorklogList', '', '', '', null, '', null, null, '0')
INSERT INTO sa_oppermission VALUES ('C8373BC0E94000011694194017001AE0', 'RL01', '/OA/CheckWorkAtten/personnelProcess', '/考勤管理/个人考勤', 'personnelActivity', '', '', '', null, '', null, null, '1')
INSERT INTO sa_oppermission VALUES ('C8373BC0EB8000013B42146CBC7010F0', 'RL01', '/OA/CheckWorkAtten/personnelProcess', '/考勤管理/考勤记录', 'DayChekinList', '', '', '', null, '', null, null, '1')
INSERT INTO sa_oppermission VALUES ('C8373BC0EE1000011C8215C05B15EFB0', 'RL01', '/OA/CheckWorkAtten/personnelProcess', '/考勤管理/考勤统计', 'reportActivity', '', '', '', null, '', null, null, '1')
INSERT INTO sa_oppermission VALUES ('C8A5A0B5BED00001ADC31CC0968311E8', 'RL01', 'SA/services/pcProcess', '/系统管理/系统工具/服务器监控', 'mainActivity', '', '', '', null, '', null, null, '1')
INSERT INTO sa_oppermission VALUES ('C8D3540ADE800001392115941C10100B', 'RL01', '/OA/docrs/process/sendDoc/sendDocProcess', '/发文管理/删除发文', 'SendDel', '', '', '', null, '', null, null, '1')
INSERT INTO sa_oppermission VALUES ('C8DC2295A8E00001B8DE85203AD016AE', 'RL01', '/SA/task/taskCenter/process', '/任务中心/任务列表', 'mainActivity', '', '', '', null, '', null, null, '1')
INSERT INTO sa_oppermission VALUES ('C8DC2295A9600001BBE919691C301C37', 'RL01', '/SA/process/monitor/process', '/任务中心/流程监控', 'mainActivity', '', '', '', null, '', null, null, '1')
INSERT INTO sa_oppermission VALUES ('C8DC2295A9E00001D89590F5198012BC', 'RL01', '/SA/task/unFlowmana/process', '/任务中心/任务处理', 'mainActivity', '', '', '', null, '', null, null, '1')
INSERT INTO sa_oppermission VALUES ('C8DC2295AA6000017BF83A6965004D50', 'RL01', '/flw/dwr/process', '/任务中心/流程设计', 'vml-dwr-editer', '', '', '', null, '', null, null, '1')
INSERT INTO sa_oppermission VALUES ('C94E5AE783D000017BE612A0165419DF', 'RL01', '029AB9CC9C5D4378B25C9BCB6C32B33D', '/采购管理/采购登记', 'bizActivity2', null, null, null, null, null, null, null, '1')
INSERT INTO sa_oppermission VALUES ('C94E5AE784A0000163C271C810651321', 'RL01', '029AB9CC9C5D4378B25C9BCB6C32B33D', '/采购管理/采购审批', 'bizActivity4', null, null, null, null, null, null, null, '1')
INSERT INTO sa_oppermission VALUES ('C94EE9000440000135A1A7F050121CA8', 'RL01', '/SA/task/taskCenter/process', '/任务中心/流程监控', 'monitorActivity', null, null, null, null, null, null, null, '1')
INSERT INTO sa_oppermission VALUES ('C94EE9000540000138D11EF078DC6810', 'RL01', '/SA/task/taskCenter/process', '/任务中心/任务处理', 'unFlowmanaActivity', null, null, null, null, null, null, null, '1')
INSERT INTO sa_oppermission VALUES ('C94EE90006D000013BDE12401F401700', 'RL01', '/SA/task/taskCenter/process', '/任务中心/系统提醒', 'reminActivity', null, null, null, null, null, null, null, '1')
INSERT INTO sa_oppermission VALUES ('C94F4E969D600001886F19D05E501F55', 'RL01', '/SA/OPM/recycled/recycledProcess', '/系统管理/组织机构/回收站-分级', 'gradeRecycledActivity', null, null, null, null, null, null, null, '1')
INSERT INTO sa_oppermission VALUES ('D0EC487F72FF4D768C92520A8B90F458', 'RL01', '/OA/docrs/process/baseCode/docArchiveTree/docArchiveTreePrecess', '/其它/收发文基础设置/设置公文归档', 'mainActivity', '', '', '', '0', '', null, '1', '2')
INSERT INTO sa_oppermission VALUES ('E6D1673C76174CCEA9919C0C16453C98', 'RL01', '/SA/OPM/management/managementProcess', '/系统管理/组织权限/业务管理权限-分级', 'gradeActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('EE9E19F50356498C96BB193356F8AF38', 'RL01', '/SA/OPM/organization/organizationProcess', '/系统管理/组织权限/组织管理-分级', 'gradeActivity', '', '', '', '0', '', null, '1', '1')
INSERT INTO sa_oppermission VALUES ('EF8F1CC8AB6F4728A7D083B6DD1790C1', 'RL01', '/OA/docrs/process/setReceiveDocTemplate/setReceiveDocTemplateProcess', '/其它/收发文基础设置/设置映射关系', 'SetORMstaticActivity', '', '', '', '0', '', null, '1', '0')
INSERT INTO sa_oppermission VALUES ('F2AF3284E2D6405E9990376C19C57D45', 'RL01', '/SA/OPM/agent/agentProcess', '/系统管理/组织权限/代理设置', 'mainActivity', '', '', '', '0', '', null, '1', '1')


EXECUTE sp_addextendedproperty N'MS_Description', N'主键', 'USER', N'dbo', 'TABLE', N'SA_SYSTEMMAINTAIN', 'COLUMN', N'SID'
EXECUTE sp_addextendedproperty N'MS_Description', N'编号', 'USER', N'dbo', 'TABLE', N'SA_SYSTEMMAINTAIN', 'COLUMN', N'SCODE'
EXECUTE sp_addextendedproperty N'MS_Description', N'名称', 'USER', N'dbo', 'TABLE', N'SA_SYSTEMMAINTAIN', 'COLUMN', N'SNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'内容', 'USER', N'dbo', 'TABLE', N'SA_SYSTEMMAINTAIN', 'COLUMN', N'STEXT'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人ID', 'USER', N'dbo', 'TABLE', N'SA_SYSTEMMAINTAIN', 'COLUMN', N'SCREATORID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人', 'USER', N'dbo', 'TABLE', N'SA_SYSTEMMAINTAIN', 'COLUMN', N'SCREATORNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建时间', 'USER', N'dbo', 'TABLE', N'SA_SYSTEMMAINTAIN', 'COLUMN', N'SCREATETIME'
EXECUTE sp_addextendedproperty N'MS_Description', N'版本号', 'USER', N'dbo', 'TABLE', N'SA_SYSTEMMAINTAIN', 'COLUMN', N'VERSION'

EXECUTE sp_addextendedproperty N'MS_Description', N'主键', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SID'
EXECUTE sp_addextendedproperty N'MS_Description', N'姓名', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'编码', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SCODE'
EXECUTE sp_addextendedproperty N'MS_Description', N'年龄', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SNUMB'
EXECUTE sp_addextendedproperty N'MS_Description', N'登陆名', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SLOGINNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'密码', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SPASSWORD'
EXECUTE sp_addextendedproperty N'MS_Description', N'机构ID', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SMAINORGID'
EXECUTE sp_addextendedproperty N'MS_Description', N'排序', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SSEQUENCE'
EXECUTE sp_addextendedproperty N'MS_Description', N'启用状态', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SVALIDSTATE'
EXECUTE sp_addextendedproperty N'MS_Description', N'描述', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SDESCRIPTION'
EXECUTE sp_addextendedproperty N'MS_Description', N'性别', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SSEX'
EXECUTE sp_addextendedproperty N'MS_Description', N'生日', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SBIRTHDAY'
EXECUTE sp_addextendedproperty N'MS_Description', N'入职时间', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SJOINDATE'
EXECUTE sp_addextendedproperty N'MS_Description', N'住址', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SHOMEPLACE'
EXECUTE sp_addextendedproperty N'MS_Description', N'学历', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SDEGREE'
EXECUTE sp_addextendedproperty N'MS_Description', N'毕业学校', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SGRADUATESCHOOL'
EXECUTE sp_addextendedproperty N'MS_Description', N'专业', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SSPECIALITY'
EXECUTE sp_addextendedproperty N'MS_Description', N'学制', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SSCHOOLLENGTH'
EXECUTE sp_addextendedproperty N'MS_Description', N'头衔', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'STITLE'
EXECUTE sp_addextendedproperty N'MS_Description', N'婚姻概况', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SMARRIAGE'
EXECUTE sp_addextendedproperty N'MS_Description', N'证件号', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SCARDNO'
EXECUTE sp_addextendedproperty N'MS_Description', N'证件类型', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SCARDKIND'
EXECUTE sp_addextendedproperty N'MS_Description', N'家庭住址', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SFAMILYADDRESS'
EXECUTE sp_addextendedproperty N'MS_Description', N'邮编', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SZIP'
EXECUTE sp_addextendedproperty N'MS_Description', N'MSN', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SMSN'
EXECUTE sp_addextendedproperty N'MS_Description', N'QQ号', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SQQ'
EXECUTE sp_addextendedproperty N'MS_Description', N'邮箱', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SMAIL'
EXECUTE sp_addextendedproperty N'MS_Description', N'手机', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SMOBILEPHONE'
EXECUTE sp_addextendedproperty N'MS_Description', N'家庭电话', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SFAMILYPHONE'
EXECUTE sp_addextendedproperty N'MS_Description', N'办公电话', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SOFFICEPHONE'
EXECUTE sp_addextendedproperty N'MS_Description', N'照片', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SPHOTO'
EXECUTE sp_addextendedproperty N'MS_Description', N'省', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SPROVINCE'
EXECUTE sp_addextendedproperty N'MS_Description', N'国家', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SCOUNTRY'
EXECUTE sp_addextendedproperty N'MS_Description', N'市', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SCITY'
EXECUTE sp_addextendedproperty N'MS_Description', N'详细地址', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SPOSITIONS'
EXECUTE sp_addextendedproperty N'MS_Description', N'身份证', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SIDCARD'
EXECUTE sp_addextendedproperty N'MS_Description', N'英文名', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SENGLISHNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'传真', 'USER', N'dbo', 'TABLE', N'SA_OPPERSON', 'COLUMN', N'SFAX'

EXECUTE sp_addextendedproperty N'MS_Description', N'类别', 'USER', N'dbo', 'TABLE', N'SA_LOG', 'COLUMN', N'STYPENAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'描述', 'USER', N'dbo', 'TABLE', N'SA_LOG', 'COLUMN', N'SDESCRIPTION'
EXECUTE sp_addextendedproperty N'MS_Description', N'操作时间', 'USER', N'dbo', 'TABLE', N'SA_LOG', 'COLUMN', N'SCREATETIME'
EXECUTE sp_addextendedproperty N'MS_Description', N'操作者', 'USER', N'dbo', 'TABLE', N'SA_LOG', 'COLUMN', N'SCREATORPERSONNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'功能模块', 'USER', N'dbo', 'TABLE', N'SA_LOG', 'COLUMN', N'SPROCESSNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'功能名称', 'USER', N'dbo', 'TABLE', N'SA_LOG', 'COLUMN', N'SACTIVITYNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'操作', 'USER', N'dbo', 'TABLE', N'SA_LOG', 'COLUMN', N'SACTIONNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'IP地址', 'USER', N'dbo', 'TABLE', N'SA_LOG', 'COLUMN', N'SIP'

EXECUTE sp_addextendedproperty N'MS_Description', N'主键', 'USER', N'dbo', 'TABLE', N'SA_HALTCAUTION', 'COLUMN', N'SID'
EXECUTE sp_addextendedproperty N'MS_Description', N'标题', 'USER', N'dbo', 'TABLE', N'SA_HALTCAUTION', 'COLUMN', N'STITLE'
EXECUTE sp_addextendedproperty N'MS_Description', N'开始时间', 'USER', N'dbo', 'TABLE', N'SA_HALTCAUTION', 'COLUMN', N'SSTARTTIME'
EXECUTE sp_addextendedproperty N'MS_Description', N'结束时间', 'USER', N'dbo', 'TABLE', N'SA_HALTCAUTION', 'COLUMN', N'SENDTIME'
EXECUTE sp_addextendedproperty N'MS_Description', N'提醒倒计时（分钟）', 'USER', N'dbo', 'TABLE', N'SA_HALTCAUTION', 'COLUMN', N'SATTENTION'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人id', 'USER', N'dbo', 'TABLE', N'SA_HALTCAUTION', 'COLUMN', N'SCREATERID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人名称', 'USER', N'dbo', 'TABLE', N'SA_HALTCAUTION', 'COLUMN', N'SCREATERNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建时间', 'USER', N'dbo', 'TABLE', N'SA_HALTCAUTION', 'COLUMN', N'SCREATETIME'
EXECUTE sp_addextendedproperty N'MS_Description', N'是否已经发送', 'USER', N'dbo', 'TABLE', N'SA_HALTCAUTION', 'COLUMN', N'SISPUSHED'
EXECUTE sp_addextendedproperty N'MS_Description', N'版本号', 'USER', N'dbo', 'TABLE', N'SA_HALTCAUTION', 'COLUMN', N'VERSION'

EXECUTE sp_addextendedproperty N'MS_Description', N'主键', 'USER', N'dbo', 'TABLE', N'SA_FLOWTRACE', 'COLUMN', N'SID'
EXECUTE sp_addextendedproperty N'MS_Description', N'操作人ID', 'USER', N'dbo', 'TABLE', N'SA_FLOWTRACE', 'COLUMN', N'SOPERATORID'
EXECUTE sp_addextendedproperty N'MS_Description', N'操作人code', 'USER', N'dbo', 'TABLE', N'SA_FLOWTRACE', 'COLUMN', N'SOPERATORCODE'
EXECUTE sp_addextendedproperty N'MS_Description', N'操作人name', 'USER', N'dbo', 'TABLE', N'SA_FLOWTRACE', 'COLUMN', N'SOPERATORNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'提交页面', 'USER', N'dbo', 'TABLE', N'SA_FLOWTRACE', 'COLUMN', N'SCURL'
EXECUTE sp_addextendedproperty N'MS_Description', N'执行页面', 'USER', N'dbo', 'TABLE', N'SA_FLOWTRACE', 'COLUMN', N'SEURL'
EXECUTE sp_addextendedproperty N'MS_Description', N'选择的人员信息', 'USER', N'dbo', 'TABLE', N'SA_FLOWTRACE', 'COLUMN', N'SCHECKPSN'
EXECUTE sp_addextendedproperty N'MS_Description', N'版本号', 'USER', N'dbo', 'TABLE', N'SA_FLOWTRACE', 'COLUMN', N'VERSION'

EXECUTE sp_addextendedproperty N'MS_Description', N'主键', 'USER', N'dbo', 'TABLE', N'SA_FLOWFOLDER', 'COLUMN', N'SID'
EXECUTE sp_addextendedproperty N'MS_Description', N'流程标识', 'USER', N'dbo', 'TABLE', N'SA_FLOWFOLDER', 'COLUMN', N'SPROCESSID'
EXECUTE sp_addextendedproperty N'MS_Description', N'流程名称', 'USER', N'dbo', 'TABLE', N'SA_FLOWFOLDER', 'COLUMN', N'SPROCESSNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'目录编码', 'USER', N'dbo', 'TABLE', N'SA_FLOWFOLDER', 'COLUMN', N'SCODE'
EXECUTE sp_addextendedproperty N'MS_Description', N'目录名称', 'USER', N'dbo', 'TABLE', N'SA_FLOWFOLDER', 'COLUMN', N'SNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'父节点', 'USER', N'dbo', 'TABLE', N'SA_FLOWFOLDER', 'COLUMN', N'SPARENT'
EXECUTE sp_addextendedproperty N'MS_Description', N'目录ID全路径', 'USER', N'dbo', 'TABLE', N'SA_FLOWFOLDER', 'COLUMN', N'SIDPATH'
EXECUTE sp_addextendedproperty N'MS_Description', N'目录名全路径', 'USER', N'dbo', 'TABLE', N'SA_FLOWFOLDER', 'COLUMN', N'SNAMEPATH'
EXECUTE sp_addextendedproperty N'MS_Description', N'目录编码全路径', 'USER', N'dbo', 'TABLE', N'SA_FLOWFOLDER', 'COLUMN', N'SCODEPATH'
EXECUTE sp_addextendedproperty N'MS_Description', N'版本号', 'USER', N'dbo', 'TABLE', N'SA_FLOWFOLDER', 'COLUMN', N'VERSION'

EXECUTE sp_addextendedproperty N'MS_Description', N'主键', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FID'
EXECUTE sp_addextendedproperty N'MS_Description', N'版本号', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'VERSION'
EXECUTE sp_addextendedproperty N'MS_Description', N'标题', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FTITLE'
EXECUTE sp_addextendedproperty N'MS_Description', N'文号', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FSYMBOL'
EXECUTE sp_addextendedproperty N'MS_Description', N'重要度', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FIMPORTANCEDEGREE'
EXECUTE sp_addextendedproperty N'MS_Description', N'内容', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FCONTENT'
EXECUTE sp_addextendedproperty N'MS_Description', N'缩略图', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FTHUMBNAIL'
EXECUTE sp_addextendedproperty N'MS_Description', N'发布标识', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FPUBLISHEDLOGO'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人ID', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FCREATORID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人(作者)', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FCREATOR'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建时间', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FCREATEDATE'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人部门', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FOPENSCOPEDEPT'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人部门ID', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FOPENSCOPEDEPTID'
EXECUTE sp_addextendedproperty N'MS_Description', N'流程状态(已开始：流转中：已完成：已终止)', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FISFINISHNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'流程标识(已开始：0；流转中：1；已完成：2；已终止：3)', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FISFINISH'
EXECUTE sp_addextendedproperty N'MS_Description', N'修改人ID', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FUPDATORID'
EXECUTE sp_addextendedproperty N'MS_Description', N'修改人', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FUPDATOR'
EXECUTE sp_addextendedproperty N'MS_Description', N'修改时间', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FUPDATETIME'
EXECUTE sp_addextendedproperty N'MS_Description', N'附件', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FACCESSORIES'
EXECUTE sp_addextendedproperty N'MS_Description', N'备注', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FREMARK'
EXECUTE sp_addextendedproperty N'MS_Description', N'发布范围', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FOPENSCOPE'
EXECUTE sp_addextendedproperty N'MS_Description', N'发布范围ID', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FOPENSCOPEID'
EXECUTE sp_addextendedproperty N'MS_Description', N'单据编号', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FBILLCODE'
EXECUTE sp_addextendedproperty N'MS_Description', N'发布人', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FPEOPLE'
EXECUTE sp_addextendedproperty N'MS_Description', N'发布人ID', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FPEOPLEID'
EXECUTE sp_addextendedproperty N'MS_Description', N'发布时间', 'USER', N'dbo', 'TABLE', N'OA_ISSUANCE', 'COLUMN', N'FPEOPLEDATE'

EXECUTE sp_addextendedproperty N'MS_Description', N'主键', 'USER', N'dbo', 'TABLE', N'SA_FLOWDRAWLG', 'COLUMN', N'SID'
EXECUTE sp_addextendedproperty N'MS_Description', N'流程标识', 'USER', N'dbo', 'TABLE', N'SA_FLOWDRAWLG', 'COLUMN', N'SPROCESSID'
EXECUTE sp_addextendedproperty N'MS_Description', N'流程名称', 'USER', N'dbo', 'TABLE', N'SA_FLOWDRAWLG', 'COLUMN', N'SPROCESSNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'流程图', 'USER', N'dbo', 'TABLE', N'SA_FLOWDRAWLG', 'COLUMN', N'SDRAWLG'
EXECUTE sp_addextendedproperty N'MS_Description', N'版本号', 'USER', N'dbo', 'TABLE', N'SA_FLOWDRAWLG', 'COLUMN', N'VERSION'
EXECUTE sp_addextendedproperty N'MS_Description', N'流程环节', 'USER', N'dbo', 'TABLE', N'SA_FLOWDRAWLG', 'COLUMN', N'SPROCESSACTY'
EXECUTE sp_addextendedproperty N'MS_Description', N'生效状态', 'USER', N'dbo', 'TABLE', N'SA_FLOWDRAWLG', 'COLUMN', N'FENABLED'
EXECUTE sp_addextendedproperty N'MS_Description', N'目录id', 'USER', N'dbo', 'TABLE', N'SA_FLOWDRAWLG', 'COLUMN', N'SFOLDERID'

EXECUTE sp_addextendedproperty N'MS_Description', N'开始时间', 'USER', N'dbo', 'TABLE', N'DEMO_FLOW_TEST', 'COLUMN', N'FSTARTTIME'
EXECUTE sp_addextendedproperty N'MS_Description', N'结束时间', 'USER', N'dbo', 'TABLE', N'DEMO_FLOW_TEST', 'COLUMN', N'FENDTIME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人', 'USER', N'dbo', 'TABLE', N'DEMO_FLOW_TEST', 'COLUMN', N'FCREATOR'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建时间', 'USER', N'dbo', 'TABLE', N'DEMO_FLOW_TEST', 'COLUMN', N'FCREATETIME'
EXECUTE sp_addextendedproperty N'MS_Description', N'原因', 'USER', N'dbo', 'TABLE', N'DEMO_FLOW_TEST', 'COLUMN', N'FRESOURCE'

EXECUTE sp_addextendedproperty N'MS_Description', N'首页布局信息', 'USER', N'dbo', 'TABLE', N'SA_PORTALLETS', null, null
EXECUTE sp_addextendedproperty N'MS_Description', N'人员ID', 'USER', N'dbo', 'TABLE', N'SA_PORTALLETS', 'COLUMN', N'SPERSONID'
EXECUTE sp_addextendedproperty N'MS_Description', N'布局', 'USER', N'dbo', 'TABLE', N'SA_PORTALLETS', 'COLUMN', N'SLAYOUTSET'
EXECUTE sp_addextendedproperty N'MS_Description', N'信息框', 'USER', N'dbo', 'TABLE', N'SA_PORTALLETS', 'COLUMN', N'SPANLES'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人ID', 'USER', N'dbo', 'TABLE', N'SA_PORTALLETS', 'COLUMN', N'SCREATORID'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人', 'USER', N'dbo', 'TABLE', N'SA_PORTALLETS', 'COLUMN', N'SCREATORNAME'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建时间', 'USER', N'dbo', 'TABLE', N'SA_PORTALLETS', 'COLUMN', N'SCREATEDATE'

EXECUTE sp_addextendedproperty N'MS_Description', N'0-未发送1-已发送', 'USER', N'dbo', 'TABLE', N'APP_PUSHMESSAGE', 'COLUMN', N'SSENDSTATE'
EXECUTE sp_addextendedproperty N'MS_Description', N'0-未接收1-已接收', 'USER', N'dbo', 'TABLE', N'APP_PUSHMESSAGE', 'COLUMN', N'SRESTATE'
EXECUTE sp_addextendedproperty N'MS_Description', N'标题', 'USER', N'dbo', 'TABLE', N'APP_PUSHMESSAGE', 'COLUMN', N'STITLE'
EXECUTE sp_addextendedproperty N'MS_Description', N'发送时间', 'USER', N'dbo', 'TABLE', N'APP_PUSHMESSAGE', 'COLUMN', N'SSDATE'
EXECUTE sp_addextendedproperty N'MS_Description', N'接收时间', 'USER', N'dbo', 'TABLE', N'APP_PUSHMESSAGE', 'COLUMN', N'SVDATE'

EXECUTE sp_addextendedproperty N'MS_Description', N'邮箱地址' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'smail'
EXECUTE sp_addextendedproperty N'MS_Description', N'密码' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'saccount'
EXECUTE sp_addextendedproperty N'MS_Description', N'发信名称' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'sname'
EXECUTE sp_addextendedproperty N'MS_Description', N'邮箱类型' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'stype'
EXECUTE sp_addextendedproperty N'MS_Description', N'发件服务器' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'ssendhost'
EXECUTE sp_addextendedproperty N'MS_Description', N'发件端口' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'ssendpost'
EXECUTE sp_addextendedproperty N'MS_Description', N'发件是否启用SSL' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'sisssl'
EXECUTE sp_addextendedproperty N'MS_Description', N'收件服务器' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'srechost'
EXECUTE sp_addextendedproperty N'MS_Description', N'收件端口' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'srecport'
EXECUTE sp_addextendedproperty N'MS_Description', N'收件是否启用SSL' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'srecssl'
EXECUTE sp_addextendedproperty N'MS_Description', N'是否公用' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'sispub'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人FID' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'fcreatepsnfid'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人ID' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'fcreatepsnid'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建人名称' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'fcreatepsnname'
EXECUTE sp_addextendedproperty N'MS_Description', N'部门ID' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'fcreatedeptid'
EXECUTE sp_addextendedproperty N'MS_Description', N'部门名称' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'fcreatedeptname'
EXECUTE sp_addextendedproperty N'MS_Description', N'机构ID' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'fcreateognid'
EXECUTE sp_addextendedproperty N'MS_Description', N'机构名称' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'fcreateognname'
EXECUTE sp_addextendedproperty N'MS_Description', N'组织ID' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'fcreateorgid'
EXECUTE sp_addextendedproperty N'MS_Description', N'组织名称' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'fcreateorgname'
EXECUTE sp_addextendedproperty N'MS_Description', N'创建时间' , 'USER',N'dbo', 'TABLE',N'sa_mailset', 'COLUMN',N'fcreatetime'

GO
CREATE VIEW V_OA_SYS_BIZSTATE_EX AS
SELECT "FID","FCODE","FSCODE","FSCOPE","FSCOPENAME","FNAME","FSNAME","FDESCRIPTION","FPARENTID","FLEVEL","FURL","FSEQUENCE","FCREATEDEPTID","FCREATEPERID","FCREATETIME","FDISUSETIME","FUSESTATUS","FUPDATEDEPTID","FUPDATEPERID","FUPDATETIME","VERSION"
FROM PUB_SysCode where fScope = 'SYS_BizState' and (fName = '编辑中'or fName = '审批中' or fName = '已完成'or fName = '已终止')
GO
create view sa_task_view as
select t.SID,
            t.SPARENTID,
            t1.SNAME,
            t.SCONTENT,
            t.SREMARK,
            t.SFLOWID,
            t.STYPEID,
            t.STYPENAME,
            t.SIMPORTANCEID,
            t.SIMPORTANCENAME,
            t.SEMERGENCYID,
            t.SEMERGENCYNAME,
            t.SPROCESS,
            t.SACTIVITY,
            t1.SCREATETIME,
            t1.SDISTRIBUTETIME,
            t1.SLASTMODIFYTIME,
            t1.SWARNINGTIME,
            t1.SLIMITTIME,
            t1.SESTARTTIME,
            t1.SEFINISHTIME,
            t1.SASTARTTIME,
            t1.SAFINISHTIME,
            t1.SEXECUTETIME,
            t1.SEPERSONID SCPERSONID,
            t1.SEPERSONNAME SCPERSONNAME,
            t1.SEDEPTID SCDEPTID,
            t1.SEDEPTNAME SCDEPTNAME,
            t1.SEOGNID SCOGNID,
            t1.SEOGNNAME SCOGNNAME,
            t1.SEPERSONID,
            t1.SEPERSONNAME,
            t1.SEDEPTID,
            t1.SEDEPTNAME,
            t1.SEOGNID,
            t1.SEOGNNAME,
            t1.SCUSTOMERID,
            t1.SCUSTOMERNAME,
            t1.SPROJECTID,
            t1.SPROJECTNAME,
            t1.SPLANID,
            t1.SPLANNAME,
            t1.SVARIABLE,
            t1.SFAKE,
            t1.SACTIVE,
            t1.SLOCK,
            t1.SSTATUSID,
            t1.SSTATUSNAME,
            t.VERSION,
            t1.SAIID,
            t1.SCATALOGID,
            t.SKINDID,
            t1.SAIACTIVE,
            t1.SAISTATUSID,
            t1.SAISTATUSNAME,
            t1.SSOURCEID,
            t1.SCURL,
            t1.SEURL,
            t1.SEXECUTEMODE,
            t1.SEXECUTEMODE2,
            t1.SPREEMPTMODE,
            t1.SSEQUENCE,
            t1.SEPERSONCODE SCPERSONCODE,
            t1.SEPOSID SCPOSID,
            t1.SEPOSCODE SCPOSCODE,
            t1.SEPOSNAME SCPOSNAME,
            t1.SEDEPTCODE SCDEPTCODE,
            t1.SEOGNCODE SCOGNCODE,
            t1.SEFID SCFID,
            t1.SEPERSONCODE,
            t1.SEPOSID,
            t1.SEPOSCODE,
            t1.SEPOSNAME,
            t1.SEDEPTCODE,
            t1.SEOGNCODE,
            t1.SEFID,
            t1.SEXECUTORNAMES,
            t.SRESPONSIBLE,
            t.SCUSTOMERCODE,
            t.SPROJECTCODE,
            t.SPLANCODE,
            t.SDATA1,
            t.SDATA2,
            t.SDATA3,
            t.SDATA4,
            t1.SWORKTIME,
            t1.SCFNAME,
            t1.SEFNAME,
            t1.SHINTS,
            t1.SSHORTCUT,
            t1.SFRONTID,
            t1.SFMAKERNAME
       from sa_task t
       left join (select * from sa_task t2 where t2.sStatusID = 'tesReady' or t2.sstatusid = 'tesAborted') t1 on t.sid = t1.sflowid
      where t.skindid = 'tkProcessInstance' or t.sstatusid = 'tesExecuting'

GO
create view sa_oporg_view as
select sID,
       sParent,
       sCode,
       sName,
       isnull(sOrgKindID, 'org') sOrgKindID,
       sFID,
       sValidState,
       SFCODE,
       sFName,
       sSequence,
       SDESCRIPTION,
       SADDRESS,
       version
  from Sa_Oporg o
 where o.svalidstate = 1
union all
select p.sID,
       t.sid sParent,
       p.sCode,
       p.sName,
       'psm' sOrgKindID,
       t.sFID + '/' + p.sid,
       t.sValidState,
       t.sfcode + '/' + p.scode,
       t.sFName + '/' + p.sname,
       t.slevel + p.sSequence sSequence,
       t.SDESCRIPTION,
       t.SADDRESS,
       p.version
  from sa_opperson p
  left join sa_oporg t on p.smainorgid = t.sid
 where p.smainorgid is not null
   and t.svalidstate = 1
   and p.svalidstate = 1
union all
select p.sID,
       t.sid sParent,
       p.sCode,
       p.sName,
       'psm' sOrgKindID,
       t.sFID + '/' + p.sid,
       t.sValidState,
       t.sfcode + '/' + p.scode,
       t.sFName + '/' + p.sname,
       t.slevel + p.sSequence sSequence,
       t.SDESCRIPTION,
       t.SADDRESS,
       p.version
  from sa_opperson p, sa_oporg t
 where t.sid in
       (select m.sorgid from sa_oppersonmember m where m.spersonid = p.sid)
   and t.svalidstate = 1
   and p.svalidstate = 1

GO 
create view sa_agent_view as
select a.sid,
       p.scode,
       p.sname,
       ag.scode as sagcode,
       ag.sname as sagname,
       a.sstarttime,
       a.sfinishtime,
       0 version
  from sa_opagent a
  left join sa_opperson p on a.spersonid = p.sid
  left join sa_opperson ag on ag.sid = a.sagentid
 where a.sactive = 1 and isnull(a.sfinishtime,getdate()) >=getdate()
GO
create view news_tables as
select a.SID AS  SID ,
  a .VERSION AS VERSION,a.FNEWSTITLE AS FNEWSTITLE,a.FSTATE AS FSTATE,a.FPEOPLE AS FPEOPLE,
  a.FTIME AS FTIME,a.FOPENSCOPE AS FOPENSCOPE,b.NEWS_NUMBER AS NEWS_NUMBER,b.NEWS_PERSON AS NEWS_PERSON,
  b.YETPERSON AS YETPERSON,a.FOPENSCOPEID from cyea_news_release a left join cyea_news_count b on a.SID = b.NEWS_RELEASEID

GO
create view cyea_notice_view as
select n.sid SID,n.fmajor_level,
       n.ftitle,n.fcontent,
       n.fdate,n.fReadCount,
       n.fname,n.FCLASS,n.fstate,
       n.fopenscopeid,n.fopenscope,
       n.FACCESSORIES,n.version
   from cyea_notice n where n.fstate = '已发布'
union all
select m.fid,m.fimportancename fmajor_level,
       m.ftitle,m.fcontent,
       m.fcreatetime fdate,m.fReadCount,
       m.fcreatepername fname,m.fcreatedeptname FCLASS,m.fissuestatename fstate,
       G.fRangeURL fopenscopeid,m.fissuerange fopenscope,
       m.Faccessories,m.version
   from oa_is_info m
   JOIN oa_is_readerrange G ON m.fid = G.fmasterid
   where m.fIssueState = 'Issued' and m.fIssueDate <= GETDATE() and m.ftype = 'Placard'
   
GO   
create view sa_oporg_recycled as
select sID,
       sParent,
       sCode,
       sName,
       isnull(sOrgKindID, 'org') sOrgKindID,
       sFID,
       sValidState,
       SFCODE,
       sFName,
       sSequence,
       SDESCRIPTION,
       SADDRESS,
       version
  from Sa_Oporg o
 where o.svalidstate = -1
union all
select distinct c.sID,
                c.sParent,
                c.sCode,
                c.sName,
                isnull(c.sOrgKindID, 'org') sOrgKindID,
                c.sFID,
                c.sValidState,
                c.SFCODE,
                c.sFName,
                c.sSequence,
                c.SDESCRIPTION,
                c.SADDRESS,
                c.version
  from Sa_Oporg o
  left join Sa_Oporg c on c.sfid like o.sfid+'%'
 where o.svalidstate = -1
   and c.svalidstate != -1
union all
select p.sID,
       t.sid sParent,
       p.sCode,
       p.sName,
       'psm' sOrgKindID,
       t.sFID + '/' + p.sid,
       t.sValidState,
       t.sfcode + '/' + p.scode,
       t.sFName + '/' + p.sname,
       t.sSequence + 1 sSequence,
       t.SDESCRIPTION,
       t.SADDRESS,
       p.version
  from sa_opperson p
  left join sa_oporg t on p.smainorgid = t.sid
 where p.smainorgid is not null
   and p.svalidstate = -1
union all
select distinct v.sID,
                v.sParent,
                v.sCode,
                v.sName,
                v.sOrgKindID,
                v.sFID,
                v.sValidState,
                v.SFCODE,
                v.sFName,
                v.sSequence,
                v.SDESCRIPTION,
                v.SADDRESS,
                v.version
  from Sa_Oporg o
  left join Sa_Oporg_View v on v.sfid like o.sfid+'%'
 where o.svalidstate = -1
   and v.svalidstate != -1
GO   
create view sa_task_monitor as
select t.SID,
       t.SPARENTID,
       t.SNAME,
       t.SCONTENT,
       t.SREMARK,
       t.SFLOWID,
       t.STYPEID,
       t.STYPENAME,
       t.SIMPORTANCEID,
       t.SIMPORTANCENAME,
       t.SEMERGENCYID,
       t.SEMERGENCYNAME,
       t.SPROCESS,
       t.SACTIVITY,
       t.SCREATETIME,
       t.SDISTRIBUTETIME,
       t.SLASTMODIFYTIME,
       t.SWARNINGTIME,
       t.SLIMITTIME,
       t.SESTARTTIME,
       t.SEFINISHTIME,
       t.SASTARTTIME,
       t.SAFINISHTIME,
       t.SEXECUTETIME,
       t.SCPERSONID,
       t.SCPERSONNAME,
       t.SCDEPTID,
       t.SCDEPTNAME,
       t.SCOGNID,
       t.SCOGNNAME,
       t.SEPERSONID,
       t.SEPERSONNAME,
       t.SEDEPTID,
       t.SEDEPTNAME,
       t.SEOGNID,
       t.SEOGNNAME,
       t.SCUSTOMERID,
       t.SCUSTOMERNAME,
       t.SPROJECTID,
       t.SPROJECTNAME,
       t.SPLANID,
       t.SPLANNAME,
       t.SVARIABLE,
       t.SFAKE,
       t.SACTIVE,
       t.SLOCK,
       t.SSTATUSID,
       t.SSTATUSNAME,
       t.VERSION,
       t.SAIID,
       t.SCATALOGID,
       t.SKINDID,
       t.SAIACTIVE,
       t.SAISTATUSID,
       t.SAISTATUSNAME,
       t.SSOURCEID,
       t.SCURL,
       t.SEURL,
       t.SEXECUTEMODE,
       t.SEXECUTEMODE2,
       t.SPREEMPTMODE,
       t.SSEQUENCE,
       t.SCPERSONCODE,
       t.SCPOSID,
       t.SCPOSCODE,
       t.SCPOSNAME,
       t.SCDEPTCODE,
       t.SCOGNCODE,
       t.SCFID,
       t.SEPERSONCODE,
       t.SEPOSID,
       t.SEPOSCODE,
       t.SEPOSNAME,
       t.SEDEPTCODE,
       t.SEOGNCODE,
       t.SEFID,
       t.SEXECUTORNAMES,
       t.SRESPONSIBLE,
       t.SCUSTOMERCODE,
       t.SPROJECTCODE,
       t.SPLANCODE,
       t.SDATA1,
       t.SDATA2,
       t.SDATA3,
       t.SDATA4,
       t.SWORKTIME,
       t.SCFNAME,
       t.SEFNAME,
       t.SHINTS,
       t.SSHORTCUT,
       t.SFRONTID,
       t.SFMAKERNAME,
       p.sname as sflowName
  from sa_task t, sa_task p
 where t.sid = p.sparentid
   and t.SFLOWID = t.sid
GO
create view sa_oporg_mphone_view as
select sID,
       sParent,
       sCode,
       sName,
       isnull(sOrgKindID, 'org') sOrgKindID,
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
 where o.svalidstate = 1
union all
select p.sID,
       t.sid sParent,
       p.sCode,
       p.sName+'('+p.smobilephone+')' as sname,
       'psm' sOrgKindID,
       t.sFID + '/' + p.sid,
       t.sValidState,
       t.sfcode + '/' + p.scode,
       t.sFName + '/' + p.sname,
       t.slevel + p.sSequence sSequence,
       t.SDESCRIPTION,
       t.SADDRESS,
       p.smobilephone,
       p.version
  from sa_opperson p
  left join sa_oporg t on p.smainorgid = t.sid
 where p.smainorgid is not null
   and t.svalidstate = 1
   and p.svalidstate = 1
union all
select p.sID,
       t.sid sParent,
       p.sCode,
        p.sName+'('+p.smobilephone+')' as sname,
       'psm' sOrgKindID,
       t.sFID + '/' + p.sid,
       t.sValidState,
       t.sfcode + '/' + p.scode,
       t.sFName + '/' + p.sname,
       t.slevel + p.sSequence sSequence,
       t.SDESCRIPTION,
       t.SADDRESS,
       p.smobilephone,
       p.version
  from sa_opperson p, sa_oporg t
 where t.sid in
       (select m.sorgid from sa_oppersonmember m where m.spersonid = p.sid)
  and p.smainorgid != t.sid
   and t.svalidstate = 1
   and p.svalidstate = 1
GO
CREATE VIEW sa_onlineinfo_view AS 
select t.SID AS sid,a.SNAME AS sname,a.SCODE AS scode,a.SFNAME AS sfname,t.SLOGINIP AS sloginip,
t.SLOGINDATE AS slogindate,b.STITLE AS STITLE,b.SMOBILEPHONE AS smobilephone,
t.SSESSIONID AS ssessionid,t.SSERVICEIP AS sserviceip,t.VERSION AS version 
from sa_onlineinfo t join sa_oporg a on t.SUSERID = a.SPERSONID 
join sa_opperson b on  b.SID = a.SPERSONID
GO
CREATE VIEW sa_loginlog_view AS 
select t.SID AS sid,a.SNAME AS sname,a.SCODE AS scode,a.SFNAME AS sfname,
t.SLOGINIP AS sloginip,t.SLOGINTIME AS slogintime,b.SMOBILEPHONE AS smobilephone,
t.VERSION AS version from sa_loginlog t join sa_oporg a on t.SUSERID = a.SPERSONID 
 join sa_opperson b on b.SID = a.SPERSONID
GO
CREATE VIEW sa_opmobilelog_view AS 
select distinct p.SID AS sid,p.SCODE AS scode,p.SNAME AS sname,
o.SFNAME AS sfname,p.SMOBILEPHONE AS smobilephone,p.VERSION AS version 
from sa_opmobilelog l join sa_oporg o on o.SPERSONID = l.SUSERID 
join sa_opperson p on p.SID = l.SUSERID
GO
CREATE VIEW sa_vpnlog_view AS 
select distinct o.SID AS sid,o.SCODE AS scode,o.SNAME AS sname,
o.SFNAME AS sfname,o.VERSION AS version,l.SLOGINTIME AS slogintime 
 from sa_loginlog l join sa_oporg o on o.SPERSONID = l.SUSERID 
 where l.SLOGINIP = 'x.x.x.x' and o.SID is not null and not exists(select p.SUSERID from sa_opmobilelog p where l.SUSERID = p.SUSERID) 
 and l.SLOGINTIME = (select max(lg.SLOGINTIME) from sa_loginlog lg where lg.SUSERID = l.SUSERID) 
