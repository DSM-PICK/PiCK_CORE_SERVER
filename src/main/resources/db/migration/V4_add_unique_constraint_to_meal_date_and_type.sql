ALTER TABLE `tbl_meal`
    ADD UNIQUE KEY `uk_meal_date_type` (`mealDate`, `mealType`);
