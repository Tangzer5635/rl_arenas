package net.ent.etnc.rl_arenas.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import net.ent.etnc.rl_arenas.models.enums.StatutMatch;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponseDto {

    private Long id;
    private Long homeTeamId;
    private String homeTeamName;
    private Long awayTeamId;
    private String awayTeamName;
    @NotNull(message = "date ne doit pas être null")
    private LocalDateTime scheduledDate;
    @NotNull(message = "statutMatch ne doit pas être null")
    private StatutMatch status;
    @PositiveOrZero(message = "scoreDomicile doit être positif ou nul")
    private Integer scoreHome;
    @PositiveOrZero(message = "scoreExterieur doit être positif ou nul")
    private Integer scoreAway;
}