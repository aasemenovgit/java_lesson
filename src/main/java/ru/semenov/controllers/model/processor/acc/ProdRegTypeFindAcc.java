package ru.semenov.controllers.model.processor.acc;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import ru.semenov.dt.entity.Tpp_ref_product_register_type;
import ru.semenov.controllers.model.CorpSettlAccountBody;
import ru.semenov.dt.repo.Tpp_ref_product_register_typeRepo;

import java.util.function.UnaryOperator;

@Component
@Qualifier("CorpSettlAcc")
@Order(3)
@RequiredArgsConstructor
public class ProdRegTypeFindAcc implements UnaryOperator<CorpSettlAccountBody> {
    @Autowired
    Tpp_ref_product_register_typeRepo registerTypeRepo;

    @Override
    public CorpSettlAccountBody apply(CorpSettlAccountBody corpSettlAccountBody) {
      Tpp_ref_product_register_type registerType = registerTypeRepo.findFirstByValue(corpSettlAccountBody.getRegistryTypeCode());
        if (registerType == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND
                    , "Код Продукта " + corpSettlAccountBody.getRegistryTypeCode()
                    + " не найдено в Каталоге продуктов Tpp_ref_product_register_type для данного типа Регистра");

        return corpSettlAccountBody;
    }
}
