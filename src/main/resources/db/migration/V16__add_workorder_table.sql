ALTER TABLE workorder
  ADD COLUMN customer_name VARCHAR(20) NULL AFTER created_at;

ALTER TABLE workorder
  ADD COLUMN status BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'true: done, false: working' AFTER created_at;

ALTER TABLE workorder
  ADD COLUMN price VARCHAR(20) NULL AFTER created_at;
