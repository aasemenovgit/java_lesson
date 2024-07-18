package task_05tests;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.semenov.controllers.model.CorpSettlAccountBody;
import ru.semenov.controllers.model.CorpSettlInstanceBody;
import ru.semenov.controllers.model.InstanceArrangement;
import ru.semenov.controllers.model.processor.acc.NecessaryFieldsAcc;
import ru.semenov.controllers.model.processor.acc.ProdRegDoublesAcc;
import ru.semenov.controllers.model.processor.acc.ProdRegTypeFindAcc;
import ru.semenov.controllers.model.processor.instnew.NecessaryFieldsInstNew;
import ru.semenov.controllers.model.processor.instnew.ProdRegTypeFindInstNew;
import ru.semenov.controllers.model.processor.instupd.NecessaryFieldsInstUpd;
import ru.semenov.controllers.model.processor.instupd.ProdDoublesInstUpd;
import ru.semenov.dt.entity.Tpp_ref_product_register_type;
import ru.semenov.dt.repo.AgreementRepo;
import ru.semenov.dt.repo.Tpp_productRepo;
import ru.semenov.dt.repo.Tpp_product_registerRepo;
import ru.semenov.dt.repo.Tpp_ref_product_register_typeRepo;
import task_05tests.exts.AgreementDoublesTest;
import task_05tests.exts.PacketInjection;
import task_05tests.exts.ProdDoublesInstNewTest;
import task_05tests.exts.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PacketInjection.class})
@SpringBootApplication(scanBasePackages = "ru.semenov")
public class Task5ApplicationTests {
    @Autowired
    private ProdDoublesInstUpd prodDoublesInstUpd;

    @BeforeEach
    void init_data() {
        accountBody.setInstanceId(1);
        accountBody.setRegistryTypeCode("03.012.002_47533_ComSoLd");

        instanceNewBody.setProductType("typeProduct");
        instanceNewBody.setProductCode("03.012.002");
        instanceNewBody.setRegisterType("registrType");
        instanceNewBody.setMdmCode("15");
        instanceNewBody.setContractNumber("dogNum123");
        instanceNewBody.setContractDate(Timestamp.valueOf("2012-12-12 12:12:12"));
        instanceNewBody.setPriority(0L);
        instanceNewBody.setInterestRatePenalty((float) 19.45);
        instanceNewBody.setMinimalBalance((float) 19.45);
        instanceNewBody.setThresholdAmount((float) 19.45);
        instanceNewBody.setAccountingDetails("accDetails");
        instanceNewBody.setRateType("rateType");
        instanceNewBody.setTaxPercentageRate((float) 19.45);
        instanceNewBody.setContractId(123456);
        instanceNewBody.setBranchCode("0022");
        instanceNewBody.setIsoCurrencyCode("800");
        instanceNewBody.setUrgencyCode("00");

        InstanceArrangement instanceArrangementNew = new InstanceArrangement();
        instanceArrangementNew.setNumber("num456");
        instanceArrangementNew.setOpeningDate(Timestamp.valueOf("2012-12-12 12:12:12"));

        instanceNewBody.setInstanceArrangement(List.of(instanceArrangementNew));

        instanceUpdBody.setInstanceId(1);
        instanceUpdBody.setProductType("typeProduct");
        instanceUpdBody.setProductCode("03.012.002");
        instanceUpdBody.setRegisterType("registrType");
        instanceUpdBody.setMdmCode("15");
        instanceUpdBody.setContractNumber("dogNum123");
        instanceUpdBody.setContractDate(Timestamp.valueOf("2012-12-12 12:12:12"));
        instanceUpdBody.setPriority(0L);
        instanceUpdBody.setInterestRatePenalty((float) 19.45);
        instanceUpdBody.setMinimalBalance((float) 19.45);
        instanceUpdBody.setThresholdAmount((float) 19.45);
        instanceUpdBody.setAccountingDetails("accDetails");
        instanceUpdBody.setRateType("rateType");
        instanceUpdBody.setTaxPercentageRate((float) 19.45);
        instanceUpdBody.setContractId(123456);
        instanceUpdBody.setBranchCode("0022");
        instanceUpdBody.setIsoCurrencyCode("800");
        instanceUpdBody.setUrgencyCode("00");

        InstanceArrangement instanceArrangementUpd = new InstanceArrangement();
        instanceArrangementUpd.setNumber("num456");
        instanceArrangementUpd.setOpeningDate(Timestamp.valueOf("2012-12-12 12:12:12"));

        instanceUpdBody.setInstanceArrangement(List.of(instanceArrangementUpd));
    }

