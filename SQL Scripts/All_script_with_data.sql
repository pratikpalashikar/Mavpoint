-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: mavadvisor_dev
-- ------------------------------------------------------
-- Server version	5.5.48

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
-- Table structure for table `advising_task`
--
DROP DATABASE IF EXISTS mavadvisor_dev;
CREATE DATABASE mavadvisor_dev;
USE mavadvisor_dev;


DROP TABLE IF EXISTS `advising_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advising_task` (
  `id` int(11) NOT NULL,
  `advisor_id` int(11) NOT NULL,
  `description` varchar(100) NOT NULL,
  `duration` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_advisor_id_idx` (`advisor_id`),
  CONSTRAINT `fk_advisor_id` FOREIGN KEY (`advisor_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advising_task`
--

LOCK TABLES `advising_task` WRITE;
/*!40000 ALTER TABLE `advising_task` DISABLE KEYS */;
INSERT INTO `advising_task` VALUES (1,6,'Academic Suggestions',30),(2,6,'Course Selection',30),(3,6,'Swap Classes',15),(4,6,'Drop Classes',15),(5,4,'General Advising',30),(6,4,'Graduation Review',30);
/*!40000 ALTER TABLE `advising_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advisor`
--

DROP TABLE IF EXISTS `advisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advisor` (
  `id` int(11) NOT NULL,
  `email_notify` tinyint(1) DEFAULT NULL,
  `assigned_students` tinyint(1) DEFAULT NULL,
  `start_student` varchar(1) DEFAULT NULL,
  `end_student` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_advisor_user` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advisor`
--

LOCK TABLES `advisor` WRITE;
/*!40000 ALTER TABLE `advisor` DISABLE KEYS */;
INSERT INTO `advisor` VALUES (2,1,0,'',''),(4,1,0,'',''),(6,1,0,'',''),(15,1,0,'',''),(25,1,0,'',''),(30,1,0,NULL,NULL);
/*!40000 ALTER TABLE `advisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `advisor_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `student_notes` varchar(255) DEFAULT NULL,
  `advisor_notes` varchar(255) DEFAULT NULL,
  `status_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_user_id_idx` (`student_id`),
  KEY `fk_advisor_user_id_idx` (`advisor_id`),
  KEY `fk_appointment_status` (`status_id`),
  KEY `fk_task_id` (`task_id`),
  CONSTRAINT `fk_advisor_user_id` FOREIGN KEY (`advisor_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_appointment_status` FOREIGN KEY (`status_id`) REFERENCES `appointment_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_user_id` FOREIGN KEY (`student_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_id` FOREIGN KEY (`task_id`) REFERENCES `advising_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,6,8,'2016-04-13 10:00:00','2016-04-13 22:30:00','\nCANCELED BY STUDENT: No reason\nCANCELED BY STUDENT: no reason','',2,1),(2,6,16,'2016-04-13 10:00:00','2016-04-13 22:30:00','hello','',0,2),(3,6,17,'2016-04-20 10:00:00','2016-04-20 23:00:00','\nCANCELED BY STUDENT: No cancel further','',2,1),(23,6,36,'2016-05-04 11:30:00','2016-05-04 00:00:00','ok this is it','Rescheduled Appointment by Advisor',2,1),(24,6,32,'2016-05-02 16:30:00','2016-05-02 18:00:00','Dance Basanti','',0,3);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment_status`
--

DROP TABLE IF EXISTS `appointment_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment_status` (
  `id` int(11) NOT NULL,
  `description` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `description` (`description`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment_status`
--

