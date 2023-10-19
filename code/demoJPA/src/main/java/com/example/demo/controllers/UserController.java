package com.example.demo.controllers;

import javax.servlet.http.HttpSession;

import com.example.data.Event;
import com.example.data.Football_Game;
import com.example.data.Player;
import com.example.data.Team;
import com.example.demo.*;
import com.example.formdata.*;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class UserController {

    Timestamp now = Timestamp.valueOf("2022-09-23 10:25:10.0");

    @Autowired
    PlayerService playerService;

    @Autowired
    Football_GameService gameService;

    @Autowired
    EventService eventService;

    @Autowired
    TeamService teamService;

    // ==================== Events ====================
    @GetMapping("/eventsMenu")
    public String eventsMenu() {
        return "adminOrUser\\eventMenu";
    }

    @GetMapping("/stateEvents")
    public String stateEvents(@RequestParam(name = "type", required = true) String type, Model m) {

        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);

        Football_Game g = this.gameService.findGame((int) session.getAttribute("gameId"));

        boolean valid = false;
        switch (type) {
            case "Game started":
                if (g.getState() == 0) {
                    valid = true;
                    g.setState(1);
                }
                break;
            case "Game finished":
                if (g.getState() == 1) {
                    if (g.getA_Goals() > g.getB_Goals()) {
                        g.getTeams().get(0).setVictories(g.getTeams().get(0).getVictories() + 1);
                        g.getTeams().get(1).setDefeats(g.getTeams().get(1).getDefeats() + 1);

                    } else if (g.getA_Goals() < g.getB_Goals()) {
                        g.getTeams().get(0).setDefeats(g.getTeams().get(0).getDefeats() + 1);
                        g.getTeams().get(1).setVictories(g.getTeams().get(1).getVictories() + 1);
                    } else {
                        g.getTeams().get(0).setDraws(g.getTeams().get(0).getDraws() + 1);
                        g.getTeams().get(1).setDraws(g.getTeams().get(1).getDraws() + 1);
                    }
                    valid = true;
                    g.setState(3);

                    g.getTeams().get(0).setNumber_of_games(g.getTeams().get(0).getNumber_of_games() + 1);
                    g.getTeams().get(1).setNumber_of_games(g.getTeams().get(1).getNumber_of_games() + 1);
                }
                break;
            case "Game interrupted":
                if (g.getState() == 1) {
                    valid = true;
                    g.setState(2);
                }
                break;
            case "Resume game":
                if (g.getState() == 2) {
                    valid = true;
                    g.setState(1);
                }
                break;
        }

        if (valid) {

            Event e = new Event(type, new Timestamp(System.currentTimeMillis()),
                    g);
            e.setGame(g);

            this.eventService.addEvent(e);
        } else {
            m.addAttribute("error", "Error: invalid action!");
            return "eventErrors";
        }

        return "redirect:/homepage";
    }

    @GetMapping("/newPlayerTimeEvent")
    public String newPlayerTimeEvent(@RequestParam(name = "type", required = true) String type, Model m) {
        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);
        session.setAttribute("eventType", type);

        List<Team> teams = this.gameService.findGame((int) session.getAttribute("gameId")).getTeams();

        m.addAttribute("allPlayers", this.playerService.getGamePlayers(teams.get(0).getId(), teams.get(1).getId()));
        m.addAttribute("allTeams", teams);
        m.addAttribute("event", new FormTimeOfEvent());
        m.addAttribute("eventType", type);

        return "adminOrUser\\teamPlayerMinute";
    }

    @PostMapping("/saveEventTimeAndPlayer")
    public String saveEventTimeAndPlayer(@ModelAttribute FormTimeOfEvent fevent, Model m) {
        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);

        String type = (String) session.getAttribute("eventType");

        Football_Game g = this.gameService.findGame((int) session.getAttribute("gameId"));
        Player p = this.playerService.findPlayerById(Integer.parseInt(fevent.getPlayerId()));
        List<Event> gameEvents = this.eventService.sortEvents(g.getId());

        Team t = null;
        switch (type) {
            case "Goal":
                if (g.getState() != 1) {
                    m.addAttribute("error", "Error: The game has yet to start/resume!");
                    return "eventErrors";
                }
                for (Event e : gameEvents) {
                    if (e.getType().equals("Red card")
                            && e.getPlayer().getId() == Integer.parseInt(fevent.getPlayerId())) {
                        m.addAttribute("error", "Error: The player is no longer in game!");
                        return "eventErrors";
                    }
                }
                if (fevent.getTeam().equals(g.getTeams().get(0).getName())) {
                    g.setA_Goals(g.getA_Goals() + 1);
                } else {
                    g.setB_Goals(g.getB_Goals() + 1);
                }

                t = this.teamService.findTeam(fevent.getTeam());
                if (t.getName().equals(p.getTeam().getName())) {
                    p.setNumber_of_goals(p.getNumber_of_goals() + 1);

                    if (t.getBest_goal_scorer() == null) {
                        t.setBest_goal_scorer(Integer.toString(p.getId()));
                    } else {
                        Player best_scorer = this.playerService
                                .findPlayerById(Integer.parseInt(t.getBest_goal_scorer()));
                        if (best_scorer.getNumber_of_goals() < p.getNumber_of_goals()) {
                            t.setBest_goal_scorer(Integer.toString(p.getId()));
                        }
                    }

                }
                break;
            case "Yellow card":
                if (g.getState() == 0) {
                    m.addAttribute("error", "Error: The game has yet to start");
                    return "eventErrors";
                }
                int nr_of_yellow_cards = 1;
                for (Event e : gameEvents) {
                    if (e.getType().equals("Red card")
                            && e.getPlayer().getId() == Integer.parseInt(fevent.getPlayerId())) {
                        m.addAttribute("error", "Error: The player is no longer in game!");
                        return "eventErrors";
                    }
                    if (e.getType().equals("Yellow card")
                            && e.getPlayer().getId() == Integer.parseInt(fevent.getPlayerId())) {
                        nr_of_yellow_cards++;
                    }
                }

                t = p.getTeam();
                if (t != null) {
                    Event e = new Event((String) session.getAttribute("eventType"),
                            new Timestamp(System.currentTimeMillis()),
                            g, p, t);

                    this.eventService.addEvent(e);
                }
                if (nr_of_yellow_cards == 2) {
                    Event e = new Event("Red card",
                            new Timestamp(System.currentTimeMillis()),
                            g, p, t);
                    this.eventService.addEvent(e);
                }
                return "redirect:/homepage";

            case "Red card":
                if (g.getState() == 0) {
                    m.addAttribute("error", "Error: The game has yet to start");
                    return "eventErrors";
                }
                t = p.getTeam();
                break;
        }

        if (t != null) {
            Event e = new Event((String) session.getAttribute("eventType"),
                    new Timestamp(System.currentTimeMillis()), g,
                    p, t);

            this.eventService.addEvent(e);
        }

        return "redirect:/homepage";
    }
}
