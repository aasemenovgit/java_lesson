package ru.semenov.controllers.model.processor.instnew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.semenov.dt.entity.Tpp_product;
import ru.semenov.controllers.model.CorpSettlInstanceBody;
import ru.semenov.dt.repo.Tpp_productRepo;


import java.util.function.UnaryOperator;

@Component
@Qualifier("CorpSettlInstNew")
@Order(5)
public class ProdCreateInstNew implements UnaryOperator<CorpSettlInstanceBody> {
    @Autowired
    Tpp_productRepo productRepo;

    @Override
    public CorpSettlInstanceBody apply(CorpSettlInstanceBody corpSettlInstanceBody) {


        Tpp_product product = new Tpp_product(
                corpSettlInstanceBody.getProductType()
                , corpSettlInstanceBody.getContractNumber()
                , corpSettlInstanceBody.getPriority()
                , corpSettlInstanceBody.getInterestRatePenalty()
                , corpSettlInstanceBody.getMinimalBalance()
                , corpSettlInstanceBody.getThresholdAmount()
                , corpSettlInstanceBody.getAccountingDetails()
                , corpSettlInstanceBody.getRateType()
                , corpSettlInstanceBody.getTaxPercentageRate()
        );

        product = productRepo.save(product);
        corpSettlInstanceBody.setInstanceId(product.getId());
        return corpSettlInstanceBody;
    }
}