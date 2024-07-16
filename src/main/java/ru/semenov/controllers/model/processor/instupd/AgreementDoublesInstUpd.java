package ru.semenov.controllers.model.processor.instupd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ru.semenov.controllers.model.CorpSettlInstanceBody;
import ru.semenov.controllers.model.processor.AgreementDoubles;

import java.util.function.UnaryOperator;

@Component
@Qualifier("CorpSettlInstUpd")
@Order(3)
public class AgreementDoublesInstUpd implements UnaryOperator<CorpSettlInstanceBody> {
    @Autowired
    @Qualifier("agrdoub")
    private AgreementDoubles agreementDoubles;

    @Override
    public CorpSettlInstanceBody apply(CorpSettlInstanceBody corpSettlInstanceBody) {
//        System.out.println("Step 3: " + this.getClass().getSimpleName());
        return agreementDoubles.apply(corpSettlInstanceBody);
    }
}
