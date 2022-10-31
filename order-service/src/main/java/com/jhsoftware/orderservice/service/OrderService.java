package com.jhsoftware.orderservice.service;

import com.jhsoftware.orderservice.dto.OrderLineItemsDto;
import com.jhsoftware.orderservice.dto.OrderRequest;
import com.jhsoftware.orderservice.model.Order;
import com.jhsoftware.orderservice.model.OrderLineItems;
import com.jhsoftware.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    //Order Repository Dependency Injection
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        //Mapping OrderLine to Dto
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .toList();

        //Set the orderlineitems to the order
        order.setOrderLineItemsList(orderLineItems);

        //Saving the order
        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
