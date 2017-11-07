-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: oucfreetalk
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `accountaccess`
--

DROP TABLE IF EXISTS `accountaccess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accountaccess` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentid` varchar(11) NOT NULL,
  `classid` int(11) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IX_accountaccess` (`id`),
  KEY `studentid` (`studentid`),
  CONSTRAINT `FK_accountaccess_students` FOREIGN KEY (`studentid`) REFERENCES `students` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accountaccess`
--

LOCK TABLES `accountaccess` WRITE;
/*!40000 ALTER TABLE `accountaccess` DISABLE KEYS */;
INSERT INTO `accountaccess` VALUES (5,'13020031069',-2,'2017-05-18 14:17:31'),(6,'13020031069',-1,'2017-05-18 14:28:09');
/*!40000 ALTER TABLE `accountaccess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apptype` int(11) NOT NULL,
  `stuid` varchar(11) NOT NULL,
  `groupid` int(11) DEFAULT NULL,
  `groupname` longtext,
  `state` int(11) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blogreply`
--

DROP TABLE IF EXISTS `blogreply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blogreply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner` varchar(11) NOT NULL,
  `replyto` varchar(11) NOT NULL,
  `createtime` datetime NOT NULL,
  `ownlocation` int(11) NOT NULL,
  `body` longtext NOT NULL,
  `state` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ownlocation` (`ownlocation`),
  KEY `owner` (`owner`),
  KEY `replyto` (`replyto`),
  CONSTRAINT `FK_blogreply_comment` FOREIGN KEY (`ownlocation`) REFERENCES `comment` (`id`),
  CONSTRAINT `FK_blogreply_students` FOREIGN KEY (`owner`) REFERENCES `students` (`id`),
  CONSTRAINT `FK_blogreply_students1` FOREIGN KEY (`replyto`) REFERENCES `students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blogreply`
--

LOCK TABLES `blogreply` WRITE;
/*!40000 ALTER TABLE `blogreply` DISABLE KEYS */;
/*!40000 ALTER TABLE `blogreply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blogs`
--

DROP TABLE IF EXISTS `blogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blogs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `body` longtext NOT NULL,
  `createtime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `owner` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `owner` (`owner`),
  CONSTRAINT `FK_blogs_students` FOREIGN KEY (`owner`) REFERENCES `students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blogs`
--

LOCK TABLES `blogs` WRITE;
/*!40000 ALTER TABLE `blogs` DISABLE KEYS */;
/*!40000 ALTER TABLE `blogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `burieddata`
--

DROP TABLE IF EXISTS `burieddata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `burieddata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stuid` varchar(11) NOT NULL,
  `actionid` int(11) NOT NULL,
  `resultid` int(11) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `burieddata`
--

LOCK TABLES `burieddata` WRITE;
/*!40000 ALTER TABLE `burieddata` DISABLE KEYS */;
INSERT INTO `burieddata` VALUES (1,'14020031094',4,0,'2017-05-18 10:39:19'),(2,'14020031094',5,0,'2017-05-18 10:39:22'),(3,'           ',4,0,'2017-05-18 10:52:28'),(4,'           ',4,0,'2017-05-18 10:52:28'),(5,'           ',4,0,'2017-05-18 10:52:28'),(6,'           ',4,0,'2017-05-18 10:52:28'),(7,'           ',4,0,'2017-05-18 10:52:28'),(8,'13020031001',0,0,'2017-05-18 10:52:50'),(9,'14020031094',0,0,'2017-05-18 10:53:09'),(10,'           ',4,0,'2017-05-18 10:55:06'),(11,'13020031002',0,0,'2017-05-18 10:55:23'),(12,'13020031002',5,0,'2017-05-18 10:55:32'),(13,'13020031001',0,0,'2017-05-18 11:56:04'),(14,'13020031001',0,0,'2017-05-18 11:56:04'),(15,'13020031069',0,0,'2017-05-18 13:46:58'),(16,'13020031069',0,0,'2017-05-18 13:59:04'),(17,'13020031069',0,0,'2017-05-18 14:21:30');
/*!40000 ALTER TABLE `burieddata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner` varchar(11) NOT NULL,
  `createtime` datetime NOT NULL,
  `body` longtext NOT NULL,
  `ownlocation` int(11) NOT NULL,
  `state` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ownlocation` (`ownlocation`),
  KEY `owner` (`owner`),
  CONSTRAINT `FK_comment_blogs` FOREIGN KEY (`ownlocation`) REFERENCES `blogs` (`id`),
  CONSTRAINT `FK_comment_students` FOREIGN KEY (`owner`) REFERENCES `students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friends` (
  `focus` varchar(11) NOT NULL,
  `befocus` varchar(11) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`befocus`,`focus`),
  KEY `focus` (`focus`),
  CONSTRAINT `FK_friends_students` FOREIGN KEY (`focus`) REFERENCES `students` (`id`),
  CONSTRAINT `FK_friends_students1` FOREIGN KEY (`befocus`) REFERENCES `students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupaccess`
