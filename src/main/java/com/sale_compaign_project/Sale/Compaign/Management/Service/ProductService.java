package com.sale_compaign_project.Sale.Compaign.Management.Service;

import com.sale_compaign_project.Sale.Compaign.Management.DTO.CompaignDTO;
import com.sale_compaign_project.Sale.Compaign.Management.DTO.DiscountDTO;
import com.sale_compaign_project.Sale.Compaign.Management.DTO.PaginationDTO;
import com.sale_compaign_project.Sale.Compaign.Management.ENUM.CompaignStatus;
import com.sale_compaign_project.Sale.Compaign.Management.Model.Compaign;
import com.sale_compaign_project.Sale.Compaign.Management.Model.CompaignDiscount;
import com.sale_compaign_project.Sale.Compaign.Management.Model.PriceHistory;
import com.sale_compaign_project.Sale.Compaign.Management.Model.Product;
import com.sale_compaign_project.Sale.Compaign.Management.Repository.CompaignDiscountRepo;
import com.sale_compaign_project.Sale.Compaign.Management.Repository.CompaignRepo;
import com.sale_compaign_project.Sale.Compaign.Management.Repository.PriceHistoryRepo;
import com.sale_compaign_project.Sale.Compaign.Management.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CompaignRepo compaignRepo;
    @Autowired
    private CompaignDiscountRepo compaignDiscountRepo;
    @Autowired
    private PriceHistoryRepo priceHistoryRepo;

    public List<Product> saveAll(List<Product> productList) {
        return productRepo.saveAll(productList);
    }

    public PaginationDTO getByPage(Pageable pageable) {
        Page<Product> result = productRepo.findAll(pageable);
        PaginationDTO paginationDTO = new PaginationDTO();

        paginationDTO.setProductList(result.getContent());
        paginationDTO.setPage(pageable.getPageNumber());
        paginationDTO.setPageSize(pageable.getPageSize());
        paginationDTO.setTotalPages((int) result.getTotalElements());

        return paginationDTO;
    }

    @Transactional
    public String createCompaign(CompaignDTO compaignDTO) {
        Compaign compaign = new Compaign();
        compaign.setTitle(compaignDTO.getTitle());
        compaign.setStartDate(compaignDTO.getStartDate());
        compaign.setEndDate(compaignDTO.getEndDate());
        compaign.setStatus(CompaignStatus.UPCOMING);
        compaignRepo.save(compaign);

        List<Integer> productIds = compaignDTO.getCampaignDiscount()
                .stream()
                .map(discountDTO -> discountDTO.getProductId()) // .map (DiscountDTO::getProductId());
                .toList();

        Map<Integer, Product> productMap = productRepo.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getProductId, product -> product));

        List<CompaignDiscount> discountList = new ArrayList<>();

        for (DiscountDTO discountDTO : compaignDTO.getCampaignDiscount()) {
            Product product = productMap.get(discountDTO.getProductId());

            if (product == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found with: " + discountDTO.getProductId()
                );
            }

            CompaignDiscount compaignDiscount = new CompaignDiscount();
            compaignDiscount.setProduct(product);
            compaignDiscount.setCompaign(compaign);
            compaignDiscount.setDiscount(discountDTO.getDiscount());

            discountList.add(compaignDiscount);
            if (discountList.size() == 50) {
                compaignDiscountRepo.saveAll(discountList);
                discountList.clear();
            }
        }

        if (!discountList.isEmpty()) {
            compaignDiscountRepo.saveAll(discountList);
        }

        return "Compaign Created Successfully";
    }

    @Transactional
    public void applyDiscount(Compaign compaign) {
        for (CompaignDiscount compaignDiscount : compaign.getCompaignDiscounts()) {
            Product product = productRepo.findById(compaignDiscount.getProduct().getProductId())
                    .orElseThrow();

            double oldPrice = product.getCurrentPrice();
            double newPrice = oldPrice * (1 - compaignDiscount.getDiscount() / 100.0);

            recordHistory(product, oldPrice, newPrice);
            product.setCurrentPrice(newPrice);

            product.setDiscount(product.getDiscount() + compaignDiscount.getDiscount());
            productRepo.save(product);
        }
        compaign.setStatus(CompaignStatus.CURRENT);
        compaignRepo.save(compaign);
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Transactional
    public void revertDiscount(Compaign compaign) {
        for (CompaignDiscount compaignDiscount : compaign.getCompaignDiscounts()) {
            Product product = productRepo.findById(compaignDiscount.getProduct().getProductId())
                    .orElseThrow();
            double oldPrice = product.getCurrentPrice();
            double newPrice = oldPrice / (1 - compaignDiscount.getDiscount() / 100.0);

            recordHistory(product, oldPrice, newPrice);

            product.setCurrentPrice(newPrice);
            product.setDiscount(product.getDiscount() - compaignDiscount.getDiscount());

            productRepo.save(product);
        }
        compaign.setStatus(CompaignStatus.PAST);
        compaignRepo.save(compaign);
    }

    private void recordHistory(Product product, double oldPrice, double newPrice) {
        PriceHistory history = new PriceHistory();
        history.setProduct(product);
        history.setOldPrice(oldPrice);
        history.setNewPrice(newPrice);
        history.setChangedAt(LocalDateTime.now());

        priceHistoryRepo.save(history);
    }

    @Transactional
    public void startCampaign(LocalDate today) {
        List<Compaign> compaigns = compaignRepo.findByStartDate(today);
        for (Compaign compaign : compaigns) {
            applyDiscount(compaign);
        }
    }

    @Transactional
    public void endCompaign(LocalDate today) {
        List<Compaign> compaigns = compaignRepo.findByEndDate(today);
        for (Compaign compaign : compaigns) {
            revertDiscount(compaign);
        }
    }
}
