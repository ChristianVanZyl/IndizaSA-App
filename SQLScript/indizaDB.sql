DROP DATABASE IF EXISTS `indizaDB`;
CREATE SCHEMA IF NOT EXISTS `indizaDB` DEFAULT CHARACTER SET utf8;
USE `indizaDB`;

-- User Table --

CREATE TABLE `indizaDB`.`user_table`(
`userID` INT NOT NULL AUTO_INCREMENT,
`userName` VARCHAR(100) NOT NULL,
`userSurname` VARCHAR(100) NOT NULL,
`userEmail` VARCHAR(100) NOT NULL,
`userPassword` VARCHAR(20) NOT NULL,
`userCredit` INT,
PRIMARY KEY(`userID`),
CONSTRAINT `userEmail_unique` UNIQUE (`userEmail`)
);

CREATE TABLE `indizaDB`.`transactionrecords_table`(
`trID` INT NOT NULL AUTO_INCREMENT,
`userID` INT NOT NULL,
`invoiceFile` LONGTEXT,
PRIMARY KEY(`trID`),
CONSTRAINT `uID`
	FOREIGN KEY (`userID`)
    REFERENCES `indizaDB`.`user_table`(`userID`)
);


-- Airport Table --
CREATE TABLE `indizaDB`.`airport_table`(
`airportID` INT NOT NULL AUTO_INCREMENT,
`airportName` VARCHAR(200) NOT NULL,
PRIMARY KEY(`airportID`)
);


-- Flight Table --

CREATE TABLE `indizaDB`.`flight_table`(
`flightID` INT NOT NULL AUTO_INCREMENT,
`destination` VARCHAR(200) NOT NULL,
`price` DOUBLE NOT NULL,
`dateOf` DATE NOT NULL,
`deptTime` VARCHAR(200) NOT NULL,
`duration` VARCHAR(200) NOT NULL,
`arrivalTime` VARCHAR(200) NOT NULL,
`numSeatsLeft` INT NOT NULL,
`airportID` INT NOT NULL,
PRIMARY KEY(`flightID`),
CONSTRAINT `airprtID`
	FOREIGN KEY (`airportID`)
    REFERENCES `indizaDB`.`airport_table`(`airportID`)
);

-- Reservation Table --
CREATE TABLE `indizaDB`.`reservation_table`(
`reservationID` INT NOT NULL AUTO_INCREMENT,
`userID` INT NOT NULL,
`flightID` INT NOT NULL,
`statusR` VARCHAR(200) NOT NULL,
PRIMARY KEY(`reservationID`),
CONSTRAINT `usrID`
	FOREIGN KEY (`userID`)
    REFERENCES `indizaDB`.`user_table`(`userID`),
CONSTRAINT `fltID`
	FOREIGN KEY (`flightID`)
    REFERENCES `indizaDB`.`flight_table`(`flightID`)
	
);
-- Payment Table --
CREATE TABLE `indizaDB`.`payment_table`(
`paymentID` INT NOT NULL AUTO_INCREMENT,
`reservationID` INT NOT NULL,
`paymentStatus` VARCHAR(200) NOT NULL,
PRIMARY KEY(`paymentID`),
CONSTRAINT `reservationID`
	FOREIGN KEY (`reservationID`)
    REFERENCES `indizaDB`.`reservation_table`(`reservationID`)
  );
    

-- INSERT DATA --

-- user table insert -- 

