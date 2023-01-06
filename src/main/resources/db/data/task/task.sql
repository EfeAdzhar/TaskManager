/*Creating User*/
/*POST*/
insert into task
values (1, 'task-1', 'task-1-description', 'NEW', 1, 1, 2, 1.0);

/*Reading user*/
/*GET*/
select *
from task
where id = 1;

/*Reading all user*/
/*GET*/
select *
from task;

/*Updating task*/
/*PUT*/
update task
set name           = 'new-task-1',
    description    = 'new-task-1-description',
    status         = 'IN_PROGRESS',
    taskTemplateId = 2,
    userid         = 1,
    assigneeid     = 2,
    version        = 1.1
where id = 1
returning task;

/*Deleting user*/
/*DELETE*/
delete
from task
where id = 1;

/*Change task status*/
/*PUT*/
/**@FIXME: Wanna use if-else condition or create a function that will throw exception if there's an error*/
update task
set status = 'IN_PROGRESS'
where id = 1;

/*Unassign Task*/
/*PUT*/
update task
set userId = null
where id = 1
returning task;

/*Reassign Task*/
/*PUT*/
update task
set userId = 1
where id = 1
returning task;