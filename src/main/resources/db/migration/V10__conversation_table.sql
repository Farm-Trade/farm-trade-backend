create table conversations (
    id  int8 generated always as identity,
    name varchar(255),
    primary key (id));