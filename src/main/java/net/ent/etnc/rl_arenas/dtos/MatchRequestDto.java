package net.ent.etnc.rl_arenas.dtos;

import jakarta.validation.constraints.Future;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchRequestDto {

    private Long homeTeamId;
    private Long awayTeamId;
    @Future(message = "date ne doit pas être dans le passé ou dans le présent")
    private LocalDateTime scheduledDate;
}