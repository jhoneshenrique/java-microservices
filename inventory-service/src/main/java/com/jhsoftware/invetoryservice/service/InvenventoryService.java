package com.jhsoftware.invetoryservice.service;

import com.jhsoftware.invetoryservice.dto.InventoryResponse;
import com.jhsoftware.invetoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Slf4j
public class InvenventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
//    @SneakyThrows //Just for demo purpouses
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        // Resilience4J testing
//        log.info("Wait started...");
//            Thread.sleep(10000);
//        log.info("Wait ended...");
        //Converts the Inventory list which came from the db into a list of InventoryResponse
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder().skucode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
