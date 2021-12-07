CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `username` varchar(200) DEFAULT NULL,
                        `password` varchar(200) DEFAULT NULL,
                        `name` varchar(200) DEFAULT NULL,
                        `status` int(1) DEFAULT 1,
                        PRIMARY KEY (`id`),UNIQUE (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4