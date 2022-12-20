package com.example.springproject.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private LocalDate date;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Comment(String comment,Customer customer) {
        this.comment = comment;
        this.customer = customer;
        this.date = LocalDate.now();
    }



    public Comment() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
