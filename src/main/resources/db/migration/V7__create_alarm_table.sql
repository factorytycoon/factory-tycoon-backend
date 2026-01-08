CREATE TABLE IF NOT EXISTS alarm (
    alarm_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sensor_id BIGINT NOT NULL,
    level VARCHAR(20) NOT NULL COMMENT 'warning/critical',
    message TEXT,
    status BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'true: solved, false: not solved',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sensor_id) REFERENCES sensor(sensor_id) ON DELETE CASCADE,
    INDEX idx_alarm_sensor_id (sensor_id),
    INDEX idx_alarm_level (level),
    INDEX idx_alarm_status (status)
);
