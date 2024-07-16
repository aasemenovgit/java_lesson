package ru.semenov.controllers.model;

import java.util.List;

public record DataRecordDto(
        List<AdditionalPropertiesVipDto> data
) {
}
