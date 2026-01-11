CREATE TABLE IF NOT EXISTS sensor (
    sensor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    `key` VARCHAR(255) NOT NULL,
    label VARCHAR(255) NOT NULL,
    unit VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (equipment_id) REFERENCES equipment(equipment_id) ON DELETE CASCADE,
    INDEX idx_sensor_equipment_id (equipment_id)
);
