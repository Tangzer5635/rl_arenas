package net.ent.etnc.rl_arenas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.ent.etnc.rl_arenas.models.enums.Role;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    @NotBlank(message = "pseudo doit contenir des caractères lisibles")
    private String pseudo;
    @NotBlank(message = "email doit contenir des caractères lisibles")
    private String email;
    @NotNull(message = "Le rôle est obligatoire")
    private Role role;
    private boolean active;
}