package com.example.springproject.controllers;

import com.example.springproject.Repository.CommentRepository;
import com.example.springproject.Repository.ServiceRepository;
import com.example.springproject.models.Comment;
import com.example.springproject.models.Customer;
import com.example.springproject.models.Service;
import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/director")
public class DirectorController {
    CommentRepository commentRepository;
    ServiceRepository serviceRepository;

    @Autowired
    public DirectorController(CommentRepository commentRepository, ServiceRepository serviceRepository) {
        this.commentRepository = commentRepository;
        this.serviceRepository = serviceRepository;
    }


    @GetMapping
    public String director() {
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
    public String createService(@RequestParam("name")String name, @RequestParam("platform") String platform, @RequestParam("price") int price){
        Service service = new Service();
        service.setName(name);
        service.setPlatform(platform);
        service.setPrice(price);
        serviceRepository.save(service);
        return "redirect:/director/services";

    }


}
