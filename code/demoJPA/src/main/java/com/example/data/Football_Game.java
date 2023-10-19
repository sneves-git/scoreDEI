package com.example.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class Football_Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private int A_Goals, B_Goals;

    @Column(nullable = false)
    private Integer state;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Timestamp game_date;

    @ManyToMany()
    private List<Team> teams;

    @OneToMany(mappedBy = "game")
    private List<Event> events;

    public Football_Game() {
    }

    public Football_Game(int a_Goals, int b_Goals, String location, Timestamp game_date, Integer state) {
        A_Goals = a_Goals;
        B_Goals = b_Goals;
        this.location = location;
        this.game_date = game_date;
        this.teams = new ArrayList<Team>();
        this.events = new ArrayList<Event>();
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getA_Goals() {
        return A_Goals;
    }

    public void setA_Goals(int a_Goals) {
        A_Goals = a_Goals;
    }

    public int getB_Goals() {
        return B_Goals;
    }

    public void setB_Goals(int b_Goals) {
        B_Goals = b_Goals;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getGame_date() {
        return game_date;
    }

    public void setGame_date(Timestamp game_date) {
        this.game_date = game_date;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Football_Game{" +
                "id=" + id +
                ", A_Goals=" + A_Goals +
                ", B_Goals=" + B_Goals +
                ", state=" + state +
                ", location='" + location + '\'' +
                ", game_date=" + game_date +
                ", team[0]=" + this.teams.get(0) +
                '}';
    }
}
