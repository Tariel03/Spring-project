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
public class ManagerController {

    private PostRepository postRepository;
    private Workers_infoRepository workersInfoRepository;
    private CustomerRepository customerRepository;
    private ManagerRepository managerRepository;
    private ZakazRepository zakazRepository;
    private SuggestWorkerRepository suggestWorkerRepository;
    private DesignerRepository designerRepository;
    private DirectorRepository directorRepository;

    @Autowired
    public ManagerController(PostRepository postRepository, Workers_infoRepository workersInfoRepository, CustomerRepository customerRepository, ManagerRepository managerRepository, ZakazRepository zakazRepository, SuggestWorkerRepository suggestWorkerRepository, DesignerRepository designerRepository, DirectorRepository directorRepository) {
        this.postRepository = postRepository;
        this.workersInfoRepository = workersInfoRepository;
        this.customerRepository = customerRepository;
        this.managerRepository = managerRepository;
        this.zakazRepository = zakazRepository;
        this.suggestWorkerRepository = suggestWorkerRepository;
        this.designerRepository = designerRepository;
        this.directorRepository = directorRepository;
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

                List<Zakaz> zakazCompletedList = zakazRepository.findZakazsByDesignerIsNotNullAndStatusLike("completed");
                model.addAttribute("zakazCompletedList",zakazCompletedList);

                List<Zakaz> zakazProcessList = zakazRepository.findZakazsByDesignerIsNotNullAndStatusLike("processing");
                model.addAttribute("zakazProcessList", zakazProcessList);

                List<SuggestWorker> suggestWorkerList = suggestWorkerRepository.findSuggestWorkersByCustomer(optionalCustomer.get());
                model.addAttribute("suggestWorkerList",suggestWorkerList);

                List<Designer> designerList = designerRepository.findDesignersByCustomerIsNotNull();
                model.addAttribute(designerList);

                List<Director> directorList = directorRepository.findDirectorsByCustomerIsNotNull();
                model.addAttribute(directorList);

                List<Workers_info> workers_infoList = workersInfoRepository.findAll();
                model.addAttribute(workers_infoList);

                return "manager_profile";
            } else {
                return "redirect:/index";
            }
        }
        return username;
    }

    @GetMapping("manager/viewZakaz/{id}")
        public String viewZakaz(Model model,@PathVariable("id")Long id){
        Optional<Zakaz> optionalZakaz = zakazRepository.findById(id);
        Zakaz zakaz = new Zakaz();
        if(optionalZakaz.isPresent()){
            zakaz = optionalZakaz.get();
        }
        model.addAttribute(zakaz);
        return "viewZakaz";
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
    @PostMapping("/manager/suggest/worker")
    public String suggestWorker(Model model, @RequestParam("type") String type,@RequestParam("salary") int salary, @RequestParam("address")String address,@RequestParam("lastname")String lastname){
        SuggestWorker suggestWorker = new SuggestWorker();
        if(type.equals("designer") || type.equals("manager")){
            suggestWorker.setMessage("Please create an account , if you agree to hire!");
        }else {
            suggestWorker.setMessage("No need to create an account!");

        }
        suggestWorker.setLastname(lastname);
        suggestWorker.setSalary(salary);
        suggestWorker.setAddress(address);
        suggestWorker.setType(type);
        suggestWorker.setStatus("sent");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal() ;
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();

        } else {
            username = principal.toString();
        }
        Optional<Customer> optionalCustomer = customerRepository.findByLogin(username);
        optionalCustomer.ifPresent(suggestWorker::setCustomer);

        suggestWorkerRepository.save(suggestWorker);
        return "redirect:/manager_profile";


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