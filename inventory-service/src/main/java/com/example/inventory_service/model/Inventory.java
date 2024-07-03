package com.example.inventory_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "t_inventory")
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String skuCode;
    private int quantity;


}
