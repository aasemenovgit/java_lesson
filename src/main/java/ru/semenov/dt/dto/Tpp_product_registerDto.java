package ru.semenov.dt.dto;

import jakarta.validation.constraints.Size;
import ru.semenov.dt.entity.Tpp_ref_product_register_type;


public record Tpp_product_registerDto(
        Integer id,
        Long product_id,
        Tpp_ref_product_register_type type,
        Long account,
        @Size(min = 0, max = 30)
        String currency_code,
        @Size(min = 0, max = 50)
        String state,
        @Size(min = 0, max = 25)
        String account_number
) {
}
