CREATE DATABASE IF NOT EXISTS `courseselection`;
CREATE  TABLE IF NOT EXISTS `courseselection`.`User` (
  `uid` BIGINT NOT NULL ,
  `uname` VARCHAR(45) NOT NULL ,
  `upwd` VARCHAR(45) NOT NULL,
  `utype` INT(1) NOT NULL,
  PRIMARY KEY (`uid`) ,
  UNIQUE INDEX `uid_UNIQUE` (`uid` ASC)
  );
CREATE  TABLE IF NOT EXISTS `courseselection`.`Student` (
  `sid` BIGINT NOT NULL ,
  `sname` VARCHAR(45) NOT NULL ,
  `gender` INT NULL ,
  `major` VARCHAR(45) NOT NULL,
  `start_year` INT NULL ,
  PRIMARY KEY (`sid`) ,
  UNIQUE INDEX `sid_UNIQUE` (`sid` ASC)
);
CREATE  TABLE IF NOT EXISTS `courseselection`.`Teacher` (
  `tid` INT NOT NULL ,
  `tname` VARCHAR(45) NULL ,
  PRIMARY KEY (`tid`) );
CREATE  TABLE IF NOT EXISTS `courseselection`.`Course` (
  `cid` INT NOT NULL ,
  `cname` VARCHAR(45) NULL ,
  `tid` INT NULL ,
  `point` INT NULL ,
  PRIMARY KEY (`cid`) ,
  INDEX `tid` (`tid` ASC) ,
  CONSTRAINT `tid`
    FOREIGN KEY (`tid` )
    REFERENCES `courseselection`.`Teacher` (`tid` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION);
CREATE  TABLE IF NOT EXISTS `courseselection`.`TakeCourse` (
  `sid` BIGINT NOT NULL ,
  `cid` INT NOT NULL ,
  `year` INT NULL ,
  `score` INT NULL ,
  PRIMARY KEY (`sid`, `cid`) ,
  INDEX `sid` (`sid` ASC) ,
  INDEX `cid` (`cid` ASC) ,
  CONSTRAINT `sid`
    FOREIGN KEY (`sid` )
    REFERENCES `courseselection`.`Student` (`sid` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `cid`
    FOREIGN KEY (`cid` )
    REFERENCES `courseselection`.`Course` (`cid` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

------

USE courseselection;
CREATE TRIGGER usertrigger1
AFTER INSERT
ON student
FOR each row
INSERT INTO `user`(uid, uname, upwd, utype) VALUES(new.sid, new.sname, new.sid, 1);

USE courseselection;
CREATE TRIGGER usertrigger2
AFTER INSERT
ON teacher
FOR each row
INSERT INTO `user`(uid, uname, upwd, utype) VALUES(new.tid, new.tname, new.tid, 0);

USE courseselection;
CREATE TRIGGER usertrigger3
AFTER DELETE
ON teacher
FOR each row
DELETE FROM `user` WHERE user.uid = old.tid;

USE courseselection;
CREATE TRIGGER usertrigger4
AFTER DELETE
ON student
FOR each row
DELETE FROM `user` WHERE user.uid = old.sid;