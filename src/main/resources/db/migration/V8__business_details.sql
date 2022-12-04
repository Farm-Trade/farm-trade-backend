create table business_details (
    id  int8 generated always as identity,
    address varchar(255),
    name varchar(255),
    payment_type varchar(255) default 'CARD',
    user_id int8,
    primary key (id));

alter table if exists users
    add column business_details_id int8;
alter table if exists users
    add constraint fk_business_details_id foreign key (business_details_id) references business_details;
alter table if exists business_details
    add constraint fk_user_id foreign key (user_id) references users;