CREATE TABLE IF NOT EXISTS inventory (
    inventory_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    factory_id BIGINT NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    location VARCHAR(255),
    unit VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    expiration_date DATE,
    FOREIGN KEY (factory_id) REFERENCES factory(factory_id) ON DELETE CASCADE,
    INDEX idx_inventory_factory_id (factory_id)
);
