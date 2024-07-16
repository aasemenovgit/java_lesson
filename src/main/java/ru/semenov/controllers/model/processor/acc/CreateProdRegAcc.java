package ru.semenov.controllers.model.processor.acc;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ru.semenov.entity.Tpp_product_register;
import ru.semenov.entity.Tpp_ref_product_register_type;
import ru.semenov.controllers.model.CorpSettlAccountBody;
import ru.semenov.controllers.model.State;
import ru.semenov.repo.Account_poolRepo;
import ru.semenov.repo.AccountRepo;
import ru.semenov.repo.Tpp_product_registerRepo;
import ru.semenov.repo.Tpp_ref_product_register_typeRepo;

import java.util.List;
import java.util.function.UnaryOperator;

@Component
@Qualifier("CorpSettlAcc")
@Order(4)
@RequiredArgsConstructor
public class CreateProdRegAcc implements UnaryOperator<CorpSettlAccountBody> {
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    Account_poolRepo accountPoolRepo;
    @Autowired
    Tpp_product_registerRepo productRegisterRepo;
    @Autowired
    Tpp_ref_product_register_typeRepo registerTypeRepo;

    @Override
    public CorpSettlAccountBody apply(CorpSettlAccountBody corpSettlAccountBody) {
//        System.out.println("Step 4: " + this.getClass().getSimpleName());

        List<Integer> accountPools = accountPoolRepo.findByParam(
                corpSettlAccountBody.getBranchCode()
                , corpSettlAccountBody.getCurrencyCode()
                , corpSettlAccountBody.getMdmCode()
                , corpSettlAccountBody.getPriorityCode()
                , corpSettlAccountBody.getRegistryTypeCode()
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

        Tpp_ref_product_register_type registerType = registerTypeRepo.findFirstByValue(corpSettlAccountBody.getRegistryTypeCode());

        var productRegistr = new Tpp_product_register(
                Long.valueOf(corpSettlAccountBody.getInstanceId())
                , registerType
                , accId
                , corpSettlAccountBody.getCurrencyCode()
                , State.OPEN.getState()
                , accNum
        );

        Tpp_product_register registr = productRegisterRepo.save(productRegistr);
        corpSettlAccountBody.setProductRegister(registr);
        return corpSettlAccountBody;
    }
}
