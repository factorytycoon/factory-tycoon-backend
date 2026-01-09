CREATE TABLE IF NOT EXISTS user_status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    status BOOLEAN NOT NULL,
    CONSTRAINT fk_user_status_user FOREIGN KEY (user_id) REFERENCES `user`(user_id)
);
