  -- Insert sample data for all tables

  -- Insert Factory (4 rows)
INSERT INTO factory (name, location, description, phone, factory_code, operation_start_at, modeling) VALUES
('Seoul Factory', 'Seoul, South Korea', 'Main production facility', '02-1234-5678', 'FACT-001', '2024-01-01 09:00:00', '/test_factory/ft.glb'),
('Busan Factory', 'Busan, South Korea', 'Secondary production facility', '051-9876-5432', 'FACT-002', '2024-01-05 08:30:00', '/test_factory/ft.glb'),
('Incheon Factory', 'Incheon, South Korea', 'Quality control facility', '032-5555-6666', 'FACT-003', '2024-01-10 10:00:00', '/test_factory/ft.glb'),
('Daegu Factory', 'Daegu, South Korea', 'Assembly facility', '053-1111-2222', 'FACT-004', '2024-01-15 07:00:00', '/test_factory/ft.glb');

  -- Insert Equipment (4 rows per factory) with location/description
INSERT INTO equipment (factory_id, name, status, type, installed_at, modeling, location, description) VALUES
(1, 'ft-pi-001', 'normal', '공장', '2023-06-01 10:00:00', '/sample/ft.glb', 'Building A - Floor 1 - Section 1', 'High-precision CNC milling machine for metal parts'),
(1, 'ft-pi-002', 'normal', '선반', '2023-06-15 11:00:00', '/sample/facility_trim.glb', 'Building A - Floor 1 - Section 2', 'Automated welding robot for assembly lines'),
(1, 'ft-pi-003', 'warning', '도색', '2023-07-01 09:00:00', '/sample/facility_heating.glb', 'Building A - Floor 2 - Section 1', 'Main assembly line for final product integration'),
(1, 'ft-pi-004', 'normal', '검수', '2023-07-15 08:00:00', '/sample/facility_moving.glb', 'Building A - Floor 2 - Section 3', 'Quality inspection and testing equipment'),
(2, 'CNC Machine 2', 'normal', 'CNC', '2023-08-01 10:00:00', '/sample/facility_test.glb', 'Building B - Floor 1 - Section 1', 'CNC lathe machine for cylindrical parts'),
(2, 'Welding Robot 2', 'error', 'Welding', '2023-08-15 11:00:00', '/sample/facility_test.glb', 'Building B - Floor 1 - Section 2', 'Heavy-duty welding robot for large components'),
(2, 'Assembly Line 2', 'normal', 'Assembly', '2023-09-01 09:00:00', '/sample/facility_test.glb', 'Building B - Floor 2 - Section 1', 'Secondary assembly line for component integration'),
(2, 'Quality Checker 2', 'normal', 'Inspection', '2023-09-15 08:00:00', '/sample/facility_test.glb', 'Building B - Floor 2 - Section 2', 'Automated inspection system with AI detection'),
(3, 'CNC Machine 3', 'normal', 'CNC', '2023-10-01 10:00:00', '/sample/facility_test.glb', 'Building C - Floor 1 - Section 1', '5-axis CNC machining center for complex parts'),
(3, 'Welding Robot 3', 'normal', 'Welding', '2023-10-15 11:00:00', '/sample/facility_test.glb', 'Building C - Floor 1 - Section 2', 'Precision welding robot for small components'),
(3, 'Assembly Line 3', 'normal', 'Assembly', '2023-11-01 09:00:00', '/sample/facility_test.glb', 'Building C - Floor 2 - Section 1', 'Specialized assembly line for custom orders'),
(3, 'Quality Checker 3', 'warning', 'Inspection', '2023-11-15 08:00:00', '/sample/facility_test.glb', 'Building C - Floor 2 - Section 2', 'Manual inspection station with measurement tools'),
(4, 'CNC Machine 4', 'normal', 'CNC', '2023-12-01 10:00:00', '/sample/facility_test.glb', 'Building D - Floor 1 - Section 1', 'Multi-purpose CNC machine for diverse operations'),
(4, 'Welding Robot 4', 'normal', 'Welding', '2023-12-15 11:00:00', '/sample/facility_test.glb', 'Building D - Floor 1 - Section 2', 'Collaborative welding robot for flexible tasks'),
(4, 'Assembly Line 4', 'normal', 'Assembly', '2024-01-01 09:00:00', '/sample/facility_test.glb', 'Building D - Floor 2 - Section 1', 'Fast-paced assembly line for high-volume production'),
(4, 'Quality Checker 4', 'normal', 'Inspection', '2024-01-15 08:00:00', '/sample/facility_test.glb', 'Building D - Floor 2 - Section 2', 'Final quality control station before shipment');

  -- Insert Sensor
