-- Sample Factory
INSERT INTO factory (name, location, description, phone) 
VALUES ('Samsung Electronics Factory', 'Seoul, South Korea', 'Advanced semiconductor manufacturing facility', '02-1234-5678');

-- Sample Equipment
INSERT INTO equipment (factory_id, name, status, type, installed_at) 
VALUES (1, 'Assembly Line A1', 'normal', 'Assembly Robot', '2024-01-15 10:00:00');

-- Sample Sensor
INSERT INTO sensor (equipment_id, name, type) 
VALUES (1, 'Temperature Sensor', 'temperature');

-- Sample Sensor Data
INSERT INTO sensor_data (sensor_id, path, date) 
VALUES (1, '/factory/line-a1/temperature', CURDATE());

-- Sample Sensor Analysis
INSERT INTO sensor_analysis (sensor_data_id, max_value, min_value, avg_value) 
VALUES (1, 85.5, 72.3, 78.9);

-- Sample Alarm
INSERT INTO alarm (sensor_id, level, message, status) 
VALUES (1, 'warning', 'Temperature approaching upper limit', FALSE);

-- Sample Inventory
INSERT INTO inventory (factory_id, item_name, quantity, unit) 
VALUES (1, 'Steel Plates', 500, 'units');

-- Sample Order
INSERT INTO orders (factory_id, customer, product_name, quantity, due_date) 
VALUES (1, 'LG Display Co.', 'Display Panel Type-X', 100, DATE_ADD(CURDATE(), INTERVAL 30 DAY));

-- Sample WorkOrder
INSERT INTO workorder (equipment_id, order_id, product_name, target_amount) 
VALUES (1, 1, 'Display Panel Type-X', 100);

-- Sample Schedule
INSERT INTO schedule (workorder_id, status, date, shift, worker) 
VALUES (1, 'scheduled', CURDATE(), 'morning', 'Kim Min-jun');

-- Sample Prediction
INSERT INTO prediction (factory_id, type, level, message, selected) 
VALUES (1, 'equipment_maintenance', 'critical', 'Equipment A1 requires maintenance within 7 days', TRUE);
