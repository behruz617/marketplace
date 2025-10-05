create table product_specifications (
                                        id bigserial primary key,
                                        product_id bigint not null,
                                        spec_id bigint not null,
                                        spec_value varchar(255),

                                        constraint fk_product foreign key (product_id) references products (id) on delete cascade,
                                        constraint fk_spec foreign key (spec_id) references specifications (id) on delete cascade
);
