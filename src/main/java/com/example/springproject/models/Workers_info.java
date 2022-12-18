package com.example.springproject.models;

import javax.persistence.*;

@Entity
public class Workers_info {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String message;
    String type;
    int salary;
    String lastname;
    String address;

    public Workers_info(String message, String type, int salary, String lastname, String address) {
        this.message = message;
        this.type = type;
        this.salary = salary;
        this.lastname = lastname;
        this.address = address;
    }

    public Workers_info() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
