/*
 Navicat MySQL Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : tanglong

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 08/05/2019 19:33:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` double(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, NULL, NULL, 1099.00);
INSERT INTO `goods` VALUES (2, '电冰箱', '家电', 1099.00);
INSERT INTO `goods` VALUES (3, '菜刀', '厨具', 1099.00);
INSERT INTO `goods` VALUES (4, '洗面奶', '护肤', 8.99);
INSERT INTO `goods` VALUES (5, '洗发水', '护肤', 56.99);
INSERT INTO `goods` VALUES (6, '苹果', '水果', 1099.00);
INSERT INTO `goods` VALUES (7, '儿童水杯', '日用品', 21.99);
INSERT INTO `goods` VALUES (8, '空调', '家电', 699.00);
INSERT INTO `goods` VALUES (10, '空调', '家电', 699.00);

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '家电');
INSERT INTO `type` VALUES (2, '厨具');
INSERT INTO `type` VALUES (3, '护肤');

SET FOREIGN_KEY_CHECKS = 1;