INSERT INTO `user_table` (`userID`, `userName`, `userSurname`, `userEmail`, `userPassword`, `userCredit`) VALUES
(1, "John", "Smith", "johns@gmail.com", "thIs123", 2000),
(2, "Sean", "Burgess", "seanb@gmail.com", "#EevT6Im", 4000),
(3, "Paul", "Hodges", "paulh@gmail.com", "78pdF9XS", 3200),
(4, "Wendy", "Grant", "wendyg@gmail.com", "1iy_DU3+", 1500),
(5, "Sally", "May", "salmay@gmail.com", "+QNPdK4q", 6300),
(6, "Sean", "Burgess", "sb@gmail.com", "#EevT6Im", 1000),
(7, "Jan", "Morrison", "jmorris@gmail.com", "-wX0I0=e", 1200),
(8, "Liam", "Turner", "lturner@gmail.com", "78pdF9XS", 1600),
(9, "Kevin", "Brown", "kbrown@gmail.com", "%fpZHy8_", 2100),
(10, "Andrea", "Payne", "apayne@gmail.com", "xYK%^4lf", 300),
(11, "Kylie", "Young", "kyoung@gmail.com", "R%_?1BaA", 2000),
(12, "Leonard", "Blake", "lblake@gmail.com", "@5gSyUdz", 5300),
(13, "Rachel", "Avery", "ravery@gmail.com", "0TgYFw#m", 800),
(14, "Joshua", "Bell", "jbell@gmail.com", "F4$&PwXz", 950),
(15, "Gavin", "Dowd", "gdowd@gmail.com", "@7ao+O^r", 2900),
(16, "Victoria", "Wright", "vwright@gmail.com", "%gb?0PcI", 1500),
(17, "Charles", "Kerr", "ck@gmail.com", "@5gSyUdz", 1300),
(18, "Rose", "Thomson", "rt@gmail.com", "0TgYFw#m", 400),
(19, "Dorothy", "Ferguson", "df@gmail.com", "F4$&PwXz", 0),
(20, "Rose", "Churchill", "rc@gmail.com", "@7ao+O^r", 200),
(21, "Leonard", "Scott", "ls@gmail.com", "%gb?0PcI", 500),
(22, "Maria", "Glover", "mglover@gmail.com", "@5gSyUdz", 3200),
(23, "Hannah", "Lambert", "hlambert@gmail.com", "0TgYFw#m", 1400),
(24, "Tracey", "Berry", "tberry@gmail.com", "F4$&PwXz", 300),
(25, "Frank", "Hemmings", "fhemmings@gmail.com", "@7ao+O^r", 0),
(26, "Connor", "Jackson", "cjack@gmail.com", "%gb?0PcI", 700),
(27, "Jane", "Rampling", "jrampling@gmail.com", "@5gSyUdz", 5300),
(28, "Deirdre", "Campbell", "dcampbell@gmail.com", "0TgYFw#m", 700),
(29, "John", "Young", "johnyoung@gmail.com", "F4$&PwXz", 20),
(30, "Simon", "Pullman", "simonp@gmail.com", "@7ao+O^r", 5500),
(31, "Kylie", "Taylor", "kyltaylor@gmail.com", "%gb?0PcI", 2300),	
(32, "Jane", "Rampling", "janeramp@gmail.com", "@5gSyUdz", 9300),
(33, "Piers", "Rampling", "pramp@gmail.com", "0TgYFw#m", 100),
(34, "Liam", "Dyer", "liamdy@gmail.com", "F4$&PwXz", 150),
(35, "Harry", "Duncan", "hduncan@gmail.com", "@7ao+O^r", 3900),
(36, "Edward", "Scott", "edscott@gmail.com", "%gb?0PcI", 1500),		
(37, "Owen", "McGrath", "omcgrath@gmail.com", "@5gSyUdz", 2330),
(38, "Pierre", "Owens", "pierreow@gmail.com", "0TgYFw#m", 1950),
(39, "Ruth", "Welch", "rwelch@gmail.com", "F4$&PwXz", 50),
(40, "Stewart", "Payne", "stepayne@gmail.com", "@7ao+O^r", 900);


-- flight table flights insert -- 

-- PLEASE NOTE, to account for number of entries, only one flight is fully booked. All 40 users and their transactions --
-- need to be noted for the DB to function correctly -- 
-- all passengers for Johannesburg - O.R. Tambo (JNB) to Mauritius (MRU) booked out 0 seats left  --


INSERT INTO `airport_table` (`airportID`, `airportName`) VALUES
(1, "O.R. Tambo"),
(2, "King Shaka"),
(3, "Cape Town");

INSERT INTO `flight_table` (`flightID`, `destination`, `price`, `dateOf`, `deptTime`, `duration`, `arrivalTime`, `numSeatsLeft`, `airportID`) VALUES
(1, "Cape Town", 920, "2021-11-22", "06:00", "2h10", "08:10", 20, 2),
(2, "Port Elizabeth",  998, "2021-12-03", "11:15", "1h20", "12:35", 35, 1),
(3, "East London",  1010, "2021-11-17", "13:20", "1h35", "14:55", 6, 3),
(4, "Johannesburg",  3252, "2021-12-16", "11:40", "1h20", "13:20", 22, 2),
(5, "Johannesburg",  841, "2021-12-20", "10:05", "2h10", "11:55", 10, 3),
(6, "Harare", 1444, "2021-11-15", "11:40", "1h35", "13:15", 11, 1),
(7, "Cape Town", 2519, "2021-12-24", "07:15","2h10", "09:25",  39, 2),
(8, "Durban", 1804, "2021-11-24","06:40", "2h", "08:40", 11, 1),
(9, "Johannesburg", 3152, "2021-12-09", "11:40", "1h40", "13:20", 15, 3),
(10, "Johannesburg", 4118, "2021-11-14", "14:20","1h50", "16:10", 12, 2),
(11, "Mauritius", 6616, "2021-12-07", "09:55", "6h10", "16:05",  0, 1),
(12, "Johannesburg", 818, "2021-12-03", "09:40", "1h10", "10:50",  5, 3),
(13, "Cape Town", 1117, "2021-11-17", "14:30", "2h", "16:30",  25, 1),
(14, "Mauritius", 4639, "2021-12-02", "17:10", "2h30", "19:40",  35, 1),
(15, "Victoria Falls", 2232, "2021-11-27", "13:45", "1h35", "15:20", 2, 1),
(16, "Victoria Falls", 2014, "2021-11-20", "11:30", "1h37", "13:07",  3, 1),
(17, "Johannesburg", 716, "2021-12-03", "09:40", "1h10", "10:50", 15, 2),
(18, "Cape Town", 932, "2021-12-03", "06:00", "2h", "08:00", 30, 2),
(19, "Cape Town", 1602, "2021-11-14", "11:35", "2h10", "13:45", 5, 1),
(20, "East London", 849, "2021-12-08", "13:55", "1h35", "15:30",  11, 3);


