
-- mysql
DROP TABLE IF EXISTS `BLACK_LIST`;
CREATE TABLE `BLACK_LIST` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL DEFAULT '' COMMENT '用户名',
  `dimension` int NOT NULL DEFAULT 2 COMMENT '维度, 0表示手机号，1表示IP,2表示设备ID',
  `type` int NOT NULL DEFAULT 0 COMMENT '类型, 0表示注册，1表示登录，2表示验证码',
  `value` varchar(128) NOT NULL DEFAULT '' COMMENT '值，对应的名称(手机号\IP\设备ID)',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `reason` varchar(512) DEFAULT NULL COMMENT '详情,进入黑名单的原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


