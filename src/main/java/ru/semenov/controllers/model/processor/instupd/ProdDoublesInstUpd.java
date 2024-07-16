package ru.semenov.controllers.model.processor.instupd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.semenov.controllers.model.CorpSettlInstanceBody;
import ru.semenov.repo.Tpp_productRepo;


import java.util.List;
import java.util.function.UnaryOperator;

@Component
@Qualifier("CorpSettlInstUpd")
@Order(2)
public class ProdDoublesInstUpd implements UnaryOperator<CorpSettlInstanceBody> {
    @Autowired
    protected
    Tpp_productRepo productRepo;

    @Override
    public CorpSettlInstanceBody apply(CorpSettlInstanceBody corpSettlInstanceBody) {
//        System.out.println("Step 2: " + this.getClass().getSimpleName());
        List<String> products = productRepo.findByIdent(corpSettlInstanceBody.getInstanceId());

        if (products.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND
                    , "Экземпляр продукта с параметром instanceId " + corpSettlInstanceBody.getInstanceId() +" не найден.");

        return corpSettlInstanceBody;
    }
}
