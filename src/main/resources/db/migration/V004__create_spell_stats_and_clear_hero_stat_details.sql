-- add 'stat_details' column to [spell] table
alter table spell add stat_details varchar(512) not null default '[]';

-- fill damage spell
update spell set stat_details = '[{"hitValue":-150.0, "heroStat":"HP", "target":"Enemy"}]' where name = 'Spell_1';

-- fill heal spell
update spell set stat_details = '[{"hitValue":150.0, "heroStat":"HP", "target":"Ally"}]' where name = 'Spell_2';

-- clear [hero]s 'stat_details'. rely on defaults.
update hero set stat_details = '{}';
