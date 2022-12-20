package com.example.springproject.controllers;

import com.example.springproject.Repository.*;
import com.example.springproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostRemove;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/director")
public class DirectorController {
    CommentRepository commentRepository;
    ServiceRepository serviceRepository;
    CustomerRepository customerRepository;
    DirectorRepository directorRepository;
    ZakazRepository zakazRepository;
    SuggestWorkerRepository suggestWorkerRepository;
    DesignerRepository designerRepository;
    ManagerRepository managerRepository;
    Workers_infoRepository workersInfoRepository;
    @Autowired
    public DirectorController(CommentRepository commentRepository, ServiceRepository serviceRepository, CustomerRepository customerRepository, DirectorRepository directorRepository, ZakazRepository zakazRepository, SuggestWorkerRepository suggestWorkerRepository, DesignerRepository designerRepository, ManagerRepository managerRepository, Workers_infoRepository workersInfoRepository) {
        this.commentRepository = commentRepository;
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
        this.directorRepository = directorRepository;
        this.zakazRepository = zakazRepository;
        this.suggestWorkerRepository = suggestWorkerRepository;
        this.designerRepository = designerRepository;
        this.managerRepository = managerRepository;
        this.workersInfoRepository = workersInfoRepository;
    }


    @GetMapping
    public String director(Model model) {
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
            Optional<Director> directorOptional = directorRepository.findDirectorByCustomer(optionalCustomer.get());
            if (directorOptional.isPresent()) {
                Director director = directorOptional.get();
                model.addAttribute(director);
                List<Service> serviceList = serviceRepository.findAll();
                model.addAttribute(serviceList);
                List<Comment> commentList = commentRepository.findAll();
                model.addAttribute(commentList);


                List<Customer> customerList = customerRepository.findCustomersByTypeNotAndTypeNotOrderById("customer", "director");
                model.addAttribute("customerList",customerList);


                List<SuggestWorker> suggestWorkerList = suggestWorkerRepository.findSuggestWorkersByStatusLike("sent");
                model.addAttribute("suggestWorkerList",suggestWorkerList);


                List<Zakaz> zakazCompletedList = zakazRepository.findZakazByStatusLike("completed");
                model.addAttribute("zakazCompletedList",zakazCompletedList);

                List<Designer> designers = designerRepository.findDesignersByCustomerEquals(null);
                model.addAttribute(designers);

                List<Manager> managerList = managerRepository.findManagersByCustomerEquals(null);
                model.addAttribute(managerList);

                List<Zakaz> zakazProcessList = zakazRepository.findZakazByStatusLike("processing");
                model.addAttribute("zakazProcessList", zakazProcessList);
                return "director/director";
            } else {
                return "redirect:/index";
            }
        }
        return username;
    }

    @GetMapping("/comments")
    public String getComments(Model model, @ModelAttribute("comment") Comment comment) {
        List<Comment> commentList = commentRepository.findAll();
        model.addAttribute(commentList);
        System.out.println(commentList);
        return "director/comments";
    }

    @PostMapping("add/customer/{id}")
    public String addCustomerId(Model model,@RequestParam("customer_id") Customer customer, @PathVariable("id")Long id){
        Optional<Designer> designer = designerRepository.findById(id);
        if(designer.isPresent()){
            Designer designer1 = designer.get();
            designer1.setCustomer(customer);
            designerRepository.save(designer1);
        }
        return "redirect:/director";
    }

    @PostMapping("edit/service/{id}")
    public String edit(Model model, @RequestParam("price") int price,  @PathVariable("id")Long id){
        Optional<Service> optionalService = serviceRepository.findById(id);
        if(optionalService.isPresent()) {
            Service service = optionalService.get();
            service.setPrice(price);
            serviceRepository.save(service);
            model.addAttribute(service);
        }

        return "redirect:/director";

    }

    @PostMapping("delete/suggestWorker/{id}")
    public String deleteSuggestWorker(@PathVariable("id") Long id){
        Optional<SuggestWorker> suggestWorkerOptional = suggestWorkerRepository.findById(id);
        if(suggestWorkerOptional.isPresent()){
            SuggestWorker suggestWorker = suggestWorkerOptional.get();
            suggestWorker.setStatus("declined");
            suggestWorker.setAnswer("Do not need this person");
            suggestWorkerRepository.save(suggestWorker);
        }
        return "redirect:/director";
    }
    @PostMapping("approve/suggestWorker/{id}")
    public String approveSuggestWorker(@PathVariable("id") Long id){
        Optional<SuggestWorker> suggestWorker = suggestWorkerRepository.findById(id);
        if(suggestWorker.isPresent()){
            SuggestWorker suggestWorker1 =suggestWorker.get();
            suggestWorker1.setStatus("approved");
            suggestWorker1.setAnswer("Approved, thanks!");
            if(suggestWorker1.getType().equals("designer") ){
                Designer designer = new Designer();
                designer.setLastname(suggestWorker1.getLastname());
                designer.setSalary(suggestWorker1.getSalary());
                designer.setAddress(suggestWorker1.getAddress());
                designerRepository.save(designer);
            }
            else if(suggestWorker1.getType().equals("manager")){
                Manager manager = new Manager();
                manager.setName(suggestWorker1.getLastname());
                manager.setSalary(suggestWorker1.getSalary());
                managerRepository.save(manager);
            }
            else{
                Workers_info workersInfo = new Workers_info();
                workersInfo.setSalary(suggestWorker1.getSalary());
                workersInfo.setLastname(suggestWorker1.getLastname());
                workersInfo.setType(suggestWorker1.getType());
                workersInfo.setAddress(suggestWorker1.getAddress());
                workersInfoRepository.save(workersInfo);


            }
        }
        return "redirect:/director";
    }


    @GetMapping("/services")
    public String getServices(Model model, @ModelAttribute("customer") Customer customer) {
        List<Service> serviceList = serviceRepository.findAll();
        model.addAttribute(serviceList);
        System.out.println(serviceList);
        return "director/services";
    }

    @PostMapping("/service/{id}")
    public String deleteService(@PathVariable("id") Long id) {
        serviceRepository.deleteById(id);
        return "redirect:/director";
    }
    @PostMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable("id") Long id) {
        commentRepository.deleteById(id);
        return "redirect:/director";
    }

    @PostMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerRepository.deleteById(id);
        return "redirect:/director";
    }

    @PostMapping("/service/createService")
    public String createService(@RequestParam("name") String name, @RequestParam("platform") String platform, @RequestParam("price") int price, @RequestParam("length") int length) {
        Service service = new Service();
        service.setName(name);
        service.setPlatform(platform);
        service.setPrice(price);
        service.setLength(length);
        serviceRepository.save(service);
        return "redirect:/director";

    }
    @PostMapping("/writeComment/{comment_id}")
    public String writeComment(Model model,@RequestParam("comment")String comment, @PathVariable("comment_id") Long comment_id){
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        Optional<Customer> optionalCustomer = customerRepository.findByLogin(username);
        Optional<Comment> optionalComment = commentRepository.findById(comment_id);
        if(optionalCustomer.isPresent()){
            System.out.println(optionalCustomer.get());
            Comment com = new Comment(comment,optionalCustomer.get());

            commentRepository.save(com);
            return "redirect:/director";
        }
        else {
            return "redirect:/index";
        }

    }
    @PostMapping("/create/Account")
    public String createCustomer(@RequestParam("email")String email, @RequestParam("login")String login , @RequestParam("name")String name, @RequestParam("password")String password, @ModelAttribute("type")String type)  {
        Customer customer = new Customer();
        Optional<Customer> optionalCustomer = customerRepository.findByLogin(login);
        if(optionalCustomer.isEmpty()) {
            customer.setEmail(email);
            customer.setName(name);
            customer.setLogin(login);
            customer.setPassword(password);
            customer.setType(type);
            customerRepository.save(customer);
            return "redirect:/director";
        }
        return "redirect:/director";
    }


}
