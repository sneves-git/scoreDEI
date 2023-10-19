package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.example.data.Event;
import com.example.data.Football_Game;
import com.example.data.Player;
import com.example.data.User;
import com.example.data.Team;
import com.example.demo.*;
import com.example.formdata.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONObject;
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
public class DataController {

    Timestamp now = new Timestamp(System.currentTimeMillis());

    @Autowired
    TeamService teamService;
    @Autowired
    UserService userService;

    @Autowired
    PlayerService playerService;

    @Autowired
    Football_GameService gameService;

    @Autowired
    EventService eventService;

    // ==================== Populate BD ====================
    @GetMapping("/createData")
    public String createData() {
        return "createData";
    }

    @PostMapping("/insertData")
    public String insertData(Model model) throws IOException {

        // ------ Populate database with user information
        User[] users = {
                new User("admin1", "tatiana", "tatiana@gmail.com", "tatiana", "934567890", true),
                new User("admin2", "sofia", "sofia@gmail.com", "sofia", "934536999", true),
                new User("admin3", "xx", "xx@gmail.com", "xx", "934500890", true)
        };

        for (User u : users) {
            if (this.userService.getUserByUsername(u.getUsername()) == null) {
                this.userService.addUser(u);
            }
        }
        // ------- Populate database with teams information
        Unirest.setTimeouts(0, 0);
        // isto contem: nome e imagem

        try {
            HttpResponse<JsonNode> response_teams = Unirest
                    .get("https://v3.football.api-sports.io/teams?league=94&season=2020")
                    .header("x-rapidapi-key", "09ab8169d43b1b5a13fdc40bc7b99d9f")
                    .header("x-rapidapi-host", "v3.football.api-sports.io")
                    .asJson();

            JsonObject object = (JsonObject) new JsonParser().parse(response_teams.getBody().toString());
            JsonArray response = (JsonArray) object.get("response");
            for (int i = 0; i < response.size(); ++i) {
                JsonObject obj = (JsonObject) response.get(i);
                JsonObject team = (JsonObject) obj.get("team");
                String team_name = (String) team.get("name").toString().replace("\"", "");

                String image = (String) team.get("logo").toString().replace("\"", "");
                if(this.teamService.findTeam(team_name) == null){
                    this.teamService.addTeam(new Team(team_name, null, image, 0, 0, 0, 0));
                }
            }
        } catch (UnirestException e1) {
            e1.printStackTrace();
        }

        // ------- Populate database with teams information
        Unirest.setTimeouts(0, 0);
        // isto contem: nome, data de nascimento, team, posicao , numero de golos
        int total = 30;
        try {
            int k = 1;
            while (true) {
                HttpResponse<JsonNode> response_players = Unirest
                        .get("https://v3.football.api-sports.io/players?league=94&season=2020&page=" + k)
                        .header("x-rapidapi-key", "09ab8169d43b1b5a13fdc40bc7b99d9f")
                        .header("x-rapidapi-host", "v3.football.api-sports.io")
                        .asJson();

                JsonObject object = (JsonObject) new JsonParser().parse(response_players.getBody().toString());
                JsonArray response = (JsonArray) object.get("response");

                for (int i = 0; i < response.size(); ++i) {
                    JsonObject obj = (JsonObject) response.get(i);

                    // name
                    JsonObject player = (JsonObject) obj.get("player");
                    String name = (String) player.get("name").toString().replace("\"", "");

                    // Birthday
                    JsonObject birth = (JsonObject) player.get("birth");
                    String birth_date = (String) birth.get("date").toString().replace("\"", "");
                    Date birthday = Date.valueOf(birth_date);

                    // Team
                    JsonArray statistics = (JsonArray) obj.get("statistics");
                    JsonObject statistics0 = (JsonObject) statistics.get(0);

                    JsonObject team = (JsonObject) statistics0.get("team");
                    String team_name = (String) team.get("name").toString().replace("\"", "");
                    Team t = this.teamService.findTeam(team_name);

                    // position
                    JsonObject games = (JsonObject) statistics0.get("games");
                    String position = (String) games.get("position").toString().replace("\"", "");
                    this.playerService.addPlayer(new Player(name, position, birthday, 0, t));
                }
                k++;

                if (k >= 10) {
                    break;
                }
            }

        } catch (UnirestException e1) {
            e1.printStackTrace();
        }

        return "redirect:/landingPage";
    }

