CREATE TABLE equipment (
    equipment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    factory_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL COMMENT 'normal / warning / error',
    type VARCHAR(255),
    installed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (factory_id) REFERENCES factory(factory_id) ON DELETE CASCADE
);

CREATE INDEX idx_equipment_factory_id ON equipment(factory_id);
