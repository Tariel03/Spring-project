package com.example.springproject.models;

import javax.persistence.*;

@Entity
public class Workers_info {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, surname, orders;
    private int salary, bonus, taxes, work_experience;

    private String available;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getTaxes() {
        return taxes;
    }

    public void setTaxes(int taxes) {
        this.taxes = taxes;
    }

    public int getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(int work_experience) {
        this.work_experience = work_experience;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public Workers_info() {

    }

    public String get_available() {
        return available;
    }

    public void set_available(String available) {
        this.available = available;
    }

    public Workers_info(Long id, String name, String surname, String orders, int salary, int bonus, int taxes, int work_experience, String available, Customer customer) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.orders = orders;
        this.salary = salary;
        this.bonus = bonus;
        this.taxes = taxes;
        this.work_experience = work_experience;
        this.available = available;
        this.customer = customer;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}