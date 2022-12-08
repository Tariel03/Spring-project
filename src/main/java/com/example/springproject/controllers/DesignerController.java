package com.example.springproject.controllers;

import com.example.springproject.Repository.ZakazRepository;
import com.example.springproject.models.Zakaz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("designer")
public class DesignerController{
    ZakazRepository zakazRepository;
    @Autowired
    public DesignerController(ZakazRepository zakazRepository) {
        this.zakazRepository = zakazRepository;
    }

    @GetMapping
    public String designer(){
        return "designer/designer";
    }

    @GetMapping("order")
    public String showOrder(@ModelAttribute("order") Zakaz zakaz, Model model) {
        List<Zakaz> zakazList = zakazRepository.findAll();
        model.addAttribute(zakazList);
        return "order";
    }


}
