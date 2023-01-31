insert into category(name) values('Fantasia');
insert into category(name) values('Drama');
insert into category(name) values('Comedia');

insert into book(title, author, price, stock, status, create_at, category_id)
values('example1', 'example1', 54.25, 25, 'CREATED', '07-01-23', 1);

insert into book(title, author, price, stock, status, create_at, category_id)
values('example2', 'example2', 21.00, 50, 'CREATED', '07-01-23', 3);

insert into book(title, author, price, stock, status, create_at, category_id)
values('example3', 'example3', 85.45, 25, 'CREATED', '07-01-23', 2);