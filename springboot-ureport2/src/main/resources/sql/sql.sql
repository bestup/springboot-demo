CREATE TABLE `tb_ureport_file` (
  `id` char(32) NOT NULL,
  `name` varchar(50) DEFAULT '' COMMENT '报表名称',
  `content` mediumblob COMMENT '报表内容',
  `is_delete` tinyint(4) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(20) DEFAULT '',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(20) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;