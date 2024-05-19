create database DB2024Team01;
use DB2024Team01;

-- User Table
create table User(
handle VARCHAR(50) NOT NULL PRIMARY KEY,
userlink VARCHAR(50)  NOT NULL ,
solvednum INT NOT NULL ,
tier ENUM('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
'10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 
'20', '21', '22', '23', '24', '25', '26', '27', '28', '29', 
'30', '31') NOT NULL,
rank_ingroup INT  NOT NULL 
);

-- uGroup Table (Group은 예약어라서 불편)
CREATE TABLE uGroup (
    groupName VARCHAR(40) NOT NULL PRIMARY KEY,
    solvedNum INT  NOT NULL ,
    ranking INT  NOT NULL 
);

-- Problems 
CREATE TABLE Problems (
    pid VARCHAR(40) NOT NULL PRIMARY KEY,
    pTitle VARCHAR(100),
    tier ENUM('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
              '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', 
              '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', 
              '30', '31') NOT NULL,
    tags JSON  NOT NULL  ,
    solvednum INT  NOT NULL ,
    link VARCHAR(200)  NOT NULL 
);

-- TodayPS
CREATE TABLE TodayPS (
    todayid INT NOT NULL PRIMARY KEY,
    pid VARCHAR(40) NOT NULL,
    picked BOOLEAN,
    handle VARCHAR(50),
    FOREIGN KEY (pid) REFERENCES Problems(pid),
    FOREIGN KEY (handle) REFERENCES User(handle)
);

-- PStogether 
CREATE TABLE PStogether (
    togetherid VARCHAR(40) NOT NULL PRIMARY KEY,
    pid VARCHAR(40) NOT NULL,
    link VARCHAR(100)  NOT NULL ,
    handle VARCHAR(50) ,
    FOREIGN KEY (pid) REFERENCES Problems(pid),
    FOREIGN KEY (handle) REFERENCES User(handle)
);

-- 스키마 확인
select * from User; 
select * from uGroup; 
select * from Problems; 
select * from TodayPS;
select * from PStogether;

-- Drop
drop table PStogether;
drop table TodayPS;
drop table problems;
drop table uGroup;
drop table User;
