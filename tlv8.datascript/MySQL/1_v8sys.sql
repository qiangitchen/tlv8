-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: v8sys
-- ------------------------------------------------------
-- Server version	5.7.12-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bo_blog`
--

DROP TABLE IF EXISTS `bo_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bo_blog` (
  `SID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `NAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ENTRY_NUMBER` int(11) DEFAULT NULL,
  `RECENT_NUMBER` int(11) DEFAULT NULL,
  `RECENT_ENTRY_NUMBER` int(11) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `COMMENT_AUDIT` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `OPENSCOPE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SETTING` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BLOGCATEGORY` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CHAIRMAN` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CHAIRMAN_ID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bo_blog`
--

LOCK TABLES `bo_blog` WRITE;
/*!40000 ALTER TABLE `bo_blog` DISABLE KEYS */;
/*!40000 ALTER TABLE `bo_blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bo_category`
--

DROP TABLE IF EXISTS `bo_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bo_category` (
  `SID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `NAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DISPLAY_ORDER` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FMASTERID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bo_category`
--

LOCK TABLES `bo_category` WRITE;
/*!40000 ALTER TABLE `bo_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `bo_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bo_comment`
--

DROP TABLE IF EXISTS `bo_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bo_comment` (
  `SID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `AUTHOR` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '回复人',
  `EMAIL` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `CONTENT` text COLLATE utf8mb4_unicode_ci COMMENT '回复内容',
  `CREATED_TIME` datetime DEFAULT NULL COMMENT '回复时间',
  `ENTRY_ID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子ID',
  `ENTRY_TITLE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '帖子标题',
  `IP` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bo_comment`
--

LOCK TABLES `bo_comment` WRITE;
/*!40000 ALTER TABLE `bo_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `bo_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bo_entry`
--

DROP TABLE IF EXISTS `bo_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bo_entry` (
  `SID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `TITLE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CONTENT` longtext COLLATE utf8mb4_unicode_ci,
  `CATEGORY_ID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CATEGORY_NAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `COMMENT_HIT` int(11) DEFAULT NULL,
  `ALLOW_COMMENT` int(11) DEFAULT NULL,
  `STATUS` int(11) DEFAULT '0',
  `CREATED_TIME` datetime DEFAULT NULL,
  `UPDATED_TIME` datetime DEFAULT NULL,
  `HITS` int(11) DEFAULT NULL,
  `BO_BLOGID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ACCESSORIES` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FEXTEND01` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ONESELF` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bo_entry`
--

LOCK TABLES `bo_entry` WRITE;
/*!40000 ALTER TABLE `bo_entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `bo_entry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cyea_news_count`
--

DROP TABLE IF EXISTS `cyea_news_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cyea_news_count` (
  `SID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `NEWS_NUMBER` int(11) DEFAULT '0',
  `NEWS_PERSON` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NEWS_PERSONID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NEWS_RELEASEID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `YETPERSON` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `YETPERSONID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cyea_news_count`
--

LOCK TABLES `cyea_news_count` WRITE;
/*!40000 ALTER TABLE `cyea_news_count` DISABLE KEYS */;
/*!40000 ALTER TABLE `cyea_news_count` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cyea_news_release`
--

DROP TABLE IF EXISTS `cyea_news_release`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cyea_news_release` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `FNEWSTITLE` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FRELEASEDEPARTMENT` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FPEOPLE` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FTIME` datetime DEFAULT NULL,
  `FNEWSNUMBER` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FRELEASECONNEXT` longtext COLLATE utf8mb4_unicode_ci,
  `FSTATE` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FSETTOPWHETHER` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FSETTOPTIME` datetime DEFAULT NULL,
  `FSETENDTIME` datetime DEFAULT NULL,
  `FACCESSORIES` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FCOLUMNNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FOPENSCOPE` varchar(4000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FOPENSCOPEID` varchar(4000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMINIPIC` longblob,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cyea_news_release`
--

LOCK TABLES `cyea_news_release` WRITE;
/*!40000 ALTER TABLE `cyea_news_release` DISABLE KEYS */;
/*!40000 ALTER TABLE `cyea_news_release` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cyea_newscolumn`
--

DROP TABLE IF EXISTS `cyea_newscolumn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cyea_newscolumn` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `FCOLUMNCODE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FCOLUMNNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FCOLUMNDESCRIBES` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FCOLUMNSTATE` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cyea_newscolumn`
--

LOCK TABLES `cyea_newscolumn` WRITE;
/*!40000 ALTER TABLE `cyea_newscolumn` DISABLE KEYS */;
/*!40000 ALTER TABLE `cyea_newscolumn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `im_message`
--

DROP TABLE IF EXISTS `im_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `im_message` (
  `sID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `favatar` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送人头像',
  `fusername` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送人昵称',
  `fid` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送人ID',
  `content` text COLLATE utf8mb4_unicode_ci COMMENT '消息内容',
  `tavatar` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '目标头像',
  `tname` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '目标名称',
  `tsign` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '对方签名',
  `stype` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消息类型',
  `tusername` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '目标昵称',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `stime` datetime DEFAULT NULL COMMENT '时间',
  `VERSION` int(11) DEFAULT NULL,
  `groupname` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '群组名称',
  `tid` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '目标ID',
  PRIMARY KEY (`sID`),
  KEY `im_message_fid` (`fid`),
  KEY `im_message_tid` (`tid`),
  KEY `im_message_state` (`state`),
  KEY `im_message_stype` (`stype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `im_message`
--

LOCK TABLES `im_message` WRITE;
/*!40000 ALTER TABLE `im_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `im_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `news_tables`
--

DROP TABLE IF EXISTS `news_tables`;
/*!50001 DROP VIEW IF EXISTS `news_tables`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `news_tables` AS SELECT 
 1 AS `SID`,
 1 AS `VERSION`,
 1 AS `FNEWSTITLE`,
 1 AS `FSTATE`,
 1 AS `FPEOPLE`,
 1 AS `FTIME`,
 1 AS `FOPENSCOPE`,
 1 AS `NEWS_NUMBER`,
 1 AS `NEWS_PERSON`,
 1 AS `YETPERSON`,
 1 AS `FOPENSCOPEID`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `personal_file`
--

DROP TABLE IF EXISTS `personal_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_file` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SFILENAME` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFILESIZE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDOCPATH` text COLLATE utf8mb4_unicode_ci,
  `SFILEID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DOCID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMASTERID` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACCESSORY` text COLLATE utf8mb4_unicode_ci,
  `SACCESSCURRENTID` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACCESSCURRENTNAME` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_file`
--

LOCK TABLES `personal_file` WRITE;
/*!40000 ALTER TABLE `personal_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `personal_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personaldocnode`
--

DROP TABLE IF EXISTS `personaldocnode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personaldocnode` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPARENTID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPARENTNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPATH` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACCESSCURRENTID` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACCESSCURRENTNAME` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personaldocnode`
--

LOCK TABLES `personaldocnode` WRITE;
/*!40000 ALTER TABLE `personaldocnode` DISABLE KEYS */;
/*!40000 ALTER TABLE `personaldocnode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_docauth`
--

DROP TABLE IF EXISTS `sa_docauth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_docauth` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SDOCPATH` text COLLATE utf8mb4_unicode_ci,
  `SAUTHORIZERFID` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAUTHORIZERNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAUTHORIZERDEPTNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAUTHORIZEEFID` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAUTHORIZEENAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SGRANTTIME` datetime DEFAULT NULL,
  `SACCESS` int(11) DEFAULT NULL,
  `SSCOPE` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAUTHORIZEEDEPTNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_docauth`
--

LOCK TABLES `sa_docauth` WRITE;
/*!40000 ALTER TABLE `sa_docauth` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_docauth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_doclog`
--

DROP TABLE IF EXISTS `sa_doclog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_doclog` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPERSONNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACCESSTYPE` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDOCID` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STIME` datetime DEFAULT NULL,
  `SDEPTNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPERSONFID` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDOCVERSIONID` int(11) DEFAULT NULL,
  `SDOCNAME` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSIZE` float DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_doclog`
--

LOCK TABLES `sa_doclog` WRITE;
/*!40000 ALTER TABLE `sa_doclog` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_doclog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_docnamespace`
--

DROP TABLE IF EXISTS `sa_docnamespace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_docnamespace` (
  `SID` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SDISPLAYNAME` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SHOST` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPORT` int(11) DEFAULT NULL,
  `SURL` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `SFLAG` int(11) DEFAULT NULL,
  `SACCESSMODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_docnamespace`
--

LOCK TABLES `sa_docnamespace` WRITE;
/*!40000 ALTER TABLE `sa_docnamespace` DISABLE KEYS */;
INSERT INTO `sa_docnamespace` VALUES ('defaultDocNameSpace','文档中心','127.0.0.1',8080,'http://127.0.0.1:8080/DocServer',37,1,'0');
/*!40000 ALTER TABLE `sa_docnamespace` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_docnode`
--

DROP TABLE IF EXISTS `sa_docnode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_docnode` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPARENTID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDOCNAME` varchar(2500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSEQUENCE` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSIZE` float DEFAULT NULL,
  `SKIND` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDOCPATH` text COLLATE utf8mb4_unicode_ci,
  `SDOCDISPLAYPATH` longtext COLLATE utf8mb4_unicode_ci,
  `SCREATORFID` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORDEPTNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATETIME` datetime DEFAULT NULL,
  `SEDITORFID` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEDITORNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEDITORDEPTNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLASTWRITERFID` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLASTWRITERNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLASTWRITERDEPTNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLASTWRITETIME` datetime DEFAULT NULL,
  `SFILEID` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDESCRIPTION` longtext COLLATE utf8mb4_unicode_ci,
  `SDOCLIVEVERSIONID` int(11) DEFAULT NULL,
  `VERSION` decimal(10,0) DEFAULT NULL,
  `SCLASSIFICATION` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SKEYWORDS` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDOCSERIALNUMBER` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFINISHTIME` datetime DEFAULT NULL,
  `SNAMESPACE` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCACHENAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SREVISIONCACHENAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFLAG` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`),
  KEY `IDX_SA_DOCNODE` (`SPARENTID`),
  KEY `IND_SA_DONNODE_SKIND` (`SKIND`),
  KEY `IDX_SA_DOCNODE_SFILEID` (`SFILEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_docnode`
--

LOCK TABLES `sa_docnode` WRITE;
/*!40000 ALTER TABLE `sa_docnode` DISABLE KEYS */;
INSERT INTO `sa_docnode` VALUES ('root',NULL,'文档中心','',NULL,'dir','/root','/文档中心','','','',NULL,'','','','','','',NULL,'','',NULL,4,'','','',NULL,'defaultDocNameSpace','','',1);
/*!40000 ALTER TABLE `sa_docnode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_flowcontrol`
--

DROP TABLE IF EXISTS `sa_flowcontrol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_flowcontrol` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SOPERATORID` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROCESS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIVITY` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTION` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SKIND` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCONTENT` longtext COLLATE utf8mb4_unicode_ci,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_flowcontrol`
--

LOCK TABLES `sa_flowcontrol` WRITE;
/*!40000 ALTER TABLE `sa_flowcontrol` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_flowcontrol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_flowdata`
--

DROP TABLE IF EXISTS `sa_flowdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_flowdata` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SFLOWID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROCESS` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIVITY` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTION` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SKIND` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCONTENT` longtext COLLATE utf8mb4_unicode_ci,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_flowdata`
--

LOCK TABLES `sa_flowdata` WRITE;
/*!40000 ALTER TABLE `sa_flowdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_flowdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_flowdrawlg`
--

DROP TABLE IF EXISTS `sa_flowdrawlg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_flowdrawlg` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `SPROCESSID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '流程标识',
  `SPROCESSNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流程名称',
  `SDRAWLG` longtext COLLATE utf8mb4_unicode_ci COMMENT '流程图',
  `VERSION` int(11) DEFAULT '0' COMMENT '版本号',
  `SPROCESSACTY` longtext COLLATE utf8mb4_unicode_ci COMMENT '流程环节',
  `SCREATORID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人ID',
  `SCREATORNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `SUPDATORID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人ID',
  `SUPDATORNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改人名称',
  `SCREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `SUPDATETIME` datetime DEFAULT NULL COMMENT '修改时间',
  `FENABLED` int(11) DEFAULT NULL COMMENT '启用状态',
  `SFOLDERID` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '目录ID',
  PRIMARY KEY (`SID`),
  UNIQUE KEY `SA_FLOWDRAWLG_SPROCESSID` (`SPROCESSID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_flowdrawlg`
--

LOCK TABLES `sa_flowdrawlg` WRITE;
/*!40000 ALTER TABLE `sa_flowdrawlg` DISABLE KEYS */;
INSERT INTO `sa_flowdrawlg` VALUES ('C9D86833FD700001DF5165CBE8E21A46','/demo/flow/process','请假流程','<v:line title=\"New Line\" style=\"position: absolute; z-index: 1; cursor: pointer; display: none;\"><v:stroke></v:stroke></v:line><div id=\"bizActivity\" title=\"New bizActivity\" style=\"border: 1px solid blue; position: absolute; left: 300px; top: 80px; width: 100px; height: 40px; cursor: default; z-index: 100; display: none;\"><v:stroke></v:stroke></div><div id=\"Activity1\" title=\"开始\" style=\"position: absolute; left: 300px; top: 80px; width: 40px; height: 40px; cursor: pointer; z-index: 1;\"><svg id=\"SvgjsSvg1000\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%\" height=\"100%\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><ellipse id=\"SvgjsEllipse1006\" rx=\"20\" ry=\"20\" cx=\"20\" cy=\"20\" stroke=\"#33cc00\" stroke-width=\"1\" fill=\"#33cc00\"></ellipse><defs id=\"SvgjsDefs1001\"></defs></svg><v:shadow></v:shadow><div style=\"position: inherit; left: 2px; top: 12px; z-index: 1; width: 100%; height: 100%; text-align: center; color: blue; font-size: 9pt;\">开始</div></div><div id=\"bizActivity2\" title=\"请假申请\" style=\"border: 1px solid blue; position: absolute; left: 270px; top: 140px; width: 100px; height: 40px; cursor: default; z-index: 1; background: rgb(255, 255, 255); box-shadow: rgb(179, 179, 179) 5px 5px 5px;\"><div id=\"bizActivity2_Label\" style=\"width: 100%; height: 100%; margin-top: 6px; text-align: center; color: rgb(0, 0, 0); font-size: 10pt; line-height: 25px;\">请假申请</div></div><div id=\"bizActivity4\" title=\"部门审批\" style=\"border: 1px solid green; position: absolute; left: 270px; top: 200px; width: 100px; height: 40px; z-index: 99999; background: rgb(255, 255, 255); box-shadow: green 5px 5px 5px;\"><div id=\"bizActivity4_Label\" style=\"width: 100%; height: 100%; margin-top: 6px; text-align: center; color: green; font-size: 10pt; line-height: 25px;\">部门审批</div></div><div id=\"bizActivity6\" title=\"领导审批\" style=\"border: 1px solid blue; position: absolute; left: 270px; top: 260px; width: 100px; height: 40px; cursor: default; z-index: 1; background: rgb(255, 255, 255); box-shadow: rgb(179, 179, 179) 5px 5px 5px;\"><div id=\"bizActivity6_Label\" style=\"width: 100%; height: 100%; margin-top: 6px; text-align: center; color: rgb(0, 0, 0); font-size: 10pt; line-height: 25px;\">领导审批</div></div><div id=\"Activity8\" title=\"结束\" style=\"position: absolute; left: 300px; top: 320px; width: 40px; height: 40px; cursor: pointer; z-index: 1;\"><svg id=\"SvgjsSvg1007\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%\" height=\"100%\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><ellipse id=\"SvgjsEllipse1009\" rx=\"20\" ry=\"20\" cx=\"20\" cy=\"20\" stroke=\"red\" stroke-width=\"1\" fill=\"red\"></ellipse><defs id=\"SvgjsDefs1008\"></defs></svg><v:shadow></v:shadow><div style=\"position: inherit; left: 2px; top: 12px; z-index: 1; width: 100%; height: 100%; text-align: center; color: blue; font-size: 9pt;\">结束</div></div><div id=\"line_3\" style=\"left: 0px; top: 0px; width: 100%; height: 100%; margin-top: 30px; position: absolute; z-index: 1; cursor: pointer;\"><svg id=\"SvgjsSvg1010\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%\" height=\"100%\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><polyline id=\"SvgjsPolyline1012\" points=\"320,90 320,102\" stroke=\"blue\" stroke-width=\"1\" fill=\"none\" marker-end=\"url(#idArrow)\"></polyline><defs id=\"SvgjsDefs1011\"></defs></svg></div><div id=\"line_5\" style=\"left: 0px; top: 0px; width: 100%; height: 100%; margin-top: 30px; position: absolute; z-index: 1; cursor: pointer;\"><svg id=\"SvgjsSvg1013\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%\" height=\"100%\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><polyline id=\"SvgjsPolyline1015\" points=\"320,150 320,162\" stroke=\"blue\" stroke-width=\"1\" fill=\"none\" marker-end=\"url(#idArrow)\"></polyline><defs id=\"SvgjsDefs1014\"></defs></svg></div><div id=\"line_7\" style=\"left: 0px; top: 0px; width: 100%; height: 100%; margin-top: 30px; position: absolute; z-index: 1; cursor: pointer;\"><svg id=\"SvgjsSvg1016\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%\" height=\"100%\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><polyline id=\"SvgjsPolyline1018\" points=\"320,210 320,222\" stroke=\"blue\" stroke-width=\"1\" fill=\"none\" marker-end=\"url(#idArrow)\"></polyline><defs id=\"SvgjsDefs1017\"></defs></svg></div><div id=\"line_9\" style=\"left: 0px; top: 0px; width: 100%; height: 100%; margin-top: 30px; position: absolute; z-index: 1; cursor: pointer;\"><svg id=\"SvgjsSvg1019\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%\" height=\"100%\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><polyline id=\"SvgjsPolyline1021\" points=\"320,270 320,282\" stroke=\"blue\" stroke-width=\"1\" fill=\"none\" marker-end=\"url(#idArrow)\"></polyline><defs id=\"SvgjsDefs1020\"></defs></svg></div><div id=\"/demo/flow/process\" style=\"left: 0px; top: 0px; width: 100%; height: 100%; margin-top: 30px;\"><svg id=\"SvgjsSvg1022\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%\" height=\"100%\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><defs id=\"SvgjsDefs1023\"></defs></svg><svg id=\"SvgjsSvg1024\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%\" height=\"100%\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><defs id=\"SvgjsDefs1025\"></defs></svg><svg id=\"SvgjsSvg1026\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%\" height=\"100%\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><defs id=\"SvgjsDefs1027\"></defs></svg><svg id=\"SvgjsSvg1028\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"100%\" height=\"100%\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"><defs id=\"SvgjsDefs1029\"></defs></svg></div><div id=\"/demo/flow/process\" style=\"left: 0px; top: 0px; width: 100%; height: 100%; margin-top: 30px;\"></div><div id=\"/demo/flow/process\" style=\"left: 0px; top: 0px; width: 100%; height: 100%; margin-top: 30px;\"></div><div id=\"/demo/flow/process\" style=\"left: 0px; top: 0px; width: 100%; height: 100%; margin-top: 30px;\"></div>',5,'{\"id\":\"\\/demo\\/flow\\/process\",\"name\":\"请假流程\",\"count\":9,\"nodes\":[{\"id\":\"Activity1\",\"name\":\"开始\",\"type\":\"start\",\"shape\":\"oval\",\"number\":1,\"left\":300,\"top\":80,\"width\":40,\"height\":40,\"property\":null},{\"id\":\"bizActivity2\",\"name\":\"请假申请\",\"type\":\"node\",\"shape\":\"rect\",\"number\":2,\"left\":270,\"top\":140,\"width\":100,\"height\":40,\"property\":[{\"id\":\"n_p_id\",\"text\":\"input\",\"value\":\"bizActivity2\"},{\"id\":\"n_p_name\",\"text\":\"input\",\"value\":\"请假申请\"},{\"id\":\"n_p_exepage\",\"text\":\"input\",\"value\":\"\\/demo\\/flow\\/mainActivity.html\"},{\"id\":\"n_p_label\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_desc\",\"text\":\"textarea\",\"value\":\"\"},{\"id\":\"n_p_group\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_roleID\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_role\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_r_grab\",\"text\":\"select\",\"value\":\"\"},{\"id\":\"n_r_grabway\",\"text\":\"select\",\"value\":\"\"},{\"id\":\"n_t_queryt\",\"text\":\"select\",\"value\":\"\"},{\"id\":\"n_p_back\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_note\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_r_transe\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_in\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_time\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_timetype\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call1\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call2\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call3\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call4\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call5\",\"text\":\"input\",\"value\":\"\"}]},{\"id\":\"mainActivity1\",\"name\":\"部门审批\",\"type\":\"node\",\"shape\":\"rect\",\"number\":4,\"left\":270,\"top\":200,\"width\":100,\"height\":40,\"property\":[{\"id\":\"n_p_id\",\"text\":\"input\",\"value\":\"mainActivity1\"},{\"id\":\"n_p_name\",\"text\":\"input\",\"value\":\"部门审批\"},{\"id\":\"n_p_exepage\",\"text\":\"input\",\"value\":\"\\/demo\\/flow\\/mainActivity2.html\"},{\"id\":\"n_p_label\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_desc\",\"text\":\"textarea\",\"value\":\"\"},{\"id\":\"n_p_group\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_roleID\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_role\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_r_grab\",\"text\":\"select\",\"value\":\"\"},{\"id\":\"n_r_grabway\",\"text\":\"select\",\"value\":\"\"},{\"id\":\"n_t_queryt\",\"text\":\"select\",\"value\":\"\"},{\"id\":\"n_p_back\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_note\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_r_transe\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_in\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_time\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_timetype\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call1\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call2\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call3\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call4\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call5\",\"text\":\"input\",\"value\":\"\"}]},{\"id\":\"bizActivity6\",\"name\":\"领导审批\",\"type\":\"node\",\"shape\":\"rect\",\"number\":6,\"left\":270,\"top\":260,\"width\":100,\"height\":40,\"property\":[{\"id\":\"n_p_id\",\"text\":\"input\",\"value\":\"bizActivity6\"},{\"id\":\"n_p_name\",\"text\":\"input\",\"value\":\"领导审批\"},{\"id\":\"n_p_exepage\",\"text\":\"input\",\"value\":\"\\/demo\\/flow\\/mainActivity2.html\"},{\"id\":\"n_p_label\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_desc\",\"text\":\"textarea\",\"value\":\"\"},{\"id\":\"n_p_group\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_roleID\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_role\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_r_grab\",\"text\":\"select\",\"value\":\"\"},{\"id\":\"n_r_grabway\",\"text\":\"select\",\"value\":\"\"},{\"id\":\"n_t_queryt\",\"text\":\"select\",\"value\":\"\"},{\"id\":\"n_p_back\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_note\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_r_transe\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_in\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_time\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_timetype\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call1\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call2\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call3\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call4\",\"text\":\"input\",\"value\":\"\"},{\"id\":\"n_p_call5\",\"text\":\"input\",\"value\":\"\"}]},{\"id\":\"Activity8\",\"name\":\"结束\",\"type\":\"end\",\"shape\":\"oval\",\"number\":8,\"left\":300,\"top\":320,\"width\":40,\"height\":40,\"property\":null}],\"lines\":[{\"id\":\"line_3\",\"name\":\"line_3\",\"type\":\"line\",\"shape\":\"line\",\"number\":3,\"from\":\"Activity1\",\"to\":\"bizActivity2\",\"fromx\":320,\"fromy\":120,\"tox\":320,\"toy\":140,\"spwidth\":0,\"spheight\":0,\"polydot\":[],\"property\":null},{\"id\":\"line_5\",\"name\":\"line_5\",\"type\":\"line\",\"shape\":\"line\",\"number\":5,\"from\":\"bizActivity2\",\"to\":\"mainActivity1\",\"fromx\":320,\"fromy\":180,\"tox\":320,\"toy\":200,\"spwidth\":0,\"spheight\":0,\"polydot\":[],\"property\":null},{\"id\":\"line_7\",\"name\":\"line_7\",\"type\":\"line\",\"shape\":\"line\",\"number\":7,\"from\":\"mainActivity1\",\"to\":\"bizActivity6\",\"fromx\":320,\"fromy\":240,\"tox\":320,\"toy\":260,\"spwidth\":0,\"spheight\":0,\"polydot\":[],\"property\":null},{\"id\":\"line_9\",\"name\":\"line_9\",\"type\":\"line\",\"shape\":\"line\",\"number\":9,\"from\":\"bizActivity6\",\"to\":\"Activity8\",\"fromx\":320,\"fromy\":300,\"tox\":320,\"toy\":320,\"spwidth\":0,\"spheight\":0,\"polydot\":[],\"property\":null}]}',NULL,NULL,'PSN01','system',NULL,'2022-06-01 15:54:09',1,'root');
/*!40000 ALTER TABLE `sa_flowdrawlg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_flowdrawlg_ipo`
--

DROP TABLE IF EXISTS `sa_flowdrawlg_ipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_flowdrawlg_ipo` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPROCESSID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPROCESSNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDRAWLG` text COLLATE utf8mb4_unicode_ci,
  `VERSION` int(11) DEFAULT NULL,
  `SPROCESSACTY` text COLLATE utf8mb4_unicode_ci,
  `SCREATORID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUPDATORID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUPDATORNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATETIME` datetime DEFAULT NULL,
  `SUPDATETIME` datetime DEFAULT NULL,
  `FENABLED` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_flowdrawlg_ipo`
--

LOCK TABLES `sa_flowdrawlg_ipo` WRITE;
/*!40000 ALTER TABLE `sa_flowdrawlg_ipo` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_flowdrawlg_ipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_flowfolder`
--

DROP TABLE IF EXISTS `sa_flowfolder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_flowfolder` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPROCESSID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROCESSNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCODE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPARENT` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SIDPATH` varchar(4000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SNAMEPATH` varchar(4000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCODEPATH` varchar(4000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`SID`),
  KEY `SOU_SA_FLWFOLDER_SPARENT` (`SPARENT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_flowfolder`
--

LOCK TABLES `sa_flowfolder` WRITE;
/*!40000 ALTER TABLE `sa_flowfolder` DISABLE KEYS */;
INSERT INTO `sa_flowfolder` VALUES ('root',NULL,'根目录','root','根目录',NULL,'/D08B63947EE1444D8C97DC0C5EAD8AD6','/根目录','/root',0);
/*!40000 ALTER TABLE `sa_flowfolder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_flowinfo`
--

DROP TABLE IF EXISTS `sa_flowinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_flowinfo` (
  `ID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `STOACTIVITY` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STOOPERATORID` varchar(4000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` decimal(10,0) DEFAULT NULL,
  `SFROMACTIVITYINSTANCEID` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STASKNAME` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STASKCONTENT` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STASKCREATETIME` datetime DEFAULT NULL,
  `STASKEMERGENCYID` decimal(10,0) DEFAULT NULL,
  `STASKWARNINGTIME` datetime DEFAULT NULL,
  `STASKLIMITTIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_flowinfo`
--

LOCK TABLES `sa_flowinfo` WRITE;
/*!40000 ALTER TABLE `sa_flowinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_flowinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_flowinfoext`
--

DROP TABLE IF EXISTS `sa_flowinfoext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_flowinfoext` (
  `ID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPROCESS` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SFROMACTIVITY` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SCREATORID` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEXECUTORS` longtext COLLATE utf8mb4_unicode_ci,
  `STOACTIVITY` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_flowinfoext`
--

LOCK TABLES `sa_flowinfoext` WRITE;
/*!40000 ALTER TABLE `sa_flowinfoext` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_flowinfoext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_flowtrace`
--

DROP TABLE IF EXISTS `sa_flowtrace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_flowtrace` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SOPERATORID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SOPERATORCODE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SOPERATORNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCURL` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEURL` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCHECKPSN` varchar(4000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) NOT NULL,
  PRIMARY KEY (`SID`),
  KEY `SA_FLOWTRACE_SOPERATORID` (`SOPERATORID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_flowtrace`
--

LOCK TABLES `sa_flowtrace` WRITE;
/*!40000 ALTER TABLE `sa_flowtrace` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_flowtrace` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_kvsequence`
--

DROP TABLE IF EXISTS `sa_kvsequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_kvsequence` (
  `K` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `V` int(11) NOT NULL,
  PRIMARY KEY (`K`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_kvsequence`
--

LOCK TABLES `sa_kvsequence` WRITE;
/*!40000 ALTER TABLE `sa_kvsequence` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_kvsequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_links`
--

DROP TABLE IF EXISTS `sa_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_links` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STITLE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SURL` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUSERNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPASSWORD` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEXPARAMS` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SOPENTYPE` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATER` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATEDATE` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_links`
--

LOCK TABLES `sa_links` WRITE;
/*!40000 ALTER TABLE `sa_links` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_log`
--

DROP TABLE IF EXISTS `sa_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_log` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `STYPENAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDESCRIPTION` longtext COLLATE utf8mb4_unicode_ci,
  `SPROCESS` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROCESSNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIVITY` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIVITYNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTION` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIONNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSTATUSNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATETIME` datetime DEFAULT NULL,
  `SCREATORFID` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORFNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORPERSONID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORPERSONNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORPOSID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORPOSNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORDEPTID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORDEPTNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATOROGNID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATOROGNNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SIP` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` decimal(10,0) DEFAULT NULL,
  `SDEVICETYPE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SOPERATINGSYSTEM` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_log`
--

LOCK TABLES `sa_log` WRITE;
/*!40000 ALTER TABLE `sa_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_loginlog`
--

DROP TABLE IF EXISTS `sa_loginlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_loginlog` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SUSERID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUSERNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLOGINIP` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLOGINTIME` datetime DEFAULT NULL,
  `PASSWORD` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `SSERVICEIP` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDAY` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDAYNUM` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_loginlog`
--

LOCK TABLES `sa_loginlog` WRITE;
/*!40000 ALTER TABLE `sa_loginlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_loginlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `sa_loginlog_view`
--

DROP TABLE IF EXISTS `sa_loginlog_view`;
/*!50001 DROP VIEW IF EXISTS `sa_loginlog_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sa_loginlog_view` AS SELECT 
 1 AS `sid`,
 1 AS `sname`,
 1 AS `scode`,
 1 AS `sfname`,
 1 AS `sloginip`,
 1 AS `slogintime`,
 1 AS `smobilephone`,
 1 AS `version`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sa_mailset`
--

DROP TABLE IF EXISTS `sa_mailset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_mailset` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SMAIL` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱地址',
  `SACCOUNT` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `SNAME` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发信名称',
  `STYPE` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱类型',
  `SSENDHOST` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发件服务器',
  `SSENDPOST` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发件端口',
  `SISSSL` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发件是否启用SSL',
  `SRECHOST` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收件服务器',
  `SRECPORT` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收件端口',
  `SRECSSL` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收件是否启用SSL',
  `SISPUB` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否公用',
  `FCREATEPSNFID` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人FID',
  `FCREATEPSNID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人ID',
  `FCREATEPSNNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `FCREATEDEPTID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门ID',
  `FCREATEDEPTNAME` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门名称',
  `FCREATEOGNID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构ID',
  `FCREATEOGNNAME` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构名称',
  `FCREATEORGID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织ID',
  `FCREATEORGNAME` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织名称',
  `FCREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_mailset`
--

LOCK TABLES `sa_mailset` WRITE;
/*!40000 ALTER TABLE `sa_mailset` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_mailset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_msnalert`
--

DROP TABLE IF EXISTS `sa_msnalert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_msnalert` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SURL` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMESSAGE` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSTATE` int(11) NOT NULL DEFAULT '0',
  `SBILLID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SBILTABLE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SRPERSONID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSPERSONID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSDATE` datetime DEFAULT NULL,
  `SRDATE` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT '0',
  `SMURL` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMSTATE` int(11) DEFAULT '0',
  `SOPIONID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT '',
  PRIMARY KEY (`SID`),
  KEY `SA_MSNALERT_SRPERSONID` (`SRPERSONID`),
  KEY `SA_MSNALERT_SOPIONID` (`SOPIONID`),
  KEY `SA_MSNALERT_SSDATE` (`SSDATE`),
  KEY `SA_MSNALERT_SBILLID` (`SBILLID`),
  KEY `SA_MSNALERT_SSTATE` (`SSTATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_msnalert`
--

LOCK TABLES `sa_msnalert` WRITE;
/*!40000 ALTER TABLE `sa_msnalert` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_msnalert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_online_log`
--

DROP TABLE IF EXISTS `sa_online_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_online_log` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SUSERID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUSERNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUSERFID` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUSERFNAME` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLOGINIP` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLOGINDATE` datetime DEFAULT NULL,
  `SLOGOUTDATE` datetime DEFAULT NULL,
  `SSESSIONID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSERVICEIP` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `SDAY` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDAYNUM` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`),
  KEY `SA_PNLINE_LOG_SLOGINDATE` (`SLOGINDATE`),
  KEY `SA_ONLINE_LOG_SSERVICEIP` (`SSERVICEIP`),
  KEY `SA_ONLINE_LOG_SSESSIONID` (`SSESSIONID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_online_log`
--

LOCK TABLES `sa_online_log` WRITE;
/*!40000 ALTER TABLE `sa_online_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_online_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_onlineinfo`
--

DROP TABLE IF EXISTS `sa_onlineinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_onlineinfo` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SUSERID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUSERNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUSERFID` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUSERFNAME` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLOGINIP` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLOGINDATE` datetime DEFAULT NULL,
  `SSESSIONID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSERVICEIP` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `SMACHINECODE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`),
  KEY `SA_ONLINEINFO_SSESSIONID` (`SSESSIONID`),
  KEY `SA_ONLINEINFO_SSERVICEIP` (`SSERVICEIP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_onlineinfo`
--

LOCK TABLES `sa_onlineinfo` WRITE;
/*!40000 ALTER TABLE `sa_onlineinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_onlineinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `sa_onlineinfo_view`
--

DROP TABLE IF EXISTS `sa_onlineinfo_view`;
/*!50001 DROP VIEW IF EXISTS `sa_onlineinfo_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sa_onlineinfo_view` AS SELECT 
 1 AS `sid`,
 1 AS `sname`,
 1 AS `scode`,
 1 AS `sfname`,
 1 AS `sloginip`,
 1 AS `slogindate`,
 1 AS `STITLE`,
 1 AS `smobilephone`,
 1 AS `ssessionid`,
 1 AS `sserviceip`,
 1 AS `version`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sa_opagent`
--

DROP TABLE IF EXISTS `sa_opagent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_opagent` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SORGFID` varchar(2048) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SORGFNAME` varchar(2048) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SAGENTID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SVALIDSTATE` int(11) DEFAULT NULL,
  `SSTARTTIME` datetime DEFAULT NULL,
  `SFINISHTIME` datetime DEFAULT NULL,
  `SPROCESS` longtext COLLATE utf8mb4_unicode_ci,
  `SCREATORID` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATETIME` datetime DEFAULT NULL,
  `SCANTRANAGENT` int(11) DEFAULT NULL,
  `VERSION` int(11) NOT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_opagent`
--

LOCK TABLES `sa_opagent` WRITE;
/*!40000 ALTER TABLE `sa_opagent` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_opagent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_opauthorize`
--

DROP TABLE IF EXISTS `sa_opauthorize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_opauthorize` (
  `SID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SORGID` varchar(65) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SORGNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SORGFID` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SORGFNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAUTHORIZEROLEID` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDESCRIPTION` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORFID` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORFNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `SROLELEVEL` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`),
  KEY `IDX_OPAUTH_ROLEID` (`SAUTHORIZEROLEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_opauthorize`
--

LOCK TABLES `sa_opauthorize` WRITE;
/*!40000 ALTER TABLE `sa_opauthorize` DISABLE KEYS */;
INSERT INTO `sa_opauthorize` VALUES ('AHR01','PSN01@ORG01','管理员','/ORG01.ogn/PSN01@ORG01.psm','/管理员/system','RL01','超级管理员','/ORG01.ogn/PSN01@ORG01.psm','/tlv8/system','2020-11-03 00:00:00',0,'0'),('B6E8199DC88D4051A26BEB004E6FA52E','ORG01','管理员','/ORG01.ogn','/管理员','C9D86FC522800001879796D01FDE110E','通用角色','/ORG01.ogn/PSN01@ORG01.psm','/管理员/system','2022-06-01 16:03:20',0,NULL);
/*!40000 ALTER TABLE `sa_opauthorize` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_opmanagement`
--

DROP TABLE IF EXISTS `sa_opmanagement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_opmanagement` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SORGID` varchar(65) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SORGNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SORGFID` varchar(2048) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SORGFNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMANAGEORGID` varchar(65) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SMANAGEORGNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMANAGEORGFID` varchar(2048) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SMANAGEORGFNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMANAGETYPEID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SCREATORFID` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORFNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_opmanagement`
--

LOCK TABLES `sa_opmanagement` WRITE;
/*!40000 ALTER TABLE `sa_opmanagement` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_opmanagement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_opmanagetype`
--

DROP TABLE IF EXISTS `sa_opmanagetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_opmanagetype` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCODE` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMANAGEORGKIND` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMANAGEORGKINDNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLEADERNUMBER` int(11) DEFAULT NULL,
  `SISSYSTEM` int(11) DEFAULT NULL,
  `VERSION` int(11) NOT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_opmanagetype`
--

LOCK TABLES `sa_opmanagetype` WRITE;
/*!40000 ALTER TABLE `sa_opmanagetype` DISABLE KEYS */;
INSERT INTO `sa_opmanagetype` VALUES ('systemManagement','系统管理','systemManagement','ogn',' 机构 部门 岗位 工作组 人员',NULL,NULL,2);
/*!40000 ALTER TABLE `sa_opmanagetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_opmobilelog`
--

DROP TABLE IF EXISTS `sa_opmobilelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_opmobilelog` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SUSERID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUSERNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SIP` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDATE` datetime DEFAULT NULL,
  `SMODE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `SSESSIONID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLOGOUTDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_opmobilelog`
--

LOCK TABLES `sa_opmobilelog` WRITE;
/*!40000 ALTER TABLE `sa_opmobilelog` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_opmobilelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `sa_opmobilelog_view`
--

DROP TABLE IF EXISTS `sa_opmobilelog_view`;
/*!50001 DROP VIEW IF EXISTS `sa_opmobilelog_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sa_opmobilelog_view` AS SELECT 
 1 AS `sid`,
 1 AS `scode`,
 1 AS `sname`,
 1 AS `sfname`,
 1 AS `smobilephone`,
 1 AS `version`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sa_oporg`
--

DROP TABLE IF EXISTS `sa_oporg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_oporg` (
  `SID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SNAME` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SCODE` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SLONGNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFNAME` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFCODE` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFID` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SORGKINDID` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SVALIDSTATE` int(11) DEFAULT NULL,
  `SPARENT` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLEVEL` int(11) DEFAULT NULL,
  `SPHONE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFAX` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SADDRESS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SZIP` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDESCRIPTION` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPERSONID` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SNODEKIND` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) NOT NULL,
  `SSHOWNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSEQUENCE` int(11) DEFAULT '1',
  PRIMARY KEY (`SID`),
  KEY `IDX_OPORG_CODE` (`SCODE`),
  KEY `IDX_SA_OPORG_SORGKINDID` (`SORGKINDID`),
  KEY `IDX_SA_OPORG_SPARENT` (`SPARENT`),
  KEY `IDX_SA_OPORG_SPERSONID` (`SPERSONID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_oporg`
--

LOCK TABLES `sa_oporg` WRITE;
/*!40000 ALTER TABLE `sa_oporg` DISABLE KEYS */;
INSERT INTO `sa_oporg` VALUES ('ORG01','管理员','TULIN','','/管理员','/TULIN','/ORG01.ogn','ogn',1,NULL,1,'','','','','','','',16,'',96),('PSN01@ORG01','system','SYSTEM','','/管理员/system','/TULIN/SYSTEM','/ORG01.ogn/PSN01@ORG01.psm','psm',1,'ORG01',2,'','','','','','PSN01','nkLeaf',15,'',1);
/*!40000 ALTER TABLE `sa_oporg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_oporg_interface`
--

DROP TABLE IF EXISTS `sa_oporg_interface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_oporg_interface` (
  `SOGNID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SOGNNAME` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SOGNID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_oporg_interface`
--

LOCK TABLES `sa_oporg_interface` WRITE;
/*!40000 ALTER TABLE `sa_oporg_interface` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_oporg_interface` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `sa_oporg_mphone_view`
--

DROP TABLE IF EXISTS `sa_oporg_mphone_view`;
/*!50001 DROP VIEW IF EXISTS `sa_oporg_mphone_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sa_oporg_mphone_view` AS SELECT 
 1 AS `sID`,
 1 AS `sParent`,
 1 AS `sCode`,
 1 AS `sName`,
 1 AS `sOrgKindID`,
 1 AS `sFID`,
 1 AS `sValidState`,
 1 AS `SFCODE`,
 1 AS `sFName`,
 1 AS `sSequence`,
 1 AS `SDESCRIPTION`,
 1 AS `SADDRESS`,
 1 AS `smobilephone`,
 1 AS `version`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sa_oporg_recycled`
--

DROP TABLE IF EXISTS `sa_oporg_recycled`;
/*!50001 DROP VIEW IF EXISTS `sa_oporg_recycled`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sa_oporg_recycled` AS SELECT 
 1 AS `sID`,
 1 AS `sParent`,
 1 AS `sCode`,
 1 AS `sName`,
 1 AS `sOrgKindID`,
 1 AS `sFID`,
 1 AS `sValidState`,
 1 AS `SFCODE`,
 1 AS `sFName`,
 1 AS `sSequence`,
 1 AS `SDESCRIPTION`,
 1 AS `SADDRESS`,
 1 AS `version`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `sa_oporg_view`
--

DROP TABLE IF EXISTS `sa_oporg_view`;
/*!50001 DROP VIEW IF EXISTS `sa_oporg_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sa_oporg_view` AS SELECT 
 1 AS `sID`,
 1 AS `sParent`,
 1 AS `sCode`,
 1 AS `sName`,
 1 AS `sOrgKindID`,
 1 AS `sFID`,
 1 AS `sValidState`,
 1 AS `SFCODE`,
 1 AS `sFName`,
 1 AS `sSequence`,
 1 AS `SDESCRIPTION`,
 1 AS `SADDRESS`,
 1 AS `smobilephone`,
 1 AS `version`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sa_oppermission`
--

DROP TABLE IF EXISTS `sa_oppermission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_oppermission` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPERMISSIONROLEID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPROCESS` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIVITYFNAME` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIVITY` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIONSNAMES` longtext COLLATE utf8mb4_unicode_ci,
  `SACTIONS` longtext COLLATE utf8mb4_unicode_ci,
  `SSEMANTICDP` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPERMISSIONKIND` int(11) DEFAULT NULL,
  `SDESCRIPTION` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSEQUENCE` int(11) DEFAULT NULL,
  `SVALIDSTATE` int(11) DEFAULT NULL,
  `VERSION` int(11) NOT NULL,
  PRIMARY KEY (`SID`),
  KEY `IDX_OPPERM_ROLEID` (`SPERMISSIONROLEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_oppermission`
--

LOCK TABLES `sa_oppermission` WRITE;
/*!40000 ALTER TABLE `sa_oppermission` DISABLE KEYS */;
INSERT INTO `sa_oppermission` VALUES ('182217CC50E84AB2A6EA3085A18BA621','RL01','/SA/doc/docSetting/docSettingProcess','/系统管理/文档/文档配置','mainActivity','','','',0,'',NULL,1,0),('1B1AF2E6140A445796ABE0442CBB54DE','RL01','/SA/doc/docPermission/docPermissionProcess','/系统管理/文档/文档关联','mainActivity','','','',0,'',NULL,1,0),('265569FC504443E1B134592E3B5FCC5C','RL01','/SA/OPM/log/logProcess','/系统管理/组织权限/组织机构日志','mainActivity','','','',0,'',NULL,1,1),('3566316F52F84896ACE1EF9BF42018BE','RL01','/SA/OPM/organization/organizationProcess','/系统管理/组织权限/组织管理','mainActivity','','','',0,'',NULL,1,1),('38A312285E834995AFCAC32E2D50265A','RL01','/SA/doc/docCenter/docCenterProcess','/系统管理/文档/文档中心','docCenter','','','',0,'',NULL,1,0),('5F62DE289C8648689D20D9370ACAE21C','RL01','/SA/OPM/role/roleProcess','/系统管理/组织权限/角色管理','mainActivity','','','',0,'',NULL,1,1),('6A02EEF862114421A0EC8BD3A4BD4222','RL01','/SA/OPM/authorization/authorizationProcess','/系统管理/组织权限/授权管理','mainActivity','','','',0,'',NULL,1,1),('6F21B10EAECC46BE95A7A9349D0B041F','RL01','/SA/OPM/grade/gradeProcess','/系统管理/组织权限/分级管理','mainActivity','','','',0,'',NULL,1,1),('82E555A346274CE092AE25B4DD0BFBE8','RL01','/SA/log/logProcess','/系统管理/系统工具/操作日志','mainActivity','','','',0,'',NULL,1,1),('84EBC08BB9D64F4092267873339FBEE2','RL01','/SA/OPM/repairTools/repairToolsProcess','/系统管理/组织权限/修复工具','mainActivity','','','',0,'',NULL,1,1),('9D8D492C5D49460E9DDA5F6CED4B1545','RL01','/SA/update/updateProcess','/系统管理/系统工具/软件更新','mainActivity','','','',0,'',NULL,1,1),('A84780D2CEF64C5B9DE9947ECD13ED28','RL01','/SA/doc/docSearch/docSearchProcess','/系统管理/文档/文档检索','mainActivity','','','',0,'',NULL,1,0),('BCBEB3FEA6DB4283AD2494254FDBCDAE','RL01','/SA/online/onlineProcess','/系统管理/系统工具/在线用户','mainActivity','','','',0,'',NULL,1,3),('C6914BAA2E84424C901DBF8FD95144D7','RL01','/SA/OPM/management/managementProcess','/系统管理/组织权限/业务管理权限','mainActivity','','','',0,'',NULL,1,1),('C6B3C40F53200001F12719F019995DA0','RL01','/SA/OPM/recycled/recycledProcess','/系统管理/组织机构/回收站','mainActivity','','','',NULL,'',NULL,NULL,0),('C7305169AE000001C7741ACC284019D3','RL01','/OA/log/LoginLogProcess','/系统管理/系统工具/用户登录统计','LoginLog','','','',NULL,'',NULL,NULL,0),('C7305169AED0000167D26C507440DE00','RL01','/SA/log/mobileAppLogProcess','/系统管理/系统工具/手机用户统计','mobileAppLog','','','',NULL,'',NULL,NULL,0),('C7305169AF800001EA2316506D70FAF0','RL01','/SA/log/VPNLogProcess','/系统管理/系统工具/VPN用户统计','VPNLog','','','',NULL,'',NULL,NULL,0),('C7305169B1900001B9731A801F5D9020','RL01','/SA/MobileNumber/MobileNumbersProcess','/系统管理/系统工具/移动号码段维护','MobileNumbers','','','',NULL,'',NULL,NULL,0),('C7D8FEE6EFE00001B6751A1E12303070','RL01','/OA/fawenDanwei/mainProcess','/系统管理/系统工具/允许发文单位','mainActivity','','','',NULL,'',NULL,NULL,0),('C82E3F2608B00001E83E18231E2A18BA','RL01','/SA/theme/bgProcess','/系统管理/系统工具/登录页背景配置','mainActivity','','','',NULL,'',NULL,NULL,1),('C8A5A0B5BED00001ADC31CC0968311E8','RL01','SA/services/pcProcess','/系统管理/系统工具/服务器监控','mainActivity','','','',NULL,'',NULL,NULL,1),('C8DC2295A8E00001B8DE85203AD016AE','RL01','/SA/task/taskCenter/process','/任务中心/任务列表','mainActivity','','','',NULL,'',NULL,NULL,1),('C8DC2295A9600001BBE919691C301C37','RL01','/SA/process/monitor/process','/任务中心/流程监控','mainActivity','','','',NULL,'',NULL,NULL,1),('C8DC2295A9E00001D89590F5198012BC','RL01','/SA/task/unFlowmana/process','/任务中心/任务处理','mainActivity','','','',NULL,'',NULL,NULL,1),('C8DC2295AA6000017BF83A6965004D50','RL01','/flw/dwr/process','/任务中心/流程设计','vml-dwr-editer','','','',NULL,'',NULL,NULL,1),('C94E5AE783D000017BE612A0165419DF','RL01','029AB9CC9C5D4378B25C9BCB6C32B33D','/采购管理/采购登记','bizActivity2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C94E5AE784A0000163C271C810651321','RL01','029AB9CC9C5D4378B25C9BCB6C32B33D','/采购管理/采购审批','bizActivity4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C94EE9000440000135A1A7F050121CA8','RL01','/SA/task/taskCenter/process','/任务中心/流程监控','monitorActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C94EE9000540000138D11EF078DC6810','RL01','/SA/task/taskCenter/process','/任务中心/任务处理','unFlowmanaActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C94EE90006D000013BDE12401F401700','RL01','/SA/task/taskCenter/process','/任务中心/系统提醒','reminActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D861368390000192A814CF1D5312A9','RL01','/demo/date-picker/process','/DEMO展示/组件例子/日期选择','Date-main',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E688000016F30AD4D19CF17A5','RL01','/demo/list/process','/DEMO展示/列表表格/平台grid','MySampleListPage',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E695000012A5410AF10DC1A6E','RL01','/demo/bootstrapgrid/process','/DEMO展示/列表表格/bootstrap表格','simpleGrid',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E6A3000016B2F1204F1401D71','RL01','/demo/directList/process','/DEMO展示/列表表格/主从列表','MyDirectGridPage',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E6B20000189365C1C19EA195A','RL01','/demo/simpleDetail/process','/DEMO展示/表单流程/基础表单','MySampleDetailPage',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E6C2000012AD2185A1A101230','RL01','/demo/simpleDirct/process','/DEMO展示/表单流程/简单主从','MyDirectDetailPage',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E6D20000126A41E30D54374B0','RL01','/demo/directFlow/process','/DEMO展示/表单流程/主从流程','MyDirectFlowPage',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E6E2000016B4C18334D051A8D','RL01','/demo/flow/process','/DEMO展示/表单流程/请假流程','mainActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E6F20000184AEADF060A07E40','RL01','/demo/flow/process','/DEMO展示/表单流程/请假审批','mainActivity1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E70200001F9BD3FFFFE10D540','RL01','/demo/listportaldetail/process','/DEMO展示/列表详细/列表详细（portal）','listActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E71300001D1F31D251A558A90','RL01','/demo/listTabDetail/process','/DEMO展示/列表详细/列表详细（Tab）','MyListdetailTagPage',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E726000019FE715201C80D2F0','RL01','/demo/echarts/process','/DEMO展示/图表展示/Step Line','slineActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E73900001C56BA7DD750E12F7','RL01','/demo/echarts/process','/DEMO展示/图表展示/时间线','timelineActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E74A000013720120013405D50','RL01','/demo/echarts/process','/DEMO展示/图表展示/饼图','pieActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D8613E75C0000136FF2600193F1E73','RL01','/demo/echarts/process','/DEMO展示/图表展示/树形图','treeActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16A900001158791B01CD04040','C9D86FC522800001879796D01FDE110E','/SA/task/taskCenter/process','/个人办公/我的待办','mainActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16AD00001CCA1F9D030502540','C9D86FC522800001879796D01FDE110E','/SA/personal/schedule/process','/个人办公/日程安排','mainActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16B100001BBD2131018307120','C9D86FC522800001879796D01FDE110E','/OA/email/process','/个人办公/电子邮件','mainActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16B500001E33519901CED1524','C9D86FC522800001879796D01FDE110E','/OA/flowset/myOpinion/process','/个人办公/审批意见设置','mainActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16B90000110D21BE01A441900','C9D86FC522800001879796D01FDE110E','/system/accessory/process','/个人办公/计算器','mainActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16BE000018B955BE07CE01646','C9D86FC522800001879796D01FDE110E','/system/workLog/process','/个人办公/工作日志/写日志','WorkLog',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16C5000011B701B46118716F7','C9D86FC522800001879796D01FDE110E','/system/workLog/process','/个人办公/工作日志/查看日志','WorkLogList',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16CB00001DD73BB1A16401010','C9D86FC522800001879796D01FDE110E','/OA/Report/DayReport/process','/个人办公/日报/个人日报','reportList',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16D200001FDA658F11D471BC9','C9D86FC522800001879796D01FDE110E','/OA/Report/DayReport/process','/个人办公/日报/日报查看','reportLookList',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16D9000011598B4E013701474','C9D86FC522800001879796D01FDE110E','/OA/Report/WeekReport/process','/个人办公/周报/个人周报','reportList',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16DF0000169A01C7813201A7B','C9D86FC522800001879796D01FDE110E','/OA/Report/WeekReport/process','/个人办公/周报/周报查看','reportLookList',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16E60000193A51A33F9C6EAF0','C9D86FC522800001879796D01FDE110E','/OA/Report/MonthReport/process','/个人办公/月报/个人月报','reportList',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16ED0000133D51A7C2F006580','C9D86FC522800001879796D01FDE110E','/OA/Report/MonthReport/process','/个人办公/月报/月报查看','reportLookList',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16F600001E0E98A2017401B02','C9D86FC522800001879796D01FDE110E','/SA/personal/cnttSrc/process','/员工自助/信息修改','PersonData',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD16FF000018DD11E4518608B00','C9D86FC522800001879796D01FDE110E','/SA/password/process','/员工自助/密码修改','mainActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD170800001674317E0B4CC41E0','C9D86FC522800001879796D01FDE110E','/SA/personal/personInfo/process','/员工自助/人事自助','mainActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FD171100001C07AF48850763950','C9D86FC522800001879796D01FDE110E','/OA/PersonUse/MYGROUP/process','/员工自助/我的组群','listActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FE07C100001B9B23FF01B601E55','RL01','SA/services/pcProcess','/系统管理/系统工具/服务器监控舱','cockpitActivity',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('C9D86FE07D1000014CEC93F0C8407AC0','RL01','/SA/mail/process','/系统管理/基础配置/邮箱配置','mailsetMain',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('F2AF3284E2D6405E9990376C19C57D45','RL01','/SA/OPM/agent/agentProcess','/系统管理/组织权限/代理设置','mainActivity','','','',0,'',NULL,1,1);
/*!40000 ALTER TABLE `sa_oppermission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_opperson`
--

DROP TABLE IF EXISTS `sa_opperson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_opperson` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SNAME` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SCODE` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SIDCARD` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SNUMB` int(11) NOT NULL,
  `SLOGINNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPASSWORD` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPASSWORDTIMELIMIT` int(11) DEFAULT NULL,
  `SPASSWORDMODIFYTIME` datetime DEFAULT NULL,
  `SMAINORGID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SSAFELEVELID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSEQUENCE` int(11) DEFAULT NULL,
  `SVALIDSTATE` int(11) DEFAULT NULL,
  `SDESCRIPTION` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSEX` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SBIRTHDAY` datetime DEFAULT NULL,
  `SJOINDATE` datetime DEFAULT NULL,
  `SHOMEPLACE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDEGREE` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SGRADUATESCHOOL` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSPECIALITY` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSCHOOLLENGTH` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STITLE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMARRIAGE` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCARDNO` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCARDKIND` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFAMILYADDRESS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SZIP` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMSN` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SQQ` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMAIL` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SMOBILEPHONE` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFAMILYPHONE` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SOFFICEPHONE` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `SPHOTO` longblob,
  `SCOUNTRY` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROVINCE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCITY` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPOSITIONS` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSCHOOL` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSTUDY` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SENGLISHNAME` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FCASN` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `FSIGNM` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`),
  UNIQUE KEY `IDX_OPPERSON_SCODE` (`SCODE`),
  KEY `IDX_OPPERSON_SMAINORGID` (`SMAINORGID`),
  KEY `XMSGINDEXMESSAGESVALIDSTATE` (`SVALIDSTATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_opperson`
--

LOCK TABLES `sa_opperson` WRITE;
/*!40000 ALTER TABLE `sa_opperson` DISABLE KEYS */;
INSERT INTO `sa_opperson` VALUES ('PSN01','system','SYSTEM','',1,'管理员','C4CA4238A0B923820DCC509A6F75849B',120,'2020-10-20 00:00:00','ORG01','',1,1,'','男','1976-12-03 00:00:00','2020-10-06 00:00:00','','','','','','','','','','','','','','','14769660886','','',4,NULL,'','','','','','','','',NULL);
/*!40000 ALTER TABLE `sa_opperson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_opperson_deatail`
--

DROP TABLE IF EXISTS `sa_opperson_deatail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_opperson_deatail` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPERSONID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STYPE` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSQUNS` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCODE` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SNAME` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SREMARK` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCLASS` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUPDATEDATE` datetime DEFAULT NULL,
  `SUPDATORID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SUPDATORNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_opperson_deatail`
--

LOCK TABLES `sa_opperson_deatail` WRITE;
/*!40000 ALTER TABLE `sa_opperson_deatail` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_opperson_deatail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_oprole`
--

DROP TABLE IF EXISTS `sa_oprole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_oprole` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `SNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `SCODE` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色编号',
  `SCATALOG` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SROLEKIND` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色分类',
  `SPARENTROLESNAMES` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '父角色名称',
  `SDESCRIPTION` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `SSEQUENCE` int(11) DEFAULT NULL COMMENT '排序',
  `SVALIDSTATE` int(11) DEFAULT NULL COMMENT '状态',
  `VERSION` int(11) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`SID`),
  UNIQUE KEY `IDX_OPROLE_CODE` (`SCODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_oprole`
--

LOCK TABLES `sa_oprole` WRITE;
/*!40000 ALTER TABLE `sa_oprole` DISABLE KEYS */;
INSERT INTO `sa_oprole` VALUES ('C9D86FC522800001879796D01FDE110E','通用角色','pub','功能角色',NULL,NULL,NULL,NULL,NULL,1),('RL01','超级管理员','opm','勿删-系统应用','fun','','',0,1,4),('RL02','任务','task','系统管理','fun','','',0,1,2),('RL02-doc','文档','doc','系统管理','fun','','',0,1,2);
/*!40000 ALTER TABLE `sa_oprole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_oprolemanage`
--

DROP TABLE IF EXISTS `sa_oprolemanage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_oprolemanage` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SORGID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构ID',
  `SORGFID` varchar(360) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构FID',
  `SPERSONID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '人员ID',
  `SROLEID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色ID',
  `SCREATORID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人ID',
  `SCREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `VERSION` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_oprolemanage`
--

LOCK TABLES `sa_oprolemanage` WRITE;
/*!40000 ALTER TABLE `sa_oprolemanage` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_oprolemanage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_oprolemanagement`
--

DROP TABLE IF EXISTS `sa_oprolemanagement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_oprolemanagement` (
  `SID` varchar(35) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SORGID` varchar(80) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SORGNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SORGFID` varchar(2048) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SORGFNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SROLEID` varchar(35) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SCREATORFID` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATORFNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) NOT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_oprolemanagement`
--

LOCK TABLES `sa_oprolemanagement` WRITE;
/*!40000 ALTER TABLE `sa_oprolemanagement` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_oprolemanagement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_oprolerole`
--

DROP TABLE IF EXISTS `sa_oprolerole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_oprolerole` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SROLEID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPARENTROLE` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_oprolerole`
--

LOCK TABLES `sa_oprolerole` WRITE;
/*!40000 ALTER TABLE `sa_oprolerole` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_oprolerole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_opsenddocdist`
--

DROP TABLE IF EXISTS `sa_opsenddocdist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_opsenddocdist` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SCUROGNID` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDISOGNID` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDISOGNNAME` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDISOGNFID` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDISOGNFNAME` varchar(2048) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`SID`),
  UNIQUE KEY `SA_OPSENDDOCDIST_OGNID` (`SCUROGNID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_opsenddocdist`
--

LOCK TABLES `sa_opsenddocdist` WRITE;
/*!40000 ALTER TABLE `sa_opsenddocdist` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_opsenddocdist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_opsenddocogn`
--

DROP TABLE IF EXISTS `sa_opsenddocogn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_opsenddocogn` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SCUROGNID` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLIMIT` decimal(10,0) DEFAULT '0',
  `VERSION` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_opsenddocogn`
--

LOCK TABLES `sa_opsenddocogn` WRITE;
/*!40000 ALTER TABLE `sa_opsenddocogn` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_opsenddocogn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_portallets`
--

DROP TABLE IF EXISTS `sa_portallets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_portallets` (
  `SPERSONID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '人员ID',
  `SLAYOUTSET` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '布局',
  `SPANLES` text COLLATE utf8mb4_unicode_ci COMMENT '信息框',
  `SCREATORID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人ID',
  `SCREATORNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人名称',
  `SCREATEDATE` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`SPERSONID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_portallets`
--

LOCK TABLES `sa_portallets` WRITE;
/*!40000 ALTER TABLE `sa_portallets` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_portallets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_portalprofiles`
--

DROP TABLE IF EXISTS `sa_portalprofiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_portalprofiles` (
  `SID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `VERSION` decimal(10,0) DEFAULT NULL,
  `SNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPERSONID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SVALUE` longtext COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_portalprofiles`
--

LOCK TABLES `sa_portalprofiles` WRITE;
/*!40000 ALTER TABLE `sa_portalprofiles` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_portalprofiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_portelinfo`
--

DROP TABLE IF EXISTS `sa_portelinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_portelinfo` (
  `SID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `STHEMETYPE` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPSNDESKINFO` longtext COLLATE utf8mb4_unicode_ci,
  `SPERSONID` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_portelinfo`
--

LOCK TABLES `sa_portelinfo` WRITE;
/*!40000 ALTER TABLE `sa_portelinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_portelinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_project`
--

DROP TABLE IF EXISTS `sa_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_project` (
  `SID` decimal(10,0) NOT NULL,
  `SNAME` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `CREATOR` decimal(10,0) DEFAULT NULL,
  `CREATE_DEPARTMENT` decimal(10,0) NOT NULL,
  `STARTDATE` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ENDDATE` varchar(19) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STATUS` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `PRIORITY` decimal(10,0) DEFAULT NULL,
  `VERSION` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_project`
--

LOCK TABLES `sa_project` WRITE;
/*!40000 ALTER TABLE `sa_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_psnmytask`
--

DROP TABLE IF EXISTS `sa_psnmytask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_psnmytask` (
  `SID` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SCAPTION` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSTARTDATE` datetime DEFAULT NULL,
  `SENDDATE` datetime DEFAULT NULL,
  `SPRIORITY` int(11) DEFAULT NULL,
  `SCONTENT` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSTATUS` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAFFAIRSTYPE` int(11) DEFAULT NULL,
  `SCOMPLETERATE` int(11) DEFAULT NULL,
  `SSTARTDATE_AXIS` int(11) DEFAULT NULL,
  `SSENDDATE` int(11) DEFAULT NULL,
  `SWHOUSER` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `SSENDDATE_AXIS` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_psnmytask`
--

LOCK TABLES `sa_psnmytask` WRITE;
/*!40000 ALTER TABLE `sa_psnmytask` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_psnmytask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_psnschedule`
--

DROP TABLE IF EXISTS `sa_psnschedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_psnschedule` (
  `SID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SCAPTION` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSTARTDATE` datetime DEFAULT NULL,
  `SENDDATE` datetime DEFAULT NULL,
  `SCONTENT` varchar(4000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPRIORITY` int(11) DEFAULT NULL,
  `SSTATUS` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSTARTDATE_AXIS` int(11) DEFAULT NULL,
  `SSENDDATE_AXIS` int(11) DEFAULT NULL,
  `SAFFAIRSTYPE` int(11) DEFAULT NULL,
  `SWHOUSER` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_psnschedule`
--

LOCK TABLES `sa_psnschedule` WRITE;
/*!40000 ALTER TABLE `sa_psnschedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_psnschedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_remindinfo`
--

DROP TABLE IF EXISTS `sa_remindinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_remindinfo` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `STITLE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `SCONTEXT` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `SDATETIME` datetime DEFAULT NULL COMMENT '时间',
  `SSTATE` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态',
  `SACTION` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作',
  `SPERSONID` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '人员ID',
  `SPERSONNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '人员名称',
  `SORGID` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织ID',
  `SORGNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组织名称',
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_remindinfo`
--

LOCK TABLES `sa_remindinfo` WRITE;
/*!40000 ALTER TABLE `sa_remindinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_remindinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_task`
--

DROP TABLE IF EXISTS `sa_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_task` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPARENTID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCONTENT` text COLLATE utf8mb4_unicode_ci,
  `SREMARK` text COLLATE utf8mb4_unicode_ci,
  `SFLOWID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STYPEID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STYPENAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SIMPORTANCEID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SIMPORTANCENAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEMERGENCYID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEMERGENCYNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROCESS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIVITY` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATETIME` datetime DEFAULT NULL,
  `SDISTRIBUTETIME` datetime DEFAULT NULL,
  `SLASTMODIFYTIME` datetime DEFAULT NULL,
  `SWARNINGTIME` datetime DEFAULT NULL,
  `SLIMITTIME` datetime DEFAULT NULL,
  `SESTARTTIME` datetime DEFAULT NULL,
  `SEFINISHTIME` datetime DEFAULT NULL,
  `SASTARTTIME` datetime DEFAULT NULL,
  `SAFINISHTIME` datetime DEFAULT NULL,
  `SEXECUTETIME` datetime DEFAULT NULL,
  `SCPERSONID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCPERSONNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCDEPTID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCDEPTNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCOGNID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCOGNNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPERSONID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPERSONNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEDEPTID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEDEPTNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEOGNID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEOGNNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCUSTOMERID` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCUSTOMERNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROJECTID` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROJECTNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPLANID` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPLANNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SVARIABLE` text COLLATE utf8mb4_unicode_ci,
  `SFAKE` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIVE` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLOCK` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSTATUSID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSTATUSNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `SAIID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCATALOGID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SKINDID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAIACTIVE` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAISTATUSID` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAISTATUSNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSOURCEID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCURL` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEURL` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEXECUTEMODE` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEXECUTEMODE2` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPREEMPTMODE` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSEQUENCE` int(11) DEFAULT NULL,
  `SCPERSONCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCPOSID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCPOSCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCPOSNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCDEPTCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCOGNCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCFID` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPERSONCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPOSID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPOSCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPOSNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEDEPTCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEOGNCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEFID` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEXECUTORNAMES` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SRESPONSIBLE` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCUSTOMERCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROJECTCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPLANCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDATA1` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDATA2` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDATA3` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDATA4` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SWORKTIME` int(11) DEFAULT NULL,
  `SCFNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEFNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SHINTS` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSHORTCUT` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFRONTID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFMAKERNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`),
  KEY `SA_FLOWID` (`SFLOWID`),
  KEY `SA_STATUS` (`SSTATUSID`),
  KEY `SA_TASK_SPROCESS` (`SPROCESS`),
  KEY `SA_TASK_SDATA_1` (`SDATA1`),
  KEY `SA_TASK_SPARENTID` (`SPARENTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_task`
--

LOCK TABLES `sa_task` WRITE;
/*!40000 ALTER TABLE `sa_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_task_history`
--

DROP TABLE IF EXISTS `sa_task_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_task_history` (
  `SID` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPARENTID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCONTENT` longtext COLLATE utf8mb4_unicode_ci,
  `SREMARK` longtext COLLATE utf8mb4_unicode_ci,
  `SFLOWID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STYPEID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STYPENAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SIMPORTANCEID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SIMPORTANCENAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEMERGENCYID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEMERGENCYNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROCESS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIVITY` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATETIME` datetime DEFAULT NULL,
  `SDISTRIBUTETIME` datetime DEFAULT NULL,
  `SLASTMODIFYTIME` datetime DEFAULT NULL,
  `SWARNINGTIME` datetime DEFAULT NULL,
  `SLIMITTIME` datetime DEFAULT NULL,
  `SESTARTTIME` datetime DEFAULT NULL,
  `SEFINISHTIME` datetime DEFAULT NULL,
  `SASTARTTIME` datetime DEFAULT NULL,
  `SAFINISHTIME` datetime DEFAULT NULL,
  `SEXECUTETIME` datetime DEFAULT NULL,
  `SCPERSONID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCPERSONNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCDEPTID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCDEPTNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCOGNID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCOGNNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPERSONID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPERSONNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEDEPTID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEDEPTNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEOGNID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEOGNNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCUSTOMERID` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCUSTOMERNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROJECTID` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROJECTNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPLANID` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPLANNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SVARIABLE` longtext COLLATE utf8mb4_unicode_ci,
  `SFAKE` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SACTIVE` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLOCK` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSTATUSID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSTATUSNAME` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `SAIID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCATALOGID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SKINDID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAIACTIVE` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAISTATUSID` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SAISTATUSNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSOURCEID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCURL` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEURL` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEXECUTEMODE` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEXECUTEMODE2` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPREEMPTMODE` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSEQUENCE` int(11) DEFAULT NULL,
  `SCPERSONCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCPOSID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCPOSCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCPOSNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCDEPTCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCOGNCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCFID` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPERSONCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPOSID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPOSCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEPOSNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEDEPTCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEOGNCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEFID` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEXECUTORNAMES` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SRESPONSIBLE` varchar(8) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCUSTOMERCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROJECTCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPLANCODE` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDATA1` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDATA2` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDATA3` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SDATA4` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SWORKTIME` int(11) DEFAULT NULL,
  `SCFNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEFNAME` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SHINTS` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SSHORTCUT` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFRONTID` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SFMAKERNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_task_history`
--

LOCK TABLES `sa_task_history` WRITE;
/*!40000 ALTER TABLE `sa_task_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_task_history` ENABLE KEYS */;
UNLOCK TABLES;

-- ----------------------------
-- Table structure for sa_handwr_signature
-- ----------------------------

DROP TABLE IF EXISTS `sa_handwr_signature`;
CREATE TABLE `sa_handwr_signature`  (
  `sid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPERSONID` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `SPERSONNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `SCREATERID` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `SCREATERNAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `SCREATETIME` datetime(0) NULL DEFAULT NULL,
  `SHSPIC` blob NULL,
  `version` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

--
-- Temporary view structure for view `sa_task_monitor`
--

DROP TABLE IF EXISTS `sa_task_monitor`;
/*!50001 DROP VIEW IF EXISTS `sa_task_monitor`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sa_task_monitor` AS SELECT 
 1 AS `SID`,
 1 AS `SPARENTID`,
 1 AS `SNAME`,
 1 AS `SCONTENT`,
 1 AS `SREMARK`,
 1 AS `SFLOWID`,
 1 AS `STYPEID`,
 1 AS `STYPENAME`,
 1 AS `SIMPORTANCEID`,
 1 AS `SIMPORTANCENAME`,
 1 AS `SEMERGENCYID`,
 1 AS `SEMERGENCYNAME`,
 1 AS `SPROCESS`,
 1 AS `SACTIVITY`,
 1 AS `SCREATETIME`,
 1 AS `SDISTRIBUTETIME`,
 1 AS `SLASTMODIFYTIME`,
 1 AS `SWARNINGTIME`,
 1 AS `SLIMITTIME`,
 1 AS `SESTARTTIME`,
 1 AS `SEFINISHTIME`,
 1 AS `SASTARTTIME`,
 1 AS `SAFINISHTIME`,
 1 AS `SEXECUTETIME`,
 1 AS `SCPERSONID`,
 1 AS `SCPERSONNAME`,
 1 AS `SCDEPTID`,
 1 AS `SCDEPTNAME`,
 1 AS `SCOGNID`,
 1 AS `SCOGNNAME`,
 1 AS `SEPERSONID`,
 1 AS `SEPERSONNAME`,
 1 AS `SEDEPTID`,
 1 AS `SEDEPTNAME`,
 1 AS `SEOGNID`,
 1 AS `SEOGNNAME`,
 1 AS `SCUSTOMERID`,
 1 AS `SCUSTOMERNAME`,
 1 AS `SPROJECTID`,
 1 AS `SPROJECTNAME`,
 1 AS `SPLANID`,
 1 AS `SPLANNAME`,
 1 AS `SVARIABLE`,
 1 AS `SFAKE`,
 1 AS `SACTIVE`,
 1 AS `SLOCK`,
 1 AS `SSTATUSID`,
 1 AS `SSTATUSNAME`,
 1 AS `VERSION`,
 1 AS `SAIID`,
 1 AS `SCATALOGID`,
 1 AS `SKINDID`,
 1 AS `SAIACTIVE`,
 1 AS `SAISTATUSID`,
 1 AS `SAISTATUSNAME`,
 1 AS `SSOURCEID`,
 1 AS `SCURL`,
 1 AS `SEURL`,
 1 AS `SEXECUTEMODE`,
 1 AS `SEXECUTEMODE2`,
 1 AS `SPREEMPTMODE`,
 1 AS `SSEQUENCE`,
 1 AS `SCPERSONCODE`,
 1 AS `SCPOSID`,
 1 AS `SCPOSCODE`,
 1 AS `SCPOSNAME`,
 1 AS `SCDEPTCODE`,
 1 AS `SCOGNCODE`,
 1 AS `SCFID`,
 1 AS `SEPERSONCODE`,
 1 AS `SEPOSID`,
 1 AS `SEPOSCODE`,
 1 AS `SEPOSNAME`,
 1 AS `SEDEPTCODE`,
 1 AS `SEOGNCODE`,
 1 AS `SEFID`,
 1 AS `SEXECUTORNAMES`,
 1 AS `SRESPONSIBLE`,
 1 AS `SCUSTOMERCODE`,
 1 AS `SPROJECTCODE`,
 1 AS `SPLANCODE`,
 1 AS `SDATA1`,
 1 AS `SDATA2`,
 1 AS `SDATA3`,
 1 AS `SDATA4`,
 1 AS `SWORKTIME`,
 1 AS `SCFNAME`,
 1 AS `SEFNAME`,
 1 AS `SHINTS`,
 1 AS `SSHORTCUT`,
 1 AS `SFRONTID`,
 1 AS `SFMAKERNAME`,
 1 AS `sflowName`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sa_task_timelimit`
--

DROP TABLE IF EXISTS `sa_task_timelimit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_task_timelimit` (
  `sID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SPROCESSID` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流程标识',
  `SPROCESSNAME` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流程名称',
  `SACTIVITY` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流程环节',
  `SACTIVITYNAME` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '环节名称',
  `SDLIMIT` int(11) DEFAULT NULL COMMENT '时限（天）',
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`sID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_task_timelimit`
--

LOCK TABLES `sa_task_timelimit` WRITE;
/*!40000 ALTER TABLE `sa_task_timelimit` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_task_timelimit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sa_tasktype`
--

DROP TABLE IF EXISTS `sa_tasktype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_tasktype` (
  `SID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SNAME` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCONCEPT` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SNEWACTIVITY` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEXECUTEACTIVITY` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SKIND` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_tasktype`
--

LOCK TABLES `sa_tasktype` WRITE;
/*!40000 ALTER TABLE `sa_tasktype` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_tasktype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `sa_vpnlog_view`
--

DROP TABLE IF EXISTS `sa_vpnlog_view`;
/*!50001 DROP VIEW IF EXISTS `sa_vpnlog_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `sa_vpnlog_view` AS SELECT 
 1 AS `sid`,
 1 AS `scode`,
 1 AS `sname`,
 1 AS `sfname`,
 1 AS `version`,
 1 AS `slogintime`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sa_worklog`
--

DROP TABLE IF EXISTS `sa_worklog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sa_worklog` (
  `SID` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `SLOCK` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCUSTOMERNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SIMPORTANCENAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPLANNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SPROJECTNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SEMERGENCYNAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SLIMITTIME` datetime DEFAULT NULL,
  `SCONTENT` longtext COLLATE utf8mb4_unicode_ci,
  `SCREATORFID` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATOFNAME` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SCREATETIME` datetime DEFAULT NULL,
  `FEXTEND01` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sa_worklog`
--

LOCK TABLES `sa_worklog` WRITE;
/*!40000 ALTER TABLE `sa_worklog` DISABLE KEYS */;
/*!40000 ALTER TABLE `sa_worklog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `news_tables`
--

/*!50001 DROP VIEW IF EXISTS `news_tables`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `news_tables` AS select `a`.`SID` AS `SID`,`a`.`VERSION` AS `VERSION`,`a`.`FNEWSTITLE` AS `FNEWSTITLE`,`a`.`FSTATE` AS `FSTATE`,`a`.`FPEOPLE` AS `FPEOPLE`,`a`.`FTIME` AS `FTIME`,`a`.`FOPENSCOPE` AS `FOPENSCOPE`,`b`.`NEWS_NUMBER` AS `NEWS_NUMBER`,`b`.`NEWS_PERSON` AS `NEWS_PERSON`,`b`.`YETPERSON` AS `YETPERSON`,`a`.`FOPENSCOPEID` AS `FOPENSCOPEID` from (`cyea_news_release` `a` left join `cyea_news_count` `b` on((`a`.`SID` = `b`.`NEWS_RELEASEID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sa_loginlog_view`
--

/*!50001 DROP VIEW IF EXISTS `sa_loginlog_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `sa_loginlog_view` AS select `t`.`SID` AS `sid`,`a`.`SNAME` AS `sname`,`a`.`SCODE` AS `scode`,`a`.`SFNAME` AS `sfname`,`t`.`SLOGINIP` AS `sloginip`,`t`.`SLOGINTIME` AS `slogintime`,`b`.`SMOBILEPHONE` AS `smobilephone`,`t`.`VERSION` AS `version` from ((`sa_loginlog` `t` join `sa_oporg` `a`) join `sa_opperson` `b`) where ((`t`.`SUSERID` = `a`.`SPERSONID`) and (`b`.`SID` = `a`.`SPERSONID`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sa_onlineinfo_view`
--

/*!50001 DROP VIEW IF EXISTS `sa_onlineinfo_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `sa_onlineinfo_view` AS select `t`.`SID` AS `sid`,`a`.`SNAME` AS `sname`,`a`.`SCODE` AS `scode`,`a`.`SFNAME` AS `sfname`,`t`.`SLOGINIP` AS `sloginip`,`t`.`SLOGINDATE` AS `slogindate`,`b`.`STITLE` AS `STITLE`,`b`.`SMOBILEPHONE` AS `smobilephone`,`t`.`SSESSIONID` AS `ssessionid`,`t`.`SSERVICEIP` AS `sserviceip`,`t`.`VERSION` AS `version` from ((`sa_onlineinfo` `t` join `sa_oporg` `a`) join `sa_opperson` `b`) where ((`t`.`SUSERID` = `a`.`SPERSONID`) and (`b`.`SID` = `a`.`SPERSONID`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sa_opmobilelog_view`
--

/*!50001 DROP VIEW IF EXISTS `sa_opmobilelog_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `sa_opmobilelog_view` AS select distinct `p`.`SID` AS `sid`,`p`.`SCODE` AS `scode`,`p`.`SNAME` AS `sname`,`o`.`SFNAME` AS `sfname`,`p`.`SMOBILEPHONE` AS `smobilephone`,`p`.`VERSION` AS `version` from ((`sa_opmobilelog` `l` join `sa_oporg` `o` on((`o`.`SPERSONID` = `l`.`SUSERID`))) join `sa_opperson` `p` on((`p`.`SID` = `l`.`SUSERID`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sa_oporg_mphone_view`
--

/*!50001 DROP VIEW IF EXISTS `sa_oporg_mphone_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `sa_oporg_mphone_view` AS select `o`.`SID` AS `sID`,`o`.`SPARENT` AS `sParent`,`o`.`SCODE` AS `sCode`,`o`.`SNAME` AS `sName`,ifnull(`o`.`SORGKINDID`,'org') AS `sOrgKindID`,`o`.`SFID` AS `sFID`,`o`.`SVALIDSTATE` AS `sValidState`,`o`.`SFCODE` AS `SFCODE`,`o`.`SFNAME` AS `sFName`,`o`.`SSEQUENCE` AS `sSequence`,`o`.`SDESCRIPTION` AS `SDESCRIPTION`,`o`.`SADDRESS` AS `SADDRESS`,'' AS `smobilephone`,`o`.`VERSION` AS `version` from `sa_oporg` `o` where ((`o`.`SVALIDSTATE` = 1) and (`o`.`SORGKINDID` <> 'psm')) union all select `p`.`SID` AS `sID`,`t`.`SID` AS `sParent`,`p`.`SCODE` AS `sCode`,concat(`p`.`SNAME`,'(',ifnull(`p`.`SMOBILEPHONE`,''),')') AS `sname`,'psm' AS `sOrgKindID`,concat(`t`.`SFID`,'/',`p`.`SID`) AS `sfid`,`t`.`SVALIDSTATE` AS `sValidState`,concat(`t`.`SFCODE`,'/',`p`.`SCODE`) AS `sfcode`,concat(`t`.`SFNAME`,'/',`p`.`SNAME`) AS `sfname`,(`t`.`SLEVEL` + `p`.`SSEQUENCE`) AS `sSequence`,`t`.`SDESCRIPTION` AS `SDESCRIPTION`,`t`.`SADDRESS` AS `SADDRESS`,`p`.`SMOBILEPHONE` AS `smobilephone`,`p`.`VERSION` AS `version` from (`sa_opperson` `p` left join `sa_oporg` `t` on((`p`.`SMAINORGID` = `t`.`SID`))) where ((`p`.`SMAINORGID` is not null) and (`t`.`SVALIDSTATE` = 1) and (`p`.`SVALIDSTATE` = 1)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sa_oporg_recycled`
--

/*!50001 DROP VIEW IF EXISTS `sa_oporg_recycled`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `sa_oporg_recycled` AS select `o`.`SID` AS `sID`,`o`.`SPARENT` AS `sParent`,`o`.`SCODE` AS `sCode`,`o`.`SNAME` AS `sName`,ifnull(`o`.`SORGKINDID`,'org') AS `sOrgKindID`,`o`.`SFID` AS `sFID`,`o`.`SVALIDSTATE` AS `sValidState`,`o`.`SFCODE` AS `SFCODE`,`o`.`SFNAME` AS `sFName`,`o`.`SSEQUENCE` AS `sSequence`,`o`.`SDESCRIPTION` AS `SDESCRIPTION`,`o`.`SADDRESS` AS `SADDRESS`,`o`.`VERSION` AS `version` from `sa_oporg` `o` where (`o`.`SVALIDSTATE` = -(1)) union all select `p`.`SID` AS `sID`,`t`.`SID` AS `sParent`,`p`.`SCODE` AS `sCode`,`p`.`SNAME` AS `sName`,'psm' AS `sOrgKindID`,concat(`t`.`SFID`,'/',`p`.`SID`) AS `concat(t.sFID,'/',p.sid)`,`t`.`SVALIDSTATE` AS `sValidState`,concat(`t`.`SFCODE`,'/',`p`.`SCODE`) AS `concat(t.sfcode,'/',p.scode)`,concat(`t`.`SFNAME`,'/',`p`.`SNAME`) AS `concat(t.sFName,'/',p.sname)`,`t`.`SSEQUENCE` AS `sSequence`,`t`.`SDESCRIPTION` AS `SDESCRIPTION`,`t`.`SADDRESS` AS `SADDRESS`,`p`.`VERSION` AS `version` from (`sa_opperson` `p` left join `sa_oporg` `t` on((`p`.`SMAINORGID` = `t`.`SID`))) where ((`p`.`SMAINORGID` is not null) and (`p`.`SVALIDSTATE` = -(1))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sa_oporg_view`
--

/*!50001 DROP VIEW IF EXISTS `sa_oporg_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `sa_oporg_view` AS select `o`.`SID` AS `sID`,`o`.`SPARENT` AS `sParent`,`o`.`SCODE` AS `sCode`,`o`.`SNAME` AS `sName`,ifnull(`o`.`SORGKINDID`,'org') AS `sOrgKindID`,`o`.`SFID` AS `sFID`,`o`.`SVALIDSTATE` AS `sValidState`,`o`.`SFCODE` AS `SFCODE`,`o`.`SFNAME` AS `sFName`,`o`.`SSEQUENCE` AS `sSequence`,`o`.`SDESCRIPTION` AS `SDESCRIPTION`,`o`.`SADDRESS` AS `SADDRESS`,'' AS `smobilephone`,`o`.`VERSION` AS `version` from `sa_oporg` `o` where ((`o`.`SVALIDSTATE` = 1) and (`o`.`SORGKINDID` <> 'psm')) union all select `p`.`SID` AS `sID`,`t`.`SID` AS `sParent`,`p`.`SCODE` AS `sCode`,`p`.`SNAME` AS `sName`,'psm' AS `sOrgKindID`,concat(`t`.`SFID`,'/',`p`.`SID`) AS `CONCAT(t.sFID,'/',p.sid)`,`t`.`SVALIDSTATE` AS `sValidState`,concat(`t`.`SFCODE`,'/',`p`.`SCODE`) AS `CONCAT(t.sfcode,'/',p.scode)`,concat(`t`.`SFNAME`,'/',`p`.`SNAME`) AS `CONCAT(t.sFName,'/',p.sname)`,(`t`.`SLEVEL` + `p`.`SSEQUENCE`) AS `sSequence`,`t`.`SDESCRIPTION` AS `SDESCRIPTION`,`t`.`SADDRESS` AS `SADDRESS`,ifnull(`p`.`SMOBILEPHONE`,'') AS `smobilephone`,`p`.`VERSION` AS `version` from (`sa_opperson` `p` left join `sa_oporg` `t` on((`p`.`SMAINORGID` = `t`.`SID`))) where ((`p`.`SMAINORGID` is not null) and (`t`.`SVALIDSTATE` = 1) and (`p`.`SVALIDSTATE` = 1)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sa_task_monitor`
--

/*!50001 DROP VIEW IF EXISTS `sa_task_monitor`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `sa_task_monitor` AS select `t`.`SID` AS `SID`,`t`.`SPARENTID` AS `SPARENTID`,`t`.`SNAME` AS `SNAME`,`t`.`SCONTENT` AS `SCONTENT`,`t`.`SREMARK` AS `SREMARK`,`t`.`SFLOWID` AS `SFLOWID`,`t`.`STYPEID` AS `STYPEID`,`t`.`STYPENAME` AS `STYPENAME`,`t`.`SIMPORTANCEID` AS `SIMPORTANCEID`,`t`.`SIMPORTANCENAME` AS `SIMPORTANCENAME`,`t`.`SEMERGENCYID` AS `SEMERGENCYID`,`t`.`SEMERGENCYNAME` AS `SEMERGENCYNAME`,`t`.`SPROCESS` AS `SPROCESS`,`t`.`SACTIVITY` AS `SACTIVITY`,`t`.`SCREATETIME` AS `SCREATETIME`,`t`.`SDISTRIBUTETIME` AS `SDISTRIBUTETIME`,`t`.`SLASTMODIFYTIME` AS `SLASTMODIFYTIME`,`t`.`SWARNINGTIME` AS `SWARNINGTIME`,`t`.`SLIMITTIME` AS `SLIMITTIME`,`t`.`SESTARTTIME` AS `SESTARTTIME`,`t`.`SEFINISHTIME` AS `SEFINISHTIME`,`t`.`SASTARTTIME` AS `SASTARTTIME`,`t`.`SAFINISHTIME` AS `SAFINISHTIME`,`t`.`SEXECUTETIME` AS `SEXECUTETIME`,`t`.`SCPERSONID` AS `SCPERSONID`,`t`.`SCPERSONNAME` AS `SCPERSONNAME`,`t`.`SCDEPTID` AS `SCDEPTID`,`t`.`SCDEPTNAME` AS `SCDEPTNAME`,`t`.`SCOGNID` AS `SCOGNID`,`t`.`SCOGNNAME` AS `SCOGNNAME`,`t`.`SEPERSONID` AS `SEPERSONID`,`t`.`SEPERSONNAME` AS `SEPERSONNAME`,`t`.`SEDEPTID` AS `SEDEPTID`,`t`.`SEDEPTNAME` AS `SEDEPTNAME`,`t`.`SEOGNID` AS `SEOGNID`,`t`.`SEOGNNAME` AS `SEOGNNAME`,`t`.`SCUSTOMERID` AS `SCUSTOMERID`,`t`.`SCUSTOMERNAME` AS `SCUSTOMERNAME`,`t`.`SPROJECTID` AS `SPROJECTID`,`t`.`SPROJECTNAME` AS `SPROJECTNAME`,`t`.`SPLANID` AS `SPLANID`,`t`.`SPLANNAME` AS `SPLANNAME`,`t`.`SVARIABLE` AS `SVARIABLE`,`t`.`SFAKE` AS `SFAKE`,`t`.`SACTIVE` AS `SACTIVE`,`t`.`SLOCK` AS `SLOCK`,`t`.`SSTATUSID` AS `SSTATUSID`,`t`.`SSTATUSNAME` AS `SSTATUSNAME`,`t`.`VERSION` AS `VERSION`,`t`.`SAIID` AS `SAIID`,`t`.`SCATALOGID` AS `SCATALOGID`,`t`.`SKINDID` AS `SKINDID`,`t`.`SAIACTIVE` AS `SAIACTIVE`,`t`.`SAISTATUSID` AS `SAISTATUSID`,`t`.`SAISTATUSNAME` AS `SAISTATUSNAME`,`t`.`SSOURCEID` AS `SSOURCEID`,`t`.`SCURL` AS `SCURL`,`t`.`SEURL` AS `SEURL`,`t`.`SEXECUTEMODE` AS `SEXECUTEMODE`,`t`.`SEXECUTEMODE2` AS `SEXECUTEMODE2`,`t`.`SPREEMPTMODE` AS `SPREEMPTMODE`,`t`.`SSEQUENCE` AS `SSEQUENCE`,`t`.`SCPERSONCODE` AS `SCPERSONCODE`,`t`.`SCPOSID` AS `SCPOSID`,`t`.`SCPOSCODE` AS `SCPOSCODE`,`t`.`SCPOSNAME` AS `SCPOSNAME`,`t`.`SCDEPTCODE` AS `SCDEPTCODE`,`t`.`SCOGNCODE` AS `SCOGNCODE`,`t`.`SCFID` AS `SCFID`,`t`.`SEPERSONCODE` AS `SEPERSONCODE`,`t`.`SEPOSID` AS `SEPOSID`,`t`.`SEPOSCODE` AS `SEPOSCODE`,`t`.`SEPOSNAME` AS `SEPOSNAME`,`t`.`SEDEPTCODE` AS `SEDEPTCODE`,`t`.`SEOGNCODE` AS `SEOGNCODE`,`t`.`SEFID` AS `SEFID`,`t`.`SEXECUTORNAMES` AS `SEXECUTORNAMES`,`t`.`SRESPONSIBLE` AS `SRESPONSIBLE`,`t`.`SCUSTOMERCODE` AS `SCUSTOMERCODE`,`t`.`SPROJECTCODE` AS `SPROJECTCODE`,`t`.`SPLANCODE` AS `SPLANCODE`,`t`.`SDATA1` AS `SDATA1`,`t`.`SDATA2` AS `SDATA2`,`t`.`SDATA3` AS `SDATA3`,`t`.`SDATA4` AS `SDATA4`,`t`.`SWORKTIME` AS `SWORKTIME`,`t`.`SCFNAME` AS `SCFNAME`,`t`.`SEFNAME` AS `SEFNAME`,`t`.`SHINTS` AS `SHINTS`,`t`.`SSHORTCUT` AS `SSHORTCUT`,`t`.`SFRONTID` AS `SFRONTID`,`t`.`SFMAKERNAME` AS `SFMAKERNAME`,`p`.`SNAME` AS `sflowName` from (`sa_task` `t` join `sa_task` `p`) where ((`t`.`SID` = `p`.`SPARENTID`) and (`t`.`SFLOWID` = `t`.`SID`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sa_vpnlog_view`
--

/*!50001 DROP VIEW IF EXISTS `sa_vpnlog_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `sa_vpnlog_view` AS select distinct `o`.`SID` AS `sid`,`o`.`SCODE` AS `scode`,`o`.`SNAME` AS `sname`,`o`.`SFNAME` AS `sfname`,`o`.`VERSION` AS `version`,`l`.`SLOGINTIME` AS `slogintime` from (`sa_loginlog` `l` join `sa_oporg` `o` on((`o`.`SPERSONID` = `l`.`SUSERID`))) where ((`l`.`SLOGINIP` = '59.216.32.182') and (`o`.`SID` is not null) and (not(exists(select `p`.`SUSERID` from `sa_opmobilelog` `p` where (`l`.`SUSERID` = `p`.`SUSERID`)))) and (`l`.`SLOGINTIME` = (select max(`lg`.`SLOGINTIME`) from `sa_loginlog` `lg` where (`lg`.`SUSERID` = `l`.`SUSERID`)))) order by `l`.`SLOGINTIME` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-01 16:07:23
