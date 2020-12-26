package com.example.web.app.controllers;

import com.example.web.app.dao.Storage;
import com.example.web.app.dao.model.Stock;
import com.example.web.app.dao.model.SupplyMaterial;
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
@RequestMapping(value = "/api/stock")
public class StockController {
    private Storage stock;

    public StockController(Storage stock) {this.stock = stock;}

    @ApiOperation(value = "Отправка материалов в цех")
    @RequestMapping(value = "add/toWorkshop", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> sendMaterialInWorkshop (@RequestBody SupplyMaterial sm) {

        Boolean bool = stock.sendSupplyToWorkshop(sm);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(bool, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Добавление материалов на склад")
    @RequestMapping(value = "add/supply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addNewMaterialOnStock (@RequestBody Stock st) {

        Boolean bool = stock.createNewMaterialOnStock(st);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(bool, headers, HttpStatus.OK);
    }


//    @ApiOperation(value = "Получение материалов со склада")
//    @RequestMapping(value = "select/supply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<StringBuilder> getMaterials (@RequestBody ByIdRequest st) {
//
//        StringBuilder sb = stock.getMaterials(st.getId());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity<>(sb, headers, HttpStatus.OK);
//    }

}
