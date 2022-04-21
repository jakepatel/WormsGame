
   
CREATE TABLE IF NOT EXISTS user (
    username    varchar(32) primary key,
    password    varbinary(16),
    games_won   int(10) default 0,
    games_lost  int(10) default 0
);

insert into user (username, password) values ("Alpha", aes_encrypt("password", 'key'));

insert into user (username, password) values ("Beta", aes_encrypt("password", 'key'));

insert into user (username, password) values ("Charlie", aes_encrypt("password", 'key'));

insert into user (username, password) values ("Echo", aes_encrypt("password", 'key'));

INSERT INTO user values ('testuser1', aes_encrypt('password', 'key'), 30, 20);
INSERT INTO user values ('testuser2', aes_encrypt('password', 'key'), 80, 45);
INSERT INTO user values ('testuser3', aes_encrypt('password', 'key'), 100, 54);
INSERT INTO user values ('testuser4', aes_encrypt('password', 'key'), 15, 12);
INSERT INTO user values ('testuser5', aes_encrypt('password', 'key'), 1, 92);
INSERT INTO user values ('testuser6', aes_encrypt('password', 'key'), 36, 100);

