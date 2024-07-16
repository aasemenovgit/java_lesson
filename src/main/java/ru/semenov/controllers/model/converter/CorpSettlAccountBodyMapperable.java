package ru.semenov.controllers.model.converter;


import ru.semenov.controllers.model.CorpSettlAccountBody;
import ru.semenov.controllers.model.CorpSettlAccountBodyDto;

public interface CorpSettlAccountBodyMapperable {
    public CorpSettlAccountBody dtoToModel(CorpSettlAccountBodyDto accountBodyDto);
}
