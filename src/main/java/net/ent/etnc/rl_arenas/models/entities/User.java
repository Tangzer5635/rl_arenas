package net.ent.etnc.rl_arenas.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.ent.etnc.rl_arenas.models.commons.AbstractPersistableWithIdSetter;
import net.ent.etnc.rl_arenas.models.enums.Role;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "USERS",
        uniqueConstraints = @UniqueConstraint(name = "uk_USER_pseudo", columnNames = {"pseudo"}))
@EqualsAndHashCode(callSuper = false, of = {"pseudo"})
@ToString(callSuper = true, of = {"pseudo", "role"})
public class User extends AbstractPersistableWithIdSetter<Long> implements UserDetails {

    @Getter
    @Setter
    @NotNull(message = "email ne doit pas être null")
    @NotEmpty(message = "email ne doit pas être vide")
    @NotBlank(message = "email doit contenir des caractères lisibles")
    @Length(min = 1, max = 150, message = "email doit avoir entre 1 et 150 caractères")
    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Getter
    @Setter
    @NotNull(message = "pseudo ne doit pas être null")
    @NotEmpty(message = "pseudo ne doit pas être vide")
    @NotBlank(message = "pseudo doit contenir des caractères lisibles")
    @Length(min = 8, max = 50, message = "pseudo doit avoir entre 8 et 50 caractères")
    @Column(name = "pseudo", length = 50, nullable = false)
    private String pseudo;

    @Getter
    @Setter
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Column(name = "password", nullable = false)
    private String password;

    @Getter
    @Setter
    @NotNull(message = "Le rôle est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    @Getter
    @Setter
    @Column(name = "active", nullable = false)
    private boolean active = true;

    /**
     * Retourne les autorités Spring Security de cet utilisateur.
     * Inclut le rôle propre et tous les rôles hérités (hiérarchie cumulative).
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAllRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .toList();
    }

    /** Le pseudo est utilisé comme identifiant de connexion. */
    @Override
    public String getUsername() {
        return pseudo;
    }

    /** Un compte inactif est considéré comme désactivé par Spring Security. */
    @Override
    public boolean isEnabled() {
        return active;
    }



}