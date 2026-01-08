CREATE TABLE IF NOT EXISTS equipment (
    equipment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    factory_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL COMMENT 'normal/warning/error/on/off',
    type VARCHAR(255),
    installed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modeling VARCHAR(255) COMMENT '3D Model File Path',
    FOREIGN KEY (factory_id) REFERENCES factory(factory_id) ON DELETE CASCADE,
    INDEX idx_equipment_factory_id (factory_id),
    INDEX idx_equipment_status (status)
);
