package com.microservices.product_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private int id;
    private String name;
    private String description;
    private int price;

}
