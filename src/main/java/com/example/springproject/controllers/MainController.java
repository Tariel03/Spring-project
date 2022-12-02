package com.example.springproject.controllers;

import com.example.springproject.Repository.CommentRepository;
import com.example.springproject.Repository.CustomerRepository;
import com.example.springproject.Repository.ServiceRepository;
import com.example.springproject.Repository.ZakazRepository;
import com.example.springproject.models.Comment;
import com.example.springproject.models.Customer;
import com.example.springproject.models.Service;
import com.example.springproject.models.Zakaz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
    public class MainController {
    HttpServletRequest httpServletRequest;
    ServiceRepository serviceRepository;

    CustomerRepository customerRepository;
    CommentRepository commentRepository;
    ZakazRepository zakazRepository;

    @Autowired
    public MainController(HttpServletRequest httpServletRequest, ServiceRepository serviceRepository, CustomerRepository customerRepository, CommentRepository commentRepository, ZakazRepository zakazRepository) {
        this.httpServletRequest = httpServletRequest;
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
        this.commentRepository = commentRepository;
        this.zakazRepository = zakazRepository;
    }





    @GetMapping("/")
    public String home(Model model, @ModelAttribute("comment")Comment comment) {
        model.addAttribute("title", "Main page");
        System.out.println(httpServletRequest.getRemoteUser().getClass().getName());
        return "home";
    }

    @GetMapping("/manager")
    public String managerMenu(Model model) {
        model.addAttribute("Name", "Manager");
        return "manager";
    }

    @GetMapping("/services")
    public String getServices(Model model, @ModelAttribute("customer") Customer customer) {
        List<Service> serviceList = serviceRepository.findAll();
        model.addAttribute(serviceList);
        System.out.println(serviceList);
        return "services";
    }
    @GetMapping("/forgetPassword")
    public String getForgetPassword(){
        return "forgetPassword";

    }
    @PostMapping("/forgetPassword")
    public String forgetPassword(Model model, @RequestParam("email")String email){
        model.getAttribute(email);
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if(customerOptional.isPresent()) return "redirect:/login";
        return "redirect:/";

    }
    @PostMapping("/writeComment")
    public String writeComment(Model model,@RequestParam("comment")String comment){
        String username = new String();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        if (principal instanceof UserDetails) {
           username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }
        Optional<Customer>optionalCustomer = customerRepository.findByLogin(username);
        if(optionalCustomer.isPresent()){
            System.out.println(optionalCustomer.get());
            Comment com = new Comment(comment,optionalCustomer.get());
            commentRepository.save(com);
            return "redirect:/director/comments";
        }
        else {
            return "redirect:/";
        }

    }
    @GetMapping("/profile")
    public String getProfile(Model model,@ModelAttribute("customer")Customer customer){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        String username = new String();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();

        } else {
            username = principal.toString();
        }
        Optional<Customer>optionalCustomer = customerRepository.findByLogin(username);
        if(optionalCustomer.isPresent()) {
            model.addAttribute(optionalCustomer.get());
            System.out.println(optionalCustomer.get());
        }
        return "profile";

    }
    @PostMapping("/edit")
    public String editProfile(Model model,@RequestParam("name")String name,
                              @RequestParam("login")String login, @RequestParam("email")String email){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();

        } else {
            username = principal.toString();
        }
        Optional<Customer>optionalCustomer = customerRepository.findByLogin(username);
        if(optionalCustomer.isPresent()){
            Customer updatedCustomer = optionalCustomer.get();
                updatedCustomer.setLogin(login);
                updatedCustomer.setName(name);
                updatedCustomer.setEmail(email);
            System.out.println(updatedCustomer.getLogin());
            customerRepository.save(updatedCustomer);
        }
        return "redirect:/profile";

    }
    @GetMapping("/edit")
    public String getProfileEdit(){
        return "edit";
    }

    @GetMapping("/order/show")
    public String showOrder(@ModelAttribute("order")Zakaz zakaz, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();

        } else {
            username = principal.toString();
        }
        Optional<Customer>optionalCustomer = customerRepository.findByLogin(username);
        if(optionalCustomer.isPresent()){
            List<Zakaz> zakazList= zakazRepository.findZakazByCustomer_Id(optionalCustomer.get());
            model.addAttribute(zakazList);
        }
        return "order";
    }

    @PostMapping("order/{service_id}")
    public String order(@PathVariable("service_id")Long service_id) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        Optional<Customer> optionalCustomer = customerRepository.findByLogin(username);
        Optional<Service> optionalService = serviceRepository.findById(service_id);
        if (optionalCustomer.isPresent()) {
            System.out.println(optionalCustomer.get());
            Zakaz zakaz = new Zakaz();
            zakaz.setCustomer(optionalCustomer.get());
            zakaz.setService(optionalService.get());
            zakazRepository.save(zakaz);
        }
        return "redirect:/order/show";


    }

    @GetMapping("/aboutUs")
    public String aboutUs(){
        return "aboutUs";
    }


}