-- flight table flights insert -- 

INSERT INTO `reservation_table` (`reservationID`, `userID`, `flightID`, `statusR`) VALUES
(1, 1, 11, "active"), 
(2, 2, 11, "active"), 
(3, 3, 11, "active"), 
(4, 4, 11, "active"), 
(5, 5, 11, "active"), 
(6, 6, 11, "active"), 
(7, 7, 11, "active"), 
(8, 8, 11, "active"), 
(9, 9, 11, "active"), 
(10, 10, 11, "active"), 
(11, 11, 11, "active"), 
(12, 12, 11, "active"), 
(13, 13, 11, "active"), 
(14, 14, 11, "active"), 
(15, 15, 11, "active"), 
(16, 16, 11, "active"), 
(17, 17, 11, "active"), 
(18, 18, 11, "active"), 
(19, 19, 11, "active"), 
(20, 20, 11, "active"), 
(21, 21, 11, "active"), 
(22, 22, 11, "active"), 
(23, 23, 11, "active"), 
(24, 24, 11, "active"), 
(25, 25, 11, "active"), 
(26, 26, 11, "active"), 
(27, 27, 11, "active"), 
(28, 28, 11, "active"), 
(29, 29, 11, "active"), 
(30, 30, 11, "active"), 
(31, 31, 11, "active"), 
(32, 32, 11, "active"), 
(33, 33, 11, "active"), 
(34, 34, 11, "active"), 
(35, 35, 11, "active"), 
(36, 36, 11, "active"), 
(37, 37, 11, "active"), 
(38, 38, 11, "active"), 
(39, 39, 11, "active"), 
(40, 40, 11, "active"), 
(41, 39, 14, "active"), 
(42, 3, 14, "active"), 
(43, 6, 14, "active"), 
(44, 25, 14, "active"), 
(45, 33, 14, "active"),
(46, 5, 18, "active"), 
(47, 7, 18, "active"), 
(48, 9, 18, "active"), 
(49, 28, 18, "active"), 
(50, 37, 18, "active"), 
(51, 4, 18, "active"), 
(52, 12, 18, "active"), 
(53, 13, 18, "active"), 
(54, 21, 18, "active"), 
(55, 34, 18, "active"); 


INSERT INTO `payment_table` (`paymentID`, `reservationID`, `paymentStatus`) VALUES
(1, 1, "Paid"),  
(2, 2, "Paid"), 
(3, 3, "Paid"), 
(4, 4, "Paid"), 
(5, 5, "Paid"), 
(6, 6, "Paid"), 
(7, 7, "Paid"), 
(8, 8, "Paid"), 
(9, 9, "Paid"), 
(10, 10, "Paid"), 
(11, 11, "Paid"), 
(12, 12, "Paid"), 
(13, 13, "Paid"), 
(14, 14, "Paid"), 
(15, 15, "Paid"), 
(16, 16, "Paid"), 
(17, 17, "Paid"), 
(18, 18, "Paid"), 
(19, 19, "Paid"), 
(20, 20, "Paid"), 
(21, 21, "Paid"), 
(22, 22, "Paid"), 
(23, 23, "Paid"), 
(24, 24, "Paid"), 
(25, 25, "Paid"), 
(26, 26, "Paid"), 
(27, 27, "Paid"), 
(28, 28, "Paid"), 
(29, 29, "Paid"), 
(30, 30, "Paid"), 
(31, 31, "Paid"), 
(32, 32, "Paid"), 
(33, 33, "Paid"), 
(34, 34, "Paid"), 
(35, 35, "Paid"), 
(36, 36, "Paid"), 
(37, 37, "Paid"), 
(38, 38, "Paid"), 
(39, 39, "Paid"), 
(40, 40, "Paid"),
(41, 41, "Paid"), 
(42, 42, "Paid"), 
(43, 43, "Paid"), 
(44, 44, "Paid"), 
(45, 45, "Paid"), 
(46, 46, "Paid"), 
(47, 47, "Paid"),
(48, 48, "Paid"), 
(49, 49, "Paid"), 
(50, 50, "Paid"), 
(51, 51, "Paid"), 
(52, 52, "Paid"), 
(53, 53, "Paid"),
(54, 54, "Paid"),
(55, 55, "Paid"); 



