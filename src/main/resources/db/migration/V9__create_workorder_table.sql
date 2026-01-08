CREATE TABLE IF NOT EXISTS workorder (
    workorder_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    target_amount INT NOT NULL,
    customer_name VARCHAR(20),
    status BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'true: done, false: working',
    price VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (equipment_id) REFERENCES equipment(equipment_id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    INDEX idx_workorder_equipment_id (equipment_id),
    INDEX idx_workorder_order_id (order_id)
);
