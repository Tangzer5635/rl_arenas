package net.ent.etnc.rl_arenas.dtos.assemblers;

import net.ent.etnc.rl_arenas.dtos.MatchRequestDto;
import net.ent.etnc.rl_arenas.dtos.MatchResponseDto;
import net.ent.etnc.rl_arenas.models.entities.Match;
import net.ent.etnc.rl_arenas.models.entities.Team;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MatchAssembler {

    public MatchResponseDto toDto(Match match) {
        return MatchResponseDto.builder()
                .id(match.getId())

                .homeTeamId(match.getDomicile().getId())
                .homeTeamName(match.getDomicile().getNom())

                .awayTeamId(match.getExterieur().getId())
                .awayTeamName(match.getExterieur().getNom())

                .scheduledDate(match.getDate())
                .status(match.getStatutMatch())

                .scoreHome(match.getScoreDomicile())
                .scoreAway(match.getScoreExterieur())

                .build();
    }

    public List<MatchResponseDto> toDtos(List<Match> matchs) {
        return matchs.stream()
                .map(this::toDto)
                .toList();
    }

    public Match toEntity(MatchRequestDto matchDto, Team homeTeam, Team awayTeam) {
        Match match = new Match();

        match.setDomicile(homeTeam);
        match.setExterieur(awayTeam);
        match.setDate(matchDto.getScheduledDate());

        return match;
    }
}