package net.ent.etnc.rl_arenas.services;

import net.ent.etnc.rl_arenas.dtos.TeamRequestDto;
import net.ent.etnc.rl_arenas.dtos.TeamUpdateRequestDto;
import net.ent.etnc.rl_arenas.models.entities.Team;
import net.ent.etnc.rl_arenas.services.commons.Service;

public interface TeamService extends Service<Team, Long> {
    Team create(TeamRequestDto dto);

    Team update(Long id, TeamUpdateRequestDto dto);

    Team delete(Long id);
}