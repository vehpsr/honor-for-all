-- mainly to test migrations...
create table users (
    id bigint not null auto_increment,
    login varchar(40) not null,
    code int not null,
    password varchar(80) not null,
    salt varchar(10) not null,
    primary key (id)
)
engine = MyISAM
default character set = utf8
collate = utf8_bin;
