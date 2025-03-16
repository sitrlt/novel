/*
 Navicat Premium Data Transfer

 Source Server         : ssm
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : novel

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 16/03/2025 14:01:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `activity_description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  `start_time` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `end_time` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `activity_status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '未开始',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, '月度读书分享会', '本次活动邀请各位读者分享自己本月读过的好书，交流读书心得和感悟。', '2025-04-10 14:00:00', '2025-04-10 16:00:00', '未开始', '2025-03-09 14:59:45');
INSERT INTO `activity` VALUES (2, '网络文学写作大赛', '面向广大文学爱好者举办的写作比赛，主题为“未来世界”，欢迎大家踊跃投稿。', '2025-05-01 00:00:00', '2025-06-30 23:59:59', '未开始', '2025-03-09 15:00:06');
INSERT INTO `activity` VALUES (3, '30 天阅读打卡挑战', '连续 30 天进行阅读打卡，坚持完成挑战的读者将有机会获得精美礼品。', '2025-06-01 00:00:00', '2025-06-30 23:59:59', '未开始', '2025-03-09 15:00:31');
INSERT INTO `activity` VALUES (4, '经典文学作品解读线上讲座', '邀请知名文学专家在线解读经典文学作品，帮助大家深入理解作品内涵。', '2025-04-15 18:00:00', '2025-04-15 21:00:00', '未开始', '2025-03-09 15:00:50');
INSERT INTO `activity` VALUES (5, '亲子阅读时光', '鼓励家长和孩子一起参与阅读，增进亲子关系，同时培养孩子的阅读习惯。', '2025-03-20 10:30:00', '2025-03-20 12:30:00', '未开始', '2025-03-09 15:18:01');

-- ----------------------------
-- Table structure for activity_feedback
-- ----------------------------
DROP TABLE IF EXISTS `activity_feedback`;
CREATE TABLE `activity_feedback`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `reader_id` int NOT NULL,
  `activity_id` int NOT NULL,
  `feedback_content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `feedback_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `activity_id`(`activity_id`) USING BTREE,
  INDEX `reader_id`(`reader_id`) USING BTREE,
  CONSTRAINT `activity_feedback_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `activity_feedback_ibfk_2` FOREIGN KEY (`reader_id`) REFERENCES `reader` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `isbn` varchar(13) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `author` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `borrowing_fee` int NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  `cover_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `publish_date` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `publisher_id` int NULL DEFAULT NULL,
  `ebook_path` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `is_ebook` tinyint(1) NOT NULL DEFAULT 1,
  `is_payable` tinyint(1) NULL DEFAULT 0,
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '审核中',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `isbn`(`isbn`) USING BTREE,
  INDEX `publisher_id`(`publisher_id`) USING BTREE,
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 345 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '9787539991236', '惊世神凰', '邹墨', 20, '强强, 嫡女, 神帝, 废柴, 女强, 穿越, 贵女\n,新书《小奶团被抢人生，五个舅舅来团宠》已发布~求支持和投喂.（双强+高甜+爽文+虐渣）她本是现代隐世家主，一朝穿越，成了异界第一废材？\n退婚？废材？特喵的睁大你们的钛合金狗眼看清楚！\n老娘分分钟让你们飞上天，与太阳肩并肩。一柄红扇倾天下，专治各种不服！\n纳尼？众人敬仰的帝尊大人表示不服？\n某女认怂，包袱款款，准备出逃，却被逮个正着。某男笑得风华绝代：“娘子，你是不是忘了，把我带走？”', 'http://localhost:8080/uploads/izbnf-iybvg.jpg', '1984-09-14', 1, NULL, 1, 1, '已审核');
INSERT INTO `book` VALUES (2, '5432345612221', '至尊神妃', '佰楚', 10, '【为你成神，所向披靡，1v1强强联手】\n　　她是世间最后一个神魔后裔，历劫而至，成了某人的心尖宠，“做本尊的神妃，这个世界都是你的！”\n　　神魔血统觉醒，她携复仇烈焰扭转乾坤，以悍然的实力威慑三界，创不灭神话。\n　　他是三界之尊，众神之首，疏离淡漠，亦妖亦仙，却为她颠覆乾坤，“孤云，你是我唯一的救赎！”', 'http://localhost:8080/uploads/60i46-hhf39.jpg', '2023-01-13', 1, NULL, 1, 1, '已审核');
INSERT INTO `book` VALUES (7, '5432345612123', '快穿系统：反派BOSS，放肆撩', '秦原', 8, '<1对1独宠甜文>霸道总裁？邪魅校草？腹黑少将？偏执少爷？统统攻略！久攻不下？呵呵，不存在的。没有攻略不下的男主，只有不努力的宿主。001系统非常的满意。它的宿主也很满意。不过········不是说好的攻略渣男主的吗？！这么一只病娇反派阴魂不散是个什么意思？？反派BOSS出场，还竟然每次都是他！一不小心攻略出一个超级反派大BOSS，变态又病娇，一言不合就开启屠杀模式，就这么一个蛇精病竟然还TM的是冥王？！宣云脂欲哭无泪，退货行不行？？喂喂喂！系统，这是怎么回事？？系统躲在墙角嘤嘤嘤，快点吧这个变态攻略掉，人家害怕~~~~《这是互宠文，互宠文，互宠文》', 'https://qidian.qpic.cn/qdbimg/349573/1011059479/300', '2021-08-28', 1, NULL, 1, 1, '已审核');
INSERT INTO `book` VALUES (8, '1432345612223', '师妹的修炼方法它不科学', '天边星星', 20, '女主是千年难遇的天灵根，她是资质最差的五灵根，为了修炼，前世的她成了女主的婢女。\r\n结果。女主一哭她挨骂；女主受伤她被罚；女主谈恋爱她惨死。很好，炮灰女配她不干了！快穿大佬云锦回到了最初的人生起点。这一次，她手握超级简化系统，什么男主女主，通通滚蛋。青莲剑典入门条件：天生剑体，悟性超绝，资质要求天灵根\r\n系统简化之后。\r\n云锦版青莲剑典：每天挥砍一百下。\r\n阴阳秘籍入门条件：身具绝品阴阳灵根，悟透阴阳意境。系统简化之后。云锦版阴阳秘籍：每天晒完太阳要记得晒月亮哦云锦躺着躺着，修为一路狂飙。女主居高临下：云锦，你这么懒散，怎么能增加修为啊？不如你来给我当婢女\r\n', 'https://bookcover.yuewen.com/qdbimg/349573/c_26825884809236204/180.webp', '2024-12-27', 1, NULL, 1, 1, '已审核');
INSERT INTO `book` VALUES (9, '5422345612223', '炮灰女配要反攻', '台之梦', 20, '　　【新文，《快穿攻略：我家宿主超凶的》欢迎支持】 “恭喜宿主，手撕白莲花+10086，荣获拆CP小能手的称号。”系统。 “……”顾浅羽。 “恭喜宿主，打脸心机表+10086，荣获拆CP小勇士的称号。”系统。 “……”顾浅羽。 “恭喜宿主，虐渣+10086，荣获拆CP不倒小金枪的称号。”系统。 “……”顾浅羽。 系统表示，总有刁民想害我们家浅羽，哼。 本文是成长流，女主一开始不是吊炸天，实力慢慢强悍的，喜欢看打脸爽的位面，请跳过前三个位面。 343198780（群号，弟子以上的人可以进来）', 'http://img.1391.com/api/v1/bookcenter/cover/1/3455665/3455665_2333eaf3a9e94e5c8f6b3260ce44361d.jpg/', '2024-02-05', 1, NULL, 1, 1, '已审核');
INSERT INTO `book` VALUES (10, '5432345612283', '始乱终弃了偏执大佬之后', '符莲', 20, '|穿书、偏执、甜宠|苏妁没想过自己只不过是在书评区留了个言，就把自己作进了刚看完的一本无CP的男主重生文里。还成了渣掉男主的女配。#渣掉偏执大佬之后我瑟瑟发抖#男主重生前，苏妁渣了男主之后，还害得男主破产，最后流落街头，被之前得罪的人推进海里溺死。男主重生后，逆天翻盘，凭借自己的记忆，不停虐渣打脸，最后成了帝都首屈一指的大佬。而苏妁，就是被虐的渣渣。*穿书后，系统001告诉苏妁，只要原著剧情走完，她就能离开这个世界。剧情结束那天，苏妁吞下瓶中药片，笑得无辜，神态却极端张扬。而薄景司却红着眼目光阴鸷地抓着她的手，“我不要你死！你死了，我让全世界陪葬！”眼睛一闭一睁，苏妁却又被001拉了回来，看着一双猩红的眼，苏妁：“……？”她要鲨了001！！！（1V1超甜~男主偏执病态蛇精病，女主苏甜娇软小可爱）', 'https://bookcover.yuewen.com/qdbimg/349573/c_15463100004320904/150', '2024-02-05', 1, NULL, 1, 1, '已审核');
INSERT INTO `book` VALUES (22, '5432345632223', '《三生三世十里桃花》', '唐七公子', 20, '讲述了青丘帝姬白浅和九重天太子夜华经历三段爱恨纠葛终成眷属的绝美仙恋故事，展现了一个奇幻的古代神话世界。', 'https://tse1-mm.cn.bing.net/th/id/OIP-C.QMm8ouBsFhDItgfIzA8AfgAAAA?w=123&h=184&c=7&r=0&o=5&dpr=1.3&pid=1.7', '2009-08-10', 1, 'ebook_3.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (23, '5432345612323', '《独步天下》', '李歆', 20, '讲述了现代少女步悠然穿越到清朝，与皇太极之间发生的一段传奇爱情故事，展现了清朝初期的历史风貌和宫廷斗争。', 'https://img1.baidu.com/it/u=3468627444,885403979&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=687', '2006-11-05', 1, 'ebook_4.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (24, '5432349612223', '《甄嬛传》', '流潋紫', 20, '讲述了少女甄嬛从一个不谙世事的闺阁女子，在复杂的宫廷斗争中逐渐成长为善于权谋的皇太后的故事，其中包含了众多的情感纠葛。', 'https://img1.baidu.com/it/u=3243704189,2444530275&fm=253&fmt=auto&app=138&f=JPEG?w=361&h=505', '2006-09-15', 1, 'ebook_5.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (25, '5432345612223', '《梦回大清》', '金子', 20, '讲述了一个现代女孩茗薇穿越到清朝康熙年间，与四阿哥、十三阿哥等发生的一系列爱恨情仇的故事，充满了历史的厚重感和浪漫的爱情元素。', 'https://b.bdstatic.com/b9eccb1478224fb2a62b1adcbfef5142.jpeg', '2004-07-25', 1, 'ebook_6.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (26, '5432345912223', '《兰陵缭乱》', 'Vivibear', 20, '以中国南北朝时期为背景，讲述了花木兰的后代、北齐第一美女兰陵王高长恭的传奇故事，展现了一段乱世中的爱情与家国情怀。', 'http://t15.baidu.com/it/u=2853234192,2408565217&fm=224&app=112&f=JPEG?w=248&h=332', '2008-03-18', 1, 'ebook_7.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (27, '5402345612223', '《木槿花西月锦绣》', '海飘雪', 20, '讲述了孪生姐妹花木槿和花锦绣，因机缘巧合与原非白、段月容等帅哥美男周旋，并发生了一系列爱恨情仇的故事，情节曲折离奇。', 'https://img3m6.ddimg.cn/13/30/1590069406-1_b_1.jpg', '2006-05-12', 1, 'ebook_8.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (28, '5432395612223', '《云中歌》', '桐华', 20, '讲述了西汉时期，绿衫女孩云歌与公子孟珏、汉昭帝刘弗陵之间发生的一段纠缠于国仇家恨的爱情故事，展现了西汉的宫廷风云。', 'https://img3m3.ddimg.cn/10/25/11205949303-1_b_1.jpg', '2006-11-01', 1, 'ebook_9.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (29, '9820101921222', '《秀丽江山之长歌行》', '李歆', 20, '以汉朝为背景，讲述了女主阴丽华在乱世中与刘秀相识相知，携手平定天下，最终成就一段盛世良缘的故事，展现了宏大的历史画卷。', 'https://img3m1.ddimg.cn/37/32/1465685731-1_b_3.jpg', '2007-04-20', 1, 'ebook_10.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (30, '4567876543234', '《蔓蔓青萝》', '桩桩', 20, '讲述了李青萝穿越到古代，在成长过程中与四皇子刘珏、子离等产生了一系列情感纠葛，最终找到真爱的故事，充满了青春浪漫气息。', 'https://img3m2.ddimg.cn/33/22/11754094902-1_b_1716393475.jpg', '2004-12-08', 1, 'ebook_11.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (31, '4323456776543', '《且试天下》', '倾泠月', 8, '讲述了主人公白风夕和黑丰息在江湖与朝堂之间的故事，他们携手闯荡江湖，最终成就了一段传奇的爱情与霸业。', 'https://img3m8.ddimg.cn/79/20/11929289308-1_b_1740962363.jpg', '2007-01-16', 1, 'ebook_12.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (32, '3456787654345', '《大漠谣》', '桐华', 8, '以西汉时期为背景，讲述了狼女金玉从大漠来到中原，与霍去病、孟西漠之间发生的一段曲折动人的爱情故事，展现了大漠与中原的不同风情。', 'http://t14.baidu.com/it/u=4162729876,1021828415&fm=224&app=112&f=JPEG?w=440&h=440', '2006-04-05', 1, 'ebook_13.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (33, '5434567654345', '《美人殇》', '梦三生', 8, '讲述了现代女孩王沫穿越到三国时期，与吕布、曹操等人物之间发生的爱恨情仇，展现了三国乱世中的爱情与权谋。', 'https://img0.baidu.com/it/u=176363165,3957150919&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '2006-07-28', 1, 'ebook_14.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (34, '3456787654334', '《孤芳不自赏》', '风弄', 8, '讲述了敬安王的侍女白娉婷与晋国镇北王楚北捷之间的爱情故事，他们在乱世中相互扶持，共同面对各种挑战。', 'https://img3m5.ddimg.cn/76/7/11168957425-1_b_1.jpg', '2007-09-10', 1, 'ebook_15.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (35, '4567876543224', '《醉玲珑》', '十四夜', 8, '讲述了凤卿尘与元凌在双时空中的皇子夺嫡时，二人出生入死、相爱相伴的故事，展现了一个奇幻的古代世界。', 'https://img3m6.ddimg.cn/16/0/29277286-1_b_10.jpg', '2006-12-03', 1, 'ebook_16.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (36, '3456789876543', '《祸国·图璧》', '十四阙', 8, '以战国时期为背景，讲述了女主叶曦禾在乱世中与男主之间的爱恨情仇，以及她在权力斗争中的挣扎与成长，情节跌宕起伏。', 'https://img3m7.ddimg.cn/81/6/11941273557-1_b_1734794750.jpg', '2008-06-18', 1, 'ebook_17.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (37, '3456787654324', '《执子之手，将子拖走》', '天下归元', 8, '讲述了一个现代女孩穿越到古代，与傲娇王爷之间发生的一系列搞笑又温馨的故事，轻松幽默中透着浪漫的爱情。', 'https://img3m0.ddimg.cn/36/12/11882596410-1_b_1734697764.jpg', '2009-02-11', 1, 'ebook_18.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (38, '1234567897654', '《月上重火》', '君子以泽', 8, '讲述了重火宫前宫主重烨之女重雪芝与月上谷谷主上官透的爱情故事，他们在江湖恩怨中相互扶持，最终走到一起。', 'https://tse3-mm.cn.bing.net/th/id/OIP-C.gv7YdOaH4SpQYzlCo9iqbAHaHa?w=174&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7', '2009-05-25', 1, 'ebook_19.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (40, '2345676543234', '将军，夫人又去给您的马接生了', '李安文', 8, '本是国医圣手，一遭穿书，成了个又丑又胖的恶毒继母。\r\n人人都道，她心思歹毒，容貌凶悍，谁家沾上谁家倒了血霉？\r\n可是，她那瘫在床上的残疾夫君，被她养成了护国神将。\r\n阴鸷狠辣的反派大儿子，被她养成了翩翩状元郎。\r\n恋爱脑二儿子，被她养成了举世神医。\r\n恶毒女配小女儿，被她养成根正苗红的上进小公主。', 'https://bookcover.yuewen.com/qdbimg/349573/c_29919050604629107/180.webp', '2024-01-02', 1, NULL, 1, 1, '已审核');
INSERT INTO `book` VALUES (41, '1234567876543', '清冷美人总腿软，疯批大佬太狠了', '臣言啊', 8, '（港圈大佬+疯批卧底+双马甲+双洁+追妻火葬场+极限拉扯）\r\n　　\r\n　　港圈财阀世家林氏的继承人林琛，是个神秘的纨绔子弟，无人见过他的真实面容。\r\n　　坊间传闻他在北美留学，是个清冷禁欲的丁克不婚族，任何女人都难近他身。\r\n　　只有陆司琪知道，他那清冷禁欲的背后，是何等的疯批……', 'https://bookcover.yuewen.com/qdbimg/349573/c_30835165004652608/180.webp', '2024-09-12', 1, NULL, 1, 1, '已审核');
INSERT INTO `book` VALUES (42, '1234565432234', '钓系公主不追了，清冷首辅火葬场', '汀州里鹤', 0, '【娇蛮肆意张扬公主×克己复礼禁欲首辅】\r\n长安城人人皆知，人嫌狗憎的昭明公主看上了一位进京赶考的穷书生。\r\n为了追求他，昭明公主利用权势欲逼他就范，只可惜穷书生宁折不弯，视若无睹。', 'https://bookcover.yuewen.com/qdbimg/349573/c_31078256204550509/180.webp', '2023-01-301', 1, NULL, 1, 0, '已审核');
INSERT INTO `book` VALUES (43, '1234567643211', '与前男友在婚礼上重逢', '三月棠墨', 0, '徐衍风一朋友在酒店碰见了熟人。\r\n那熟人正是徐衍风大学时期交的女朋友，已成过去式。\r\n两人打了个照面，朋友转头就跟徐衍风通风报信：“你猜我瞧见谁了？你前女友！那个玩弄你感情的女人！她今天在熙庭酒店办婚礼，你不来砸场子？”\r\n徐衍风只有一位前女友，且是初恋，那个叫夏熙的女人。', 'https://bookcover.yuewen.com/qdbimg/349573/c_29711211003856004/180.webp', '2023-04-12', 2, NULL, 1, 0, '已审核');
INSERT INTO `book` VALUES (44, '9787532174232', '知否知否应是绿肥红瘦', '关心则乱', 0, '作品通过北宋官宦家庭少女盛明兰的成长、爱情、婚姻故事，展开了一幅由闺阁少女到侯门主母的生活画卷，讲述一个家宅的兴荣，古代礼教制度下的女性奋斗传奇。', 'https://img3m9.ddimg.cn/49/2/1431753889-1_b_1734500396.jpg', '2010-07-01', 2, 'zhifou_ebook.pdf', 1, 0, '已审核');
INSERT INTO `book` VALUES (45, '9787559432176', '锦心似玉', '吱吱', 0, '故事围绕罗十一娘展开，她在复杂的侯府环境中，凭借自身的聪慧和坚韧，收获了爱情与成长，展现了古代深宅大院中的生活百态。', 'https://img3m8.ddimg.cn/65/12/699463478-1_b_2.jpg', '2019-05-01', 2, 'jinxin_ebook.pdf', 1, 0, '已审核');
INSERT INTO `book` VALUES (46, '9787229123456', '庶女有毒', '秦简', 0, '讲述了相府庶女李未央前世一朝为后却不得善终，重生后步步为营，在乱世中逆袭的故事，情节跌宕起伏，充满权谋斗争。', 'https://img3m2.ddimg.cn/26/19/11874180212-1_b_1736739880.jpg', '2013-03-01', 2, 'shunv_ebook.pdf', 1, 0, '已审核');
INSERT INTO `book` VALUES (47, '9787550028765', '嫡谋', '面北眉南', 0, '任瑶期重生后，凭借着上辈子的记忆和经验，在复杂的家族环境中周旋，为自己和家人谋得幸福，展现了古代家族的恩怨情仇。', 'https://img3m4.ddimg.cn/72/23/11195303994-1_b_1.jpg', '2015-08-01', 2, 'dimou_ebook.pdf', 1, 0, '已审核');
INSERT INTO `book` VALUES (48, '9787539978901', '惜花芷', '空留', 0, '女主角花芷重生后，以柔弱之躯扛起家族重担，在风雨飘摇中守护家人，同时收获了美好的爱情，是一部充满励志与温情的古言佳作。', 'https://img0.baidu.com/it/u=390489214,2161919322&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667', '2017-04-01', 2, 'xihuazhi_ebook.pdf', 1, 0, '已审核');
INSERT INTO `book` VALUES (49, '9787100123456', '人类简史：从动物到上帝', '尤瓦尔·赫拉利', 0, '以从全新的角度阐述了人类从原始石器时代到 21 世纪的发展历程，探讨了人类是如何通过认知革命、农业革命和科学革命改变了自身和世界的命运。', 'https://img3m3.ddimg.cn/4/13/11681610043-1_b_1705599865.jpg', '2014-11-01', 2, 'renleijianshi_ebook.pdf', 0, 0, '已审核');
INSERT INTO `book` VALUES (50, '9787544289012', '明朝那些事儿', '当年明月', 0, '用幽默风趣的语言讲述了从 1344 年到 1644 年这三百年间关于明朝的一些故事，以史料为基础，以年代和具体人物为主线，对明朝十七帝和其他王公权贵和小人物的命运进行全景展示。', 'https://img3m5.ddimg.cn/22/10/29296795-1_b_3.jpg', '2006-09-01', 2, 'mingchaonaxieshier_ebook.pdf', 0, 0, '已审核');
INSERT INTO `book` VALUES (51, '9787101134567', '史记', '司马迁', 0, '中国第一部纪传体通史，记载了上至上古传说中的黄帝时代，下至汉武帝太初四年间共 3000 多年的历史，对后世史学和文学产生了深远影响。', 'https://img3m4.ddimg.cn/96/11/23861274-1_b_17.jpg', '1959-09-01', 2, 'shiji_ebook.pdf', 0, 0, '已审核');
INSERT INTO `book` VALUES (52, '9787532189012', '全球通史：从史前史到 21 世纪', '斯塔夫里阿诺斯', 0, '内容上起人类的起源，下迄 20 世纪 70 年代多极世界相对峙时期，涉及政治、经济、军事、文化、教育等方面，是一部颇具影响力的世界历史著作。', 'https://img3m5.ddimg.cn/13/30/11908055425-1_b_1739953798.jpg', '2005-06-01', 2, 'quanqiutongshi_ebook.pdf', 0, 0, '已审核');
INSERT INTO `book` VALUES (53, '9787100145678', '时间简史', '斯蒂芬·霍金', 0, '介绍了宇宙的起源、黑洞、时间旅行等前沿科学概念，以通俗易懂的语言激发了人们对宇宙科学的兴趣和思考。', 'https://img0.baidu.com/it/u=2917148778,2464631895&fm=253&fmt=auto&app=138&f=JPG?w=640&h=426', '1988-04-01', 2, 'shijianjianshi_ebook.pdf', 0, 0, '已审核');
INSERT INTO `book` VALUES (54, '9787559637890', '你是我的荣耀', '顾漫', 0, '故事讲述了人气女星乔晶晶在游戏中与曾经的高中同学、现为航天设计师的于途意外重逢，两人在相处中逐渐产生感情，展现了浪漫的都市爱情故事。', 'https://img3m4.ddimg.cn/94/0/27869584-1_b_1723095414.jpg', '2019-06-01', 2, 'rongyao_ebook.pdf', 0, 0, '已审核');
INSERT INTO `book` VALUES (55, '9787533956789', '何以笙箫默', '顾漫', 10, '讲述了何以琛和赵默笙一段年少时的爱恋牵出一生的纠缠，一个执着于等待和相爱的故事，充满了青春的回忆与爱情的坚守。', 'https://img3m4.ddimg.cn/40/10/29690734-1_b_1715766849.jpg', '2003-09-01', 2, 'heyi_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (56, '9787544278901', '微微一笑很倾城', '顾漫', 10, '计算机系花贝微微在游戏中结识了大神肖奈，两人从游戏走向现实，演绎了一段浪漫甜蜜的爱情故事，展现了青春校园与互联网时代的爱情风貌。', 'https://img3m9.ddimg.cn/26/35/11604313439-1_b_1702523354.jpg', '2009-08-01', 2, 'weiwei_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (57, '9787550045678', '原来你还在这里', '辛夷坞', 10, '以苏韵锦和程铮的爱情为主线，讲述了两人从青涩的校园时光到步入社会后的情感纠葛，展现了爱情的曲折与美好。', 'https://img3m3.ddimg.cn/8/35/11760809453-1_b_1717658239.jpg', '2007-11-01', 2, 'yuanlai_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (58, '9787539989012', '致我们单纯的小美好', '赵乾乾', 10, '讲述了陈小希与江辰19年间共同成长，从两小无猜到错失后的再次牵手的爱情故事，充满了青春的纯真与美好。', 'https://img3m7.ddimg.cn/70/34/11771527057-1_b_1720168855.jpg', '2014-02-01', 2, 'meihao_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (59, '9787111567890', '资本论', '卡尔·马克思', 10, '以剩余价值为中心，对资本主义进行了彻底的批判，同时也是马克思主义政治经济学的重要著作，对理解资本主义经济运行规律有重要意义。', 'https://q7.itc.cn/q_70/images03/20241016/c0b17110e13f4a558909bccb7a8b707e.jpeg', '1867-09-01', 2, 'zibenlun_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (60, '9787559656789', '穷爸爸富爸爸', '罗伯特·清崎', 10, '通过对比“穷爸爸”和“富爸爸”截然不同的金钱观和财富观，揭示了在财务方面的智慧和投资理念，引导读者树立正确的理财观念。', 'https://img1.baidu.com/it/u=3559980229,1991680786&fm=253&fmt=auto&app=138&f=JPEG?w=609&h=474', '1997-04-01', 2, 'qiongbabafubaba_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (61, '9787100156789', '经济学原理', 'N·格里高利·曼昆', 10, '分为微观经济学和宏观经济学两部分，以通俗易懂的方式介绍了经济学的基本原理和方法，是经济学领域的经典入门教材。', 'https://img3m3.ddimg.cn/48/15/23697183-1_b_1.jpg', '1998-02-01', 2, 'jingjixueyuanli_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (62, '9787533901234', '小狗钱钱', '博多·舍费尔', 10, '以故事的形式讲述了如何从小学会认识和使用金钱，以及如何进行理财投资，是一本适合儿童和青少年阅读的理财启蒙书籍。', 'https://img2.baidu.com/it/u=3936414513,4095676179&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=727', '1996-08-01', 2, 'xiaogouqianqian_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (63, '9787544201234', '黑天鹅：如何应对不可预知的未来', '纳西姆·尼古拉斯·塔勒布', 10, '探讨了极其罕见、不可预测但却具有重大影响力的事件，即“黑天鹅事件”，以及如何在不确定性的世界中应对这些事件，对投资和风险管理有重要启示。', 'https://img3m7.ddimg.cn/91/4/11968405507-1_b_1737900181.jpg', '2007-05-01', 2, 'heitiane_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (64, '9787559667890', '斗破苍穹', '天蚕土豆', 10, '讲述了天才少年萧炎在创造了家族空前绝后的修炼纪录后突然成了废人，种种打击接踵而至，但他却不屈不挠，在逆境中不断成长，最终走向巅峰的故事。', 'https://img3m4.ddimg.cn/88/17/29213404-1_b_4.jpg', '2009-04-01', 2, 'doupocangqiong_ebook.pdf', 1, 1, '已审核');
INSERT INTO `book` VALUES (65, '9787533912345', '斗罗大陆', '唐家三少', 10, '描绘了一个充满武魂的斗罗大陆世界，主角唐三凭借自己的努力和智慧，在这个世界中不断成长，与伙伴们一起经历了无数冒险，是一部极具想象力的玄幻佳作。', 'https://img3m5.ddimg.cn/34/34/12100013575-1_b_1701415381.jpg', '2008-12-01', 2, 'duoludalu_ebook.pdf', 1, 1, '已审核');
INSERT INTO `book` VALUES (66, '9787544212345', '遮天', '辰东', 10, '讲述了叶凡在九龙拉棺的机缘下，穿越到北斗星域，从此踏上了一条逆天修行之路，在这个过程中，他揭开了诸多神秘面纱，经历了无数的挑战和奇遇。', 'https://img3m3.ddimg.cn/64/16/11869845733-1_b_1734818289.jpg', '2009-12-01', 2, 'zhetian_ebook.pdf', 1, 1, '已审核');
INSERT INTO `book` VALUES (67, '9787550067890', '诛仙', '萧鼎', 10, '以一个少年张小凡的经历为主线，讲述了他在青云门、魔教等多方势力之间的挣扎与成长，展现了一个宏大的仙侠玄幻世界，充满了爱恨情仇与侠义精神。', 'https://img3m2.ddimg.cn/98/1/11753589572-1_b_1716381599.jpg', '2003-04-01', 2, 'zhuxian_ebook.pdf', 1, 1, '已审核');
INSERT INTO `book` VALUES (68, '9787539902345', '神墓', '辰东', 10, '一个死去万载岁月的平凡青年从远古神墓中复活而出，悠悠万载，沧海桑田，他开始了逆天改命之路，探寻神墓的秘密，情节跌宕起伏，充满奇幻色彩。', 'https://img3m9.ddimg.cn/54/3/11888046279-1_b_1733815446.jpg', '2006-07-01', 2, 'shenmu_ebook.pdf', 1, 1, '已审核');
INSERT INTO `book` VALUES (69, '9787559645678', '乌合之众：大众心理研究', '古斯塔夫·勒庞', 10, '深入探讨了群体心理的特点和影响，揭示了群体在某些情况下表现出的非理性行为和心理机制，是群体心理学领域的经典之作。', 'https://img14.360buyimg.com/pop/jfs/t1/37000/15/26/130706/5ca5d5b6E6c0dca45/9cd7639ac4ce59aa.jpg', '1895-01-01', 2, 'wuhezhizhong_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (70, '9787533990123', '乡土中国', '费孝通', 10, '是费孝通著述的一部研究中国农村的作品，从乡村社区、文化传递、家族制度、道德观念等多个方面剖析了中国乡土社会的结构和特点。', 'https://img1.baidu.com/it/u=1650463871,1238580518&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=625', '1947-04-01', 2, 'xiangtuzhongguo_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (71, '9787544290123', '百年孤独', '加西亚·马尔克斯', 10, '讲述了布恩迪亚家族七代人的传奇故事，以及加勒比海沿岸小镇马孔多的百年兴衰，反映了拉丁美洲一个世纪以来风云变幻的历史，充满魔幻现实主义色彩。', 'https://img2.baidu.com/it/u=3449340288,1546098534&fm=253&fmt=auto&app=120&f=JPEG?w=712&h=1006', '1967-06-01', 2, 'bainianbudu_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (72, '9787550056789', '文化苦旅', '余秋雨', 10, '通过对中国历史文化的追溯和思考，以散文的形式展现了作者在文化探寻过程中的感悟和思考，兼具文化内涵和文学价值。', 'https://img3m3.ddimg.cn/82/7/11687299453-1_b_1705898536.jpg', '1992-03-01', 2, 'wenhuakulu_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (73, '9787539991234', '平凡的世界', '路遥', 10, '以中国 70 年代中期到 80 年代中期 10 年间为背景，通过复杂的矛盾纠葛，以孙少安和孙少平两兄弟为中心，展示了普通人在大时代历史进程中所走过的艰难而又充满希望的道路。', 'https://img2.baidu.com/it/u=3859913840,2361485867&fm=253&fmt=auto&app=120&f=JPEG?w=286&h=381', '1986-12-01', 2, 'pingfandeshijie_ebook.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (79, '9787101123456', '中国通史', '吕思勉', 10, '介绍中国历史的书籍', 'https://img3m1.ddimg.cn/43/17/23655301-1_b_13.jpg', '2023-01-01', 1, 'https://example.com/ebook1.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (80, '9787302567890', '世界历史', 'H.G. Wells', 10, '介绍世界历史的书籍', 'https://img3m2.ddimg.cn/87/8/25859382-1_b_5.jpg', '2023-02-01', 1, 'https://example.com/ebook2.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (82, '9787108012345', '国史大纲', '钱穆', 10, '中国通史著作', 'https://img3m6.ddimg.cn/7/12/29444686-1_b_2.jpg', '2023-04-01', 1, 'https://example.com/ebook4.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (83, '9787111234567', '中国历代政治得失', '钱穆', 10, '中国政治制度史', 'https://img3m3.ddimg.cn/6/25/29192433-1_b_1714356697.jpg', '2023-05-01', 1, 'https://example.com/ebook5.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (85, '9787100345679', '中国哲学简史', '冯友兰', 10, '中国哲学史', 'https://img3m9.ddimg.cn/91/35/25111639-1_b_1.jpg', '2023-07-01', 1, 'https://example.com/ebook7.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (86, '9787108456787', '论语译注', '杨伯峻', 10, '儒家经典解释', 'https://img3m8.ddimg.cn/87/1/29831658-1_b_1735101975.jpg', '2023-08-01', 1, 'https://example.com/ebook8.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (87, '9787101567890', '孙子兵法', '孙武', 10, '古代军事著作', 'https://img3m2.ddimg.cn/86/8/29368832-1_b_4.jpg', '2023-09-01', 1, 'https://example.com/ebook9.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (88, '9787302678904', '资治通鉴', '司马光', 10, '中国古代编年体史书', 'https://img3m7.ddimg.cn/94/24/25111147-1_b_1716543791.jpg', '2023-10-01', 1, 'https://example.com/ebook10.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (89, '9787100678911', '古文观止', '吴楚材、吴调侯', 10, '古代散文选集', 'https://img3m2.ddimg.cn/10/32/24021172-1_b_18.jpg', '2023-11-01', 1, 'https://example.com/ebook11.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (90, '9787108789012', '诗经', '无名氏', 10, '中国古代诗歌总集', 'https://img3m6.ddimg.cn/15/26/28970286-1_b_10.jpg', '2023-12-01', 1, 'https://example.com/ebook12.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (91, '9787101890123', '楚辞', '屈原', 10, '中国古代诗歌集', 'https://img3m7.ddimg.cn/86/16/27916997-1_b_12.jpg', '2024-01-01', 1, 'https://example.com/ebook13.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (92, '9787302901234', '红楼梦', '曹雪芹', 10, '中国古典小说', 'https://img3m1.ddimg.cn/9/22/102771-1_b_1691995673.jpg', '2024-02-01', 1, 'https://example.com/ebook14.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (93, '9787100101235', '西游记', '吴承恩', 10, '中国古典小说', 'https://img3m6.ddimg.cn/89/33/104336-1_b_1692404527.jpg', '2024-03-01', 1, 'https://example.com/ebook15.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (94, '9787108111236', '水浒传', '施耐庵', 10, '中国古典小说', 'https://img3m2.ddimg.cn/35/7/105272-1_b_5.jpg', '2024-04-01', 1, 'https://example.com/ebook16.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (95, '9787101211237', '三国演义', '罗贯中', 10, '中国古典小说', 'https://img3m6.ddimg.cn/8/30/29193326-1_b_9.jpg', '2024-05-01', 1, 'https://example.com/ebook17.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (96, '9787302311238', '儒林外史', '吴敬梓', 10, '中国古典小说', 'https://img3m2.ddimg.cn/16/1/28995532-1_b_11.jpg', '2024-06-01', 1, 'https://example.com/ebook18.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (97, '9787100411239', '聊斋志异', '蒲松龄', 10, '中国古典小说', 'https://img3m7.ddimg.cn/51/0/25299897-1_b_8.jpg', '2024-07-01', 1, 'https://example.com/ebook19.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (98, '9787108511230', '东周列国志', '冯梦龙', 10, '中国历史小说', 'https://img3m5.ddimg.cn/92/28/23486555-1_b_1716791813.jpg', '2024-08-01', 1, 'https://example.com/ebook20.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (305, '9787121345678', '社会学原理', 'Anthony Giddens', 10, '介绍社会学原理的书籍', 'https://img3m1.ddimg.cn/39/10/11865748791-1_b_1728873894.jpg', '2023-01-01', 1, 'https://example.com/ebook21.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (306, '9787122456789', '文化研究导论', 'Simon During', 10, '介绍文化研究的书籍', 'https://img3m1.ddimg.cn/61/0/29355541-1_b_2.jpg', '2023-02-01', 1, 'https://example.com/ebook22.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (308, '9787124567890', '人类简史', '尤瓦尔·赫拉利', 10, '人类历史概述', 'https://img3m6.ddimg.cn/33/6/29127516-1_b_2.jpg', '2023-04-01', 1, 'https://example.com/ebook24.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (309, '9787125678901', '社会学的想象力', 'C. Wright Mills', 10, '社会学理论', 'https://img3m5.ddimg.cn/57/7/613669575-1_b_1.jpg', '2023-05-01', 1, 'https://example.com/ebook25.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (310, '9787126789012', '现代性的后果', '安东尼·吉登斯', 10, '现代社会分析', 'https://img3m7.ddimg.cn/36/27/27865467-1_b_2.jpg', '2023-06-01', 1, 'https://example.com/ebook26.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (311, '9787127890123', '社会学', '吉登斯', 10, '社会学概论', 'https://img3m8.ddimg.cn/68/17/29321888-1_b_12.jpg', '2023-07-01', 1, 'https://example.com/ebook27.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (312, '9787128901234', '文化与社会', '雷蒙德·威廉斯', 10, '文化与社会的关系', 'https://img3m5.ddimg.cn/93/19/1449793515-1_b_1701392070.jpg', '2023-08-01', 1, 'https://example.com/ebook28.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (313, '9787129012345', '社会学入门', '安东尼·吉登斯', 10, '社会学入门书籍', 'https://img3m0.ddimg.cn/55/3/11737493020-1_b_1713030724.jpg', '2023-09-01', 1, 'https://example.com/ebook29.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (314, '9787130123456', '社会学理论的结构', '安东尼·吉登斯', 10, '社会学理论结构', 'https://img3m2.ddimg.cn/5/33/11852329802-1_b_1732613608.jpg', '2023-10-01', 1, 'https://example.com/ebook30.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (315, '9787131234567', '社会学主要思潮', '雷蒙德·阿隆', 10, '社会学思潮', 'https://img3m4.ddimg.cn/28/1/29570734-1_b_2.jpg', '2023-11-01', 1, 'https://example.com/ebook31.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (316, '9787132345678', '社会学的意识', '安东尼·吉登斯', 10, '社会学意识', 'https://img3m2.ddimg.cn/1/1/26435872-1_b_3.jpg', '2023-12-01', 2, 'https://example.com/ebook32.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (317, '9787133456789', '社会学的想象力', 'C. Wright Mills', 10, '社会学想象力', 'https://img3m9.ddimg.cn/36/36/29197809-1_b_8.jpg', '2024-01-01', 2, 'https://example.com/ebook33.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (318, '9787134567890', '社会学与人类学', '克利福德·盖尔茨', 10, '社会学与人类学', 'https://img3m7.ddimg.cn/26/33/29796947-1_b_1731056451.jpg', '2024-02-01', 2, 'https://example.com/ebook34.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (319, '9787135678901', '社会学方法论', '马克斯·韦伯', 10, '社会学方法论', 'https://img3m3.ddimg.cn/6/3/29251833-1_b_3.jpg', '2024-03-01', 2, 'https://example.com/ebook35.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (320, '9787136789012', '社会学的基本概念', '安东尼·吉登斯', 10, '社会学基本概念', 'https://img3m5.ddimg.cn/97/16/28968685-1_b_3.jpg', '2024-04-01', 2, 'https://example.com/ebook36.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (321, '9787137890123', '社会学的界限', '安东尼·吉登斯', 10, '社会学界限', 'https://img3m0.ddimg.cn/83/8/11743314050-1_b_1713936332.jpg', '2024-05-01', 2, 'https://example.com/ebook37.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (322, '9787138901234', '社会学的历史', '彼得·伯杰', 10, '社会学历史', 'https://img3m0.ddimg.cn/28/16/25278490-1_b_5.jpg', '2024-06-01', 2, 'https://example.com/ebook38.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (323, '9787139012345', '社会学的实践', '安东尼·吉登斯', 10, '社会学实践', 'https://img3m2.ddimg.cn/64/20/29481472-1_b_6.jpg', '2024-07-01', 2, 'https://example.com/ebook39.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (324, '9787140123456', '社会学的未来', '安东尼·吉登斯', 10, '社会学未来', 'https://img3m0.ddimg.cn/56/29/11879507630-1_b_1734199361.jpg', '2024-08-01', 2, 'https://example.com/ebook40.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (325, '9787141345678', '经济学原理', 'N. Gregory Mankiw', 10, '经济学入门书籍', 'https://img3m7.ddimg.cn/90/15/29333097-1_b_1729247292.jpg', '2023-01-01', 2, 'https://example.com/ebook41.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (326, '9787142456780', '宏观经济学', 'Olivier Blanchard', 10, '宏观经济学原理', 'https://img3m3.ddimg.cn/38/30/26909723-1_b_6.jpg', '2023-02-01', 2, 'https://example.com/ebook42.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (327, '9787143456780', '微观经济学', 'Paul Krugman', 10, '微观经济学原理', 'https://img3m1.ddimg.cn/86/12/28516541-1_b_1692335734.jpg', '2023-03-01', 2, 'https://example.com/ebook43.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (328, '9787144567890', '发展经济学', 'Dante B. Canlas', 10, '经济发展研究', 'https://img3m0.ddimg.cn/64/3/29802430-1_b_1731322129.jpg', '2023-04-01', 2, 'https://example.com/ebook44.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (329, '9787145678901', '国际经济学', 'Paul R. Krugman', 10, '国际经济学原理', 'https://img3m7.ddimg.cn/84/2/29202807-1_b_3.jpg', '2023-05-01', 2, 'https://example.com/ebook45.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (330, '9787146789012', '货币银行学', 'Frederic S. Mishkin', 10, '货币与银行学', 'https://img3m5.ddimg.cn/57/18/29134965-1_b_3.jpg', '2023-06-01', 2, 'https://example.com/ebook46.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (331, '9787147890123', '财政学', 'Harvey Rosen', 10, '财政学原理', 'https://img3m3.ddimg.cn/8/34/28546163-1_b_1692335725.jpg', '2023-07-01', 2, 'https://example.com/ebook47.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (332, '9787148901234', '计量经济学', 'Damodar N. Gujarati', 10, '计量经济学原理', 'https://img3m3.ddimg.cn/42/31/29432643-1_b_8.jpg', '2023-08-01', 2, 'https://example.com/ebook48.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (333, '9787149012345', '经济学思想史', 'Robert L. Hebert', 10, '经济学思想发展', 'https://img3m2.ddimg.cn/64/9/25262092-1_b_5.jpg', '2023-09-01', 2, 'https://example.com/ebook49.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (334, '9787150123456', '环境经济学', 'Robert N. Stavins', 10, '环境经济分析', 'https://img3m9.ddimg.cn/23/31/11895008819-1_b_1735115031.jpg', '2023-10-01', 2, 'https://example.com/ebook50.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (335, '9787151234567', '健康经济学', 'Sherman Folland & Allen C. Goodman', 10, '健康经济分析', 'https://img3m2.ddimg.cn/68/34/1574948102-1_b_1.jpg', '2023-11-01', 2, 'https://example.com/ebook51.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (336, '9787152345678', '教育经济学', 'C. M. Belfield & H. M. Levin', 10, '教育经济分析', 'https://img3m5.ddimg.cn/24/11/11179743225-1_b_1.jpg', '2023-12-01', 2, 'https://example.com/ebook52.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (337, '9787153456789', '劳动经济学', 'Harry J. Holzer', 10, '劳动经济分析', 'https://img3m1.ddimg.cn/38/36/29775971-1_b_1725930862.jpg', '2024-01-01', 2, 'https://example.com/ebook53.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (338, '9787154567890', '产业组织', 'William Baumol', 10, '产业组织理论', 'https://img3m7.ddimg.cn/96/10/25326177-1_b_3.jpg', '2024-02-01', 2, 'https://example.com/ebook54.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (339, '9787155678901', '公共经济学', 'Harvey Rosen', 10, '公共经济学原理', 'https://img3m2.ddimg.cn/78/5/29337342-1_b_1.jpg', '2024-03-01', 2, 'https://example.com/ebook55.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (340, '9787156789012', '城市经济学', 'Arthur O\'Sullivan', 10, '城市经济分析', 'https://img3m1.ddimg.cn/34/3/29470651-1_b_2.jpg', '2024-04-01', 2, 'https://example.com/ebook56.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (341, '9787157890123', '农业经济学', 'J. D. Gorman', 10, '农业经济分析', 'https://img3m8.ddimg.cn/51/1/29505318-1_b_1.jpg', '2024-05-01', 2, 'https://example.com/ebook57.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (342, '9787158901234', '金融经济学', 'R. C. K. Burdekin', 10, '金融经济分析', 'https://img3m9.ddimg.cn/80/24/25320419-1_b_8.jpg', '2024-06-01', 2, 'https://example.com/ebook58.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (343, '9787159012345', '行为经济学', 'Dan Ariely', 10, '行为经济分析', 'https://img3m8.ddimg.cn/43/0/25232668-1_b_4.jpg', '2024-07-01', 2, 'https://example.com/ebook59.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (344, '9787160123456', '信息经济学', 'Hal R. Varian', 10, '信息经济分析', 'https://img3m1.ddimg.cn/65/13/29398511-1_b_3.jpg', '2024-08-01', 2, 'https://example.com/ebook60.pdf', 0, 1, '已审核');
INSERT INTO `book` VALUES (345, '1822118881121', '我喜欢你', '宿迁', 10, '描述巴拉巴拉', 'http://localhost:8080/uploads/20225597201217.jpg', '2025-03-14', 2, NULL, 1, 0, '待审核');

-- ----------------------------
-- Table structure for book_inventory
-- ----------------------------
DROP TABLE IF EXISTS `book_inventory`;
CREATE TABLE `book_inventory`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `total_copies` int NOT NULL,
  `available_copies` int NOT NULL,
  `publisher_id` int NOT NULL,
  `book_isbn` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `publisher_id`(`publisher_id`) USING BTREE,
  INDEX `book_isbn`(`book_isbn`) USING BTREE,
  CONSTRAINT `book_inventory_ibfk_2` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `book_inventory_ibfk_3` FOREIGN KEY (`book_isbn`) REFERENCES `book` (`isbn`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_inventory
-- ----------------------------
INSERT INTO `book_inventory` VALUES (54, 100, 43, 1, '5432345632223');
INSERT INTO `book_inventory` VALUES (55, 100, 30, 1, '5432345612323');
INSERT INTO `book_inventory` VALUES (56, 100, 9, 1, '5432349612223');
INSERT INTO `book_inventory` VALUES (57, 100, 22, 1, '5432345612223');
INSERT INTO `book_inventory` VALUES (58, 100, 11, 1, '5432345912223');
INSERT INTO `book_inventory` VALUES (59, 100, 22, 1, '5402345612223');
INSERT INTO `book_inventory` VALUES (60, 100, 44, 1, '5432395612223');
INSERT INTO `book_inventory` VALUES (61, 100, 66, 1, '9820101921222');
INSERT INTO `book_inventory` VALUES (62, 100, 76, 1, '4567876543234');
INSERT INTO `book_inventory` VALUES (63, 100, 88, 1, '4323456776543');
INSERT INTO `book_inventory` VALUES (64, 100, 98, 1, '3456787654345');
INSERT INTO `book_inventory` VALUES (65, 100, 65, 1, '5434567654345');
INSERT INTO `book_inventory` VALUES (66, 100, 22, 1, '3456787654334');
INSERT INTO `book_inventory` VALUES (67, 100, 11, 1, '4567876543224');
INSERT INTO `book_inventory` VALUES (68, 10, 3, 1, '3456789876543');
INSERT INTO `book_inventory` VALUES (69, 100, 21, 1, '3456787654324');
INSERT INTO `book_inventory` VALUES (70, 100, 41, 1, '1234567897654');
INSERT INTO `book_inventory` VALUES (75, 100, 44, 2, '9787100123456');
INSERT INTO `book_inventory` VALUES (76, 100, 66, 2, '9787544289012');
INSERT INTO `book_inventory` VALUES (77, 100, 77, 2, '9787101134567');
INSERT INTO `book_inventory` VALUES (78, 100, 88, 2, '9787532189012');
INSERT INTO `book_inventory` VALUES (79, 100, 98, 2, '9787100145678');
INSERT INTO `book_inventory` VALUES (80, 100, 66, 2, '9787559637890');
INSERT INTO `book_inventory` VALUES (81, 100, 22, 2, '9787533956789');
INSERT INTO `book_inventory` VALUES (82, 100, 11, 2, '9787544278901');
INSERT INTO `book_inventory` VALUES (83, 10, 0, 2, '9787550045678');
INSERT INTO `book_inventory` VALUES (84, 100, 21, 2, '9787539989012');
INSERT INTO `book_inventory` VALUES (85, 100, 44, 2, '9787111567890');
INSERT INTO `book_inventory` VALUES (86, 10, 2, 2, '9787559656789');
INSERT INTO `book_inventory` VALUES (87, 100, 14, 2, '9787100156789');
INSERT INTO `book_inventory` VALUES (88, 100, 43, 2, '9787533901234');
INSERT INTO `book_inventory` VALUES (89, 100, 77, 2, '9787544201234');
INSERT INTO `book_inventory` VALUES (90, 100, 11, 2, '9787559645678');
INSERT INTO `book_inventory` VALUES (91, 10, 3, 2, '9787533990123');
INSERT INTO `book_inventory` VALUES (92, 100, 21, 2, '9787544290123');
INSERT INTO `book_inventory` VALUES (93, 100, 44, 2, '9787550056789');
INSERT INTO `book_inventory` VALUES (94, 10, 1, 2, '9787539991234');
INSERT INTO `book_inventory` VALUES (95, 20, 10, 1, '9787101123456');
INSERT INTO `book_inventory` VALUES (96, 100, 88, 1, '9787302567890');
INSERT INTO `book_inventory` VALUES (97, 100, 98, 1, '9787108012345');
INSERT INTO `book_inventory` VALUES (98, 100, 67, 1, '9787111234567');
INSERT INTO `book_inventory` VALUES (99, 100, 22, 1, '9787100345679');
INSERT INTO `book_inventory` VALUES (100, 100, 11, 1, '9787108456787');
INSERT INTO `book_inventory` VALUES (101, 10, 2, 1, '9787101567890');
INSERT INTO `book_inventory` VALUES (102, 100, 21, 1, '9787302678904');
INSERT INTO `book_inventory` VALUES (103, 100, 44, 1, '9787100678911');
INSERT INTO `book_inventory` VALUES (104, 10, 2, 1, '9787108789012');
INSERT INTO `book_inventory` VALUES (105, 100, 20, 1, '9787101890123');
INSERT INTO `book_inventory` VALUES (106, 100, 43, 1, '9787302901234');
INSERT INTO `book_inventory` VALUES (107, 100, 77, 1, '9787100101235');
INSERT INTO `book_inventory` VALUES (108, 100, 11, 1, '9787108111236');
INSERT INTO `book_inventory` VALUES (109, 10, 3, 1, '9787101211237');
INSERT INTO `book_inventory` VALUES (110, 100, 21, 1, '9787302311238');
INSERT INTO `book_inventory` VALUES (111, 100, 44, 1, '9787100411239');
INSERT INTO `book_inventory` VALUES (112, 10, 1, 1, '9787108511230');
INSERT INTO `book_inventory` VALUES (113, 100, 76, 1, '9787121345678');
INSERT INTO `book_inventory` VALUES (114, 100, 88, 1, '9787122456789');
INSERT INTO `book_inventory` VALUES (115, 100, 97, 1, '9787124567890');
INSERT INTO `book_inventory` VALUES (116, 100, 66, 1, '9787125678901');
INSERT INTO `book_inventory` VALUES (117, 100, 22, 1, '9787126789012');
INSERT INTO `book_inventory` VALUES (118, 100, 11, 1, '9787127890123');
INSERT INTO `book_inventory` VALUES (119, 10, 2, 1, '9787128901234');
INSERT INTO `book_inventory` VALUES (120, 100, 21, 1, '9787129012345');
INSERT INTO `book_inventory` VALUES (121, 100, 42, 1, '9787130123456');
INSERT INTO `book_inventory` VALUES (122, 100, 44, 1, '9787131234567');
INSERT INTO `book_inventory` VALUES (123, 100, 66, 2, '9787132345678');
INSERT INTO `book_inventory` VALUES (124, 100, 77, 2, '9787133456789');
INSERT INTO `book_inventory` VALUES (125, 100, 88, 2, '9787134567890');
INSERT INTO `book_inventory` VALUES (126, 100, 76, 2, '9787135678901');
INSERT INTO `book_inventory` VALUES (127, 100, 88, 2, '9787136789012');
INSERT INTO `book_inventory` VALUES (128, 100, 98, 2, '9787137890123');
INSERT INTO `book_inventory` VALUES (129, 100, 66, 2, '9787138901234');
INSERT INTO `book_inventory` VALUES (130, 100, 22, 2, '9787139012345');
INSERT INTO `book_inventory` VALUES (131, 100, 11, 2, '9787140123456');
INSERT INTO `book_inventory` VALUES (132, 10, 3, 2, '9787141345678');
INSERT INTO `book_inventory` VALUES (133, 100, 21, 2, '9787142456780');
INSERT INTO `book_inventory` VALUES (134, 100, 42, 2, '9787143456780');
INSERT INTO `book_inventory` VALUES (135, 100, 44, 2, '9787144567890');
INSERT INTO `book_inventory` VALUES (136, 100, 65, 2, '9787145678901');
INSERT INTO `book_inventory` VALUES (137, 100, 77, 2, '9787146789012');
INSERT INTO `book_inventory` VALUES (138, 100, 88, 2, '9787147890123');
INSERT INTO `book_inventory` VALUES (139, 100, 76, 2, '9787148901234');
INSERT INTO `book_inventory` VALUES (140, 100, 88, 2, '9787149012345');
INSERT INTO `book_inventory` VALUES (141, 100, 98, 2, '9787150123456');
INSERT INTO `book_inventory` VALUES (142, 100, 66, 2, '9787151234567');
INSERT INTO `book_inventory` VALUES (143, 100, 22, 2, '9787152345678');
INSERT INTO `book_inventory` VALUES (144, 100, 11, 2, '9787153456789');
INSERT INTO `book_inventory` VALUES (145, 10, 3, 2, '9787154567890');
INSERT INTO `book_inventory` VALUES (146, 100, 21, 2, '9787155678901');
INSERT INTO `book_inventory` VALUES (147, 100, 42, 2, '9787156789012');
INSERT INTO `book_inventory` VALUES (148, 100, 44, 2, '9787157890123');
INSERT INTO `book_inventory` VALUES (149, 100, 66, 2, '9787158901234');
INSERT INTO `book_inventory` VALUES (150, 100, 77, 2, '9787159012345');
INSERT INTO `book_inventory` VALUES (151, 100, 88, 2, '9787160123456');

-- ----------------------------
-- Table structure for book_labels
-- ----------------------------
DROP TABLE IF EXISTS `book_labels`;
CREATE TABLE `book_labels`  (
  `book_id` int NOT NULL,
  `label_id` int NOT NULL,
  PRIMARY KEY (`book_id`, `label_id`) USING BTREE,
  INDEX `label_id`(`label_id`) USING BTREE,
  CONSTRAINT `book_labels_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `book_labels_ibfk_2` FOREIGN KEY (`label_id`) REFERENCES `label` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_labels
-- ----------------------------
INSERT INTO `book_labels` VALUES (1, 1);
INSERT INTO `book_labels` VALUES (2, 1);
INSERT INTO `book_labels` VALUES (8, 1);
INSERT INTO `book_labels` VALUES (22, 1);
INSERT INTO `book_labels` VALUES (23, 1);
INSERT INTO `book_labels` VALUES (24, 1);
INSERT INTO `book_labels` VALUES (25, 1);
INSERT INTO `book_labels` VALUES (26, 1);
INSERT INTO `book_labels` VALUES (27, 1);
INSERT INTO `book_labels` VALUES (28, 1);
INSERT INTO `book_labels` VALUES (29, 1);
INSERT INTO `book_labels` VALUES (30, 1);
INSERT INTO `book_labels` VALUES (31, 1);
INSERT INTO `book_labels` VALUES (32, 1);
INSERT INTO `book_labels` VALUES (33, 1);
INSERT INTO `book_labels` VALUES (34, 1);
INSERT INTO `book_labels` VALUES (35, 1);
INSERT INTO `book_labels` VALUES (36, 1);
INSERT INTO `book_labels` VALUES (37, 1);
INSERT INTO `book_labels` VALUES (38, 1);
INSERT INTO `book_labels` VALUES (40, 1);
INSERT INTO `book_labels` VALUES (42, 1);
INSERT INTO `book_labels` VALUES (44, 1);
INSERT INTO `book_labels` VALUES (45, 1);
INSERT INTO `book_labels` VALUES (46, 1);
INSERT INTO `book_labels` VALUES (47, 1);
INSERT INTO `book_labels` VALUES (48, 1);
INSERT INTO `book_labels` VALUES (7, 2);
INSERT INTO `book_labels` VALUES (9, 2);
INSERT INTO `book_labels` VALUES (10, 2);
INSERT INTO `book_labels` VALUES (41, 2);
INSERT INTO `book_labels` VALUES (43, 2);
INSERT INTO `book_labels` VALUES (54, 2);
INSERT INTO `book_labels` VALUES (55, 2);
INSERT INTO `book_labels` VALUES (56, 2);
INSERT INTO `book_labels` VALUES (57, 2);
INSERT INTO `book_labels` VALUES (58, 2);
INSERT INTO `book_labels` VALUES (49, 4);
INSERT INTO `book_labels` VALUES (50, 4);
INSERT INTO `book_labels` VALUES (51, 4);
INSERT INTO `book_labels` VALUES (52, 4);
INSERT INTO `book_labels` VALUES (53, 4);
INSERT INTO `book_labels` VALUES (69, 4);
INSERT INTO `book_labels` VALUES (70, 4);
INSERT INTO `book_labels` VALUES (71, 4);
INSERT INTO `book_labels` VALUES (72, 4);
INSERT INTO `book_labels` VALUES (73, 4);
INSERT INTO `book_labels` VALUES (79, 4);
INSERT INTO `book_labels` VALUES (80, 4);
INSERT INTO `book_labels` VALUES (82, 4);
INSERT INTO `book_labels` VALUES (83, 4);
INSERT INTO `book_labels` VALUES (85, 4);
INSERT INTO `book_labels` VALUES (86, 4);
INSERT INTO `book_labels` VALUES (87, 4);
INSERT INTO `book_labels` VALUES (88, 4);
INSERT INTO `book_labels` VALUES (89, 4);
INSERT INTO `book_labels` VALUES (90, 4);
INSERT INTO `book_labels` VALUES (91, 4);
INSERT INTO `book_labels` VALUES (92, 4);
INSERT INTO `book_labels` VALUES (93, 4);
INSERT INTO `book_labels` VALUES (94, 4);
INSERT INTO `book_labels` VALUES (95, 4);
INSERT INTO `book_labels` VALUES (96, 4);
INSERT INTO `book_labels` VALUES (97, 4);
INSERT INTO `book_labels` VALUES (98, 4);
INSERT INTO `book_labels` VALUES (1, 5);
INSERT INTO `book_labels` VALUES (2, 5);
INSERT INTO `book_labels` VALUES (8, 5);
INSERT INTO `book_labels` VALUES (22, 5);
INSERT INTO `book_labels` VALUES (23, 5);
INSERT INTO `book_labels` VALUES (24, 5);
INSERT INTO `book_labels` VALUES (25, 5);
INSERT INTO `book_labels` VALUES (26, 5);
INSERT INTO `book_labels` VALUES (305, 5);
INSERT INTO `book_labels` VALUES (306, 5);
INSERT INTO `book_labels` VALUES (308, 5);
INSERT INTO `book_labels` VALUES (309, 5);
INSERT INTO `book_labels` VALUES (310, 5);
INSERT INTO `book_labels` VALUES (311, 5);
INSERT INTO `book_labels` VALUES (312, 5);
INSERT INTO `book_labels` VALUES (313, 5);
INSERT INTO `book_labels` VALUES (314, 5);
INSERT INTO `book_labels` VALUES (315, 5);
INSERT INTO `book_labels` VALUES (316, 5);
INSERT INTO `book_labels` VALUES (317, 5);
INSERT INTO `book_labels` VALUES (318, 5);
INSERT INTO `book_labels` VALUES (319, 5);
INSERT INTO `book_labels` VALUES (320, 5);
INSERT INTO `book_labels` VALUES (321, 5);
INSERT INTO `book_labels` VALUES (322, 5);
INSERT INTO `book_labels` VALUES (323, 5);
INSERT INTO `book_labels` VALUES (324, 5);
INSERT INTO `book_labels` VALUES (340, 5);
INSERT INTO `book_labels` VALUES (325, 6);
INSERT INTO `book_labels` VALUES (326, 6);
INSERT INTO `book_labels` VALUES (327, 6);
INSERT INTO `book_labels` VALUES (328, 6);
INSERT INTO `book_labels` VALUES (329, 6);
INSERT INTO `book_labels` VALUES (330, 6);
INSERT INTO `book_labels` VALUES (331, 6);
INSERT INTO `book_labels` VALUES (332, 6);
INSERT INTO `book_labels` VALUES (333, 6);
INSERT INTO `book_labels` VALUES (334, 6);
INSERT INTO `book_labels` VALUES (335, 6);
INSERT INTO `book_labels` VALUES (336, 6);
INSERT INTO `book_labels` VALUES (337, 6);
INSERT INTO `book_labels` VALUES (338, 6);
INSERT INTO `book_labels` VALUES (339, 6);
INSERT INTO `book_labels` VALUES (340, 6);
INSERT INTO `book_labels` VALUES (341, 6);
INSERT INTO `book_labels` VALUES (342, 6);
INSERT INTO `book_labels` VALUES (343, 6);
INSERT INTO `book_labels` VALUES (344, 6);
INSERT INTO `book_labels` VALUES (1, 7);
INSERT INTO `book_labels` VALUES (2, 7);
INSERT INTO `book_labels` VALUES (7, 7);
INSERT INTO `book_labels` VALUES (23, 7);
INSERT INTO `book_labels` VALUES (64, 7);
INSERT INTO `book_labels` VALUES (65, 7);
INSERT INTO `book_labels` VALUES (66, 7);
INSERT INTO `book_labels` VALUES (67, 7);
INSERT INTO `book_labels` VALUES (68, 7);

-- ----------------------------
-- Table structure for book_reviews
-- ----------------------------
DROP TABLE IF EXISTS `book_reviews`;
CREATE TABLE `book_reviews`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `reader_id` int NOT NULL,
  `book_id` int NOT NULL,
  `review_text` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  `rating` int NULL DEFAULT NULL,
  `review_date` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `reader_id`(`reader_id`) USING BTREE,
  INDEX `book_id`(`book_id`) USING BTREE,
  CONSTRAINT `book_reviews_ibfk_1` FOREIGN KEY (`reader_id`) REFERENCES `reader` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `book_reviews_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_reviews
-- ----------------------------
INSERT INTO `book_reviews` VALUES (1, 3, 1, '非常棒', 5, '2025-03-02');
INSERT INTO `book_reviews` VALUES (2, 2, 1, 'good！好久没看到这么好看的小说了，超级棒！！', 5, '2025-03-02');
INSERT INTO `book_reviews` VALUES (3, 1, 1, '一般般', 4, '2025-03-02 22:17:47');
INSERT INTO `book_reviews` VALUES (4, 1, 2, '超级好看，高举大旗', 5, '2025-03-02 22:23:55');
INSERT INTO `book_reviews` VALUES (5, 2, 1, '可以，非常好看', 5, '2025-03-02 22:42:49');
INSERT INTO `book_reviews` VALUES (6, 1, 2, '嘿嘿，我喜欢\n', 5, '2025-03-02 22:45:44');
INSERT INTO `book_reviews` VALUES (7, 2, 2, '可以的，大大太棒了', 5, '2025-03-02 22:46:10');
INSERT INTO `book_reviews` VALUES (8, 1, 43, '一般般，不太喜欢这个剧情', 3, '2025-03-02 22:52:01');
INSERT INTO `book_reviews` VALUES (9, 1, 7, '好久没有看过这么精彩的故事了，喜欢', 5, '2025-03-03 13:16:49');
INSERT INTO `book_reviews` VALUES (10, 1, 8, '太赞了', 5, '2025-03-03 13:26:42');
INSERT INTO `book_reviews` VALUES (11, 3, 2, '非常好看，非常好看，大大我爱你\n', 5, '2025-03-03 15:33:21');
INSERT INTO `book_reviews` VALUES (12, 8, 1, '啦啦啦啦啦\n', 5, '2025-03-03 16:12:32');
INSERT INTO `book_reviews` VALUES (13, 1, 42, '非常不错\n', 5, '2025-03-04 17:28:00');
INSERT INTO `book_reviews` VALUES (14, 7, 59, '太深奥了，看不懂，但是五星走起', 5, '2025-03-07 12:59:54');
INSERT INTO `book_reviews` VALUES (16, 18, 7, '主角超帅我超爱，为宣云脂举大旗', 5, '2025-03-10 14:48:35');

-- ----------------------------
-- Table structure for borrow_record
-- ----------------------------
DROP TABLE IF EXISTS `borrow_record`;
CREATE TABLE `borrow_record`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `reader_id` int NOT NULL,
  `book_isbn` varchar(13) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `due_date` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `return_date` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `borrow_date` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `status` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '借阅中',
  `fine` decimal(10, 2) NULL DEFAULT 0.00,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_reader_book`(`reader_id`, `book_isbn`) USING BTREE,
  UNIQUE INDEX `reader_id`(`reader_id`, `book_isbn`) USING BTREE,
  INDEX `book_isbn`(`book_isbn`) USING BTREE,
  CONSTRAINT `borrow_record_ibfk_1` FOREIGN KEY (`reader_id`) REFERENCES `reader` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `borrow_record_ibfk_2` FOREIGN KEY (`book_isbn`) REFERENCES `book` (`isbn`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow_record
-- ----------------------------
INSERT INTO `borrow_record` VALUES (1, 6, '5432345612123', '2025-05-11', NULL, '2025-03-12', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (2, 6, '9787124567890', '2025-05-11', NULL, '2025-03-12', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (3, 6, '5432345632223', '2025-05-11', NULL, '2025-03-12', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (5, 6, '5432349612223', '2025-05-11', NULL, '2025-03-12', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (6, 7, '9787539991236', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (7, 7, '1234567897654', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (9, 7, '4567876543234', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (10, 1, '9787539991236', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (11, 1, '1432345612223', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (12, 1, '5432349612223', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (13, 7, '9787539978901', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (14, 7, '9787550045678', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (15, 6, '9787550028765', '2025-05-12', NULL, '2025-03-13', '已处理', NULL);
INSERT INTO `borrow_record` VALUES (16, 6, '9787532174232', '2025-05-12', NULL, '2025-03-13', '已处理', NULL);
INSERT INTO `borrow_record` VALUES (17, 6, '9787229123456', '2025-05-12', NULL, '2025-03-13', '已处理', NULL);
INSERT INTO `borrow_record` VALUES (18, 7, '5432349612223', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (19, 1, '1234567897654', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (20, 19, '5432349612223', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (21, 7, '9787559637890', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (22, 19, '9787100156789', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (23, 7, '9787100156789', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (24, 6, '9787100156789', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (25, 1, '9787100156789', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (26, 19, '9787539902345', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (27, 19, '9787559432176', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (28, 19, '5432345632223', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (29, 19, '5434567654345', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (30, 19, '9787533912345', '2025-05-12', NULL, '2025-03-13', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (31, 2, '9787128901234', '2025-05-13', NULL, '2025-03-14', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (32, 2, '2345676543234', '2025-05-13', NULL, '2025-03-14', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (33, 20, '9787550067890', '2025-05-13', NULL, '2025-03-14', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (35, 20, '5432349612223', '2025-05-13', NULL, '2025-03-14', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (41, 21, '5432349612223', '2025-05-13', NULL, '2025-03-14', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (42, 21, '9787539991236', '2025-05-14', NULL, '2025-03-15', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (43, 21, '9787100156789', '2025-05-14', NULL, '2025-03-15', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (44, 21, '1234567897654', '2025-05-14', NULL, '2025-03-15', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (45, 21, '9787550045678', '2025-05-14', NULL, '2025-03-15', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (46, 21, '9787145678901', '2025-05-14', NULL, '2025-03-15', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (47, 21, '9787550028765', '2025-05-14', NULL, '2025-03-15', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (48, 22, '9787539978901', '2025-05-14', NULL, '2025-03-15', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (49, 22, '1234567643211', '2025-05-14', NULL, '2025-03-15', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (50, 22, '9787100156789', '2025-05-14', NULL, '2025-03-15', '借阅中', NULL);
INSERT INTO `borrow_record` VALUES (51, 22, '5432349612223', '2025-05-14', NULL, '2025-03-15', '借阅中', NULL);

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `reader_id` int NOT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `status` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '待处理',
  `admin_response` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  `response_date` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `created_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `feedback_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `target_entity` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `reader_id`(`reader_id`) USING BTREE,
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`reader_id`) REFERENCES `reader` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (4, 1, '有一本书看不了了，帮我修一下', '已处理', '好的，马上为您解决', '2025-03-05 16:42:28', '2025-03-05 16:42:23', '2025-03-07 13:34:45', '0', 'admin');
INSERT INTO `feedback` VALUES (5, 2, '你好，我点开不了书架了', '已处理', '马上处理，请稍后', '2025-03-05 21:21:15', '2025-03-05 21:16:57', '2025-03-07 13:34:45', '0', 'admin');
INSERT INTO `feedback` VALUES (6, 2, '好奇怪\n', '已处理', '怎么了', '2025-03-05 21:21:15', '2025-03-05 21:18:05', '2025-03-07 13:34:45', '0', 'admin');
INSERT INTO `feedback` VALUES (7, 2, '问题已经解决了，谢谢', '已处理', '感谢使用', '2025-03-05 21:21:15', '2025-03-05 21:21:08', '2025-03-07 13:34:45', '0', 'admin');
INSERT INTO `feedback` VALUES (8, 2, '哇塞，太棒了', '已处理', '不要玩了，谢谢', '2025-03-05 21:22:32', '2025-03-05 21:22:29', '2025-03-07 13:34:45', '0', 'admin');
INSERT INTO `feedback` VALUES (14, 3, '我再试试\n', '已处理', '加油ing', '2025-03-06 15:55:27', '2025-03-06 15:55:24', '2025-03-07 13:34:45', '0', 'admin');
INSERT INTO `feedback` VALUES (15, 3, '我好饱', '已处理', '差不多承担', '2025-03-06 15:56:41', '2025-03-06 15:56:38', '2025-03-07 13:34:45', '0', 'admin');
INSERT INTO `feedback` VALUES (21, 7, '11111', '已处理', '好的', '2025-03-07 14:17:10', '2025-03-07 13:55:25', '2025-03-07 13:55:25', '测试一下', 'admin');
INSERT INTO `feedback` VALUES (22, 7, '有问题', '已处理', '收到，下次会改正', '2025-03-07 14:18:49', '2025-03-07 13:55:55', '2025-03-07 13:55:55', '关于书籍《与前男友在婚礼上重逢》的反馈', 'publisher');
INSERT INTO `feedback` VALUES (23, 6, '界面非常丑，抽到不堪入目', '已处理', '抱歉，马上修复', '2025-03-07 14:00:06', '2025-03-07 14:00:02', '2025-03-07 14:00:02', '首页展示有问题', 'admin');
INSERT INTO `feedback` VALUES (24, 6, '书籍翻卷', '已处理', '好的', '2025-03-07 14:25:22', '2025-03-07 14:25:18', '2025-03-07 14:25:18', '关于书籍《资本论》的反馈', 'publisher');
INSERT INTO `feedback` VALUES (25, 7, '来了来了', '已处理', NULL, '2025-03-08 23:28:24', '2025-03-07 14:31:31', '2025-03-07 14:31:31', '关于书籍《与前男友在婚礼上重逢》的反馈', 'admin');

-- ----------------------------
-- Table structure for interests
-- ----------------------------
DROP TABLE IF EXISTS `interests`;
CREATE TABLE `interests`  (
  `label_id` int NOT NULL AUTO_INCREMENT,
  `reader_id` int NOT NULL,
  PRIMARY KEY (`label_id`, `reader_id`) USING BTREE,
  INDEX `reader_id`(`reader_id`) USING BTREE,
  CONSTRAINT `interests_ibfk_1` FOREIGN KEY (`reader_id`) REFERENCES `reader` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of interests
-- ----------------------------
INSERT INTO `interests` VALUES (5, 1);
INSERT INTO `interests` VALUES (5, 2);
INSERT INTO `interests` VALUES (1, 3);
INSERT INTO `interests` VALUES (5, 3);
INSERT INTO `interests` VALUES (5, 6);
INSERT INTO `interests` VALUES (6, 6);
INSERT INTO `interests` VALUES (1, 7);
INSERT INTO `interests` VALUES (2, 7);
INSERT INTO `interests` VALUES (7, 7);
INSERT INTO `interests` VALUES (1, 8);
INSERT INTO `interests` VALUES (5, 8);
INSERT INTO `interests` VALUES (2, 18);
INSERT INTO `interests` VALUES (6, 18);
INSERT INTO `interests` VALUES (1, 19);
INSERT INTO `interests` VALUES (2, 19);
INSERT INTO `interests` VALUES (7, 19);
INSERT INTO `interests` VALUES (7, 20);
INSERT INTO `interests` VALUES (4, 21);
INSERT INTO `interests` VALUES (5, 21);
INSERT INTO `interests` VALUES (4, 22);
INSERT INTO `interests` VALUES (5, 22);
INSERT INTO `interests` VALUES (1, 23);
INSERT INTO `interests` VALUES (5, 23);
INSERT INTO `interests` VALUES (1, 24);
INSERT INTO `interests` VALUES (2, 24);
INSERT INTO `interests` VALUES (2, 25);
INSERT INTO `interests` VALUES (4, 25);

-- ----------------------------
-- Table structure for label
-- ----------------------------
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `label` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of label
-- ----------------------------
INSERT INTO `label` VALUES (1, '古代言情');
INSERT INTO `label` VALUES (2, '现代言情');
INSERT INTO `label` VALUES (4, '历史文化');
INSERT INTO `label` VALUES (5, '社会文化');
INSERT INTO `label` VALUES (6, '经济');
INSERT INTO `label` VALUES (7, '玄幻');

-- ----------------------------
-- Table structure for librarian
-- ----------------------------
DROP TABLE IF EXISTS `librarian`;
CREATE TABLE `librarian`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `employee_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `employee_id`(`employee_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of librarian
-- ----------------------------
INSERT INTO `librarian` VALUES (1, '张三', '2011', '2004224@abc', '1722184809@qq.com');
INSERT INTO `librarian` VALUES (2, '李四', '1222', '23111', '18920191111');

-- ----------------------------
-- Table structure for participation
-- ----------------------------
DROP TABLE IF EXISTS `participation`;
CREATE TABLE `participation`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `reader_id` int NOT NULL,
  `activity_id` int NOT NULL,
  `participation_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `participation_status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '未参与',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_reader_activity`(`reader_id`, `activity_id`) USING BTREE,
  INDEX `activity_id`(`activity_id`) USING BTREE,
  CONSTRAINT `participation_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `participation_ibfk_2` FOREIGN KEY (`reader_id`) REFERENCES `reader` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of participation
-- ----------------------------
INSERT INTO `participation` VALUES (1, 7, 3, '2025-03-09 17:54:39', '已报名');
INSERT INTO `participation` VALUES (7, 19, 2, '2025-03-13 20:46:05', '已报名');

-- ----------------------------
-- Table structure for publisher
-- ----------------------------
DROP TABLE IF EXISTS `publisher`;
CREATE TABLE `publisher`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `contact_person` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of publisher
-- ----------------------------
INSERT INTO `publisher` VALUES (1, '上海出版社', '列宁', '12334543280', '1112', '6778888');
INSERT INTO `publisher` VALUES (2, '广东出版社', '树林', '18293877387', '2004224@abc', '1722184809@qq.com');

-- ----------------------------
-- Table structure for reader
-- ----------------------------
DROP TABLE IF EXISTS `reader`;
CREATE TABLE `reader`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `address` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `registration_date` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `cover_image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `account_balance` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reader
-- ----------------------------
INSERT INTO `reader` VALUES (1, '森芸', '123456@abc', '1722184809@qq.com', '12345432399', '湖南省衡阳市', '2025-03-12 20:11:20', 'http://localhost:8080/uploads/dd8963626f15de478871a23cb7d99171.jpg', 122);
INSERT INTO `reader` VALUES (2, '楚月月', '1223', 'pengy263@gmail.com', '16782345689', '湖南省株洲市', '2025-03-12 20:11:20', 'http://localhost:8080/uploads/17006996477366819.jpg', 462);
INSERT INTO `reader` VALUES (3, '森迪', '123345', '1722184808@qq.com', '18920202222', '湖南省衡阳市', '2025-03-12 20:11:20', 'http://localhost:8080/uploads/202362913577135.jpg', 500);
INSERT INTO `reader` VALUES (6, '柚子', '2001', 'pengying263@gmail.com', '12131211222', '湖南省株洲市', '2025-03-12 20:11:20', 'http://localhost:8080/uploads/202161102147270.png', 631);
INSERT INTO `reader` VALUES (7, '苏苏', '10922', 'pengying163@gmail.com', '11211121233', '湖南省张家界市', '2025-03-12 20:11:20', 'http://localhost:8080/uploads/3e73967081b7dd9a5677f89bb58a05b2.jpg', 88);
INSERT INTO `reader` VALUES (8, '苏灵', '1111', '1892192837@qq.com', '15116829693', '湖南省衡阳市衡东县', '2025-03-12 20:11:20', 'http://localhost:8080/uploads/320b1beeac636ece182a2197184e51fc.jpeg', 400);
INSERT INTO `reader` VALUES (18, '思瑰', '12345', '192803912@qq.com', '19823017651', '湖南省长沙市', '2025-03-12 20:11:20', 'http://localhost:8080/uploads/avatars/img2.png', 20);
INSERT INTO `reader` VALUES (19, '蕙褛', '11223', '8977650@qq.com', '19210221892', '湖南省', '2025-03-13 20:15:52', 'http://localhost:8080/uploads/2023411023594950.jpg', 540);
INSERT INTO `reader` VALUES (20, '淼钧', '123456', '19203838@qq.com', '12310229811', '湖南省', '2025-03-14 19:19:47', 'http://localhost:8080/uploads/avatars/img1.png', 204);
INSERT INTO `reader` VALUES (21, '月七', '123456', '19021919@qq.com', '12034229111', '上海市', '2025-03-14 19:32:02', 'http://localhost:8080/uploads/20246211427548374.jpg', 500);
INSERT INTO `reader` VALUES (22, '珑遂', '123456', '19022201@qq.com', '17212882190', '湖北省', '2025-03-15 17:16:42', 'http://localhost:8080/uploads/5d5004a0f97a41c6b7d22e803299c673.jpg', 20);
INSERT INTO `reader` VALUES (23, '缘蔻', '123456', NULL, '19210113281', NULL, '2025-03-15 17:20:04', 'http://localhost:8080/uploads/e1a56ee5200bf38b0133ac5945289a20.jpg', 0);
INSERT INTO `reader` VALUES (24, '月凤', '123456', NULL, '17263889101', NULL, '2025-03-15 17:41:47', 'http://localhost:8080/uploads/0484af683f911c71aa9b99f19e5a40ca.jpg', 0);
INSERT INTO `reader` VALUES (25, '森珑', '123456', NULL, '18772919100', NULL, '2025-03-16 13:23:45', 'http://localhost:8080/uploads/avatars/img3.jpg', NULL);

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `reader_id` int NOT NULL,
  `book_isbn` varchar(13) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `reserve_date` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '等待中',
  `admin_response` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `reader_id`(`reader_id`) USING BTREE,
  INDEX `reservation_ibfk_2`(`book_isbn`) USING BTREE,
  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`reader_id`) REFERENCES `reader` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`book_isbn`) REFERENCES `book` (`isbn`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reservation
-- ----------------------------
INSERT INTO `reservation` VALUES (11, 7, '9787559656789', '2025-03-08 22:18:37', '已处理', '书籍库存已更新，系统自动帮您加入书架，请尽快来取书');
INSERT INTO `reservation` VALUES (12, 8, '9787539991234', '2025-03-09 14:19:14', '已处理', '自动加入借书架，请自行取书');
INSERT INTO `reservation` VALUES (15, 21, '5432349612223', '2025-03-14 19:58:32', '已处理', '自动加入借书架');
INSERT INTO `reservation` VALUES (16, 21, '9787550045678', '2025-03-15 16:51:04', '已处理', '请查收');
INSERT INTO `reservation` VALUES (17, 22, '5432349612223', '2025-03-15 17:17:07', '已处理', '自动加入借书架');

SET FOREIGN_KEY_CHECKS = 1;
