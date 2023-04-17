CREATE TABLE account (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          first_name VARCHAR(255),
                          last_name VARCHAR(255),
                          email VARCHAR(255),
                          created_date TIMESTAMP,
                         /* modified_date TIMESTAMP,*/
                          locale VARCHAR(255),
                          status VARCHAR(255)
);

INSERT INTO account(first_name, last_name, email, created_date, locale, status)
VALUES('Nikita', 'Serkov', 'ser@mail.ru', '2023-04-17T11:51:02+00:00', 'RUS', 'ACTIVE');

INSERT INTO account(first_name, last_name, email, created_date, locale, status)
VALUES('Anna', 'Serkova', 'ann@mail.ru', '2023-04-17T11:51:02+00:00', 'FRENCH', 'ACTIVE');

INSERT INTO account(first_name, last_name, email, created_date, locale, status)
VALUES('Peter', 'Ivanov', 'pet@mail.ru', '2023-04-17T11:51:02+00:00', 'ENGLISH', 'DEACTIVATE');