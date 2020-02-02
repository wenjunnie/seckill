-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.17-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for seckill
CREATE DATABASE IF NOT EXISTS `seckill` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `seckill`;

-- Dumping structure for table seckill.item
CREATE TABLE IF NOT EXISTS `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  `description` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sales` int(11) NOT NULL DEFAULT '0',
  `img_url` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table seckill.item: ~3 rows (approximately)
DELETE FROM `item`;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` (`id`, `title`, `price`, `description`, `sales`, `img_url`) VALUES
	(1, 'iphone', 999.99, '一台苹果手机', 84, 'http://p0.meituan.net/mogu/9489f17d0fbee583202481a3cd04b858296735.jpg@660w_500h_1e_1c'),
	(2, 'apple', 11.11, '一个苹果', 9, 'http://p0.meituan.net/660.500/joymerchant/e8f73195ae6f4b554aa4b31175ba822d--1975495663.jpg'),
	(3, 'gift', 13.14, '神秘礼物', 18, 'https://img.meituan.net/msmerchant/24f06695418b3f1e9bcc854fd2d404da146374.jpg@380w_214h_1e_1c'),
	(4, '水果沙拉', 6.6, '一份水果沙拉', 0, 'https://img.meituan.net/msmerchant/af4ecba340222d180c3347142d775b40187621.jpg@380w_214h_1e_1c');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;

-- Dumping structure for table seckill.item_stock
CREATE TABLE IF NOT EXISTS `item_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `item_id` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='将库存服务分开是为了用户下单时其他用户浏览商品不会加行锁受影响';

-- Dumping data for table seckill.item_stock: ~2 rows (approximately)
DELETE FROM `item_stock`;
/*!40000 ALTER TABLE `item_stock` DISABLE KEYS */;
INSERT INTO `item_stock` (`id`, `stock`, `item_id`) VALUES
	(1, 501, 1),
	(2, 547, 2),
	(3, 94, 3),
	(4, 132, 4);
/*!40000 ALTER TABLE `item_stock` ENABLE KEYS */;

