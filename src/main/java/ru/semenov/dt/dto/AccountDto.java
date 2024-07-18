package ru.semenov.dt.dto;

import jakarta.validation.constraints.Size;
import ru.semenov.dt.entity.Account_pool;


public record AccountDto(
        Integer id,
        Account_pool account_pool_id,
        @Size(min = 0, max = 25)
        String account_number,
        Boolean bussy
) {
}
