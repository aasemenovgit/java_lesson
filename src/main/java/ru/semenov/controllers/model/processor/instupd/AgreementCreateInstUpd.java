package ru.semenov.controllers.model.processor.instupd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.semenov.controllers.model.CorpSettlInstanceBody;
import ru.semenov.controllers.model.InstanceArrangement;
import ru.semenov.entity.Agreement;
import ru.semenov.entity.Tpp_product;
import ru.semenov.repo.AgreementRepo;
import ru.semenov.repo.Tpp_productRepo;


import java.util.function.UnaryOperator;

@Component
@Qualifier("CorpSettlInstUpd")
@Order(4)
public class AgreementCreateInstUpd implements UnaryOperator<CorpSettlInstanceBody> {
    @Autowired
    Tpp_productRepo productRepo;

    @Autowired
    AgreementRepo agreementRepo;

    @Override
    public CorpSettlInstanceBody apply(CorpSettlInstanceBody corpSettlInstanceBody) {
//        System.out.println("Step 4: " + this.getClass().getSimpleName());

        for (InstanceArrangement instanceArrangement : corpSettlInstanceBody.getInstanceArrangement()) {

            Tpp_product product = productRepo.findById(corpSettlInstanceBody.getInstanceId()).orElse(null);
            Agreement agreement = new Agreement(
                    product
                    , instanceArrangement.getGeneralAgreementId()
                    , instanceArrangement.getSupplementaryAgreementId()
                    , instanceArrangement.getArrangementType()
                    , instanceArrangement.getShedulerJobId()
                    , instanceArrangement.getNumber()
                    , instanceArrangement.getOpeningDate()
                    , instanceArrangement.getClosingDate()
                    , instanceArrangement.getCancelDate()
                    , instanceArrangement.getValidityDuration()
                    , instanceArrangement.getCancellationReason()
                    , instanceArrangement.getStatus()
                    , instanceArrangement.getInterestCalculationDate()
                    , instanceArrangement.getInterestRate()
                    , instanceArrangement.getCoefficient()
                    , instanceArrangement.getCoefficientAction()
                    , instanceArrangement.getMinimumInterestRate()
                    , instanceArrangement.getMinimumInterestRateCoefficient()
                    , instanceArrangement.getMinimumInterestRateCoefficientAction()
                    , instanceArrangement.getMaximalnterestRate()
                    , instanceArrangement.getMaximalnterestRateCoefficient()
                    , instanceArrangement.getMaximalnterestRateCoefficientAction()
            );

            agreementRepo.save(agreement);
        }

        return corpSettlInstanceBody;
    }
}
