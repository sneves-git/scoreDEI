package com.example.data;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

import javax.persistence.Id;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Timestamp time_of_event;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Football_Game game;

    @ManyToOne
    private Team team;

    public Event() {
    }

    public Event(String type, Timestamp time_of_event, Football_Game game) {
        this.type = type;
        this.time_of_event = time_of_event;
        this.player = null;
        this.team = null;
        this.game = game;
    }

    public Event(String type, Timestamp time_of_event, Football_Game game, Player player, Team team) {
        this.type = type;
        this.time_of_event = time_of_event;
        this.player = player;
        this.team = team;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getTime_of_event() {
        return time_of_event;
    }

    public void setTime_of_event(Timestamp time_of_event) {
        this.time_of_event = time_of_event;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Football_Game getGame() {
        return game;
    }

    public void setGame(Football_Game game) {
        this.game = game;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", time_of_event=" + time_of_event +
                ", player=" + player +
                ", game=" + game +
                '}';
    }
}
