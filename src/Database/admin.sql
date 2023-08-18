-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 20 juil. 2023 à 14:56
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `taskmanager`
--

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id` int NOT NULL,
  `first_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `last_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `role` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `birthdate` date DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`id`, `first_name`, `last_name`, `email`, `role`, `birthdate`, `username`, `password`) VALUES
(1, 'gheiyth', 'tabarki', 'tbagheiyth@gmail.com', 'Admin', '2000-01-17', 'gheiyth', 'gheiyth'),
(2, 'adem', 'pitcho', 'adempitcho@gmail.com', 'User', '2001-04-03', 'Adem', 'Adem'),
(3, 'rayen', 'saidi', 'rayensaidi@gmail.com', 'user', '1998-07-22', 'rayen', 'rayen000'),
(5, 'ghassen', 'jemai', 'jemaighassen@esprit.tn', 'user', '1999-07-16', 'ghassen', 'ghassen'),
(10, NULL, NULL, 'ayemn10@gmail.com', 'admin', NULL, 'aymen', 'aymen'),
(6, 'ahmed', 'tabarki', 'ahmed@gmail.com', 'user', '2023-07-13', 'ahmed', 'ahmed'),
(4, NULL, NULL, 'asmatabarki@gmail.com', 'user', NULL, 'asma', '271746451');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
