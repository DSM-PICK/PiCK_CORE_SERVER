-- 중복 attendance 행 제거 (user_id 기준, 가장 작은 id 하나만 유지)
DELETE a1 FROM tbl_attendance a1
INNER JOIN tbl_attendance a2
    ON a1.user_id = a2.user_id AND a1.id > a2.id;

ALTER TABLE tbl_attendance
    ADD CONSTRAINT uk_attendance_user_id UNIQUE (user_id);

-- 중복 status 행 제거 (user_id 기준, 가장 작은 id 하나만 유지)
DELETE s1 FROM tbl_status s1
INNER JOIN tbl_status s2
    ON s1.user_id = s2.user_id AND s1.id > s2.id;

ALTER TABLE tbl_status
    ADD CONSTRAINT uk_status_user_id UNIQUE (user_id);
