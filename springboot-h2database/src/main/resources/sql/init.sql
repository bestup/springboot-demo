drop schema if exists `test`;
create schema `test`;
use `test`;
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `age` int(2) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `score` int(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `student1`;
CREATE TABLE `student1`
(
    `student_id`   int(10) unsigned NOT NULL AUTO_INCREMENT,
    `student_name` varchar(1024)    NOT NULL,
    `gender`       tinyint(1)       NOT NULL,
    `age`          int(3)           NOT NULL,
    PRIMARY KEY (`student_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;


