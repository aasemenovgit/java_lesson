package ru.semenov.controllers.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.semenov.controllers.model.*;

import ru.semenov.controllers.model.converter.CorpSettlInstanceBodyMapper;
import ru.semenov.repo.AgreementRepo;
import ru.semenov.repo.Tpp_product_registerRepo;


import java.util.List;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class CorpSettlInstanceService implements CorpSettlInstanceServiceable{
    @Autowired
    @Qualifier("CorpSettlInstNew")
    private List<UnaryOperator<CorpSettlInstanceBody>> operatorsNew;

    @Autowired
    @Qualifier("CorpSettlInstUpd")
    private List<UnaryOperator<CorpSettlInstanceBody>> operatorsUpd;
    private CorpSettlInstanceBody instanceBody;
    private final CorpSettlInstanceBodyMapper instanceBodyMapper;
    @Autowired
    Tpp_product_registerRepo registerRepo;

    @Autowired
    AgreementRepo agreementRepo;

    public ResponceInstMsg process(CorpSettlInstanceBodyDto instanceMsgIn) {
//        System.out.println("Instance Service : " + this.getClass().getSimpleName());

        instanceBody = instanceBodyMapper.dtoToModel(instanceMsgIn);

        if (instanceBody.getInstanceId() == null) operatorsNew.stream().forEach(x -> x.apply(instanceBody));
        else operatorsUpd.stream().forEach(x -> x.apply(instanceBody));

        instanceBody.setRegisterId(registerRepo.findAllByParam(instanceBody.getInstanceId()));
        instanceBody.setSupplementaryAgreementId(agreementRepo.findAllByParam(instanceBody.getInstanceId()));
        return new ResponceInstMsg(instanceBody.getInstanceId().toString(), instanceBody.getRegisterId(), instanceBody.getSupplementaryAgreementId());
    }
}
