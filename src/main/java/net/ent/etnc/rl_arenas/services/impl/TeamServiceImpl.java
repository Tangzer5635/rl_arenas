package net.ent.etnc.rl_arenas.services.impl;

import net.ent.etnc.rl_arenas.dtos.TeamRequestDto;
import net.ent.etnc.rl_arenas.dtos.TeamUpdateRequestDto;
import net.ent.etnc.rl_arenas.models.entities.Team;
import net.ent.etnc.rl_arenas.repositories.TeamRepository;
import net.ent.etnc.rl_arenas.services.TeamService;
import net.ent.etnc.rl_arenas.services.commons.AbstractService;
import net.ent.etnc.rl_arenas.services.commons.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamServiceImpl extends AbstractService<Team, TeamRepository>
        implements TeamService {

    private final MatchCheckService matchCheckService;

    @Autowired
    public TeamServiceImpl(
            TeamRepository teamRepository,
            MatchCheckService matchCheckService) {

        super(teamRepository);
        this.matchCheckService = matchCheckService;
    }

    @Override
    @Transactional
    public Team create(TeamRequestDto dto) {

        if (repository.existsByNom(dto.getNom())) {
            throw new ServiceException("Le nom de l'équipe est déjà utilisé");
        }

        if (repository.existsByTag(dto.getTag())) {
            throw new ServiceException("Le tag de l'équipe est déjà utilisé");
        }

        Team team = new Team();

        team.setNom(dto.getNom());
        team.setTag(dto.getTag());
        team.setRegion(dto.getRegion());

        team.setActive(true);

        return repository.save(team);
    }

    @Override
    @Transactional
    public Team update(Long id, TeamUpdateRequestDto dto) {

        Team team = repository.findById(id)
                .orElseThrow(() ->
                        new ServiceException("L'équipe n'existe pas")
                );

        if (repository.existsByNomAndIdNot(dto.getNom(), id)) {
            throw new ServiceException(
                    "Le nom de l'équipe est déjà utilisé"
            );
        }
        if (team.isActive() && !dto.isActive()) {

            if (matchCheckService.hasBlockingMatch(id)) {
                throw new ServiceException(
                        "Impossible de désactiver l'équipe car " +
                                "elle participe à un match programmé ou en cours"
                );
            }
        }

        team.setNom(dto.getNom());
        team.setRegion(dto.getRegion());
        team.setActive(dto.isActive());

        return repository.save(team);
    }

    @Override
    @Transactional
    public Team delete(Long id) {

        Team team = repository.findById(id)
                .orElseThrow(() ->
                        new ServiceException("L'équipe n'existe pas")
                );

        if (!team.isActive()) {
            throw new ServiceException(
                    "L'équipe est déjà désactivée"
            );
        }

        if (matchCheckService.hasBlockingMatch(id)) {
            throw new ServiceException(
                    "Impossible de désactiver l'équipe car " +
                            "elle participe à un match programmé ou en cours"
            );
        }
        team.setActive(false);

        return repository.save(team);
    }
}