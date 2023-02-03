create type task_type as enum (
    'DEMO',
    'BUG'
    );

create type task_status as enum (
    'NEW',
    'IN_PROGRESS',
    'COMPLETED',
    'FAILED'
    );

create table users
(
    id    integer primary key,
    email text,
    name  varchar(50) not null
);

create table task_template
(
    id          integer primary key,
    name        varchar(50) not null,
    description text,
    taskType    task_type   not null
);

create table tasks
(
    id             integer primary key,
    name           varchar(50) not null,
    description    text,
    status         task_status not null,
    taskTemplateId integer     not null,
    userId         integer,
    assigneeId     integer     not null,
    version        float    not null default 1.0,

    constraint fk_taskTemplateId foreign key (taskTemplateId) references task_template (id),
    constraint fk_userId foreign key (userId) references users (id),
    constraint fk_assigneeId foreign key (assigneeId) references users (id)
);

/*FUNCTIONS*/
/*TASK*/
create function changeTaskStatus(oldStatus task_status, newStatus task_status)
    returns task_status as
$$
begin
    if oldStatus = 'COMPLETED' or oldStatus = 'FAILED' then
        raise exception 'Task status is completed or failed';
    else
        return newStatus;
    END IF;
END;
$$
    language plpgsql volatile;

create function createOrUpdate(oldTask tasks, newTask tasks)
    returns tasks as
$$
begin
    if oldTask.id = newTask.id then
        update oldTask
        set name           = newTask.name,
            description    = newTask.description,
            status         = newTask.status,
            taskTemplateId = newTask.taskTemplateId,
            userId         = newTask.userId,
            assigneeId     = newTask.assigneeId,
            version        = version + 1;
    else
        insert into tasks
        values (newTask.id, newTask.name, newTask.description,
                newTask.status, newTask.taskTemplateId, newTask.userId,
                newTask.assigneeId, newTask.version);
    END IF;
END;
$$
    language plpgsql volatile;

/**ERROR*/
create function getUser(userId integer)
    returns users as
$$
begin
    select *
    from users
    where id = userId;
end;
$$

    language plpgsql volatile;

create function reassignTask(taskId integer, newUserId integer)
    returns integer as
$$
declare
    newUser users = getUser(newUserId);
begin
    if newUser is not null then
        update tasks
        set userId = newUser.id
        where id = taskId;
    end if;
END;
$$
    language plpgsql volatile;