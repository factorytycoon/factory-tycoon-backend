ALTER TABLE inventory
  ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP AFTER updated_at;

ALTER TABLE inventory
  ADD COLUMN expiration_date DATE NULL AFTER created_at;

