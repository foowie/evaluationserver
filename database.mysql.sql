-- Adminer 3.3.4 MySQL dump

SET NAMES utf8;
SET foreign_key_checks = 0;
SET time_zone = 'SYSTEM';
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP TABLE IF EXISTS `Category`;
CREATE TABLE `Category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `Category` (`id`, `name`) VALUES
(1,	'Samples');

DROP TABLE IF EXISTS `Competition`;
CREATE TABLE `Competition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created` datetime NOT NULL,
  `name` varchar(200) NOT NULL,
  `startDate` datetime DEFAULT NULL,
  `stopDate` datetime DEFAULT NULL,
  `timePenalization` int(11) DEFAULT NULL,
  `dontUpdateStatisticsBefore` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `creator` (`creator`),
  CONSTRAINT `Competition_ibfk_1` FOREIGN KEY (`creator`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `Competition` (`id`, `active`, `created`, `name`, `startDate`, `stopDate`, `timePenalization`, `dontUpdateStatisticsBefore`, `creator`) VALUES
(1,	1,	'2012-01-25 19:54:59',	'Sample competition',	NULL,	NULL,	NULL,	NULL,	1);

DROP TABLE IF EXISTS `CompetitionGroup`;
CREATE TABLE `CompetitionGroup` (
  `competitionId` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  KEY `competitionId` (`competitionId`),
  KEY `groupId` (`groupId`),
  CONSTRAINT `CompetitionGroup_ibfk_1` FOREIGN KEY (`competitionId`) REFERENCES `Competition` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CompetitionGroup_ibfk_2` FOREIGN KEY (`groupId`) REFERENCES `UserGroup` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `CompetitionGroup` (`competitionId`, `groupId`) VALUES
(1,	1);

