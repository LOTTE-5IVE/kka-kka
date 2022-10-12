create table if not exists product_order
(
    id         bigint auto_increment primary key,
    `count`    int    null,
    price      int    null,
    order_id   bigint null,
    product_id bigint null,
    constraint FK2enlqlbvrcvijyjxhbqfxub72
        foreign key (order_id) references `order` (id),
    constraint FKh73acsd9s5wp6l0e55td6jr1m
        foreign key (product_id) references product (id)
);
