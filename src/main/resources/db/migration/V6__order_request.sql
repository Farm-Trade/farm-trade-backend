create table order_requests (
    id  bigserial not null,
    auction_end_date date,
    completed boolean not null,
    loading_date date,
    notes varchar(2048),
    quantity numeric(19, 2) check (quantity>=1) not null,
    unit_price numeric(19, 2) check (unit_price>=0) not null,
    unit_price_update numeric(19, 2) check (unit_price_update>=0),
    ultimate_price numeric(19, 2) check (ultimate_price>=0),
    owner int8,
    product_name_id int8,
    primary key (id));
alter table if exists order_requests
    add constraint fk_user_id foreign key (owner) references users;
alter table if exists order_requests
    add constraint fk_product_name_id foreign key (product_name_id) references product_names