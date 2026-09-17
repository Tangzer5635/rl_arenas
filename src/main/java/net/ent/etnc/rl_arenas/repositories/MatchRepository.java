package net.ent.etnc.rl_arenas.repositories;

import net.ent.etnc.rl_arenas.models.enums.StatutMatch;
import net.ent.etnc.rl_arenas.models.entities.Match;
import net.ent.etnc.rl_arenas.repositories.commons.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends BaseRepository<Match> {


    @Query("""
                SELECT COUNT(m) > 0
                FROM Match m
                WHERE (m.domicile.id = :teamId
                       OR m.exterieur.id = :teamId)
                  AND m.statutMatch IN :statuts
            """)
    boolean existsBlockingMatch(@Param("teamId") Long teamId, @Param("statuts") List<StatutMatch> statuts);
}