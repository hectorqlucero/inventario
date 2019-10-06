-- MariaDB dump 10.17  Distrib 10.4.7-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: inventario
-- ------------------------------------------------------
-- Server version	10.4.7-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compras` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `provedor_id` int(11) unsigned NOT NULL,
  `producto_id` int(11) unsigned NOT NULL,
  `num_recibido` int(11) NOT NULL,
  `compra_fecha` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `compra_producto_fk` (`producto_id`),
  KEY `compra_provedor_fk` (`provedor_id`),
  CONSTRAINT `compra_producto_fk` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`),
  CONSTRAINT `compra_provedor_fk` FOREIGN KEY (`provedor_id`) REFERENCES `provedores` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compras`
--

LOCK TABLES `compras` WRITE;
/*!40000 ALTER TABLE `compras` DISABLE KEYS */;
INSERT INTO `compras` VALUES (1,2,2,50,'2014-11-02'),(2,2,1,15,'2014-09-02'),(3,3,3,10,'2014-11-12'),(4,2,2,25,'2014-01-02'),(5,2,3,20,'2014-02-22'),(6,1,1,5,'2015-11-02'),(7,3,3,3,'2014-01-02'),(8,1,3,20,'2015-11-11'),(9,2,1,0,'2014-11-02'),(10,1,1,5,'2016-11-02'),(11,2,5,12,'2016-11-02'),(12,2,3,90,'2016-11-02'),(13,1,6,23,'2016-08-02'),(15,2,10,13,'2017-11-02'),(16,1,10,0,'2017-01-02'),(17,1,2,0,'2017-02-22'),(18,2,1,10,'2017-03-02'),(19,2,9,12,'2017-03-03'),(20,2,13,1234,'2017-05-12'),(21,1,12,0,'2017-05-22'),(22,1,13,0,'2017-06-12'),(23,2,3,0,'2017-08-02'),(24,3,9,400,'2017-10-02'),(25,1,14,0,'2017-11-02'),(26,2,16,12,'1917-11-30'),(27,1,3,0,'2017-07-02'),(28,3,17,33,'2017-07-12'),(29,1,18,453,'2017-07-23'),(30,2,18,22,'2017-11-02'),(31,2,1,5,'2019-10-02'),(32,2,1,10,'2019-10-01'),(33,2,1,5,'2019-09-23'),(34,1,1,5,'1919-10-02'),(35,3,1,10,'2019-10-01'),(36,2,1,10,'2019-10-02'),(37,3,1,100,'2019-10-02'),(38,2,1,100,'2019-10-01'),(39,1,1,100,'2019-09-17'),(40,3,1,100,'2019-10-01'),(41,2,1,100,'2019-10-02'),(42,3,3,10,'1919-10-02');
/*!40000 ALTER TABLE `compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `titulo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `apell_paterno` varchar(255) DEFAULT NULL,
  `apell_materno` varchar(255) DEFAULT NULL,
  `producto_id` int(11) unsigned NOT NULL,
  `enviado_numero` int(11) DEFAULT NULL,
  `orden_fecha` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_product_fk` (`producto_id`),
  CONSTRAINT `order_product_fk` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,NULL,'Suzy','Cliente',NULL,3,10,'1914-04-01'),(2,NULL,'Suzy','Cliente',NULL,3,20,'2014-04-22'),(3,NULL,'Ben','Thomas',NULL,1,5,'2014-04-11'),(4,NULL,'Johnny','Test',NULL,3,10,'2014-04-02'),(5,NULL,'Steve','Smith',NULL,1,20,'2014-04-15'),(6,NULL,'Steve','Palmer',NULL,3,3,'2014-02-22'),(7,NULL,'Tim','Scott',NULL,3,5,'2014-03-22'),(8,NULL,'Dave','Boyd',NULL,3,10,'2014-01-22'),(9,NULL,'Suzy','Cliente',NULL,2,30,'2014-01-21'),(10,NULL,'Dylan','Test',NULL,3,5,'2014-04-23'),(11,NULL,'Betty','Fryar',NULL,3,12,'2014-04-22'),(12,NULL,'Jerry','Sellers',NULL,2,124,'2014-04-22'),(13,NULL,'BOB','SMITH',NULL,2,500,'2014-05-11'),(14,NULL,'Suzy','Cliente',NULL,5,5,'2015-04-07'),(15,NULL,'Suzy','Cliente',NULL,9,50,'2015-04-07'),(16,NULL,'Suzy','Cliente',NULL,3,1,'2015-04-07'),(17,NULL,'Suzy','Cliente',NULL,10,5,'2015-09-09'),(18,NULL,'John','Lemeasure',NULL,10,12,'2016-02-05'),(19,NULL,'Suzy','Cliente',NULL,9,2,'2017-02-25'),(20,NULL,'','',NULL,9,1,'2017-01-15'),(21,NULL,'llkjh','kjlkh',NULL,11,250,'2017-02-15'),(22,NULL,'Suzy','Cliente',NULL,16,14,'2017-04-05'),(23,NULL,'Suzy','Cliente',NULL,11,50,'1917-06-05'),(24,NULL,'Suzy','Cliente',NULL,9,200,'2017-06-05'),(25,NULL,'Test','Cowley',NULL,14,12,'1917-11-05'),(26,NULL,'Elvis','P',NULL,17,900,'2017-04-05'),(27,NULL,'Elvis','P',NULL,18,9000,'2017-06-05'),(28,NULL,'','',NULL,4,0,'2017-04-05');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productos` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `p_nombre` varchar(255) DEFAULT NULL,
  `n_parte` varchar(255) DEFAULT NULL,
  `p_etiqueta` varchar(255) DEFAULT NULL,
  `inv_inicio` int(11) DEFAULT NULL,
  `inv_recibido` int(11) DEFAULT NULL,
  `inv_enviado` int(11) DEFAULT NULL,
  `inv_en_mano` int(11) DEFAULT NULL,
  `r_minimo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'Servidor Dell','Xp 2000','Servidor Dell- Xp 2000',100017,580,25,100572,15),(2,'Google Chromebooks','1','Google Chromebooks- 1.0',100,75,654,-479,20),(3,'Cisco Ruteador','10X','Cisco Ruteador- 10X',45,153,76,122,88),(4,'Sadasd','21','Sadasd- 21',25,0,0,25,10),(5,'Semih','37','Semih- 37',1,12,5,8,5),(6,'Crazy Horse Ruteador','123DF5','Crazy Horse Ruteador- 123DF5',5,23,0,28,1),(7,'Monitores','','Monitores- 999',0,0,0,0,0),(8,'PRUEBA','123','PRUEBA- 123',10,0,0,10,10),(9,'bob','bob-1','bob- 1',500,412,267,645,400),(10,'Multimeter','c345','Multimeter- c345',3,13,17,-1,4),(11,'dfgdf','54334','dfgdf- 54334',0,0,300,-300,0),(12,'UniBox','1','UniBox- 1',200,0,0,200,300),(13,'Test 1','123456','Test- 123456',50,1234,0,1284,10),(14,'Toby','57456','Toby- 57456',567,0,12,555,56467),(15,'sdsad','sdsdsad','sdsad- sdsdsad',12,0,0,12,12),(16,'test','55555','test- 55555',500,12,0,512,25),(17,'Firewalls','362436','Firewalls- 362436',5,33,900,-862,10),(18,'Cables','7734','Cables- 7734',9,475,9000,-8516,100),(19,'Test','1','Test- 001',25,0,0,25,222);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provedores`
--

DROP TABLE IF EXISTS `provedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provedores` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `provedor` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provedores`
--

