CREATE TABLE sensor_analysis (
    sensor_analysis_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sensor_data_id BIGINT NOT NULL,
    max_value DECIMAL(15, 2),
    min_value DECIMAL(15, 2),
    avg_value DECIMAL(15, 2),
    FOREIGN KEY (sensor_data_id) REFERENCES sensor_data(sensor_data_id) ON DELETE CASCADE
);

CREATE INDEX idx_sensor_analysis_sensor_data_id ON sensor_analysis(sensor_data_id);
