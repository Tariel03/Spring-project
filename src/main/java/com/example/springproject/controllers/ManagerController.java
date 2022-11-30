package com.example.springproject.controllers;

import com.example.springproject.Repository.CustomerRepository;
import com.example.springproject.Repository.DirectorRepository;
import com.example.springproject.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.font.ShapeGraphicAttribute;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    DirectorRepository directorRepository;
    CustomerRepository customerRepository;

    @Autowired
    public ManagerController(DirectorRepository directorRepository,CustomerRepository customerRepository) {
        this.directorRepository = directorRepository;
        this.customerRepository = customerRepository;
    }


    @GetMapping("/customer")
    public String getCustomers(Model model, @ModelAttribute("customer") Customer customer){
        List<Customer> customerList = customerRepository.findAll();
        model.addAttribute(customerList);
        System.out.println(customerList);
        return "customer/show";
    }
    @GetMapping("/customer/{id}")
    public String getCustomer(Model model, @ModelAttribute("customer") Customer customer, @PathVariable Long id){
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isPresent()) customer = customerOptional.get();
        model.addAttribute(customer);
        return "customer/index";

    }

    @PostMapping("/customer/show")
    public String createCustomer(@RequestParam("email")String email, @RequestParam("login")String login , @RequestParam("name")String name, @RequestParam("password")String password, Model model) throws Exception {
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setName(name);
        customer.setLogin(login);
        customer.setPassword(password);
        customerRepository.save(customer);
        return "redirect:/";
    }








}
