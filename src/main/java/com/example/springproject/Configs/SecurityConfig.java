package com.example.springproject.Configs;

import com.example.springproject.Repository.CustomerRepository;
import com.example.springproject.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
    @EnableWebSecurity
   public class SecurityConfig {
    CustomerRepository customerRepository;
    @Autowired
    public SecurityConfig(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests((requests) -> requests
                            .antMatchers("/", "/home","main","forgetPassword").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin((form) -> form
                            .loginPage("/login")
                            .permitAll()
                    )
                    .logout(LogoutConfigurer::permitAll);

            return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
//            UserDetails user =
//                    User.withDefaultPasswordEncoder()
//                            .username("tariel")
//                            .password("4508")
//                            .roles("Manager")
//                            .build();
//            UserDetails user1 = User.withDefaultPasswordEncoder()
//                    .username("Aizat")
//                    .password("3000")
//                    .roles("Customer")
//                    .build();
            List<UserDetails> users = new ArrayList<>();
            List<Customer> customerList = customerRepository.findAll();
            for (Customer customer:customerList
                 ) {
                users.add(User.withDefaultPasswordEncoder().
                        username(customer.getLogin())
                        .password(customer.getPassword())
                        .roles("user")
                        .build());
            }
            return new InMemoryUserDetailsManager(users);


        }


}
