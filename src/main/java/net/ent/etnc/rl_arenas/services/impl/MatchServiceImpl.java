package net.ent.etnc.rl_arenas.services.impl;

import net.ent.etnc.rl_arenas.dtos.FinishMatchRequestDto;
import net.ent.etnc.rl_arenas.dtos.MatchRequestDto;
import net.ent.etnc.rl_arenas.dtos.assemblers.MatchAssembler;
import net.ent.etnc.rl_arenas.models.entities.Match;
import net.ent.etnc.rl_arenas.models.entities.Team;
import net.ent.etnc.rl_arenas.models.enums.StatutMatch;
import net.ent.etnc.rl_arenas.repositories.MatchRepository;
import net.ent.etnc.rl_arenas.services.MatchService;
import net.ent.etnc.rl_arenas.services.TeamService;
import net.ent.etnc.rl_arenas.services.commons.AbstractService;
import net.ent.etnc.rl_arenas.services.commons.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchServiceImpl
        extends AbstractService<Match, MatchRepository>
        implements MatchService {

    @Autowired
    private TeamService teamService;

    @Autowired
    private MatchAssembler matchAssembler;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository) {
        super(matchRepository);
    }

    @Override
    @Transactional
    public Match create(MatchRequestDto dto) {
        if (dto.getHomeTeamId().equals(dto.getAwayTeamId())) {
            throw new ServiceException(
                    "L'équipe domicile et l'équipe extérieure doivent être différentes"
            );
        }

        Team homeTeam = teamService.findById(dto.getHomeTeamId())
                .orElseThrow(() ->
                        new ServiceException(
                                "L'équipe domicile n'existe pas"
                        )
                );

        Team awayTeam = teamService.findById(dto.getAwayTeamId())
                .orElseThrow(() ->
                        new ServiceException(
                                "L'équipe extérieure n'existe pas"
                        )
                );

        if (!homeTeam.isActive()) {
            throw new ServiceException(
                    "L'équipe domicile doit être active"
            );
        }

        if (!awayTeam.isActive()) {
            throw new ServiceException(
                    "L'équipe extérieure doit être active"
            );
        }

        Match match = matchAssembler.toEntity(
                dto,
                homeTeam,
                awayTeam
        );

        match.setStatutMatch(StatutMatch.PROGRAMME);

        match.setScoreDomicile(null);
        match.setScoreExterieur(null);

        return repository.save(match);
    }

    @Override
    @Transactional
    public Match start(Long id) {

        Match match = repository.findById(id)
                .orElseThrow(() ->
                        new ServiceException("Le match n'existe pas")
                );

        if (match.getStatutMatch() != StatutMatch.PROGRAMME) {
            throw new ServiceException(
                    "Le match doit être programmé pour pouvoir démarrer"
            );
        }
        match.setStatutMatch(StatutMatch.EN_COURS);
        return repository.save(match);
    }

    @Override
    @Transactional
    public Match finish(Long id, FinishMatchRequestDto finishDto) {

        Match match = repository.findById(id)
                .orElseThrow(() ->
                        new ServiceException("Le match n'existe pas")
                );

        if (match.getStatutMatch() != StatutMatch.EN_COURS) {
            throw new ServiceException(
                    "Le match doit être en cours pour pouvoir être terminé"
            );
        }
        if (finishDto.getScoreHome() == null) {
            throw new ServiceException(
                    "Le score domicile est obligatoire"
            );
        }

        if (finishDto.getScoreAway() == null) {
            throw new ServiceException(
                    "Le score extérieur est obligatoire"
            );
        }
        match.setScoreDomicile(finishDto.getScoreHome());
        match.setScoreExterieur(finishDto.getScoreAway());

        match.setStatutMatch(StatutMatch.TERMINE);

        return repository.save(match);
    }

    @Override
    public Match cancel(Long id) {
        Match match = repository.findById(id)
                .orElseThrow(() ->
                        new ServiceException("Le match n'existe pas")
                );

        if (match.getStatutMatch() != StatutMatch.PROGRAMME) {
            throw new ServiceException(
                    "Seul un match programmé peut être annulé"
            );
        }
        match.setStatutMatch(StatutMatch.ANNULE);
        return repository.save(match);
    }
}