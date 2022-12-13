package com.example.springproject.controllers;

import com.example.springproject.Repository.CustomerRepository;
import com.example.springproject.Repository.DesignerRepository;
import com.example.springproject.Repository.ZakazRepository;
import com.example.springproject.models.*;
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
    DesignerRepository designerRepository;
    @Autowired
    public DesignerController( DesignerRepository designerRepository,ZakazRepository zakazRepository, CustomerRepository customerRepository) {
        this.zakazRepository = zakazRepository;
        this.customerRepository = customerRepository;
        this.designerRepository= designerRepository;

    }
    @GetMapping
    public String designer(Model model, @ModelAttribute("type") Type type,Zakaz zakaz) {
        List<Zakaz> zakazList = zakazRepository.findAll();
        currentUser(model);
        model.addAttribute(zakazList);
        int counter=0;
        for(int i=0;i<zakazList.size();i++){
            counter++;
        }
        model.addAttribute(counter);
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
            Optional<Designer> designerOptional = designerRepository.findDesignerByCustomer(optionalCustomer.get());
            if (designerOptional.isPresent()) {
                Designer designer = designerOptional.get();
                model.addAttribute(designer);

                return "designer/designer";
            } else {
                return "redirect:/index";
            }
        }
        return username;
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

//    @GetMapping()
//    public String showOrder(@ModelAttribute("order") Zakaz zakaz, Model model) {
//        List<Zakaz> zakazList = zakazRepository.findAll();
//        currentUser(model);
//        model.addAttribute(zakazList);
//        int counter=0;
//        for(int i=0;i<zakazList.size();i++){
//            counter++;
//        }
//        model.addAttribute(counter);
//        return "designer/designer";
//    }
    @GetMapping ("/profiledes")
    public String profildes(Model model){
        currentUser(model);
        return "designer/profiledes";
    }


}
