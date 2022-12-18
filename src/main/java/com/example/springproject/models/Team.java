package com.example.springproject.models;

import javax.persistence.*;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "first_designer_id")
    private Workers_info first_designer;

    public Workers_info getFirst_designer() {
        return first_designer;
    }

    public void setFirst_designer(Workers_info first_designer) {
        this.first_designer = first_designer;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "second_designer_id")
    private Workers_info second_designer;

    public Workers_info getSecond_designer() {
        return second_designer;
    }

    public void setSecond_designer(Workers_info second_designer) {
        this.second_designer = second_designer;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "third_designer_id")
    private Workers_info third_designer;

    public Workers_info getThird_designer() {
        return third_designer;
    }

    public void setThird_designer(Workers_info third_designer) {
        this.third_designer = third_designer;
    }

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "manager_id")
    private Workers_info manager;

    public void setManager(Workers_info manager) {
        this.manager = manager;
    }

    public Workers_info getManager() {
        return manager;
    }

    private String team_name;

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public Team(Workers_info first_designer, Workers_info second_designer, Workers_info third_designer, Workers_info manager, String team_name) {
        this.first_designer = first_designer;
        this.second_designer = second_designer;
        this.third_designer = third_designer;
        this.manager = manager;
        this.team_name = team_name;
    }

    public Team() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
