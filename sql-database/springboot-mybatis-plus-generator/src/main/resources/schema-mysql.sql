-- --------------------
-- MySQL
-- --------------------
CREATE TABLE `dept`
(
    `DEPTNO` int(2) NOT NULL,
    `DNAME`  varchar(14) DEFAULT NULL,
    `LOC`    varchar(13) DEFAULT NULL,
    PRIMARY KEY (`DEPTNO`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `emp`
(
    `EMPNO`    bigint(20) NOT NULL,
    `ENAME`    varchar(10)   DEFAULT NULL,
    `JOB`      varchar(9)    DEFAULT NULL,
    `MGR`      int(4)        DEFAULT NULL,
    `HIREDATE` date          DEFAULT NULL,
    `SAL`      decimal(7, 2) DEFAULT NULL,
    `COMM`     decimal(7, 2) DEFAULT NULL,
    `DEPTNO`   int(2)        DEFAULT NULL,
    PRIMARY KEY (`EMPNO`),
    KEY        `fk_deptno` (`DEPTNO`),
    CONSTRAINT `emp_ibfk_1` FOREIGN KEY (`DEPTNO`) REFERENCES `dept` (`DEPTNO`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

