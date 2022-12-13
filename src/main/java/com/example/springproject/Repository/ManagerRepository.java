package com.example.springproject.Repository;

import com.example.springproject.models.Customer;
import com.example.springproject.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
    Optional<Manager> findByCustomer(Customer customer);
}
