package com.example.web.app.controllers;

import com.example.web.app.api.request.ChangeHumanWorkability;
import com.example.web.app.api.request.ByIdRequest;
import com.example.web.app.dao.Main;
import com.example.web.app.dao.model.Human;
import com.example.web.app.dao.model.Rebuke;
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
@RequestMapping(value = "/api/human")
public class PeopleManagementController {
    private final Main dataBase;

    public PeopleManagementController(Main dataBase) {
        this.dataBase = dataBase;
    }

    @ApiOperation(value = "Получение данных о сотруднике")
    @RequestMapping(value = "select/human", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Human> selectHuman(@RequestBody ByIdRequest id) {
        Human human = dataBase.selectHumanById(id.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(human, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Создание нового сотрудника")
    @RequestMapping(value = "create/human", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> createNewHuman (@RequestBody Human human) {

        Boolean bool = dataBase.createNewHuman(human);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(bool, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Запись замечания сотруднику")
    @RequestMapping(value = "create/rebuke", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> createNewRebuke (@RequestBody Rebuke rebuke) {

        Boolean bool = dataBase.createNewRebukeForHuman(rebuke);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(bool, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Изменить статус сотрудника")
    @RequestMapping(value = "change/workability", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> createHumanWorkability (@RequestBody ChangeHumanWorkability chw) {

        Boolean bool = dataBase.changeWorkability(chw.getId(), chw.getIs_working());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(bool, headers, HttpStatus.OK);
    }
}
