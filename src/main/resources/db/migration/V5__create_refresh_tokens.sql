create table refresh_tokens(
    id Bigserial primary key,
    user_id BigInt not null references users(id),
    token varchar(255) not null,
    expiry_date timestamp not null,
    revoked boolean default false
)