-- ============================================
-- Sample Data for Factory Tycoon Application
-- ============================================

-- 1. Factory Sample Data (4개)
INSERT INTO factory (name, location, description, phone, factory_code, modeling, operation_start_at) VALUES
('Samsung Electronics Manufacturing Plant', 'Seoul, South Korea', 'Advanced semiconductor and display manufacturing facility', '02-1234-5678', 'FC001', '/models/samsung-plant.3d', '2023-01-15 09:00:00'),
('LG Display Manufacturing Center', 'Incheon, South Korea', 'Large-scale LCD and OLED display production', '032-5678-9012', 'FC002', '/models/lg-center.3d', '2023-03-20 08:00:00'),
('SK Hynix Semiconductor Fab', 'Icheon, South Korea', 'Memory chip manufacturing and testing facility', '031-3456-7890', 'FC003', '/models/skhynix-fab.3d', '2023-02-10 07:30:00'),
('Hyundai Motor Parts Factory', 'Gwangju, South Korea', 'Automotive parts and components manufacturing', '062-9876-5432', 'FC004', '/models/hyundai-parts.3d', '2023-04-05 06:00:00');

-- 2. Equipment Sample Data (16개)
INSERT INTO equipment (factory_id, name, status, type, installed_at, modeling) VALUES
-- Factory 1
(1, 'Assembly Line A1', 'normal', 'Robotic Assembly System', '2023-01-20 10:00:00', '/models/line-a1.3d'),
(1, 'Testing Station B1', 'warning', 'Automated Test Equipment', '2023-02-05 14:30:00', '/models/station-b1.3d'),
(1, 'Packaging Unit C1', 'normal', 'Automated Packaging Machine', '2023-01-25 11:00:00', '/models/unit-c1.3d'),
(1, 'Quality Control D1', 'error', 'Vision Inspection System', '2023-03-01 09:15:00', '/models/qc-d1.3d'),
-- Factory 2
(2, 'Display Production Line EP1', 'normal', 'LCD Display Manufacturing', '2023-03-25 08:00:00', '/models/ep1.3d'),
(2, 'OLED Coating Station FP2', 'normal', 'OLED Coating Equipment', '2023-04-10 10:30:00', '/models/fp2.3d'),
(2, 'Lamination Unit GP3', 'warning', 'Display Lamination Machine', '2023-04-15 15:00:00', '/models/gp3.3d'),
(2, 'Inspection Line HP4', 'normal', 'Display Quality Inspection', '2023-04-20 11:45:00', '/models/hp4.3d'),
-- Factory 3
(3, 'Wafer Processing IP1', 'normal', 'Wafer Fabrication Equipment', '2023-02-15 08:00:00', '/models/ip1.3d'),
(3, 'Etching Machine JP2', 'warning', 'Plasma Etching System', '2023-02-20 10:30:00', '/models/jp2.3d'),
(3, 'Implantation Unit KP3', 'normal', 'Ion Implantation Equipment', '2023-03-05 09:00:00', '/models/kp3.3d'),
(3, 'Metrology Station LP4', 'normal', 'Precision Measurement System', '2023-03-10 14:00:00', '/models/lp4.3d'),
-- Factory 4
(4, 'Stamping Press MP1', 'normal', 'High-Power Stamping Machine', '2023-04-01 07:00:00', '/models/mp1.3d'),
(4, 'Welding Robot NP2', 'normal', 'Automated Welding System', '2023-04-08 09:30:00', '/models/np2.3d'),
(4, 'Painting Booth OP3', 'warning', 'Automated Painting Line', '2023-04-12 08:15:00', '/models/op3.3d'),
(4, 'Assembly Station PP4', 'normal', 'Final Assembly Line', '2023-04-18 10:00:00', '/models/pp4.3d');

-- 3. Sensor Sample Data (4개)
INSERT INTO sensor (equipment_id, name, type) VALUES
(1, 'Temperature Sensor TS001', 'temperature'),
(2, 'Vibration Sensor VS001', 'vibration'),
(3, 'Pressure Sensor PS001', 'pressure'),
(4, 'Current Sensor CS001', 'current');

-- 4. Sensor Data Sample Data (4개)
INSERT INTO sensor_data (sensor_id, path, date) VALUES
(1, '/factory/line-a1/temperature', CURDATE()),
(2, '/factory/station-b1/vibration', CURDATE()),
(3, '/factory/unit-c1/pressure', CURDATE()),
(4, '/factory/qc-d1/current', CURDATE());

-- 5. Sensor Analysis Sample Data (4개)
INSERT INTO sensor_analysis (sensor_data_id, max_value, min_value, avg_value) VALUES
(1, 85.50, 72.30, 78.90),
(2, 12.80, 8.40, 10.60),
(3, 98.50, 95.20, 96.80),
(4, 45.30, 42.10, 43.70);

