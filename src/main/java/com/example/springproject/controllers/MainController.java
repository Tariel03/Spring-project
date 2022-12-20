package com.example.springproject.controllers;

import com.example.springproject.Repository.*;
import com.example.springproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
    public class MainController {
    HttpServletRequest httpServletRequest;
    ServiceRepository serviceRepository;

    CustomerRepository customerRepository;
    CommentRepository commentRepository;
    ZakazRepository zakazRepository;
    static Customer realCustomer;
    DesignerRepository designerRepository;

    @Autowired
    public MainController(HttpServletRequest httpServletRequest, ServiceRepository serviceRepository, CustomerRepository customerRepository, CommentRepository commentRepository, ZakazRepository zakazRepository, DesignerRepository designerRepository) {
        this.httpServletRequest = httpServletRequest;
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
        this.commentRepository = commentRepository;
        this.zakazRepository = zakazRepository;
        this.designerRepository = designerRepository;
    }

    @GetMapping("/index")
    public String index(Model model){
        List<Service> serviceList = serviceRepository.findAll();
        model.addAttribute(serviceList);
        List<Comment> commentList = commentRepository.findAll();
        List<Comment> commentArrayList = new ArrayList<>();
        List<Customer> customerList = customerRepository.findCustomerByTypeNotOrderById("customer");
        model.addAttribute(customerList);
        if(commentList.size()>5){
            for (int i = commentList.size()-1; i > commentList.size()- 6; i--) {
                commentArrayList.add(commentList.get(i));
            }
        }
        else {
            commentArrayList =commentList;
        }

        model.addAttribute(commentArrayList);

        List<Zakaz> zakazList = zakazRepository.findZakazByCustomer_Id(currentUser(model));
        model.addAttribute(zakazList);
        System.out.println(serviceList.size());
        Customer customer = currentUser(model);
        return "index";
    }



    private Customer currentUser(Model model) {
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
        return optionalCustomer.get();
    }


    @GetMapping("/services")
    public String getServices(Model model) {
        List<Service> serviceList = serviceRepository.findAll();
        model.addAttribute(serviceList);
        System.out.println(serviceList);
        return "services";
    }
    @GetMapping("/designers")
    public String getWorkers(Model model) {
        List<Designer> designers = designerRepository.findAll();
        model.addAttribute("designers",designers);
        return "workers";
    }

    @GetMapping("/forgetPassword")
    public String forgetPassword(Model model) {
        String status = "Hello!";
        model.addAttribute("status", status);
        System.out.println(status);
        return "/forgetPassword";

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
        }
        return "redirect:/index";
    }
    @GetMapping("/profile")
    public String getProfile(Model model,@ModelAttribute("customer")Customer customer){
        currentUser(model);
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
            customerRepository.save(updatedCustomer);
        }
        return "redirect:/login";

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
            zakaz.setStatus("received");
        }
        return "redirect:/index";


    }

    @PostMapping("/changePassword")
    public String changePassword(Model model,@RequestParam("login")String login, @RequestParam("password")String password){
        Customer customer = realCustomer;
        customer.setLogin(login);
        customer.setPassword(password);
        customerRepository.save(customer);
        return "redirect:/login";

    }

    @PostMapping("/findByEmail")
    public String findByEmail(Model model,@RequestParam("email")String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        String status = new String();
        if(customer.isPresent()){
            realCustomer = customer.get();
            status = "Success";
        }
        else {
            status = "No such email";
        }
        model.addAttribute(status);


        return "redirect:/forgetPassword";
    }

    @GetMapping("customer/{id}")
    public String viewCustomer(Model model,@PathVariable("id")Long id){
        Optional<Customer> customer = customerRepository.findById(id);
        Customer customer1 = new Customer();
        if(customer.isPresent()){
           customer1 = customer.get();
        }
        model.addAttribute(customer1);
        return "viewCustomer";
    }

    @GetMapping("designer/{id}")
    public String viewDesigner(Model model,@PathVariable("id")Long id){
        Optional<Designer> designer = designerRepository.findById(id);
        Designer designer1 = new Designer();
        if(designer.isPresent()){
            designer1 = designer.get();
            List<Zakaz> completedZakazsList = zakazRepository.findZakazsByDesignerAndStatusLike(designer1,"completed");
            model.addAttribute("completedZakazs",completedZakazsList.size());
            List<Zakaz> processZakazsList = zakazRepository.findZakazsByDesignerAndStatusLike(designer1,"processing");
            model.addAttribute("processZakazs",processZakazsList.size());
            List<Zakaz> zakazList = zakazRepository.findZakazsByDesigner(designer1);
            model.addAttribute("zakaz", zakazList.size());
        }
        model.addAttribute(designer1);
        return "viewDesigner";
    }





}

