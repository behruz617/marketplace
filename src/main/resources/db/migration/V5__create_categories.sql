create table categories (
                            id bigserial primary key,
                            name varchar(255) not null,
                            parent_id bigint,
                            is_active boolean default true,
                            constraint fk_category_parent foreign key (parent_id) references categories (id)
);
