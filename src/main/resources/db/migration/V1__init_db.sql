drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 1 increment 1;

drop table if exists product_names cascade;
create table product_names (
    id  bigserial not null,
    approved boolean not null,
    create_request_permission int4,
    name varchar(255),
    type int4,
    primary key (id));

alter table if exists product_names
 add constraint unique_product_name_constraint unique (name);


drop table if exists products cascade;
create table products (
    id  bigserial not null,
    img varchar(255),
    quantity numeric(19, 2) check (quantity>=1),
    reserved_quantity numeric(19, 2) not null,
    size numeric(19, 2) check (size>=1),
    owner int8,
    product_name_id int8,
    primary key (id));

alter table if exists products
    add constraint fk_product_name_id foreign key (product_name_id) references product_names;


drop table if exists users cascade;
create table users (
    id int8 not null,
    activation_code varchar(255),
    created_at timestamp,
    email varchar(255),
    full_name varchar(255),
    is_active boolean default false,
    password varchar(255),
    phone varchar(255),
    role varchar(255),
    primary key (id));

alter table if exists products
    add constraint fk_user_id foreign key (owner) references users;
alter table if exists users
    add constraint unique_email_constraint unique (email);
alter table if exists users
    add constraint unique_phone_constraint unique (phone);
