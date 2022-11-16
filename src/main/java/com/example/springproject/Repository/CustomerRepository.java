package com.example.springproject.Repository;

import com.example.springproject.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("SELECT s FROM Customer s where s.login = ?1")
    Optional<Customer> findByLogin(String login);
}