--

DROP TABLE IF EXISTS `groupaccess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groupaccess` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stuid` varchar(11) NOT NULL,
  `groupid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupaccess`
--

LOCK TABLES `groupaccess` WRITE;
/*!40000 ALTER TABLE `groupaccess` DISABLE KEYS */;
/*!40000 ALTER TABLE `groupaccess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupname` varchar(50) NOT NULL,
  `createtime` datetime NOT NULL,
  `groupintroduction` longtext NOT NULL,
  `ico` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lostafound`
--

DROP TABLE IF EXISTS `lostafound`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lostafound` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stuid` varchar(11) NOT NULL,
  `area` varchar(100) NOT NULL,
  `secarea` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `createtime` datetime NOT NULL,
  `state` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lostafound`
--

LOCK TABLES `lostafound` WRITE;
/*!40000 ALTER TABLE `lostafound` DISABLE KEYS */;
INSERT INTO `lostafound` VALUES (1,'14020031094','东区','水房','王*瑞','2017-05-17 12:19:40','\0'),(2,'13020031066','东区','水房','刘*峰','2017-05-17 13:20:49','\0'),(3,'13020031070','东区','浴室','刘*峰','2017-05-17 15:00:11',''),(4,'13020031066','东区','水房','刘*峰','2017-05-18 09:33:12','\0'),(5,'13020031066','东区','水房','刘*峰','2017-05-18 10:27:46',''),(6,'14020031094','东区','水房','王*瑞','2017-05-18 10:39:18','\0'),(7,'13020031001','东区','餐厅','刘*峰','2017-05-18 10:52:28',''),(8,'13020031001','东区','餐厅','刘*峰','2017-05-18 10:52:28',''),(9,'13020031001','东区','餐厅','刘*峰','2017-05-18 10:52:28',''),(10,'13020031001','东区','餐厅','刘*峰','2017-05-18 10:52:28',''),(11,'13020031001','东区','餐厅','刘*峰','2017-05-18 10:52:28',''),(12,'13020031002','东区','浴室','刘*峰','2017-05-18 10:55:06','\0');
/*!40000 ALTER TABLE `lostafound` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notices`
--

DROP TABLE IF EXISTS `notices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `noticeclass` int(11) NOT NULL,
  `postid` int(11) DEFAULT NULL,
  `stuid` varchar(11) NOT NULL,
  `replyid` int(11) DEFAULT NULL,
  `replystuid` varchar(11) NOT NULL,
  `state` bit(1) NOT NULL,
  `commentsid` int(11) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notices`
--

