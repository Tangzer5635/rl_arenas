package net.ent.etnc.rl_arenas.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishMatchRequestDto {
    private Integer scoreHome;
    private Integer scoreAway;
}