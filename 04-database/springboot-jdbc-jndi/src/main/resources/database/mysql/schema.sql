create table if not exists USER (
    USER_ID BIGINT not null primary key auto_increment,
    USER_NAME varchar(100),
    USER_SEX varchar(1),
    USER_AGE INT(3),
    USER_ID_NO VARCHAR(18),
    USER_PHONE_NUM VARCHAR(11),
    USER_EMAIL VARCHAR(100),
    CREATE_TIME DATETIME,
    MODIFY_TIME DATETIME,
    USER_STATE VARCHAR(1)
);