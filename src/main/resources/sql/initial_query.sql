create table if not exists `users`
(
    `id`       bigint auto_increment not null unique,
    `name`     varchar(100)          not null,
    `username` varchar(50)           not null,
    `password` varchar(50)           not null
);
alter table `users`
    add constraint pk__users__id primary key (`id`);
alter table `users`
    add constraint un__users__username unique (`username`);