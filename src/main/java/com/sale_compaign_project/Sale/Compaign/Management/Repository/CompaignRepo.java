package com.sale_compaign_project.Sale.Compaign.Management.Repository;

import com.sale_compaign_project.Sale.Compaign.Management.Model.Compaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompaignRepo extends JpaRepository<Compaign, Integer> {
    List<Compaign> findByStartDate(LocalDate startDate);
    List<Compaign > findByEndDate(LocalDate endDate);
}
