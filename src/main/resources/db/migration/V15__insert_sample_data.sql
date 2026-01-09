  -- Insert sample data for all tables

  -- Insert Factory (4 rows)
INSERT INTO factory (factory_id, name, location, description, phone, factory_code, operation_start_at, modeling) VALUES
(1, 'Seoul Factory', 'Seoul, South Korea', 'Main production facility', '02-1234-5678', 'FACT-001', '2024-01-01 09:00:00', '/test_factory/ft.glb'),
(2, 'Busan Factory', 'Busan, South Korea', 'Secondary production facility', '051-9876-5432', 'FACT-002', '2024-01-05 08:30:00', '/test_factory/ft.glb'),
(3, 'Incheon Factory', 'Incheon, South Korea', 'Quality control facility', '032-5555-6666', 'FACT-003', '2024-01-10 10:00:00', '/test_factory/ft.glb'),
(4, 'Daegu Factory', 'Daegu, South Korea', 'Assembly facility', '053-1111-2222', 'FACT-004', '2024-01-15 07:00:00', '/test_factory/ft.glb');

  -- Insert Equipment (4 rows per factory) with location/description
INSERT INTO equipment (factory_id, name, status, type, installed_at, modeling, location, description) VALUES
(1, 'ft-pi-001', 'normal', 'CNC', '2023-06-01 10:00:00', '/sample/facility_test.glb', 'Building A - Floor 1 - Section 1', 'High-precision CNC milling machine for metal parts'),
(1, 'ft-pi-002', 'normal', 'Welding', '2023-06-15 11:00:00', '/sample/facility_test.glb', 'Building A - Floor 1 - Section 2', 'Automated welding robot for assembly lines'),
(1, 'ft-pi-003', 'warning', 'Assembly', '2023-07-01 09:00:00', '/sample/facility_test.glb', 'Building A - Floor 2 - Section 1', 'Main assembly line for final product integration'),
(1, 'ft-pi-004', 'normal', 'Inspection', '2023-07-15 08:00:00', '/sample/facility_test.glb', 'Building A - Floor 2 - Section 3', 'Quality inspection and testing equipment'),
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

  -- Insert Sensor (4 rows per equipment)
INSERT INTO sensor (equipment_id, name, type) VALUES
(1, 'Temperature Sensor 1-1', 'Temperature'),
(1, 'Pressure Sensor 1-1', 'Pressure'),
(1, 'Vibration Sensor 1-1', 'Vibration'),
(1, 'Speed Sensor 1-1', 'Speed'),
(2, 'Temperature Sensor 2-1', 'Temperature'),
(2, 'Pressure Sensor 2-1', 'Pressure'),
(2, 'Vibration Sensor 2-1', 'Vibration'),
(2, 'Speed Sensor 2-1', 'Speed'),
(3, 'Temperature Sensor 3-1', 'Temperature'),
(3, 'Pressure Sensor 3-1', 'Pressure'),
(3, 'Vibration Sensor 3-1', 'Vibration'),
(3, 'Speed Sensor 3-1', 'Speed'),
(4, 'Temperature Sensor 4-1', 'Temperature'),
(4, 'Pressure Sensor 4-1', 'Pressure'),
(4, 'Vibration Sensor 4-1', 'Vibration'),
(4, 'Speed Sensor 4-1', 'Speed'),
(5, 'Temperature Sensor 5-1', 'Temperature'),
(5, 'Pressure Sensor 5-1', 'Pressure'),
(5, 'Vibration Sensor 5-1', 'Vibration'),
(5, 'Speed Sensor 5-1', 'Speed'),
(6, 'Temperature Sensor 6-1', 'Temperature'),
(6, 'Pressure Sensor 6-1', 'Pressure'),
(6, 'Vibration Sensor 6-1', 'Vibration'),
(6, 'Speed Sensor 6-1', 'Speed'),
(7, 'Temperature Sensor 7-1', 'Temperature'),
(7, 'Pressure Sensor 7-1', 'Pressure'),
(7, 'Vibration Sensor 7-1', 'Vibration'),
(7, 'Speed Sensor 7-1', 'Speed'),
(8, 'Temperature Sensor 8-1', 'Temperature'),
(8, 'Pressure Sensor 8-1', 'Pressure'),
(8, 'Vibration Sensor 8-1', 'Vibration'),
(8, 'Speed Sensor 8-1', 'Speed'),
(9, 'Temperature Sensor 9-1', 'Temperature'),
(9, 'Pressure Sensor 9-1', 'Pressure'),
(9, 'Vibration Sensor 9-1', 'Vibration'),
(9, 'Speed Sensor 9-1', 'Speed'),
(10, 'Temperature Sensor 10-1', 'Temperature'),
(10, 'Pressure Sensor 10-1', 'Pressure'),
(10, 'Vibration Sensor 10-1', 'Vibration'),
(10, 'Speed Sensor 10-1', 'Speed'),
(11, 'Temperature Sensor 11-1', 'Temperature'),
(11, 'Pressure Sensor 11-1', 'Pressure'),
(11, 'Vibration Sensor 11-1', 'Vibration'),
(11, 'Speed Sensor 11-1', 'Speed'),
(12, 'Temperature Sensor 12-1', 'Temperature'),
(12, 'Pressure Sensor 12-1', 'Pressure'),
(12, 'Vibration Sensor 12-1', 'Vibration'),
(12, 'Speed Sensor 12-1', 'Speed'),
(13, 'Temperature Sensor 13-1', 'Temperature'),
(13, 'Pressure Sensor 13-1', 'Pressure'),
(13, 'Vibration Sensor 13-1', 'Vibration'),
(13, 'Speed Sensor 13-1', 'Speed'),
(14, 'Temperature Sensor 14-1', 'Temperature'),
(14, 'Pressure Sensor 14-1', 'Pressure'),
(14, 'Vibration Sensor 14-1', 'Vibration'),
(14, 'Speed Sensor 14-1', 'Speed'),
(15, 'Temperature Sensor 15-1', 'Temperature'),
(15, 'Pressure Sensor 15-1', 'Pressure'),
(15, 'Vibration Sensor 15-1', 'Vibration'),
(15, 'Speed Sensor 15-1', 'Speed'),
(16, 'Temperature Sensor 16-1', 'Temperature'),
(16, 'Pressure Sensor 16-1', 'Pressure'),
(16, 'Vibration Sensor 16-1', 'Vibration'),
(16, 'Speed Sensor 16-1', 'Speed');

  -- Insert Sensor Data (4 rows per sensor)
