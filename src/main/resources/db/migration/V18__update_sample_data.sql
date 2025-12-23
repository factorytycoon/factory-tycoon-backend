UPDATE workorder
SET customer_name = 'LG Display Co.',
    status = FALSE,
    price = '5000000'
WHERE workorder_id = 1;

UPDATE inventory
SET created_at = CURRENT_TIMESTAMP,
    expiration_date = DATE_ADD(CURDATE(), INTERVAL 365 DAY)
WHERE inventory_id = 1;