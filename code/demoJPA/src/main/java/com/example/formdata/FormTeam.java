package com.example.formdata;

import com.example.data.Player;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

public class FormTeam {
    private String name;

    private String best_goal_scorer;

    private String image;

    private int number_of_games, victories, defeats, draws;


    public FormTeam(){}
    public FormTeam(String name, String image) {
        this.name = name;
        this.image = image;
        this.number_of_games = 0;
        this.best_goal_scorer = null;
        this.defeats = 0;
        this.draws = 0;
        this.victories = 0;
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
}
