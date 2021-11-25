CREATE TABLE `unit` (
                        `id` bigint(12) NOT NULL AUTO_INCREMENT,
                        `ncrb_id` varchar(20) DEFAULT NULL,
                        `idunittype` bigint(12) DEFAULT NULL,
                        `head_rank` int(3) DEFAULT NULL,
                        `unit_name` varchar(200) DEFAULT NULL,
                        `unit_short_code` varchar(200) NOT NULL,
                        `is_parent_unit` int(1) DEFAULT 0,
                        `status` bigint(1) DEFAULT 1,
                        `admin_unitid` int(11) NOT NULL DEFAULT 0,
                        `is_old` int(1) DEFAULT 0,
                        `parent_unitid` int(11) DEFAULT NULL,
                        `lft` int(11) DEFAULT NULL,
                        `rgt` int(11) DEFAULT NULL,
                        `depth` int(11) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8