LOCK TABLES `appointment_status` WRITE;
/*!40000 ALTER TABLE `appointment_status` DISABLE KEYS */;
INSERT INTO `appointment_status` VALUES (2,'Canceled'),(1,'Complete'),(0,'Scheduled');
/*!40000 ALTER TABLE `appointment_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollment_status_type`
--

DROP TABLE IF EXISTS `enrollment_status_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enrollment_status_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `description` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollment_status_type`
--

LOCK TABLES `enrollment_status_type` WRITE;
/*!40000 ALTER TABLE `enrollment_status_type` DISABLE KEYS */;
INSERT INTO `enrollment_status_type` VALUES (3,'Alumni'),(2,'Current'),(1,'Prospective');
/*!40000 ALTER TABLE `enrollment_status_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(11) NOT NULL,
  `enrollment_status` int(11) NOT NULL,
  `student_type` int(11) NOT NULL,
  PRIMARY KEY (`sid`),
  KEY `fk_enrollment_status` (`enrollment_status`),
  KEY `fk_student_type` (`student_type`),
  KEY `fk_student_user` (`id`),
  CONSTRAINT `fk_enrollment_status` FOREIGN KEY (`enrollment_status`) REFERENCES `enrollment_status_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_type` FOREIGN KEY (`student_type`) REFERENCES `student_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_user` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,1,1,1),(2,7,1,1),(3,9,1,1),(4,13,1,1),(7,19,1,4),(8,36,1,2);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_type`
--

DROP TABLE IF EXISTS `student_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `description` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_type`
--

LOCK TABLES `student_type` WRITE;
/*!40000 ALTER TABLE `student_type` DISABLE KEYS */;
INSERT INTO `student_type` VALUES (3,'Doctorate-CSE'),(9,'Doctorate-EE'),(6,'Doctorate-ME'),(2,'Graduate-CSE'),(8,'Graduate-EE'),(5,'Graduate-ME'),(1,'Undergraduate-CSE'),(7,'Undergraduate-EE'),(4,'Undergraduate-ME');
/*!40000 ALTER TABLE `student_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timeslot`
--

DROP TABLE IF EXISTS `timeslot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timeslot` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `advisor_id` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_advisor_time_slot_idx` (`advisor_id`),
  CONSTRAINT `fk_advisor_time_slot` FOREIGN KEY (`advisor_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timeslot`
--

LOCK TABLES `timeslot` WRITE;
/*!40000 ALTER TABLE `timeslot` DISABLE KEYS */;
INSERT INTO `timeslot` VALUES (30,6,'2016-04-15 09:00:00','2016-04-15 12:00:00'),(31,6,'2016-04-18 09:00:00','2016-04-18 12:00:00'),(32,6,'2016-04-19 09:00:00','2016-04-19 12:00:00'),(33,6,'2016-04-20 09:00:00','2016-04-20 12:00:00'),(34,6,'2016-04-21 09:00:00','2016-04-21 12:00:00'),(35,6,'2016-04-22 09:00:00','2016-04-22 12:00:00'),(36,6,'2016-04-25 09:00:00','2016-04-25 12:00:00'),(37,6,'2016-04-26 09:00:00','2016-04-26 12:00:00'),(38,6,'2016-04-27 09:00:00','2016-04-27 12:00:00'),(39,6,'2016-04-28 09:00:00','2016-04-28 12:00:00'),(40,6,'2016-05-02 16:30:00','2016-05-02 19:00:00'),(41,6,'2016-05-03 07:30:00','2016-05-03 08:30:00'),(42,6,'2016-05-04 16:30:00','2016-05-04 18:30:00'),(43,30,'2016-05-02 08:00:00','2016-05-02 00:00:00'),(44,30,'2016-05-03 08:00:00','2016-05-03 00:00:00');
/*!40000 ALTER TABLE `timeslot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  `password_hash` varchar(50) NOT NULL,
  `password_salt` varchar(50) NOT NULL,
  `password_expiration` date NOT NULL,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `uta_id` varchar(25) DEFAULT NULL,
  `user_type` int(11) NOT NULL,
  `user_status` int(11) NOT NULL,
  `telephone` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `id` (`id`),
  KEY `fk_user_status` (`user_status`),
  KEY `fk_mav_user_type_idx` (`user_type`),
  CONSTRAINT `fk_mav_user_type` FOREIGN KEY (`user_type`) REFERENCES `user_type` (`user_type_id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_user_status` FOREIGN KEY (`user_status`) REFERENCES `user_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (15,'abc@gmail.com','abc','Zf0+GX09TWO+vxtyPSF03r+wQpmANa8qwSKdivWrF2I=','lHokitKEvcWd/YNAVnubAw==','2016-10-07','Pratik','Palashikar','6846',2,0,'6822487783'),(13,'abhinav@uta.edu','hu','NAsWAVcKwbFrOXnt4EC7C061bvHJi/0EW86FHJs+H/A=','n1W4aQ+lgou9Cwc7MDtLSA==','2016-10-07','Pratik','Palashikar','545646546',3,1,'6822487783'),(1,'admin@mavs.uta.edu','admin','h8pZ8TVpdqY3muqGXTjRCyIiROWNiphv4lKKuxHwXJY=','Sdhz6ypNg1cKf0cBVsjNZg==','2016-01-01','System','Admin','1001999999',1,1,NULL),(25,'advisor@gmail.com','advisor','n1W7F9MOdvbdQ5BIEn2ieLXldIL4LOpS5g4e1dYEi9o=','r7sSdJhLGGMwwCa/a+FOEA==','2016-10-07','advisor','advisor','1001227244',2,0,'6822487783'),(32,'ashwinikardile3@gmail.com','Ashwini','rmTrq9zLEBvADl+U0ce3rHlYn5IPiRHySyFUKbeF23I=','JYsiX0Dw7L9qSfb9zTFV4Q==','2016-11-02','Ashwini','Ashwini','',3,1,'6822487783'),(2,'dkung@mavs.uta.edu','dkung','RT7alRXi1mc2TC0/iLyl22i+herJDKunD/zVsv3E8DY=','eK0PkdrGz4SFB6UrMybw7Q==','2016-01-01','David','Kung','1001001234',2,0,NULL),(9,'karan@gmail.com','karan','AW13c8Nal4DrcBR9rSuo+6kGq18JPJcQyt45qBwdjYw=','lXh8IxHaGBxgNdJvoLuplA==','2016-10-07','Pratik','Palashikar',NULL,3,1,'6822487783'),(21,'KayKaru@gmail.com','KayKaru','9RxqgXHiQsf8Vhbb5wdFt1U4m+zI1p4eQfHF/Es7xOQ=','ktj+ytuaZdLaQnIXf7vWTQ==','2016-10-07','KayKaru','KayKaru','',3,1,'6822487783'),(36,'kdshah@mavs.uta.edu','Naruto','/y11vJ/nDk6u9I9JdYmYauCIwAuPdSvWXZwxjIxmJLc=','Ro3vNjVchEVyDIQnyT01WQ==','2016-11-05','Naruto','Naruto','1001227244',3,1,'6822487783'),(19,'kk@gmail.com','kk','COJxFq6q/bBHsfx6MvoBOaO6ybsHaFfWEJASFjaAuf4=','KDVeIcYIlQqz4UXoMyR7MQ==','2016-10-07','kk','kk','',3,1,'6822487783'),(28,'kondaabhinav1@gmail.com','abhinav1','yR5eU+gVEtQDwuE93zp6n2Wu4qSeip+gmJbXGz1swgA=','5x7ATusZiA4JSd95wg5m/g==','2016-10-30','abhinav1','K','',3,1,'888888'),(16,'kondaabhinav@gmail.com','abhinav','SRzOcA5kfCyKn05nHEegOP0ml7iQJwEzqyuR1AO3/S4=','Z6yQu9C6t6H1YNK41gC9eA==','2016-10-07','Abhinav','Konda',NULL,3,1,'6822487783'),(17,'manish@gmail.com','manish','f5lyCId6Pke4t0BF4IFTqCPnoYrqp9QpcEM00JAm4LY=','DdsvX/g8C8XAWUSdTjEc7g==','2016-10-07','manish','kumar',NULL,3,1,'6822487783'),(30,'pratik.palashikar@mavs.uta.edu','Tushar','8Udi77R8/WHKbQaxhCmMVizRVJ91LF8CBYr1LZspnuI=','YbyUpyF9Y7e9NmdgdG59DA==','2016-11-02','Tushar','Sonawane','1001227244',2,1,'6822487783'),(34,'pratik@cingo.me','pikya','OHRXbpOmobFVg0/sRMsWYQbkvekuAPpJg835WSkZXVI=','XgLV0uuQ0y79jjfGnVSMXw==','2016-11-04','Pikya','Pikya','',3,1,'6822487783'),(8,'pratik@gmail.com','pratik','H/tLJPSzlEvP/GGhHmnuTgENognDSATTJntdS4EJM14=','X2hjcwz7zXqBli+16UhhWQ==','2016-10-07','Pratik','Palashikar','1001227244',3,1,NULL),(33,'pratikpalashikar@cingo.com','Mogli','0NXOwVLRJRa+/OIiktcSQBcxtZsM+TpTZpihi8KIEVc=','32DdCzK69gsebZ1VKrYDdQ==','2016-11-03','Mogli','Mogli','1001227244',3,1,'6822487783'),(5,'pratikpalashikar@gmail.com','pratikpalashikar@gmail.com','4N3EVx+Nxf5HLEzUyGwhow2a5IcDbS0xP/UeymOWs2M=','h35TbLyUMWpUHKZ9MmxskA==','2016-10-07','Pratik','Palashikar','1001227244',1,1,NULL),(29,'pratikpalashikar@mavs.uta.edu','Chiku','hpj8aszeC7jxpShRTiX5/PLANXiA4n2oBhS+TsQWNpc=','821hmR+XDH0SFUq0J4Im4g==','2016-11-01','Chiku','Chiku','1001227244',3,1,'6822487783'),(4,'relmasri@mavs.uta.edu','relmasri','FmDO5Gt+2Ml9JOVkBw5vGSeqmWvSF07ixL7D2hjE4YE=','XkggsLkcb38J1/fVEKqUdw==','2016-01-01','Ramirez','Elmasri','1001444444',2,0,NULL),(6,'sajibdatta@gmail.com','Sajib','GKiaAGdfbrn1ccSrBUAdjStlsxwgHPNa3q7i3nJYiJ4=','rdJwnniy4ENPgKNoAm+Hfw==','2016-10-07','Sajib','Datta','1001227244',2,1,NULL),(23,'siddesh@mavs.uta.edu','siddesh','w3VEmzwzUN13A1bQ7i12LUlKjnowxtLB6U/8KChDXzo=','qRVKlbMkUK6+zSj5C2wrHg==','2016-10-07','siddesh','siddesh','654654',3,1,'6822487783'),(18,'test@gmail.com','test','cAAbGeDCr9HUMuj5AHFZRmoEYtll3jR1e1dOq9t83gk=','hLsbdI6qyktceQ5uhHiKvg==','2016-10-07','Pratik','Palashikar',NULL,3,1,'6822487783'),(24,'testsub@gmail.com','testsub','ya3NJ8BBn0zxkbRZ5+Dmq/Ywf18AoAtNTexPXPG9yN0=','iG7u1VOVUFbqVg7Nt3zAnA==','2016-10-07','testsub','testsub','',3,1,'123456789'),(3,'tprescott@mavs.uta.edu','tprescott','NlSnpZZR5EKmVXYJMzL+pdGwSsmv5+czi3GXecUMOvA=','R+Z/6aNNRWjxD27lBQ2sMg==','2016-01-01','Travis','Prescott','1001123456',3,1,NULL),(12,'vo@gmail.com','vo','ew2BGF4yC8bO55zI2crmfsNeE39KZznjc4Y80+4vhrM=','oSDbBhM609UNHDynH8nJTg==','2016-10-07','Pratik','Palashikar',NULL,3,1,'6822487783');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_session`
--

DROP TABLE IF EXISTS `user_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_session` (
  `token` varchar(32) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`token`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_session`
--

LOCK TABLES `user_session` WRITE;
/*!40000 ALTER TABLE `user_session` DISABLE KEYS */;
INSERT INTO `user_session` VALUES ('DA98D9853EA107A576028E8F683D9C6D',16),('F38429F1020D038416394393B07C541A',24);
/*!40000 ALTER TABLE `user_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_status`
--

DROP TABLE IF EXISTS `user_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_status` (
  `id` int(11) NOT NULL,
  `description` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `description` (`description`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_status`
--

LOCK TABLES `user_status` WRITE;
/*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
INSERT INTO `user_status` VALUES (1,'Active'),(0,'Inactive');
/*!40000 ALTER TABLE `user_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_type` (
  `user_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_class` varchar(100) NOT NULL,
  PRIMARY KEY (`user_type_id`),
  UNIQUE KEY `user_class` (`user_class`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (1,'edu.uta.cse.group9.model.Admin'),(2,'edu.uta.cse.group9.model.Advisor'),(3,'edu.uta.cse.group9.model.Student');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-06 15:43:52
