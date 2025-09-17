create table users(
    id BIGSERIAL PRIMARY KEY ,
    email varchar(255)unique not null ,
    password varchar(255)not null ,
    created_at timestamp not null default now()
)