-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: weiyunmei
-- ------------------------------------------------------
-- Server version	5.7.11

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
-- Table structure for table `advertisement`
--

DROP TABLE IF EXISTS `advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisement` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `icon` varchar(200) DEFAULT NULL COMMENT '图标',
  `simple_descript` varchar(200) DEFAULT NULL COMMENT '简述',
  `type` varchar(50) DEFAULT NULL COMMENT '任务类型',
  `money_count` int(11) DEFAULT NULL COMMENT '总奖金',
  `money` int(11) DEFAULT NULL COMMENT '任务金额',
  `company_proportion` int(11) DEFAULT NULL COMMENT '分成比例，公司',
  `user_proportion` int(11) DEFAULT NULL COMMENT '分成比例 - 用户',
  `start_time` bigint(20) DEFAULT NULL COMMENT '任务开始时间',
  `end_time` bigint(20) DEFAULT NULL COMMENT '任务结束时间',
  `release_enterprise` varchar(50) DEFAULT NULL COMMENT '发布企业',
  `execution_flow` text COMMENT '执行流程',
  `details` text COMMENT '详细描述',
  `execution_address` varchar(200) DEFAULT NULL COMMENT '任务执行地址',
  `is_examine` int(11) DEFAULT '1' COMMENT '是否需要人工审核 0 不需要/1需要',
  `status` int(11) DEFAULT NULL COMMENT '广告状态',
  `create_time` bigint(20) DEFAULT NULL COMMENT '广告创建时间',
  PRIMARY KEY (`id`),
  KEY `id_idx` (`type`),
  KEY `enterprise_id_key_idx` (`release_enterprise`),
  CONSTRAINT `advertisement_type_key` FOREIGN KEY (`type`) REFERENCES `advertisement_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `enterprise_id_key` FOREIGN KEY (`release_enterprise`) REFERENCES `enterprise` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisement`
--

