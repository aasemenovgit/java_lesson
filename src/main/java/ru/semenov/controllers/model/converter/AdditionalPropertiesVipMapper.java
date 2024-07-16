package ru.semenov.controllers.model.converter;

import org.springframework.stereotype.Service;
import ru.semenov.controllers.model.AdditionalPropertiesVip;
import ru.semenov.controllers.model.AdditionalPropertiesVipDto;

@Service
public class AdditionalPropertiesVipMapper implements AdditionalPropertiesVipMapperable {
    public static AdditionalPropertiesVip dtoToModel(AdditionalPropertiesVipDto additionalPropertiesVipDto) {
        AdditionalPropertiesVip additProp = new AdditionalPropertiesVip();
        additProp.setKey(additionalPropertiesVipDto.key());
        additProp.setName(additionalPropertiesVipDto.name());
        additProp.setValue(additionalPropertiesVipDto.value());
        return additProp;
    }
}
