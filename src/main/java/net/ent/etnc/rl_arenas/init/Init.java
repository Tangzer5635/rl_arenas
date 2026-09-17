package net.ent.etnc.rl_arenas.init;

import net.ent.etnc.rl_arenas.dtos.MatchRequestDto;
import net.ent.etnc.rl_arenas.models.entities.Team;
import net.ent.etnc.rl_arenas.models.entities.User;
import net.ent.etnc.rl_arenas.models.enums.Role;
import net.ent.etnc.rl_arenas.services.MatchService;
import net.ent.etnc.rl_arenas.services.TeamService;
import net.ent.etnc.rl_arenas.services.UserService;
import net.ent.etnc.rl_arenas.services.commons.ServiceException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Init implements CommandLineRunner {

    private final TeamService teamService;
    private final MatchService matchService;
    private User admin, j1;
    private Team teamLorient, teamNantes;
    private UserService userService;

    public Init(
            UserService userService,
            TeamService teamService,
            MatchService matchService) {

        this.userService = userService;
        this.teamService = teamService;
        this.matchService = matchService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userService.count() > 0) return;

        initUsers();
        initTeams();
        initMatches();
    }

    private void initUsers() throws ServiceException {
        admin = userService.create(
                buildUser("tangzersite", "Tanguy1234", "tanguy@gmail.com", Role.ADMIN)
        );

        j1 = userService.create(buildUser("Joueur 1", "123456789", "joueurs1@gmail.com", Role.JOUEUR)
        );
    }

    private void initTeams() throws ServiceException {
        teamLorient = teamService.create(
                buildTeam("Football Club de Lorient", "FCL", "EU")
        );

        teamNantes = teamService.create(
                buildTeam("Football Club de Nantes", "FCN", "EU")
        );
    }

    private void initMatches() throws ServiceException {
        MatchRequestDto matchDto = MatchRequestDto.builder()
                .homeTeamId(teamLorient.getId())
                .awayTeamId(teamNantes.getId())
                .scheduledDate(LocalDateTime.now().plusDays(1))
                .build();

        matchService.create(matchDto);
    }

    private Team buildTeam(String nom, String tag, String region) throws ServiceException {

        Team team = new Team();
        team.setNom(nom);
        team.setTag(tag);
        team.setRegion(region);

        return team;
    }

    private User buildUser(String pseudo, String password, String email, Role role) {

        User u = new User();
        u.setPseudo(pseudo);
        u.setEmail(email);
        u.setPassword(password);
        u.setRole(role);
        u.setActive(true);

        return u;
    }
}