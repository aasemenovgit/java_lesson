package ru.semenov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.semenov.controllers.model.CorpSettlAccountBodyDto;
import ru.semenov.controllers.model.service.CorpSettlAccountServiceable;


@RestController
@RequestMapping("/corporate-settlement-account/")
public class CorporateSettlementAccountController {
    @Autowired
    CorpSettlAccountServiceable accountService;

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> corporateSettlementAccount(@RequestBody CorpSettlAccountBodyDto accountMsgIn)
    {

        Object responce = accountService.process(accountMsgIn);

        return new ResponseEntity<>(responce, new HttpHeaders(), HttpStatus.CREATED);
    }
}
