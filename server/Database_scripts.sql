-- ”дал€ем таблицы, если они существуют
DROP TABLE IF EXISTS timetable;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS usertype;
DROP TABLE IF EXISTS auth;

-- “аблица дл€ хранени€ логина и парол€ пользовател€
CREATE TABLE auth (
     login varchar NOT NULL,
     "password" varchar NOT NULL,
     CONSTRAINT auth_pk PRIMARY KEY (login)
);

INSERT INTO auth(login, password) VALUES ('ADMIN','QURNSU4=');

-- “аблица, содержаща€ в себе типы пользователей
CREATE TABLE usertype (
     "type" varchar NOT NULL,
     CONSTRAINT usertype_pk PRIMARY KEY (type)
);

INSERT INTO usertype(type) VALUES ('ADMINISTRATOR');
INSERT INTO usertype(type) VALUES ('EMPLOYEE');
INSERT INTO usertype(type) VALUES ('USER');

-- “аблица с персональными данными пользователей
CREATE TABLE users (
    id varchar NOT NULL,
    first_name varchar NOT NULL,
    last_name varchar NOT NULL,
    middle_name varchar NULL,
    birth_date timestamp NOT NULL,
    phone_number varchar NOT NULL,
    user_type varchar NOT NULL,
    deleted bool NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

INSERT INTO users(id, first_name, last_name, middle_name, birth_date, phone_number, user_type, deleted)
    VALUES ('ADMIN', 'ADMIN', 'ADMIN', 'ADMIN', '01-01-1990', '+70000000000', 'ADMIN', false);

-- “аблица дл€ расписани€ пользователей и сотрудников
CREATE TABLE timetable (
    id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    employee varchar NOT NULL,
    "user" varchar NULL,
    "time" timestamp NULL,
    CONSTRAINT "FK_EMP" FOREIGN KEY (employee) REFERENCES users(id),
    CONSTRAINT "FK_USER" FOREIGN KEY ("user") REFERENCES users(id)
);