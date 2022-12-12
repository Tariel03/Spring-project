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

    public Comment getCommentt() {
        return commentt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCommentt(Comment commentt) {
        this.commentt = commentt;
    }

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment commentt;

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

    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
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

    public Comment(String comment, Comment commentt, Customer customer) {
        this.comment = comment;
        this.date = LocalDate.now();
        this.commentt = commentt;
        this.customer = customer;
    }

    public Comment() {
    }


}
