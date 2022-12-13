package com.example.springproject.models;


import javax.persistence.*;

@Entity
public class Manager implements Us{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public Manager(Customer customer, int salary) {
        this.customer = customer;
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    int salary;



    public Manager() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String TypeOfAc() {
        return null;
    }

    @Override
    public void menu() {

    }

    @Override
    public String login(String login, String password) {
        return null;
    }

    @Override
    public String signUp(String login, String password) {
        return null;
    }


}
