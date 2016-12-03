-- create [auth_token_type] table
create table auth_token_type (
    id int not null auto_increment,
    name varchar(20) not null,
    description varchar(128) not null,
    primary key (id)
)
engine = MyISAM
default character set = utf8
collate = utf8_bin;

-- create [auth_token] table
create table auth_token (
    id varchar(40) not null,
    type_id int not null,
    user_id bigint not null,
    updated_date bigint not null,
    primary key (id),
    foreign key (user_id)
    references users(id),
    foreign key (type_id)
    references auth_token_type(id)
)
engine = MyISAM
default character set = utf8
collate = utf8_bin;

-- unique index on 'login' and 'code' for [users] table
create unique index uq_login_code on users(login, code);

-- add bearer auth token
insert into auth_token_type(name, description) values ('bearer', 'Bearer authorization token');

-- create guest user to handle anonimous session
insert into users(login, code, password, salt) values ('guest', 0, '', '');