-- 6. Alarm Sample Data (4개)
INSERT INTO alarm (sensor_id, level, message, status) VALUES
(1, 'warning', 'Temperature approaching upper limit (>80°C)', FALSE),
(2, 'critical', 'Vibration level exceeds safety threshold', TRUE),
(3, 'warning', 'Pressure fluctuation detected', FALSE),
(4, 'critical', 'Current consumption abnormal', FALSE);

-- 7. Orders Sample Data (4개)
INSERT INTO orders (factory_id, customer, product_name, quantity, due_date) VALUES
(1, 'Apple Inc.', 'Semiconductor Chips Type-X', 50000, DATE_ADD(CURDATE(), INTERVAL 30 DAY)),
(2, 'Samsung Display', 'OLED Display Panels 55-inch', 10000, DATE_ADD(CURDATE(), INTERVAL 45 DAY)),
(3, 'Intel Corporation', 'Memory Chips DDR5 Series', 80000, DATE_ADD(CURDATE(), INTERVAL 60 DAY)),
(4, 'Hyundai Motors', 'Engine Control Modules', 25000, DATE_ADD(CURDATE(), INTERVAL 35 DAY));

-- 8. WorkOrder Sample Data (4개)
INSERT INTO workorder (equipment_id, order_id, product_name, target_amount, customer_name, status, price) VALUES
(1, 1, 'Semiconductor Chips Type-X', 10000, 'Apple Inc.', FALSE, '5000000'),
(5, 2, 'OLED Display Panels 55-inch', 2500, 'Samsung Display', FALSE, '8500000'),
(9, 3, 'Memory Chips DDR5 Series', 20000, 'Intel Corporation', FALSE, '12000000'),
(13, 4, 'Engine Control Modules', 6250, 'Hyundai Motors', TRUE, '3500000');

-- 9. Schedule Sample Data (4개)
INSERT INTO schedule (workorder_id, status, date, shift, worker) VALUES
(1, 'scheduled', CURDATE(), 'morning', 'Kim Min-jun'),
(2, 'in-progress', DATE_ADD(CURDATE(), INTERVAL 1 DAY), 'afternoon', 'Park Ji-won'),
(3, 'in-progress', DATE_ADD(CURDATE(), INTERVAL 2 DAY), 'night', 'Lee Sung-ho'),
(4, 'completed', DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'morning', 'Choi Young-soo');

-- 10. Prediction Sample Data (4개)
INSERT INTO prediction (factory_id, type, level, message, selected) VALUES
(1, 'equipment_maintenance', 'critical', 'Equipment A1 requires maintenance within 7 days', TRUE),
(2, 'production_delay', 'warning', 'Production may be delayed due to component shortage', FALSE),
(3, 'equipment_failure', 'critical', 'Wafer processing equipment shows signs of failure', TRUE),
(4, 'quality_issue', 'warning', 'Quality defect rate increasing, review process needed', FALSE);

-- 11. User Sample Data (4개 - 각 팩토리별로)
INSERT INTO `user` (factory_id, name, dob, phone, email, password, role) VALUES
(1, 'Kim Min-jun', '1985-03-15', '010-1234-5678', 'minjun.kim@samsung.com', '$2a$10$dXJ3SW6G7P50eS3sWOeE.e0HjBHYEHXhZABqGfNyJDMFbHLQ9H.h6', 'OWNER'),
(2, 'Park Ji-won', '1990-07-22', '010-2345-6789', 'jiwon.park@lg.com', '$2a$10$dXJ3SW6G7P50eS3sWOeE.e0HjBHYEHXhZABqGfNyJDMFbHLQ9H.h6', 'WORKER'),
(3, 'Lee Sung-ho', '1988-11-30', '010-3456-7890', 'sungho.lee@sk.com', '$2a$10$dXJ3SW6G7P50eS3sWOeE.e0HjBHYEHXhZABqGfNyJDMFbHLQ9H.h6', 'OWNER'),
(4, 'Choi Young-soo', '1992-05-18', '010-4567-8901', 'youngsoo.choi@hyundai.com', '$2a$10$dXJ3SW6G7P50eS3sWOeE.e0HjBHYEHXhZABqGfNyJDMFbHLQ9H.h6', 'WORKER');

-- 12. Inventory Sample Data (4개)
INSERT INTO inventory (factory_id, item_name, quantity, unit, expiration_date) VALUES
(1, 'Silicon Wafers 300mm', 500, 'units', DATE_ADD(CURDATE(), INTERVAL 365 DAY)),
(2, 'Glass Substrates', 800, 'sheets', DATE_ADD(CURDATE(), INTERVAL 180 DAY)),
(3, 'Chemical Reagents', 250, 'liters', DATE_ADD(CURDATE(), INTERVAL 90 DAY)),
(4, 'Steel Plates 10mm', 1200, 'units', DATE_ADD(CURDATE(), INTERVAL 365 DAY));