INSERT INTO sensor_data (sensor_id, path, date) VALUES
(1, '/data/sensor/1/2024-01-01.csv', '2024-01-01'),
(1, '/data/sensor/1/2024-01-02.csv', '2024-01-02'),
(1, '/data/sensor/1/2024-01-03.csv', '2024-01-03'),
(1, '/data/sensor/1/2024-01-04.csv', '2024-01-04'),
(2, '/data/sensor/2/2024-01-01.csv', '2024-01-01'),
(2, '/data/sensor/2/2024-01-02.csv', '2024-01-02'),
(2, '/data/sensor/2/2024-01-03.csv', '2024-01-03'),
(2, '/data/sensor/2/2024-01-04.csv', '2024-01-04'),
(3, '/data/sensor/3/2024-01-01.csv', '2024-01-01'),
(3, '/data/sensor/3/2024-01-02.csv', '2024-01-02'),
(3, '/data/sensor/3/2024-01-03.csv', '2024-01-03'),
(3, '/data/sensor/3/2024-01-04.csv', '2024-01-04'),
(4, '/data/sensor/4/2024-01-01.csv', '2024-01-01'),
(4, '/data/sensor/4/2024-01-02.csv', '2024-01-02'),
(4, '/data/sensor/4/2024-01-03.csv', '2024-01-03'),
(4, '/data/sensor/4/2024-01-04.csv', '2024-01-04');

  -- Insert Sensor Analysis (4 rows per sensor data)
INSERT INTO sensor_analysis (sensor_data_id, max_value, min_value, avg_value) VALUES
(1, 95.5, 45.3, 70.4),
(2, 98.2, 48.1, 73.2),
(3, 92.1, 42.5, 67.3),
(4, 96.8, 46.9, 71.9),
(5, 125.4, 50.2, 87.8),
(6, 128.9, 52.1, 90.5),
(7, 122.3, 48.7, 85.5),
(8, 127.6, 51.4, 89.5),
(9, 55.4, 20.1, 37.8),
(10, 58.9, 22.5, 40.7),
(11, 52.3, 18.6, 35.5),
(12, 57.6, 21.3, 39.5),
(13, 88.4, 35.2, 61.8),
(14, 91.9, 37.8, 64.9),
(15, 85.3, 33.1, 59.2),
(16, 90.6, 36.4, 63.5);

  -- Insert Alarm (5 rows)
