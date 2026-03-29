package com.sale_compaign_project.Sale.Compaign.Management.Repository;

import com.sale_compaign_project.Sale.Compaign.Management.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
}
