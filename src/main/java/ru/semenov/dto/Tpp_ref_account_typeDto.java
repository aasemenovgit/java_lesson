package ru.semenov.dto;

import jakarta.validation.constraints.Size;

public record Tpp_ref_account_typeDto(
        Integer internal_id,
        @Size(min = 0, max = 100)
        String value
) {
}
