CREATE TABLE IF NOT EXISTS hw_order (
   id SERIAL PRIMARY KEY,
   order_number VARCHAR(255) NOT NULL,
   order_rows VARCHAR(255)
);
