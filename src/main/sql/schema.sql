/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : localhost:3306
 Source Schema         : seckill

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 02/06/2018 17:12:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill`  (
`seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '秒杀商品名称',
`number` int(12) NOT NULL COMMENT '秒杀商品数量',
`start_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '秒杀开始时间',
`end_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀结束时间',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`seckill_id`) USING BTREE,
INDEX `idx_start_time`(`start_time`) USING BTREE,
INDEX `idx_end_time`(`end_time`) USING BTREE,
INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1004 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '秒杀活动表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES (1000, '1000元秒杀iphone6', 100, '2018-06-01 00:00:00', '2018-06-02 00:00:00', '2018-06-02 17:03:11');
INSERT INTO `seckill` VALUES (1001, '400元秒杀iPad2', 10, '2018-06-01 00:00:00', '2018-06-02 00:00:00', '2018-06-02 17:03:11');
INSERT INTO `seckill` VALUES (1002, '500元秒杀红米NOTE', 500, '2018-06-01 00:00:00', '2018-06-02 00:00:00', '2018-06-02 17:03:11');
INSERT INTO `seckill` VALUES (1003, '500元秒杀小米4', 100, '2018-06-01 00:00:00', '2018-06-02 00:00:00', '2018-06-02 17:03:11');

-- ----------------------------
-- Table structure for success_kill
-- ----------------------------
DROP TABLE IF EXISTS `success_kill`;
CREATE TABLE `success_kill`  (
`seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品id',
`user_phone` bigint(20) NOT NULL COMMENT '用户手机号',
`status` tinyint(4) NOT NULL COMMENT '状态显示 -1 无效 0成功  1已付款',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
PRIMARY KEY (`seckill_id`, `user_phone`) USING BTREE,
INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '秒杀成功明细表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
