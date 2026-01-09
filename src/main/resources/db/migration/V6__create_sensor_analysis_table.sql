CREATE TABLE IF NOT EXISTS sensor_analysis (
    sensor_analysis_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sensor_id BIGINT NOT NULL,
    date DATE,
    max_value DECIMAL(15, 2),
    min_value DECIMAL(15, 2),
    avg_value DECIMAL(15, 2),
    FOREIGN KEY (sensor_id) REFERENCES sensor(sensor_id) ON DELETE CASCADE,
    INDEX idx_sensor_analysis_sensor_id (sensor_id),
    INDEX idx_sensor_analysis_sensor_id_date (sensor_id, date)
);
