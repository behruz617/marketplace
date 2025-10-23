create table cart
(
    id          BiGSERIAL primary key,
    user_id     BIGINT not null,
    total_price DECIMAL(19, 2),
    constraint fk_car_user foreign key (user_id) references users (id) on delete cascade
);

create table cart_items
(
    id         BIGSERIAL primary key,
    cart_id    BIGINT not null,
    product_id BIGINT not null,
    quantity   INT    not null,
    subtotal   decimal(19, 2),
    constraint fk_cart foreign key (cart_id) references cart (id) on delete cascade ,
    constraint fk_cart_product foreign key (product_id) references products(id)
);