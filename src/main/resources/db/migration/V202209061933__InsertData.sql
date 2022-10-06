insert into persons (first_name, last_name)
values ('John', 'Johnson');
insert into notes (person_id, body)
values (LAST_INSERT_ID(), 'Yep. It`s me.');

insert into persons (first_name, last_name)
values ('Dep', 'Depson');

insert into persons (first_name, last_name)
values ('Kevin', 'Kalkin');
SET
@person_id = LAST_INSERT_ID();
insert into notes (person_id, body)
values (@person_id, 'It`s a spider!.');
insert into notes (person_id, body)
values (@person_id, 'Call the police!');

insert into persons (first_name, last_name)
values ('Nency', 'Dish');

insert into persons (first_name, last_name)
values ('Peta', 'Arcistone');
SET
@person_id = LAST_INSERT_ID();
insert into notes (person_id, body)
values (@person_id, 'Where did you see me?');
insert into notes (person_id, body)
values (@person_id, 'Yep. It`s me.');
insert into notes (person_id, body)
values (@person_id, 'I`m an actress');
