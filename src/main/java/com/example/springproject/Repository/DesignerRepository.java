package com.example.springproject.Repository;


import com.example.springproject.models.Customer;
import com.example.springproject.models.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignerRepository extends JpaRepository<Designer,Long> {

    Optional<Designer> findDesignerByCustomer(Customer customer);

    List<Designer> findDesignersByCustomerEquals(Customer customer);

}