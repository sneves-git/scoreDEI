package com.example.formdata;

import com.example.data.Team;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.sql.Date;

public class FormPlayer {
    private String name, game_position;
    private Date birth_date;
    private int number_of_goals;
    private String team;

    public FormPlayer() {
    }

    public FormPlayer(String name, String game_position, Date birth_date, String team) {
        this.name = name;
        this.game_position = game_position;
        this.birth_date = birth_date;
        this.number_of_goals = 0;
        this.team = team;
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

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public int getNumber_of_goals() {
        return number_of_goals;
    }

    public void setNumber_of_goals(int number_of_goals) {
        this.number_of_goals = number_of_goals;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