    CorpSettlAccountBody accountBody = new CorpSettlAccountBody();
    CorpSettlInstanceBody instanceNewBody = new CorpSettlInstanceBody();
    CorpSettlInstanceBody instanceUpdBody = new CorpSettlInstanceBody();

    @Test
    @DisplayName("Acc 1")
    void testAccountNecessaryFields(@Autowired NecessaryFieldsAcc necessaryFieldsAcc) {
        accountBody.setInstanceId(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> necessaryFieldsAcc.apply(accountBody));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getBody().getStatus());

        accountBody.setInstanceId(1);
        Assertions.assertDoesNotThrow(() -> necessaryFieldsAcc.apply(accountBody));
    }

    @Test
    @DisplayName("Acc 2")
    void testAccountRegisterTypeExist(@Autowired ProdRegTypeFindAcc regTypeFindAcc) {
        Tpp_ref_product_register_typeRepo registerTypeRepo = mock(Tpp_ref_product_register_typeRepo.class);
        when(registerTypeRepo.findFirstByValue("xxx_03.012.002_47533_ComSoLd_xxx")).thenReturn(null);
        when(registerTypeRepo.findFirstByValue("03.012.002_47533_ComSoLd")).thenReturn(new Tpp_ref_product_register_type());

        Assertions.assertDoesNotThrow(() -> regTypeFindAcc.apply(accountBody));

        accountBody.setRegistryTypeCode("xxx_03.012.002_47533_ComSoLd_xxx");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> regTypeFindAcc.apply(accountBody));
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), exception.getBody().getStatus());
    }

    @Test
    @DisplayName("Inst 1. ")
    void testInstNewNecFields(@Autowired NecessaryFieldsInstNew necessaryFieldsInstNew) {
        instanceNewBody.setProductCode(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> necessaryFieldsInstNew.apply(instanceNewBody));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getBody().getStatus());

        instanceNewBody.setProductCode("03.012.002");
        Assertions.assertDoesNotThrow(() -> necessaryFieldsInstNew.apply(instanceNewBody));
    }


    @Test
    @DisplayName("Inst3")
    void testInstNewAgreementDoubles() {
        AgreementRepo agreementRepo = mock(AgreementRepo.class);

        when(agreementRepo.findByParam("num456")).thenReturn(Collections.singletonList("product_id"));
        when(agreementRepo.findByParam("NonExistContract")).thenReturn(Collections.emptyList());

        AgreementDoublesTest agreementDoublesInstNewTest = new AgreementDoublesTest(agreementRepo);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> agreementDoublesInstNewTest.apply(instanceNewBody));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getBody().getStatus());

        instanceNewBody.getInstanceArrangement().get(0).setNumber("NonExistContract");

        Assertions.assertDoesNotThrow(() -> agreementDoublesInstNewTest.apply(instanceNewBody));
    }

    @Test
    @DisplayName("Inst 4")
    void testInstNewExistRegisterType(@Autowired ProdRegTypeFindInstNew prodRegTypeFindInstNew) {
        Tpp_ref_product_register_typeRepo registerTypeRepo = mock(Tpp_ref_product_register_typeRepo.class);

        when(registerTypeRepo.findByParam("03.012.002", "Клиентский")).thenReturn(Collections.singletonList("product_id"));
        when(registerTypeRepo.findByParam("NonExistProductCode", "Клиентский")).thenReturn(Collections.emptyList());


        Assertions.assertDoesNotThrow(() -> prodRegTypeFindInstNew.apply(instanceNewBody));

        instanceNewBody.setProductCode("NonExistProductCode");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> prodRegTypeFindInstNew.apply(instanceNewBody));
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), exception.getBody().getStatus());
    }

    @Test
    @DisplayName("Inst Upd 1")
    void testInstUpdNecFields(@Autowired NecessaryFieldsInstUpd necessaryFieldsInstUpd) {
        instanceUpdBody.setProductCode(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> necessaryFieldsInstUpd.apply(instanceUpdBody));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getBody().getStatus());

        instanceUpdBody.setProductCode("03.012.002");
        Assertions.assertDoesNotThrow(() -> necessaryFieldsInstUpd.apply(instanceUpdBody));
    }


}
