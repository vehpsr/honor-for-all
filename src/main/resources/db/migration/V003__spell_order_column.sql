-- add 'pos' column to [spell] table
alter table spell add pos tinyint not null default 0;

-- fill pos values
update spell set pos = (case when name = 'Spell_1' then 1 else 2 end);