INSERT INTO sensor (equipment_id, `key`, label, unit) VALUES
(1, 'temp', '온도', '°C'),
(1, 'humi', '습도', '%'),
(1, 'illu', '조도', 'lux'),
(2, 'rpm', '회전수', 'rpm'),
(2, 'nois', '소음', 'dB'),
(2, 'disp', '진동', 'mm'),
(2, 'temp', '온도', '°C'),
(2, 'humi', '습도', '%'),
(2, 'illu', '조도', 'lux'),
(3, 'pres', '압력', 'bar'),
(3, 'voc', '가스', 'ppm'),
(3, 'temp', '온도', '°C'),
(3, 'humi', '습도', '%'),
(3, 'illu', '조도', 'lux'),
(4, 'phot', '포토센서', 'bool'),
(4, 'torq', '토크', 'Nm'),
(4, 'weig', '무게', 'g'),
(4, 'temp', '온도', '°C'),
(4, 'humi', '습도', '%'),
(4, 'illu', '조도', 'lux');

  -- Insert Sensor Data (4 rows per sensor)
INSERT INTO sensor_data (sensor_id, date) VALUES
(1, '2024-01-01'),
(1, '2024-01-02'),
(1, '2024-01-03'),
(1, '2024-01-04'),
(2, '2024-01-01'),
(2, '2024-01-02'),
(2, '2024-01-03'),
(2, '2024-01-04'),
(3, '2024-01-01'),
(3, '2024-01-02'),
(3, '2024-01-03'),
(3, '2024-01-04'),
(4, '2024-01-01'),
(4, '2024-01-02'),
(4, '2024-01-03'),
(4, '2024-01-04');

  -- Insert Sensor Analysis (4 rows per sensor data)
INSERT INTO sensor_analysis (sensor_id, date, max_value, min_value, avg_value) VALUES
(1, '2024-01-01', 95.5, 45.3, 70.4),
(1, '2024-01-02', 98.2, 48.1, 73.2),
(1, '2024-01-03', 92.1, 42.5, 67.3),
(1, '2024-01-04', 96.8, 46.9, 71.9),
(2, '2024-01-01', 125.4, 50.2, 87.8),
(2, '2024-01-02', 128.9, 52.1, 90.5),
(2, '2024-01-03', 122.3, 48.7, 85.5),
(2, '2024-01-04', 127.6, 51.4, 89.5),
(3, '2024-01-01', 55.4, 20.1, 37.8),
(3, '2024-01-02', 58.9, 22.5, 40.7),
(3, '2024-01-03', 52.3, 18.6, 35.5),
(3, '2024-01-04', 57.6, 21.3, 39.5),
(4, '2024-01-01', 88.4, 35.2, 61.8),
(4, '2024-01-02', 91.9, 37.8, 64.9),
(4, '2024-01-03', 85.3, 33.1, 59.2),
(4, '2024-01-04', 90.6, 36.4, 63.5);

  -- Insert Alarm (5 rows)
INSERT INTO alarm (equipment_id, description, level, status, sensor_dt, created_at) VALUES
(2, 'Turning issue - high RPM deviation detected', 'red', 'OPEN', '2026-01-08 06:48:56', '2026-01-08 06:52:44'),
(1, 'Temperature warning - above operational threshold', 'yellow', 'OPEN', '2026-01-08 07:10:30', '2026-01-08 07:15:22'),
(3, 'Vibration detected - excessive movement in spindle', 'red', 'CLOSE', '2026-01-08 08:25:15', '2026-01-08 08:30:10'),
(2, 'Noise level exceeds safety limit', 'yellow', 'OPEN', '2026-01-08 09:15:45', '2026-01-08 09:20:33'),
(4, 'RPM instability detected - motor variance high', 'red', 'CLOSE', '2026-01-08 10:05:20', '2026-01-08 10:12:15');

  -- Insert Orders (4 rows)
