package com.example.demo;

import java.util.List;

import com.example.data.Event;
import com.example.data.Football_Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Football_GameService {
    @Autowired
    private Football_GameRepository gameRepository;

    public void addGame(Football_Game Football_Game) {
        gameRepository.save(Football_Game);
    }

    public List<Football_Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    public Football_Game findGame(int id) {
        return gameRepository.findGame(id);
    }

    public List<Football_Game> findGamesInProgress() {
        return gameRepository.findGamesInProgress();
    }

    public List<Football_Game> findFutureGames() {
        return gameRepository.findFutureGames();
    }

    @Transactional
    public void deleteGame(int id) {
        gameRepository.deleteGame(id);
    }
}
