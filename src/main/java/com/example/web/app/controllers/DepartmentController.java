package com.example.web.app.controllers;

import com.example.web.app.api.request.ByDepRequest;
import com.example.web.app.api.request.ByIdRequest;
import com.example.web.app.api.request.ByNSPRequest;
import com.example.web.app.dao.DepartmentSalary;
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
@RequestMapping(value = "/api/departmentSalary")
public class DepartmentController {

    private final DepartmentSalary ds;

    public DepartmentController(DepartmentSalary ds) {
        this.ds = ds;
    }

    @ApiOperation(value = "Получение данные о работах сотрудника")
    @RequestMapping(value = "select/whereHumanWork", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringBuilder> selectModelById(@RequestBody ByIdRequest id) {
        StringBuilder sb = ds.getInfoAboutHuman(id.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(sb, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Получение данные о сотруднике по ФИО")
    @RequestMapping(value = "select/ByHumanNSP", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringBuilder> searchByNSP(@RequestBody ByNSPRequest NSP) {
        StringBuilder sb = ds.searchByNSP(NSP);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(sb, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Измненение зарплаты")
    @RequestMapping(value = "change/salary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> changePayAndBounty(@RequestBody ByDepRequest BDR) {
        Boolean sb = ds.changePosAndPay(BDR.getId(), BDR.getNumberofwork(), BDR.getChangedep(), BDR.getChangepos(), BDR.getChangepay()  );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(sb, headers, HttpStatus.OK);
    }
}
