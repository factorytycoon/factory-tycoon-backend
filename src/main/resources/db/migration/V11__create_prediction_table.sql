CREATE TABLE IF NOT EXISTS prediction (
    prediction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    factory_id BIGINT NOT NULL,
    type VARCHAR(100) NOT NULL,
    level VARCHAR(50) NOT NULL COMMENT 'warning/critical',
    message TEXT,
    selected BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (factory_id) REFERENCES factory(factory_id) ON DELETE CASCADE,
    INDEX idx_prediction_factory_id (factory_id)
);
