package com.example.data;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    private String best_goal_scorer;

    @Column(unique = true, nullable = false)
    private String image;

    @Column(nullable = false)
    private int number_of_games, victories, defeats, draws;

    @OneToMany(mappedBy = "team")
    private List<Player> players;

    @OneToMany(mappedBy = "team")
    private List<Event> events;

    public Team() {
    }

    public Team(String name, String best_goal_scorer, String image, int number_of_games, int victories,
            int defeats, int draws) {

        this.name = name;
        this.best_goal_scorer = best_goal_scorer;
        this.image = image;
        this.number_of_games = number_of_games;
        this.victories = victories;
        this.defeats = defeats;
        this.draws = draws;
        this.players = new ArrayList<Player>();
        this.events = new ArrayList<Event>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBest_goal_scorer() {
        return best_goal_scorer;
    }

    public void setBest_goal_scorer(String best_goal_scorer) {
        this.best_goal_scorer = best_goal_scorer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumber_of_games() {
        return number_of_games;
    }

    public void setNumber_of_games(int number_of_games) {
        this.number_of_games = number_of_games;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public int getDefeats() {
        return defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event e) {
        this.events.add(e);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", best_goal_scorer='" + best_goal_scorer + '\'' +
                ", image=" + image +
                ", number_of_games=" + number_of_games +
                ", victories=" + victories +
                ", defeats=" + defeats +
                ", draws=" + draws +
                '}';
    }
}
