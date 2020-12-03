
CREATE TABLE `sys_department` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `department_name` varchar(64) NOT NULL DEFAULT '' COMMENT '部门名称',
    `department_alias_name` varchar(64) NOT NULL DEFAULT '' COMMENT '部门别名',
    `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型，1：技术；2：业务；3：行政',
    `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：启用，1:停用',
    `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
    `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
    `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0：未删除，1：已删除)',
    `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
    `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_sys_department_1` (`department_name`) USING BTREE,
    KEY `idx_sys_department_2` (`department_alias_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';

CREATE TABLE `sys_team` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `team_name` varchar(64) NOT NULL DEFAULT '' COMMENT '项目名称',
    `team_alias_name` varchar(64) NOT NULL DEFAULT '' COMMENT '项目别名',
    `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '类型，1：技术；2：业务；3：行政',
    `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：启用，1:停用',
    `department_id` int NOT NULL COMMENT '所属部门id',
    `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
    `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
    `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0：未删除，1：已删除)',
    `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
    `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
PRIMARY KEY (`id`),
KEY `idx_sys_team_1` (`team_name`) USING BTREE,
KEY `idx_sys_team_2` (`team_alias_name`) USING BTREE,
KEY `idx_sys_team_3` (`department_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='项目表';

CREATE TABLE `sys_user` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_name` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名称',
    `phone` varchar(64) NOT NULL DEFAULT '' COMMENT '用户手机',
    `password` varchar(64) NOT NULL DEFAULT '' COMMENT '用户密码',
    `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态，0：启用，1:停用',
    `department_id` int NOT NULL DEFAULT '0' COMMENT '所属部门id',
    `team_id` int NOT NULL DEFAULT '0' COMMENT '所属团队id',
    `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
    `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `update_user` int(11) NOT NULL DEFAULT '0' COMMENT '修改人',
    `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改时间',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0：未删除，1：已删除)',
    `version` int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
    `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
PRIMARY KEY (`id`) USING BTREE,
KEY `idx_sys_user_1` (`user_name`) USING BTREE,
KEY `idx_sys_user_2` (`phone`) USING BTREE,
KEY `idx_sys_user_3` (`department_id`) USING BTREE,
KEY `idx_sys_user_4` (`team_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

CREATE TABLE `order_record` (
    `id` int NOT NULL AUTO_INCREMENT,
    `version` int NOT NULL DEFAULT '0',
    `state` smallint NOT NULL DEFAULT '0' COMMENT '生产状态',
    `order_number` varchar(30) NOT NULL DEFAULT '' COMMENT 'po 单号',
    `plate_making_id` int NOT NULL DEFAULT '0' COMMENT '打版单id',
    `plate_making_num` varchar(30) NOT NULL DEFAULT '' COMMENT '打版单号',
    `spu` varchar(64) NOT NULL DEFAULT '' COMMENT 'spu',
    `reference_spu` varchar(64) NOT NULL DEFAULT '' COMMENT '参考spu',
    `color` varchar(50) NOT NULL DEFAULT '' COMMENT '颜色',
    `color_id` int NOT NULL DEFAULT '0' COMMENT '颜色id',
    `time_required` int NOT NULL DEFAULT '0' COMMENT '时效要求',
    `purchase_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '生产单价',
    `reference_imageurl` varchar(256) NOT NULL DEFAULT '' COMMENT '参考图片地址',
    `material_type_enum` smallint NOT NULL DEFAULT '0' COMMENT '面料品类',
    `three_category_id` smallint NOT NULL DEFAULT '0' COMMENT '三级分类',
    `special_technology_tag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0：无特殊工艺，1：特殊工艺，',
    `special_technology_text` varchar(100) NOT NULL DEFAULT '' COMMENT '特殊工艺描述',
    `first_order` tinyint NOT NULL DEFAULT '0' COMMENT '是否首单，0：否，1：是',
    `urgent` tinyint NOT NULL DEFAULT '0' COMMENT '是否紧急，0：否，1：是',
    `batch_color` tinyint NOT NULL DEFAULT '0' COMMENT '是否批色，0：否，1：是',
    `return_type` tinyint NOT NULL DEFAULT '0' COMMENT '返工类型',
    `old_supplier` varchar(50) NOT NULL DEFAULT '' COMMENT '历史供应商',
    `old_supplier_id` int NOT NULL DEFAULT '0' COMMENT '历史供应商id',
    `supplier` varchar(50) NOT NULL DEFAULT '' COMMENT '供应商',
    `supplier_id` int NOT NULL DEFAULT '0' COMMENT '供应商id',
    `merchandiser` varchar(20) NOT NULL DEFAULT '' COMMENT '跟单员',
    `old_merchandiser` varchar(20) NOT NULL DEFAULT '' COMMENT '历史跟单员',
    `group_name` varchar(50) NOT NULL DEFAULT '' COMMENT '事业部',
    `group_id` varchar(50) NOT NULL DEFAULT '' COMMENT '事业部id',
    `color_card` tinyint NOT NULL DEFAULT '0' COMMENT '色卡借取状态:1 已借工厂，2 未借，3 已归还',
    `material_upload_flag` tinyint NOT NULL DEFAULT '0' COMMENT '物料上传状态，1 上传，0未上传',
    `receive_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '分单时间',
    `paper_make_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '做样时间，制作产前样的时间',
    `paper_finish_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '回样时间，完成产前样的时间',
    `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `create_user` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
    `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
    `update_user` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='mes生产订单表';

CREATE TABLE `mq_send_message` (
   `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
   `message_param` mediumtext COMMENT '请求报文',
   `template_bean_name` varchar(100) NOT NULL DEFAULT '' COMMENT 'bean名称',
   `access_system` varchar(64) NOT NULL DEFAULT '' COMMENT '接收系统',
   `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '消息状态(1:等待发送，2：发送成功，3：死亡)',
   `send_count` int NOT NULL DEFAULT '0' COMMENT '重发次数',
   `send_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '最后一次推送时间',
   `die_count` int NOT NULL DEFAULT '5' COMMENT '死亡（最大发送）次数',
   `error_msg` varchar(255) NOT NULL DEFAULT '' COMMENT '执行错误信息',
   `confirm_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '发送成功回调时间',
   `die_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '变更为死亡状态时间',
   `send_user` bigint NOT NULL DEFAULT '0' COMMENT '重发人',
   `create_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '创建时间',
   `create_user` bigint NOT NULL DEFAULT '0' COMMENT '创建人',
   `update_time` datetime NOT NULL DEFAULT '1970-01-01 08:00:00' COMMENT '更新时间',
   `update_user` bigint NOT NULL DEFAULT '0' COMMENT '更新人',
   `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0：未删除，1：已删除)',
   `version` int NOT NULL DEFAULT '0' COMMENT '版本号，非查询操作需要递增+1',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='mq消息发送表';

CREATE TABLE `order_record` (
    `id` int NOT NULL AUTO_INCREMENT,
    `state` smallint NOT NULL DEFAULT '0' COMMENT '生产状态',
    `order_number` varchar(30) NOT NULL DEFAULT '' COMMENT '订单号',
    `spu` varchar(64) NOT NULL DEFAULT '' COMMENT 'spu',
    `reference_spu` varchar(64) NOT NULL DEFAULT '' COMMENT '参考spu',
    `color` varchar(50) NOT NULL DEFAULT '' COMMENT '颜色',
    `color_id` int NOT NULL DEFAULT '0' COMMENT '颜色id',
    `purchase_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '生产单价',
    `reference_image` varchar(256) NOT NULL DEFAULT '' COMMENT '参考图片地址',
    `material_type_enum` smallint NOT NULL DEFAULT '0' COMMENT '面料品类',
    `three_category_id` smallint NOT NULL DEFAULT '0' COMMENT '三级分类',
    `special_technology_tag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0：无特殊工艺，1：特殊工艺，',
    `special_technology_text` varchar(100) NOT NULL DEFAULT '' COMMENT '特殊工艺描述',
    `first_order` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否首单，0：否，1：是',
    `urgent` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否紧急，0：否，1：是',
    `supplier` varchar(50) NOT NULL DEFAULT '' COMMENT '供应商',
    `supplier_id` int NOT NULL DEFAULT '0' COMMENT '供应商id',
    `merchandiser` varchar(20) NOT NULL DEFAULT '' COMMENT '跟单员',
    `group_name` varchar(50) NOT NULL DEFAULT '' COMMENT '事业部',
    `group_id` varchar(50) NOT NULL DEFAULT '' COMMENT '事业部id',
    `receive_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '分单时间',
    `paper_make_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '做样时间，制作产前样的时间',
    `paper_finish_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '回样时间，完成产前样的时间',
    `create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建时间',
    `create_user` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
    `update_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
    `update_user` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0:否，1:是',
    `version` tinyint(1) NOT NULL DEFAULT '0' COMMENT '版本',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='生产订单表';