ALTER TABLE tbl_attendance
    ADD CONSTRAINT uk_attendance_user_id UNIQUE (user_id);

ALTER TABLE tbl_status
    ADD CONSTRAINT uk_status_user_id UNIQUE (user_id);
