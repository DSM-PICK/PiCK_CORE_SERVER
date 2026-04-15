DELETE t1 FROM tbl_meal t1
                   INNER JOIN tbl_meal t2
WHERE t1.meal_date = t2.meal_date
  AND t1.meal_type = t2.meal_type
  AND t1.id > t2.id;

ALTER TABLE `tbl_meal`
    MODIFY COLUMN `meal_type` varchar(20) NOT NULL;

ALTER TABLE `tbl_meal`
    ADD UNIQUE KEY `uk_meal_date_type` (`meal_date`, `meal_type`);