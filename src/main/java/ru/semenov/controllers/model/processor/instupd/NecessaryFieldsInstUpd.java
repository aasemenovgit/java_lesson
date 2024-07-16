package ru.semenov.controllers.model.processor.instupd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ru.semenov.controllers.model.CorpSettlInstanceBody;
import ru.semenov.controllers.model.processor.NecessaryFields;

import java.util.function.UnaryOperator;

@Component("necessfieldsInstUpd")
@Qualifier("CorpSettlInstUpd")
@Order(1)
public class NecessaryFieldsInstUpd implements UnaryOperator<CorpSettlInstanceBody> {
    @Autowired
    @Qualifier("necessfields")
    private NecessaryFields necessaryFields;

    @Override
    public CorpSettlInstanceBody apply(CorpSettlInstanceBody corpSettlInstanceBody) {
//        System.out.println("Step 1: " + this.getClass().getSimpleName());
        return (CorpSettlInstanceBody) necessaryFields.apply(corpSettlInstanceBody);
    }
}
