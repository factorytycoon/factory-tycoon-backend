-- 1. Create new table with correct schema
-- 1. Add new columns
ALTER TABLE sensor_analysis
ADD COLUMN sensor_id BIGINT,
ADD COLUMN date DATE;

-- 2. Migrate data
UPDATE sensor_analysis sa
JOIN sensor_data sd ON sa.sensor_data_id = sd.sensor_data_id
SET sa.sensor_id = sd.sensor_id,
    sa.date = sd.date;

-- 3. Modify columns and Drop old constraints
ALTER TABLE sensor_analysis MODIFY COLUMN sensor_id BIGINT NOT NULL;

ALTER TABLE sensor_analysis DROP FOREIGN KEY sensor_analysis_ibfk_1;
DROP INDEX idx_sensor_analysis_sensor_data_id ON sensor_analysis;
ALTER TABLE sensor_analysis DROP COLUMN sensor_data_id;

-- 4. Add new constraints
ALTER TABLE sensor_analysis ADD CONSTRAINT fk_sensor_analysis_sensor FOREIGN KEY (sensor_id) REFERENCES sensor(sensor_id) ON DELETE CASCADE;
CREATE INDEX idx_sensor_analysis_sensor_id ON sensor_analysis(sensor_id);
CREATE INDEX idx_sensor_analysis_date ON sensor_analysis(date);
