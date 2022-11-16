package com.example.springproject.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer implements User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
   private String position;
    private int salary;
    private String login;
    private String password;

    public Customer() {

    }


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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Customer(String name, String position, int salary, String login, String password) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.login = login;
        this.password = password;
    }
}
