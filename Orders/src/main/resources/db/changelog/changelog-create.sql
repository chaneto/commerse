--liquibase formatted sql
--changeset chan:1

create table orders(
 id serial PRIMARY KEY,
 product_id INT NOT NULL,
 product VARCHAR(100) NOT NULL,
 quantity DECIMAL(9,2) NOT NULL,
 created_date DATE NOT NULL,
 last_modified_date DATE NOT NULL);