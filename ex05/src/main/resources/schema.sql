CREATE SEQUENCE IF NOT EXISTS seq1 START WITH 1;

CREATE TABLE IF NOT EXISTS employee (
   id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('seq1'),
   first_name VARCHAR(255) NOT NULL,
   last_name VARCHAR(255) NOT NULL,
   city VARCHAR(255)
);
