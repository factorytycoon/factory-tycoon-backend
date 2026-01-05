CREATE TABLE schedule (
    schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    workorder_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    date DATE NOT NULL,
    shift VARCHAR(50) NOT NULL,
    worker VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (workorder_id) REFERENCES workorder(workorder_id) ON DELETE CASCADE,
    INDEX idx_schedule_workorder_id (workorder_id),
    INDEX idx_schedule_date (date)
);
