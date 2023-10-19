package com.example.demo;

import java.util.List;
import java.util.ArrayList;

import com.example.data.Team;
import com.example.formdata.FormTeam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public void addTeam(Team team) {
        teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        List<Team> teamRecords = new ArrayList<>();
        teamRepository.findAll().forEach(teamRecords::add);
        return teamRecords;
    }

    public Team findTeam(String teamName) {
        return teamRepository.findTeam(teamName);
    }

    public Team getTeamById(int id) {
        return teamRepository.getTeamById(id);
    }

    @Transactional
    public void deleteTeam(String name) {
        teamRepository.deleteTeam(name);
    }

    public Team checkName(String name) {
        return teamRepository.checkName(name);
    }

    public Team checkImage(String name) {
        return teamRepository.checkImage(name);
    }


    public List<Team> getAllTeamsByNumberOfGames(){
        return teamRepository.getAllTeamsByNumberOfGames();
    }

    public List<Team> getAllTeamsByNumberOfVictories(){
        return teamRepository.getAllTeamsByNumberOfVictories();
    }

    public List<Team> getAllTeamsByNumberOfDefeats(){
        return teamRepository.getAllTeamsByNumberOfDefeats();
    }

    public List<Team> getAllTeamsByNumberOfDraws(){
        return teamRepository.getAllTeamsByNumberOfDraws();
    }
}
