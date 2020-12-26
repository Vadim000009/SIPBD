package com.example.web.app.controllers;

import com.example.web.app.api.request.ByDateRequest;
import com.example.web.app.api.request.ByIdRequest;
import com.example.web.app.dao.WorkshopWork;
import com.example.web.app.dao.model.Production;
import com.example.web.app.dao.model.Workshop;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/workshop")
public class WorkshopController {

    private final WorkshopWork wsw;

    public WorkshopController(WorkshopWork wsw) {this.wsw = wsw;}

    @ApiOperation(value = "Получение данные о цехе")
    @RequestMapping(value = "select/workshop", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Workshop> getInfoWorkshop() {
        String human_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Workshop sw = wsw.getInfoAboutWorkshop(human_id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(sw, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Получение данных о поставках")
    @RequestMapping(value = "get/info", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ByDateRequest> getinfoAboutSupply(@RequestBody ByDateRequest workshop_id) {
        ByDateRequest req = wsw.selectByDate(workshop_id.getWorkshop_id());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(req, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Отправить продукцию")
    @RequestMapping(value = "send/production", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> sendProduction (@RequestBody Production production) {

        Boolean bool = wsw.sendProduct(production);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(bool, headers, HttpStatus.OK);
    }
}
