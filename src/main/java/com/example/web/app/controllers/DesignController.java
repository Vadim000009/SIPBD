package com.example.web.app.controllers;


import com.example.web.app.api.request.ByIdRequest;
import com.example.web.app.api.request.ChangeByIdAndMaket;
import com.example.web.app.dao.Design;
import com.example.web.app.dao.model.ModelHoody;
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
@RequestMapping(value = "/api/design")
public class DesignController {

    private final Design design;

    public DesignController(Design design) {
        this.design = design;
    }

    @ApiOperation(value = "Получение данных о модели")
    @RequestMapping(value = "select/model", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ModelHoody> selectModelById(@RequestBody ByIdRequest id) {
        ModelHoody mh = design.selectModelById(id.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(mh, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Создание новой модели")
    @RequestMapping(value = "create/model", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> createNewModel (@RequestBody ModelHoody mh) {
        String human_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Boolean bool = design.createNewModel(human_id, mh);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(bool, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Изменение макета толстовки")
    @RequestMapping(value = "change/model", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> changeModelMaket (@RequestBody ChangeByIdAndMaket cbiam) {
        Boolean bool = design.changeMaket(cbiam.getId(), cbiam.getUrlMaket());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(bool, headers, HttpStatus.OK);
    }
}
