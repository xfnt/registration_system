-- Удаляем все таблицы
DROP TABLE IF EXISTS timetable;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS usertype;
DROP TABLE IF EXISTS auth;

-- Таблицы для хранения аккаунтов
CREATE TABLE auth (
     login varchar NOT NULL,
     "password" varchar NOT NULL,
     CONSTRAINT auth_pk PRIMARY KEY (login)
);
-- Добавляем Учетку Админа
INSERT INTO auth(login, password) VALUES ('ADMIN','QURNSU4=');

-- Таблица типов пользователя
CREATE TABLE usertype (
     "type" varchar NOT NULL,
     CONSTRAINT usertype_pk PRIMARY KEY (type)
);

INSERT INTO usertype(type) VALUES ('EMPLOYEE');
INSERT INTO usertype(type) VALUES ('USER');

-- Таблица с персональными данными пользователя
CREATE TABLE users (
    id varchar NOT NULL,
    first_name varchar NOT NULL,
    last_name varchar NOT NULL,
    middle_name varchar NULL,
    birth_date timestamp NOT NULL,
    phone_number varchar NOT NULL,
    user_type varchar NOT NULL,
    deleted bool NOT NULL,
    admin bool NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT "FK_ID" FOREIGN KEY (id) REFERENCES auth(login),
    CONSTRAINT "FK_TYPE" FOREIGN KEY (user_type) REFERENCES reg_sys.usertype("type")
);
-- Добовляем Администратора
INSERT INTO users(id, first_name, last_name, middle_name, birth_date, phone_number, user_type, deleted)
    VALUES ('ADMIN', 'ADMIN', 'ADMIN', 'ADMIN', '01-01-1990', '+70000000000', 'EMPLOYEE', false, true);

-- Таблица для учета записей
CREATE TABLE timetable (
    id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    employee varchar NOT NULL,
    "user" varchar NULL,
    "time" timestamp NULL,
    CONSTRAINT "FK_EMP" FOREIGN KEY (employee) REFERENCES users(id),
    CONSTRAINT "FK_USER" FOREIGN KEY ("user") REFERENCES users(id)
);

-- Функция для пагинации
-- CREATE OR REPLACE FUNCTION reg_sys.pagination(
--     PageNumber INTEGER = NULL,
--     PageSize INTEGER = null
-- )
--     RETURNS SETOF reg_sys.users as
-- $BODY$
-- BEGIN
--     return  query
--         select *
--         from reg_sys.users
--         order by id
--         limit PageSize
--             offset ((PageNumber-1)*PageSize);
-- end;
-- $BODY$
--     LANGUAGE plpgsql;
-- ;