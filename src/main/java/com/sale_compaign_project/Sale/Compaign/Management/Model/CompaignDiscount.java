package com.sale_compaign_project.Sale.Compaign.Management.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "compaign_discount")
public class CompaignDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int discountId;

    @ManyToOne
    @JoinColumn(name = "compaign_id")
    private Compaign compaign;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private double discount;

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public Compaign getCompaign() {
        return compaign;
    }

    public void setCompaign(Compaign compaign) {
        this.compaign = compaign;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
