package com.example.springproject.Repository;

import com.example.springproject.models.Customer;
import com.example.springproject.models.Zakaz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ZakazRepository extends JpaRepository<Zakaz, Long> {
    @Query("SELECT z FROM Zakaz z where z.customer = ?1")
    List<Zakaz>findZakazByCustomer_Id(Customer customer);


}
