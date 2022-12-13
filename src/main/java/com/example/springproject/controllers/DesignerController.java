package com.example.springproject.controllers;

import com.example.springproject.Repository.CustomerRepository;
import com.example.springproject.Repository.ZakazRepository;
import com.example.springproject.models.Customer;
import com.example.springproject.models.Zakaz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("designer")
public class DesignerController{
    ZakazRepository zakazRepository;
    CustomerRepository customerRepository;
    @Autowired
    public DesignerController(ZakazRepository zakazRepository, CustomerRepository customerRepository) {
        this.zakazRepository = zakazRepository;
        this.customerRepository = customerRepository;
    }




    private void currentUser(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();

        } else {
            username = principal.toString();
        }
        Optional<Customer> optionalCustomer = customerRepository.findByLogin(username);
        if(optionalCustomer.isPresent()) {
            model.addAttribute(optionalCustomer.get());
            System.out.println(optionalCustomer.get());
        }
        return;
    }

    @GetMapping()
    public String showOrder(@ModelAttribute("order") Zakaz zakaz, Model model) {
        currentUser(model);
        List<Zakaz> zakazList = zakazRepository.findAll();
        model.addAttribute(zakazList);
        return "designer/designer";
    }
    @GetMapping ("/profiledes")
    public String profildes(Model model){
        currentUser(model);
        return "designer/profiledes";
    }


}
