
   
CREATE TABLE IF NOT EXISTS user (
    username    varchar(32) primary key,
    password    varbinary(16),
    games_won   int(10) default 0,
    games_lost  int(10) default 0
);