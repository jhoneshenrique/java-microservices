package com.jhsoftware.invetoryservice.controller;

import com.jhsoftware.invetoryservice.service.InvenventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InvenventoryService invenventoryService;

    //It receives a skucode and check if it is in stock
    @GetMapping("/sku-code")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode){
        return invenventoryService.isInStock(skuCode);
    }
}
