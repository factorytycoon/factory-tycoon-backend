CREATE TABLE factory_status (
    factory_status_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    factory_id VARCHAR(255),
    total_score100 INT NOT NULL,
    rank VARCHAR(255),
    safety_alert_count INT NOT NULL,
    target_production BIGINT NOT NULL,
    actual_production BIGINT NOT NULL,
    avg_profit DECIMAL(19, 2),
    current_profit DECIMAL(19, 2),
    defect_rate DOUBLE NOT NULL,
    operation_rate DOUBLE NOT NULL,
    maintenance_done BOOLEAN NOT NULL
);