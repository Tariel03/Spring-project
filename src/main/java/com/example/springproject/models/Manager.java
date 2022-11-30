package com.example.springproject.models;


public class Manager implements Us{
    private Long id;

    String name;
    String position;

    int salary;

    String login;
    String password;
    String type;

    public Manager(Long id, String name, String position, int salary, String login, String password, String type) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.login = login;
        this.password = password;
        this.type = type;
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
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
