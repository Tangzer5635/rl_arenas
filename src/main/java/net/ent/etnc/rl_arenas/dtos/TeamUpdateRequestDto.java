package net.ent.etnc.rl_arenas.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamUpdateRequestDto {

    @NotBlank(message = "nom doit contenir des caractères lisibles")
    private String nom;
    @NotBlank(message = "region doit contenir des caractères lisibles")
    private String region;
    private boolean active;
}