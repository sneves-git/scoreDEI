package com.example.demo;

import java.util.List;

import com.example.data.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public List<Event> sortEvents(int gameId) {
        return eventRepository.sortEvents(gameId);
    }

    @Transactional
    public void deleteEventsFromPlayer(int playerId) {
        eventRepository.deleteEventsFromPlayer(playerId);
    }

    @Transactional
    public void deleteEventsFromGame(int id) {
        eventRepository.deleteEventsFromGame(id);
    }

}
