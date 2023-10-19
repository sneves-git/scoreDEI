package com.example.demo;

import com.example.data.Team;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    @Query("select t from Team t where t.name = ?1")
    public Team findTeam(String teamName);

    @Query("select t from Team t where t.id = ?1")
    public Team getTeamById(int id);

    @Modifying
    @Query("delete from Team t where t.name = ?1")
    public void deleteTeam(String name);

    @Query("select t from Team t where t.name = ?1")
    public Team checkName(String name);

    @Query("select t from Team t where t.image = ?1")
    public Team checkImage(String name);

    @Query("select t from Team t ORDER BY t.number_of_games DESC")
    public List<Team> getAllTeamsByNumberOfGames();

    @Query("select t from Team t ORDER BY t.victories DESC")
    public List<Team> getAllTeamsByNumberOfVictories();

    @Query("select t from Team t ORDER BY t.defeats DESC")
    public List<Team> getAllTeamsByNumberOfDefeats();

    @Query("select t from Team t ORDER BY t.draws DESC")
    public List<Team> getAllTeamsByNumberOfDraws();


}