INSERT INTO orders (factory_id, customer, product_name, quantity, due_date) VALUES
(1, 'ABC Electronics', 'PCB Assembly', 1000, '2024-02-28'),
(1, 'XYZ Manufacturing', 'Motor Component', 500, '2024-03-15'),
(2, 'DEF Industries', 'Metal Fastener', 5000, '2024-02-15'),
(3, 'GHI Corporation', 'Plastic Housing', 2000, '2024-03-30');

  -- Insert Workorder (4 rows)
INSERT INTO workorder (equipment_id, order_id, product_name, target_amount, customer_name, status, price) VALUES
(1, 1, 'PCB Assembly', 250, 'ABC', FALSE, '50000'),
(2, 2, 'Motor Component', 125, 'XYZ', FALSE, '75000'),
(5, 3, 'Metal Fastener', 1250, 'DEF', TRUE, '30000'),
(9, 4, 'Plastic Housing', 500, 'GHI', FALSE, '45000');

  -- Insert Schedule (4 rows)
INSERT INTO schedule (workorder_id, status, date, shift, worker) VALUES
(1, 'pending', '2024-01-10', 'morning', 'Kim, Min-ho'),
(2, 'in_progress', '2024-01-11', 'afternoon', 'Lee, Ji-won'),
(3, 'completed', '2024-01-12', 'night', 'Park, Sung-il'),
(4, 'pending', '2024-01-13', 'morning', 'Choi, Young-soo');

  -- Insert User (4 rows)
INSERT INTO `user` (factory_id, name, dob, phone, email, password, role, created_at, image) VALUES
  (1,'Kim Min-ho', '1985-05-12','010-1234-5678','sf@sf.com','$2b$10$/0a6E6Zpf3VG4VeMD5F/NOlaH.6wMstvWgLP/DdhyJELeqrpWtMmK','OWNER','2024-01-01 09:00:00',NULL),
  (1,'Lee Ji-won','1990-08-25','010-2345-6789','fs@fs.com','$2b$10$498HEzFnAWeVzlpuuccw6u6qkJCDQCJKAA5x5wpYGgN/d1v.MkRkK','WORKER','2024-01-01 09:00:00',NULL),
  (2,'Park Sung-il','1988-03-18','010-3456-7890','park.sungil@factory.com','$2a$10$slYQmyNdGzin7olVN3/p2OPST9/PgBkqquzi.Ss7KIUgO2t0jKMUm','OWNER','2024-01-01 09:00:00',NULL),
  (3,'Choi Young-soo','1992-11-30','010-4567-8901','choi.youngsoo@factory.com','$2a$10$slYQmyNdGzin7olVN3/p2OPST9/PgBkqquzi.Ss7KIUgO2t0jKMUm','WORKER','2024-01-01 09:00:00',NULL);

  -- Insert Prediction (4 rows)
INSERT INTO prediction (user_id, description) VALUES
(1, 'CNC Machine 1 requires maintenance in 7 days due to operational hours exceeding threshold'),
(2, 'Welding Robot 2 shows signs of potential failure - immediate inspection recommended'),
(3, 'Assembly Line 3 efficiency has dropped by 15% - check for bottlenecks'),
(4, 'Safety inspection is required immediately for Quality Checker equipment');
  -- Insert Inventory (4 rows)
INSERT INTO inventory (factory_id, item_name, quantity, location, unit, expiration_date) VALUES
(1, 'Steel Plate', 500, 'Warehouse A', 'pcs', NULL),
(1, 'Rubber Gasket', 1000, 'Warehouse B', 'box', '2025-12-31'),
(2, 'Aluminum Bar', 300, 'Warehouse A', 'pcs', NULL),
(3, 'Plastic Resin', 200, 'Warehouse C', 'kg', '2025-06-30');

-- Insert User Status (4 rows)
INSERT INTO user_status (user_id, date, status) VALUES
(1, '2024-01-08', TRUE),
(2, '2024-01-08', TRUE),
(3, '2024-01-08', FALSE),
(4, '2024-01-08', TRUE);