LOCK TABLES `provedores` WRITE;
/*!40000 ALTER TABLE `provedores` DISABLE KEYS */;
INSERT INTO `provedores` VALUES (1,'ShockWave Tech'),(2,'CDW'),(3,'ACME Tech Supplies');
/*!40000 ALTER TABLE `provedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lastname` varchar(45) DEFAULT NULL,
  `firstname` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` text DEFAULT NULL,
  `dob` varchar(45) DEFAULT NULL,
  `cell` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `fax` varchar(45) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `level` char(1) DEFAULT NULL COMMENT 'A=Administrador,U=Usuario,S=Sistema',
  `active` char(1) DEFAULT NULL COMMENT 'T=Active,F=Not active',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Lucero','Hector','hectorqlucero@gmail.com','$s0$e0801$FuDkcPV8xTcfk0LvxzzjZg==$Rwo2iGqWsH/NJirv5TII3XYQ1Q7q3RyRvZZnhjnGYKk=','1957-02-07',NULL,NULL,NULL,'hectorqlucero@gmail.com','S','T'),(2,'Pescador','Marco','marcopescador@hotmail.com','$s0$e0801$+9abT9KB1mJIhHpnko4zKw==$02hK1FQgbEYoQ0a83oj574urmQIboiGfPxmRI8p8ygY=','1968-10-04',NULL,NULL,NULL,'marcopescador@hotmail.com','S','T');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-06 12:02:53
