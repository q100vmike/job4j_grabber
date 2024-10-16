CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company values (1, 'MTS');
insert into company values (2, 'Oppo');
insert into company values (3, 'Xiaomi');
insert into company values (4, 'Nike');
insert into company values (5, 'Danon');
insert into company values (6, 'VimBillDann');
insert into company values (7, 'Megafon');
insert into company values (8, 'Beeline');

insert into person values (1, 'Mike', 1);
insert into person values (2, 'Gorge', 1);
insert into person values (3, 'Elena', 1);
insert into person values (4, 'Got', 2);
insert into person values (5, 'Mit', 3);
insert into person values (6, 'Mike', 3);
insert into person values (7, 'Sara', 5);
insert into person values (8, 'Olga', 6);
insert into person values (9, 'Sergey', 7);
insert into person values (10, 'Lesha', 7);
insert into person values (11, 'Alex', 7);
insert into person values (12, 'Svorog', 8);
insert into person values (13, 'Nikol', 8);
insert into person values (14, 'Boris', 8);


select * from company c ;
select * from person p ;


--1. В одном запросе получить
-- имена всех person, которые не состоят в компании с id = 5;
-- название компании для каждого человека.

select p.name, c.name from person p left join company c
                                              on p.company_id = c.id where c.id != 5

--2. Необходимо выбрать название компании с максимальным
--количеством человек + количество человек в этой компании.
--Нужно учесть, что таких компаний может быть несколько.
select count(p.name) cnt, c."name" from person p left join company c
                                                           on p.company_id = c.id group by c.name
having count(p.name) =
       (select max(cnt) from (select count(p.name) cnt from person p group by p.company_id ))
