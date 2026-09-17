package net.ent.etnc.rl_arenas.repositories;

import net.ent.etnc.rl_arenas.models.entities.Team;
import net.ent.etnc.rl_arenas.repositories.commons.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends BaseRepository<Team> {

    boolean existsByNom(String nom);

    boolean existsByTag(String tag);

    boolean existsByNomAndIdNot(String nom, Long id);

}