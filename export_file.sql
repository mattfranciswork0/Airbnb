-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: airbnb
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookings` (
  `user_id` int(11) NOT NULL,
  `listing_id` int(11) NOT NULL,
  `check_in` date NOT NULL,
  `check_out` date NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `listing_id` (`listing_id`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_authentication` (`id`),
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`listing_id`) REFERENCES `listings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,1,'2017-10-29','2017-10-31');
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `images` (
  `image_path` varchar(255) NOT NULL,
  `caption` tinytext,
  `listing_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES ('storage/emulated/0/Airbnb/1504319451521.png','So much',1),('storage/emulated/0/Airbnb/1504783447344.png','I love u',1),('storage/emulated/0/Airbnb/1506820398665.png',NULL,2),('storage/emulated/0/Airbnb/1508006794064.png',NULL,3),('storage/emulated/0/Airbnb/1508032053293.png',NULL,4),('storage/emulated/0/Airbnb/1508034829985.png',NULL,5),('storage/emulated/0/DCIM/Screenshots/Screenshot_20171031-114850','hi',1),('storage/emulated/0/DCIM/Camera/20171030_100509',NULL,1),('storage/emulated/0/DCIM/Screenshots/Screenshot_20171031-114850','hi',1),('storage/emulated/0/DCIM/Screenshots/Screenshot_20171031-114850','hi',1);
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listings`
--

DROP TABLE IF EXISTS `listings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `property_ownership` varchar(50) NOT NULL,
  `property_type` varchar(50) NOT NULL,
  `total_guest` varchar(50) NOT NULL,
  `total_bedrooms` varchar(50) NOT NULL,
  `total_beds` varchar(50) NOT NULL,
  `total_bathrooms` varchar(50) NOT NULL,
  `bathroom_type` varchar(50) NOT NULL,
  `country` varchar(100) NOT NULL,
  `street` varchar(200) NOT NULL,
  `extra_place_details` varchar(100) DEFAULT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(100) DEFAULT NULL,
  `lng` varchar(200) DEFAULT NULL,
  `lat` varchar(200) DEFAULT NULL,
  `essentials` tinyint(1) DEFAULT '0',
  `internet` tinyint(1) NOT NULL DEFAULT '0',
  `shampoo` tinyint(1) NOT NULL DEFAULT '0',
  `hangers` tinyint(1) NOT NULL DEFAULT '0',
  `tv` tinyint(1) NOT NULL DEFAULT '0',
  `heating` tinyint(1) NOT NULL DEFAULT '0',
  `air_conditioning` tinyint(1) NOT NULL DEFAULT '0',
  `breakfast` tinyint(1) NOT NULL DEFAULT '0',
  `kitchen` tinyint(1) NOT NULL DEFAULT '0',
  `laundry` tinyint(1) NOT NULL DEFAULT '0',
  `parking` tinyint(1) NOT NULL DEFAULT '0',
  `elevator` tinyint(1) NOT NULL DEFAULT '0',
  `pool` tinyint(1) NOT NULL DEFAULT '0',
  `gym` tinyint(1) NOT NULL DEFAULT '0',
  `place_description` text,
  `place_title` varchar(250) DEFAULT NULL,
  `suitable_for_children` tinyint(1) DEFAULT NULL,
  `suitable_for_infants` tinyint(1) DEFAULT NULL,
  `suitable_for_pets` tinyint(1) DEFAULT NULL,
  `smoking_allowed` tinyint(1) DEFAULT NULL,
  `parties_allowed` tinyint(1) DEFAULT NULL,
  `additional_rules` text,
  `listing_length` varchar(50) NOT NULL,
  `arrive_after` varchar(250) DEFAULT NULL,
  `leave_before` varchar(250) DEFAULT NULL,
  `min_stay` varchar(50) NOT NULL,
  `max_stay` varchar(250) DEFAULT NULL,
  `price` varchar(50) NOT NULL,
  `date_listed` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `listings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_authentication` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listings`
--

LOCK TABLES `listings` WRITE;
/*!40000 ALTER TABLE `listings` DISABLE KEYS */;
INSERT INTO `listings` VALUES (1,1,'Shared','House','3','6 bedrooms','5 beds','1 bathroom','Shared','Canada','Robin Hill Circle','','Oakville','ON','-79.7341107','43.3805241',1,0,0,0,0,0,0,0,0,1,1,1,1,1,'Beautiful i','hi\n',1,0,0,0,0,'','11','12am','10pm','2','3','10','2017-11-02'),(2,1,'Shared','House','3','1 bedroom','1 bed','1 bathroom','Shared','Canada','Robin Hill Circle','','Oakville','ON','-79.7341107','43.3805241',1,1,1,1,1,1,1,1,1,1,1,1,1,1,'Beautiful','Cool place',1,1,1,1,1,'','11','12am','10pm','1','','10','2017-10-14'),(3,1,'Shared','House','3','1 bedroom','1 bed','1 bathroom','Shared','Canada','Robin Hill Circle','','Oakville','ON','-79.7341107','43.3805241',1,1,1,1,1,1,1,1,1,1,1,1,1,1,'Beautiful','Ugly place',1,1,1,1,1,'','11','12am','10pm','1','','10','2017-10-14'),(4,1,'Shared','House','3','1 bedroom','1 bed','1 bathroom','Shared','Canada','Robin Hill Circle','','Oakville','ON','-79.7341107','43.3805241',1,1,1,1,1,1,1,1,1,1,1,1,1,1,'Beautiful',' Hut',1,1,1,1,1,'','11','12am','10pm','1','','10','2017-10-14'),(5,1,'Shared','House','3','1 bedroom','1 bed','1 bathroom','Shared','Canada','Robin Hill Circle','','Oakville','ON','-79.7341107','43.3805241',1,1,1,1,1,1,1,1,1,1,1,1,1,1,'Beautiful','Island',1,1,1,1,1,'','11','12am','10pm','1','','10','2017-10-14');
/*!40000 ALTER TABLE `listings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testing`
--

DROP TABLE IF EXISTS `testing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testing`
--

LOCK TABLES `testing` WRITE;
/*!40000 ALTER TABLE `testing` DISABLE KEYS */;
INSERT INTO `testing` VALUES (1,'Matt'),(2,'John'),(3,'Greg'),(4,'Matt'),(5,'Poe'),(6,'Poe'),(7,'Poe'),(8,'Poe'),(9,'Poe');
/*!40000 ALTER TABLE `testing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_authentication`
--

DROP TABLE IF EXISTS `user_authentication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_authentication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_num` varchar(255) NOT NULL,
  `profile_image_path` text,
  `about_me` text,
  `location` varchar(250) DEFAULT NULL,
  `work` varchar(250) DEFAULT NULL,
  `languages` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_authentication`
