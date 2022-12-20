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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public String designer(Model model, @ModelAttribute("type") Type type, Zakaz zakaz) {

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

                List<Zakaz> zakazs=zakazRepository.findZakazsByDesignerAndStatusLike(designer,"processing");
                model.addAttribute("zakazs",zakazs);

                List<Zakaz> completedorder=zakazRepository.findZakazsByDesignerAndStatusLike(designer,"completed");
                model.addAttribute("completedorders",completedorder);

                currentUser(model);

                List<Zakaz>counter=zakazRepository.findZakazByStatusLike("No completed");

                model.addAttribute("nocompletedorders",counter);
                model.addAttribute("counter",counter.size());

                List<Customer>CustomerList=customerRepository.findCustomerByType("customer");
                model.addAttribute("countCustomers",CustomerList.size());

                model.addAttribute("countcompleted",completedorder.size());

                model.addAttribute("countCustomers",CustomerList.size());

                return "designer/designer";
            } else {
                return "redirect:/index";
            }
        }
        return username;
    }
@PostMapping("process/{id}")
public String process(@PathVariable(value = "id")Long id,Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();

        } else {
            username = principal.toString();
        }
        Designer  designer = new Designer();
        Optional<Customer> optionalCustomer = customerRepository.findByLogin(username);
        if(optionalCustomer.isPresent()){
            Optional<Designer> designerOptional =designerRepository.findDesignerByCustomer(optionalCustomer.get());
            if(designerOptional.isPresent()){
                designer = designerOptional.get();
            }
        }

    Optional<Zakaz> zakazOptional=zakazRepository.findById(id);

        if(zakazOptional.isPresent()) {
            Zakaz zakaz = zakazOptional.get();

            if (!zakaz.getStatus().equals("completed")) {
                zakaz.setDate(LocalDate.now());
                zakaz.setDesigner(designer);
                zakaz.setStatus("processing");
                zakazRepository.save(zakaz);
            } else if (zakaz.getStatus().equals("completed")) {
                zakaz.setDate(LocalDate.now());
            }

        }return "redirect:/designer";

    }
    @PostMapping("to_do/{id}")
    public String to_do(@PathVariable(value = "id")Long id,Model model){
        Optional<Zakaz> zakazOptional=zakazRepository.findById(id);
        if(zakazOptional.isPresent()){
            Zakaz zakaz=zakazOptional.get();
            zakaz.setStatus("completed");
            zakazRepository.save(zakaz);
        }return "redirect:/designer";

    }




    private Customer currentUser(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        Customer customer = new Customer();
        Optional<Customer> optionalCustomer = customerRepository.findByLogin(username);
        if(optionalCustomer.isPresent()) {
            model.addAttribute(optionalCustomer.get());
            System.out.println(optionalCustomer.get());
            customer = optionalCustomer.get();
        }
        return customer;
    }


    @GetMapping("/profiledes")
    public String manager(Model model, @ModelAttribute("type") Type type) {
        currentUser(model);
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
            Optional<Designer> managerOptional = designerRepository.findDesignerByCustomer(optionalCustomer.get());
            if (managerOptional.isPresent()) {
                Designer designer = managerOptional.get();
                model.addAttribute(designer);
                return "designer/profiledes";
            } else {
                return "redirect:/index";
            }
        }
        return username;
    }
    @GetMapping("/message")
    public String message(Model model){
        return "/designer/message";
    }
}
