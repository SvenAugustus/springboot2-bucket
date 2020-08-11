/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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


CREATE TABLE user
(
    id      INT PRIMARY KEY auto_increment,
    name    VARCHAR,
    role_id INT
);


CREATE TABLE role
(
    id   INT PRIMARY KEY auto_increment,
    name VARCHAR
);

CREATE TABLE permission
(
    id   INT PRIMARY KEY auto_increment,
    name VARCHAR,
    uid  INT
);


insert into role(id, name)
values (10, 'admin');
insert into user(id, name, role_id)
values (1, 'root', 10);
insert into permission(id, name, uid)
values (11, 'connect', 1);
insert into permission(id, name, uid)
values (12, 'resource', 1);

