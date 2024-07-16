package ru.semenov.controllers.model.service;


import ru.semenov.controllers.model.CorpSettlInstanceBodyDto;
import ru.semenov.controllers.model.ResponceInstMsg;

public interface CorpSettlInstanceServiceable {
    ResponceInstMsg process(CorpSettlInstanceBodyDto instanceMsgIn);
}
