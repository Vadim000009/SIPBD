package com.example.web.app.controllers;

import com.example.web.app.api.request.ChangeTestResultRequest;
import com.example.web.app.dao.Tester;
import com.example.web.app.dao.model.TestReport;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/testers")
public class TesterController {
    private final Tester testerBase;

    public TesterController(Tester testerBase) {
        this.testerBase = testerBase;
    }

    @ApiOperation(value = "Получение данных о модели")
    @RequestMapping(value = "select/model", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TestReport> getModelToTest() {
        TestReport tr = testerBase.getModelToTest();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(tr, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Изменение данных о тестировании")
    @RequestMapping(value = "change/any", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> changeResult (@RequestBody ChangeTestResultRequest ctrr) {
        Boolean bool = testerBase.changeStatus(ctrr.getIs_allowed(), ctrr.getId(), ctrr.getReviem());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(bool, headers, HttpStatus.OK);
    }
}
