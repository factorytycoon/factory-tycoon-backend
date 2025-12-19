CREATE TABLE `user` (
    user_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    factory_id  BIGINT NOT NULL,
    name        VARCHAR(100) NOT NULL,
    dob         DATE NOT NULL,
    phone       VARCHAR(20) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL COMMENT 'BCrypt',
    role        VARCHAR(20) NOT NULL COMMENT 'owner/worker',

    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE INDEX idx_user_role ON `user`(role);
CREATE INDEX idx_user_phone ON `user`(phone);
