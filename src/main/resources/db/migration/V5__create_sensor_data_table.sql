CREATE TABLE IF NOT EXISTS sensor_data (
    sensor_data_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sensor_id BIGINT NOT NULL,
    date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sensor_id) REFERENCES sensor(sensor_id) ON DELETE CASCADE,
    INDEX idx_sensor_data_sensor_id (sensor_id),
    INDEX idx_sensor_data_date (date)
);
