create table IF NOT EXISTS credit_card (id bigint not null, account_balance decimal(19,2), account_number integer not null, primary key (id));
create table IF NOT EXISTS hibernate_sequence (next_val bigint);
create table IF NOT EXISTS primary_account (id bigint not null, account_balance decimal(19,2), account_number integer not null, primary key (id));
create table IF NOT EXISTS role (role_id integer not null, name varchar(255), primary key (role_id));
create table IF NOT EXISTS savings_account (id bigint not null, account_balance decimal(19,2), account_number integer not null, primary key (id));
create table IF NOT EXISTS user (user_id bigint not null, email varchar(255), enabled bit not null, first_name varchar(255), last_name varchar(255), password varchar(255), phone varchar(255), username varchar(255), credit_card_id bigint, primary_account_id bigint, savings_account_id bigint, primary key (user_id));
create table IF NOT EXISTS user_role (user_role_id bigint not null, role_id integer, user_id bigint, primary key (user_role_id));

insert into  role(role_id , name) VALUES(0,'ROLE_USER');