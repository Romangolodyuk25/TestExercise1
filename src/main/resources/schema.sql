DROP TABLE cash CASCADE;

CREATE TABLE IF NOT EXISTS cash (
cash_id INTEGER generated by default as identity primary key,
coordinates varchar(30) not null,
address varchar(512) not null unique
);