create table if not exists USER (
    ID long not null primary key auto_increment,
    NAME varchar(100),
    SEX varchar(1),
    AGE NUMBER(3),
    ID_NO VARCHAR(18),
    PHONE_NUM VARCHAR(11),
    EMAIL VARCHAR(100),
    CREATE_TIME DATE,
    MODIFY_TIME DATE,
    STATE VARCHAR(1)
);

create table if not exists U_Order (
    ID long not null primary key auto_increment,
    USER_ID long,
    CREATE_TIME DATE,
    MODIFY_TIME DATE,
    STATE VARCHAR(1)
);