LOCK TABLES `advertisement` WRITE;
/*!40000 ALTER TABLE `advertisement` DISABLE KEYS */;
/*!40000 ALTER TABLE `advertisement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advertisement_data`
--

DROP TABLE IF EXISTS `advertisement_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisement_data` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `label` varchar(10) DEFAULT NULL COMMENT '数据label',
  `name` varchar(200) DEFAULT NULL COMMENT '数据名称',
  `type` varchar(45) DEFAULT NULL COMMENT '数据类型',
  `advertisement_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_idx` (`advertisement_id`),
  KEY `type_key_idx` (`type`),
  CONSTRAINT `advertisement_id_key` FOREIGN KEY (`advertisement_id`) REFERENCES `advertisement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `type_key` FOREIGN KEY (`type`) REFERENCES `advertisement_data_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisement_data`
--

LOCK TABLES `advertisement_data` WRITE;
/*!40000 ALTER TABLE `advertisement_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `advertisement_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advertisement_data_type`
--

DROP TABLE IF EXISTS `advertisement_data_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisement_data_type` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `name` varchar(45) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisement_data_type`
--

LOCK TABLES `advertisement_data_type` WRITE;
/*!40000 ALTER TABLE `advertisement_data_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `advertisement_data_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advertisement_type`
--

DROP TABLE IF EXISTS `advertisement_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisement_type` (
  `id` varchar(50) NOT NULL COMMENT '广告创建时间',
  `name` varchar(45) DEFAULT NULL COMMENT '类型名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisement_type`
--

LOCK TABLES `advertisement_type` WRITE;
/*!40000 ALTER TABLE `advertisement_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `advertisement_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enterprise`
--

DROP TABLE IF EXISTS `enterprise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enterprise` (
  `id` varchar(50) NOT NULL,
  `login_name` varchar(45) DEFAULT NULL COMMENT '登录账号',
  `password` varchar(45) DEFAULT NULL COMMENT '登录密码',
  `name` varchar(45) DEFAULT NULL COMMENT '企业名称',
  `email` varchar(45) DEFAULT NULL COMMENT '企业邮箱',
  `contacts` varchar(45) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(18) DEFAULT NULL COMMENT '联系电话',
  `payment_card` varchar(45) DEFAULT NULL COMMENT '银行卡号',
  `alipay` varchar(45) DEFAULT NULL COMMENT '支付宝',
  `business_license` varchar(200) DEFAULT NULL COMMENT '营业执照',
  `identity_card_face` varchar(200) DEFAULT NULL COMMENT '身份证',
  `identity_card_back` varchar(200) DEFAULT NULL COMMENT '身份证，背面',
  `money` bigint(20) DEFAULT NULL COMMENT '账户余额',
  `frozen_money` bigint(20) DEFAULT NULL COMMENT '冻结金额',
  `consume_money` bigint(20) DEFAULT NULL COMMENT '历史消费金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enterprise`
--

LOCK TABLES `enterprise` WRITE;
/*!40000 ALTER TABLE `enterprise` DISABLE KEYS */;
/*!40000 ALTER TABLE `enterprise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enterprise_consume_log`
--

DROP TABLE IF EXISTS `enterprise_consume_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enterprise_consume_log` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '扣款时间',
  `money` bigint(20) DEFAULT NULL COMMENT '扣款金额',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注信息',
  `enterprise_id` varchar(50) DEFAULT NULL COMMENT '企业id',
  `advertisement_id` varchar(50) DEFAULT NULL COMMENT '广告id',
  PRIMARY KEY (`id`),
  KEY `enterprise_consume_log_enterprise_key_idx` (`enterprise_id`),
  KEY `enterprise_consume_log_advertisement_key_idx` (`advertisement_id`),
  CONSTRAINT `enterprise_consume_log_advertisement_key` FOREIGN KEY (`advertisement_id`) REFERENCES `advertisement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `enterprise_consume_log_enterprise_key` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enterprise_consume_log`
--

LOCK TABLES `enterprise_consume_log` WRITE;
/*!40000 ALTER TABLE `enterprise_consume_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `enterprise_consume_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enterprise_recharge_log`
--

DROP TABLE IF EXISTS `enterprise_recharge_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enterprise_recharge_log` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '充值时间',
  `money` bigint(20) DEFAULT NULL COMMENT '充值金额',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注信息',
  `enterprise_id` varchar(50) DEFAULT NULL COMMENT '企业id',
  PRIMARY KEY (`id`),
  KEY `enterprise_consume_log_enterprise_key_idx` (`enterprise_id`),
  CONSTRAINT `enterprise_recharge_log_enterprise_key0` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enterprise_recharge_log`
--

LOCK TABLES `enterprise_recharge_log` WRITE;
/*!40000 ALTER TABLE `enterprise_recharge_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `enterprise_recharge_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_admin`
--

DROP TABLE IF EXISTS `site_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `site_admin` (
  `id` varchar(50) NOT NULL COMMENT '管理员id',
  `loginname` varchar(45) DEFAULT NULL COMMENT '登录名',
  `password` varchar(45) DEFAULT NULL COMMENT '登录密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_admin`
--

LOCK TABLES `site_admin` WRITE;
/*!40000 ALTER TABLE `site_admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `site_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL COMMENT '用户id',
  `icon` varchar(200) DEFAULT NULL COMMENT '用户头像',
  `name` varchar(45) DEFAULT NULL COMMENT '用户姓名',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `weichat_id` varchar(45) DEFAULT NULL COMMENT '用户的openID，微信',
  `weichat_name` varchar(45) DEFAULT NULL COMMENT '微信姓名',
  `birthday` bigint(20) DEFAULT NULL COMMENT '生日',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `parentId` varchar(50) DEFAULT NULL COMMENT '上级粉丝',
  `total_money` bigint(20) DEFAULT NULL COMMENT '累计收益',
  `money` bigint(20) DEFAULT NULL COMMENT '账户余额',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `parent_user_key_idx` (`parentId`),
  CONSTRAINT `parent_user_key` FOREIGN KEY (`parentId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_advertisement`
--

DROP TABLE IF EXISTS `user_advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_advertisement` (
  `id` varchar(50) NOT NULL,
  `advertisement_id` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '任务状态',
  `user` varchar(50) DEFAULT NULL COMMENT '所属用户',
  PRIMARY KEY (`id`),
  KEY `user_advertisement_key_idx` (`advertisement_id`),
  KEY `user_advertisement_user_key_idx` (`user`),
  CONSTRAINT `user_advertisement_key` FOREIGN KEY (`advertisement_id`) REFERENCES `advertisement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_advertisement_user_key` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_advertisement`
--

LOCK TABLES `user_advertisement` WRITE;
/*!40000 ALTER TABLE `user_advertisement` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_advertisement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_advertisement_data`
--

DROP TABLE IF EXISTS `user_advertisement_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_advertisement_data` (
  `id` varchar(50) NOT NULL,
  `advertisement_data_id` varchar(50) DEFAULT NULL COMMENT '提交的数据label',
  `user_advertisement_id` varchar(50) DEFAULT NULL COMMENT '数据所属任务',
  `value` varchar(100) DEFAULT NULL COMMENT '提交的数据',
  PRIMARY KEY (`id`),
  KEY `fk_user_advertisement_data_user_advertisement1_idx` (`user_advertisement_id`),
  KEY `fk_user_advertisement_data_advertisement_data1_idx` (`advertisement_data_id`),
  CONSTRAINT `fk_user_advertisement_data_advertisement_data1` FOREIGN KEY (`advertisement_data_id`) REFERENCES `advertisement_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_advertisement_data_user_advertisement1` FOREIGN KEY (`user_advertisement_id`) REFERENCES `user_advertisement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_advertisement_data`
--

LOCK TABLES `user_advertisement_data` WRITE;
/*!40000 ALTER TABLE `user_advertisement_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_advertisement_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_advertisement_log`
--

DROP TABLE IF EXISTS `user_advertisement_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_advertisement_log` (
  `id` varchar(50) NOT NULL,
  `user_advertisement_id` varchar(50) DEFAULT NULL COMMENT '用户任务id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `user_advertisement_execute_log_key_idx` (`user_advertisement_id`),
  CONSTRAINT `user_advertisement_execute_log_key` FOREIGN KEY (`user_advertisement_id`) REFERENCES `user_advertisement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_advertisement_log`
--

LOCK TABLES `user_advertisement_log` WRITE;
/*!40000 ALTER TABLE `user_advertisement_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_advertisement_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_cashing_log`
--

DROP TABLE IF EXISTS `user_cashing_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_cashing_log` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '提现时间',
  `money` bigint(20) DEFAULT NULL COMMENT '提现金额',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注信息',
  `order_no` varchar(45) DEFAULT NULL COMMENT '提现流水号',
  `weichat_order_no` varchar(200) DEFAULT NULL COMMENT '微信订单号',
  `user_id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_recharge_log_copy1_user1_idx` (`user_id`),
  CONSTRAINT `fk_user_recharge_log_copy1_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_cashing_log`
--

LOCK TABLES `user_cashing_log` WRITE;
/*!40000 ALTER TABLE `user_cashing_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_cashing_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_consume_log`
--

DROP TABLE IF EXISTS `user_consume_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_consume_log` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '扣款时间',
  `money` bigint(20) DEFAULT NULL COMMENT '扣款金额',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注信息',
  `user_id` varchar(50) NOT NULL,
  `user_advertisement_id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_consume_log_user1_idx` (`user_id`),
  KEY `fk_user_consume_log_user_advertisement1_idx` (`user_advertisement_id`),
  CONSTRAINT `fk_user_consume_log_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_consume_log_user_advertisement1` FOREIGN KEY (`user_advertisement_id`) REFERENCES `user_advertisement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_consume_log`
--

LOCK TABLES `user_consume_log` WRITE;
/*!40000 ALTER TABLE `user_consume_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_consume_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-07 15:33:43
