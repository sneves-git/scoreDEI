package com.example.demo;

import java.util.List;

import com.example.data.Event;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
    @Query("select e from Event e where e.game.id = ?1 order by e.time_of_event")
    public List<Event> sortEvents(int gameId);

    @Modifying
    @Query("delete from Event e where e.player.id = ?1")
    public void deleteEventsFromPlayer(int id);

    @Modifying
    @Query("delete from Event e where e.game.id = ?1")
    public void deleteEventsFromGame(int id);
}
