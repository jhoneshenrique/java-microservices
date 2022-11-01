package com.jhsoftware.invetoryservice.controller;

import com.jhsoftware.invetoryservice.dto.InventoryResponse;
import com.jhsoftware.invetoryservice.service.InvenventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InvenventoryService invenventoryService;

    //It receives a skucode and check if it is in stock
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return invenventoryService.isInStock(skuCode);
    }
}
