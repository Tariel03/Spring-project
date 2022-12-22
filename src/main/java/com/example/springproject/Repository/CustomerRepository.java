package com.example.springproject.Repository;

import com.example.springproject.models.Customer;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
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
    @Query("SELECT s from Customer  s where s not in(select  des.customer from Designer des )" +
            " and s not in (select dir.customer FROM Director  dir) " +
            "and s not in (select man.customer From Manager man) and s.type not like 'customer'")
    List<Customer>findExCustomers();

    @Query("SELECT s from Customer  s where s  in(select  des.customer from Designer des )" +
            " or  s in (select man.customer From Manager man) and s.type not like 'customer' and  s.type not like 'director'")
    List<Customer>findWorkers();

    List<Customer> findCustomerByTypeNotOrderById(String type);

    List<Customer> findCustomerByType(String type);

    List<Customer>findCustomersByTypeNotAndTypeNotOrderById(String type1, String type2);





}
