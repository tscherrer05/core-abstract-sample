DROP TABLE SHOE IF EXISTS;
CREATE TABLE SHOE (
    SHOE_ID integer identity primary key,
    SHOE_NAME varchar(255) not null,
    SHOE_COLOR varchar(50) not null,
    SHOE_SIZE integer not null
);