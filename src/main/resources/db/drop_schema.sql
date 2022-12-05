-- V11
alter table messages drop constraint fk_from_user_id;
alter table messages drop constraint fk_conversation_id;
drop table messages;
-- V10
alter table conversations drop constraint fk_conversation_id;
alter table users drop constraint fk_member_id;
drop table conversations;
-- V8
alter table business_details drop constraint fk_user_id;
alter table users drop constraint fk_business_details_id;
drop table business_details;
-- V7
alter table price_update_history drop constraint fk_order_request_id;
alter table price_update_history drop constraint fk_updater_id;
alter table price_update_history drop constraint fk_product_id;
drop table price_update_history;
-- V6
alter table order_requests drop constraint fk_user_id;
alter table order_requests drop constraint fk_product_name_id;
drop table order_requests;
-- V1
alter table users drop constraint unique_email_constraint;
alter table users drop constraint unique_phone_constraint;
alter table products drop constraint fk_user_id;
drop table users;
alter table products drop constraint fk_product_name_id;
drop table products;
alter table product_names drop constraint unique_product_name_constraint;
drop table product_names;
drop sequence hibernate_sequence;
drop table if exists flyway_schema_history;
