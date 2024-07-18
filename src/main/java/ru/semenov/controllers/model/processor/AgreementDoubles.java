package ru.semenov.controllers.model.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.semenov.controllers.model.CorpSettlInstanceBody;
import ru.semenov.controllers.model.InstanceArrangement;
import ru.semenov.dt.repo.AgreementRepo;


import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Component("agrdoub")
public class AgreementDoubles implements UnaryOperator<CorpSettlInstanceBody> {
    @Autowired
    protected
    AgreementRepo agreementRepo;

    @Override
    public CorpSettlInstanceBody apply(CorpSettlInstanceBody corpSettlMsg) {
//        System.out.println("Step super: " + this.getClass().getSimpleName());

        List<String> agreements = new ArrayList<>();
        for (InstanceArrangement inst : corpSettlMsg.getInstanceArrangement()) {
            List<String> prod = agreementRepo.findByParam(inst.getNumber());
            if (!prod.isEmpty())
                agreements.add("Параметр № Дополнительного соглашения (сделки) Number " + inst.getNumber()
                        + " уже существует для ЭП с ИД  " + prod.get(0) + ".");
        }

        if (!agreements.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST
                    , agreements.stream().collect(Collectors.joining(", "))
            );
        return corpSettlMsg;
    }
}
