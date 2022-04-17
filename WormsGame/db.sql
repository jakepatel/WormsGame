CREATE TABLE IF NOT EXISTS user(
    username    varchar(32) primary key,
    password    varbinary(16),
    role        varchar(10),
    high_score  numeric(10)
);
