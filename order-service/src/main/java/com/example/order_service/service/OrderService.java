package com.example.order_service.service;

import com.example.order_service.dto.InventoryResponse;
import com.example.order_service.dto.OrderLineItemsDto;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.model.Orders;
import com.example.order_service.model.OrderLineItems;
import com.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest)
    {
        Orders orders = new Orders(); //to get an order
        orders.setOrderNumber(UUID.randomUUID().toString()); //random generated number

        List<OrderLineItems>orderLineItems= orderRequest.getOrderLineItemsDtosList()
                .stream()
                .map(this::mapToDto)
                .toList();
                orders.setOrderLineItemsList(orderLineItems);
       List<String> skuCodes =orders.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();
    //call the inventory service to check whether product is in stock or not!
     InventoryResponse[] inventoryResponseArray =  webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
     boolean allProductsInStock= Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

     if(allProductsInStock){
         orderRepository.save(orders);
         return "order placed successfuly";
     }
     else throw new IllegalArgumentException("Product is not in stock!");
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }


}
