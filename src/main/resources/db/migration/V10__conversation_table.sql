create table conversations (
    id  int8 generated always as identity,
    name varchar(255),
    primary key (id));

create table conversation_members (
    member_id int8 not null,
    conversation_id int8 not null,
    primary key (member_id, conversation_id));

alter table if exists conversation_members
    add constraint fk_conversation_id foreign key (member_id) references users;
alter table if exists conversation_members
    add constraint fk_member_id foreign key (conversation_id) references conversations;