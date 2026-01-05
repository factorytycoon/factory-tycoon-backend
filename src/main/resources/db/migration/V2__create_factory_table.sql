CREATE TABLE factory (
    factory_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    description TEXT,
    phone VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modeling VARCHAR(255) COMMENT '3D Model File Path',
    operation_start_at TIMESTAMP COMMENT '운영 시작 시간 (가동 시간 계산용)',
    factory_code VARCHAR(20) COMMENT '공장 코드',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_factory_created_at (created_at)
);
