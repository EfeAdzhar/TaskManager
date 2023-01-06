/*Creating Task Template*/
/*POST*/
insert into task_template
values (1, 'task_template_1', 'description-1', 'DEMO');

/*Reading Task Template*/
/*GET*/
select *
from task_template
where id = 1;

/*Updating Task Template*/
/*PUT*/
update task_template
set name        = 'new-task_template-1',
    description = 'new-description',
    taskType    = 'BUG'
where id = 1
returning task_template;

/*Deleting Task Template*/
/*DELETE*/
delete
from task_template
where id = 1;

/*Updating Task Template Type*/
/*PUT*/
update task_template
set taskType = 'BUG'
where id = 1;