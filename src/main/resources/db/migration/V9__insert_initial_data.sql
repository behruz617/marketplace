-- seller əlavə edirik

insert into users(id, email, password, created_at, role)
values (1, 'seller1@mail.com', 'password', now(), 'SELLER');

-- category əlavə edirik
insert into categories(id, name, parent_id, is_active)
values (1, 'Telefonlar', null, true);

-- product əlavə edirik
insert into products(name, price, seller_id, color, model, stock_count, category_id, is_active)
values ('Iphone 15', 2500.00, 1, 'Mavi', '15', 10, 1, true);

-- bir neçə specification əlavə edirik
insert into specifications(name) values
                                     ('Brend'),
                                     ('Məhsul tipi'),
                                     ('Frontal kamera'),
                                     ('Korpus Materialı'),
                                     ('Əməliyyat sistemi');

-- məhsula specification bağlayırıq
insert into product_specifications(product_id, spec_id, spec_value) values
                                                                        (1, 1, 'Apple'),
                                                                        (1, 2, 'Smartfon'),
                                                                        (1, 3, '18 MPX'),
                                                                        (1, 4, 'Şüşə, Paslanmayan polad'),
                                                                        (1, 5, 'iOS 26');
