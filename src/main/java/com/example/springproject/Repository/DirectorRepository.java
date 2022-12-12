package com.example.springproject.Repository;

import com.example.springproject.models.Customer;
import com.example.springproject.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director,Long> {

    Optional<Director> findDirectorByCustomer(Customer customer);

}
