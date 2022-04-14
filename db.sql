DROP TABLE IF EXISTS client cascade;

CREATE TABLE client
(
       client_id            SERIAL NOT NULL PRIMARY KEY,
       client_ip_Address    VARCHAR(50) NOT NULL,
       client_full_name     VARCHAR(50) NULL,
       client_email         VARCHAR(30) NULL,
       client_phone_number  VARCHAR(30) NULL,
       client_password      VARCHAR(50) NULL,
       client_log           VARCHAR(1000000) NULL
);
