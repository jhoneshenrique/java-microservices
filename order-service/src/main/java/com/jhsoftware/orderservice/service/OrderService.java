package com.jhsoftware.orderservice.service;

import com.jhsoftware.orderservice.dto.InventoryResponse;
import com.jhsoftware.orderservice.dto.OrderLineItemsDto;
import com.jhsoftware.orderservice.dto.OrderRequest;
import com.jhsoftware.orderservice.model.Order;
import com.jhsoftware.orderservice.model.OrderLineItems;
import com.jhsoftware.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    //Order Repository Dependency Injection
    private final OrderRepository orderRepository;

    //WebClient dependency injection
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        //Mapping OrderLine to Dto
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .toList();

        //Set the orderlineitems to the order
        order.setOrderLineItemsList(orderLineItems);

        //Get all the skucodes of the products
        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        //Call Inventory service, and place order if product is in stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder
                        .queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class) //retrieve the body of the info.
                .block(); //That tell us is a syncronous call.

        //Check if the items inside the array exists
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            //Saving the order
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Product is not in stock!");
        }


    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
