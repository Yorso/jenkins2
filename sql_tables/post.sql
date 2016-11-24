CREATE TABLE `post` (
`id` int(11) AUTO_INCREMENT,
`title` text,
`date` datetime,
`user_id` int(11),
PRIMARY KEY (`id`),
CONSTRAINT `user_id` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
)
