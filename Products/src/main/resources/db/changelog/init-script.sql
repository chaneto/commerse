liquibase formatted sql
changeset chan:1

DROP USER IF EXISTS chaneto;
DROP DATABASE IF EXISTS products_db;

CREATE USER chaneto with PASSWORD '12345';
CREATE DATABASE products_db with OWNER chaneto;
GRANT ALL PRIVILEGES ON DATABASE products_db TO chaneto;