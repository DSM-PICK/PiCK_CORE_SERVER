ALTER TABLE tbl_classroom
    ADD COLUMN move VARCHAR(20) NOT NULL DEFAULT '';
    AFTER user_name;
