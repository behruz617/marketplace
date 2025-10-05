create table products (
                          id bigserial primary key,
                          name varchar(255) not null,
                          price numeric(10,2) not null,
                          color varchar(50),
                          model varchar(100),
                          stock_count int default 0,
                          is_active boolean default true,
                          seller_id bigint not null,
                          category_id bigint,

                          constraint fk_product_seller foreign key (seller_id) references users (id),
                          constraint fk_product_category foreign key (category_id) references categories (id)
);
