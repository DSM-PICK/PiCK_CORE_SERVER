ALTER TABLE `tbl_user`
    DROP COLUMN `device_token`;

ALTER TABLE `tbl_admin`
    DROP COLUMN `device_token`;

CREATE TABLE IF NOT EXISTS `tbl_user_device_token`
(
    `id`           BINARY(16)   NOT NULL,
    `user_id`      BINARY(16)   NOT NULL,
    `device_token` VARCHAR(255) NOT NULL,
    `os`           VARCHAR(10)  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_device_token` (`device_token`),
    INDEX `idx_user_device_token_user_id` (`user_id`),
    CONSTRAINT `fk_user_device_token_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `tbl_user` (`id`)
            ON UPDATE CASCADE
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `tbl_admin_device_token`
(
    `id`           BINARY(16)   NOT NULL,
    `admin_id`     BINARY(16)   NOT NULL,
    `device_token` VARCHAR(255) NOT NULL,
    `os`           VARCHAR(10)  NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_admin_device_token` (`device_token`),
    INDEX `idx_admin_device_token_admin_id` (`admin_id`),
    CONSTRAINT `fk_admin_device_token_admin`
        FOREIGN KEY (`admin_id`)
            REFERENCES `tbl_admin` (`id`)
            ON UPDATE CASCADE
            ON DELETE CASCADE
);
