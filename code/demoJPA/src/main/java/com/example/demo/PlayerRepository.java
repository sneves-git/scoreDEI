package com.example.demo;

import java.util.List;

import com.example.data.Player;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    @Query("select p from Player p")
    public List<Player> getAllPlayers();

    @Query("select p from Player p where p.id = ?1")
    public Player findPlayerById(int playerId);

    @Query("select p from Player p where p.team.id = ?1 or p.team.id = ?2")
    public List<Player> getGamePlayers(int team_A, int team_B);

    @Query("select p from Player p ORDER BY p.number_of_goals DESC")
    public List<Player> getBestScorer();

    @Modifying
    @Query("delete from Player p where p.id = ?1")
    public void deletePlayer(int id);

    @Modifying
    @Query("delete from Player p where p.team.id = ?1")
    public void deletePlayerFromTeam(int id);
}
