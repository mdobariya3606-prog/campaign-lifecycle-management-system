package com.sale_compaign_project.Sale.Compaign.Management.Controller;

import com.sale_compaign_project.Sale.Compaign.Management.DTO.CompaignDTO;
import com.sale_compaign_project.Sale.Compaign.Management.DTO.PaginationDTO;
import com.sale_compaign_project.Sale.Compaign.Management.Model.Compaign;
import com.sale_compaign_project.Sale.Compaign.Management.Model.Product;
import com.sale_compaign_project.Sale.Compaign.Management.Service.ProductService;
import jakarta.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/saveAll")
    public List<Product> saveAll(@RequestBody List<Product> productList) {
        return productService.saveAll(productList);
    }

    @GetMapping("/findAll")
    public List<Product> findAll(ServletRequest servletRequest) {
        return productService.findAll();
    }

    @GetMapping
    public PaginationDTO getByPage(Pageable pageable) {
        return productService.getByPage(pageable);
    }

    @PostMapping("/createCompaign")
    public String createCompaign(@RequestBody CompaignDTO compaignDetails) {
        return productService.createCompaign(compaignDetails);
    }
}