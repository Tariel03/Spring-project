package com.example.springproject.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(schema = "zakaz")
public class Zakaz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDate localDate;

    public Zakaz(Customer customer, Service service) {
        this.customer = customer;
        this.service = service;
        this.localDate = LocalDate.now();
    }

    public Zakaz() {
        this.localDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return localDate;
    }

    public void setDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
