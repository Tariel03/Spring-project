package com.example.springproject.controllers;

import com.example.springproject.Repository.CommentRepository;
import com.example.springproject.Repository.CustomerRepository;
import com.example.springproject.Repository.ServiceRepository;
import com.example.springproject.models.Comment;
import com.example.springproject.models.Customer;
import com.example.springproject.models.Service;
import com.example.springproject.models.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/director")
public class DirectorController {
    CommentRepository commentRepository;
    ServiceRepository serviceRepository;
    CustomerRepository customerRepository;

    @Autowired
    public DirectorController(CommentRepository commentRepository, ServiceRepository serviceRepository ,CustomerRepository customerRepository) {
        this.commentRepository = commentRepository;
        this.serviceRepository = serviceRepository;
        this.customerRepository = customerRepository;
    }


    @GetMapping
    public String director(Model model, @ModelAttribute("type")Type type) {
        List<Type> typesList = Arrays.asList(new Type("manager","manager"), new Type("customer","customer"), new Type("designer","designer"));
        model.addAttribute("typesList",typesList);
        return "director/directorMenu";
    }

    @GetMapping("/comments")
    public String getComments(Model model, @ModelAttribute("comment") Comment comment) {
        List<Comment> commentList = commentRepository.findAll();
        model.addAttribute(commentList);
        System.out.println(commentList);
        return "director/comments";
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
        return "redirect:/director/services";
    }


    @PostMapping("/service/createService")
    public String createService(@RequestParam("name") String name, @RequestParam("platform") String platform, @RequestParam("price") int price) {
        Service service = new Service();
        service.setName(name);
        service.setPlatform(platform);
        service.setPrice(price);
        serviceRepository.save(service);
        return "redirect:/director/services";

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
            com.setCommentt(optionalComment.get());
            commentRepository.save(com);
            return "redirect:/director/comments";
        }
        else {
            return "redirect:/";
        }

    }
    @PostMapping("/create/Account")
    public String createCustomer(@RequestParam("email")String email, @RequestParam("login")String login , @RequestParam("name")String name, @RequestParam("password")String password, Model model, @ModelAttribute("type")Type type) throws Exception {
        Customer customer = new Customer();
        Optional<Customer> optionalCustomer = customerRepository.findByLogin(login);
        if(optionalCustomer.isEmpty()) {
            customer.setEmail(email);
            customer.setName(name);
            customer.setLogin(login);
            customer.setPassword(password);
            customer.setType(type.getType());
            System.out.println(type.getType());
            customerRepository.save(customer);
            return "redirect:/login";
        }
        return "redirect:/home";
    }


}
