-- Insert Workorder sample data for 30 days
-- order_id: 5, 6, 7, 8, 9, 10
-- equipment_id: 1, 2, 3 (cycling)

INSERT INTO workorder (equipment_id, order_id, product_name, target_amount, customer_name, status, price, created_at) VALUES
-- Day 1: 2025-12-18
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-18 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-18 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-18 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-18 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-18 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-18 17:00:00'),

-- Day 2: 2025-12-19
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-19 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-19 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-19 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-19 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-19 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-19 17:00:00'),

-- Day 3: 2025-12-20
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-20 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-20 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-20 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-20 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-20 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-20 17:00:00'),

-- Day 4: 2025-12-21
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-21 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-21 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-21 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-21 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-21 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-21 17:00:00'),

-- Day 5: 2025-12-22
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-22 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-22 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-22 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-22 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-22 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-22 17:00:00'),

-- Day 6: 2025-12-23
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-23 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-23 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-23 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-23 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-23 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-23 17:00:00'),

-- Day 7: 2025-12-24
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-24 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-24 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-24 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-24 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-24 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-24 17:00:00'),

-- Day 8: 2025-12-25
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-25 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-25 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-25 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-25 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-25 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-25 17:00:00'),

-- Day 9: 2025-12-26
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-26 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-26 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-26 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-26 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-26 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-26 17:00:00'),

-- Day 10: 2025-12-27
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-27 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-27 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-27 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-27 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-27 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-27 17:00:00'),

-- Day 11: 2025-12-28
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-28 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-28 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-28 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-28 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-28 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-28 17:00:00'),

-- Day 12: 2025-12-29
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-29 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-29 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-29 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-29 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-29 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-29 17:00:00'),

-- Day 13: 2025-12-30
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-30 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-30 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-30 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-30 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-30 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-30 17:00:00'),

-- Day 14: 2025-12-31
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2025-12-31 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2025-12-31 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2025-12-31 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2025-12-31 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2025-12-31 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2025-12-31 17:00:00'),

-- Day 15: 2026-01-01
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-01 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-01 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-01 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-01 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-01 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-01 17:00:00'),

-- Day 16: 2026-01-02
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-02 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-02 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-02 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-02 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-02 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-02 17:00:00'),

-- Day 17: 2026-01-03
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-03 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-03 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-03 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-03 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-03 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-03 17:00:00'),

-- Day 18: 2026-01-04
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-04 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-04 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-04 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-04 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-04 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-04 17:00:00'),

-- Day 19: 2026-01-05
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-05 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-05 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-05 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-05 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-05 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-05 17:00:00'),

-- Day 20: 2026-01-06
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-06 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-06 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-06 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-06 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-06 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-06 17:00:00'),

-- Day 21: 2026-01-07
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-07 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-07 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-07 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-07 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-07 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-07 17:00:00'),

-- Day 22: 2026-01-08
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-08 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-08 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-08 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-08 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-08 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-08 17:00:00'),

-- Day 23: 2026-01-09
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-09 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-09 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-09 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-09 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-09 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-09 17:00:00'),

-- Day 24: 2026-01-10
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-10 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-10 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-10 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-10 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-10 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-10 17:00:00'),

-- Day 25: 2026-01-11
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-11 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-11 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-11 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-11 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-11 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-11 17:00:00'),

-- Day 26: 2026-01-12
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-12 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-12 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-12 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-12 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-12 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-12 17:00:00'),

-- Day 27: 2026-01-13
(1, 5, 'Product Order 5', 100, 'Customer A', 1, '50000', '2026-01-13 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 1, '75000', '2026-01-13 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 1, '60000', '2026-01-13 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 1, '55000', '2026-01-13 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 1, '65000', '2026-01-13 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 1, '70000', '2026-01-13 17:00:00'),

-- Day 28: 2026-01-14 (in-progress)
(1, 5, 'Product Order 5', 100, 'Customer A', 0, '50000', '2026-01-14 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 0, '75000', '2026-01-14 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 0, '60000', '2026-01-14 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 0, '55000', '2026-01-14 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 0, '65000', '2026-01-14 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 0, '70000', '2026-01-14 17:00:00'),

-- Day 29: 2026-01-15 (in-progress)
(1, 5, 'Product Order 5', 100, 'Customer A', 0, '50000', '2026-01-15 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 0, '75000', '2026-01-15 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 0, '60000', '2026-01-15 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 0, '55000', '2026-01-15 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 0, '65000', '2026-01-15 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 0, '70000', '2026-01-15 17:00:00'),

-- Day 30: 2026-01-16 (in-progress)
(1, 5, 'Product Order 5', 100, 'Customer A', 0, '50000', '2026-01-16 09:00:00'),
(2, 6, 'Product Order 6', 80, 'Customer B', 0, '75000', '2026-01-16 10:30:00'),
(3, 7, 'Product Order 7', 120, 'Customer C', 0, '60000', '2026-01-16 12:00:00'),
(1, 8, 'Product Order 8', 90, 'Customer D', 0, '55000', '2026-01-16 14:00:00'),
(2, 9, 'Product Order 9', 110, 'Customer E', 0, '65000', '2026-01-16 15:30:00'),
(3, 10, 'Product Order 10', 95, 'Customer F', 0, '70000', '2026-01-16 17:00:00');
