CREATE TABLE supplier (
                          id BIGSERIAL PRIMARY KEY,
                          tax_number VARCHAR(255),
                          name VARCHAR(255),
                          email VARCHAR(255),
                          street VARCHAR(255),
                          building_no VARCHAR(255),
                          district VARCHAR(255),
                          city VARCHAR(255),
                          postal_code VARCHAR(255)
);


CREATE TABLE customer (
                          id BIGSERIAL PRIMARY KEY,
                          id_number VARCHAR(255),
                          first_name VARCHAR(255),
                          last_name VARCHAR(255),
                          street VARCHAR(255),
                          building_no VARCHAR(255),
                          district VARCHAR(255),
                          city VARCHAR(255),
                          postal_code VARCHAR(255)
);


CREATE TABLE invoice_item (
                              id BIGSERIAL PRIMARY KEY,
                              product_name VARCHAR(255),
                              unit_price DOUBLE PRECISION,
                              quantity DOUBLE PRECISION,
                              tax_rate DOUBLE PRECISION
);


CREATE TABLE invoice (
                         uuid UUID PRIMARY KEY,
                         invoice_date DATE,
                         invoice_time TIME,
                         pdf_url VARCHAR(1000),
                         note TEXT,
                         supplier_id BIGINT REFERENCES supplier(id),
                         customer_id BIGINT REFERENCES customer(id),
                         invoice_item_id BIGINT REFERENCES invoice_item(id)
);