-- Dumping structure for table seckill.order_info
CREATE TABLE IF NOT EXISTS `order_info` (
  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `item_price` double DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `order_price` double DEFAULT NULL,
  `promo_id` int(11) DEFAULT '0' COMMENT '非0表示是秒杀商品价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table seckill.order_info: ~92 rows (approximately)
DELETE FROM `order_info`;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
INSERT INTO `order_info` (`id`, `user_id`, `item_id`, `item_price`, `amount`, `order_price`, `promo_id`) VALUES
	('2019122200000000', 9, 2, NULL, 1, NULL, 0),
	('2019122200000100', 9, 2, 13.42, 1, 13.42, 0),
	('2019122200000200', 9, 2, 13.42, 1, 13.42, 0),
	('2019122200000300', 9, 1, 13.42, 1, 13.42, 0),
	('2019122200000400', 9, 2, 13.42, 1, 13.42, 0),
	('2019122200000500', 9, 2, 13.42, 1, 13.42, 0),
	('2019122200000600', 9, 1, 13.42, 1, 13.42, 0),
	('2019122200000700', 9, 2, 13, 1, 13, 0),
	('2019122200000800', 9, 1, 13.42, 1, 13.42, 0),
	('2019122200000900', 9, 1, 100, 1, 100, 1),
	('2019122200001000', 9, 1, 100, 1, 100, 1),
	('2019122500001100', 23, 2, 13, 1, 13, 0),
	('2019122500001200', 23, 2, 13, 1, 13, 0),
	('2019122500001300', 23, 1, 99.9, 1, 99.9, 1),
	('2019122500001400', 9, 1, 99.9, 1, 99.9, 1),
	('2019122600001500', 9, 1, 99.9, 1, 99.9, 1),
	('2019122600001600', 9, 1, 99.9, 1, 99.9, 1),
	('2019122600001700', 9, 1, 99.9, 1, 99.9, 1),
	('2019122600001800', 9, 1, 99.9, 1, 99.9, 1),
	('2019122600001900', 9, 1, 99.9, 1, 99.9, 1),
	('2019122600002000', 9, 1, 99.9, 1, 99.9, 1),
	('2019122600002100', 9, 1, 99.9, 1, 99.9, 1),
	('2019122700002200', 9, 1, 99.9, 1, 99.9, 1),
	('2019123000002300', 1, 3, 13.14, 1, 13.14, 0),
	('2019123000002400', 1, 1, 99.9, 1, 99.9, 1),
	('2019123000002500', 1, 1, 99.9, 1, 99.9, 1),
	('2019123000002600', 1, 1, 99.9, 1, 99.9, 1),
	('2019123000002700', 1, 1, 99.9, 1, 99.9, 1),
	('2019123000002800', 1, 1, 99.9, 1, 99.9, 1),
	('2019123000002900', 1, 1, 99.9, 1, 99.9, 1),
	('2019123000003000', 1, 1, 99.9, 1, 99.9, 1),
	('2019123000003100', 1, 1, 99.9, 1, 99.9, 1),
	('2019123000003200', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100003300', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100003400', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100003500', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100003600', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100003700', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100003800', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100003900', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100004000', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100004100', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100004200', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100004300', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100004400', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100004500', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100004600', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100004700', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100004800', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100004900', 1, 3, 13.14, 1, 13.14, 0),
	('2019123100005000', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100005100', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100005200', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100005300', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100005400', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100005500', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100005600', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100005700', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100005800', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100005900', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100006000', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100006100', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100006200', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100006300', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100006400', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100006500', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100006600', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100006700', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100006800', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100006900', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100007000', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100007100', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100007200', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100007300', 1, 3, 13.14, 1, 13.14, 0),
	('2019123100007400', 1, 3, 13.14, 1, 13.14, 0),
	('2019123100007500', 1, 3, 13.14, 1, 13.14, 0),
	('2019123100007600', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100007700', 1, 1, 99.9, 1, 99.9, 1),
	('2019123100007800', 1, 3, 13.14, 1, 13.14, 0),
	('2019123100007900', 1, 3, 13.14, 1, 13.14, 0),
	('2020010100008000', 1, 1, 99.9, 1, 99.9, 1),
	('2020010100008100', 1, 1, 99.9, 1, 99.9, 1),
	('2020010100008200', 1, 1, 99.9, 1, 99.9, 1),
	('2020010100008300', 1, 1, 99.9, 1, 99.9, 1),
	('2020010100008400', 1, 1, 99.9, 1, 99.9, 1),
	('2020010100008500', 1, 3, 13.14, 1, 13.14, 0),
	('2020010100008600', 1, 1, 99.9, 1, 99.9, 1),
	('2020010100008700', 1, 1, 99.9, 1, 99.9, 1),
	('2020010100008800', 1, 3, 13.14, 1, 13.14, 0),
	('2020010200008900', 1, 1, 99.9, 1, 99.9, 1),
	('2020010200009000', 1, 1, 99.9, 1, 99.9, 1),
	('2020010200009100', 1, 3, 13.14, 1, 13.14, 0),
	('2020010200009200', 1, 1, 99.9, 1, 99.9, 1),
	('2020010200009300', 1, 3, 13.14, 1, 13.14, 0),
	('2020010200009400', 1, 3, 13.14, 1, 13.14, 0),
	('2020010200009500', 1, 1, 99.9, 1, 99.9, 1),
	('2020010200009600', 1, 1, 99.9, 1, 99.9, 1),
	('2020010200009700', 1, 1, 99.9, 1, 99.9, 1),
	('2020010400009800', 1, 3, 13.14, 1, 13.14, 0),
	('2020010400009900', 1, 1, 99.9, 1, 99.9, 1),
	('2020010400010000', 1, 3, 13.14, 1, 13.14, 0),
	('2020010400010100', 1, 1, 99.9, 1, 99.9, 1),
	('2020020100010200', 4, 3, 13.14, 1, 13.14, 0),
	('2020020100010300', 4, 3, 13.14, 1, 13.14, 0),
	('2020020100010400', 4, 1, 99.9, 1, 99.9, 1),
	('2020020100010500', 4, 1, 99.9, 1, 99.9, 1);
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;

-- Dumping structure for table seckill.promo
CREATE TABLE IF NOT EXISTS `promo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `item_id` int(11) DEFAULT NULL,
  `promo_item_price` double DEFAULT NULL,
  `end_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='秒杀活动表';

-- Dumping data for table seckill.promo: ~1 rows (approximately)
DELETE FROM `promo`;
/*!40000 ALTER TABLE `promo` DISABLE KEYS */;
INSERT INTO `promo` (`id`, `promo_name`, `start_date`, `item_id`, `promo_item_price`, `end_date`) VALUES
	(1, 'iphone秒杀', '2019-12-23 15:06:00', 1, 99.9, '2020-03-24 12:03:14'),
	(2, 'apple秒杀', '2020-01-31 19:27:27', 2, 1.1, '2020-03-03 19:27:27');
/*!40000 ALTER TABLE `promo` ENABLE KEYS */;

-- Dumping structure for table seckill.sequence_info
CREATE TABLE IF NOT EXISTS `sequence_info` (
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `current_value` int(11) NOT NULL DEFAULT '0',
  `step` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table seckill.sequence_info: ~0 rows (approximately)
DELETE FROM `sequence_info`;
/*!40000 ALTER TABLE `sequence_info` DISABLE KEYS */;
INSERT INTO `sequence_info` (`name`, `current_value`, `step`) VALUES
	('order_info', 106, 1);
/*!40000 ALTER TABLE `sequence_info` ENABLE KEYS */;

-- Dumping structure for table seckill.stock_log
CREATE TABLE IF NOT EXISTS `stock_log` (
  `stock_log_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `item_id` int(11) DEFAULT '0',
  `amount` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0' COMMENT '1表示初始状态，2表示下单扣库存成功，3表示下单回滚',
  PRIMARY KEY (`stock_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='库存流水表';

-- Dumping data for table seckill.stock_log: ~28 rows (approximately)
DELETE FROM `stock_log`;
/*!40000 ALTER TABLE `stock_log` DISABLE KEYS */;
INSERT INTO `stock_log` (`stock_log_id`, `item_id`, `amount`, `status`) VALUES
	('0a1d7c3bc2b643f8a8478bc3eafb220f', 1, 1, 2),
	('1318ec608b2a4fa78d46bcccef644875', 1, 1, 2),
	('153dd2047189499d9822b57947a6aa4b', 1, 1, 2),
	('295c48f90c5948938cc80db42160bd5c', 1, 1, 2),
	('3125c2956c28465b9db41ae2cfc6d4e5', 3, 1, 2),
	('33a1094301b343a18fcdda7e4bf08824', 1, 1, 2),
	('369df3846c6d40c5aeb2d8cbf28888a1', 3, 1, 2),
	('397e25c910a542728695b1fd27e730c8', 1, 1, 2),
	('3c2b1ba9a7834c1e9238269b18a12c18', 3, 1, 2),
	('40b9645c792f421883c72e9cd94fb84c', 1, 1, 2),
	('41fe55f185b647ae82965c2a72cfebcd', 1, 1, 2),
	('48b5ea55cceb403c982ca1f540279e95', 1, 1, 2),
	('52b324c8ad6144228164e1b3da1790c4', 3, 1, 2),
	('5566f7013e054bbfb55078a19ef5b811', 3, 1, 2),
	('56f43833fec04671b6cadd433fd1a549', 3, 1, 2),
	('5ea577eab0e94487ad7129a3712f5664', 1, 1, 2),
	('6233d4cce42d4d998eec423dfa5b6ddd', 3, 1, 2),
	('71622ea7f13b4fee86e33bcfc8427778', 3, 1, 2),
	('79a5e260e1a84ab1a934208b9a4b47c9', 3, 1, 2),
	('8b7d23f6f8f84db88da25eef06ae469f', 1, 1, 2),
	('8c6f11f6d88348c998f3ff0ada2b1001', 3, 1, 2),
	('98504b90b8714260993ea5d16e4ceb67', 1, 1, 2),
	('9d2ab0f55e684f708066f8b8b86df49c', 1, 1, 2),
	('a56588897b1f4edba3aff04006293f2a', 1, 1, 2),
	('acff5d2fdef44109ab86399a84606c83', 1, 1, 2),
	('b4b9816a4feb4ae0afe0a15b7560131b', 3, 1, 2),
	('bcf024cc698c45f2961bf5e479800606', 1, 1, 2),
	('c09ed6b16e104b12996d31524f0b8a13', 1, 1, 2),
	('c9f324891cef442bb481e2bce6dfcfc7', 1, 1, 2),
	('cc351b2ab59b4fa6aedd700d425d722c', 1, 1, 2),
	('d6458bf0856b4b70ae74cfdad52fb44f', 1, 1, 2),
	('db5ac3cbffb74436abbeae86a7fe10cf', 1, 1, 2),
	('dcb741fcfe704b688156393d534be0a0', 3, 1, 2),
	('e23b817506a2443eb3f97d3030a429ea', 1, 1, 2),
	('e923b44d485f4f75a3248df4f40415c3', 1, 1, 2),
	('eecd2fa00ae14d4c9dadd37e9be60bb9', 1, 1, 2),
	('efa3ebd5320f4154ab54c1d14968efe9', 3, 1, 2),
	('fd65edb25b1c4d3e899e502825a8517b', 3, 1, 2);
/*!40000 ALTER TABLE `stock_log` ENABLE KEYS */;

-- Dumping structure for table seckill.user_info
CREATE TABLE IF NOT EXISTS `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `gender` tinyint(4) NOT NULL COMMENT '1男性2女性',
  `age` int(11) NOT NULL,
  `telphone` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `register_mode` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'NULL' COMMENT '注册方式',
  `third_party_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'NULL' COMMENT '第三方账号id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table seckill.user_info: ~4 rows (approximately)
DELETE FROM `user_info`;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` (`id`, `name`, `gender`, `age`, `telphone`, `register_mode`, `third_party_id`) VALUES
	(1, 'nie', 1, 22, '55', 'byPhone', 'NULL'),
	(2, 'abc', 2, 15, '1', 'byPhone', 'NULL'),
	(3, 'wenjun', 1, 22, '18801000733', 'byPhone', 'NULL'),
	(4, 'test', 2, 18, '123', 'byPhone', 'NULL'),
	(5, 'nie', 1, 22, '18801000732', 'byPhone', 'NULL');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;

-- Dumping structure for table seckill.user_password
CREATE TABLE IF NOT EXISTS `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table seckill.user_password: ~4 rows (approximately)
DELETE FROM `user_password`;
/*!40000 ALTER TABLE `user_password` DISABLE KEYS */;
INSERT INTO `user_password` (`id`, `encrpt_password`, `user_id`) VALUES
	(1, '$2a$10$pO/XLRGIjdIfj3hVQ7.b7ON5F3n5s6ymot2uSGnGitf9PfOM1RybG', 1),
	(2, '$2a$10$ZLB8g7MGiawf0eXHDu/MkuKQVyURfiCeIC2d31w3KeH8smEDrhKvi', 2),
	(3, '$2a$10$YGN8JOf/iW/t9dMmtmJgTON4jBXPIhuPr1jk4wyvUoaf0PJalEn06', 3),
	(4, '$2a$10$fQGhBNWVZOnwhbKSo2rDNO0o/pPs3kZWx3xtBrOUw9s82z5xeOEte', 4),
	(5, '$2a$10$YGN8JOf/iW/t9dMmtmJgTON4jBXPIhuPr1jk4wyvUoaf0PJalEn06', 5);
/*!40000 ALTER TABLE `user_password` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
