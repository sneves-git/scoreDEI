package com.example.demo.controllers;

import com.example.data.Football_Game;
import com.example.data.Player;
import com.example.data.User;
import com.example.data.Team;
import com.example.demo.EventService;
import com.example.demo.Football_GameService;
import com.example.demo.PlayerService;
import com.example.demo.TeamService;
import com.example.demo.UserService;
import com.example.formdata.*;
import java.lang.Math;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    EventService eventService;

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;

    @Autowired
    PlayerService playerService;

    @Autowired
    Football_GameService gameService;

    // ==================== Settings ====================
    @GetMapping("/settings")
    public String settings(Model m) {

        return "admin\\settings";
    }

    // ============================== Add User ==============================
    @GetMapping("/registerUser")
    public String registerUser(Model m) {
        m.addAttribute("usernameMessage", "");
        m.addAttribute("emailMessage", "");
        m.addAttribute("phoneMessage", "");

        m.addAttribute("user", new User());
        return "admin\\user\\registerUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, Model m) {

        User username = this.userService.checkUsername(user.getUsername());
        User email = this.userService.checkEmail(user.getEmail());
        User phone = this.userService.checkPhone(user.getPhone());

        if (username == null && email == null && phone == null) {
            this.userService.addUser(user);
        } else {
            if (username != null) {
                m.addAttribute("usernameMessage", "This username is already in use.");
            }
            if (email != null) {
                m.addAttribute("emailMessage", "This email is already in use.");
            }
            if (phone != null) {
                m.addAttribute("phoneMessage", "This phone is already in use.");
            }

            m.addAttribute("user", new User());
            return "admin\\user\\registerUser";
        }
        return "redirect:/homepage";
    }

    // ==================== Manage Users ====================
    @GetMapping("/manageUsers")
    public String manageUsers(Model m) {
        List<User> users = this.userService.getAllUsers();
        m.addAttribute("users", users);
        return "admin\\user\\manageUsers";
    }

    // ==================== Edit User ====================
    @GetMapping("/editUser")
    public String editUser(@RequestParam(name = "id", required = true) int id, Model m) {

        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);
        session.setAttribute("userId", id);

        User u = this.userService.getUserById(id);

        m.addAttribute("oldInfo", u);
        m.addAttribute("user", new User());
        return "admin\\user\\editUser";
    }

    @PostMapping("/saveEditedUser")
    public String saveEditedUser(@ModelAttribute User user, Model m) {
        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);
        int id = (int) session.getAttribute("userId");
        User u = this.userService.getUserById(id);

        m.addAttribute("emailMessage", "");
        m.addAttribute("usernameMessage", "");
        m.addAttribute("phoneMessage", "");

        boolean flag = false;
        if (user.getEmail().length() != 0) {
            if (!u.getEmail().equals(user.getEmail())) {
                User email = this.userService.checkEmail(user.getEmail());
                if (email != null) {
                    flag = true;
                    m.addAttribute("emailMessage", "This email is already in use.");
                }
            }
        }
        if (user.getUsername().length() != 0) {
            if (!u.getUsername().equals(user.getUsername())) {
                User username = this.userService.checkUsername(user.getUsername());
                if (username != null) {
                    flag = true;
                    m.addAttribute("usernameMessage", "This username is already in use.");
                }
            }
        }
        if (user.getPhone().length() != 0) {
            if (!u.getPhone().equals(user.getPhone())) {
                User phone = this.userService.checkPhone(user.getPhone());
                if (phone != null) {
                    flag = true;
                    m.addAttribute("phoneMessage", "This phone is already in use.");
                }
            }
        }

        // error
        if (flag) {
            m.addAttribute("oldInfo", u);
            m.addAttribute("user", new User());
            return "admin\\user\\editUser";
        }

        if (user.getEmail().length() != 0) {
            u.setEmail(user.getEmail());
        }
        if (user.getUsername().length() != 0) {
            u.setUsername(user.getUsername());
        }
        if (user.getPhone().length() != 0) {
            u.setPhone(user.getPhone());
        }
        if (user.getPassword().length() != 0) {
            u.setPassword(user.getPassword());
        }
        if (user.getName().length() != 0) {
            u.setName(user.getName());
        }

        u.setAdmin(user.isAdmin());
        this.userService.addUser(u);
        return "redirect:/manageUsers";
    }

    // ==================== Delete User ====================
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam(name = "username", required = true) String username) {
        this.userService.deleteUser(username);
        return "redirect:/manageUsers";
    }

    // ============================== Add Team ==============================
    @GetMapping("/addNewTeam")
    public String addTeam(Model m) {
        m.addAttribute("nameMessage", "");
        m.addAttribute("imageMessage", "");
        m.addAttribute("team", new FormTeam());
        return "admin\\team\\addTeam";
    }

    @PostMapping("/saveTeam")
    public String saveTeam(@ModelAttribute FormTeam fteam, Model m) {
        Team name = this.teamService.checkName(fteam.getName());
        Team image = this.teamService.checkImage(fteam.getImage());

        m.addAttribute("nameMessage", "");
        m.addAttribute("imageMessage", "");

        if (name == null && image == null) {
            Team team = new Team(fteam.getName(), fteam.getBest_goal_scorer(), fteam.getImage(),
                    fteam.getNumber_of_games(),
                    fteam.getVictories(), fteam.getDefeats(), fteam.getDraws());
            this.teamService.addTeam(team);
            return "redirect:/homepage";
        }
        if (name != null) {
            m.addAttribute("nameMessage", "Team name is already in use.");
        }
        if (image != null) {
            m.addAttribute("imageMessage", "Image is already in use.");
        }

        m.addAttribute("team", new FormTeam());
        return "admin\\team\\addTeam";

    }

    // ==================== Manage team ====================
    @GetMapping("/manageTeams")
    public String manageTeams(Model m) {
        List<Team> teams = this.teamService.getAllTeams();
        m.addAttribute("teams", teams);
        return "admin\\team\\manageTeams";
    }

    // ==================== Edit Team ====================
    @GetMapping("/editTeam")
    public String editTeam(@RequestParam(name = "id", required = true) int id, Model m) {
        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);

        session.setAttribute("teamId", id);

        Team t = this.teamService.getTeamById(id);

        m.addAttribute("team", new FormTeam());
        m.addAttribute("oldTeam", t);
        return "admin\\team\\editTeam";
    }

    @PostMapping("/saveEditedTeam")
    public String saveEditedTeam(@ModelAttribute FormTeam team, Model m) {
        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);
        int id = (int) session.getAttribute("teamId");
        Team t = this.teamService.getTeamById(id);

        boolean flag = false;
        if (team.getName().length() != 0) {
            if (!team.getName().equals(t.getName())) {
                Team name = this.teamService.checkName(team.getName());
                if (name != null) {
                    m.addAttribute("nameMessage", "Team name is already in use.");
                    flag = true;
                }
            }
        }
        if (team.getImage().length() != 0) {
            if (!team.getImage().equals(t.getImage())) {
                Team image = this.teamService.checkImage(team.getImage());
                if (image != null) {
                    m.addAttribute("imageMessage", "Image is already in use.");
                    flag = true;
                }
            }
        }

        if (flag) {
            m.addAttribute("team", new FormTeam());
            m.addAttribute("oldTeam", t);
            return "admin\\team\\editTeam";
        }
        if (team.getName().length() != 0) {
            t.setName(team.getName());
        }
        if (team.getImage().length() != 0) {
            t.setImage(team.getImage());
        }
        this.teamService.addTeam(t);
        return "redirect:/manageTeams";
    }

    // ==================== Delete Team ====================
    @GetMapping("/deleteTeam")
    public String deleteTeam(@RequestParam(name = "teamName", required = true) String teamName) {
        Team t = this.teamService.findTeam(teamName);
        List<Football_Game> games = this.gameService.getAllGames();
        for (Football_Game g : games) {
            if (g.getTeams().get(0).getName().equals(teamName)
                    || g.getTeams().get(1).getName().equals(teamName)) {
                this.eventService.deleteEventsFromGame(g.getId());
                this.gameService.deleteGame(g.getId());
            }
        }
        this.playerService.deletePlayerFromTeam(t.getId());
        this.teamService.deleteTeam(teamName);

        return "redirect:/manageTeams";
    }

    // ============================== Add Player ==============================
    @GetMapping("/addNewPlayer")
    public String addPlayer(Model m) {
        String time = (new Timestamp(System.currentTimeMillis()).toString());
        String[] arrTime = time.split(" ");
        String new_time = arrTime[0];

        m.addAttribute("currentDate", new_time);
        m.addAttribute("player", new FormPlayer());
        m.addAttribute("allTeams", this.teamService.getAllTeams());
        return "admin\\player\\addPlayer";
    }

    @PostMapping("/savePlayer")
    public String savePlayer(@ModelAttribute FormPlayer fplayer) {
        Team t = this.teamService.findTeam(fplayer.getTeam());

        Player player = new Player(fplayer.getName(), fplayer.getGame_position(), fplayer.getBirth_date(),
                fplayer.getNumber_of_goals(), t);

        this.playerService.addPlayer(player);
        return "redirect:/homepage";
    }

    // ==================== Manage Player ====================
    @GetMapping("/managePlayers")
    public String managePlayers(Model m) {
        List<Player> players = this.playerService.getAllPlayers();
        m.addAttribute("players", players);
        return "admin\\player\\managePlayers";
    }

    // ==================== Edit Player ====================
    @GetMapping("/editPlayer")
    public String editPlayer(@RequestParam(name = "id", required = true) int id, Model m) {
        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);
        session.setAttribute("playerId", id);

        Player p = this.playerService.findPlayerById(id);

        m.addAttribute("oldPlayer", p);
        m.addAttribute("player", new FormPlayer());
        m.addAttribute("allTeams", this.teamService.getAllTeams());

        return "admin\\player\\editPlayer";
    }

    @PostMapping("/saveEditedPlayer")
    public String saveEditedPlayer(@ModelAttribute FormPlayer player) {
        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);
        int id = (int) session.getAttribute("playerId");
        Player p = this.playerService.findPlayerById(id);

        if (player.getName().length() != 0) {
            p.setName(player.getName());
        }
        if (player.getGame_position().length() != 0) {
            p.setGame_position(player.getGame_position());
        }
        if (player.getTeam().length() != 0) {
            Team t = this.teamService.findTeam(player.getTeam());
            p.setTeam(t);
        }
        p.setBirthday(player.getBirth_date());

        this.playerService.addPlayer(p);
        return "redirect:/managePlayers";
    }

    // ==================== Delete Player ====================
    @GetMapping("/deletePlayer")
    public String deletePlayer(@RequestParam(name = "id", required = true) int id) {
        this.eventService.deleteEventsFromPlayer(id);
        this.playerService.deletePlayer(id);
        return "redirect:/managePlayers";
    }

    // ============================== Add Game ==============================
    @GetMapping("/addNewGame")
    public String addGame(Model m) {
        String time = (new Timestamp(System.currentTimeMillis()).toString());
        time = time.replace(" ", "T");
        String[] arrTime = time.split(":");
        String new_time = arrTime[0] + ":" + arrTime[1];

        m.addAttribute("currentDate", new_time);
        m.addAttribute("game", new FormGame());
        m.addAttribute("allTeams", this.teamService.getAllTeams());
        return "admin\\game\\addGame";
    }

    @PostMapping("/saveGame")
    public String saveGame(@ModelAttribute FormGame fgame) {
        String date = fgame.getGame_date();
        if (StringUtils.countMatches(date, ":") == 1) {
            date += ":00";
        }
        Timestamp value = Timestamp.valueOf(date.replace("T", " "));

        Football_Game game = new Football_Game(fgame.getA_Goals(), fgame.getB_Goals(), fgame.getLocation(),
                value, 0);

        Team t = this.teamService.findTeam(fgame.getA_Team());
        game.addTeam(t);

        t = this.teamService.findTeam(fgame.getB_Team());

        game.addTeam(t);

        this.gameService.addGame(game);

        return "redirect:/homepage";
    }

    // ==================== Manage Game ====================
    @GetMapping("/manageGames")
    public String manageGames(Model m) {
        List<Football_Game> games = this.gameService.findFutureGames();
        m.addAttribute("games", games);
        return "admin\\game\\manageGames";
    }

    // ==================== Edit Game ====================
    @GetMapping("/editGame")
    public String editGame(@RequestParam(name = "id", required = true) int id, Model m) {
        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);
        session.setAttribute("gameId", id);

        Football_Game game = this.gameService.findGame(id);

        m.addAttribute("oldGame", game);
        m.addAttribute("allTeams", this.teamService.getAllTeams());
        m.addAttribute("game", new FormGame());
        return "admin\\game\\editGame";
    }

    @PostMapping("/saveEditedGame")
    public String saveEditedGame(@ModelAttribute FormGame fgame) {
        ServletRequestAttributes servlet = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = servlet.getRequest().getSession(true);
        int id = (int) session.getAttribute("gameId");
        Football_Game g = this.gameService.findGame(id);

        if (fgame.getA_Team().length() != 0) {
            Team a_team = this.teamService.findTeam(fgame.getA_Team());
            g.getTeams().set(0, a_team);
        }
        if (fgame.getB_Team().length() != 0) {
            Team b_team = this.teamService.findTeam(fgame.getB_Team());

            g.getTeams().set(1, b_team);
        }
        if (fgame.getLocation().length() != 0) {
            g.setLocation(fgame.getLocation());
        }

        String date = fgame.getGame_date();
        if (StringUtils.countMatches(date, ":") == 1) {
            date += ":00";
        }
        Timestamp value = Timestamp.valueOf(date.replace("T", " "));
        g.setGame_date(value);

        this.gameService.addGame(g);
        return "redirect:/manageGames";
    }

    // ==================== Delete Game ====================
    @GetMapping("/deleteGame")
    public String deleteGame(@RequestParam(name = "id", required = true) int id) {
        this.gameService.deleteGame(id);
        return "redirect:/manageGames";
    }
}
