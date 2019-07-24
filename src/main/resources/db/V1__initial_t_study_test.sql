DROP TABLE IF EXISTS `t_study_test`;
CREATE TABLE `t_study_test` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL COMMENT '名称',
`age` int(11) DEFAULT NULL COMMENT '年龄',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='文章';
# 添加测试数据：
INSERT INTO `t_study_test` (`id`, `name`, `age`) VALUES ('10000', '测试1', '19');
INSERT INTO `t_study_test` (`id`, `name`, `age`) VALUES ('10001', '测试2', '20');
INSERT INTO `t_study_test` (`id`, `name`, `age`) VALUES ('10002', '测试3', '21');
INSERT INTO `t_study_test` (`id`, `name`, `age`) VALUES ('10003', '测试4', '22');
INSERT INTO `t_study_test` (`id`, `name`, `age`) VALUES ('10004', '测试5', '23');