create table product_images(
    id BIGSERIAL PRIMARY KEY ,
    file_name varchar(255) not null ,
    fil_url varchar(255)not null ,
    product_id BIGINT not null ,
    constraint fk_product_image_product foreign key (product_id) references products(id) on delete  cascade


)