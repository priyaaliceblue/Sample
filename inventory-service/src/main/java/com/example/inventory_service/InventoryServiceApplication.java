package com.example.inventory_service;

import com.example.inventory_service.model.Inventory;
import com.example.inventory_service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository)
	{
		return args -> {
			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("aurelia_dress");
			inventory2.setQuantity(100);

			Inventory inventory3 = new Inventory();
			inventory3.setSkuCode("aurelia_kurtaset");
			inventory3.setQuantity(0);

			inventoryRepository.save(inventory2);
			inventoryRepository.save(inventory3);
		};
	}

}
