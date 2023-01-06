/*Creating User*/
/*POST*/
insert into users
values (1, 'user-1@email.com', 'user-1');
insert into users
values (2, 'user-2@email.com', 'user-2');

/*Reading user*/
/*GET*/
select *
from users
where id = 1;

/*Updating user*/
/*PUT*/
update users
set email = 'new-user-1@email.com',
    name  = 'new-user-1'
where id = 1
returning users;

/*Deleting user*/
/*DELETE*/
delete
from users
where id = 1;