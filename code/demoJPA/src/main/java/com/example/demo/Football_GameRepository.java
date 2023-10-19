package com.example.demo;

import java.util.List;

import com.example.data.Football_Game;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import antlr.debug.Event;

public interface Football_GameRepository extends CrudRepository<Football_Game, Integer> {

    @Query("select g from Football_Game g")
    public List<Football_Game> getAllGames();

    @Query("select g from Football_Game g where g.state = 1 or g.state = 2")
    public List<Football_Game> findGamesInProgress();

    @Query("select g from Football_Game g where g.state = 0")
    public List<Football_Game> findFutureGames();

    @Query("select g from Football_Game g where g.id = ?1")
    public Football_Game findGame(int id);

    @Modifying
    @Query("delete from Football_Game g where g.id = ?1")
    public void deleteGame(int id);


}
