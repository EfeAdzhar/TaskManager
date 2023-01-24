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
    version        integer     not null default 1.0,

    constraint fk_taskTemplateId foreign key (taskTemplateId) references task_template (id),
    constraint fk_userId foreign key (userId) references users (id),
    constraint fk_assigneeId foreign key (assigneeId) references users (id)
);