-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 16, 2020 at 05:21 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `souq`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `user_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`user_id`, `product_id`) VALUES
(11, 14),
(11, 25),
(24, 14),
(24, 15),
(24, 25);

-- --------------------------------------------------------

--
-- Table structure for table `favorite`
--

CREATE TABLE `favorite` (
  `favorite_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `favorite`
--

INSERT INTO `favorite` (`favorite_id`, `user_id`, `product_id`) VALUES
(108, 24, 14),
(110, 24, 15),
(111, 24, 16);

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

CREATE TABLE `history` (
  `history_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ordering`
--

CREATE TABLE `ordering` (
  `ordering_id` int(11) NOT NULL,
  `order_number` int(11) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `status` text,
  `name_on_card` text,
  `card_number` text,
  `expiration_date` date DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ordering`
--

INSERT INTO `ordering` (`ordering_id`, `order_number`, `order_date`, `status`, `name_on_card`, `card_number`, `expiration_date`, `user_id`, `product_id`) VALUES
(18, 55470123, '2020-06-15', 'shipped', 'Marwa', '51c983fd74d04728e7615b1ddbe2433fb2b8d11995dd6c49434b7278cc559617', '2022-06-00', 24, 18),
(19, 66595700, '2020-06-15', 'shipped', 'Marwa', '2b211448b53a4ab638b90828957dd5d1c178302280ae6afd3ab7f1028bb9d761', '2023-07-00', 24, 30),
(20, 55789264, '2020-06-15', 'shipped', 'Nora', '025c975551f0f1c6b9a64c6e2f0b772e63838c950bf9b1d575b7218038c9e77c', '2022-08-00', 11, 21),
(21, 66116913, '2020-06-15', 'shipped', 'Nora', '966a0b5e0af457852f709b0ec3f006e73e02de025f7d0143d9c8ebf21d36f4da', '2028-04-00', 11, 32),
(22, 55524173, '2020-06-16', 'shipped', 'Nora', '5681e7cd516a74a88c4b13cfe90a976e322557c3540de331b1a5f502e6c4842c', '2022-08-00', 11, 21),
(23, 55506266, '2020-06-16', 'shipped', 'Nora', '8a9bc9585ad5a5b5c58b14162996aa2b23afb7a1c0df4a674a4e01dfc5dc74b8', '2024-04-00', 11, 21);

-- --------------------------------------------------------

--
-- Table structure for table `poster`
--

CREATE TABLE `poster` (
  `poster_id` int(11) NOT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `poster`
--

INSERT INTO `poster` (`poster_id`, `image`) VALUES
(4, 'storage_poster\\1586915706231newsfeed1.PNG'),
(5, 'storage_poster\\1586915711879newsfeed2.PNG'),
(6, 'storage_poster\\1586915716333newsfeed3.PNG');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `product_name` text,
  `price` double(11,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `supplier` text NOT NULL,
  `image` text NOT NULL,
  `category` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `product_name`, `price`, `quantity`, `supplier`, `image`, `category`) VALUES
(14, 'Iphone XS Max ', 15989.00, 300, 'Apple', 'storage_product\\1578602090348iphone_xs_max.jpg', 'mobile'),
(15, 'Xiaomi Mi A1', 2555.00, 400, 'Xiaomi', 'storage_product\\1578602439473mi_a1.jpg', 'mobile'),
(16, 'Samsung Galaxy S10', 1266.60, 450, 'Samsung ', 'storage_product\\1578602676852galaxy_s10.jpg', 'mobile'),
(17, 'Huawei P30 Lite', 4695.00, 450, 'Huawei ', 'storage_product\\1578602815200p30_llte.jpg', 'mobile'),
(18, 'Apple iPhone 6S ', 5319.00, 300, 'Apple', 'storage_product\\1578602897488iphone_6s.jpg', 'mobile'),
(19, 'Huawei Y9 prime', 3380.00, 300, 'Huawei', 'storage_product\\1578603046641y9_prime.jpg', 'mobile'),
(20, 'Oppo Find X ', 15147.00, 300, 'Oppo ', 'storage_product\\1578603154028find_x.jpg', 'mobile'),
(21, 'Oppo A5S ', 2649.00, 700, 'Oppo ', 'storage_product\\1578603248234oppo_a5s.jpg', 'mobile'),
(22, 'Apple Iphone X With Facetime', 13888.00, 690, 'Apple', 'storage_product\\1578603369303iphone_x.jpg', 'mobile'),
(23, 'Samsung Galaxy S9', 10149.00, 500, 'Samsung', 'storage_product\\1578787404437galaxy_s.jpg', 'mobile'),
(24, 'Apple MacBook Air MQD32', 16499.00, 700, 'Apple', 'storage_product\\1578949241064apple_macbook_air_mqd32.jpg', 'laptop'),
(25, 'Apple MacBook MNYF2 ', 20200.00, 700, 'Apple', 'storage_product\\1578949482473apple_macbook_mnyf2.jpg', 'laptop'),
(26, 'Lenovo V145 Laptop', 3249.00, 700, 'Lenovo', 'storage_product\\1578949593137lenovo_v145.jpg', 'laptop'),
(27, 'Lenovo V130 Laptop', 4799.00, 676, 'Lenovo', 'storage_product\\1578949711235lenovo_v130.jpg', 'laptop'),
(28, 'HP PRO Book 450', 8850.00, 676, 'HP', 'storage_product\\1578949852513hp_pro_book_450.jpg', 'laptop'),
(29, 'HP OMEN ', 2899.90, 943, 'HP', 'storage_product\\1578949956839hp_omen.jpg', 'laptop'),
(30, 'Dell 3580 Laptop', 9999.00, 340, 'Dell', 'storage_product\\1578950115413dell_3580.jpg', 'laptop'),
(31, 'Dell Inspiron 3576 Notebook', 6191.00, 600, 'Dell', 'storage_product\\1578950196252dell_inspiron_3576.jpg', 'laptop'),
(32, 'Lenovo Ideapad 330 Laptop', 3740.00, 845, 'Lenovo ', 'storage_product\\1578950328009lenovo_ideapad_330.jpg', 'laptop'),
(33, 'Dell G3-3579 Gaming Laptop', 18444.00, 755, 'Dell', 'storage_product\\1578950480476dell_g3-3579_gaming.jpg', 'laptop'),
(35, 'Jenga toy - Folds High Wood Toys', 199.00, 70, 'Scoopforsale', 'storage_product\\1584383886944item_XXL_22009042_28649300.jpg', 'toy'),
(36, 'Dominoes 3636 In Box Toy - Multicolor', 59.00, 245, 'Macashope\n', 'storage_product\\1584384204697item_XL_9306984_10099208.jpg', 'toy'),
(37, 'Lego Toy Ninjago The Golden Dragon ', 799.00, 450, 'SouqEgypt', 'storage_product\\1584384346218item_XL_43457666_994d4cfc787d8.jpg', 'toy'),
(38, 'Clay Ice Cream Machine Toy For Kids', 115.00, 300, 'Macashope', 'storage_product\\1584384495028item_XL_37073918_144768916.jpg', 'toy'),
(39, 'Little Chef Toy for Kids', 350.00, 250, 'Dokan55', 'storage_product\\1584385368740item_XL_77268323_3078d6062ac15.jpg', 'toy'),
(40, 'Village Building Block Toy Set', 119.00, 100, 'Kingsons', 'storage_product\\1584385632569item_XL_38586747_150503533.jpg', 'toy'),
(41, 'Musical Electronic Keyboard Toy for Kids', 482.00, 150, 'SouqEgypt', 'storage_product\\1584385840711item_XL_68472912_3d16f21654ba9.jpg', 'toy'),
(48, 'Trefl Toy Story 4 Puzzle', 114.00, 150, 'SouqEgypt', 'storage_product\\1584386909831item_XL_73895297_44847625945bc.jpg', 'toy'),
(49, 'Fishing Toy Battery Operated 42 Fishes', 129.00, 660, 'SouqEgypt', 'storage_product\\1584387499975item_XL_6068961_3185089.jpg', 'toy'),
(50, 'Bingo game', 35.00, 660, 'RightTech', 'storage_product\\1584387715524item_XL_12104633_18232194.jpg', 'toy'),
(51, 'Bimbo baby bouncer stainless steel', 210.00, 30, 'Moro', 'storage_product\\1584387944814item_XL_48416598_b3b145a380c89.jpg', 'baby'),
(52, 'Chicco Snappy Stroller', 1852.00, 45, 'SouqEgypt', 'storage_product\\1584388083812item_XL_9017885_32112873.jpg', 'baby'),
(53, 'Moro rocking chair', 250.00, 12, 'Moro', 'storage_product\\1584388198554item_XL_99338310_2b4fe0429332d.jpg', 'baby'),
(54, 'Nania Cosmo SP Group Baby Car Seat', 1000.00, 300, 'SouqEgypt', 'storage_product\\1584392483475item_XL_68323402_e099c9938f6ad.jpg', 'baby'),
(55, 'Universal CarryCot For Boys', 348.00, 23, 'SouqEgypt', 'storage_product\\1584392588404item_XXL_32856743_fca2f9b1fd841.jpg', 'baby'),
(56, 'Mastela 6519 Portable Swing', 1630.00, 50, 'Malikababyshop', 'storage_product\\1584392713899item_XL_26130347_48578034.jpg', 'baby'),
(57, 'Baby Walker with Toys', 1500.00, 39, 'SouqEgypt', 'storage_product\\1584392793248item_XL_69832926_d5665dd53cac7.jpg', 'baby'),
(58, 'Baby travel cot bag 3 in 1', 318.00, 57, ' Elgmalnew', 'storage_product\\1584393087687item_XL_7750165_6665206.jpg', 'baby'),
(59, 'Maxi Cosi Citi Baby Car Seat', 3000.00, 60, 'SouqEgypt', 'storage_product\\1584393757637item_XL_33112354_126296765.jpg', 'baby'),
(60, 'Universal Mini Cross Rocking Baby Crib', 1125.00, 34, 'SouqEgypt', 'storage_product\\1584394081100item_XL_32894804_150649273.jpg', 'baby');

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `review_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `feedback` varchar(150) DEFAULT NULL,
  `rate` float(11,1) DEFAULT NULL,
  `review_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`review_id`, `user_id`, `product_id`, `feedback`, `rate`, `review_date`) VALUES
(2, 11, 14, 'Nice', 3.0, '2020-03-09'),
(4, 11, 15, 'good', 4.0, '2020-03-10'),
(5, 24, 15, 'Amazing', 5.0, '2020-03-10'),
(47, 11, 33, 'Very good', 4.0, '2020-05-05'),
(49, 24, 25, 'Wonderful', 5.0, '2020-05-06');

-- --------------------------------------------------------

--
-- Table structure for table `shipping`
--

CREATE TABLE `shipping` (
  `shipping_id` int(11) NOT NULL,
  `address` text,
  `city` text,
  `country` text,
  `zip` text,
  `phone` text,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `shipping`
--

INSERT INTO `shipping` (`shipping_id`, `address`, `city`, `country`, `zip`, `phone`, `user_id`, `product_id`) VALUES
(18, 'Sidi Gaber', 'Alexandria', 'Egypt', '4545', '012564988', 24, 18),
(19, 'Sidi Gaber', 'Alexandria', 'Egypt', '4545', '01079559906', 24, 30),
(20, 'Naser City', 'Cairo', 'Egypt', '3030', '0128686888', 11, 21),
(21, 'Naser City', 'Cairo', 'Egypt', '3030', '8655686', 11, 32),
(22, 'Naser City', 'Cairo', 'Egypt', '3030', '8568690', 11, 21);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `email` text NOT NULL,
  `password` text NOT NULL,
  `gender` text NOT NULL,
  `age` int(11) NOT NULL,
  `image` text NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`, `gender`, `age`, `image`, `isAdmin`) VALUES
(1, 'Ibrahiem', 'ub@yahoo.com', '$2b$10$LPcm3fgAlSVld2j5LJppQu1pepDUP6TDPrMmHbhR98R0XTwc51t/u', 'male', 20, '', 0),
(2, 'Soha ALi', 'soha@gmail.com', '$2b$10$L425NpkGdt4xeU.xKzk3H.yDk6QAYz27UTRiRSzb7TLTywvFVScmO', '', 20, '', 0),
(3, 'Hana', 'hana@gmail.com', '$2b$10$wS.5Tt9IwMrVTxYmzunxPeO89GHhE4n0U/3Tl9dRL.MAhypfEI79m', '', 0, '', 0),
(4, 'Salma', 'salma@yahoo.com', '$2b$10$HJDIhG/9IjYDNaE4UnnAPO.oSNh84F1Z3eTWqUN5ScihSUm6nVK6y', '', 0, '', 0),
(5, 'Rami', 'rami@gmail.com', '$2b$10$Se7Cq/jvuTHWX5mgiP.JKeALEhu2.3d/RnYovQnZEJatrvr.nDhFe', 'undertermined', 0, '', 0),
(6, 'Noha', 'noha@gmail.com', '$2b$10$eixgjcRPslWalSIH.ydQkeO1//36x2EHyCuOJOBivNsnL2JLfObhq', 'undertermined', 0, '', 0),
(7, 'Hala', 'hala@gmail.com', '$2b$10$YlR6KkjIZDdx5GCHubfhR.ZF7oyIh4pIH0jFYqe5hPhkorxBsws9W', 'undertermined', 0, '', 0),
(10, 'Samah', 'samah@gmail.com', '$2b$10$nwzNWE7Tgh97o4.vhrUcouQsHtAZJIhTjwwNt5SCbrUy5MRlHtq0O', 'undertermined', 0, '', 0),
(11, 'Nora', 'nora@gmail.com', '$2b$10$0DuvDVZV2haShCHS67yhtepXp1CycJ5u1HfOu0FMjPyfRPqjVGxxa', 'undertermined', 0, 'storage_user\\1582402749541flower.jpeg', 0),
(12, 'Laila', 'laila@gmail.com', '$2b$10$izHdZQARIOI1BQC4xXIaT.I9uYHeHNeT5wdHEsePzkyITnBlT0Hie', 'undertermined', 0, 'storage_user\\1582408987863tinkerbell.jpg', 0),
(13, 'Ramez', 'ramez@gmail.com', '$2b$10$BOCch/rDmv3nj.dXru/UuOx4OOgyAUHk7opdHR9juSNXU9yTJokVy', 'undertermined', 0, '', 0),
(14, 'Yoyo', 'yoyo@gmail.com', '$2b$10$k/kCBPDTwL0XaklF4KNLSec8zstHr7F2fHxq9gW0N6vb/.t6JZLpy', 'undertermined', 0, '', 0),
(16, 'Mahmoud', 'mahmoud@gmail.com', '$2b$10$Hg8gFwwq/0yRHUgSMqCwIOgpm6Cl8/KcxuDqzD0leC1LcNYmCpAfW', 'undertermined', 0, '', 0),
(18, 'Noran', 'noran@gmail.com', '$2b$10$JnLybPmqZL62fTv7zDiL0Oo984GT0TRckJbATjGhgWpQvdF8xjkv.', 'undertermined', 0, '', 0),
(21, 'Hadi', 'hadi@gmail.com', '$2b$10$LMnFEE1CicWWRo0ZEG93dOc4OIK15oWCgGt2/1z8/K4E6nf3KjnLm', 'undertermined', 0, '', 0),
(23, 'Rania', 'rania@gmail.com', '$2b$10$B3QxKhzqRB5HvLA5/4MSA.M8cMW3R7nIaVsXD.FdSFzx6w6pcDckC', 'undertermined', 0, '', 0),
(24, 'Marwa Eltayeb', 'marwa_eltayeb@yahoo.com', '$2b$10$TS95svgviRXseNqgQ5Bzr.O5zP8BGx2tNUx8qhObXv6ychDcNdye6', 'undertermined', 0, 'storage_user\\1583003208523tinkerbell.jpg', 1),
(25, 'Nahla', 'nahla@gmail.com', '$2b$10$/XW7ccom7cb7L6N55MHtd.ITGNinMIt6kesSpq33EM3mnO.yX4Thu', 'female', 30, '', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`user_id`,`product_id`),
  ADD UNIQUE KEY `cart_constraint` (`user_id`,`product_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `favorite`
--
ALTER TABLE `favorite`
  ADD PRIMARY KEY (`favorite_id`),
  ADD UNIQUE KEY `fav_constraint` (`user_id`,`product_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`history_id`),
  ADD UNIQUE KEY `history_constraint` (`user_id`,`product_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `ordering`
--
ALTER TABLE `ordering`
  ADD PRIMARY KEY (`ordering_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `poster`
--
ALTER TABLE `poster`
  ADD PRIMARY KEY (`poster_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`review_id`),
  ADD UNIQUE KEY `review_constraint` (`user_id`,`product_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `shipping`
--
ALTER TABLE `shipping`
  ADD PRIMARY KEY (`shipping_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `favorite`
--
ALTER TABLE `favorite`
  MODIFY `favorite_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=112;
--
-- AUTO_INCREMENT for table `history`
--
ALTER TABLE `history`
  MODIFY `history_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ordering`
--
ALTER TABLE `ordering`
  MODIFY `ordering_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `poster`
--
ALTER TABLE `poster`
  MODIFY `poster_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;
--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `review_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;
--
-- AUTO_INCREMENT for table `shipping`
--
ALTER TABLE `shipping`
  MODIFY `shipping_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `favorite`
--
ALTER TABLE `favorite`
  ADD CONSTRAINT `favorite_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `favorite_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `history`
--
ALTER TABLE `history`
  ADD CONSTRAINT `history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `history_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ordering`
--
ALTER TABLE `ordering`
  ADD CONSTRAINT `ordering_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ordering_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `review_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `shipping`
--
ALTER TABLE `shipping`
  ADD CONSTRAINT `shipping_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `shipping_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
