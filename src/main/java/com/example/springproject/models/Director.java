package com.example.springproject.models;


import javax.persistence.*;

@Entity
public class Director implements Us{
    @Id
    private Long id;

    @Column(name = "name",nullable = false,length = 30,insertable = false,updatable = false)
    String name;
    @Column(name = "position",nullable = false,length = 30)
    String position;
    @Column(name = "name", nullable = false)
    int salary;
    @Column(name = "login", nullable = false,length = 30)
    String login;
    @Column(name = "password", nullable = false,length = 30)
    String password;
    @Column(name = "type",nullable = false,length = 30)
    String type;

    public Director(String name, String position, int salary, String login, String password, String type) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.login = login;
        this.password = password;
        this.type = "director";
    }

    public Director() {

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
