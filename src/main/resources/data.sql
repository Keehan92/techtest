-- CREATE TABLE countries (
--                          id int,
--                          name varchar(255),
--                          FirstName varchar(255),
--                          Address varchar(255),
--                          City varchar(255)
-- );
-- INSERT INTO countries (id, name) VALUES (1, 'USA');
-- INSERT INTO countries (id, name) VALUES (2, 'France');
-- INSERT INTO countries (id, name) VALUES (3, 'Brazil');
-- INSERT INTO countries (id, name) VALUES (4, 'Italy');
-- INSERT INTO countries (id, name) VALUES (5, 'Canada');
--
-- CREATE TABLE countries (
--                            id int,
--                            name varchar(255),
--                            FirstName varchar(255),
--                            Address varchar(255),
--                            City varchar(255)
-- );

CREATE TABLE users (
                         id int NOT NULL AUTO_INCREMENT,
                         name varchar(255) NOT NULL,
                         fiat_balance DECIMAL(19,8),
                         eth_balance DECIMAL(19,8),
                         btc_balance DECIMAL(19,8),
                         PRIMARY KEY (id)
);
INSERT INTO users (name,fiat_balance, eth_balance, btc_balance) VALUES ('demo', 50000, 0,0);

CREATE TABLE oracle (
                       id int NOT NULL AUTO_INCREMENT,
                       ticker varchar(3) NOT NULL,
                       buy_price DECIMAL(19,8),
                       sell_price DECIMAL(19,8),
                       PRIMARY KEY (id)
);

CREATE TABLE history (
                        id int NOT NULL AUTO_INCREMENT,
                        ticker varchar(3) NOT NULL,
                        type varchar(3) NOT NULL,
                        price DECIMAL(19,8),
                        fiat_amount DECIMAL(19,8),
                        crypto_amount DECIMAL(19,8),
                        transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (id)
);