    // ==================== LandingPage ====================
    @GetMapping("/landingPage")
    public String landingPage() {
        return "landingPage";
    }

    // ==================== Login ====================
    @GetMapping("/loginResult")
    public String login(Model m) {
        m.addAttribute("login", new Login());
        return "login";
    }

    @GetMapping("/login")
    public String loginResult(@ModelAttribute Login data, Model m) {
        Optional<User> u = this.userService.getUser(data.getUsername(), data.getPassword());

        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);

        if (u.isPresent()) {
            User user = u.get();
            session.setAttribute("user", user);
            return "redirect:/homepage";

        } else {
            session.setAttribute("user", null);
            return "loginFail";
        }
    }

    // ==================== Logout ====================
    @GetMapping("/logout")
    public String logout() {

        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);
        session.setAttribute("user", null);

        return "redirect:/homepage";
    }

    // ==================== Homepage ====================
    @GetMapping("/homepage")
    public String adminHomepage(Model m) {
        List<Football_Game> gamesInProgress = this.gameService.findGamesInProgress();
        List<Football_Game> FutureGames = this.gameService.findFutureGames();
        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);

        m.addAttribute("gamesInProgress", gamesInProgress);
        m.addAttribute("futureGames", FutureGames);
        m.addAttribute("user", session.getAttribute("user"));
        return "homepage";
    }

    // ==================== Statistics ====================
    @GetMapping("/statisticsMenu")
    public String statsMenu(@RequestParam(name = "type", required = true) String type, Model m) {
        List<Team> t = null;
        if(type.equals("Games")){
            t = this.teamService.getAllTeamsByNumberOfGames();
        }else if(type.equals("Victories")){
            t = this.teamService.getAllTeamsByNumberOfVictories();
        }else if(type.equals("Draws")){
            t = this.teamService.getAllTeamsByNumberOfDraws();
        }else if(type.equals("Defeats")){
            t = this.teamService.getAllTeamsByNumberOfDefeats();
        }
        m.addAttribute("teams", t);
        List<Player> p = this.playerService.getBestScorer();

        if (p.get(0).getNumber_of_goals() == 0) {
            m.addAttribute("bestScorer", "None");
        } else {
            m.addAttribute("bestScorer", p.get(0).getName() + " - " + p.get(0).getTeam().getName());

        }
        return "statisticsMenu";
    }

    // ==================== Game Info ====================
    @GetMapping("/gameInfo")
    public String guestOrUser_gameInfo(@RequestParam(name = "id", required = true) int id, Model m) {
        Football_Game g = this.gameService.findGame(id);

        Long t = 0L, t_aux = 0L;
        Long now_milis = System.currentTimeMillis();

        boolean interruption = false;

        if (g.getState() != 0) {
            for (Event e : g.getEvents()) {
                if (e.getType().equals("Game started")) {
                    t = now_milis - e.getTime_of_event().getTime();
                }
                if (e.getType().equals("Game interrupted")) {
                    interruption = true;
                    t_aux = e.getTime_of_event().getTime();
                }
                if (e.getType().equals("Resume game")) {
                    interruption = false;
                    t = t - (e.getTime_of_event().getTime() - t_aux);
                }
            }
            if (interruption) {
                t = t - (now_milis - t_aux);
            }
        }
        t = t / 60000;

        List<List<String>> playersList = new ArrayList<>();
        List<Event> events = eventService.sortEvents(g.getId());
        for (Event e : events) {
            List<String> event = new ArrayList<>();
            event.add(e.getTime_of_event().toString());
            event.add(e.getType());

            if (e.getPlayer() != null) {
                event.add(e.getPlayer().getName());
                event.add(e.getTeam().getName());

            } else {
                event.add("");
                event.add("");
            }
            playersList.add(event);
        }
        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);
        session.setAttribute("gameId", id);

        m.addAttribute("game", g);
        m.addAttribute("game_time", t);
        m.addAttribute("eventsInfo", playersList);
        m.addAttribute("user", session.getAttribute("user"));

        return "gameInfo";

    }

}