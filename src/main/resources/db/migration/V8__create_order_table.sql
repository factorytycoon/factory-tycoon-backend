CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    factory_id BIGINT NOT NULL,
    customer VARCHAR(255) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    due_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (factory_id) REFERENCES factory(factory_id) ON DELETE CASCADE
);

CREATE INDEX idx_order_factory_id ON orders(factory_id);
CREATE INDEX idx_order_customer ON orders(customer);
