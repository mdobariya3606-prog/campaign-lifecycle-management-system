package com.sale_compaign_project.Sale.Compaign.Management.Scheduler;

import com.sale_compaign_project.Sale.Compaign.Management.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CompaignScheduler {
    @Autowired
    ProductService productService;

    @Scheduled(cron = "0 03 19 * * *")
    public void checkCompaign() {
        LocalDate today = LocalDate.now();
        productService.startCampaign(today);
        productService.endCompaign(today);
    }
}
