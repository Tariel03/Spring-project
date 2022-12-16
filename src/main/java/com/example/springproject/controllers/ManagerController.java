package com.example.springproject.controllers;

import com.example.springproject.Repository.CustomerRepository;
import com.example.springproject.Repository.ManagerRepository;
import com.example.springproject.Repository.ZakazRepository;
import com.example.springproject.models.*;
import com.example.springproject.Repository.PostRepository;
import com.example.springproject.Repository.Workers_infoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ManagerController {

    private PostRepository postRepository;
    private Workers_infoRepository workersInfoRepository;
    private CustomerRepository customerRepository;
    private ManagerRepository managerRepository;
    private ZakazRepository zakazRepository;

  @Autowired
    public ManagerController(PostRepository postRepository, Workers_infoRepository workersInfoRepository, CustomerRepository customerRepository, ManagerRepository managerRepository, ZakazRepository zakazRepository) {
        this.postRepository = postRepository;
        this.workersInfoRepository = workersInfoRepository;
        this.customerRepository = customerRepository;
        this.managerRepository = managerRepository;
        this.zakazRepository = zakazRepository;
    }

    @GetMapping("/manager")
    public String blogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "manager";
    }

    @GetMapping("/manager_profile")
    public String manager(Model model, @ModelAttribute("type") Type type) {
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
            Optional<Manager> managerOptional = managerRepository.findManagerByCustomer(optionalCustomer.get());
            if (managerOptional.isPresent()) {
                Manager manager = managerOptional.get();
                model.addAttribute(manager);

                Iterable<Post> posts = postRepository.findAll();
                model.addAttribute("posts", posts);

                Iterable<Workers_info> workersInfos = workersInfoRepository.findAll();
                model.addAttribute("workersInfos", workersInfos);
//                List<Service> serviceList = serviceRepository.findAll();
//                model.addAttribute(serviceList);
//                List<Comment> commentList = commentRepository.findAll();
//                model.addAttribute(commentList);
                List<Customer> customerList = customerRepository.findCustomerByTypeNot("customer");
                List<Type> typesList = Arrays.asList(new Type("manager","manager"), new Type("customer","customer"), new Type("designer","designer"));
                model.addAttribute("typesList",typesList);
                model.addAttribute(customerList);

                List<Workers_info> workers_infos = workersInfoRepository.findWorkers_infoByavailableLike("yes");
                model.addAttribute("workers_infos",workers_infos);

                List<Zakaz> zakazCompletedList = zakazRepository.findZakazByStatusLike("completed");
                model.addAttribute("zakazCompletedList",zakazCompletedList);

                List<Zakaz> zakazProcessList = zakazRepository.findZakazByStatusLike("processing");
                model.addAttribute("zakazProcessList", zakazProcessList);

                return "manager_profile";
            } else {
                return "redirect:/index";
            }
        }
        return username;
    }

    @GetMapping("/manager/add")
    public String blogAdd(Model model){
        return "blog-add";
    }

    @PostMapping("/manager/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/manager/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model){
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/manager/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model){
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/manager/{id}/edit")
    public String blogPostUpdate(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, @PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/manager/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

//    @GetMapping("/manager/blog-main")
//    public String employeeMain(Model model){
//
//        return "blog-main";
//    }
    @PostMapping("/manager/customer/show")
    public String createCustomer(@RequestParam("email")String email, @RequestParam("login")String login , @RequestParam("name")String name, @RequestParam("password")String password, Model model) throws Exception {
        Customer customer = new Customer();
        model.addAttribute(customer);
        Optional<Customer> optionalCustomer = customerRepository.findByLogin(login);
        if(optionalCustomer.isEmpty()) {
            customer.setEmail(email);
            customer.setName(name);
            customer.setLogin(login);
            customer.setPassword(password);
            customer.setType("customer");
            customerRepository.save(customer);
            return "redirect:/login";
        }
        return "redirect:/login?success";
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
            return "redirect:/manager_profile";
        }
        return "redirect:/manager_profile";
    }

}