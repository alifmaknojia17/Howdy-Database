DROP DATABASE howdy;
CREATE DATABASE IF NOT EXISTS `howdy`;
USE howdy;

CREATE TABLE IF NOT EXISTS `classes` (

	crn int, 
	subj varchar(10), 
	crse varchar(20), 
	sec int, 
	cmp varchar(10), 
	cred varchar(20), 
	title varchar(100), 
	days varchar(20), 
	startTime varchar(20),
	endTime varchar(20), 
	cap int, 
	act int, 
	rem int, 
	instructor varchar(200), 
	startDate varchar(20),
	endDate varchar(20), 
	building varchar(20), 
	roomnumber varchar(20)
);

CREATE TABLE IF NOT EXISTS `course` (

	crn int, 
	subj varchar(10), 
	crse varchar(20), 
	sec int, 
	cmp varchar(10), 
	cred varchar(20), 
	title varchar(100), 
	days varchar(20), 
	startTime varchar(20),
	endTime varchar(20), 
	TimetoNext time,
	cap int, 
	act int, 
	rem int, 
	instructor varchar(200), 
	startDate varchar(20),
	endDate varchar(20), 
	building varchar(20), 
	roomnumber varchar(20)
);

CREATE TABLE IF NOT EXISTS `professor` (

	subj varchar(10),
	crse varchar(20),
	instructor varchar(200)
);

CREATE TABLE IF NOT EXISTS `room` (

	crn int,
	building varchar(20),
	roomnumber varchar(20),
	cap int,
	days varchar(20),
	startTime time,
	endTime time,
	TimetoNext time
);

CREATE TABLE IF NOT EXISTS `temps` (

	crn int,
	building varchar(20),
	roomnumber varchar(20),
	cap int,
	days varchar(20),
	startTime time,
	endTime time
);

CREATE TABLE IF NOT EXISTS `temp` (

	TimetoNext time
);

LOAD DATA INFILE '/Users/alifmaknojia/Desktop/howdyin' INTO TABLE classes 
		  FIELDS TERMINATED BY ' ';
LOAD DATA INFILE '/Users/alifmaknojia/Desktop/howdyfreetime' INTO TABLE temp;

INSERT INTO professor SELECT subj, crse, instructor FROM classes;

INSERT INTO temps 
			SELECT crn, building, roomnumber, cap, days, startTime, endTime 
			FROM classes WHERE endTime != 'WEB' AND startTime != 'tba' 
			AND endTime != 'WEB' AND endTime != 'tba' AND building != 'tba'
			AND roomnumber != 'tba' AND Days != 'tba' AND building != 'web' 
			ORDER BY building ASC, roomnumber ASC, days, startTime;

ALTER TABLE temps ADD id int NOT NULL AUTO_INCREMENT PRIMARY KEY;
ALTER TABLE temp ADD id int NOT NULL AUTO_INCREMENT PRIMARY KEY;

INSERT INTO room 
			SELECT crn,building,roomnumber,cap,days,starttime,endtime,timetonext
			FROM temps INNER JOIN temp ON temps.id = temp.id;

INSERT INTO course
			SELECT classes.crn, classes.subj, classes.crse, classes.sec, 
				   classes.cmp, classes.cred, classes.title, room.days, 
				   room.starttime, room.endtime, room.timetonext, room.cap, 
				   classes.act, classes.rem, classes.instructor, classes.startdate, 
				   classes.enddate, room.building, room.roomnumber 
				   FROM classes INNER JOIN room ON classes.crn = room.crn;
				   