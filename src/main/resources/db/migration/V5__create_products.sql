CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price NUMERIC(10,2) NOT NULL,
                          seller_id BIGINT NOT NULL,

                          CONSTRAINT fk_seller
                              FOREIGN KEY (seller_id) REFERENCES users(id)
                                  ON DELETE CASCADE
);
