-- clean up [spell] 'stat_details'
update spell set stat_details = '';

-- fill damage spell
update spell set stat_details = '{"stats": [{"hitValue":-150.0, "heroStat":"HP", "target":"Enemy"}]}' where name = 'Spell_1';

-- fill heal spell
update spell set stat_details = '{"stats": [{"hitValue":150.0, "heroStat":"HP", "target":"Ally"}]}' where name = 'Spell_2';