--

LOCK TABLES `user_authentication` WRITE;
/*!40000 ALTER TABLE `user_authentication` DISABLE KEYS */;
INSERT INTO `user_authentication` VALUES (1,'Matt','Francis','a@gmail.com','$2a$10$PbxEG/VMRaJ74WSVr1wZCe5AUFTynOIC20o5aMK3yM1g4F/tPVDT2','2897727436','storage/emulated/0/DCIM/Screenshots/Screenshot_20171112-162044',NULL,'egypt','wb','arab'),(2,'Matt','Francis','q@gmail.com','$2a$10$./dDyHDcepCettf7IeUw6O7g77yOlGK7hCBhndSExjjgbgI5VNxXC','2897727436',NULL,NULL,NULL,NULL,NULL),(3,'Matt','Francis','q@gmail.com','$2a$10$0j2SsEBAxESateNuAM3Qy.Ju8DP5Yz82rq7/Of..woTJFz.wWdRDG','2897727436',NULL,NULL,NULL,NULL,NULL),(4,'Jo','Francis','q@gmail.com','$2a$10$SMhs71Y8CF1OmcVGJS8IOe6y7g8QO6WAU8rqM/kqMcKOwZl6UIsT2','2897727436',NULL,NULL,NULL,NULL,NULL),(5,'Jo','Francis','q@gmail.com','$2a$10$gJrJyTfr8pwzDfS2bKYjX.a7MqY/NlVicIKWFPGvqxgUp9ZO6EUdC','2897727436',NULL,NULL,NULL,NULL,NULL),(6,'Love','You','g@gmail.com','$2a$10$esHSazvaQSNVO5HugK/3g.z7pExjYh6eiSqtGSJTYRggcc4T.a5tW','+12897727465',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user_authentication` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-18  9:17:37
