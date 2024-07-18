package task_05tests.exts;


import ru.semenov.controllers.model.processor.AgreementDoubles;
import ru.semenov.dt.repo.AgreementRepo;

public class AgreementDoublesTest extends AgreementDoubles {
    public AgreementDoublesTest(AgreementRepo agreementRepo) {
        super.agreementRepo = agreementRepo;
    }
}
