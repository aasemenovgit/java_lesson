package task_05tests.exts;


import ru.semenov.controllers.model.processor.instnew.ProdDoublesInstNew;
import ru.semenov.dt.repo.Tpp_productRepo;

public class ProdDoublesInstNewTest extends ProdDoublesInstNew {
    public ProdDoublesInstNewTest(Tpp_productRepo productRepo) {
        super.productRepo = productRepo;
    }
}
