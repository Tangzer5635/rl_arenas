package net.ent.etnc.rl_arenas.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponseDto {

    private Long id;
    private String nom;
    private String tag;
    private String region;
    private boolean actif;
}