-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.41 - MySQL Community Server - GPL
-- Server OS:                    Linux
-- HeidiSQL Version:             12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for blockchain_data
CREATE DATABASE IF NOT EXISTS `blockchain_data` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `blockchain_data`;

-- Dumping structure for table blockchain_data.event_type
CREATE TABLE IF NOT EXISTS `event_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table blockchain_data.symbol
CREATE TABLE IF NOT EXISTS `symbol` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table blockchain_data.ticker
CREATE TABLE IF NOT EXISTS `ticker` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_type_id` int NOT NULL DEFAULT (0),
  `event_timestamp` bigint DEFAULT NULL,
  `symbol_id` int NOT NULL DEFAULT '0',
  `price_change` decimal(20,8) DEFAULT NULL,
  `price_change_percent` decimal(10,4) DEFAULT NULL,
  `weighted_average_price` decimal(20,8) DEFAULT NULL,
  `first_trade_before_window` decimal(20,8) DEFAULT NULL,
  `last_price` decimal(20,8) NOT NULL,
  `last_quantity` decimal(20,8) DEFAULT NULL,
  `best_bid_price` decimal(20,8) DEFAULT NULL,
  `best_bid_quantity` decimal(20,8) DEFAULT NULL,
  `best_ask_price` decimal(20,8) DEFAULT NULL,
  `best_ask_quantity` decimal(20,8) DEFAULT NULL,
  `open_price` decimal(20,8) DEFAULT NULL,
  `high_price` decimal(20,8) DEFAULT NULL,
  `low_price` decimal(20,8) DEFAULT NULL,
  `total_traded_base_asset_volume` decimal(25,8) DEFAULT NULL,
  `total_traded_quote_asset_volume` decimal(25,8) DEFAULT NULL,
  `statistics_open_time` bigint DEFAULT NULL,
  `statistics_close_time` bigint DEFAULT NULL,
  `first_trade_id` bigint DEFAULT NULL,
  `last_trade_id` bigint DEFAULT NULL,
  `total_trade_count` bigint DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_ticker_event_type` (`event_type_id`),
  KEY `FK_ticker_symbol` (`symbol_id`),
  CONSTRAINT `FK_ticker_event_type` FOREIGN KEY (`event_type_id`) REFERENCES `event_type` (`id`),
  CONSTRAINT `FK_ticker_symbol` FOREIGN KEY (`symbol_id`) REFERENCES `symbol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4900 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table blockchain_data.trade
CREATE TABLE IF NOT EXISTS `trade` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_type_id` int NOT NULL DEFAULT '0',
  `event_timestamp` bigint NOT NULL,
  `symbol_id` int NOT NULL DEFAULT '0',
  `trade_id` bigint NOT NULL,
  `price` decimal(20,8) NOT NULL,
  `quantity` decimal(20,8) NOT NULL,
  `trade_time` bigint NOT NULL,
  `is_buyer_market_maker` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `trade_id` (`trade_id`),
  KEY `FK_trade_event_type` (`event_type_id`) USING BTREE,
  KEY `FK_trade_symbol` (`symbol_id`) USING BTREE,
  CONSTRAINT `FK_trade_event_type` FOREIGN KEY (`event_type_id`) REFERENCES `event_type` (`id`),
  CONSTRAINT `FK_trade_symbol` FOREIGN KEY (`symbol_id`) REFERENCES `symbol` (`id`),
  CONSTRAINT `chk_positive_price` CHECK ((`price` > 0)),
  CONSTRAINT `chk_positive_quantity` CHECK ((`quantity` > 0)),
  CONSTRAINT `trade_chk_1` CHECK ((`price` > 0)),
  CONSTRAINT `trade_chk_2` CHECK ((`quantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=23139 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table blockchain_data.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL DEFAULT '0',
  `last_name` varchar(50) NOT NULL DEFAULT '0',
  `email` varchar(50) NOT NULL DEFAULT '0',
  `api_key` varchar(50) NOT NULL DEFAULT '0',
  `active` smallint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
