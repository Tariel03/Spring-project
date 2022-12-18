package com.example.springproject.Repository;

import com.example.springproject.models.Customer;
import com.example.springproject.models.Designer;
import com.example.springproject.models.Director;
import com.example.springproject.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long> {

    Optional<Manager> findManagerByCustomer(Customer customer);

    List<Manager> findManagersByCustomerEquals(Customer customer);



}