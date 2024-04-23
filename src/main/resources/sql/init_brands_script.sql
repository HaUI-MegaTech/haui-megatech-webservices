CREATE TABLE IF NOT EXISTS `brands` (
    `brand_id` INT KEY AUTO_INCREMENT,
    `name` VARCHAR(255) CHARACTER SET utf8,
    `img` VARCHAR(255) CHARACTER SET utf8,
    `when_created` DATETIME
);
INSERT INTO `brands` 
VALUES 
	(1,'HP','https://cdn.tgdd.vn/Brand/1/logo-hp-149x40-1.png',NULL),
	(2,'Asus','https://cdn.tgdd.vn/Brand/1/logo-asus-149x40.png',NULL),
	(3,'Acer','https://cdn.tgdd.vn/Brand/1/logo-acer-149x40.png',NULL),
	(4,'Lenovo','https://cdn.tgdd.vn/Brand/1/logo-lenovo-149x40.png',NULL),
	(5,'Dell','https://cdn.tgdd.vn/Brand/1/logo-dell-149x40.png',NULL),
	(6,'MSI','https://cdn.tgdd.vn/Brand/1/logo-msi-149x40.png',NULL),
	(7,'MacBook','https://cdn.tgdd.vn/Brand/1/logo-macbook-149x40.png',NULL),
	(8,'LG','https://cdn.tgdd.vn/Brand/1/logo-lg-149x40.png',NULL);
    
UPDATE review.brands SET when_created = current_timestamp();
