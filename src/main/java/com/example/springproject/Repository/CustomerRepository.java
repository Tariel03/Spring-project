package com.example.springproject.Repository;

import com.example.springproject.models.Customer;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("SELECT s FROM Customer s where s.login = ?1")
    Optional<Customer> findByLogin(String login);

    @Query("SELECT s from Customer s where s.email = ?1")
    Optional<Customer>findByEmail(String email);
    @Query("SELECT s from Customer s where s.login=?1 and s.password=?2")
    Optional<Customer>findByLoginAndPassword(String login, String password);


}
