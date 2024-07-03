package com.example.order_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="t_orderlineitems")
public class OrderLineItems {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String skuCode;
    private int price;
    private int quantity;
}
