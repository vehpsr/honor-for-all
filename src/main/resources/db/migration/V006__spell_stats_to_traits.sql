-- rename [spell] 'stat_details' column to 'spell_details'
alter table spell change stat_details spell_details varchar(512) not null default '{}';

-- clean up [spell] 'spell_details'
update spell set spell_details = '{}';

-- fill damage spell
update spell set spell_details = '{"traits": [{"hitValue":-150.0, "heroStat":"HP", "target":"Enemy"}]}' where name = 'Spell_1';

-- fill heal spell
update spell set spell_details = '{"traits": [{"hitValue":150.0, "heroStat":"HP", "target":"Ally"}]}' where name = 'Spell_2';
