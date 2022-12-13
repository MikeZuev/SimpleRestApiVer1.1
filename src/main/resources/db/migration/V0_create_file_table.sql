create table if not exists file(
    `id` int not null auto_increment,
    `name` varchar(1000) not null,
    `path` text,
    primary key(`id`)
    );