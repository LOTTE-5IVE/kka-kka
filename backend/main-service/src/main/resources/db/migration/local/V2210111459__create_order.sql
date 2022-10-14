create table if not exists `order`
(
    id          bigint auto_increment primary key,
    `ordered_at`  datetime(6) null,
    total_price int         null,
    member_id   bigint      null,
    constraint FKbtfnkke0l8kyq7lyhpwjtg5ev
        foreign key (member_id) references member (id)
);



