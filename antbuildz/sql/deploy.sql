SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+08:00";


drop schema if exists antbuildzteam6;
CREATE SCHEMA antbuildzteam6;
USE antbuildzteam6;

-- --------------------------------------------------------

--
-- Table structure for table "user"
--

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id int NOT NULL AUTO_INCREMENT,
  uen varchar(10) NOT NULL,
  company_name varchar(30) NOT NULL,
  email varchar(30) NOT NULL,
  password varchar(255) NOT NULL,
  ispartner boolean NOT NULL,
  isverified boolean NOT NULL,
  CONSTRAINT user_pk PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1; 



--
-- Table structure for table "admin"
--

DROP TABLE IF EXISTS admin;
CREATE TABLE admin (
  email varchar(30) NOT NULL,
  password varchar(255) NOT NULL,
  CONSTRAINT admin_pk PRIMARY KEY (email)
) ENGINE=InnoDB; 



--
-- Table structure for table "request"
--


DROP TABLE IF EXISTS request;

CREATE TABLE request (
  id int NOT NULL AUTO_INCREMENT,
  user_id int NOT NULL,
  request_open_datetime DATETIME NOT NULL,
  request_close_datetime DATETIME NOT NULL,
  quantity int NOT NULL,
  transport_type varchar(30) NOT NULL,
  capacity varchar(30) NOT NULL,
  origin_location varchar(100) NOT NULL,
  destination_location varchar(100) NOT NULL, 
  rental_start_datetime DATETIME NOT NULL,
  rental_end_datetime DATETIME NOT NULL,
  accepted_bid_id int,
  equipment_volume DOUBLE NOT NULL,
  equipment_weight DOUBLE NOT NULL,
  special_request varchar(150),  
  CONSTRAINT request_pk PRIMARY KEY (id),
  CONSTRAINT request_fk1 FOREIGN KEY (user_id) references user(id)
) ENGINE=InnoDB AUTO_INCREMENT=1; 

--
-- Table structure for table "transport"
--

DROP TABLE IF EXISTS transport;
CREATE TABLE transport (
  id int NOT NULL AUTO_INCREMENT,
  partner_id int NOT NULL,
  capacity DOUBLE NOT NULL,
  CONSTRAINT transport_pk PRIMARY KEY (id),
  CONSTRAINT transport_fk1 FOREIGN KEY (partner_id) references user(id)
) ENGINE=InnoDB AUTO_INCREMENT=1; 


--
-- Table structure for table "bid"
--

DROP TABLE IF EXISTS bid;
CREATE TABLE bid (
  id int NOT NULL AUTO_INCREMENT,
  request_id int NOT NULL,
  partner_id int NOT NULL,
  CONSTRAINT bid_pk PRIMARY KEY (id),
  CONSTRAINT bid_fk1 FOREIGN KEY (request_id) references request(id),
  CONSTRAINT bid_fk2 FOREIGN KEY (partner_id) references user(id)
) ENGINE=InnoDB AUTO_INCREMENT=1; 
