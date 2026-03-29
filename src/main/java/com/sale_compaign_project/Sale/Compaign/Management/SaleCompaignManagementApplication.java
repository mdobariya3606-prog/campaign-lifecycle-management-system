package com.sale_compaign_project.Sale.Compaign.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaleCompaignManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaleCompaignManagementApplication.class, args);
	}

}
