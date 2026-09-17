package net.ent.etnc.rl_arenas.services.impl;

import net.ent.etnc.rl_arenas.models.enums.StatutMatch;
import net.ent.etnc.rl_arenas.repositories.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchCheckService {

    private final MatchRepository matchRepository;

    public MatchCheckService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public boolean hasBlockingMatch(Long teamId) {

        List<StatutMatch> statutsBloquants = List.of(
                StatutMatch.PROGRAMME,
                StatutMatch.EN_COURS
        );

        return matchRepository.existsBlockingMatch(teamId, statutsBloquants);
    }
}