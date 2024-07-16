package ru.semenov.controllers.model.processor.instnew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.semenov.entity.Tpp_product_register;
import ru.semenov.entity.Tpp_ref_product_register_type;
import ru.semenov.controllers.model.CorpSettlInstanceBody;
import ru.semenov.controllers.model.State;
import ru.semenov.repo.AccountRepo;
import ru.semenov.repo.Account_poolRepo;
import ru.semenov.repo.Tpp_product_registerRepo;
import ru.semenov.repo.Tpp_ref_product_register_typeRepo;



import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@Component
@Qualifier("CorpSettlInstNew")
@Order(6)
public class ProdRegCreateInstNew implements UnaryOperator<CorpSettlInstanceBody> {
    @Autowired
    Tpp_product_registerRepo registerRepo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    Account_poolRepo accountPoolRepo;
    @Autowired
    Tpp_ref_product_register_typeRepo registerTypeRepo;

    @Override
    public CorpSettlInstanceBody apply(CorpSettlInstanceBody corpSettlInstanceBody) {
//        System.out.println("Step 6: " + this.getClass().getSimpleName());

        List<String> prodRegistries = new ArrayList<>();
        for (String regType : corpSettlInstanceBody.getExistRegisterTypes()) {
            List<Integer> accountPools = accountPoolRepo.findByParam(
                    corpSettlInstanceBody.getBranchCode()
                    , corpSettlInstanceBody.getIsoCurrencyCode()
                    , corpSettlInstanceBody.getMdmCode()
                    , corpSettlInstanceBody.getUrgencyCode()
                    , regType
            );

            Long accId = null;
            String accNum = null;

            if (!accountPools.isEmpty()) {
                List<String[]> res = accountRepo.findByParam(accountPools.get(0));

                if (!res.isEmpty()) {
                    accId = Long.parseLong(res.get(0)[0]);
                    accNum = res.get(0)[1];
                }
            }
            Tpp_ref_product_register_type registerType = registerTypeRepo.findFirstByValue(regType);
            Tpp_product_register register = new Tpp_product_register(
                    Long.valueOf(corpSettlInstanceBody.getInstanceId())
                    , registerType
                    , accId
                    , corpSettlInstanceBody.getIsoCurrencyCode()
                    , State.OPEN.getState()
                    , accNum
            );
            register = registerRepo.save(register);
            prodRegistries.add(register.getId().toString());
        }
        corpSettlInstanceBody.setRegisterId(prodRegistries);

        return corpSettlInstanceBody;
    }
}