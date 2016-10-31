-- create [hero_class] table
create table hero_class (
    id smallint not null auto_increment,
    name varchar(40) not null,
    description varchar(512) not null,
    primary key (id)
)
engine = MyISAM
default character set = utf8
collate = utf8_bin;

-- create [hero] table
create table hero (
    id bigint not null auto_increment,
    name varchar(40) not null,
    description varchar(512) not null,
    stat_details varchar(256) not null,
    class_id smallint not null,
    imgUri varchar(60) not null,
    primary key (id),
    foreign key (class_id)
    references hero(id)
)
engine = MyISAM
default character set = utf8
collate = utf8_bin;

-- create [spell] table
create table spell (
    id bigint not null auto_increment,
    hero_id bigint not null,
    name varchar(40) not null,
    description varchar(512) not null,
    imgUri varchar(60) not null,
    primary key (id),
    foreign key (hero_id)
    references hero(id)
)
engine = MyISAM
default character set = utf8
collate = utf8_bin;

-- fill hero classes
insert into hero_class (name, description) values
('Warrior', 'Kind of tank'),
('Support', 'Kind of healer'),
('Assassin', 'Damage dealer'),
('Specialist', 'Kind of... nice guy to have');

-- insert dummy hero
insert into hero (name, description, imgUri, class_id, stat_details)
select 'Prima' as name, 'Firts stub hero' as description, '' as imgUri, id as class_id,
'{"armor":100,"damage":125,"spell_power":100.0,"block":10,"crit_damage":1.2,"HP_regen":55,"MP_regen":15,"crit_chance":0.1,"resistance":0,"attack_power":100,"HP":10000.0,"evade":0.1,"MP":550.0}' as stat_details
from hero_class where name = 'Warrior';

-- insert hero spells
set @hero_id = (select id from hero where name ='Prima');
insert into spell (hero_id, name, description, imgUri)
select @hero_id as hero_id, 'Spell_1' as name, 'Awesome damage spell' as description, '' as imgUri;
insert into spell (hero_id, name, description, imgUri)
select @hero_id as hero_id, 'Spell_2' as name, 'Awesome heal spell' as description, '' as imgUri;
