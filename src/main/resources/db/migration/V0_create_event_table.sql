create table if not exists event(
    `id` int not null auto_increment,
    `user_id` int not null,
    `file_id` int not null,
    primary key (`id`),
    foreign key (`user_id`) references user(id),
    foreign key (`file_id`) references file(id)
    );