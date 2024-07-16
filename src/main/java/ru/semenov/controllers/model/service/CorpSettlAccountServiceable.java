package ru.semenov.controllers.model.service;


import ru.semenov.controllers.model.CorpSettlAccountBodyDto;
import ru.semenov.controllers.model.ResponceAccMsg;

public interface CorpSettlAccountServiceable {
    ResponceAccMsg process(CorpSettlAccountBodyDto accountMsgIn);
}
