create table post (
    id serial primary key,
    name text,
    text text,
    link varchar(100) NOT NULL,
    created timestamp,
    CONSTRAINT link_unique UNIQUE (link)
);