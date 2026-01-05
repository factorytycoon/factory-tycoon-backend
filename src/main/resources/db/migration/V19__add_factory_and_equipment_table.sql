ALTER TABLE factory
  ADD COLUMN modeling VARCHAR(255) NULL COMMENT '3D Model File Path' AFTER created_at;

ALTER TABLE factory
  ADD COLUMN operation_start_at TIMESTAMP NULL COMMENT '운영 시작 시간 (가동 시간 계산용)' AFTER created_at;

ALTER TABLE equipment
  ADD COLUMN modeling VARCHAR(255) NULL COMMENT '3D Model File Path' AFTER created_at;
