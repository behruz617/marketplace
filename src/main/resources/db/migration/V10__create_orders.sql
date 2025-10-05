create table orders
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT      not null,
    status       VARCHAR(50) not null DEFAULT 'CREATED',
    created_at   TIMESTAMP            DEFAULT current_timestamp,
    update_at    TIMESTAMP            DEFAULT current_timestamp,
    delivered_at TIMESTAMP NULL,
    CONSTRAINT fk_orders_user foreign key (user_id) references users (id) on delete cascade
);

create table order_items
(
    id         BIGSERIAL PRIMARY KEY,
    order_id   BIGINT         not null,
    product_id BIGINT         not null,
    quantity   INT            not null check ( quantity > 0 ),
    price      DECIMAL(10, 2) not null,

    constraint fk_orders_items_order foreign key (order_id) references orders (id) on delete cascade,
    constraint fk_orders_items_product foreign key (product_id) references products (id) on delete cascade
);

create index idx_orders_user on orders (user_id);
create index idx_orders_items on order_items (order_id);
create index idx_orders_items_product on order_items (product_id);