package com.sale_compaign_project.Sale.Compaign.Management.Model;

import com.sale_compaign_project.Sale.Compaign.Management.ENUM.CompaignStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compaign")
public class Compaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int compaignId;

    private LocalDate startDate;
    private LocalDate endDate;
    private String title;

    @Enumerated(EnumType.STRING)
    private CompaignStatus status;

    @OneToMany(mappedBy = "compaign", cascade = CascadeType.ALL)
    private List<CompaignDiscount> compaignDiscounts = new ArrayList<>();

    public int getCompaignId() {
        return compaignId;
    }

    public void setCompaignId(int compaignId) {
        this.compaignId = compaignId;
    }

    public List<CompaignDiscount> getCompaignDiscounts() {
        return compaignDiscounts;
    }

    public void setCompaignDiscounts(List<CompaignDiscount> compaignDiscounts) {
        this.compaignDiscounts = compaignDiscounts;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CompaignStatus getStatus() {
        return status;
    }

    public void setStatus(CompaignStatus status) {
        this.status = status;
    }
}