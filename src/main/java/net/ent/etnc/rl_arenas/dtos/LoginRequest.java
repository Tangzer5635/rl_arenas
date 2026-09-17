package net.ent.etnc.rl_arenas.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Corps de la requête d'authentification ({@code POST /api/v1/auth/login/}).
 */
@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "pseudo doit contenir des caractères lisibles")
    private String pseudo;
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
}
