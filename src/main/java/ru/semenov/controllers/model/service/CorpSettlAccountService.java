package ru.semenov.controllers.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.semenov.controllers.model.CorpSettlAccountBody;
import ru.semenov.controllers.model.CorpSettlAccountBodyDto;
import ru.semenov.controllers.model.ResponceAccMsg;
import ru.semenov.controllers.model.converter.CorpSettlAccountBodyMapper;


import java.util.List;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class CorpSettlAccountService implements CorpSettlAccountServiceable {
    @Autowired
    private List<UnaryOperator<CorpSettlAccountBody>> operators;
    private CorpSettlAccountBody accountBody;
    private final CorpSettlAccountBodyMapper accountBodyMapper;

    public ResponceAccMsg process(CorpSettlAccountBodyDto accountMsgIn) {
        System.out.println("ResponceAccMsg"+this.getClass().getName());
        accountBody = accountBodyMapper.dtoToModel(accountMsgIn);
        System.out.println("accountBody"+accountBody.toString());
        operators.stream().forEach(x -> x.apply(accountBody));
        return new ResponceAccMsg(accountBody.getProductRegister().getId());
    }
}
