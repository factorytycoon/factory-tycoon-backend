-- Fix foreign key constraints to enable cascade delete
-- Drop existing foreign keys
ALTER TABLE schedule DROP FOREIGN KEY schedule_ibfk_1;
ALTER TABLE workorder DROP FOREIGN KEY workorder_ibfk_1;
ALTER TABLE workorder DROP FOREIGN KEY workorder_ibfk_2;
ALTER TABLE prediction DROP FOREIGN KEY prediction_ibfk_1;

-- Re-add foreign keys with ON DELETE CASCADE
ALTER TABLE workorder ADD CONSTRAINT workorder_ibfk_1 
    FOREIGN KEY (equipment_id) REFERENCES equipment(equipment_id) ON DELETE CASCADE;

ALTER TABLE workorder ADD CONSTRAINT workorder_ibfk_2 
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE;

ALTER TABLE schedule ADD CONSTRAINT schedule_ibfk_1 
    FOREIGN KEY (workorder_id) REFERENCES workorder(workorder_id) ON DELETE CASCADE;

ALTER TABLE prediction ADD CONSTRAINT prediction_ibfk_1 
    FOREIGN KEY (factory_id) REFERENCES factory(factory_id) ON DELETE CASCADE;
