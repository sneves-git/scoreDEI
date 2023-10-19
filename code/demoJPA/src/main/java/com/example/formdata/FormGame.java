package com.example.formdata;

import com.example.data.Event;
import com.example.data.Team;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

public class FormGame {

    private int A_Goals, B_Goals;
    private String A_Team, B_Team;

    private Integer state;

    private String location;

    private String game_date;

    public FormGame() {
    }

    public FormGame(String a_Team, String b_Team, String location, String game_date) {
        this.A_Team = a_Team;
        this.B_Team = b_Team;
        this.location = location;
        this.game_date = game_date;
        this.A_Goals = 0;
        this.B_Goals = 0;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getA_Team() {
        return A_Team;
    }

    public void setA_Team(String a_Team) {
        A_Team = a_Team;
    }

    public String getB_Team() {
        return B_Team;
    }

    public void setB_Team(String b_Team) {
        B_Team = b_Team;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGame_date() {
        return game_date;
    }

    public void setGame_date(String game_date) {
        this.game_date = game_date;
    }
}
