package com.example.formdata;

public class FormTimeOfEvent {
    private String team;
    private String playerId;

    public FormTimeOfEvent() {
    }

    public FormTimeOfEvent(String team, String playerId) {
        this.playerId = playerId;
        this.team = team;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
