package task_05tests.exts;


import ru.semenov.controllers.model.processor.instupd.ProdDoublesInstUpd;
import ru.semenov.dt.repo.Tpp_productRepo;

public class ProdDoublesInstUpdTest extends ProdDoublesInstUpd {
    public ProdDoublesInstUpdTest(Tpp_productRepo productRepo) {
        super.productRepo = productRepo;
    }
}