LOCK TABLES `notices` WRITE;
/*!40000 ALTER TABLE `notices` DISABLE KEYS */;
/*!40000 ALTER TABLE `notices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postc`
--

DROP TABLE IF EXISTS `postc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `postc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `postlocation` int(11) NOT NULL,
  `owner` varchar(11) NOT NULL,
  `createtime` datetime NOT NULL,
  `body` longtext NOT NULL,
  `ownpost` int(11) NOT NULL,
  `state` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ownpost` (`ownpost`),
  KEY `owner` (`owner`),
  CONSTRAINT `FK_postc_posts` FOREIGN KEY (`ownpost`) REFERENCES `posts` (`id`),
  CONSTRAINT `FK_postc_students` FOREIGN KEY (`owner`) REFERENCES `students` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postc`
--

LOCK TABLES `postc` WRITE;
/*!40000 ALTER TABLE `postc` DISABLE KEYS */;
INSERT INTO `postc` VALUES (1,2,'123123123','2017-11-07 15:59:16','suibianhuifu',1,''),(3,3,'13020031001','2017-11-07 16:04:05','fdgdfgd',1,''),(5,4,'13020031001','2017-11-07 16:06:11','fdfghfghfghgdfgd',1,'');
/*!40000 ALTER TABLE `postc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postclass`
--

DROP TABLE IF EXISTS `postclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `postclass` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `state` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postclass`
--

LOCK TABLES `postclass` WRITE;
/*!40000 ALTER TABLE `postclass` DISABLE KEYS */;
INSERT INTO `postclass` VALUES (1,'中国海洋大学','');
/*!40000 ALTER TABLE `postclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postreply`
--

DROP TABLE IF EXISTS `postreply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `postreply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createtime` datetime NOT NULL,
  `owner` varchar(11) NOT NULL,
  `replyto` varchar(11) NOT NULL,
  `ownlocation` int(11) NOT NULL,
  `contenttext` longtext NOT NULL,
  `state` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ownlocation` (`ownlocation`),
  KEY `owner` (`owner`),
  KEY `replyto` (`replyto`),
  CONSTRAINT `FK_postreply_postc` FOREIGN KEY (`ownlocation`) REFERENCES `postc` (`id`),
  CONSTRAINT `FK_postreply_students` FOREIGN KEY (`owner`) REFERENCES `students` (`id`),
  CONSTRAINT `FK_postreply_students1` FOREIGN KEY (`replyto`) REFERENCES `students` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postreply`
--

LOCK TABLES `postreply` WRITE;
/*!40000 ALTER TABLE `postreply` DISABLE KEYS */;
INSERT INTO `postreply` VALUES (8,'2017-11-07 16:15:55','14020031127','123123123',1,'hahahaha',''),(9,'2017-11-07 16:25:07','14020031127','123123123',1,'hahahaha','');
/*!40000 ALTER TABLE `postreply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `createtime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `realbody` int(11) NOT NULL,
  `body` int(11) NOT NULL,
  `owner` varchar(11) NOT NULL,
  `contenttext` longtext NOT NULL,
  `ownclass` int(11) NOT NULL,
  `state` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ownclass` (`ownclass`),
  KEY `owner` (`owner`),
  CONSTRAINT `FK_posts_students` FOREIGN KEY (`ownclass`) REFERENCES `postclass` (`id`),
  CONSTRAINT `FK_posts_students1` FOREIGN KEY (`owner`) REFERENCES `students` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'test','2017-11-06 20:54:42','2017-11-07 16:25:07',4,4,'14020031127','123456',1,NULL),(2,'test','2017-11-06 21:01:33','2017-11-06 21:01:33',1,1,'14020031127','test',1,NULL),(3,'test','2017-11-06 21:13:15','2017-11-06 21:13:15',1,1,'123123123','test',1,NULL),(4,'nbhaha','2017-11-06 21:55:33','2017-11-06 21:55:33',1,1,'13020031001','åæå¤§å¸',1,NULL);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `students` (
  `id` varchar(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `ifname` bit(1) NOT NULL,
  `nikename` varchar(50) NOT NULL,
  `pic` longtext NOT NULL,
  `password` varchar(70) NOT NULL,
  `sex` bit(1) NOT NULL,
  `ifsex` bit(1) NOT NULL,
  `birth` varchar(10) NOT NULL,
  `ifbirth` bit(1) NOT NULL,
  `year` varchar(4) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `ifemail` bit(1) NOT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  `ifmobile` bit(1) NOT NULL,
  `exp` int(11) NOT NULL DEFAULT '0',
  `family` varchar(100) DEFAULT NULL,
  `introduction` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES ('123123123','','\0','zjk','pic','123456','\0','\0','1996-01-01','\0','1996',NULL,'\0',NULL,'\0',0,NULL,'海大学生'),('123321123','','\0','zjk','pic','123456','\0','\0','1996-01-01','\0','1996',NULL,'\0',NULL,'\0',0,NULL,'海大学生'),('123456','123456','\0','123456','pic','123456','\0','\0','1977-7-7','\0','2014',NULL,'\0',NULL,'\0',0,NULL,'暂无介绍'),('123456789','123456789','\0','123456789','','123456','\0','\0','1996-6-30','\0','2014',NULL,'\0',NULL,'\0',0,NULL,'海大学生'),('13020031001',' ','\0','刘天峰','/img/1.png','123456','','\0','2017-05-18','\0','2017',NULL,'\0',NULL,'\0',0,NULL,'海大学生'),('13020031002',' ','\0','刘天峰','/img/1.png','1000:mUN6+Ekox7yTb9Qrgf73/XFkRixQ7LhF:CRiyDk7eUwoWyIefZmlMxLG3rtTs86PS','','\0','2017-05-18','\0','2017',NULL,'\0',NULL,'\0',0,NULL,'海大学生'),('13020031066',' ','\0','kk','/img/1.png','1000:wMbpskhnDNWQnkZiT06UDqQL4T1jMD62:scp6mWF7td/ahTb3NJY1U73y7y4Ehj2v','\0','\0','2017-05-17','\0','2017',NULL,'\0',NULL,'\0',0,NULL,'hello'),('13020031067',' ','\0','呜呜呜','/img/1.png','1000:fuIdYX3z1mvSs+bg3H5Eo6ORBiKBOS04:K2txJZru9DDsXiCa9HOE9ZzRh8dr6dkW','','\0','2017-05-18','\0','2017',NULL,'\0',NULL,'\0',0,NULL,'全球全球'),('13020031069',' ','\0','刘天峰','/img/1.png','1000:u0un2T/7NNXjkGUclLuwFY9cOAV8ocjo:q+jEcF/phUaTx3sv9rRFuzwnaaVkAAh/','','\0','2017-05-18','\0','2017',NULL,'\0',NULL,'\0',0,NULL,'海大学生\n\n'),('13020031070',' ','\0','刘天峰','/img/1.png','1000:0dshqAD0o0UAWCca3fuPdWOY0YDz5Usy:X+z8eh14WNfLMOkvdNvuP32pimJFpL/W','','\0','2017-05-17','\0','2017',NULL,'\0',NULL,'\0',0,NULL,'一个大四学生'),('13020031099',' ','\0','刘天峰','/img/1.png','1000:BUH1PYefIJaErUm3L4ryUW333wXwToa5:E/wakGKyqV3+pQfqu8puj4D1KN0+QD5R','','\0','2017-05-17','\0','2017',NULL,'\0',NULL,'\0',0,NULL,'一名大四学生'),('14020031090',' ','\0','黑猫回收者','/img/1.png','1000:Tm64cCrTMmuaAhzolYcQpTsAsm7OgXSc:qC56B7S10/IsFF1TlC5JUAvJR2GlQjev','\0','\0','2017-05-17','\0','2017',NULL,'\0',NULL,'\0',0,NULL,'啊三大三大洒洒打啊'),('14020031091',' ','\0','黑貓','/img/1.png','1000:Jg3cz76WWNJx+LQd4Pe+yWLYPCOw9wE+:jhELsJoccioS87GHlUKCx8uapF4CMPei','\0','\0','2017-05-17','\0','2017',NULL,'\0',NULL,'\0',0,NULL,'啊三大洒洒的'),('14020031094',' ','\0','黑猫回收者','/img/1.png','1000:Dv6Oemq5FG5MuquvMS711lHA8Ly15RdZ:vwVToO2JhxWxLahuGzjDe7xmgfXBecMo','\0','\0','2017-05-17','\0','2017',NULL,'\0',NULL,'\0',0,NULL,'2000-2-3T11:57:59'),('14020031127','zjk','','Zzzzzzzjk','pic','123456','\0','','1996-06-30','\0','2014',NULL,'',NULL,'',0,'test','test');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stumessage`
--

DROP TABLE IF EXISTS `stumessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stumessage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `postid` varchar(11) NOT NULL,
  `receiveid` varchar(11) NOT NULL,
  `state` bit(1) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stumessage`
--

LOCK TABLES `stumessage` WRITE;
/*!40000 ALTER TABLE `stumessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `stumessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sysdiagrams`
--

DROP TABLE IF EXISTS `sysdiagrams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sysdiagrams` (
  `name` varchar(128) NOT NULL,
  `principal_id` int(11) NOT NULL,
  `diagram_id` int(11) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `definition` longblob,
  PRIMARY KEY (`diagram_id`),
  UNIQUE KEY `UK_principal_name` (`principal_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sysdiagrams`
--

LOCK TABLES `sysdiagrams` WRITE;
/*!40000 ALTER TABLE `sysdiagrams` DISABLE KEYS */;
/*!40000 ALTER TABLE `sysdiagrams` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-07 22:59:32
