
INSERT INTO equipment (equipment_id, factory_id, name, status, type, installed_at, created_at, modeling)
VALUES (2, 1, 'Assembly Line A2', 'normal', 'Assembly Robot', '2024-01-16 10:00:00', NOW(), NULL);

INSERT INTO equipment (equipment_id, factory_id, name, status, type, installed_at, created_at, modeling)
VALUES (3, 1, 'Assembly Line A3', 'normal', 'Assembly Robot', '2024-01-17 10:00:00', NOW(), NULL);

INSERT INTO equipment (equipment_id, factory_id, name, status, type, installed_at, created_at, modeling)
VALUES (4, 1, 'Assembly Line A4', 'normal', 'Assembly Robot', '2024-01-18 10:00:00', NOW(), NULL);

INSERT INTO equipment (equipment_id, factory_id, name, status, type, installed_at, created_at, modeling)
VALUES (5, 1, 'Assembly Line A5', 'normal', 'Assembly Robot', '2024-01-19 10:00:00', NOW(), NULL);



INSERT INTO workorder (equipment_id, order_id, product_name, target_amount, price, status, customer_name, created_at, updated_at)
VALUES (2, 1, 'order 1', 300, 5000000, FALSE, 'LG', NOW(), NOW());

INSERT INTO workorder (equipment_id, order_id, product_name, target_amount, price, status, customer_name, created_at, updated_at)
VALUES (3, 1, 'order 2', 200, 3000000, FALSE, 'Samsung', NOW(), NOW());

INSERT INTO workorder (equipment_id, order_id, product_name, target_amount, price, status, customer_name, created_at, updated_at)
VALUES (4, 1, 'order 3', 500, 8000000, FALSE, 'LIG', NOW(), NOW());

INSERT INTO workorder (equipment_id, order_id, product_name, target_amount, price, status, customer_name, created_at, updated_at)
VALUES (5, 1, 'order 4', 200, 7000000, FALSE, 'Samsung', NOW(), NOW());
