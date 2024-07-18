package ru.semenov.dt.dto;

import jakarta.validation.constraints.Size;
import ru.semenov.dt.entity.Tpp_ref_product_class;
import ru.semenov.dt.entity.Tpp_ref_account_type;

import java.sql.Timestamp;

public record Tpp_ref_product_register_typeDto(
        Integer internal_id,
        @Size(min = 0, max = 100)
        String value,
        @Size(min = 0, max = 100)
        String register_type_name,
        Tpp_ref_product_class product_class_code,
        Timestamp register_type_start_date,
        Timestamp register_type_end_date,
        Tpp_ref_account_type account_type
) {
}
