CREATE TABLE IF NOT EXISTS `alarm` (
    `alarm_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `equipment_id` BIGINT NOT NULL,
    `monitor_name` varchar(100) DEFAULT NULL,
    `trigger_name` varchar(100) DEFAULT NULL,
    `sensor_snapshot` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`sensor_snapshot`)),
    `status` varchar(20) DEFAULT 'OPEN',
    `sensor_dt` datetime DEFAULT NULL,
    `created_at` datetime DEFAULT current_timestamp(),
    PRIMARY KEY (`alarm_id`),
    FOREIGN KEY (`equipment_id`) REFERENCES equipment (`equipment_id`) ON DELETE CASCADE,
    KEY `idx_equipment` (`equipment_id`),
    KEY `idx_created_at` (`created_at`)
);