-- --------------------
-- H2
-- --------------------

CREATE TABLE city
(
    id      INT PRIMARY KEY auto_increment,
    name    VARCHAR,
    state   VARCHAR,
    country VARCHAR
);

CREATE TABLE account
(
    id         INT PRIMARY KEY auto_increment,
    login_name VARCHAR,
    password   VARCHAR,
    nick_name  VARCHAR,
    age        INT,
    location   VARCHAR
);
