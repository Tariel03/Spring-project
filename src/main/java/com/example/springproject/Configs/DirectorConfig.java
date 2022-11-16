package com.example.springproject.Configs;

import com.example.springproject.Repository.CustomerRepository;
import com.example.springproject.Repository.DirectorRepository;
import com.example.springproject.models.Customer;
import com.example.springproject.models.Director;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Configuration
@Component
public class DirectorConfig {
    CommandLineRunner commandLineRunner(DirectorRepository directorRepository, Director director,CustomerRepository customerRepository, Customer customer
    ) {

//        private String name;
//        private String position;
//        private int salary;
//        private String login;
//        private String password;
        return args -> {
            Customer customer1 = new Customer("tar","tar",50000,"tar","tar");
            customerRepository.save(customer1);


        };
    }
}
