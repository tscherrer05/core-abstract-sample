DROP TABLE SHOE IF EXISTS;
CREATE TABLE SHOE (
    id integer identity primary key,
    color varchar(50) not null,
    size integer not null
);