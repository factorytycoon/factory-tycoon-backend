-- Idempotent sample data insert for MySQL

-- Equipment: insert only if equipment_id does not exist
INSERT INTO equipment (equipment_id, factory_id, name, status, type, installed_at, created_at, modeling)
SELECT 2, 1, 'Assembly Line A2', 'normal', 'Assembly Robot', '2024-01-16 10:00:00', NOW(), NULL
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM equipment WHERE equipment_id = 2);

INSERT INTO equipment (equipment_id, factory_id, name, status, type, installed_at, created_at, modeling)
SELECT 3, 1, 'Assembly Line A3', 'normal', 'Assembly Robot', '2024-01-17 10:00:00', NOW(), NULL
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM equipment WHERE equipment_id = 3);

INSERT INTO equipment (equipment_id, factory_id, name, status, type, installed_at, created_at, modeling)
SELECT 4, 1, 'Assembly Line A4', 'normal', 'Assembly Robot', '2024-01-18 10:00:00', NOW(), NULL
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM equipment WHERE equipment_id = 4);

INSERT INTO equipment (equipment_id, factory_id, name, status, type, installed_at, created_at, modeling)
SELECT 5, 1, 'Assembly Line A5', 'normal', 'Assembly Robot', '2024-01-19 10:00:00', NOW(), NULL
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM equipment WHERE equipment_id = 5);


-- Workorders: avoid duplicates by checking a natural key
INSERT INTO workorder (equipment_id, order_id, product_name, target_amount, price, status, customer_name, created_at, updated_at)
SELECT 2, 1, 'order 1', 300, '5000000', FALSE, 'LG', NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (
	SELECT 1 FROM workorder 
	WHERE equipment_id = 2 AND order_id = 1 AND product_name = 'order 1'
);

INSERT INTO workorder (equipment_id, order_id, product_name, target_amount, price, status, customer_name, created_at, updated_at)
SELECT 3, 1, 'order 2', 200, '3000000', FALSE, 'Samsung', NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (
	SELECT 1 FROM workorder 
	WHERE equipment_id = 3 AND order_id = 1 AND product_name = 'order 2'
);

INSERT INTO workorder (equipment_id, order_id, product_name, target_amount, price, status, customer_name, created_at, updated_at)
SELECT 4, 1, 'order 3', 500, '8000000', FALSE, 'LIG', NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (
	SELECT 1 FROM workorder 
	WHERE equipment_id = 4 AND order_id = 1 AND product_name = 'order 3'
);

INSERT INTO workorder (equipment_id, order_id, product_name, target_amount, price, status, customer_name, created_at, updated_at)
SELECT 5, 1, 'order 4', 200, '7000000', FALSE, 'Samsung', NOW(), NOW()
FROM DUAL WHERE NOT EXISTS (
	SELECT 1 FROM workorder 
	WHERE equipment_id = 5 AND order_id = 1 AND product_name = 'order 4'
);
