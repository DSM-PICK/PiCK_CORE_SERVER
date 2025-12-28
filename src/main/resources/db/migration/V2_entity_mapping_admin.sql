ALTER TABLE `tbl_weekend_meal_period`
    ADD INDEX `idx_weekend_meal_period_admin_id` (`admin_id`),
    ADD CONSTRAINT `fk_weekend_meal_period_admin`
        FOREIGN KEY (`admin_id`)
            REFERENCES `tbl_admin` (`id`)
            ON UPDATE CASCADE
               ON DELETE CASCADE ;

ALTER TABLE `tbl_notice`
    ADD INDEX `idx_notice_admin_id` (`admin_id`),
    ADD CONSTRAINT `fk_notice_admin`
        FOREIGN KEY (`admin_id`)
            REFERENCES `tbl_admin` (`id`)
            ON UPDATE CASCADE
               ON DELETE CASCADE ;

