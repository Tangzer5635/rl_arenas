package net.ent.etnc.rl_arenas.services;

import net.ent.etnc.rl_arenas.dtos.FinishMatchRequestDto;
import net.ent.etnc.rl_arenas.dtos.MatchRequestDto;
import net.ent.etnc.rl_arenas.models.entities.Match;
import net.ent.etnc.rl_arenas.services.commons.Service;

public interface MatchService extends Service<Match, Long> {
    Match create(MatchRequestDto dto);

    Match start(Long id);

    Match finish(Long id, FinishMatchRequestDto finishDto);

    Match cancel(Long id);
}