CREATE TABLE IF NOT EXISTS account
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255),
    surname      VARCHAR(255),
    email        VARCHAR(255),
    created_date TIMESTAMP,
    status       VARCHAR(255)
);

INSERT INTO account(name, surname, email, created_date, status)
VALUES ('Никита', 'Злобин', 'nzlo@mail.ru', '2023-04-17T11:51:02+00:00', 'ACTIVE');

INSERT INTO account(name, surname, email, created_date, status)
VALUES ('Анна', 'Успокина', 'ann@mail.ru', '2023-04-17T11:51:02+00:00', 'ACTIVE');

INSERT INTO account(name, surname, email, created_date, status)
VALUES ('Петер', 'Семенов', 'pet@mail.ru', '2023-04-17T11:51:02+00:00', 'DEACTIVATE');