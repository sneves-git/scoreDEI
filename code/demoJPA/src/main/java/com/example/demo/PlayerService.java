package com.example.demo;

import java.util.List;

import java.util.List;

import com.example.data.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.getAllPlayers();
    }

    public void addPlayer(Player Player) {
        playerRepository.save(Player);
    }

    public Player findPlayerById(int playerId) {
        return playerRepository.findPlayerById(playerId);
    }

    public List<Player> getGamePlayers(int team_A, int team_B) {
        return playerRepository.getGamePlayers(team_A, team_B);
    }

    public List<Player> getBestScorer() {
        return playerRepository.getBestScorer();
    }

    @Transactional
    public void deletePlayer(int id) {
        playerRepository.deletePlayer(id);
    }

    @Transactional
    public void deletePlayerFromTeam(int id) {
        playerRepository.deletePlayerFromTeam(id);
    }
}
