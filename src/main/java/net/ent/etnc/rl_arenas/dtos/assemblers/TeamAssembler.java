package net.ent.etnc.rl_arenas.dtos.assemblers;

import net.ent.etnc.rl_arenas.dtos.TeamRequestDto;
import net.ent.etnc.rl_arenas.dtos.TeamResponseDto;
import net.ent.etnc.rl_arenas.models.entities.Team;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamAssembler {

    public TeamResponseDto toDto(Team team) {
        return TeamResponseDto.builder()
                .id(team.getId())
                .nom(team.getNom())
                .tag(team.getTag())
                .region(team.getRegion())
                .actif(team.isActive())
                .build();
    }

    public List<TeamResponseDto> toDtos(List<Team> teams) {
        return teams.stream()
                .map(this::toDto)
                .toList();
    }

    public Team toEntity(TeamRequestDto teamDto) {
        Team team = new Team();
        if (teamDto.getId() != null) {
            team.setId(teamDto.getId());
        }
        team.setNom(teamDto.getNom());
        team.setTag(teamDto.getTag());
        team.setRegion(teamDto.getRegion());
        team.setActive(teamDto.isActive());
        return team;
    }

}