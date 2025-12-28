ALTER TABLE `tbl_classroom`
    ADD INDEX `idx_classroom_user_id` (`user_id`),
  ADD CONSTRAINT `fk_classroom_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `tbl_user` (`id`)
    ON UPDATE CASCADE
       ON DELETE CASCADE;

ALTER TABLE `tbl_after_school`
    ADD INDEX `idx_after_school_user_id` (`user_id`),
  ADD CONSTRAINT `fk_after_school_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `tbl_user` (`id`)
    ON UPDATE CASCADE
       ON DELETE CASCADE;

ALTER TABLE `tbl_attendance`
    ADD INDEX `idx_attendance_user_id` (`user_id`),
  ADD CONSTRAINT `fk_attendance_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `tbl_user` (`id`)
    ON UPDATE CASCADE
       ON DELETE CASCADE;

ALTER TABLE `tbl_application`
    ADD INDEX `idx_application_user_id` (`user_id`),
  ADD CONSTRAINT `fk_application_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `tbl_user` (`id`)
    ON UPDATE CASCADE
       ON DELETE SET NULL;

ALTER TABLE `tbl_application_story`
    ADD INDEX `idx_application_story_user_id` (`user_id`),
  ADD CONSTRAINT `fk_application_story_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `tbl_user` (`id`)
    ON UPDATE CASCADE
       ON DELETE CASCADE;

ALTER TABLE `tbl_weekend_meal`
    ADD INDEX `idx_weekend_meal_id` (`user_id`),
    ADD CONSTRAINT `fk_weekend_meal`
        FOREIGN KEY (`user_id`)
            REFERENCES `tbl_user` (`id`)
            ON UPDATE CASCADE
               ON DELETE CASCADE ;
