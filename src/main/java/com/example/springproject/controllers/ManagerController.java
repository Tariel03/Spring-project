package com.example.springproject.controllers;

import com.example.springproject.Repository.CustomerRepository;
import com.example.springproject.models.Customer;
import com.example.springproject.models.Post;
import com.example.springproject.models.Workers_info;
import com.example.springproject.Repository.PostRepository;
import com.example.springproject.Repository.Workers_infoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ManagerController {

    private PostRepository postRepository;
    private Workers_infoRepository workersInfoRepository;
    private CustomerRepository customerRepository;
    @Autowired
    public ManagerController(PostRepository postRepository, Workers_infoRepository workersInfoRepository, CustomerRepository customerRepository) {
        this.postRepository = postRepository;
        this.workersInfoRepository = workersInfoRepository;
        this.customerRepository = customerRepository;
    }
    @GetMapping("/manager")
    public String blogMain(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
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

    @GetMapping("/manager/about_employee")
    public String employeeMain(Model model){
        Iterable<Workers_info> workersInfos = workersInfoRepository.findAll();
        model.addAttribute("workersInfos", workersInfos);
        return "blog-main";
    }
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

}
