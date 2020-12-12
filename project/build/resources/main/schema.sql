CREATE TABLE IF NOT EXISTS hw_order (
   id SERIAL PRIMARY KEY,
   order_number VARCHAR(255) NOT NULL);

CREATE TABLE IF NOT EXISTS hw_order_row (
   id SERIAL PRIMARY KEY,
   item_name VARCHAR(255) NOT NULL,
   quantity INTEGER NOT NULL,
   price DOUBLE PRECISION NOT NULL,
   order_id INTEGER,
   CONSTRAINT fk_order
    FOREIGN KEY (order_id)
        REFERENCES hw_order(id)
);