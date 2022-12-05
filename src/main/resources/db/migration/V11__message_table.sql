create table messages (
    id  int8 generated always as identity,
    from_user_id int8 not null,
    conversation_id int8 not null,
    text varchar(2048),
    created_at timestamp,
    primary key (id));

alter table if exists messages
    add constraint fk_from_user_id foreign key (from_user_id) references users;
alter table if exists messages
    add constraint fk_conversation_id foreign key (conversation_id) references conversations;