DROP TABLE IF EXISTS `CompetitionTask`;
CREATE TABLE `CompetitionTask` (
  `taskId` int(11) NOT NULL,
  `competitionId` int(11) NOT NULL,
  KEY `competitionId` (`competitionId`),
  KEY `taskId` (`taskId`),
  CONSTRAINT `CompetitionTask_ibfk_1` FOREIGN KEY (`competitionId`) REFERENCES `Competition` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CompetitionTask_ibfk_2` FOREIGN KEY (`taskId`) REFERENCES `Task` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `CompetitionTask` (`taskId`, `competitionId`) VALUES
(1,	1);

DROP TABLE IF EXISTS `File`;
CREATE TABLE `File` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `pathName` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `fileSize` bigint(20) NOT NULL,
  `fileData` int(11) DEFAULT NULL,
  `title` varchar(100) COLLATE utf8_bin NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_File_fileData` (`fileData`),
  KEY `type` (`type`),
  CONSTRAINT `File_ibfk_1` FOREIGN KEY (`type`) REFERENCES `FileType` (`id`),
  CONSTRAINT `FK_File_fileData` FOREIGN KEY (`fileData`) REFERENCES `FileData` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `File` (`id`, `created`, `name`, `pathName`, `fileSize`, `fileData`, `title`, `type`) VALUES
(1,	'2012-02-02 16:00:12',	'longdiff',	NULL,	7324,	1,	'Diff of numbers',	1),
(2,	'2012-02-02 16:02:11',	'input.txt',	NULL,	21,	2,	'input.txt',	2),
(3,	'2012-02-02 16:02:11',	'output.txt',	NULL,	21,	3,	'output.txt',	3),
(4,	'2012-04-18 18:32:04',	'Input -> Output solution.cpp',	NULL,	232,	4,	'Input -> Output solution.cpp',	4);

DROP TABLE IF EXISTS `FileData`;
CREATE TABLE `FileData` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fileData` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `FileData` (`id`, `fileData`) VALUES
(1,	'ELF\0\0\0\0\0\0\0\0\0\0\0\0\0\0àƒ4\0\0\0t\0\0\0\0\0\04\0 \0\0(\0\0\Z\0\0\0\04\0\0\04€4€\0\0\0\0\0\0\0\0\0\0\0\0\0\0\04\0\044\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0€\0€,\0\0,\0\0\0\0\0\0\0\0\0\0\0\0\0ŸŸ\0\0$\0\0\0\0\0\0\0\0\0\0\0(\0\0(Ÿ(ŸÈ\0\0\0È\0\0\0\0\0\0\0\0\0\0\0\0H\0\0HHD\0\0\0D\0\0\0\0\0\0\0\0\0Qåtd\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Råtd\0\0ŸŸì\0\0\0ì\0\0\0\0\0\0\0\0\0/lib/ld-linux.so.2\0\0\0\0\0\0\0\0\0\0\0GNU\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0GNU\0QÊ>\'\\ä¨\Z{0ÛW$oßé;\0\0\0\0\0\0\0\0\0\0\0\0\0 \0 \0\0\0\0\0\0\0­KãÀ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\06\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0 \0\0\0M\0\0\0\0\0\0\0\0\0\0\0\0\0\0F\0\0\0\0\0\0\0\0\0\0\0\0\0\0)\0\0\0\0\0\0\0\0\0\0\0\0\0\0/\0\0\0\0\0\0\0\0\0\0\0\0\0\0\Z\0\0\0‡\0\0\0\0\0\0__gmon_start__\0libc.so.6\0_IO_stdin_used\0fopen\0printf\0__isoc99_fscanf\0fclose\0__libc_start_main\0GLIBC_2.1\0GLIBC_2.0\0GLIBC_2.7\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ii\r\0\0\0_\0\0\0\0\0\0ii\r\0\0\0i\0\0\0\0\0\0ii\r\0\0\0s\0\0\0\0\0\0\0ğŸ\0\0\0 \0\0 \0\0 \0\0 \0\0 \0\0 \0\0U‰åSƒìè\0\0\0\0[Ã´\0\0‹“üÿÿÿ…Òtè.\0\0\0è\0\0è`\0\0X[ÉÃÿ5øŸÿ%üŸ\0\0\0\0ÿ%\0 h\0\0\0\0éàÿÿÿÿ% h\0\0\0éĞÿÿÿÿ% h\0\0\0éÀÿÿÿÿ% h\0\0\0é°ÿÿÿÿ% h \0\0\0é ÿÿÿÿ% h(\0\0\0éÿÿÿ\0\0\0\0\0\0\0\0\0\0\0\01í^‰áƒäğPTRh°†hP†QVh…è“ÿÿÿôU‰åSƒì€=0 \0u?¡4 » ŸëŸÁûƒë9Øs¶\0\0\0\0ƒÀ£4 ÿ…Ÿ¡4 9ØrèÆ0 ƒÄ[]Ãt&\0¼\'\0\0\0\0U‰åƒì¡$Ÿ…Àt¸\0\0\0\0…Àt	Ç$$ŸÿĞÉÃU‰åƒì(ƒ}\0u¸\0\0\0ëg¸‡Uì‰T$‰D$‹E‰$è²şÿÿ‰Eô¸‡Uè‰T$‰D$‹E‰$è”şÿÿ‰Eğ‹Eô;Eğt¸\0\0\0ë‹Uì‹Eè9Ât¸\0\0\0ëƒ}ôÿu¸\0\0\0\0ÉÃU‰åƒäğƒì ƒ}~ƒ}~!‹, ¸ ‡‰T$‰$èˆşÿÿ¸\0\0\0é\0\0º#‡‹EƒÀ‹\0‰T$‰$èUşÿÿ‰D$º#‡‹EƒÀ‹\0‰T$‰$è8şÿÿ‰D$ƒ}tº#‡‹EƒÀ‹\0‰T$‰$èşÿÿë¸\0\0\0\0‰D$ƒ|$\0tƒ|$\0t\rƒ}t%ƒ|$\0u‹, ¸ ‡‰T$‰$èèıÿÿ¸\0\0\0ëk‹D$‰D$‹D$‰D$‹D$‰$è•şÿÿ‰D$‹D$‹…  ¸ ‡‰T$‰$è¥ıÿÿ‹D$‰$èyıÿÿ‹D$‰$èmıÿÿƒ}t‹D$‰$è[ıÿÿ¸\0\0\0\0ÉÃU‰åWVSèZ\0\0\0Ã™\0\0ƒìèËüÿÿ» ÿÿÿƒ ÿÿÿ)ÇÁÿ…ÿt$1ö‹E‰D$‹E‰D$‹E‰$ÿ”³ ÿÿÿƒÆ9şrŞƒÄ[^_]Ã¶\0\0\0\0U‰å]Ã‹$ÃU‰åSƒì¡Ÿƒøÿt»ŸfƒëÿĞ‹ƒøÿuôƒÄ[]ÃU‰åSƒìè\0\0\0\0[Ãü\0\0èıÿÿY[ÉÃ\0\0\0\0\0AC\0PE\0WA\0IE\0%ld\0%s\0r\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿÿÿÿ\0\0\0\0ÿÿÿÿ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\04ƒ\r\0\0\0ì†õşÿoŒ\0\0\0,‚\0\0\0¬\n\0\0\0}\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ôŸ\0\0\00\0\0\0\0\0\0\0\0\0\0\0\0ƒ\0\0\0ü‚\0\0\0\0\0\0\0\0\0\0\0\0şÿÿo¼‚ÿÿÿo\0\0\0ğÿÿoª‚\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0(Ÿ\0\0\0\0\0\0\0\0zƒŠƒšƒªƒºƒÊƒ\0\0\0\0\0\0\0\0‡‡‡‡GCC: (Ubuntu/Linaro 4.5.2-8ubuntu4) 4.5.2\0GCC: (Ubuntu/Linaro 4.5.2-8ubuntu3) 4.5.2\0\0.symtab\0.strtab\0.shstrtab\0.interp\0.note.ABI-tag\0.note.gnu.build-id\0.gnu.hash\0.dynsym\0.dynstr\0.gnu.version\0.gnu.version_r\0.rel.dyn\0.rel.plt\0.init\0.text\0.fini\0.rodata\0.eh_frame\0.ctors\0.dtors\0.jcr\0.dynamic\0.got\0.got.plt\0.data\0.bss\0.comment\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\044\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0#\0\0\0\0\0\0\0\0\0HH\0\0 \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\01\0\0\0\0\0\0\0\0\0hh\0\0$\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0D\0\0\0öÿÿo\0\0\0ŒŒ\0\0 \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0N\0\0\0\0\0\0\0\0\0¬¬\0\0€\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0V\0\0\0\0\0\0\0\0\0,‚,\0\0}\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0^\0\0\0ÿÿÿo\0\0\0ª‚ª\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0k\0\0\0şÿÿo\0\0\0¼‚¼\0\0@\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0z\0\0\0	\0\0\0\0\0\0ü‚ü\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ƒ\0\0\0	\0\0\0\0\0\0ƒ\0\00\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Œ\0\0\0\0\0\0\0\0\04ƒ4\0\00\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0‡\0\0\0\0\0\0\0\0\0dƒd\0\0p\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0’\0\0\0\0\0\0\0\0\0àƒà\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0˜\0\0\0\0\0\0\0\0\0ì†ì\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0‡\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¦\0\0\0\0\0\0\0\0\0(‡(\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0°\0\0\0\0\0\0\0\0\0Ÿ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0·\0\0\0\0\0\0\0\0\0Ÿ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0¾\0\0\0\0\0\0\0\0\0$Ÿ$\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ã\0\0\0\0\0\0\0\0\0(Ÿ(\0\0È\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ì\0\0\0\0\0\0\0\0\0ğŸğ\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ñ\0\0\0\0\0\0\0\0\0ôŸô\0\0$\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0Ú\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0à\0\0\0\0\0\0\0\0\00 0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0å\0\0\0\0\0\00\0\0\0\0\0\0\00\0\0T\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0„\0\0î\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ü\0\0P\0\0\0\0\0,\0\0\0\0\0\0\0\0\0	\0\0\0\0\0\0\0\0\0\0\0\0\0\0L\Z\0\0P\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\04\0\0\0\0\0\0\0\0\0\0H\0\0\0\0\0\0\0\0\0\0h\0\0\0\0\0\0\0\0\0\0Œ\0\0\0\0\0\0\0\0\0\0¬\0\0\0\0\0\0\0\0\0\0,‚\0\0\0\0\0\0\0\0\0\0ª‚\0\0\0\0\0\0\0\0\0\0¼‚\0\0\0\0\0\0\0\0\0\0ü‚\0\0\0\0\0	\0\0\0\0\0ƒ\0\0\0\0\0\n\0\0\0\0\04ƒ\0\0\0\0\0\0\0\0\0\0dƒ\0\0\0\0\0\0\0\0\0\0àƒ\0\0\0\0\0\r\0\0\0\0\0ì†\0\0\0\0\0\0\0\0\0\0‡\0\0\0\0\0\0\0\0\0\0(‡\0\0\0\0\0\0\0\0\0\0Ÿ\0\0\0\0\0\0\0\0\0\0Ÿ\0\0\0\0\0\0\0\0\0\0$Ÿ\0\0\0\0\0\0\0\0\0\0(Ÿ\0\0\0\0\0\0\0\0\0\0ğŸ\0\0\0\0\0\0\0\0\0\0ôŸ\0\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0\0\0\00 \0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0ñÿ\0\0\0Ÿ\0\0\0\0\0\0\Z\0\0\0Ÿ\0\0\0\0\0\0(\0\0\0$Ÿ\0\0\0\0\0\05\0\0\0„\0\0\0\0\0\r\0K\0\0\00 \0\0\0\0\0Z\0\0\04 \0\0\0\0\0h\0\0\0p„\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0\0\0\0\0ñÿt\0\0\0Ÿ\0\0\0\0\0\0\0\0\0(‡\0\0\0\0\0\0\0\0\0$Ÿ\0\0\0\0\0\0›\0\0\0À†\0\0\0\0\0\r\0±\0\0\0\0\0\0\0\0\0\0\0\0ñÿ¼\0\0\0ôŸ\0\0\0\0\0\0Ò\0\0\0Ÿ\0\0\0\0\0\0\0ã\0\0\0Ÿ\0\0\0\0\0\0\0ö\0\0\0(Ÿ\0\0\0\0\0\0ÿ\0\0\0 \0\0\0\0 \0\0\n\0\0°†\0\0\0\0\r\0\Z\0\0àƒ\0\0\0\0\0\r\0!\0\0\0\0\0\0\0\0\0\0\0\0\0<\0\0\0\0\0\0\0\0\0\0 \0\0\0K\0\0\0\0\0\0\0\0\0\0 \0\0\0_\0\0‡\0\0\0\0\0f\0\0ì†\0\0\0\0\0\0l\0\0  \0\0\0\0\0v\0\0\0\0\0\0\0\0\0\0\0\0\0“\0\0‡\0\0\0\0\0¢\0\0 \0\0\0\0\0\0¯\0\0\0\0\0\0\0\0\0\0\0\0\0Á\0\0\0\0\0\0\0\0\0\0\0\0\0Ò\0\0 \0\0\0\0\0ß\0\0 Ÿ\0\0\0\0\0ì\0\0P†Z\0\0\0\0\r\0ü\0\0\0\0\0\0\0\0\0\0\0\0\0\0\00 \0\0\0\0\0ñÿ\Z\0\08 \0\0\0\0\0ñÿ\0\00 \0\0\0\0\0ñÿ&\0\0µ†\0\0\0\0\r\0=\0\0”„|\0\0\0\0\r\0E\0\0…@\0\0\0\r\0J\0\04ƒ\0\0\0\0\0\0\0crtstuff.c\0__CTOR_LIST__\0__DTOR_LIST__\0__JCR_LIST__\0__do_global_dtors_aux\0completed.6155\0dtor_idx.6157\0frame_dummy\0__CTOR_END__\0__FRAME_END__\0__JCR_END__\0__do_global_ctors_aux\0longdiff.c\0_GLOBAL_OFFSET_TABLE_\0__init_array_end\0__init_array_start\0_DYNAMIC\0data_start\0__libc_csu_fini\0_start\0__isoc99_fscanf@@GLIBC_2.7\0__gmon_start__\0_Jv_RegisterClasses\0_fp_hw\0_fini\0responses\0__libc_start_main@@GLIBC_2.0\0_IO_stdin_used\0__data_start\0fclose@@GLIBC_2.1\0fopen@@GLIBC_2.1\0__dso_handle\0__DTOR_END__\0__libc_csu_init\0printf@@GLIBC_2.0\0__bss_start\0_end\0_edata\0__i686.get_pc_thunk.bx\0process\0main\0_init\0'),
(2,	'1 2 3 4 5 6 7 8 9 10\n'),
(3,	'1 2 3 4 5 6 7 8 9 10\n'),
(4,	'#include <cstdlib>\n#include <iostream>\n#include <stdio.h>\n\nusing namespace std;\n\n\nint main(int argc, char** argv) {\n	int n, r;\n	while(true) {\n		r = scanf(\"%d\", &n);\n		if (r == EOF)\n		    break;\n		printf(\"%d\\n\", n);\n	}\n	return 0;\n}\n\n');

DROP TABLE IF EXISTS `FileType`;
CREATE TABLE `FileType` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `FileType` (`id`, `name`) VALUES
(1,	'Resolver'),
(2,	'Input data'),
(3,	'Output data'),
(4,	'Solution');

DROP TABLE IF EXISTS `Language`;
CREATE TABLE `Language` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `extension` varchar(10) COLLATE utf8_bin NOT NULL,
  `keyName` varchar(20) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `Language` (`id`, `extension`, `keyName`, `name`) VALUES
(1,	'c',	'c',	'C'),
(2,	'cpp',	'cpp',	'C++');

DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyName` varchar(20) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `Role` (`id`, `keyName`, `name`) VALUES
(1,	'admin',	'Administrator'),
(2,	'contestant',	'Contestant');

DROP TABLE IF EXISTS `Solution`;
CREATE TABLE `Solution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateInsert` datetime NOT NULL,
  `dateEvaluated` datetime DEFAULT NULL,
  `evaluationLockUntil` datetime DEFAULT NULL,
  `log` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `memory` int(11) DEFAULT NULL,
  `timeLength` int(11) DEFAULT NULL,
  `file` int(11) NOT NULL,
  `language` int(11) NOT NULL,
  `systemReply` int(11) DEFAULT NULL,
  `task` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `competition` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Solution_language` (`language`),
  KEY `FK_Solution_file` (`file`),
  KEY `FK_Solution_systemReply` (`systemReply`),
  KEY `FK_Solution_task` (`task`),
  KEY `user` (`user`),
  KEY `competition` (`competition`),
  CONSTRAINT `FK_Solution_file` FOREIGN KEY (`file`) REFERENCES `File` (`id`),
  CONSTRAINT `FK_Solution_language` FOREIGN KEY (`language`) REFERENCES `Language` (`id`),
  CONSTRAINT `FK_Solution_systemReply` FOREIGN KEY (`systemReply`) REFERENCES `SystemReply` (`id`),
  CONSTRAINT `FK_Solution_task` FOREIGN KEY (`task`) REFERENCES `Task` (`id`),
  CONSTRAINT `Solution_ibfk_1` FOREIGN KEY (`user`) REFERENCES `User` (`id`),
  CONSTRAINT `Solution_ibfk_2` FOREIGN KEY (`competition`) REFERENCES `Competition` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `Solution` (`id`, `dateInsert`, `dateEvaluated`, `evaluationLockUntil`, `log`, `memory`, `timeLength`, `file`, `language`, `systemReply`, `task`, `user`, `competition`) VALUES
(1,	'2012-01-01 00:00:00',	NULL,	NULL,	NULL,	NULL,	NULL,	4,	2,	NULL,	1,	2,	1);

DROP TABLE IF EXISTS `SystemReply`;
CREATE TABLE `SystemReply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accepting` bit(1) NOT NULL,
  `keyName` varchar(2) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `keyName` (`keyName`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `SystemReply` (`id`, `accepting`, `keyName`, `name`) VALUES
(1,	1,	'AC',	'Accepted'),
(2,	0,	'CE',	'Compile error'),
(3,	0,	'PE',	'Presentation error'),
(4,	0,	'WA',	'Wrong answer'),
(5,	0,	'RE',	'Runtime error'),
(6,	0,	'TL',	'Time limit exceeded'),
(7,	0,	'ML',	'Memory limit exceeded'),
(8,	0,	'OL',	'Output limit exceeded'),
(9,	0,	'RF',	'Restricted function'),
(10,	0,	'IE',	'Internal error');

DROP TABLE IF EXISTS `Task`;
CREATE TABLE `Task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` datetime NOT NULL,
  `description` longtext COLLATE utf8_bin NOT NULL,
  `memoryLimit` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `outputLimit` int(11) NOT NULL,
  `sampleInput` longtext COLLATE utf8_bin,
  `sampleOutput` longtext COLLATE utf8_bin,
  `sourceLimit` int(11) NOT NULL,
  `timeLimit` int(11) NOT NULL,
  `inputData` int(11) NOT NULL,
  `outputData` int(11) DEFAULT NULL,
  `resultResolver` int(11) NOT NULL,
  `category` int(11) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Task_inputData` (`inputData`),
  KEY `FK_Task_resultResolver` (`resultResolver`),
  KEY `FK_Task_outputData` (`outputData`),
  KEY `category` (`category`),
  KEY `creator` (`creator`),
  CONSTRAINT `FK_Task_inputData` FOREIGN KEY (`inputData`) REFERENCES `File` (`id`),
  CONSTRAINT `FK_Task_outputData` FOREIGN KEY (`outputData`) REFERENCES `File` (`id`),
  CONSTRAINT `FK_Task_resultResolver` FOREIGN KEY (`resultResolver`) REFERENCES `File` (`id`),
  CONSTRAINT `Task_ibfk_1` FOREIGN KEY (`category`) REFERENCES `Category` (`id`),
  CONSTRAINT `Task_ibfk_2` FOREIGN KEY (`creator`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `Task` (`id`, `created`, `description`, `memoryLimit`, `name`, `outputLimit`, `sampleInput`, `sampleOutput`, `sourceLimit`, `timeLimit`, `inputData`, `outputData`, `resultResolver`, `category`, `creator`) VALUES
(1,	'2012-01-01 00:00:00',	'Read numbers from input and write them to output.',	10240000,	'Input -> Output',	10240,	'1 2 3',	'1 2 3',	10240,	3000,	2,	3,	1,	1,	1);

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created` datetime NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(128) NOT NULL,
  `surname` varchar(100) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `creator` (`creator`),
  KEY `role` (`role`),
  CONSTRAINT `User_ibfk_1` FOREIGN KEY (`creator`) REFERENCES `User` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `User_ibfk_2` FOREIGN KEY (`role`) REFERENCES `Role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO `User` (`id`, `active`, `created`, `email`, `firstName`, `login`, `password`, `surname`, `creator`, `role`) VALUES
(1,	1,	'2012-01-01 00:00:00',	'',	'',	'admin',	'149410b9a49e41f199593d66b38a55ff728c710fe4513a64b41fb58606f05c83',	'',	NULL,	1),
(2,	1,	'2012-01-01 00:00:00',	'',	'',	'contestant',	'0b3d13c525722abc1bb5c612a13f1ee17694efafe4334960a704cf31f0edd2b',	'',	1,	2);

DROP TABLE IF EXISTS `UserGroup`;
CREATE TABLE `UserGroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `description` text,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `UserGroup` (`id`, `active`, `description`, `name`) VALUES
(1,	1,	'',	'Sample team');

DROP TABLE IF EXISTS `UserUserGroup`;
CREATE TABLE `UserUserGroup` (
  `userId` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  KEY `userId` (`userId`),
  KEY `groupId` (`groupId`),
  CONSTRAINT `UserUserGroup_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `UserUserGroup_ibfk_2` FOREIGN KEY (`groupId`) REFERENCES `UserGroup` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `UserUserGroup` (`userId`, `groupId`) VALUES
(2,	1);

-- 2012-04-18 20:31:02
