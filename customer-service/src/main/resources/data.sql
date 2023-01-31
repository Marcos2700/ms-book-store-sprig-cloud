insert into region(id, name) values(1, 'Latam');
insert into region(id, name) values(2, 'Norteamerica');
insert into region(id, name) values(3, 'Europa');
insert into region(id, name) values(4, 'Africa');
insert into region(id, name) values(5, 'Asia');
insert into region(id, name) values(6, 'Oceania');
insert into region(id, name) values(7, 'Antartida');

insert into customer(id, number_id, first_name, last_name, email, photo_url, state, region_id)
    values(1, '5698-9987-55884', 'Juan', 'Illo', 'illoJuan@gmail.com', '', 'CREATED', 3);