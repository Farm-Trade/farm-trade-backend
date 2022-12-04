create table price_update_history (
    id int8 generated always as identity,
    created_at timestamp,
    updated_from numeric(19, 2),
    updated_to numeric(19, 2),
    order_request int8 not null,
    updater int8 not null,
    product int8 not null,
    primary key (id));

alter table if exists price_update_history
    add constraint fk_order_request_id foreign key (order_request) references order_requests;
alter table if exists price_update_history
    add constraint fk_updater_id foreign key (updater) references users;
alter table if exists price_update_history
    add constraint fk_product_id foreign key (product) references products;