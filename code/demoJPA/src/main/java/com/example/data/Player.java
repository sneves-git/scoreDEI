package com.example.data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name, game_position;

    @Column(nullable = false)
    private Date birth_date;

    @Column(nullable = false)
    private int number_of_goals;

    @ManyToOne()
    private Team team;

    @OneToMany(mappedBy = "player")
    private List<Event> events;

    public Player() {
    }

    public Player(String name, String game_position, Date birthday, int number_of_goals) {
        this.name = name;
        this.game_position = game_position;
        this.birth_date = birthday;
        this.number_of_goals = number_of_goals;
        this.events = new ArrayList<Event>();

    }

    public Player(String name, String game_position, Date birthday, int number_of_goals, Team team) {
        this.name = name;
        this.game_position = game_position;
        this.birth_date = birthday;
        this.number_of_goals = number_of_goals;
        this.team = team;
        this.events = new ArrayList<Event>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGame_position() {
        return game_position;
    }

    public void setGame_position(String game_position) {
        this.game_position = game_position;
    }

    public Date getBirthday() {
        return birth_date;
    }

    public void setBirthday(Date birthday) {
        this.birth_date = birthday;
    }

    public int getNumber_of_goals() {
        return number_of_goals;
    }

    public void setNumber_of_goals(int number_of_goals) {
        this.number_of_goals = number_of_goals;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
