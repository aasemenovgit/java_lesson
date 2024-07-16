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
import ru.semenov.controllers.model.CorpSettlInstanceBodyDto;
import ru.semenov.controllers.model.service.CorpSettlInstanceServiceable;


@RestController
@RequestMapping("/corporate-settlement-instance/")
public class CorporateSettlementInstanceController {
    @Autowired
    CorpSettlInstanceServiceable instanceService;

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> corporateSettlementInstance(@RequestBody CorpSettlInstanceBodyDto instanceMsgIn)
    {

        Object responce = instanceService.process(instanceMsgIn);

        return new ResponseEntity<>(responce, new HttpHeaders(), HttpStatus.CREATED);
    }
}
