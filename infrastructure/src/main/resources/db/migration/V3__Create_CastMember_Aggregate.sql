CREATE TABLE cast_members
(
    id         CHAR(32)  NOT NULL PRIMARY KEY,
    name       varchar(255) NOT NULL,
    type       varchar(32) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
);