INSERT INTO alarm (equipment_id, monitor_name, trigger_name, sensor_snapshot, status, sensor_dt, created_at) VALUES
(2, 'turning', 'red', '{"device_id": "ft-pi-002", "timestamp": "2026-01-08T06:48:56+00:00", "sensors": [{"type": "rpm", "value": 326.79}, {"type": "nois", "value": 110.09}, {"type": "disp", "value": 0.02}, {"type": "temp", "value": 89.23}, {"type": "humi", "value": 56.82}, {"type": "illu", "value": 651.33}]}', 'OPEN', '2026-01-08 06:48:56', '2026-01-08 06:52:44'),
(1, 'temperature', 'yellow', '{"device_id": "ft-pi-001", "timestamp": "2026-01-08T07:10:30+00:00", "sensors": [{"type": "rpm", "value": 450.49}, {"type": "nois", "value": 117.63}, {"type": "disp", "value": 0.08}, {"type": "temp", "value": 92.33}, {"type": "humi", "value": 58.19}, {"type": "illu", "value": 640.17}]}', 'OPEN', '2026-01-08 07:10:30', '2026-01-08 07:15:22'),
(3, 'vibration', 'red', '{"device_id": "ft-pi-003", "timestamp": "2026-01-08T08:25:15+00:00", "sensors": [{"type": "rpm", "value": 520.15}, {"type": "nois", "value": 125.45}, {"type": "disp", "value": 0.15}, {"type": "temp", "value": 85.67}, {"type": "humi", "value": 62.34}, {"type": "illu", "value": 680.29}]}', 'CLOSE', '2026-01-08 08:25:15', '2026-01-08 08:30:10'),
(2, 'noise', 'yellow', '{"device_id": "ft-pi-002", "timestamp": "2026-01-08T09:15:45+00:00", "sensors": [{"type": "rpm", "value": 380.25}, {"type": "nois", "value": 118.90}, {"type": "disp", "value": 0.05}, {"type": "temp", "value": 87.10}, {"type": "humi", "value": 60.15}, {"type": "illu", "value": 655.80}]}', 'OPEN', '2026-01-08 09:15:45', '2026-01-08 09:20:33'),
(4, 'rpm', 'red', '{"device_id": "ft-pi-004", "timestamp": "2026-01-08T10:05:20+00:00", "sensors": [{"type": "rpm", "value": 580.95}, {"type": "nois", "value": 130.22}, {"type": "disp", "value": 0.12}, {"type": "temp", "value": 95.40}, {"type": "humi", "value": 65.78}, {"type": "illu", "value": 720.45}]}', 'CLOSE', '2026-01-08 10:05:20', '2026-01-08 10:12:15');

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

  -- Insert Prediction (4 rows)
INSERT INTO prediction (factory_id, type, level, message, selected) VALUES
(1, 'maintenance', 'warning', 'CNC Machine 1 requires maintenance in 7 days', FALSE),
(2, 'failure', 'critical', 'Welding Robot 2 failure risk detected', TRUE),
(3, 'efficiency', 'warning', 'Assembly Line 3 efficiency dropped by 15%', FALSE),
(4, 'safety', 'critical', 'Safety inspection required immediately', TRUE);

  -- Insert User (4 rows)
INSERT INTO `user` (factory_id, name, dob, phone, email, password, role, created_at, image) VALUES
  (1,'Kim Min-ho', '1985-05-12','010-1234-5678','sf@sf.com','$2b$10$/0a6E6Zpf3VG4VeMD5F/NOlaH.6wMstvWgLP/DdhyJELeqrpWtMmK','OWNER','2024-01-01 09:00:00',NULL),
  (1,'Lee Ji-won','1990-08-25','010-2345-6789','fs@fs.com','$2b$10$498HEzFnAWeVzlpuuccw6u6qkJCDQCJKAA5x5wpYGgN/d1v.MkRkK','WORKER','2024-01-01 09:00:00',NULL),
  (2,'Park Sung-il','1988-03-18','010-3456-7890','park.sungil@factory.com','$2a$10$slYQmyNdGzin7olVN3/p2OPST9/PgBkqquzi.Ss7KIUgO2t0jKMUm','OWNER','2024-01-01 09:00:00',NULL),
  (3,'Choi Young-soo','1992-11-30','010-4567-8901','choi.youngsoo@factory.com','$2a$10$slYQmyNdGzin7olVN3/p2OPST9/PgBkqquzi.Ss7KIUgO2t0jKMUm','WORKER','2024-01-01 09:00:00',NULL);
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
